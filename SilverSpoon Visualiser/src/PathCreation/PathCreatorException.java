package PathCreation;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/** Custom exception to wrap ridiculous amount of path creation exceptions into.
 *
 * @author lubo
 */
public class PathCreatorException extends Exception {
    public PathCreatorException(String message) {
        super(message);
    }
}
