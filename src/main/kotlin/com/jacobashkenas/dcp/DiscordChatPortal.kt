package com.jacobashkenas.dcp
import com.mojang.logging.LogUtils
import net.fabricmc.api.ModInitializer

object DiscordChatPortal: ModInitializer {
    private const val MOD_ID = "dcp"

    override fun onInitialize() {
        LogUtils.getLogger().info("Initialized.")
    }
}