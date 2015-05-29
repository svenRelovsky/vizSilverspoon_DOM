/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

/**
 * Defines commonly used constants in application.
 * Mostly filenames + paths, modes.
 * @author User Lubo
 */
public class Constants {
    
    ////////////////////////////////////////////////////////
    /******************************************************/
    ////////////////////////////////////////////////////////
    //Constants representing specific boards
    public static final int RASPBERRY_PI_BOARD = 0;
    public static final int BEAGLEBONE_BOARD = 1;
    public static final int CUBIEBOARD_BOARD = 2;
    
    
    ////////////////////////////////////////////////////////
    /******************************************************/
    ////////////////////////////////////////////////////////
    //Paths to resources
    public static final String RASPBERRY_PI_RESOURCE = 
            "/DeskTemplates/raspberry_pi_b+_breadboard.svg";
    public static final String BEAGLEBONE_RESOURCE = 
            "/DeskTemplates/beagleboneblack.svg";
    public static final String CUBIEBOARD_RESOURCE = 
            "/DeskTemplates/CubieBoard2.svg";
    
    ////////////////////////////////////////////////////////
    /******************************************************/
    ////////////////////////////////////////////////////////
    //Output types constants
    public static final int SVG_OUTPUT_TYPE = 0;
    public static final int HTML_OUTPUT_TYPE = 1;
}
