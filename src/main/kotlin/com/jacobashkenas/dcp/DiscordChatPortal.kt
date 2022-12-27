package com.jacobashkenas.dcp
import net.fabricmc.api.ModInitializer
import net.fabricmc.loader.api.FabricLoader

object DiscordChatPortal: ModInitializer {
    private const val MOD_ID = "dcp"
    private fun log(str: String) { println("[DiscordChatPortal] $str") }

    override fun onInitialize() {
        log("Initialized.")
    }
}