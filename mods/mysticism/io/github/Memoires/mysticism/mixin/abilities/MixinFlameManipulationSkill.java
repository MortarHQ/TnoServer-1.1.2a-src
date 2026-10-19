package io.github.Memoires.mysticism.mixin.abilities;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.github.Memoires.mysticism.registry.world.MysticismBiomes;
import io.github.Memoires.mysticism.storage.MysticismStorages;
import io.github.manasmods.tensura.ability.magic.Element;
import io.github.manasmods.tensura.ability.skill.extra.FlameManipulationSkill;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FlameManipulationSkill.class)
public class MixinFlameManipulationSkill {
   @ModifyReturnValue(method = "canUseFire(Lnet/minecraft/world/entity/LivingEntity;)Z", at = @At("RETURN"))
   private static boolean mysticism$canUseFire(boolean original, LivingEntity entity) {
      if (MysticismStorages.getElementFrom(entity).getElement() == Element.FLAME
         || entity.level().getBiome(entity.blockPosition()).is(MysticismBiomes.FIRE_BIOME)) {
         original = true;
      }

      return original;
   }
}
