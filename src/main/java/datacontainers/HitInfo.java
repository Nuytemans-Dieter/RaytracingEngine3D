package datacontainers;

import maths.vector.Direction;
import raytracing.RayTracer;

import java.util.HashMap;
import java.util.Map;

public class HitInfo {

    // Keep track of the t-value
    private Double lowestT;
    private final Map<Double, Direction> tNormalMap;
    private final Map<Double, Boolean> isEntering;

    // The normal at the intersection point with the lowestT
    private Direction lowestTNormal;
    private boolean lowestIsEntering;

    /**
     * Create a HitInfo object with basic information about a ray - object collision
     *
     * @param tNormalMap mapping T value and normal of the intersection with the object
     */
    public HitInfo(Map<Double, Direction> tNormalMap, Map<Double, Boolean> isEntering)
    {
        this.lowestT = null;
        this.isEntering = isEntering;

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
                lowestIsEntering = isEntering.get( entry.getKey() );
            }
        }
    }


    /**
     * Create a HitInfo object with basic information about a ray - object collision
     */
    public HitInfo()
    {
        this( new HashMap<Double, Direction>(), new HashMap<Double, Boolean>() );
    }

    public void addHit(double hit, Direction normal, boolean isEntering)
    {
        this.tNormalMap.put( hit, normal );
        this.isEntering.put( hit, isEntering );

        if (this.lowestT == null || hit < this.lowestT)
        {
            this.lowestT = hit;
            this.lowestTNormal = normal;
            this.lowestIsEntering = isEntering;
        }
    }

    public Double getLowestT()
    {
        return lowestT;
    }

    public boolean isLowestEntering()
    {
        return lowestIsEntering;
    }

    public Direction getLowestTNormal()
    {
        return this.lowestTNormal;
    }

}
