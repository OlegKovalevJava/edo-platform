package com.edoplatform.integration.xml;

import com.edoplatform.domain.model.Document;
import com.edoplatform.domain.model.DocumentId;
import com.edoplatform.domain.model.DocumentStatus;
import com.edoplatform.integration.xml.exception.XmlParsingException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import java.io.InputStream;
import java.time.Instant;

/**
 * Реализация {@link XmlParser} для {@link Document} на основе DOM.
 * Использует защищенный парсер ({@link SecureDomParser}) с поддержкой неймспейсов
 * и защитой от XXE-атак.
 */
public class DomDocumentParser implements XmlParser<Document> {

    private final DocumentBuilder documentBuilder;

    public DomDocumentParser() {
        this.documentBuilder = SecureDomParser.createDocumentBuilder();
    }

    @Override
    public Document parse(InputStream inputStream) throws XmlParsingException {
        try {
            org.w3c.dom.Document dom = documentBuilder.parse(inputStream);
            Element root = dom.getDocumentElement();
            return parseDocument(root);
        } catch (XmlParsingException e) {
            throw e;
        } catch (Exception e) {
            throw new XmlParsingException("Failed to parse XML document", e);
        }
    }

    private Document parseDocument(Element root) {
        String id = getChildText(root, "id");
        String status = getChildText(root, "status");
        String createdAt = getChildText(root, "createdAt");
        String updatedAt = getChildText(root, "updatedAt");

        if (id == null || id.isBlank()) {
            throw new XmlParsingException("Missing required element: <id>");
        }
        if (status == null || status.isBlank()) {
            throw new XmlParsingException("Missing required element: <status>");
        }

        DocumentId documentId = DocumentId.fromString(id);
        DocumentStatus documentStatus = DocumentStatus.valueOf(status);
        Instant created = (createdAt != null && !createdAt.isBlank())
                ? Instant.parse(createdAt)
                : Instant.now();
        Instant updated = (updatedAt != null && !updatedAt.isBlank())
                ? Instant.parse(updatedAt)
                : created;

        return Document.reconstruct(documentId, documentStatus, created, updated);
    }

    private String getChildText(Element parent, String tagName) {
        NodeList nodeList = parent.getElementsByTagName(tagName);
        if (nodeList.getLength() == 0) {
            return null;
        }
        Element child = (Element) nodeList.item(0);
        String text = child.getTextContent();
        return text != null ? text.trim() : null;
    }
}
