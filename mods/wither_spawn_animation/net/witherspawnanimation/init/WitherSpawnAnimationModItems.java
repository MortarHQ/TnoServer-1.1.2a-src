package net.witherspawnanimation.init;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredRegister.Items;

public class WitherSpawnAnimationModItems {
   public static final Items REGISTRY = DeferredRegister.createItems("wither_spawn_animation");
   public static final DeferredItem<Item> NO_W = block(WitherSpawnAnimationModBlocks.NO_W);

   private static DeferredItem<Item> block(DeferredHolder<Block, Block> block) {
      return REGISTRY.register(block.getId().getPath(), () -> new BlockItem((Block)block.get(), new Properties()));
   }
}
