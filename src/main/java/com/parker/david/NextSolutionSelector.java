package com.parker.david;

import java.util.ArrayList;

/**
 * an interface for selecting a solution from a set of candidate solutions and the current solution.
 * This allows for multiple definitions of a solution selector
 */
public interface NextSolutionSelector {
	/**
	 * method that selects a new solution from the current solution and neighbourhood.
	 * Should return null if no new solution is selected
	 *
	 * @param currentSolution the current solution around which the neighbourhood is generated
	 * @param neighbourhood   the neighbourhood of solutions
	 * @return a new solution to continue optimisation, or null if search is over
	 */
	CandidateSolution getNewSolution(CandidateSolution currentSolution, ArrayList<CandidateSolution> neighbourhood);

	/**
	 * a function that indicates whether the search has terminated or if there are more iterations still left using this method
	 *
	 * @return a boolean function that returns true if we terminate our search, and false if we have yet to terminate the search
	 */
	boolean isSearchComplete();
}
