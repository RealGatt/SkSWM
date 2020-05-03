package space.gatt.skswm;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import com.grinderwolf.swm.api.SlimePlugin;
import com.grinderwolf.swm.api.world.SlimeWorld;
import com.grinderwolf.swm.api.world.properties.SlimeProperties;
import com.grinderwolf.swm.api.world.properties.SlimePropertyMap;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import space.gatt.skswm.enums.EnumClassInfo;
import space.gatt.skswm.enums.SWMProperty;
import space.gatt.skswm.enums.SWMStorageType;
import space.gatt.skswm.enums.TypeClassInfo;

import java.io.IOException;

public class SWMAddon extends JavaPlugin {

    private SlimePlugin slimeInstance;
    private SkriptAddon addonInstance;
    private static SWMAddon instance;

    public static void log(String s){
        System.out.println("[SkSWM] " + s);
    }

    public SlimePlugin getSlimeInstance() {
        return slimeInstance;
    }

    public SkriptAddon getAddonInstance() {
        return addonInstance;
    }

    public static SWMAddon getInstance() {
        return instance;
    }

    @Override
    public void onEnable(){
        instance = this;
        if (Bukkit.getPluginManager().getPlugin("SlimeWorldManager")  == null){
            log("SlimeWorldManager isn't installed. Read the docs.");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        if (Bukkit.getPluginManager().getPlugin("Skript")  == null){
            log("Skript isn't installed. Whatcha doin' fool?");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        if (!Skript.isAcceptRegistrations()){
            log("Skript went too fast, and stopped accepting registrations");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        addonInstance = Skript.registerAddon(this);
        slimeInstance = (SlimePlugin)Bukkit.getPluginManager().getPlugin("SlimeWorldManager");
        try {

            EnumClassInfo.create(SWMStorageType.class, "swmstoragetype").register();
            EnumClassInfo.create(SWMProperty.class, "swmpropertytype").register();
            TypeClassInfo.create(SlimePropertyMap.class, "swmproperties").register();

            addonInstance.loadClasses("space.gatt.skswm.elements.exprs");
            addonInstance.loadClasses("space.gatt.skswm.elements.effects");
            addonInstance.loadClasses("space.gatt.skswm.elements.events");
            addonInstance.loadClasses("space.gatt.skswm.elements.conditions");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
