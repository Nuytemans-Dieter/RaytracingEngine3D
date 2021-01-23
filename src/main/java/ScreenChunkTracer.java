import graphics.DrawLib;
import graphics.Rgb;
import objects.LightEmitter;
import objects.Material;
import objects.Object3D;
import objects.lighting.GlobalIllumination;
import raytracing.RayTraceInfo;
import raytracing.RayTracer;

import java.util.List;

public class ScreenChunkTracer extends Thread {

    private final RayTracer rayTracer;
    private final DrawLib drawLib;

    private final int startU;
    private final int stopU;
    private final int startV;
    private final int stopV;

    private final long startTime;
    private final int chunkU;
    private final int chunkV;

    public ScreenChunkTracer(int startU, int stopU, int startV, int stopV, List<Object3D> objects, List<LightEmitter> lights, GlobalIllumination globalIllumination, DrawLib drawLib, long startTime, int chunkU, int chunkV)
    {
        this.startU = startU;
        this.stopU = stopU;
        this.startV = startV;
        this.stopV = stopV;

        this.rayTracer = new RayTracer(objects, lights, globalIllumination);
        this.drawLib = drawLib;

        this.startTime = startTime;
        this.chunkU = chunkU;
        this.chunkV = chunkV;
    }

    @Override
    public void run()
    {
        for (int u = this.startU; u < this.stopU; u++)
        for (int v = this.startV; v < this.stopV; v++)
        {
            Rgb color = new Rgb(0, 0, 0);

            RayTraceInfo info = rayTracer.tracePoint(u, v);
            if ( info.getClosestObject() != null )
            {
//                Material material = info.getClosestObject().getMaterial();
//                Rgb illumination = rayTracer.calculateIllumination( info ).applyIntensity( material.colorStrength );
//                Rgb reflection = rayTracer.calculateReflection( info ).applyIntensity( material.reflectivity );
//                Rgb refraction = rayTracer.calculateRefraction( info ).applyIntensity( material.transparency );

                // Find the colour of this point returning to the eye from the point of intersection
//                color.addRgb( illumination ).addRgb( reflection ).addRgb( refraction );
                color.addRgb( rayTracer.calcLight( info ) );
            }
            else
            {
                color.addRgb( rayTracer.getVoidColor() );
            }

            // Update the color in this pixel
            drawLib.drawPoint(u, v, color);
        }

        // Make sure all pixels are effectively drawn to the screen
        drawLib.forceUpdate();
        long end = System.currentTimeMillis();
        long delta = end - this.startTime;
        System.out.println("Calculation time: " + delta + "ms for chunk (" + chunkU + ", " + chunkV + ")");
    }

}
