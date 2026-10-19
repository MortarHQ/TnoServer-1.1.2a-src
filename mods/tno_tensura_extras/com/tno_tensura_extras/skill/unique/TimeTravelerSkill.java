package com.tno_tensura_extras.skill.unique;

import com.tno_tensura_extras.util.TimeTravelManager;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;

public final class TimeTravelerSkill extends Skill {
   private static final int USE_COOLDOWN_SECONDS = 1800;
   private static final double USE_MASTERY_GAIN = 5.0;
   private static final ResourceLocation ICON = ResourceLocation.fromNamespaceAndPath("tno_tensura_extras", "textures/skill/unique/time_traveler.png");

   public TimeTravelerSkill() {
      super(SkillType.UNIQUE);
   }

   public ResourceLocation getSkillIcon() {
      return ICON;
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return "default";
   }

   public void onLearnSkill(ManasSkillInstance instance, LivingEntity entity) {
      super.onLearnSkill(instance, entity);
      TimeTravelManager.recordSeed(instance, entity);
   }

   public boolean canInteractSkill(ManasSkillInstance instance, LivingEntity entity) {
      MinecraftServer server = entity.level().getServer();
      return server != null && !isAllowedSinglePlayer(server) ? false : super.canInteractSkill(instance, entity);
   }

   public boolean canActivateSkill(ManasSkillInstance instance, LivingEntity entity, int mode) {
      return this.canInteractSkill(instance, entity);
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (mode == 0 && entity instanceof ServerPlayer && instance != null && !instance.onCoolDown(mode)) {
         TimeTravelManager.request(instance, entity);
      }
   }

   public static void applySuccessfulUse(ManasSkillInstance instance, LivingEntity entity, int mode) {
      if (instance != null && entity != null) {
         instance.addMasteryPoint(entity, 5.0);
         instance.setCoolDown(1800, mode);
      }
   }

   private static boolean isAllowedSinglePlayer(MinecraftServer server) {
      return server.isSingleplayer()
         && !server.isDedicatedServer()
         && !server.isPublished()
         && server.getPlayerList() != null
         && server.getPlayerList().getPlayerCount() == 1;
   }
}
