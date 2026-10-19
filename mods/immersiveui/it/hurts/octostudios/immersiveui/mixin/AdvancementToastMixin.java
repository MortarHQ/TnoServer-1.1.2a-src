package it.hurts.octostudios.immersiveui.mixin;

import com.mojang.math.Axis;
import it.hurts.octostudios.immersiveui.ImmersiveUI;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.toasts.AdvancementToast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.client.gui.components.toasts.Toast.Visibility;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AdvancementToast.class)
public class AdvancementToastMixin {
   @Shadow
   @Final
   private AdvancementHolder advancement;

   @Inject(
      method = "render(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/client/gui/components/toasts/ToastComponent;J)Lnet/minecraft/client/gui/components/toasts/Toast$Visibility;",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/gui/GuiGraphics;renderFakeItem(Lnet/minecraft/world/item/ItemStack;II)V",
         shift = Shift.BEFORE
      )
   )
   public void renderItem(GuiGraphics guiGraphics, ToastComponent toastComponent, long l, CallbackInfoReturnable<Visibility> cir) {
      if (ImmersiveUI.CONFIG.isEnableAdvancementToastItems()) {
         Minecraft mc = Minecraft.getInstance();
         float delta = mc.player.tickCount + mc.getTimer().getGameTimeDeltaPartialTick(true) + this.advancement.id().hashCode() / 1000;
         guiGraphics.pose().pushPose();
         guiGraphics.pose().translate(16.0F, 16.0F, 150.0F);
         guiGraphics.pose().mulPose(Axis.ZP.rotation(Mth.sin(delta * 0.05F) * 0.15F));
         guiGraphics.pose().mulPose(Axis.XP.rotation(Mth.cos(delta * 0.1F) * 0.2F));
         guiGraphics.pose().mulPose(Axis.YP.rotation(Mth.cos(delta * 0.075F) * 0.3F));
         guiGraphics.pose().translate(0.0F, Mth.sin(delta * 0.1F), 0.0F);
         guiGraphics.pose().translate(-16.0F, -16.0F, -150.0F);
      }
   }

   @Inject(
      method = "render(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/client/gui/components/toasts/ToastComponent;J)Lnet/minecraft/client/gui/components/toasts/Toast$Visibility;",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;renderFakeItem(Lnet/minecraft/world/item/ItemStack;II)V", shift = Shift.AFTER)
   )
   public void renderItemEnd(GuiGraphics guiGraphics, ToastComponent toastComponent, long l, CallbackInfoReturnable<Visibility> cir) {
      if (ImmersiveUI.CONFIG.isEnableAdvancementToastItems()) {
         guiGraphics.pose().popPose();
      }
   }
}
