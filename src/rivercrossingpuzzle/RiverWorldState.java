package rivercrossingpuzzle;

import cm3038.search.*;
import java.math.BigInteger;
import java.util.*;

/**
 * The RIverWorldState object.
 *
 * @author Simeon Dobrudzhanski 1406444
 */
public class RiverWorldState extends RiverWorld implements State {

    /**
     * The boat that the state will be using.
     */
    public Boat boat;

    /**
     * The boat location.
     */
    public Location boatLocation;

    /**
     * The world that the state is modeled from.
     */
    public RiverWorld riverWorld;

    /**
     * Creates the RiverWOrldState object.
     *
     * @param riverWorld The world that the state is modeled from.
     * @param boat The boat that the state will be using.
     * @param boatLocation The boat location.
     * @param northBank North bank, inherited from world.
     * @param southBank South bank, inherited from world.
     */
    public RiverWorldState(RiverWorld riverWorld, Boat boat, Location boatLocation, ArrayList<Person> northBank, ArrayList<Person> southBank) {
        super();
        this.northBank.addAll(northBank);
        this.southBank.addAll(southBank);
        this.riverWorld = riverWorld;
        this.boat = new Boat(boat.seats, boat.maxLoad);       
        this.boatLocation = boatLocation;
    }

    /**
     * Returns a list of possible actions.
     *
     * @return Possible actions.
     */
    @Override
    public List<ActionStatePair> successor() {
        List<ActionStatePair> result = new ArrayList<>();

        // Any people on the boat get unloaded first to start clean.
        while (!boat.peopleOnBoat.isEmpty()) {
            if (boatLocation == Location.SOUTH) {
                southBank.add(boat.peopleOnBoat.remove(0));
                boatLocation = Location.SOUTH;
            } else {
                northBank.add(boat.peopleOnBoat.remove(0));
                boatLocation = Location.NORTH;
            }
        }

        // Depending on where the boat is, that bank will be used to get all possiblen next states.
        ArrayList<Person> peopleOnBoatBank = boatLocation == Location.SOUTH ? southBank : northBank;
        // Gets all possible combinations i.e. all the ways that people on that bank can be grouped.
        // This does not mean they are valid combinations due to puzzle restrictions.
        // Contains a list of lists(combinations). Combinations contain the indexes of the people from that bank
        // which are needed for the combination.
        // i.e. Combinations = [[0th guy][0th guy, 1st guy][1th guy, 3rd guy, 4th guy]] etc..
        ArrayList<ArrayList<Integer>> possibleCombinationsOnBoat = getPeopleCombinationsOnBoat(peopleOnBoatBank);
        ArrayList<ArrayList<Integer>> validCombinationsOnBoat = new ArrayList<>();

        // Goes through all possible combinations.
        for (int i = 0; i < possibleCombinationsOnBoat.size(); i++) {

            // Ignores combination which has no people in it.
            ArrayList<Integer> currentCombination = possibleCombinationsOnBoat.get(i);
            if (currentCombination == null) {
                continue;
            }

            // Ignores combinations which are bigger than the max seats on the boat.
            if (currentCombination.size() > boat.seats) {
                //System.out.println("Combination " + currentCombination + " is not good due to needed seats being "
                //        + currentCombination.size() + "/" + boat.seats + ".");
                continue;
            }
            double totalWeightOfCombination = 0;
            boolean canAnyoneSail = false;

            // Iterating over the people in a combination.
            for (int j = 0; j < currentCombination.size(); j++) {

                // Gets the person that matches the index in the combination.
                // Current combination is an arraylist of integers/indexes.
                Person selectedPerson = peopleOnBoatBank.get(currentCombination.get(j));
                if (selectedPerson.canSail) {
                    canAnyoneSail = true;
                }
                totalWeightOfCombination += selectedPerson.weight;
            }
            if (canAnyoneSail && totalWeightOfCombination <= boat.maxLoad) {
                validCombinationsOnBoat.add(currentCombination);
                //System.out.println("Combination " + currentCombination + " is good.");
            } else if (totalWeightOfCombination > boat.maxLoad) {
                //System.out.println("Combination " + currentCombination + " is not good due to total weight being "
                //        + totalWeightOfCombination + "/" + boat.maxLoad + ".");
            } else if (!canAnyoneSail) {
                //System.out.println("Combination " + currentCombination + " is not good due to no sailors.");
            }
        }
        //System.out.println("End result: " + validCombinationsOnBoat.size() + "/" + possibleCombinationsOnBoat.size() + " combinations are good when:"
        //      + "\nNorth is: " + northBank.toString() + "\nSouth is: " + southBank + "\nBoat is at " + boatLocation
        //    + "\n-----------------------");

        // Once the valid combinations are sorted from all possible ones, new actions are created.
        for (int i = 0; i < validCombinationsOnBoat.size(); i++) {
            RiverWorldAction action = new RiverWorldAction(this, validCombinationsOnBoat.get(i));
            RiverWorldState nextState = this.applyAction(action);
            ActionStatePair actionStatePair = new ActionStatePair(action, nextState);
            result.add(actionStatePair);
        }
        return result;
    }

    /**
     * Compares two states.
     *
     * @param riverWorldState State to compare to.
     * @return Result of comparison.
     */
    @Override
    public boolean equals(Object riverWorldState) {
        if (!(riverWorldState instanceof RiverWorldState)) {
            return false;
        }
        RiverWorldState otherState = (RiverWorldState) riverWorldState;
        // To correctly compare array lists, other than just providing
        // overriden equals and hashCode methods,
        // they need to be sorted as well (Person has overridden compareTo method).
        Collections.sort(this.northBank);
        Collections.sort(this.southBank);
        Collections.sort(otherState.northBank);
        Collections.sort(otherState.southBank);
        return this.northBank.size() == otherState.northBank.size()
                && this.southBank.size() == otherState.southBank.size()
                && ((List) this.northBank).equals((List) otherState.northBank)
                && ((List) this.southBank).equals((List) otherState.southBank)
                && (this.boatLocation == otherState.boatLocation);
    }

    /**
     * The effective java method has been used to create a unique hashCode for a
     * Person object.
     * https://medium.com/codelog/overriding-hashcode-method-effective-java-notes-723c1fedf51c
     *
     * @return HashCode of RiverWorldState object.
     */
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + northBank.hashCode();
        result = 31 * result + southBank.hashCode();
        result = 31 * result + boatLocation.hashCode();
        return result;

    }

    /**
     * Gets all possible combinations of people. Credit for original C# idea and
     * code goes to
     * https://stackoverflow.com/questions/7802822/all-possible-combinations-of-a-list-of-values/41642733
     * and that code has been translated and made to work for Java. The idea is
     * that combinations can be represented as a series of bits. i.e. 3 people
     * can be combined as 000, 001, 010, 011, 100, 101, 110, 111. Representing
     * them as bits is very helpful as by doing this we can easily get all
     * combinations and not get repeating ones.
     *
     * @param boatBank The bank of people to work with.
     * @return A list of all the ways to combine these people.
     */
    public ArrayList<ArrayList<Integer>> getPeopleCombinationsOnBoat(ArrayList<Person> boatBank) {

        // Getting the number of all possible combinations (as bits).
        int count = (int) Math.pow(2, boatBank.size());
        ArrayList<ArrayList<Integer>> possibleCombinations = new ArrayList<>();
        for (int i = 1; i <= count - 1; i++) {
            ArrayList<Integer> currentCombination = new ArrayList<>();

            // For each iteration, str will be 1, 10, 11, 100 etc. as strings.
            // i practically represents a combination.
            String str = Integer.toString(i, 2);

            // The str gets turned to an number ("101" becomes 101).
            // For big banks/count, str can reach lengths of hundreds of characters.
            // ints and long will not work for bigger combinations, BigInteger has no max limit.
            // One downfall of this solution is that it might not be practical for weaker devices.
            BigInteger strInt = new BigInteger(str);

            // If a bank has 8 total combinations, integer 101 will be used to get string 00000101
            // By padding zeroes on the left depending on count of combos.
            str = String.format("%0" + boatBank.size() + "d", strInt);

            // j practically represent the index of a bank.
            // i.e for a bank of 8 people, j will get from 0 to 7.
            for (int j = 0; j < str.length(); j++) {

                // 001 is a combination of 3rd guy, 101 is a combination of 1st and 3rd guy etc..
                // For 001, index 2 will get added to the list, for 101, indexes 0 and 2 etc.
                if (str.charAt(j) == '1') {
                    //System.out.print(boatBank.get(j));

                    // Result for 101 will be arraylist [0,2] (1st and 3rd guy on the bank).
                    currentCombination.add(j);
                }
            }
            possibleCombinations.add(currentCombination);
            //System.out.println();
        }
        return possibleCombinations;
    }

    /**
     * Returns a new state by applying an action to the current one. Action
     * already has information about the current state and one combination to
     * perform. Multiple actions for one state will each have a different
     * combination (valid one).
     *
     * @param action The action to perform.
     * @return The new state.
     */
    public RiverWorldState applyAction(RiverWorldAction action) {

        // Creating local clean copies that do not reference the ones from the state.
        // We do not want to modify the state in any way.
        ArrayList<Integer> validCombination = new ArrayList<>(action.validCombination);
        ArrayList<Person> northBankCopy = new ArrayList<>(action.northBank);
        ArrayList<Person> southBankCopy = new ArrayList<>(action.southBank);
        Boat boatCopy = new Boat(action.boat.seats, action.boat.maxLoad);
        Location boatLocationCopy = action.boatLocation;
        // To correctly compare array lists, other than just providing
        // overriden equals and hashCode methods,
        // they need to be sorted as well (Person has overridden compareTo method).
        Collections.sort(northBankCopy);
        Collections.sort(southBankCopy);
        for (int i = 0; i < validCombination.size(); i++) {
            if (boatLocationCopy == Location.NORTH) {

                // As valid combinations contains indexes which originate from
                // the given bank, we do not want the size of the bank to change
                // until all people are picked. Instead, the picked "positions"
                // are replaced with nulls which, when done, get removed.
                boatCopy.peopleOnBoat.add(northBankCopy.get(validCombination.get(i)));
                northBankCopy.set(validCombination.get(i), null);
            } else {
                boatCopy.peopleOnBoat.add(southBankCopy.get(validCombination.get(i)));
                southBankCopy.set(validCombination.get(i), null);
                boatLocationCopy = Location.SOUTH;
            }
        }
        northBankCopy.removeAll(Collections.singleton(null));
        southBankCopy.removeAll(Collections.singleton(null));

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
        RiverWorldState result = new RiverWorldState(riverWorld, boatCopy, boatLocationCopy, northBankCopy, southBankCopy);
        return result;
    }

    @Override
    public String toString() {
        String peopleOnNorthBank = "";
        String peopleOnSouthBank = "";
        if (northBank.isEmpty()) {
            peopleOnNorthBank += "empty\n";
        } else {
            peopleOnNorthBank += northBank.toString() + "\n";
        }
        if (southBank.isEmpty()) {
            peopleOnSouthBank += "empty\n";
        } else {
            peopleOnSouthBank += southBank.toString() + "\n";
        }
        String output = "-----------NORTH BANK-----------\n";
        output += peopleOnNorthBank;
        output += boatLocation == Location.NORTH ? "BOAT~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" : "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n";
        output += "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n";
        output += boatLocation == Location.SOUTH ? "BOAT~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" : "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n";
        output += peopleOnSouthBank;
        output += "-----------SOUTH BANK-----------\n";
        output += "------------------------------------------------------------------------";
        return output;
    }
}
