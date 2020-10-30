package datacontainers;

import maths.vector.Point;
import objects.Object3D;

public class RayTraceInfo {

    private final Point hitLocation;
    private final Double closestT;
    private final Object3D closestObject;

    public RayTraceInfo(Point hitLocation, Double closestT, Object3D closestObject)
    {
        this.hitLocation = hitLocation;
        this.closestT = closestT;
        this.closestObject = closestObject;
    }

    public Point getHitLocation()
    {
        return this.hitLocation;
    }

    public Double getClosestT()
    {
        return this.closestT;
    }

    public Object3D getClosestObject()
    {
        return this.closestObject;
    }

}
