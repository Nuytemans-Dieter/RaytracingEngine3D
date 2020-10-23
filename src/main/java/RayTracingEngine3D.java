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
import objects.lighting.GlobalIllumination;
import objects.lighting.LightSource;
import objects.Object3D;
import objects.Ray;
import objects.object3d.Cube;
import objects.object3d.Sphere;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        objects.add( c );
        Object3D room = new Cube();
        room.setTransformation( matrixFactory.getScaling(10.0, 10.0, 10.0) );
        objects.add( room );

        final List<LightEmitter> lights = new ArrayList<>();
        lights.add( new GlobalIllumination(0.2) );
        lights.add( new LightSource( new Point(3, 0.5, 0.5), 0.2 ) );

        for (int i = 0; i < 1; i++)
        {

            long start = System.currentTimeMillis();

            // Update all inverse matrices per frame refresh
            objects.forEach(Object3D::updateInverse);

            for (int u = 0; u < screenSize.width; u++)
            for (int v = 0; v < screenSize.height; v++)
            {
                final double ux = -W_half + (W * u) / screenSize.width;
                final double uy = -H_half + (H * v) / screenSize.height;

                // Build the ray through this pixel and the camera
                Direction defaultDirection = new Direction(-ux, -uy, -camDistance);

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
                            new Direction( inverseTransform.multiply(defaultDirection) )
                    );

                    // Calculate collisions
                    Double t = object.getCollidingT(ray);
                    if ((t != null && t >= 0) && (closestT == null || t <= closestT))
                    {
                        hitLocation = ray.getPoint( t );
                        closestT = t;
                        closestObject = object;
                    }
                }

                double illumination = 0;
                if (hitLocation != null)
                for (LightEmitter light : lights)
                {
                    final Point lightLocation = light.getLocation();
                    Direction dir = new Direction(
                        lightLocation.getX() - hitLocation.getX(),
                        lightLocation.getY() - hitLocation.getY(),
                        lightLocation.getZ() - hitLocation.getZ()
                    );

                    Double lightClosestT = null;
                    for (Object3D object : objects)
                    {
                        final Ray ray = new Ray(
//                                new Point( object.getInverseCache().multiply( hitLocation ) ),
//                                new Direction( object.getInverseCache().multiply( dir ) )
                                hitLocation, dir
                        );
                        Double t = object.getCollidingT( ray );
                        if (lightClosestT == null || t < lightClosestT)
                            lightClosestT = t;
                    }

                    System.out.println(lightClosestT);
                    if (lightClosestT == null || lightClosestT < 0.2 || lightClosestT > 1  )
                        illumination += light.getIntensity();

                }
                else for (LightEmitter light : lights)
                    if (light instanceof GlobalIllumination)
                        illumination += light.getIntensity();

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
