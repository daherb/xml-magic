/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.idsmannheim.lza.xmlmagic;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import org.jdom2.Attribute;
import org.jdom2.DocType;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;

/**
 *
 * @author Herbert Lange <lange@ids-mannheim.de>
 */
public class XmlFileInfo {

    Document xmlDoc;
    
    public XmlFileInfo(Document d) {
        this.xmlDoc = d;
    }
    /**
     * Returns the doctype if it exists
     * @return the doctype or empty
     */
    public Optional<DocType> getDoctype() {
        return Optional.ofNullable(xmlDoc.getDocType());
    }
    
    /**
     * Returns the root element
     * @return the root element
     */
    public Element getRootElement() {
        return xmlDoc.getRootElement();
    }
    
    /**
     * Gets the document version attribute from the root element
     * @return the version or empty
     */
    public Optional<String> getRootVersion() {
        return Optional.ofNullable(xmlDoc.getRootElement().getAttribute("version")).map(Attribute::getValue);
    }
    
    /**
     * Returns All namespaces defined in the root element
     * @return the namespaces
     */
    public Map<String,String> getRootNamespaces() {
        HashMap<String,String> nss = new HashMap<>();
        nss.put(xmlDoc.getRootElement().getNamespace().getPrefix(),xmlDoc.getRootElement().getNamespace().getURI());
        for (Namespace n : xmlDoc.getRootElement().getAdditionalNamespaces()) {
            nss.put(n.getPrefix(), n.getURI());
        }
        return nss;
    }
    

    /**
     * Returns the XML schemas if they exist
     * @return the potentially empty list of schema files
     */
    public List<String> getRootSchemas() {
        ArrayList<String> schemas = new ArrayList<>();
        // Create the schema namespace and get the attribute defined in it
        Map<String,String> nss = getRootNamespaces();
        if (nss.containsKey("xsi")) {
            Namespace ns = Namespace.getNamespace("xsi", nss.get("xsi"));
            Attribute a = xmlDoc.getRootElement().getAttribute("schemaLocation", ns);
            if (a != null) {
                schemas.addAll(List.of(a.getValue().split("\\s+")));
            }
        }
        return schemas;
    }
}
