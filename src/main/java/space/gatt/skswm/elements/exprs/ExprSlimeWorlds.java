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
import org.bukkit.event.Event;
import space.gatt.skswm.SWMAddon;
import space.gatt.skswm.enums.SWMStorageType;

import java.io.IOException;


@Name("SlimeWorldManager - All SlimeWorlds")
@Description("List all SlimeWorlds.")
@Examples({"set {gameworld::worlds::*} to all slime worlds in file"})
@Since("1.0.0")


public class ExprSlimeWorlds  extends SimpleExpression<String> {

    static {
        Skript.registerExpression(ExprSlimeWorlds.class, String.class, ExpressionType.SIMPLE,
                "all slime worlds in %swmstoragetype%");
    }

    private Expression<SWMStorageType> storageType;

    @Override
    public boolean init(Expression<?>[] expressions, int pattern, Kleenean kleenean, SkriptParser.ParseResult parse) {
        this.storageType = (Expression<SWMStorageType>)expressions[0];
        return true;
    }

    @Override
    protected String[] get(Event event) {

        String loaderType = this.storageType.getSingle(event).name().toLowerCase();
        SlimeLoader loader = SWMAddon.getInstance().getSlimeInstance().getLoader(loaderType);

        try {
            return loader.listWorlds().toArray(new String[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String[]{};
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return "slime world property map";
    }
}
