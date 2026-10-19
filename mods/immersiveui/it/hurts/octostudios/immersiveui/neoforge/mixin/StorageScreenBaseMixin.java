package it.hurts.octostudios.immersiveui.neoforge.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import it.hurts.octostudios.immersiveui.client.MouseInfo;
import it.hurts.octostudios.immersiveui.compat.ExtraScreenData;
import it.hurts.octostudios.immersiveui.util.CommonCode;
import it.hurts.octostudios.octolib.client.particle.ParticleSystem;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.inventory.Slot;
import net.p3pp3rf1y.sophisticatedcore.client.gui.StorageScreenBase;
import net.p3pp3rf1y.sophisticatedcore.common.gui.StorageContainerMenuBase;
import net.p3pp3rf1y.sophisticatedcore.common.gui.UpgradeContainerBase;
import net.p3pp3rf1y.sophisticatedcore.upgrades.cooking.CookingLogicContainer;
import net.p3pp3rf1y.sophisticatedcore.upgrades.cooking.CookingUpgradeContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(StorageScreenBase.class)
public abstract class StorageScreenBaseMixin {
   @Shadow
   private boolean initializing;
   @Unique
   Map<Slot, Float> backpackProgress = new HashMap<>();
   @Unique
   Slot myHoveredSlot;
   @Unique
   AtomicReference<Float> timer = new AtomicReference<>(0.0F);
   @Unique
   AtomicBoolean shouldBurstCompat = new AtomicBoolean(false);

   @Shadow
   protected abstract boolean isHovering(Slot var1, double var2, double var4);

   @Shadow
   public abstract int getLeftX();

   @Shadow
   public abstract int getTopY();

   @Inject(require = 0, method = "renderBg(Lnet/minecraft/client/gui/GuiGraphics;FII)V", at = @At("HEAD"))
   public void shake(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY, CallbackInfo ci) {
      CommonCode.shakeScreen(guiGraphics, (Screen)this, this.timer, 2.0F);
   }

   @Inject(require = 0, method = "render(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", at = @At("TAIL"))
   public void resetOldMouse2(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
      MouseInfo mouseInfo = ((ExtraScreenData)this).getMouseInfo();
      mouseInfo.oX = mouseX;
      mouseInfo.oY = mouseY;
      guiGraphics.pose().pushPose();
      guiGraphics.pose().translate(this.getLeftX(), this.getTopY(), 0.0F);
      ParticleSystem.renderScreenParticles((Screen)this, guiGraphics, Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(false));
      guiGraphics.pose().popPose();
   }

   @Inject(require = 0, method = "renderSuper(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", at = @At("HEAD"))
   private void renderHead(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci) {
      this.myHoveredSlot = null;
   }

   @Inject(
      require = 0,
      method = "renderSuper(Lnet/minecraft/client/gui/GuiGraphics;IIF)V",
      at = @At(
         value = "INVOKE",
         target = "Lnet/p3pp3rf1y/sophisticatedcore/client/gui/StorageScreenBase;renderSlot(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/world/inventory/Slot;)V",
         shift = Shift.BEFORE
      )
   )
   private void hoveredSlot(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, CallbackInfo ci, @Local Slot slot) {
      if (this.isHovering(slot, mouseX, mouseY)) {
         this.myHoveredSlot = slot;
      }
   }

   @Inject(
      require = 0,
      method = "renderUpgradeSlots(Lnet/minecraft/client/gui/GuiGraphics;II)V",
      at = @At(
         value = "INVOKE",
         target = "Lnet/p3pp3rf1y/sophisticatedcore/client/gui/StorageScreenBase;renderSlot(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/world/inventory/Slot;)V",
         shift = Shift.BEFORE
      )
   )
   private void hoveredSlot2(GuiGraphics guiGraphics, int mouseX, int mouseY, CallbackInfo ci, @Local Slot slot) {
      if (this.isHovering(slot, mouseX, mouseY) && slot.isActive()) {
         this.myHoveredSlot = slot;
      }
   }

   @Inject(
      require = 0,
      method = "renderStorageInventorySlots(Lnet/minecraft/client/gui/GuiGraphics;IIZ)V",
      at = @At(
         value = "INVOKE",
         target = "Lnet/p3pp3rf1y/sophisticatedcore/client/gui/StorageScreenBase;renderSlot(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/world/inventory/Slot;)V",
         shift = Shift.BEFORE
      )
   )
   private void hoveredSlot2(GuiGraphics guiGraphics, int mouseX, int mouseY, boolean canShowHover, CallbackInfo ci, @Local Slot slot) {
      if (canShowHover && this.isHovering(slot, mouseX, mouseY) && slot.isActive()) {
         this.myHoveredSlot = slot;
      }
   }

   @Inject(
      require = 0,
      method = "renderSlot(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/world/inventory/Slot;)V",
      at = @At(
         value = "INVOKE",
         target = "Lnet/p3pp3rf1y/sophisticatedcore/client/gui/StorageScreenBase;renderStack(Lnet/minecraft/client/gui/GuiGraphics;IILnet/minecraft/world/item/ItemStack;ZLjava/lang/String;)V",
         shift = Shift.BEFORE
      )
   )
   private void renderSize(GuiGraphics guiGraphics, Slot slot, CallbackInfo ci) {
      CommonCode.floatingRenderSize(guiGraphics, slot, this.myHoveredSlot, this.backpackProgress);
      StorageScreenBase screen = (StorageScreenBase)this;
      Optional<UpgradeContainerBase<?, ?>> upgrade = ((StorageContainerMenuBase)screen.getMenu()).getSlotUpgradeContainer(slot);
      if (!upgrade.isEmpty()) {
         if (upgrade.get() instanceof CookingUpgradeContainer<?, ?> cookingUpgradeContainer && slot.getSlotIndex() == 1) {
            CookingLogicContainer<?> logic = cookingUpgradeContainer.getSmeltingLogicContainer();
            float litProgress = (float)(logic.getBurnTimeFinish() - Minecraft.getInstance().level.getGameTime()) / logic.getBurnTimeTotal();
            CommonCode.renderFurnaceParticles(screen, slot, litProgress == 0.0F, logic.isCooking(), this.shouldBurstCompat);
         }
      }
   }
}
