package scenes;

import graphics.Rgb;
import interfaces.ITransMatFactory;
import interfaces.Scene;
import maths.vector.Point;
import objects.LightEmitter;
import objects.Object3D;
import objects.lighting.LightSource;
import objects.materials.*;
import objects.object3d.Cube;
import objects.object3d.Cylinder;
import objects.object3d.Sphere;
import objects.textures.CheckerboardTexture;
import objects.textures.CircleTexture;
import objects.textures.ImageTexture;

import java.util.List;

public class BasicScene implements Scene {

    @Override
    public List<Object3D> getObjects() {

        Object3D room = new Cube().setMaterial( new Lambertian( new Rgb(0.8f, 0.8f, 0.8f) ).setTexture( new CircleTexture()) );
        room.addTransformations( fact.getTranslation(0, -3.5, 0) );
        room.addTransformations( fact.getScaling(5, 5, 8) );
        objects.add( room );

        Object3D sphere = new Sphere().setMaterial( new Mirror() );
        sphere.addTransformations( fact.getTranslation( -3, 0.5, 0 ) );
        objects.add( sphere );

        Object3D cube = new Cube().setMaterial( new Aluminium() );
        cube.addTransformations( fact.getTranslation( 0, 0.5, 0 ) );
        cube.addTransformations( fact.getRotation( ITransMatFactory.RotationAxis.Y, Math.PI / 4 ) );
        objects.add( cube );

        Object3D cylinder = new Cylinder().setMaterial( new Lambertian( Rgb.fromColor( Rgb.Color.RED ) ) );
        cylinder.addTransformations( fact.getTranslation( 3, 0.5, 0 ) );
        cylinder.addTransformations( fact.getRotation( ITransMatFactory.RotationAxis.X, Math.PI / 4 ) );
        cylinder.addTransformations( fact.getRotation( ITransMatFactory.RotationAxis.Z, Math.PI / 4 ) );
        objects.add( cylinder );

        Object3D sphere2 = new Sphere().setMaterial( new Plastic( new Rgb(0.3f, 0.6f, 0.2f) ) );
        sphere2.addTransformations( fact.getTranslation( 2.5, 0.5, 4 ) );
        sphere2.addTransformations( fact.getScaling( 0.5, 0.5 ,0.5 ) );
        objects.add( sphere2 );


        Object3D glass = new Sphere().setMaterial( new Glass() );
        glass.addTransformations( fact.getTranslation( 0, 0.5, 5 ) );
        glass.addTransformations( fact.getScaling( 0.2, 0.2 ,0.2 ) );
        objects.add( glass );

        Object3D cube2 = new Cube().setMaterial( new Lambertian().setTexture( new ImageTexture( "checker.png" )));
        cube2.addTransformations( fact.getTranslation( -2.5, 1, 4 ) );
        cube2.addTransformations( fact.getScaling( 0.2, 0.2 ,0.2 ) );
        objects.add( cube2 );

        return objects;
    }

    @Override
    public List<LightEmitter> getLights() {
        lights.add( new LightSource( new Point(2, -2, 6), 0.5, new Rgb(1,1,1) ) );
        lights.add( new LightSource( new Point(0, -2, 6), 0.5, new Rgb(1,1,1) ) );
        lights.add( new LightSource( new Point(-2, -2, 6), 0.5, new Rgb(1,1,1) ) );
        return lights;
    }
}
