package com.tensura_tno.mixin.client;

import com.tensura_tno.compat.YigdCompat;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Redirect;

@Pseudo
@Mixin(targets = "io.github.manasmods.tensura.ability.skill.unique.UnyieldingSkill", remap = false)
public abstract class UnyieldingYigdDeathProxyMixin {
   @Redirect(
      method = "onDeath(Lio/github/manasmods/manascore/skill/api/ManasSkillInstance;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/damagesource/DamageSource;)Z",
      at = @At(value = "INVOKE", target = "Lio/github/manasmods/tensura/entity/human/CloneEntity;die(Lnet/minecraft/world/damagesource/DamageSource;)V"),
      remap = false,
      require = 0
   )
   private void tensuraTno$markUnyieldingDeathProxy(@Coerce Object cloneObject, DamageSource source) {
      LivingEntity clone = (LivingEntity)cloneObject;
      YigdCompat.markPlayerDeathProxy(clone);

      try {
         clone.die(source);
      } finally {
         YigdCompat.clearPlayerDeathProxyMarker(clone);
      }
   }
}
