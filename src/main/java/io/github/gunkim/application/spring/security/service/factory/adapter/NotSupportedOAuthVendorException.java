package io.github.gunkim.application.spring.security.service.factory.adapter;

public class NotSupportedOAuthVendorException extends RuntimeException {
    public NotSupportedOAuthVendorException(String message) {
        super(message);
    }
}
