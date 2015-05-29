/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import XMLHandling.*;
import PathCreation.*;
import java.util.List;
import org.w3c.dom.Document;

/**
 * Connects GUIs to application backend.
 * @author User Lubo
 */
public class BackendHandler {
    private String inputXMLPath;
    private String outputDirPath;
    private boolean showOut;
    private int boardType;
    private int outputType;

    public void setInputXMLPath(String inputXMLPath) throws BackendHandlerException {
        if(inputXMLPath == null || inputXMLPath.isEmpty())
            throw new BackendHandlerException("input path can't be null or empty");
        this.inputXMLPath = inputXMLPath;
    }

    public void setOutputDirPath(String outputDirPath) throws BackendHandlerException {
        if(outputDirPath == null || outputDirPath.isEmpty())
            throw new BackendHandlerException("output directory can't be null or empty");
        this.outputDirPath = outputDirPath;
    }

    public void setShowOut(boolean showOut) {
        this.showOut = showOut;
    }

    public void setBoardType(int boardType) {
        this.boardType = boardType;
    }

    public void setOutputType(int outputType) {
        this.outputType = outputType;
    }
    
    public void drawBoard() throws BackendHandlerException {
        List<String> route;
        PathCreator pc;
        
        try{
            Document doc = XMLHandler.loadNewXML(inputXMLPath);
            route = XMLHandler.parseRoute(doc);
            pc = new PathCreator(route);
            doc = pc.run(boardType);
            saveXmlToOutputType(doc);
            
            if(showOut) showOutput("dummy");
            
        } catch(XMLHandlerException | PathCreatorException ex) {
            throw new BackendHandlerException(ex.toString());
        }
    }
    
    private void saveXmlToOutputType(Document doc) throws BackendHandlerException, XMLHandlerException {
        switch(outputType){
            case Constants.SVG_OUTPUT_TYPE:
                XMLHandler.saveXMLToFile(doc, outputDirPath + "/output_board.svg");
                break;
            case Constants.HTML_OUTPUT_TYPE:
                //MODIFY THIS!!!
                //THIS IS NOT VALID HTML!!!
                XMLHandler.saveXMLToFile(doc, outputDirPath + "/output_board.html");
                break;
            default:
                throw new BackendHandlerException("unknown output type");
        }
    }
    
    private void showOutput(String filePath) throws BackendHandlerException {
        throw new BackendHandlerException("not implemented yet!");
        
        /*Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                URI uri = new URI("file://" + replaceWhiteSpaces(fileComplete.getAbsolutePath()));
                desktop.browse(uri);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/
    }
}
