package XMLHandling;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/** Custom exception to wrap ridiculous amount of XML parsing exceptions into.
 *
 * @author lubo
 */
public class XMLHandlerException extends Exception {
    public XMLHandlerException(String message) {
        super(message);
    }
}
