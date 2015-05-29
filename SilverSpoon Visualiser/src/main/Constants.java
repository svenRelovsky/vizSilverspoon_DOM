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
    
    public static final String header = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
            + "<!-- Generator: Adobe Illustrator 16.0.0, SVG Export Plug-In . SVG Version: 6.00 Build 0)  -->\n"
            + "<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">";
    public static final String header2 = "<?xml version='1.0' encoding='UTF-8' standalone='no'?>\n"
            + "<!-- Created with Fritzing (http://www.fritzing.org/) -->";
}
