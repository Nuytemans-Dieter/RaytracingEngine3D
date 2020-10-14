import graphics.DrawLib;
import graphics.Rgb;
import maths.Matrix;
import maths.vector.Point;
import objects.Object3D;
import objects.Ray;
import objects.object3d.Sphere;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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

        // Due to using a 4k monitor, I manually specify the dimensions (for obvious performance reasons)
//        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final Dimension screenSize = new Dimension(600, 400);
        final float viewAngle;
        final float cameraDistance;

        final DrawLib drawLib = new DrawLib(screenSize.width, screenSize.height);
//        final Point cameraLocation = new Point(0, 0, 5);
        Point cameraLocation = new Point((double)screenSize.width/2, (double) screenSize.height/2, 5);

        final List<Object3D> objects = new ArrayList<>();
        objects.add( new Sphere(100.0) );

        int numCollisions = 0;

        for (int u = 0; u < screenSize.width; u++)
        for (int v = 0; v < screenSize.height; v++)
        {
            Ray ray = new Ray(cameraLocation, u, v, 0);
            for (Object3D object : objects)
                if ( object.isColliding( ray ) )
                {
                    numCollisions++;
                    Rgb rgb = object.getcolor();
                    drawLib.drawPoint(v, u, rgb.r(), rgb.g(), rgb.b());
                    break;
                }
        }
        System.out.println("Collisions: " + numCollisions);
        drawLib.forceUpdate();

        try
        {
            Thread.sleep(100);
            drawLib.forceUpdate();
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }

    }

}
