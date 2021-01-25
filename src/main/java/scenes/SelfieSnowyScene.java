package scenes;

import graphics.Rgb;
import interfaces.ITransMatFactory;
import interfaces.Scene;
import maths.vector.Point;
import objects.LightEmitter;
import objects.Material;
import objects.Object3D;
import objects.lighting.LightSource;
import objects.materials.Aluminium;
import objects.materials.Lambertian;
import objects.materials.Mirror;
import objects.materials.Snow;
import objects.object3d.Cylinder;
import objects.object3d.Plane;
import objects.object3d.Sphere;
import objects.textures.ImageTexture;

import java.util.List;

public class SelfieSnowyScene implements Scene {

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
        Object3D sky = new Plane().setMaterial( new Mirror());
        sky.addTransformations(
                fact.getTranslation(0, 0, -15),
                fact.getScaling(30, 30, 1)
        );
        objects.add( sky );

        // Build the snow man
        float scale = 0.6f;
        for (int i = 0; i < 3; i++)
        {
            Material material = i != 2 ?  new Snow( false ) : new Lambertian( new ImageTexture( "selfie.png" ) );
            Object3D ground = new Sphere().setMaterial( material );
            ground.addTransformations(
                    fact.getTranslation(-4, -2*i+1, 0),
                    fact.getScaling(1.2 + scale, 1.1 + scale/2, 1.2 + scale)
            );
            if (i == 2)
                ground.addTransformations( fact.getRotation( ITransMatFactory.RotationAxis.Y, -Math.PI/2 ) );
            objects.add( ground );
            scale -= 0.2;
        }

        return objects;
    }

    @Override
    public List<LightEmitter> getLights() {
        lights.add( new LightSource(new Point(4, -2, 0), 2.0, Rgb.fromColor( Rgb.Color.WHITE )));
        return lights;
    }
}
