package com.tensura_tno.event;

import com.tensura_tno.TensuraTNOMod;
import com.tensura_tno.race.SlimeHumanFormState;
import com.tensura_tno.race.SlimeRaceHelper;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.registry.skill.UniqueSkills;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

public final class SlimePredationHumanFormHandler {
   private static final Set<ResourceLocation> OTHERWORLDER_IDS = Set.of(
      id("folgen"),
      id("hinata_sakaguchi"),
      id("kirara_mizutani"),
      id("kyoya_tachibana"),
      id("mai_furuki"),
      id("mark_lauren"),
      id("shinji_tanimura"),
      id("shin_ryusei"),
      id("shizu"),
      id("shogo_taguchi")
   );
   private static volatile boolean registered = false;

   private SlimePredationHumanFormHandler() {
   }

   public static void register() {
      if (!registered) {
         synchronized (SlimePredationHumanFormHandler.class) {
            if (!registered) {
               NeoForge.EVENT_BUS.register(SlimePredationHumanFormHandler.class);
               registered = true;
            }
         }
      }
   }

   @SubscribeEvent
   public static void onLivingDeath(LivingDeathEvent event) {
      LivingEntity victim = event.getEntity();
      if (!victim.level().isClientSide()) {
         if (isOtherworlderOrPlayer(victim)) {
            if (event.getSource().getEntity() instanceof Player playerKiller) {
               if (playerKiller != victim) {
                  if (playerKiller instanceof ServerPlayer server) {
                     if (SlimeRaceHelper.isSlimeRace(server)) {
                        if (hasPredationSkill(server)) {
                           SlimeHumanFormState.unlock(server);
                           TensuraTNOMod.LOGGER
                              .info(
                                 "[TensuraTNO] Slime player {} unlocked human-form toggle (devoured {})",
                                 server.getGameProfile().getName(),
                                 victim.getType().builtInRegistryHolder().key().location()
                              );
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private static boolean isOtherworlderOrPlayer(LivingEntity victim) {
      if (victim instanceof Player) {
         return true;
      }

      EntityType<?> type = victim.getType();
      ResourceLocation id = type.builtInRegistryHolder().key().location();
      return OTHERWORLDER_IDS.contains(id);
   }

   private static boolean hasPredationSkill(LivingEntity entity) {
      try {
         ManasSkill predator = (ManasSkill)UniqueSkills.PREDATOR.get();
         if (predator != null && SkillUtils.hasSkill(entity, predator)) {
            return true;
         }
      } catch (Throwable var3) {
      }

      try {
         ManasSkill gluttony = (ManasSkill)UniqueSkills.GLUTTONY.get();
         if (gluttony != null && SkillUtils.hasSkill(entity, gluttony)) {
            return true;
         }
      } catch (Throwable var2) {
      }

      return false;
   }

   private static ResourceLocation id(String path) {
      return ResourceLocation.fromNamespaceAndPath("tensura", path);
   }
}
