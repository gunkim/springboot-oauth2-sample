package io.github.gunkim.security.service.factory.adapter;

public class NotSupportedOAuthVendorException extends RuntimeException {
    public NotSupportedOAuthVendorException(String message) {
        super(message);
    }
}
