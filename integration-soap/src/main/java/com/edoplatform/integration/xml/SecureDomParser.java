package com.edoplatform.integration.xml;

import com.edoplatform.integration.xml.exception.XmlParsingException;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.InputStream;

/**
 * Фабрика защищенных DOM-парсеров.
 * Отключает внешние сущности (XXE-защита), включает поддержку неймспейсов,
 * опционально подключает XSD-валидацию.
 */
public final class SecureDomParser {

    private SecureDomParser() {

    }

    /**
     * Создает защищенный DocumentBuilder.
     *
     * @param xsdInputStream XSD-схема для валидации (null — без валидации)
     * @return настроенный DocumentBuilder
     */
    public static DocumentBuilder createDocumentBuilder(InputStream xsdInputStream) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            // Защита от XXE (XML External Entity attacks)
            factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            factory.setXIncludeAware(false);
            factory.setExpandEntityReferences(false);

            // Поддержка неймспейсов (для SOAP)
            factory.setNamespaceAware(true);

            // XSD-валидация (опционально)
            if (xsdInputStream != null) {
                SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
                Schema schema = schemaFactory.newSchema(new javax.xml.transform.stream.StreamSource(xsdInputStream));
                factory.setSchema(schema);
            }

            return factory.newDocumentBuilder();
        } catch (ParserConfigurationException | SAXException e) {
            throw new XmlParsingException("Failed to create secure DocumentBuilder", e);
        }
    }

    /**
     * Создает DocumentBuilder без XSD-валидации.
     */
    public static DocumentBuilder createDocumentBuilder() {
        return createDocumentBuilder(null);
    }
}
