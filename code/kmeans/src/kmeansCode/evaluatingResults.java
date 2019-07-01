package hw2code;
import java.util.*;

public class evaluatingResults {

	public int[][] confusionMatrix;
	public double[] recall;
	public double[] precision;
	public double[] f1score;
	public ArrayList<Integer> maxPermutation;
	
	
	public evaluatingResults() {
		
	}
	
	public int[][] getConfusionMatrix(HashMap<Integer,Integer> predictedFolders, 
			ArrayList<Integer> actualFolders, int numActualFolders, int numPredictedFolders){
		int[][] preConfusionMatrix= new int[numActualFolders][numPredictedFolders];
		
		//Initialization
		for(int i=0;i<numPredictedFolders; i++) {
			for(int j=0; j<numActualFolders; j++) {
				preConfusionMatrix[i][j]=0;
			}
		}
		

		
		double[] TotalDocs = new double[numActualFolders];
		//double[] TotalPredictions = new double[numPredictedFolders];
		//creating the confusion Matrix
		for(int i=0;i<predictedFolders.size();i++) {
				preConfusionMatrix[predictedFolders.get(i)][actualFolders.get(i)]+=1;
				TotalDocs[actualFolders.get(i)]+=1.0;
				//TotalPredictions[predictedFolders.get(i)]+=1.0;
		}
		

		
		
		
		//initializing the max term array and maxIndex Array
/*		int[] maxTerm=new int[numActualFolders]; int[] maxIndex=new int[numActualFolders];
		
		for(int numActual=0; numActual<numActualFolders; numActual++) {
			maxTerm[numActual]=-1;
			maxIndex[numActual]=-1;
		}
		
		//finding the maxterm. note that if two max terms are equal, the first one is used
		for(int numActual=0; numActual<numActualFolders; numActual++) {
			for(int numPredicted=0; numPredicted<numPredictedFolders; numPredicted++) {
				if(confusionMatrix[numPredicted][numActual] > maxTerm[numActual]) {
					maxTerm[numActual]=confusionMatrix[numPredicted][numActual];
					maxIndex[numActual]=numPredicted;
				}	
			}
		}*/
		

		ArrayList<Integer> applicableNumbers = new ArrayList<Integer>();
		for(int i=0; i < numPredictedFolders; i++) {
			applicableNumbers.add(i);
		}
		
		ArrayList<ArrayList<Integer>> permutations= new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> allPermutations=findPermutations(permutations, 
				applicableNumbers);
		

		
		
		
		//find which permutation has the highest recall. This is useful because
		//there are n specific spots that are filled by a folder and we want the user
		//to guess all n.
		double maxRecall=-1.0; int maxPermNum=-1;
		for(int permNum=0; permNum<allPermutations.size();permNum++) {
			ArrayList<Integer> currentPerm=allPermutations.get(permNum);
			double totalRecall=0.0;
			for(int currentFold=0;currentFold<currentPerm.size();currentFold++) {
				totalRecall+=preConfusionMatrix[currentPerm.get(currentFold)][currentFold]/TotalDocs[currentFold];
			}
			double averageRecall=totalRecall/currentPerm.size();
			if(averageRecall> maxRecall) {
				maxRecall=averageRecall; maxPermNum=permNum;
			}	
		}
		
		

		
		//filling in the max recall matrix
		ArrayList<Integer> currentPermutation=allPermutations.get(maxPermNum);
		int[][] trueConfusionMatrix=new int[currentPermutation.size()][numActualFolders];	
		for(int i=0; i<trueConfusionMatrix.length; i++) {
			for(int j=0; j<trueConfusionMatrix[i].length;j++) {
				trueConfusionMatrix[i][j]=preConfusionMatrix[currentPermutation.get(i)][j];
			}
		}

		this.maxPermutation=allPermutations.get(maxPermNum);
		
		return trueConfusionMatrix;
		
		//this.confusionMatrix
		
	}
	
	/*private static calcRecall(int[][] confusionMatrix,double[] totalDocs) {
		
	}*/
	
	
	public static void printConfusionMatrix(int[][] confusionMat, String[] NERFolders) {
		System.out.println(" "); System.out.println("Confusion Matrix");
		System.out.print("  ");
		for(int j=0; j<confusionMat[0].length;j++) {
			System.out.print(NERFolders[j]);System.out.print(" ");
		}
		System.out.print("\n");
		
		for(int i=0; i<confusionMat.length; i++) {
			System.out.print(NERFolders[i]);System.out.print(" ");
			for(int j=0; j<confusionMat[0].length;j++) {
				System.out.print(confusionMat[i][j]);System.out.print(" ");
			}
			System.out.print("\n");
		}
		
	}
	
	
	
	public void calcStats(int[][] foundConfMat, String[] NERFolders) {
		this.confusionMatrix=foundConfMat;
		
		double[] TotalPredictions = new double[foundConfMat.length];
		double[] TotalDocs = new double[foundConfMat[0].length];
		
		for(int i=0; i<TotalPredictions.length;i++) {
			TotalPredictions[i]=0.0;
			TotalDocs[i]=0.0;
		}
		
		
		for(int i=0; i<TotalPredictions.length;i++) {
			for(int j=0; j<TotalDocs.length; j++) {
				TotalPredictions[i]+=((double) foundConfMat[i][j]);
				TotalDocs[j]+=((double) foundConfMat[i][j]);
			}
			
		}
		
		/*double truePositives=0.0; double totalPredicted=0.0; double totalActual=0.0;
		
		for(int i=0; i<TotalPredictions.length;i++) {
			truePositives+=((double) foundConfMat[i][i]);
			totalPredicted+=TotalPredictions[i];
			totalActual+=TotalDocs[i];
		}*/
		
		double[] precisionVector=new double[TotalPredictions.length];
		double[] recallVector=new double[TotalDocs.length];
		double[] f1Vector=new double[TotalDocs.length];
		
		double truePositives=0.0; double numTotalDocs=0.0; double numTotalPredictions=0.0;
		
		for(int i=0; i<TotalPredictions.length;i++) {
			precisionVector[i]=((double) foundConfMat[i][i])/TotalPredictions[i];
			recallVector[i]=((double) foundConfMat[i][i])/TotalDocs[i];
			f1Vector[i]=2*(precisionVector[i]*recallVector[i])/(precisionVector[i]+recallVector[i]);
			//truePositives+=((double) foundConfMat[i][i]);
			//numTotalDocs+=TotalDocs[i]; numTotalPredictions+=TotalPredictions[i];
			
			
		}
		
		
		this.precision=precisionVector; this.recall=recallVector;
		this.f1score=f1Vector;
		
		System.out.print("Recall: "); 
		
		for(int i=0; i<this.recall.length;i++) {
			System.out.print(String.format("%s: ", NERFolders[i])); System.out.print(this.recall[i]); System.out.print(" ");
		}
		
		System.out.print("\n");
		
		System.out.print("Precision: "); 

		for(int i=0; i<this.precision.length;i++) {
			System.out.print(String.format("%s: ", NERFolders[i])); System.out.print(this.precision[i]); System.out.print(" ");
		}
		
		System.out.print("\n");
		
		System.out.print("F1 Score: "); 
		
		for(int i=0; i<this.f1score.length;i++) {
			System.out.print(String.format("%s: ", NERFolders[i])); System.out.print(this.f1score[i]); System.out.print(" ");
		}
		
		System.out.print("\n");
		
		
	}
	
	
	//finds all permutations
	private static ArrayList<ArrayList<Integer>> findPermutations(ArrayList<ArrayList<Integer>> permutations, 
			ArrayList<Integer> applicableNumbers){
		
			if(applicableNumbers.isEmpty()) {
				return permutations;
			}
			
			ArrayList<ArrayList<Integer>> allPermutations=new ArrayList<ArrayList<Integer>>();
			
			int currentApplicableNumbersSize=applicableNumbers.size();
			for(int number=0; number<currentApplicableNumbersSize;number++) {
				ArrayList<Integer> newApplicableNumbers = new ArrayList<Integer>(applicableNumbers);
				Integer correctNumber=newApplicableNumbers.get(number);
				newApplicableNumbers.remove(correctNumber);
				ArrayList<ArrayList<Integer>> permutationsFromHere=findPermutations(permutations,newApplicableNumbers);
				
				ArrayList<ArrayList<Integer>> truePermutations=new ArrayList<ArrayList<Integer>>();

				if(permutationsFromHere.size() >0) {
					int currentPermutationsSize=permutationsFromHere.size();
					for(int permutationNum=0;permutationNum<currentPermutationsSize;permutationNum++) {
						ArrayList<Integer> currentPermutation=permutationsFromHere.get(permutationNum);
						currentPermutation.add(0, correctNumber);
						truePermutations.add(currentPermutation);
					}
				}else {
					ArrayList<Integer> currentPermutation=new ArrayList<Integer>();
					currentPermutation.add(0, correctNumber);
					truePermutations.add(currentPermutation);
				}
				allPermutations.addAll(truePermutations);
				
			}
		
		
		return allPermutations;
	}
	
		
	
	
	
	public static void main(String[] args) {
		
	/*	ArrayList<Integer> applicableNumbers = new ArrayList<Integer>();
		for(int i=0; i < 3; i++) {
			applicableNumbers.add(i);
		}
		
		ArrayList<ArrayList<Integer>> permutations= new ArrayList<ArrayList<Integer>>();
		
		System.out.println(findPermutations(permutations,applicableNumbers));
		
		
		int k=3;
		
		ArrayList<Integer> documentClusters = new ArrayList<Integer>(); 
		for(int i=0; i<3;i++) {
			for(int j=0; j<8;j++) {
				documentClusters.add(i);
			}
		}
		
		
		HashMap<Integer,Integer> predictedClusters = new HashMap<Integer, Integer>();
		predictedClusters.put(0,2);
		predictedClusters.put(1,2);
		predictedClusters.put(2,2);
		predictedClusters.put(3,2);
		predictedClusters.put(4,2);
		predictedClusters.put(5,2);
		predictedClusters.put(6,2);
		predictedClusters.put(7,2);
		predictedClusters.put(8,1);
		predictedClusters.put(9,0);
		predictedClusters.put(10,1);
		predictedClusters.put(11,0);
		predictedClusters.put(12,0);
		predictedClusters.put(13,0);
		predictedClusters.put(14,0);
		predictedClusters.put(15,1);
		predictedClusters.put(16,2);
		predictedClusters.put(17,2);
		predictedClusters.put(18,2);
		predictedClusters.put(19,2);
		predictedClusters.put(20,2);
		predictedClusters.put(21,2);
		predictedClusters.put(22,2);
		predictedClusters.put(23,2);
		int[][] trueConfusionMatrix;*/
		//getConfusionMatrix(predictedClusters,documentClusters,k,k);		
		/*predictedClusters.put(0,2);
		predictedClusters.put(1,2);
		predictedClusters.put(2,2);
		predictedClusters.put(3,2);
		predictedClusters.put(4,2);
		predictedClusters.put(5,2);
		predictedClusters.put(6,2);
		predictedClusters.put(7,2);
		predictedClusters.put(8,1);
		predictedClusters.put(9,1);
		predictedClusters.put(10,1);
		predictedClusters.put(11,1);
		predictedClusters.put(12,1);
		predictedClusters.put(13,1);
		predictedClusters.put(14,1);
		predictedClusters.put(15,1);
		predictedClusters.put(16,0);
		predictedClusters.put(17,0);
		predictedClusters.put(18,0);
		predictedClusters.put(19,0);
		predictedClusters.put(20,0);
		predictedClusters.put(21,0);
		predictedClusters.put(22,0);
		predictedClusters.put(23,0);*/
		/*int[][] trueConfusionMatrix=getConfusionMatrix(predictedClusters,documentClusters,k,k);
		
		System.out.println("Confusion Matrix");
		
		
		for(int i=0; i<trueConfusionMatrix.length;i++) {
			for(int j=0; j<trueConfusionMatrix[i].length;j++) {
				System.out.print(trueConfusionMatrix[i][j]); System.out.print(" ");
			}
			System.out.print("\n");
		}
		evaluatingResults evalResObj=new evaluatingResults();
		
		evalResObj.calcStats(trueConfusionMatrix);
		System.out.print("Recall: ");System.out.print(evalResObj.recall); System.out.print("\n");
		System.out.print("Precision: ");System.out.print(evalResObj.precision); System.out.print("\n");
		System.out.print("F1Score: ");System.out.print(evalResObj.f1score);	System.out.print("\n");
	*/	
	}
	
	
	
	
}
