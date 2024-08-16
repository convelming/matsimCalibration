package utils;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.config.ConfigWriter;
import org.matsim.core.config.ReflectiveConfigGroup;
import org.matsim.core.config.groups.StrategyConfigGroup;
import org.matsim.core.controler.Controler;
import org.matsim.core.scenario.ScenarioUtils;

/**
 * Author：Milu
 * 严重落后进度啦......
 * date：2024/6/3
 * project:matsimParamCalibration
 */
public class TestConfig {
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            System.out.println(Math.random());
        }
//        Config config = ConfigUtils.createConfig();
//        config.plans().setInputFile("/users/convel/desktop/lastItersInputfile.xml");
//        config.controler().setLastIteration(2);
//        config.transit().setUseTransit(true);
//        config.transit().setTransitScheduleFile("/users/convel/desktop/transitSchedule.xml");
//        config.transit().setVehiclesFile("/users/convel/desktop/transitVehicles.xm");
//        config.network().setInputFile("/users/convel/desktop/newNetwork.xml");
//        config.strategy().setFractionOfIterationsToDisableInnovation(1.0);
//        config.strategy().setMaxAgentPlanMemorySize(5);
//        config.global().setCoordinateSystem("epsg:3857");
//        config.global().setNumberOfThreads(20);
//        config.parallelEventHandling().setNumberOfThreads(10);
//        config.qsim().setNumberOfThreads(10);
//
//        StrategyConfigGroup.StrategySettings strategySettings = new StrategyConfigGroup.StrategySettings();
//        strategySettings.setStrategyName("BestScore");//SelectRandom BestScore KeepLastSelected ChangeExpBeta SelectExpBeta SelectPathSizeLogit
//        strategySettings.setWeight(9999.0);
//        config.strategy().addStrategySettings(strategySettings);
//        // remove KeepLastSelected if there is or set bestscore large enough
//
//
//        new ConfigWriter(config).write("/users/convel/desktop/testConfig.xml");
//        Scenario scenario = ScenarioUtils.loadScenario(config) ;
//        Controler controler = new Controler( scenario ) ;
//        controler.run();
    }
}
