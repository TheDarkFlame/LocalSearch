package com.parker.david;

import java.util.ArrayList;

/**
 * this is an implementation of a neighbourhood generation strategy.
 * In particular, it calculates the single bit compliment for each decision variable (encoded as a boolean)
 */
public class SingleBitComplimentGenerator implements NeighbourhoodGenerator {
	/**
	 * this function loops through i times, where i is the number of decision variables in the current solution.
	 * it makes a copy of the current solution, compliments the ith bit, and adds that new solution to the neighbourhood
	 * finally, after looping through all decision variables, the neighbourhood is returned.
	 * No logic is in place to avoid already visited decision variable combinations.
	 *
	 * @param currentSolution the solution for which a new neighbourhood needs to be found
	 * @return an array of candidate solutions that are in the neighbourhood of the current solution
	 */
	@Override
	public ArrayList<CandidateSolution> generateSolutionNeighbourhood(CandidateSolution currentSolution) {
		ArrayList<CandidateSolution> neighbourhood = new ArrayList<>();

		//loop through all decision variables
		for (int i = 0; i < currentSolution.numberOfDecisionVariables(); i++) {

			//create a copy, flip the ith bit, and add to the neighbourhood
			CandidateSolution neighbour = currentSolution.copy();
			neighbour.setIthDecisionVariable(i, !neighbour.getIthDecisionVariable(i));
			neighbourhood.add(neighbour);
		}
		return neighbourhood;
	}
}
