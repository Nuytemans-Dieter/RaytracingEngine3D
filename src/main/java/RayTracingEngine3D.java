import interfaces.Scene;
import datacontainers.ScreenInfo;
import graphics.DrawLib;
import input.KeyboardInput;
import interfaces.Input;
import objects.LightEmitter;
import objects.lighting.GlobalIllumination;
import objects.Object3D;
import raytracing.RayTracer;
import scenes.*;

import java.util.ArrayList;
import java.util.List;

public class RayTracingEngine3D {

    public static void main (String[] args) {

        Scene scene = new RoomScene();
        final List<Object3D> objects = scene.getObjects();
        final List<LightEmitter> lights = scene.getLights();

        RayTracer rayTracer = new RayTracer(objects, lights, new GlobalIllumination(1.0));
        ScreenInfo screenInfo = rayTracer.getScreenInfo();

        // Initialisations
        final Input input = new KeyboardInput();
        final DrawLib drawLib = new DrawLib(screenInfo.getScreenSize().width, screenInfo.getScreenSize().height, (KeyboardInput) input);

        // Update all inverse matrices
        objects.forEach(Object3D::updateInverse);

        // Start timing the ray tracing
        long start = System.currentTimeMillis();

        int numChunksU = 4;
        int numChunksV = 4;

        int width = screenInfo.getScreenSize().width;
        int height = screenInfo.getScreenSize().height;

        int uWidth = width / numChunksU;
        int vWidth = height / numChunksV;

        for (int u = 0; u < numChunksU; u++)
        for (int v = 0; v < numChunksV; v++)
            new ScreenChunkTracer(u * uWidth, (u + 1) * uWidth, v * vWidth, (v+1) * vWidth, objects, lights, new GlobalIllumination(0.4), drawLib, start, u, v).start();

        // Make sure all pixels are effectively drawn to the screen
        drawLib.forceUpdate();


    }

}
