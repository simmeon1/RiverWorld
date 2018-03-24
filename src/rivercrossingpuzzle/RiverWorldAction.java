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
    
    ArrayList<Person> northBank;
    ArrayList<Person> southBank;
    Boat boat;
    Location boatLocation;
    RiverWorldState riverWorldState;
    
    public RiverWorldAction (RiverWorldState riverWorldState, Boat boat) {
        
    }   
    
    @Override
    public String toString() {
        return "";
    }    
}
