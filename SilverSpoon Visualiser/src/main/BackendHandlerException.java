/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/** Custom exception to wrap ridiculous amount of backend exceptions into.
 *
 * @author lubo
 */
public class BackendHandlerException extends Exception {
    public BackendHandlerException(String message) {
        super(message);
    }
}
