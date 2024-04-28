package org.vadere.state.attributes.scenario;

import org.vadere.state.attributes.AttributesAbsorber;
import org.vadere.state.scenario.ScenarioElement;
import org.vadere.util.geometry.shapes.VShape;
import org.vadere.util.reflection.VadereAttribute;

/**
 * An AbsorbingArea is a {@link ScenarioElement} which absorbs agents.
 */
public class AttributesAbsorbingArea extends AttributesVisualElement {
    /**
     * <i>absorber</i> specifies the attributes of the absorbing area.
     */
    @VadereAttribute
    protected AttributesAbsorber absorber = new AttributesAbsorber();

    // Constructors
    public AttributesAbsorbingArea() {
        super();
    }
    public AttributesAbsorbingArea(final VShape shape) {
        super();
        this.shape = shape;
    }

    public AttributesAbsorbingArea(final VShape shape, final int id) {
        super();
        this.shape = shape;
        this.id = id;
    }

    public AttributesAbsorbingArea(final VShape shape, final int id, double deletionDistance) {
        super();
        this.shape = shape;
        this.id = id;
        this.absorber.setDeletionDistance(deletionDistance);
    }

    public double getDeletionDistance() {
        return this.absorber.getDeletionDistance();
    }

    public void setDeletionDistance(double deletionDistance) {
        checkSealed();
        this.absorber.setDeletionDistance(deletionDistance);
    }

}
