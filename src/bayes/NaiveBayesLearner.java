package bayes;

import java.io.File;
import java.util.ArrayList;

public class NaiveBayesLearner {

	ArrayList<DocumentCategory> trainCategories;
	ArrayList<Document> testDocuments;
	ConfusionMatrix confMatrix;
	
	/** Given the pat to the training set, uploads all the info from the folder
	 * 
	 */
	public NaiveBayesLearner() {
		trainCategories = new ArrayList<DocumentCategory>();
		testDocuments = new ArrayList<Document>();
	}

	public void loadTrainingSet(String trainingSetPath, Type type){
		trainCategories = new ArrayList<DocumentCategory>();
		loadCategoryFromDirectory(trainingSetPath, trainCategories, type);
		confMatrix = new ConfusionMatrix(trainCategories);
		
	}
	
	public void loadTestSet(String testSetPath) {
		loadDocumentFromDirectory(testSetPath, testDocuments);
	}
	
	public void loadCategoryFromDirectory(String path, ArrayList<DocumentCategory> cat, Type type){
		File folder = new File(path);
		if(!folder.exists()){
			System.out.println("It is saying that:"+ path+" does not exist. Is the path name correct?");
			System.out.println("Exiting program");
			System.exit(0);
		}
		
		File[] subFolders = folder.listFiles();
		
		for(int i =0; i< subFolders.length; i++){
			DocumentCategory temp = DocumentCategory.makeNew(type, subFolders[i]);
			cat.add(temp);
		}
	}//end loadFromDirectory()
	
	public void loadDocumentFromDirectory(String path, ArrayList<Document> list){
		File folder = new File(path);
		if(!folder.exists()){
			System.out.println("It is saying that:"+ path+" does not exist. Is the path name correct?");
			System.out.println("Exiting program");
			System.exit(0);
		}
		
		File[] subFolders = folder.listFiles();
		
		for(int i =0; i< subFolders.length; i++){
			File f = subFolders[i];
			File[] docs = f.listFiles();
			for(int j =0; j<docs.length; j++){
				Document temp = new Document(docs[j]);
				list.add(temp);
			}
		}
	}//end loadFromDirectory()


	public void runPredictionSet(){
		BayesModel bMod = new BayesModel();
		
		double startTime = System.currentTimeMillis();
		System.out.println("Running the prediction set");
		double predictionAccuracy = predictTestSet(bMod);
		double elapsedTime = System.currentTimeMillis() - startTime; 
		System.out.println("Predicted at "+ predictionAccuracy);
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
		
		for(Document testDoc: testDocuments){
				String correctCat = testDoc.getCategory();
				String predictedCat = model.predict(testDoc);
				if(correctCat.equals(predictedCat)){
					numClassifiedCorrectly++;
				}
				else{}
				confMatrix.increment(correctCat, predictedCat);
				numClassified++;
			}
		
		//stuff with the confusion matrix
		
		System.out.println(confMatrix);
		return numClassifiedCorrectly/numClassified;
	}
	
	

}
