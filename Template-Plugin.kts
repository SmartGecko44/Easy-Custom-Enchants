import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.enchantments.EnchantmentTarget
import org.bukkit.inventory.ItemStack

internal class TemplatePlugin {
    private val templateEnchantment = EnchantmentClass(
        "Template",                                         // The name of the enchantment
        1,                                                  // The starting level of the enchantment
        5,                                                  // The maximum level of the enchantment
        EnchantmentTarget.BOW,                              // The target item type of the enchantment
        false,                                              // Whether the enchantment is a treasure enchantment
        false,                                              // Whether the enchantment is a cursed enchantment
        listOf<Enchantment>(Enchantment.PUNCH),             // A list of enchantments that conflict with this enchantment (currently only supports vanilla enchantments)
        listOf<ItemStack>(Material.BOW, Material.CROSSBOW), // A list of items that can be enchanted with this enchantment
        "OnProjectileLaunch"                                // A list of events that are required for this enchantment to work
    )


}