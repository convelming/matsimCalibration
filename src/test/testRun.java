package test;

import org.matsim.api.core.v01.Scenario;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.config.groups.PlansCalcRouteConfigGroup;
import org.matsim.core.controler.Controler;
import org.matsim.core.controler.OutputDirectoryHierarchy;
import org.matsim.core.scenario.ScenarioUtils;
import planParam.PlanParamCalibrationConfigGroup;
import planParam.PlanParamCalibrationModule;

/**
 * Author：Milu
 * 严重落后进度啦......
 * date：2024/6/26
 * project:matsimParamCalibration
 */
public class testRun {
    public static void main(String[] args) {
//        Config config = ConfigUtils.loadConfig("testInput2/gridConfig.xml");
        Config config = ConfigUtils.loadConfig("testInput2/gridConfigRandParam.xml", PlanParamCalibrationConfigGroup.createDefaultConfig());
        PlanParamCalibrationConfigGroup planParamCalibrationConfigGroup = (PlanParamCalibrationConfigGroup)config.getModules().get("PlanParamCalibrationConfigGroup");
//        planParamCalibrationConfigGroup.setGroundTruthSubPopulation("survey");
        planParamCalibrationConfigGroup.setGroundTruthSubPopulation("test");

        Scenario scenario = ScenarioUtils.loadScenario(config) ;
        Controler controler = new Controler( scenario ) ;
        controler.getConfig().controler().setLastIteration(20);
        controler.addOverridingModule(new PlanParamCalibrationModule(config));
        config.qsim().setRemoveStuckVehicles(true);
        config.qsim().setStuckTime(5.0);
        config.plansCalcRoute().setAccessEgressType(PlansCalcRouteConfigGroup.AccessEgressType.accessEgressModeToLink);
        controler.getConfig().controler().setOverwriteFileSetting( OutputDirectoryHierarchy.OverwriteFileSetting.deleteDirectoryIfExists );
//        controler.getConfig().controler().setOutputDirectory("/Users/convel/IdeaProjects/matsimParamCalibration/paramCalibration/outputOnlyWithCMAES0606");
        controler.getConfig().controler().setOutputDirectory("/Users/convel/desktop/output0627-Cali");
//        controler.getConfig().plans().setInputFile("/Users/convel/IdeaProjects/matsimParamCalibration/linkCount230427/pop/testPop.xml");
        controler.run();
    }
}
