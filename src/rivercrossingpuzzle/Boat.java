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
    public Person[] peopleOnBoat;
    public RiverWorld world;
    //public Location location;

    public Boat(int seats, double maxLoad, RiverWorld world) {
        this.seats = seats;
        this.maxLoad = maxLoad;
        this.world = world;
        this.peopleOnBoat = new Person[seats];
        //this.location = Location.SOUTH;
    }
    
    public Boat loadPersonOnBoat (Person person) {
        for (int i = 0; i < peopleOnBoat.length; i++) {
            if (peopleOnBoat[i] == null) {
                peopleOnBoat[i] = person;
                break;
            }
        }
        return this;
    }
    
    public int getCountOfPeopleOnBoat () {
        int result = 0;
        for (int i = 0; i < peopleOnBoat.length; i++) {
            if (peopleOnBoat[i] != null) {
                result++;
            }
        }
        return result;
    }
    
    public void getAllUniquePoepleOnBoatCombinations() {

    }    
}
