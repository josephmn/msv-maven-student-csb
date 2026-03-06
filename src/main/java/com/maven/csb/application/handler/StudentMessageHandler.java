package com.maven.csb.application.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maven.csb.application.dto.StudentMessage;
import com.maven.csb.domain.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StudentMessageHandler {

    private final ObjectMapper mapper;
    private final EmailService emailService;

    public void handle(String body) {
        try {

            StudentMessage msg = mapper.readValue(body, StudentMessage.class);
            StudentMessage.Operation operation = mapper.readValue(msg.getOperation(), StudentMessage.Operation.class);
            StudentMessage.Data data = mapper.readValue(msg.getData(), StudentMessage.Data.class);

            String emailBody = """
                    Student Info
                    
                    Document: %s
                    Name: %s %s
                    Email: %s
                    Age: %s
                    Status: %s
                    """.formatted(
                operation.getDocument(),
                operation.getName(),
                operation.getLastName(),
                operation.getEmail(),
                data.getAge(),
                data.isStatus()
            );

            emailService.sendEmail(
                operation.getEmail(),
                "Student new: " + operation.getDocument(),
                emailBody
            );

        } catch (Exception e) {
            throw new RuntimeException("Error processing student message", e);
        }
    }
}
