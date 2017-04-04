Everything except the Kurtosis graphs for the ICA section was done with WEKA. The graphs were generated in MATLAB.

Install the plugin for ICA, found at: https://github.com/cgearhart/students-filters

**Unless mentioned otherwise, use default WEKA settings**

K-MEANS (Euclidean Distance)

	- Cluster > Clusterer > Simple K-Means algorithm
	- Ignore Attributes > {Select Class Attribute} > Select
	- Classes to clusters evaluation > {Select Class Attribute}
	- Change the value for k as required

EXPECTATION MAXIMIZATION (Euclidean Distance)

	- Cluster > Clusterer > EM algorithm
	- Ignore Attributes > {Select Class Attribute} > Select 
	- Classes to clusters evaluation > {Select Class Attribute}
	- Change the value for numClusters as required 

PRINCIPAL COMPONENT ANALYSIS

	- Preprocess > Filter > Unsupervised > Attribute > PrincipalComponents
	- Set maximumAttributeNames = -1
	- Set varianceCovered = 0.9
	- Apply and Save
	- To run Clustering on the reduced data, load the saved .arff file, and rerun K-MEANS and EM
	- To add the clusters as a feature, after running clustering on the reduced data:
		- Right click > Visualize cluster assignments
		- Save the .arff file

RANDOMIZED PROJECTION

	- Preprocess > Filter > Unsupervised > Attribute > RandomProjection
	- Set percent = 40
	- Change seed value for multiple trials {10, 25, 42} as required
	- Apply and Save
	- To run Clustering on the reduced data, load the saved .arff file, and rerun K-MEANS and EM
	- To add the clusters as a feature, after running clustering on the reduced data:
		- Right click > Visualize cluster assignments
		- Save the .arff file

INFORMATION GAIN ATTRIBUTE EVALUATION

	- Select Attributes > Attribute Evaluator > InfoGainAttributeEval
	- Change threshold for Search Method > Ranker as required
	- Start
	- Right Click > Save reduced data
	- To run Clustering on the reduced data, load the saved .arff file, and rerun K-MEANS and EM
	- To add the clusters as a feature, after running clustering on the reduced data:
		- Right click > Visualize cluster assignments
		- Save the .arff file

INDEPENDENT COMPONENT ANALYSIS (Only works for Wine Quality, not for OptDigits)

	- Preprocess > Filter > Unsupervised > Attribute > IndependentComponents
	- Set numAttributes = 5
	- Set tolerance = 0.01
	- Apply and Save
	- To run Clustering on the reduced data, load the saved .arff file, and rerun K-MEANS and EM
	- To add the clusters as a feature, after running clustering on the reduced data:
		- Right click > Visualize cluster assignments
		- Save the .arff file

INDEPENDENT COMPONENT ANALYSIS KURTOSIS GRAPHS (MATLAB)

	- Open the ML folder
	- Run the following 4 files in MATLAB to plot the graphs:

		- makeICAOptDigBefore.m
		- makeICAOptDigAfter.m
		- makeICAWineQualBefore.m
		- makeICAWineQualAfter.m

	- Make sure the .data files are in the same path. 

NEURAL NETWORK (WEKA)

	- Set Learning Rate = 0.4
	- Set Momentum = 0.5
	- Set Hidden Layers = 5
	- Set trainingTime = 800
	- Test options > Percentage split > 70 %
	- In the dropdown menu above Start, make sure the {Class Attribute} is chosen rather than the Cluster



