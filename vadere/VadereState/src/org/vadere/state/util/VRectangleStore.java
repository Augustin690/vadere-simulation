package org.vadere.state.util;

import org.vadere.util.geometry.shapes.ShapeType;
import org.vadere.util.geometry.shapes.VRectangle;
import org.vadere.util.reflection.VadereAttribute;

@SuppressWarnings("unused")
@VadereAttribute(name = "RectangularGeometry")
public class VRectangleStore extends JacksonObjectMapper.VShapeStore {
    /**
     * This attribute stores the x coordinate of the origin point
     */
    public Double x;
    /**
     * This attribute stores the x coordinate of the origin point
     */
    public Double y;
    /**
     * This attribute stores the width of the rectangle.<br>
     * It cannot be less or equals zero.
     */
    public Double width;
    /**
     * This attribute stores the height of the rectangle.<br>
     * It cannot be less or equals zero.
     */
    public Double height;
    @VadereAttribute(exclude = true)
    public ShapeType type = ShapeType.RECTANGLE;

    public VRectangleStore() {
        x = 0.0;
        y = 0.0;
        width = 1.0;
        height = 1.0;
    }

    public VRectangleStore(VRectangle vRect) {
        x = vRect.x;
        y = vRect.y;
        height = vRect.height;
        width = vRect.width;
    }

    public VRectangle newInstance() {
        return new VRectangle(x, y, width, height);
    }
}
