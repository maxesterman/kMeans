package hw2code;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.util.*;
import java.lang.*;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
/*import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.process.*;*/
import edu.stanford.nlp.simple.*;
import javax.swing.JFrame;
import org.jfree.chart.*;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.plot.*;
import org.jfree.data.xy.*;

public class nlpconstruct {
	
	public nlpconstruct() {
	}
	
	//the main function for the program more or less
	public static void main(String[] NERFolders, String rootFolder, String stringPath,
			boolean doCosSimilarity, int k) throws Exception {
		
		//System.out.println(args.length);
		preprocessingStep preprocessDocs;		
		
		//initializing the file and the list of files
		File folder;
		File[] listOfFiles;
		
		
		
		List<ArrayList<String>> currentText = new ArrayList<ArrayList<String>>();
		ArrayList<Integer> folderBelongings = new ArrayList<Integer>();
		
		ArrayList<HashMap<String,Integer>> termCounts = new ArrayList<HashMap<String,Integer>>();
		Set<String> totalTermCounts = new TreeSet<String>();
		
		ArrayList<ArrayList<String>> termCountsPerFolder= new ArrayList<ArrayList<String>>();
		
		
		
		
		ArrayList<preprocessedObject> preprocObjList = new ArrayList<preprocessedObject>();
		
		ArrayList<HashMap<String,Integer>> topNGramCountList= new ArrayList<HashMap<String,Integer>>();
		
		String textForDoc;
		
		//starting the stanfordCoreNLP pipeline as stated on the documentation
		//for the stanford nlp website
		Properties props = new Properties();
		props.setProperty("annotators", "tokenize,ssplit,pos,lemma, ner");
	    props.setProperty("coref.algorithm", "neural");
	    StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

	    

	    int NERFolderNum=0;
		
	    
	    //folderNumbers and iterating through directory names
	    int folderNum=0;
		for(String dirName : NERFolders) {
			String dirName2;
			dirName2=rootFolder+dirName;
			preprocessDocs = new preprocessingStep(dirName2);
			folder = new File(System.getProperty("user.dir"));
			
			//gets the files to iterate through
			listOfFiles = folder.listFiles();
			System.out.println(listOfFiles);
			
			
			//initializing the word counts to subset the matrix.
			HashMap<String,Integer> NGramCountForFol = new HashMap<String,Integer>();
			HashMap<String,Integer> NumDocsWithNGram = new HashMap<String,Integer>();
			HashMap<String,Integer> topNGramCount;
			preprocPerDoc docToPreproc = new preprocPerDoc();
			
			
			
			ArrayList<String> currentTextList = new ArrayList<String>();
			int numDocs=0;
			
			//iterating through the files in the directory
			for(File file : listOfFiles) {
				System.out.println(file.getName());
				
				//getting the text for the document
				textForDoc=preprocessDocs.getFileText(file.getName());
				String textAfterFilter=preprocessDocs.removeStopWords(textForDoc);
				currentTextList.add(textForDoc);
				folderBelongings.add(folderNum);


				String NERPath="/Users/maxsterman/Downloads/NER/"
						+NERFolders[NERFolderNum]+"/"+file.getName();
				
				/*BufferedWriter br = new BufferedWriter(new FileWriter(NERPath));
				StringBuilder sb = new StringBuilder();*/
				

				
				preprocessedObject newPreProc =preprocessDocs.createdPreprocessedObject(textAfterFilter,
						pipeline, NERPath);
				
				
				

				/*System.out.println("NER Tags: ");
				System.out.println(newPreProc.nerTagsList);
				System.out.println("Tokens Size: ");
				System.out.println(newPreProc.tokenedList.size());
				System.out.println("NER Size: ");
				System.out.println(newPreProc.nerTagsList.size());*/
				

				/* *********************** SAVING NER TO FILE IF NEEDED ******************* */
				
				/*for(String currToken: newPreProc.nerTagsList) {
					sb.append(currToken); sb.append("\n");
				}
				br.write(sb.toString());
				br.close();*/
				
				/* ************************************************************* */

				//System.out.println("Ngrams");
				List<String> UselessNERs = preprocessDocs.getUselessNERTerms(
						newPreProc.nerTagsList, newPreProc.tokenedList);
				
				/*if(folderNum == 1) {
					System.out.println(newPreProc.LemmasList);
				}*/
				
				List<String> allNGrams = preprocessDocs.makeNGrams(newPreProc,2,4);
				//now removes punctuation from the lemmas list
				List<String> totalWordsForTermCount=preprocessDocs.removePunctuations(newPreProc.LemmasList);

				
				//gets all the terms
				//List<String> totalWordsForTermCount=lemmasListForTermCount;
				totalWordsForTermCount.addAll(allNGrams);
				
				//removes useless NGrams (i.e. those with number related topics) and Makes them lower case
				totalWordsForTermCount=preprocessDocs.removingUselessNERGrams(UselessNERs, 
						totalWordsForTermCount);
				totalWordsForTermCount=preprocessDocs.makeAllNGramsLCase(totalWordsForTermCount);
				
				
				HashMap<String,Integer> WordCount = preprocessDocs.findTermCount(totalWordsForTermCount);

				
				NGramCountForFol = docToPreproc.summingFolderWordCount(NGramCountForFol, WordCount);
				NumDocsWithNGram = docToPreproc.summingNumDocsPerFolder(NumDocsWithNGram, WordCount);
				
				
				termCounts.add(WordCount);

				preprocObjList.add(newPreProc);
				numDocs+=1;

				//break;
			}
			
			
			//finding the top number of NGrams in the document
			topNGramCount=docToPreproc.findNBest(NGramCountForFol, NumDocsWithNGram, folderNum, 3, numDocs);
			topNGramCountList.add(topNGramCount);
			termCountsPerFolder.add(new ArrayList<String>(topNGramCount.keySet()));
			
			
			//adding the file text to this new documentlist
			currentText.add(currentTextList);
			folderNum+=1;
			NERFolderNum+=1;
			//break;
			
		}
		
		
		
		
		//////////TFIDF WITH NO FEATURE SELECTION//////////////////

		
		//this if a for show class to show what the document term matrix and
		//the tfidfMatrix looks
		termMatrixAndtfIdf docMatrixClassForShow = new termMatrixAndtfIdf();
		int[][] numTermsMatrixForShow=docMatrixClassForShow.getNumTermsMatrix(termCounts,termCounts);
		double[][] tfIdfMatrixForShow=docMatrixClassForShow.getTfIdfMatrix(numTermsMatrixForShow);
		//docMatrixClassForShow.getTopics(tfIdfMatrixForShow,stringPath,folderBelongings);
		
		
		
		//////////TFIDF WITH FEATURE SELECTION//////////////////
		
		//here I call the tfIdfClass, get the Document Terms Matrix (NumTermsMatrix),
		//for the feature selection. Note that the
		termMatrixAndtfIdf docMatrixClass = new termMatrixAndtfIdf();
		int[][] numTermsMatrix=docMatrixClass.getNumTermsMatrix(termCounts,topNGramCountList);
		double[][] tfIdfMatrix=docMatrixClass.getTfIdfMatrix(numTermsMatrix);
		docMatrixClass.getTopics(tfIdfMatrix,stringPath,folderBelongings);
		
		dataClustering dataClusteringProcess = new dataClustering();
		HashMap<Integer,Integer> kmeansClusterResult;
		//HashMap<Integer,Integer> kmeansClusterResult = dataClusteringProcess.kMeansClustering(tfIdfMatrix, 3);
		
		String KMEansPath="/Users/maxsterman/Downloads/HW2 Output/kMeansCombinations.csv";
		

		//int k=3;
		int[] kmeansInit= {0,1,2};
		kmeansClusterResult=dataClusteringProcess.kMeansClusteringInit(tfIdfMatrix, k, kmeansInit, doCosSimilarity);
		evaluatingResults confusionMatrixResults = new evaluatingResults();
		int[][] confusionMatrix=confusionMatrixResults.getConfusionMatrix(kmeansClusterResult, folderBelongings, 
				k, k);
		
		//print confusion and calc stats that will also be preinted
		confusionMatrixResults.printConfusionMatrix(confusionMatrix, NERFolders);
		confusionMatrixResults.calcStats(confusionMatrix, NERFolders);
		
		
		//Converting the predicted label documents to its correct form
		ArrayList<Integer> correctPermutation=confusionMatrixResults.maxPermutation;
		ArrayList<Integer> predictedFolders = new ArrayList<Integer>();
		for(Integer docNum=0; docNum<kmeansClusterResult.size();docNum++) {
			
			//System.out.println(kmeansClusterResult.get(docNum));
			predictedFolders.add(correctPermutation.indexOf(kmeansClusterResult.get(docNum)));
		}
		
		

		
		

		//matrix calculations
		matrixCalculations tfIdfAsApacheMatrix= new matrixCalculations(tfIdfMatrix);
		//tfIdfAsApacheMatrix.printThisIfTdfMatrix();
		//using the singular value decomposition to get the tfIdf Matrix in two dimensional form
		double[][] dimReductMat=tfIdfAsApacheMatrix.getTwoColumnsOfU();
		
		Color[] colorsToUse= {Color.orange,Color.green,Color.black};
		
		
		String[] NERFoldersPlusTopics = new String[NERFolders.length];
		

		
		//creating a new list of the form "<Folder Name> (Topics: <Top Topic 1>, <Top Topic 2>, <Top Topic 3>)
		for(int folNum=0; folNum<NERFoldersPlusTopics.length; folNum++) {
			ArrayList<String> topTopicsForCurFolder=termCountsPerFolder.get(folNum);
			NERFoldersPlusTopics[folNum]=String.format("%s (Topics: %s, %s, %s)", NERFolders[folNum],
					topTopicsForCurFolder.get(0),topTopicsForCurFolder.get(1),topTopicsForCurFolder.get(2));
			//System.out.println(NERFoldersPlusTopics[folNum]);
		}
		
		
		
		
		//visualizing the original folders
		//Note the parameters (
		visualizationClass origFolders = new visualizationClass("Actual Folder Composition",
				 NERFolders,folderBelongings, dimReductMat, colorsToUse);
		origFolders.setSize(800, 400);
		origFolders.setVisible(true);
		
		//colors used for the class
		Color[] colorsToUse2= {Color.yellow,Color.blue,Color.MAGENTA};

		//visualizing the predicted folders
		visualizationClass predFoldersViz = new visualizationClass("Predicted Folder Composition",
				NERFoldersPlusTopics,predictedFolders, dimReductMat, colorsToUse2);
		predFoldersViz.setSize(800, 400);
		predFoldersViz.setVisible(true);

		
		
		
	}

}
