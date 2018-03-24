/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rivercrossingpuzzle;

import cm3038.search.*;
import cm3038.search.informed.*;
import java.util.*;

/**
 *
 * @author Simeon
 */
public class RiverCrossingPuzzle {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrayList<Person> listOfPeople = new ArrayList<Person>();
        listOfPeople.add(new Person(85, true));
        listOfPeople.add(new Person(86, false));
        ArrayList<Person> emptyListOfPeople = new ArrayList<Person>();
        RiverWorld riverWorld = new RiverWorld();
        Boat boat = new Boat(1, 190, riverWorld);
        RiverWorldState currentState = new RiverWorldState(riverWorld, boat, Location.SOUTH, emptyListOfPeople, listOfPeople);
        RiverWorldState goalState = new RiverWorldState(riverWorld, boat, Location.NORTH, listOfPeople, emptyListOfPeople);
        //System.out.println(currentState.toString());
        //System.out.println(goalState.toString());
        currentState.successor();
        //System.out.println(currentState.toString());
    }

}
