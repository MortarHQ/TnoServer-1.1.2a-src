package com.chronodawn.mixin.client;

import com.chronodawn.client.shield.ClientShieldCooldowns;
import com.chronodawn.items.shield.ChronoShieldMarker;
import com.chronodawn.items.shield.ChronoShieldTier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiGraphics.class)
public class GuiGraphicsShieldCdMixin {
   @Inject(method = "renderItemDecorations(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;II)V", at = @At("TAIL"))
   private void chronodawn$renderShieldCooldownOverlay(Font font, ItemStack stack, int x, int y, CallbackInfo ci) {
      if (stack.getItem() instanceof ChronoShieldMarker marker) {
         Minecraft mc = Minecraft.getInstance();
         ClientLevel level = mc.level;
         if (level != null) {
            long now = level.getGameTime();
            float partial = 0.0F;
            ChronoShieldTier tier = marker.getChronoShieldTier();
            float progress = 0.0F;
            if (tier.hasTimeEcho) {
               progress = ClientShieldCooldowns.getEchoProgress(now, partial);
            }

            if (progress <= 0.0F && tier.hasSpeedOnBlock) {
               progress = ClientShieldCooldowns.getSpeedProgress(now, partial);
            }

            if (!(progress <= 0.0F)) {
               GuiGraphics self = (GuiGraphics)this;
               int top = y + Mth.floor(16.0F * (1.0F - progress));
               int bot = top + Mth.ceil(16.0F * progress);
               self.fill(x, top, x + 16, bot, 200, Integer.MAX_VALUE);
            }
         }
      }
   }
}
