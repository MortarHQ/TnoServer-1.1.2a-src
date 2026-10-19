package org.crypticdev.ancient_artifacts.mixin.tensura.ability.battlewill;

import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.battlewill.Battlewill;
import io.github.manasmods.tensura.ability.battlewill.melee.RoaringLionPunchArt;
import io.github.manasmods.tensura.config.ability.BattlewillConfig.RoaringLionPunch;
import io.github.manasmods.tensura.util.EnergyHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.crypticdev.ancient_artifacts.artifact.ArtifactItem;
import org.crypticdev.ancient_artifacts.artifact.ArtifactRefinement;
import org.crypticdev.ancient_artifacts.component.RefinementComponent;
import org.crypticdev.ancient_artifacts.config.dynamic_effects.BeastAuraConfig;
import org.crypticdev.ancient_artifacts.platform.PlatformEquipment;
import org.crypticdev.ancient_artifacts.registry.Artifacts;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(RoaringLionPunchArt.class)
public abstract class RoaringLionPunchArtMixin extends Battlewill {
   @Shadow
   @Final
   private static RoaringLionPunch CONFIG;

   @Shadow
   public abstract double getAuraCost(LivingEntity var1, ManasSkillInstance var2, int var3);

   @ModifyVariable(method = "onPressed", at = @At("STORE"), name = "auraCost")
   private double modifyAuraCost(double value, ManasSkillInstance instance, LivingEntity entity) {
      if (!PlatformEquipment.hasArtifact(entity, Artifacts.VermillionBirdCrown)) {
         return value;
      }

      ItemStack stack = PlatformEquipment.getArtifactStack(entity, Artifacts.VermillionBirdCrown);
      if (entity instanceof Player player) {
         if (!((ArtifactItem)stack.getItem()).getArtifact().artifactEffects().get(1).unlockCondition().apply(stack, player)) {
            return value;
         }

         BeastAuraConfig config = (BeastAuraConfig)ConfigRegistry.getConfig(BeastAuraConfig.class);
         RefinementComponent refinement = ArtifactRefinement.get(stack);
         int refinementSteps = Math.max(0, refinement.upgrades().get(1));
         if (config.max_refinements != -1) {
            refinementSteps = Math.min(refinementSteps, config.max_refinements);
         }

         return Math.min(
            EnergyHelper.getMaxAura(entity) * CONFIG.maxAuraMultiplier,
            instance.isMastered(entity)
               ? (double)config.ap_max_usage_mastered + refinementSteps * config.ap_max_usage_increase_per_refinement
               : (double)config.ap_max_usage * (refinementSteps * config.ap_max_usage_increase_per_refinement)
         );
      } else {
         return value;
      }
   }
}
