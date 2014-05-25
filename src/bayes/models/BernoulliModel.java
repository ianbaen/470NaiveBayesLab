package bayes.models;

import java.math.BigDecimal;
import java.util.Set;

import bayes.Document;
import bayes.DocumentCategory;

public class BernoulliModel extends BayesModel{

	
	public BernoulliModel(){
		super();
		
	}
	

	@Override
	public String predict(Document doc) {
		String bestCat = "";
		BigDecimal highestProb = BigDecimal.ZERO;
		
		Set<String> words = doc.getWordCounts().keySet();
		
		for(DocumentCategory curCat: trainingSet ){
			BigDecimal curProb = new BigDecimal(probsOfCats.get(curCat));
			for(String word: vocabulary){
				boolean docContainsWord = doc.contains(word); 
				BigDecimal prob = new BigDecimal(curCat.probabilityWordAppearsInCatDocument(word));
				if(docContainsWord){
					curProb= curProb.multiply(prob);
				}
				else{
					curProb = curProb.multiply(BigDecimal.ONE.subtract(prob));
				}
			}
			if(highestProb.compareTo(curProb) < 0){
				highestProb = curProb;
				bestCat =curCat.getCategoryName();
				System.out.println(bestCat);
			}
			else{
				System.out.println("not: "+curCat.getCategoryName());
			}
		}
		return bestCat;
	}//end predict



}
