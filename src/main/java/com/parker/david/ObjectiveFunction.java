package com.parker.david;

import java.util.ArrayList;

/**
 * this is our objective function, it accepts a candidate solution and returns the fitness for that candidate solution
 */
public class ObjectiveFunction {
	/**
	 * this is the internal storage for weights for each decision variable
	 */
	private final ArrayList<Integer> objectiveFunctionEncoded;

	/**
	 * we initialise the objective function by defining each decision variable weight
	 * as an element of an arraylist of integers
	 *
	 * @param objectiveFunctionWeights an arraylist of integers with decision variable weights
	 */
	ObjectiveFunction(ArrayList<Integer> objectiveFunctionWeights) {
		objectiveFunctionEncoded = objectiveFunctionWeights;
	}

	/**
	 * this is the function that is called to calculate the fitness of a particular solution.
	 *
	 * @param solution this is the candidate solution that we seek to find fitness for
	 * @return the value of the fitness function for this solution
	 */
	public int evaluateFitness(CandidateSolution solution) {
		//set an initial value of fitness to the minimum (0)
		int totalFitnessValue = 0;

		//for each decision variable that is not false/0, we add the value associated with that decision variable to totalFitnessValue
		for (int i = 0; i < objectiveFunctionEncoded.size(); i++) {
			if (solution.getIthDecisionVariable(i))
				totalFitnessValue += objectiveFunctionEncoded.get(i);
		}
		return totalFitnessValue;
	}
}
