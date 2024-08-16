//package tripDairyCalibration;
//
//import org.apache.commons.math3.analysis.MultivariateFunction;
//import org.apache.commons.math3.optim.InitialGuess;
//import org.apache.commons.math3.optim.MaxEval;
//import org.apache.commons.math3.optim.PointValuePair;
//import org.apache.commons.math3.optim.SimpleBounds;
//import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
//import org.apache.commons.math3.optim.nonlinear.scalar.ObjectiveFunction;
//import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.CMAESOptimizer;
//import org.apache.commons.math3.random.JDKRandomGenerator;
//
///**
// * Author：Milu
// * 严重落后进度啦......
// * date：2023/12/13
// * project:huangpuScienceCity
// */
//public class MultiNomialLogit {
//
//        // Your data and parameters go here
//        private double[][] features;  // Feature matrix
//        private int[] labels;          // Labels (response variable)
//        private int numFeatures;       // Number of features
//        private int numClasses;        // Number of classes
//
//        public MultiNomialLogit(double[][] features, int[] labels, int numFeatures, int numClasses) {
//            this.features = features;
//            this.labels = labels;
//            this.numFeatures = numFeatures;
//            this.numClasses = numClasses;
//        }
//
//        public void fit() {
//            // Initial parameter guess
//            double[] initialParameters = new double[numFeatures * (numClasses - 1)];
//
//            // Set up the optimizer
//                CMAESOptimizer optimizer = new CMAESOptimizer(
//                        100,       // Lambda (population size)
//                        0.001,       // Sigma (step size)
//                        true,      // Stop fitness value
//                        1000,      // Max iterations
//                        2,      // Is active CMA
//                        new JDKRandomGenerator(),  // Random generator
//                        true,      // Generate invertible covariance matrix
//                        null
//                );
//
//            // Define the objective function
//            MultivariateFunction objectiveFunction = params -> {
//                // Calculate negative log-likelihood
//                double logLikelihood = calculateNegativeLogLikelihood(params);
//                return -logLikelihood; // Negative because the optimizer minimizes, but we want to maximize log-likelihood
//            };
//
//            // Optimize the objective function
//            PointValuePair result = optimizer.optimize(
//                    new MaxEval(10000),
//                    new ObjectiveFunction(objectiveFunction),
//                    GoalType.MAXIMIZE,
//                    new InitialGuess(initialParameters),
//                    new SimpleBounds(new double[numFeatures * (numClasses - 1)], new double[numFeatures * (numClasses - 1)])
//            );
//
//            // Extract the optimized parameters
//            double[] optimizedParameters = result.getPoint();
//
//            // Output the optimized parameters
//            System.out.println("Optimized Parameters:");
//            for (int i = 0; i < optimizedParameters.length; i++) {
//                System.out.println("Parameter " + i + ": " + optimizedParameters[i]);
//            }
//        }
//
//        private double calculateNegativeLogLikelihood(double[] params) {
//            // Implement the calculation of the negative log-likelihood here
//            // Use the provided features, labels, and parameters
//            // Return the negative log-likelihood value
//            // This method will depend on your specific multinomial logit model and dataset
//            // It typically involves looping through data points, calculating probabilities, and summing up the log-likelihood
//            // Refer to textbooks or online resources for details on multinomial logit likelihood calculation
//            return 0.0; // Placeholder, replace with actual implementation
//        }
//
//        public static void main(String[] args) {
//            // Example usage
//            double[][] features = {{1.0, 2.0}, {2.0, 3.0}, {3.0, 4.0}};
//            int[] labels = {0, 1, 2};
//            int numFeatures = 2;
//            int numClasses = 3;
//
//            MultiNomialLogit model = new MultiNomialLogit(features, labels, numFeatures, numClasses);
//            model.fit();
//        }
//
//}
