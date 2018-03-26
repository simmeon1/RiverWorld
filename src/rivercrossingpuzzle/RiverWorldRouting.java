/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rivercrossingpuzzle;

import cm3038.search.Node;
import cm3038.search.State;
import cm3038.search.informed.BestFirstSearchProblem;

/**
 *
 * @author Simeon
 */


public class RiverWorldRouting extends BestFirstSearchProblem{
    
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
        return 0;
    } //end method
    
    public boolean isGoal(State state) {
        return state.equals(this.goalState);
    }
}
