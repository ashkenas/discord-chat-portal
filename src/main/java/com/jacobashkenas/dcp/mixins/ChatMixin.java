package com.jacobashkenas.dcp.mixins;

import com.jacobashkenas.dcp.DiscordChatPortal;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public class ChatMixin {
    @Inject(method="logChatMessage", at=@At("HEAD"))
    private void logChatMessage(Text message, MessageType.Parameters params, @Nullable String prefix, CallbackInfo ci) {
        if (params.name() == null) return;

        DiscordChatPortal.INSTANCE.postMessage(
                message.getString(),
                params.name().getString()
        );
    }
}
