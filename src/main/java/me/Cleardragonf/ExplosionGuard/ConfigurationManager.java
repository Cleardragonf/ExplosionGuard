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
                                config.getNode("Undo?").setComment("Would you like any damage to be undone? YES = Undoes damage || NO = Damage is permenate");
                                config.getNode("Undo?", "Undo_Creeper_Explosiong").setValue("yes");
                                config.getNode("Undo?", "Creeper's_Rollback_Time").setComment("in Seconds").setValue("5");
                                config.getNode("Undo?", "UndoTNT)_Explosiong").setValue("yes");
                                config.getNode("Undo?", "TNT's_Rollback_Time").setComment("in Seconds").setValue("10");
                                config.getNode("Undo?", "Undo_Ghast's_Explosion").setValue("yes");
                                config.getNode("Undo?", "Ghast_Rollback_Time").setValue("10");
                                config.getNode("DropItems_Creeper").setComment("Do you want Creeper Explosions to Drop Items?  YES = true || NO = false").setValue("false");
                                config.getNode("DropItems_TNT").setComment("Do you want TNT Explosions to Drop Items?  YES = true || NO = false").setValue("false");
                                config.getNode("DropItems_Ghast").setComment("Do you want Ghast Explosions to Drop Items?  YES = true || NO = false").setValue("false");
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