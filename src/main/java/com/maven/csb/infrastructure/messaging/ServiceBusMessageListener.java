package com.maven.csb.infrastructure.messaging;

import com.azure.messaging.servicebus.ServiceBusReceivedMessageContext;
import com.maven.csb.application.handler.StudentMessageHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ServiceBusMessageListener {

    private final StudentMessageHandler handler;

    public void processMessage(ServiceBusReceivedMessageContext context) {
        String body = context.getMessage().getBody().toString();
        handler.handle(body);
    }

    public void processError(Throwable error) {
        System.out.println("Error: " + error.getMessage());
    }
}
