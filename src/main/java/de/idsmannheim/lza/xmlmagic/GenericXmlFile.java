/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.idsmannheim.lza.xmlmagic;

import org.jdom2.Document;

/**
 *
 * @author Herbert Lange <lange@ids-mannheim.de>
 */
public class GenericXmlFile extends XmlFile {

    public GenericXmlFile(Document d) {
        super(d);
    }

    
    @Override
    public boolean matches() {
        return true;
    }

    @Override
    public MimeType getMimeType() {
        return new MimeType("application","xml");
    }
    
}
