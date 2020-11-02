import datacontainers.RayTraceInfo;
import datacontainers.ScreenInfo;
import graphics.Rgb;
import maths.Matrix;
import maths.Vector;
import maths.vector.Direction;
import maths.vector.Point;
import objects.LightEmitter;
import objects.Material;
import objects.Object3D;
import objects.Ray;
import objects.lighting.GlobalIllumination;

import java.awt.*;
import java.util.List;

public class RayTracer {

    private final List<Object3D> objects;
    private final List<LightEmitter> lights;
    private final GlobalIllumination globalIllumination;

    private final Point eyeLocation;

    private final ScreenInfo screenInfo;
    private final double camDistance;

    // Bias to prevent surface acne when calculating light
    private final double BIAS = 0.01;
    // Reflection depth: Maximum recursion depth when doing reflective calculations
    private final int REFLECTION_DEPTH = 1;

    public RayTracer(List<Object3D> objects, List<LightEmitter> lights, GlobalIllumination globalIllumination)
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
        Ray hitRay = null;

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
                hitRay = ray;
            }
        }

        return new RayTraceInfo(hitLocation, closestT, closestObject, hitRay);
    }

    /**
     * Get the illumination for a specific RayTraceInfo (combined diffusion, specular and global illumination)
     * Get an object like this with RayTracer#tracePoint(u, v)
     *
     * @param info the RayTraceInfo for a specific pixel
     * @return the amount of direct light in this location
     */
    public Rgb getIllumination(RayTraceInfo info)
    {
        if (info.getClosestObject() == null)
            return new Rgb(
                    globalIllumination.getColor().r(),
                    globalIllumination.getColor().g(),
                    globalIllumination.getColor().b()
            );

        // Calculate illumination in this point
        Rgb illumination = new Rgb(0, 0, 0);
        Material hitMaterial = info.getClosestObject().getMaterial();

        Vector toViewer = info.getHitRay().getDirection().normalise().multiply( -1 );

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
                        if ((t >= BIAS) && (t < lightClosestT))
                            lightClosestT = t;
                }

                // Add the illumination from this light if there is no colliding object on this line, or when the object is located behind the light
                if ( lightClosestT >= 1)
                {

                    // Calculate diffusion

                    Direction normal = info.getClosestObject().getNormal( info.getHitLocation() );
                    double intensity = normal.dotProduct( dir ) / ( normal.getNorm() * dir.getNorm() );
                    // Only light up if the hit point is facing the light
                    if (dir.dotProduct( normal ) > 0)
                        illumination.addRgb(
                            (float) Math.max( hitMaterial.diffusivityR * intensity * light.getColor().r(), 0 ),
                            (float) Math.max( hitMaterial.diffusivityG * intensity * light.getColor().g(), 0 ),
                            (float) Math.max( hitMaterial.diffusivityB * intensity * light.getColor().b(), 0 )
                        );

                    // Calculate the specular component

                    Vector toLight = new Direction( info.getHitLocation(), lightLocation );
                    Vector halfway = toLight.add( toViewer ).normalise();
                    double spec = halfway.dotProduct( normal );

                    // If the hit point is facing the light
                    if (spec > 0)
                    {
                        double phong = Math.pow(spec, hitMaterial.roughness);
                        illumination.addRgb(
                            (float) Math.max(hitMaterial.specularR * phong * light.getColor().r(), 0),
                            (float) Math.max(hitMaterial.specularG * phong * light.getColor().g(), 0),
                            (float) Math.max(hitMaterial.specularB * phong * light.getColor().b(), 0)
                        );
                    }
                }

            }

        // Add global illumination
        illumination.addRgb(
            Math.max(globalIllumination.getColor().r() * hitMaterial.ambientR, 0),
            Math.max(globalIllumination.getColor().g() * hitMaterial.ambientB, 0),
            Math.max(globalIllumination.getColor().b() * hitMaterial.ambientB, 0)
        );

        Rgb color = info.getClosestObject().getColor();
        color.applyIntensity( illumination );
        return color;
    }


    public Rgb calculateReflection(RayTraceInfo info)
    {
        return this.calculateReflection(info, this.REFLECTION_DEPTH);
    }

    private Rgb calculateReflection(RayTraceInfo info, int recursiveIndex)
    {
        int index = recursiveIndex - 1;

        Rgb color = new Rgb(0, 0, 0);

        // Stop recursion when there is no hit object
        if (info.getHitLocation() == null || info.getClosestObject() == null || info.getHitRay() == null)
            return color;

        Ray viewRay = info.getHitRay();
        Vector direction = viewRay.getDirection().normalise();
        Vector normal = info.getClosestObject().getNormal( info.getHitLocation() ).normalise();

        double product = direction.dotProduct( normal );
        if (product < 0)
            normal = normal.multiply(-1);

        Vector reflectedDirection = direction.subtract( normal.multiply( 2 * product ) );

        // Find the closest hit
        Point hitLocation = null;
        Double closestT = null;
        Object3D closestObject = null;
        Ray hitRay = null;

        // Find all intersections
        for (Object3D object : objects)
        {

            // Calculate specific ray for this object
            Matrix inverseTransform = object.getInverseCache();
            final Ray reflected = new Ray(
                    new Point( inverseTransform.multiply( info.getHitLocation() ) ),
                    new Direction( inverseTransform.multiply( reflectedDirection ) )
            );

            // Calculate collisions
            Double t = object.calcHitInfo( reflected ).getLowestT();
            if ((t != null && t >= 0) && (closestT == null || t <= closestT))
            {
                // Use the transformation of the object to place this hitpoint at the right location
                hitLocation = new Point(object.getTransformation().multiply( reflected.getPoint( t ) ));
                closestT = t;
                closestObject = object;
                hitRay = reflected;
            }
        }

        RayTraceInfo newInfo = new RayTraceInfo(hitLocation, closestT, closestObject, hitRay);

        Material hitMaterial = info.getClosestObject().getMaterial();

        Rgb lightComponent = hitMaterial.getColor().applyIntensity( hitMaterial.colorStrength );
        Rgb reflectionComponent;
        if (index > 0)
        {
            reflectionComponent = this.calculateReflection(newInfo, index);
            reflectionComponent.applyIntensity( hitMaterial.reflectivity );
        }
        else reflectionComponent = new Rgb(0, 0, 0);

//        Rgb refractionComponent = new Rgb(0, 0, 0).applyIntensity( hitMaterial.transparency );

        color.addRgb( lightComponent ).addRgb( reflectionComponent );//.addRgb( refractionComponent );
        return color;
    }
}
