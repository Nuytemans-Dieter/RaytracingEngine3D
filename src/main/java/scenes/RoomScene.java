package scenes;

import graphics.Rgb;
import interfaces.ITransMatFactory;
import interfaces.Scene;
import maths.TransMatFactory;
import maths.vector.Point;
import objects.LightEmitter;
import objects.Object3D;
import objects.lighting.LightSource;
import objects.materials.Glass;
import objects.materials.Lambertian;
import objects.materials.Mirror;
import objects.object3d.Cube;
import objects.object3d.Cylinder;
import objects.object3d.Plane;
import objects.object3d.Sphere;
import objects.textures.ImageTexture;

import java.util.ArrayList;
import java.util.List;

public class RoomScene implements Scene {
    @Override
    public List<Object3D> getObjects() {

        ITransMatFactory matrixFactory = new TransMatFactory();
        final List<Object3D> objects = new ArrayList<>();

        Object3D sphere = new Sphere().setMaterial( new Mirror() );
        sphere.addTransformations( matrixFactory.getTranslation(-2, 0, 4) );
        objects.add( sphere );

        Object3D cylinder = new Cylinder().setMaterial( new Lambertian( Rgb.Color.GREEN ) );
        cylinder.addTransformations( matrixFactory.getTranslation(2, 0, -2) );
        cylinder.setTransformation( matrixFactory.getRotation(ITransMatFactory.RotationAxis.X, Math.PI / 6 ) );
        objects.add(cylinder);

        Object3D sphere3 = new Cylinder().setMaterial( new Lambertian(new ImageTexture("grass.jpg")) );
        sphere3.addTransformations( matrixFactory.getTranslation(0, 0, 2) );
        sphere3.addTransformations( matrixFactory.getRotation( ITransMatFactory.RotationAxis.X, Math.PI / 4 ) );
        objects.add( sphere3 );

        Object3D plane = new Plane().setMaterial( new Glass() );
        plane.addTransformations( matrixFactory.getTranslation(2, 0, 4) );
        objects.add( plane );

        Object3D room = new Cube().setMaterial( new Lambertian() );
        room.addTransformations( matrixFactory.getTranslation(0, -3.5, 0) );
        room.addTransformations( matrixFactory.getScaling(5, 5, 8) );
        objects.add( room );

        return objects;
    }

    @Override
    public List<LightEmitter> getLights() {
        final List<LightEmitter> lights = new ArrayList<>();
        lights.add( new LightSource( new Point(-2, -2, 2), 3.0, new Rgb(1,1,1) ) );
        return lights;
    }
}
