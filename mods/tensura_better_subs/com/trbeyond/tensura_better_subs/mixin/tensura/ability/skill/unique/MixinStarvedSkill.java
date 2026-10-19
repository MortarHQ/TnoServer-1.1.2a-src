package com.trbeyond.tensura_better_subs.mixin.tensura.ability.skill.unique;

import com.trbeyond.tensura_better_subs.ability.skill.IFoodChain;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.unique.StarvedSkill;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(StarvedSkill.class)
public class MixinStarvedSkill implements IFoodChain {
   @Inject(
      method = "onPressed(Lio/github/manasmods/manascore/skill/api/ManasSkillInstance;Lnet/minecraft/world/entity/LivingEntity;II)V",
      at = @At("HEAD"),
      cancellable = true,
      remap = true
   )
   private void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode, CallbackInfo ci) {
      switch (mode) {
         case 3:
            this.openProvideMenu(entity, (StarvedSkill)this);
            ci.cancel();
      }
   }

   @Override
   public int getProvideMode() {
      return 3;
   }
}
