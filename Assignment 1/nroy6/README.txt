I used the Weka GUI to run my experiments.

DECISION TREES

	- J48 algorithm. 
	- All default settings. Change the confidence factor only. 

BOOSTING

	- AdaBoost algorithm with J48 as the classifier. 
	- All default settings. 
	- Change the confidence factor for J48. 
	- Change the number of iterations for AdaBoost.

NEURAL NETS

	- MultilayerPerceptron classifer

For Optical Digits:

	- Number of nodes/layer = 5
	- Number of layers = 1
	- Change the learning rate
	- Change the momentum
	- Change the training time (# of epochs)
	- Everything else default settings

For Phishing Websites:

	- Number of nodes/layer = a
	- Training time (# of epochs) = 500
	- Change the learning rate
	- Change the momentum
	- Change the number of hidden layers
	- Everything else default settings

K-NEAREST NEIGHBORS

IBK algorithm with EuclideanDistance

For Optical Digits:

	- Use weight = 1
	- Change the value of k
	- Everything else default settings

For Phishing Websites:

	- Use weight = 1 OR weight = 1/distance based on experiment
	- Change the value of k
	- Everything else default settings

SUPPORT VECTOR MACHINES

	- SMO algorithm. 
	- Change Kernel:
		> Polykernel: exponent = 1, everything else default settings
		> RBFkernel: gamma = 0.01, everything else default settings
	- Change the value of C

