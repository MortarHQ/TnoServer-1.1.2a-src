package com.natamus.collective.fabric.mixin;

import com.mojang.authlib.GameProfile;
import com.natamus.collective.fabric.callbacks.CollectiveChatEvents;
import java.time.Instant;
import net.minecraft.class_2556;
import net.minecraft.class_2561;
import net.minecraft.class_7471;
import net.minecraft.class_7594;
import net.minecraft.class_2556.class_7602;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = class_7594.class, priority = 1001)
public abstract class ChatListenerMixin {
   @Unique
   class_7602 collective$bound;
   @Unique
   GameProfile collective$gameProfile;

   @Inject(method = "showMessageToPlayer", at = @At("HEAD"))
   public void captureParams(
      class_7602 bound,
      class_7471 playerChatMessage,
      class_2561 component,
      GameProfile gameProfile,
      boolean bl,
      Instant instant,
      CallbackInfoReturnable<Boolean> cir
   ) {
      this.collective$bound = bound;
      this.collective$gameProfile = gameProfile;
   }

   @ModifyVariable(method = "showMessageToPlayer", at = @At("HEAD"), argsOnly = true)
   public class_2561 modifyMessage(class_2561 component) {
      try {
         return ((CollectiveChatEvents.On_Client_Chat)CollectiveChatEvents.CLIENT_CHAT_RECEIVED.invoker())
            .onClientChat(
               (class_2556)this.collective$bound.comp_919().comp_349(),
               component,
               this.collective$gameProfile != null ? this.collective$gameProfile.getId() : null
            );
      } finally {
         this.collective$bound = null;
         this.collective$gameProfile = null;
      }
   }
}
