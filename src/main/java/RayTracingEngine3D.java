import datacontainers.RayTraceInfo;
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
import objects.materials.Lambertian;
import objects.materials.Mirror;
import objects.object3d.Cube;
import objects.object3d.Sphere;

import java.util.ArrayList;
import java.util.List;

public class RayTracingEngine3D {

    public static void main (String[] args) {

        // Initialise the matrix factory
        final ITransMatFactory matrixFactory = new TransMatFactory();

        // Initialise objects
        final List<Object3D> objects = new ArrayList<>();

        Object3D sphere = new Sphere( new Lambertian( Rgb.Color.GREEN ) );
        sphere.setTransformation( matrixFactory.getScaling(1.0, 1.6, 1.0) );
        objects.add( sphere );

        Object3D sphere2 = new Sphere( new Mirror() );
        sphere2.setTransformation( matrixFactory.getTranslation( 2, 0, 0 ));
        objects.add(sphere2);

        final List<LightEmitter> lights = new ArrayList<>();
//        lights.add( new GlobalIllumination(0.6) );
        lights.add( new LightSource( new Point(2, -2, 5), 1.0, new Rgb(1f, 1f, 1f) ) );
        lights.add( new LightSource( new Point(3, -2, 0), 1.0, new Rgb(1f, 1f, 1f) ) );

        RayTracer rayTracer = new RayTracer(objects, lights, new GlobalIllumination(1.0));
        ScreenInfo screenInfo = rayTracer.getScreenInfo();

        // Initialisations
        final Input input = new KeyboardInput();
        final DrawLib drawLib = new DrawLib(screenInfo.getScreenSize().width, screenInfo.getScreenSize().height, (KeyboardInput) input);

        // Generate a number of frames (assuming a changing environment or moving camera)
        for (int i = 0; i < 1; i++)
        {
            long start = System.currentTimeMillis();

            // Update all inverse matrices per frame refresh
            objects.forEach(Object3D::updateInverse);

            // Iterate through each pixel
            for (int u = 0; u < screenInfo.getScreenSize().width; u++)
            for (int v = 0; v < screenInfo.getScreenSize().height; v++)
            {
                Rgb color = new Rgb(0, 0, 0);

                RayTraceInfo info = rayTracer.tracePoint(u, v);
                if ( info.getClosestObject() != null )
                {
                    Material material = info.getClosestObject().getMaterial();
                    Rgb illumination = rayTracer.getIllumination( info ).applyIntensity( material.colorStrength );
                    Rgb reflection = rayTracer.calculateReflection( info ).applyIntensity( material.reflectivity );

                    // Find the colour of this point returning to the eye from the point of intersection
//                    color = illumination.addRgb( reflection );
                    color.addRgb( illumination ).addRgb( reflection );
                }

                // Compute the hit point and the normal vector in this point
//            Point hitPoint = ray.getPoint(closestT);

                // Update the color in this pixel
                drawLib.drawPoint(u, v, color);
            }

            // Make sure all pixels are effectively drawn to the screen
            drawLib.forceUpdate();
            
            long end = System.currentTimeMillis();
            long delta = end - start;
            System.out.println("Calculation time: " + delta + "ms");
            try
            {
                if ( delta <= 200 )
                    Thread.sleep(200 - delta);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

}
