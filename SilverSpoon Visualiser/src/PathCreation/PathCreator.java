/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PathCreation;

import XMLHandling.*;
import main.Constants;
import java.awt.Point;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
    
    /**
     * Constructor. Sets class parameters.
     * @param fullRoute Route to be printed, list of string elements
     * @throws PathCreation.PathCreatorException
     */
    public PathCreator(List<String> fullRoute) throws PathCreatorException {
        if(fullRoute == null || fullRoute.isEmpty())
            throw new PathCreatorException("route can't be null nor empty");
            
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
     * runs computation of necessary parts for each board
     *
     * @author Sven
     * @param board is type of board
     * @return XML document with drawn path
     * @throws XMLHandling.XMLHandlerException
     * @throws PathCreation.PathCreatorException
     */
    public Document run(int board) throws XMLHandlerException, PathCreatorException {
        InputStream in;
        try{
            switch (board) {
                case Constants.RASPBERRY_PI_BOARD:
                    in = getClass().getResourceAsStream(Constants.RASPBERRY_PI_RESOURCE);
                    doc = XMLHandler.loadNewXML(in);
                    in.close();
                    break;
                case Constants.BEAGLEBONE_BOARD:
                    in = getClass().getResourceAsStream(Constants.BEAGLEBONE_RESOURCE);
                    doc = XMLHandler.loadNewXML(in);
                    in.close();
                    break;
                case Constants.CUBIEBOARD_BOARD:
                    in = getClass().getResourceAsStream(Constants.CUBIEBOARD_RESOURCE);
                    doc = XMLHandler.loadNewXML(in);
                    in.close();
                    break;
                default:
                    throw new PathCreatorException("unknown board type");
            }
        } catch (IOException ex) {
            throw new PathCreatorException(ex.toString());
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
            case Constants.RASPBERRY_PI_BOARD:
                outPath.setAttribute("d", "M " + (currPos.x + xSizeOfRect / 2) + ", " + currPos.y
                        + " L " + "130" + " " + currPos.y //130 means x point of meeting
                );
                currPos.x = 130;
                outPath.setAttribute("stroke", "white");
                outPath.setAttribute("stroke-width", String.valueOf(1 + scale));

                outPath.setAttribute("fill", "none");
                outPath.setAttribute("marker-end", "url(#knob)");
                break;
            case Constants.BEAGLEBONE_BOARD:
                outPath.setAttribute("d", "M " + (currPos.x + xSizeOfRect / 2) + ", " + currPos.y
                        + " L " + "180" + " " + currPos.y //130 means x point of meeting
                );
                currPos.x = 180;
                outPath.setAttribute("stroke", "white");
                outPath.setAttribute("stroke-width", String.valueOf(1 + scale));

                outPath.setAttribute("fill", "none");
                outPath.setAttribute("marker-end", "url(#knob)");
                break;
            case Constants.CUBIEBOARD_BOARD:
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
        return doc;
    }
    
    private static int computeCountOnLine(int totalCount) throws PathCreatorException {
        switch(totalCount){
            case 1 : return 1;
            case 2 : return 2;
            case 3 : return 3;
            case 4 : return 4;
            case 5 : return 5;
            case 6 : return 3;
            case 7 : return 4;
            case 8 : return 4;
            case 9 : return 5;
            case 10 : return 5;
            case 11 : return 5;
            case 12 : return 4;
            case 13 : return 5;
            case 14 : return 5;
            case 15 : return 5;
            default : throw new PathCreatorException("unsupported count of elements in path!");
        }
    }
    
    private Element manageBoard(int board, Element group) {
        Element rect = doc.createElement("rect");
        Element path = doc.createElement("path");
        int numbOfLines;

        if (board > 2 || board < 0) {
            return null;
        }

        switch (board) {
            case Constants.RASPBERRY_PI_BOARD:

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

            case Constants.BEAGLEBONE_BOARD://1 - BEAGLE

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

            case Constants.CUBIEBOARD_BOARD:

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
}
