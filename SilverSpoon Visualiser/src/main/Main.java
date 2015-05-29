/**
 * Project for course PB138 (Modern Markup Languages and Their Application) in 
 * semester Spring 2015 on Masaryk University, Faculty of Informatics, Brno, Czech Republic.
 * Authors:
 *      Marek Burda
 *      Lubo Obratil
 *      Sven Relovsky
 *      Matej Sipka
 */
package main;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main class for our project.
 * Implemented CLI interface.
 * @author lubo
 */
public class Main {

    /**
     * Command line interface method
     * @param args the command line arguments
     */
    static String content = new String();
    static File fileComplete;

    public static void main(String[] args) {
        
        if ((args.length == 1 && (args[0].equals("-h") || args[0].equals("-help")))) {
            writeManual();
        } else {
            if (args.length == 0) {
                GUI gui = new GUI();
                gui.run();
            } else {

                int length = args.length;
                String pathXml = new String();
                String pathOut = new String();
                boolean showOut = false;
                String deskType = new String();

                if (args.length < 3 || args.length > 4) {
                    System.err.println("Some arguments are missing.");
                    writeManual();
                    return;
                }

                for (int i = 0; i < length; i++) {
                    if (args[i].startsWith("-i")) {
                        pathXml = args[i].substring(3);
                    }
                    if (args[i].startsWith("-o")) {
                        pathOut = args[i].substring(3);
                    }
                    if (args[i].startsWith("-d")) {
                        deskType = args[i].substring(3);
                    }
                    if (args[i].startsWith("-s")) {
                        showOut = true;
                    }
                }

                if (pathXml.length() == 0 || deskType.length() == 0 || pathOut.length() == 0) {
                    System.err.println("Incomplete or wrong arguments");
                    writeManual();
                    return;
                }

                if (!(deskType.equals("beaglebone") || deskType.equals("raspberry") || deskType.equals("cubieboard"))) {
                    System.err.println("Incomplete or wrong arguments.");
                    writeManual();
                    return;
                }
                
                File xmlFile = new File(pathXml);
                if (!xmlFile.exists()) {
                    System.err.println("Wrong path to xml config file!");
                    return;
                }
                if (!pathXml.substring(pathXml.length() - 4).equals(".xml")) {
                    System.err.println("Not a xml config file!");
                    return;
                }

                if (!new File(pathOut).isDirectory()) {
                    System.err.println("Not a valid folder to save!");
                    return;
                }
                
                //Arguments are in order, let's set up our backend
                BackendHandler bh = new BackendHandler();
                
                try {
                    bh.setInputXMLPath(pathXml);
                    bh.setOutputDirPath(pathOut);
                    bh.setShowOut(showOut);
                    bh.setBoardType(convertBoardNameToInt(deskType));
                    
                    //Backend parameters set, run actions.
                    bh.drawBoard();
                    
                    //Everything is awesomee!
                    System.out.println("Output file is saved. Everything is OK, application ended without error.");
                } catch (BackendHandlerException ex) {
                    //Oops!
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public static int convertBoardNameToInt(String boardName) {
        switch(boardName){
            case "raspberry" : return Constants.RASPBERRY_PI_BOARD;
            case "beaglebone" : return Constants.BEAGLEBONE_BOARD;
            case "cubieboard" : return Constants.CUBIEBOARD_BOARD;
            default : throw new IllegalArgumentException("unknown name of the board");
        }
    }
    
    private static void writeManual() {
        System.out.println("----------MANUAL---------");
        System.out.println(/*"-g for graphical GUI \n"
                + "else \n"
                +*/ "Arguments that have to be used :\n"
                + "-i=<path> : filepath to XML configuration file\n"
                + "-o=<path> : filepath to output file\n"
                + "-s : to show output file -optional\n"
                + "One of the following arguments for wanted desk type: \n"
                + "-d=beaglebone or raspberry or cubieboard: for Beaglebone Black, Raspberry Pi B+, or CubieBoard 2\n"
        );
    }
}
