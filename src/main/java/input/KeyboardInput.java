package input;

import interfaces.Input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class KeyboardInput extends KeyAdapter implements Input {

    private final Map<Action, Boolean> keyStates = new HashMap<>();

    private final double TRANSLATION_WEIGHT = 0.05;
    private final double ROTATION_WEIGHT = 0.05;

    public KeyboardInput(){
        for (Action action : Action.values()) keyStates.put(action, false);
    }


    @Override
    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_Z)     keyStates.put(Action.MOVE_FORWARD,  true);
        if(key == KeyEvent.VK_S)     keyStates.put(Action.MOVE_BACKWARD, true);
        if(key == KeyEvent.VK_Q)     keyStates.put(Action.MOVE_LEFT,     true);
        if(key == KeyEvent.VK_D)     keyStates.put(Action.MOVE_RIGHT,    true);
        if(key == KeyEvent.VK_UP)    keyStates.put(Action.ROTATE_UP,     true);
        if(key == KeyEvent.VK_DOWN)  keyStates.put(Action.ROTATE_DOWN,   true);
        if(key == KeyEvent.VK_LEFT)  keyStates.put(Action.ROTATE_LEFT,   true);
        if(key == KeyEvent.VK_RIGHT) keyStates.put(Action.ROTATE_RIGHT,  true);

    }


    @Override
    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_Z)     keyStates.put(Action.MOVE_FORWARD,  false);
        if(key == KeyEvent.VK_S)     keyStates.put(Action.MOVE_BACKWARD, false);
        if(key == KeyEvent.VK_Q)     keyStates.put(Action.MOVE_LEFT,     false);
        if(key == KeyEvent.VK_D)     keyStates.put(Action.MOVE_RIGHT,    false);
        if(key == KeyEvent.VK_UP)    keyStates.put(Action.ROTATE_UP,     false);
        if(key == KeyEvent.VK_DOWN)  keyStates.put(Action.ROTATE_DOWN,   false);
        if(key == KeyEvent.VK_LEFT)  keyStates.put(Action.ROTATE_LEFT,   false);
        if(key == KeyEvent.VK_RIGHT) keyStates.put(Action.ROTATE_RIGHT,  false);
    }

    @Override
    public Map<Action, Double> getCurrentInput()
    {
        final Map<Action, Double> pressedKeys = new HashMap<>();
        for (Map.Entry<Action, Boolean> entry : keyStates.entrySet())
            if ( entry.getValue() )
                pressedKeys.put(
                        entry.getKey(),
                        entry.getKey().isTranslation() ? this.TRANSLATION_WEIGHT : this.ROTATION_WEIGHT
                );

        return pressedKeys;
    }
}
