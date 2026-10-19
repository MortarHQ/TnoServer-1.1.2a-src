package com.shin.tensura_kumodesu.client.renderer.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.shin.tensura_kumodesu.entity.taratect.SmallLesserTaratectEntity;
import com.shin.tensura_kumodesu.registry.entities.KumodesuEntities;
import com.shin.tensura_kumodesu.registry.race.KumodesuRaces;
import dev.architectury.registry.registries.RegistrySupplier;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.RaceAPI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;

public class TaratectLayer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
   private static Map<ManasRace, EntityType<? extends Entity>> raceToEntityMap;
   private static boolean initialized = false;
   private final Map<UUID, Entity> entityCache = new HashMap<>();

   private static <T> T safeGet(Object supplier) {
      try {
         return (T)(supplier instanceof RegistrySupplier<?> rs ? rs.get() : supplier);
      } catch (Exception var2) {
         return null;
      }
   }

   private static void register(Map<ManasRace, EntityType<? extends Entity>> map, Object raceSupplier, Object entitySupplier) {
      ManasRace race = safeGet(raceSupplier);
      EntityType<? extends Entity> entity = safeGet(entitySupplier);
      if (race != null && entity != null) {
         map.put(race, entity);
      }
   }

   private static Map<ManasRace, EntityType<? extends Entity>> getRaceMap() {
      if (initialized) {
         return raceToEntityMap;
      }

      Map<ManasRace, EntityType<? extends Entity>> map = new HashMap<>();
      register(map, KumodesuRaces.SMALL_LESSER_TARATECT, KumodesuEntities.SMALL_LESSER_TARATECT);
      register(map, KumodesuRaces.ORTHOCADINAHT, KumodesuEntities.ORTHOCADINAHT);
      register(map, KumodesuRaces.SMALL_TARATECT, KumodesuEntities.SMALL_TARATECT);
      register(map, KumodesuRaces.TARATECT, KumodesuEntities.TARATECT);
      register(map, KumodesuRaces.GREAT_TARATECT, KumodesuEntities.GREAT_TARATECT);
      register(map, KumodesuRaces.GREATER_TARATECT, KumodesuEntities.GREATER_TARATECT);
      register(map, KumodesuRaces.ARCH_TARATECT, KumodesuEntities.ARCH_TARATECT);
      register(map, KumodesuRaces.QUEEN_TARATECT, KumodesuEntities.QUEEN_TARATECT);
      register(map, KumodesuRaces.SMALL_POISON_TARATECT, KumodesuEntities.SMALL_POISON_TARATECT);
      register(map, KumodesuRaces.ZOA_ELE, KumodesuEntities.ZOA_ELE);
      register(map, KumodesuRaces.EDE_SAINE, KumodesuEntities.EDE_SAINE);
      register(map, KumodesuRaces.ZANA_HOROWA, KumodesuEntities.ZANA_HOROWA);
      register(map, KumodesuRaces.ARACHNE, KumodesuEntities.ARACHNE);
      register(map, KumodesuRaces.LESSER_TARATECT, KumodesuEntities.LESSER_TARATECT);
      register(map, KumodesuRaces.LESSER_POISON_TARATECT, KumodesuEntities.LESSER_POISON_TARATECT);
      register(map, KumodesuRaces.POISON_TARATECT, KumodesuEntities.POISON_TARATECT);
      register(map, KumodesuRaces.GREAT_POISON_TARATECT, KumodesuEntities.GREAT_POISON_TARATECT);
      raceToEntityMap = map;
      initialized = true;
      return raceToEntityMap;
   }

   public TaratectLayer(Context context) {
      super(null);
   }

   public void render(
      PoseStack poseStack,
      MultiBufferSource buffer,
      int packedLight,
      AbstractClientPlayer player,
      float limbSwing,
      float limbSwingAmount,
      float partialTicks,
      float ageInTicks,
      float netHeadYaw,
      float headPitch
   ) {
      Minecraft mc = Minecraft.getInstance();
      if (mc.level != null) {
         Optional<ManasRace> raceOpt = RaceAPI.getRaceFrom(player).getRace().map(ManasRaceInstance::getRace);
         if (!raceOpt.isEmpty()) {
            ManasRace race = raceOpt.get();
            EntityType<? extends Entity> entityType = getRaceMap().get(race);
            if (entityType != null) {
               UUID id = player.getUUID();
               Entity taratectEntity = this.entityCache.get(id);
               if (taratectEntity == null || taratectEntity.getType() != entityType) {
                  try {
                     taratectEntity = entityType.create(player.level());
                     if (taratectEntity == null) {
                        return;
                     }

                     this.entityCache.put(id, taratectEntity);
                  } catch (Exception ignored) {
                     return;
                  }
               }

               taratectEntity.setPos(player.getX(), player.getY(), player.getZ());
               taratectEntity.tickCount = player.tickCount;
               taratectEntity.setDeltaMovement(0.0, 0.0, 0.0);
               taratectEntity.setOnGround(true);
               float headYaw = Mth.lerp(partialTicks, player.yHeadRotO, player.yHeadRot);
               float bodyYaw = Mth.lerp(partialTicks, player.yBodyRotO, player.yBodyRot);
               float playerPitch = Mth.lerp(partialTicks, player.xRotO, player.getXRot());
               float relativeHeadYaw = Mth.wrapDegrees(headYaw - bodyYaw);
               if (taratectEntity instanceof SmallLesserTaratectEntity smallLesser) {
                  smallLesser.setCustomHeadRotation(relativeHeadYaw, playerPitch);
                  smallLesser.setLimbSwing(limbSwing);
                  smallLesser.setLimbSwingAmount(limbSwingAmount);
                  boolean moving = player.walkAnimation.speed() > 0.01F;
                  boolean crouching = player.isCrouching();
                  smallLesser.setPlayerState(moving, crouching);
                  smallLesser.setOwner(player);
               }

               poseStack.pushPose();
               poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
               float bonus = race == safeGet(KumodesuRaces.ARACHNE) ? 0.7F : 0.0F;
               if (player.getPose() == Pose.CROUCHING) {
                  poseStack.translate(0.0, -1.38 - bonus, 0.0);
               } else {
                  poseStack.translate(0.0, -1.5 - bonus, 0.0);
               }

               EntityRenderer<? super Entity> renderer = mc.getEntityRenderDispatcher().getRenderer(taratectEntity);
               if (!player.hasEffect(MobEffects.INVISIBILITY)) {
                  renderer.render(taratectEntity, 0.0F, partialTicks, poseStack, buffer, packedLight);
               }

               poseStack.popPose();
            }
         }
      }
   }
}
