import graphics.DrawLib;
import graphics.Rgb;
import maths.Matrix;
import maths.vector.Direction;
import maths.vector.Point;
import objects.Object3D;
import objects.Ray;
import objects.object3d.Sphere;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Raytracing engine 3 d.
 */
public class RaytracingEngine3D {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main (String[] args) {


        // Get the screen dimensions
        // Due to using a 4k monitor, I manually specify the dimensions (for obvious performance reasons)
//        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final Dimension screenSize = new Dimension(600, 400);
        final double aspect = (double)screenSize.width / (double)screenSize.height;

        // Camera and internal screen settings
        final double camDistance = 5;
        final double viewAngle = Math.PI / 3;
        final double W = 2 * camDistance * Math.tan( viewAngle / 2 );
        final double H = W / aspect;
        final Point eyeLocation = new Point(0, 0, camDistance);

        // Precompute half-screen sizes
        final double W_half = W/2;
        final double H_half = H/2;

        // Initialise screen
        final DrawLib drawLib = new DrawLib(screenSize.width, screenSize.height);

        // Initialise objects
        final List<Object3D> objects = new ArrayList<>();
        objects.add( new Sphere(1) );

        for (int u = 0; u < screenSize.width; u++)
        for (int v = 0; v < screenSize.height; v++)
        {
            final double ux = -W_half + (W * u) / screenSize.width;
            final double uy = -H_half + (H * v) / screenSize.height;

            // Build the ray through this pixel and the camera
            final Ray ray = new Ray(eyeLocation, new Direction(-ux, -uy, -camDistance));

            Map<Double, Object3D> intersections = new HashMap<>();
            // Find all intersections
            for (Object3D object : objects)
            {
                Double t = object.getCollidingT(ray);
                if (t != null && t >= 0) intersections.put(t, object);
            }

            double closestT = -1;
            Object3D closestObject = null;
            // Find the closest intersection that is in front of the eye
            for (Map.Entry<Double, Object3D> entry : intersections.entrySet())
            {
                if (closestT == -1 || entry.getKey() < closestT)
                {
                    closestT = entry.getKey();
                    closestObject = entry.getValue();
                }
            }

            if (closestObject == null) continue;
            // Compute the hit point and the normal vector in this point
            Point hitPoint = ray.getPoint(closestT);

            // Find the colour of this point returning to the eye from the point of intersection
            Rgb color = closestObject.getcolor();

            // Update the color in this pixel
            drawLib.drawPoint(u, v, color);
        }

        drawLib.forceUpdate();

    }

}
