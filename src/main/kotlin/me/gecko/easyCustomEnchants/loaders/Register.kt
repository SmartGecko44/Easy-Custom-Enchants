package me.gecko.easyCustomEnchants.loaders

import me.gecko.easyCustomEnchants.enchantments.EnchantmentClass
import me.gecko.easyCustomEnchants.logic.EnchantmentHandler
import me.gecko.easyCustomEnchants.logic.SetAndGet
import java.io.File
import java.io.IOError
import java.nio.file.NoSuchFileException
import javax.script.ScriptEngineManager
import kotlin.math.log

internal class Register(private val setAndGet: SetAndGet) {
    val engine = ScriptEngineManager().getEngineByExtension("kts")
    val pluginsDir = File("plugins/EasyCustomEnchants/plugins")
    val logger = setAndGet.getLogger()
    val EnchantmentHandler = setAndGet.getEnchantmentHandler()

    fun loadEnchantmentsFromFolder() {
        if (engine == null) {
            logger.severe("Kotlin scripting engine not found!")
            return
        }

        if (!pluginsDir.exists() || !pluginsDir.isDirectory) {
            logger.info("No plugin folder found! Creating one...")
            if (pluginsDir.mkdir()) {
                logger.info("Successfully created plugin folder!")
            } else {
                logger.severe("Unable to create plugin folder!")
                throw IOError(NoSuchFileException("Unable to create plugin folder!"))
            }
        }

        val pluginFiles = pluginsDir.listFiles { file -> file.extension == "kts" }

        if (pluginFiles.isNullOrEmpty()) {
            logger.info("No plugins found in plugin folder!")
            return
        }

        pluginFiles.forEach { scriptFile ->
            logger.info("Loading plugin: ${scriptFile.name}")
            try {
                engine.eval(scriptFile.readText()) // Load the script

                val enchantment = engine.eval("getEnchantment()") as? EnchantmentClass

                if (enchantment != null) {
                    EnchantmentHandler.addEnchantment(enchantment)
                    logger.info("Successfully loaded ${enchantment.getName()}!")
                } else {
                    logger.severe("Failed to load enchantment from ${scriptFile.name}")
                }
            } catch (e: Exception) {
                logger.severe("Failed to load plugin ${scriptFile.name}: ${e.message}")
            }
        }
    }
}