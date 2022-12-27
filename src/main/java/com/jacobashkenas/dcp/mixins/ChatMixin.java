package com.jacobashkenas.dcp.mixins;

import com.jacobashkenas.dcp.DiscordChatPortal;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings({"unused"})
@Mixin(ServerPlayNetworkHandler.class)
public class ChatMixin {
    @Inject(method = "sendChatMessage", at = @At("RETURN"))
    private void sendChatMessage(SignedMessage message, MessageType.Parameters params, CallbackInfo ci) {
        if (params.targetName() != null) return;

        DiscordChatPortal.INSTANCE.postMessage(
                message.getContent().getString(),
                params.name().getString(),
                message.getSender().toString()
        );
    }
}
