package com.tensura_tno.mixin.client;

import com.tensura_tno.ai.SubordinateCloneGuard;
import io.github.manasmods.tensura.entity.human.CloneEntity;
import io.github.manasmods.tensura.entity.human.CloneEntity.CopySkill;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = CloneEntity.class, remap = false)
public abstract class SubordinateCloneSourceMixin implements SubordinateCloneGuard.SpawnSource {
   @Unique
   private boolean tensuraTno$subordinateClone;

   @Inject(
      method = "copyStatsAndSkills(Lnet/minecraft/world/entity/LivingEntity;Lio/github/manasmods/tensura/entity/human/CloneEntity$CopySkill;Z)V",
      at = @At("HEAD"),
      cancellable = true,
      remap = false
   )
   private void tensuraTno$checkCloneSummoner(LivingEntity summoner, CopySkill copySkill, boolean copyRace, CallbackInfo ci) {
      if (SubordinateCloneGuard.isRestrictedSummoner(summoner)) {
         this.tensuraTno$subordinateClone = true;
         ci.cancel();
      }
   }

   @Override
   public boolean tensuraTno$isSubordinateClone() {
      return this.tensuraTno$subordinateClone;
   }
}
