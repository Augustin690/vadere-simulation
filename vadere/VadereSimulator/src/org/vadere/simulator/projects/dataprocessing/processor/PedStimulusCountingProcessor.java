package org.vadere.simulator.projects.dataprocessing.processor;

import org.vadere.annotation.factories.dataprocessors.DataProcessorClass;
import org.vadere.simulator.control.simulation.SimulationState;
import org.vadere.simulator.projects.dataprocessing.ProcessorManager;
import org.vadere.simulator.projects.dataprocessing.datakey.EventtimePedestrianIdKey;
import org.vadere.simulator.projects.dataprocessing.datakey.TimestepKey;
import org.vadere.state.attributes.AttributesSimulation;
import org.vadere.state.attributes.processor.AttributesAreaProcessor;
import org.vadere.state.attributes.processor.AttributesPedStimulusCountingProcessor;
import org.vadere.state.scenario.MeasurementArea;
import org.vadere.state.scenario.Pedestrian;
import org.vadere.state.simulation.InformationDegree;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@DataProcessorClass(label = "PedStimulusCountingProcessor")
public class PedStimulusCountingProcessor extends DataProcessor<TimestepKey, InformationDegree> {

	private Predicate<Pedestrian> filter_by_stimuli;
	private Pattern filter_pattern = null;
	private double stopIfPercentageIsInformed;
	private int numberOfAdditionalSteps;
	private int lastStep = -1;
	private boolean fullfilled = false;
	private MeasurementArea measurementArea;
	private boolean forceSimulationEnd = false;

	public PedStimulusCountingProcessor() {
		super("numberPedsInformed", "numberPedsAll", "percentageInformed");
	}

	@Override
	public void init(ProcessorManager manager) {
		super.init(manager);
		AttributesPedStimulusCountingProcessor  attr = getAttributes();
		this.measurementArea = manager.getMeasurementArea(attr.getMeasurementAreaId(), false);

		if (getAttributes().isRegexFilter()){
			filter_pattern = Pattern.compile(getAttributes().getInformationFilter());
			filter_by_stimuli = ped -> ped.getKnowledgeBase().knowsAbout(filter_pattern);
		} else {
			filter_by_stimuli = ped -> ped.getKnowledgeBase().knowsAbout(getAttributes().getInformationFilter());
		}

		stopIfPercentageIsInformed = attr.getStopIfPercentageIsInformed();
		numberOfAdditionalSteps = attr.getNumberOfAdditionalTimeFrames();
		forceSimulationEnd = attr.isForceSimulationEnd();

	}

	@Override
	protected void doUpdate(SimulationState state) {
		// measure information degree only in the interesting area
		Collection<Pedestrian> pedestrians = state.getTopography().getElements(Pedestrian.class);
		List<Pedestrian> peds = pedestrians.stream().filter(pedestrian -> this.measurementArea.getShape().contains(pedestrian.getPosition())).collect(Collectors.toList());

		int numberPedsInformed = (int) peds.stream().filter(p -> filter_by_stimuli.test(p)).count();
		// assumption: only one stimulus is provided
		int numberPedsAll = (int) peds.stream().filter(p-> p.getFootstepHistory().getFootSteps().size() > 1).count();

		numberPedsAll = Math.max(numberPedsAll,numberPedsInformed);
		InformationDegree informationDegree =  new InformationDegree(numberPedsInformed, numberPedsAll);

		// force stop before simulation time defined in json is reached.
		if (forceSimulationEnd) {
			if (informationDegree.getPercentageInformed() >= stopIfPercentageIsInformed && !fullfilled) {
				lastStep = state.getStep() + numberOfAdditionalSteps;
				fullfilled = true;
			}

			if (fullfilled && state.getStep() >= lastStep) {
				setStopSimBeforeSimFinish(true);
			}

		}

		putValue(new TimestepKey(state.getStep()), informationDegree);


	}



	@Override
	public AttributesPedStimulusCountingProcessor getAttributes() {
		if(super.getAttributes() == null) {
			setAttributes(new AttributesPedStimulusCountingProcessor());
		}
		return (AttributesPedStimulusCountingProcessor)super.getAttributes();
	}


	@Override
	public String[] toStrings(TimestepKey key) {
		String[] informationDegrees = this.getValue(key).getValueString();
		return informationDegrees;
	}





}
