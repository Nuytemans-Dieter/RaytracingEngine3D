import objects.materials.Aluminium;
import objects.materials.Lambertian;
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
import objects.materials.Gold;
import objects.materials.Mirror;
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

        Object3D sphere = new Sphere().setMaterial( new Mirror() );
        sphere.addTransformations( matrixFactory.getTranslation(2, 0, 0) );
        sphere.addTransformations(
                matrixFactory.getRotation(ITransMatFactory.RotationAxis.X, Math.PI),
                matrixFactory.getRotation(ITransMatFactory.RotationAxis.Y, Math.PI),
                matrixFactory.getRotation(ITransMatFactory.RotationAxis.Z, Math.PI)
        );
        objects.add( sphere );

        Object3D sphere2 = new Cylinder().setMaterial( new Lambertian( Rgb.Color.GREEN ) );
        sphere2.setTransformation( matrixFactory.getRotation(ITransMatFactory.RotationAxis.X, Math.PI / 6 ) );
        sphere2.addTransformations( matrixFactory.getTranslation(-1, 0, 0) );
        objects.add(sphere2);

        Object3D room = new Cube().setMaterial( new Lambertian( Rgb.Color.RED ) );
        room.addTransformations( matrixFactory.getScaling(20, 20, 20) );
        objects.add( room );

        final List<LightEmitter> lights = new ArrayList<>();
        lights.add( new LightSource( new Point(0, 0, 5), 5.0, new Rgb(1,1,1) ) );
//        lights.add( new GlobalIllumination(0.6) );

        RayTracer rayTracer = new RayTracer(objects, lights, new GlobalIllumination(1.0));
        ScreenInfo screenInfo = rayTracer.getScreenInfo();

        // Initialisations
        final Input input = new KeyboardInput();
        final DrawLib drawLib = new DrawLib(screenInfo.getScreenSize().width, screenInfo.getScreenSize().height, (KeyboardInput) input);

        // Start timing the ray tracing
        long start = System.currentTimeMillis();

        // Update all inverse matrices
        objects.forEach(Object3D::updateInverse);

        // Iterate through each pixel
        int width = screenInfo.getScreenSize().width;
        int height = screenInfo.getScreenSize().height;
        ScreenChunkTracer threadOne = new ScreenChunkTracer(    0,      width / 2, 0, height / 2, rayTracer, drawLib, start);
        ScreenChunkTracer threadTwo = new ScreenChunkTracer(    width / 2,    width,     0, height / 2, rayTracer, drawLib, start);
        ScreenChunkTracer threadThree = new ScreenChunkTracer(  0,      width / 2, height / 2, height,  rayTracer, drawLib, start);
        ScreenChunkTracer threadFour = new ScreenChunkTracer(   width / 2,    width,     height / 2, height,  rayTracer, drawLib, start);

        threadOne.start();
        threadTwo.start();
        threadThree.start();
        threadFour.start();

        // Make sure all pixels are effectively drawn to the screen
        drawLib.forceUpdate();


    }

}
