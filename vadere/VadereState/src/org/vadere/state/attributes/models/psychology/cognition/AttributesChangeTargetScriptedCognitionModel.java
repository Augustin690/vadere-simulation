package org.vadere.state.attributes.models.psychology.cognition;
/**
 * <p>
 * The ChangeTargetScriptedCognitionModel changes the target id of agents. The stimulus
 * {@link org.vadere.state.psychology.perception.types.ChangeTargetScripted} describes at which
 * simulation time agents should change their target.
 * </p>
 * <p>
 * The target id is changed directly here at cognition layer and on locomotion layer an agent just performs a step.
 * This is a workaround, because a target change is always associated with a step in the locomotion layer.
 * If an agent does not take a step in the current time step and the target change is scheduled then,
 * the target change would be skipped. This is due to our update scheme.
 * </p>
 */
public class AttributesChangeTargetScriptedCognitionModel extends AttributesCognitionModel {
}
