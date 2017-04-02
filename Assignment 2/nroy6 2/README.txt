*********************************************************************************************************
												README
*********************************************************************************************************

---------------------------------------------------------------------------------------------------------
												PART - 1
---------------------------------------------------------------------------------------------------------

- ABAGAIL was used for all the tests of Randomized Optimization Algorithms. 
- For the ANN and ZeroR classifier, Weka was used.
- In order to recreate the Weka results:
	- Use the phishing-test.arff and phishing-train.arff files
	- Set the hidden layers to be 'a,a' for ANN
	- Set the learning rate to be 0.7 for ANN
	- Set the momentum to be 0.3 for ANN
	- Change the number of iterations as required
	- For any parameters not mentioned above, use default values

- AbaloneTest.java (submitted) is the specific file that was used to run all the tests. 
- In order to recreate the tests: 
	- Include the phishingTest.txt and phishingTrain.txt files in same path as AbaloneTest
	- Set the number of algorithms being run on line 40
	- Set the number of optimization problems required to be the same as the previous step on line 38
	- Set the number of Neural Networks required to be the same as the previous step on line 37
	- Set the number of training iterations on line 29
	- Set the names of algorithms being run on line 41 for the names printed to be accurate
	- In order to get train/test error at every X training iterations, copy the for loops in the main method between lines 77 and 98, and add them to the train method. Nest them in a conditional to check whether we are at a iteration that is a multiple of X. Add print statements accordingly to see the results.

---------------------------------------------------------------------------------------------------------
												PART - 2
---------------------------------------------------------------------------------------------------------

- ABAGAIL was used for all the tests
- In order to recreate the tests:
	- Use the default FourPeaksTest.java file and modify the parameters of each algorithm
	- Use the default TravelingSalesmanTest.java file and modify the parameters of each algorithm
	- Use the default KnapsackTest.java file and modify the parameters of each algorithm
	- To get the time taken, get the System Time right before train is called, and right after train is called, and subtract the values.
	- Change the number of iterations as required. 