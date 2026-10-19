package com.tensura_tno.client.race;

import com.mojang.blaze3d.vertex.PoseStack;
import com.tensura_tno.TensuraTNOCompatConfig;
import com.tensura_tno.ability.skill.HumanFormSkill;
import com.tensura_tno.race.SlimeRaceHelper;
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

public final class PlayerSlimeRenderManager {
   public static final Set<ResourceLocation> SLIME_RACE_IDS = SlimeRaceHelper.SLIME_RACE_IDS;
   private static final PlayerSlimeRenderer RENDERER = new PlayerSlimeRenderer();
   private static final ConcurrentMap<UUID, PlayerSlimeAnimatable> ANIMATABLES = new ConcurrentHashMap<>();

   private PlayerSlimeRenderManager() {
   }

   public static boolean shouldRenderAsSlime(Player player) {
      if (!TensuraTNOCompatConfig.isRacePlayerModelsEnabled()) {
         return false;
      } else if (player == null) {
         return false;
      } else if (player.isSpectator()) {
         return false;
      } else {
         return isHumanFormActive(player) ? false : isSlimeRace(player);
      }
   }

   public static boolean isSlimeRace(LivingEntity entity) {
      return SlimeRaceHelper.isSlimeRace(entity);
   }

   private static boolean isHumanFormActive(Player player) {
      AttributeInstance scale = player.getAttribute(Attributes.SCALE);
      return scale == null ? false : scale.getModifier(HumanFormSkill.HUMAN_FORM_SCALE) != null;
   }

   public static PlayerSlimeAnimatable getOrCreate(Player player) {
      return ANIMATABLES.compute(player.getUUID(), (uuid, existing) -> {
         if (existing == null) {
            return new PlayerSlimeAnimatable(player);
         }

         existing.setPlayer(player);
         return (PlayerSlimeAnimatable)existing;
      });
   }

   public static void forget(UUID uuid) {
      ANIMATABLES.remove(uuid);
   }

   public static void render(Player player, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, float partialTick) {
      PlayerSlimeAnimatable animatable = getOrCreate(player);
      RENDERER.render(poseStack, animatable, bufferSource, null, null, packedLight, partialTick);
   }
}
