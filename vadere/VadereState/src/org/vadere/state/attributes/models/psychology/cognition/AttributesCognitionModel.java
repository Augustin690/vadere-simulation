package org.vadere.state.attributes.models.psychology.cognition;

import org.vadere.state.attributes.Attributes;

/**
 * <p>
 * In the cognition phase, agent make decision based on their perception.
 * </p>
 * <p>
 * A cognition model decides to which {@link org.vadere.state.psychology.cognition.SelfCategory} a {@link org.vadere.state.scenario.Pedestrian}
 * identifies to. From this {@link org.vadere.state.psychology.cognition.SelfCategory} a specific behavior derives.
 * E.g. if {@Link SelfCategory} = COOPERATIVE, pedestrians
 * can swap places.
 * </p>
 */
public abstract class AttributesCognitionModel extends Attributes {
}
