package com.jacobashkenas.dcp.mixins;

import com.mojang.logging.LogUtils;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public class ChatMixin {
    @Inject(method = "sendChatMessage", at = @At("RETURN"))
    private void sendChatMessage(SignedMessage message, MessageType.Parameters params, CallbackInfo ci) {
        LogUtils.getLogger().info("Got Message: " + message.getContent().getString());
    }
}
