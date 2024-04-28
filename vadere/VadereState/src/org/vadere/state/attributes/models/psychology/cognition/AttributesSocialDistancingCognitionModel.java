package org.vadere.state.attributes.models.psychology.cognition;

/**
 * This model decides whether agents perform social distancing or not.
 * It performs social distancing by modifying the potential strategy.
 * (See <a href="https://doi.org/10.17815/CD.2021.116">)
 */
public class AttributesSocialDistancingCognitionModel extends AttributesCognitionModel {
    /**
     * <repulsionFactor> factor for social distancing
     */
    private double repulsionFactor = 1.6444;
    /**
     * <i> repulsionIntercept</i> sum of the parameters found with regression 0.0658c + 0.6161
     * where the corridor width c is fixed c = 2
     */
    private double repulsionIntercept  = 0.7477;
    /**
     * <minDistance> minimum distance for social distancing
     */
    private double minDistance = 1.25;
    /**
     * <maxDistance> maximum distance for social distancing
     */
    private double maxDistance = 2.0;

    public double getRepulsionFactor() {
        return repulsionFactor;
    }

    public void setRepulsionFactor(double repulsionFactor) {
        this.repulsionFactor = repulsionFactor;
    }

    public double getRepulsionIntercept() {
        return repulsionIntercept;
    }

    public void setRepulsionIntercept(double repulsionIntercept) {
        this.repulsionIntercept = repulsionIntercept;
    }

    public double getMinDistance() {
        return minDistance;
    }

    public void setMinDistance(double minDistance) {
        this.minDistance = minDistance;
    }

    public double getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(double maxDistance) {
        this.maxDistance = maxDistance;
    }
}
