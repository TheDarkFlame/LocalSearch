package com.parker.david;

import java.util.ArrayList;

/**
 * an interface for a neighbourhood generation function, allowing us to potentially
 * define multiple neighbourhood generation strategies
 */
public interface NeighbourhoodGenerator {
	/**
	 * this generates an arraylist around the current solution, implementation will be specific to the generation method
	 *
	 * @param currentSolution the solution around which we are searching for the neighbourhood
	 * @return returns an arraylist of candidate solutions in the neighbourhood of the current solution
	 */
	ArrayList<CandidateSolution> generateSolutionNeighbourhood(CandidateSolution currentSolution);
}
