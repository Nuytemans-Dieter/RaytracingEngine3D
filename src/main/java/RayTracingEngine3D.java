import objects.materials.*;
import objects.object3d.Cube;
import objects.object3d.Cylinder;
import objects.object3d.Plane;
import objects.textures.ImageTexture;
import raytracing.RayTraceInfo;
import datacontainers.ScreenInfo;
import graphics.DrawLib;
import graphics.Rgb;
import input.KeyboardInput;
import interfaces.ITransMatFactory;
import interfaces.Input;
import maths.TransMatFactory;
import maths.vector.Point;
import objects.LightEmitter;
import objects.Material;
import objects.lighting.GlobalIllumination;
import objects.lighting.LightSource;
import objects.Object3D;
import objects.object3d.Sphere;
import raytracing.RayTracer;

import java.util.ArrayList;
import java.util.List;

public class RayTracingEngine3D {

    public static void main (String[] args) {

        // Initialise the matrix factory
        final ITransMatFactory matrixFactory = new TransMatFactory();

        // Initialise objects
        final List<Object3D> objects = new ArrayList<>();

//        Object3D sphere = new Sphere().setMaterial( new Mirror() );
//        sphere.addTransformations( matrixFactory.getTranslation(-2, 0, 4) );
//        objects.add( sphere );

//        Object3D cylinder = new Cylinder().setMaterial( new Lambertian( Rgb.Color.GREEN ) );
//        cylinder.addTransformations( matrixFactory.getTranslation(2, 0, -2) );
//        cylinder.setTransformation( matrixFactory.getRotation(ITransMatFactory.RotationAxis.X, Math.PI / 6 ) );
//        objects.add(cylinder);
//
//        Object3D sphere3 = new Cylinder().setMaterial( new Lambertian(new ImageTexture("grass.jpg")) );
//        sphere3.addTransformations( matrixFactory.getTranslation(0, 0, 2) );
//        sphere3.addTransformations( matrixFactory.getRotation( ITransMatFactory.RotationAxis.X, Math.PI / 4 ) );
//        objects.add( sphere3 );

//        Object3D plane = new Plane().setMaterial( new Glass() );
//        plane.addTransformations( matrixFactory.getTranslation(2, 0, 4) );
//        objects.add( plane );

//        Object3D room = new Cube().setMaterial( new Lambertian() );
//        room.addTransformations( matrixFactory.getTranslation(0, -3.5, 0) );
//        room.addTransformations( matrixFactory.getScaling(5, 5, 8) );
//        objects.add( room );

        Object3D background = new Plane().setMaterial( new Lambertian( new Rgb((float) 0.5, (float) 0.92, 1) ) );
        background.addTransformations( matrixFactory.getScaling( 30, 20, 1 ) );
        background.addTransformations( matrixFactory.getTranslation( 0, 0, -20 ) );
        objects.add( background );

        Object3D sun = new Plane().setMaterial( new Lambertian( new Rgb((float) 0.8, (float) 0.8, 0) ).setEmissive(0.8f, 0.8f, 0) );
        sun.addTransformations( matrixFactory.getTranslation( 10, -10, -18 ) );
        objects.add( sun );

        // Build dirt ground
        for (int x = -7; x < 7; x++)
        for (int z = -6; z < 6; z++)
        {
            Object3D block = new Cube().setMaterial( new Lambertian( new ImageTexture( "dirt.jpg" ) ) );
            block.addTransformations( matrixFactory.getScaling(0.5, 0.5, 0.5) );
            block.addTransformations( matrixFactory.getTranslation(2*x, 4, 2*z) );
            objects.add( block );
        }

        // Build tree
        for (int i = 0; i < 7; i++)
        {
            Object3D wood = new Cube().setMaterial( new Lambertian( new ImageTexture( ("wood.jpg" ) ) ));
            wood.addTransformations( matrixFactory.getScaling(0.5, 0.5, 0.5) );
            wood.addTransformations( matrixFactory.getTranslation(-6, 2 - i, 0) );
            objects.add( wood );
        }


        // Build cobblestone wall
        for (int x = -1; x < 2; x++)
        for (int y = -1; y < 2; y++)
        {
            if (x != 0 || y != 0)
            {
                Object3D cobble = new Cube().setMaterial( new Lambertian( new ImageTexture( ("cobblestone.png" ) ) ));
                cobble.addTransformations( matrixFactory.getScaling(0.5, 0.5, 0.5) );
                cobble.addTransformations( matrixFactory.getTranslation(2*x + 2, 2*y, 4) );
                objects.add( cobble );
            }
        }


        // Build glass wall
        for (int x = -1; x < 1; x++)
        for (int y = -1; y < 1; y++)
        {
                Object3D cobble = new Cube().setMaterial( new Glass() );
                cobble.addTransformations( matrixFactory.getScaling(0.5, 0.5, 0.5) );
                cobble.addTransformations( matrixFactory.getTranslation(2*x - 4, 2*y + 2, 4) );
                objects.add( cobble );
        }


        final List<LightEmitter> lights = new ArrayList<>();
        lights.add( new LightSource( new Point(10, -10, -17.99), 2.0, new Rgb(1,1,1) ) );
        lights.add( new LightSource( new Point(11, -11, -19), 3.0, new Rgb(1,1,1) ) );
//        lights.add( new LightSource( new Point(-2, -2, 2), 3.0, new Rgb(1,1,1) ) );
//        lights.add( new GlobalIllumination(0.6) );

        RayTracer rayTracer = new RayTracer(objects, lights, new GlobalIllumination(1.0));
        ScreenInfo screenInfo = rayTracer.getScreenInfo();

        // Initialisations
        final Input input = new KeyboardInput();
        final DrawLib drawLib = new DrawLib(screenInfo.getScreenSize().width, screenInfo.getScreenSize().height, (KeyboardInput) input);

        // Update all inverse matrices
        objects.forEach(Object3D::updateInverse);

        // Start timing the ray tracing
        long start = System.currentTimeMillis();

        int numChunksU = 2;
        int numChunksV = 2;

        int width = screenInfo.getScreenSize().width;
        int height = screenInfo.getScreenSize().height;

        int uWidth = width / numChunksU;
        int vWidth = height / numChunksV;

        for (int u = 0; u < numChunksU; u++)
        for (int v = 0; v < numChunksV; v++)
            new ScreenChunkTracer(u * uWidth, (u + 1) * uWidth, v * vWidth, (v+1) * vWidth, objects, lights, new GlobalIllumination(0.4), drawLib, start, u, v).start();

        // Make sure all pixels are effectively drawn to the screen
        drawLib.forceUpdate();


    }

}
