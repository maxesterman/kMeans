{\rtf1\ansi\ansicpg1252\cocoartf1504\cocoasubrtf830
{\fonttbl\f0\fswiss\fcharset0 Helvetica;}
{\colortbl;\red255\green255\blue255;}
{\*\expandedcolortbl;;}
\margl1440\margr1440\vieww10800\viewh8400\viewkind0
\pard\tx720\tx1440\tx2160\tx2880\tx3600\tx4320\tx5040\tx5760\tx6480\tx7200\tx7920\tx8640\pardirnatural\partightenfactor0

\f0\fs24 \cf0 README:\
\
I preform NLP Analysis to cluster certain articles into a specified group. In this example, the group is a folder. These folders were given and the goal is to cluster these files into the file they were once in. Important note: The evaluation of kmeans was done with kmeans++. I also implemented a Kmeans algorithm, but kmeans++ provides more accurate results. \
\
\
Also, please read the section about the graphs, as I have a good explanation for the results. Essentially, the graphs exemplify the orthogonality of the results.\
\
\
\
\
\pard\tx720\tx1440\tx2160\tx2880\tx3600\tx4320\tx5040\tx5760\tx6480\tx7200\tx7920\tx8640\pardirnatural\partightenfactor0

\b \cf0 HOW TO RUN:\
\
\pard\tx720\tx1440\tx2160\tx2880\tx3600\tx4320\tx5040\tx5760\tx6480\tx7200\tx7920\tx8640\pardirnatural\partightenfactor0

\b0 \cf0 The best way to run this is to open up the folder in an Eclipse IDE and run enteringFile. \
\
PLEASE CHANGE THE FILES AND PATHS LISTED in the enteringFile section of the class. The main things that need to do is to change the path. \
\
\pard\tx720\tx1440\tx2160\tx2880\tx3600\tx4320\tx5040\tx5760\tx6480\tx7200\tx7920\tx8640\pardirnatural\partightenfactor0

\b \cf0 While k and cosine similarityare initial parameters, i would highly not recommend changing them. The model works well when using cosine similarity and k=3 is selected. 
\b0 \
\

\b CLASSES:
\b0 \
\

\b enteringFile:\
\pard\tx720\tx1440\tx2160\tx2880\tx3600\tx4320\tx5040\tx5760\tx6480\tx7200\tx7920\tx8640\pardirnatural\partightenfactor0

\b0 \cf0 \
This is a very simple class with the init parameters. Specifically, the only parameters used in this class are the folder paths and the path to save the topics.txt file.\
\
There are 5 main variables to set:\
\
1) rootFolder=The root directory for the folders (/Users/\'85../dataset_3/data/)\
2) The Name of the Folders (i.e. C1)\
3) outputPath: The topics.txt path\
4) int k: the number of clusters\
5) a boolean for running cosine similarity (cosine similarity when true, euclidean distance when false)\
\
\
\
\pard\tx720\tx1440\tx2160\tx2880\tx3600\tx4320\tx5040\tx5760\tx6480\tx7200\tx7920\tx8640\pardirnatural\partightenfactor0

\b \cf0 nlpconstruct (the main class on the program):\
\pard\tx720\tx1440\tx2160\tx2880\tx3600\tx4320\tx5040\tx5760\tx6480\tx7200\tx7920\tx8640\pardirnatural\partightenfactor0

\b0 \cf0 \
In this class, the whole program is called sequentially. This class only has the main method, with the parameters being the init parameters created from enteringFile. Through the rest of the file, it calls functions in the other classes and keeps the variables as parameters, throwing them into future classes.\
\
Important notes about this: 1) StanfordNLP pipeline is initialized in this class so that way it isn\'92t redone. for every file. 2) There is a nested for loop in this file. The outer for loop is to loop through the folders. The inner for loop is to for loop through the files within the folder.\
\
\pard\tx720\tx1440\tx2160\tx2880\tx3600\tx4320\tx5040\tx5760\tx6480\tx7200\tx7920\tx8640\pardirnatural\partightenfactor0

\b \cf0 preprocessedObject  (object of the preprocessed Lists):\
\pard\tx720\tx1440\tx2160\tx2880\tx3600\tx4320\tx5040\tx5760\tx6480\tx7200\tx7920\tx8640\pardirnatural\partightenfactor0

\b0 \cf0 \
This class is made the preprocessed object. Specifically there are four parameters:\
\
1) Tokens\
2) Lemmas\
3) Stems\
4) NERs\
\
\pard\tx720\tx1440\tx2160\tx2880\tx3600\tx4320\tx5040\tx5760\tx6480\tx7200\tx7920\tx8640\pardirnatural\partightenfactor0

\b \cf0 preprocessingStep (functions for preprocessing):\
\
\pard\tx720\tx1440\tx2160\tx2880\tx3600\tx4320\tx5040\tx5760\tx6480\tx7200\tx7920\tx8640\pardirnatural\partightenfactor0

\b0 \cf0 This class has all the main preprocessing functions:\
\
1) This takes the file path as the input and tries to get the text from this file path. This then tries to convert the textile into string.\
2) removeStopWords: In this function, a list of stop words is removed from the article text, known as currentText.\
3) createPreprocessObject: In this function, the preprocessed object is created to run the function. Specifically, it creates the preprocessedObject as listed above.\
4) removeStopWords: this is meant to remove stop words from the text\
5)removePunctuations: this removes all the punctuations in the object\
6)removePuncLessPeriods: removes all the punctuation less the stop words. This is important to ignore phrases that may come between the clauses when getting all the terms\
7) lets Stem: stems each work in the document\
8) makeNGrams: in the one, I make all the ngrams. For this hw, I did all n grams from size 2 to 4. I also did ngrams on the lemmas, just so that way it ignored the difference between grammar (rate vs rates), etc.\
9) makeAllNGramsLCase: this is done so that way all ngrams are lower case, so that way they can be compared correctly.\
10) findTermCount: this is to find the term count for all the ngrams that don\'92t have punctuations and are all lowercase\
11) getUselessNERs: Find all the NERs that have numerical values, or numerical style, like money, and erase them. They effect the document greatly.\
12) removingUselessNERGrams. Removes all the NER Grams that contain the unnecessary numerical information.\
\
\pard\tx720\tx1440\tx2160\tx2880\tx3600\tx4320\tx5040\tx5760\tx6480\tx7200\tx7920\tx8640\pardirnatural\partightenfactor0

\b \cf0 preprocessPerDoc (functions for feature selection):\
\
\pard\tx720\tx1440\tx2160\tx2880\tx3600\tx4320\tx5040\tx5760\tx6480\tx7200\tx7920\tx8640\pardirnatural\partightenfactor0

\b0 \cf0 This main class was to select the most used n terms per folder. Then when subsetting the tf-idf matrix, it would use the union of the top n terms for each folder. In this example, I used n=3, which meant there were nine features after feature selection.
\b \

\b0 \
\
\pard\tx720\tx1440\tx2160\tx2880\tx3600\tx4320\tx5040\tx5760\tx6480\tx7200\tx7920\tx8640\pardirnatural\partightenfactor0

\b \cf0 termMatrixandTfIdf (functions for creating the java class):\
\pard\tx720\tx1440\tx2160\tx2880\tx3600\tx4320\tx5040\tx5760\tx6480\tx7200\tx7920\tx8640\pardirnatural\partightenfactor0

\b0 \cf0 \
This class creates both the term matrix and the tf-idf Matrix. There are three main functions\
\
1) getNumTermsMatrix: This essentially gets the document term counts matrix. Note that there are two parameters: termCounts, which is a list of hash maps for all the terms and their counts in the documents, and termCountsPerFol, which contains the hash maps of all the terms that you want to select. \
\
The important thing to note is that while the matrix will only have the number of features of termCountsPerFolder, the class saves the num of ALL TERMS per document, i.e. termCounts. Thus, when computing the tf-idf, the number of terms per document are the number of ALL the TERMS in the document, and not just the selected features.\
\
\
2) getTfIdfMatrix: Using the numTermsMatrix as the parameter, it calculates, the tf idf. Note that tfidf is calculated with the full dataset, (using the metrics calculated in getNumTermsMatrix). Feature selection is then applied from there using kmeans\
\
3) getTopics: The point of this is to get the tf idf scores for all the topics. This uses insertion sort to sort the topics by the tf idf scores. This saves it into the topics.txt file. Note that you have to specifiy the topics.txt file in the enteringFile program.\
\
\
\pard\tx720\tx1440\tx2160\tx2880\tx3600\tx4320\tx5040\tx5760\tx6480\tx7200\tx7920\tx8640\pardirnatural\partightenfactor0

\b \cf0 dataClustering (class of computing kmeans):\
\
\pard\tx720\tx1440\tx2160\tx2880\tx3600\tx4320\tx5040\tx5760\tx6480\tx7200\tx7920\tx8640\pardirnatural\partightenfactor0

\b0 \cf0 In this class, I compute means to figure out the results of how the svd to see how it works. Ultimately, I use kmeans++ to do the calculation as it helps spread out the distance.\
\
As mentioned above, the features selected are the top terms per document after removing punctuations, step words, Useless NERs, and computing the lemmas and the n-grmas. The result turned out that the documents of each folder lied on each of their own 3-D spaces with none of the 3-D Spaces overlapping.\
\
The main function for this is kMeansInit which takes into parameters, the tf-idf matrix and the k value. Note that, while k=3, one of the init parameters in enteringFile.java is the k.\
\
\pard\tx720\tx1440\tx2160\tx2880\tx3600\tx4320\tx5040\tx5760\tx6480\tx7200\tx7920\tx8640\pardirnatural\partightenfactor0

\b \cf0 matrixCalculation (class for computing the SVD):\
\
\pard\tx720\tx1440\tx2160\tx2880\tx3600\tx4320\tx5040\tx5760\tx6480\tx7200\tx7920\tx8640\pardirnatural\partightenfactor0

\b0 \cf0 In this class, I create the Singular Value Decomposition. To do this, I use the Apache Commons Math package in java. The NLPConstruct program calls the matrixCalculation constructor accepting the tf-idf matrix as the parameter. Then this program calls getTwoColumnsOfU to get the two vectors of the U matrix for the two largest eigenvalues. \
\
The goal of this is to get the clusters and this works reasonably well. As I mentioned, the data is relatively orthogonal to each other after feature selection is applied to the tf-idf matrix. Thus, in a 2-D visualization, one folder is on the Y-axis, one folder is on the x-axis, and one is approximately at the center, showing the orthogonality of all these matrices.\
\
\
\pard\tx720\tx1440\tx2160\tx2880\tx3600\tx4320\tx5040\tx5760\tx6480\tx7200\tx7920\tx8640\pardirnatural\partightenfactor0

\b \cf0 evaluatingResults:\
\
\pard\tx720\tx1440\tx2160\tx2880\tx3600\tx4320\tx5040\tx5760\tx6480\tx7200\tx7920\tx8640\pardirnatural\partightenfactor0

\b0 \cf0 In this class, I evaluate the results. The following is predicted:\
\
1) A confusion matrix with the predicted folders on the rows and the actual folders on the columns\
2) Recall vector\
3) Precision Vector\
4) F1 Score\
\
These are all printed when running the program
\b \

\b0 \
\pard\tx720\tx1440\tx2160\tx2880\tx3600\tx4320\tx5040\tx5760\tx6480\tx7200\tx7920\tx8640\pardirnatural\partightenfactor0

\b \cf0 visualizationClass (for visualizing the data): \
\pard\tx720\tx1440\tx2160\tx2880\tx3600\tx4320\tx5040\tx5760\tx6480\tx7200\tx7920\tx8640\pardirnatural\partightenfactor0

\b0 \cf0 \
In this visualization class, I just have one main function that creates and produces the chart using JFreeChart. This constructor takes 5 arguments\
\
1) The Title\
2) The name of the folders\
3) The list of where the folders belong\
4) The SVD Reduced matrix as an array of doubles\
5) The color list\
\
I create two classes: one for the actual folders and one for the predicted folders.\
\
Note that the visualizations are such that one cluster lies on the y-axis, one of the x-axis, and one in the center. This makes sense because each document lies on its own 3D-axis, since it shares three key words that don\'92t (usually) exist in the other documents. Thus, they are generally orthogonal. The SVD shows these clusters. Thus, each one should lie on its own axis.\
\
If I were able to find a 3-D graph in Java, the third cluster would lie on that z axis. Nonetheless, that cluster settling at the center and the other two lying on the x- and y- axis shows tha\
\
\pard\tx720\tx1440\tx2160\tx2880\tx3600\tx4320\tx5040\tx5760\tx6480\tx7200\tx7920\tx8640\pardirnatural\partightenfactor0

\b \cf0 \
DEPENDECIES:\
\
\pard\tx720\tx1440\tx2160\tx2880\tx3600\tx4320\tx5040\tx5760\tx6480\tx7200\tx7920\tx8640\pardirnatural\partightenfactor0

\b0 \cf0 I forget what the exact binaries are called but here are the packages I used. \
\
1) Stanford Core NLP \
2) Apache commons Math\
3) JFreeChart\
4) Open NLP\
\
I put the zip files from the internet in that folder.\
\
\
}