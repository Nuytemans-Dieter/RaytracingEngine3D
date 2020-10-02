import graphics.DrawLib;
import maths.Matrix;

public class RaytracingEngine3D {

    public static void main (String[] args)
    {
//        DrawLib screen = new DrawLib(720, 500);
//        screen.drawOval(300, 30, 20, 20);
//        screen.drawOval(300, 70, 20, 20);
        Matrix mat = new Matrix()
                .setPosition(0, 0, 2)
                .setPosition(1, 1, 3)
                .setPosition(2, 2, 4)
                .setPosition(3, 3, 5);

        System.out.println( mat );
    }

}
