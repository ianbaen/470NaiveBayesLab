package bayes.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import bayes.Document;
import bayes.DocumentCategory;

public abstract class BayesModel {

	ArrayList<DocumentCategory> trainingSet;
	HashSet<String> vocabulary;
	HashMap<DocumentCategory, Double> probsOfCats;
	
	public BayesModel(){
		trainingSet = null;
		vocabulary =  null;
		probsOfCats = null;
		
	}
	
	public void setTrainingSet(ArrayList<DocumentCategory> trainingSet) {	
		this.trainingSet = trainingSet;		
		fillVocab();
		fillProbsOfCats();		
	}

	private void fillVocab() {
		vocabulary = new HashSet<String>();
		for(DocumentCategory curCat: trainingSet){
			Set<String> curSet = curCat.getWordCounts().keySet();
			vocabulary.addAll(curSet);
		}
	}
	
	public void fillProbsOfCats(){
		probsOfCats =  new HashMap<>();
		double totalFiles = 0;
		for(DocumentCategory curCat: trainingSet ){
			totalFiles += curCat.getDocuments().size();
		}
		
		for(DocumentCategory curCat: trainingSet ){
			double curCatDocCount = curCat.getDocuments().size();
			probsOfCats.put(curCat, curCatDocCount/totalFiles);
		}
		
		
	}

	public abstract String predict(Document doc);
}
