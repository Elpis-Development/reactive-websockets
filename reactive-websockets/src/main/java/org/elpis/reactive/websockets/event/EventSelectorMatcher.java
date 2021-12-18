package org.elpis.reactive.websockets.event;

import org.elpis.reactive.websockets.event.annotation.EventSelector;
import org.elpis.reactive.websockets.event.model.WebSocketEvent;

/**
 * Extension of {@link EventSelectorProcessor} with {@link Boolean} result returned by default.

 * @author Alex Zharkov
 * @see EventSelectorProcessor
 * @see WebSocketEvent
 * @see EventSelector
 * @since 0.1.0
 */
public interface EventSelectorMatcher<E extends WebSocketEvent<?>> extends EventSelectorProcessor<E, Boolean> {
}
