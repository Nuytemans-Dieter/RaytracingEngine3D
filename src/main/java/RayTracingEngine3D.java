import graphics.DrawLib;
import graphics.Rgb;
import input.KeyboardInput;
import interfaces.ITransMatFactory;
import interfaces.Input;
import maths.Matrix;
import maths.TransMatFactory;
import maths.vector.Direction;
import maths.vector.Point;
import objects.LightEmitter;
import objects.lighting.LightSource;
import objects.Object3D;
import objects.Ray;
import objects.object3d.Cube;
import objects.object3d.Sphere;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RayTracingEngine3D {

    public static void main (String[] args) {


        // Get the screen dimensions
        // Due to using a 4k monitor, I manually specify the dimensions (for obvious performance reasons)
//        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final Dimension screenSize = new Dimension(600, 400);
        final double aspect = (double)screenSize.width / (double)screenSize.height;

        // Camera and internal screen settings
        final double camDistance = 7;
        final double viewAngle = Math.PI / 3;
        final double W = 2 * camDistance * Math.tan( viewAngle / 2 );
        final double H = W / aspect;
        Point eyeLocation = new Point(0, 0, camDistance);

        // Precompute half-screen sizes
        final double W_half = W/2;
        final double H_half = H/2;

        // Initialisations
        final Input input = new KeyboardInput();
        final DrawLib drawLib = new DrawLib(screenSize.width, screenSize.height, (KeyboardInput) input);
        final ITransMatFactory matrixFactory = new TransMatFactory();

        // Initialise objects
        final List<Object3D> objects = new ArrayList<>();
        Object3D c = new Cube();
        c.setTransformation( matrixFactory.getRotation(ITransMatFactory.RotationAxis.Y, Math.PI/4) );
        c.addTransformations( matrixFactory.getRotation(ITransMatFactory.RotationAxis.X, Math.PI / 6) );
        objects.add( c );
//        Object3D sphere = new Sphere();
//        sphere.setTransformation( matrixFactory.getScaling(1.0, 1.3, 1.0) );
//        objects.add( sphere );
//        Object3D room = new Cube();
//        room.setTransformation( matrixFactory.getScaling(10.0, 10.0, 10.0) );
//        objects.add( room );

        final List<LightEmitter> lights = new ArrayList<>();
//        lights.add( new GlobalIllumination(0.6) );
        lights.add( new LightSource( new Point(0, 2, 0), 0.6 ) );

        double globalIllumination = 0.6;

        // Generate a number of frames (assuming a changing environment or moving camera)
        for (int i = 0; i < 1; i++)
        {

            long start = System.currentTimeMillis();

            // Update all inverse matrices per frame refresh
            objects.forEach(Object3D::updateInverse);

            // Iterate through each pixel
            for (int u = 0; u < screenSize.width; u++)
            for (int v = 0; v < screenSize.height; v++)
            {
                // Calculate current pixel offset
                final double ux = -W_half + (W * u) / screenSize.width;
                final double uy = -H_half + (H * v) / screenSize.height;

                // Build the ray through this pixel and the camera
                Direction defaultDirection = new Direction(-ux, -uy, -camDistance);

                // Find the closest hit
                Point hitLocation = null;
                Double closestT = null;
                Object3D closestObject = null;

                // Find all intersections
                for (Object3D object : objects)
                {

                    // Calculate specific ray for this object
                    Matrix inverseTransform = object.getInverseCache();
                    final Ray ray = new Ray(
                            new Point( inverseTransform.multiply( eyeLocation ) ),
                            new Direction( inverseTransform.multiply( defaultDirection ) )
                    );

                    // Calculate collisions
                    Double t = object.calcHitInfo(ray).getLowestT();
                    if ((t != null && t >= 0) && (closestT == null || t <= closestT))
                    {
                        // Use the transformation of the object to place this hitpoint at the right location
                        hitLocation = new Point(object.getTransformation().multiply( ray.getPoint( t ) ));
                        closestT = t;
                        closestObject = object;
                    }
                }

                // Calculate illumination in this point
                double illumination = 0;
                if (hitLocation != null)
                for (LightEmitter light : lights)
                {
                    final Point lightLocation = light.getLocation();
                    final Direction dir = new Direction(hitLocation, lightLocation);

                    final double bias = 0.01;

                    // Get hitpoints on the line between the hitpoint and the light location
                    double lightClosestT = 1;
                    for (Object3D object : objects)
                    {
                        Matrix inverseTransform = object.getInverseCache();
                        final Ray lightRay = new Ray(
                                new Point( inverseTransform.multiply( hitLocation ) ),
                                new Direction( inverseTransform.multiply( dir ) )
                        );

                        List<Double> hitTimes = object.calcHitInfo( lightRay ).getHitTimes();
                        for (double t : hitTimes)
                            if ((t >= bias) && (t < lightClosestT))
                                lightClosestT = t;
                    }

                    // Add the illumination from this light
                    // If there is no colliding object on this line, or when the object is located behind the light
                    if ( lightClosestT >= 1 )
                    {
                        illumination += light.getIntensity();
                    }

                }

                // Add global illumination for all points
                illumination += globalIllumination;

                // Find the colour of this point returning to the eye from the point of intersection
                Rgb color = (closestObject != null) ? closestObject.getcolor().applyIntensity( (float) illumination ) : new Rgb(0, 0, 0);


                // Compute the hit point and the normal vector in this point
//            Point hitPoint = ray.getPoint(closestT);

                // Update the color in this pixel
                drawLib.drawPoint(u, v, color);
            }

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
