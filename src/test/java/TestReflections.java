import maths.vector.Direction;
import org.junit.Test;

public class TestReflections {

    @Test
    public void testReflection()
    {

        Direction dir = new Direction(
                -0.41291008728635326,
                -0.8644540160269087,
                -0.28674817173283856
        );

        Direction normal = new Direction(
                0.43248621682754446,
                0.869724915376641,
                0.2377693080010391
        );

        Direction result = new Direction(
                0.45084708764007464,
                0.8725519430980506,
                0.18812232765485243
        );

        assert calcReflectedDirection( dir, normal ).equals( result );

        dir = new Direction(
                -3.248557514640294,
                -8.0,
                4.310882009949205
        );

        normal = new Direction(
                0,
                0,
                1
        );

        result = new Direction(
                -3.248557514640294,
                -8.0,
                -4.310882009949205
        );

        assert calcReflectedDirection( dir, normal ).equals( result );

    }


    private Direction calcReflectedDirection(Direction direction, Direction normal)
    {
        double product = direction.dotProduct(normal) * 2;
        Direction scalarNormal = normal.multiply( product ).toDirection();
        return direction.subtract( scalarNormal ).toDirection();
    }
}
