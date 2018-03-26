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
        ArrayList<Integer> validCombination = new ArrayList<>();
        for (int i = 0; i < this.validCombination.size(); i++) {
            validCombination.add(this.validCombination.get(i));
        }
        ArrayList<Person> northBank = new ArrayList<>();
        for (int i = 0; i < this.northBank.size(); i++) {
            northBank.add(this.northBank.get(i));
        }
        ArrayList<Person> southBank = new ArrayList<>();
        for (int i = 0; i < this.southBank.size(); i++) {
            southBank.add(this.southBank.get(i));
        }
        Boat boat = new Boat(this.boat.seats, this.boat.maxLoad, this.boat.world, this.boat.location);
        String result = "North is: " + northBank.toString() + ". South is: " + southBank.toString() + ". Boat is at: " + boat.location;
        for (int i = 0; i < validCombination.size(); i++) {
            if (boat.location == Location.NORTH) {
                boat.peopleOnBoat.add(northBank.get(i));
                northBank.set(i, null);
            } else if (boat.location == Location.SOUTH) {
                boat.peopleOnBoat.add(southBank.get(i));
                southBank.set(i, null);
            }
        }
        if (boat.location == Location.NORTH) {
            northBank.removeAll(Collections.singleton(null));
        } else if (boat.location == Location.SOUTH) {
            southBank.removeAll(Collections.singleton(null));
        }
        result += "\nBoat has transported: " + boat.peopleOnBoat.toString() + " from " + (boat.location == Location.NORTH ? "NORTH" : "SOUTH")
                + " to " + (boat.location == Location.NORTH ? "SOUTH" : "NORTH");
        boat.location = boat.location == Location.NORTH ? Location.SOUTH : Location.NORTH;
        while (boat.peopleOnBoat.size() > 0) {
            if (boat.location == Location.NORTH) {
                northBank.add(boat.peopleOnBoat.remove(0));
            } else if (boat.location == Location.SOUTH) {
                southBank.add(boat.peopleOnBoat.remove(0));
            }
        }
        result += "\nNorth is: " + northBank.toString() + ". South is: " + southBank.toString() + ". Boat is at: " + boat.location;
        result += "\n------------------------------------------------------------------------\n";
        return result;
    }
}
