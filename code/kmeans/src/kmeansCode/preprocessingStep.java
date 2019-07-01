package hw2code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.StringUtils;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.process.*;
import opennlp.tools.stemmer.*;


public class preprocessingStep {
	
	public preprocessingStep(String currentPath) {
		System.out.println(currentPath);
		System.setProperty("user.dir",currentPath);
	}
	
	//function for porterstemmer per string
	public static String letsStem(String testString) {
		
		PorterStemmer testStemmer = new PorterStemmer();
		return testStemmer.stem(testString);
		
	}
	
	//annotating new documnet
	public static CoreDocument newCoreDoc(StanfordCoreNLP pipeline,
			String textString) {
	    CoreDocument textDocument = new CoreDocument(textString);
	    pipeline.annotate(textDocument);
	    return textDocument;
	}
	
	//tokenizing coredocument
	public static List<CoreLabel> tokenizeDoc(CoreDocument textDocument){
		List<CoreLabel> tokesInDoc = textDocument.tokens();
		return tokesInDoc;
	}
	
	//list of stop words. very comprehensive
	public static String[] stopWords={"a",
			"about",
			"above",
			"after",
			"again",
			"against",
			"all",
			"also",
			"am",
			"an",
			"and",
			"any",
			"approx",
			"are",
			"aren't",
			"as",
			"at",
			"be",
			"because",
			"been",
			"before",
			"being",
			"below",
			"between",
			"both",
			"but",
			"by",
			"can",
			"can't",
			"cannot",
			"claim",
			"could",
			"couldn't",
			"did",
			"didn't",
			"do",
			"does",
			"doesn't",
			"doing",
			"don't",
			"down",
			"during",
			"even",
			"each",
			"few",
			"for",
			"from",
			"further",
			"get",
			"give",
			"had",
			"hadn't",
			"has",
			"hasn't",
			"have",
			"haven't",
			"having",
			"he",
			"he'd",
			"he'll",
			"he's",
			"her",
			"here",
			"here's",
			"hers",
			"herself",
			"him",
			"himself",
			"his",
			"how",
			"how's",
			"however",
			"i",
			"i'd",
			"i'll",
			"i'm",
			"i've",
			"if",
			"in",
			"into",
			"is",
			"isn't",
			"it",
			"it's",
			"its",
			"itself",
			"let's",
			"make",
			"me",
			"more",
			"most",
			"musn't",
			"must",
			"mustn't",
			"my",
			"myself",
			"new",
			"no",
			"nor",
			"not",
			"now",
			"of",
			"off",
			"on",
			"once",
			"only",
			"or",
			"other",
			"ought",
			"our",
			"ours",
			"ourselves",
			"out",
			"over",
			"own",
			"part",
			"pay",
			"pays",
			"paying",
			"paid",
			"question",
			"same",
			"say",
			"says",
			"saying",
			"said",
			"set",
			"shan't",
			"she",
			"she'd",
			"she'll",
			"she's",
			"should",
			"shouldn't",
			"since",
			"so",
			"some",
			"such",
			"than",
			"take",
			"that",
			"that's",
			"the",
			"their",
			"theirs",
			"them",
			"themselves",
			"then",
			"there",
			"there's",
			"these",
			"they",
			"they'd",
			"they'll",
			"they're",
			"they've",
			"this",
			"those",
			"through",
			"time",
			"to",
			"too",
			"under",
			"until",
			"up",
			"use",
			"very",
			"was",
			"way",
			"wasn't",
			"we",
			"we'd",
			"we'll",
			"we're",
			"we've",
			"were",
			"weren't",
			"what",
			"what's",
			"when",
			"when's",
			"where",
			"where's",
			"which",
			"while",
			"who",
			"who's",
			"whom",
			"why",
			"why's",
			"will",
			"with",
			"won't",
			"would",
			"wouldn't",
			"you",
			"you'd",
			"you'll",
			"you're",
			"you've",
			"your",
			"yours",
			"yourself",
			"yourselves",
			"A",
			"About",
			"Above",
			"After",
			"Again",
			"Against",
			"All",
			"Also",
			"Am",
			"An",
			"And",
			"Any",
			"Approx",
			"Are",
			"Aren't",
			"As",
			"At",
			"Be",
			"Because",
			"Been",
			"Before",
			"Being",
			"Below",
			"Between",
			"Both",
			"But",
			"By",
			"Can",
			"Can't",
			"Cannot",
			"Claim",
			"Could",
			"Couldn't",
			"Did",
			"Didn't",
			"Do",
			"Does",
			"Doesn't",
			"Doing",
			"Don't",
			"Down",
			"During",
			"Each",
			"Even",
			"Few",
			"For",
			"From",
			"Further",
			"Get",
			"Give",
			"Had",
			"Hadn't",
			"Has",
			"Hasn't",
			"Have",
			"Haven't",
			"Having",
			"He",
			"He'd",
			"He'll",
			"He's",
			"Her",
			"Here",
			"Here's",
			"Hers",
			"Herself",
			"Him",
			"Himself",
			"His",
			"How",
			"How's",
			"However",
			"I",
			"I'd",
			"I'll",
			"I'm",
			"I've",
			"If",
			"In",
			"Into",
			"Is",
			"Isn't",
			"It",
			"It's",
			"Its",
			"Itself",
			"Let's",
			"Make",
			"Me",
			"More",
			"Most",
			"Musn't",
			"Must",
			"Mustn't",
			"My",
			"Myself",
			"New",
			"No",
			"Nor",
			"Not",
			"Now",
			"Of",
			"Off",
			"On",
			"Once",
			"Only",
			"Or",
			"Other",
			"Ought",
			"Our",
			"Ours",
			"Ourselves",
			"Out",
			"Over",
			"Own",
			"Part",
			"Pay",
			"Pays",
			"Paid",
			"Paying",
			"Question",
			"Same",
			"Say",
			"Says",
			"Saying",
			"Said",
			"Set",
			"Shan't",
			"She",
			"She'd",
			"She'll",
			"She's",
			"Should",
			"Shouldn't",
			"Since",
			"So",
			"Some",
			"Such",
			"Take",
			"Than",
			"That",
			"That's",
			"The",
			"Their",
			"Theirs",
			"Them",
			"Themselves",
			"Then",
			"There",
			"There's",
			"These",
			"They",
			"They'd",
			"They'll",
			"They're",
			"They've",
			"This",
			"Those",
			"Through",
			"Time",
			"To",
			"Too",
			"Under",
			"Until",
			"Up",
			"Use",
			"Very",
			"Was",
			"Wasn't",
			"Way",
			"We",
			"We'd",
			"We'll",
			"We're",
			"We've",
			"Were",
			"Weren't",
			"What",
			"What's",
			"When",
			"When's",
			"Where",
			"Where's",
			"Which",
			"While",
			"Who",
			"Who's",
			"Whom",
			"Why",
			"Why's",
			"Will",
			"With",
			"Won't",
			"Would",
			"Wouldn't",
			"You",
			"You'd",
			"You'll",
			"You're",
			"You've",
			"Your",
			"Yours",
			"Yourself",
			"Yourselves"};
			
		
	//this string removes the stop words for the entire document text
	public static String removeStopWords(String currentText) {
		//currentText=currentText.toLowerCase();
		//System.out.println("current text with punctuation:");
		//System.out.println(currentText);
		//currentText=currentText.replaceAll(".", "");
		//currentText=currentText.replaceAll(",", "");
		//currentText=currentText.replaceAll("-","");
		//System.out.println("current text without punctuation:");
		//System.out.println(currentText);
		int i=0;
		for(String word : stopWords) {
			currentText=currentText.replaceAll(" "+word+" "," ");
			currentText=currentText.replaceAll(" "+word+"\\. ", "\\. ");
			currentText=currentText.replaceAll(" "+word+", ", ", ");
			currentText=currentText.replaceAll(" "+word+"! ", "! ");
			currentText=currentText.replaceAll("-"+word+"-", "");
			currentText=currentText.replaceAll("-"+word+" ", "-");
			currentText=currentText.replaceAll(" "+word+"-", "-");
			currentText=currentText.replaceAll("\""+word+" ", " ");
			currentText=currentText.replaceAll(" "+word+"\"", " ");
			currentText=currentText.replaceAll("\'"+word+" ", " ");
			currentText=currentText.replaceAll(" "+word+"\'", " ");
			//System.out.println(i);
			//System.out.println(currentText);
			++i;
		}
		return currentText;
		
		
	}
	
	
	//this function gets the text for the file
	public String getFileText(String fileName) throws Exception {	
		//File newFile = new File("/Users/maxsterman/Downloads/dataset_3/data/C1/article01.txt");
		System.out.println(System.getProperty("user.dir")+"/"+fileName);
		File newFile = new File(System.getProperty("user.dir")+"/"+fileName);
		BufferedReader newBuff = new BufferedReader(new FileReader(newFile));
		String newString; String textString="";
		while((newString = newBuff.readLine())!= null) {
			textString+=newString+" ";	
		}
		//System.out.println(textString);
		return textString;
		//CoreDocument document = new CoreDocument(textString);
	}
	
	
	/*public static HashMap<String,Integer> runPreprocessing(String textString){
		Properties props = new Properties();
	    // set the list of annotators to run
	    props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner");
		//props.setProperty("annotators", "tokenize,ssplit,pos,lemma");
	    // set a property for an annotator, in this case the coref annotator is being set to use the neural algorithm
	    props.setProperty("coref.algorithm", "neural");
	    
//	    props.setProperty("ner.useSUTime", "false");
	    // build pipeline
	    StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
	    System.out.println("done");

		
	    System.out.println("Example: token");

	    
	    CoreDocument textDocument = new CoreDocument(textString);
	    pipeline.annotate(textDocument);
	    
	    List<CoreLabel> tokesInDoc = textDocument.tokens();
	    
	    List<String> DocLemmas = new ArrayList<String>();
	    List<String> NERTags = new ArrayList<String>();
	    
	    
	    for(CoreSentence sents : textDocument.sentences()) {
	    	NERTags.addAll(sents.nerTags());
	    }
	    
	    edu.stanford.nlp.simple.Document docForLemmas = 
	    		new edu.stanford.nlp.simple.Document(textString);
	    
	    for(edu.stanford.nlp.simple.Sentence sents: docForLemmas.sentences()) {
	    	DocLemmas.addAll(sents.lemmas());
	    }
	    
	    System.out.println("Lemmas: ");
	    System.out.println(DocLemmas);
	    System.out.println("NERTAGS");
	    System.out.println(NERTags);
	    
	    Collection<String> allNgrams = StringUtils.getNgramsFromTokens(tokesInDoc, 1,4);
	    
	    

	    
	    HashMap<String,Integer> termCount = findTermCount(allNgrams);
	    
	    System.out.println("Term Count Set");
	    System.out.println(termCount.entrySet());
	    
	    Set<String> setOfTermKeys=termCount.keySet();
	    Iterator<String> termKeyIterator=setOfTermKeys.iterator();
	    String currentTerm;
	    while(termKeyIterator.hasNext()) {
	    	currentTerm=termKeyIterator.next();
	    	//System.out.println(currentTerm+": "+termCount.get(currentTerm));
	    }
	    
	    return termCount;
	    
	    
	    
	    
	    
	    
		
		
	}*/
	
	
	//this moves all the punctuations for the file
	public static List<String> removePunctuations(List<String> lemmaWords){
		Iterator<String> lemmasIterator = lemmaWords.iterator();
		String currString;
		while(lemmasIterator.hasNext()) {
			currString=lemmasIterator.next();
			if(currString.contains("\'") || currString.contains(":") || 
					currString.contains("'") || currString.contains("\"")
					|| currString.contains("`") || currString.contains("(")
					|| currString.contains(")") || currString.contains(".")
					|| currString.contains(",") || currString.contains("!")
					|| currString.contains("?") || currString.contains("%")
					|| currString.contains("$") || currString.contains("-")) {
				lemmasIterator.remove();
			}
		}
		return lemmaWords;
		
		
	}
	
	
	//this creates the main preprocessed object, which is the object of lists for the token, stemmed, lemmas,
	//and the NERs
	public static preprocessedObject createdPreprocessedObject(String textString, StanfordCoreNLP pipeline,
			String NERPath) throws Exception{


	    
	    CoreDocument textDocument = new CoreDocument(textString);
	    pipeline.annotate(textDocument);
	    
	    
	    //creating the core tokens 
	    List<CoreLabel> tokesInDoc = textDocument.tokens();
	    
	    //going through each word and using the porter stemmer
	    List<String> stemmedWords = new ArrayList<String>();
	    for(CoreLabel currToken: tokesInDoc) {
	    //	System.out.println(currToken.originalText());
	    	stemmedWords.add(letsStem(currToken.originalText()));
	    }
	    
	    //initializing the doc lemmas
	    List<String> DocLemmas = new ArrayList<String>();
	    
	    //this saved the NERs, useless now
	    //List<String> NERTags = gettingNERList(NERPath);
	   // System.out.println(NERTags);
	    
	    /* ***********ACTUAL NER *********** */
	    //use this once this works
	    List<String> NERTags = new ArrayList<String>();	    
	    //getting the NERTags
	    for(CoreSentence sents : textDocument.sentences()) {
	    	//NERTags.addAll(sents.posTags());
	    	NERTags.addAll(sents.nerTags());
	    }
	    
	    /* *********************************** */
	    
	    
	    /* **************** Calculating Lemmas ****************** */
	    //using the simple nlp for the lemmas
	    edu.stanford.nlp.simple.Document docForLemmas = 
	    		new edu.stanford.nlp.simple.Document(textString);
	    
	    for(edu.stanford.nlp.simple.Sentence sents: docForLemmas.sentences()) {
	    	DocLemmas.addAll(sents.lemmas());
	    }
	    
	    /* ***************************** */
	    /*System.out.println("Lemmas: ");
	    System.out.println(DocLemmas);
	    System.out.println("NERTAGS");
	    System.out.println(NERTags);*/
	    
	    /* creates the preprocessed object */
	    preprocessedObject preprocessedObj= new preprocessedObject(tokesInDoc,DocLemmas,NERTags,stemmedWords);

	    /*System.out.println(DocLemmas);
	    System.out.println("NERTAGS");
	    System.out.println(NERTags);*/
	    return preprocessedObj;
	    

	    
		
		
	}
	
/*	public static HashMap<String,Integer> runPreprocessing(String textString){
		Properties props = new Properties();
	    // set the list of annotators to run
	    props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner");
		//props.setProperty("annotators", "tokenize,ssplit,pos,lemma");
	    // set a property for an annotator, in this case the coref annotator is being set to use the neural algorithm
	    props.setProperty("coref.algorithm", "neural");
	    
//	    props.setProperty("ner.useSUTime", "false");
	    // build pipeline
	    StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
	    System.out.println("done");

		
	    System.out.println("Example: token");

	    
	    CoreDocument textDocument = new CoreDocument(textString);
	    pipeline.annotate(textDocument);
	    
	    List<CoreLabel> tokesInDoc = textDocument.tokens();
	    
	    List<String> DocPosTags = new ArrayList<String>();
	    List<String> NERTags = new ArrayList<String>();
	    
	    
	    for(CoreSentence sents : textDocument.sentences()) {
	    	DocPosTags.addAll(sents.posTags());
	    	NERTags.addAll(sents.nerTags());
	    }
	    
	    System.out.println(DocPosTags);
	    System.out.println("NERTAGS");
	    System.out.println(NERTags);
	    
	    Collection<String> allNgrams = StringUtils.getNgramsFromTokens(tokesInDoc, 1,4);
	    
	    

	    
	    HashMap<String,Integer> termCount = findTermCount(allNgrams);
	    
	    System.out.println("Term Count Set");
	    System.out.println(termCount.entrySet());
	    
	    Set<String> setOfTermKeys=termCount.keySet();
	    Iterator<String> termKeyIterator=setOfTermKeys.iterator();
	    String currentTerm;
	    while(termKeyIterator.hasNext()) {
	    	currentTerm=termKeyIterator.next();
	    	//System.out.println(currentTerm+": "+termCount.get(currentTerm));
	    }
	    
	    return termCount;
	    
	    
	    
	    
	    
	    
		
		
	}*/
	
	
	/*This is the function that makes the NGrams. The three arguments are
	 * preprocessedObj: The preprocessed object from the class
	 * smallestNGramSize: The smallest ngram size (set of words) that we would want. Generally, best to start at 2 since this adds all valid tokens
	 * LargestNGramSize: The smallest ngram size (set of words) that we would want.
	 * */
	public List<String> makeNGrams(preprocessedObject preprocessedObj, int smallestNGramSize, int LargestNgramSize){
		List<String> CurrentLemmas = preprocessedObj.LemmasList;
		
		//want to get rid of all punctuation asside from commas and periods. this important because we want to 
		//use that as a separator
		List<String> NoPuncLemmas = removePuncLessCommaLessPer(CurrentLemmas);
		List<String> NGrams = new ArrayList<String>();
		
		
		//ngrams are for looped to get
		for(int nGramSize=smallestNGramSize;nGramSize<=LargestNgramSize;nGramSize++) {
			for(int word=0; word<NoPuncLemmas.size()-nGramSize;word++) {
				String nGramToAdd="";
				for(int letter=0;letter<nGramSize;letter++) {
					//create ngram
					if(letter == 0) {
						nGramToAdd+=NoPuncLemmas.get(word+letter);
					}else {
						nGramToAdd+=(" "+NoPuncLemmas.get(word+letter));
					}
				}
				
				//making sure ngrams with end of sentence or end of phrase punctuation isn't added
				if(!(nGramToAdd.contains(".") || nGramToAdd.contains(",") || nGramToAdd.contains("!")
						|| nGramToAdd.contains("?"))) {
					NGrams.add(nGramToAdd);
				}
			}
		}
		
		return NGrams;
		//return CurrentLemmas;
		
		
	}
	
	//removes all punctuations besides end of phrase stuff (such as commas, periods, etc.)
	//Parameter-lemmasList: just the list of lemmas created earlier
	public List<String> removePuncLessCommaLessPer(List<String> lemmasList){
		//iterate through all the lemmas
		Iterator<String> lemmasIterator = lemmasList.iterator();
		String currString;
		while(lemmasIterator.hasNext()) {
			currString=lemmasIterator.next();
			//remove all the lemmas with certain punctuation
			if(currString.contains("\'") || currString.contains(":") || 
					currString.contains("'") || currString.contains("\"")
					|| currString.contains("`") || currString.contains("(")
					|| currString.contains(")")) {
				lemmasIterator.remove();
			}
		}
		//return the punctuation-less list
		return lemmasList;
	}
	
	//replacing all NGrams with their lowercase value
	public List<String> makeAllNGramsLCase(List<String> NGramList){
		//List<String> correctNGram = new ArrayList<String>();
		
		//System.out.println("Before making lcase"); System.out.println(NGramList);
		
		//replacing NGrams with Lower Case Value
		String NGramString;
		for(int currentGram=0; currentGram<NGramList.size(); currentGram++) {
			NGramString=NGramList.get(currentGram).toLowerCase();
			NGramList.set(currentGram, NGramString);
		}
				
		//System.out.println("After making lcase"); System.out.println(NGramList);
		
		return NGramList;
	}
	
	
	//This function finds how many terms there were for each NGram
	public static HashMap<String,Integer> findTermCount(List<String> NGramCollection){
	 	HashMap<String, Integer> termCount = new HashMap<String,Integer>();
	    Iterator<String> termIterator=NGramCollection.iterator();
	    String currentTerm;
	    while(termIterator.hasNext()) {
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
	    	
	    } 
	    return termCount;
	}

	//gets the list of NERs that were saved to my file system. useless now
	private static List<String> gettingNERList(String filePath) throws Exception{
		File newFile = new File(filePath);
		BufferedReader newBuff = new BufferedReader(new FileReader(newFile));
		String newString; List<String> NERAList = new ArrayList<String>();
		while((newString = newBuff.readLine())!= null) {
				NERAList.add(newString);
		}
		return NERAList;
		/*String[] NERList = new String[NERAList.size()];
		
		for(int currNERNum=0;currNERNum<NERAList.size();currNERNum++) {
			NERList[currNERNum]=NERAList.get(currNERNum);
		}
		
		return NERList;*/
	}

	//this function got all the terms that included NERS that were of little value: such as dates
	//numbers, orders, times, numerical money (Essentially any data that was seemingly worthless
	//Parameters: NERList (the list of NERs for each document), 
	//OldTokenList, the list of tokens
	//Returns: List of strings to remove the NERs
	public static List<String> getUselessNERTerms(List<String> NERList, List<CoreLabel> OldTokenList){
		
		//NER Topics that are like stop words. I chose this because it has numerical values, which will
		//not likely represent general topics
		String[] Stop_topics = {"MONEY", "NUMBER", "ORDINAL", "PERCENT", "DATE", "TIME", "DURATION", "SET"};
		ArrayList<String> Stop_topics_List = new ArrayList<String>();		
		for(int topic = 0; topic<Stop_topics.length;topic++) {
			Stop_topics_List.add(Stop_topics[topic]);
		}
		
		
		
		//convert tokens from strings to core labels
		List<String> TokenList=new ArrayList<String>(); 
		for(CoreLabel oldToken:OldTokenList) {
			TokenList.add(oldToken.word());
		}
		
		

		
		//collecting NERs for looping through each one
		List<String> NERCollections= new ArrayList<String>(); String CurrentToken=""; 
		String CurrentNER="";
		for(int numTokens=0; numTokens<TokenList.size();numTokens++) {
			String PreviousNER=CurrentNER;
			CurrentNER=NERList.get(numTokens);
			
			//want words that will be useless (numbers of sorts)
			if(Stop_topics_List.contains(CurrentNER)) {	
					
					//checks if this is the same NERs as before
					if(PreviousNER.equals(CurrentNER)) {
						if(!TokenList.get(numTokens).isEmpty()) {
							NERCollections.add(CurrentToken);
							NERCollections.add(TokenList.get(numTokens));
							CurrentToken+=" "+TokenList.get(numTokens);
						}
					}else if(numTokens>0){
						//System.out.println("NER:"); System.out.println(CurrentNER); System.out.println("Token:");
						//System.out.println(CurrentToken);
						if(!CurrentToken.isEmpty()) {
							NERCollections.add(CurrentToken);
						}	
						CurrentToken=TokenList.get(numTokens);
					}else {
						CurrentToken=TokenList.get(numTokens);
					}
				
			}else {
				if(Stop_topics_List.contains(PreviousNER) && (!CurrentToken.isEmpty())){
					//System.out.println(CurrentToken);
					NERCollections.add(CurrentToken);
				}
				CurrentToken="";
			}
			
			//adding the current token to the collection if it isn't empty and it's the last token
			if((!CurrentToken.isEmpty()) && ((numTokens+1)==TokenList.size())) {
				NERCollections.add(CurrentToken);
			}
			
		}
		
		//System.out.println("Useless NERs:");
		//System.out.println(NERCollections);
		
		return NERCollections;
		
	}
	
	//Remove all NGrams that contained the UselessNERGrams
	//Paramters: the list of string of the Useless NER Grams (ALLNERGrams) and AllNgrams (AllNGrams)
	//Return: AllNGrams
	public List<String> removingUselessNERGrams(List<String> ALLNERGrams, List<String> AllNGrams){
		
		//Iterate through all the Ngrams and delete the ones with the useless NER Grams
		Iterator<String> NGramIter = AllNGrams.iterator();
		String CurrNGram;
		while(NGramIter.hasNext()) {
			CurrNGram=NGramIter.next();
			if(ALLNERGrams.contains(CurrNGram)) {
				NGramIter.remove();
			}
		}
		
		return AllNGrams;
	}
	
	
	//public List<ArrayList<HashMap<String,Integer>>>findTopNTermsPerDoc
	
	
}


