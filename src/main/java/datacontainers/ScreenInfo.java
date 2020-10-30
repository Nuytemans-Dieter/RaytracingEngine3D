package datacontainers;

import java.awt.*;

public class ScreenInfo {

    private final Dimension screenSize;

    // Make immutable fields easily accessible
    public final double W;
    public final double H;
    public final double W_half;
    public final double H_half;

    public ScreenInfo(Dimension screenSize, double W, double H)
    {
        this.screenSize = screenSize;

        this.W = W;
        this.H = H;

        this.W_half = W / 2;
        this.H_half = H / 2;
    }

    public Dimension getScreenSize()
    {
        return this.screenSize;
    }

}
