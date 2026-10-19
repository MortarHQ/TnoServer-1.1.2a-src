package org.crypticdev.ancient_artifacts.mixin.tensura.ability.skill.extra;

import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.extra.BlackFlameSkill;
import net.minecraft.world.entity.LivingEntity;
import org.crypticdev.ancient_artifacts.registry.ArtifactAttributesRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(BlackFlameSkill.class)
public class BlackFlameSkillMixin {
   @ModifyArgs(
      method = "onPressed(Lio/github/manasmods/manascore/skill/api/ManasSkillInstance;Lnet/minecraft/world/entity/LivingEntity;II)V",
      at = @At(value = "INVOKE", target = "Lio/github/manasmods/tensura/entity/projectile/magic/HellFlareProjectile;setDamage(F)V")
   )
   private void multiplyHellFlarePrimary(Args args, ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (entity.getAttribute(ArtifactAttributesRegistry.HELL_FLARE_DAMAGE_MULTIPLIER) != null) {
         float originalDamage = (Float)args.get(0);
         float multiplier = (float)entity.getAttributeValue(ArtifactAttributesRegistry.HELL_FLARE_DAMAGE_MULTIPLIER);
         args.set(0, originalDamage * multiplier);
      }
   }

   @ModifyArgs(
      method = "onPressed(Lio/github/manasmods/manascore/skill/api/ManasSkillInstance;Lnet/minecraft/world/entity/LivingEntity;II)V",
      at = @At(value = "INVOKE", target = "Lio/github/manasmods/tensura/entity/projectile/magic/HellFlareProjectile;setSecondaryDamage(F)V")
   )
   private void multiplyHellFlareSecondary(Args args, ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (entity.getAttribute(ArtifactAttributesRegistry.HELL_FLARE_DAMAGE_MULTIPLIER) != null) {
         float originalSecondary = (Float)args.get(0);
         float multiplier = (float)entity.getAttributeValue(ArtifactAttributesRegistry.HELL_FLARE_DAMAGE_MULTIPLIER);
         args.set(0, originalSecondary * multiplier);
      }
   }
}
