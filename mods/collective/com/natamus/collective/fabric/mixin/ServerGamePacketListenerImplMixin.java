package com.natamus.collective.fabric.mixin;

import com.mojang.datafixers.util.Pair;
import com.natamus.collective.fabric.callbacks.CollectiveChatEvents;
import com.natamus.collective_common_fabric.functions.MessageFunctions;
import net.minecraft.class_2561;
import net.minecraft.class_3222;
import net.minecraft.class_3244;
import net.minecraft.class_5250;
import net.minecraft.class_5837;
import net.minecraft.class_7471;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(value = class_3244.class, priority = 1001)
public abstract class ServerGamePacketListenerImplMixin {
   @Shadow
   public class_3222 field_14140;

   @Inject(
      method = "method_45064(Lnet/minecraft/network/chat/PlayerChatMessage;Lnet/minecraft/network/chat/Component;Lnet/minecraft/server/network/FilteredText;)V",
      at = @At("HEAD"),
      locals = LocalCapture.CAPTURE_FAILSOFT,
      cancellable = true
   )
   public void handleChat(class_7471 playerChatMessage, class_2561 component, class_5837 filteredText, CallbackInfo ci) {
      class_2561 message = class_2561.method_43470("<" + this.field_14140.method_5477().getString() + "> " + playerChatMessage.method_46291().getString());
      Pair<Boolean, class_2561> pair = ((CollectiveChatEvents.On_Server_Chat)CollectiveChatEvents.SERVER_CHAT_RECEIVED.invoker())
         .onServerChat(this.field_14140, message, this.field_14140.method_5667());
      if (pair != null) {
         class_2561 newMessage = (class_2561)pair.getSecond();
         MessageFunctions.broadcastMessage(this.field_14140.method_5770(), (class_5250)newMessage);
         ci.cancel();
      }
   }
}
