/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rivercrossingpuzzle;

import cm3038.search.ActionStatePair;
import cm3038.search.State;
import java.util.*;

/**
 *
 * @author Simeon
 */
public class Boat {

    public int seats;
    public double maxLoad;
    public ArrayList<Person> peopleOnBoat;
    public Location location;
    public RiverWorld world;
    //public Location location;

    public Boat(int seats, double maxLoad, RiverWorld world, Location location) {
        this.seats = seats;
        this.maxLoad = maxLoad;
        this.world = world;
        this.peopleOnBoat = new ArrayList<Person>();
        this.location = location;
        //this.location = Location.SOUTH;
    }
    
    public Person loadPersonOnBoat (Person person) {
        peopleOnBoat.add(person);
        return person;
    }
    
    public int getCountOfPeopleOnBoat () {
        return peopleOnBoat.size();
    }
     
}
