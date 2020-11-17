package datacontainers;

import maths.vector.Direction;
import raytracing.RayTracer;

import java.util.HashMap;
import java.util.Map;

public class HitInfo {

    // Keep track of the t-value
    private Double lowestT;
    private final Map<Double, Direction> tNormalMap;

    // The normal at the intersection point with the lowestT
    private Direction lowestTNormal;

    /**
     * Create a HitInfo object with basic information about a ray - object collision
     *
     * @param tNormalMap mapping T value and normal of the intersection with the object
     */
    public HitInfo(Map<Double, Direction> tNormalMap)
    {
        this.lowestT = null;

        // Add all entries
        this.tNormalMap = new HashMap<>();
        this.tNormalMap.putAll( tNormalMap );

        // Find lowest value
        for (Map.Entry<Double, Direction> entry : tNormalMap.entrySet())
        {
            if (this.lowestT == null || entry.getKey() < this.lowestT)
            {
                lowestT = entry.getKey();
                lowestTNormal = entry.getValue();
            }
        }
    }


    /**
     * Create a HitInfo object with basic information about a ray - object collision
     */
    public HitInfo()
    {
        this( new HashMap<Double, Direction>() );
    }

    public void addHit(double hit, Direction normal)
    {
        this.tNormalMap.put( hit, normal );

        if (this.lowestT == null || hit < this.lowestT)
        {
            this.lowestT = hit;
            this.lowestTNormal = normal;
        }
    }

    public boolean isHit()
    {
        return tNormalMap.size() != 0;
    }

    public Double getLowestT()
    {
        return lowestT;
    }

    public Direction getLowestTNormal()
    {
        return this.lowestTNormal;
    }

    public Map<Double, Direction> getTNormalMap()
    {
        return this.tNormalMap;
    }

}
