package com.tensura_tno.client.race;

import com.mojang.blaze3d.vertex.PoseStack;
import com.tensura_tno.TensuraTNOCompatConfig;
import com.tensura_tno.ability.skill.HumanFormSkill;
import io.github.manasmods.manascore.race.api.RaceAPI;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

public final class PlayerOrcRenderManager {
   public static final Set<ResourceLocation> ORC_RACE_IDS = Set.of(
      ResourceLocation.fromNamespaceAndPath("tensura", "orc"),
      ResourceLocation.fromNamespaceAndPath("tensura", "high_orc"),
      ResourceLocation.fromNamespaceAndPath("tensura", "spirit_boar"),
      ResourceLocation.fromNamespaceAndPath("tensura", "orc_lord"),
      ResourceLocation.fromNamespaceAndPath("tensura", "orc_disaster"),
      ResourceLocation.fromNamespaceAndPath("tensura", "divine_boar")
   );
   private static final PlayerOrcRenderer RENDERER = new PlayerOrcRenderer();
   private static final ConcurrentMap<UUID, PlayerOrcAnimatable> ANIMATABLES = new ConcurrentHashMap<>();

   private PlayerOrcRenderManager() {
   }

   public static boolean shouldRenderAsOrc(Player player) {
      if (!TensuraTNOCompatConfig.isRacePlayerModelsEnabled()) {
         return false;
      } else if (player == null) {
         return false;
      } else if (player.isSpectator()) {
         return false;
      } else {
         return isHumanFormActive(player) ? false : isOrcRace(player);
      }
   }

   public static boolean isOrcRace(LivingEntity entity) {
      if (entity == null) {
         return false;
      }

      try {
         return RaceAPI.getRaceFrom(entity).getRace().map(inst -> {
            ResourceLocation id = inst.getRace().getRegistryName();
            return id != null && ORC_RACE_IDS.contains(id);
         }).orElse(false);
      } catch (Throwable t) {
         return false;
      }
   }

   private static boolean isHumanFormActive(Player player) {
      AttributeInstance scale = player.getAttribute(Attributes.SCALE);
      return scale == null ? false : scale.getModifier(HumanFormSkill.HUMAN_FORM_SCALE) != null;
   }

   public static PlayerOrcAnimatable getOrCreate(Player player) {
      return ANIMATABLES.compute(player.getUUID(), (uuid, existing) -> {
         if (existing == null) {
            return new PlayerOrcAnimatable(player);
         }

         existing.setPlayer(player);
         return (PlayerOrcAnimatable)existing;
      });
   }

   public static void forget(UUID uuid) {
      ANIMATABLES.remove(uuid);
   }

   public static void render(Player player, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, float partialTick) {
      PlayerOrcAnimatable animatable = getOrCreate(player);
      RENDERER.render(poseStack, animatable, bufferSource, null, null, packedLight, partialTick);
   }
}
