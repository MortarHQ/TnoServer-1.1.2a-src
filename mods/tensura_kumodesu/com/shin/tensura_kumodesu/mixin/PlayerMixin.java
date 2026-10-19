package com.shin.tensura_kumodesu.mixin;

import com.shin.tensura_kumodesu.registry.skill.KumoIntrinsicSkills;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.data.TensuraBlockTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {
   protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
      super(entityType, level);
   }

   @Inject(
      method = "makeStuckInBlock(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/phys/Vec3;)V",
      at = @At("HEAD"),
      cancellable = true
   )
   public void makeStuckInBlock(BlockState blockState, Vec3 vec3, CallbackInfo ci) {
      if (SkillUtils.isSkillToggled(this, (ManasSkill)KumoIntrinsicSkills.SPIDERMOVEMENT.get())) {
         if (!blockState.is(TensuraBlockTags.WEB_BLOCKS)) {
            super.makeStuckInBlock(blockState, vec3);
         }

         ci.cancel();
      }
   }
}
