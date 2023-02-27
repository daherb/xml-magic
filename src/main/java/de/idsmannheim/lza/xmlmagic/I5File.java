/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.idsmannheim.lza.xmlmagic;

import java.util.HashMap;
import org.jdom2.Element;

/**
 * Class Representing I5 files, i.e. the IDS internal TEI P5 dialect
 * @author Herbert Lange <lange@ids-mannheim.de>
 */
public class I5File extends XmlFile {

    /**
     * Checks if it is a valid I5 file
     * @return 
     */
    public boolean isTeiI5() {
        return getDoctype().isPresent() && 
                getDoctype().get().equals("idsCorpus") &&
                getRootElement().getName().equals("idsCorpus");
    }
    @Override
    public boolean matches() {
        return isTeiI5();
    }

    @Override
    public MimeType getMimeType() {
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("dialect", "i5");
        Element root = getRootElement();
        String version = root.getAttributeValue("version");
        if (version != null)
            parameters.put("version", version);
        String teiForm = root.getAttributeValue("TEIForm");
        if (teiForm != null)
            parameters.put("teiform", teiForm);
        return new MimeType("application", "tei", "xml").addParameters(parameters);

    }
    
}
