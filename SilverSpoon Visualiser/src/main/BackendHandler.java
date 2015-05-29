package main;

import XMLHandling.*;
import PathCreation.*;
import java.awt.Desktop;
import java.io.File;
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
    
    public void drawBoard() throws BackendHandlerException {
        List<String> route;
        PathCreator pc;
        String outputFilePath = outputDirPath + "/output_board.svg";
        
        try{
            Document doc = XMLHandler.loadNewXML(inputXMLPath);
            route = XMLHandler.parseRoute(doc);
            pc = new PathCreator(route);
            doc = pc.run(boardType);
            XMLHandler.saveXMLToFile(doc, outputFilePath);
            
            if(showOut) showOutput(outputFilePath);
        } catch(XMLHandlerException | PathCreatorException ex) {
            throw new BackendHandlerException(ex.toString());
        }
    }
    
    private void showOutput(String filePath) throws BackendHandlerException {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                File file = new File(filePath);
                desktop.open(file);
            } catch (Exception e) {
                throw new BackendHandlerException(e.toString());
            }
        }
    }
    
    private static String replaceWhiteSpaces(String path) {
        return path.replaceAll("\\s+", "%20");
    }
}
