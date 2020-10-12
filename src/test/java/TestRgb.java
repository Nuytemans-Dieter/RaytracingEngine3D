import graphics.Rgb;
import org.junit.Test;

public class TestRgb {

    @Test
    public void testRgb()
    {
        Rgb rgb = new Rgb(0.2f, 0.8f, 0.4f);
        assert rgb.r() == 0.2f;
        assert rgb.g() == 0.8f;
        assert  rgb.b() == 0.4f;
    }

}
