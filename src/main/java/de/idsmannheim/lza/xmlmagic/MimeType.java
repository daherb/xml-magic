/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.idsmannheim.lza.xmlmagic;

import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Class representing MIME types
 * @author Herbert Lange <lange@ids-mannheim.de>
 */
public class MimeType {
    // The main type, e.g. application
    String type;
    // The subtype, e.g. xml
    String subtype;
    // An optional suffix, e.g. +xml for derived formats
    Optional<String> suffix = Optional.empty();
    HashMap<String,String> parameters = new HashMap<>();

    /**
     * Default constructor
     * @param type // The main type, e.g. application
     * @param subtype // The subtype, e.g. xml
     */
    public MimeType(String type, String subtype) {
        this.type = type;
        this.subtype = subtype;
    }

    /**
     * Default constructor with suffix
     * @param type // The main type, e.g. application
     * @param subtype // The subtype, e.g. xml
     * @param suffix // An optional suffix, e.g. +xml for derived formats such as atom+xml
     */
    public MimeType(String type, String subtype, String suffix) {
        this.type = type;
        this.subtype = subtype;
        this.suffix = Optional.of(suffix);
    }

    /**
     * Adds parameters to the type
     * @param parameters the map representing the parameters
     * @return the updated MimeType
     */
    public MimeType addParameters(HashMap<String, String> parameters) {
        this.parameters.putAll(parameters);
        return this;
    }

    @Override
    public String toString() {
        // Create basic mime-type
        StringBuilder sb = new StringBuilder(type + "/" + subtype);
        // Add suffix if present
        if (suffix.isPresent()) {
            sb.append("+").append(suffix.get());
        }
        // Add parameters
        sb.append(parameters.keySet().stream().map((name) -> " ; " + name + "=" + parameters.get(name)).collect(Collectors.joining()));
        return sb.toString();
    }

    public String getType() {
        return type;
    }

    public String getSubtype() {
        return subtype;
    }

    public Optional<String> getSuffix() {
        return suffix;
    }

    public HashMap<String, String> getParameters() {
        return parameters;
    }
    
    
}
