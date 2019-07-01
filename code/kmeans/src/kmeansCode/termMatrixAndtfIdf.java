package hw2code;
import java.util.*;
import java.lang.*;
import java.io.*;


public class termMatrixAndtfIdf {
	
	private int[] numTermsInDoc;
	private int[] numTimesInAllDocs;
	private int[][] numTermsMatrix;
	private ArrayList<String> allTerms;
	private double[][] tfIdfMatrix;
	
	private double[][] scoresPerTerm;
	private ArrayList<ArrayList<String>> sortedTerms;
	
	public termMatrixAndtfIdf() {
		
	}
	
	
	public int[][] getNumTermsMatrix(ArrayList<HashMap<String,Integer>> termCounts,
			ArrayList<HashMap<String,Integer>> termCountsPerFol){
		
		
		getTotalTermCountsAllFol(termCountsPerFol);
		//getTotalTermCounts(termCounts);
		this.numTermsMatrix= new int[termCounts.size()][this.allTerms.size()];
		this.numTimesInAllDocs = new int[this.allTerms.size()];
		for(int i=0; i<this.allTerms.size(); i++) {
			this.numTimesInAllDocs[i]=0;
		}
		this.numTermsInDoc = new int[termCounts.size()];
		for(int i=0;i<termCounts.size();i++) {
			//this.numTermsInDoc[i]=0;
			
			//gets the number of all terms in each document. note that this
			//this is the number of terms in all documents
			this.numTermsInDoc[i]=termCounts.get(i).size();
			for(int j=0; j<this.allTerms.size(); j++) {
				HashMap<String,Integer> CurrentMap = termCounts.get(i);
				if(CurrentMap.containsKey(this.allTerms.get(j))){
					this.numTermsMatrix[i][j]=CurrentMap.get(this.allTerms.get(j));
					
					
					
					//OLD-Don't use this. Results are weird-finding how many times term is in document
					//this.numTermsInDoc[i]+=1;
					
					
					
					//number of times the term is in AllDocs
					this.numTimesInAllDocs[j]+=1;
				}else{
					this.numTermsMatrix[i][j]=0;
				}
			}
		}
		
		
		return this.numTermsMatrix;
	}
	
	//First Get Term Counts and them filter them out by size
	private void getTotalTermCounts(ArrayList<HashMap<String,Integer>> termCounts){
		
		
		
		Set<String> totalTermCounts = new TreeSet<String>();
		for(int i=0; i < termCounts.size();i++) {	
			totalTermCounts.addAll(termCounts.get(i).keySet());
		}
		
		ArrayList<String> allTermsList = new ArrayList<String>(totalTermCounts);
		//this.allTerms= new ArrayList<String>(totalTermCounts);
		
		
	 	HashMap<String, Integer> allTermsCounts = new HashMap<String,Integer>();
	 	
	 	
	    String currentTerm; int docCount; int currentCount;
	 	for(int termNum=0;termNum<allTermsList.size();termNum++) {
	 		currentTerm=allTermsList.get(termNum);
	 		allTermsCounts.put(currentTerm, 0);
	 		for(int docNum=0; docNum<termCounts.size();docNum++) {
	 			HashMap<String,Integer> currentDocTermCount=termCounts.get(docNum);
		    	if(currentDocTermCount.containsKey(currentTerm)) {
		    		//getting the doc and the total counts for the terms
		    		docCount=currentDocTermCount.get(currentTerm);
		    		currentCount=allTermsCounts.get(currentTerm);
		    		//replace with the sum of the two
		    		allTermsCounts.replace(currentTerm, currentCount+docCount);

		    	}
	 		}
	 	}
	 	
	 	
	 	//removes terms that occur fewer than n times among all documents
	    Iterator<String> termIterator=allTermsList.iterator();
	    while(termIterator.hasNext()) {
	    	currentTerm=termIterator.next();
	    	if(allTermsCounts.get(currentTerm) < 10) {
	    		termIterator.remove();
	    		
	    	}
	    }
	    this.allTerms=allTermsList;
	    //String currentTerm;
	    
	    
	    
	    /*while(termIterator.hasNext()) {
	    	Integer currentCount;
	    	currentTerm=termIterator.next();
    		if((!currentTerm.contains(".")) && (!currentTerm.contains(","))){
		    	if(termCount.containsKey(currentTerm)) {
			    		currentCount=termCount.get(currentTerm);
			    		termCount.replace(currentTerm, currentCount+1);
	
		    	}else {
		    		termCount.put(currentTerm, 1);
		    	}
    		}
	    	
	    }*/
		
		
		
	}
	
	private void getTotalTermCountsAllFol(ArrayList<HashMap<String,Integer>> termCountsPerFold) {
		
		
		
		Set<String> totalTermCounts = new TreeSet<String>();
		for(int i=0; i < termCountsPerFold.size();i++) {	
			totalTermCounts.addAll(termCountsPerFold.get(i).keySet());
		}
		
		ArrayList<String> allTermsList = new ArrayList<String>(totalTermCounts);
		//this.allTerms= new ArrayList<String>(totalTermCounts);
		
		
	 	HashMap<String, Integer> allTermsCounts = new HashMap<String,Integer>();
	 	
	 	
	    String currentTerm; int docCount; int currentCount;
	 	for(int termNum=0;termNum<allTermsList.size();termNum++) {
	 		currentTerm=allTermsList.get(termNum);
	 		allTermsCounts.put(currentTerm, 0);
	 		for(int docNum=0; docNum<termCountsPerFold.size();docNum++) {
	 			HashMap<String,Integer> currentDocTermCount=termCountsPerFold.get(docNum);
		    	if(currentDocTermCount.containsKey(currentTerm)) {
		    		//getting the doc and the total counts for the terms
		    		docCount=currentDocTermCount.get(currentTerm);
		    		currentCount=allTermsCounts.get(currentTerm);
		    		//replace with the sum of the two
		    		allTermsCounts.replace(currentTerm, currentCount+docCount);

		    	}
	 		}
	 	}
	 	
	    this.allTerms=allTermsList;
	    //String currentTerm;
	    
	    
	    
	    /*while(termIterator.hasNext()) {
	    	Integer currentCount;
	    	currentTerm=termIterator.next();
    		if((!currentTerm.contains(".")) && (!currentTerm.contains(","))){
		    	if(termCount.containsKey(currentTerm)) {
			    		currentCount=termCount.get(currentTerm);
			    		termCount.replace(currentTerm, currentCount+1);
	
		    	}else {
		    		termCount.put(currentTerm, 1);
		    	}
    		}
	    	
	    }*/
	}

	
	
	
	//this function creates the tf-idf Matrix, it does so by for-looping through all the  terms
	public double[][] getTfIdfMatrix(int[][] numTermsMatrix) {
		this.tfIdfMatrix = new double[numTermsMatrix.length][this.allTerms.size()];

		//filling in tfIdfMatrix
		//remember i is document and j is term
		for(int i=0; i<numTermsMatrix.length; i++) {
			for(int j=0;j<allTerms.size();j++) {
				
				//calculating tfIdfValue, with the number of termsinTheMatrix/number of terms in the document
				this.tfIdfMatrix[i][j]=((double) numTermsMatrix[i][j]/this.numTermsInDoc[i])*
						Math.log((double)numTermsMatrix.length/this.numTimesInAllDocs[j]);
			}
			
		}
		return this.tfIdfMatrix;
	}
	
	//getting topics and putting it into topics.txt (this needs to be put into the topicsPath)
	public void getTopics(double[][] tfIdfMatrix, String topicsPath,ArrayList<Integer> folderNumPerDoc) throws Exception{
		
		ArrayList<Integer> folderNums= new ArrayList<Integer>();
		for(int folNum=0;folNum<folderNumPerDoc.size(); folNum++) {
			int thisFolNum=folderNumPerDoc.get(folNum);
			if(!folderNums.contains(thisFolNum)) {
				folderNums.add(thisFolNum);
			}
		}
		
		
		this.sortedTerms = new ArrayList<ArrayList<String>>();
		this.scoresPerTerm=getTfIdfPerTerm(tfIdfMatrix,folderNums,folderNumPerDoc);
		double[][] sortedTermScores=sortTfIdfScores(folderNums.size());
		
		
		BufferedWriter br = new BufferedWriter(new FileWriter(topicsPath));
		StringBuilder sb = new StringBuilder();
		
		//writes topics per document
		for(int docNum=0; docNum <sortedTermScores.length;docNum++) {
			sb.append(String.format("Document %d:\n", docNum));
			for(int termNum=0; termNum<sortedTermScores[docNum].length;termNum++) {
				sb.append(sortedTermScores[docNum][termNum]);
				sb.append(": ");
				sb.append(this.sortedTerms.get(docNum).get(termNum));
				sb.append("\n");
			}
			sb.append("\n");
		}
		br.write(sb.toString());
		br.close();
		
		
	}
	
	//summing up the tfIdf Scores per term per document.
	//the row number of this is the number of documents and the column number are the number of
	//terms
	//Therefore each element is the sum of the tf-idf scores for a term for a document
	private double[][] getTfIdfPerTerm(double[][] tfIdfMatrix, ArrayList<Integer> folderNums,
			ArrayList<Integer> folderNumPerDoc) {
		
		//generates tfIdfScores per term by summing up through double for loop
		double[][] tfIdfScoresPerTerm = new double[folderNums.size()][tfIdfMatrix[0].length];
		
		//initializing the term scores per document
		int currFolder;
		for(int currFolderNum=0; currFolderNum<folderNums.size(); currFolderNum++) {
			currFolder=folderNums.get(currFolderNum);
			for(int term=0;term<tfIdfScoresPerTerm[0].length;term++) {
				tfIdfScoresPerTerm[currFolder][term]=0;
			}
		}	
				
		
		//get the currFolder per doc and then add the tf-idf value for the term in the doc to the
		//total tf idf scores
		for(int docNum=0; docNum<tfIdfMatrix.length; docNum++) {
			for(int term=0;term<tfIdfScoresPerTerm[0].length;term++) {
			//currFolder=folderNums.get(folderNumPerDoc.get(docNum));
				currFolder=folderNumPerDoc.get(docNum);
			//tfIdfScoresPerTerm[currFolder][term]=0;	
				tfIdfScoresPerTerm[currFolder][term]+=tfIdfMatrix[docNum][term];
			}
		}
		
		return tfIdfScoresPerTerm;

	}
	

	//sorts terms using insertion sort
	private double[][] sortTfIdfScores(int numberOfFolders) {
		//initializing the sortedTermsArrayList
		
		double[][] sortedMatrix =new double[this.scoresPerTerm.length][this.scoresPerTerm[0].length];
		for(int currFolder=0; currFolder<numberOfFolders;currFolder++) {
			ArrayList<String> currSortedTerms = new ArrayList<String>();
						
			//time for the insertion sort
			for(int i=0;i<sortedMatrix[currFolder].length;i++) {
				
				//putting in the value at the ith position
				sortedMatrix[currFolder][i]=this.scoresPerTerm[currFolder][i];
				currSortedTerms.add(this.allTerms.get(i));
				for(int j=0; j<i;j++) {
					//making the comparison from 0 to i-1
					if(sortedMatrix[currFolder][i]>sortedMatrix[currFolder][j]) {
						
						//get temp value of jth element
						double tempDouble=sortedMatrix[currFolder][j];
						String tempString=currSortedTerms.get(j);
						
						//set jth elements of matrix to ith element
						sortedMatrix[currFolder][j]=sortedMatrix[currFolder][i];
						currSortedTerms.set(j, currSortedTerms.get(i));
						
						//put the old jth value in to the ith spot
						sortedMatrix[currFolder][i]=tempDouble;
						currSortedTerms.set(i, tempString);
						
					}
				}
			}
			this.sortedTerms.add(currSortedTerms);
		}
		return sortedMatrix;
	}
	
	
	
}
