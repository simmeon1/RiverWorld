/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rivercrossingpuzzle;

/**
 *
 * @author Simeon
 */
public class RiverWorld {

    public Person[] southBank;
    public Person[] northBank;

    public RiverWorld(Person[] listOfPeople) {
        northBank = new Person[listOfPeople.length];
        southBank = new Person[listOfPeople.length];        
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
                peopleOnSouthBank += southBank[i].weight+ " , ";
            } else {
                peopleOnSouthBank += "empty, ";
            }
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
