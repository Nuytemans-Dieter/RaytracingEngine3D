package interfaces;

import java.util.Map;

public interface Input {

    enum Action {
        MOVE_FORWARD,
        MOVE_BACKWARD,
        MOVE_LEFT,
        MOVE_RIGHT,
        ROTATE_UP,
        ROTATE_DOWN,
        ROTATE_LEFT,
        ROTATE_RIGHT;

        public boolean isTranslation()
        {
            return (this == Action.MOVE_FORWARD || this == Action.MOVE_BACKWARD || this == Action.MOVE_LEFT || this == Action.MOVE_RIGHT);
        }
    }

    /**
     * Get a map of all current applied actions with a weight for each action
     *
     * @return the map of actions and weights
     */
    Map<Action, Double> getCurrentInput();

}
