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
public class Person implements Comparable<Person> {

    public String name;
    public double weight;
    public boolean canSail;

    public Person(String name, double weight, boolean canSail) {
        this.name = name;
        this.weight = weight;
        this.canSail = canSail;
    }

    public String toString() {
        return name + "(" + weight + ")" + (canSail ? "[S]" : "");
    }

    @Override
    public int compareTo(Person o) {
        // convert input string to char array
        if (name.compareTo(o.name) == 0) {
            if (weight == o.weight) {
                if (canSail == o.canSail) {
                    return 0;
                }
                return canSail ? 0 : 1;
            }
            return weight > o.weight ? 0 : 1;
        }
        return name.compareTo(o.name);
    }
}
