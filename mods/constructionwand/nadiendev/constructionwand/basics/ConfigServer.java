package nadiendev.constructionwand.basics;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import nadiendev.constructionwand.items.ModItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;
import net.neoforged.neoforge.common.ModConfigSpec.Builder;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;
import net.neoforged.neoforge.common.ModConfigSpec.IntValue;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ConfigServer {
   public static final ModConfigSpec SPEC;
   public static final IntValue LIMIT_CREATIVE;
   public static final IntValue MAX_RANGE;
   public static final IntValue UNDO_HISTORY;
   public static final BooleanValue ANGEL_FALLING;
   public static final ConfigValue<List<?>> SIMILAR_BLOCKS;
   private static final String[] SIMILAR_BLOCKS_DEFAULT = new String[]{
      "minecraft:dirt;minecraft:grass_block;minecraft:coarse_dirt;minecraft:podzol;minecraft:mycelium;minecraft:farmland;minecraft:dirt_path;minecraft:rooted_dirt"
   };
   public static final BooleanValue TE_WHITELIST;
   public static final ConfigValue<List<?>> TE_LIST;
   private static final String[] TE_LIST_DEFAULT = new String[]{"chiselsandbits"};
   private static final HashMap<ResourceLocation, ConfigServer.WandProperties> wandProperties = new HashMap<>();

   public static ConfigServer.WandProperties getWandProperties(Item wand) {
      return wandProperties.getOrDefault(BuiltInRegistries.ITEM.getKey(wand), ConfigServer.WandProperties.DEFAULT);
   }

   static {
      Builder builder = new Builder();
      builder.comment(
         new String[]{
            "This is the Server config for ConstructionWand.",
            "If you're not familiar with Forge's new split client/server config, let me explain:",
            "Client config is stored in the /config folder and only contains client specific settings like graphics and keybinds.",
            "Mod behavior is configured in the Server config, which is world-specific and thus located",
            "in the /saves/myworld/serverconfig folder. If you want to change the serverconfig for all",
            "new worlds, copy the config files in the /defaultconfigs folder."
         }
      );
      new ConfigServer.WandProperties(builder, ModItems.WAND_STONE, Tiers.STONE.getUses(), 9, 0, 0, false);
      new ConfigServer.WandProperties(builder, ModItems.WAND_IRON, Tiers.IRON.getUses(), 27, 2, 9, true);
      new ConfigServer.WandProperties(builder, ModItems.WAND_DIAMOND, Tiers.DIAMOND.getUses(), 128, 8, 25, true);
      new ConfigServer.WandProperties(builder, ModItems.WAND_NETHERITE, Tiers.NETHERITE.getUses(), 512, 15, 75, true);
      new ConfigServer.WandProperties(builder, ModItems.WAND_INFINITY, 0, 1024, 16, 81, true);
      builder.push("misc");
      builder.comment("Block limit for Infinity Wand used in creative mode");
      LIMIT_CREATIVE = builder.defineInRange("InfinityWandCreative", 2048, 1, Integer.MAX_VALUE);
      builder.comment("Maximum placement range (0: unlimited). Affects all wands and is meant for lag prevention, not game balancing.");
      MAX_RANGE = builder.defineInRange("MaxRange", 100, 0, Integer.MAX_VALUE);
      builder.comment("Number of operations that can be undone");
      UNDO_HISTORY = builder.defineInRange("UndoHistory", 3, 0, Integer.MAX_VALUE);
      builder.comment("Place blocks below you while falling > 10 blocks with angel core (Can be used to save you from drops/the void)");
      ANGEL_FALLING = builder.define("AngelFalling", false);
      builder.comment("Blocks to treat equally when in Similar mode. Enter block IDs seperated by ;");
      SIMILAR_BLOCKS = builder.defineList("SimilarBlocks", Arrays.asList(SIMILAR_BLOCKS_DEFAULT), obj -> true);
      builder.pop();
      builder.push("tileentity");
      builder.comment(
         new String[]{
            "White/Blacklist for Tile Entities. Allow/Prevent blocks with TEs from being placed by wand.",
            "You can either add block ids like minecraft:chest or mod ids like minecraft"
         }
      );
      TE_LIST = builder.defineList("TEList", Arrays.asList(TE_LIST_DEFAULT), obj -> true);
      builder.comment("If set to TRUE, treat TEList as a whitelist, otherwise blacklist");
      TE_WHITELIST = builder.define("TEWhitelist", false);
      builder.pop();
      SPEC = builder.build();
   }

   public static class WandProperties {
      public static final ConfigServer.WandProperties DEFAULT = new ConfigServer.WandProperties(null, null, null, null, null);
      private final IntValue durability;
      private final IntValue limit;
      private final IntValue angel;
      private final IntValue destruction;
      private final BooleanValue upgradeable;

      private WandProperties(IntValue durability, IntValue limit, IntValue angel, IntValue destruction, BooleanValue upgradeable) {
         this.durability = durability;
         this.limit = limit;
         this.angel = angel;
         this.destruction = destruction;
         this.upgradeable = upgradeable;
      }

      public WandProperties(
         Builder builder,
         DeferredHolder<Item, ? extends Item> wandSupplier,
         int defDurability,
         int defLimit,
         int defAngel,
         int defDestruction,
         boolean defUpgradeable
      ) {
         ResourceLocation registryName = wandSupplier.getId();
         builder.push(registryName.getPath());
         if (defDurability > 0) {
            builder.comment("Wand durability");
            this.durability = builder.defineInRange("durability", defDurability, 1, Integer.MAX_VALUE);
         } else {
            this.durability = null;
         }

         builder.comment("Wand block limit");
         this.limit = builder.defineInRange("limit", defLimit, 1, Integer.MAX_VALUE);
         builder.comment("Max placement distance with angel core (0 to disable angel core)");
         this.angel = builder.defineInRange("angel", defAngel, 0, Integer.MAX_VALUE);
         builder.comment("Wand destruction block limit (0 to disable destruction core)");
         this.destruction = builder.defineInRange("destruction", defDestruction, 0, Integer.MAX_VALUE);
         builder.comment("Allow wand upgrading by putting the wand together with a wand core in a crafting grid.");
         this.upgradeable = builder.define("upgradeable", defUpgradeable);
         builder.pop();
         ConfigServer.wandProperties.put(registryName, this);
      }

      public int getDurability() {
         return this.durability == null ? -1 : (Integer)this.durability.get();
      }

      public int getLimit() {
         return this.limit == null ? 0 : (Integer)this.limit.get();
      }

      public int getAngel() {
         return this.angel == null ? 0 : (Integer)this.angel.get();
      }

      public int getDestruction() {
         return this.destruction == null ? 0 : (Integer)this.destruction.get();
      }

      public boolean isUpgradeable() {
         return this.upgradeable != null && (Boolean)this.upgradeable.get();
      }
   }
}
