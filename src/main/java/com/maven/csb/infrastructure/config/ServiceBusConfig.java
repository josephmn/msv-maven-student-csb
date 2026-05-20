package com.maven.csb.infrastructure.config;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusProcessorClient;
import com.maven.csb.infrastructure.messaging.ServiceBusMessageListener;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ServiceBusConfig {
    @Value("${azure.servicebus.connection-string}")
    private String connectionString;

    @Value("${azure.servicebus.topic-name}")
    private String topicName;

    @Value("${azure.servicebus.subscription-name}")
    private String subscriptionName;

    private final ServiceBusMessageListener listener;

    @Bean
    public ServiceBusProcessorClient processorClient() {

        return new ServiceBusClientBuilder()
            .connectionString(connectionString)
            .processor()
            .topicName(topicName)
            .subscriptionName(subscriptionName)
            .disableAutoComplete() // IMPORTANT: Disable auto-complete to ensure messages are only completed after successful processing
            .processMessage(listener::processMessage)
            .processError(error -> listener.processError(error.getException()))
            .buildProcessorClient();
    }
}
