package org.vadere.state.attributes.distributions;


import org.vadere.util.reflection.VadereAttribute;

/**
 * Binomial Distribution is a distribution that returns a number of events per second.
 * @author Aleksandar Ivanov(ivanov0@hm.edu), Lukas Gradl (lgradl@hm.edu), Ludwig Jaeck
 */

public class AttributesBinomialDistribution extends AttributesDistribution {
	/**
	 * trials describes the number of trials.
	 */
	@VadereAttribute
	private Integer trials = 0;
	/**
	 * p describes the probability of success.
	 */
	@VadereAttribute
	private Double p = 0.0;

	public int getTrials() {
		return trials;
	}

	public void setTrials(int trials) {
		this.trials = trials;
	}

	public double getP() {
		return p;
	}

	public void setP(double p) {
		this.p = p;
	}
}
