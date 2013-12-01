package ch.uzh.softwareengineering.ateam.client;

import java.util.ArrayList;

public class Data implements VotingService{
	
	private ArrayList<Voting> dataset;
	private int size;
	
	public Data(){
		dataset = new ArrayList();
	}
		
	@Override
	public Voting getVoting(int id) {
		// TODO Auto-generated method stub
		return dataset.get(id);
	}

	@Override
	public ArrayList<Voting> getVotings() {
		// TODO Auto-generated method stub
		return dataset;
	}

	@Override
	public Voting addVoting(Voting voting) {
		if (size == 0){  //immediately insert the vote if the set is empty. 
			dataset.add(voting);
			size++;
			return null;
		}
		for(int i = 0; i < size; i++){
			if (voting.getYear() > dataset.get(i).getYear()){  //insert the vote at position i, if it is 'younger' than the vote at this time 
				dataset.add(i, voting); 
				size++;
				return null;
			}
			if (voting.getYear() == dataset.get(i).getYear()){ // If two votes have the same age, check if the new one lexicographically precedes the old one. 
				if(voting.getTitle().compareTo(dataset.get(i).getTitle()) < 0){
					dataset.add(i, voting);
					size++;
					return null;
				}
			}
		}
		dataset.add(voting);
		size++;
		return null;
	}
	
	public int getSize(){
		return size;
	}
	
	public void quickSort(int left, int right){
		if (left >= right){
			return;
		}
		
		int j = partition(left, right);
		
		quickSort(left, j -1);
		quickSort(j + 1, right); 
	}
	
	public int partition(int left, int right){
		int j = left;
		int k = right -1;
		int pivot = dataset.get(right).getYear();
	
		while (j < k){
			while (dataset.get(j).getYear() <= pivot && j < right){
				j++;
			}
		
			while (dataset.get(k).getYear() >= pivot && k > left){
				k--;
			}
		
			if (j < k){
				Voting temp = dataset.get(j);
				dataset.set(j, dataset.get(k));
				dataset.set(k, temp);
			}
		}
	
		if (dataset.get(j).getYear() > pivot){
			Voting temp = dataset.get(j);
			dataset.set(j, dataset.get(right));
			dataset.set(right, temp);
		}
		
		return j; 
	}
}
