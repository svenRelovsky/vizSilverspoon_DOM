/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PathCreation;

import XMLHandling.XMLHandler;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 * Class for inserting path elements (rectangles, arrows) into SVG DOM.
 * @author Sven, Marek
 */
public class PathCreator {
    Point currPos;
    List<String> route;

    Document doc = null;

    String pinInput;
    String pinOutput;

    Element sVG;
    int scale;

    int xSizeOfRect;
    int ySizeOfRect;
    int xSizeOfSpace;
    int ySizeOfSpace;
    int maxCount;

    int fontSize;
}
