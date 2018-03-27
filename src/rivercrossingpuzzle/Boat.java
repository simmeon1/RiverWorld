package rivercrossingpuzzle;

import java.util.*;

/**
 * Models a Boat object to be used in the puzzle.
 *
 * @author Simeon Dobrudzhanski 1406444
 */
public class Boat {

    /**
     * The number of seats that the boat has.
     */
    public int seats;

    /**
     * The maximum weight a boat can handle.
     */
    public double maxLoad;

    /**
     * A list of the current people on the boat.
     */
    public ArrayList<Person> peopleOnBoat;

    /**
     * Creates a boat with the given seats and max load. It is created with no
     * people.
     *
     * @param seats The number of seats that the boat has.
     * @param maxLoad The maximum weight a boat can handle.
     * @param world The initial river world.
     */
    public Boat(int seats, double maxLoad) {
        this.seats = seats;
        this.maxLoad = maxLoad;
        this.peopleOnBoat = new ArrayList<Person>();
    }
}
