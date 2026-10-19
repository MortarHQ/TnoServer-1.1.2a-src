package org.crypticdev.ancient_artifacts;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.EntityEvent;
import dev.architectury.event.events.common.LootEvent;
import dev.architectury.event.events.common.PlayerEvent;
import dev.architectury.event.events.common.TickEvent;
import dev.architectury.event.events.common.EntityEvent.LivingHurt;
import dev.architectury.event.events.common.LootEvent.ModifyLootTable;
import dev.architectury.event.events.common.PlayerEvent.PlayerJoin;
import dev.architectury.event.events.common.TickEvent.Player;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.registry.skill.ExtraSkills;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.player.ITensuraPlayer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootPool.Builder;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import org.crypticdev.ancient_artifacts.artifact.ArtifactItem;
import org.crypticdev.ancient_artifacts.attachments.CombatTracker;
import org.crypticdev.ancient_artifacts.config.AncientArtifactsConfig;
import org.crypticdev.ancient_artifacts.config.LootInstanceConfig;
import org.crypticdev.ancient_artifacts.config.UnlockableEffectConfig;
import org.crypticdev.ancient_artifacts.platform.PlatformEquipment;
import org.crypticdev.ancient_artifacts.registry.ArtifactAttributesRegistry;
import org.crypticdev.ancient_artifacts.registry.ArtifactDataComponents;
import org.crypticdev.ancient_artifacts.registry.ArtifactItemTab;
import org.crypticdev.ancient_artifacts.registry.ArtifactItems;
import org.crypticdev.ancient_artifacts.registry.Artifacts;

public final class AncientArtifacts {
   public static final String MOD_ID = "ancient_artifacts";
   private static final Set<UUID> crouchingPlayers = new HashSet<>();
   public static AncientArtifactsConfig CONFIG;
   public static ArrayList<LootInstanceConfig> lootInstanceConfigs = new ArrayList<>();
   public static Map<String, UnlockableEffectConfig> unlockableEffectConfigs = Map.of();
   private static boolean initialized;

   private AncientArtifacts() {
   }

   public static void init() {
      if (!initialized) {
         ConfigRegistry.registerConfig(new AncientArtifactsConfig());
         CONFIG = (AncientArtifactsConfig)ConfigRegistry.getConfig(AncientArtifactsConfig.class);
         EffectConfigs.register();
         initialized = true;
         ArtifactItemTab.init();
         ArtifactDataComponents.init();
         ArtifactAttributesRegistry.init();
         ArtifactItems.init();
         PlayerEvent.PLAYER_JOIN.register((PlayerJoin)player -> {
            CombatTracker.setLastCombatTick(player, 0L);
            CombatTracker.setFirstCombatTick(player, 0L);
         });
         LootEvent.MODIFY_LOOT_TABLE
            .register(
               (ModifyLootTable)(key, context, builtin) -> {
                  for (LootInstanceConfig lootInstance : lootInstanceConfigs) {
                     if (key.location().toString().equals(lootInstance.loot_id)) {
                        Builder poolBuilder = LootPool.lootPool()
                           .setRolls(ConstantValue.exactly(1.0F))
                           .when(LootItemRandomChanceCondition.randomChance(lootInstance.chance));

                        for (int i = 0; i < lootInstance.itemWeights.size(); i++) {
                           String[] split = lootInstance.itemWeights.get(i).split(":");
                           String itemid = split[0] + ":" + split[1];
                           int weight = Integer.parseInt(split[2]);
                           poolBuilder = poolBuilder.add(
                              LootItem.lootTableItem((ItemLike)BuiltInRegistries.ITEM.get(ResourceLocation.bySeparator(itemid, ':'))).setWeight(weight)
                           );
                        }

                        context.addPool(poolBuilder);
                     }
                  }
               }
            );
         TickEvent.PLAYER_POST
            .register(
               (Player)player -> {
                  if (player instanceof ServerPlayer serverPlayer) {
                     UUID playerUuid = serverPlayer.getUUID();
                     if (serverPlayer.isCrouching()) {
                        if (!crouchingPlayers.contains(playerUuid)) {
                           crouchingPlayers.add(playerUuid);
                           ITensuraPlayer data = TensuraStorages.getPlayerDataFrom(serverPlayer);
                           if (SkillUtils.hasSkill(player, (ManasSkill)ExtraSkills.ALL_SEEING_EYE.get())
                              && SkillUtils.isSkillToggled(player, (ManasSkill)ExtraSkills.ALL_SEEING_EYE.get())
                              && PlatformEquipment.hasArtifact(player, Artifacts.SashOfQuickness)) {
                              ItemStack itemStack = PlatformEquipment.getArtifactStack(player, Artifacts.SashOfQuickness);
                              Item item = itemStack.getItem();
                              ArtifactItem artifactItem = (ArtifactItem)item;
                              if (artifactItem.getArtifact().artifactEffects().get(1).unlockCondition().apply(itemStack, player)) {
                                 if (data.isForcedThirdPerson()) {
                                    data.setForcedThirdPerson(false);
                                    data.markDirty();
                                 } else {
                                    data.setForcedThirdPerson(true);
                                    data.markDirty();
                                 }
                              }
                           }
                        }
                     } else {
                        crouchingPlayers.remove(playerUuid);
                     }
                  }
               }
            );
         EntityEvent.LIVING_HURT.register((LivingHurt)(entity, source, amount) -> {
            if (source.getEntity() instanceof net.minecraft.world.entity.player.Player player) {
               CombatTracker.enterCombat(player);
               int totalArmorPoints = entity.getArmorValue();
               if (totalArmorPoints > 0 && player instanceof IPlayerArmorTracker tracker) {
                  tracker.your_mod_id$setLastTargetArmor(totalArmorPoints);
               }
            }

            if (entity instanceof net.minecraft.world.entity.player.Player player) {
               CombatTracker.enterCombat(player);
               if (source.getEntity() instanceof LivingEntity livingAttacker) {
                  int totalArmorPoints = livingAttacker.getArmorValue();
                  if (totalArmorPoints > 0 && player instanceof IPlayerArmorTracker tracker) {
                     tracker.your_mod_id$setLastTargetArmor(totalArmorPoints);
                  }
               }
            }

            return EventResult.pass();
         });
      }
   }
}
