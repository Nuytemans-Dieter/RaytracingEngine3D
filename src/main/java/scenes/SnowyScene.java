package scenes;

import graphics.Rgb;
import interfaces.ITransMatFactory;
import interfaces.Scene;
import maths.vector.Point;
import objects.LightEmitter;
import objects.Object3D;
import objects.lighting.LightSource;
import objects.materials.Aluminium;
import objects.materials.Lambertian;
import objects.materials.Mirror;
import objects.materials.Snow;
import objects.object3d.Cube;
import objects.object3d.Cylinder;
import objects.object3d.Plane;
import objects.object3d.Sphere;
import objects.textures.ImageTexture;

import java.util.List;

public class SnowyScene implements Scene {

    @Override
    public List<Object3D> getObjects() {

        // Add floor planes
        for (int x = -5; x < 5; x++)
        for (int z = -3; z < 3; z++)
        {
            Object3D ground = new Plane().setMaterial( new Snow( true ) );
            ground.addTransformations(
                    fact.getTranslation(x * 7, 2, z * 7),
                    fact.getScaling(5, 1, 5),
                    fact.getRotation( ITransMatFactory.RotationAxis.X, Math.PI / 2 )
            );
            objects.add( ground );
        }

        // Build the sky
        Object3D sky = new Plane().setMaterial( new Mirror() );
        sky.addTransformations(
                fact.getTranslation(0, 0, -15),
                fact.getScaling(30, 30, 1)
        );
        objects.add( sky );

        Object3D house = new Cube().setMaterial( new Lambertian().setTexture( new ImageTexture( "house.jpg" )) );
        house.addTransformations( fact.getTranslation( 5, 1, -2 ) );
        objects.add( house );

        // Build the snow man
        float scale = 0.6f;
        for (int i = 0; i < 3; i++)
        {
            Object3D ground = new Sphere().setMaterial( new Snow( false ) );
            ground.addTransformations(
                    fact.getTranslation(-4, -2*i+1, 0),
                    fact.getScaling(1.2 + scale, 1.1 + scale/2, 1.2 + scale)
            );
            objects.add( ground );
            scale -= 0.2;
        }

        Object3D leftEye = new Sphere().setMaterial( new Aluminium() );
        leftEye.addTransformations(
                fact.getTranslation(-3.75, -2.95, 1.4),
                fact.getScaling(0.1, 0.1, 0.1)
        );
        objects.add( leftEye );

        Object3D rightEye = new Sphere().setMaterial( new Aluminium() );
        rightEye.addTransformations(
                fact.getTranslation(-3.2, -2.95, 1.2),
                fact.getScaling(0.1, 0.1, 0.1)
        );
        objects.add( rightEye );

        Object3D nose = new Cylinder().setMaterial( new Lambertian( Rgb.fromColor( Rgb.Color.RED ) ) );
        nose.addTransformations(
                fact.getTranslation(-3.5, -2.65, 1.2),
                fact.getRotation( ITransMatFactory.RotationAxis.X, Math.PI / 2 ),
                fact.getRotation( ITransMatFactory.RotationAxis.Z, -Math.PI / 6 ),
                fact.getScaling(0.15, 0.3, 0.15)
        );
        objects.add( nose );

        return objects;
    }

    @Override
    public List<LightEmitter> getLights() {
        lights.add( new LightSource(new Point(4, -2, 2), 2.0, Rgb.fromColor( Rgb.Color.WHITE )));
        return lights;
    }
}
