/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.idsmannheim.lza.xmlmagic;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import org.jdom2.Document;
import org.reflections.Reflections;

/**
 * Class wrapping all other XML formats
 * @author Herbert Lange <lange@ids-mannheim.de>
 */
public class XmlMagic {

    Document document;
    public XmlMagic(Document document) {
        this.document = document;
    }
    
    public List<XmlFile> getMatchingFormats() {
        List<XmlFile> formats = new ArrayList<>();
        Reflections reflections = new Reflections(App.class.getPackage().getName());
        for (Class c : reflections.getSubTypesOf(XmlFile.class)) {
            try {
                XmlFile format = (XmlFile) c.getConstructor(Document.class).newInstance(document);
                if (format.matches())
                    formats.add(format);
            }
            catch (IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
            }
        }
        return formats;
    }
    
    public List<MimeType> getMimeTypes() {
        List<MimeType> mimeTypes = new ArrayList<>();
        for (XmlFile info : getMatchingFormats()) {
                mimeTypes.add(info.getMimeType());
        }
        return mimeTypes;
    }
}
