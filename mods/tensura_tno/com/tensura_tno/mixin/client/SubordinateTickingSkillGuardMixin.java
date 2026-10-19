package com.tensura_tno.mixin.client;

import com.tensura_tno.ai.SubordinateSkillAiController;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.impl.SkillStorage;
import io.github.manasmods.manascore.skill.impl.TickingSkill;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = TickingSkill.class, remap = false)
public abstract class SubordinateTickingSkillGuardMixin {
   @Shadow
   @Final
   private ManasSkill skill;
   @Shadow
   @Final
   private int mode;
   @Shadow
   @Final
   private int keyNumber;

   @Inject(method = "tick", at = @At("HEAD"), cancellable = true, remap = false)
   private void tensuraTno$stopStaleAiCast(SkillStorage storage, LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
      if (SubordinateSkillAiController.shouldStopHeldTick(entity, this.skill, this.mode, this.keyNumber)) {
         cir.setReturnValue(false);
      }
   }
}
