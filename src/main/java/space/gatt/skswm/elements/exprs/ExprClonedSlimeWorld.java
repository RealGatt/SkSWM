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
import com.grinderwolf.swm.api.exceptions.WorldAlreadyExistsException;
import com.grinderwolf.swm.api.loaders.SlimeLoader;
import com.grinderwolf.swm.api.world.SlimeWorld;
import com.grinderwolf.swm.api.world.properties.SlimePropertyMap;
import org.bukkit.event.Event;
import space.gatt.skswm.SWMAddon;
import space.gatt.skswm.enums.SWMStorageType;

import java.io.IOException;

@Name("SlimeWorldManager - Clone World Instance")
@Description("Clone a SlimeWorld Instance.")
@Examples({"set {gameworld::skymapClone} to slime world cloned from {_anotherSlimeWorld} named \"SkyClone\" saved to file", "set {gameworld::skymapClone} to read-only slime world cloned from {_anotherSlimeWorld} named \"SkyClone\"  saved to file"})
@Since("1.1.0")

public class ExprClonedSlimeWorld extends SimpleExpression<SlimeWorld> {

    static {
        Skript.registerExpression(ExprClonedSlimeWorld.class, SlimeWorld.class, ExpressionType.SIMPLE,
                "(slime world|slimeworld|slime-world) cloned from %slimeworld% named %string% saved to %swmstoragetype%[ using properties %slimepropertymap%]",
                "(read-only|readonly|read only) (slime world|slimeworld|slime-world) cloned from %slimeworld% named %string% saved to %swmstoragetype%[ using properties %slimepropertymap%]");
    }

    private boolean readOnly = false;
    private Expression<SlimeWorld> worldToCopy;
    private Expression<String> newWorldName;
    private Expression<SWMStorageType> storageType;
    private Expression<SlimePropertyMap> properties = null;

    @Override
    public boolean init(Expression<?>[] expressions, int pattern, Kleenean kleenean, SkriptParser.ParseResult parse) {

        this.worldToCopy = (Expression<SlimeWorld>)expressions[0];
        this.newWorldName = (Expression<String>)expressions[1];
        this.storageType = (Expression<SWMStorageType>)expressions[2];
        if (expressions.length == 4) this.properties = (Expression<SlimePropertyMap>)expressions[3];

        this.readOnly = pattern == 1;
        return true;
    }

    @Override
    protected SlimeWorld[] get(Event event) {

        SlimeWorld sl;

        String loaderType = this.storageType.getSingle(event).name().toLowerCase();
        SlimeLoader loader = SWMAddon.getInstance().getSlimeInstance().getLoader(loaderType);

        SlimeWorld copyFrom = this.worldToCopy.getSingle(event);
        String worldName = this.newWorldName.getSingle(event);
        SlimePropertyMap props = null;
        if (this.properties != null) props = this.properties.getSingle(event);

        try {
            sl = copyFrom.clone(worldName, loader, readOnly);
        }catch (WorldAlreadyExistsException| IOException e) {
            e.printStackTrace();
            if (e instanceof WorldAlreadyExistsException)
                Skript.error("A World named " + worldName + " in Storage " + loaderType + " already exists.");
            return null;
        }
        if (props != null) sl.getPropertyMap().merge(props);
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
        return "cloned slime world";
    }
}
