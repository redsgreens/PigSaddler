package redsgreens.PigSaddler;

import org.bukkit.entity.Pig;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityListener;

public class PigListener extends EntityListener {
	PigSaddler Plugin;
	
	public PigListener(PigSaddler plugin) {
		Plugin = plugin;
	}
	
	public void onCreatureSpawn(CreatureSpawnEvent event) {
		if(event.isCancelled()) return;
		
//		if(event.getEntity() instanceof Pig && event.getLocation().getWorld().getName().equalsIgnoreCase("world")) {
		if(event.getEntity() instanceof Pig) {
			Pig pig = (Pig) event.getEntity();
			PigSaddler.Pigz.add(pig);
			Plugin.getServer().getScheduler().scheduleSyncDelayedTask(Plugin, new Runnable() {
			    public void run() {
			    	while(PigSaddler.Pigz.size() > 0){
			    		Pig pig = PigSaddler.Pigz.get(0);
			    		pig.setSaddle(true);
			    		PigSaddler.Pigz.remove(0);
			    	}
			    }
			}, 0);
		}
	}
}
