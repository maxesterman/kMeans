package hw2code;

import java.io.*;


public class enteringFile {
	
	public static void main(String[] args) throws Exception{
		
		
		//the rootdirectory
		String rootFolder="/Users/maxsterman/Downloads/dataset_3/data/";
		
		
		//The names of the folders
		String[] TopicsFolders= {"C1","C4","C7"};
		
		
		//Path to Save Topics.txt
		String outputPath="/Users/maxsterman/Downloads/HW2 Output/topics.txt";
		
		//boolean for using cosine similarity
		boolean cosineSimilarity=true;
		
		//k
		int k=3;
		
		nlpconstruct nlpCons = new nlpconstruct();
		nlpCons.main(TopicsFolders, rootFolder, outputPath,cosineSimilarity,k);
		//nlpCons.main(pathsToUse, rootFolder, outputPath,cosineSimilarity,k);
		
		
		

	}

}
