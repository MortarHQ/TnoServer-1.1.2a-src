package net.blay09.mods.waystones.item;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import net.blay09.mods.balm.api.DeferredObject;
import net.blay09.mods.balm.api.item.BalmItems;
import net.blay09.mods.waystones.block.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class ModItems {
   public static DeferredObject<CreativeModeTab> creativeModeTab;
   public static Item returnScroll;
   public static Item blankScroll;
   public static Item boundScroll;
   public static Item warpScroll;
   public static Item warpStone;
   public static Item warpDust;
   public static Item dormantShard;
   public static Item attunedShard;
   public static Item deepslateShard;
   public static Item crumblingAttunedShard;

   public static void initialize(BalmItems items) {
      items.registerItem(() -> returnScroll = new ReturnScrollItem(items.itemProperties()), id("return_scroll"));
      items.registerItem(() -> blankScroll = new BlankScrollItem(items.itemProperties()), id("blank_scroll"));
      items.registerItem(() -> boundScroll = new BoundScrollItem(items.itemProperties()), id("bound_scroll"), null);
      items.registerItem(() -> warpScroll = new WarpScrollItem(items.itemProperties()), id("warp_scroll"));
      items.registerItem(() -> warpStone = new WarpStoneItem(items.itemProperties()), id("warp_stone"));
      items.registerItem(() -> warpDust = new WarpDustItem(items.itemProperties()), id("warp_dust"));
      items.registerItem(() -> dormantShard = new ShardItem(items.itemProperties()), id("dormant_shard"));
      items.registerItem(() -> attunedShard = new AttunedShardItem(items.itemProperties()), id("attuned_shard"), null);
      items.registerItem(() -> deepslateShard = new ShardItem(items.itemProperties()), id("deepslate_shard"), null);
      items.registerItem(() -> crumblingAttunedShard = new CrumblingAttunedShardItem(items.itemProperties()), id("crumbling_attuned_shard"), null);
      creativeModeTab = items.registerCreativeModeTab(() -> new ItemStack(ModBlocks.waystone), id("waystones"));
      items.setCreativeModeTabSorting(
         id("waystones"),
         new Comparator<ItemLike>() {
            private static final String[] patternStrings = new String[]{
               "waystone",
               "white_portstone",
               "red_sharestone",
               "warp_plate",
               "blank_scroll",
               "return_scroll",
               "warp_scroll",
               "warp_stone",
               "warp_dust",
               "dormant_shard",
               "deepslate_shard",
               ".+_waystone",
               ".+_sharestone",
               ".+_portstone",
               "attuned_shard",
               "crumbling_attuned_shard"
            };
            private static final Map<String, Integer> indexMap = new HashMap<>();
            private static final Map<Pattern, Integer> patternIndexMap = new HashMap<>();

            private static int getIndex(String name) {
               Integer index = indexMap.get(name);
               if (index != null) {
                  return index;
               }

               for (Entry<Pattern, Integer> entry : patternIndexMap.entrySet()) {
                  if (entry.getKey().matcher(name).matches()) {
                     return entry.getValue();
                  }
               }

               return -1;
            }

            public int compare(ItemLike o1, ItemLike o2) {
               ResourceLocation id1 = BuiltInRegistries.ITEM.getKey(o1.asItem());
               ResourceLocation id2 = BuiltInRegistries.ITEM.getKey(o2.asItem());
               String name1 = id1.getPath();
               String name2 = id2.getPath();
               int index1 = getIndex(name1);
               int index2 = getIndex(name2);
               if (index1 != -1 && index2 != -1) {
                  return Integer.compare(index1, index2);
               } else if (index1 != -1) {
                  return -1;
               } else {
                  return index2 != -1 ? 1 : name1.compareTo(name2);
               }
            }

            static {
               for (int i = 0; i < patternStrings.length; i++) {
                  String patternString = patternStrings[i];
                  indexMap.put(patternString, i);
                  patternIndexMap.put(Pattern.compile(patternString), i);
               }
            }
         }
      );
   }

   private static ResourceLocation id(String name) {
      return ResourceLocation.fromNamespaceAndPath("waystones", name);
   }
}
