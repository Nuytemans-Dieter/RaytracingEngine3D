import graphics.DrawLib;

public class RaytracingEngine3D {

    public static void main (String[] args)
    {
        DrawLib screen = new DrawLib(720, 500);
        screen.drawOval(300, 30, 20, 20);
        screen.drawOval(300, 70, 20, 20);
    }

}
