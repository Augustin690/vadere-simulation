package org.vadere.state.attributes.models.psychology.perception;

import org.vadere.state.psychology.perception.types.*;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * An agent perceives multiple stimuli from the environment like for example a sound or visual information.
 * <br>(see {@link org.vadere.state.psychology.perception.types.Stimulus} for stimuli available).<br><br>
 * We assume that some stimuli are more important than other stimuli.
 * For example a Threat like a fire is more important than a waiting signal in a queue.
 * We assume that only the most important stimulus has an intensity that exceeds the sensory threshold.
 */
public class AttributesSimplePerceptionModel extends AttributesPerceptionModel {
    /** <p>
     *  <i>priority</i> specifies the ranking of the stimuli.
     *  </p>
     *  <p>
     *  Example: <br>
     *  <code>
     *  "priority" : {<br>
     *           "1" : "InformationStimulus",<br>
     *           "2" : "ChangeTargetScripted",<br>
     *           "3" : "ChangeTarget",<br>
     *           "4" : "Threat",<br>
     *           "5" : "Wait",<br>
     *           "6" : "WaitInArea",<br>
     *           "7" : "DistanceRecommendation"<br>
     *  }
     *  </code>
     *  </p>
     *
     */
    Map<Integer, String> priority;

    public AttributesSimplePerceptionModel() {
        this.priority = getDefaultRanking();
    }

    public Map<Integer, String> getDefaultRanking() {
        Map<Integer, String> map = new HashMap();

        map.put(1, InformationStimulus.class.getSimpleName());
        map.put(2, ChangeTargetScripted.class.getSimpleName());
        map.put(3, ChangeTarget.class.getSimpleName());
        map.put(4, Threat.class.getSimpleName());
        map.put(5, Wait.class.getSimpleName());
        map.put(6, WaitInArea.class.getSimpleName());
        map.put(7, DistanceRecommendation.class.getSimpleName());

        return map;
    }

    public TreeMap<Integer, String> getSortedPriorityQueue(){
        return new TreeMap<>(this.priority);
    }
}
