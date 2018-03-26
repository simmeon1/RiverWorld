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
public class RiverWorldAction extends Action {

    ArrayList<Integer> validCombination;
    //RiverWorldState riverWorldState;
    ArrayList<Person> southBank;
    ArrayList<Person> northBank;
    Boat boat;

    public RiverWorldAction(ArrayList<Person> northBank, ArrayList<Person> southBank, Boat boat, ArrayList<Integer> validCombination) {
        this.validCombination = validCombination;
        this.southBank = new ArrayList<>();
        this.northBank = new ArrayList<>();
        for (int i = 0; i < northBank.size(); i++) {
            this.northBank.add(northBank.get(i));
        }
        for (int i = 0; i < southBank.size(); i++) {
            this.southBank.add(southBank.get(i));
        }
        this.boat = new Boat(boat.seats, boat.maxLoad, boat.world, boat.location);
    }

    @Override
    public String toString() {
        String result = "North is: " + northBank.toString() + ". South is: " + southBank.toString() + ". Boat is at: " + boat.location;
        for (int i = 0; i < validCombination.size(); i++) {
            if (boat.location == Location.NORTH) {
                boat.peopleOnBoat.add(northBank.remove(i));
            } else if (boat.location == Location.SOUTH) {
                boat.peopleOnBoat.add(southBank.remove(i));
            }
        }
        result += "\nBoat has transported: " + boat.peopleOnBoat.toString() + " from " + (boat.location == Location.NORTH ? "NORTH" : "SOUTH")
                + " to " + (boat.location == Location.NORTH ? "SOUTH" : "NORTH");
        boat.location = boat.location == Location.NORTH ? Location.SOUTH : Location.NORTH;
        for (int i = 0; i < boat.peopleOnBoat.size(); i++) {
            if (boat.location == Location.NORTH) {
                northBank.add(boat.peopleOnBoat.remove(i));
            } else if (boat.location == Location.SOUTH) {
                southBank.add(boat.peopleOnBoat.remove(i));
            }
        }
        result += "\nNorth is: " + northBank.toString() + ". South is: " + southBank.toString() + ". Boat is at: " + boat.location;
        return result;
    }
}
