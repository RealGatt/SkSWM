package space.gatt.skswm.elements.exprs;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.grinderwolf.swm.api.loaders.SlimeLoader;
import com.grinderwolf.swm.api.world.SlimeWorld;
import com.grinderwolf.swm.api.world.properties.SlimePropertyMap;
import org.bukkit.event.Event;
import space.gatt.skswm.SWMAddon;
import space.gatt.skswm.enums.SWMProperty;
import space.gatt.skswm.enums.SWMStorageType;

@Name("SlimeWorldManager - New SlimePropertyMap Instance")
@Description("Create a new SlimePropertyMap.")
@Examples({"set {_spm} to new slime world property map"})
@Since("1.0.0")

public class ExprNewSlimePropertyMap extends SimpleExpression<SlimePropertyMap> {

    static {
        Skript.registerExpression(ExprNewSlimePropertyMap.class, SlimePropertyMap.class, ExpressionType.SIMPLE,
                "new slime world property map");
    }

    @Override
    public boolean init(Expression<?>[] expressions, int pattern, Kleenean kleenean, SkriptParser.ParseResult parse) {
        return true;
    }

    @Override
    protected SlimePropertyMap[] get(Event event) {
        SlimePropertyMap map = new SlimePropertyMap();
        for (SWMProperty prop : SWMProperty.values()){
            switch (prop.getValueType()){
                case "String":
                    map.setString(prop.getProperty(), (String)prop.getProperty().getDefaultValue());
                    break;
                case "Boolean":
                    map.setBoolean(prop.getProperty(), (Boolean) prop.getProperty().getDefaultValue());
                    break;
                case "Integer":
                    map.setInt(prop.getProperty(), (Integer) prop.getProperty().getDefaultValue());
                    break;
            }
        }
        return new SlimePropertyMap[]{map};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends SlimePropertyMap> getReturnType() {
        return SlimePropertyMap.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return "slime world property map";
    }
}
