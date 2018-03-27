package rivercrossingpuzzle;

import cm3038.search.*;
import java.util.*;

/**
 * River Crossing puzzle main class.
 * @author Simeon Dobrudzhanski 1406444
 */
public class RiverCrossingPuzzle {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        /* The north/south banks are array lists. 
        The following array lists will be used to create the current and goal
        state of the puzzle. */
        ArrayList<Person> emptyListOfPeople = new ArrayList<>();
        ArrayList<Person> listOfPeople = new ArrayList<Person>();
        listOfPeople.add(new Person("Adam", 100, true));
        listOfPeople.add(new Person("Betty", 90, false));
        listOfPeople.add(new Person("Claire", 50, true));
        listOfPeople.add(new Person("Dave", 30, false));
        listOfPeople.add(new Person("Ewan", 110, false));
        listOfPeople.add(new Person("Frank", 150, true));
        listOfPeople.add(new Person("George", 65, false));
        //listOfPeople.add(new Person("Harry", 85, true));
        //listOfPeople.add(new Person("Ian", 50, false));
        //listOfPeople.add(new Person("Jack", 120, true));
        //listOfPeople.add(new Person("Kurt", 100, false));
        
        // Testing the RiverWorld object.
        RiverWorld riverWorld = new RiverWorld();
        //System.out.println(riverWorld);
        
        //Creating a boat;
        Boat boat = new Boat(2, 200);
        
        
        // Creating the current and goal states.
        RiverWorldState currentState = new RiverWorldState(riverWorld, boat, Location.SOUTH, emptyListOfPeople, listOfPeople);
        RiverWorldState goalState = new RiverWorldState(riverWorld, boat, Location.NORTH, listOfPeople, emptyListOfPeople);
        /*System.out.println(currentState.toString());
        System.out.println(goalState.toString());*/
        
        
        // Testing the successor function. The code within it can print out the
        // good combinations of people.
        /*currentState.successor();
        System.out.println(currentState.toString());*/
        
        // Testing the action object.
        /*ArrayList<Integer> validCombinationTest = new ArrayList<>();
        validCombinationTest.add(0);
        validCombinationTest.add(2);
        RiverWorldAction action = new RiverWorldAction(currentState.northBank, currentState.southBank, currentState.boat, currentState.boatLocation, validCombinationTest);
        System.out.println(action.toString());*/
        
        // Testing creating a new state from an action object.
        /*System.out.println(currentState.toString());        
        RiverWorldState newState = currentState.applyAction(action);
        System.out.println(newState.toString());*/
        
        
        //System.out.println(currentState.toString());
        //System.out.println(currentState.equals(currentState));        
        //System.out.println(currentState.hashCode());
        //System.out.println(goalState.hashCode());
        
        // RUns the solution
        SearchProblem problem = new RiverWorldRouting(currentState, goalState);
        System.out.println("Searching...");
        Path path = problem.search();
        System.out.println("Done!");
        if (path == null)
        {
            System.out.println("No solution");
        } else {
            path.print();
            System.out.println("Nodes visited: " + problem.nodeVisited);
            System.out.println("Cost: " + path.cost + "\n");
        }
        //System.out.println(listOfPeople.get(0).hashCode());
        //System.out.println(listOfPeople.get(3).hashCode());
    }
}
