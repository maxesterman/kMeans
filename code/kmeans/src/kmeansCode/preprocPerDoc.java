package hw2code;
import java.util.*;

public class preprocPerDoc {
	
	public preprocPerDoc() {
		
	}
	
	public static HashMap<String,Integer> summingFolderWordCount(
			HashMap<String,Integer> listOfWordCountsForFolder, HashMap<String,Integer> WordCountsPerDoc){
		
		//HashMap<String, Integer> CurrentFolderHashMap=listOfWordCountsForFolder.get(currentFolder);
		
		//find the number of terms per folder
		Integer docValue; Integer folderValue;
		for(String term : WordCountsPerDoc.keySet()) {
			docValue=WordCountsPerDoc.get(term);
			if(listOfWordCountsForFolder.containsKey(term)) {
				folderValue=listOfWordCountsForFolder.get(term);
				listOfWordCountsForFolder.replace(term, folderValue+docValue);
			}else {
				listOfWordCountsForFolder.put(term, docValue);
			}
			
		}
		
		//listOfWordCountsForFolder.set(currentFolder, CurrentFolderHashMap);
		
		return listOfWordCountsForFolder;
	}
	
	
	//tries to find the number of documents per folder
	public static HashMap<String,Integer> summingNumDocsPerFolder(
			HashMap<String,Integer> numDocsWithTerm, HashMap<String,Integer> WordCountsPerDoc){
		
		//HashMap<String, Integer> CurrentFolderHashMap=listOfWordCountsForFolder.get(currentFolder);
		
		//find the number of terms per folder
		Integer docValue; Integer folderCount;
		for(String term : WordCountsPerDoc.keySet()) {
			//docValue=WordCountsPerDoc.get(term);
			if(numDocsWithTerm.containsKey(term)) {
				folderCount=numDocsWithTerm.get(term);
				numDocsWithTerm.replace(term, folderCount+1);
			}else {
				numDocsWithTerm.put(term, 1);
			}
			
		}
		
		//listOfWordCountsForFolder.set(currentFolder, CurrentFolderHashMap);
		
		return numDocsWithTerm;
	}
	
	
	public static HashMap<String,Integer> findNBest(HashMap<String,Integer> listOfWordCountsForFolder, 
			HashMap<String,Integer> numDocsWithTerm, int currentFolder, int maxTermCount, int numDocs){
		//Getting the current hashmap and putting into the keyset
		//HashMap<String, Integer> CurrentFolderHashMap=listOfWordCountsForFolder.get(currentFolder);
		HashMap<String, Integer> CurrentFolderHashMap=listOfWordCountsForFolder;
		List<String> CurrentFolderKeySet=new ArrayList<String>(CurrentFolderHashMap.keySet());
		
		
		
		//Initializing the sorted NGram HashMap
		HashMap<String, Integer> SortedNGramHashMap = new HashMap<String, Integer>();
		String[] SortedNGrams = new String[CurrentFolderHashMap.size()];
		//Integer[] SortedNGramCounts = new Integer[CurrentFolderHashMap.size()];
		double[] SortedNGramCounts = new double[CurrentFolderHashMap.size()];
		
		double ndocsForTerm; double wordCountForTerm; double numDocsInFolder=(double)numDocs;
			
			
			
		//time for the insertion sort
		//int i=0;
		for(int i=0;i<CurrentFolderHashMap.size();i++) {
		
			//putting in the value at the ith position
			SortedNGrams[i]=CurrentFolderKeySet.get(i);
			
			
			
			ndocsForTerm=(double)numDocsWithTerm.get(CurrentFolderKeySet.get(i));
			wordCountForTerm=(double)CurrentFolderHashMap.get(CurrentFolderKeySet.get(i));
			SortedNGramCounts[i]=(wordCountForTerm)*(ndocsForTerm/numDocsInFolder);
			//currSortedTerms.add(this.allTerms.get(i));
			for(int j=0; j<i;j++) {
				//making the comparison from 0 to i-1
				/*if(sortedMatrix[currFolder][i] > 0) {
					System.out.println("this is true");
					System.out.println(String.format("Folder Num: %d, Term Num % d", currFolder,i));
				}*/
				if(SortedNGramCounts[i]>SortedNGramCounts[j]) {
					
					//get temp value of jth element
					Double tempDouble=SortedNGramCounts[j];
					String tempString=SortedNGrams[j];
					
					//set jth elements of matrix to ith element
					SortedNGramCounts[j]=SortedNGramCounts[i];
					SortedNGrams[j]=SortedNGrams[i];
					
					//put the old jth value in to the ith spot
					SortedNGramCounts[i]=tempDouble;
					SortedNGrams[i]=tempString;
					
				}
			}
		}
		
		
		for(int i=0;i<maxTermCount;i++) {
			SortedNGramHashMap.put(SortedNGrams[i], CurrentFolderHashMap.get(SortedNGrams[i]));
		}
		
		
		
		return SortedNGramHashMap;
		
		
	}
	
	
	
}
