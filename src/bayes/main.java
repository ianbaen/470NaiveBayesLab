package bayes;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class main {

	public static void main(String[] args) {
		String pathTo20News = getHomeDirectory() + File.separator+ "20news-bydate";
		String trainingSetPath = pathTo20News + File.separator + "20news-bydate-train";
		String testSetPath = pathTo20News + File.separator + "20news-bydate-test";
		
		
		System.out.println("Uploading the 20 news group training set from: "+ trainingSetPath);
		NaiveBayesLearner bayes = new NaiveBayesLearner();
		bayes.loadTrainingSet(trainingSetPath);
		
		
		System.out.println("Uploading the 20 news group test set from: "+ testSetPath);
		bayes.loadTestSet(testSetPath);
		
		bayes.runBernoulli();
//		a multinomial model, and
//		a smoothed model.
		
	}

	private static String getHomeDirectory() {
		return System.getProperty("user.home");
	}

}
