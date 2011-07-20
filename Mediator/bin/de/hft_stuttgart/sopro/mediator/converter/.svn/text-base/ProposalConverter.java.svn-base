package de.hft_stuttgart.sopro.mediator.converter;

import java.util.ArrayList;
import java.util.List;

import de.hft_stuttgart.sopro.common.exceptions.JobInPermutationNotFoundException;
import de.hft_stuttgart.sopro.common.exceptions.StarttimeNotFoundException;
import de.hft_stuttgart.sopro.common.project.IJob;
import de.hft_stuttgart.sopro.common.proposal.IProposal;
import de.hft_stuttgart.sopro.common.proposal.Proposal;
import de.hft_stuttgart.sopro.common.wrapper.JobWrapper;
import de.hft_stuttgart.sopro.common.wrapper.ProposalWrapper;

/**
 * This class mainly converts from {@link IProposal} instances to
 * {@link ProposalWrapper} instances and vice versa. Based upon converter
 * classes by edu
 * 
 * @author Sandro Degiorgi - sdegiorgi@gmail.com
 * @author Annemarie Meissner - meissner.annemarie@gmx.de
 */
public class ProposalConverter {

	/**
	 * Default constructor.
	 */
	public ProposalConverter() {
	}

	/**
	 * Converts a {@link ProposalWrapper} array to a {@link Proposal} list.
	 * 
	 * @param wrappedProposals
	 *            the array proposals.
	 * @return A list {@link IProposal} objects or null.
	 */
 	public List<IProposal> fromProposalWrapperArrayToProposalList(ProposalWrapper[] wrappedProposals) {
		List<IProposal> proposals = null;
		if (null != wrappedProposals) {
			proposals = new ArrayList<IProposal>(wrappedProposals.length);
			for (int i = 0; i < wrappedProposals.length; i++) {
				proposals.add(fromProposalWrapperToProposal(wrappedProposals[i]));
			}
		}
		return proposals;
	}
 	
	/**
	 * Converts a {@link ProposalWrapper} array to a {@link Proposal} list.
	 * 
	 * @param wrappedProposal
	 *            the wrapped proposal.
	 * @return An {@link IProposal} object or null.
	 */
 	private IProposal fromProposalWrapperToProposal(ProposalWrapper wrappedProposal) { 
		IProposal proposal = null;
		if (null != wrappedProposal) {
            proposal = new Proposal(wrappedProposal.getNumberOfJobs(), wrappedProposal.getNumberOfResources());
            proposal.setEvaluationPoints(wrappedProposal.getEvaluationPoints());
            
            List<List<Integer>> startTimes = new ArrayList<List<Integer>>();
            int wrappedStartTimes[][] = wrappedProposal.getStartTimes();
            
			for (int resIndex = 0; resIndex < wrappedProposal.getNumberOfResources(); resIndex++) {
				List<Integer> startTimeArray = new ArrayList<Integer>(wrappedProposal.getNumberOfJobs());
				for (int jobIndex = 0; jobIndex < wrappedProposal.getNumberOfJobs(); jobIndex++) {
					    startTimeArray.add(wrappedStartTimes[resIndex][jobIndex]);
				}
                startTimes.add(startTimeArray);
			}
            proposal.setStarttimes(startTimes);
            proposal.setPermutation(new JobConverter().fromJobWrapperArrayToJobList(wrappedProposal.getPermutation()));
		}
		return proposal;
	}
	
	/**
	 * Converts a {@link IProposal} list to a {@link ProposalWrapper} array.
	 * 
	 * @param proposals
	 *            the offered list of proposals.
	 * @return An array with {@link ProposalWrapper} objects or null.
	 */
	public ProposalWrapper[] fromProposalListToProposalWrapperArray(List<IProposal> sourceProposals) {
		ProposalWrapper[] targetProposals = null;
		if (null != sourceProposals) {
			targetProposals = new ProposalWrapper[sourceProposals.size()];
			for (int i = 0; i < sourceProposals.size(); i++) {
				targetProposals[i] = fromProposalToProposalWrapper(sourceProposals.get(i));
			}
		}
		return targetProposals;
	}

	/**
	 * Converts a single {@link IProposal} to a {@link PropsalWrapper}.
	 * 
	 * @param sourceProposal
	 *            The proposal to convert.
	 * @return An instance of {@link ProposalWrapper} or null.
	 */
	private ProposalWrapper fromProposalToProposalWrapper(IProposal sourceProposal) {
		ProposalWrapper wrappedProposal = null;
		if (null != sourceProposal) {
			wrappedProposal = new ProposalWrapper(sourceProposal.getPermutationSize(),sourceProposal.getNumberOfResourceStarttimes());
			int startTimes[][] = new int[sourceProposal.getNumberOfResourceStarttimes()][sourceProposal.getPermutationSize()];
			// guess this is needless, since evaluation points are == 0 here
			wrappedProposal.setEvaluationPoints(sourceProposal.getEvaluationPoints());

			for (int resIndex = 0; resIndex < sourceProposal.getNumberOfResourceStarttimes(); resIndex++) {
				for (int jobIndex = 0; jobIndex < sourceProposal.getPermutationSize(); jobIndex++) {
					try {
						startTimes[resIndex][jobIndex] = sourceProposal.getStarttime(resIndex, jobIndex);
					} catch (StarttimeNotFoundException e) {
						// StartTimeNotFoundException - should not happen
						e.printStackTrace();
					}
				}
			}
			wrappedProposal.setStartTimes(startTimes);

			List<IJob> sourceProposalJobs = new ArrayList<IJob>();
			for (int jobIndex = 0; jobIndex < sourceProposal.getPermutationSize(); jobIndex++) {
				try {
					sourceProposalJobs.add(sourceProposal.getPermutation(jobIndex));
				} catch (JobInPermutationNotFoundException e) {
					// JobInPermutationNotFoundException - again, should not
					// happen
					e.printStackTrace();
				}
			}

			wrappedProposal.setPermutation(new JobConverter().fromJobListToJobWrapperArray(sourceProposalJobs));
		}
		return wrappedProposal;
	}
	
	/**
	 * Compare all ProposalWrapper attributes with the Proposal attributes
	 * 
	 * @param wrapperObj
	 *            the ProposalWrapper object
	 * @param jobObj
	 *            the Proposal object
	 * @return true if all attributes of the Proposal objects have the same value as
	 *         the attributes of the Proposal wrapper object
	 */
	public boolean compareProposalWrapperWithProposal(ProposalWrapper proposalWrapper, IProposal proposalObj){
		if (proposalWrapper == null && proposalObj == null) {
			return true;
		} else if (proposalWrapper != null && proposalObj == null) {
			return false;
		} else if (proposalWrapper == null && proposalObj != null) {
			return false;
		} else {
			//Check evaluation points
			if(proposalWrapper.getEvaluationPoints()!=proposalObj.getEvaluationPoints()){
				return false;
			}
			
			//Check the number of jobs in the permutation in the proposals
			if(proposalWrapper.getNumberOfJobs()!= proposalObj.getPermutationSize()){
				return false;
			}
			
			//Check the number of resources on the proposals
			if(proposalWrapper.getNumberOfResources() != proposalObj.getNumberOfResourceStarttimes()){
				return false;
			}
			
			//Check the Permutation
			JobWrapper[] permutationWrapper = proposalWrapper.getPermutation();
			if(permutationWrapper.length != proposalObj.getPermutationSize()){
				return false;
			}
			
			//Check Permutation content
			JobConverter jobConverter = new JobConverter();
			for(int i=0; i<permutationWrapper.length; i++){
				try {
					if(!jobConverter.compareJobWrapperWithJob(permutationWrapper[i], proposalObj.getPermutation(i))){
						return false;
					}
				} catch (JobInPermutationNotFoundException e) {
					e.printStackTrace();
				}
			}
			
			//Check the start times
			int[][] starttimes = proposalWrapper.getStartTimes();
			for(int i=0; i<proposalWrapper.getNumberOfResources(); i++){
				int[] starttimePerResource = starttimes[i];
				if(starttimePerResource.length!=proposalObj.getPermutationSize()){
					return false;
				}
				
				for(int j=0; j<starttimePerResource.length; j++){
					int starttimeWrapper = starttimePerResource[j];
					int starttimeProposal = 0;
					try {
						starttimeProposal = proposalObj.getStarttime(i, j);
					} catch (StarttimeNotFoundException e) {
						e.printStackTrace();
					}
					if(starttimeWrapper!=starttimeProposal){
						return false;
					}
				}
			}
		
		}
		
		return true;
	}
}
