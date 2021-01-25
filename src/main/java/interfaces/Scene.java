package interfaces;

import objects.LightEmitter;
import objects.Object3D;

import java.util.List;

public interface Scene {

    List<Object3D> getObjects();
    List<LightEmitter> getLights();

}
