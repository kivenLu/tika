package org.apache.tika.parser.xml;

import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.AppendableAdaptor;
import org.apache.tika.sax.XHTMLContentHandler;
import org.apache.tika.utils.Utils;
import org.jaxen.SimpleNamespaceContext;
import org.jdom.Document;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

/**
 * Dublin core metadata parser
 */
public class DcXMLParser extends XMLParserUtils implements Parser {

    public void parse(InputStream stream, ContentHandler handler,
            Metadata metadata) throws IOException, SAXException, TikaException {
        Document xmlDoc = Utils.parse(stream);
        // Set NameSpaceContext for Dublin Core metadata
        SimpleNamespaceContext context = new SimpleNamespaceContext();
        context.addNamespace("dc", "http://purl.org/dc/elements/1.1/");
        setXmlParserNameSpaceContext(context);
        extractContent(xmlDoc, Metadata.TITLE, "//dc:title", metadata);
        extractContent(xmlDoc, Metadata.SUBJECT, "//dc:subject", metadata);
        extractContent(xmlDoc, Metadata.CREATOR, "//dc:creator", metadata);
        extractContent(xmlDoc, Metadata.DESCRIPTION, "//dc:description",
                metadata);
        extractContent(xmlDoc, Metadata.PUBLISHER, "//dc:publisher", metadata);
        extractContent(xmlDoc, Metadata.CONTRIBUTOR, "//dc:contributor",
                metadata);
        extractContent(xmlDoc, Metadata.TYPE, "//dc:type", metadata);
        extractContent(xmlDoc, Metadata.FORMAT, "//dc:format", metadata);
        extractContent(xmlDoc, Metadata.IDENTIFIER, "//dc:identifier", metadata);
        extractContent(xmlDoc, Metadata.LANGUAGE, "//dc:language", metadata);
        extractContent(xmlDoc, Metadata.RIGHTS, "//dc:rights", metadata);

        XHTMLContentHandler xhtml = new XHTMLContentHandler(handler, metadata);
        xhtml.startDocument();
        xhtml.startElement("p");
        concatOccurrence(xmlDoc, "//*", " ", new AppendableAdaptor(xhtml));
        xhtml.endElement("p");
        xhtml.endDocument();
    }
}
