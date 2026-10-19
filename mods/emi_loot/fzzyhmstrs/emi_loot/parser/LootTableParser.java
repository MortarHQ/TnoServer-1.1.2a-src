package fzzyhmstrs.emi_loot.parser;

import com.google.common.collect.Multimap;
import com.google.gson.JsonElement;
import com.mojang.datafixers.util.Either;
import fzzyhmstrs.emi_loot.EMILoot;
import fzzyhmstrs.emi_loot.mixins.CombinedEntryAccessor;
import fzzyhmstrs.emi_loot.mixins.ConditionalLootFunctionAccessor;
import fzzyhmstrs.emi_loot.mixins.ItemEntryAccessor;
import fzzyhmstrs.emi_loot.mixins.LeafEntryAccessor;
import fzzyhmstrs.emi_loot.mixins.LootPoolAccessor;
import fzzyhmstrs.emi_loot.mixins.LootPoolEntryAccessor;
import fzzyhmstrs.emi_loot.mixins.LootTableAccessor;
import fzzyhmstrs.emi_loot.mixins.LootTableEntryAccessor;
import fzzyhmstrs.emi_loot.mixins.TagEntryAccessor;
import fzzyhmstrs.emi_loot.networking.ClearPayload;
import fzzyhmstrs.emi_loot.parser.processor.NumberProcessors;
import fzzyhmstrs.emi_loot.parser.registry.LootParserRegistry;
import fzzyhmstrs.emi_loot.server.ArchaeologyLootTableSender;
import fzzyhmstrs.emi_loot.server.BlockLootTableSender;
import fzzyhmstrs.emi_loot.server.ChestLootTableSender;
import fzzyhmstrs.emi_loot.server.ComplexLootPoolBuilder;
import fzzyhmstrs.emi_loot.server.GameplayLootTableSender;
import fzzyhmstrs.emi_loot.server.LootBuilder;
import fzzyhmstrs.emi_loot.server.LootSender;
import fzzyhmstrs.emi_loot.server.MobLootTableSender;
import fzzyhmstrs.emi_loot.server.NestedLootTableResults;
import fzzyhmstrs.emi_loot.server.ServerResourceData;
import fzzyhmstrs.emi_loot.server.SimpleLootPoolBuilder;
import fzzyhmstrs.emi_loot.util.LText;
import fzzyhmstrs.emi_loot.util.TextKey;
import it.unimi.dsi.fastutil.booleans.BooleanIterator;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import me.fzzyhmstrs.fzzy_config.api.ConfigApi;
import net.minecraft.advancements.critereon.ItemPredicate.Builder;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.EntryGroup;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.level.storage.loot.entries.SequentialEntry;
import net.minecraft.world.level.storage.loot.entries.TagEntry;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import org.jetbrains.annotations.Nullable;

public class LootTableParser {
   private static final Map<ResourceLocation, ChestLootTableSender> chestSenders = new HashMap<>();
   private static final Map<ResourceLocation, BlockLootTableSender> blockSenders = new HashMap<>();
   private static final Map<ResourceLocation, MobLootTableSender> mobSenders = new HashMap<>();
   private static final Map<ResourceLocation, GameplayLootTableSender> gameplaySenders = new HashMap<>();
   private static final Map<ResourceLocation, ArchaeologyLootTableSender> archaeologySenders = new HashMap<>();
   public static final Object2BooleanMap<LootTableParser.PostProcessor> postProcessors;
   public static String currentTable = "none";
   public static List<ResourceLocation> parsedDirectDrops = new LinkedList<>();
   public static boolean hasParsedLootTables = false;
   public static Registry<LootTable> lootManager = null;
   public static RegistryOps<JsonElement> registryOps = null;
   private static final List<LootItemCondition> emptyConditions = List.of();

   private static boolean hasPostProcessed() {
      BooleanIterator var0 = postProcessors.values().iterator();

      while (var0.hasNext()) {
         boolean bl = (Boolean)var0.next();
         if (!bl) {
            return false;
         }
      }

      return true;
   }

   public void registerServer(ServerPlayer player) {
      if (!hasPostProcessed()) {
         EMILoot.LOGGER.warn("Post-processing not completed for some reason, completing now...");

         for (LootTableParser.PostProcessor process : LootTableParser.PostProcessor.values()) {
            postProcess(process);
         }

         EMILoot.LOGGER.warn("Post-processing complete!");
      }

      if (ConfigApi.INSTANCE.network().canSend(ClearPayload.TYPE.id(), player)) {
         ConfigApi.INSTANCE.network().send(ClearPayload.INSTANCE, player);
      }

      if (EMILoot.config.parseChestLoot) {
         chestSenders.forEach((id, chestSender) -> chestSender.send(player));
      }

      if (EMILoot.config.parseBlockLoot) {
         blockSenders.forEach((id, blockSender) -> blockSender.send(player));
      }

      if (EMILoot.config.parseMobLoot) {
         mobSenders.forEach((id, mobSender) -> mobSender.send(player));
      }

      if (EMILoot.config.parseGameplayLoot) {
         gameplaySenders.forEach((id, gameplaySender) -> gameplaySender.send(player));
      }

      if (EMILoot.config.parseArchaeologyLoot) {
         archaeologySenders.forEach((id, archaeologySender) -> archaeologySender.send(player));
      }
   }

   public static void parseLootTables(Registry<LootTable> manager) {
      lootManager = manager;
      parsedDirectDrops = new LinkedList<>();
      chestSenders.clear();
      blockSenders.clear();
      mobSenders.clear();
      gameplaySenders.clear();
      archaeologySenders.clear();

      try {
         long start = System.currentTimeMillis();
         EMILoot.LOGGER.info("parsing loot tables");
         manager.entrySet().forEach(entryx -> {
            LootTable table = (LootTable)entryx.getValue();
            if (!((LootTableAccessor)table).getPools().isEmpty()) {
               parseLootTable(((ResourceKey)entryx.getKey()).location(), table);
            }
         });
         if (EMILoot.config.parseMobLoot) {
            ResourceLocation chk = ResourceLocation.withDefaultNamespace("pig");
            BuiltInRegistries.ENTITY_TYPE
               .entrySet()
               .forEach(
                  entryx -> {
                     EntityType<?> type = (EntityType<?>)entryx.getValue();
                     if (type == EntityType.SHEEP) {
                        for (ResourceLocation sheepId : ServerResourceData.SHEEP_TABLES) {
                           parseEntityType(manager, type, sheepId, chk, emptyConditions);
                        }
                     }

                     ResourceLocation shearingId = ResourceLocation.fromNamespaceAndPath(
                        ((ResourceKey)entryx.getKey()).location().getNamespace(), "shearing/" + ((ResourceKey)entryx.getKey()).location().getPath()
                     );
                     LootTable shearingTable = (LootTable)manager.get(shearingId);
                     if (shearingTable != null && shearingTable != LootTable.EMPTY) {
                        parseEntityType(manager, type, shearingId, chk, List.of(MatchTool.toolMatches(Builder.item().of(new ItemLike[]{Items.SHEARS})).build()));
                     }

                     parseEntityType(manager, type, type.getDefaultLootTable().location(), chk, emptyConditions);
                  }
               );
         }

         Multimap<ResourceLocation, LootTable> missedDrops = ServerResourceData.getMissedDirectDrops(parsedDirectDrops);

         for (Entry<ResourceLocation, LootTable> entry : missedDrops.entries()) {
            if (EMILoot.DEBUG) {
               EMILoot.LOGGER.info("parsing missed direct drop table: {}", entry.getKey());
            }

            parseLootTable(entry.getKey(), entry.getValue());
         }

         EMILoot.LOGGER.info("finished parsing loot tables in {}", System.currentTimeMillis() - start);
         hasParsedLootTables = true;
      } catch (Throwable e) {
         EMILoot.LOGGER.error("Critical unhandled error encountered while parsing loot tables. Results may be incomplete.");
         EMILoot.LOGGER.error("Thrown Error: ", e);
      }
   }

   private static void parseLootTable(ResourceLocation id, LootTable lootTable) {
      if (!ServerResourceData.skipTable(lootTable, id)) {
         currentTable = id.toString();

         try {
            LootContextParamSet type = lootTable.getParamSet();
            if (type == LootContextParamSets.CHEST && EMILoot.config.parseChestLoot) {
               chestSenders.put(id, parseChestLootTable(lootTable, id));
            } else if (type == LootContextParamSets.BLOCK && EMILoot.config.parseBlockLoot) {
               blockSenders.put(id, parseBlockLootTable(lootTable, id));
            } else if ((type == LootContextParamSets.FISHING || type == LootContextParamSets.GIFT || type == LootContextParamSets.PIGLIN_BARTER)
               && EMILoot.config.parseGameplayLoot) {
               gameplaySenders.put(id, parseGameplayLootTable(lootTable, id));
            } else if (type == LootContextParamSets.ARCHAEOLOGY && EMILoot.config.parseArchaeologyLoot) {
               archaeologySenders.put(id, parseArchaeologyTable(lootTable, id));
            }
         } catch (Throwable e) {
            EMILoot.LOGGER.error("Critical error encountered while parsing Loot Table {}", id);
            EMILoot.LOGGER.error("Thrown Error: ", e);
         }
      }
   }

   public static void postProcess(LootTableParser.PostProcessor process) {
      if (hasParsedLootTables) {
         try {
            for (LootSender<?> sender : chestSenders.values()) {
               for (LootBuilder builder : sender.getBuilders()) {
                  for (LootPoolEntryContainer entry : builder.getEntriesToPostProcess(process)) {
                     if (EMILoot.DEBUG) {
                        EMILoot.LOGGER.info("Post-processing builder in chest sender: {}", sender.getId());
                     }

                     parseLootPoolEntry(builder, entry, process);
                  }
               }

               sender.build();
            }

            for (LootSender<?> sender : blockSenders.values()) {
               for (LootBuilder builder : sender.getBuilders()) {
                  for (LootPoolEntryContainer entry : builder.getEntriesToPostProcess(process)) {
                     if (EMILoot.DEBUG) {
                        EMILoot.LOGGER.info("Post-processing builder in block sender: {}", sender.getId());
                     }

                     parseLootPoolEntry(builder, entry, process);
                  }
               }

               sender.build();
            }

            for (LootSender<?> sender : mobSenders.values()) {
               for (LootBuilder builder : sender.getBuilders()) {
                  for (LootPoolEntryContainer entry : builder.getEntriesToPostProcess(process)) {
                     if (EMILoot.DEBUG) {
                        EMILoot.LOGGER.info("Post-processing builder in mob sender: {}", sender.getId());
                     }

                     parseLootPoolEntry(builder, entry, process);
                  }
               }

               sender.build();
            }

            for (LootSender<?> sender : gameplaySenders.values()) {
               for (LootBuilder builder : sender.getBuilders()) {
                  for (LootPoolEntryContainer entry : builder.getEntriesToPostProcess(process)) {
                     if (EMILoot.DEBUG) {
                        EMILoot.LOGGER.info("Post-processing builder in gameplay sender: {}", sender.getId());
                     }

                     parseLootPoolEntry(builder, entry, process);
                  }
               }

               sender.build();
            }

            for (LootSender<?> sender : archaeologySenders.values()) {
               for (LootBuilder builder : sender.getBuilders()) {
                  for (LootPoolEntryContainer entry : builder.getEntriesToPostProcess(process)) {
                     if (EMILoot.DEBUG) {
                        EMILoot.LOGGER.info("Post-processing builder in archaeology sender: {}", sender.getId());
                     }

                     parseLootPoolEntry(builder, entry, process);
                  }
               }

               sender.build();
            }

            postProcessors.put(process, true);
         } catch (Throwable e) {
            EMILoot.LOGGER.error("Critical error encountered while post-processing for {}", process);
            EMILoot.LOGGER.error("Thrown Error: ", e);
         }
      }
   }

   private static void parseEntityType(
      Registry<LootTable> manager, EntityType<?> type, ResourceLocation mobTableId, ResourceLocation fallback, List<LootItemCondition> addedConditions
   ) {
      try {
         ResourceLocation mobId = BuiltInRegistries.ENTITY_TYPE.getKey(type);
         LootTable mobTable = (LootTable)manager.get(mobTableId);
         if (ServerResourceData.skipTable(mobTable, mobTableId)) {
            return;
         }

         if ((type == EntityType.PIG && mobId.equals(fallback) || mobTable != LootTable.EMPTY) && mobTable != null) {
            currentTable = mobTableId.toString();
            mobSenders.put(mobTableId, parseMobLootTable(mobTable, mobTableId, mobId, addedConditions));
         } else if (EMILoot.DEBUG) {
            EMILoot.LOGGER.warn("Found empty mob table at id: {}", mobTableId);
         }
      } catch (Throwable e) {
         EMILoot.LOGGER.error("Critical error encountered while parsing Mob Loot Table {}", mobTableId.toString());
         EMILoot.LOGGER.error("Thrown Error: ", e);
      }
   }

   private static ChestLootTableSender parseChestLootTable(LootTable lootTable, ResourceLocation id) {
      ChestLootTableSender sender = new ChestLootTableSender(id);
      List<LootPool> pools = ((LootTableAccessor)lootTable).getPools();
      List<LootTable> directTables = ServerResourceData.getDirectTables(lootTable, id, EMILoot.config.doDirectDrops(EMILoot.Type.CHEST));
      if (!directTables.isEmpty()) {
         parsedDirectDrops.add(id);
         List<LootPool> allPools = new ArrayList<>(pools.size() + directTables.size());
         allPools.addAll(pools);

         for (LootTable directTable : directTables) {
            if (directTable != null) {
               allPools.addAll(((LootTableAccessor)directTable).getPools());
            }
         }

         pools = allPools;
      }

      for (LootPool pool : pools) {
         NumberProvider rollProvider = ((LootPoolAccessor)pool).getRolls();
         float conditionalMultiplier = 1.0F;

         for (LootItemCondition condition : ((LootPoolAccessor)pool).getConditions()) {
            if (condition instanceof LootItemRandomChanceCondition randomChanceLootCondition) {
               conditionalMultiplier *= NumberProcessors.getRollAvg(randomChanceLootCondition.chance());
            }
         }

         float rollAvg = NumberProcessors.getRollAvg(rollProvider) * conditionalMultiplier;
         SimpleLootPoolBuilder builder = new SimpleLootPoolBuilder(rollAvg);

         for (LootPoolEntryContainer entry : ((LootPoolAccessor)pool).getEntries()) {
            parseLootPoolEntry(builder, entry);
         }

         sender.addBuilder(builder);
      }

      return sender;
   }

   private static BlockLootTableSender parseBlockLootTable(LootTable lootTable, ResourceLocation id) {
      BlockLootTableSender sender = new BlockLootTableSender(id);
      parseBlockLootTableInternal(lootTable, sender, false);
      List<LootTable> directTables = ServerResourceData.getDirectTables(lootTable, id, EMILoot.config.doDirectDrops(EMILoot.Type.BLOCK));
      if (!directTables.isEmpty()) {
         parsedDirectDrops.add(id);
         parseBlockDirectLootTable(directTables, sender);
      }

      return sender;
   }

   private static void parseBlockDirectLootTable(Collection<LootTable> tables, BlockLootTableSender sender) {
      for (LootTable directTable : tables) {
         if (directTable != null) {
            parseBlockLootTableInternal(directTable, sender, true);
         }
      }
   }

   private static void parseBlockLootTableInternal(LootTable lootTable, BlockLootTableSender sender, boolean isDirect) {
      for (LootPool pool : ((LootTableAccessor)lootTable).getPools()) {
         List<LootItemCondition> conditions = ((LootPoolAccessor)pool).getConditions();
         List<LootTableParser.LootConditionResult> parsedConditions = parseLootConditions(conditions, ItemStack.EMPTY, false);
         if (isDirect) {
            if (EMILoot.DEBUG) {
               EMILoot.LOGGER.info("Adding direct drop block condition to {}", currentTable);
            }

            parsedConditions.add(new LootTableParser.LootConditionResult(TextKey.of("emi_loot.condition.direct_drop")));
         }

         List<LootItemFunction> functions = ((LootPoolAccessor)pool).getFunctions();
         List<LootTableParser.LootFunctionResult> parsedFunctions = new LinkedList<>();

         for (LootItemFunction function : functions) {
            LootTableParser.LootFunctionResult r = parseLootFunction(function);
            if (!r.skip()) {
               parsedFunctions.add(r);
            }
         }

         NumberProvider rollProvider = ((LootPoolAccessor)pool).getRolls();
         float rollAvg = NumberProcessors.getRollAvg(rollProvider);
         ComplexLootPoolBuilder builder = new ComplexLootPoolBuilder(rollAvg, parsedConditions, parsedFunctions);

         for (LootPoolEntryContainer entry : ((LootPoolAccessor)pool).getEntries()) {
            parseLootPoolEntry(builder, entry);
         }

         sender.addBuilder(builder);
      }
   }

   private static MobLootTableSender parseMobLootTable(LootTable lootTable, ResourceLocation id, ResourceLocation mobId) {
      return parseMobLootTable(lootTable, id, mobId, emptyConditions);
   }

   private static MobLootTableSender parseMobLootTable(
      LootTable lootTable, ResourceLocation id, ResourceLocation mobId, List<LootItemCondition> addedConditions
   ) {
      MobLootTableSender sender = new MobLootTableSender(id, mobId);
      parseMobLootTableInternal(lootTable, sender, false, addedConditions);
      List<LootTable> directTables = ServerResourceData.getDirectTables(lootTable, id, EMILoot.config.doDirectDrops(EMILoot.Type.MOB));
      if (!directTables.isEmpty()) {
         parsedDirectDrops.add(id);
         parseMobDirectLootTable(directTables, sender);
      }

      return sender;
   }

   private static void parseMobDirectLootTable(Collection<LootTable> tables, MobLootTableSender sender) {
      for (LootTable directTable : tables) {
         if (directTable != null) {
            parseMobLootTableInternal(directTable, sender, true, emptyConditions);
         }
      }
   }

   private static void parseMobLootTableInternal(LootTable lootTable, MobLootTableSender sender, boolean isDirect, List<LootItemCondition> addedConditions) {
      for (LootPool pool : ((LootTableAccessor)lootTable).getPools()) {
         List<LootItemCondition> conditions = ((LootPoolAccessor)pool).getConditions();
         List<LootTableParser.LootConditionResult> parsedConditions = parseLootConditions(conditions, ItemStack.EMPTY, false);
         if (isDirect) {
            if (EMILoot.DEBUG) {
               EMILoot.LOGGER.info("Adding direct drop mob condition to {}", currentTable);
            }

            parsedConditions.add(new LootTableParser.LootConditionResult(TextKey.of("emi_loot.condition.direct_drop")));
         }

         List<LootItemFunction> functions = ((LootPoolAccessor)pool).getFunctions();
         List<LootTableParser.LootFunctionResult> parsedFunctions = new LinkedList<>();

         for (LootItemFunction function : functions) {
            LootTableParser.LootFunctionResult r = parseLootFunction(function);
            if (!r.skip()) {
               parsedFunctions.add(r);
            }
         }

         NumberProvider rollProvider = ((LootPoolAccessor)pool).getRolls();
         float rollAvg = NumberProcessors.getRollAvg(rollProvider);
         ComplexLootPoolBuilder builder = new ComplexLootPoolBuilder(rollAvg, parsedConditions, parsedFunctions);

         for (LootPoolEntryContainer entry : ((LootPoolAccessor)pool).getEntries()) {
            parseLootPoolEntry(builder, entry);
         }

         sender.addBuilder(builder);
      }
   }

   private static GameplayLootTableSender parseGameplayLootTable(LootTable lootTable, ResourceLocation id) {
      GameplayLootTableSender sender = new GameplayLootTableSender(id);
      parseGameplayLootTableInternal(lootTable, sender, false);
      List<LootTable> directTables = ServerResourceData.getDirectTables(lootTable, id, EMILoot.config.doDirectDrops(EMILoot.Type.GAMEPLAY));
      if (!directTables.isEmpty()) {
         parsedDirectDrops.add(id);
         parseGameplayDirectLootTable(directTables, sender);
      }

      return sender;
   }

   private static void parseGameplayDirectLootTable(Collection<LootTable> tables, GameplayLootTableSender sender) {
      for (LootTable directTable : tables) {
         if (directTable != null) {
            parseGameplayLootTableInternal(directTable, sender, true);
         }
      }
   }

   private static void parseGameplayLootTableInternal(LootTable lootTable, GameplayLootTableSender sender, boolean isDirect) {
      for (LootPool pool : ((LootTableAccessor)lootTable).getPools()) {
         List<LootItemCondition> conditions = ((LootPoolAccessor)pool).getConditions();
         List<LootTableParser.LootConditionResult> parsedConditions = parseLootConditions(conditions, ItemStack.EMPTY, false);
         if (isDirect) {
            if (EMILoot.DEBUG) {
               EMILoot.LOGGER.info("Adding direct drop gameplay condition to {}", currentTable);
            }

            parsedConditions.add(new LootTableParser.LootConditionResult(TextKey.of("emi_loot.condition.direct_drop")));
         }

         List<LootItemFunction> functions = ((LootPoolAccessor)pool).getFunctions();
         List<LootTableParser.LootFunctionResult> parsedFunctions = new LinkedList<>();

         for (LootItemFunction function : functions) {
            LootTableParser.LootFunctionResult r = parseLootFunction(function);
            if (!r.skip()) {
               parsedFunctions.add(r);
            }
         }

         NumberProvider rollProvider = ((LootPoolAccessor)pool).getRolls();
         float rollAvg = NumberProcessors.getRollAvg(rollProvider);
         ComplexLootPoolBuilder builder = new ComplexLootPoolBuilder(rollAvg, parsedConditions, parsedFunctions);

         for (LootPoolEntryContainer entry : ((LootPoolAccessor)pool).getEntries()) {
            parseLootPoolEntry(builder, entry);
         }

         sender.addBuilder(builder);
      }
   }

   private static ArchaeologyLootTableSender parseArchaeologyTable(LootTable lootTable, ResourceLocation id) {
      ArchaeologyLootTableSender sender = new ArchaeologyLootTableSender(id);
      List<LootPool> pools = ((LootTableAccessor)lootTable).getPools();
      List<LootTable> directTables = ServerResourceData.getDirectTables(lootTable, id, EMILoot.config.doDirectDrops(EMILoot.Type.ARCHAEOLOGY));
      if (!directTables.isEmpty()) {
         parsedDirectDrops.add(id);
         List<LootPool> allPools = new ArrayList<>(pools.size() + directTables.size());
         allPools.addAll(pools);

         for (LootTable directTable : directTables) {
            if (directTable != null) {
               allPools.addAll(((LootTableAccessor)directTable).getPools());
            }
         }

         pools = allPools;
      }

      for (LootPool pool : pools) {
         NumberProvider rollProvider = ((LootPoolAccessor)pool).getRolls();
         float conditionalMultiplier = 1.0F;

         for (LootItemCondition condition : ((LootPoolAccessor)pool).getConditions()) {
            if (condition instanceof LootItemRandomChanceCondition) {
               conditionalMultiplier *= NumberProcessors.getRollAvg(((LootItemRandomChanceCondition)condition).chance());
            }
         }

         float rollAvg = NumberProcessors.getRollAvg(rollProvider) * conditionalMultiplier;
         SimpleLootPoolBuilder builder = new SimpleLootPoolBuilder(rollAvg);

         for (LootPoolEntryContainer entry : ((LootPoolAccessor)pool).getEntries()) {
            parseLootPoolEntry(builder, entry);
         }

         sender.addBuilder(builder);
      }

      return sender;
   }

   private static void parseGenericComplexLootTableInternal(LootTable lootTable, LootSender<ComplexLootPoolBuilder> sender) {
      for (LootPool pool : ((LootTableAccessor)lootTable).getPools()) {
         List<LootItemCondition> conditions = ((LootPoolAccessor)pool).getConditions();
         List<LootTableParser.LootConditionResult> parsedConditions = parseLootConditions(conditions, ItemStack.EMPTY, false);
         List<LootItemFunction> functions = ((LootPoolAccessor)pool).getFunctions();
         List<LootTableParser.LootFunctionResult> parsedFunctions = new LinkedList<>();

         for (LootItemFunction function : functions) {
            LootTableParser.LootFunctionResult r = parseLootFunction(function);
            if (!r.skip()) {
               parsedFunctions.add(r);
            }
         }

         NumberProvider rollProvider = ((LootPoolAccessor)pool).getRolls();
         float rollAvg = NumberProcessors.getRollAvg(rollProvider);
         ComplexLootPoolBuilder builder = new ComplexLootPoolBuilder(rollAvg, parsedConditions, parsedFunctions);

         for (LootPoolEntryContainer entry : ((LootPoolAccessor)pool).getEntries()) {
            parseLootPoolEntry(builder, entry);
         }

         sender.addBuilder(builder);
      }
   }

   static void parseLootPoolEntry(LootBuilder builder, LootPoolEntryContainer entry) {
      parseLootPoolEntry(builder, entry, null);
   }

   static void parseLootPoolEntry(LootBuilder builder, LootPoolEntryContainer entry, @Nullable LootTableParser.PostProcessor process) {
      if (entry instanceof TagEntry tagEntry) {
         if (process == LootTableParser.PostProcessor.TAG) {
            List<LootTableParser.ItemEntryResult> result = parseTagEntry(tagEntry, false);
            result.forEach(builder::addItem);
         } else {
            builder.addEntryForPostProcessing(LootTableParser.PostProcessor.TAG, tagEntry);
         }
      } else {
         List<LootTableParser.ItemEntryResult> result = parseLootPoolEntry(entry, false);
         result.forEach(builder::addItem);
      }
   }

   static List<LootTableParser.ItemEntryResult> parseLootPoolEntry(LootPoolEntryContainer entry, boolean parentIsAlternative) {
      if (entry instanceof LootItem itemEntry) {
         return parseItemEntry(itemEntry, parentIsAlternative);
      } else if (entry instanceof AlternativesEntry alternativeEntry) {
         return parseAlternativeEntry(alternativeEntry);
      } else if (entry instanceof EntryGroup groupEntry) {
         return parseGroupEntry(groupEntry, parentIsAlternative);
      } else if (entry instanceof SequentialEntry sequenceEntry) {
         return parseSequenceEntry(sequenceEntry, parentIsAlternative);
      } else if (entry instanceof TagEntry tagEntry) {
         return parseTagEntry(tagEntry, parentIsAlternative);
      } else {
         return entry instanceof NestedLootTable lootTableEntry ? parseLootTableEntry(lootTableEntry, parentIsAlternative) : List.of();
      }
   }

   static List<LootTableParser.ItemEntryResult> parseItemEntry(LootItem entry, boolean parentIsAlternative) {
      int weight = ((LeafEntryAccessor)entry).getWeight();
      ItemStack item = new ItemStack(((ItemEntryAccessor)entry).getItem());
      List<LootItemFunction> functions = ((LeafEntryAccessor)entry).getFunctions();
      List<LootItemCondition> conditions = ((LootPoolEntryAccessor)entry).getConditions();
      return parseItemEntry(weight, item, functions, conditions, parentIsAlternative);
   }

   static List<LootTableParser.ItemEntryResult> parseItemEntry(
      int weight, ItemStack item, List<LootItemFunction> functions, List<LootItemCondition> conditions, boolean parentIsAlternative
   ) {
      LootTableParser.FunctionApplierResult functionApplierResult = applyLootFunctionToItem(functions, item, weight, parentIsAlternative);
      List<LootTableParser.ItemEntryResult> conditionalEntryResults = functionApplierResult.conditionalResults;
      List<TextKey> functionTexts = functionApplierResult.functionTexts;
      item = functionApplierResult.stack;
      List<TextKey> conditionsTexts = parseLootConditionTexts(conditions, item, parentIsAlternative);
      List<LootTableParser.ItemEntryResult> returnList = new LinkedList<>();
      returnList.add(new LootTableParser.ItemEntryResult(item, weight, conditionsTexts, functionTexts));
      conditionalEntryResults.forEach(conditionalEntry -> {
         conditionalEntry.conditions.addAll(conditionsTexts);
         conditionalEntry.functions.addAll(functionTexts);
         returnList.add(conditionalEntry);
      });
      return returnList;
   }

   static List<LootTableParser.ItemEntryResult> parseTagEntry(TagEntry entry, boolean parentIsAlternative) {
      TagKey<Item> items = ((TagEntryAccessor)entry).getName();
      if (EMILoot.DEBUG) {
         EMILoot.LOGGER.info(">>> Parsing tag entry {}", items.location());
      }

      Iterable<Holder<Item>> itemsItr = BuiltInRegistries.ITEM.getTagOrEmpty(items);
      List<LootTableParser.ItemEntryResult> returnList = new LinkedList<>();
      int weight = ((LeafEntryAccessor)entry).getWeight();
      List<LootItemFunction> functions = ((LeafEntryAccessor)entry).getFunctions();
      List<LootItemCondition> conditions = ((LootPoolEntryAccessor)entry).getConditions();

      for (Holder<Item> item : itemsItr) {
         ItemStack stack = new ItemStack((ItemLike)item.value());
         returnList.addAll(parseItemEntry(weight, stack, functions, conditions, parentIsAlternative));
      }

      return returnList;
   }

   static List<LootTableParser.ItemEntryResult> parseAlternativeEntry(AlternativesEntry entry) {
      List<LootPoolEntryContainer> children = ((CombinedEntryAccessor)entry).getChildren();
      List<LootItemCondition> conditions = ((LootPoolEntryAccessor)entry).getConditions();
      List<TextKey> conditionsTexts = parseLootConditionTexts(conditions, ItemStack.EMPTY, true);
      List<LootTableParser.ItemEntryResult> results = new LinkedList<>();
      children.forEach(lootEntry -> {
         List<LootTableParser.ItemEntryResult> result = parseLootPoolEntry(lootEntry, true);
         result.forEach(resultEntry -> {
            resultEntry.conditions.addAll(conditionsTexts);
            results.add(resultEntry);
         });
      });
      return results;
   }

   static List<LootTableParser.ItemEntryResult> parseGroupEntry(EntryGroup entry, boolean parentIsAlternative) {
      List<LootPoolEntryContainer> children = ((CombinedEntryAccessor)entry).getChildren();
      List<LootItemCondition> conditions = ((LootPoolEntryAccessor)entry).getConditions();
      List<TextKey> conditionsTexts = parseLootConditionTexts(conditions, ItemStack.EMPTY, parentIsAlternative);
      List<LootTableParser.ItemEntryResult> results = new LinkedList<>();
      children.forEach(lootEntry -> {
         List<LootTableParser.ItemEntryResult> result = parseLootPoolEntry(lootEntry, parentIsAlternative);
         result.forEach(resultEntry -> {
            resultEntry.conditions.addAll(conditionsTexts);
            results.add(resultEntry);
         });
      });
      return results;
   }

   static List<LootTableParser.ItemEntryResult> parseSequenceEntry(SequentialEntry entry, boolean parentIsAlternative) {
      List<LootPoolEntryContainer> children = ((CombinedEntryAccessor)entry).getChildren();
      List<LootItemCondition> conditions = ((LootPoolEntryAccessor)entry).getConditions();
      List<TextKey> conditionsTexts = parseLootConditionTexts(conditions, ItemStack.EMPTY, parentIsAlternative);
      List<LootTableParser.ItemEntryResult> results = new LinkedList<>();
      TextKey sequenceCondition = TextKey.of("emi_loot.condition.sequence");
      children.forEach(lootEntry -> {
         List<LootTableParser.ItemEntryResult> result = parseLootPoolEntry(lootEntry, parentIsAlternative);
         result.forEach(resultEntry -> {
            resultEntry.conditions.addAll(conditionsTexts);
            resultEntry.conditions.add(sequenceCondition);
            results.add(resultEntry);
         });
      });
      return results;
   }

   static List<LootTableParser.ItemEntryResult> parseLootTableEntry(NestedLootTable entry, boolean parentIsAlternative) {
      Either<ResourceKey<LootTable>, LootTable> value = ((LootTableEntryAccessor)entry).getValue();
      LootTable table = (LootTable)value.map(lootManager::get, Function.identity());
      if (table == null) {
         return List.of();
      }

      ResourceLocation id = lootManager.getKey(table);
      if (id == null) {
         return List.of();
      }

      List<LootItemCondition> conditions = ((LootPoolEntryAccessor)entry).getConditions();
      List<TextKey> conditionsTexts = parseLootConditionTexts(conditions, ItemStack.EMPTY, parentIsAlternative);
      LootSender<ComplexLootPoolBuilder> results = new NestedLootTableResults();
      parseGenericComplexLootTableInternal(table, results);
      List<LootTableParser.ItemEntryResult> parsedList = new ArrayList<>();
      results.getBuilders().forEach(parsedBuilder -> parsedList.addAll(parsedBuilder.revert()));
      parsedList.forEach(result -> result.conditions.addAll(conditionsTexts));
      List<LootItemFunction> functions = ((LeafEntryAccessor)entry).getFunctions();
      return applyLootFunctionsToTableResults(functions, parsedList, parentIsAlternative);
   }

   static LootTableParser.LootFunctionResult parseLootFunction(LootItemFunction function) {
      return parseLootFunction(function, ItemStack.EMPTY, false);
   }

   static LootTableParser.LootFunctionResult parseLootFunction(LootItemFunction function, ItemStack stack, boolean parentIsAlternative) {
      LootItemFunctionType<?> type;
      try {
         type = function.getType();
      } catch (Exception e) {
         EMILoot.LOGGER.error("Failed to determine a function type for stack {} in table {}", stack.getHoverName(), currentTable);
         EMILoot.LOGGER.error("Thrown Error:", e);
         return LootTableParser.LootFunctionResult.EMPTY;
      }

      List<TextKey> conditionsTexts;
      if (function instanceof LootItemConditionalFunction) {
         List<LootItemCondition> conditions = ((ConditionalLootFunctionAccessor)function).getConditions();
         conditionsTexts = parseLootConditionTexts(conditions, stack, parentIsAlternative);
      } else {
         conditionsTexts = new LinkedList<>();
      }

      try {
         return LootParserRegistry.parseFunction(function, stack, type, parentIsAlternative, conditionsTexts);
      } catch (Exception e) {
         EMILoot.LOGGER.error("Failed to parse LootFunction of type {} for stack {} in table {}", new Object[]{type, stack.getHoverName(), currentTable});
         EMILoot.LOGGER.error("Thrown Error:", e);
         return LootTableParser.LootFunctionResult.EMPTY;
      }
   }

   private static List<LootTableParser.ItemEntryResult> applyLootFunctionsToTableResults(
      List<LootItemFunction> functions, List<LootTableParser.ItemEntryResult> parsedList, boolean parentIsAlternative
   ) {
      List<LootTableParser.ItemEntryResult> conditionalEntryResults = new LinkedList<>();
      List<LootTableParser.ItemEntryResult> processedEntryResults = new LinkedList<>();
      parsedList.forEach(itemEntry -> {
         LootTableParser.FunctionApplierResult result = applyLootFunctionToItem(functions, itemEntry.item, itemEntry.weight, parentIsAlternative);
         List<TextKey> conditionTexts = itemEntry.conditions;
         List<TextKey> functionTexts = itemEntry.functions;
         functionTexts.addAll(result.functionTexts);
         processedEntryResults.add(new LootTableParser.ItemEntryResult(result.stack, itemEntry.weight, conditionTexts, functionTexts));
         conditionalEntryResults.addAll(result.conditionalResults);
      });
      processedEntryResults.addAll(conditionalEntryResults);
      return processedEntryResults;
   }

   private static LootTableParser.FunctionApplierResult applyLootFunctionToItem(
      List<LootItemFunction> functions, ItemStack item, int weight, boolean parentIsAlternative
   ) {
      List<TextKey> functionTexts = new LinkedList<>();
      List<LootTableParser.ItemEntryResult> conditionalEntryResults = new LinkedList<>();

      for (LootItemFunction lootFunction : functions) {
         LootTableParser.LootFunctionResult result = parseLootFunction(lootFunction, item, parentIsAlternative);
         TextKey lootText = result.text;
         ItemStack newStack = result.stack;
         List<TextKey> resultConditions = result.conditions;
         if (!resultConditions.isEmpty()) {
            ItemStack conditionalItem;
            if (newStack != ItemStack.EMPTY) {
               conditionalItem = newStack;
            } else {
               conditionalItem = item;
            }

            List<TextKey> conditionalFunctionTexts = new ArrayList<>();
            conditionalFunctionTexts.add(lootText);
            conditionalEntryResults.add(new LootTableParser.ItemEntryResult(conditionalItem, weight, resultConditions, conditionalFunctionTexts));
         } else {
            if (lootText.isNotEmpty() && !lootText.skip()) {
               functionTexts.add(lootText);
            }

            if (newStack != ItemStack.EMPTY) {
               item = newStack;
            }
         }
      }

      return new LootTableParser.FunctionApplierResult(conditionalEntryResults, functionTexts, item);
   }

   public static List<TextKey> parseLootConditionTexts(List<LootItemCondition> conditions, ItemStack item, boolean parentIsAlternative) {
      List<TextKey> conditionsTexts = new LinkedList<>();

      for (LootTableParser.LootConditionResult result : parseLootConditions(conditions, item, parentIsAlternative)) {
         conditionsTexts.add(result.text);
      }

      return conditionsTexts;
   }

   public static List<LootTableParser.LootConditionResult> parseLootConditions(List<LootItemCondition> conditions, ItemStack item, boolean parentIsAlternative) {
      List<LootTableParser.LootConditionResult> parsedConditions = new LinkedList<>();

      for (LootItemCondition condition : conditions) {
         for (LootTableParser.LootConditionResult result : parseLootCondition(condition, item, parentIsAlternative)) {
            if (result.text.isNotEmpty()) {
               parsedConditions.add(result);
            }
         }
      }

      return parsedConditions;
   }

   public static List<LootTableParser.LootConditionResult> parseLootCondition(LootItemCondition condition, ItemStack stack) {
      return parseLootCondition(condition, stack, false);
   }

   public static List<LootTableParser.LootConditionResult> parseLootCondition(LootItemCondition condition, ItemStack stack, boolean parentIsAlternative) {
      LootItemConditionType type;
      try {
         type = condition.getType();
      } catch (Exception e) {
         EMILoot.LOGGER.error("failed to determine a condition type for stack {} in table {}", stack.getHoverName(), currentTable);
         return Collections.singletonList(LootTableParser.LootConditionResult.EMPTY);
      }

      try {
         return LootParserRegistry.parseCondition(condition, type, stack, parentIsAlternative);
      } catch (Exception e) {
         EMILoot.LOGGER
            .error("Failed to parse LootCondition of type {} for stack {} in table {}", new Object[]{condition.getType(), stack.getHoverName(), currentTable});
         EMILoot.LOGGER.error("Thrown Error:", e);
         return Collections.singletonList(LootTableParser.LootConditionResult.EMPTY);
      }
   }

   public static Component compileConditionTexts(ItemStack stack, List<LootTableParser.LootConditionResult> results) {
      MutableComponent finalText = LText.empty();
      int size = results.size();

      for (int i = 0; i < size; i++) {
         LootTableParser.LootConditionResult result = results.get(i);
         Component resultText = result.text.process(stack, null).text();
         if (i == 0) {
            finalText = resultText.copy();
         } else {
            finalText.append(resultText);
         }

         if (i < size - 1) {
            finalText.append(LText.translatable("emi_loot.and"));
         }
      }

      return finalText;
   }

   static {
      Object2BooleanOpenHashMap<LootTableParser.PostProcessor> map = new Object2BooleanOpenHashMap();

      for (LootTableParser.PostProcessor value : LootTableParser.PostProcessor.values()) {
         map.put(value, false);
      }

      postProcessors = map;
   }

   public record FunctionApplierResult(List<LootTableParser.ItemEntryResult> conditionalResults, List<TextKey> functionTexts, ItemStack stack) {
   }

   public record ItemEntryResult(ItemStack item, int weight, List<TextKey> conditions, List<TextKey> functions) {
   }

   public record LootConditionResult(TextKey text) {
      public static LootTableParser.LootConditionResult EMPTY = new LootTableParser.LootConditionResult(TextKey.empty());

      public TextKey getText() {
         return this.text;
      }
   }

   public record LootFunctionResult(TextKey text, ItemStack stack, List<TextKey> conditions) {
      public static LootTableParser.LootFunctionResult EMPTY = new LootTableParser.LootFunctionResult(TextKey.empty(), ItemStack.EMPTY, new LinkedList<>());

      public boolean skip() {
         return this.conditions.isEmpty() && this.text.skip();
      }
   }

   public enum PostProcessor {
      TAG;
   }
}
