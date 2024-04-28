package org.vadere.state.attributes;

import com.fasterxml.jackson.annotation.JsonView;
import org.vadere.state.attributes.distributions.AttributesDistribution;
import org.vadere.state.attributes.scenario.AttributesTarget;
import org.vadere.state.util.Views;
import org.vadere.util.reflection.VadereAttribute;

/**
 * This component controls the waiting behaviour of this waiting area.
 */
public class AttributesWaiter extends AttributesEnabled {
    /**
     * This attribute stores the distribution used for each agent to calculate the time an agent
     * waits at this waiting area.
     */
    @VadereAttribute
    @JsonView(Views.CacheViewExclude.class)
    private AttributesDistribution distribution;
    /**
     * This attribute stores whether the waiter handles arriving agents in batches or as individuals.
     * Waiting phase of a waiter will start as soon as the number of waiting ({@link AttributesTarget#parallelEvents})
     * has been reached. Note that individualWaiting=false and {@link AttributesTarget#parallelEvents}=0 make agents
     * wait forever.
     */
    @VadereAttribute
    @JsonView(Views.CacheViewExclude.class)
    private Boolean individualWaiting = true;

    public AttributesWaiter(){
        super();
    }

    public AttributesWaiter(boolean enabled){
        super(enabled);
    }

    public AttributesDistribution getDistribution() {
        return distribution;
    }

    public AttributesWaiter setDistribution(AttributesDistribution distribution) {
        this.distribution = distribution;
        return this;
    }

    public boolean isIndividualWaiting() {
        return individualWaiting;
    }

    public void setIndividualWaiting(boolean individualWaiting) {
        this.individualWaiting = individualWaiting;
    }
}
