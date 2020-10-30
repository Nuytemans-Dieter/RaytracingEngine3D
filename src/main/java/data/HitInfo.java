package data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HitInfo {

    private boolean isHit;
    private Double lowestT;
    private final List<Double> hitTimes;

    public HitInfo(double... hits)
    {
        this.lowestT = null;

        this.hitTimes = new ArrayList<>();
        for (double hit : hits)
        {
            if (this.lowestT == null || hit < this.lowestT)
                lowestT = hit;
            this.hitTimes.add(hit);
        }

        isHit = hitTimes.size() != 0;
    }

    public void addHit(double hit)
    {
        this.hitTimes.add( hit );

        if (this.lowestT == null || hit < this.lowestT)
            lowestT = hit;

        isHit = hitTimes.size() != 0;
    }

    public boolean isHit()
    {
        return isHit;
    }

    public Double getLowestT()
    {
        return lowestT;
    }

    public List<Double> getHitTimes()
    {
        return this.hitTimes;
    }

}
