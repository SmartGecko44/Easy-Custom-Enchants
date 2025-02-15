package me.gecko.easyCustomEnchants.logic

import me.gecko.easyCustomEnchants.data.ConfigurationManager

internal class SetAndGet {
    private val configManager = ConfigurationManager()

    fun getConfigManager(): ConfigurationManager {
        return configManager
    }
}