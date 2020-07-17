package space.gatt.skswm.enums;

import com.grinderwolf.swm.api.world.properties.SlimeProperties;
import com.grinderwolf.swm.api.world.properties.SlimeProperty;

public enum SWMProperty {

    SPAWN_X         (SlimeProperties.SPAWN_X, "Integer"),
    SPAWN_Y         (SlimeProperties.SPAWN_Y, "Integer"),
    SPAWN_Z         (SlimeProperties.SPAWN_Z, "Integer"),
    DIFFICULTY      (SlimeProperties.DIFFICULTY, "String"),
    ENVIRONMENT     (SlimeProperties.ENVIRONMENT, "String"),
    PVP             (SlimeProperties.PVP, "Boolean"),
    WORLD_TYPE      (SlimeProperties.WORLD_TYPE, "String"),
    ALLOW_ANIMALS   (SlimeProperties.ALLOW_ANIMALS, "Boolean"),
    ALLOW_MONSTERS  (SlimeProperties.ALLOW_MONSTERS, "Boolean");

// SPAWN_X, SPAWN_Y, SPAWN_Z, DIFFICULTY, ENVIRONMENT, PVP, WORLD_TYPE, ALLOW_ANIMALS, ALLOW_MONSTERS

    private SlimeProperty prop;
    private String valueType;

    public SlimeProperty getProperty() {
        return prop;
    }


    public String getValueType() {
        return valueType;
    }

    SWMProperty(SlimeProperty prop, String valueType) {
        this.prop = prop;
        this.valueType = valueType;
    }
}
