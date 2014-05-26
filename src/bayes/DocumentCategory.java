package bayes;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public abstract class DocumentCategory {

	String categoryName;
	HashMap<String, Integer> wordCounts;
	int numOfDocs;
	
	public DocumentCategory(File documentFolder) {
		wordCounts = new HashMap<String, Integer>();
		categoryName = extractCategoryName(documentFolder);
		
		//intialize documents
		File[] documentFiles = documentFolder.listFiles();
		
		numOfDocs = documentFiles.length;
		for(int i =0; i< documentFiles.length; i++){
			trainDocument(documentFiles[i]);
		}
	}

	protected abstract void trainDocument(File file);
	
	/**This method does two things:
	 * 1)make the key lower case to avoid "RanDomWOrds" not being the same as "rANDOMWord"
	   2) trims off non-letter characters at the begining and ending of words (so that "healing." is the same as "healing")
	 * @param key
	 * @return
	 */
	public static String trim(String key) {
		String temp = key;
		
		//makes the key lower case
		key = key.toLowerCase();
		
		//trim off the none letter characters
		while(!key.equals("") && !Character.isLetter(key.charAt(0))){
			key = key.substring(1);
		}
		while(!key.equals("") && !Character.isLetter(key.charAt(key.length()-1))){
			key = key.substring(0, key.length()-1);
		}
		
		return key;
	}
	
	protected abstract double probWordGivenCategory(String word);
	
	private double probCategory(int totalDocs) {
		return (double)numOfDocs/totalDocs;
	}
	
	public double probCategoryGivenDoc(Document testDoc, int totalDocs) {
		double product = Math.log(1);
		 for(String w: testDoc.wordCounts.keySet())
			 product = product + Math.log(probWordGivenCategory(w));
		
		return (double)product + Math.log(probCategory(totalDocs));
	}


	private String extractCategoryName(File documentFolder) {
		String path = documentFolder.getAbsolutePath();
		int seperatorIndex = path.lastIndexOf(File.separator);
		return path.substring(seperatorIndex+1);
	}

	public String getCategoryName() {
		return categoryName;
	}
	public int getTotalDocs() {
		return numOfDocs;
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
	
	final static String[] commonWords = {"the","be","to","of","and",
											"a","in", "that","have",
											"I","it","for","not","on","with",
											"he","as","you","do","at",
											"this","but","his","by","from","they",
											"we","say","her","she","or","an","will",
											"my","one","all","would","there","their",
											"what","so","up","out","if","about","who",
											"get","which","go","me","when","make",
											"can","like","time","no","just","him","know",
											"take","person","into","year","your","good",
											"some","could","them","see","other","than",
											"then","now","look","only","come","its",
											"over","think","also","back","after",
											"use","two","how","our","work","first","well",
											"way","even","new","want","because","any",
											"these","give","day","most","us"};
	
}
