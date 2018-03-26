/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rivercrossingpuzzle;

import cm3038.search.Action;
import java.util.*;

/**
 *
 * @author Simeon
 */
public class RiverWorldAction extends Action{
    
    ArrayList<String> validCombination;
    Boat boat;
    RiverWorldState riverWorldState;
    
    public RiverWorldAction (RiverWorldState riverWorldState, Boat boat, ArrayList<String> validCombination) {
        this.validCombination = validCombination;
        this.boat = boat;
        this.riverWorldState = riverWorldState;
    }
    
    @Override
    public String toString() {
        String result = riverWorldState.toString();
        
        return result;
    }    
}
