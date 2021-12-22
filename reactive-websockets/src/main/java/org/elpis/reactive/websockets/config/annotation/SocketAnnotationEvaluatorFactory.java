package org.elpis.reactive.websockets.config.annotation;

import lombok.NonNull;
import org.elpis.reactive.websockets.exception.WebSocketConfigurationException;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents a registry of all the implementations of {@link SocketApiAnnotationEvaluator}. Registered as Spring Bean on application startup.
 * <p>Supports custom {@link SocketApiAnnotationEvaluator} implementations.
 * <p><strong>NOTE: </strong>{@link SocketApiAnnotationEvaluator} implementations with duplicate annotations are not permitted - only one implementation per one annotation.
 *
 * @author Alex Zharkov
 * @see SocketApiAnnotationEvaluator
 * @since 0.1.0
 */
@Component
@ComponentScan(basePackageClasses = SocketApiAnnotationEvaluator.class)
public final class SocketAnnotationEvaluatorFactory {
    private final Set<Class<? extends Annotation>> supportedAnnotations;
    private final Map<Class<? extends Annotation>, SocketApiAnnotationEvaluator<?>> annotationEvaluators;

    //TODO: Don't allow duplicates
    public SocketAnnotationEvaluatorFactory(@NonNull final List<SocketApiAnnotationEvaluator<?>> annotationEvaluators) {
        this.supportedAnnotations = annotationEvaluators.stream()
                .map(SocketApiAnnotationEvaluator::getAnnotationType)
                .collect(Collectors.toSet());

        this.annotationEvaluators = annotationEvaluators.stream()
                .collect(Collectors.toMap(SocketApiAnnotationEvaluator::getAnnotationType, Function.identity()));
    }

    @SuppressWarnings("rawtypes")
    public Optional<SocketApiAnnotationEvaluator> getEvaluator(@NonNull final Annotation[] annotations) {
        final List<Annotation> annotationsFiltered = Stream.of(annotations)
                .filter(annotation -> supportedAnnotations.contains(annotation.annotationType()))
                .collect(Collectors.toList());

        if (annotationsFiltered.size() > 1) {
            final String failedAnnotations = annotationsFiltered.stream()
                    .map(annotation -> "@" + annotation.getClass().getName())
                    .collect(Collectors.joining(","));

            throw new WebSocketConfigurationException("Ambiguous WebSocket annotations found " + failedAnnotations
                    + ". Only one declared annotation is legal");
        }

        return annotationsFiltered.stream()
                .findFirst()
                .map(annotation -> annotationEvaluators.get(annotation.annotationType()));
    }
}