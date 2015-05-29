package XMLHandling;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import main.Constants;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Class for handling XML file and object manipulation.
 * Loads, saves, validates and parses route from XML DOM object.
 * See method documentation for details.
 * @author lubo
 */

public class XMLHandler {
    
    /**
     * Loads XML file into DOM Document object.
     * @param inXmlPath path to XML file
     * @return DOM Document object
     * @throws XMLHandlerException error when parsing or opening file 
     */
    public static Document loadNewXML (String inXmlPath) throws XMLHandlerException {
        if(inXmlPath == null) throw new XMLHandlerException("argument can't be null");
        
        Document xml;
        
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            xml = builder.parse(inXmlPath);
        } catch (SAXException | ParserConfigurationException | IOException e) {
            throw new XMLHandlerException(e.toString());
        }
        
        return xml;
    }
    
    /**
     * Loads InputStream into DOM Document object. Caller is responsible for 
     * closing InputStream!!!
     * @param is InputStream
     * @return DOM Document object
     * @throws XMLHandlerException error when parsing or opening file 
     */
    public static Document loadNewXML (InputStream is) throws XMLHandlerException {
        if(is == null) throw new XMLHandlerException("argument can't be null");
        
        Document xml;
        
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            xml = builder.parse(is);
        } catch (SAXException | ParserConfigurationException | IOException e) {
            throw new XMLHandlerException(e.toString());
        }
        
        return xml;
    }
    

    /**
     * Saves given DOM object into XML file.
     * @param xml DOM Document object
     * @param outXmlPath path to output XML file 
     * @throws XMLHandlerException  transformation or saving error
     */
    public static void saveXMLToFile (Document xml , String outXmlPath) throws XMLHandlerException{
        if(outXmlPath == null) throw new XMLHandlerException("argument can't be null");
        if(xml == null) throw new XMLHandlerException("xml object is null");
        
        try{
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            DOMSource source = new DOMSource(xml);
            StreamResult result = new StreamResult(outXmlPath);
            transformer.transform(source, result);   
        } catch (TransformerConfigurationException e) {
            throw new XMLHandlerException(e.toString());
        } catch (TransformerException e) {
            throw new XMLHandlerException(e.toString());
        }
    }

    /**
     * Parses route in given DOM object, returns
     * @param xml DOM object
     * @return parsed route, ordering stays same as in XML
     * @throws XMLHandlerException null argument
     */
    public static LinkedList<String> parseRoute (Document xml) throws XMLHandlerException {
        if (xml == null) throw new XMLHandlerException("argument can't be empty");
        
        LinkedList<String> route = new LinkedList();
        
        Element elRoute = (Element)
                (xml.getElementsByTagName("route")).item(0);
        Element elFrom = (Element)
                (elRoute.getElementsByTagName("from")).item(0);
        
        parsePin(elFrom.getAttribute("uri") , route);
        parseWaypoint(elFrom.getAttribute("uri") , route);
        
        NodeList nlTo = elRoute.getElementsByTagName("to");
        
        for (int i = 0 ; i < nlTo.getLength() ; i++) {
            Element elTo = (Element) nlTo.item(i);
            parseWaypoint(elTo.getAttribute("uri") , route);
        }
        
        if(!route.getLast().equals("mqtt")) {
            Element lastTo = (Element) nlTo.item(nlTo.getLength() - 1);
            parsePin(lastTo.getAttribute("uri") , route);
        }
        
        return route;
    }
    
    /**
     * Gets pin from given attribute adds it at the end of route.
     * @param att first attribute of element from
     * @param route object to add start into
     * @throws XMLHandlerException null or empty arguments, invalid structure of
     *                                  attribute
     */
    private static void parsePin (String att , LinkedList<String> route) throws XMLHandlerException{
        if(att == null || att.isEmpty() || route == null)
            throw new XMLHandlerException("attribute can't be null or empty");
        
        Pattern p = Pattern.compile("\\A.*://(.*)\\?.*\\Z");
        Matcher m = p.matcher(att);
        if(m.matches() && m.groupCount() != 2) {
            route.add(m.group(1));
        } else {
            throw new XMLHandlerException("no or multiple starts detected in " + att);
        }
    }
    
    /**
     * Gets waypoint from given attribute, appends it into route
     * @param att attribute with waypoint
     * @param route route to add into
     * @throws XMLHandlerException null or empty attribute
     */
    private static void parseWaypoint (String att , LinkedList<String> route) throws XMLHandlerException{
        if(att == null || att.isEmpty())
            throw new XMLHandlerException("attribute can't be null or empty");
        
        route.add(att.substring(0 , att.indexOf(":")));
    }
}
