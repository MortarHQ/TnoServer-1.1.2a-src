package org.anti_ad.mc.ipnext.mixin;

import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Inventory;
import org.anti_ad.mc.ipnext.config.LockedSlotsSettings;
import org.anti_ad.mc.ipnext.event.LockSlotsHandler;
import org.anti_ad.mc.ipnext.event.LockedSlotKeeper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MixinMinecraftClient {
   @Shadow
   public LocalPlayer player;
   @Shadow
   @Final
   public Options options;
   @Shadow
   @Nullable
   public Screen screen;

   @Inject(at = @At("HEAD"), method = "handleKeybinds()V")
   public void handleInputEvents(CallbackInfo var1) {
      IMixinKeyBinding var2 = (IMixinKeyBinding)this.options.keyDrop;
      if (LockedSlotsSettings.INSTANCE.getLOCKED_SLOTS_DISABLE_THROW_FOR_NON_STACKABLE().getValue()
         && Inventory.isHotbarSlot(this.player.getInventory().selected)
         && var2.getTimesPressed() > 0
         && !LockSlotsHandler.INSTANCE.isHotbarQMoveActionAllowed(this.player.getInventory().selected + 36, true)) {
         var2.setPressed(false);
         var2.setTimesPressed(0);
      }

      if (LockedSlotsSettings.INSTANCE.getLOCKED_SLOTS_EMPTY_HOTBAR_AS_SEMI_LOCKED().getValue()) {
         IMixinKeyBinding var3 = (IMixinKeyBinding)this.options.keySwapOffhand;
         if (this.screen == null && Inventory.isHotbarSlot(this.player.getInventory().selected) && var3.getTimesPressed() > 0) {
            LockedSlotKeeper.INSTANCE.setPickingItem(true);
            LockedSlotKeeper.INSTANCE.ignoreSelectedHotbarSlotForHandSwap();
            LockedSlotKeeper.INSTANCE.setPickingItem(false);
         }
      }
   }

   @Inject(at = @At("HEAD"), method = "pickBlock()V")
   private void doItemPickPre(CallbackInfo var1) {
      LockedSlotKeeper.INSTANCE.setPickingItem(true);
   }

   @Inject(at = @At("TAIL"), method = "pickBlock()V")
   private void doItemPickPost(CallbackInfo var1) {
      LockedSlotKeeper.INSTANCE.setPickingItem(false);
   }
}
