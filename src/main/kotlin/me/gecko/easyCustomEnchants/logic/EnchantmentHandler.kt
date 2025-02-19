package me.gecko.easyCustomEnchants.logic

import me.gecko.easyCustomEnchants.data.ConfigurationManager
import me.gecko.easyCustomEnchants.enchantments.EnchantmentClass
import org.bukkit.Registry
import org.bukkit.enchantments.Enchantment
import java.util.logging.Logger

internal class EnchantmentHandler(setAndGet: SetAndGet) {
    private val enchantments = ArrayList<EnchantmentClass>()
    private val logger: Logger = Logger.getLogger(ConfigurationManager::class.java.name)

    fun addEnchantment(enchantment: EnchantmentClass) {
        enchantments.add(enchantment)
    }

    fun getAllEnchantments(): List<EnchantmentClass> {
        return enchantments
    }

    fun test() {
        Registry.ENCHANTMENT.get()
    }
}