package com.maven.csb.domain.services;

public interface EmailService {
    void sendEmail(String to, String subject, String text);
}
