package me.gecko.easyCustomEnchants

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class EasyCustomEnchants : JavaPlugin() {

    override fun onEnable() {
        // Plugin startup logic
        Bukkit.getConsoleSender().sendMessage("Initializing EasyCustomEnchants")

    }

    override fun onDisable() {
        // Plugin shutdown logic
    }


}
