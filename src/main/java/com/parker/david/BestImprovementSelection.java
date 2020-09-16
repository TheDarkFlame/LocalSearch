package com.parker.david;

import java.util.ArrayList;

/**
 * The Implementation of a solution selection interface.
 * selects
 */
public class BestImprovementSelection implements NextSolutionSelector {

	/**
	 * this flag is set to true
	 */
	private boolean searchTerminationFlag = false;

	/**
	 * this is the objective function, used for evaluating fitness of the candidate solution
	 */
	private final ObjectiveFunction objectiveFunction;

	/**
	 * this is the set of constraints which we use to determine if a solution is feasible
	 */
	private final ConstraintSet constraints;

	/**
	 * the constructor, requires that an objective function is given
	 *
	 * @param objectiveFunction the objective function
	 * @param constraints       the set of constraints
	 */
	BestImprovementSelection(ObjectiveFunction objectiveFunction, ConstraintSet constraints) {
		this.objectiveFunction = objectiveFunction;
		this.constraints = constraints;
	}

	/**
	 * our function to get a new solution from the neighbourhood given the current solution.
	 * returns null if there is no solution better than the current best solution
	 * also sets searchTerminationFlag to true if we have found the best solution
	 *
	 * @param currentSolution the current selected solution
	 * @param neighbourhood   the neighbourhood of solutions around the current solution as found by some neighbourhood function
	 * @return the new best solution from the neighbourhood or null
	 */
	@Override
	public CandidateSolution getNewSolution(CandidateSolution currentSolution, ArrayList<CandidateSolution> neighbourhood) {
		CandidateSolution bestSolution = null;
		//define the score to beat as the score of the current solution
		int scoreToBeat = objectiveFunction.evaluateFitness(currentSolution);

		//loop through the neighbourhood, first check if the solution is feasible, then if feasible, find the score of each neighbour
		for (CandidateSolution neighbour : neighbourhood) {

			//if solution is not feasible, move onto the next solution
			if (!constraints.isFeasible(neighbour))
				continue;

			//if neighbour score is the best so far, we set this neighbour as the best solution, and set its score as the score to beat
			int neighbourScore = objectiveFunction.evaluateFitness(neighbour);
			if (neighbourScore > scoreToBeat) {
				bestSolution = neighbour;
				scoreToBeat = neighbourScore;
			}
		}

		//our initial best solution is null, if it has not been set to something else, it means that we didn't find a better solution
		if (bestSolution == null)
			//we can't improve the solution, terminate the search
			searchTerminationFlag = true;
		return bestSolution;
	}

	/**
	 * accessor for searchTerminationFlag, returns true when the search has completed
	 *
	 * @return a boolean, true if no more better solutions are found, false if there are still better solutions
	 */
	@Override
	public boolean isSearchComplete() {
		return searchTerminationFlag;
	}
}
