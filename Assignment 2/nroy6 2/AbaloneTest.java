package opt.test;

import func.nn.feedfwd.*;
import func.nn.backprop.*;
//import func.nn.feedfwd.FeedForwardNeuralNetworkFactory;
import opt.*;
import opt.example.*;
import opt.prob.MIMIC;
import opt.ga.*;
import shared.*;

import java.util.*;
import java.io.*;
import java.text.*;

/**
 * Implementation of randomized hill climbing, simulated annealing, and genetic algorithm to
 * find optimal weights to a neural network that is classifying abalone as having either fewer
 * or more than 15 rings.
 *
 * @author Hannah Lau
 * @version 1.0
 */
public class AbaloneTest {
//    private static Instance[] instances = initializeInstances();
    private static Instance[] testInstances = getTestInstances();
    private static Instance[] trainInstances = getTrainInstances();

    private static int inputLayer = 30, hiddenLayer = 16, outputLayer = 1, trainingIterations = 5000;
    private static FeedForwardNeuralNetworkFactory factory = new FeedForwardNeuralNetworkFactory();
//    private static BackPropagationNetworkFactory factory = new BackPropagationNetworkFactory();

    private static ErrorMeasure measure = new SumOfSquaresError();

    private static DataSet set = new DataSet(testInstances);

    private static FeedForwardNetwork networks[] = new FeedForwardNetwork[10];
    private static NeuralNetworkOptimizationProblem[] nnop = new NeuralNetworkOptimizationProblem[3];

    private static OptimizationAlgorithm[] oa = new OptimizationAlgorithm[1];
    private static String[] oaNames = {"OA1", "OA2","OA3"};
    private static String results = "";

    private static DecimalFormat df = new DecimalFormat("0.000");

    public static void main(String[] args) {
        for (int i = 0; i < oa.length; i++) {
            networks[i] = factory.createClassificationNetwork(new int[]{inputLayer, hiddenLayer,
                    hiddenLayer, outputLayer});
            nnop[i] = new NeuralNetworkOptimizationProblem(set, networks[i], measure);
        }

        oa[0] = new RandomizedHillClimbing(nnop[0]);

//        double temperature = 1E2;
//        double coolingRate = 0.02;
//        oa[0] = new SimulatedAnnealing(temperature, coolingRate, nnop[0]);

//        int popSize = 100;
//        int toMate = 50;
//        int toMutate = 10;
//        oa[0] = new StandardGeneticAlgorithm(popSize, toMate, toMutate, nnop[0]);

        for (int i = 0; i < oa.length; i++) {
            System.out.println("Iteration" + i);
            double start = System.nanoTime(), end, trainingTime, testingTime, correct = 0, incorrect = 0;
            train(oa[i], networks[i], oaNames[i]); //trainer.train();
            end = System.nanoTime();
            trainingTime = end - start;
            trainingTime /= Math.pow(10, 9);

            Instance optimalInstance = oa[i].getOptimal();
            networks[i].setWeights(optimalInstance.getData());

            double predicted, actual;
            start = System.nanoTime();
            for (Instance instance : testInstances) {
                networks[i].setInputValues(instance.getData());
                networks[i].run();

                predicted = Double.parseDouble(instance.getLabel().toString());
                actual = Double.parseDouble(networks[i].getOutputValues().toString());

                double trash = Math.abs(predicted - actual) < 0.5 ? correct++ : incorrect++;

            }

            double trainPredicted, trainActual, trainCorrect = 0, trainIncorrect = 0;
            for (Instance instance : trainInstances) {
                networks[i].setInputValues(instance.getData());
                networks[i].run();

                trainPredicted = Double.parseDouble(instance.getLabel().toString());
                trainActual = Double.parseDouble(networks[i].getOutputValues().toString());

                double trash = Math.abs(trainPredicted - trainActual) < 0.5 ? trainCorrect++ : trainIncorrect++;

            }
            end = System.nanoTime();
            testingTime = end - start;
            testingTime /= Math.pow(10, 9);

            results += "\n--------------------------------------------\nResults for " + oaNames[i]
                    + ": \n--------------------------------------------\nCorrectly classified (TEST) " + correct
                    + " instances." + "\nIncorrectly classified (TEST) " + incorrect
                    + " instances.\nPercent correctly classified (TEST): " + df.format(correct / (correct + incorrect) * 100)
                    + "%\n" + "\nCorrectly classified (TRAIN)" + trainCorrect + " instances."
                    + "\nIncorrectly classified (TRAIN) " + trainIncorrect
                    + " instances.\nPercent correctly classified (TRAIN): "
                    + df.format(trainCorrect/ (trainCorrect + trainIncorrect) * 100) + "%\n" + "\nTraining time: "
                    + df.format(trainingTime) + " seconds\nTesting time: " + df.format(testingTime) + " seconds\n";



        }

        System.out.println(results);
    }

    private static void train(OptimizationAlgorithm oa, FeedForwardNetwork network, String oaName) {
        System.out.println("\nError results for " + oaName + "\n---------------------------");
        String otherResults = "";

        for (int i = 0; i < trainingIterations; i++) {
            oa.train();

            double error = 0;
            for (Instance instance : trainInstances) {
                network.setInputValues(instance.getData());
                network.run();

                Instance output = instance.getLabel(), example = new Instance(network.getOutputValues());
                example.setLabel(new Instance(Double.parseDouble(network.getOutputValues().toString())));
                error += measure.value(output, example);
            }

            System.out.println(df.format(error));
        }
    }

    private static Instance[] getTestInstances() {
        double[][][] attributes = new double[3317][][];

        try {
            BufferedReader br = new BufferedReader(new FileReader(new File("src/opt/test/phishingTest.txt")));

            for (int i = 0; i < attributes.length; i++) {
                Scanner scan = new Scanner(br.readLine());
                scan.useDelimiter(",");

                attributes[i] = new double[2][];
                attributes[i][0] = new double[30]; // 30 attributes
                attributes[i][1] = new double[1];

                for (int j = 0; j < 30; j++)
                    attributes[i][0][j] = Double.parseDouble(scan.next());

                attributes[i][1][0] = Double.parseDouble(scan.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Instance[] instances = new Instance[attributes.length];

        for (int i = 0; i < instances.length; i++) {
            instances[i] = new Instance(attributes[i][0]);
            // classifications range from 0 to 30; split into 0 - 14 and 15 - 30
            instances[i].setLabel(new Instance(attributes[i][1][0] < 1 ? 0 : 1));
        }

        return instances;
    }

    private static Instance[] getTrainInstances() {
        double[][][] attributes = new double[7738][][];

        try {
            BufferedReader br = new BufferedReader(new FileReader(new File("src/opt/test/phishingTrain.txt")));

            for (int i = 0; i < attributes.length; i++) {
                Scanner scan = new Scanner(br.readLine());
                scan.useDelimiter(",");

                attributes[i] = new double[2][];
                attributes[i][0] = new double[30]; // 30 attributes
                attributes[i][1] = new double[1];

                for (int j = 0; j < 30; j++)
                    attributes[i][0][j] = Double.parseDouble(scan.next());

                attributes[i][1][0] = Double.parseDouble(scan.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Instance[] instances = new Instance[attributes.length];

        for (int i = 0; i < instances.length; i++) {
            instances[i] = new Instance(attributes[i][0]);
            // classifications range from 0 to 30; split into 0 - 14 and 15 - 30
            instances[i].setLabel(new Instance(attributes[i][1][0] < 1 ? 0 : 1));
        }

        return instances;
    }

    private static Instance[] initializeInstances() {

        double[][][] attributes = new double[11055][][];

        try {
            BufferedReader br = new BufferedReader(new FileReader(new File("src/opt/test/phishing.txt")));

            for (int i = 0; i < attributes.length; i++) {
                Scanner scan = new Scanner(br.readLine());
                scan.useDelimiter(",");

                attributes[i] = new double[2][];
                attributes[i][0] = new double[30]; // 30 attributes
                attributes[i][1] = new double[1];

                for (int j = 0; j < 30; j++)
                    attributes[i][0][j] = Double.parseDouble(scan.next());

                attributes[i][1][0] = Double.parseDouble(scan.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Instance[] instances = new Instance[attributes.length];

        for (int i = 0; i < instances.length; i++) {
            instances[i] = new Instance(attributes[i][0]);
            // classifications range from 0 to 30; split into 0 - 14 and 15 - 30
            instances[i].setLabel(new Instance(attributes[i][1][0] < 0 ? 0 : 1));
        }

        return instances;
    }
}
