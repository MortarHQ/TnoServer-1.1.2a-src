package io.github.Memoires.mysticism.mixin.client;

import io.github.Memoires.mysticism.registry.effects.MysticismMobEffects;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
public class MixinMouseHandler {
   @Shadow
   @Final
   private Minecraft minecraft;

   @Inject(method = "turnPlayer(D)V", at = @At("HEAD"), cancellable = true)
   private void myst$lockView(double dt, CallbackInfo ci) {
      LocalPlayer p = this.minecraft.player;
      if (p != null && p.hasEffect(TensuraMobEffects.getReference(MysticismMobEffects.FIXATION))) {
         ci.cancel();
      }
   }
}
