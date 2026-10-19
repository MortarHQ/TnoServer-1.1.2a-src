package it.hurts.sskirillss.relics.init;

import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.DispenserBlock;

public class DispenserBehaviorRegistry {
   public static void register() {
      DispenserBlock.registerProjectileBehavior((ItemLike)ItemRegistry.RELIC_EXPERIENCE_BOTTLE.get());
   }
}
