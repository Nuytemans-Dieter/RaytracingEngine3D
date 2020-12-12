package util;

import maths.vector.Point;
import objects.Object3D;

import java.util.List;

public class ObjectFinder {

    /**
     * Finds the closest object that encloses this point
     * Simple search that finds the closest origin location of the object, to estimate the smallest enclosing object
     *
     * @param location the enclosed location
     * @param objects the list of objects under consideration
     * @return null if no object contains this point, Object3D otherwise
     */
    public Object3D findClosestObject(Point location, List<Object3D> objects)
    {
        Object3D closest = null;
        Double lowest = null;

        for (Object3D object : objects)
        {
            Double dist = object.enclosedDistance( location );
            if ((dist != null && lowest == null) || (lowest != null && dist != null && dist < lowest))
            {
                closest = object;
                lowest = dist;
            }
        }

        return closest;
    }

}
