package org.crypticdev.stextras.handler;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.BlockEvent;
import dev.architectury.event.events.common.EntityEvent;
import dev.architectury.event.events.common.BlockEvent.Break;
import dev.architectury.event.events.common.EntityEvent.LivingDeath;
import dev.architectury.registry.registries.Registrar;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.RaceAPI;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.battlewill.Battlewill;
import io.github.manasmods.tensura.ability.magic.Element;
import io.github.manasmods.tensura.ability.magic.Magic;
import io.github.manasmods.tensura.ability.magic.spiritual.SpiritualMagic.SpiritLevel;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.event.TensuraEntityEvents;
import io.github.manasmods.tensura.event.TensuraSkillEvents;
import io.github.manasmods.tensura.event.TensuraSpiritEvents;
import io.github.manasmods.tensura.event.TensuraEntityEvents.AttributeBaseValueChangedEvent;
import io.github.manasmods.tensura.event.TensuraEntityEvents.LivingPostDamageEvent;
import io.github.manasmods.tensura.event.TensuraEntityEvents.NamingEvent;
import io.github.manasmods.tensura.event.TensuraEntityEvents.PossessionEvent;
import io.github.manasmods.tensura.event.TensuraEntityEvents.SpiritualHurtEvent;
import io.github.manasmods.tensura.event.TensuraSkillEvents.SkillPlunderEvent;
import io.github.manasmods.tensura.event.TensuraSpiritEvents.SpiritLevelUpdate;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.SubordinateHelper;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.storage.loot.LootParams.Builder;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import org.crypticdev.stextras.config.STPrestigeConfig;
import org.crypticdev.stextras.event.STPlayerEvents;
import org.crypticdev.stextras.net.STExtrasQuestSync;
import org.crypticdev.stextras.net.S2C.STExtrasBossKillsSync;
import org.crypticdev.stextras.quest.assignment.PlayerQuestAssignmentService;
import org.crypticdev.stextras.quest.assignment.RacePrestigeAssignmentService;
import org.crypticdev.stextras.quest.assignment.RepeatableQuestService;
import org.crypticdev.stextras.quest.definition.QuestCategory;
import org.crypticdev.stextras.quest.definition.QuestDefinition;
import org.crypticdev.stextras.quest.definition.QuestObjectiveType;
import org.crypticdev.stextras.quest.definition.QuestSubordinateMode;
import org.crypticdev.stextras.quest.definition.TensuraMasteryTypeIds;
import org.crypticdev.stextras.quest.definition.TensuraQuestTargetIds;
import org.crypticdev.stextras.quest.race.RaceMessages;
import org.crypticdev.stextras.quest.registry.QuestRegistry;
import org.crypticdev.stextras.quest.reward.QuestRewardService;
import org.crypticdev.stextras.quest.schedule.QuestScheduleEvents;
import org.crypticdev.stextras.storage.STExtarsStorage;
import org.crypticdev.stextras.storage.quest.ISTExtrasQuests;
import org.crypticdev.stextras.storage.quest.STExtrasQuestStorage;
import org.crypticdev.stextras.utils.EntityTagUtil;
import org.crypticdev.stextras.utils.PrestigeUtils;
import org.crypticdev.stextras.utils.TextUtil;
import org.jetbrains.annotations.Nullable;

public final class HandlePrestigeListeners {
   public static final STPrestigeConfig CONFIG_PRESTIGE = (STPrestigeConfig)ConfigRegistry.getConfig(STPrestigeConfig.class);
   private static final Set<QuestCategory> PRIORITY_CATEGORIES = Set.of(
      QuestCategory.DAILY, QuestCategory.WEEKLY, QuestCategory.REQUIRED, QuestCategory.RACE_PRESTIGE
   );

   private HandlePrestigeListeners() {
   }

   public static void init() {
      registerKill();
      registerMine();
      registerFarm();
      registerPrestigeBusListeners();
      registerTensuraListeners();
      registerOwnTest();
   }

   private static void registerKill() {
      EntityEvent.LIVING_DEATH.register((LivingDeath)(killed, source) -> {
         IExistence existence = TensuraStorages.getExistenceFrom(killed);
         boolean hadOwnerBeforeCleanup = killed.getTags().contains("stextras_had_owner");
         boolean hasPermanentOwner = existence.getPermanentOwner() != null;
         boolean wasOwned = hasPermanentOwner || hadOwnerBeforeCleanup;
         if (source.getEntity() instanceof LivingEntity actor) {
            Optional<HandlePrestigeListeners.QuestProgressSource> progressSource = questProgressSource(actor);
            if (progressSource.isEmpty()) {
               return EventResult.pass();
            }

            ResourceLocation id = BuiltInRegistries.ENTITY_TYPE.getKey(killed.getType());
            ServerPlayer player = progressSource.get().owner();
            if (!wasOwned || CONFIG_PRESTIGE.killOwnedEntities) {
               incrementMatching(progressSource.get(), QuestObjectiveType.KILL, id, 1, killed);
            }

            STExtrasBossKillsSync.sendTo(player, PrestigeUtils.getBossKillCount(player));
         }

         return EventResult.pass();
      });
   }

   private static void registerMine() {
      BlockEvent.BREAK.register((Break)(world, pos, state, player, xp) -> {
         if (world instanceof ServerLevel serverLevel && player instanceof ServerPlayer sp) {
            Map<ResourceLocation, Integer> targets = collectBlockBreakTargets(serverLevel, pos, state, sp);
            if (targets.isEmpty()) {
               return EventResult.pass();
            }

            if (isSilkTouchLikeEffect(serverLevel, pos, state, sp)) {
               return EventResult.pass();
            }

            for (java.util.Map.Entry<ResourceLocation, Integer> entry : targets.entrySet()) {
               incrementMatchingBlock(sp, QuestObjectiveType.MINE, entry.getKey(), entry.getValue(), state);
            }

            return EventResult.pass();
         } else {
            return EventResult.pass();
         }
      });
   }

   private static void registerFarm() {
      BlockEvent.BREAK.register((Break)(world, pos, state, player, xp) -> {
         if (world instanceof ServerLevel serverLevel && player instanceof ServerPlayer sp) {
            for (java.util.Map.Entry<ResourceLocation, Integer> entry : collectBlockBreakTargets(serverLevel, pos, state, sp).entrySet()) {
               incrementMatchingBlock(sp, QuestObjectiveType.FARM, entry.getKey(), entry.getValue(), state);
            }

            return EventResult.pass();
         } else {
            return EventResult.pass();
         }
      });
   }

   private static void registerTensuraListeners() {
      TensuraEntityEvents.NAMING_EVENT.register((NamingEvent)(entity, player, xpCost, moneyCost, namingType, name) -> {
         if (player instanceof ServerPlayer sp) {
            ResourceLocation type = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
            incrementMatching(sp, QuestObjectiveType.NAME, type, 1, entity);
         }

         return EventResult.pass();
      });
      TensuraEntityEvents.SPIRITUAL_HURT_EVENT.register((SpiritualHurtEvent)(entity, source, amount, damage, finalAmount, damageSource) -> {
         if (source instanceof LivingEntity actor) {
            ResourceLocation type = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
            int delta = Math.max(1, Math.round(amount));
            incrementMatching(actor, QuestObjectiveType.SPIRITUAL_DAMAGE, type, delta, entity);
         }

         return EventResult.pass();
      });
      TensuraEntityEvents.LIVING_POST_DAMAGE.register((LivingPostDamageEvent)(entity, source, amount) -> {
         if (source.getEntity() instanceof LivingEntity actor) {
            ResourceLocation type = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
            int delta = Math.max(1, Math.round(amount));
            incrementMatchingDamage(actor, type, delta, entity, source);
         }
      });
      TensuraEntityEvents.POSSESSION_EVENT.register((PossessionEvent)(target, possessor) -> {
         if (possessor instanceof ServerPlayer sp && target instanceof LivingEntity livingTarget) {
            ResourceLocation type = BuiltInRegistries.ENTITY_TYPE.getKey(livingTarget.getType());
            incrementMatching(sp, QuestObjectiveType.POSSESS, type, 1, livingTarget);
         }

         return EventResult.pass();
      });
      TensuraSkillEvents.SKILL_PLUNDER.register((SkillPlunderEvent)(target, source, copied, skill) -> {
         ServerPlayer player = null;
         if (source instanceof ServerPlayer sourcePlayer) {
            player = sourcePlayer;
         } else if (target instanceof ServerPlayer targetPlayer) {
            player = targetPlayer;
         }

         LivingEntity livingTarget = (player == source ? target : source) instanceof LivingEntity living ? living : null;
         ManasSkill plunderedSkill = skill != null && skill.isPresent() ? (ManasSkill)skill.get() : null;
         if (player != null) {
            progressPlunder(player, livingTarget, plunderedSkill);
         }

         return EventResult.pass();
      });
      TensuraSpiritEvents.SPIRIT_UPDATE.register((SpiritLevelUpdate)(entity, element, level) -> {
         if (entity instanceof ServerPlayer sp && element != null && element.isPresent() && level != null && level.isPresent()) {
            progressSpiritContract(sp, (Element)element.get(), (SpiritLevel)level.get());
         }

         return EventResult.pass();
      });
      TensuraEntityEvents.ATTRIBUTE_BASE_CHANGE_EVENT.register((AttributeBaseValueChangedEvent)(entity, attribute, before, after) -> {
         if (entity instanceof LivingEntity living && living.getServer() != null) {
            living.getServer().execute(() -> checkEpReach(living));
         }

         return EventResult.pass();
      });
   }

   private static void registerOwnTest() {
      QuestScheduleEvents.SYNC.register((QuestScheduleEvents.Sync)(server, snapshot, isMaster) -> server.getPlayerList().getPlayers().forEach(player -> {}));
   }

   private static void registerPrestigeBusListeners() {
      STPlayerEvents.CRAFT.register((STPlayerEvents.CraftCallback)(player, result) -> {
         if (player instanceof ServerPlayer sp) {
            ResourceLocation id = BuiltInRegistries.ITEM.getKey(result.getItem());
            incrementMatching(sp, QuestObjectiveType.CRAFT, id, result.getCount(), null);
         }
      });
      STPlayerEvents.COOK.register((STPlayerEvents.CookCallback)(player, result) -> {
         if (player instanceof ServerPlayer sp) {
            ResourceLocation id = BuiltInRegistries.ITEM.getKey(result.getItem());
            incrementMatching(sp, QuestObjectiveType.COOK, id, result.getCount(), null);
         }
      });
      STPlayerEvents.TRADE.register((STPlayerEvents.TradeCallback)(player, offer, result) -> {
         if (player instanceof ServerPlayer sp) {
            ItemStack out = result != null ? result : (offer != null ? offer.getResult() : ItemStack.EMPTY);
            if (!out.isEmpty()) {
               ResourceLocation id = BuiltInRegistries.ITEM.getKey(out.getItem());
               incrementMatching(sp, QuestObjectiveType.TRADE, id, out.getCount(), null);
            }
         }
      });
      STPlayerEvents.SMITH.register((STPlayerEvents.SmithCallback)(player, result) -> {
         if (player instanceof ServerPlayer sp) {
            ResourceLocation id = BuiltInRegistries.ITEM.getKey(result.getItem());
            incrementMatching(sp, QuestObjectiveType.SMITH_MINECRAFT, id, result.getCount(), null);
         }
      });
      STPlayerEvents.BREED.register((STPlayerEvents.BreedCallback)(player, baby, a, b) -> {
         if (player instanceof ServerPlayer sp) {
            ResourceLocation id = BuiltInRegistries.ENTITY_TYPE.getKey(baby.getType());
            incrementMatching(sp, QuestObjectiveType.BREED, id, 1, baby);
         }
      });
      STPlayerEvents.CONSUME.register((STPlayerEvents.ConsumeCallback)(actor, food) -> {
         if (food != null && !food.isEmpty()) {
            ResourceLocation id = BuiltInRegistries.ITEM.getKey(food.getItem());
            incrementMatching(actor, QuestObjectiveType.CONSUME, id, food.getCount(), null);
         }
      });
      STPlayerEvents.ENCHANT.register((STPlayerEvents.EnchantCallback)(player, stack) -> {
         if (stack != null && !stack.isEmpty()) {
            progressEnchant(player, stack);
         }
      });
      STPlayerEvents.MINING_STATION.register((STPlayerEvents.MiningStationCallback)(player, stack) -> {
         if (player instanceof ServerPlayer sp && stack != null && !stack.isEmpty()) {
            ResourceLocation id = BuiltInRegistries.ITEM.getKey(stack.getItem());
            incrementMatching(sp, QuestObjectiveType.MINING_STATION, id, stack.getCount(), null);
         }
      });
      STPlayerEvents.WOODCUT.register((STPlayerEvents.WoodcutCallback)(player, stack) -> {
         if (player instanceof ServerPlayer sp && stack != null && !stack.isEmpty()) {
            ResourceLocation id = BuiltInRegistries.ITEM.getKey(stack.getItem());
            incrementMatching(sp, QuestObjectiveType.WOODCUT, id, stack.getCount(), null);
         }
      });
      STPlayerEvents.SPELL_BINDING.register((STPlayerEvents.SpellBindingCallback)(player, skillId) -> {
         if (player instanceof ServerPlayer sp && skillId != null) {
            incrementMatching(sp, QuestObjectiveType.SPELL_BINDING, skillId, 1, null);
         }
      });
      STPlayerEvents.EQUIP_EVOLVE.register((STPlayerEvents.EquipEvolveCallback)(actor, stack) -> {
         if (stack != null && !stack.isEmpty()) {
            ResourceLocation id = BuiltInRegistries.ITEM.getKey(stack.getItem());
            incrementMatching(actor, QuestObjectiveType.EQUIP_EVOLVE, id, 1, null);
         }
      });
      STPlayerEvents.MASTERY_POINT
         .register((STPlayerEvents.MasteryPointCallback)(player, skillInstance, before, after) -> progressMasteredSkillQuests(player, skillInstance));
      STPlayerEvents.SKILL_MASTERED
         .register((STPlayerEvents.SkillMasteredCallback)(player, skillInstance) -> progressMasteredSkillQuests(player, skillInstance));
      STPlayerEvents.SKILL_INTERACTION
         .register((STPlayerEvents.SkillInteractionCallback)(player, skillInstance, interactionType) -> progressMasteredSkillQuests(player, skillInstance));
      STPlayerEvents.TR_SMITH.register((STPlayerEvents.TRSmithCallback)(player, result) -> {
         if (player instanceof ServerPlayer sp) {
            ResourceLocation id = BuiltInRegistries.ITEM.getKey(result.getItem());
            incrementMatching(sp, QuestObjectiveType.SMITH_TENSURA, id, result.getCount(), null);
         }
      });
      STPlayerEvents.RACE_EVOLVE.register((STPlayerEvents.RaceEvolveCallback)(player, race) -> {
         if (player instanceof ServerPlayer sp) {
            if (STExtarsStorage.Quests.get(sp).hasCompletedRace(race.getRegistryName())) {
               return;
            }

            RaceMessages.send(player, race.getRegistryName(), RaceMessages.Kind.LORE);
            PlayerQuestAssignmentService.ensureRacePrestigeForCurrentRace(sp, true);
         }
      });
   }

   private static void progressMasteredSkillQuests(LivingEntity actor, ManasSkillInstance skillInstance) {
      if (skillInstance != null && skillInstance.isMastered(actor)) {
         ManasSkill masteredSkill = skillInstance.getSkill();
         ResourceLocation specificSkillId = SkillAPI.getSkillRegistry().getId(masteredSkill);
         if (specificSkillId != null) {
            Optional<HandlePrestigeListeners.QuestProgressSource> progressSource = questProgressSource(actor);
            if (!progressSource.isEmpty()) {
               ServerPlayer player = progressSource.get().owner();
               STExtrasQuestStorage store = ((ISTExtrasQuests)player).stextras$getQuests();
               if (hasRelevantMasteryTarget(player, store, specificSkillId, masteredSkill)) {
                  store = prepareQuestProgress(player);
                  String uniqueKey = specificSkillId.toString();
                  incrementMatchingPrepared(progressSource.get(), store, QuestObjectiveType.MASTER, specificSkillId, 1, actor, null, null);
                  if (masteredSkill instanceof Skill tensuraSkill) {
                     incrementMatchingPrepared(
                        progressSource.get(),
                        store,
                        QuestObjectiveType.MASTER_TYPE,
                        TensuraMasteryTypeIds.skillTypeId(tensuraSkill.getType()),
                        1,
                        actor,
                        uniqueKey,
                        null
                     );
                  } else if (masteredSkill instanceof Battlewill) {
                     incrementMatchingPrepared(
                        progressSource.get(), store, QuestObjectiveType.MASTER_TYPE, TensuraMasteryTypeIds.battlewillId(), 1, actor, uniqueKey, null
                     );
                  }

                  if (masteredSkill instanceof Magic magic) {
                     incrementMatchingPrepared(
                        progressSource.get(),
                        store,
                        QuestObjectiveType.MASTER_TYPE_MAGIC,
                        TensuraMasteryTypeIds.magicTypeId(magic.getType()),
                        1,
                        actor,
                        uniqueKey,
                        null
                     );

                     for (ResourceLocation subtypeId : TensuraMasteryTypeIds.magicSubtypeIds(magic)) {
                        incrementMatchingPrepared(progressSource.get(), store, QuestObjectiveType.MASTER_SUBTYPE_MAGIC, subtypeId, 1, actor, uniqueKey, null);
                     }
                  }

                  syncQuestStore(player, store);
               }
            }
         }
      }
   }

   private static boolean hasRelevantMasteryTarget(ServerPlayer player, STExtrasQuestStorage store, ResourceLocation specificSkillId, Object masteredSkill) {
      boolean activeMatch = store.activeAll()
         .stream()
         .map(QuestRegistry.INSTANCE::byId)
         .anyMatch(quest -> matchesMasteredSkillQuest(quest, specificSkillId, masteredSkill));
      return activeMatch || hasRelevantRacePrestigeMasteryTarget(player, store, specificSkillId, masteredSkill);
   }

   private static boolean hasRelevantRacePrestigeMasteryTarget(
      ServerPlayer player, STExtrasQuestStorage store, ResourceLocation specificSkillId, Object masteredSkill
   ) {
      Optional<ResourceLocation> maybeRaceId = currentRaceId(player);
      if (!maybeRaceId.isEmpty() && !store.hasCompletedRace(maybeRaceId.get())) {
         ResourceLocation raceId = maybeRaceId.get();
         return QuestRegistry.INSTANCE
            .byCategory(QuestCategory.RACE_PRESTIGE)
            .stream()
            .filter(quest -> raceId.equals(quest.race))
            .anyMatch(quest -> matchesMasteredSkillQuest(quest, specificSkillId, masteredSkill));
      } else {
         return false;
      }
   }

   private static boolean matchesMasteredSkillQuest(@Nullable QuestDefinition quest, ResourceLocation specificSkillId, Object masteredSkill) {
      if (quest == null) {
         return false;
      } else if (matchesQuest(quest, QuestObjectiveType.MASTER, specificSkillId, null, null)) {
         return true;
      } else if (masteredSkill instanceof Skill tensuraSkill
         && matchesQuest(quest, QuestObjectiveType.MASTER_TYPE, TensuraMasteryTypeIds.skillTypeId(tensuraSkill.getType()), null, null)) {
         return true;
      } else {
         if (masteredSkill instanceof Battlewill && matchesQuest(quest, QuestObjectiveType.MASTER_TYPE, TensuraMasteryTypeIds.battlewillId(), null, null)) {
            return true;
         }

         if (masteredSkill instanceof Magic magic) {
            if (matchesQuest(quest, QuestObjectiveType.MASTER_TYPE_MAGIC, TensuraMasteryTypeIds.magicTypeId(magic.getType()), null, null)) {
               return true;
            }

            for (ResourceLocation subtypeId : TensuraMasteryTypeIds.magicSubtypeIds(magic)) {
               if (matchesQuest(quest, QuestObjectiveType.MASTER_SUBTYPE_MAGIC, subtypeId, null, null)) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   private static void incrementMatching(LivingEntity actor, QuestObjectiveType creator, ResourceLocation type, int amount, @Nullable LivingEntity target) {
      incrementMatching(actor, creator, type, amount, target, null);
   }

   private static void incrementMatching(
      HandlePrestigeListeners.QuestProgressSource progressSource, QuestObjectiveType creator, ResourceLocation type, int amount, @Nullable LivingEntity target
   ) {
      incrementMatchingTypes(progressSource, creator, List.of(type), amount, HandlePrestigeListeners.QuestProgressContext.entity(target), null);
   }

   private static void incrementMatchingBlock(
      ServerPlayer player, QuestObjectiveType creator, ResourceLocation type, int amount, @Nullable BlockState blockState
   ) {
      incrementMatchingTypes(
         HandlePrestigeListeners.QuestProgressSource.owner(player),
         creator,
         List.of(type),
         amount,
         HandlePrestigeListeners.QuestProgressContext.block(blockState),
         null
      );
   }

   private static void incrementMatchingDamage(LivingEntity actor, ResourceLocation type, int amount, LivingEntity target, DamageSource source) {
      incrementMatchingTypes(actor, QuestObjectiveType.DAMAGE, List.of(type), amount, HandlePrestigeListeners.QuestProgressContext.damage(target, source), null);
   }

   private static void incrementMatching(
      LivingEntity actor, QuestObjectiveType creator, ResourceLocation type, int amount, @Nullable LivingEntity target, @Nullable String uniqueKey
   ) {
      incrementMatchingTypes(actor, creator, List.of(type), amount, HandlePrestigeListeners.QuestProgressContext.entity(target), uniqueKey);
   }

   private static void incrementMatching(
      LivingEntity actor,
      QuestObjectiveType creator,
      ResourceLocation type,
      int amount,
      @Nullable LivingEntity target,
      @Nullable String uniqueKey,
      @Nullable BlockState blockState
   ) {
      incrementMatchingTypes(
         actor, creator, List.of(type), amount, new HandlePrestigeListeners.QuestProgressContext(target, blockState, null, null, null, Map.of()), uniqueKey
      );
   }

   private static void incrementMatchingTypes(
      LivingEntity actor,
      QuestObjectiveType creator,
      List<ResourceLocation> types,
      int amount,
      HandlePrestigeListeners.QuestProgressContext context,
      @Nullable String uniqueKey
   ) {
      Optional<HandlePrestigeListeners.QuestProgressSource> progressSource = questProgressSource(actor);
      if (!progressSource.isEmpty()) {
         incrementMatchingTypes(progressSource.get(), creator, types, amount, context, uniqueKey);
      }
   }

   private static void incrementMatchingTypes(
      HandlePrestigeListeners.QuestProgressSource progressSource,
      QuestObjectiveType creator,
      List<ResourceLocation> types,
      int amount,
      HandlePrestigeListeners.QuestProgressContext context,
      @Nullable String uniqueKey
   ) {
      ServerPlayer player = progressSource.owner();
      STExtrasQuestStorage store = prepareQuestProgress(player);
      incrementMatchingPrepared(progressSource, store, creator, types, amount, context, uniqueKey);
      syncQuestStore(player, store);
   }

   private static void progressPlunder(ServerPlayer player, @Nullable LivingEntity target, @Nullable ManasSkill skill) {
      List<ResourceLocation> types = new ArrayList<>();
      if (target != null) {
         types.add(BuiltInRegistries.ENTITY_TYPE.getKey(target.getType()));
      }

      if (skill != null) {
         ResourceLocation skillId = SkillAPI.getSkillRegistry().getId(skill);
         if (skillId != null) {
            types.add(skillId);
         }
      }

      if (!types.isEmpty()) {
         String uniqueKey = "plunder:" + player.level().getGameTime() + ":" + types;
         incrementMatchingTypes(player, QuestObjectiveType.PLUNDER, types, 1, HandlePrestigeListeners.QuestProgressContext.entity(target), uniqueKey);
      }
   }

   private static void progressSpiritContract(ServerPlayer player, Element element, SpiritLevel level) {
      ResourceLocation elementId = TensuraQuestTargetIds.elementId(element);
      ResourceLocation levelId = TensuraQuestTargetIds.spiritLevelId(level);
      incrementMatchingTypes(
         player, QuestObjectiveType.SPIRIT_CONTRACT, List.of(elementId, levelId), 1, HandlePrestigeListeners.QuestProgressContext.spirit(levelId), null
      );
   }

   private static void progressEnchant(ServerPlayer player, ItemStack stack) {
      ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(stack.getItem());
      Map<ResourceLocation, Integer> enchantments = enchantmentLevels(stack);
      List<ResourceLocation> types = new ArrayList<>(1 + enchantments.size());
      if (itemId != null) {
         types.add(itemId);
      }

      types.addAll(enchantments.keySet());
      if (!types.isEmpty()) {
         incrementMatchingTypes(
            player, QuestObjectiveType.ENCHANT, types, stack.getCount(), HandlePrestigeListeners.QuestProgressContext.enchant(itemId, enchantments), null
         );
      }
   }

   private static Map<ResourceLocation, Integer> enchantmentLevels(ItemStack stack) {
      Map<ResourceLocation, Integer> levels = new LinkedHashMap<>();
      addEnchantmentLevels(levels, (ItemEnchantments)stack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY));
      addEnchantmentLevels(levels, (ItemEnchantments)stack.getOrDefault(DataComponents.STORED_ENCHANTMENTS, ItemEnchantments.EMPTY));
      return levels;
   }

   private static void addEnchantmentLevels(Map<ResourceLocation, Integer> levels, ItemEnchantments enchantments) {
      if (!enchantments.isEmpty()) {
         for (Entry<Holder<Enchantment>> entry : enchantments.entrySet()) {
            Holder<Enchantment> enchantment = (Holder<Enchantment>)entry.getKey();
            int level = entry.getIntValue();
            enchantment.unwrapKey().<ResourceLocation>map(ResourceKey::location).ifPresent(id -> levels.merge(id, level, Math::max));
         }
      }
   }

   private static void checkEpReach(LivingEntity actor) {
      Optional<HandlePrestigeListeners.QuestProgressSource> progressSource = questProgressSource(actor);
      if (!progressSource.isEmpty()) {
         double maxEp = Math.max(0.0, EnergyHelper.getMaxEP(actor));
         int threshold = maxEp >= 2.147483647E9 ? Integer.MAX_VALUE : (int)Math.floor(maxEp);
         completeMatchingThreshold(progressSource.get(), QuestObjectiveType.EP_REACH, threshold);
      }
   }

   private static STExtrasQuestStorage prepareQuestProgress(ServerPlayer player) {
      RepeatableQuestService.ensureRepeatablesApplied(player);
      RacePrestigeAssignmentService.ensureForCurrentRace(player, false, false);
      return ((ISTExtrasQuests)player).stextras$getQuests();
   }

   private static void incrementMatchingPrepared(
      HandlePrestigeListeners.QuestProgressSource progressSource,
      STExtrasQuestStorage store,
      QuestObjectiveType creator,
      ResourceLocation type,
      int amount,
      @Nullable LivingEntity target,
      @Nullable String uniqueKey,
      @Nullable BlockState blockState
   ) {
      incrementMatchingPrepared(
         progressSource,
         store,
         creator,
         List.of(type),
         amount,
         new HandlePrestigeListeners.QuestProgressContext(target, blockState, null, null, null, Map.of()),
         uniqueKey
      );
   }

   private static void incrementMatchingPrepared(
      HandlePrestigeListeners.QuestProgressSource progressSource,
      STExtrasQuestStorage store,
      QuestObjectiveType creator,
      List<ResourceLocation> types,
      int amount,
      HandlePrestigeListeners.QuestProgressContext context,
      @Nullable String uniqueKey
   ) {
      ServerPlayer player = progressSource.owner();
      startMatchingRacePrestigeQuests(progressSource, store, creator, types, amount, context);
      List<HandlePrestigeListeners.QuestProgressTarget> targets = store.activeAll()
         .stream()
         .map(qidx -> new HandlePrestigeListeners.QuestProgressTarget(qidx, QuestRegistry.INSTANCE.byId(qidx)))
         .filter(targetQuestx -> matchesQuest(targetQuestx.quest(), creator, types, context))
         .filter(targetQuestx -> canQuestProgressFrom(targetQuestx.quest(), progressSource, creator))
         .filter(targetQuestx -> uniqueKey == null || !store.hasCounted(targetQuestx.id(), uniqueKey))
         .toList();
      if (CONFIG_PRESTIGE.questPriority && targets.stream().anyMatch(targetQuestx -> PRIORITY_CATEGORIES.contains(targetQuestx.quest().category))) {
         targets = targets.stream().filter(targetQuestx -> targetQuestx.quest().category != QuestCategory.REPEATABLE).toList();
      }

      for (HandlePrestigeListeners.QuestProgressTarget targetQuest : targets) {
         ResourceLocation qid = targetQuest.id();
         QuestDefinition q = targetQuest.quest();
         if (uniqueKey != null) {
            store.addCounted(qid, uniqueKey);
         }

         boolean completed;
         if (q.noAmountRequired()) {
            store.markCompleted(qid);
            completed = true;
         } else {
            int req = Math.max(1, q.amount);
            int delta = Math.max(0, amount);
            completed = store.addProgressAndMaybeComplete(qid, delta, req);
         }

         if (completed) {
            finishCompletedQuest(player, store, qid, q, progressSource.subordinateRewardTarget());
         }
      }
   }

   private static void completeMatchingThreshold(HandlePrestigeListeners.QuestProgressSource progressSource, QuestObjectiveType creator, int threshold) {
      ServerPlayer player = progressSource.owner();
      STExtrasQuestStorage store = prepareQuestProgress(player);
      HandlePrestigeListeners.QuestProgressContext context = HandlePrestigeListeners.QuestProgressContext.empty();
      List<ResourceLocation> types = List.of();
      startMatchingRacePrestigeQuests(progressSource, store, creator, types, threshold, context);
      List<HandlePrestigeListeners.QuestProgressTarget> targets = store.activeAll()
         .stream()
         .map(qidx -> new HandlePrestigeListeners.QuestProgressTarget(qidx, QuestRegistry.INSTANCE.byId(qidx)))
         .filter(targetQuestx -> matchesQuest(targetQuestx.quest(), creator, types, context))
         .filter(targetQuestx -> canQuestProgressFrom(targetQuestx.quest(), progressSource, creator))
         .toList();
      if (CONFIG_PRESTIGE.questPriority && targets.stream().anyMatch(targetQuestx -> PRIORITY_CATEGORIES.contains(targetQuestx.quest().category))) {
         targets = targets.stream().filter(targetQuestx -> targetQuestx.quest().category != QuestCategory.REPEATABLE).toList();
      }

      for (HandlePrestigeListeners.QuestProgressTarget targetQuest : targets) {
         ResourceLocation qid = targetQuest.id();
         QuestDefinition q = targetQuest.quest();
         int req = Math.max(1, q.amount);
         if (threshold >= req) {
            store.markCompleted(qid);
            finishCompletedQuest(player, store, qid, q, progressSource.subordinateRewardTarget());
         } else if (threshold > store.getProgress(qid)) {
            store.setProgress(qid, threshold);
         }
      }

      syncQuestStore(player, store);
   }

   private static void finishCompletedQuest(
      ServerPlayer player, STExtrasQuestStorage store, ResourceLocation qid, QuestDefinition q, @Nullable LivingEntity subordinateRewardTarget
   ) {
      STPlayerEvents.fireQuestCompleted(player, qid);
      MutableComponent base = Component.translatable("stextras.quest.completed", new Object[]{q.name});
      String resolved = base.getString();
      if (CONFIG_PRESTIGE.questsUseSmallCaps) {
         resolved = TextUtil.toSmallCaps(resolved);
      }

      player.sendSystemMessage(Component.literal(resolved));
      QuestRewardService.grant(player, q);
      if (subordinateRewardTarget != null) {
         QuestRewardService.grantSubordinate(player, subordinateRewardTarget, q);
      }

      if (q.category == QuestCategory.RACE_PRESTIGE) {
         Optional<ResourceLocation> currentRaceId = currentRaceId(player);
         if (currentRaceId.isPresent() && currentRaceId.get().equals(q.race)) {
            ResourceLocation myRace = currentRaceId.get();
            List<QuestDefinition> allRaceQuests = QuestRegistry.INSTANCE
               .byCategory(QuestCategory.RACE_PRESTIGE)
               .stream()
               .filter(rq -> myRace.equals(rq.race))
               .toList();
            boolean allDone = allRaceQuests.stream().allMatch(rq -> store.completed(QuestCategory.RACE_PRESTIGE).contains(rq.id));
            if (allDone) {
               RaceMessages.send(player, myRace, RaceMessages.Kind.COMPLETE);
            }
         }
      }
   }

   private static void syncQuestStore(ServerPlayer player, STExtrasQuestStorage store) {
      if (store.isDirty()) {
         STExtrasQuestSync.sendTo(player, store.toNet());
         store.clearDirty();
      }
   }

   private static void startMatchingRacePrestigeQuests(
      HandlePrestigeListeners.QuestProgressSource progressSource,
      STExtrasQuestStorage store,
      QuestObjectiveType creator,
      List<ResourceLocation> types,
      int amount,
      HandlePrestigeListeners.QuestProgressContext context
   ) {
      ServerPlayer player = progressSource.owner();
      Optional<ResourceLocation> currentRaceId = currentRaceId(player);
      if (!currentRaceId.isEmpty()) {
         ResourceLocation raceId = currentRaceId.get();
         if (!store.hasCompletedRace(raceId)) {
            List<QuestDefinition> racePool = QuestRegistry.INSTANCE
               .byCategory(QuestCategory.RACE_PRESTIGE)
               .stream()
               .filter(quest -> raceId.equals(quest.race))
               .toList();
            List<QuestDefinition> matching = racePool.stream()
               .filter(quest -> matchesQuest(quest, creator, types, context))
               .filter(quest -> canQuestProgressFrom(quest, progressSource, creator))
               .toList();
            if (!matching.isEmpty()) {
               if (CONFIG_PRESTIGE.ImmediateRaceQuests) {
                  if (!RaceAPI.getRaceFrom(player).getRace().map(race -> race.getNextEvolutions(player).isEmpty()).orElse(false)) {
                     return;
                  }
               } else {
                  Set<ResourceLocation> matchingIds = matching.stream().map(quest -> quest.id).collect(Collectors.toSet());
                  RacePrestigeAssignmentService.applyRaceQuests(player, store, racePool, matchingIds, amount);
               }

               if (!store.hasStartedRaceMessage(raceId)) {
                  RaceMessages.send(player, raceId, RaceMessages.Kind.START);
                  store.markStartedRaceMessage(raceId);
               }
            }
         }
      }
   }

   private static boolean canQuestProgressFrom(
      @Nullable QuestDefinition quest, HandlePrestigeListeners.QuestProgressSource progressSource, QuestObjectiveType creator
   ) {
      if (quest == null) {
         return false;
      }

      QuestSubordinateMode mode = quest.subordinate == null ? QuestSubordinateMode.NONE : quest.subordinate;
      return !progressSource.subordinate() ? mode != QuestSubordinateMode.ONLY : mode != QuestSubordinateMode.NONE && QuestSubordinateMode.supports(creator);
   }

   private static Optional<HandlePrestigeListeners.QuestProgressSource> questProgressSource(LivingEntity actor) {
      if (actor instanceof ServerPlayer player) {
         return Optional.of(HandlePrestigeListeners.QuestProgressSource.owner(player));
      } else {
         LivingEntity owner;
         try {
            owner = SubordinateHelper.getSubordinateOwner(actor);
         } catch (Throwable ignored) {
            owner = null;
         }

         return owner instanceof ServerPlayer player ? Optional.of(new HandlePrestigeListeners.QuestProgressSource(player, actor, true)) : Optional.empty();
      }
   }

   private static boolean matchesQuest(
      @Nullable QuestDefinition q, QuestObjectiveType creator, @Nullable ResourceLocation type, @Nullable LivingEntity target, @Nullable BlockState blockState
   ) {
      return matchesQuest(
         q, creator, type == null ? List.of() : List.of(type), new HandlePrestigeListeners.QuestProgressContext(target, blockState, null, null, null, Map.of())
      );
   }

   private static boolean matchesQuest(
      @Nullable QuestDefinition q, QuestObjectiveType creator, List<ResourceLocation> types, HandlePrestigeListeners.QuestProgressContext context
   ) {
      if (q == null || q.creator != creator) {
         return false;
      }

      if (q.creator == QuestObjectiveType.DAMAGE && !matchesDamageType(q, context.damageSource())) {
         return false;
      }

      if (q.creator == QuestObjectiveType.SPIRIT_CONTRACT && q.spiritLevel != null) {
         if (context.spiritLevel() == null) {
            return false;
         }

         if (!isMinecraftAny(q.spiritLevel) && !q.spiritLevel.equals(context.spiritLevel())) {
            return false;
         }
      }

      if (q.creator == QuestObjectiveType.ENCHANT && !matchesEnchantFilters(q, context)) {
         return false;
      }

      LivingEntity target = context.target();
      if (target != null) {
         if (q.mobCategory != null && target.getType().getCategory() != q.mobCategory) {
            return false;
         }

         if (!q.tags.isEmpty()) {
            for (String tagExpr : q.tags) {
               if (!matchesEntityTag(target, tagExpr)) {
                  return false;
               }
            }
         }
      }

      return !matchesQuestTypes(q, types) ? false : matchesBlockStage(q, context.blockState());
   }

   private static boolean matchesQuestTypes(QuestDefinition quest, List<ResourceLocation> types) {
      if (quest.types.isEmpty()) {
         return true;
      }

      if (quest.types.stream().anyMatch(HandlePrestigeListeners::isMinecraftAny)) {
         return true;
      }

      for (ResourceLocation type : types) {
         if (quest.types.contains(type)) {
            return true;
         }
      }

      return false;
   }

   private static boolean matchesEnchantFilters(QuestDefinition quest, HandlePrestigeListeners.QuestProgressContext context) {
      if (!quest.enchantItems.isEmpty()) {
         ResourceLocation item = context.enchantItem();
         if (item == null) {
            return false;
         }

         if (quest.enchantItems.stream().noneMatch(HandlePrestigeListeners::isMinecraftAny) && !quest.enchantItems.contains(item)) {
            return false;
         }
      }

      if (!quest.enchantments.isEmpty()) {
         Map<ResourceLocation, Integer> enchantments = context.enchantments();
         if (enchantments.isEmpty()) {
            return false;
         }

         if (quest.enchantments.stream().noneMatch(HandlePrestigeListeners::isMinecraftAny) && quest.enchantments.stream().noneMatch(enchantments::containsKey)
            )
          {
            return false;
         }
      }

      return matchesEnchantLevel(quest, context.enchantments());
   }

   private static boolean matchesEnchantLevel(QuestDefinition quest, Map<ResourceLocation, Integer> enchantments) {
      if (quest.minEnchantLevel < 0 && quest.maxEnchantLevel < 0) {
         return true;
      }

      if (enchantments.isEmpty()) {
         return false;
      }

      List<ResourceLocation> candidates = new ArrayList<>();
      if (!quest.enchantments.isEmpty()) {
         if (quest.enchantments.stream().anyMatch(HandlePrestigeListeners::isMinecraftAny)) {
            candidates.addAll(enchantments.keySet());
         } else {
            for (ResourceLocation enchantment : quest.enchantments) {
               if (enchantments.containsKey(enchantment)) {
                  candidates.add(enchantment);
               }
            }
         }
      } else if (!quest.types.isEmpty()) {
         for (ResourceLocation type : quest.types) {
            if (enchantments.containsKey(type)) {
               candidates.add(type);
            }
         }
      }

      if (candidates.isEmpty()) {
         candidates.addAll(enchantments.keySet());
      }

      int min = quest.minEnchantLevel >= 0 ? quest.minEnchantLevel : 1;
      int max = quest.maxEnchantLevel >= 0 ? quest.maxEnchantLevel : Integer.MAX_VALUE;

      for (ResourceLocation enchantment : candidates) {
         int level = enchantments.getOrDefault(enchantment, 0);
         if (level >= min && level <= max) {
            return true;
         }
      }

      return false;
   }

   private static boolean matchesDamageType(QuestDefinition quest, @Nullable DamageSource source) {
      if (quest.damageTypes.isEmpty() && quest.damageTypeTags.isEmpty()) {
         return true;
      }

      if (source == null) {
         return false;
      }

      for (ResourceLocation damageType : quest.damageTypes) {
         if (source.is(ResourceKey.create(Registries.DAMAGE_TYPE, damageType))) {
            return true;
         }
      }

      for (ResourceLocation damageTypeTag : quest.damageTypeTags) {
         if (source.is(TagKey.create(Registries.DAMAGE_TYPE, damageTypeTag))) {
            return true;
         }
      }

      return false;
   }

   private static boolean isMinecraftAny(ResourceLocation id) {
      return "minecraft".equals(id.getNamespace()) && "any".equals(id.getPath());
   }

   private static boolean matchesEntityTag(LivingEntity entity, String expr) {
      try {
         String[] kv = expr.split(":", 2);
         if (kv.length != 2) {
            return false;
         }

         String key = kv[0].trim();
         String val = kv[1].trim();
         EntityTagUtil.TagValue tagValue = EntityTagUtil.getTagValue(entity, key).orElse(null);
         return tagValue == null ? false : tagValue.asString().map(v -> v.equalsIgnoreCase(val)).orElse(false);
      } catch (Exception e) {
         return false;
      }
   }

   private static Optional<ResourceLocation> currentRaceId(ServerPlayer player) {
      Optional<ManasRaceInstance> opt = RaceAPI.getRaceFrom(player).getRace();
      if (opt.isEmpty()) {
         return Optional.empty();
      }

      Registrar<ManasRace> reg = RaceAPI.getRaceRegistry();
      return Optional.ofNullable(reg.getId(opt.get().getRace()));
   }

   private static Map<ResourceLocation, Integer> collectBlockBreakTargets(ServerLevel world, BlockPos pos, BlockState state, ServerPlayer player) {
      Map<ResourceLocation, Integer> targets = new LinkedHashMap<>();
      ResourceLocation blockId = BuiltInRegistries.BLOCK.getKey(state.getBlock());
      targets.put(blockId, 1);

      for (ItemStack drop : Block.getDrops(state, world, pos, world.getBlockEntity(pos), player, player.getMainHandItem())) {
         if (!drop.isEmpty()) {
            ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(drop.getItem());
            if (itemId != null && !itemId.equals(blockId) && !BuiltInRegistries.BLOCK.containsKey(itemId)) {
               targets.merge(itemId, drop.getCount(), Integer::sum);
            }
         }
      }

      return targets;
   }

   private static boolean matchesBlockStage(QuestDefinition quest, @Nullable BlockState blockState) {
      if (blockState == null) {
         return true;
      }

      Optional<HandlePrestigeListeners.BlockStage> stage = resolveBlockStage(blockState);
      if (!quest.hasStageConstraint()) {
         return quest.creator == QuestObjectiveType.FARM && stage.isPresent() ? stage.get().current() >= stage.get().max() : true;
      }

      if (stage.isEmpty()) {
         return false;
      }

      int currentStage = stage.get().current();
      int minStage = quest.minStage >= 0 ? quest.minStage : Integer.MIN_VALUE;
      int maxStage = quest.maxStage >= 0 ? quest.maxStage : Integer.MAX_VALUE;
      return currentStage >= minStage && currentStage <= maxStage;
   }

   private static Optional<HandlePrestigeListeners.BlockStage> resolveBlockStage(BlockState blockState) {
      IntegerProperty preferred = null;
      IntegerProperty fallback = null;

      for (Property<?> property : blockState.getProperties()) {
         if (property instanceof IntegerProperty integerProperty) {
            if (fallback == null) {
               fallback = integerProperty;
            }

            if ("age".equals(property.getName()) || "stage".equals(property.getName())) {
               preferred = integerProperty;
               break;
            }
         }
      }

      IntegerProperty property = preferred != null ? preferred : fallback;
      if (property == null) {
         return Optional.empty();
      }

      int current = (Integer)blockState.getValue(property);
      int max = property.getPossibleValues().stream().max(Integer::compareTo).orElse(current);
      return Optional.of(new HandlePrestigeListeners.BlockStage(current, max));
   }

   public static boolean isSilkTouchLikeEffect(ServerLevel level, BlockPos pos, BlockState state, ServerPlayer player) {
      Item blockItem = state.getBlock().asItem();
      if (blockItem == Items.AIR) {
         return false;
      }

      ItemStack realTool = player.getMainHandItem();
      ItemStack dummyTool = realTool.copy();
      dummyTool.set(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
      Builder lootBuilder = new Builder(level)
         .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(pos))
         .withOptionalParameter(LootContextParams.THIS_ENTITY, player)
         .withOptionalParameter(LootContextParams.BLOCK_ENTITY, level.getBlockEntity(pos));
      boolean naturallyDropsSelf = false;
      lootBuilder.withParameter(LootContextParams.TOOL, dummyTool);

      for (ItemStack drop : state.getDrops(lootBuilder)) {
         if (drop.is(blockItem)) {
            naturallyDropsSelf = true;
            break;
         }
      }

      if (naturallyDropsSelf) {
         return false;
      }

      boolean toolCausesSelfDrop = false;
      lootBuilder.withParameter(LootContextParams.TOOL, realTool);

      for (ItemStack drop : state.getDrops(lootBuilder)) {
         if (drop.is(blockItem)) {
            toolCausesSelfDrop = true;
            break;
         }
      }

      return toolCausesSelfDrop;
   }

   private record BlockStage(int current, int max) {
   }

   private record QuestProgressContext(
      @Nullable LivingEntity target,
      @Nullable BlockState blockState,
      @Nullable DamageSource damageSource,
      @Nullable ResourceLocation spiritLevel,
      @Nullable ResourceLocation enchantItem,
      Map<ResourceLocation, Integer> enchantments
   ) {
      private QuestProgressContext {
         enchantments = enchantments == null ? Map.of() : Map.copyOf(enchantments);
      }

      private static HandlePrestigeListeners.QuestProgressContext empty() {
         return new HandlePrestigeListeners.QuestProgressContext(null, null, null, null, null, Map.of());
      }

      private static HandlePrestigeListeners.QuestProgressContext entity(@Nullable LivingEntity target) {
         return new HandlePrestigeListeners.QuestProgressContext(target, null, null, null, null, Map.of());
      }

      private static HandlePrestigeListeners.QuestProgressContext block(@Nullable BlockState blockState) {
         return new HandlePrestigeListeners.QuestProgressContext(null, blockState, null, null, null, Map.of());
      }

      private static HandlePrestigeListeners.QuestProgressContext damage(LivingEntity target, DamageSource source) {
         return new HandlePrestigeListeners.QuestProgressContext(target, null, source, null, null, Map.of());
      }

      private static HandlePrestigeListeners.QuestProgressContext spirit(ResourceLocation spiritLevel) {
         return new HandlePrestigeListeners.QuestProgressContext(null, null, null, spiritLevel, null, Map.of());
      }

      private static HandlePrestigeListeners.QuestProgressContext enchant(@Nullable ResourceLocation item, Map<ResourceLocation, Integer> enchantments) {
         return new HandlePrestigeListeners.QuestProgressContext(null, null, null, null, item, enchantments);
      }
   }

   private record QuestProgressSource(ServerPlayer owner, LivingEntity actor, boolean subordinate) {
      private static HandlePrestigeListeners.QuestProgressSource owner(ServerPlayer player) {
         return new HandlePrestigeListeners.QuestProgressSource(player, player, false);
      }

      @Nullable
      private LivingEntity subordinateRewardTarget() {
         return this.subordinate ? this.actor : null;
      }
   }

   private record QuestProgressTarget(ResourceLocation id, QuestDefinition quest) {
   }
}
