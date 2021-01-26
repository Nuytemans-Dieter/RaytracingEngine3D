package scenes;

import graphics.Rgb;
import interfaces.Scene;
import maths.vector.Point;
import objects.LightEmitter;
import objects.Object3D;
import objects.lighting.LightSource;
import objects.materials.Aluminium;
import objects.materials.Lambertian;
import objects.materials.Mirror;
import objects.object3d.Cube;
import objects.object3d.Sphere;

import java.util.List;

public class EmptyRoom implements Scene {

    @Override
    public List<Object3D> getObjects() {

        Object3D room = new Cube().setMaterial( new Lambertian() );
        room.addTransformations( fact.getTranslation(0, -3.5, 0) );
        room.addTransformations( fact.getScaling(5, 5, 8) );
        objects.add( room );

        Object3D sphere = new Sphere().setMaterial( new Lambertian(Rgb.fromColor( Rgb.Color.RED )) );
        sphere.addTransformations( fact.getTranslation(0, -4, -2) );
        objects.add( sphere );

        Object3D sphere2 = new Sphere().setMaterial( new Mirror() );
        sphere2.addTransformations( fact.getTranslation(0, 0, -2) );
        objects.add( sphere2 );

        return objects;
    }

    @Override
    public List<LightEmitter> getLights() {
        lights.add( new LightSource( new Point(-2, -2, 2), 3.0, new Rgb(1,1,1) ) );
        return lights;
    }
}
