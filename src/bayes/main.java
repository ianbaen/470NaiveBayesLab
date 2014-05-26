package bayes;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class main {

	public static void main(String[] args) {
		String pathTo20News = getHomeDirectory() + File.separator+ "20news-bydate";
		String trainingSetPath = pathTo20News + File.separator + "20news-bydate-train";
		String testSetPath = pathTo20News + File.separator + "20news-bydate-test";
		
		NaiveBayesLearner bayes = new NaiveBayesLearner();
		
		System.out.println("Uploading the 20 news group test set from: "+ testSetPath);
		bayes.loadTestSet(testSetPath);
		System.out.println("Uploaded the test set.");
		
		System.out.println("Uploading the 20 news group training set from: "+ trainingSetPath);
		bayes.loadTrainingSet(trainingSetPath, Type.MULTIVARIATE);//using multivariate works
		System.out.println("Uploaded Baseline training set.");
		System.out.println("Running Baseline");
		bayes.runBaseline();
		
		
		System.out.println("Uploading the 20 news group training set from: "+ trainingSetPath);
		bayes.loadTrainingSet(trainingSetPath, Type.BERNOULLI);
		System.out.println("Uploaded Bernoulli training set.");
		
		System.out.println("Running Bernoulli");
		bayes.runPredictionSet();
		
		System.out.println("Uploading the 20 news group training set from: "+ trainingSetPath);
		bayes.loadTrainingSet(trainingSetPath, Type.MULTIVARIATE);
		System.out.println("Uploaded Multivariate training set.");
		System.out.println("Running Multivariate");
		bayes.runPredictionSet();
		
//		a multinomial model, and
//		a smoothed model.
		
	}

	private static String getHomeDirectory() {
		return System.getProperty("user.home");
	}

	
	

}
