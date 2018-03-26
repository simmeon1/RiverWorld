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

    Boat boat;
    Location boatLocation;
    ArrayList<Integer> validCombination;
    ArrayList<Person> northBank;
    ArrayList<Person> southBank;

    public RiverWorldAction(ArrayList<Person> northBank, ArrayList<Person> southBank, Boat boat, Location boatLocation, ArrayList<Integer> validCombination) {
        this.validCombination = new ArrayList<>();
        this.southBank = new ArrayList<>();
        this.northBank = new ArrayList<>();
        for (int i = 0; i < validCombination.size(); i++) {
            this.validCombination.add(validCombination.get(i));
        }
        for (int i = 0; i < northBank.size(); i++) {
            this.northBank.add(northBank.get(i));
        }
        for (int i = 0; i < southBank.size(); i++) {
            this.southBank.add(southBank.get(i));
        }
        this.boat = new Boat(boat.seats, boat.maxLoad, boat.world);
        this.boatLocation = boatLocation;
        double loadOnBoat = 0;
        for (int i = 0; i < this.validCombination.size(); i++) {
            if (this.boatLocation == Location.NORTH) {
                loadOnBoat += this.northBank.get(i).weight;
            } else if (this.boatLocation == Location.SOUTH) {
                loadOnBoat += this.southBank.get(i).weight;
            }
        }
        this.cost = loadOnBoat;
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
        Boat boat = new Boat(this.boat.seats, this.boat.maxLoad, this.boat.world);
        Location boatLocation = this.boatLocation;
        String result = "North is: " + northBank.toString() + ".\nSouth is: " + southBank.toString() + ".\nBoat is at: " + boatLocation;
        for (int i = 0; i < validCombination.size(); i++) {
            if (boatLocation == Location.NORTH) {
                boat.peopleOnBoat.add(northBank.get(validCombination.get(i)));
                northBank.set(validCombination.get(i), null);
            } else if (boatLocation == Location.SOUTH) {
                boat.peopleOnBoat.add(southBank.get(validCombination.get(i)));
                southBank.set(validCombination.get(i), null);
            }
        }
        if (boatLocation == Location.NORTH) {
            northBank.removeAll(Collections.singleton(null));
        } else if (boatLocation == Location.SOUTH) {
            southBank.removeAll(Collections.singleton(null));
        }
        result += "\nBoat has transported: " + boat.peopleOnBoat.toString() + " from " + (boatLocation == Location.NORTH ? "NORTH" : "SOUTH")
                + " to " + (boatLocation == Location.NORTH ? "SOUTH" : "NORTH");
        boatLocation = boatLocation == Location.NORTH ? Location.SOUTH : Location.NORTH;
        while (boat.peopleOnBoat.size() > 0) {
            if (boatLocation == Location.NORTH) {
                northBank.add(boat.peopleOnBoat.remove(0));
            } else if (boatLocation == Location.SOUTH) {
                southBank.add(boat.peopleOnBoat.remove(0));
            }
        }
        result += "\nNorth is: " + northBank.toString() + ".\nSouth is: " + southBank.toString() + ".\nBoat is at: " + boatLocation;
        result += "\n------------------------------------------------------------------------";
        return result;
    }
}
