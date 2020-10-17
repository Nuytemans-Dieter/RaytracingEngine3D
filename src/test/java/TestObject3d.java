import maths.Matrix;
import maths.TransMatFactory;
import objects.Object3D;
import objects.object3d.Sphere;
import org.junit.Test;
import interfaces.ITransMatFactory;

public class TestObject3d {

    @Test
    public void testSetTransformation()
    {
        ITransMatFactory fact = new TransMatFactory();
        Matrix translation = fact.getTranslation(20.0, 5.0, 6.4);

        Object3D object = new Sphere();

        // No transformation by default
        assert object.getTransformation().equals( fact.getTranslation(0, 0, 0) );

        // Test transformation update
        object.setTransformation( translation );
        assert object.getTransformation().equals( translation );
        Matrix rotate = fact.getRotation(ITransMatFactory.RotationAxis.X, 15.0);
        object.addTransformations(rotate);
        assert object.getTransformation().equals( translation.multiply( rotate ) );
    }

    @Test
    public void testAddTransformation()
    {
        // Initialise test objects
        Object3D first = new Sphere();
        Object3D second = new Sphere();
        ITransMatFactory fact = new TransMatFactory();

        // Generate transformation matrices
        Matrix rot = fact.getRotation(ITransMatFactory.RotationAxis.Y, Math.PI/2);
        Matrix scale = fact.getScaling(15.0, 1.2, 1.3);
        Matrix trans = fact.getTranslation(12.0, 13.0, 2.0);

        // Apply all transforms in different ways
        first.addTransformations( rot.multiply(scale).multiply(trans)  );
        second.addTransformations( rot, scale ,trans );

        assert second.getTransformation().equals(rot.multiply(scale).multiply(trans));
        assert first.getTransformation().equals( second.getTransformation() );
    }

}
