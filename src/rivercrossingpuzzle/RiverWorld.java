package rivercrossingpuzzle;

import java.util.*;

/**
 * RiverWorld object which represent the world without people or a boat.
 * Used to build up RiverWorldState objects.
 * @author Simeon Dobrudzhanski 1406444
 */
public class RiverWorld {
    
    /**
     * Represents north bank (no people).
     */
    public ArrayList<Person> northBank;

    /**
     * Represents south bank (no people).
     */
    public ArrayList<Person> southBank;
    
    /**
     * Initializes the RiverWorld object.
     */
    public RiverWorld() {
        northBank = new ArrayList<Person>();
        southBank = new ArrayList<Person>();
    }

    @Override
    public String toString() {
        String peopleOnNorthBank = "";
        String peopleOnSouthBank = "";
        peopleOnNorthBank += northBank.toString();
        peopleOnSouthBank += southBank.toString();
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
