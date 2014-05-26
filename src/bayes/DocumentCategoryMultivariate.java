package bayes;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class DocumentCategoryMultivariate extends DocumentCategory{

	public DocumentCategoryMultivariate(File documentFolder) {
		super(documentFolder);
	}

	@Override
	protected void trainDocument(File file) {
		trainDocumentMultivariate(file);
	}
	
	
	private void trainDocumentMultivariate(File file){
		Scanner scanner = null;
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			System.out.println("Error occured with: "+ file.getAbsolutePath());
			System.out.println("Program will exit");
			e.printStackTrace();
			System.exit(0);
		}
		//goes though and updates word counts for each word, multiple per document.
		while(scanner.hasNext()){
			String key = scanner.next();
			key = trim(key);
			if(commonWords.contains(key) || key.equals(""))
				continue;
			
			numOfWords++;
			if(wordCounts.containsKey(key)){
				int count = wordCounts.get(key);
				count++;
				wordCounts.put(key, count);
			}
			else{
				wordCounts.put(key, 1);
			}
		}
	}
	
	@Override
	protected double probWordGivenCategory(String word) {
		if(wordCounts.containsKey(word))
			return wordCounts.get(word)/(double)numOfWords;
		else
			return .00000001;//smoothing
	}

	public String getCategoryName() {
		return categoryName;
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

	
}
