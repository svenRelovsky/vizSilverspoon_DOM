/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import XMLHandling.XMLHandlerException;
import XMLHandling.XMLHandler;
import java.io.Console;
import java.io.File;
import java.util.List;
import org.w3c.dom.Document;

/**
 * Main class for our project.
 *
 * @author lubo
 */
public class Main {

    /**
     * Simple demo, opens XML file, parses it and writes route on console. Ends
     * with error on any exception thrown (parsing, no file, etc...)
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        if ((args.length != 0 && args[0].equals("-h")) || args.length == 0) {
            System.out.println("----------MANUAL---------");
            System.out.println("-g for graphical GUI \n"
                    + "else \n"
                    + "Arguments that have to be used :\n"
                    + "-i=<path> : filepath to XML configuration file\n"
                    + "-o=<path> : filepath to output file\n"
                    + "-s : to show output file -optional\n"
                    + "One of wanted output file types: \n"
                    + "-f=svg or html: for output file saved as svg or html\n"
                    + "One of the following arguments for wanted desk type: \n"
                    + "-d=beaglebone or raspberry or cubieboard: for Beaglebone Black, Raspberry Pi B+, or CubieBoard 2\n"
            );

        } else {
            if (args.length == 1 && args[0].equals("-g")) {
                GUI giu = new GUI();
                giu.run();
            } else {

                int length = args.length;
                String pathXml = new String();
                String pathOut = new String();
                boolean showOut = false;
                String typeOut = new String();
                String deskType = new String();

                if (args.length < 4 || args.length > 5) {
                    System.err.println("Some arguments are missing, type -h for help");
                    return;
                }
//                } else {
//                    for (int i = 0; i < length; i++) {
//                        if (!args[i].startsWith("-i") || !args[i].startsWith("-o")
//                                || !args[i].startsWith("-o") || !args[i].startsWith("-f")
//                                || !args[i].startsWith("-d")) {
//                            System.err.println("Some parameters are missing, type parameter -h for help");
//                            return;
//                        }
//                    }

                for (int i = 0; i < length; i++) {
                    if (args[i].startsWith("-i")) {
                        pathXml = args[i].substring(3);
                    }
                    if (args[i].startsWith("-o")) {
                        pathOut = args[i].substring(3);
                    }
                    if (args[i].startsWith("-f")) {
                        typeOut = args[i].substring(3);
                    }
                    if (args[i].startsWith("-d")) {
                        deskType = args[i].substring(3);
                    }
                    if (args[i].startsWith("-s")) {
                        showOut = true;
                    }
                }

                if (pathXml.length() == 0 || deskType.length() == 0 || typeOut.length() == 0 || pathOut.length() == 0) {
                    System.err.println("Incomplete or wrong arguments, type -h for help.");
                    return;
                }

                if (!(deskType.equals("beaglebone") || deskType.equals("raspberry") || deskType.equals("cubieboard"))
                        || !(typeOut.equals("svg") || typeOut.equals("html"))) {
                    System.err.println("Incomplete or wrong arguments, type -h for help.");
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
            }
        }

        /*List route;
         try {
         //Testing xml is in file test.xml (quite obvious)
         Document xmlDom = XMLHandler.loadNewXML("test.xml");
         route = XMLHandler.parseRoute(xmlDom);
         } catch (XMLHandlerException e) {
         System.out.println("An error occurred: " + e.toString());
         return;
         }
        
         for(int i = 0 ; i < route.size() ; i++){
         System.out.println(route.get(i));
         }*/
    }

}
