package org.vadere.state.attributes;

import org.checkerframework.framework.qual.Unused;
import org.vadere.annotation.helptext.HelpIgnore;
import org.vadere.util.reflection.VadereAttribute;

import java.util.Objects;
import java.util.Random;

/**
 * Provides attributes for the simulation, like visualizationEnabled and
 * writeSimulationData.
 */
public class AttributesSimulation extends Attributes {

	/** Store the members of this class under this key in the JSON file. */
	@VadereAttribute(exclude = true)
	@HelpIgnore
	public static final String JSON_KEY = "attributesSimulation";
	/**
	 * <i>finishTime</i> is the time in seconds when the simulation should stop.
	 */
	private double finishTime = 500;
	/**
	 * <i>simTimeStepLength</i> is the length of one simulation time step in seconds.
	 */
	private double simTimeStepLength = 0.4;
	/**
	 * <i>realTimeSimTimeRatio</i> is the ratio between real time and simulation time.
	 */
	private double realTimeSimTimeRatio = 0.1;
	/**
	 * <i>writeSimulationData</i> indicates whether the simulation output data should be written to the filesystem.
	 */
	private boolean writeSimulationData = true;
	/**
	 * <i>visualizationEnabled</i> enables visualization of the potential fields and enables hardware independent rendering.
	 */
	private boolean visualizationEnabled = true;
	/**
	 * <i>printFPS</i> is not used currently
	 */
	private boolean printFPS = false;
	/**
	 * <i>digitsPerCoordinate</i> is the number of digits after the decimal point for the coordinates.
	 */
	private int digitsPerCoordinate = 2;
	/**
	 * <i>useFixedSeed</i> indicates whether a fixed seed should be used for the simulation.
	 * <br>
	 * A seed defines how the random number generator in Vadere determines random numbers.
	 * Random numbers are used, e.g., by the optimal steps model when the next step of an agent
	 * is calculated. If useFixedSeed is true and a simulation is repeated without altering any parameters,
	 * the result remains exactly the same. If useFixedSeed is false, e.g., the pedestrians’
	 * spawning positions, their trajectories, etc. may vary
	 */
	private boolean useFixedSeed = true;
	/**
	 * <i>fixedSeed</i> determines the random numbers used in the simulation uniquely
	 * <br>
	 * See <i>useFixedSeed</i>. Only plays a role if useFixedSeed is true.
	 */
	private long fixedSeed = new Random().nextLong();
	/**
	 * <i>simulationSeed</i> is the seed used for the simulation.
	 * <br>
	 * Editing this parameter has no effect on a simulation. It only returns the seed that has been used
	 * for simulation in the scenario output file (if <i>useFixedSeed</i> is true, simulationSeed equals <i>fixedSeed</i>;
	 * if <i>useFixed</i> is false, <i>simulationSeed</i> is some arbitrary number).
	 */
	private long simulationSeed;

	// Getter...

	public double getFinishTime() {
		return finishTime;
	}

	public double getSimTimeStepLength() {
		return simTimeStepLength;
	}

	public double getRealTimeSimTimeRatio() {
		return realTimeSimTimeRatio;
	}

	public boolean isWriteSimulationData() {
		return writeSimulationData;
	}

	public boolean isVisualizationEnabled() {
		return visualizationEnabled;
	}

	public boolean isPrintFPS() {
		return printFPS;
	}

	public int getDigitsPerCoordinate() {
		return digitsPerCoordinate;
	}

	public boolean isUseFixedSeed() {
		return useFixedSeed;
	}

	public long getFixedSeed() {
		return fixedSeed;
	}

	public long getSimulationSeed() {
		return simulationSeed;
	}

	// Setters...

	public void setFinishTime(double finishTime) {
		checkSealed();
		this.finishTime = finishTime;
	}

	public void setSimTimeStepLength(double simTimeStepLength) {
		checkSealed();
		this.simTimeStepLength = simTimeStepLength;
	}

	public void setRealTimeSimTimeRatio(double realTimeSimTimeRatio) {
		checkSealed();
		this.realTimeSimTimeRatio = realTimeSimTimeRatio;
	}

	public void setWriteSimulationData(boolean writeSimulationData) {
		checkSealed();
		this.writeSimulationData = writeSimulationData;
	}

	public void setVisualizationEnabled(boolean visualizationEnabled) {
		checkSealed();
		this.visualizationEnabled = visualizationEnabled;
	}

	public void setPrintFPS(boolean printFPS) {
		checkSealed();
		this.printFPS = printFPS;
	}


	public void setDigitsPerCoordinate(int digitsPerCoordinate) {
		checkSealed();
		this.digitsPerCoordinate = digitsPerCoordinate;
	}

	public void setUseFixedSeed(boolean useFixedSeed) {
		checkSealed();
		this.useFixedSeed = useFixedSeed;
	}

	public void setFixedSeed(long fixedSeed) {
		checkSealed();
		this.fixedSeed = fixedSeed;
	}

	public void setSimulationSeed(long simulationSeed) {
		checkSealed();
		this.simulationSeed = simulationSeed;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		AttributesSimulation that = (AttributesSimulation) o;
		return Double.compare(that.finishTime, finishTime) == 0 &&
				Double.compare(that.simTimeStepLength, simTimeStepLength) == 0 &&
				Double.compare(that.realTimeSimTimeRatio, realTimeSimTimeRatio) == 0 &&
				writeSimulationData == that.writeSimulationData &&
				visualizationEnabled == that.visualizationEnabled &&
				printFPS == that.printFPS &&
				digitsPerCoordinate == that.digitsPerCoordinate &&
				useFixedSeed == that.useFixedSeed &&
				fixedSeed == that.fixedSeed &&
				simulationSeed == that.simulationSeed;
	}

	@Override
	public int hashCode() {
		return Objects.hash(finishTime, simTimeStepLength, realTimeSimTimeRatio, writeSimulationData, visualizationEnabled, printFPS, digitsPerCoordinate, useFixedSeed, fixedSeed, simulationSeed);
	}
}
