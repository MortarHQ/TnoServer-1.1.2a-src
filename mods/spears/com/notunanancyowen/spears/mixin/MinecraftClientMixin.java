package com.notunanancyowen.spears.mixin;

import com.notunanancyowen.spears.Spears;
import com.notunanancyowen.spears.SpearsClient;
import com.notunanancyowen.spears.components.PiercingWeapon;
import com.notunanancyowen.spears.packets.PlayerStabC2SPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public abstract class MinecraftClientMixin {
   @Shadow
   @Nullable
   public LocalPlayer player;
   @Unique
   ItemStack spears$previousSlot = ItemStack.EMPTY;

   @Shadow
   @Nullable
   public abstract ClientPacketListener getConnection();

   @Inject(
      method = "startAttack()Z",
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/player/LocalPlayer;getItemInHand(Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/item/ItemStack;",
         shift = Shift.AFTER
      ),
      cancellable = true
   )
   private void spearAttack(CallbackInfoReturnable<Boolean> cir) {
      if (this.player != null) {
         if (this.player.getMainHandItem().get(Spears.MINIMUM_ATTACK_CHARGE) instanceof Float f && this.player.getAttackStrengthScale(0.0F) < f) {
            cir.setReturnValue(false);
         } else if ((Spears.config.getOrDefault("attribute_swap_bypasses_minimum_attack_charge", true)
                  && this.spears$previousSlot != null
                  && !this.spears$previousSlot.isEmpty()
               ? this.spears$previousSlot
               : this.player.getMainHandItem())
            .get(Spears.PIERCING_WEAPON) instanceof PiercingWeapon p) {
            p.playSound(this.player);
            if (this.getConnection() != null) {
               SpearsClient.sendPacketUniversal.apply(new PlayerStabC2SPacket(this.player.getId()));
            }

            this.player.swing(InteractionHand.MAIN_HAND);
            this.player.resetAttackStrengthTicker();
            cir.setReturnValue(true);
         }
      }

      this.spears$previousSlot = ItemStack.EMPTY;
   }

   @Inject(method = "handleKeybinds()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isSpectator()Z", ordinal = 0))
   private void readLastItem(CallbackInfo ci) {
      if (this.player != null) {
         this.spears$previousSlot = this.player.getMainHandItem();
      }
   }
}
