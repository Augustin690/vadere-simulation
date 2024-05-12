package org.vadere.state.attributes.models;

import java.util.Arrays;
import java.util.List;

import org.vadere.annotation.factories.attributes.ModelAttributeClass;
import org.vadere.state.attributes.Attributes;

@ModelAttributeClass
public class AttributesSIRG extends Attributes {

	private int infectionsAtStart = 0;
	private double infectionRate = 0.01;

	private double infectionMaxDistance = 1;
	// Task 5: Recovery rate
	private double recoveryRate = 0.01;

	// Task 5: infectionRate
	private double infectionRateWhenAdded = 0;
	public double getInfectionRateWhenAdded() {
		return infectionRateWhenAdded;
	}


	public int getInfectionsAtStart() { return infectionsAtStart; }

	public double getInfectionRate() {
		return infectionRate;
	}


	// Task 5-2: Get recovery rate

	public double getRecoveryRate() {
		return recoveryRate;
	}


	public double getInfectionMaxDistance() {
		return infectionMaxDistance;
	}

}
