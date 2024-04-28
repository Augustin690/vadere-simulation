package org.vadere.state.attributes.models.psychology.perception;

import org.vadere.state.attributes.Attributes;
import org.vadere.state.psychology.perception.types.Stimulus;

/**
 * <p>
 * A perception model models the perception of an agent.
 * </p>
 * <p>
 * An agent perceives stimuli from the environment like for example a sound or visual information (see {@link Stimulus} for stimuli available).
 * Basically, the perception model behaves like a filter for stimuli.
 * Only stimuli whose intensity exceed a certain threshold are forwarded to the {@link org.vadere.state.attributes.models.psychology.cognition.AttributesCognitionModel} .
 * Please find the Sensory threshold theory for background on the theory.
 * Note that any function could be implemented to model the sensory process.
 * In {@link AttributesSimplePerceptionModel} and {@link AttributesMultiPerceptionModel}, we model the sensory process in a very simplistic way.
 * Please see the respective model descriptions.
 * </p>
 */
public abstract class AttributesPerceptionModel extends Attributes {
}
