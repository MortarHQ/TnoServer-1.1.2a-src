package com.blackgear.platform.common.integration;

import com.blackgear.platform.common.integration.neoforge.BlockIntegrationImpl;
import com.google.common.collect.Maps;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.ExpectPlatform.Transformed;
import java.util.function.Consumer;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.BlockState;

public class BlockIntegration {
   @ExpectPlatform
   @Transformed
   public static void registerIntegrations(Consumer<BlockIntegration.Event> listener) {
      BlockIntegrationImpl.registerIntegrations(listener);
   }

   public interface Event {
      void registerBlockInteraction(BlockInteraction var1);

      void registerFuelItem(ItemLike var1, int var2);

      void registerCompostableItem(ItemLike var1, float var2);

      void registerWaxableBlock(Block var1, Block var2);

      void registerOxidableBlock(Block var1, Block var2);

      default void registerDispenserBehavior(ItemLike item, DispenseItemBehavior behavior) {
         DispenserBlock.registerBehavior(item, behavior);
      }

      default void registerStrippableBlock(Block target, Block result) {
         AxeItem.STRIPPABLES = Maps.newHashMap(AxeItem.STRIPPABLES);
         AxeItem.STRIPPABLES.putIfAbsent(target, result);
      }

      default void registerFlattenableBlock(Block target, BlockState result) {
         ShovelItem.FLATTENABLES.putIfAbsent(target, result);
      }

      default void registerFlattenableBlock(Block target, Block result) {
         this.registerFlattenableBlock(target, result.defaultBlockState());
      }

      default void registerFlammableBlock(Block target, int encouragement, int flammability) {
         ((FireBlock)Blocks.FIRE).setFlammable(target, encouragement, flammability);
      }
   }
}
