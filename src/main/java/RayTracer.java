import datacontainers.RayTraceInfo;
import datacontainers.ScreenInfo;
import maths.Matrix;
import maths.vector.Direction;
import maths.vector.Point;
import objects.LightEmitter;
import objects.Object3D;
import objects.Ray;

import java.awt.*;
import java.util.List;

public class RayTracer {

    private final List<Object3D> objects;
    private final List<LightEmitter> lights;
    private final double globalIllumination;

    private final Point eyeLocation;

    private final ScreenInfo screenInfo;
    private final double camDistance;

    // Bias to prevent surface acne when calculating light
    final double bias = 0.01;

    public RayTracer(List<Object3D> objects, List<LightEmitter> lights, double globalIllumination)
    {

        // Get the screen dimensions
        // Due to using a 4k monitor, I manually specify the dimensions (for obvious performance reasons)
//        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final Dimension screenSize = new Dimension(600, 400);
        final double aspect = (double)screenSize.width / (double)screenSize.height;

        // Camera and internal screen settings
        this.camDistance = 7;
        final double viewAngle = Math.PI / 3;
        final double W = 2 * camDistance * Math.tan( viewAngle / 2 );
        final double H = W / aspect;
        this.eyeLocation = new Point(0, 0, camDistance);

        screenInfo = new ScreenInfo(screenSize, W, H);

        this.objects = objects;
        this.lights = lights;
        this.globalIllumination = globalIllumination;
    }

    public ScreenInfo getScreenInfo()
    {
        return this.screenInfo;
    }

    /**
     * Get the RayTraceInfo for a ray through the camera and a specific pixel indicated by u and v
     *
     * @param u the column's index of this pixel
     * @param v the row's index of this pixel
     * @return RayTraceInfo that contains: the hit location, value of t at hit location and the closest object
     */
    public RayTraceInfo tracePoint(int u, int v)
    {
        // Calculate current pixel offset
        final double ux = -screenInfo.W_half + (screenInfo.W * u) / screenInfo.getScreenSize().width;
        final double uy = -screenInfo.H_half + (screenInfo.H * v) / screenInfo.getScreenSize().height;

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

        return new RayTraceInfo(hitLocation, closestT, closestObject);
    }

    /**
     * Get the illumination for a specific RayTraceInfo
     * Get an object like this with RayTracer#tracePoint(u, v)
     *
     * @param info the RayTraceInfo for a specific pixel
     * @return the amount of direct light in this location
     */
    public double getIllumination(RayTraceInfo info)
    {
        // Calculate illumination in this point
        double illumination = 0;

        if (info.getHitLocation() != null)
            for (LightEmitter light : lights)
            {
                final Point lightLocation = light.getLocation();
                final Direction dir = new Direction(info.getHitLocation(), lightLocation);

                // Get hitpoints on the line between the hitpoint and the light location
                double lightClosestT = 1;
                for (Object3D object : objects)
                {
                    Matrix inverseTransform = object.getInverseCache();
                    final Ray lightRay = new Ray(
                            new Point( inverseTransform.multiply( info.getHitLocation() ) ),
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

        return illumination;
    }

}
