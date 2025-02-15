package me.gecko.easyCustomEnchants.data

import me.gecko.easyCustomEnchants.EasyCustomEnchants
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.nio.file.Files
import java.nio.file.NoSuchFileException
import java.util.logging.Level
import java.util.logging.Logger

internal class ConfigurationManager() {
    private val ENABLED_CONF = ": 1\n"
    private val DISABLED_CONF = ": 0\n"
    private val COULD_NOT_BE_CREATED = "Config file could not be created"
    private val CREATED = "Config file created!"
    private val logger: Logger = Logger.getLogger(ConfigurationManager::class.java.name)
    private var configFile: File = File("plugins/EasyCustomEnchants/config.yml")
    private var config: FileConfiguration? = null

    init {
        val pluginFolder = File("plugins/EasyCustomEnchants")

        if (!pluginFolder.exists() && !pluginFolder.mkdir()) {
            logger.severe(COULD_NOT_BE_CREATED)
        } else {
            logger.info(CREATED)
        }

        try {
            if (!configFile.exists() && !configFile.createNewFile()) {
                logger.severe(COULD_NOT_BE_CREATED)
            } else {
                logger.info(CREATED)
            }
            config = YamlConfiguration.loadConfiguration(configFile)
        } catch (ex: IOException) {
            JavaPlugin.getPlugin(EasyCustomEnchants::class.java).logger.severe("Could not load config file: ${ex.message}")
        }
    }

    fun getFileWriter(): FileWriter {
        return runCatching {
            FileWriter(configFile).use { it }
        }.getOrElse {
            throw IOException("Unable to create FileWriter", it)
        }
    }

    fun getConfig(): FileConfiguration {
        config = YamlConfiguration.loadConfiguration(configFile)
        return config as YamlConfiguration
    }

    fun saveConfig() {
        runCatching {
            config?.save(configFile)
        }.onFailure {
            logger.severe("Unable to save config: ${it.message}")
        }
    }

    fun resetConfig(player: Player) {
        try {
            if (!configFile.exists() && !configFile.createNewFile()) {
                logger.severe(COULD_NOT_BE_CREATED)
                player.sendMessage("${ChatColor.RED}$COULD_NOT_BE_CREATED")
            } else if (!isFileDeleted()) {
                logger.log(Level.SEVERE, "Config file could not be deleted")
                player.sendMessage("${ChatColor.RED}Config file could not be reset")
            } else {
                Bukkit.getConsoleSender().sendMessage("${ChatColor.GREEN}Config file deleted!")
                if (!configFile.createNewFile()) {
                    logger.severe(COULD_NOT_BE_CREATED)
                    player.sendMessage("${ChatColor.RED}Config file could not be reset")
                } else {
                    logger.info(CREATED)
                    getFileWriter()
                    player.sendMessage("${ChatColor.GREEN}Config reset!")
                }
            }
            config = YamlConfiguration.loadConfiguration(configFile)
        } catch (ex: IOException) {
            logger.log(Level.SEVERE, "Could not reset config file", ex)
        }
    }

    private fun isFileDeleted(): Boolean {
        return runCatching {
            Files.delete(configFile.toPath())
            true
        }.getOrElse { e ->
            when (e) {
                is NoSuchFileException -> true // Consider file deleted
                is IOException -> false // Failed to delete
                else -> false
            }
        }
    }
}