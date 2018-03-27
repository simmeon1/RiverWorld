package rivercrossingpuzzle;

import cm3038.search.*;

/**
 * Models a Person object.
 * @author Simeon Dobrudzhanski 1406444
 */
public class Person implements Comparable<Person> {

    /**
     * Person name.
     */
    public String name;

    /**
     * Person weight.
     */
    public double weight;

    /**
     * Person's ability to sail a boat.
     */
    public boolean canSail;

    /**
     * Creates the Person object.
     * @param name Person name.
     * @param weight Person weight.
     * @param canSail Person's ability to sail a boat.
     */
    public Person(String name, double weight, boolean canSail) {
        this.name = name;
        this.weight = weight;
        this.canSail = canSail;
    }

    @Override
    public String toString() {
        return name + "(" + weight + ")" + (canSail ? "[S]" : "");
    }
    
    /**
     * The north/south banks and boat location between the current and goal state
     * must match for the states to match. The banks are represented as array lists
     * of Person objects. Comparing them is safer and easier when they are sorted, thus
     * a sorting method has been added for People objects. Sorting first 
     * sorts alphabetically, then weight wise, then sailing ability wise, otherwise
     * defaults.
     * @param o Other person.
     * @return Result of comparison.
     */
    @Override
    public int compareTo(Person o) {
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
    
    /**
     * If all data matches between two Person objects, they are the same person.
     * @param o Person to compare to.
     * @return Result of comparison.
     */
    @Override
    public boolean equals (Object o) {
        if (o == this) return true;
        if (!(o instanceof Person)) {
            return false;
        }
        Person other = (Person) o;
        return this.name.equals(other.name) && this.weight == other.weight
                && this.canSail == other.canSail;
    }
    
    /**
     * The effective java method has been used to create a unique hashCode for a Person object.
     * https://medium.com/codelog/overriding-hashcode-method-effective-java-notes-723c1fedf51c
     * @return HashCode of Person object.
     */
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + name.hashCode();
        long weightLong = Double.doubleToLongBits(weight);
        result = 31 * result + (int) (weightLong ^ (weightLong >>> 32));
        result = 31 * result + (canSail ? 1 : 0);
        return result;
    }
}
