import maths.Matrix;

public class RaytracingEngine3D {

    public static void main (String[] args)
    {
//        DrawLib screen = new DrawLib(720, 500);
//        screen.drawOval(300, 30, 20, 20);
//        screen.drawOval(300, 70, 20, 20);
        Matrix mat = new Matrix()
                .modify(0, 0, 2)
                .modify(1, 1, 3)
                .modify(2, 2, 4)
                .modify(3, 3, 5);

        System.out.println( mat );
    }

}
