package net.unusual.block_factorys_bosses.mixins.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.unusual.block_factorys_bosses.attachment.entity.RollAttachment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
public class MouseHandlerMixin {
   @Shadow
   private double xpos;
   @Shadow
   private double ypos;

   @Inject(method = "onMove", at = @At("HEAD"), cancellable = true)
   private void mouseMoveStop(long window, double xPos, double yPos, CallbackInfo ci) {
      Minecraft minecraft = Minecraft.getInstance();
      if (minecraft.player != null) {
         if (RollAttachment.fromPlayer(minecraft.player).isRolling()) {
            ci.cancel();
            if (window == Minecraft.getInstance().getWindow().getWindow()) {
               this.xpos = xPos;
               this.ypos = yPos;
            }
         }
      }
   }
}
