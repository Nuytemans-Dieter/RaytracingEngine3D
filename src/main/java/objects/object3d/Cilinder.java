package objects.object3d;

import graphics.Rgb;
import objects.Object3D;
import objects.Ray;

public class Cilinder extends Object3D {
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
