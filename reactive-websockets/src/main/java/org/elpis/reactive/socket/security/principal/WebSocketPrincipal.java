package org.elpis.reactive.socket.security.principal;

import org.springframework.lang.NonNull;

import java.io.Serializable;
import java.security.Principal;

public class WebSocketPrincipal<T> implements Principal, Serializable {
    private final T authentication;

    public WebSocketPrincipal(@NonNull final T authentication) {
        this.authentication = authentication;
    }

    @Override
    public String getName() {
        return "WebSocketPrincipal";
    }

    public T getAuthentication() {
        return authentication;
    }
}
