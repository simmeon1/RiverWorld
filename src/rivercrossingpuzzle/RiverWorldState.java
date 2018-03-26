/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rivercrossingpuzzle;

import cm3038.search.*;
import com.sun.xml.internal.ws.util.StringUtils;
import java.math.BigInteger;
import java.util.*;

/**
 *
 * @author Simeon
 */
public class RiverWorldState implements State {

    public Boat boat;
    public RiverWorld riverWorld;
    public ArrayList<Person> northBank;
    public ArrayList<Person> southBank;

    public RiverWorldState(RiverWorld riverWorld, Boat boat, ArrayList<Person> northBank, ArrayList<Person> southBank) {
        this.riverWorld = riverWorld;
        this.boat = boat;
        this.northBank = northBank;
        this.southBank = southBank;
    }

    /*public RiverWorld generatePeopleOnBank(ArrayList<Person> listOfPeople, Location bank) {
        for (int i = 0; i < listOfPeople.size(); i++) {
            if (bank == Location.NORTH) {
                riverWorld.northBank.set(i, listOfPeople.get(i));
            } else {
                riverWorld.southBank.set(i, listOfPeople.get(i));
            }
        }
        return riverWorld;
    }*/
    public List<ActionStatePair> successor() {
        List<ActionStatePair> result = new ArrayList<ActionStatePair>();
        for (int i = 0; i < boat.peopleOnBoat.size(); i++) {
            if (boat.peopleOnBoat.get(i) != null) {
                if (boat.location == Location.SOUTH) {
                    southBank.add(boat.peopleOnBoat.remove(i));
                } else {
                    northBank.add(boat.peopleOnBoat.remove(i));
                }
            }
        }
        ArrayList<Person> peopleOnBoatBank = boat.location == Location.SOUTH ? southBank : northBank;
        ArrayList<ArrayList<Integer>> possibleCombinationsOnBoat = getPeopleCombinationsOnBoat(peopleOnBoatBank);
        ArrayList<ArrayList<Integer>> validCombinationsOnBoat = new ArrayList<>();

        for (int i = 0; i < possibleCombinationsOnBoat.size(); i++) {
            ArrayList<Integer> currentCombination = possibleCombinationsOnBoat.get(i);
            if (currentCombination == null) {
                continue;
            }
            if (currentCombination.size() > boat.seats) {
                System.out.println("Combination " + currentCombination + " is not good due to needed seats being "
                        + currentCombination.size() + "/" + boat.seats + ".");
                continue;
            }
            double totalWeightOfCombination = 0;
            boolean canAnyoneSail = false;
            for (int j = 0; j < currentCombination.size(); j++) {
                Person selectedPerson = peopleOnBoatBank.get(currentCombination.get(j));
                if (selectedPerson.canSail) {
                    canAnyoneSail = true;
                }
                totalWeightOfCombination += selectedPerson.weight;
            }
            if (canAnyoneSail && totalWeightOfCombination <= boat.maxLoad) {
                validCombinationsOnBoat.add(currentCombination);
                System.out.println("Combination " + currentCombination + " is good.");
            } else if (totalWeightOfCombination > boat.maxLoad) {
                System.out.println("Combination " + currentCombination + " is not good due to total weight being "
                        + totalWeightOfCombination + "/" + boat.maxLoad + ".");
            } else if (!canAnyoneSail) {
                System.out.println("Combination " + currentCombination + " is not good due to no sailors.");
            }
        }
        System.out.println("End result: " + validCombinationsOnBoat.size() + "/" + possibleCombinationsOnBoat.size() + " combinations are good.");

        return result;
    }

    /*public int countPeopleInCombination(String combination) {
        String str = combination;
        String findStr = "(";
        int lastIndex = 0;
        int count = 0;

        while (lastIndex != -1) {

            lastIndex = str.indexOf(findStr, lastIndex);

            if (lastIndex != -1) {
                count++;
                lastIndex += findStr.length();
            }
        }
        return count;
    }*/
    public ArrayList<ArrayList<Integer>> getPeopleCombinationsOnBoat(ArrayList<Person> boatBank) {
        int count = (int) Math.pow(2, boatBank.size());
        ArrayList<ArrayList<Integer>> possibleCombinations = new ArrayList<>();
        for (int i = 1; i <= count - 1; i++) {
            ArrayList<Integer> currentCombination = new ArrayList<>();
            String str = Integer.toString(i, 2);
            BigInteger strInt = new BigInteger(str);
            str = String.format("%0" + boatBank.size() + "d", strInt);
            for (int j = 0; j < str.length(); j++) {
                if (str.charAt(j) == '1') {
                    //System.out.print(boatBank.get(j));
                    currentCombination.add(j);
                }
            }
            possibleCombinations.add(currentCombination);
            //System.out.println();
        }
        return possibleCombinations;
    }

    /*public ArrayList<Person> getPeopleOnBank(ArrayList<Person> bank) {
        int result = 0;
        for (int i = 0; i < bank.size(); i++) {
            if (bank.get(i) != null) {
                result++;
            }
        }
        ArrayList<Person> peopleOnBank = new ArrayList<Person>();
        for (int j = 0; j < bank.size(); j++) {
            if (bank.get(j) != null) {
                for (int n = 0; n < peopleOnBank.size(); n++) {
                    if (peopleOnBank[n] == null) {
                        peopleOnBank[n] = bank[j];
                        break;
                    }
                }
            }
        }
        return peopleOnBank;
    }*/
    public String toString() {
        String peopleOnNorthBank = "";
        String peopleOnSouthBank = "";
        if (northBank.size() == 0) {
            peopleOnNorthBank += "empty";
        } else {
            for (int i = 0; i < northBank.size(); i++) {
                peopleOnNorthBank += northBank.get(i).weight + " , ";
            }
        }
        if (southBank.size() == 0) {
            peopleOnSouthBank += "empty";
        } else {
            for (int i = 0; i < southBank.size(); i++) {
                peopleOnSouthBank += southBank.get(i).weight + " , ";
            }
        }
        String output = "-----------NORTH BANK-----------\n";
        output += peopleOnNorthBank;
        output += boat.location == Location.NORTH ? "\nBOAT(" + boat.getCountOfPeopleOnBoat() + ")~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" : "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~";
        output += "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~";
        output += boat.location == Location.SOUTH ? "\nBOAT(" + boat.getCountOfPeopleOnBoat() + ")~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" : "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n";
        output += peopleOnSouthBank;
        output += "\n-----------SOUTH BANK-----------\n";
        output += "\n------------------------------------------------------------------------\n";
        return output;
    }
}
