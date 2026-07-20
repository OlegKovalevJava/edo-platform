package com.edoplatform.integration.xml;

import com.edoplatform.integration.xml.exception.XmlParsingException;

import java.io.InputStream;

/**
 * Контракт для парсинга XML в объекты доменной модели.
 * @param <T> тип доменного объекта, получаемого в результате парсинга
 */
public interface XmlParser<T> {

    T parse(InputStream inputStream) throws XmlParsingException;
}
