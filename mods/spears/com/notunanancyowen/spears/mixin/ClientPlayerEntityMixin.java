package com.notunanancyowen.spears.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.authlib.GameProfile;
import com.notunanancyowen.spears.Spears;
import com.notunanancyowen.spears.SpearsClient;
import com.notunanancyowen.spears.components.UseEffects;
import com.notunanancyowen.spears.packets.TriggerStabEffectsC2SPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.Input;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public abstract class ClientPlayerEntityMixin extends Player {
   @Shadow
   public Input input;
   @Shadow
   @Final
   protected Minecraft minecraft;

   public ClientPlayerEntityMixin(Level world, BlockPos pos, float yaw, GameProfile gameProfile) {
      super(world, pos, yaw, gameProfile);
   }

   @Inject(method = "aiStep()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isUsingItem()Z", shift = Shift.AFTER))
   private void removeMovementSpeedPenalty(CallbackInfo ci) {
      if (this.getUseItem() != null && this.getUseItem().getComponents().get(Spears.USE_EFFECTS) instanceof UseEffects u) {
         float f = 5.0F * u.strafeSpeed();
         this.input.forwardImpulse *= f;
         this.input.leftImpulse *= f;
      }
   }

   @ModifyExpressionValue(method = "canStartSprinting()Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isUsingItem()Z"))
   private boolean makeSurePlayerCanSprint(boolean original) {
      return this.getUseItem() != null && this.getUseItem().getComponents().get(Spears.USE_EFFECTS) instanceof UseEffects u && u.allowSprinting()
         ? false
         : original;
   }

   @Inject(method = "tick()V", at = @At("TAIL"))
   private void triggerOnSwingEffects(CallbackInfo ci) {
      if (Spears.hasBetterCombat && this.minecraft.getConnection() != null) {
         try {
            if (SpearsClient.getUpswingTicks == null) {
               SpearsClient.getUpswingTicks = Minecraft.class.getDeclaredMethod("getUpswingTicks");
            }

            if (SpearsClient.getUpswingTicks.invoke(this.minecraft) instanceof Integer i) {
               if (i > SpearsClient.lastUpswingTicksForBetterCombat) {
                  SpearsClient.sendPacketUniversal.apply(new TriggerStabEffectsC2SPacket(true));
               }

               SpearsClient.lastUpswingTicksForBetterCombat = i;
            }
         } catch (Throwable var4) {
         }
      }
   }
}
