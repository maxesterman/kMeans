package hw2code;


import java.util.*;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.StringUtils;
import edu.stanford.nlp.process.DocumentPreprocessor;
import opennlp.tools.stemmer.*;

public class preprocessedObject {
	
	public List<CoreLabel> tokenedList;
	public List<String> LemmasList;
	public List<String> nerTagsList;
	public List<String> stemmedWordsList;
	public preprocessedObject(List<CoreLabel> tokens,List<String> posTags, 
			List<String> nerTags, List<String> stemmedWords) {
		this.tokenedList=tokens;
		this.LemmasList=posTags;
		this.nerTagsList=nerTags;
		this.stemmedWordsList=stemmedWords;
	}
}
