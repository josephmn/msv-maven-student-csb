package com.maven.csb.infrastructure.messaging;

import com.azure.messaging.servicebus.ServiceBusProcessorClient;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceBusConsumer {
    private final ServiceBusProcessorClient processorClient;

    @PostConstruct
    private void start() {
        processorClient.start();
        System.out.println("Consumer started and listening to Service Bus topic...");
    }
}
