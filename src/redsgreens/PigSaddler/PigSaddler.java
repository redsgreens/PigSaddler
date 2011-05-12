package redsgreens.PigSaddler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;
import org.yaml.snakeyaml.Yaml;

/**
 * @author redsgreens
 **/
public class PigSaddler extends JavaPlugin {
    private final HashMap<Player, Boolean> debugees = new HashMap<Player, Boolean>();
	private PluginDescriptionFile pdfFile;
	private static HashMap<String, Integer> configMap = new HashMap<String, Integer>();

    public PigListener pigListener;
	public PluginManager pluginManager;


    public void onEnable() {
    	pluginManager = getServer().getPluginManager();

    	loadConfig();
    	
    	pigListener = new PigListener(this);
        pluginManager.registerEvent(Event.Type.CREATURE_SPAWN, pigListener, Priority.Normal, this);

        pdfFile = this.getDescription();
        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
    }
    
    @SuppressWarnings("unchecked")
	private void loadConfig()
    {
    	try
    	{
        	File folder = this.getDataFolder();
        	// create the data folder if it doesn't exist
        	if(!folder.exists()){
        		folder.mkdirs();
        	}

        	File configFile = new File(folder, "config.yml");
    		// create a default config if one doesn't exist
        	if (!configFile.exists()){
    			configFile.createNewFile();
    			InputStream res = PigSaddler.class.getResourceAsStream("/config.yml");
    			FileWriter tx = new FileWriter(configFile);
    			for (int i = 0; (i = res.read()) > 0;) tx.write(i);
    			tx.flush();
    			tx.close();
    			res.close();
    		}

    		BufferedReader rx = new BufferedReader(new FileReader(configFile));
    		Yaml yaml = new Yaml();
    		
    		// clear the config before loading
    		configMap.clear();

    		// load it
    		configMap = (HashMap<String,Integer>)yaml.load(rx);
    		
    		// close the file
    		rx.close();

     	} catch (Exception e) {
    		System.out.println("PigSaddler error: " + e.getMessage());
    	}

    }
    
    public static Integer getWorldSaddlePercent(String world)
    {
    	if(configMap.containsKey(world))
    		return configMap.get(world);
    	else
    		return 0;
    }
    
    public void onDisable() {
        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is disabled." );
    	
    }
    
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

