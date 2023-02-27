package de.idsmannheim.lza.xmlmagic;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;
import java.util.logging.Logger;
import org.jdom2.DocType;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.reflections.Reflections;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws URISyntaxException, MalformedURLException, JDOMException, IOException
    {
        if (args.length == 0 ) {
            System.out.println("Input filename required");
            System.exit(-1);
        }
        else {
            URI input = new URI(args[0]);
            Document document = new SAXBuilder().build(input.toURL());
            XmlMagic magic = new XmlMagic(document);
            for (XmlFile file : magic.getMatchingFormats()) {
                try {
                    if (file.matches()) {
                        LOG.info("Doctype: " + file.getDoctype().map(DocType::getElementName).toString());
                        LOG.info("Root element: " + file.getRootElement().toString());
                        LOG.info("Root version: " + file.getRootVersion().toString());
                        LOG.info("Root namespace: " + file.getRootNamespaces().toString());
                        LOG.info("Root schemas: " + file.getRootSchemas().toString());
                        LOG.info("MIME type: " + file.getMimeType().toString());
                    }
                }
                catch (Exception e) {
                    
                }
            }
        }
    }
    private static final Logger LOG = Logger.getLogger(App.class.getName());
}
