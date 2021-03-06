package by.itacademy.lesson09.operable.serialization.xml;

import by.itacademy.lesson09.domain.Patient;
import by.itacademy.lesson09.domain.Registry;
import by.itacademy.lesson09.operable.serialization.WritePatients;
import by.itacademy.lesson09.operable.serialization.io.WriteLocalFile;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WriteLocalXML extends WritePatients {
    private static final Logger LOGGER = Logger.getLogger(WriteLocalFile.class.getName());
    private Document document;

    public WriteLocalXML(Registry registry, String source) {
        super(registry, source);
    }

    @Override
    public void operation() {
        try {
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Node root = document.createElement("Registry");
            document.appendChild(root);
            for (Patient element : registry) {
                root.appendChild(new ElementPatient(document).create(element.getProperties()));
            }
            writeDoc();
        } catch (ParserConfigurationException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    private void writeDoc() {
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new FileOutputStream(file));
            transformer.transform(source, result);
        } catch (TransformerException | FileNotFoundException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    @Override
    public String typo() {
        return "Write patients info into local XML file";
    }
}
