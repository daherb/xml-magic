/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.idsmannheim.lza.xmlmagic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.filter.ElementFilter;
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
                .map((prefix) -> Namespace.getNamespace(prefix, rootNamespaces.get(prefix)))
                .toList();
        for (String prefix : rootNamespaces.keySet()) {
            if (!prefix.isBlank()) {
                componentElements.addAll(
                        XPathFactory.instance().compile("//"+ prefix + ":Components/child::node()",
                                new ElementFilter(),
                                new HashMap<>(),
                                namespaces).evaluate(xmlDoc)
                );
            }
        }
        return componentElements;
    }
}