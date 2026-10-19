package nadiendev.constructionwand.basics;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import nadiendev.constructionwand.ConstructionWand;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class ReplacementRegistry {
   private static final HashSet<HashSet<Item>> replacements = new HashSet<>();

   public static void init() {
      replacements.clear();

      for (Object key : (List)ConfigServer.SIMILAR_BLOCKS.get()) {
         if (key instanceof String) {
            HashSet<Item> set = new HashSet<>();

            for (String id : ((String)key).split(";")) {
               Item item = (Item)BuiltInRegistries.ITEM.get(ResourceLocation.tryParse(id));
               if (item != null && item != Items.AIR) {
                  set.add(item);
               } else {
                  ConstructionWand.LOGGER.warn("Replacement Registry: Could not find item " + id);
               }
            }

            if (!set.isEmpty()) {
               replacements.add(set);
            }
         }
      }
   }

   public static Set<Item> getMatchingSet(Item item) {
      HashSet<Item> res = new HashSet<>();

      for (HashSet<Item> set : replacements) {
         if (set.contains(item)) {
            res.addAll(set);
         }
      }

      res.remove(item);
      return res;
   }

   public static boolean matchBlocks(Block b1, Block b2) {
      if (b1 == b2) {
         return true;
      }

      if (b1 != Blocks.AIR && b2 != Blocks.AIR) {
         for (HashSet<Item> set : replacements) {
            if (set.contains(b1.asItem()) && set.contains(b2.asItem())) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }
}
