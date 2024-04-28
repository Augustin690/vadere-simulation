package org.vadere.state.attributes.scenario;

import org.vadere.state.scenario.ScenarioElement;
import org.vadere.util.geometry.shapes.VShape;

/**
 * A MeasurementArea is a {@link ScenarioElement} which is used by many DataProcessors,
 * usually used to measure crowd density, number of agents, etc. A DataProcessor uses
 * the MeasurementArea's id to identify the area.
 */
public class AttributesMeasurementArea extends AttributesVisualElement {
	public AttributesMeasurementArea(){};

	public AttributesMeasurementArea(int id) {
		this.id = id;
	}

	public AttributesMeasurementArea(int id, VShape shape) {
		this.shape = shape;
		this.id = id;
	}
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		AttributesMeasurementArea that = (AttributesMeasurementArea) o;
		return id == that.id &&
				shape.equals(that.shape);
	}

	@Override
	public int hashCode() {
		int result = shape != null ? shape.hashCode() : 0;
		result = 31 * result + id;
		return result;
	}
}
