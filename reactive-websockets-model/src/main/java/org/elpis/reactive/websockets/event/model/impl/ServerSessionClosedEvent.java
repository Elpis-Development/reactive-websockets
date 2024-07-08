package org.elpis.reactive.websockets.event.model.impl;

import org.elpis.reactive.websockets.config.CloseInitiator;
import org.elpis.reactive.websockets.config.SessionCloseInfo;
import org.elpis.reactive.websockets.event.model.WebSocketEvent;

public class ServerSessionClosedEvent implements WebSocketEvent<SessionCloseInfo> {
    private final SessionCloseInfo sessionCloseInfo;

    public ServerSessionClosedEvent(final SessionCloseInfo sessionCloseInfo) {
        this.sessionCloseInfo = new SessionCloseInfo(sessionCloseInfo);
        this.sessionCloseInfo.setCloseInitiator(CloseInitiator.SERVER);
    }

    @Override
    public SessionCloseInfo payload() {
        return sessionCloseInfo;
    }

}
