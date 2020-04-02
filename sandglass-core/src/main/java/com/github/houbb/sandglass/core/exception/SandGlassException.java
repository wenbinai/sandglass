package com.github.houbb.sandglass.core.exception;

/**
 * @author binbin.hou
 * @since 0.0.1
 */
public class SandGlassException extends RuntimeException {

    public SandGlassException() {
    }

    public SandGlassException(String message) {
        super(message);
    }

    public SandGlassException(String message, Throwable cause) {
        super(message, cause);
    }

    public SandGlassException(Throwable cause) {
        super(cause);
    }

    public SandGlassException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
