package me.Cleardragonf.ExplosionGuard;


import java.io.File;
import java.util.logging.Logger;

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

import com.google.inject.Inject;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
 
@Plugin(id = "Explosion Guard", name = "ExplosionGuard", version = "Beta.1.0")

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
            if (bang.getCause().first(Creeper.class).isPresent()) {
                if (ConfigurationManager.getInstance().getConfig().getNode("Explosions", "Creeper").getString().equalsIgnoreCase("false")){
                    if (ConfigurationManager.getInstance().getConfig().getNode("Undo?", "Undo_Creeper_Explosiong").getString().equalsIgnoreCase("yes")){
                    	
                        }
                }
            }
            if (bang.getCause().first(PrimedTNT.class).isPresent()) {
                if (ConfigurationManager.getInstance().getConfig().getNode("Explosions", "TNT").getString().equalsIgnoreCase("false")){
                    if (ConfigurationManager.getInstance().getConfig().getNode("Undo?", "Undo_TNT_Explosion").getString().equalsIgnoreCase("yes")){
                        
                    }
                }
            }            
            if (bang.getCause().first(Ghast.class).isPresent()) {
                if (ConfigurationManager.getInstance().getConfig().getNode("Explosions", "Ghast").getString().equalsIgnoreCase("false")){
                    if (ConfigurationManager.getInstance().getConfig().getNode("Undo?", "Undo_Ghast's_Explosion").getString().equalsIgnoreCase("yes")){
                        
                    }
                }
            } 
        }
        
        /*---This here manages the itemDrops from Explosions---*/
        @Listener
        public void itemDrop(DropItemEvent.Destruct itemDrop){
            if (itemDrop.getCause().first(Creeper.class).isPresent()) {
                if (ConfigurationManager.getInstance().getConfig().getNode("DropItems_Creeper").getString().equalsIgnoreCase("false")){
                    itemDrop.setCancelled(true);
                }
            }
            if (itemDrop.getCause().first(PrimedTNT.class).isPresent()){
                if (ConfigurationManager.getInstance().getConfig().getNode("DropItems_TNT").getString().equalsIgnoreCase("false")){
                    itemDrop.setCancelled(true);
                }
            }
            if (itemDrop.getCause().first(Ghast.class).isPresent()){
                if (ConfigurationManager.getInstance().getConfig().getNode("DropItems_Ghast").getString().equalsIgnoreCase("false")){
                    itemDrop.setCancelled(true);
                }
            }
        }
        
}        