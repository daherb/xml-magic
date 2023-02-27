/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.idsmannheim.lza.xmlmagic;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.filter.ElementFilter;
import org.jdom2.input.SAXBuilder;
import org.jdom2.xpath.XPathFactory;

/**
 * Class providing information about a loaded CMDI file
 * @author Herbert Lange <lange@ids-mannheim.de>
 */
public class CmdiFile extends XmlFile {

    public CmdiFile(Document d) {
        super(d);
    }
    
    public boolean isCmdiFile() {
        return getRootElement().getName().equals("CMD") &&
                getRootSchemas().stream().anyMatch((uri) -> uri.startsWith("http://www.clarin.eu/cmd/")) &&
                getRootNamespaces().values().parallelStream().anyMatch((uri) -> uri.startsWith("http://www.clarin.eu/cmd/"));
    }
    
    public List<Element> getCmdiComponentElements() {
        List<Element> componentElements = new ArrayList<>();
        Map<String,String> rootNamespaces = getRootNamespaces();
        List<Namespace> namespaces = rootNamespaces.keySet().stream()
                .map((prefix) -> (prefix.isBlank() ? Namespace.getNamespace("default", rootNamespaces.get(prefix)) : Namespace.getNamespace(prefix, rootNamespaces.get(prefix))))
                .toList();
        for (Namespace ns : namespaces) {
            componentElements.addAll(
                    XPathFactory.instance().compile("//"+ ns.getPrefix() + ":Components/child::node()",
                            new ElementFilter(),
                            new HashMap<>(),
                            namespaces).evaluate(xmlDoc)
            );
        }
        return componentElements;
    }
    
    public Map<String, String> getCmdiProfiles() {
        HashMap<String,String> profiles = new HashMap<>();
        List<String> uris = new ArrayList<>(getRootSchemas());
        uris.addAll(getRootNamespaces().values());
        for (String uri: uris) {
            if (uri.matches("http(s)?://catalog.clarin.eu/ds/ComponentRegistry/.*")) {
                // Try to downlaod the profile specification (not the schema -> remove potentially trailing /xsd)
                try {
                    URL url = new URL(Pattern.compile("/xsd$").matcher(uri).replaceAll("/xml"));
                    LOG.info(url.toString());
                    Document profile = new SAXBuilder().build(url);
                    List<Element> headers = XPathFactory.instance().compile("//Header", new ElementFilter())
                                .evaluate(profile);
                    for (Element e : headers) {
                        profiles.put(e.getChild("ID").getText(), e.getChild("Name").getText());
                    }
                }
                catch (IOException | JDOMException e) {
                    
                }
            }
        }
        return profiles;
    }

    @Override
    public boolean matches() {
        return isCmdiFile();
    }

    @Override
    public MimeType getMimeType() {
        HashMap<String,String> parameters = new HashMap<>();
        // Add profiles
        for (String profile : getCmdiProfiles().keySet()) {
            parameters.put("profile", profile);
        }
        String version = getRootElement().getAttributeValue("CMDVersion");
        // Add version
        if (version != null)
            parameters.put("version", version);
        return new MimeType("application","x-cmdi","xml").addParameters(parameters);
    }
    
    
    private static final Logger LOG = Logger.getLogger(CmdiFile.class.getName());
}