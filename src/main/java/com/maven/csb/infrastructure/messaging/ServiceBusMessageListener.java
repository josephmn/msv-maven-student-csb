package com.maven.csb.infrastructure.messaging;

import com.azure.messaging.servicebus.ServiceBusReceivedMessageContext;
import com.maven.csb.application.handler.StudentMessageHandler;
import com.maven.csb.infrastructure.utils.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ServiceBusMessageListener {

    private final StudentMessageHandler handler;

    public void processMessage(ServiceBusReceivedMessageContext context) {

        String subject = context.getMessage().getSubject();
        log.info("Received Message from ServiceBus with subject: {}", subject);

        // Process only person-created events
        if (!Constants.PERSON_CREATED_SUBJECT.equals(subject)) {
            log.info("Message ignore: {}", subject);
            context.complete();
            return;
        }

        String body = context.getMessage()
            .getBody()
            .toString();

        handler.handle(body);
        log.info("Message processed: {}", body);

        // completar mensaje procesado
        context.complete();
    }

    public void processError(Throwable error) {
        log.info("Error: {}", error.getMessage());
    }
}
