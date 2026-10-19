package com.tensura_tno.mixin.client;

import com.tensura_tno.compat.vanillabackport.VanillaBackportSurfaceRuleCompat;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.SurfaceRules.RuleSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = NoiseGeneratorSettings.class, priority = 200)
public abstract class VanillaBackportSurfaceRulesMixin {
   @Shadow
   @Final
   @Mutable
   private RuleSource surfaceRule;

   @Inject(
      method = "<init>(Lnet/minecraft/world/level/levelgen/NoiseSettings;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/levelgen/NoiseRouter;Lnet/minecraft/world/level/levelgen/SurfaceRules$RuleSource;Ljava/util/List;IZZZZ)V",
      at = @At("TAIL")
   )
   private void tensuraTno$deduplicateBackportRules(CallbackInfo ci) {
      this.surfaceRule = VanillaBackportSurfaceRuleCompat.deduplicate(this.surfaceRule);
   }
}
