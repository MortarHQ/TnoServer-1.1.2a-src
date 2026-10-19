package net.p3pp3rf1y.sophisticatedbackpacks.registry.tool;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.fml.ModList;
import net.p3pp3rf1y.sophisticatedbackpacks.SophisticatedBackpacks;
import net.p3pp3rf1y.sophisticatedbackpacks.registry.IRegistryDataLoader;
import net.p3pp3rf1y.sophisticatedcore.util.RegistryHelper;

public class ToolRegistry {
   private static final String TOOLS_PROPERTY = "tools";
   private static final Set<String> modsWithMapping = new HashSet<>();
   private static final ToolRegistry.ToolMapping<Block, BlockContext> BLOCK_TOOL_MAPPING = new ToolRegistry.ToolMapping<>(
      BuiltInRegistries.BLOCK, BlockContext::getBlock
   );
   private static final ToolRegistry.ToolMapping<EntityType<?>, Entity> ENTITY_TOOL_MAPPING = new ToolRegistry.ToolMapping<>(
      BuiltInRegistries.ENTITY_TYPE, Entity::getType
   );

   private ToolRegistry() {
   }

   public static boolean isToolForBlock(ItemStack stack, Block block, Level level, BlockState blockState, BlockPos pos) {
      return BLOCK_TOOL_MAPPING.isToolFor(stack, block, () -> new BlockContext(level, blockState, block, pos));
   }

   public static boolean isToolForEntity(ItemStack stack, Entity entity) {
      return ENTITY_TOOL_MAPPING.isToolFor(stack, entity.getType(), () -> entity);
   }

   protected static Tuple<Set<Item>, Set<Predicate<ItemStack>>> getItemsAndItemPredicates(Entry<String, JsonElement> property) {
      if (property.getValue().isJsonArray()) {
         JsonArray toolArray = GsonHelper.convertToJsonArray(property.getValue(), "");
         return getItemsAndItemPredicates(toolArray);
      } else {
         SophisticatedBackpacks.LOGGER.error("Invalid tools list - needs to be an array {}", property.getValue());
         return new Tuple(Collections.emptySet(), Collections.emptySet());
      }
   }

   protected static Tuple<Set<Item>, Set<Predicate<ItemStack>>> getItemsAndItemPredicates(JsonArray toolArray) {
      Set<Item> items = new HashSet<>();
      Set<Predicate<ItemStack>> itemPredicates = new HashSet<>();

      for (JsonElement jsonElement : toolArray) {
         if (jsonElement.isJsonPrimitive()) {
            ResourceLocation itemName = ResourceLocation.parse(jsonElement.getAsString());
            if (!BuiltInRegistries.ITEM.containsKey(itemName)) {
               SophisticatedBackpacks.LOGGER.debug("{} isn't loaded in item registry, skipping ...", itemName);
            }

            Item item = (Item)BuiltInRegistries.ITEM.get(itemName);
            items.add(item);
         } else if (jsonElement.isJsonObject()) {
            Matchers.getItemMatcher(jsonElement).ifPresent(itemPredicates::add);
         }
      }

      return new Tuple(items, itemPredicates);
   }

   public static void addModWithMapping(String modId) {
      modsWithMapping.add(modId);
   }

   public static class BlockToolsLoader extends ToolRegistry.ToolsLoaderBase<Block, BlockContext> {
      public BlockToolsLoader() {
         super(
            Matchers.getBlockMatcherFactories(),
            ToolRegistry.BLOCK_TOOL_MAPPING,
            BuiltInRegistries.BLOCK,
            BuiltInRegistries.BLOCK::getOptional,
            "block_tools",
            "blocks"
         );
      }
   }

   public static class EntityToolsLoader extends ToolRegistry.ToolsLoaderBase<EntityType<?>, Entity> {
      public EntityToolsLoader() {
         super(
            Matchers.getEntityMatcherFactories(),
            ToolRegistry.ENTITY_TOOL_MAPPING,
            BuiltInRegistries.ENTITY_TYPE,
            BuiltInRegistries.ENTITY_TYPE::getOptional,
            "entity_tools",
            "entities"
         );
      }
   }

   private static class ToolMapping<V, C> {
      private final Registry<V> registry;
      private final Function<C, V> getObjectFromContext;
      private final Map<V, Set<Item>> notToolCache = new HashMap<>();
      private final Map<V, Set<Item>> objectTools = new HashMap<>();
      private final Map<V, Set<Predicate<ItemStack>>> objectToolPredicates = new HashMap<>();
      private final Map<Predicate<C>, Set<Item>> objectPredicateTools = new HashMap<>();
      private final Map<Predicate<C>, Set<Predicate<ItemStack>>> objectPredicateToolPredicates = new HashMap<>();

      public ToolMapping(Registry<V> registry, Function<C, V> getObjectFromContext) {
         this.registry = registry;
         this.getObjectFromContext = getObjectFromContext;
      }

      private void addObjectPredicateTools(Tuple<Set<Item>, Set<Predicate<ItemStack>>> tools, Predicate<C> predicate) {
         ((Set)tools.getA()).forEach(t -> this.objectPredicateTools.computeIfAbsent(predicate, p -> new HashSet<>()).add(t));
         ((Set)tools.getB()).forEach(tp -> this.objectPredicateToolPredicates.computeIfAbsent(predicate, p -> new HashSet<>()).add(tp));
      }

      private void addObjectTools(Tuple<Set<Item>, Set<Predicate<ItemStack>>> tools, V object) {
         ((Set)tools.getA()).forEach(t -> this.objectTools.computeIfAbsent(object, b -> new HashSet<>()).add(t));
         ((Set)tools.getB()).forEach(tp -> this.objectToolPredicates.computeIfAbsent(object, b -> new HashSet<>()).add(tp));
      }

      public void clear() {
         this.notToolCache.clear();
         this.objectTools.clear();
         this.objectToolPredicates.clear();
         this.objectPredicateTools.clear();
         this.objectPredicateToolPredicates.clear();
      }

      public boolean isToolFor(ItemStack stack, V object, Supplier<C> getContext) {
         Item item = stack.getItem();
         if (this.objectTools.containsKey(object) && this.objectTools.get(object).contains(item)) {
            return true;
         }

         if (this.notToolCache.containsKey(object) && this.notToolCache.get(object).contains(item)) {
            return false;
         }

         if (this.tryToMatchAgainstObjectToolPredicates(stack, object)) {
            return true;
         }

         C context = getContext.get();
         if (this.tryToMatchAgainstObjectPredicateTools(item, context)) {
            return true;
         }

         if (this.tryToMatchAgainstObjectPredicateToolPredicates(stack, context)) {
            return true;
         }

         if (this.tryToMatchNoMappingMod(stack, object)) {
            return true;
         }

         this.notToolCache.computeIfAbsent(object, b -> new HashSet<>()).add(item);
         return false;
      }

      private boolean tryToMatchNoMappingMod(ItemStack stack, V object) {
         if (this.isNoMappingModAndNonStackableItemFromSameMod(stack, object)) {
            this.addObjectToolMapping(object, stack.getItem());
            return true;
         } else {
            return false;
         }
      }

      private boolean isNoMappingModAndNonStackableItemFromSameMod(ItemStack stack, V object) {
         return RegistryHelper.getRegistryName(this.registry, object)
               .map(
                  rn -> !rn.getNamespace().equals("minecraft")
                     && !ToolRegistry.modsWithMapping.contains(rn.getNamespace())
                     && RegistryHelper.getRegistryName(BuiltInRegistries.ITEM, stack.getItem())
                        .map(itemRegistryName -> itemRegistryName.getNamespace().equals(rn.getNamespace()))
                        .orElse(false)
               )
               .orElse(false)
            && stack.getMaxStackSize() == 1;
      }

      private boolean tryToMatchAgainstObjectPredicateToolPredicates(ItemStack stack, C context) {
         for (Entry<Predicate<C>, Set<Predicate<ItemStack>>> entry : this.objectPredicateToolPredicates.entrySet()) {
            if (entry.getKey().test(context)) {
               Set<Predicate<ItemStack>> toolPredicates = entry.getValue();
               if (this.tryToMatchTools(stack, this.getObjectFromContext.apply(context), toolPredicates)) {
                  return true;
               }
            }
         }

         return false;
      }

      private boolean tryToMatchAgainstObjectToolPredicates(ItemStack stack, V object) {
         if (this.objectToolPredicates.containsKey(object)) {
            Set<Predicate<ItemStack>> toolPredicates = this.objectToolPredicates.get(object);
            return this.tryToMatchTools(stack, object, toolPredicates);
         } else {
            return false;
         }
      }

      private boolean tryToMatchAgainstObjectPredicateTools(Item item, C context) {
         for (Entry<Predicate<C>, Set<Item>> entry : this.objectPredicateTools.entrySet()) {
            if (entry.getKey().test(context) && entry.getValue().contains(item)) {
               this.addObjectToolMapping(this.getObjectFromContext.apply(context), item);
               return true;
            }
         }

         return false;
      }

      private boolean tryToMatchTools(ItemStack stack, V object, Set<Predicate<ItemStack>> toolPredicates) {
         for (Predicate<ItemStack> itemPredicate : toolPredicates) {
            if (itemPredicate.test(stack)) {
               this.objectTools.computeIfAbsent(object, b -> new HashSet<>()).add(stack.getItem());
               return true;
            }
         }

         return false;
      }

      private void addObjectToolMapping(V block, Item item) {
         this.objectTools.computeIfAbsent(block, b -> new HashSet<>()).add(item);
      }

      public Map<V, Set<Item>> getObjectTools() {
         return this.objectTools;
      }

      public void addModPredicateTools(String modId, Tuple<Set<Item>, Set<Predicate<ItemStack>>> tools) {
         this.addObjectPredicateTools(tools, new ModMatcher<>(this.registry, modId, this.getObjectFromContext));
      }
   }

   private abstract static class ToolsLoaderBase<V, C> implements IRegistryDataLoader {
      private final List<IMatcherFactory<C>> objectMatcherFactories;
      private final ToolRegistry.ToolMapping<V, C> toolMapping;
      private final Registry<V> registry;
      private final Function<ResourceLocation, Optional<V>> getObjectFromRegistry;
      private final String name;
      private final String objectJsonArrayName;

      public ToolsLoaderBase(
         List<IMatcherFactory<C>> objectMatcherFactories,
         ToolRegistry.ToolMapping<V, C> toolMapping,
         Registry<V> registry,
         Function<ResourceLocation, Optional<V>> getObjectFromRegistry,
         String name,
         String objectJsonArrayName
      ) {
         this.objectMatcherFactories = objectMatcherFactories;
         this.toolMapping = toolMapping;
         this.registry = registry;
         this.getObjectFromRegistry = getObjectFromRegistry;
         this.name = name;
         this.objectJsonArrayName = objectJsonArrayName;
      }

      @Override
      public String getName() {
         return this.name;
      }

      @Override
      public void parse(JsonObject json, @Nullable String modId) {
         for (JsonElement jsonElement : GsonHelper.getAsJsonArray(json, this.name)) {
            if (jsonElement.isJsonObject()) {
               JsonObject entry = jsonElement.getAsJsonObject();
               this.parseEntry(entry);
            }
         }

         this.toolMapping
            .getObjectTools()
            .keySet()
            .forEach(object -> RegistryHelper.getRegistryName(this.registry, object).ifPresent(rn -> ToolRegistry.modsWithMapping.add(rn.getNamespace())));
      }

      @Override
      public void clear() {
         this.toolMapping.clear();
         ToolRegistry.modsWithMapping.clear();
      }

      private void parseEntry(JsonObject entry) {
         if (entry.size() == 1) {
            this.parseFromProperty(entry);
         } else if (entry.size() == 2 && entry.has(this.objectJsonArrayName) && entry.has("tools")) {
            this.parseFromArrays(GsonHelper.getAsJsonArray(entry, this.objectJsonArrayName), GsonHelper.getAsJsonArray(entry, "tools"));
         } else {
            SophisticatedBackpacks.LOGGER
               .error(
                  "Invalid block tools entry - needs to have either 1 array property with mod/entity name or \"{}\" and \"tools\" array properties {}",
                  this.objectJsonArrayName,
                  entry
               );
         }
      }

      private void parseFromArrays(JsonArray blocksArray, JsonArray toolsArray) {
         Tuple<Set<Item>, Set<Predicate<ItemStack>>> tools = ToolRegistry.getItemsAndItemPredicates(toolsArray);
         if (!((Set)tools.getA()).isEmpty() || !((Set)tools.getB()).isEmpty()) {
            for (JsonElement jsonElement : blocksArray) {
               if (jsonElement.isJsonPrimitive() && jsonElement.getAsString().contains(":")) {
                  this.parseObjectEntry(tools, jsonElement.getAsString());
               } else {
                  this.parseObjectPredicateEntry(tools, jsonElement);
               }
            }
         }
      }

      private void parseObjectPredicateEntry(Tuple<Set<Item>, Set<Predicate<ItemStack>>> tools, JsonElement jsonElement) {
         for (IMatcherFactory<C> blockMatcherFactory : this.objectMatcherFactories) {
            if (blockMatcherFactory.appliesTo(jsonElement)) {
               blockMatcherFactory.getPredicate(jsonElement).ifPresent(predicate -> this.toolMapping.addObjectPredicateTools(tools, (Predicate<C>)predicate));
               break;
            }
         }
      }

      private void parseObjectEntry(Tuple<Set<Item>, Set<Predicate<ItemStack>>> tools, String objectName) {
         ResourceLocation registryName = ResourceLocation.parse(objectName);
         Optional<V> objectOptional = this.getObjectFromRegistry.apply(registryName);
         if (objectOptional.isPresent()) {
            this.toolMapping.addObjectTools(tools, objectOptional.get());
         } else {
            SophisticatedBackpacks.LOGGER.debug("{} doesn't exist in registry, skipping ...", objectName);
         }
      }

      private void parseFromProperty(JsonObject entry) {
         for (Entry<String, JsonElement> property : entry.entrySet()) {
            if (property.getKey().contains(":")) {
               this.parseObjectTools(property);
            } else {
               this.parseModTools(property);
            }
         }
      }

      private void parseModTools(Entry<String, JsonElement> property) {
         String modId = property.getKey();
         if (!ModList.get().isLoaded(modId)) {
            SophisticatedBackpacks.LOGGER.debug("{} mod isn't loaded, skipping ... {} ", modId, property);
         } else {
            Tuple<Set<Item>, Set<Predicate<ItemStack>>> tools = ToolRegistry.getItemsAndItemPredicates(property);
            if (!((Set)tools.getA()).isEmpty() || !((Set)tools.getB()).isEmpty()) {
               this.toolMapping.addModPredicateTools(modId, tools);
            }
         }
      }

      private void parseObjectTools(Entry<String, JsonElement> property) {
         Tuple<Set<Item>, Set<Predicate<ItemStack>>> tools = ToolRegistry.getItemsAndItemPredicates(property);
         if (!((Set)tools.getA()).isEmpty() || !((Set)tools.getB()).isEmpty()) {
            this.parseObjectEntry(tools, property.getKey());
         }
      }
   }
}
