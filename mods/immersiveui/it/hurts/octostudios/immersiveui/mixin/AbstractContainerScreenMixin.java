package it.hurts.octostudios.immersiveui.mixin;

import it.hurts.octostudios.immersiveui.ImmersiveUI;
import it.hurts.octostudios.immersiveui.client.MouseInfo;
import it.hurts.octostudios.immersiveui.client.RenderInfo;
import it.hurts.octostudios.immersiveui.compat.ExtraScreenData;
import it.hurts.octostudios.immersiveui.util.CommonCode;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(AbstractContainerScreen.class)
public abstract class AbstractContainerScreenMixin implements ExtraScreenData {
   @Unique
   Random random = new Random();
   @Unique
   Map<Slot, Float> expandingProgress = new HashMap<>();
   @Shadow
   @Nullable
   protected Slot hoveredSlot;
   @Shadow
   private ItemStack draggingItem;
   @Shadow
   protected int leftPos;
   @Shadow
   protected int topPos;
   @Unique
   private float immersiveui$ticker;
   @Unique
   private MouseInfo mouseInfo = new MouseInfo();
   @Unique
   private RenderInfo renderInfo = new RenderInfo();
   @Unique
   AtomicReference<Float> timerCommon = new AtomicReference<>(0.0F);

   @Shadow
   protected abstract boolean isHovering(Slot var1, double var2, double var4);

   @Override
   public MouseInfo getMouseInfo() {
      if (this.mouseInfo == null) {
         this.mouseInfo = new MouseInfo();
      }

      return this.mouseInfo;
   }

   @Override
   public RenderInfo getRenderInfo() {
      if (this.renderInfo == null) {
         this.renderInfo = new RenderInfo();
      }

      return this.renderInfo;
   }

   @Override
   public Random getRandom() {
      if (this.random == null) {
         this.random = new Random();
      }

      return this.random;
   }

   @Override
   public Map<Slot, Float> getExpandingProgress() {
      if (this.expandingProgress == null) {
         this.expandingProgress = new HashMap<>();
      }

      return this.expandingProgress;
   }

   @Inject(
      method = "renderBackground(Lnet/minecraft/client/gui/GuiGraphics;IIF)V",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/gui/screens/inventory/AbstractContainerScreen;renderBg(Lnet/minecraft/client/gui/GuiGraphics;FII)V",
         shift = Shift.BEFORE
      )
   )
   public void renderBg(GuiGraphics guiGraphics, int i, int j, float f, CallbackInfo ci) {
      if (!ImmersiveUI.SOPHISTICATED_COMPAT.isStorageScreenBase((Screen)this)) {
         CommonCode.shakeScreen(guiGraphics, (Screen)this, this.timerCommon, 1.0F);
      }
   }

   @Inject(
      method = "renderFloatingItem(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/world/item/ItemStack;IILjava/lang/String;)V",
      at = @At("HEAD"),
      cancellable = true
   )
   public void renderFunkyItem(GuiGraphics guiGraphics, ItemStack itemStack, int i, int j, String string, CallbackInfo ci) {
      CommonCode.renderFloating((Screen)this, guiGraphics, this.getMouseInfo(), i, j, itemStack, this.getRandom(), this.getRenderInfo(), string, ci);
      this.getMouseInfo().oX = i;
      this.getMouseInfo().oY = j;
   }

   @Inject(
      method = "renderSlot(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/world/inventory/Slot;)V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;renderItem(Lnet/minecraft/world/item/ItemStack;III)V", shift = Shift.BEFORE),
      require = 0
   )
   public void renderSize(GuiGraphics guiGraphics, Slot slot, CallbackInfo ci) {
      CommonCode.floatingRenderSize(guiGraphics, slot, this.hoveredSlot, this.getExpandingProgress());
   }

   @Inject(method = "renderSlotHighlight(Lnet/minecraft/client/gui/GuiGraphics;III)V", at = @At("HEAD"), cancellable = true)
   private static void disableSlotHighlight(GuiGraphics guiGraphics, int x, int y, int blitOffset, @NotNull CallbackInfo ci) {
      if (!ImmersiveUI.CONFIG.isEnableVanillaSlotHighlighting()) {
         ci.cancel();
      }

      guiGraphics.fillGradient(RenderType.gui(), x, y, x + 16, y + 16, -2130706433, -2130706433, blitOffset);
      ci.cancel();
   }

   @Inject(
      method = "render(Lnet/minecraft/client/gui/GuiGraphics;IIF)V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/Slot;isActive()Z", shift = Shift.BEFORE),
      locals = LocalCapture.CAPTURE_FAILSOFT
   )
   public void fixHovering(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci, int i, int j, int k, Slot slot) {
      if (this.isHovering(slot, mouseX, mouseY) && slot.isActive()) {
         this.hoveredSlot = slot;
      }
   }

   @Inject(method = "renderBackground(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", at = @At("HEAD"))
   public void resetOldMouse(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
      float deltaTime = Minecraft.getInstance().getTimer().getRealtimeDeltaTicks();
      this.getMouseInfo().deltaX = (this.mouseInfo.oX - mouseX) / deltaTime / 20.0F;
      this.getMouseInfo().deltaY = (this.mouseInfo.oY - mouseY) / deltaTime / 20.0F;
   }

   @Inject(method = "render(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", at = @At("RETURN"))
   public void resetOldMouse2(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
      this.getMouseInfo().oX = mouseX;
      this.getMouseInfo().oY = mouseY;
   }
}
