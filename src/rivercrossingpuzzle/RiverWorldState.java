/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rivercrossingpuzzle;

import cm3038.search.*;
import com.sun.xml.internal.ws.util.StringUtils;
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
    public Location boatLocation;

    public RiverWorldState(RiverWorld riverWorld, Boat boat, Location boatLocation, ArrayList<Person> northBank, ArrayList<Person> southBank) {
        this.riverWorld = riverWorld;
        this.boat = boat;
        this.northBank = northBank;
        this.southBank = southBank;
        this.boatLocation = boatLocation;
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
                if (boatLocation == Location.SOUTH) {
                    southBank.add(boat.peopleOnBoat.remove(i));
                } else {
                    northBank.add(boat.peopleOnBoat.remove(i));
                }
            }
        }
        ArrayList<Person> peopleOnBoatBank = boatLocation == Location.SOUTH ? southBank : northBank;
        ArrayList<String> possibleCombinationsOnBoat = getPeopleCombinationsOnBoat(peopleOnBoatBank);
        ArrayList<String> validCombinationsOnBoat = new ArrayList<>();

        for (int i = 0; i < possibleCombinationsOnBoat.size(); i++) {
            String currentCombination = possibleCombinationsOnBoat.get(i);
            if (currentCombination == null || countPeopleInCombination(currentCombination) > boat.seats) {
                continue;
            }
            double totalWeightOfCombination = 0;
            boolean canAnyoneSail = false;
            String selectedIndex = "";
            for (int j = 0; j < currentCombination.length(); j++) {
                if (currentCombination.charAt(j) != '|') {
                    if (currentCombination.charAt(j) == '(' || currentCombination.charAt(j) == ')') {
                        continue;
                    }
                    selectedIndex += currentCombination.charAt(j);
                    continue;
                }
                Person selectedPerson = peopleOnBoatBank.get(Integer.parseInt(selectedIndex));
                if (selectedPerson.canSail) {
                    canAnyoneSail = true;
                }
                totalWeightOfCombination += selectedPerson.weight;
            }
            if (canAnyoneSail && totalWeightOfCombination <= boat.maxLoad) {
                validCombinationsOnBoat.add(currentCombination);
                System.out.println("Combination " + currentCombination + " is good.");
            } else {
                System.out.println("Combination " + currentCombination + " is not good.");
            }
            selectedIndex = "";
        }

        return result;
    }

    public int countPeopleInCombination(String combination) {
        String str = combination;
        String findStr = "|";
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
    }

    public ArrayList<String> getPeopleCombinationsOnBoat(ArrayList<Person> boatBank) {
        int count = (int) Math.pow(2, boatBank.size());
        ArrayList<String> possibleCombinations = new ArrayList<>();
        for (int i = 1; i <= count - 1; i++) {
            String currentCombination = "";
            String str = Integer.toString(i, 2);
            str = String.format("%0" + boatBank.size() + "d", Integer.parseInt(str));
            for (int j = 0; j < str.length(); j++) {
                if (str.charAt(j) == '1') {
                    System.out.print(boatBank.get(j));
                    currentCombination += "(" + j + ")" + "|";
                }
            }
            possibleCombinations.add(currentCombination);
            System.out.println();
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
        for (int i = 0; i < northBank.size(); i++) {
            if (northBank.get(i) != null) {
                peopleOnNorthBank += northBank.get(i).weight + " , ";
            } else {
                peopleOnNorthBank += "empty, ";
            }
            if (southBank.get(i) != null) {
                peopleOnSouthBank += southBank.get(i).weight + " , ";
            } else {
                peopleOnSouthBank += "empty, ";
            }
        }
        String output = "-----------NORTH BANK-----------\n";
        output += peopleOnNorthBank;
        output += boatLocation == Location.NORTH ? "\nBOAT(" + boat.getCountOfPeopleOnBoat() + ")~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" : "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~";
        output += "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~";
        output += boatLocation == Location.SOUTH ? "\nBOAT(" + boat.getCountOfPeopleOnBoat() + ")~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" : "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n";
        output += peopleOnSouthBank;
        output += "\n-----------SOUTH BANK-----------\n";
        output += "\n------------------------------------------------------------------------\n";
        return output;
    }
}
