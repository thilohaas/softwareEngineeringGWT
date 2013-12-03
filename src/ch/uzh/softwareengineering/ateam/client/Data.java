package ch.uzh.softwareengineering.ateam.client;

import java.util.ArrayList;

public class Data implements VotingService{
	
	private ArrayList<Voting> dataset;
	
	public Data(ArrayList<Voting> dataset){
		this.dataset = dataset;
	}
	
	public Voting getVoting(int index) {
		if(index < this.dataset.size()) {
			return this.dataset.get(index);
		}
		
		return null;
	}

	@Override
	public ArrayList<Voting> getVotings() {
		// TODO Auto-generated method stub
		return dataset;
	}
	
	public int getSize(){
		return dataset.size();
	}

	public void copyData(Data newData){
		dataset.clear();
		for(int i = 0; i < newData.getSize(); i++){
			dataset.add(newData.getVotings().get(i));
		}
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
