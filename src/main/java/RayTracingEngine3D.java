import interfaces.Scene;
import datacontainers.ScreenInfo;
import graphics.DrawLib;
import input.KeyboardInput;
import interfaces.Input;
import maths.Matrix;
import maths.TransMatFactory;
import objects.LightEmitter;
import objects.lighting.GlobalIllumination;
import objects.Object3D;
import raytracing.RayTracer;
import scenes.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RayTracingEngine3D {

    public static void main (String[] args) {

        Scene scene = new EmptyRoom();
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

        TransMatFactory fact = new TransMatFactory();

        Matrix transform = new Matrix();

        while(true)
        {

            Map<Input.Action, Double> map = input.getCurrentInput();
            for (Map.Entry<Input.Action, Double> entry : map.entrySet())
            {
                switch (entry.getKey())
                {
                    case MOVE_FORWARD:
                        transform = fact.getTranslation( 0, 0, -1 ).multiply( transform );
                        break;
                    case MOVE_BACKWARD:
                        transform = fact.getTranslation( 0, 0, 1 ).multiply( transform );
                        break;
                    case MOVE_LEFT:
                        transform = fact.getTranslation( 1, 0, 0 ).multiply( transform );
                        break;
                    case MOVE_RIGHT:
                        transform = fact.getTranslation( -1, 0, 0 ).multiply( transform );
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

            List<Thread> threads = new ArrayList<>();

            for (int u = 0; u < numChunksU; u++)
                for (int v = 0; v < numChunksV; v++)
                {
                    Thread thread = new ScreenChunkTracer(u * uWidth, (u + 1) * uWidth, v * vWidth, (v + 1) * vWidth, objects, lights, new GlobalIllumination(0.4), drawLib, start, u, v, transform);
                    threads.add( thread );
                    thread.start();
                }

            // Await all threads
            for (Thread thread : threads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // Make sure all pixels are effectively drawn to the screen
            drawLib.forceUpdate();
            try {
                Thread.sleep( 50 );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

}
