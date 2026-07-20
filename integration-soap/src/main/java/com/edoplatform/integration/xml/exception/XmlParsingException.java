package com.edoplatform.integration.xml.exception;

/**
 * Исключение при парсинге XML.
 * Содержит информацию о причине ошибки.
 */
public class XmlParsingException extends RuntimeException {

    public XmlParsingException(String message) {
        super(message);
    }

    public XmlParsingException(String message, Throwable cause) {
        super(message, cause);
    }
}
