package org.vadere.state.attributes.distributions;


/**
 * Single Spawn Distribution is a distribution that only allows one event to occur.
 *
 * @author Lukas Gradl (lgradl@hm.edu), Ludwig Jaeck
 */

public class AttributesSingleSpawnDistribution extends AttributesDistribution {
	public double getSpawnTime() {
		return spawnTime;
	}

	public void setSpawnTime(double spawnTime) {
		this.spawnTime = spawnTime;
	}

	/**
	 * spawnTime describes the time the event occurs.
	 */
	Double spawnTime = 0.0;

}
