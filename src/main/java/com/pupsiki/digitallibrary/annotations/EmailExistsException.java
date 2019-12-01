package com.pupsiki.digitallibrary.annotations;

@SuppressWarnings("serial")
public class EmailExistsException extends Throwable {
    public EmailExistsException(final String message) {
        super(message);
    }
}
