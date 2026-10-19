package com.tensura_tno.mixin.client;

import io.github.manasmods.tensura.ability.SkillClientUtils;
import io.github.manasmods.tensura.data.TensuraEntityTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(value = SkillClientUtils.class, remap = false)
public class TensuraLivingEntitySenseMixin {
   @Inject(method = "getGlowColor", at = @At("HEAD"), cancellable = true, require = 0, remap = false)
   private static void tensuraTno$onlyLivingEntities(Player player, Entity entity, CallbackInfoReturnable<Integer> cir) {
      if (!(entity instanceof LivingEntity living && !entity.getType().is(TensuraEntityTags.NON_LIVING) && living.isAlive() && !(living.getHealth() <= 0.0F))) {
         cir.setReturnValue(-1);
      }
   }
}
