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
     * combinations. It is a list which contains lists of integers which are
     * used as indexes. The lists are of various size and together they
     * represent the valid combinations of people i.e. the combinations which
     * can actually get on the boat. The indexes are used to pick out the right
     * people from a bank (depending on where boat is), load them on a boat and
     * move them across.
     * i.e. Combinations = [[0th guy][0th guy, 1st guy][1th guy, 3rd guy, 4th guy]] etc..
     */
    public RiverWorldAction(RiverWorldState riverWorldState, ArrayList<Integer> validCombination) {

        // The action object is populated with fields which are NOT references 
        // of those from the state, as we do not any changes in the action object
        // to get reflected in the state.
        this.validCombination = new ArrayList<>();
        this.southBank = new ArrayList<>();
        this.northBank = new ArrayList<>();
        for (int i = 0; i < validCombination.size(); i++) {
            this.validCombination.add(validCombination.get(i));
        }
        for (int i = 0; i < riverWorldState.northBank.size(); i++) {
            this.northBank.add(riverWorldState.northBank.get(i));
        }
        for (int i = 0; i < riverWorldState.southBank.size(); i++) {
            this.southBank.add(riverWorldState.southBank.get(i));
        }
        this.boat = new Boat(riverWorldState.boat.seats, riverWorldState.boat.maxLoad);
        this.boatLocation = riverWorldState.boatLocation;

        // The loadOnBoat is the total weight of the people that may get on the boat.
        // As it is populated from the valid combinations, it is never over the
        // actual max load of the boat.
        double loadOnBoat = 0;

        // Depending on where the boat is, people from that bank are loaded.
        // The valid combinations contain the indexes of the people in the combination.
        // i.e. Combinations = [[0th guy][0th guy, 1st guy][1th guy, 3rd guy, 4th guy]] etc..
        for (int i = 0; i < this.validCombination.size(); i++) {
            if (this.boatLocation == Location.NORTH) {
                loadOnBoat += this.northBank.get(this.validCombination.get(i)).weight;
            } else if (this.boatLocation == Location.SOUTH) {
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
        ArrayList<Integer> validCombination = new ArrayList<>();
        ArrayList<Person> northBank = new ArrayList<>();
        ArrayList<Person> southBank = new ArrayList<>();
        for (int i = 0; i < this.validCombination.size(); i++) {
            validCombination.add(this.validCombination.get(i));
        }
        for (int i = 0; i < this.northBank.size(); i++) {
            northBank.add(this.northBank.get(i));
        }
        for (int i = 0; i < this.southBank.size(); i++) {
            southBank.add(this.southBank.get(i));
        }
        Boat boat = new Boat(this.boat.seats, this.boat.maxLoad);
        Location boatLocation = this.boatLocation;

        // Here is where the people are picked, boat is loaded, transported 
        // and unloaded.
        Collections.sort(northBank);
        Collections.sort(southBank);
        String result = "North is: " + northBank.toString() + ".\nSouth is: " + southBank.toString() + ".\nBoat is at: " + boatLocation + "\n";
        for (int i = 0; i < validCombination.size(); i++) {
            if (boatLocation == Location.NORTH) {
                boat.peopleOnBoat.add(northBank.get(validCombination.get(i)));
                // As valid combinations contains indexes which originate from
                // the given bank, we do not want the size of the bank to change
                // until all people are picked. Instead, the picked "positions"
                // are replaced with nulls which, when done, get removed.
                northBank.set(validCombination.get(i), null);
            } else if (boatLocation == Location.SOUTH) {
                boat.peopleOnBoat.add(southBank.get(validCombination.get(i)));
                southBank.set(validCombination.get(i), null);
            }
        }

        // Removing the nulls.
        northBank.removeAll(Collections.singleton(null));
        southBank.removeAll(Collections.singleton(null));
        result += "Boat has transported: " + boat.peopleOnBoat.toString() + " from " + (boatLocation == Location.NORTH ? "NORTH" : "SOUTH")
                + " to " + (boatLocation == Location.NORTH ? "SOUTH" : "NORTH") + "\n";
        
        // Boat location changes when the people are transported.
        // Depending on which bank it is, that is where the people get unloaded and added.
        boatLocation = boatLocation == Location.NORTH ? Location.SOUTH : Location.NORTH;
        while (boat.peopleOnBoat.size() > 0) {
            if (boatLocation == Location.NORTH) {
                northBank.add(boat.peopleOnBoat.remove(0));
            } else if (boatLocation == Location.SOUTH) {
                southBank.add(boat.peopleOnBoat.remove(0));
            }
        }
        Collections.sort(northBank);
        Collections.sort(southBank);
        result += "North is: " + northBank.toString() + ".\nSouth is: " + southBank.toString() + ".\nBoat is at: " + boatLocation + "\n";
        result += "------------------------------------------------------------------------";
        return result;
    }
}
