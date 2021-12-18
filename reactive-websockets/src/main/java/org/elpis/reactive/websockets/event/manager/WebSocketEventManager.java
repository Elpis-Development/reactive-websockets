package org.elpis.reactive.websockets.event.manager;

import org.elpis.reactive.websockets.event.model.WebSocketEvent;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

/**
 * Contract for posting/receiving declared {@link WebSocketEvent}.
 * @author Alex Zharkov
 * @since 0.1.0
 */
public interface WebSocketEventManager<T extends WebSocketEvent<?>> {
    int MEDIUM_EVENT_QUEUE_SIZE = 256;

    /**
     * Takes any {@link WebSocketEvent} and shares for all listeners.
     *
     * @param t any WebSocketEvent implementation
     * @return {@link Sinks.EmitResult} - result code signaling about success/failure of sending an event
     * @since 0.1.0
     */
    Sinks.EmitResult fire(final T t);

    /**
     * Used to make an event subscription.
     *
     * @return {@link Publisher} - any Publisher implementation containing event sequence
     * @since 0.1.0
     */
    Publisher<T> listen();

    /**
     * Converts {@link Publisher} from {@link #listen() listen} to {@link Mono}.
     *
     * @return {@link Mono}
     * @since 0.1.0
     */
    default Mono<T> asMono() {
        return Mono.from(this.listen());
    }

    /**
     * Converts {@link Publisher} from {@link #listen() listen} to {@link Flux}.
     *
     * @return {@link Flux}
     * @since 0.1.0
     */
    default Flux<T> asFlux() {
        return Flux.from(this.listen());
    }
}
