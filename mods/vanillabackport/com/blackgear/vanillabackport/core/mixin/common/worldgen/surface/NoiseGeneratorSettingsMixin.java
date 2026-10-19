package com.blackgear.vanillabackport.core.mixin.common.worldgen.surface;

import com.blackgear.vanillabackport.common.worldgen.ModSurfaceRuleData;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.SurfaceRules.RuleSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = NoiseGeneratorSettings.class, priority = 100)
public class NoiseGeneratorSettingsMixin {
   @Shadow
   @Final
   @Mutable
   private RuleSource surfaceRule;
   @Unique
   private boolean vb$appliedRules = false;

   @Inject(
      method = "<init>(Lnet/minecraft/world/level/levelgen/NoiseSettings;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/levelgen/NoiseRouter;Lnet/minecraft/world/level/levelgen/SurfaceRules$RuleSource;Ljava/util/List;IZZZZ)V",
      at = @At("TAIL")
   )
   private void vb$injectSurfaceRules(CallbackInfo ci) {
      if (!this.vb$appliedRules) {
         this.surfaceRule = SurfaceRules.sequence(new RuleSource[]{ModSurfaceRuleData.makeRules(), this.surfaceRule});
         this.vb$appliedRules = true;
      }
   }
}
