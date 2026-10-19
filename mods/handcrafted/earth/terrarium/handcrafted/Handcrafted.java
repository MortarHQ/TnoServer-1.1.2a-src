package earth.terrarium.handcrafted;

import earth.terrarium.handcrafted.common.registry.ModBlockEntityTypes;
import earth.terrarium.handcrafted.common.registry.ModBlocks;
import earth.terrarium.handcrafted.common.registry.ModEntityTypes;
import earth.terrarium.handcrafted.common.registry.ModItems;
import earth.terrarium.handcrafted.common.registry.ModSoundEvents;

public class Handcrafted {
   public static final String MOD_ID = "handcrafted";

   public static void init() {
      ModBlocks.BLOCKS.init();
      ModItems.ITEMS.init();
      ModItems.TABS.init();
      ModBlockEntityTypes.BLOCK_ENTITY_TYPES.init();
      ModEntityTypes.ENTITY_TYPES.init();
      ModSoundEvents.SOUND_EVENTS.init();
   }
}
