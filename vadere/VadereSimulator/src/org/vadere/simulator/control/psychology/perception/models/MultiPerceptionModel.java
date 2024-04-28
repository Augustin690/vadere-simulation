package org.vadere.simulator.control.psychology.perception.models;

import org.vadere.state.attributes.models.psychology.perception.AttributesMultiPerceptionModel;
import org.vadere.state.attributes.models.psychology.perception.AttributesPerceptionModel;
import org.vadere.state.psychology.perception.types.*;
import org.vadere.state.scenario.Pedestrian;
import org.vadere.state.scenario.Topography;

import java.util.*;
import java.util.stream.Collectors;
/**
 * Implementation for a perception model.
 *
 * An agent perceives multiple stimuli from the environment like for example a sound or visual information (see {@link Stimulus} for stimuli available).
 * We assume that the intensity of every single stimuli exceeds the respective sensory threshold.

 */
public class MultiPerceptionModel extends PerceptionModel {

    private Topography topography;
    private AttributesMultiPerceptionModel attributes;


    @Override
    public void initialize(Topography topography, final double simTimeStepLengh) {
        this.topography = topography;
        this.attributes = new AttributesMultiPerceptionModel();

    }

    /**
     * We assume that the intensity of all stimuli exceed the sensory threshold.
     * These stimuli are then evaluated in the cognition phase.
     */

    @Override
    public void update(HashMap<Pedestrian, List<Stimulus>> pedSpecificStimuli) {

        for (Map.Entry<Pedestrian, List<Stimulus>> pedStimuli : pedSpecificStimuli.entrySet()) {

            LinkedList<Stimulus> stimuli = pedStimuli.getValue().stream().collect(Collectors.toCollection(LinkedList::new));
            Pedestrian ped = pedStimuli.getKey();
            ped.setNextPerceivedStimuli(stimuli);
        }

    }

    @Override
    public void setAttributes(AttributesPerceptionModel attributes) {
        this.attributes = (AttributesMultiPerceptionModel) attributes;

    }

    @Override
    public AttributesMultiPerceptionModel getAttributes() {
        return this.attributes;
    }


}
