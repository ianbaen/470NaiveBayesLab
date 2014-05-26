package bayes;
import java.util.ArrayList;

public class BayesModel {

	ArrayList<DocumentCategory> trainingSet;
	int totalDocs;
	
	public BayesModel(){
		trainingSet = null;
		totalDocs = 0;
	}
	
	public void setTrainingSet(ArrayList<DocumentCategory> trainingSet) {	
		this.trainingSet = trainingSet;	
		for(DocumentCategory d : trainingSet)
			totalDocs += d.getTotalDocs();		
	}
	
	public String predict(Document doc){
		
		String bestCat = "";
		double highestProb = Double.NEGATIVE_INFINITY;
		
		for(DocumentCategory curCat: trainingSet ){
			
			double curPrb = curCat.probCategoryGivenDoc(doc, totalDocs);
			if(highestProb < curPrb){
				highestProb = curPrb;
				bestCat = curCat.getCategoryName();
			}else{
				//System.out.println("not: "+curCat.getCategoryName());
			}
			
		}
		//System.out.println("document probability: "+highestProb + "best category:" + bestCat);
		return bestCat;
		
	}
}
