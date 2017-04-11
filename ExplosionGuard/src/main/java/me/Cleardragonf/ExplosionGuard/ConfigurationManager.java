package me.Cleardragonf.ExplosionGuard;
 
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;

import java.io.File;
import java.io.IOException;
 
public class ConfigurationManager {
 
        private static ConfigurationManager instance = new ConfigurationManager();
       
        public static ConfigurationManager getInstance() {
                return instance;
        }
       
        private ConfigurationLoader<CommentedConfigurationNode> configLoader;
        private CommentedConfigurationNode config;
      
        public void setup(File configFile, ConfigurationLoader<CommentedConfigurationNode> configLoader) {
                this.configLoader = configLoader;
               
                if (!configFile.exists()) {
                        try {
                                configFile.createNewFile();
                                loadConfig();
                                config.getNode("Explosions").setComment("Disable or Enable These Explosion Types? True = No Explosions || False = Explosions");
                                config.getNode("Explosions", "TNT").setValue("true");
                                config.getNode("Explosions", "Creeper").setValue("true");
                                config.getNode("Explosions", "Ghast").setValue("true");
                                config.getNode("Undo?").setComment("Would you like any damage to be undone?");
                                config.getNode("Undo?", "Creeper", "Would you like this to Rollback?").setValue("yes");
                                config.getNode("Undo?", "Creeper", "Time to Heal?").setComment("in Seconds").setValue("5");
                                config.getNode("Undo?", "TNT", "Would you like this to Rollback?").setValue("yes");
                                config.getNode("Undo?", "TNT", "Time to Heal?").setComment("in Seconds").setValue("10");
                                config.getNode("Undo?", "Ghast", "Would you like this to Rollback?").setValue("yes");
                                config.getNode("Undo?", "Ghast", "Time to Heal?").setValue("10");
                                config.getNode("Items", "TNT", "Drop").setValue("false");
                                config.getNode("Items", "Ghast", "Drop").setValue("false");
                                config.getNode("Items", "Creeper", "Drop").setValue("false");
                                saveConfig();
                        }
                       
                        catch (Exception e) {
                                e.printStackTrace();
                        }
                }
               
                else {
                        loadConfig();
                }
        }
       
        public CommentedConfigurationNode getConfig() {
                return config;
        }
       
        public void saveConfig() {
                try {
                        configLoader.save(config);
                }
               
                catch (IOException e) {
                        e.printStackTrace();
                }
        }
       
        public void loadConfig() {
                try {
                        config = configLoader.load();
                }
               
                catch (IOException e) {
                        e.printStackTrace();
                }
        }
}