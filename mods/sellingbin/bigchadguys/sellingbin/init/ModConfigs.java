package bigchadguys.sellingbin.init;

import bigchadguys.sellingbin.config.EntityGroupsConfig;
import bigchadguys.sellingbin.config.ItemGroupsConfig;
import bigchadguys.sellingbin.config.SellingBinConfig;
import bigchadguys.sellingbin.config.TileGroupsConfig;
import dev.architectury.event.events.common.LifecycleEvent;
import java.util.ArrayList;
import java.util.List;

public class ModConfigs extends ModRegistries {
   public static List<Runnable> POST_LOAD = new ArrayList<>();
   public static TileGroupsConfig TILE_GROUPS;
   public static EntityGroupsConfig ENTITY_GROUPS;
   public static ItemGroupsConfig ITEM_GROUPS;
   public static SellingBinConfig SELLING_BIN;

   public static void register(boolean initialization) {
      SELLING_BIN = new SellingBinConfig().read();
      if (!initialization) {
         POST_LOAD.forEach(Runnable::run);
         POST_LOAD.clear();
      } else {
         LifecycleEvent.SETUP.register((Runnable)() -> {
            POST_LOAD.forEach(Runnable::run);
            POST_LOAD.clear();
         });
      }
   }
}
