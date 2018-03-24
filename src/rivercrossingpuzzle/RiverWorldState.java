/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rivercrossingpuzzle;

import cm3038.search.*;
import java.util.*;

/**
 *
 * @author Simeon
 */
public class RiverWorldState implements State {

    public Boat boat;
    public RiverWorld riverWorld;
    public Person[] northBank;
    public Person[] southBank;
    public Location boatLocation;

    public RiverWorldState(RiverWorld riverWorld, Boat boat, Location boatLocation, Person[] northBank, Person[] southBank) {
        this.riverWorld = riverWorld;
        this.boat = boat;
        this.northBank = northBank;
        this.southBank = southBank;
        this.boatLocation = boatLocation;
    }

    public RiverWorld generatePeopleOnBank(Person[] listOfPeople, Location bank) {
        for (int i = 0; i < listOfPeople.length; i++) {
            if (bank == Location.NORTH) {
                riverWorld.northBank[i] = listOfPeople[i];
            } else {
                riverWorld.southBank[i] = listOfPeople[i];
            }
        }
        return riverWorld;
    }

    public List<ActionStatePair> successor() {
        List<ActionStatePair> result = new ArrayList<ActionStatePair>();
        for (int i = 0; i < boat.seats; i++) {
            if (boat.peopleOnBoat[i] != null) {
                if (boatLocation == Location.SOUTH) {
                    for (int j = 0; j < southBank.length; j++) {
                        if (southBank[j] == null) {
                            southBank[j] = boat.peopleOnBoat[i];
                            boat.peopleOnBoat[i] = null;
                            break;
                        }
                    }
                } else {
                    for (int j = 0; j < northBank.length; j++) {
                        if (northBank[j] == null) {
                            northBank[j] = boat.peopleOnBoat[i];
                            boat.peopleOnBoat[i] = null;
                            break;
                        }
                    }
                }
            }
        }
        Person[] peopleOnBoatBank = getPeopleOnBank(boatLocation == Location.SOUTH ? southBank : northBank);
        String[] possibleCombinationsOnBoat = getPeopleCombinationsOnBoat(peopleOnBoatBank);
        ArrayList<String> validCombinationsOnBoat = new ArrayList<>(); 
        
        for (int i = 0; i < possibleCombinationsOnBoat.length; i++) {
            if (possibleCombinationsOnBoat[i] == null || possibleCombinationsOnBoat[i].length() > boat.seats) {
                continue;
            }
            double totalWeightOfCombination = 0;
            boolean canAnyoneSail = false;
            for (int j = 0; j < possibleCombinationsOnBoat[i].length(); j++) {
                Person selectedPerson = peopleOnBoatBank[Character.getNumericValue(possibleCombinationsOnBoat[i].charAt(j))];
                if (selectedPerson.canSail) {
                    canAnyoneSail = true;
                }
                totalWeightOfCombination += selectedPerson.weight;
            }
            if (canAnyoneSail && totalWeightOfCombination <= boat.maxLoad) {
                validCombinationsOnBoat.add(possibleCombinationsOnBoat[i]);
                System.out.println("Combination " + possibleCombinationsOnBoat[i] + " is good.");
            } else {
                System.out.println("Combination " + possibleCombinationsOnBoat[i] + " is not good.");
            }
        }
        
        return result;
    }

    public String[] getPeopleCombinationsOnBoat(Person[] boatBank) {           
        int count = (int) Math.pow(2, boatBank.length);
        String[] possibleCombinations = new String[count];    
        for (int i = 1; i <= count - 1; i++) {
            String currentCombination = "";
            String str = Integer.toString(i, 2);
            str = String.format("%0" + boatBank.length + "d", Integer.parseInt(str));
            for (int j = 0; j < str.length(); j++) {
                if (str.charAt(j) == '1') {
                    System.out.print(boatBank[j]);
                    currentCombination += j;
                }
            }
            possibleCombinations[i] = currentCombination;
            System.out.println();
        }
        return possibleCombinations;
    }

    public Person[] getPeopleOnBank(Person[] bank) {
        int result = 0;
        for (int i = 0; i < bank.length; i++) {
            if (bank[i] != null) {
                result++;
            }
        }
        Person[] peopleOnBank = new Person[result];
        for (int j = 0; j < bank.length; j++) {
            if (bank[j] != null) {
                for (int n = 0; n < peopleOnBank.length; n++) {
                    if (peopleOnBank[n] == null) {
                        peopleOnBank[n] = bank[j];
                        break;
                    }
                }
            }
        }
        return peopleOnBank;
    }

    public String toString() {
        String peopleOnNorthBank = "";
        String peopleOnSouthBank = "";
        for (int i = 0; i < northBank.length; i++) {
            if (northBank[i] != null) {
                peopleOnNorthBank += northBank[i].weight + " , ";
            } else {
                peopleOnNorthBank += "empty, ";
            }
            if (southBank[i] != null) {
                peopleOnSouthBank += southBank[i].weight + " , ";
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
