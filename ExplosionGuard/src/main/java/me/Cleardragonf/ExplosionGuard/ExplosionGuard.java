package me.Cleardragonf.ExplosionGuard;


import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.entity.explosive.PrimedTNT;
import org.spongepowered.api.entity.living.monster.Creeper;
import org.spongepowered.api.entity.living.monster.Ghast;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.game.state.GameStartingServerEvent;
import org.spongepowered.api.event.item.inventory.DropItemEvent;
import org.spongepowered.api.event.world.ExplosionEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.google.inject.Inject;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
 
@Plugin(id = "explosionguard", name = "ExplosionGuard", version = "Beta.1.0")

public class ExplosionGuard {

        @Inject
        private Logger logger;
        
        @Inject
        private void setLogger(Logger logger){
            this.logger = logger;
        }
        public Logger getLogger(){
            return logger;
        }
        
        @Listener
        public void enable(GameStartingServerEvent event)
        {
            ConfigurationManager.getInstance().setup(configFile, configManager);
        }
        
        @Inject
        @DefaultConfig(sharedRoot = false)
        private File configFile;
       
        @Inject
        @DefaultConfig(sharedRoot = false)
        ConfigurationLoader<CommentedConfigurationNode> configManager;
        
        @Listener
        public void onExplosionEvent(ExplosionEvent.Pre event) {
            /*--
            getLogger().info(event.getCause().toString());
            ---*/
            if (event.getCause().first(Creeper.class).isPresent()) {
                if (ConfigurationManager.getInstance().getConfig().getNode("Explosions", "Creeper").getString().equalsIgnoreCase("true")){
                    event.setCancelled(true);
                }
                if (ConfigurationManager.getInstance().getConfig().getNode("Explosions", "Creeper").getString().equalsIgnoreCase("false")){
                    event.setCancelled(false);
                }
            }
            if (event.getCause().first(PrimedTNT.class).isPresent()){
                if (ConfigurationManager.getInstance().getConfig().getNode("Explosions", "TNT").getString().equalsIgnoreCase("true")){
                    event.setCancelled(true);
                }
                if (ConfigurationManager.getInstance().getConfig().getNode("Explosions", "TNT").getString().equalsIgnoreCase("false")){
                    event.setCancelled(false);
                    
                }
            }
            if (event.getCause().first(Ghast.class).isPresent()){
                if (ConfigurationManager.getInstance().getConfig().getNode("Explosions", "Ghast").getString().equalsIgnoreCase("true")){
                    event.setCancelled(true);
                }
                if (ConfigurationManager.getInstance().getConfig().getNode("Explosions", "Ghast").getString().equalsIgnoreCase("false")){
                    event.setCancelled(false);
                    
                }
            }            
            //this is here for logging what caused the explosiong so it can be logged
            //*getLogger().info(event.getCause().toString());

        }
        
        /*---This operates the healing time and operations---*/
        
        @Listener(order = Order.LAST)
         
        public void onExplode(final ExplosionEvent.Detonate bang){
            if (bang.getCause().first(PrimedTNT.class).isPresent()) {
                if (ConfigurationManager.getInstance().getConfig().getNode("Explosions", "TNT").getString().equalsIgnoreCase("false")){
                    if (ConfigurationManager.getInstance().getConfig().getNode("Undo?", "TNT", "Would you like this to Rollback?").getString().equalsIgnoreCase("yes")){
                        final List<Location<World>> transactions = bang.getAffectedLocations();
                        final Mend mend = new Mend(transactions);
                        Sponge.getScheduler().createTaskBuilder()
                                .delay(ConfigurationManager.getInstance().getConfig().getNode("Undo?", "TNT", "Time to Heal?").getLong(), TimeUnit.SECONDS)
                                .name("Explosion Repair Task")
                                .execute(mend::heal)
                                .submit(this);
                    }
                }
            }                  
            if (bang.getCause().first(Creeper.class).isPresent()) {
                if (ConfigurationManager.getInstance().getConfig().getNode("Explosions", "Creeper").getString().equalsIgnoreCase("false")){
                    if (ConfigurationManager.getInstance().getConfig().getNode("Undo?", "Creeper", "Would you like this to Rollback?").getString().equalsIgnoreCase("yes")){
                    	bang.getAffectedLocations();
                            final List<Location<World>> transactions = bang.getAffectedLocations();
                            final Mend mend = new Mend(transactions);
							Sponge.getGame().getScheduler().createTaskBuilder()
                                    .delay(15, TimeUnit.SECONDS)
                                    .name("Explosion Repair Task")
                                    .execute(mend::heal)
                                    .submit(this);
                        }
                }
            }   
            if (bang.getCause().first(Ghast.class).isPresent()) {
                if (ConfigurationManager.getInstance().getConfig().getNode("Explosions", "Ghast").getString().equalsIgnoreCase("false")){
                    if (ConfigurationManager.getInstance().getConfig().getNode("Undo?", "Undo_Ghast's_Explosion").getString().equalsIgnoreCase("yes")){
                        bang.getAffectedLocations();
                        final List<Location<World>> transactions = bang.getAffectedLocations();
						final Mend mend = new Mend(transactions);
                        Sponge.getScheduler().createTaskBuilder()
                                .delay(ConfigurationManager.getInstance().getConfig().getNode("Undo?", "Ghast_Rollback_Time").getLong(), TimeUnit.SECONDS)
                                .name("Explosion Repair Task")
                                .execute(mend::heal)
                                .submit(this);
                    }
                }
            } 
        }
        
        /*---This here manages the itemDrops from Explosions---*/
        @Listener
        public void itemDrop(DropItemEvent.Destruct itemDrop){
            if (itemDrop.getCause().first(Creeper.class).isPresent()) {
                if (ConfigurationManager.getInstance().getConfig().getNode("Items", "Creeper", "Drop").getString().equalsIgnoreCase("false")){
                    itemDrop.setCancelled(true);
                }
            }
            if (itemDrop.getCause().first(PrimedTNT.class).isPresent()){
                if (ConfigurationManager.getInstance().getConfig().getNode("Items", "TNT", "Drop").getString().equalsIgnoreCase("false")){
                    itemDrop.setCancelled(true);
                }
            }
            if (itemDrop.getCause().first(Ghast.class).isPresent()){
                if (ConfigurationManager.getInstance().getConfig().getNode("Items", "Ghast", "Drop").getString().equalsIgnoreCase("false")){
                    itemDrop.setCancelled(true);
                }
            }
        }
        
}        