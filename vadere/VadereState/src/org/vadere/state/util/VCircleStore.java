package org.vadere.state.util;

import org.vadere.util.geometry.shapes.ShapeType;
import org.vadere.util.geometry.shapes.VCircle;
import org.vadere.util.geometry.shapes.VPoint;
import org.vadere.util.reflection.VadereAttribute;

@SuppressWarnings("unused")
@VadereAttribute(name = "CircleGeometry")
public class VCircleStore extends JacksonObjectMapper.VShapeStore {
    /**
     * This attribute stores the radius of the circle.<br>
     * It cannot be less or equals zero.
     */
    public Double radius;
    /**
     * This attribute stores the center origin point of the circle.
     */
    public VPoint center;
    @VadereAttribute(exclude = true)
    public ShapeType type = ShapeType.CIRCLE;

    public VCircleStore() {
    }

    public VCircleStore(VCircle vCircle) {
        radius = vCircle.getRadius();
        center = vCircle.getCenter();
    }

    public VCircle newInstance() {
        return new VCircle(center, radius);
    }
}
