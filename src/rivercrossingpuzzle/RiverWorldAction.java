package rivercrossingpuzzle;

import cm3038.search.*;
import java.util.*;

/**
 * A RiverWorldAction object. This action object will contain information
 * regarding a given state object as well as a given list of people to transport
 * to the other side (it actually does this only when action is applied).
 *
 * @author Simeon Dobrudzhanski 1406444
 */
public class RiverWorldAction extends Action {

    Boat boat;
    Location boatLocation;
    ArrayList<Integer> validCombination;
    ArrayList<Person> northBank;
    ArrayList<Person> southBank;

    /**
     * Creates the action.
     *
     * @param riverWorldState The state to read from.
     * @param validCombination The people to transport. Can be one of many
     * combinations. It is a list which integers which are used as indexes. A
     * valid combination is one that can actually get on a boat. The indexes are
     * used to pick out the right people from a bank (depending on where boat
     * is), load them on a boat and move them across. i.e. Combination = [0th
     * guy] OR [0th guy, 1st guy] OR [1st guy, 3rd guy, 4th guy] etc..
     */
    public RiverWorldAction(RiverWorldState riverWorldState, ArrayList<Integer> validCombination) {

        // The action object is populated with fields which are NOT references 
        // of those from the state, as we do not any changes in the action object
        // to get reflected in the state.
        this.validCombination = new ArrayList<>(validCombination);
        this.northBank = new ArrayList<>(riverWorldState.northBank);
        this.southBank = new ArrayList<>(riverWorldState.southBank);
        this.boat = new Boat(riverWorldState.boat.seats, riverWorldState.boat.maxLoad);
        this.boatLocation = riverWorldState.boatLocation;

        // The loadOnBoat is the total weight of the people that may get on the boat.
        // As it is populated from the valid combination, it is never over the
        // actual max load of the boat.
        double loadOnBoat = 0;

        // Depending on where the boat is, people from that bank are loaded.
        // The valid combination contains the indexes of the people in the combination.
        // i.e. Combination = [0th guy] OR [0th guy, 1st guy] OR [1th guy, 3rd guy, 4th guy] etc..
        for (int i = 0; i < this.validCombination.size(); i++) {
            if (this.boatLocation == Location.NORTH) {
                loadOnBoat += this.northBank.get(this.validCombination.get(i)).weight;
            } else {
                loadOnBoat += this.southBank.get(this.validCombination.get(i)).weight;
            }
        }
        this.cost = loadOnBoat;
    }

    @Override
    /**
     * Prints out the result of performing the action. Local variables are used
     * to create copies of the action field. Done to do a "mock" execution of
     * the action. For proper use it must be applied from the successor method.
     */
    public String toString() {

        // Local variables are created exclusively for the toString method.
        ArrayList<Integer> validCombinationCopy = new ArrayList<>(this.validCombination);
        ArrayList<Person> northBankCopy = new ArrayList<>(this.northBank);
        ArrayList<Person> southBankCopy = new ArrayList<>(this.southBank);
        Boat boatCopy = new Boat(this.boat.seats, this.boat.maxLoad);
        Location boatLocationCopy = this.boatLocation;

        // Here is where the people are picked, boat is loaded, transported 
        // and unloaded.
        // To correctly compare array lists, other than just providing
        // overriden equals and hashCode methods,
        // they need to be sorted as well (Person has overridden compareTo method).
        Collections.sort(northBankCopy);
        Collections.sort(southBankCopy);
        String result = "North is: " + northBankCopy.toString() + ".\nSouth is: " + southBankCopy.toString() + ".\nBoat is at: " + boatLocationCopy + "\n";
        for (int i = 0; i < validCombinationCopy.size(); i++) {
            if (boatLocationCopy == Location.NORTH) {
                boatCopy.peopleOnBoat.add(northBankCopy.get(validCombinationCopy.get(i)));
                // As a valid combination contains indexes which originate from
                // the given bank, we do not want the size of the bank to change
                // until all people are picked. Instead, the picked "positions"
                // are replaced with nulls which, when done, get removed.
                northBankCopy.set(validCombinationCopy.get(i), null);
            } else if (boatLocationCopy == Location.SOUTH) {
                boatCopy.peopleOnBoat.add(southBankCopy.get(validCombinationCopy.get(i)));
                southBankCopy.set(validCombinationCopy.get(i), null);
            }
        }

        // Removing the nulls.
        northBankCopy.removeAll(Collections.singleton(null));
        southBankCopy.removeAll(Collections.singleton(null));
        result += "Boat has transported: " + boatCopy.peopleOnBoat.toString() + " from " + (boatLocationCopy == Location.NORTH ? "NORTH" : "SOUTH")
                + " to " + (boatLocationCopy == Location.NORTH ? "SOUTH" : "NORTH") + "\n";

        // Boat location changes when the people are transported.
        // Depending on which bank it is, that is where the people get unloaded and added.
        boatLocationCopy = boatLocationCopy == Location.NORTH ? Location.SOUTH : Location.NORTH;
        while (!boatCopy.peopleOnBoat.isEmpty()) {
            if (boatLocationCopy == Location.NORTH) {
                northBankCopy.add(boatCopy.peopleOnBoat.remove(0));
            } else if (boatLocationCopy == Location.SOUTH) {
                southBankCopy.add(boatCopy.peopleOnBoat.remove(0));
            }
        }
        // To correctly compare array lists, other than just providing
        // overriden equals and hashCode methods,
        // they need to be sorted as well (Person has overridden compareTo method).
        Collections.sort(northBankCopy);
        Collections.sort(southBankCopy);
        result += "North is: " + northBankCopy.toString() + ".\nSouth is: " + southBankCopy.toString() + ".\nBoat is at: " + boatLocationCopy + "\n";
        result += "------------------------------------------------------------------------";
        return result;
    }
}
