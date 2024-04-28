package org.vadere.state.psychology.perception.types;

import java.util.LinkedList;

/**
 * The subpopulation filter pedestrians that are affected by the stimuli.
 * If the list of affected pedestrians is empty, all pedestrians are affected;
 */
public class SubpopulationFilter {
    /**
     * <i>affectedPedestrianIds</i> is a list of pedestrian ids that are affected by the stimuli.
     */
    private LinkedList<Integer> affectedPedestrianIds;

    public SubpopulationFilter(){
        this.affectedPedestrianIds = new LinkedList<>();
    }

    public SubpopulationFilter(LinkedList<Integer> affectedPedestrianIds) {
        this.affectedPedestrianIds = affectedPedestrianIds;
    }

    public LinkedList<Integer> getAffectedPedestrianIds() {
        return affectedPedestrianIds;
    }

    public void setAffectedPedestrianIds(LinkedList<Integer> affectedPedestrianIds) {
        this.affectedPedestrianIds = affectedPedestrianIds;
    }





}
