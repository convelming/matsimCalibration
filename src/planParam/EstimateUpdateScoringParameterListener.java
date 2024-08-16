package planParam;

import com.google.inject.Inject;
import optimizer.CMAES4PlanParamCalibration;
import optimizer.CMAES_Hansen;
import optimizer.CMAESwriter;
import optimizer.FitnessFunction4Cmaes;
import org.apache.commons.math3.optim.SimpleBounds;
import org.apache.commons.math3.util.DoubleArray;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.Plan;
import org.matsim.core.config.groups.PlanCalcScoreConfigGroup;
import org.matsim.core.controler.listener.ReplanningListener;
import org.matsim.core.replanning.StrategyManager;
import org.matsim.core.router.TripRouter;
import org.matsim.core.scoring.functions.*;
import utils.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Author：Milu
 * 严重落后进度啦......
 *
 *
 *
 * date：2024/1/9
 * project:matsimParamCalibration
 */
public class EstimateUpdateScoringParameterListener implements ReplanningListener{
    private static final Logger log = LogManager.getLogger(EstimateUpdateScoringParameterListener.class);
    // TODO: 2024/2/5 check if inject all these variables may cause potential problems???
    @Inject
    StrategyManager strategyManager;
    @Inject
    PlanCalcScoreConfigGroup planCalcScoreConfigGroup;
    @Inject
    PlanParamCalibrationConfigGroup planParamCalibrationConfigGroup;
    @Inject
    Scenario scenario;
    @Inject
    TripRouter tripRouter;


    ScoringParameters scoringParameters;// TODO: 2024/2/27  recorded parameters is set in Scoring Parameters and needed to be rewritten for null subpopulation

    public EstimateUpdateScoringParameterListener() {
        // couldn't think of something to write here...

    }

    @Override
    public void notifyReplanning(org.matsim.core.controler.events.ReplanningEvent replanningEvent) {
        // generate alternative plans for ground-truth samples
        log.info("start to generate subpopulation:" + planParamCalibrationConfigGroup.getGroundTruthSubPopulation() + "'s " + planParamCalibrationConfigGroup.getMaxAlternativePlans() + " alternative plans.");
        GenerateAlternativePlans gap = new GenerateAlternativePlans(planParamCalibrationConfigGroup, scenario, tripRouter);
        Map<Person, List<Plan>> fixedPersonPlans = gap.getFixedPersonPlans();
        CalcPlanScore.calAltPlanScores(fixedPersonPlans,scenario); // update each plans score
        // extract used variables and formulate cmaes variables
        log.info("Get required parameters for subpopulation ...");
        // todo    should activeate in maybe before iterationStart handler?
        //  Anyway it only needs to unlock once, but does it have any negative effects for other modules esp. default modules?
        if (replanningEvent.getIteration()==1) {
            ConfigGroupUtils.unlockPlanCalScoreConfigGroup(planCalcScoreConfigGroup);
        }
        if (replanningEvent.getIteration()<=planParamCalibrationConfigGroup.getDesignatedIteration()){

            PlanCalcScoreParamDoubleArrayMapping planCalcScoreParamDoubleArrayMapping = new PlanCalcScoreParamDoubleArrayMapping(planCalcScoreConfigGroup);
//        double[] planCalsArray = planCalcScoreParamDoubleArrayMapping.initializeDoubleArray();
        // optimize planCalParams parameters
            log.info("Optimize parameters Using CMA-ES ...");
//        CMAES cmaes = new CMAES(new Individual(planCalcScoreParamDoubleArrayMapping.initializeDoubleArray(), MaximumLogLikelihoodFunction.cal(fixedPersonPlans)),100,planCalcScoreParamDoubleArrayMapping.getDefaultParameterBounds(),0.15);
//        cmaes.evolve(100,fixedPersonPlans,scenario,0.1);

//            CMAES_Hansen cmaes = new CMAES_Hansen(planCalcScoreParamDoubleArrayMapping.initializeDoubleArray(),planCalcScoreParamDoubleArrayMapping.getDefaultParameterBounds());
//            cmaes.initialize();
//            cmaes.loopGenerations(fixedPersonPlans,scenario);
            // TODO: 2024/3/22  config cmaes max iteration in configgroup

//            double tmpMle = fitnessFunction4Cmaes.value(planCalcScoreParamDoubleArrayMapping.initializeDoubleArray());
            double[] designatedLowerBound = new double[]{-1000,-1000,1.00E-04,0.0001,-1000,-1000,-1000,50000,1.00E-05,29760,3600,29760,27780,5100,9000,-1000,-1000,-1000,-1000,-1,-200,-1000,-1000,-120,-1000,-100,-0.03,-1000,-1000,-1000,-1000,-10,-100,-1000,-1000,-1000,-1000,-2.5,-15};
            double[] designatedUpperBound = new double[]{-0.0001,-0.0001,1000,21,-0.0001,-0.0001,-0.0001,57200,3900,36960,10800,36960,34980,12300,16200,1000,1000,-0.0001,1000,-0.0001,-0.4,1000,1000,-0.001,1000,-0.5,-0.0001,1000,1000,1000,1000,-0.2,-2,1000,1000,-0.0001,1000,-0.5,-0.01};
            CMAES4PlanParamCalibration cmaes4PlanParamCalibration = new CMAES4PlanParamCalibration(100000, planParamCalibrationConfigGroup.getConvergeDiffThreshold(), planCalcScoreParamDoubleArrayMapping,
                    designatedLowerBound,designatedUpperBound);
            FitnessFunction4Cmaes fitnessFunction4Cmaes = new FitnessFunction4Cmaes(scenario,fixedPersonPlans);
            cmaes4PlanParamCalibration.run(fitnessFunction4Cmaes);
            // update the parameters in planCalcScoreConfigGroup
            log.info("Update parameters in planCalcScoreConfigGroup, writing results to file");
            double[] updatedParams = cmaes4PlanParamCalibration.getBestResults();
//            double newMle = fitnessFunction4Cmaes.value(updatedParams);
//            if(cmaes4PlanParamCalibration.bestFitness>tmpMle) { // make sure the values are at least no worse than the last iteration note: this does not make anysence 'cause the plan will alter!!!!!
            planCalcScoreParamDoubleArrayMapping.updateParamsPerDoubleArray(updatedParams);
            try {
                new CMAESwriter(cmaes4PlanParamCalibration).append(scenario.getConfig().controler().getOutputDirectory()+"/ouput_cmaes.csv", replanningEvent.getIteration());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
//            if (replanningEvent.getIteration()==this.scenario.getConfig().controler().getLastIteration()){
            try {
                new CMAESwriter(cmaes4PlanParamCalibration).writeCMAESstatics(scenario.getConfig().controler().getOutputDirectory()+"/ouput_cmaes_parameters"+replanningEvent.getIteration()+".csv");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
//            }
        }
    }

}