package com.jacobashkenas.dcp
import com.google.gson.Gson
import com.mojang.logging.LogUtils
import net.fabricmc.api.ModInitializer
import org.slf4j.Logger
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse.BodyHandlers
import java.nio.file.Path

object DiscordChatPortal: ModInitializer {
    private const val MOD_ID = "dcp"
    private var props = DCPPropertiesHandler()

    val LOGGER: Logger = LogUtils.getLogger()

    fun postMessage(message: String, username: String) {
        if (props.URL.isEmpty()) return;

        val request = HttpRequest.newBuilder(URI(props.URL))
            .headers("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(Gson().toJson(object {
                val content = message
                val username = username
                val avatar_url = "https://mc-heads.net/head/$username/" + (if (props.FACE_LEFT) "left" else "right")
                val allowed_mentions = object {
                    val parse = IntArray(0)
                }
            })))
            .build()

        HttpClient.newBuilder().build().sendAsync(request, BodyHandlers.discarding())
    }

    override fun onInitialize() {
        props = DCPPropertiesHandler.loadProperties(Path.of("./config/discord-chat-portal.properties"))
        if (props.URL.isNotEmpty())
            LOGGER.info("Initialized.")
    }
}