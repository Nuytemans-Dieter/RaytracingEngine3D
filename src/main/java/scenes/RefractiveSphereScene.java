package scenes;

import graphics.Rgb;
import interfaces.Scene;
import maths.vector.Point;
import objects.LightEmitter;
import objects.Object3D;
import objects.lighting.LightSource;
import objects.materials.Lambertian;
import objects.materials.Transparent;
import objects.object3d.Cube;
import objects.object3d.Cylinder;
import objects.object3d.Sphere;
import objects.textures.ImageTexture;

import java.util.List;

public class RefractiveSphereScene implements Scene {

    @Override
    public List<Object3D> getObjects() {

        Object3D room = new Cube().setMaterial( new Lambertian() );
        room.addTransformations( fact.getTranslation(0, -3.5, 0) );
        room.addTransformations( fact.getScaling(5, 5, 8) );
        objects.add( room );

        Object3D sphere = new Sphere().setMaterial( new Transparent() );
        objects.add( sphere );

        Object3D cylinder = new Cylinder().setMaterial( new Lambertian().setTexture( new ImageTexture( "checker.png" ) ) );
        cylinder.addTransformations( fact.getTranslation(0, -2, 0) );
        objects.add( cylinder );

        return objects;
    }

    @Override
    public List<LightEmitter> getLights() {
        lights.add( new LightSource( new Point(-2, -2, 2), 5.0, new Rgb(1,1,1) ) );
        return lights;
    }
}
