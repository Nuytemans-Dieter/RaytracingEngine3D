package raytracing;

import datacontainers.HitInfo;
import datacontainers.ScreenInfo;
import graphics.Rgb;
import interfaces.ITransMatFactory;
import maths.Matrix;
import maths.TransMatFactory;
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
    private final Rgb voidColor = new Rgb(0.2f, 0.2f, 0.2f);

    private final Point eyeLocation;
//    private final Direction viewDirection;
    private final Matrix transformation;

    private final ScreenInfo screenInfo;
    private final double camDistance;

    public final boolean DISABLE_SHADOWS = false;

    public static final double EPSILON = 0.00000001; // Bias to prevent surface acne when calculating light
    private final int RECURSION_DEPTH = 5;           // Maximum recursion depth for reflection and refraction
    private final double REFLECTION_THRESHOLD = 0;   // The minimum amount of reflectivity of a material before it is allowed to reflect
    private final double REFRACTION_THRESHOLD = 0;   // The minimum amount of transparency of a material before it is allowed to reflect

    public RayTracer(List<Object3D> objects, List<LightEmitter> lights, GlobalIllumination globalIllumination)
    {

        // Get the screen dimensions
//        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final Dimension screenSize = new Dimension(600, 400);
        final double aspect = (double)screenSize.width / (double)screenSize.height;

        // Camera and internal screen settings
        this.camDistance = 7;
        final double viewAngle = Math.PI / 2;
        final double W = 2 * camDistance * Math.tan( viewAngle / 2 );
        final double H = W / aspect;
        this.eyeLocation = new Point(0, 0, camDistance);
//        this.viewDirection = new Direction(0, 0, 1);
        this.transformation = new TransMatFactory().getRotation(ITransMatFactory.RotationAxis.Y, 0);

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
                            // Offset of the current pixel                      Offset used to center screen
        final double ux = (screenInfo.W * u) / screenInfo.getScreenSize().width  - screenInfo.W_half;
        final double uy = (screenInfo.H * v) / screenInfo.getScreenSize().height - screenInfo.H_half;

        // Build the ray through this pixel and the camera
        Direction defaultDirection = new Direction(-ux, -uy, -camDistance)/*.add( this.viewDirection ).toDirection()*/;
        defaultDirection = this.transformation.multiply( defaultDirection );

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
        return this.tracePoint( ray, RayTracer.EPSILON );
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
                    inverseTransform.multiply( origin ),
                    inverseTransform.multiply( direction )
            );

            // Calculate collisions
            HitInfo info = object.calcHitInfo( transformedRay, epsilon );
            Double t = info.getLowestT();
            if ((t != null && t >= epsilon) && (closestT == null || t <= closestT))
            {
                // Use the transformation of the object to place this hitpoint at the right location
                hitLocation = object.getTransformation().multiply( transformedRay.getPoint( t ) );
                closestT = t;
                closestObject = object;
                hitRay = new Ray(origin, direction);
                closestNormal = info.getLowestTNormal();
            }
        }

        return new RayTraceInfo(hitLocation, closestT, closestObject, hitRay, closestNormal);
    }


    /**
     * Get the first object that a given ray will exit
     * Automatically considers an epsilon value that acts as a bias to prevent surface acne
     *
     * @param ray the traced ray
     * @return The first object that this ray will exit (or the last object that an inverse ray enters)
     */
    public Object3D getFirstExitedObject(Ray ray)
    {
        Point origin = ray.getOrigin();
        Direction direction = ray.getDirection();

        // Find the closest hit
        Double closestT = null;
        Object3D closestObject = null;

        // Find all intersections
        for (Object3D object : objects)
        {

            // Calculate specific ray for this object
            Matrix inverseTransform = object.getInverseCache();
            final Ray transformedRay = new Ray(
                    inverseTransform.multiply( origin ),
                    inverseTransform.multiply( direction )
            );

            // Calculate collisions
            HitInfo info = object.calcHitInfo( transformedRay, RayTracer.EPSILON );
            Double t = info.getLowestT();
            if ((t != null && t >= RayTracer.EPSILON) && (closestT == null || t <= closestT) && !info.isLowestEntering())
            {
                // Use the transformation of the object to place this hitpoint at the right location
                closestT = t;
                closestObject = object;
            }
        }

        return closestObject;
    }

    public Rgb calcLight(RayTraceInfo info)
    {
        return this.calcLight( info, this.RECURSION_DEPTH );
    }

    public Rgb calcLight(RayTraceInfo info, int depth)
    {
        assert depth >= 0;

        Rgb color = new Rgb(0, 0, 0);

        Object3D hitObject = info.getClosestObject();

        // If no object is hit, return color of the void
        if (hitObject == null)
            return this.voidColor.clone();

        // Add the illumination (global, diffuse and specular)
        color.addRgb( this.calculateIllumination( info ) );

        // Limit recursion depth
        if (depth <= 0)
            return color;

        // Apply material properties to illumination when reflection/refraction has to take place
        color.applyIntensity( hitObject.getMaterial().colorStrength );

        // Calculate the transformed normal: the inverse transposed matrix does not suffer from non-uniform scaling
        Direction transformedNormal = hitObject.getInverseCache().transpose().multiply( info.getNormal()).normalise();
//        Direction transformedNormal = hitObject.getTransformation().multiply( info.getNormal()).normalise();

        depth--;

        final float reflectivity = hitObject.getMaterial().reflectivity;
        if (reflectivity > this.REFLECTION_THRESHOLD)
        {

            // Reflection calculations

            Direction hitDirection = info.getHitRay().getDirection();
            double product = hitDirection.dotProduct( transformedNormal ) * 2;
            Direction scalarNormal = transformedNormal.multiply( product ).toDirection();
            Direction reflectedDirection = hitDirection.subtract( scalarNormal ).toDirection();

            Ray reflectedRay = new Ray(info.getHitLocation(), reflectedDirection);
            RayTraceInfo reflectedHitInfo = this.tracePoint( reflectedRay, EPSILON);

            color.addRgb( this.calcLight( reflectedHitInfo, depth ) ).applyIntensity( reflectivity );
        }

        final float transparency = hitObject.getMaterial().transparency;
        if (transparency > this.REFRACTION_THRESHOLD)
        {

            // Get the last entered object
            Ray reversedRay = new Ray(
                info.getHitLocation(),
                info.getHitRay().getDirection().multiply(-1).toDirection()
            );

            Object3D previous = this.getFirstExitedObject( reversedRay );

            float[] c1 = hitObject.getMaterial().getLightSpeed();
            float[] c2 = previous != null ? previous.getMaterial().getLightSpeed() : new float[]{1, 1, 1};
            float[] c3 = new float[] { c2[0]/c1[0], c2[1]/c1[1], c2[2]/c1[2] };

            // Refraction calculations

            Ray ray = info.getHitRay();
            Direction direction = ray.getDirection();

            double mDotDir = transformedNormal.dotProduct( direction );
            double cosTheta2 = Math.sqrt( 1 - Math.pow( c3[0], 2 ) * (1 - Math.pow(mDotDir, 2) ));
            double factor = c3[0] * mDotDir - cosTheta2;

            Vector dirComponent = direction.multiply( c3[0] );
            Vector normComponent = transformedNormal.multiply( factor );
            Vector refractedDirection = transformedNormal.add( dirComponent );
            refractedDirection = normComponent.add( refractedDirection.multiply(-1) );

            Ray refracted = new Ray(
                info.getHitLocation(),
                refractedDirection.toDirection()
            );

            RayTraceInfo refractedHitInfo = this.tracePoint(refracted, EPSILON);
            color.addRgb( this.calcLight( refractedHitInfo, depth ) ).applyIntensity( transparency );
        }

        return color;
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

        if (info.getHitLocation() != null)
        {
            Direction normal = info.getClosestObject().getTransformation().multiply( info.getNormal() ).normalise();

            for (LightEmitter light : lights)
            {
                // Build a ray from the hitpoint to the light: t=0 at hitpoint, t=1 at the light
                final Direction dir = new Direction( info.getHitLocation(), light.getLocation());
                final Ray lightRay = new Ray(info.getHitLocation(), dir);

                // Get hitpoints on the line between the hitpoint and the light location
                RayTraceInfo hit = this.tracePoint( lightRay, EPSILON);
                double lightClosestT = hit.getClosestT() != null ? hit.getClosestT() : 1;

                // Handle transparent objects
                double lightEffectivity = lightClosestT < 1 ? hit.getClosestObject().getMaterial().transparency : 1;
                if (this.DISABLE_SHADOWS) lightEffectivity = 1;

                // Add the illumination from this light if there is no colliding object on this line, or when the object is located behind the light
                if (lightEffectivity > 0)
                {

                    // Calculate diffusion

                    double product = normal.dotProduct(dir);
                    if ( product < 0 )
                        normal = normal.multiply( -1 ).toDirection();

                    double intensity = product / Math.abs(normal.getNorm() * dir.getNorm());

                    // Only light up if the hit point is facing the light
                    if (intensity > 0)
                        illumination.addRgb(
                                (float) Math.max(hitMaterial.diffusivityR * intensity * light.getColor().r() * lightEffectivity, 0),
                                (float) Math.max(hitMaterial.diffusivityG * intensity * light.getColor().g() * lightEffectivity, 0),
                                (float) Math.max(hitMaterial.diffusivityB * intensity * light.getColor().b() * lightEffectivity, 0)
                        );

                    // Calculate the specular component

                    Vector toViewer = info.getHitRay().getDirection().multiply(-1).normalise();

                    Vector toLight = new Direction( info.getHitLocation(), light.getLocation() );
                    Vector halfway = toLight.add( toViewer ).normalise();
                    double spec = halfway.dotProduct( normal );

                    if (spec > 0) // If the hit point is facing the light
                    {
                        double phong = Math.pow(spec, hitMaterial.roughness);
                        illumination.addRgb(
                                (float) Math.max(hitMaterial.specularR * phong * light.getColor().r() * lightEffectivity, 0),
                                (float) Math.max(hitMaterial.specularG * phong * light.getColor().g() * lightEffectivity, 0),
                                (float) Math.max(hitMaterial.specularB * phong * light.getColor().b() * lightEffectivity, 0)
                        );
                    }
                }

            }
        }

        // Add global illumination
        illumination.addRgb(
            Math.max(globalIllumination.getColor().r(), 0),
            Math.max(globalIllumination.getColor().g(), 0),
            Math.max(globalIllumination.getColor().b(), 0)
        );

        Rgb color = info.getClosestObject().getColor();
        color.applyIntensity( illumination );
        return color;
    }

}
