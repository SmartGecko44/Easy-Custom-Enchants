package me.gecko.easyCustomEnchants.enchantments

import org.bukkit.enchantments.EnchantmentTarget
import org.bukkit.inventory.ItemStack

class EnchantmentClass(
    private val name: String,
    private val maxLevel: Int,
    private val startLevel: Int,
    private val itemTarget: EnchantmentTarget,
    private val isTreasure: Boolean,
    private val isCursed: Boolean,
    private val conflictsWith: List<EnchantmentClass>,
    private val canEnchantItem: List<ItemStack>
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

    fun conflictsWith(enchantmentClass: EnchantmentClass): Boolean {
        return conflictsWith.contains(enchantmentClass)
    }

    fun canEnchantItem(itemStack: ItemStack): Boolean {
        return canEnchantItem.contains(itemStack)
    }

    fun getConflictsWith(): List<EnchantmentClass> {
        return conflictsWith
    }

    fun getCanEnchantItem(): List<ItemStack> {
        return canEnchantItem
    }
}