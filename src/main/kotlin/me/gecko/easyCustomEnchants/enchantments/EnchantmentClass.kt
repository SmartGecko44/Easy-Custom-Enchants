package me.gecko.easyCustomEnchants.enchantments

import org.bukkit.enchantments.Enchantment
import org.bukkit.enchantments.EnchantmentTarget
import org.bukkit.event.Event
import org.bukkit.event.Listener
import org.bukkit.inventory.ItemStack

internal class EnchantmentClass(
    private val name: String,
    private val maxLevel: Int,
    private val startLevel: Int,
    private val itemTarget: EnchantmentTarget,
    private val isTreasure: Boolean,
    private val isCursed: Boolean,
    private val conflictsWith: List<Enchantment>,
    private val canEnchantItem: List<ItemStack>,
    private val requiredListeners: List<String>
    ) {

    fun getName(): String {
        return name
    }

    fun getStartLevel(): Int {
        return startLevel
    }

    fun getMaxLevel(): Int {
        return maxLevel
    }

    fun getItemTarget(): EnchantmentTarget {
        return itemTarget
    }

    fun isTreasure(): Boolean {
        return isTreasure
    }

    fun isCursed(): Boolean {
        return isCursed
    }

    fun conflictsWith(enchantment: Enchantment): Boolean {
        return conflictsWith.contains(enchantment)
    }

    fun canEnchantItem(itemStack: ItemStack): Boolean {
        return canEnchantItem.contains(itemStack)
    }

    fun getConflictsWith(): List<Enchantment> {
        return conflictsWith
    }

    fun getCanEnchantItem(): List<ItemStack> {
        return canEnchantItem
    }

    fun getRequiredListeners(): List<String> {
        return requiredListeners
    }
}