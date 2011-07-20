/*
 * $LastChangedRevision: 774 $ $LastChangedBy: annemarie $ $LastChangedDate:
 * 2009-11-06 14:44:40 +0100 (Fr, 06 Nov 2009) $ $HeadURL:
 * http://sopro.examer.de
 * /svn/branches/CommonLayer/src/de/hft_stuttgart/sopro/common/data/DataIO.java
 * $ $Id: DataIO.java 774 2009-12-20 11:28:13Z annemarie $
 */
package de.hft_stuttgart.sopro.agent.data;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;

import de.hft_stuttgart.sopro.common.exceptions.JobInPermutationNotFoundException;
import de.hft_stuttgart.sopro.common.exceptions.StarttimeNotFoundException;
import de.hft_stuttgart.sopro.common.proposal.IProposal;

/**
 * A Class which provides methods for loading the problem-data from a file and
 * save the solution into a file.
 * 
 * @author Annemarie Meissner
 * @version 1.0
 */
public class DataOutputWriter {

	private static final String SOLUTION_RESULTS_PATH = "SolutionResults";

	/**
	 * The method gets the absolute path of the java project for the solution
	 * results
	 * 
	 * @return the absolute path of the solution results folder
	 */
	public String getSolutionResultsPath() {
		if (System.getProperties().getProperty("test.sulutionResults", null) != null) {
			return System.getProperties().getProperty("test.solutionResults");
		} else {
			return SOLUTION_RESULTS_PATH + File.separator;
		}
	}

	/**
	 * The method initializes the file for the negotiation time line
	 * 
	 * @param filePath
	 *            the filePath where the file should be written
	 * @return the file Writer object which is used in the method
	 *         writeNegotiationTimeLine
	 * @throws IOException
	 *             if the named file exists but is a directory rather than a
	 *             regular file, does not exist but cannot be created, or cannot
	 *             be opened for any other reason
	 */
	public FileWriter initWriteNegotiationTimeLine(String filePath, String projectName, int negotiationRounds, int proposalsPerRound, String votingAlgorithm) {
		// Associate the filepath and the project name with the current
		// timestamp
		String fileName = filePath + projectName + "__" + System.currentTimeMillis() + ".csv";
		FileWriter fileWriter = null;

		try {
			fileWriter = new FileWriter(fileName);
			// Write Negotiation specific data
			String projectSpecific = "Rounds; Proposals per Round; Voting Algorithm; \n";
			fileWriter.append(projectSpecific);
			String projectSpecificValues = negotiationRounds + ";" + proposalsPerRound + ";" + votingAlgorithm + ";\n\n";
			fileWriter.append(projectSpecificValues);

			// Write the column name in the file
			String columnName = "Round; TCV Agent1; TCV Agent2; TCV Sum;\n";
			fileWriter.append(columnName);
			fileWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return fileWriter;
	}

	/**
	 * The method writes the result of the current negotiation round into a csv
	 * file
	 * 
	 * @param fileWriter
	 *            the initialized fileWriter
	 * @param totalCashValueAgent1
	 *            the total cash value of the agent1 of the current round
	 * @param totalCashValueAgent2
	 *            the total cash value of the agent2 of the current round
	 * @param roundNumber
	 *            the number of the current round
	 * @return the string which is written in the file
	 */
	public String writeNegotiationTimeLine(FileWriter fileWriter, double totalCashValueAgent1, double totalCashValueAgent2, int roundNumber) {

		// Convert the double values in a prinable form
		DecimalFormat printFormat = new DecimalFormat("###,###0.0000");
		StringBuffer outputString = new StringBuffer();
		int numOfRes = 4;
		double sum = totalCashValueAgent1 + totalCashValueAgent2;
		outputString.append(roundNumber + ";" + printFormat.format(totalCashValueAgent1 / numOfRes) + ";" + printFormat.format(totalCashValueAgent2 / numOfRes) + ";"
				+ printFormat.format(sum / numOfRes) + ";\n");
		try {
			fileWriter.append(outputString.toString());
			fileWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return outputString.toString();
	}

	/**
	 * The method closes the file of the negotiation time line and print also
	 * the best solution in the file
	 * 
	 * @param fileWriter
	 *            the initialized fileWriter
	 * @param bestSolution
	 *            the highest sum of the the two cash values of the negotiation
	 * @param bestSolutionRound
	 *            the number of the round in which the best solution was found
	 * @param runtime
	 *            the runtime of the negotiation in milliseconds
	 * @return
	 */
	public String closeWriteNegotiationTimeLine(FileWriter fileWriter, double bestSolution, int bestSolutionRound, double runtime) {
		StringBuffer outputString = new StringBuffer();

		try {
			// outputString.append("\n; ; ; Best TCV sum; in Round; \n");
			int numOfRes = 4;
			DecimalFormat printFormat = new DecimalFormat("###,###0.0000");
			outputString.append(bestSolutionRound + "; ; ; " + printFormat.format(bestSolution / numOfRes) + "; Best TCV sum \n\n");
			double runtimeS = runtime / 1000;
			DecimalFormat printSeconds = new DecimalFormat("0.00");
			outputString.append("Runtime in sec" + ";" + printSeconds.format(runtimeS) + "; \n");

			fileWriter.append(outputString.toString());
			fileWriter.flush();

			// Close the file writer
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return outputString.toString();
	}

	/**
	 * The method writes the solution, the winner proposal, of a negotiation in
	 * a file
	 * 
	 * @param winner
	 *            the last winner proposal of the of a negotiation
	 * @param projectName
	 *            the name of the project, the instance
	 */
	public void writeSolutionProposal(String filePath, IProposal winner, String projectName) {
		StringBuffer outputString = new StringBuffer();
		FileWriter fileWriter = null;
		// Check if the file already exist
		boolean newFile;
		try {
			newFile = new File(filePath).createNewFile();

			// Create the fileWriter object
			fileWriter = new FileWriter(filePath);
			int numberOfJobs = winner.getPermutationSize();
			if (newFile) {
				// Add the column names in the file
				fileWriter.append("Instance; Number Of Jobs; Permutation;\n");
				outputString.append(projectName + ";" + numberOfJobs + ";");
				for (int i = 0; i < numberOfJobs; i++) {
					outputString.append(winner.getPermutation(i).getJobNumber() + " ");
				}
				fileWriter.append(outputString);
				fileWriter.append("\n");
				fileWriter.append("\n");
				fileWriter.flush();
			}

			// Write the starttimes of the solution proposals in the file
			for (int i = 1; i < numberOfJobs; i++) {
				fileWriter.append("Starttimes Job " + winner.getPermutation(i).getJobNumber() + ";");
				for (int j = 0; j < winner.getNumberOfResourceStarttimes(); j++) {
					int starttime = winner.getStarttime(j, i);
					fileWriter.append(starttime + " ");
				}
				fileWriter.append("\n");
			}
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JobInPermutationNotFoundException e) {
			e.printStackTrace();
		} catch (StarttimeNotFoundException e) {
			e.printStackTrace();
		}
	}

}
