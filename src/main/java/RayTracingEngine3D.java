import interfaces.Scene;
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
import scenes.MinecraftScene;

import java.util.ArrayList;
import java.util.List;

public class RayTracingEngine3D {

    public static void main (String[] args) {

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

        Scene scene = new MinecraftScene();
        final List<Object3D> objects = scene.getObjects();
        final List<LightEmitter> lights = scene.getLights();

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

        int numChunksU = 4;
        int numChunksV = 4;

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
