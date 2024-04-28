package org.vadere.state.attributes.scenario;

import com.fasterxml.jackson.annotation.JsonView;
import org.vadere.state.attributes.AttributesScenarioElement;
import org.vadere.state.util.Views;
import org.vadere.util.geometry.shapes.VCircle;
import org.vadere.util.geometry.shapes.VPolygon;
import org.vadere.util.geometry.shapes.VRectangle;
import org.vadere.util.geometry.shapes.VShape;
import org.vadere.util.reflection.VadereAttribute;

/**
 * A <i>Visual Element</i> in Vadere is any scenario element in the topography that can be drawn in the topography editor.
 * Each visual element has a shape and a visibility flag.
 */
public class AttributesVisualElement extends AttributesScenarioElement {
    /**
     * <i>shape</i> is the geometric shape of the scenario element.
     * In the topography editor, the shape can be changed by the user.
     * Available shapes are:
     * <ul>
     *     <li><b>{@link VRectangle}</b> which is defined by its sw origin, width and height</li>
     *     <li><b>{@link VPolygon}</b> which is defined by a list of points</li>
     *     <li><b>{@link VCircle}</b> which is defined by its center origin and radius</li>
     * </ul>
     */
    protected VShape shape;
    /**
     * Use <i>visible</i> to show/hide a scenario element in the topography creator.
     * Making a scenario element invisible allows the user to click scenario elements (in the GUI)
     * that are otherwise hidden in the layer below.
     * This attribute does not affect the visibility of scenario elements in the simulation
     * results (online- / post-visualisation). Please use the settings dialog of the online- /
     * post-visualization to show/hide scenario elements in the simulation results.
     */
    @JsonView(Views.CacheViewExclude.class)
    protected Boolean visible;

    public AttributesVisualElement() {
        super();
        this.shape = new VRectangle(0, 0, 1, 1);
        this.visible = true;
    }

    public VShape getShape() {
        return this.shape;
    }

    public void setShape(VShape shape) {
        checkSealed();
        this.shape = shape;
    }

    public void setVisible(boolean visible) {
        checkSealed();
        this.visible = visible;
    }

    public Boolean isVisible(){
        return this.visible;
    }

}
