/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rivercrossingpuzzle;

import cm3038.search.*;
import java.util.Random;

/**
 *
 * @author Simeon
 */
public class Person implements Comparable<Person> {
    public int id;
    public double weight;
    public boolean canSail;
    
    public Person (double weight, boolean canSail) {
        Random rand = new Random();
        this.id = rand.nextInt(9999999)+1;
        this.weight = weight;
        this.canSail = canSail;
    }
    
    public String toString() {
        return "ID: " + id + ". Weight: " + weight + ". Sail? " + canSail;
    }
    
    @Override
    public int compareTo(Person o) {
        return id - o.id;
    }
}
