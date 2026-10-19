package com.tensura_tno.ability.skill;

import com.tensura_tno.network.MagicOreSensePackets;
import dev.architectury.networking.NetworkManager;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.registry.skill.ExtraSkills;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.util.EnergyHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class MagicOreSenseSkill extends Skill {
   private static final int MAX_MASTERY = 100;
   private static final int NORMAL_RADIUS = 12;
   private static final int MASTERED_RADIUS = 20;
   private static final int NORMAL_DURATION_TICKS = 160;
   private static final int MASTERED_DURATION_TICKS = 320;
   private static final int NORMAL_COOLDOWN_SECONDS = 12;
   private static final int MASTERED_COOLDOWN_SECONDS = 6;
   private static final double NORMAL_MAGICULE_COST = 2500.0;
   private static final double MASTERED_MAGICULE_COST = 1500.0;

   public MagicOreSenseSkill() {
      super(SkillType.EXTRA);
   }

   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("tensura_tno", "textures/skill/magic_ore_sense.png");
   }

   public int getMaxMastery() {
      return 100;
   }

   public boolean checkAcquiringRequirement(Player entity, double newEP) {
      return hasAcquisitionPrerequisites(entity);
   }

   public static boolean hasAcquisitionPrerequisites(Player entity) {
      return SkillUtils.isSkillMastered(entity, (ManasSkill)ExtraSkills.MAGIC_SENSE.get())
         && SkillUtils.isSkillMastered(entity, (ManasSkill)ExtraSkills.SENSE_HEAT_SOURCE.get())
         && SkillUtils.isSkillMastered(entity, (ManasSkill)ExtraSkills.SENSE_SOUNDWAVE.get());
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      return instance.isMastered(entity) ? 1500.0 : 2500.0;
   }

   public boolean canIgnoreCoolDown(ManasSkillInstance instance, LivingEntity entity, int mode) {
      return false;
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (!entity.level().isClientSide()) {
         if (entity instanceof ServerPlayer player) {
            if (!instance.onCoolDown(mode)) {
               if (!EnergyHelper.isOutOfEnergy(player, instance, mode)) {
                  boolean mastered = instance.isMastered(player);
                  int radius = mastered ? 20 : 12;
                  int durationTicks = mastered ? 320 : 160;
                  int cooldownSeconds = mastered ? 6 : 12;
                  NetworkManager.sendToPlayer(player, new MagicOreSensePackets.StartScanPayload(radius, durationTicks));
                  player.level()
                     .playSound(
                        null, player.getX(), player.getY(), player.getZ(), (SoundEvent)TensuraSoundEvents.BUFF_ACTIVATE.get(), SoundSource.PLAYERS, 0.8F, 1.2F
                     );
                  instance.addMasteryPoint(player, 1.0);
                  instance.setCoolDown(cooldownSeconds, mode);
                  instance.markDirty();
               }
            }
         }
      }
   }
}
