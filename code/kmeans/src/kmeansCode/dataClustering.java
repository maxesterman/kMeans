package hw2code;

import java.io.*;
import java.util.*;
import java.lang.*;

public class dataClustering {
	
	public dataClustering() {
		
	}
	
	public int[] usedInitValues;
	
	public static HashMap<Integer,Integer> kMeansClusteringInit(double[][] tfIdfMatrix, 
			int k, int[] startingCentroids, boolean doCosSimilarity) throws Exception{
		
		
		//document clusters are best as a hashMap (at least i find)
		HashMap<Integer,Integer> documentClusters = new HashMap<Integer,Integer>();
		//initializing the documentCluster Values at -1
		for(int docNum=0; docNum<tfIdfMatrix.length;docNum++) {
			documentClusters.put(docNum, -1);
		}
		

		
		int numIterations=10000;
		double[][] theMeans=new double[k][tfIdfMatrix[0].length];
		
		Random randInt = new Random();
		int currentCentroidNum;
		
		//initialization of the array to random centroids
		
		ArrayList<Integer> centroidValues= new ArrayList<Integer>();
		
		int[] initCentroids=generateInitClusters(tfIdfMatrix,k, doCosSimilarity);
		
		for(int i=0;i<k; i++) {
			//getting the random initialized centroid values, which can be between 0 and numDocs-1
			//currentCentroidNum=startingCentroids[i];
			currentCentroidNum=initCentroids[i];
			//System.out.println(String.format("Centroid %d, Initialized Value %d", i, currentCentroidNum));
			//if(i != 0) {
				
				//filling in the initial values, which are the randomly chosen centroids
				for(int j=0;j<tfIdfMatrix[i].length; j++) {
					theMeans[i][j]=tfIdfMatrix[currentCentroidNum][j];
					
				}
			//}
			documentClusters.replace(currentCentroidNum, i);
		}
		
		SimilarityMeasures similarityMeasuresObj = new SimilarityMeasures();
		
		//creating the clusters for the previous set of clusters
		HashMap<Integer,Integer> FormerClusters=new HashMap<Integer,Integer>();
		for(int i=0; i< documentClusters.size();i++) {
			FormerClusters.put(i,documentClusters.get(i));
		}
		
		
		boolean continueIterations=true;
		//going through a number of iterations of k-means
		int iteration=0; 
		while(continueIterations){
		//for(int iteration=0;iteration<numIterations;iteration++) {
			for(int i=0; i< documentClusters.size();i++) {
				FormerClusters.replace(i,documentClusters.get(i));
			}
			for(int docNum=0; docNum<tfIdfMatrix.length;docNum++) {

				
				//calculate the similarity measure and see if it is the smallest one.
				//if so, it is the smallest cluster.
				int smallestCluster=0; double smallestClusterSimilarity=0.0; int randCluster=0;
				double similarityValue=-1.0;
				double greqOrLess=1.0;
				ArrayList<Integer> sameTopValues= new ArrayList<Integer>();
				
				
				for(int centroid=0; centroid<k; centroid++) {

					if(doCosSimilarity) {
						similarityValue=similarityMeasuresObj.cosineSimilarity(tfIdfMatrix,
							theMeans,docNum,centroid);
					}else {
						similarityValue=similarityMeasuresObj.calcEuclideanDistance(tfIdfMatrix,
								theMeans,docNum,centroid);
						greqOrLess=-1.0;
					}
					
					//System.out.println(String.format("Similarity Value for cluster, %d: %2.3f for doc %d", 
					//		centroid,similarityValue, docNum));
					
					//finding the most similar
					if(centroid ==0) {
						smallestClusterSimilarity=similarityValue;
						smallestCluster=centroid;
						sameTopValues.add(centroid);
					}else {
						if((greqOrLess*similarityValue) > (greqOrLess*smallestClusterSimilarity)) {
							smallestCluster=centroid;
							smallestClusterSimilarity=similarityValue;
							
							//clearing out the sameTopValues to make sure 
							//to not mess with this process and adds the newest one
							//just in case the top value actually happens
							sameTopValues.clear();
							sameTopValues.add(centroid);
						}else if(similarityValue == smallestClusterSimilarity) {
							sameTopValues.add(centroid);
						}

					
					}
					
				}
				
				//if there is more than one top value, pick the centroid with the highest initial value
				if(sameTopValues.size()>1) {
					//smallestCluster=sameTopValues.get(randCluster % k);
					//randCluster+=1;
					
					smallestCluster=sameTopValues.get(randInt.nextInt(sameTopValues.size()));
					//System.out.println(String.format("Randomization happens with %d", docNum));
				}
				
				
				//end of forloop, we know what smallest distance is, put it in the hashmap
				documentClusters.replace(docNum, smallestCluster);
				
			}
			
			//checks to see if the two clusters are the same. if so, it ends the iterations
			//and moves on
			if(documentClusters.equals(FormerClusters)) {
				continueIterations=false;
			}



			//end of iteration. time to recalculate the means
			theMeans=recalculateMeans(tfIdfMatrix,theMeans,documentClusters,k);
			//System.out.println(String.format("iteration: %d", iteration));
			iteration+=1;
			if(iteration>=10000) {
				continueIterations=false;
			}
		}
		
		
		//System.out.println(String.format("Took %d iterations", iteration));
		

		
		
		return documentClusters;
			
	}
	
	
	public static HashMap<Integer,Integer> kMeansClustering(double[][] tfIdfMatrix, int k) throws Exception{
		
		BufferedWriter br = new BufferedWriter(new FileWriter("/Users/maxsterman/Downloads/HW2 Output/kmeans_iterations.csv"));
		StringBuilder sb = new StringBuilder();
		
		
		
		//document clusters are best as a hashMap (at least i find)
		HashMap<Integer,Integer> documentClusters = new HashMap<Integer,Integer>();
		//initializing the documentCluster Values with clusters of -1
		for(int docNum=0; docNum<tfIdfMatrix.length;docNum++) {
			documentClusters.put(docNum, -1);
		}
		
		BufferedWriter br2 = new BufferedWriter(new FileWriter("/Users/maxsterman/Downloads/HW2 Output/cosine_Similarities.csv"));		
		StringBuilder sb2 = new StringBuilder();
		
		int numIterations=10000;
		double[][] theMeans=new double[k][tfIdfMatrix[0].length];
		
		Random randInt = new Random();
		int currentCentroidNum;
		List<Integer> currRandomValues= new ArrayList<Integer>();
		
		//initialization of the array to random centroids
		for(int i=0;i<k; i++) {
			//getting the random initialized centroid values, which can be between 0 and numDocs-1
			currentCentroidNum=randInt.nextInt(tfIdfMatrix.length);
			while(currRandomValues.contains(currentCentroidNum)) {
				currentCentroidNum=randInt.nextInt(tfIdfMatrix.length);
			}
			currRandomValues.add(currentCentroidNum);
			System.out.println(String.format("Centroid %d, Initialized Value %d", i, currentCentroidNum));
			
			//filling in the initial values, which are the randomly chosen centroids
			for(int j=0;j<tfIdfMatrix[0].length; j++) {
				theMeans[i][j]=tfIdfMatrix[currentCentroidNum][j];
			}
			
		}
		
		SimilarityMeasures similarityMeasuresObj = new SimilarityMeasures();
		
		//going through a number of iterations of k-means
		for(int iteration=0;iteration<numIterations;iteration++) {
			if(iteration % 10000 == 1) {
				System.out.println(String.format("iteration %d",iteration));
				System.out.println(documentClusters);
			}
			
			if(iteration == 99999) {
				System.out.println(String.format("iteration %d",iteration));
				System.out.println(documentClusters);
			}
			
			
			for(int docNum=0; docNum<tfIdfMatrix.length;docNum++) {
				if(docNum ==0) {
					sb2.append(String.format("iteration: %d", iteration));
					sb2.append(",");
					
				}
				
				//calculate the similarity measure and see if it is the smallest one.
				//if so, it is the smallest cluster.
				int smallestCluster=0; double smallestClusterSimilarity=0.0; 
				double similarityValue;
				ArrayList<Integer> sameTopValues= new ArrayList<Integer>();
				
				
				for(int centroid=0; centroid<k; centroid++) {
					

					
					similarityValue=similarityMeasuresObj.cosineSimilarity(tfIdfMatrix,
							theMeans,docNum,centroid)-(0.3)*similarityMeasuresObj.calcEuclideanDistance(tfIdfMatrix,
							theMeans,docNum,centroid);
					
					if(docNum == 0) {
						sb2.append(similarityValue);
						sb2.append(",");
					}
					
					//finding the most similar
					if(centroid ==0) {
						smallestClusterSimilarity=similarityValue;
						sameTopValues.add(centroid);
					}else {
						if(similarityValue > smallestClusterSimilarity) {
							smallestCluster=centroid;
							smallestClusterSimilarity=similarityValue;
							
							//clearing out the sameTopValues to make sure 
							//to not mess with this process and adds the newest one
							//just in case the top value actually happens
							sameTopValues.clear();
							sameTopValues.add(centroid);
						}else if(similarityValue == smallestClusterSimilarity) {
							sameTopValues.add(centroid);
						}
					}
					
				}
				
				if(sameTopValues.size()>1) {
					smallestCluster=sameTopValues.get(randInt.nextInt(sameTopValues.size()));
					System.out.print(String.format("happened at %d",iteration));
				}
				
				if(iteration == 89999) {
					System.out.println(String.format("iteration %d before clustering replacement of doc %d",
							iteration, docNum));
					System.out.println(documentClusters);
				}
				
				
				//end of forloop, we know what smallest distance is, put it in the hashmap
				documentClusters.replace(docNum, smallestCluster);
				
			}
			if(iteration == 89999) {
				System.out.println(String.format("iteration %d after clustering replacement",iteration));
				System.out.println(documentClusters);
				
			}
			sb.append(String.format("iteration: %d", iteration));
			sb.append(",");
			for(int colNum=0;colNum<documentClusters.size();colNum++) {
				sb.append(documentClusters.get(colNum));
				sb.append(",");
			}
			
			sb2.append("\n");
			
			sb.append("\n");


			
			//end of iteration. time to recalculate the means
			theMeans=recalculateMeans(tfIdfMatrix,theMeans,documentClusters,k);
			
		}
		
		
		br2.write(sb2.toString());
		br2.close();
		
		br.write(sb.toString());
		br.close();

		
		System.out.println("just before being returns");
		System.out.println(documentClusters);
		System.out.println("just after returns");
		return documentClusters;
			
	}
	
	public static double[][] recalculateMeans(double[][] tfidfMatrix, double[][] theMeans, 
			HashMap<Integer,Integer> documentClusters, int k){
		
		//initializing how many documents in the cluster
		double[] numDocsInCluster = new double[k];
		
		//resetting the cluster and the number of documents in the cluster back to 0
		for(int centroid=0; centroid<theMeans.length;centroid++) {
			//intializing number of docs in the cluster
			numDocsInCluster[centroid]=0.0;
		}
		
		//iterating through documents to figure out which cluster each of them are in
		int currentCluster;
		for(int docNum=0;docNum<tfidfMatrix.length;docNum++) {
			//finding which cluster the document is in. Noting that there is one more
			//document in that cluster.
			currentCluster=documentClusters.get(docNum);
			
			//if 0, it hasn't been used, so summed value needs to be initialized
			//important, because if a point has no documents, then the cluster becomes the zero 
			//vector otherwise
			if(numDocsInCluster[currentCluster]==0.0) {
				for(int col=0; col<theMeans[currentCluster].length;col++) {
					theMeans[currentCluster][col]=0.0;
				}
			}
			
			//adding 1 to the number of documents in the cluster
			numDocsInCluster[currentCluster]+=1.0;
			
			
			//adding the tfidfValues to the Means matrix
			for(int col=0; col<tfidfMatrix[docNum].length;col++) {
				theMeans[currentCluster][col]+=tfidfMatrix[docNum][col];
			}
		}
		
		//computing the averages for each mean dimension. this means diving theMeans value
		//by the number of documents in the cluster.
		for(int centroid=0; centroid<k;centroid++) {
			for(int col=0; col<theMeans[centroid].length;col++) {
				if(numDocsInCluster[centroid]>0.0) {
					theMeans[centroid][col]=theMeans[centroid][col]/numDocsInCluster[centroid];
				}
			}
		}
		
		

		
		return theMeans;
		
	}

	
	
	private static int[] generateInitClusters(double[][] tfIdfMatrix, int k, boolean doCosSimilarity) {
		
		int[] initClusters = new int[k];
		ArrayList<Integer> initClusterSofar=new ArrayList<Integer>();
		Random randomIntGen = new Random();
		double[][] tfForCluster = new double[k][tfIdfMatrix[0].length];
		double[] cosineSimilarityList = new double[tfIdfMatrix.length];
		
		for(int docNum=0;docNum<tfIdfMatrix.length;docNum++) { cosineSimilarityList[docNum]=0.0;}
		
		
		SimilarityMeasures simMeasObj = new SimilarityMeasures();
		
		for(int i=0; i<k; i++) {
			int newCentroid=-1;
			if(i==0) {Integer numGen=randomIntGen.nextInt(tfIdfMatrix.length);
				initClusterSofar.add(numGen); initClusters[i]=numGen; 
				newCentroid=numGen;
			}else {
				int[] eligibleDocs = new int[tfIdfMatrix.length-initClusterSofar.size()];
				
				int currentIndex=0;
				for(int docNum=0;docNum<tfIdfMatrix.length;docNum++) {
					
					//checks if the cluster contains the specific document,
					//if it does not, do not include it in the eligible docs
					//if it does, then calculate the cosine similarity and 
					//and check if it is the greatest one.
					if(!initClusterSofar.contains(docNum)){
						double greqOrLe=1.0;
						eligibleDocs[currentIndex]=docNum; currentIndex+=1;
						double cosSimilarityValue =simMeasObj.cosineSimilarity(tfIdfMatrix, 
								tfForCluster, docNum, i-1);
						if(!doCosSimilarity) {
							cosSimilarityValue =simMeasObj.calcEuclideanDistance(tfIdfMatrix, 
									tfForCluster, docNum, i-1);
							greqOrLe=-1.0;
						}
						
						
						if((greqOrLe*cosSimilarityValue) > (greqOrLe*cosineSimilarityList[docNum])) {
							cosineSimilarityList[docNum]=cosSimilarityValue;
						}
					}
				}
				int bestInitCluster=findInitalCluster(tfIdfMatrix, cosineSimilarityList,eligibleDocs,
						doCosSimilarity);
				initClusters[i]=bestInitCluster; initClusterSofar.add(bestInitCluster); 
				newCentroid=bestInitCluster;
			}
			for(int featNum=0; featNum<tfForCluster[i].length;featNum++) {

				tfForCluster[i][featNum]=tfIdfMatrix[initClusters[i]][featNum];
			}
			
			
		}

		
		
		return initClusters;
		

		
	}
	
	//find the clusters
	private static int findInitalCluster(double[][] tfIdfMatrix, double[] cosineSimilaritiesList,
			int[] eligibleDocs, boolean doCosSimilarity){
		
		Random RandIntGen = new Random();
		int numEligDocs = eligibleDocs.length;
		

		
		double[] inverseCosineSimilarities= new double[numEligDocs];
		
		
		for(int i=0; i<numEligDocs;i++) {
			int currDocNum=eligibleDocs[i];
			//finding the  Distance^2. notice its 1-because we want far away distances
			inverseCosineSimilarities[i]=(1.0-cosineSimilaritiesList[currDocNum])*
					(1.0-cosineSimilaritiesList[currDocNum]);
			if(!doCosSimilarity) {
				inverseCosineSimilarities[i]=(cosineSimilaritiesList[currDocNum])*
						(cosineSimilaritiesList[currDocNum]);
			}
			
			
		}
		
		double[] weightedProbabilityMeans=new double[numEligDocs];
		int[] numDraws =new int[numEligDocs];
		
		for(int draw=0; draw<10000; draw++) {
			numDraws[RandIntGen.nextInt(numEligDocs)]+=1;
		}
		
		
		//initializing the Max Values
		double MaxValue=-1.0;
		int MaxIndex=-1;
		
		//calculate the weighted value
		for(int i=0; i<numEligDocs;i++) {	
			weightedProbabilityMeans[i]=inverseCosineSimilarities[i]*((double) numDraws[i]);
			
			double greqOrLe=1.0;
			if(!doCosSimilarity) {
				MaxValue=100000000.0; greqOrLe=-1.0;
			}
			
			
			if((greqOrLe*weightedProbabilityMeans[i])>(greqOrLe*MaxValue)) {
				MaxIndex=eligibleDocs[i]; MaxValue=weightedProbabilityMeans[i];				
			}
		}
		
		
		return MaxIndex;
		
	}
	

}
