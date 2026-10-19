package net.unusual.block_factorys_bosses.mixins.client;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.unusual.block_factorys_bosses.entity.decoration.CannonEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin extends AbstractClientPlayer {
   public LocalPlayerMixin(ClientLevel clientLevel, GameProfile gameProfile) {
      super(clientLevel, gameProfile);
   }

   @Inject(method = "startRiding", at = @At("HEAD"))
   private void onStartRiding(Entity entity, boolean force, CallbackInfoReturnable<Boolean> cir) {
      if (super.startRiding(entity, force) && entity instanceof CannonEntity cannon) {
         this.setYRot(cannon.getYRot());
         this.setXRot(cannon.getXRot());
      }
   }
}
