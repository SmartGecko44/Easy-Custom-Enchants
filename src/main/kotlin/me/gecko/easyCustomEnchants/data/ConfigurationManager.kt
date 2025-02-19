package me.gecko.easyCustomEnchants.data

import me.gecko.easyCustomEnchants.enchantments.EnchantmentClass
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.enchantments.Enchantment
import org.bukkit.enchantments.EnchantmentTarget
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.nio.file.Files
import java.nio.file.NoSuchFileException
import java.util.logging.Level
import java.util.logging.Logger

internal class ConfigurationManager {
    private val ENABLED_CONF = ": 1\n"
    private val DISABLED_CONF = ": 0\n"
    private val COULD_NOT_BE_CREATED = "Config file could not be created"
    private val CREATED = "Config file created!"
    private val logger = Logger.getLogger(ConfigurationManager::class.java.name)
    private var configFile: File = File("plugins/EasyCustomEnchants/config.yml")

    val config: FileConfiguration
        get() = YamlConfiguration.loadConfiguration(configFile)

    init {
        if (!configFile.exists()) {
            try {
                configFile.parentFile.mkdirs()
                configFile.createNewFile()
                logger.info("Config file created!")
            } catch (ex: IOException) {
                logger.severe("Could not create config file: ${ex.message}")
            }
        }
    }

    private fun getFileWriter(): FileWriter {
        return runCatching {
            FileWriter(configFile).use { it }
        }.getOrElse {
            throw IOException("Unable to create FileWriter", it)
        }
    }

    private fun saveConfig() {
        runCatching {
            config.save(configFile)
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

    fun addEnchantment(enchantmentClass: EnchantmentClass) {
        val path = "enchantments.${enchantmentClass.getName()}"
        config["$path.minLevel"] = enchantmentClass.getStartLevel()
        config["$path.maxLevel"] = enchantmentClass.getMaxLevel()
        config["$path.itemTarget"] = enchantmentClass.getItemTarget()
        config["$path.isTreasure"] = enchantmentClass.isTreasure()
        config["$path.isCursed"] = enchantmentClass.isCursed()
        config["$path.conflictsWith"] = enchantmentClass.getConflictsWith().map { it }
        config["$path.canEnchantItem"] = enchantmentClass.getCanEnchantItem().map { it }
        config["$path.requiredListeners"] = enchantmentClass.getRequiredListeners().map { it }
    }

    fun getEnchantment(name: String) : EnchantmentClass? {
        val path = "enchantments.$name"
        try {
            config[path]
        } catch (e: Exception) {
            logger.warning("Could not get enchantment: $name (${e.message})")
            return null
        }
        return EnchantmentClass(
            name,
            config["$path.minLevel"] as Int,
            config["$path.maxLevel"] as Int,
            config["$path.itemTarget"] as EnchantmentTarget,
            config["$path.isTreasure"] as Boolean,
            config["$path.isCursed"] as Boolean,
            config["$path.conflictsWith"] as List<Enchantment>,
            config["$path.canEnchantItem"] as List<ItemStack>,
            config["$path.requiredListeners"] as List<String>
        )
    }

    fun getEnchantment() : List<EnchantmentClass>? {
        val path = "enchantments"
        try {
            config[path]
        } catch (e: Exception) {
            logger.severe("Could not get enchantments (${e.message})")
            return null
        }

        return config.getConfigurationSection(path)?.getKeys(false)?.mapNotNull {
            getEnchantment(it)
        }
    }
}