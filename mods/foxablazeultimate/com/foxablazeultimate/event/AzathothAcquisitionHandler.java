package com.foxablazeultimate.event;

import com.foxablazeultimate.FoxAblazeUltimateMod;
import com.foxablazeultimate.ability.skill.azathoth.AzathothSkill;
import com.foxablazeultimate.config.AzathothConfig;
import com.foxablazeultimate.registry.FoxAblazeUltimateSkills;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.impl.SkillStorage;
import io.github.manasmods.manascore.storage.api.StorageHolder;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.TensuraSkillInstance;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;

@EventBusSubscriber(modid = "foxablazeultimate")
public final class AzathothAcquisitionHandler {
   private static final Set<UUID> ACQUIRING = new HashSet<>();

   private AzathothAcquisitionHandler() {
   }

   @SubscribeEvent
   public static void onPlayerTick(Post event) {
      if (event.getEntity() instanceof ServerPlayer player && player.isAlive() && !player.isSpectator()) {
         AzathothConfig.Settings config = AzathothConfig.settings();
         if (config.acquisitionEnabled && player.tickCount % Math.max(1, config.acquisitionCheckIntervalTicks) == 0 && ACQUIRING.add(player.getUUID())) {
            try {
               tryAcquire(player, config);
            } finally {
               ACQUIRING.remove(player.getUUID());
            }
         }
      }
   }

   public static Optional<ManasSkillInstance> prerequisite(ServerPlayer player, ManasSkill skill, boolean requireMastery) {
      return skill == null
         ? Optional.empty()
         : SkillAPI.getSkillsFrom(player)
            .getSkill(skill)
            .filter(instance -> Double.isFinite(instance.getMastery()) && instance.getMastery() >= 0.0)
            .filter(instance -> !instance.isTemporarySkill() && !instance.isSubInstance())
            .filter(instance -> !requireMastery || instance.isMastered(player));
   }

   public static Optional<ManasSkillInstance> requiredSkill(ServerPlayer player, ResourceLocation id, boolean requireMastery) {
      if (id == null) {
         return Optional.empty();
      }

      Optional<ManasSkillInstance> owned = prerequisite(player, (ManasSkill)SkillAPI.getSkillRegistry().get(id), requireMastery);
      return !owned.isPresent() && id.equals(FoxAblazeUltimateSkills.RAPHAEL.getId())
         ? prerequisite(player, (ManasSkill)FoxAblazeUltimateSkills.CIEL.get(), requireMastery)
         : owned;
   }

   private static void tryAcquire(ServerPlayer player, AzathothConfig.Settings config) {
      SkillStorage storage = SkillAPI.getSkillsFrom(player);
      AzathothSkill target = (AzathothSkill)FoxAblazeUltimateSkills.AZATHOTH.get();
      if (!storage.getSkill(target).isPresent() && UltimateSkillProtector.canAcquireUnique(player, target.getRegistryName())) {
         double cost = config.acquisitionMagiculeCost;
         if (Double.isFinite(cost) && !(cost < 0.0) && config.acquisitionRequiredSkills != null && !config.acquisitionRequiredSkills.isEmpty()) {
            for (String value : new ArrayList<>(config.acquisitionRequiredSkills)) {
               ResourceLocation id = value == null ? null : ResourceLocation.tryParse(value);
               if (id == null || id.equals(target.getRegistryName()) || requiredSkill(player, id, config.acquisitionRequiresMastery).isEmpty()) {
                  return;
               }
            }

            IExistence existence = TensuraStorages.getExistenceFrom(player);
            if (existence != null && Double.isFinite(existence.getMagicule()) && !(existence.getMagicule() < cost)) {
               TensuraSkillInstance evolved = new TensuraSkillInstance(target);
               evolved.setMastery(Math.clamp(config.acquisitionGrantMastery, 0, evolved.getMaxMastery()));
               evolved.getOrCreateTag().putBoolean("NoMagiculeCost", true);
               evolved.getOrCreateTag().putBoolean("GiftedInstance", true);
               existence.setMagicule(existence.getMagicule() - cost);

               try {
                  SkillHelper.learnSkill(player, evolved, -1, null);
               } catch (RuntimeException failure) {
                  FoxAblazeUltimateMod.LOGGER.warn("Azathoth acquisition failed for {}", player.getGameProfile().getName(), failure);
               } finally {
                  if (prerequisite(player, target, false).isEmpty()) {
                     existence.setMagicule(existence.getMagicule() + cost);
                  }

                  existence.markDirty();
                  storage.markDirty();
                  TensuraStorages.getAbilityFrom(player).markDirty();
                  ((StorageHolder)player).manasCore$sync(player);
               }

               return;
            } else {
               return;
            }
         }
      }
   }
}
