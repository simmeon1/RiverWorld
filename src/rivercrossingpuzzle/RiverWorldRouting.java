package rivercrossingpuzzle;

import cm3038.search.*;
import cm3038.search.informed.*;
import java.util.*;

/**
 * Represent the puzzle problem and how to solve it (heuristic)
 *
 * @author Simeon Dobrudzhanski 1406444
 */
public class RiverWorldRouting extends BestFirstSearchProblem {

    /**
     * The goal state.
     */
    public RiverWorldState goal;

    /**
     * Creates the RiverWorldRouting object.
     *
     * @param start Start state.
     * @param goal Goal state.
     */
    public RiverWorldRouting(State start, State goal) {
        super(start, goal);
    }

    /**
     * The evaluation function is used to get the cost of a node. Depending on
     * the cost that node/path might get picked first or later. Works on the
     * idea that f(n)=g(n)+h(n). With the heuristic the node that gets picked
     * next is the one that seems the closest to the goal.
     *
     * @param node Node/path to get the cost of.
     * @return The cost.
     */
    @Override
    public double evaluation(Node node) {
        // Without a heuristic (0), the node/path to get picked first will be the one
        // that has the lowest cost to get to. The heuristic adds an admissable cost
        // to the goal state from the current state and influences the paths explored first.
        return node.getCost() + this.heuristic(node.state);
    }

    /**
     * Calculates a heuristic. This heuristic gets its cost by calculating the
     * total weight/cost of the people which are not on the right banks. In
     * other words, it can be said that in this scenario there are boats on both
     * ends, they have no weight or seat limit and that everybody can sail. This
     * is an admissible heuristic as it cannot be costlier than the best case
     * solution (boat is on the correct bank and everybody can get on and reach
     * the goal state once sailed). Realistically, the boat and people will have
     * their limits and will take more turns.
     *
     * @param state Current state.
     * @return Heuristic cost.
     */
    public double heuristic(State state) {
        double result = 0;

        // Creating local variables in order to not modify the state objects.
        RiverWorldState currentRiverWorldState = (RiverWorldState) state;
        RiverWorldState goalRiverWorldState = (RiverWorldState) this.goalState;
        ArrayList<Person> currentNorthBank = new ArrayList<>(currentRiverWorldState.northBank);
        ArrayList<Person> currentSouthBank = new ArrayList<>(currentRiverWorldState.southBank);
        ArrayList<Person> goalNorthBank = new ArrayList<>(goalRiverWorldState.northBank);
        ArrayList<Person> goalSouthBank = new ArrayList<>(goalRiverWorldState.southBank);

        // Array lists of missing people are created.
        // Whoever is on the wrong bank will need to get across at some point
        // so his/her cost is added to the heuristic.
        // The list is based on the goal list from which the members that are already
        // there are removed, leaving only the people that still need to move.
        // Their cost is added to the heuristic.
        ArrayList<Person> missingPeopleOnNorthBank = new ArrayList<>(goalNorthBank);
        ArrayList<Person> missingPeopleOnSouthBank = new ArrayList<>(goalSouthBank);
        missingPeopleOnNorthBank.removeAll(currentNorthBank);        
        missingPeopleOnSouthBank.removeAll(currentSouthBank);
        for (int i = 0; i < missingPeopleOnNorthBank.size(); i++) {
            result += missingPeopleOnNorthBank.get(i).weight;
        }
        for (int i = 0; i < missingPeopleOnSouthBank.size(); i++) {
            result += missingPeopleOnSouthBank.get(i).weight;
        }
        return result;
    }

    /**
     * Compares two sates.
     *
     * @param state Other state.
     * @return True if they match, false if they do not.
     */
    @Override
    public boolean isGoal(State state) {
        return state.equals(this.goalState);
    }
}
