package me.gecko.easyCustomEnchants.logic

import me.gecko.easyCustomEnchants.data.ConfigurationManager
import java.util.logging.Logger

internal class SetAndGet {
    private val configManager = ConfigurationManager()
    private val enchantmentHandler = EnchantmentHandler(this)

    fun getConfigManager(): ConfigurationManager {
        return configManager
    }

    fun getLogger() : Logger {
         return Logger.getLogger(ConfigurationManager::class.java.name)
    }

    fun getEnchantmentHandler() : EnchantmentHandler {
        return enchantmentHandler
    }
}