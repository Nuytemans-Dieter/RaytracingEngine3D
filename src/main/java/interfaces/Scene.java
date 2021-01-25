package interfaces;

import maths.TransMatFactory;
import objects.LightEmitter;
import objects.Object3D;

import java.util.ArrayList;
import java.util.List;

public interface Scene {

    TransMatFactory fact = new TransMatFactory();

    List<Object3D> objects = new ArrayList<>();
    List<LightEmitter> lights = new ArrayList<>();

    List<Object3D> getObjects();
    List<LightEmitter> getLights();

}
