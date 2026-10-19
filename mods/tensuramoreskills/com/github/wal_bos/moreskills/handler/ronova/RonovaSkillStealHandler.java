package com.github.wal_bos.moreskills.handler.ronova;

import com.github.wal_bos.moreskills.ability.skill.shades.ronova.RonovaConfigAccess;
import dev.architectury.event.EventResult;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.data.TensuraEntityTags;
import io.github.manasmods.tensura.event.TensuraSkillEvents;
import io.github.manasmods.tensura.event.TensuraSkillEvents.SkillPlunderEvent;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public final class RonovaSkillStealHandler {
   private RonovaSkillStealHandler() {
   }

   public static void steal(LivingEntity owner, LivingEntity dead) {
      if ((Boolean)RonovaConfigAccess.cfg().deathFieldStealSkills.get()) {
         if (!dead.getType().is(TensuraEntityTags.NO_SKILL_PLUNDER)) {
            int stolen = 0;

            for (ManasSkillInstance target : List.copyOf(SkillAPI.getSkillsFrom(dead).getLearnedSkills())) {
               ManasSkill skill = target.getSkill();
               if (skill != null
                  && !target.isTemporarySkill()
                  && !(target.getMastery() < 0.0)
                  && !(skill instanceof Skill tensuraSkill && tensuraSkill.getType() == SkillType.ULTIMATE)) {
                  Changeable<ManasSkill> changeable = Changeable.of(skill);
                  EventResult result = ((SkillPlunderEvent)TensuraSkillEvents.SKILL_PLUNDER.invoker()).plunder(dead, owner, true, changeable);
                  if (!result.isFalse()) {
                     ManasSkill finalSkill = (ManasSkill)changeable.get();
                     SkillHelper.learnSkill(owner, finalSkill, target.getRemoveTime());
                     if (SkillAPI.getSkillsFrom(owner).getSkill(finalSkill).isPresent()) {
                        SkillAPI.getSkillsFrom(dead).forgetSkill(finalSkill);
                        stolen++;
                     }
                  }
               }
            }

            if ((Boolean)RonovaConfigAccess.cfg().deathFieldAddVictimEpToMax.get()) {
               double ep = EnergyHelper.getMaxEP(dead) * (Double)RonovaConfigAccess.cfg().deathFieldEpGainMultiplier.get();
               EnergyHelper.setBaseMaxEP(owner, EnergyHelper.getBaseMaxEP(owner) + ep);
            }

            if (stolen > 0 && owner instanceof Player player) {
               player.displayClientMessage(
                  Component.translatable("skill.tensuramoreskills.ronova.skills_stolen", new Object[]{stolen, dead.getDisplayName()})
                     .withStyle(ChatFormatting.GOLD),
                  true
               );
               owner.level()
                  .playSound(null, owner.getX(), owner.getY(), owner.getZ(), (SoundEvent)SoundEvents.SOUL_ESCAPE.value(), SoundSource.PLAYERS, 0.8F, 0.8F);
            }
         }
      }
   }
}
