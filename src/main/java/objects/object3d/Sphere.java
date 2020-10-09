package objects.object3d;

import graphics.Rgb;
import objects.Ray;
import objects.Object3D;

public class Sphere extends Object3D {
    @Override
    public boolean isColliding(Ray ray)
    {

        return false;
    }

    @Override
    public Rgb getcolor()
    {
        return null;
    }
}
