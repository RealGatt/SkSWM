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
import com.grinderwolf.swm.api.world.SlimeWorld;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import space.gatt.skswm.SWMAddon;
import space.gatt.skswm.enums.SWMStorageType;

import java.io.IOException;

@Name("SlimeWorldManager - Unlock Slime World")
@Description("Unlock Slime World. CANNOT BE RAN ASYNC")
@Examples({"unlock world \"coolworld\" in mongodb"})
@Since("1.0.0")
public class EffUnlockWorld extends Effect {
    static {
        Skript.registerEffect(EffUnlockWorld.class, "unlock [slime ]world %string% in %swmstoragetype%");
    }

    private Expression<String> worldName;
    private Expression<SWMStorageType> storageType;

    @Override
    protected void execute(Event event) {
        String worldName = this.worldName.getSingle(event);
        SWMStorageType type = this.storageType.getSingle(event);
        try {
            SWMAddon.getInstance().getSlimeInstance().getLoader(type.name().toLowerCase()).unlockWorld(worldName);
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
        return "unlock world";
    }
}
