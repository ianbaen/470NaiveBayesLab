package bayes;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Document {

	
	HashMap<String, Integer> wordCounts;
	String category;
	String id;
	
	public Document(File file) {
		this.category = extractCategoryName(file);
		id = getFileId(file);
		Scanner scanner = null;
		try {
			scanner = new Scanner(file);
		} catch (FileNotFoundException e) {
			System.out.println("Error occured with: "+ file.getAbsolutePath());
			System.out.println("Program will exit");
			e.printStackTrace();
			System.exit(0);
		}
		
		wordCounts = new HashMap<>();
		
		while(scanner.hasNext()){
			String key = scanner.next();
			key = trim(key);
			
			if("".equals(key)){
				continue;
			}
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

	private String extractCategoryName(File documentFolder) {
		String path = documentFolder.getAbsolutePath();
		int seperatorIndex = path.lastIndexOf(File.separator);
		
		String catPath = path.substring(0, seperatorIndex);
		seperatorIndex = catPath.lastIndexOf(File.separator);
		
		return catPath.substring(seperatorIndex+1);
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




	private String getFileId(File file) {
		String path = file.getAbsolutePath();
		int seperatorIndex = path.lastIndexOf(File.separator);
		return path.substring(seperatorIndex+1);
	}

	
	
	public HashMap<String, Integer> getWordCounts() {
		return wordCounts;
	}




	public void setWordCounts(HashMap<String, Integer> wordCounts) {
		this.wordCounts = wordCounts;
	}




	public String getCategory() {
		return category;
	}




	public void setCategory(String category) {
		this.category = category;
	}




	public String getId() {
		return id;
	}




	public void setId(String id) {
		this.id = id;
	}




	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append(category+": "+id);
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
