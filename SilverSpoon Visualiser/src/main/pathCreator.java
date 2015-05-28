/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

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
 *
 * @author sven, marek
 */
public class pathCreator {

    /**
     * Current position
     */
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

    /**
     * @author Sven
     * @param startPos
     * @param fullRoute
     */
    public pathCreator(Point startPos, List<String> fullRoute) {
        this.currPos = startPos;
        this.route = fullRoute;

        //If the path starts from pin
        if (this.route.get(0).contains("P")) {
            pinInput = this.route.remove(0);
        } else {
            pinInput = null;
            //This should never happen, because the route as is defined in project is always from pin
        }

        //If the path ends on pin (the last "to uri" is gpio)
        if (this.route.get(this.route.size() - 1).contains("P")) {
            pinOutput = this.route.remove(this.route.size() - 1);
        } else {
            pinOutput = null;
        }

    }

    /**
     * @author Burda
     */
    private Element fromPinoutPath(Element group,String firstElement, int model) {
        Element g;
        double xCoord, yCoord;
        switch (model) {
            case 0://raspberry
                if (firstElement.length() > 2) {
                    int pinNumber = Integer.parseInt(firstElement.substring(2));
                    if (pinNumber > 19) {
                        yCoord = 12.355;
                        xCoord = 23.175 + pinNumber * 7.2;
                    } else {
                        yCoord = 5.156;
                        xCoord = 23.175 + (39 - pinNumber) * 7.2;
                    }
                } else {
                    xCoord = 27.643;
                    yCoord = 11.045;
                }
                g = doc.createElement("line");
                g.setAttribute("x1", Double.toString(xCoord));
                g.setAttribute("y1", Double.toString(yCoord));
                g.setAttribute("x2", Double.toString(xCoord));
                g.setAttribute("y2", "25");
                g.setAttribute("style", "stroke:rgb(255,0,0);stroke-width:1");
                group.appendChild(g);
                
                g = doc.createElement("line");
                g.setAttribute("x1", Double.toString(xCoord));
                g.setAttribute("y1", "25");
                g.setAttribute("x2", "27");
                g.setAttribute("y2", "25");
                g.setAttribute("style", "stroke:rgb(255,0,0);stroke-width:1");
                group.appendChild(g);

                g = doc.createElement("line");
                g.setAttribute("x1", "27");
                g.setAttribute("y1", "25");
                g.setAttribute("x2", "27");
                g.setAttribute("y2", "50");
                g.setAttribute("style", "stroke:rgb(255,0,0);stroke-width:1");
                group.appendChild(g);
                break;
            case 1://beaglebone
                if (firstElement.charAt(1) == '8') {
                    int pinNumber = Integer.parseInt(firstElement.substring(2,3));
                    if (firstElement.length() > 2) {
                        xCoord = 63 + 7.2 * (pinNumber % 2);
                        yCoord = 12.6 - 6.8 * (pinNumber % 2);
                    } else {
                        xCoord = 67;
                        yCoord = 17;
                    }
                    g = doc.createElement("line");
                    g.setAttribute("x1", Double.toString(xCoord));
                    g.setAttribute("y1", Double.toString(yCoord));
                    g.setAttribute("x2", Double.toString(xCoord));
                    g.setAttribute("y2", "2");
                    g.setAttribute("style", "stroke:rgb(255,0,0);stroke-width:1");
                    group.appendChild(g);
                    
                    g = doc.createElement("line");
                    g.setAttribute("x1", Double.toString(xCoord));
                    g.setAttribute("y1", "20");
                    g.setAttribute("x2", "60");
                    g.setAttribute("y2", "20");
                    g.setAttribute("style", "stroke:rgb(255,0,0);stroke-width:1");
                    group.appendChild(g);
                    
                    g = doc.createElement("line");
                    g.setAttribute("x1", "60");
                    g.setAttribute("y1", "20");
                    g.setAttribute("x2", "60");
                    g.setAttribute("y2", "30");
                    g.setAttribute("style", "stroke:rgb(255,0,0);stroke-width:1");
                    group.appendChild(g);
                } else {
                    int pinNumber = Integer.parseInt(firstElement.substring(2,3));
                    if (firstElement.length() > 2) {
                        xCoord = 63 + 7.2 * (pinNumber % 2);
                        yCoord = 149.4 - 6.8 * (pinNumber % 2);
                    } else {
                        xCoord = 67;
                        yCoord = 138.6;
                    }
                    g = doc.createElement("line");
                    g.setAttribute("x1", Double.toString(xCoord));
                    g.setAttribute("y1", Double.toString(yCoord));
                    g.setAttribute("x2", Double.toString(xCoord));
                    g.setAttribute("y2", "130");
                    g.setAttribute("style", "stroke:rgb(255,0,0);stroke-width:1");
                    group.appendChild(g);
                    
                    g = doc.createElement("line");
                    g.setAttribute("x1", Double.toString(xCoord));
                    g.setAttribute("y1", "130");
                    g.setAttribute("x2", "60");
                    g.setAttribute("y2", "130");
                    g.setAttribute("style", "stroke:rgb(255,0,0);stroke-width:1");
                    group.appendChild(g);
                    
                    g = doc.createElement("line");
                    g.setAttribute("x1", "60");
                    g.setAttribute("y1", "130");
                    g.setAttribute("x2", "60");
                    g.setAttribute("y2", "30");
                    g.setAttribute("style", "stroke:rgb(255,0,0);stroke-width:1");
                    group.appendChild(g);
                }
                break;
            case 2://cubieboard
                if (firstElement.charAt(1) == '8') {
                    if (firstElement.length() > 2) {
                        int pinNumber = Integer.parseInt(firstElement.substring(2,3));

                        xCoord = 3590 + (pinNumber % 2) * 174.2;
                        yCoord = 330 - (pinNumber % 2) * 171.5;
                    } else {

                        xCoord = 3650;
                        yCoord = 650;
                    }
                    g = doc.createElement("line");
                    g.setAttribute("x1", Double.toString(xCoord));
                    g.setAttribute("y1", Double.toString(yCoord));
                    g.setAttribute("x2", Double.toString(xCoord));
                    g.setAttribute("y2", "850");
                    g.setAttribute("style", "stroke:rgb(255,0,0);stroke-width:35");
                    group.appendChild(g);
                    
                    g = doc.createElement("line");
                    g.setAttribute("x1", Double.toString(xCoord));
                    g.setAttribute("y1", "850");
                    g.setAttribute("x2", "3750");
                    g.setAttribute("y2", "850");
                    g.setAttribute("style", "stroke:rgb(255,0,0);stroke-width:35");
                    group.appendChild(g);
                    
                } else {
                    if (firstElement.length() > 2) {
                        int pinNumber = Integer.parseInt(firstElement.substring(2,3));
                        xCoord = 3590 + (pinNumber % 2) * 174.2;
                        yCoord = 5337 + (pinNumber % 2) * 171.5;
                    } else {
                        xCoord = 3650;
                        yCoord = 5200;
                    }
                    
                    g = doc.createElement("line");
                    g.setAttribute("x1", Double.toString(xCoord));
                    g.setAttribute("y1", Double.toString(yCoord));
                    g.setAttribute("x2", Double.toString(xCoord));
                    g.setAttribute("y2", "5000");
                    g.setAttribute("style", "stroke:rgb(255,0,0);stroke-width:35");
                    group.appendChild(g);
                    
                    g = doc.createElement("line");
                    g.setAttribute("x1", Double.toString(xCoord));
                    g.setAttribute("y1", "5000");
                    g.setAttribute("x2", "850");
                    g.setAttribute("y2", "5000");
                    g.setAttribute("style", "stroke:rgb(255,0,0);stroke-width:35");
                    group.appendChild(g);
                    
                    g = doc.createElement("line");
                    g.setAttribute("x1", "850");
                    g.setAttribute("y1", "5000");
                    g.setAttribute("x2", "850");
                    g.setAttribute("y2", "3750");
                    g.setAttribute("style", "stroke:rgb(255,0,0);stroke-width:35");
                    group.appendChild(g);
                }
                break;
        }

        return group;
    }
    
    /**
     * @author Burda
     */
    private String fromPinoutPath(String firstElement, int model) {
        String output = new String();
        double xCoord, yCoord;
        switch (model) {
            case 0://raspberry
                if (firstElement.length() > 2) {
                    int pinNumber = Integer.parseInt(firstElement.substring(2));
                    if (pinNumber > 19) {
                        yCoord = 12.355;
                        xCoord = 23.175 + pinNumber * 7.2;
                    } else {
                        yCoord = 5.156;
                        xCoord = 23.175 + (39 - pinNumber) * 7.2;
                    }
                } else {
                    xCoord = 27.643;
                    yCoord = 11.045;
                }
                output = "<line x1=\"" + xCoord + "\" y1=\"" + yCoord + "\" x2=\"" + xCoord + "\" "
                        + "y2=\"" + 25 + "\" style=\"stroke:rgb(255,0,0);stroke-width:1\"/>";

                output.concat("<line x1=\"" + xCoord + "\" y1=\"" + 25 + "\" x2=\"" + 27 + "\" "
                        + "y2=\"" + 25 + "\" style=\"stroke:rgb(255,0,0);stroke-width:1\"/>");
                output.concat("<line x1=\"" + 27 + "\" y1=\"" + 25 + "\" x2=\"" + 27 + "\" "
                        + "y2=\"" + 50 + "\" style=\"stroke:rgb(255,0,0);stroke-width:1\"/>");
                break;
            case 1://beaglebone
                if (firstElement.charAt(1) == '8') {
                    int pinNumber = Integer.parseInt(firstElement.substring(2));
                    if (firstElement.length() > 2) {
                        xCoord = 63 + 7.2 * (pinNumber % 2);
                        yCoord = 12.6 - 6.8 * (pinNumber % 2);
                    } else {
                        xCoord = 67;
                        yCoord = 17;
                    }
                    output = "<line x1=\"" + xCoord + "\" y1=\"" + yCoord + "\" x2=\"" + xCoord + "\" "
                            + "y2=\"" + 20 + "\" style=\"stroke:rgb(255,0,0);stroke-width:4;stroke-dasharray:5,5\"/>";
                    output.concat("<line x1=\"" + xCoord + "\" y1=\"" + 20 + "\" x2=\"" + 60 + "\" "
                            + "y2=\"" + 20 + "\" style=\"stroke:rgb(255,0,0);stroke-width:4;stroke-dasharray:5,5\"/>");
                    output.concat("<line x1=\"" + 60 + "\" y1=\"" + 20 + "\" x2=\"" + 60 + "\" "
                            + "y2=\"" + 30 + "\" style=\"stroke:rgb(255,0,0);stroke-width:4;stroke-dasharray:5,5\"/>");
                } else {
                    int pinNumber = Integer.parseInt(firstElement.substring(2));
                    if (firstElement.length() > 2) {
                        xCoord = 63 + 7.2 * (pinNumber % 2);
                        yCoord = 149.4 - 6.8 * (pinNumber % 2);
                    } else {
                        xCoord = 67;
                        yCoord = 138.6;
                    }
                    output = "<line x1=\"" + xCoord + "\" y1=\"" + yCoord + "\" x2=\"" + xCoord + "\" "
                            + "y2=\"" + 130 + "\" style=\"stroke:rgb(255,0,0);stroke-width:4;stroke-dasharray:5,5\"/>";
                    output.concat("<line x1=\"" + xCoord + "\" y1=\"" + 130 + "\" x2=\"" + 60 + "\" "
                            + "y2=\"" + 130 + "\" style=\"stroke:rgb(255,0,0);stroke-width:4;stroke-dasharray:5,5\"/>");
                    output.concat("<line x1=\"" + 60 + "\" y1=\"" + 130 + "\" x2=\"" + 60 + "\" "
                            + "y2=\"" + 30 + "\" style=\"stroke:rgb(255,0,0);stroke-width:4;stroke-dasharray:5,5\"/>");
                }

                break;
            case 2://cubieboard
                if (firstElement.charAt(1) == '8') {
                    if (firstElement.length() > 2) {
                        int pinNumber = Integer.parseInt(firstElement.substring(2));

                        xCoord = 3590 + (pinNumber % 2) * 174.2;
                        yCoord = 330 - (pinNumber % 2) * 171.5;
                    } else {

                        xCoord = 3650;
                        yCoord = 650;
                    }
                    output = "<line x1=\"" + xCoord + "\" y1=\"" + yCoord + "\" x2=\"" + xCoord + "\" "
                            + "y2=\"" + 850 + "\" style=\"stroke:rgb(255,0,0);stroke-width:25;stroke-dasharray:5,5\"/>";
                    output.concat("<line x1=\"" + xCoord + "\" y1=\"" + 850 + "\" x2=\"" + 3750 + "\" "
                            + "y2=\"" + 850 + "\" style=\"stroke:rgb(255,0,0);stroke-width:25;stroke-dasharray:5,5\"/>");
                } else {
                    if (firstElement.length() > 2) {
                        int pinNumber = Integer.parseInt(firstElement.substring(2));
                        xCoord = 3590 + (pinNumber % 2) * 174.2;
                        yCoord = 5337 + (pinNumber % 2) * 171.5;
                    } else {
                        xCoord = 3650;
                        yCoord = 5200;
                    }
                    output = "<line x1=\"" + xCoord + "\" y1=\"" + yCoord + "\" x2=\"" + xCoord + "\" "
                            + "y2=\"" + 5000 + "\" style=\"stroke:rgb(255,0,0);stroke-width:25;stroke-dasharray:5,5\"/>";
                    output.concat("<line x1=\"" + xCoord + "\" y1=\"" + 5000 + "\" x2=\"" + 850 + "\" "
                            + "y2=\"" + 5000 + "\" style=\"stroke:rgb(255,0,0);stroke-width:25;stroke-dasharray:5,5\"/>");
                    output.concat("<line x1=\"" + 850 + "\" y1=\"" + 5000 + "\" x2=\"" + 850 + "\" "
                            + "y2=\"" + 3750 + "\" style=\"stroke:rgb(255,0,0);stroke-width:25;stroke-dasharray:5,5\"/>");
                }
                break;
        }

        return output;
    }

    /**
     * @author Burda
     */
    private Element toPinoutPath(String lastElement, int model,Element group) {
        double xCoord, yCoord;
        Element g;
        switch (model) {
            case 0://raspberry
                if (lastElement.length() > 2) {
                    int pinNumber = Integer.parseInt(lastElement.substring(2,3));
                    if (pinNumber > 19) {
                        yCoord = 12.355;
                        xCoord = 23.175 + pinNumber * 7.2;
                    } else {
                        yCoord = 5.156;
                        xCoord = 23.175 + (39 - pinNumber) * 7.2;
                    }
                } else {
                    xCoord = 27.643;
                    yCoord = 11.045;
                }
                
                g = doc.createElement("line");
                g.setAttribute("x1", "140");
                g.setAttribute("y1", "80");
                g.setAttribute("x2", "185");
                g.setAttribute("y2", "80");
                g.setAttribute("style", "stroke:rgb(0,0,255);stroke-width:1");
                group.appendChild(g);
                
                g = doc.createElement("line");
                g.setAttribute("x1", "185");
                g.setAttribute("y1", "80");
                g.setAttribute("x2", "185");
                g.setAttribute("y2", "28");
                g.setAttribute("style", "stroke:rgb(0,0,255);stroke-width:1");
                group.appendChild(g);

                g = doc.createElement("line");
                g.setAttribute("x1", "185");
                g.setAttribute("y1", "28");
                g.setAttribute("x2", Double.toString(xCoord));
                g.setAttribute("y2", "28");
                g.setAttribute("style", "stroke:rgb(0,0,255);stroke-width:1");
                group.appendChild(g);

                g = doc.createElement("line");
                g.setAttribute("x1", Double.toString(xCoord));
                g.setAttribute("y1", "28");
                g.setAttribute("x2", Double.toString(xCoord));
                g.setAttribute("y2", Double.toString(yCoord));
                g.setAttribute("style", "stroke:rgb(0,0,255);stroke-width:1");
                group.appendChild(g);
                
                break;
            case 1://beaglebone
                if (lastElement.charAt(1) == '8') {
                    int pinNumber = Integer.parseInt(lastElement.substring(2,3));
                    if (lastElement.length() > 2) {
                        xCoord = 63 + 7.2 * (pinNumber % 2);
                        yCoord = 12.6 - 6.8 * (pinNumber % 2);
                    } else {
                        xCoord = 67;
                        yCoord = 17;
                    }
                    
                    g = doc.createElement("line");
                    g.setAttribute("x1", "180");
                    g.setAttribute("y1", "80");
                    g.setAttribute("x2", "200");
                    g.setAttribute("y2", "80");
                    g.setAttribute("style", "stroke:rgb(0,0,255);stroke-width:1");
                    group.appendChild(g);
                    
                    g = doc.createElement("line");
                    g.setAttribute("x1", "200");
                    g.setAttribute("y1", "80");
                    g.setAttribute("x2", "200");
                    g.setAttribute("y2", "24");
                    g.setAttribute("style", "stroke:rgb(0,0,255);stroke-width:1");
                    group.appendChild(g);
                    
                    g = doc.createElement("line");
                    g.setAttribute("x1", "200");
                    g.setAttribute("y1", "24");
                    g.setAttribute("x2", Double.toString(xCoord));
                    g.setAttribute("y2", "24");
                    g.setAttribute("style", "stroke:rgb(0,0,255);stroke-width:1");
                    group.appendChild(g);
                    
                    g = doc.createElement("line");
                    g.setAttribute("x1", Double.toString(xCoord));
                    g.setAttribute("y1", "24");
                    g.setAttribute("x2", Double.toString(xCoord));
                    g.setAttribute("y2", Double.toString(yCoord));
                    g.setAttribute("style", "stroke:rgb(0,0,255);stroke-width:1");
                    group.appendChild(g);
                } else {
                    int pinNumber = Integer.parseInt(lastElement.substring(2,3));
                    if (lastElement.length() > 2) {
                        xCoord = 63 + 7.2 * (pinNumber % 2);
                        yCoord = 149.4 - 6.8 * (pinNumber % 2);
                    } else {
                        xCoord = 67;
                        yCoord = 138.6;
                    }
                    
                    g = doc.createElement("line");
                    g.setAttribute("x1", "180");
                    g.setAttribute("y1", "80");
                    g.setAttribute("x2", "200");
                    g.setAttribute("y2", "80");
                    g.setAttribute("style", "stroke:rgb(0,0,255);stroke-width:1");
                    group.appendChild(g);
                    
                    g = doc.createElement("line");
                    g.setAttribute("x1", "200");
                    g.setAttribute("y1", "80");
                    g.setAttribute("x2", "200");
                    g.setAttribute("y2", "125");
                    g.setAttribute("style", "stroke:rgb(0,0,255);stroke-width:1");
                    group.appendChild(g);
                    
                    g = doc.createElement("line");
                    g.setAttribute("x1", "200");
                    g.setAttribute("y1", "125");
                    g.setAttribute("x2", Double.toString(xCoord));
                    g.setAttribute("y2", "125");
                    g.setAttribute("style", "stroke:rgb(0,0,255);stroke-width:1");
                    group.appendChild(g);
                    
                    g = doc.createElement("line");
                    g.setAttribute("x1", Double.toString(xCoord));
                    g.setAttribute("y1", "125");
                    g.setAttribute("x2", Double.toString(xCoord));
                    g.setAttribute("y2", Double.toString(yCoord));
                    g.setAttribute("style", "stroke:rgb(0,0,255);stroke-width:1");
                    group.appendChild(g);
                }

                break;
            case 2://cubieboard
                if (lastElement.charAt(1) == '8') {
                    if (lastElement.length() > 2) {
                        int pinNumber = Integer.parseInt(lastElement.substring(2));
                        xCoord = 3590 + (pinNumber % 2) * 174.2;
                        yCoord = 330 - (pinNumber % 2) * 171.5;
                    } else {

                        xCoord = 3650;
                        yCoord = 650;
                    }
                    
                    g = doc.createElement("line");
                    g.setAttribute("x1", "6500");
                    g.setAttribute("y1", "3000");
                    g.setAttribute("x2", "6500");
                    g.setAttribute("y2", "4250");
                    g.setAttribute("style", "stroke:rgb(0,0,255);stroke-width:35");
                    group.appendChild(g);
                    
                    g = doc.createElement("line");
                    g.setAttribute("x1", "6500");
                    g.setAttribute("y1", "4250");
                    g.setAttribute("x2", "7200");
                    g.setAttribute("y2", "4250");
                    g.setAttribute("style", "stroke:rgb(0,0,255);stroke-width:35");
                    group.appendChild(g);
                    
                    g = doc.createElement("line");
                    g.setAttribute("x1", "7200");
                    g.setAttribute("y1", "4250");
                    g.setAttribute("x2", "7200");
                    g.setAttribute("y2", "900");
                    g.setAttribute("style", "stroke:rgb(0,0,255);stroke-width:35");
                    group.appendChild(g);
                    
                    g = doc.createElement("line");
                    g.setAttribute("x1", "7200");
                    g.setAttribute("y1", "900");
                    g.setAttribute("x2", Double.toString(xCoord));
                    g.setAttribute("y2", "900");
                    g.setAttribute("style", "stroke:rgb(0,0,255);stroke-width:35");
                    group.appendChild(g);
                    
                    g = doc.createElement("line");
                    g.setAttribute("x1", Double.toString(xCoord));
                    g.setAttribute("y1", "900");
                    g.setAttribute("x2", Double.toString(xCoord));
                    g.setAttribute("y2", Double.toString(yCoord));
                    g.setAttribute("style", "stroke:rgb(0,0,255);stroke-width:35");
                    group.appendChild(g);
                } else {
                    if (lastElement.length() > 2) {
                        int pinNumber = Integer.parseInt(lastElement.substring(2));
                        xCoord = 3590 + (pinNumber % 2) * 174.2;
                        yCoord = 5337 + (pinNumber % 2) * 171.5;
                    } else {
                        xCoord = 3650;
                        yCoord = 5200;
                    }
                    g = doc.createElement("line");
                    g.setAttribute("x1", "6500");
                    g.setAttribute("y1", "3000");
                    g.setAttribute("x2", "6500");
                    g.setAttribute("y2", "5100");
                    g.setAttribute("style", "stroke:rgb(0,0,255);stroke-width:35");
                    group.appendChild(g);
                    
                    g = doc.createElement("line");
                    g.setAttribute("x1", "6500");
                    g.setAttribute("y1", "5100");
                    g.setAttribute("x2", Double.toString(xCoord));
                    g.setAttribute("y2", "5100");
                    g.setAttribute("style", "stroke:rgb(0,0,255);stroke-width:35");
                    group.appendChild(g);
                    
                    g = doc.createElement("line");
                    g.setAttribute("x1", Double.toString(xCoord));
                    g.setAttribute("y1", "5100");
                    g.setAttribute("x2", Double.toString(xCoord));
                    g.setAttribute("y2", Double.toString(yCoord));
                    g.setAttribute("style", "stroke:rgb(0,0,255);stroke-width:35");
                    group.appendChild(g);
                }
                break;
        }
        return group;
    }
    
    
    /**
     * @author Burda
     */
    private String toPinoutPath(String lastElement, int model) {
        String output = new String();
        double xCoord, yCoord;
        switch (model) {
            case 0://raspberry
                if (lastElement.length() > 2) {
                    int pinNumber = Integer.parseInt(lastElement.substring(2));
                    if (pinNumber > 19) {
                        yCoord = 12.355;
                        xCoord = 23.175 + pinNumber * 7.2;
                    } else {
                        yCoord = 5.156;
                        xCoord = 23.175 + (39 - pinNumber) * 7.2;
                    }
                } else {
                    xCoord = 27.643;
                    yCoord = 11.045;
                }
                output = "<line x1=\"140\" y1=\"80\" x2=\"185\" y2=\"80\" style=\"stroke:rgb(0,0,255);stroke-width:1\"/>";

                output.concat("<line x1=\"185\" y1=\"80\" x2=\"185\" y2=\"28\" style=\"stroke:rgb(0,0,255);stroke-width:1\"/>");

                output.concat("<line x1=\"" + 185 + "\" y1=\"" + 28 + "\" x2=\"" + xCoord + "\" "
                        + "y2=\"" + 28 + "\" style=\"stroke:rgb(0,0,255);stroke-width:1\"/>");
                output.concat("<line x1=\"" + xCoord + "\" y1=\"" + 28 + "\" x2=\"" + xCoord + "\" "
                        + "y2=\"" + yCoord + "\" style=\"stroke:rgb(0,0,255);stroke-width:1\"/>");
                break;
            case 1://beaglebone
                if (lastElement.charAt(1) == '8') {
                    int pinNumber = Integer.parseInt(lastElement.substring(2));
                    if (lastElement.length() > 2) {
                        xCoord = 63 + 7.2 * (pinNumber % 2);
                        yCoord = 12.6 - 6.8 * (pinNumber % 2);
                    } else {
                        xCoord = 67;
                        yCoord = 17;
                    }
                    output = "<line x1=\"" + 180 + "\" y1=\"" + 80 + "\" x2=\"" + 200 + "\" "
                            + "y2=\"" + 80 + "\" style=\"stroke:rgb(0,0,255);stroke-width:1\"/>";
                    output.concat("<line x1=\"" + 200 + "\" y1=\"" + 80 + "\" x2=\"" + 200 + "\" "
                            + "y2=\"" + 24 + "\" style=\"stroke:rgb(0,0,255);stroke-width:1\"/>");
                    output.concat("<line x1=\"" + 200 + "\" y1=\"" + 24 + "\" x2=\"" + xCoord + "\" "
                            + "y2=\"" + 24 + "\" style=\"stroke:rgb(0,0,255);stroke-width:1\"/>");
                    output.concat("<line x1=\"" + xCoord + "\" y1=\"" + 24 + "\" x2=\"" + xCoord + "\" "
                            + "y2=\"" + yCoord + "\" style=\"stroke:rgb(0,0,255);stroke-width:1\"/>");
                } else {
                    int pinNumber = Integer.parseInt(lastElement.substring(2));
                    if (lastElement.length() > 2) {
                        xCoord = 63 + 7.2 * (pinNumber % 2);
                        yCoord = 149.4 - 6.8 * (pinNumber % 2);
                    } else {
                        xCoord = 67;
                        yCoord = 138.6;
                    }
                    output = "<line x1=\"" + 180 + "\" y1=\"" + 80 + "\" x2=\"" + 200 + "\" "
                            + "y2=\"" + 80 + "\" style=\"stroke:rgb(0,0,255);stroke-width:1\"/>";
                    output.concat("<line x1=\"" + 200 + "\" y1=\"" + 80 + "\" x2=\"" + 200 + "\" "
                            + "y2=\"" + 125 + "\" style=\"stroke:rgb(0,0,255);stroke-width:1\"/>");
                    output.concat("<line x1=\"" + 200 + "\" y1=\"" + 125 + "\" x2=\"" + xCoord + "\" "
                            + "y2=\"" + 125 + "\" style=\"stroke:rgb(0,0,255);stroke-width:1\"/>");
                    output.concat("<line x1=\"" + xCoord + "\" y1=\"" + 125 + "\" x2=\"" + xCoord + "\" "
                            + "y2=\"" + yCoord + "\" style=\"stroke:rgb(0,0,255);stroke-width:1\"/>");
                }

                break;
            case 2://cubieboard
                if (lastElement.charAt(1) == '8') {
                    if (lastElement.length() > 2) {
                        int pinNumber = Integer.parseInt(lastElement.substring(2));
                        xCoord = 3590 + (pinNumber % 2) * 174.2;
                        yCoord = 330 - (pinNumber % 2) * 171.5;
                    } else {

                        xCoord = 3650;
                        yCoord = 650;
                    }
                    output = "<line x1=\"" + 6500 + "\" y1=\"" + 3000 + "\" x2=\"" + 6500 + "\" "
                            + "y2=\"" + 4250 + "\" style=\"stroke:rgb(0,0,255);stroke-width:35\"/>";
                    output.concat("<line x1=\"" + 6500 + "\" y1=\"" + 4250 + "\" x2=\"" + 7200 + "\" "
                            + "y2=\"" + 4250 + "\" style=\"stroke:rgb(0,0,255);stroke-width:35\"/>");
                    output.concat("<line x1=\"" + 6500 + "\" y1=\"" + 4250 + "\" x2=\"" + 7200 + "\" "
                            + "y2=\"" + 900 + "\" style=\"stroke:rgb(0,0,255);stroke-width:35\"/>");
                    output.concat("<line x1=\"" + 7200 + "\" y1=\"" + 900 + "\" x2=\"" + xCoord + "\" "
                            + "y2=\"" + 900 + "\" style=\"stroke:rgb(0,0,255);stroke-width:35\"/>");
                    output.concat("<line x1=\"" + xCoord + "\" y1=\"" + 900 + "\" x2=\"" + xCoord + "\" "
                            + "y2=\"" + yCoord + "\" style=\"stroke:rgb(0,0,255);stroke-width:35\"/>");
                } else {
                    if (lastElement.length() > 2) {
                        int pinNumber = Integer.parseInt(lastElement.substring(2));
                        xCoord = 3590 + (pinNumber % 2) * 174.2;
                        yCoord = 5337 + (pinNumber % 2) * 171.5;
                    } else {
                        xCoord = 3650;
                        yCoord = 5200;
                    }
                    output = "<line x1=\"" + 6500 + "\" y1=\"" + 3000 + "\" x2=\"" + 6500 + "\" "
                            + "y2=\"" + 5100 + "\" style=\"stroke:rgb(0,0,255);stroke-width:25;stroke-dasharray:5,5\"/>";
                    output.concat("<line x1=\"" + 6500 + "\" y1=\"" + 5100 + "\" x2=\"" + xCoord + "\" "
                            + "y2=\"" + 5100 + "\" style=\"stroke:rgb(0,0,255);stroke-width:25;stroke-dasharray:5,5\"/>");
                    output.concat("<line x1=\"" + xCoord + "\" y1=\"" + 5100 + "\" x2=\"" + xCoord + "\" "
                            + "y2=\"" + yCoord + "\" style=\"stroke:rgb(0,0,255);stroke-width:25;stroke-dasharray:5,5\"/>");
                }
                break;
        }
        return output;
    }

    /**
     * @author Burda
     */
    private String toEthernetPath(int model) {
        String output = new String();
        switch (model) {
            case 0://raspberry
                output = "<line x1=\"140\" y1=\"80\" x2=\"185\" y2=\"80\" style=\"stroke:rgb(0,0,255);stroke-width:1\"/>";
                output.concat("<line x1=\"185\" y1=\"80\" x2=\"185\" y2=\"130\" style=\"stroke:rgb(0,0,255);stroke-width:1\"/>");
                output.concat("<line x1=\"185\" y1=\"130\" x2=\"195\" y2=\"130\" style=\"stroke:rgb(0,0,255);stroke-width:1\"/>");
                break;
            case 1://beaglebone
                output = "<line x1=\"180\" y1=\"80\" x2=\"200\" y2=\"80\" style=\"stroke:rgb(0,0,255);stroke-width:1\"/>";
                output.concat("<line x1=\"200\" y1=\"80\" x2=\"200\" y2=\"110\" style=\"stroke:rgb(0,0,255);stroke-width:1\"/>");
                output.concat("<line x1=\"200\" y1=\"110\" x2=\"213\" y2=\"110\" style=\"stroke:rgb(0,0,255);stroke-width:1\"/>");
                break;
            case 2://cubieboard
                output = "<line x1=\"6500\" y1=\"3000\" x2=\"6500\" y2=\"4250\" style=\"stroke:rgb(0,0,255);stroke-width:35\"/>";
                output.concat("<line x1=\"6500\" y1=\"4250\" x2=\"6900\" y2=\"4250\" style=\"stroke:rgb(0,0,255);stroke-width:35\"/>");
                break;
        }
        return output;
    }

    /**
     * @author Burda
     */
    private Element toEthernetPath(Element group, int model) {
        Element g0;
        switch (model) {
            case 0://raspberry
                g0 = doc.createElement("line");
                g0.setAttribute("x1", "140");
                g0.setAttribute("y1", "80");
                g0.setAttribute("x2", "185");
                g0.setAttribute("y2", "80");
                g0.setAttribute("style", "stroke:rgb(0,0,255);stroke-width:1");
                group.appendChild(g0);
                
                g0 = doc.createElement("line");
                g0.setAttribute("x1", "185");
                g0.setAttribute("y1", "80");
                g0.setAttribute("x2", "185");
                g0.setAttribute("y2", "130");
                g0.setAttribute("style", "stroke:rgb(0,0,255);stroke-width:1");
                group.appendChild(g0);
                
                g0 = doc.createElement("line");
                g0.setAttribute("x1", "185");
                g0.setAttribute("y1", "130");
                g0.setAttribute("x2", "195");
                g0.setAttribute("y2", "130");
                g0.setAttribute("style", "stroke:rgb(0,0,255);stroke-width:1");
                group.appendChild(g0);
                break;
            case 1://beaglebone
                g0 = doc.createElement("line");
                g0.setAttribute("x1", "180");
                g0.setAttribute("y1", "80");
                g0.setAttribute("x2", "200");
                g0.setAttribute("y2", "80");
                g0.setAttribute("style", "stroke:rgb(0,0,255);stroke-width:1");
                group.appendChild(g0);
                
                g0 = doc.createElement("line");
                g0.setAttribute("x1", "200");
                g0.setAttribute("y1", "80");
                g0.setAttribute("x2", "200");
                g0.setAttribute("y2", "110");
                g0.setAttribute("style", "stroke:rgb(0,0,255);stroke-width:1");
                group.appendChild(g0);
                
                g0 = doc.createElement("line");
                g0.setAttribute("x1", "200");
                g0.setAttribute("y1", "110");
                g0.setAttribute("x2", "213");
                g0.setAttribute("y2", "110");
                g0.setAttribute("style", "stroke:rgb(0,0,255);stroke-width:1");
                group.appendChild(g0);
                
                break;
            case 2://cubieboard
                g0 = doc.createElement("line");
                g0.setAttribute("x1", "6500");
                g0.setAttribute("y1", "3000");
                g0.setAttribute("x2", "6500");
                g0.setAttribute("y2", "4250");
                g0.setAttribute("style", "stroke:rgb(0,0,255);stroke-width:35");
                group.appendChild(g0);
                
                g0 = doc.createElement("line");
                g0.setAttribute("x1", "6500");
                g0.setAttribute("y1", "4250");
                g0.setAttribute("x2", "6900");
                g0.setAttribute("y2", "4250");
                g0.setAttribute("style", "stroke:rgb(0,0,255);stroke-width:1");
                group.appendChild(g0);
                
                break;
                
            default:
                return null;
        }
        return group;
    }
    
    
    /*
     This is what should defs look like for printing nice arrowheads
     <defs>
     <marker id="knob"
     viewBox="0 0 10 10" refX="0" refY="5"
     markerUnits="strokeWidth"
     markerWidth="4" markerHeight="3" orient="auto">
     <path d="M 0 0 L 10 5 L 0 10 z" />
     </marker>
     </defs>
    
     */

    /**
     * @author Sven
     * @param fullRoute Route to be printed, list of string elements
     *
     */
    public pathCreator(List<String> fullRoute) {
        currPos = new Point();
        this.route = fullRoute;

        //If the path starts from pin
        if (this.route.get(0).contains("P")) {
            pinInput = this.route.remove(0);
        } else {
            pinInput = null;
            //This should never happen, because the route as is defined in project is always from pin
        }

        //If the path ends on pin (the last "to uri" is gpio)
        if (this.route.get(this.route.size() - 1).contains("P")) {
            pinOutput = this.route.remove(this.route.size() - 1);
        } else {
            pinOutput = null;
        }

    }

    /**
     * Opens XML wellformed document
     *
     * @param path
     * @return Opened xml Document
     * @throws Exception
     */
    public Document openDocument(File file) throws Exception {
        Document xml;

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            xml = builder.parse(file);
        } catch (SAXException | ParserConfigurationException | IOException e) {

            throw new Exception("Error loading document!");
        }

        return xml;
    }

    private String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    /**
     * runs computation of neccessary parts for each board
     *
     * @author Sven
     * @param path Path to the specific OUTPUT(.svg)
     * @param board is type of board 0-rasppie, 1 - beaglebone 2- cubieboard
     * @throws java.lang.Exception
     */
    public void run(String path, int board) throws Exception {
        //TODO based on board (relative path) :     
        //doc =  openDocument("/home/sven/Downloads/messif/XMLproject/src/xmlproject/test.xml");
        //getClass().getResourceAsStream("/incompleteDesks/beagleboneblack.txt");
        switch (board) {
            case 0:
                InputStream in
                        = getClass().getResourceAsStream("/incompleteDesks/raspberry_pi_b+_breadboard.svg");
                String content = convertStreamToString(in);
                File CBtemp = new File("CBtemp.svg");
                FileWriter fw = new FileWriter(CBtemp);
                fw.write(content);
                fw.close();
                doc = openDocument(CBtemp);
                break;
            case 1:
                //doc = openDocument(convertStreamToString(getClass().getResourceAsStream("/incompleteDesks/beagleboneblack.svg")));
                break;
            case 2:
                //doc = openDocument(convertStreamToString(getClass().getResourceAsStream("/incompleteDesks/CubieBoard2.svg")));
                break;
        }

        Element e = (Element) doc.getDocumentElement();

        Element g = doc.createElement("g");

        // Computation number of elements in one line implemented for 3,4,5
        this.maxCount = computeCountOnLine(route.size());
        g = manageBoard(board, g);
        g = printRoutePath(g, route, this.xSizeOfRect);

        e.appendChild(g); // into svg the outline group

        //Path itself
        //Not Impl. yet
        //to point of meeting with output
        Element outPath = doc.createElement("path");

        switch (board) {
            case 0:
                outPath.setAttribute("d", "M " + (currPos.x + xSizeOfRect / 2) + ", " + currPos.y
                        + " L " + "130" + " " + currPos.y //130 means x point of meeting
                );
                currPos.x = 130;
                outPath.setAttribute("stroke", "white");
                outPath.setAttribute("stroke-width", String.valueOf(1 + scale));

                outPath.setAttribute("fill", "none");
                outPath.setAttribute("marker-end", "url(#knob)");
                break;
            case 1:
                outPath.setAttribute("d", "M " + (currPos.x + xSizeOfRect / 2) + ", " + currPos.y
                        + " L " + "180" + " " + currPos.y //130 means x point of meeting
                );
                currPos.x = 180;
                outPath.setAttribute("stroke", "white");
                outPath.setAttribute("stroke-width", String.valueOf(1 + scale));

                outPath.setAttribute("fill", "none");
                outPath.setAttribute("marker-end", "url(#knob)");
                break;
            case 2:
                outPath.setAttribute("d", "M " + (currPos.x + xSizeOfRect / 2) + ", " + currPos.y
                        + " L " + "6800" + " " + currPos.y //130 means x point of meeting
                );
                currPos.x = 6800;
                outPath.setAttribute("stroke", "white");
                outPath.setAttribute("stroke-width", String.valueOf(1 + scale));

                outPath.setAttribute("fill", "none");
                outPath.setAttribute("marker-end", "url(#knob)");
                break;

        }

        g.appendChild(outPath);

        //output
     /*   if(!pinOutput.isEmpty()){
         this.svg += toPinoutPath();
         }else{
         this.svg += toEthernetPath();
         }*/
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new FileOutputStream(new File(path)));

        transformer.transform(source, result);
    }

    private Element manageBoard(int board, Element group) {
        Element rect = doc.createElement("rect");
        Element path = doc.createElement("path");
        int numbOfLines;

        if (board > 2 || board < 0) {
            return null;
        }

        switch (board) {
            case 0://0 - RASP PIE

                rect.setAttribute("x", "25");
                rect.setAttribute("y", "55");
                rect.setAttribute("width", "110");
                rect.setAttribute("height", "50");
                rect.setAttribute("fill", "darkgreen");
                rect.setAttribute("fill-opacity", "0.5");

                fontSize = 5;
                xSizeOfRect = 16;
                ySizeOfRect = 10;
                //move to text by:= x+3 y+7

                xSizeOfSpace = (110 - (maxCount * xSizeOfRect)) / (maxCount + 1);

                numbOfLines = (route.size() + maxCount - 1) / maxCount;
                ySizeOfSpace = (50 - (numbOfLines * ySizeOfRect)) / (numbOfLines + 1);

                //Setting starting position
                this.scale = 0;
                currPos.x = 25 + xSizeOfSpace + xSizeOfRect / 2;
                //path from pinout

                currPos.y = 55 + ySizeOfSpace;

                path.setAttribute("d", "M " + 30 + ", " + 50
                        + " L " + 30 + " " + ((currPos.y + 50) / 2)
                        + " L " + currPos.x + " " + ((currPos.y + 50) / 2)
                        + " L " + currPos.x + " " + (currPos.y)
                );
                currPos.y += ySizeOfRect / 2;
                path.setAttribute("stroke", "white");
                path.setAttribute("stroke-width", String.valueOf(1 + scale));

                path.setAttribute("fill", "none");
                path.setAttribute("marker-end", "url(#knob)");

                group.appendChild(rect);
                return group;

            case 1://1 - BEAGLE

                rect.setAttribute("x", "55");
                rect.setAttribute("y", "35");
                rect.setAttribute("width", "120");
                rect.setAttribute("height", "70");
                rect.setAttribute("fill", "darkgreen");
                rect.setAttribute("fill-opacity", "0.5");

                fontSize = 6;

                xSizeOfRect = 17;
                ySizeOfRect = 12;

                xSizeOfSpace = (120 - (maxCount * xSizeOfRect)) / (maxCount + 1);

                //computation of Y size of space depends on number of lines
                numbOfLines = (route.size() + maxCount - 1) / maxCount;
                ySizeOfSpace = (70 - (numbOfLines * ySizeOfRect)) / (numbOfLines + 1);
                this.scale = 0;

                currPos.x = 55 + xSizeOfSpace + xSizeOfRect / 2;
                currPos.y = 35 + ySizeOfSpace;
                //path form pinout

                path.setAttribute("d", "M " + 55 + ", " + 30
                        + " L " + 55 + " " + ((currPos.y + 30) / 2)
                        + " L " + currPos.x + " " + ((currPos.y + 30) / 2)
                        + " L " + currPos.x + " " + (currPos.y)
                );
                currPos.y += ySizeOfRect / 2;
                path.setAttribute("stroke", "white");
                path.setAttribute("stroke-width", String.valueOf(1 + scale));

                path.setAttribute("fill", "none");
                path.setAttribute("marker-end", "url(#knob)");

                currPos.y = 35 + ySizeOfSpace + ySizeOfRect / 2;
                group.appendChild(path);
                group.appendChild(rect);

                return group;

            case 2://2  CUBIE

                rect.setAttribute("x", "3300");
                rect.setAttribute("y", "1300");
                rect.setAttribute("width", "3300");
                rect.setAttribute("height", "2300");
                rect.setAttribute("fill", "darkgreen");
                rect.setAttribute("fill-opacity", "0.75");

                xSizeOfRect = 500;
                ySizeOfRect = 400;
                this.scale = 20;

                xSizeOfSpace = (3300 - (maxCount * xSizeOfRect)) / (maxCount + 1);
                numbOfLines = (route.size() + maxCount - 1) / maxCount;
                ySizeOfSpace = (2300 - (numbOfLines * ySizeOfRect)) / (numbOfLines + 1);
                fontSize = 200;

                currPos.x = 3300 + xSizeOfSpace + xSizeOfRect / 2;

                currPos.y = 1300 + ySizeOfSpace;

                path.setAttribute("d", "M " + 3450 + ", " + 1100
                        + " L " + 3450 + " " + ((currPos.y + 1100) / 2)
                        + " L " + currPos.x + " " + ((currPos.y + 1100) / 2)
                        + " L " + currPos.x + " " + (currPos.y - 3 * scale)
                );
                currPos.y += ySizeOfRect / 2;
                path.setAttribute("stroke", "white");
                path.setAttribute("stroke-width", String.valueOf(1 + scale));

                path.setAttribute("fill", "none");
                path.setAttribute("marker-end", "url(#knob)");

                group.appendChild(rect);
                group.appendChild(path);
                return group;
        }

        return group;
    }

    /**
     * Computation of maximal elements on one line so there will not be only 1
     * or 2 e on newline
     *
     * @author Sven
     * @param totalCount
     * @return
     */
    private static int computeCountOnLine(int totalCount) {
        int maxCountOnLine;
        if (totalCount > 15) {
            throw new IndexOutOfBoundsException("Unsupported count of elements in path!");
        }

        if (totalCount > 5) {
            maxCountOnLine = 5;
            if ((totalCount % 5) < 3 && (totalCount % 5) != 0) {
                if (totalCount % 3 == 0) {
                    maxCountOnLine = 3;
                } else {
                    if (totalCount % 3 == 1) {
                        if (totalCount % 4 > totalCount % 5) {
                            maxCountOnLine = 4;
                        }
                    }
                }

            }
            if (totalCount % 4 == 0) {
                maxCountOnLine = 4;
            }
        } else {
            maxCountOnLine = totalCount;
        }
        return maxCountOnLine;
    }

    private Element printElementsInLine(Element group, List<String> route) {

        Element outGroup = doc.createElement("g");

        for (int i = 0; i < route.size(); i++) {

            Element inGroup = doc.createElement("g");
            Element rect = doc.createElement("rect");

            rect.setAttribute("x", String.valueOf(currPos.x - xSizeOfRect / 2 + i * (xSizeOfRect + xSizeOfSpace)));
            rect.setAttribute("y", String.valueOf(currPos.y - ySizeOfRect / 2));
            rect.setAttribute("width", String.valueOf(xSizeOfRect));
            rect.setAttribute("height", String.valueOf(ySizeOfRect));
            rect.setAttribute("fill", "white");
            inGroup.appendChild(rect);

            Element text = doc.createElement("text");
            text.setAttribute("x", String.valueOf(currPos.x - xSizeOfRect / 2 + i * (xSizeOfRect + xSizeOfSpace)));
            text.setAttribute("y", String.valueOf(currPos.y + ySizeOfRect / 4));
            text.setAttribute("font-family", "Verdana");
            text.setAttribute("font-size", String.valueOf(fontSize));
            text.setAttribute("fill", "blue");
            text.setTextContent(route.get(i));
            inGroup.appendChild(text);

            if (i < (route.size() - 1)) {
                Element path = doc.createElement("path");
                path.setAttribute("d", "M " + (currPos.x + i * (xSizeOfRect + xSizeOfSpace) + xSizeOfRect / 2) + ", " + (currPos.y) + "L " + (currPos.x + xSizeOfSpace + i * (xSizeOfSpace + xSizeOfRect) + xSizeOfRect / 2 - 2 - scale * 3) + " " + (currPos.y));
                path.setAttribute("stroke", "white");
                path.setAttribute("stroke-width", String.valueOf(1 + scale));
                path.setAttribute("fill", "white");
                path.setAttribute("marker-end", "url(#knob)"); //print arrow

                inGroup.appendChild(path);
            }
            outGroup.appendChild(inGroup);

        }
        group.appendChild(outGroup);

        return group; //not implemented yet
    }

    /**
     * printing Elements of a single line
     *
     * @param route list of items to be printed(should not exceed 5)
     * @return rectangles and arrows in group elements
     */
    private String printElementsInLine(List<String> route) {
        String res;
        res = "";
        res = "<g>\n";//begining of group

        int spaceBetween = 0;
        for (int i = 0; i < route.size(); i++) {

            res += "<g>"; //begining of group
            res += "<rect x=\"" + (currPos.x - xSizeOfRect / 2 + i * (xSizeOfRect + xSizeOfSpace)) + "\" y=\""
                    + (currPos.y - ySizeOfRect / 2) + "\" width=\"" + xSizeOfRect + "\" height=\"" + ySizeOfRect + "\" fill=\"white\" ></rect>";
            res += "  <text x=\"" + (currPos.x - xSizeOfRect / 2 + i * (xSizeOfRect + xSizeOfSpace) + 1 + scale) + "\" y=\"" + (currPos.y + ySizeOfRect / 4) + "\" font-family=\"Verdana\" font-size=\"" + fontSize + "\" fill=\"blue\" > " + route.get(i) + " </text>";

            res += "</g>"; //end of group

            if (i < (route.size() - 1)) {
                res += "<path d=\"M " + (currPos.x + i * (xSizeOfRect + xSizeOfSpace) + xSizeOfRect / 2) + ", " + (currPos.y) + "L " + (currPos.x + xSizeOfSpace + i * (xSizeOfSpace + xSizeOfRect) + xSizeOfRect / 2 - 2 - scale * 3) + " " + (currPos.y) + "\" stroke=\"black\"\n"
                        + "                stroke-width=\"" + (1 + scale) + "\" fill=\"white\"\n"
                        + "                marker-end=\"url(#knob)\" />"; //print arrow
            }

        }

        res += "</g>\n"; //end of group

        return res; //not implemented yet
    }

    private Element printConnectingArrow(Element group, Point from, Point to) {

        int space = (to.y - from.y) / 2;
        Element path = doc.createElement("path");
        if (scale == 0) {
            scale = 1;
        }
        path.setAttribute("d", "M " + from.x + ", " + from.y
                + " L " + from.x + " " + (from.y + ySizeOfSpace / 2)
                + " L " + to.x + " " + (from.y + ySizeOfSpace / 2)
                + " L " + to.x + " " + (to.y - scale * 3)
        );
        if (scale == 1) {
            scale = 0;
        }

        path.setAttribute("stroke", "white");
        path.setAttribute("stroke-width", String.valueOf(1 + scale));

        path.setAttribute("fill", "none");
        path.setAttribute("marker-end", "url(#knob)");
        String res = "";
        res += "<path d=\"M " + from.x + ", " + from.y;
        res += " L " + from.x + " " + (from.y + ySizeOfSpace / 2);
        res += " L " + to.x + " " + (from.y + ySizeOfSpace / 2);

        group.appendChild(path);

        return group;

    }

    private int countXsize(int countOnLine, int lenght) {
        // number of el. on line 
        int spaces = 10;
        return (lenght - countOnLine * spaces) / countOnLine;

    }

    private Element printRoutePath(Element group, List<String> s, int maxSizeX) {

        Element groupHere = group;
        //computation number of lines
        int numberOfLines;

        numberOfLines = (route.size() + maxCount - 1) / maxCount;

        for (int i = 0; i < numberOfLines; i++) {
            if (i < (numberOfLines) - 1) {
                groupHere = printElementsInLine(groupHere, route.subList(i * maxCount, (i + 1) * maxCount));
            } else {
                if (route.size() % maxCount != 0) {
                    groupHere = printElementsInLine(groupHere,
                            route.subList(
                                    i * maxCount,
                                    i * maxCount + (route.size() % maxCount)
                            ));
                } else {
                    groupHere = printElementsInLine(groupHere,
                            route.subList(
                                    i * maxCount,
                                    i * maxCount + (maxCount)
                            ));
                }

            }
            if (i < numberOfLines - 1) {
                //TODO set the newpoint to work with all the boards
                //position of end of line
                Point p = new Point(currPos.x + (maxCount - 1) * (xSizeOfSpace) + (maxCount - 1) * (xSizeOfRect), currPos.y + ySizeOfRect / 2);

                Point fin = new Point(currPos.x, currPos.y + ySizeOfRect / 2 + ySizeOfSpace);

                groupHere = printConnectingArrow(groupHere, p, fin);
                currPos = fin;
                currPos.y += ySizeOfRect / 2;

            } else {
                if (route.size() % maxCount == 0) {
                    currPos.x += (maxCount - 1) * (xSizeOfRect + xSizeOfSpace);
                } else {
                    currPos.x += ((route.size() % maxCount) - 1) * (xSizeOfRect + xSizeOfSpace);
                }
            }
        }

        return groupHere;
    }

}
