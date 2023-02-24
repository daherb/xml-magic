package de.idsmannheim.lza.xmlmagic;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Logger;
import org.jdom2.DocType;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

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
            XmlFileInfo info = new XmlFileInfo(new SAXBuilder().build(input.toURL()));
            LOG.info(info.getDoctype().map(DocType::getElementName).toString());
            LOG.info(info.getRootElement().toString());
            LOG.info(info.getRootVersion().toString());
            LOG.info(info.getRootNamespaces().toString());
            LOG.info(info.getRootSchemas().toString());
        }
    }
    private static final Logger LOG = Logger.getLogger(App.class.getName());
}
