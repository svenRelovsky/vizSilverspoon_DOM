/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import XMLHandling.XMLHandlerException;
import XMLHandling.XMLHandler;
import java.util.List;
import org.w3c.dom.Document;

/**
 * Main class for our project.
 * @author lubo
 */
public class Main {

    /**
     * Simple demo, opens XML file, parses it and writes route on console.
     * Ends with error on any exception thrown (parsing, no file, etc...)
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        List route;
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
        }
    }
}
