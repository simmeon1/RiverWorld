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
        listOfPeople.add(new Person(50, true));
        listOfPeople.add(new Person(60, false));
        listOfPeople.add(new Person(70, true));
        //listOfPeople.add(new Person(80, false));
        //listOfPeople.add(new Person(90, true));
        //listOfPeople.add(new Person(55, true));
        //listOfPeople.add(new Person(65, false));
        //listOfPeople.add(new Person(75, true));
        //listOfPeople.add(new Person(85, false));
        //listOfPeople.add(new Person(95, true));
        ArrayList<Person> emptyListOfPeople = new ArrayList<Person>();
        RiverWorld riverWorld = new RiverWorld();
        Boat boat = new Boat(2, 155, riverWorld, Location.SOUTH);
        RiverWorldState currentState = new RiverWorldState(riverWorld, boat, emptyListOfPeople, listOfPeople);
        RiverWorldState goalState = new RiverWorldState(riverWorld, boat, listOfPeople, emptyListOfPeople);
        //System.out.println(currentState.toString());
        //System.out.println(goalState.toString());
        currentState.successor();
        //System.out.println(currentState.toString());
    }

}
