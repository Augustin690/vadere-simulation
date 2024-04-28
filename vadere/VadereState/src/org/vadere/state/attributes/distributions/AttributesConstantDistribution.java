package org.vadere.state.attributes.distributions;

import org.vadere.util.reflection.VadereAttribute;

/**
 * Constant Distribution is a distribution that returns always the same value.
 *
 * @author Lukas Gradl (lgradl@hm.edu), Aleksandar Ivanov
 */

public class AttributesConstantDistribution extends AttributesDistribution {
	/**
	 * updateFrequency is the value that is returned by the distribution.
	 * It is usually used as a time period between two samples.
	 */
	@VadereAttribute
	Double updateFrequency = 0.0;

	public AttributesConstantDistribution(){
		this(0.0);
	}
	public AttributesConstantDistribution(double updateFrequency){
		this.updateFrequency = updateFrequency;
	}
	public void setUpdateFrequency(double updateFrequency){
		this.updateFrequency = updateFrequency;
	}

	public double getUpdateFrequency(){
		return this.updateFrequency;
	}
}
