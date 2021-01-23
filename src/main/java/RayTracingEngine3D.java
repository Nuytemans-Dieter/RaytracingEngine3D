import objects.materials.*;
import objects.object3d.Cube;
import objects.object3d.Cylinder;
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

        Object3D sphere = new Sphere().setMaterial( new Gold() );
        sphere.addTransformations( matrixFactory.getTranslation(22, 0, 2) );
//        sphere.addTransformations(
//                matrixFactory.getRotation(ITransMatFactory.RotationAxis.X, Math.PI),
//                matrixFactory.getRotation(ITransMatFactory.RotationAxis.Y, Math.PI),
//                matrixFactory.getRotation(ITransMatFactory.RotationAxis.Z, Math.PI)
//        );
        objects.add( sphere );

        Object3D sphere2 = new Cylinder().setMaterial( new Lambertian( Rgb.Color.GREEN ) );
        sphere2.setTransformation( matrixFactory.getRotation(ITransMatFactory.RotationAxis.X, Math.PI / 6 ) );
        sphere2.addTransformations( matrixFactory.getTranslation(2, 0, -2) );
        objects.add(sphere2);

//        Object3D sphere3 = new Sphere().setMaterial( new Transparent() );
//        sphere3.addTransformations( matrixFactory.getTranslation(2, 0, 2) );
//        objects.add( sphere3 );

//        Object3D room = new Cube().setMaterial( new Lambertian( Rgb.Color.RED ) );
//        room.addTransformations( matrixFactory.getScaling(20, 20, 20) );
//        objects.add( room );

        final List<LightEmitter> lights = new ArrayList<>();
        lights.add( new LightSource( new Point(0, 0, -5), 5.0, new Rgb(1,1,1) ) );
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
            new ScreenChunkTracer(u * uWidth, (u + 1) * uWidth, v * vWidth, (v+1) * vWidth, rayTracer, drawLib, start, u, v).start();

        // Make sure all pixels are effectively drawn to the screen
        drawLib.forceUpdate();


    }

}
