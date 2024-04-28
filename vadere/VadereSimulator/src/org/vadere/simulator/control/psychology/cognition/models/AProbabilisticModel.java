package org.vadere.simulator.control.psychology.cognition.models;

import org.vadere.simulator.models.Model;
import org.vadere.state.psychology.cognition.SelfCategory;
import org.vadere.state.psychology.information.InformationState;
import org.vadere.state.psychology.perception.types.ElapsedTime;
import org.vadere.state.psychology.perception.types.Stimulus;
import org.vadere.state.psychology.perception.types.Threat;
import org.vadere.state.scenario.Pedestrian;

import java.util.Collection;

/**
 * Abstract class for a cognition model.
 * A probabilistic cognition model is a model that assigns a {@link SelfCategory} according to a probability function.
 * This is necessary, because people can behave differently even if they perceive the same stimuli
 * due to their individual experiences.
 * Usually, it is very difficult to model such individual experiences. That is why we use probability functions instead.
 */

public abstract class AProbabilisticModel implements ICognitionModel {


    void setInformationState(Pedestrian pedestrian) {
        // in deterministic models, all agents react to ..
        Stimulus mostImportantStimulus = pedestrian.getMostImportantStimulus();
        if ((pedestrian.getKnowledgeBase().getInformationState() == InformationState.NO_INFORMATION)
                && (!(mostImportantStimulus instanceof ElapsedTime)) ) {
            pedestrian.getKnowledgeBase().setInformationState(InformationState.INFORMATION_CONVINCING_RECEIVED);
        }
    }

    public void setInformationStateGroupMember(Collection<Pedestrian> pedestrians){
        // Assumption: decisions are met groupwise.
        // The decision is assigned to the the first group member in the update list
        // Group members get the label 'FOLLOW_INFORMED_GROUP_MEMBER'
        for (Pedestrian ped : pedestrians) {
            ped.getKnowledgeBase().setInformationState(InformationState.FOLLOW_INFORMED_GROUP_MEMBER);
        }
    }

    public void updateInformationState(Pedestrian pedestrian){
        // Assume that an agent is informed and reacts to information a..
        setInformationState(pedestrian);
        // handle group members separately
        setInformationStateGroupMember(pedestrian.getPedGroupMembers());
    }

}
