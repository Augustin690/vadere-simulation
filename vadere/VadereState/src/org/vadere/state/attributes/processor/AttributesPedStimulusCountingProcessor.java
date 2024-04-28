package org.vadere.state.attributes.processor;

import org.vadere.util.geometry.shapes.VShape;

public class AttributesPedStimulusCountingProcessor extends AttributesProcessor {

	private String informationFilter = "";
	private boolean isRegexFilter = false;
	private double stopIfPercentageIsInformed = 0.95;
	private int numberOfAdditionalTimeFrames = 20;
	private int measurementAreaId = -1;
	private boolean forceSimulationEnd = false;

	public String getInformationFilter() {
		return informationFilter;
	}

	public void setInformationFilter(String informationFilter) {
		checkSealed();
		this.informationFilter = informationFilter;
	}

	public boolean isRegexFilter() {
		return isRegexFilter;
	}

	public void setRegexFilter(boolean regexFilter) {
		checkSealed();
		isRegexFilter = regexFilter;
	}

	public int getNumberOfAdditionalTimeFrames() {
		return numberOfAdditionalTimeFrames;
	}

	public double getStopIfPercentageIsInformed() {
		return stopIfPercentageIsInformed;
	}


	public int getMeasurementAreaId() {
		return measurementAreaId;
	}

	public boolean isForceSimulationEnd() {
		return forceSimulationEnd;
	}
}
