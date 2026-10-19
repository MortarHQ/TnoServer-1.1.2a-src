package io.github.manasmods.tensura_iaf.mixin;

import com.iafenvoy.iceandfire.item.block.IceSpikesBlock;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.registry.skill.ResistanceSkills;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(IceSpikesBlock.class)
public class MixinIceSpikesBlock {
   @Inject(
      method = "stepOn(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/entity/Entity;)V",
      cancellable = true,
      at = @At("HEAD")
   )
   private void stepOn(Level worldIn, BlockPos pos, BlockState pState, Entity entityIn, CallbackInfo ci) {
      if (entityIn instanceof LivingEntity entity) {
         if (SkillUtils.isSkillToggled(entity, (ManasSkill)ResistanceSkills.COLD_RESISTANCE.get())) {
            ci.cancel();
         } else if (SkillUtils.isSkillToggled(entity, (ManasSkill)ResistanceSkills.COLD_NULLIFICATION.get())) {
            ci.cancel();
         } else if (SkillUtils.isSkillToggled(entity, (ManasSkill)ResistanceSkills.THERMAL_FLUCTUATION_RESISTANCE.get())) {
            ci.cancel();
         } else if (SkillUtils.isSkillToggled(entity, (ManasSkill)ResistanceSkills.THERMAL_FLUCTUATION_NULLIFICATION.get())) {
            ci.cancel();
         }
      }
   }
}
