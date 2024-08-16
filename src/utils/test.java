package utils;

import org.apache.commons.lang3.event.EventUtils;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.network.Network;
import org.matsim.api.core.v01.population.Plan;
import org.matsim.api.core.v01.population.PlanElement;
import org.matsim.api.core.v01.population.Population;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.controler.Controler;
import org.matsim.core.network.NetworkUtils;
import org.matsim.core.population.PopulationUtils;
import org.matsim.core.population.routes.GenericRouteImpl;
import org.matsim.core.router.PlanRouter;
import org.matsim.core.router.TripRouter;
import org.matsim.core.router.TripStructureUtils;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.core.utils.timing.TimeInterpretation;
import org.matsim.core.utils.timing.TimeTracker;
import org.matsim.counts.CountsModule;
import org.matsim.facilities.FacilitiesUtils;
import org.matsim.facilities.FacilitiesWriter;
import org.matsim.facilities.MatsimFacilitiesReader;

import java.util.*;

/**
 * Author：Milu
 * 严重落后进度啦......
 * date：2024/1/17
 * project:matsimParamCalibration
 */
public class test {
    public static void main(String[] args) {
        String file= "/Users/convel/Desktop/pt_route_data/Canton/Nansha/output/output_network.xml.gz";
        Network network = NetworkUtils.readNetwork(file);
        Set<String> roadTypes = new HashSet<>();

    }
}
