package com.nododiiiii.ponderer.mixin;

import com.nododiiiii.ponderer.ui.CoordPickState;
import com.nododiiiii.ponderer.ui.NbtPickState;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftNbtPickMixin {
   @Inject(method = "handleKeybinds", at = @At("HEAD"), require = 0)
   private void ponderer$interceptMiddlePickForNbtCapture(CallbackInfo ci) {
      Minecraft mc = (Minecraft)this;
      if (mc.player != null && mc.screen == null) {
         if (NbtPickState.isActive()) {
            while (mc.options.keyPickItem.consumeClick()) {
               NbtPickState.handleUseClick();
            }

            mc.player.displayClientMessage(Component.translatable("ponderer.ui.nbt_pick.middle_prompt"), true);
         } else {
            if (CoordPickState.isActive()) {
               CoordPickState.onClientTick();

               while (mc.options.keyPickItem.consumeClick()) {
                  CoordPickState.handleMiddleClick();
               }
            }
         }
      }
   }
}
