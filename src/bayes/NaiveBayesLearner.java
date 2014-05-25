package bayes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import bayes.models.BayesModel;
import bayes.models.BernoulliModel;

public class NaiveBayesLearner {

	ArrayList<DocumentCategory> trainCategories;
	ArrayList<DocumentCategory> testCategories;
	
	/** Given the pat to the training set, uploads all the info from the folder
	 * 
	 */
	public NaiveBayesLearner() {
		trainCategories = new ArrayList<DocumentCategory>();
		testCategories = new ArrayList<DocumentCategory>();
	}

	public void loadTrainingSet(String trainingSetPath) {
		loadFromDirectory(trainingSetPath, trainCategories);
	}
	
	
	public void loadTestSet(String testSetPath) {
		loadFromDirectory(testSetPath, testCategories);
	}
	
	public void loadFromDirectory(String path, ArrayList<DocumentCategory> cat){
		File folder = new File(path);
		if(!folder.exists()){
			System.out.println("It is saying that:"+ path+" does not exist. Is the path name correct?");
			System.out.println("Exiting program");
			System.exit(0);
		}
		
		File[] subFolders = folder.listFiles();
		
		for(int i =0; i< subFolders.length; i++){
			DocumentCategory temp = new DocumentCategory(subFolders[i]);
			cat.add(temp);
			System.out.println("\tDone uploading: "+ temp.getCategoryName());
		}
	}//end loadFromDirectory()

	public void runBernoulli(){
		BayesModel bMod = new BernoulliModel();
		
		double startTime = System.currentTimeMillis();
		System.out.println("Running the Multivariate Bernoulli model");
		double predictionAccuracy = predictTestSet(bMod);
		double elapsedTime = System.currentTimeMillis() - startTime; 
		System.out.println("Bernoulli predicted at "+ predictionAccuracy);
		System.out.println("Test took: "+ elapsedTime/1000.0 + " seconds");
		
	}

	
	/** Given a model (Bernoulli, multinomial, smoothed), it returns the prediction accrucary of the model on test set.
	 *  The test set being the test set in the 20news-bydate-test folder
	 * @param model
	 * @return
	 */
	public double predictTestSet(BayesModel model){
		model.setTrainingSet(trainCategories);
		double numClassified = 0;
		double numClassifiedCorrectly = 0;
		
		for(DocumentCategory testCat: testCategories){
			String correctCat = testCat.getCategoryName();
			for(Document testDoc: testCat.getDocuments()){
				String predictedCat = model.predict(testDoc);
				if(correctCat.equals(predictedCat)){
					numClassifiedCorrectly++;
					System.out.println("good: "+predictedCat+ ": "+testDoc.getId());
				}
				else{
					System.out.println("bad. actual: "+correctCat+"  Predicted: "+ predictedCat  );
				}
				numClassified++;
			}
		}
		//stuff with the confusion matrix
		
		
		return numClassifiedCorrectly/numClassified;
	}
	
	

}
