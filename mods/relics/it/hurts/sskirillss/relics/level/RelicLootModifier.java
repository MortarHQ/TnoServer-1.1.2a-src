package it.hurts.sskirillss.relics.level;

import com.google.common.base.Suppliers;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.hurts.sskirillss.relics.init.ConfigRegistry;
import it.hurts.sskirillss.relics.init.LootCodecRegistry;
import it.hurts.sskirillss.relics.items.relics.base.IRelicItem;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.LootEntry;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import javax.annotation.Nonnull;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.jetbrains.annotations.NotNull;

public class RelicLootModifier extends LootModifier {
   public static final Supplier<MapCodec<RelicLootModifier>> CODEC = Suppliers.memoize(
      () -> RecordCodecBuilder.mapCodec(inst -> codecStart(inst).apply(inst, RelicLootModifier::new))
   );
   public static final List<RelicLootModifier.LootEntryCache> LOOT_ENTRIES = new ArrayList<>();

   public RelicLootModifier(LootItemCondition[] conditionsIn) {
      super(conditionsIn);
   }

   @Nonnull
   @NotNull
   protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
      Entity entity = (Entity)context.getParamOrNull(LootContextParams.THIS_ENTITY);
      Vec3 vec = (Vec3)context.getParamOrNull(LootContextParams.ORIGIN);
      String table = context.getQueriedLootTableId().toString();
      if (vec != null && entity != null) {
         RandomSource random = context.getRandom();
         if (random.nextDouble() > ConfigRegistry.LOOT_CONFIG.getRelicGenChance()) {
            return generatedLoot;
         }

         BlockPos pos = new BlockPos((int)vec.x(), (int)vec.y(), (int)vec.z());
         Level level = entity.level();
         List<RelicLootModifier.LootEntryCache> entries = new ArrayList<>();

         for (RelicLootModifier.LootEntryCache entry : LOOT_ENTRIES) {
            if (entry.getTables().stream().anyMatch(matcher -> matcher.matches(table))
               && entry.getDimensions().stream().anyMatch(matcher -> matcher.matches(level.dimension().location().toString()))
               && entry.getBiomes().stream().anyMatch(matcher -> matcher.matches(level.getBiome(pos).getRegisteredName()))) {
               entries.add(entry);
            }
         }

         if (entries.isEmpty()) {
            return generatedLoot;
         }

         double weight = entries.stream().mapToDouble(RelicLootModifier.LootEntryCache::getWeight).sum();
         if (weight <= 0.0) {
            return generatedLoot;
         }

         double range = random.nextDouble() * weight;

         for (RelicLootModifier.LootEntryCache entry : entries) {
            range -= entry.getWeight();
            if (range <= 0.0) {
               generatedLoot.add(entry.getItem().getDefaultInstance());
               break;
            }
         }

         return generatedLoot;
      } else {
         return generatedLoot;
      }
   }

   public MapCodec<? extends IGlobalLootModifier> codec() {
      return (MapCodec<? extends IGlobalLootModifier>)LootCodecRegistry.RELIC_LOOT.get();
   }

   public static void processRelicCache(IRelicItem relic) {
      MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
      if (server != null) {
         LOOT_ENTRIES.removeIf(entryx -> entryx.getItem() == relic);

         for (LootEntry entry : relic.getLootData().getEntries()) {
            Item item = relic.getItem();
            if (item != null) {
               LOOT_ENTRIES.add(
                  new RelicLootModifier.LootEntryCache(
                     compileRegex(entry.getDimensions()), compileRegex(entry.getBiomes()), compileRegex(entry.getTables()), entry.getWeight(), item
                  )
               );
            }
         }
      }
   }

   private static List<RelicLootModifier.MatcherEntry> compileRegex(List<String> patterns) {
      List<RelicLootModifier.MatcherEntry> entries = new ArrayList<>();

      for (String pattern : patterns) {
         try {
            entries.add(new RelicLootModifier.RegexEntry(Pattern.compile(pattern)));
         } catch (PatternSyntaxException e) {
            entries.add(new RelicLootModifier.StringEntry(pattern));
         }
      }

      return entries;
   }

   @EventBusSubscriber
   public static class Events {
      @SubscribeEvent
      public static void onServerStarted(ServerStartedEvent event) {
         if (RelicLootModifier.LOOT_ENTRIES.isEmpty()) {
            for (Entry<ResourceKey<Item>, Item> entry : BuiltInRegistries.ITEM.entrySet()) {
               if (entry.getValue() instanceof IRelicItem relic) {
                  RelicLootModifier.processRelicCache(relic);
               }
            }
         }
      }
   }

   public static class LootEntryCache {
      private List<RelicLootModifier.MatcherEntry> dimensions;
      private List<RelicLootModifier.MatcherEntry> biomes;
      private List<RelicLootModifier.MatcherEntry> tables;
      private int weight;
      private Item item;

      public List<RelicLootModifier.MatcherEntry> getDimensions() {
         return this.dimensions;
      }

      public List<RelicLootModifier.MatcherEntry> getBiomes() {
         return this.biomes;
      }

      public List<RelicLootModifier.MatcherEntry> getTables() {
         return this.tables;
      }

      public int getWeight() {
         return this.weight;
      }

      public Item getItem() {
         return this.item;
      }

      public void setDimensions(List<RelicLootModifier.MatcherEntry> dimensions) {
         this.dimensions = dimensions;
      }

      public void setBiomes(List<RelicLootModifier.MatcherEntry> biomes) {
         this.biomes = biomes;
      }

      public void setTables(List<RelicLootModifier.MatcherEntry> tables) {
         this.tables = tables;
      }

      public void setWeight(int weight) {
         this.weight = weight;
      }

      public void setItem(Item item) {
         this.item = item;
      }

      @Override
      public boolean equals(Object o) {
         if (o == this) {
            return true;
         } else if (!(o instanceof RelicLootModifier.LootEntryCache other)) {
            return false;
         } else {
            if (!other.canEqual(this)) {
               return false;
            }

            if (this.getWeight() != other.getWeight()) {
               return false;
            }

            Object this$dimensions = this.getDimensions();
            Object other$dimensions = other.getDimensions();
            if (this$dimensions == null ? other$dimensions == null : this$dimensions.equals(other$dimensions)) {
               Object this$biomes = this.getBiomes();
               Object other$biomes = other.getBiomes();
               if (this$biomes == null ? other$biomes == null : this$biomes.equals(other$biomes)) {
                  Object this$tables = this.getTables();
                  Object other$tables = other.getTables();
                  if (this$tables == null ? other$tables == null : this$tables.equals(other$tables)) {
                     Object this$item = this.getItem();
                     Object other$item = other.getItem();
                     return this$item == null ? other$item == null : this$item.equals(other$item);
                  } else {
                     return false;
                  }
               } else {
                  return false;
               }
            } else {
               return false;
            }
         }
      }

      protected boolean canEqual(Object other) {
         return other instanceof RelicLootModifier.LootEntryCache;
      }

      @Override
      public int hashCode() {
         int PRIME = 59;
         int result = 1;
         result = result * 59 + this.getWeight();
         Object $dimensions = this.getDimensions();
         result = result * 59 + ($dimensions == null ? 43 : $dimensions.hashCode());
         Object $biomes = this.getBiomes();
         result = result * 59 + ($biomes == null ? 43 : $biomes.hashCode());
         Object $tables = this.getTables();
         result = result * 59 + ($tables == null ? 43 : $tables.hashCode());
         Object $item = this.getItem();
         return result * 59 + ($item == null ? 43 : $item.hashCode());
      }

      @Override
      public String toString() {
         return "RelicLootModifier.LootEntryCache(dimensions="
            + this.getDimensions()
            + ", biomes="
            + this.getBiomes()
            + ", tables="
            + this.getTables()
            + ", weight="
            + this.getWeight()
            + ", item="
            + this.getItem()
            + ")";
      }

      public LootEntryCache(
         List<RelicLootModifier.MatcherEntry> dimensions,
         List<RelicLootModifier.MatcherEntry> biomes,
         List<RelicLootModifier.MatcherEntry> tables,
         int weight,
         Item item
      ) {
         this.dimensions = dimensions;
         this.biomes = biomes;
         this.tables = tables;
         this.weight = weight;
         this.item = item;
      }
   }

   private abstract static sealed class MatcherEntry permits RelicLootModifier.RegexEntry, RelicLootModifier.StringEntry {
      public abstract boolean matches(String var1);
   }

   private static non-sealed class RegexEntry extends RelicLootModifier.MatcherEntry {
      private Pattern entry;

      @Override
      public boolean matches(String input) {
         return this.entry.matcher(input).matches();
      }

      public Pattern getEntry() {
         return this.entry;
      }

      public void setEntry(Pattern entry) {
         this.entry = entry;
      }

      @Override
      public String toString() {
         return "RelicLootModifier.RegexEntry(entry=" + this.getEntry() + ")";
      }

      public RegexEntry(Pattern entry) {
         this.entry = entry;
      }

      @Override
      public boolean equals(Object o) {
         if (o == this) {
            return true;
         } else if (!(o instanceof RelicLootModifier.RegexEntry other)) {
            return false;
         } else {
            if (!other.canEqual(this)) {
               return false;
            }

            if (!super.equals(o)) {
               return false;
            }

            Object this$entry = this.getEntry();
            Object other$entry = other.getEntry();
            return this$entry == null ? other$entry == null : this$entry.equals(other$entry);
         }
      }

      protected boolean canEqual(Object other) {
         return other instanceof RelicLootModifier.RegexEntry;
      }

      @Override
      public int hashCode() {
         int PRIME = 59;
         int result = super.hashCode();
         Object $entry = this.getEntry();
         return result * 59 + ($entry == null ? 43 : $entry.hashCode());
      }
   }

   private static non-sealed class StringEntry extends RelicLootModifier.MatcherEntry {
      private String entry;

      @Override
      public boolean matches(String input) {
         return this.entry.equals(input);
      }

      public String getEntry() {
         return this.entry;
      }

      public void setEntry(String entry) {
         this.entry = entry;
      }

      @Override
      public String toString() {
         return "RelicLootModifier.StringEntry(entry=" + this.getEntry() + ")";
      }

      public StringEntry(String entry) {
         this.entry = entry;
      }

      @Override
      public boolean equals(Object o) {
         if (o == this) {
            return true;
         } else if (!(o instanceof RelicLootModifier.StringEntry other)) {
            return false;
         } else {
            if (!other.canEqual(this)) {
               return false;
            }

            if (!super.equals(o)) {
               return false;
            }

            Object this$entry = this.getEntry();
            Object other$entry = other.getEntry();
            return this$entry == null ? other$entry == null : this$entry.equals(other$entry);
         }
      }

      protected boolean canEqual(Object other) {
         return other instanceof RelicLootModifier.StringEntry;
      }

      @Override
      public int hashCode() {
         int PRIME = 59;
         int result = super.hashCode();
         Object $entry = this.getEntry();
         return result * 59 + ($entry == null ? 43 : $entry.hashCode());
      }
   }
}
