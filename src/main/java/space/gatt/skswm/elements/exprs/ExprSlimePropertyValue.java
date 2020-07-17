package space.gatt.skswm.elements.exprs;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.expressions.base.SimplePropertyExpression;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import com.grinderwolf.swm.api.world.properties.SlimePropertyMap;
import org.bukkit.event.Event;
import space.gatt.skswm.enums.SWMProperty;

@Name("SlimeWorldManager - Change Property Value")
@Description("Modifies a property value.")
@Examples({"set spawn x of {_props} to 6"})
@Since("1.0.0")

public class ExprSlimePropertyValue extends SimpleExpression<Object> {

    static {
        Skript.registerExpression(ExprSlimePropertyValue.class, Object.class, ExpressionType.SIMPLE, "%swmproperty% of %slimepropertymap%");
    }

    private Expression<SlimePropertyMap> properties;
    private Expression<SWMProperty> propertyType;

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode != Changer.ChangeMode.SET) {
            Skript.error("Cannot " + mode.toString() + " a property type.");
            return null;
        }
        return CollectionUtils.array(this.isSingle() ? Object.class : Object[].class);
    }

    @Override
    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        this.propertyType = (Expression<SWMProperty>)expressions[0];
        this.properties = (Expression<SlimePropertyMap>)expressions[1];
        return true;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        if (mode != Changer.ChangeMode.SET) {
            return;
        }

        SlimePropertyMap props = this.properties.getSingle(e);
        SWMProperty property = this.propertyType.getSingle(e);
        Object value = delta[0];

        switch (property.getValueType()){
            case "String":
                props.setString(property.getProperty(), value.toString());
                break;
            case "Integer":
                if (value instanceof Integer)
                    props.setInt(property.getProperty(), (Integer)value);
                else
                    Skript.error(property.getProperty().getNbtName() + " expected a Integer, but got " + value.toString());
                break;
            case "Boolean":
                if (value instanceof Boolean)
                    props.setBoolean(property.getProperty(), (Boolean)value);
                else
                    Skript.error(property.getProperty().getNbtName() + " expected a Boolean, but got " + value.toString());
                break;
        }
    }

    @Override
    protected Object[] get(Event event) {
        SlimePropertyMap props = this.properties.getSingle(event);
        SWMProperty property = this.propertyType.getSingle(event);
        switch (property.getValueType()){
            case "String":
                return new String[]{props.getString(property.getProperty())};
            case "Integer":
                return new Integer[]{props.getInt(property.getProperty())};
            case "Boolean":
                return new Boolean[]{props.getBoolean(property.getProperty())};
            default:
                return null;
        }
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<?> getReturnType() {
        return Object.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return "slime property";
    }
}
