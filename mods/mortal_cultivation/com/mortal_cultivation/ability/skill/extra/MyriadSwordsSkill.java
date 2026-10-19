package com.mortal_cultivation.ability.skill.extra;

import com.mortal_cultivation.ability.skill.base.LegacySkill;
import com.mortal_cultivation.myriadswords.MyriadSwordsServerState;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.util.EnergyHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class MyriadSwordsSkill extends LegacySkill {
   private static final double MAGICULE_COST_PER_SECOND = 2000.0;
   private static final double MAGICULE_COST_PER_SECOND_MASTERED = 1000.0;

   public MyriadSwordsSkill() {
      super(SkillType.EXTRA);
   }

   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("mortal_cultivation", "textures/skill/extra/myriad_swords_return.png");
   }

   @Override
   public boolean meetEPRequirement(Player entity, double curEP) {
      return false;
   }

   @Override
   public double magiculeCost(LivingEntity entity, ManasSkillInstance instance) {
      return instance.isMastered(entity) ? 1000.0 : 2000.0;
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      return false;
   }

   public boolean shouldTriggerReleaseOnHeldInterrupt(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      return true;
   }

   @Override
   public boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks) {
      if (entity instanceof ServerPlayer player) {
         if (heldTicks % 20 == 0 && EnergyHelper.isOutOfEnergy(player, instance, 0)) {
            MyriadSwordsServerState.stop(player);
            return false;
         }

         MyriadSwordsServerState.tick(player);
         if (heldTicks > 0 && heldTicks % BASE_CONFIG.Mastery.masteryHoldTick == 0) {
            this.addMasteryPoint(instance, player);
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   public void onRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks) {
      if (entity instanceof ServerPlayer player) {
         MyriadSwordsServerState.stop(player);
      }
   }
}
