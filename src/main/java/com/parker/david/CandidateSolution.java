package com.parker.david;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * This is our data class that stores a candidate solution.
 * It is mostly just a wrapper for arraylist, but also contains a method that makes a deepcopy of itself
 * (thus creating a new entity that is the same as this entity with underlying data
 * that is a copy of the previous object instead of a reference to the same underlying data)
 */
public class CandidateSolution {
	/**
	 * our decision variables encoded as a boolean vector
	 */
	private final ArrayList<Boolean> decisionVariables;

	/**
	 * the number of decision variables in this problem, needed for iterating through the candidate solution
	 *
	 * @return the number of decision variables
	 */
	public int numberOfDecisionVariables() {
		return decisionVariables.size();
	}

	/**
	 * get the ith decision variable
	 * given: [5,3,2,7,4,0,6]
	 * getIthDecisionVariable(0) = 5, 1 = 3, etc
	 *
	 * @param i the ith decision variable's value
	 * @return a boolean corresponding to the decision variable at the ith position
	 */
	public Boolean getIthDecisionVariable(int i) {
		return decisionVariables.get(i);
	}

	/**
	 * same as the getter, but sets the decision variable instead
	 *
	 * @param i        the ith decision variable's value
	 * @param newValue the value to set the decision variable to
	 */
	public void setIthDecisionVariable(int i, Boolean newValue) {
		decisionVariables.set(i, newValue);
	}

	/**
	 * our constructor, take an arraylist of booleans and wraps it to create com.parker.david.CandidateSolution
	 *
	 * @param decisionVariables an arraylist of booleans correlating to the decision variables
	 */
	CandidateSolution(ArrayList<Boolean> decisionVariables) {
		this.decisionVariables = decisionVariables;
	}

	/**
	 * our deep copy method. Creates a brand new CandidateSolution that is a copy of this CandidateSolution.
	 * Changing data in one object does not affect data in the other object.
	 *
	 * @return a new solution that is a copy of the current one
	 */
	CandidateSolution copy() {
		ArrayList<Boolean> copyOfDecisionVariables = new ArrayList<>();
		for (Boolean decisionVariable : decisionVariables) {
			copyOfDecisionVariables.add(decisionVariable);
		}
		return new CandidateSolution(copyOfDecisionVariables);
	}

	/**
	 * toString method formats out Candidate solution nicely as an array of 0 and 1.
	 * example output: [0,1,0,0,0,1]
	 *
	 * @return a string representation of the decision variables encoded in 1s and 0s
	 */
	@Override
	public String toString() {
		return "[" + decisionVariables.stream().map(aBoolean -> aBoolean ? "1" : "0").collect(Collectors.joining()) + "]";
	}
}