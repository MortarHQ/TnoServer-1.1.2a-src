package com.tensura_tno.client.race;

import com.tensura_tno.ability.skill.HumanFormSkill;
import io.github.manasmods.manascore.race.api.RaceAPI;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

public final class PlayerKumodesuRenderManager {
   public static final Set<ResourceLocation> SPIDER_RACE_IDS = Set.of(
      ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", "small_lesser_taratect"),
      ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", "orthocadinaht"),
      ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", "small_taratect"),
      ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", "taratect"),
      ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", "great_taratect"),
      ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", "greater_taratect"),
      ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", "arch_taratect"),
      ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", "queen_taratect"),
      ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", "small_poison_taratect"),
      ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", "zoa_ele"),
      ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", "ede_saine"),
      ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", "zana_horowa"),
      ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", "arachne"),
      ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", "lesser_taratect"),
      ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", "lesser_poison_taratect"),
      ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", "poison_taratect"),
      ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", "great_poison_taratect")
   );

   private PlayerKumodesuRenderManager() {
   }

   public static boolean shouldRenderAsSpider(Player player) {
      if (player == null) {
         return false;
      } else if (player.isSpectator()) {
         return false;
      } else {
         return isHumanFormActive(player) ? false : isSpiderRace(player);
      }
   }

   public static boolean shouldBypassKumodesuFirstPersonItemCancel(LivingEntity entity) {
      if (!(entity instanceof Player player)) {
         return false;
      } else if (player.isSpectator()) {
         return false;
      } else {
         return isHumanFormActive(player) ? false : isSpiderRace(player) && !isArachneRace(player);
      }
   }

   public static boolean isSpiderRace(LivingEntity entity) {
      if (entity == null) {
         return false;
      }

      try {
         return RaceAPI.getRaceFrom(entity).getRace().map(inst -> {
            ResourceLocation id = inst.getRace().getRegistryName();
            return id != null && SPIDER_RACE_IDS.contains(id);
         }).orElse(false);
      } catch (Throwable t) {
         return false;
      }
   }

   private static boolean isArachneRace(LivingEntity entity) {
      if (entity == null) {
         return false;
      }

      try {
         return RaceAPI.getRaceFrom(entity)
            .getRace()
            .map(inst -> ResourceLocation.fromNamespaceAndPath("tensura_kumodesu", "arachne").equals(inst.getRace().getRegistryName()))
            .orElse(false);
      } catch (Throwable t) {
         return false;
      }
   }

   private static boolean isHumanFormActive(Player player) {
      AttributeInstance scale = player.getAttribute(Attributes.SCALE);
      return scale == null ? false : scale.getModifier(HumanFormSkill.HUMAN_FORM_SCALE) != null;
   }
}
