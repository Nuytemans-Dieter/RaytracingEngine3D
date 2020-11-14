package raytracing;

import datacontainers.HitInfo;
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
import java.util.Map;

public class RayTracer {

    private final List<Object3D> objects;
    private final List<LightEmitter> lights;
    private final GlobalIllumination globalIllumination;
    private final Rgb voidColor = new Rgb(0.2f, 0.2f, 0.2f);

    private final Point eyeLocation;

    private final ScreenInfo screenInfo;
    private final double camDistance;

    // Bias to prevent surface acne when calculating light
    public static final double BIAS = 0.01;
    // Reflection depth: Maximum recursion depth when doing reflective calculations
    private final int REFLECTION_DEPTH = 5;

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


    /**
     * Get a clone of the rgb values of the void
     *
     * @return the rgb value of the void
     */
    public Rgb getVoidColor() {
        return this.voidColor.clone();
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

        return this.tracePoint( new Ray( eyeLocation, defaultDirection ) );
    }

    /**
     * Get the RayTraceInfo for given ray
     *
     * @param ray the ray that should be traced for its nearest collision
     * @return RayTraceInfo that contains: the hit location, value of t at hit location and the closest object
     */
    public RayTraceInfo tracePoint( Ray ray)
    {
        return this.tracePoint( ray, 0 );
    }

    /**
     * Get the RayTraceInfo for given ray
     *
     * @param ray the ray that should be traced for its nearest collision
     * @param epsilon a bias to avoid surface acne due to rounding errors
     * @return RayTraceInfo that contains: the hit location, value of t at hit location and the closest object
     */
    public RayTraceInfo tracePoint( Ray ray, double epsilon )
    {
        Point origin = ray.getOrigin();
        Direction direction = ray.getDirection();

        // Find the closest hit
        Point hitLocation = null;
        Double closestT = null;
        Object3D closestObject = null;
        Ray hitRay = null;
        Direction closestNormal = null;

        // Find all intersections
        for (Object3D object : objects)
        {

            // Calculate specific ray for this object
            Matrix inverseTransform = object.getInverseCache();
            final Ray transformedRay = new Ray(
                    new Point( inverseTransform.multiply( origin ) ),
                    new Direction( inverseTransform.multiply( direction ) )
            );

            // Calculate collisions
            HitInfo info = object.calcHitInfo( transformedRay );
            Double t = info.getLowestT();
            if ((t != null && t >= epsilon) && (closestT == null || t <= closestT))
            {
                // Use the transformation of the object to place this hitpoint at the right location
                hitLocation = new Point(object.getTransformation().multiply( transformedRay.getPoint( t ) ));
                closestT = t;
                closestObject = object;
                hitRay = transformedRay;
                closestNormal = new Direction( object.getInverseCache().multiply( info.getLowestTNormal() ) );
            }
        }

        return new RayTraceInfo(hitLocation, closestT, closestObject, hitRay, closestNormal);
    }

    /**
     * Get the illumination for a specific RayTraceInfo (combined diffusion, specular and global illumination)
     * Get an object like this with raytracing.RayTracer#tracePoint(u, v)
     *
     * @param info the RayTraceInfo for a specific pixel
     * @return the amount of direct light in this location
     */
    public Rgb calculateIllumination(RayTraceInfo info)
    {
        if (info.getClosestObject() == null)
            return voidColor.clone();

        // Calculate illumination in this point
        Rgb illumination = new Rgb(0, 0, 0);
        Material hitMaterial = info.getClosestObject().getMaterial();

        Vector toViewer = info.getHitRay().getDirection().normalise().multiply(-1);
        Direction normal = info.getNormal();

        if (info.getHitLocation() != null)
        {
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
                            new Point(inverseTransform.multiply(info.getHitLocation())),
                            new Direction(inverseTransform.multiply(dir))
                    );

                    HitInfo hitInfo = object.calcHitInfo(lightRay);
                    Map<Double, Direction> hitTimes = hitInfo.getTNormalMap();

                    for (Map.Entry<Double, Direction> entry : hitTimes.entrySet())
                        if ((entry.getKey() >= BIAS) && (entry.getKey() < lightClosestT))
                            lightClosestT = entry.getKey();

                }

                // Add the illumination from this light if there is no colliding object on this line, or when the object is located behind the light
                if (lightClosestT >= 1)
                {

                    // Calculate diffusion

                    double intensity = normal.dotProduct(dir) / (normal.getNorm() * dir.getNorm());
                    // Only light up if the hit point is facing the light
                    if (dir.dotProduct(normal) > 0)
                        illumination.addRgb(
                                (float) Math.max(hitMaterial.diffusivityR * intensity * light.getColor().r(), 0),
                                (float) Math.max(hitMaterial.diffusivityG * intensity * light.getColor().g(), 0),
                                (float) Math.max(hitMaterial.diffusivityB * intensity * light.getColor().b(), 0)
                        );

                    // Calculate the specular component

                    Vector toLight = new Direction(info.getHitLocation(), lightLocation);
                    Vector halfway = toLight.add(toViewer).normalise();
                    double spec = halfway.dotProduct(normal);

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
        int recursiveDepth = recursiveIndex - 1;

        Rgb color = Rgb.fromColor( Rgb.Color.BLACK );

        // If this ray does not hit anything, assume a hit with the void
        if (info.getClosestObject() == null || info.getHitLocation() == null || info.getHitRay() == null)
            return voidColor.clone();

        Object3D object = info.getClosestObject();
        Material material = object.getMaterial();
        Point location = info.getHitLocation();

        // If the depth is reached, assume black
        if (recursiveDepth < 0)
            return color;

        if (material.reflectivity >= 0)
        {
            Ray ray = info.getHitRay();
            Vector direction = ray.getDirection();
            Direction normal = info.getNormal();

            double product = direction.dotProduct( normal );

            Direction reflectedDirection = new Direction( direction.subtract( normal.multiply( 2 * product ) ));
            Ray reflectedRay = new Ray(location, reflectedDirection);
            RayTraceInfo hitInfo = this.tracePoint( reflectedRay, this.BIAS );

            if (hitInfo.getClosestObject() != null)
            {
                Rgb reflectedComponent = this.calculateReflection(hitInfo , recursiveDepth );
                Rgb illuminationComponent = this.calculateIllumination( hitInfo );

                reflectedComponent.applyIntensity( hitInfo.getClosestObject().getMaterial().reflectivity );
                illuminationComponent.applyIntensity( hitInfo.getClosestObject().getMaterial().colorStrength );

                color.addRgb( reflectedComponent );
                color.addRgb( illuminationComponent );
            }
            else
            {
                color.addRgb( this.voidColor.clone() );
            }
        }
        return color;
    }
}