package org.vadere.state.attributes.distributions;


/**
 * Poisson Distribution is a distribution that returns a number of events per second.
 *
 * @author Lukas Gradl (lgradl@hm.edu), Ludwig Jaeck
 */

public class AttributesPoissonDistribution extends AttributesDistribution {
	public double getNumberPedsPerSecond() {
		return numberPedsPerSecond;
	}

	public void setNumberPedsPerSecond(double numberPedsPerSecond) {
		this.numberPedsPerSecond = numberPedsPerSecond;
	}

	/**
	 * numberPedsPerSecond describes the number of events per second.
	 */
	Double numberPedsPerSecond = 0.0;
}
