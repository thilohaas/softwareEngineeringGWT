package ch.uzh.softwareengineering.ateam.client;

import java.util.ArrayList;

public class Data implements VotingService{
	
	private ArrayList<Voting> dataset;
	private int size = 0;
	
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
		dataset.add(voting);
		size++;
		return null;
	}
	
	public int getSize(){
		return size;
	}
	
	public void copyData(Data newData){
		dataset.clear();
		for(int i = 0; i < newData.getSize(); i++){
			dataset.add(newData.getVotings().get(i));
		}
		size = dataset.size();
		
	}
}
