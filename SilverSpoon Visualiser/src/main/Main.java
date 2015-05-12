/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import XMLHandling.XMLHandlerException;
import XMLHandling.XMLHandler;
import java.io.Console;
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

        int prepinace = args.length;

        if (args.length!=0 && args[0].equals("-h")) {
            System.out.println("Arguments that have to be used :\n"
                    + "-i : filepath to XML configuration file - optionable\n"
                    + "-o : filepath to output file - optionable\n"
                    + "-s : to show output file"
                    + "One of wanted output file types: \n"
                    + "-svg : output file type will be .svg\n"
                    + "-html : output file type will be .html\n"
                    + "One of the following arguments for wanted desk type: \n"
                    + "-b : for Beaglebone Black \n"
                    + "-r : for Raspberry Pi B+ \n"
                    + "-c : for CubieBoard 2\n");

        } else {
            
            GUI giu = new GUI();
            giu.run();
            
//            if (prepinace != 4) {
//                System.err.println("Some parameters are missing type parameter -h for help");
//            }
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
