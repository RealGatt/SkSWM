package space.gatt.skswm.enums;

import com.grinderwolf.swm.api.world.properties.SlimeProperties;
import com.grinderwolf.swm.api.world.properties.SlimeProperty;

public enum SWMProperty {

    SPAWN_X(SlimeProperties.SPAWN_X),
    SPAWN_Y(SlimeProperties.SPAWN_Y),
    SPAWN_Z(SlimeProperties.SPAWN_Z),
    DIFFICULTY(SlimeProperties.DIFFICULTY),
    ENVIRONMENT(SlimeProperties.ENVIRONMENT),
    PVP(SlimeProperties.PVP),
    WORLD_TYPE(SlimeProperties.WORLD_TYPE),
    ALLOW_ANIMALS(SlimeProperties.ALLOW_ANIMALS),
    ALLOW_MONSTERS(SlimeProperties.ALLOW_MONSTERS);

    private SlimeProperty prop;

    public SlimeProperty getProperty() {
        return prop;
    }

    SWMProperty(SlimeProperty prop) {
        this.prop = prop;
    }
}
