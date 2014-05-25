package bayes;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class DocumentCategory {

	String categoryName;
	ArrayList<Document> documents;
	HashMap<String, Integer> wordCounts;
	HashMap<String, Double> probWordAppearsInCatDocument;
	
	public DocumentCategory(File documentFolder) {
		wordCounts = new HashMap<>();
		documents = new ArrayList<Document>();
		probWordAppearsInCatDocument =  new HashMap<>();
		categoryName = extractCategoryName(documentFolder);
		
		
		//intialize documents
		File[] documentFiles = documentFolder.listFiles();
		for(int i =0; i< documentFiles.length; i++){
			Document temp = new Document(categoryName, documentFiles[i]);
			documents.add(temp);
			addDocumentWordCountToMasterCount(temp);
		}
		
		fillProbWordAppearsInCatDocument();
	}

	private void fillProbWordAppearsInCatDocument() {
		for(String word: wordCounts.keySet()){
			double numOfDocs = documents.size();
			double numOfDocsWordAppearsIn = 0;
			for(Document curDoc: documents){
				if(curDoc.contains(word)){
					numOfDocsWordAppearsIn++;
				}
			}
			probWordAppearsInCatDocument.put(word, numOfDocsWordAppearsIn/numOfDocs);
		}
	}

	private void addDocumentWordCountToMasterCount(Document temp) {
		HashMap<String, Integer> docCounts = temp.getWordCounts();
		
		for(String key: docCounts.keySet()){
			//if the master word already has the word
			if(wordCounts.containsKey(key)){
				int count = wordCounts.get(key);
				count+= docCounts.get(key);
				wordCounts.put(key, count);
			}
			//if the master word count does not have the word
			else{
				int count = docCounts.get(key);
				wordCounts.put(key, count);
			}
		}
		
	}

	private String extractCategoryName(File documentFolder) {
		String path = documentFolder.getAbsolutePath();
		int seperatorIndex = path.lastIndexOf(File.separator);
		return path.substring(seperatorIndex+1);
	}

	public String getCategoryName() {
		return categoryName;
	}
	
	
	public ArrayList<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(ArrayList<Document> documents) {
		this.documents = documents;
	}

	public HashMap<String, Integer> getWordCounts() {
		return wordCounts;
	}

	public void setWordCounts(HashMap<String, Integer> wordCounts) {
		this.wordCounts = wordCounts;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(categoryName+" ");
		for(String key: wordCounts.keySet()){
			int count = wordCounts.get(key);
			sb.append("\n\t"+ key +"  "+ count);
		}
		return sb.toString();
	}

	public boolean contains(String word) {
		return wordCounts.containsKey(word);
	}

	public double probabilityWordAppearsInCatDocument(String word) {
		if(probWordAppearsInCatDocument.containsKey(word)){
			return probWordAppearsInCatDocument.get(word);
		} 
		else{
			/*This is the smoothing thing that he mentioned in his email, I couldn't make it 0 or 1 for bernoullis because
			 * probabilities are multiplied together and would result in zero prob (couldn't use 1 because we do 1- what is returned by this method)*/
			return 0.5;
		}
	}

}
