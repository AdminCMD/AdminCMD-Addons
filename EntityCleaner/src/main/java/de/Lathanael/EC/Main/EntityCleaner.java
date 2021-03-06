/*************************************************************************
 * Copyright (C) 2012 Philippe Leipold
 *
 * EntityCleaner is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * EntityCleaner is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with EntityCleaner. If not, see <http://www.gnu.org/licenses/>.
 *
 **************************************************************************/

package de.Lathanael.EC.Main;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;

import be.Balor.Manager.CommandManager;
import be.Balor.Manager.LocaleManager;
import be.Balor.Manager.Permissions.PermParent;
import be.Balor.Tools.Configuration.File.ExtendedConfiguration;
import be.Balor.bukkit.AdminCmd.ACPluginManager;
import be.Balor.bukkit.AdminCmd.AbstractAdminCmdPlugin;

import de.Lathanael.EC.Commands.Purge;
import de.Lathanael.EC.Utils.ECConfig;
import de.Lathanael.EC.Utils.Scheduler;

/**
 * @author Lathanael (aka Philippe Leipold)
 *
 * ECConfig code taken from AdminCmd.
 */
public class EntityCleaner extends AbstractAdminCmdPlugin{

	public static boolean debug;
	public static Scheduler scheduler;
	public static ExtendedConfiguration conf;
	public static File configFolder;

	@Override
	public void onDisable() {
		getLogger().info("Plugin Disabled.");
	}

	@Override
	public void onEnable() {
		super.onEnable();
		final PluginDescriptionFile pdfFile = this.getDescription();
		getLogger().info("Plugin Enabled. (version " + pdfFile.getVersion() + ")");
		ECConfig.setPluginInfos(pdfFile);
		configFolder = getDataFolder();
		conf = ExtendedConfiguration.loadConfiguration(new File(
		getDataFolder(), "config.yml"));
		conf.addDefaults(ECConfig.getDefaultvalues());
		conf.options().header(ECConfig.getHeader());
		conf.options().copyDefaults(true);
		try {
			conf.save();
		} catch (IOException e1) {
			getLogger().log(Level.SEVERE, "Configuration saving problem", e1);
		}
		ECConfig.setConfig(conf);
		debug = ECConfig.getBoolean("debugMsg");
		permissionLinker.registerAllPermParent();
	}

	@Override
	public void registerCmds() {
		CommandManager.getInstance().registerCommand(Purge.class);
	}

	@Override
	protected void registerPermParents() {
		PermParent major = new PermParent("entityCleaner.*");
		permissionLinker.setMajorPerm(major);
	}

	@Override
	protected void setDefaultLocale() {
		ChatColor col1 = ChatColor.GOLD;
		ChatColor col2 = ChatColor.AQUA;
		ChatColor col3 = ChatColor.DARK_GREEN;
		LocaleManager.getInstance().addLocale("TaskChange", col2 + "Task " + col1 + "%name " + col2 + "has been modified with the following variable(s)://n"
				+ col3 + "%list");
		LocaleManager.getInstance().addLocale("TaskStop", col2 + "Task " + col1 + "%name " + col2 + "has been stopped!");
		LocaleManager.getInstance().addLocale("TaskStart", col2 + "Task " + col1 + "%name " + col2 + "has been started!");
		LocaleManager.getInstance().addLocale("TaskRestart", col2 + "Task " + col1 + "%name " + col2 + "has been restarted!");
	}

	public static void reloadConf() {
		try {
			conf.save();
		} catch (IOException e1) {
			ACPluginManager.getPluginInstance("EntityCleaner").getLogger().log(Level.SEVERE, "Configuration saving problem", e1);
		}
		conf = ExtendedConfiguration.loadConfiguration(new File(
				configFolder, "config.yml"));
		ECConfig.setConfig(conf);
	}

}
