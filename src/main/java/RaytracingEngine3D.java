import graphics.DrawLib;
import graphics.Rgb;
import maths.Matrix;
import maths.vector.Point;
import objects.Object3D;
import objects.Ray;
import objects.object3d.Sphere;

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

        int width = 400;
        int heigth = 200;

        DrawLib drawLib = new DrawLib(width, heigth);
        Point cameraLocation = new Point((double)width/2, (double)heigth/2, 5);

        List<Object3D> objects = new ArrayList<>();
        objects.add( new Sphere() );

//        for (int x = 0; x < width; x++)
//        {
//            for (int y = 0; y < heigth; y++)
//            {
//                Ray ray = new Ray(cameraLocation, x, y);
//                for (Object3D object : objects)
//                    if ( object.isColliding( ray ) )
//                    {
//                        Rgb rgb = object.getcolor();
//                        drawLib.drawPoint(y, x, rgb.r(), rgb.g(), rgb.b());
//                        break;
//                    }
//            }
//        }
    }

}
