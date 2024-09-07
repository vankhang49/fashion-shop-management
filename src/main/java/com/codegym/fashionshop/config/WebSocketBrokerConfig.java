package com.codegym.fashionshop.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * Configuration class for setting up WebSocket message broker.
 * Enables WebSocket message handling, backed by a message broker.
 *
 * @author : NhiNTY
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketBrokerConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * Configures the message broker.
     * Sets the prefix for filtering destinations targeting application annotated methods
     * and enables a simple in-memory message broker for broadcasting messages to subscribers
     * on specified destination prefixes.
     *
     * @param registry the MessageBrokerRegistry to configure.
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
        registry.setApplicationDestinationPrefixes("/app");
    }

    /**
     * Registers STOMP endpoints mapping each to a specific URL and enabling SockJS as a fallback option.
     * Sets allowed origins for cross-origin requests.
     *
     * @param registry the StompEndpointRegistry to configure.
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOrigins("http://localhost:3000","http://localhost:3001").withSockJS();
    }
}
