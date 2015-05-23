/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.List;


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
    
    String pinInput;
    String pinOutput;
    
    String svg;
    
    int xSize;
    int ySize;
    
    /**
     * @author Sven
     * @param startPos
     * @param fullRoute 
     */
    public pathCreator(Point startPos, List<String> fullRoute, int xSize, int ySize){
        this.currPos = startPos;
        this.route = fullRoute;
        
        this.xSize = xSize;
        this.ySize = ySize;
        
        this.svg = "";
        
        //If the path starts from pin
        if(this.route.get(0).contains("P")){       
            pinInput = this.route.remove(0);
        }else{
            pinInput = null;
            //This should never happen, because the route as is defined in project is always from pin
        }
        
        //If the path ends on pin (the last "to uri" is gpio)
        if(this.route.get(this.route.size()-1).contains("P")){       
            pinOutput = this.route.remove(this.route.size()-1);
        }else{
            pinOutput = null;            
        }
        
    }
    
    
    
    /**
     * @author Sven
     */
//    public void run(){
//        //input
//        this.svg += fromPinoutPath();
//        
//        //Path itself
//        //Not Impl. yet
//        
//        //output
//        if(!pinOutput.isEmpty()){
//            this.svg += toPinoutPath();
//        }else{
//            this.svg += toEthernetPath();
//        }
//        
//    }
//    
//    
    private String fromPinoutPath(String firstElement,int model){
        String output = new String();
        double xCoord,yCoord;
        switch(model){
            case 0://raspberry
                if(firstElement.length() > 2){
                    int pinNumber = Integer.parseInt(firstElement.substring(2));
                    if(pinNumber > 19){
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
                output = "<line x1=\""+ xCoord +"\" y1=\""+ yCoord + "\" x2=\""+ xCoord + "\" "
                        + "y2=\"" + 34.915 + "\" style=\"stroke:rgb(255,0,0);stroke-width:4;stroke-dasharray:5,5\">";
                
                output.concat("<line x1=\""+ xCoord +"\" y1=\""+ 34.915 + "\" x2=\""+ 28.2 + "\" "
                        + "y2=\"" + 34.915 + "\" style=\"stroke:rgb(255,0,0);stroke-width:4;stroke-dasharray:5,5\">");
                break;
            case 1://beaglebone
                
                if(topPin){
                    if(firstElement.length() > 2){
                        int pinNumber = Integer.parseInt(firstElement.substring(2));
//                        xCoord = 127.192 + (pinNumber % 2) * 6.1;
//                        yCoord = 12 + (pinNumber % 2) * 6.1;
                        xCoord = 127.22 + (pinNumber % 2) * 6.1;
                        yCoord = 193.5 - (pinNumber % 2) * 6.5;
                    } else {
//                        xCoord = 140.016;
//                        yCoord = 23.383;
                        xCoord = 130.8;
                        yCoord = 181.6;
                    }
                    output = "<line x1=\""+ xCoord +"\" y1=\""+ yCoord + "\" x2=\""+ xCoord + "\" "
                        + "y2=\"" + 159.5 + "\" style=\"stroke:rgb(255,0,0);stroke-width:4;stroke-dasharray:5,5\">";
                    output.concat("<line x1=\""+ xCoord +"\" y1=\""+ 159.5 + "\" x2=\""+ 127.5 + "\" "
                    + "y2=\"" + 159.5 + "\" style=\"stroke:rgb(255,0,0);stroke-width:4;stroke-dasharray:5,5\">");
                }else{
                    if(firstElement.length() > 2){
                        int pinNumber = Integer.parseInt(firstElement.substring(2));
//                        xCoord = 3590;
//                        yCoord = 5337;
                        xCoord = 127.22 + (pinNumber % 2) * 6.1;
                        yCoord = 16.35 - (pinNumber % 2) * 6;
                    } else {
                        xCoord = 130.3;
                        yCoord = 22;
//                        xCoord = 140.305;
//                        yCoord = 183.988;
                    }
                    output = "<line x1=\""+ xCoord +"\" y1=\""+ yCoord + "\" x2=\""+ xCoord + "\" "
                        + "y2=\"" + 30 + "\" style=\"stroke:rgb(255,0,0);stroke-width:4;stroke-dasharray:5,5\">";
                    output.concat("<line x1=\""+ xCoord +"\" y1=\""+ 30 + "\" x2=\""+ 127.5 + "\" "
                    + "y2=\"" + 30 + "\" style=\"stroke:rgb(255,0,0);stroke-width:4;stroke-dasharray:5,5\">");
                    output.concat("<line x1=\""+ 127.5 +"\" y1=\""+ 30 + "\" x2=\""+ 127.5 + "\" "
                        + "y2=\"" + 159.5 + "\" style=\"stroke:rgb(255,0,0);stroke-width:4;stroke-dasharray:5,5\">");
                }
                break;
            case 2://cubieboard
                
                if(topPin){
                    int pinNumber = Integer.parseInt(firstElement.substring(2));
                    if(firstElement.length()>2){
                        xCoord = 63 + 7.2 * (pinNumber % 2);
                        yCoord = 149.4 - 6.8 * (pinNumber % 2);
                    } else {
                        xCoord = 67;
                        yCoord = 138.6;
                    }
                    output = "<line x1=\""+ xCoord +"\" y1=\""+ yCoord + "\" x2=\""+ xCoord + "\" "
                        + "y2=\"" + 140 + "\" style=\"stroke:rgb(255,0,0);stroke-width:4;stroke-dasharray:5,5\">";
                    output.concat("<line x1=\""+ xCoord +"\" y1=\""+ 140 + "\" x2=\""+ 62 + "\" "
                    + "y2=\"" + 140 + "\" style=\"stroke:rgb(255,0,0);stroke-width:4;stroke-dasharray:5,5\">");
                }else{
                    int pinNumber = Integer.parseInt(firstElement.substring(2));
                    if(firstElement.length()>2){
                        xCoord = 63 + 7.2 * (pinNumber % 2);
                        yCoord = 12.6 - 6.8 * (pinNumber % 2);
                    } else {
                        xCoord = 67;
                        yCoord = 17;
                    }
                    output = "<line x1=\""+ xCoord +"\" y1=\""+ yCoord + "\" x2=\""+ xCoord + "\" "
                        + "y2=\"" + 15 + "\" style=\"stroke:rgb(255,0,0);stroke-width:4;stroke-dasharray:5,5\">";
                    output.concat("<line x1=\""+ xCoord +"\" y1=\""+ 15 + "\" x2=\""+ 62 + "\" "
                    + "y2=\"" + 15 + "\" style=\"stroke:rgb(255,0,0);stroke-width:4;stroke-dasharray:5,5\">");
                    output.concat("<line x1=\""+ 62 +"\" y1=\""+ 15 + "\" x2=\""+ 62 + "\" "
                    + "y2=\"" + 140 + "\" style=\"stroke:rgb(255,0,0);stroke-width:4;stroke-dasharray:5,5\">");
                }
                
                break;
            default:
        }
        
        return output;
    }
    
//    private String toPinoutPath(){
//        throw new UnsupportedOperationException("Not implemented yet!");
//    }
//    
//    private String toEthernetPath(){
//        throw new UnsupportedOperationException("Not implemented yet!");
//    }
//    
//    
//    /**
//     * Computation of maximal elements on one line so there will not be only 1 or 2 e on newline
//     * @author Sven
//     * @param totalCount
//     * @return 
//     */
//    private static int computeCountOnLine(int totalCount){
//        int maxCountOnLine;
//        if(totalCount > 15){
//            throw new IndexOutOfBoundsException("Unsupported count of elements in path!");
//        }
//        
//        if (totalCount > 5) {
//            maxCountOnLine = 5;
//            if ((totalCount % 5) < 3 && (totalCount % 5) != 0) {
//                if (totalCount % 3 == 0 ) {
//                    maxCountOnLine = 3;
//                }else{
//                if (totalCount % 3 == 1 ){
//                    if(totalCount % 4 > totalCount % 5){
//                        maxCountOnLine = 4;
//                    }             
//                }
//                }
//                
//            }
//            if (totalCount % 4 == 0) {
//                    maxCountOnLine = 4;
//            }
//        }else{
//            maxCountOnLine = totalCount;
//        }
//        return maxCountOnLine;
//    }
//    
//    private static String printElementsInLine(List<String> route ){
//        return null; //not implemented yet
//    }
//    
//    /**
//     * @param s -the name of rectangles
//     * @param posX - X starting position
//     * @param posY - Y starting position
//     * @param maxSizeX - lenght of line
//     */
//    private static String printRoutePath(){
//        
//        int line = 1;
//        int xSizeOfRect = 50;
//        int ySizeOfRect = 50;
//        int currX = posX+50;
//        int currY = posY+50;
//        String res = "";
//        // Computation number of elements in one line implemented for 3,4,5
//        int maxCount = computeCountOnLine( route.size() );
//        
//        //computation number of lines
//        int numberOfLines;
//        if((route.size()) % maxCount == 0){
//            numberOfLines = (route.size()) | maxCount;
//        }else{
//            numberOfLines = ((route.size()) | maxCount) + 1 ;
//        }
//        
//        
//        for(int i = 0; i < numberOfLines; i++){
//            if(i == numberOfLines - 1 && route.size() % maxCount != 0){
//            // last not fully writen line  
//              
//                for(int j = 0; j < route.size() % maxCount; j++){
//                    
//                }
//                
//            }else{
//            //not the last line, contains full number of elements
//                for(int j=0; j < maxCount; j++){
//                    
//                }
//            }
//        }
//            
//            
//            
//        
////        
////        for(String text :route){
////            if(line%2 == 0){
////                res += "<g>"; //begining of group
////                res += "<rect x=\""+currX+"\" y=\""+currY+"\" width=\""+xSizeOfRect+"\" height=\""+ySizeOfRect+"\" fill=\"white\" ></rect>";
////                res += "  <text x=\""+(currX+10)+"\" y=\""+(currY+10)+"\" font-family=\"Verdana\" font-size=\"20\" fill=\"blue\" > " + text + " </text>";
////                res += "</g>"; //end of group
////                currX += xSizeOfRect + 20;
////                if(currX+xSizeOfRect+10 > maxSizeX){
////                    line++;
////                }
////            }else{
////                
////            }
////        }
//        return res;
//    }
    
 

}
