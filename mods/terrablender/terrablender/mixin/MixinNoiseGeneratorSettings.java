package terrablender.mixin;

import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.SurfaceRules.RuleSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import terrablender.api.SurfaceRuleManager;
import terrablender.worldgen.IExtendedNoiseGeneratorSettings;

@Mixin(NoiseGeneratorSettings.class)
public class MixinNoiseGeneratorSettings implements IExtendedNoiseGeneratorSettings {
   @Shadow
   private RuleSource surfaceRule;
   @Unique
   private SurfaceRuleManager.RuleCategory ruleCategory = null;
   @Unique
   private RuleSource namespacedSurfaceRuleSource = null;

   @Inject(method = "surfaceRule", at = @At("HEAD"), cancellable = true)
   private void surfaceRule(CallbackInfoReturnable<RuleSource> cir) {
      if (this.ruleCategory != null) {
         if (this.namespacedSurfaceRuleSource == null) {
            this.namespacedSurfaceRuleSource = SurfaceRuleManager.getNamespacedRules(this.ruleCategory, this.surfaceRule);
         }

         cir.setReturnValue(this.namespacedSurfaceRuleSource);
      }
   }

   @Override
   public void setRuleCategory(SurfaceRuleManager.RuleCategory ruleCategory) {
      this.ruleCategory = ruleCategory;
   }
}
