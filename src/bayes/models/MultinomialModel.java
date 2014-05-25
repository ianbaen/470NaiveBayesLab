package bayes.models;

import java.math.BigDecimal;
import java.util.Set;

import bayes.Document;
import bayes.DocumentCategory;

public class MultinomialModel extends BayesModel{

	
	
	@Override
	public String predict(Document doc) {
		String bestCat = "";
		BigDecimal highestProb = BigDecimal.ZERO;
		

		
		for(DocumentCategory curCat: trainingSet ){
			BigDecimal curProb = new BigDecimal(probsOfCats.get(curCat));
			
			
			
			
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
	}

}
