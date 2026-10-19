package com.foxablazeultimate.event;

import com.foxablazeultimate.config.HellSniperConfig;
import com.foxablazeultimate.registry.FoxAblazeUltimateSkills;
import com.foxablazeultimate.world.FoxAblazeGameRules;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.ability.TensuraSkillInstance;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.skill.UniqueSkills;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.storage.unique.ITrulyUnique;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;

@EventBusSubscriber(modid = "foxablazeultimate")
public final class HellSniperAcquisitionHandler {
   private HellSniperAcquisitionHandler() {
   }

   @SubscribeEvent
   public static void onPlayerTick(Post event) {
      if (event.getEntity() instanceof ServerPlayer player && !player.level().isClientSide()) {
         HellSniperConfig config = HellSniperConfig.get();
         if (config != null) {
            int interval = Math.max(1, config.HellSniper.acquisitionCheckIntervalTicks);
            if (player.tickCount % interval == 0) {
               tryAcquire(player, config.HellSniper);
            }
         }
      }
   }

   private static void tryAcquire(ServerPlayer player, HellSniperConfig.HellSniperSettings config) {
      if (!SkillUtils.hasSkill(player, (ManasSkill)FoxAblazeUltimateSkills.HELL_SNIPER.get())) {
         Optional<ManasSkillInstance> sniper = SkillAPI.getSkillsFrom(player)
            .getSkill((ManasSkill)UniqueSkills.SNIPER.get())
            .filter(instance -> !instance.isTemporarySkill())
            .filter(instance -> !instance.isSubInstance())
            .filter(instance -> instance.isMastered(player));
         if (!sniper.isEmpty()) {
            IExistence existence = TensuraStorages.getExistenceFrom(player);
            if (existence != null && (existence.isTrueDemonLord() || existence.isTrueHero())) {
               AttributeInstance maximumMagicule = player.getAttribute(TensuraAttributes.MAX_MAGICULE);
               if (TensuraAttributes.MAX_MAGICULE.value() instanceof RangedAttribute ranged && maximumMagicule != null) {
                  double minimum = ranged.getMinValue();
                  double baseCapacity = Math.max(0.0, maximumMagicule.getBaseValue());
                  double requiredCapacity = Math.max(0.0, config.acquisitionRequiredMaximumMagicule);
                  double capacityCost = Math.max(0.0, config.acquisitionMaximumMagiculeCost);
                  double currentMagicule = Math.max(0.0, existence.getMagicule());
                  if (!(currentMagicule < requiredCapacity)) {
                     if (!(baseCapacity < requiredCapacity) && !(maximumMagicule.getBaseValue() - minimum < capacityCost)) {
                        ResourceLocation skillId = FoxAblazeUltimateSkills.HELL_SNIPER.getId();
                        if (!player.level().getGameRules().getBoolean(FoxAblazeGameRules.HELL_SNIPER_TRULY_UNIQUE) || !isOwnedByOther(player, skillId)) {
                           TensuraSkillInstance hellSniper = new TensuraSkillInstance((ManasSkill)FoxAblazeUltimateSkills.HELL_SNIPER.get());
                           hellSniper.setMastery(Math.max(0, config.acquisitionGrantMastery));
                           hellSniper.getOrCreateTag().putBoolean("NoMagiculeCost", true);
                           hellSniper.getOrCreateTag().putBoolean("GiftedInstance", true);
                           hellSniper.markDirty();
                           if (SkillHelper.learnSkill(player, hellSniper)) {
                              maximumMagicule.setBaseValue(Math.max(minimum, maximumMagicule.getBaseValue() - capacityCost));
                              existence.setMagicule(Math.min(existence.getMagicule(), EnergyHelper.getMaxMagicule(player)));
                              existence.markDirty();
                              player.level()
                                 .playSound(
                                    null,
                                    player.getX(),
                                    player.getY(),
                                    player.getZ(),
                                    (SoundEvent)TensuraSoundEvents.GENERIC_CAST.get(),
                                    SoundSource.PLAYERS,
                                    1.0F,
                                    0.85F
                                 );
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private static boolean isOwnedByOther(ServerPlayer player, ResourceLocation skillId) {
      if (skillId == null) {
         return false;
      }

      MinecraftServer server = player.getServer();
      if (server == null) {
         return false;
      }

      ServerLevel overworld = server.overworld();
      if (overworld == null) {
         return false;
      }

      ITrulyUnique unique = TensuraStorages.getUniqueStorageFrom(overworld);
      UUID owner = unique.getOwner(skillId);
      return owner != null && !owner.equals(player.getUUID());
   }
}
