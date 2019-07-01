package hw2code;

import java.io.*;
import java.util.*;
import java.lang.*;



public class SimilarityMeasures {
	
	public SimilarityMeasures() {
		
	}
	
	public static double calcEuclideanDistance(double[][] tdfIdfMatrix, 
			double[][] centroidVector,int currentDoc, int currentCentroid) {
		//initialize sum of square distances
		double euclidDist=0.0;
		for(int i=0; i<centroidVector[currentCentroid].length;i++) {
			//calculating the square the old fashion way
			euclidDist+=(tdfIdfMatrix[currentDoc][i]-centroidVector[currentCentroid][i])*
					(tdfIdfMatrix[currentDoc][i]-centroidVector[currentCentroid][i]);
		}
		
		//take the sum of square distances to get 
		return Math.sqrt(euclidDist);
	}
	
	
	public static double cosineSimilarity(double[][] tdfIdfMatrix, 
			double[][] centroidVector,int currentDoc, int currentCentroid) {
		
		
		//calculate the document's and centroid's  squared norms 
		double docNorm=calculateSqNorm(tdfIdfMatrix,currentDoc);
		double centroidNorm=calculateSqNorm(centroidVector,currentCentroid);
		
		//calculate the interaction value
		double interactionValue=calculateInteraction(tdfIdfMatrix,
				centroidVector,currentDoc,currentCentroid);
		
		//cosine simularity=Interaction Value/(product of doc and centroid Norms)7
		return (interactionValue)/(docNorm*centroidNorm);
		
		
	}
	
	public static double calculateSqNorm(double[][] normToCalc, int rowToCalc) {
		//initialize the sqNorm
		double sqNorm=0.0;
		
		for(int i=0; i<normToCalc[rowToCalc].length;i++) {
			//calculating the sum of squares
			sqNorm+=(normToCalc[rowToCalc][i]*normToCalc[rowToCalc][i]);
		}	
			
		//return the square root of the squaredNorm
		return Math.sqrt(sqNorm);
	}
	
	
	
	//calculating the interaction value, assuming that 
	public static double calculateInteraction(double[][] tdfIdfMatrix, 
			double[][] centroidVector,int currentDoc, int currentCentroid) {
		
		
		double interaction=0.0;
		for(int i=0; i<centroidVector[currentCentroid].length;i++) {
			//calculating the interaction the old fashion way
			interaction+=(tdfIdfMatrix[currentDoc][i]*centroidVector[currentCentroid][i]);
		}
		return interaction;
	}

	
	
}
