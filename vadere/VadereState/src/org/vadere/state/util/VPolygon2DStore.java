package org.vadere.state.util;

import org.vadere.util.geometry.GeometryUtils;
import org.vadere.util.geometry.shapes.ShapeType;
import org.vadere.util.geometry.shapes.VPoint;
import org.vadere.util.geometry.shapes.VPolygon;
import org.vadere.util.reflection.VadereAttribute;

import java.util.List;

@SuppressWarnings("unused")
@VadereAttribute(name = "PolygonGeometry")
public class VPolygon2DStore extends JacksonObjectMapper.VShapeStore {
    @VadereAttribute(exclude = true)
    public ShapeType type = ShapeType.POLYGON;
    /**
     * This list is a collection of all point that make up the polygon.
     * The points are lay out clockwise.
     */
    public List<VPoint> points;

    public VPolygon2DStore() {
    }

    public VPolygon2DStore(VPolygon vPoly) {
        points = vPoly.getPoints();
    }

    public VPolygon newInstance() {
        return GeometryUtils.polygonFromPoints2D(points);
    }
}
