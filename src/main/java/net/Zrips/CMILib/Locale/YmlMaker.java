package net.Zrips.CMILib.Locale;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import net.Zrips.CMILib.CMILib;
import net.Zrips.CMILib.Logs.CMIDebug;

public class YmlMaker {
    CMILib Plugin;
    public String fileName;
    private JavaPlugin plugin;
    public File ConfigFile;
    private FileConfiguration Configuration;

//    public YmlMaker(CMILib Plugin) {
//	this.Plugin = Plugin;
//    }

    public YmlMaker(JavaPlugin plugin, String fileName) {
	if (plugin == null) {
	    throw new IllegalArgumentException("plugin cannot be null");
	}
	this.plugin = plugin;
	this.fileName = fileName;
	File dataFolder = plugin.getDataFolder();
	if (dataFolder == null) {
	    throw new IllegalStateException();
	}

	this.ConfigFile = new File(dataFolder.toString() + File.separatorChar + this.fileName);
    }

    public void reloadConfig() {
	this.Configuration = YamlConfiguration.loadConfiguration(this.ConfigFile);
	InputStream defConfigStream = this.plugin.getResource(this.fileName);
	if (defConfigStream != null) {
	    YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(this.ConfigFile);
	    this.Configuration.setDefaults(defConfig);
	}
	if (defConfigStream != null)
	    try {
		defConfigStream.close();
	    } catch (IOException e) {
		e.printStackTrace();
	    }

    }

    public FileConfiguration getConfig() {
	if (this.Configuration == null) {
	    reloadConfig();
	}
	return this.Configuration;
    }

    public void saveConfig() {
	if ((this.Configuration == null) || (this.ConfigFile == null)) {
	    return;
	}
	try {
	    getConfig().save(this.ConfigFile);
	} catch (IOException ex) {
	    this.plugin.getLogger().log(Level.SEVERE, "Could not save config to " + this.ConfigFile, ex);
	}
    }

    public void saveDefaultConfig() {
	if (!this.ConfigFile.exists()) {
	    this.plugin.saveResource(this.fileName, false);
	}
    }
}
