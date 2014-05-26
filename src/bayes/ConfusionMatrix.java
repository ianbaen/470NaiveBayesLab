package bayes;

import java.util.ArrayList;

public class ConfusionMatrix {

	ArrayList<String> docCategories;
	//see comment on incrementPrediction for how the cooridantes map to the matrix
	int[][] matrix;
	

	public ConfusionMatrix(ArrayList<DocumentCategory> trainingSet) {
		docCategories = new ArrayList<String>();
		for(int i =0; i< trainingSet.size(); i++){
			docCategories.add(trainingSet.get(i).getCategoryName());
		}
		
		matrix = new int[docCategories.size()][docCategories.size()];
		for(int i =0; i < docCategories.size(); i++){
			for(int j =0; j < docCategories.size(); j++){
				matrix[i][j] = 0;
			}
		}
	}
	
	
	/* I tired to do this by row column form. [3][1] is the 3rd row down (the 3rd true category)
	 * while [1] is the 1st column in the predicted category 
	 * 
	 * */
	public void increment( String actual, String predicted ){
		int truthIndex = docCategories.indexOf(actual);
		int predictionIndex = docCategories.indexOf(predicted);
		matrix[truthIndex][predictionIndex]++;
	}
	
	public String toString(){
		StringBuilder sb =  new StringBuilder();
		sb.append("\t");
		for(String curCat:docCategories){
			sb.append(curCat+"\t");
		}
		sb.append("\n");
		for(int i =0; i < docCategories.size(); i++){
			sb.append(docCategories.get(i) + "\t");
			for(int j =0; j < docCategories.size(); j++){
				sb.append(matrix[i][j]+"\t");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	
	
}
