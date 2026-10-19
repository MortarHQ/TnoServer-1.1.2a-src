package io.github.Memoires.mysticism.mixin.abilities;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.github.Memoires.mysticism.registry.skill.UniqueMysticismSkills;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.ability.battlewill.utility.AirFlightArt;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AirFlightArt.class)
public class MixinAirFlightArt {
   @ModifyReturnValue(
      method = "getMagiculeCost(Lnet/minecraft/world/entity/LivingEntity;Lio/github/manasmods/manascore/skill/api/ManasSkillInstance;I)D",
      at = @At("RETURN")
   )
   private static double mysticism$getMagiculeCost(double original, LivingEntity entity) {
      return SkillUtils.hasSkillFully(entity, (ManasSkill)UniqueMysticismSkills.RESTRICTED.get()) ? 0.0 : original;
   }

   @ModifyReturnValue(
      method = "getAuraCost(Lnet/minecraft/world/entity/LivingEntity;Lio/github/manasmods/manascore/skill/api/ManasSkillInstance;I)D",
      at = @At("RETURN")
   )
   private static double mysticism$getAuraCost(double original, LivingEntity entity) {
      return SkillUtils.hasSkillFully(entity, (ManasSkill)UniqueMysticismSkills.RESTRICTED.get()) ? original * 2.0 : original;
   }
}
