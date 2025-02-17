package me.gecko.easyCustomEnchants.logic

import me.gecko.easyCustomEnchants.data.ConfigurationManager
import me.gecko.easyCustomEnchants.enchantments.EnchantmentClass
import java.util.logging.Logger

internal class EnchantmentHandler(setAndGet: SetAndGet) {
    private val enchantments = ArrayList<EnchantmentClass>()
    private val logger: Logger = Logger.getLogger(ConfigurationManager::class.java.name)

    init {
        try {
            enchantments.addAll(setAndGet.getConfigManager().getEnchantment()!!)
        } catch (e: Exception) {
            logger.severe("Could not load enchantments: ${e.message}")
        }
    }

    fun getAllEnchantments(): List<EnchantmentClass> {
        return enchantments
    }
}