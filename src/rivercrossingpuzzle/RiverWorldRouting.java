/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rivercrossingpuzzle;

import cm3038.search.Node;
import cm3038.search.State;
import cm3038.search.informed.BestFirstSearchProblem;
import java.util.ArrayList;

/**
 *
 * @author Simeon
 */
public class RiverWorldRouting extends BestFirstSearchProblem {

    public RiverWorldState goal;	//the goal state
    public RiverWorld world;	//the ant world

    public RiverWorldRouting(State start, State goal, RiverWorld world) {
        super(start, goal);
        this.world = world;
    } //end method

    @Override
    public double evaluation(Node node) {
        return node.getCost() + this.heuristic(node.state);	//f(n)=g(n)+h(n)
    } //end method

    public double heuristic(State state) {
        double result = 0;
        RiverWorldState currentRiverWorldState = (RiverWorldState) state;
        RiverWorldState goalState = (RiverWorldState) this.goalState;
        //Location goalLocation = goal.boatLocation == Location.NORTH ? Location.NORTH : Location.SOUTH;
        ArrayList<Person> currentNorthBank = new ArrayList<>();
        ArrayList<Person> currentSouthBank = new ArrayList<>();
        ArrayList<Person> goalNorthBank = new ArrayList<>();
        ArrayList<Person> goalSouthBank = new ArrayList<>();
        for (int i = 0; i < currentRiverWorldState.northBank.size(); i++) {
            currentNorthBank.add(currentRiverWorldState.northBank.get(i));
        }
        for (int i = 0; i < currentRiverWorldState.southBank.size(); i++) {
            currentSouthBank.add(currentRiverWorldState.southBank.get(i));
        }
        for (int i = 0; i < goalState.northBank.size(); i++) {
            goalNorthBank.add(goalState.northBank.get(i));
        }
        for (int i = 0; i < goalState.southBank.size(); i++) {
            goalSouthBank.add(goalState.southBank.get(i));
        }
        ArrayList<Person> missingPeopleOnNorthBank = new ArrayList<>(goalNorthBank);
        missingPeopleOnNorthBank.removeAll(currentNorthBank);
        ArrayList<Person> missingPeopleOnSouthBank = new ArrayList<>(goalSouthBank);
        missingPeopleOnSouthBank.removeAll(currentSouthBank);
        for (int i = 0; i < missingPeopleOnNorthBank.size(); i++) {
            result += missingPeopleOnNorthBank.get(i).weight;
        }
        for (int i = 0; i < missingPeopleOnSouthBank.size(); i++) {
            result += missingPeopleOnSouthBank.get(i).weight;
        }
        return result;
    } //end method

    public boolean isGoal(State state) {
        return state.equals(this.goalState);
    }
}
