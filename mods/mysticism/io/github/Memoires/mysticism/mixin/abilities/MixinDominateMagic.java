package io.github.Memoires.mysticism.mixin.abilities;

import io.github.Memoires.mysticism.registry.skill.UniqueMysticismSkills;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.ability.magic.aspectual.mental.DominateMagic;
import io.github.manasmods.tensura.config.ability.magic.AspectualMagicConfig;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(DominateMagic.class)
public class MixinDominateMagic {
   @ModifyVariable(
      method = "onHeld(Lio/github/manasmods/manascore/skill/api/ManasSkillInstance;Lnet/minecraft/world/entity/LivingEntity;II)Z",
      at = @At("HEAD"),
      argsOnly = true,
      name = "arg3"
   )
   private int mysticism$forceHeldTicks(int heldTicks, ManasSkillInstance instance, LivingEntity entity, int mode) {
      if (SkillUtils.hasSkillFully(entity, (ManasSkill)UniqueMysticismSkills.SUBJUGATOR.get())
         && SkillUtils.isSkillMastered(entity, (ManasSkill)UniqueMysticismSkills.SUBJUGATOR.get())) {
         Entity target = entity.level().getEntity(instance.getOrCreateTag().getInt("TargetID"));
         if (target != null) {
            int castTime = ((AspectualMagicConfig)ConfigRegistry.getConfig(AspectualMagicConfig.class)).Dominate.castTime;
            instance.getOrCreateTag().putInt("ForcedHeldSeconds", castTime);
            return castTime;
         }
      }

      return heldTicks;
   }

   @ModifyVariable(
      method = "onRelease(Lio/github/manasmods/manascore/skill/api/ManasSkillInstance;Lnet/minecraft/world/entity/LivingEntity;III)V",
      at = @At("HEAD"),
      argsOnly = true,
      name = "arg3"
   )
   private int mysticism$forceHeldTicksOnRelease(int heldTicks, ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      CompoundTag tag = instance.getOrCreateTag();
      return tag.contains("ForcedHeldSeconds") ? tag.getInt("ForcedHeldSeconds") : heldTicks;
   }
}
