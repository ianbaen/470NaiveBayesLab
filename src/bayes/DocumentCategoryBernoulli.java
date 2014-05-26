package bayes;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class DocumentCategoryBernoulli extends DocumentCategory{

	public DocumentCategoryBernoulli(File documentFolder) {
		super(documentFolder);
	}

	@Override
	protected void trainDocument(File file) {
		trainDocumentBernoulli(file);
	}
	
	private void trainDocumentBernoulli(File file){
		Scanner scanner = null;
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			System.out.println("Error occured with: "+ file.getAbsolutePath());
			System.out.println("Program will exit");
			e.printStackTrace();
			System.exit(0);
		}
		
		//wordCounts = new HashMap<>();
		
		Set<String> found = new HashSet<String>();
		found.addAll(Arrays.asList(commonWords));
		
		//goes though and updates word counts for each word, once per document.
		while(scanner.hasNext()){
			String key = scanner.next();
			key = trim(key);
			if(found.contains(key) || key.equals(""))
				continue;
			
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
	
	@Override
	protected double probWordGivenCategory(String word) {
		if(wordCounts.containsKey(word))
			return wordCounts.get(word)/(double)numOfDocs;
		else
			return 1;//smoothing
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
