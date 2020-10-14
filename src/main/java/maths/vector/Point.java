package maths.vector;

import maths.Vector;

public class Point extends Vector {

    public Point(double x, double y, double z)
    {
        super(x, y, z, 1);
    }

    public double distance(Point point)
    {
        return Math.sqrt(
            Math.pow((this.getX() - point.getX() ), 2 ) +
            Math.pow((this.getY() - point.getY() ), 2 ) +
            Math.pow((this.getZ() - point.getZ() ), 2 )
        );
    }

}
