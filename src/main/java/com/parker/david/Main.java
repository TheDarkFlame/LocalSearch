package com.parker.david;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciitable.CWC_LongestLine;
import de.vandermeer.asciithemes.a8.A8_Grids;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Our entry point into the program
 */
public class Main {
	/**
	 * entry point, just runs the problem over the given set of starts
	 */
	public static void main(String[] args) {
		//set our objective function from the problem
		ObjectiveFunction objectiveFunction = new ObjectiveFunction(new ArrayList<>(Arrays.asList(8, 12, 9, 14, 16, 10, 6, 7, 11, 13)));

		//set our constraints
		ConstraintSet constraints = new ConstraintSet();
		constraints.add(new Constraint(new ArrayList<>(Arrays.asList(3, 2, 1, 4, 3, 3, 1, 2, 2, 5)), Constraint.Operators.LESS_THAN_OR_EQUAL, 12));

		//set our initial solutions to the problem
		ArrayList<CandidateSolution> initialSolutions = new ArrayList<>();
		initialSolutions.add(new CandidateSolution(intListToBoolean(new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0)))));
		initialSolutions.add(new CandidateSolution(intListToBoolean(new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 1, 0)))));
		initialSolutions.add(new CandidateSolution(intListToBoolean(new ArrayList<>(Arrays.asList(0, 1, 1, 1, 0, 1, 0, 1, 0, 0)))));

		//loop through all specified starts and run the optimisation process for each of them
		for (CandidateSolution solution : initialSolutions) {
			optimiseProcess(solution, objectiveFunction, constraints);
		}
	}

	/**
	 * The bulk of everything is in here. Much functionality here could be moved into classes to make this function simpler.
	 * however, in the interest of keeping code easy to follow, the optimisation is coordinated largely by this function.
	 * this optimisation uses best improvement with single bit compliment, but is generalised in such a way that any
	 * selection method could be used and any neighbourhood generator could be used.
	 *
	 * @param startSolution     the start solution for this set of solutions
	 * @param objectiveFunction the objective function which we aim to optimise for
	 * @param constraints       the set of constraints which define the valid solution space
	 */
	public static void optimiseProcess(CandidateSolution startSolution, ObjectiveFunction objectiveFunction, ConstraintSet constraints) {

		//create an instance of a next solution selector. We choose here to use best improvement as our selector type
		NextSolutionSelector solutionSelector = new BestImprovementSelection(objectiveFunction, constraints);

		//create an instance of a neighbourhood generator. We choose here to use single-bit compliment as our generator
		NeighbourhoodGenerator neighbourhoodGenerator = new SingleBitComplimentGenerator();

		//create table and add header to it
		AsciiTable outputTable = new AsciiTable();
		outputTable.addRule();
		outputTable.addRow("t", "s^(t)", "z", "Neighbour", "Bit", "New z", " ");

		//we create our candidate solution object from our initial solution decision variables
		CandidateSolution currentSolution = startSolution;

		int iterationCounter = 0;

		//while our solution selector indicates that the search is not complete:
		//  1.  add the current solution into the list of solutions
		//  (first element thus is our initial solution, last is the solution before we can't find a new solution)
		//  2.  generate a new neighbourhood
		//  3.  select the best solution from that neighbourhood
		while (!solutionSelector.isSearchComplete()) {
			CandidateSolution oldSolution = currentSolution;
			ArrayList<CandidateSolution> solutionNeighbourhood = neighbourhoodGenerator.generateSolutionNeighbourhood(currentSolution);
			currentSolution = solutionSelector.getNewSolution(currentSolution, solutionNeighbourhood);
			printIteration(outputTable, iterationCounter++, objectiveFunction, constraints, oldSolution, solutionNeighbourhood, currentSolution);

			//search is over, print out the first half of the last step to indicate it is complete
			if (currentSolution == null) {
				outputTable.addLightRule();
				outputTable.addRow(iterationCounter, oldSolution, objectiveFunction.evaluateFitness(oldSolution), "", "", "", "");
				outputTable.addRule();
			}
		}

		//format the table and print it out
		outputTable.getRenderer().setCWC(new CWC_LongestLine());
		outputTable.getContext().setGrid(A8_Grids.lineDoubleBlocks());
		outputTable.setTextAlignment(TextAlignment.CENTER);
		System.out.println(outputTable.render());
		System.out.println();
		System.out.println();
	}

	/**
	 * A simple function to convert an input integer arraylist into a boolean arraylist, thus preserving memory
	 *
	 * @param intList the input integer arraylist
	 * @return a Boolean ArrayList
	 */
	private static ArrayList<Boolean> intListToBoolean(ArrayList<Integer> intList) {
		return intList.stream().map((number) -> number != 0).collect(Collectors.toCollection(ArrayList::new));
	}

	/**
	 * this is a print function that generates a print table for elements in the following order:
	 * t | s^(t) | z | Neighbour | Bit | New z |
	 *
	 * @param table             the table object that holds all the data
	 * @param iteration         the current iteration, t
	 * @param currentSolution   the current solution that this iteration used as a starting point
	 * @param neighbourhood     the neighbourhood found from the current solution
	 * @param objectiveFunction the objective function, used to calculate the fitness value in the table
	 * @param selectedNeighbour the solution contained in the neighbourhood that was selected
	 */
	private static void printIteration(AsciiTable table, int iteration, ObjectiveFunction objectiveFunction, ConstraintSet constraints, CandidateSolution currentSolution, ArrayList<CandidateSolution> neighbourhood, CandidateSolution selectedNeighbour) {
		//add in a line to split off this iteration
		table.addLightRule();

		// loop through all neighbours and print them out nicely, with the first line being a full line
		for (int i = 0; i < neighbourhood.size(); i++) {
			//grab the neighbour from the neighbourhood
			CandidateSolution neighbour = neighbourhood.get(i);
			table.addRow(
					//first row needs to contain iteration number, current solution, and fitness value, rest of rows they are blank
					(i == 0) ? iteration : "",
					(i == 0) ? currentSolution : "",
					(i == 0) ? objectiveFunction.evaluateFitness(currentSolution) : "",
					neighbour,
					i + 1, // the 1-indexed (instead of 0 indexed) bit that was flipped
					(constraints.isFeasible(neighbour)) ? objectiveFunction.evaluateFitness(neighbour) : "infeasible", //if feasible, the current fitness, otherwise "infeasible"
					(selectedNeighbour == neighbour) ? "<---" : "" //if this neighbour is selected, put in arrow, else nothing
			);

		}
	}
}
