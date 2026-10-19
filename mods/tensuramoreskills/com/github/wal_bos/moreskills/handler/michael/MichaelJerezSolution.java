package com.github.wal_bos.moreskills.handler.michael;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.net.msg.MichaelJerezVfxPayload;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public final class MichaelJerezSolution implements MichaelAttack {
   @Override
   public String modeId() {
      return "michael_angel_of_locks.jerez";
   }

   @Override
   public double magiculeCost(LivingEntity entity, ManasSkillInstance instance) {
      return ((Integer)MoreSkillsConfigs.GRAND.michaelAngelOfLocks.jerezCost.get()).intValue();
   }

   @Override
   public void activate(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (!entity.level().isClientSide()) {
         Entity selected = ObjectSelectionHelper.getTargetingEntity(entity, (Double)MoreSkillsConfigs.GRAND.michaelAngelOfLocks.jerezRange.get(), false, true);
         if (!(selected instanceof LivingEntity target && MichaelActions.validEnemy(entity, target))) {
            MichaelActions.fail(entity, "No solution found.");
            instance.setCoolDown(5, mode);
         } else if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
            AttributeInstance armor = target.getAttribute(Attributes.ARMOR);
            if (armor != null) {
               armor.addOrReplacePermanentModifier(new AttributeModifier(MichaelActions.JEREZ_ARMOR_BREAK, -0.6, Operation.ADD_MULTIPLIED_TOTAL));
            }

            target.addEffect(new MobEffectInstance(TensuraMobEffects.getReference(TensuraMobEffects.SEVERANCE_BLADE), 160, 2, true, false, true), entity);
            target.hurt(MichaelActions.damage(entity, TensuraDamageTypes.SEVERANCE), 80.0F);
            MichaelJerezVfxPayload.send(entity, target, 140);
            entity.swing(InteractionHand.MAIN_HAND, true);
            entity.level()
               .playSound(
                  null, target.getX(), target.getY(), target.getZ(), (SoundEvent)TensuraSoundEvents.DEBUFF_ACTIVATE.get(), SoundSource.PLAYERS, 1.3F, 0.7F
               );
            instance.addMasteryPoint(entity);
            instance.setCoolDown(
               instance.isMastered(entity)
                  ? (Integer)MoreSkillsConfigs.GRAND.michaelAngelOfLocks.jerezCooldownMasteredTicks.get()
                  : (Integer)MoreSkillsConfigs.GRAND.michaelAngelOfLocks.jerezCooldownTicks.get(),
               mode
            );
         }
      }
   }
}
