package redsgreens.PigSaddler;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;

/**
 * @author redsgreens
 **/
public class PigSaddler extends JavaPlugin {
    private final HashMap<Player, Boolean> debugees = new HashMap<Player, Boolean>();
    public PigListener pigListener;
	public PluginManager pluginManager;

	public static ArrayList<Pig> Pigz = new ArrayList<Pig>();

    public void onEnable() {
    	pluginManager = getServer().getPluginManager();
    	pigListener = new PigListener(this);
        pluginManager.registerEvent(Event.Type.CREATURE_SPAWN, pigListener, Priority.Normal, this);

        PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
    }
    public void onDisable() {}
    
    public boolean isDebugging(final Player player) {
        if (debugees.containsKey(player)) {
            return debugees.get(player);
        } else {
            return false;
        }
    }

    public void setDebugging(final Player player, final boolean value) {
        debugees.put(player, value);
    }
}

