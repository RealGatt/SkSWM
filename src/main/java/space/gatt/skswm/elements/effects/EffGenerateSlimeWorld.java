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
import com.grinderwolf.swm.api.world.SlimeWorld;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import space.gatt.skswm.SWMAddon;

@Name("SlimeWorldManager - Load Slime World")
@Description("Load Slime World. CANNOT BE RAN ASYNC")
@Examples({"load slime world {_slimeWorld}"})
@Since("1.0.0")
public class EffGenerateSlimeWorld extends Effect {
    static {
        Skript.registerEffect(EffGenerateSlimeWorld.class, "(generate|load) slime world %slimeworld%");
    }

    private Expression<SlimeWorld> worldInstance;

    @Override
    protected void execute(Event event) {
        SlimeWorld world = worldInstance.getSingle(event);
        Bukkit.getScheduler().runTask(SWMAddon.getInstance(), ()-> SWMAddon.getInstance().getSlimeInstance().generateWorld(world));
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        this.worldInstance = (Expression<SlimeWorld>)expressions[0];
        return true;
    }

    @Override
    public String toString(Event event, boolean b) {
        return "generate world";
    }
}
