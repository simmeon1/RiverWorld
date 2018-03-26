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
        listOfPeople.add(new Person("Adam", 100, true));
        listOfPeople.add(new Person("Betty", 90, false));
        listOfPeople.add(new Person("Claire", 50, true));
        listOfPeople.add(new Person("Dave", 30, false));
        listOfPeople.add(new Person("Ewan", 110, true));
        listOfPeople.add(new Person("Frank", 150, false));
        listOfPeople.add(new Person("George", 65, false));
        listOfPeople.add(new Person("Harry", 85, true));
        listOfPeople.add(new Person("Ian", 50, false));
        //listOfPeople.add(new Person("Jack", 120, true));
        //listOfPeople.add(new Person("Kurt", 100, false));
        ArrayList<Person> emptyListOfPeople = new ArrayList<Person>();
        RiverWorld riverWorld = new RiverWorld();
        Boat boat = new Boat(4, 200, riverWorld);
        RiverWorldState currentState = new RiverWorldState(riverWorld, boat, Location.SOUTH, emptyListOfPeople, listOfPeople);
        RiverWorldState goalState = new RiverWorldState(riverWorld, boat, Location.NORTH, listOfPeople, emptyListOfPeople);
        //System.out.println(currentState.toString());
        //System.out.println(goalState.toString());
        //currentState.successor();
        ArrayList<Integer> validCombinationTest = new ArrayList<>();
        validCombinationTest.add(0);
        validCombinationTest.add(2);
        //System.out.println(currentState.toString());
        //RiverWorldAction action = new RiverWorldAction(currentState.northBank, currentState.southBank, currentState.boat, validCombinationTest);
        //System.out.println(currentState.toString());
        //System.out.println(action.toString());
        //RiverWorldState newState = currentState.applyAction(action);
        //System.out.println(newState.toString());
        //System.out.println(currentState.toString());
        //System.out.println(currentState.equals(currentState));
        //System.out.println(currentState.hashCode());
        //System.out.println(goalState.hashCode());
        SearchProblem problem = new RiverWorldRouting(currentState, goalState, riverWorld);
        System.out.println("Searching...");		//print some message
        Path path = problem.search();				//perform search, get result
        System.out.println("Done!");			//print some message
        if (path == null) //if it is null, no solution
        {
            System.out.println("No solution");
        } else {
            path.print();							//otherwise print path
            System.out.println("Nodes visited: " + problem.nodeVisited);
            System.out.println("Cost: " + path.cost + "\n");
        }
        //System.out.println(listOfPeople.get(0).hashCode());
        //System.out.println(listOfPeople.get(3).hashCode());
    } //end method
}
