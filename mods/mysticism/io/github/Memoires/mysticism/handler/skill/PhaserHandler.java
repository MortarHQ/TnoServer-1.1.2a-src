package io.github.Memoires.mysticism.handler.skill;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.PlayerEvent;
import dev.architectury.event.events.common.PlayerEvent.PlayerJoin;
import io.github.Memoires.mysticism.ability.skill.unique.PhaserSkill;
import io.github.Memoires.mysticism.registry.dimension.MysticismDimensions;
import io.github.Memoires.mysticism.registry.skill.UniqueMysticismSkills;
import io.github.manasmods.manascore.race.api.SpawnPointHelper;
import io.github.manasmods.manascore.skill.api.EntityEvents;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.event.TensuraEntityEvents;
import io.github.manasmods.tensura.event.TensuraEntityEvents.DimensionTravelEvent;
import io.github.manasmods.tensura.registry.dimension.TensuraDimensions;
import java.util.Optional;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class PhaserHandler {
   public static void init() {
      EntityEvents.LIVING_PRE_TICK.register(PhaserHandler::sendTargetToOW);
      PlayerEvent.PLAYER_JOIN.register((PlayerJoin)serverPlayer -> {
         Optional<ManasSkillInstance> opt = SkillAPI.getSkillsFrom(serverPlayer).getSkill((ManasSkill)UniqueMysticismSkills.PHASER.get());
         if (opt.isPresent()) {
            ManasSkillInstance instance = opt.get();
            PhaserSkill.resetTags(instance);
         }
      });
      TensuraEntityEvents.DIMENSION_TRAVEL_EVENT
         .register(
            (DimensionTravelEvent)(target, teleporter, dimension) -> {
               if (target instanceof LivingEntity entity
                  && SkillUtils.hasSkillFully(entity, (ManasSkill)UniqueMysticismSkills.PHASER.get())
                  && dimension.equals(MysticismDimensions.KAMUI_DIMENSION)) {
                  Optional<ManasSkillInstance> phaserInstance = SkillAPI.getSkillsFrom(entity).getSkill(UniqueMysticismSkills.PHASER.getId());
                  if (entity.level() instanceof ServerLevel && phaserInstance.isPresent()) {
                     if (entity.level().dimension() == TensuraDimensions.LABYRINTH) {
                        return EventResult.pass();
                     }

                     phaserInstance.get().getOrCreateTag().putString("sourceDimension", entity.level().dimension().location().toString());
                     phaserInstance.get().markDirty();
                  }
               }

               return EventResult.pass();
            }
         );
   }

   private static void sendTargetToOW(LivingEntity entity) {
      if (!entity.level().isClientSide()) {
         if (entity.level().dimension().equals(MysticismDimensions.KAMUI_DIMENSION)) {
            if (!entity.isOnPortalCooldown()) {
               if (!(entity.getY() > -60.0)) {
                  if (entity instanceof ServerPlayer sp) {
                     MinecraftServer server = entity.getServer();
                     if (server != null) {
                        ResourceKey<Level> dimensionKey = Level.OVERWORLD;
                        ServerLevel targetLevel = server.getLevel(dimensionKey);
                        if (targetLevel != null) {
                           entity.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 120, 1));
                           Vec3 safe = PhaserSkill.scaledClampedSafeTarget(
                              (ServerLevel)entity.level(), targetLevel, entity, entity.getX(), entity.getY(), entity.getZ()
                           );
                           SpawnPointHelper.teleportToAcrossDimensions(entity, dimensionKey, safe.x, safe.y, safe.z, entity.getXRot(), entity.getYRot());
                           sp.setPortalCooldown();
                        }
                     }
                  }
               }
            }
         }
      }
   }
}
