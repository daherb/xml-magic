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
            Reflections reflections = new Reflections(App.class.getPackage().getName());
            for (Class c : reflections.getSubTypesOf(XmlFile.class)) {
                try {
                    LOG.info(c.getName());
                    XmlFile o = (XmlFile) c.getConstructor(Document.class).newInstance(document);
                    if (o.matches()) {
                        LOG.info("Doctype: " + o.getDoctype().map(DocType::getElementName).toString());
                        LOG.info("Root element: " + o.getRootElement().toString());
                        LOG.info("Root version: " + o.getRootVersion().toString());
                        LOG.info("Root namespace: " + o.getRootNamespaces().toString());
                        LOG.info("Root schemas: " + o.getRootSchemas().toString());
                        LOG.info("MIME type: " + o.getMimeType().toString());
                    }
                }
                catch (Exception e) {
                    
                }
            }
        }
    }
    private static final Logger LOG = Logger.getLogger(App.class.getName());
}
