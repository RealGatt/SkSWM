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
import com.grinderwolf.swm.api.exceptions.CorruptedWorldException;
import com.grinderwolf.swm.api.exceptions.NewerFormatException;
import com.grinderwolf.swm.api.exceptions.UnknownWorldException;
import com.grinderwolf.swm.api.exceptions.WorldInUseException;
import com.grinderwolf.swm.api.loaders.SlimeLoader;
import com.grinderwolf.swm.api.world.SlimeWorld;
import com.grinderwolf.swm.api.world.properties.SlimePropertyMap;
import org.bukkit.event.Event;
import space.gatt.skswm.SWMAddon;
import space.gatt.skswm.enums.SWMStorageType;

import java.io.IOException;

@Name("SlimeWorldManager - Load World Instance")
@Description("Load a SlimeWorld Instance.")
@Examples({"set {gameworld::skymap} to slime world named \"skymap\" from file", "set {gameworld::skymap} to read-only slime world named \"skymap\" from file"})
@Since("1.0.0")

public class ExprSlimeWorldInstance extends SimpleExpression<SlimeWorld> {

    static {
        Skript.registerExpression(ExprSlimeWorldInstance.class, SlimeWorld.class, ExpressionType.SIMPLE,
                "(slime world|slimeworld|slime-world) named %string% from %swmstoragetype% using properties %swmproperties%",
                "(read-only|readonly|read only) (slime world|slimeworld|slime-world) named %string% from %swmstoragetype% using properties %swmproperties%");
    }

    private boolean readOnly = false;
    private Expression<String> worldName;
    private Expression<SWMStorageType> storageType;
    private Expression<SlimePropertyMap> properties;

    @Override
    public boolean init(Expression<?>[] expressions, int pattern, Kleenean kleenean, SkriptParser.ParseResult parse) {

        this.worldName = (Expression<String>)expressions[0];
        this.storageType = (Expression<SWMStorageType>)expressions[1];
        this.properties = (Expression<SlimePropertyMap>)expressions[2];

        this.readOnly = pattern == 1;
        return true;
    }

    @Override
    protected SlimeWorld[] get(Event event) {

        SlimeWorld sl;

        String loaderType = this.storageType.getSingle(event).name().toLowerCase();
        SlimeLoader loader = SWMAddon.getInstance().getSlimeInstance().getLoader(loaderType);

        String worldName = this.worldName.getSingle(event);
        SlimePropertyMap props = this.properties.getSingle(event);

        try {
            sl = SWMAddon.getInstance().getSlimeInstance().loadWorld(loader, worldName, readOnly, props);
        } catch (Exception e) {
            SWMAddon.log(event.getEventName() + " attempted to load a SlimeWorld that threw an error. Probably an invalid name?");
            e.printStackTrace();
            return null;
        }

        return new SlimeWorld[]{sl};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends SlimeWorld> getReturnType() {
        return SlimeWorld.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return "slime world";
    }
}
