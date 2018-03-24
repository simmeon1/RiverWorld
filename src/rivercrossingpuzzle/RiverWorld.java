/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rivercrossingpuzzle;

import java.util.*;

/**
 *
 * @author Simeon
 */
public class RiverWorld {

    public ArrayList<Person> southBank;
    public ArrayList<Person> northBank;

    public RiverWorld() {
        northBank = new ArrayList<Person>();
        southBank = new ArrayList<Person>();
    }

    public String toString() {
        String peopleOnNorthBank = "";
        String peopleOnSouthBank = "";
        for (int i = 0; i < northBank.size(); i++) {
            peopleOnNorthBank += northBank.get(i).weight + "(" + (northBank.get(i).canSail ? "canSail" : "noSail") + "), ";
        }
        for (int i = 0; i < southBank.size(); i++) {
            peopleOnSouthBank += southBank.get(i).weight + "(" + (southBank.get(i).canSail ? "canSail" : "noSail") + "), ";
        }
        String output = "-----------NORTH BANK-----------\n";
        output += peopleOnNorthBank;
        output += "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
                + "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
                + "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n";
        output += peopleOnSouthBank;
        output += "\n-----------SOUTH BANK-----------\n";
        return output;
    }
}
