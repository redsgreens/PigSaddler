package redsgreens.PigSaddler;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.entity.Pig;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityListener;

public class PigListener extends EntityListener {
	PigSaddler Plugin;
	Random rand = new Random();

	// list of pigs that need saddles
	static ArrayList<Pig> Pigz = new ArrayList<Pig>();
	
	public PigListener(PigSaddler plugin) {
		Plugin = plugin;
	}
	
	public void onCreatureSpawn(CreatureSpawnEvent event) {
		if(event.isCancelled()) return;
		
		if(event.getEntity() instanceof Pig) 
		{
			Pig pig = (Pig) event.getEntity();
			Pigz.add(pig);
			Plugin.getServer().getScheduler().scheduleSyncDelayedTask(Plugin, new Runnable() {
			    public void run() {
			    	Integer worldPercent;
			    	while(Pigz.size() > 0){
			    		Pig pig = Pigz.get(0);
			    		worldPercent = PigSaddler.getWorldSaddlePercent(pig.getLocation().getWorld().getName());
			    		if(worldPercent != 0)
				    		if(rand.nextInt((Integer)(100 / worldPercent)) == 0)
				    			pig.setSaddle(true);

			    		Pigz.remove(0);
			    	}
			    }
			}, 0);
		}
	}
}
