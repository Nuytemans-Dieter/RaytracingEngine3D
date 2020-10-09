package objects;

import graphics.Rgb;

public abstract class Object3D {

    public abstract boolean isColliding(Ray ray);

    public abstract Rgb getcolor();

}
