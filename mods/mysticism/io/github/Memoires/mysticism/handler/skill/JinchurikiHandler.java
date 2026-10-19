package io.github.Memoires.mysticism.handler.skill;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.PlayerEvent;
import dev.architectury.event.events.common.EntityEvent.LivingDeath;
import dev.architectury.event.events.common.PlayerEvent.PlayerQuit;
import dev.architectury.event.events.common.PlayerEvent.PlayerRespawn;
import io.github.Memoires.mysticism.config.ability.skill.UniqueMysticismSkillConfig;
import io.github.Memoires.mysticism.entity.skill.UncontrolledJinchurikiEntity;
import io.github.Memoires.mysticism.registry.skill.UniqueMysticismSkills;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.EntityEvents;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.TensuraSkill;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.GameType;

public class JinchurikiHandler {
   protected static final UniqueMysticismSkillConfig.Jinchuriki CONFIG = ((UniqueMysticismSkillConfig)ConfigRegistry.getConfig(UniqueMysticismSkillConfig.class))
      .Jinchuriki;

   public static void init() {
      EntityEvents.DEATH_EVENT_NORMAL.register((LivingDeath)(entity, source) -> {
         if (entity instanceof UncontrolledJinchurikiEntity jinchuriki && jinchuriki.getOwner() instanceof ServerPlayer owner && owner.isAlive()) {
            restoreGameMode(owner);
            float lethalDamage = Math.max(owner.getHealth(), 1.0F);
            owner.getCombatTracker().recordDamage(source, lethalDamage);
            owner.setHealth(0.0F);
            owner.die(source);
         }

         return EventResult.pass();
      });
      PlayerEvent.PLAYER_RESPAWN.register((PlayerRespawn)(player, keepEverything, removalReason) -> restoreGameMode(player));
      PlayerEvent.PLAYER_QUIT.register((PlayerQuit)player -> {
         restoreGameMode(player);
         removeUncontrolledClone(player);
      });
      EntityEvents.DEATH_EVENT_NORMAL
         .register(
            (LivingDeath)(entity, source) -> {
               if (entity instanceof UncontrolledJinchurikiEntity jinch) {
                  LivingEntity owner = jinch.getOwner();
                  if (owner != null) {
                     Optional<ManasSkillInstance> opt = SkillAPI.getSkillsFrom(owner).getSkill((ManasSkill)UniqueMysticismSkills.JINCHURIKI.get());
                     if (opt.isEmpty()) {
                        return EventResult.pass();
                     }

                     int cooldown = opt.get().isMastered(owner) ? CONFIG.tailedBeastCloakCooldownMastered : CONFIG.tailedBeastCloakCooldown;
                     opt.get().setCoolDown(cooldown, 1);
                     owner.level()
                        .playSound(null, owner.getX(), owner.getY(), owner.getZ(), TensuraSoundEvents.BUFF_DEACTIVATE, TensuraSkill.ABILITY_SOUND, 1.0F, 1.0F);
                  }
               }

               return EventResult.pass();
            }
         );
   }

   public static void restoreGameMode(ServerPlayer player) {
      player.setCamera(player);
      SkillAPI.getSkillsFrom(player).getSkill((ManasSkill)UniqueMysticismSkills.JINCHURIKI.get()).ifPresent(instance -> {
         CompoundTag tag = instance.getOrCreateTag();
         if (tag.contains("PriorGamemode")) {
            int gameModeId = tag.getInt("PriorGamemode");
            GameType previousGameMode = GameType.byId(gameModeId);
            player.setCamera(player);
            player.setGameMode(previousGameMode);
            tag.putBoolean("CloakActive", false);
            tag.remove("PriorGamemode");
            tag.remove("UncontrolledClone");
         }
      });
   }

   private static void removeUncontrolledClone(ServerPlayer player) {
      SkillAPI.getSkillsFrom(player).getSkill((ManasSkill)UniqueMysticismSkills.JINCHURIKI.get()).ifPresent(instance -> {
         CompoundTag tag = instance.getOrCreateTag();
         if (tag.hasUUID("UncontrolledClone")) {
            UUID cloneUUID = tag.getUUID("UncontrolledClone");

            for (ServerLevel level : player.server.getAllLevels()) {
               if (level.getEntity(cloneUUID) instanceof UncontrolledJinchurikiEntity clone) {
                  clone.discard();
                  break;
               }
            }

            tag.remove("UncontrolledClone");
         }
      });
   }
}
