/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rivercrossingpuzzle;

import cm3038.search.*;

/**
 *
 * @author Simeon
 */
public class Person {
    public double weight;
    public boolean canSail;
    
    public Person (double weight, boolean canSail) {
        this.weight = weight;
        this.canSail = canSail;
    }
    
    public String toString() {
        return "Weight: " + weight + ". Sail? " + canSail;
    }
}
