package space.gatt.skswm.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.grinderwolf.swm.api.exceptions.UnknownWorldException;
import org.bukkit.event.Event;
import space.gatt.skswm.SWMAddon;
import space.gatt.skswm.enums.SWMStorageType;

import java.io.IOException;

@Name("SlimeWorldManager - Delete Slime World")
@Description("Delete Slime World. Cannot be undone")
@Examples({"delete slime world \"coolworld\" in mongodb"})
@Since("1.0.0")
public class EffDeleteSlimeWorld extends Effect {
    static {
        Skript.registerEffect(EffDeleteSlimeWorld.class, "delete [slime ]world %string% from %swmstoragetype%");
    }

    private Expression<String> worldName;
    private Expression<SWMStorageType> storageType;

    @Override
    protected void execute(Event event) {
        String worldName = this.worldName.getSingle(event);
        SWMStorageType type = this.storageType.getSingle(event);
        try {
            SWMAddon.getInstance().getSlimeInstance().getLoader(type.name().toLowerCase()).deleteWorld(worldName);
        } catch (UnknownWorldException|IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        this.worldName = (Expression<String>)expressions[0];
        this.storageType = (Expression<SWMStorageType>)expressions[0];
        return true;
    }

    @Override
    public String toString(Event event, boolean b) {
        return "delete world";
    }
}
