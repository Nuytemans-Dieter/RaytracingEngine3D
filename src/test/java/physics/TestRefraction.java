package physics;

import maths.Vector;
import maths.vector.Direction;
import objects.Ray;
import org.junit.Test;

public class TestRefraction {

    @Test
    public void testRefraction()
    {
        float c3 = 1f;

        // Refraction calculations

        Direction direction = new Direction(1, 0, 1);
        Direction normal = new Direction(0, 0, 1);

        double normDotDir = normal.dotProduct( direction );
        double cosTheta2 = Math.sqrt( 1 - Math.pow( c3, 2 ) * (1 - Math.pow(normDotDir, 2) ));
        double factor = c3 * normDotDir - cosTheta2;

        Vector dirComponent = direction.multiply( c3 );
        Vector refractedDirection = dirComponent.add( normal.multiply(factor) );

        assert  refractedDirection.equals( new Direction( 1, 0, 1) );
    }

}
