package raytracing;

import maths.vector.Direction;
import maths.vector.Point;
import objects.Object3D;
import objects.Ray;

public class RayTraceInfo {

    private final Point hitLocation;
    private final Double closestT;
    private final Object3D closestObject;
    private final Ray hitRay;
    private final Direction normal;
    private final Boolean isEntering;

    public RayTraceInfo(Point hitLocation, Double closestT, Object3D closestObject, Ray hitRay, Direction closestTNormal, Boolean isEntering)
    {
        this.hitLocation = hitLocation;
        this.closestT = closestT;
        this.closestObject = closestObject;
        this.hitRay = hitRay;
        this.normal = closestTNormal;
        this.isEntering = isEntering;
    }

    public Point getHitLocation()
    {
        return this.hitLocation;
    }

    public Double getClosestT()
    {
        return this.closestT;
    }

    public Direction getNormal()
    {
        return this.normal;
    }

    public Object3D getClosestObject()
    {
        return this.closestObject;
    }

    public Ray getHitRay()
    {
        return this.hitRay;
    }

    public Boolean getIsEntering()
    {
        return this.isEntering;
    }

}
