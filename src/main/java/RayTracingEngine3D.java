import graphics.DrawLib;
import graphics.Rgb;
import input.KeyboardInput;
import interfaces.ITransMatFactory;
import interfaces.Input;
import maths.Matrix;
import maths.TransMatFactory;
import maths.vector.Direction;
import maths.vector.Point;
import objects.LightSource;
import objects.Object3D;
import objects.Ray;
import objects.object3d.Cube;

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

        final List<LightSource> lights = new ArrayList<>();
        lights.add( new LightSource( new Point(0, 7, 3) ) );

        for (int i = 0; i < 100; i++)
        {

            long start = System.currentTimeMillis();

            Map<Input.Action, Double> map = input.getCurrentInput();
            Matrix transform = new Matrix();
            for (Map.Entry<Input.Action, Double> entry : map.entrySet())
            {
                switch (entry.getKey())
                {
                    case MOVE_FORWARD:
                        break;
                    case MOVE_BACKWARD:
                        break;
                    case MOVE_LEFT:
                        break;
                    case MOVE_RIGHT:
                        break;
                    case ROTATE_UP:
                        break;
                    case ROTATE_DOWN:
                        break;
                    case ROTATE_LEFT:
                        break;
                    case ROTATE_RIGHT:
                        break;
                }
            }

            objects.forEach(Object3D::updateInverse);

            for (int u = 0; u < screenSize.width; u++)
            for (int v = 0; v < screenSize.height; v++)
            {
                final double ux = -W_half + (W * u) / screenSize.width;
                final double uy = -H_half + (H * v) / screenSize.height;

                // Build the ray through this pixel and the camera
                Direction defaultDirection = new Direction(-ux, -uy, -camDistance);
//                defaultDirection = new Direction( matrixFactory.getRotation(ITransMatFactory.RotationAxis.Y, Math.PI/6).multiply( defaultDirection ) );

                Double closestT = null;
                Object3D closestObject = null;
                // Find all intersections
                for (Object3D object : objects)
                {
//                    object.addTransformations( matrixFactory.getRotation(ITransMatFactory.RotationAxis.Z, Math.PI / 10) );

                    // Calculate specific ray for this object
//                    Matrix inverseTransform = object.getTransformation().inverse();
                    Matrix inverseTransform = object.getInverseCache();
                    final Ray ray = new Ray(
                            new Point( inverseTransform.multiply( eyeLocation ) ),
                            new Direction( inverseTransform.multiply(defaultDirection) )
                    );

                    // Calculate collisions
                    Double t = object.getCollidingT(ray);
                    if ((t != null && t >= 0) && (closestT == null || t <= closestT))
                    {
                        closestT = t;
                        closestObject = object;
                    }
                }


                // Find the colour of this point returning to the eye from the point of intersection
                Rgb color = (closestObject != null) ? closestObject.getcolor() : new Rgb(0, 0, 0);


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
