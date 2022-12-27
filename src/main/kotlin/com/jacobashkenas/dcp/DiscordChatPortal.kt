package com.jacobashkenas.dcp
import com.google.gson.Gson
import com.mojang.logging.LogUtils
import net.fabricmc.api.ModInitializer
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse.BodyHandlers
import java.nio.file.Path

object DiscordChatPortal: ModInitializer {
    private const val MOD_ID = "dcp"
    private var url = ""

    val LOGGER = LogUtils.getLogger()

    fun postMessage(message: String, username: String, uuid: String) {
        if (this.url.isEmpty()) return;

        val request = HttpRequest.newBuilder(URI(url))
            .headers("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(Gson().toJson(object {
                val content = message
                val username = username
                val avatar_url = "https://mc-heads.net/head/$uuid/left"
                val allowed_mentions = object {
                    val parse = IntArray(0)
                }
            })))
            .build()

        HttpClient.newBuilder().build().sendAsync(request, BodyHandlers.discarding())
    }

    override fun onInitialize() {
        val props = DCPPropertiesHandler.loadProperties(Path.of("./config/discord-chat-portal.properties"))
        this.url = props.URL;
        if (this.url.isNotEmpty())
            LOGGER.info("Initialized.")
    }
}