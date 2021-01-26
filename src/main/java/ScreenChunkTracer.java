import graphics.DrawLib;
import graphics.Rgb;
import maths.Matrix;
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

    private final Matrix transform;

    public ScreenChunkTracer(int startU, int stopU, int startV, int stopV, List<Object3D> objects, List<LightEmitter> lights, GlobalIllumination globalIllumination, DrawLib drawLib, long startTime, int chunkU, int chunkV, Matrix transform)
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

        this.transform = transform;
    }

    @Override
    public void run()
    {
        for (int u = this.startU; u < this.stopU; u++)
        for (int v = this.startV; v < this.stopV; v++)
        {
            Rgb color = new Rgb(0, 0, 0);

            RayTraceInfo info = rayTracer.tracePoint(u, v, transform);
            color.addRgb( info.getClosestObject() != null ? rayTracer.calcLight( info ) : rayTracer.getVoidColor() );

            // Update the color in this pixel
            drawLib.drawPoint(u, v, color);
        }

        // Make sure all pixels are effectively drawn to the screen
        drawLib.forceUpdate();
        long end = System.currentTimeMillis();
        long delta = end - this.startTime;
//        System.out.println("Calculation time: " + delta + "ms for chunk (" + chunkU + ", " + chunkV + ")");
    }

}
