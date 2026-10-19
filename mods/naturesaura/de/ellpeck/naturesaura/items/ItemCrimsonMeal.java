package de.ellpeck.naturesaura.items;

import de.ellpeck.naturesaura.gen.ModFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class ItemCrimsonMeal extends ItemImpl {
   public ItemCrimsonMeal() {
      super("crimson_meal");
   }

   public InteractionResult useOn(UseOnContext context) {
      Level level = context.getLevel();
      BlockPos pos = context.getClickedPos();
      BlockState state = level.getBlockState(pos);
      if (state.getBlock() == Blocks.NETHER_WART) {
         if (!level.isClientSide) {
            if (level.random.nextInt(5) == 0) {
               int age = (Integer)state.getValue(NetherWartBlock.AGE);
               if (age >= 3) {
                  Registry<ConfiguredFeature<?, ?>> registry = level.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE);
                  ((ConfiguredFeature)registry.getHolderOrThrow(ModFeatures.Configured.NETHER_WART_MUSHROOM).value())
                     .place((ServerLevel)level, ((ServerLevel)level).getChunkSource().getGenerator(), level.random, pos);
               } else {
                  level.setBlockAndUpdate(pos, (BlockState)state.setValue(NetherWartBlock.AGE, age + 1));
               }
            }

            level.levelEvent(2005, pos, 0);
            context.getItemInHand().shrink(1);
         }

         return InteractionResult.SUCCESS;
      } else if (level.getBlockState(pos.above()).isAir() && level.getBlockState(pos).getBlock() == Blocks.SOUL_SAND) {
         if (!level.isClientSide) {
            for (int i = level.random.nextInt(5); i >= 0; i--) {
               BlockPos offset = pos.offset(Mth.nextInt(level.random, -3, 3), 1, Mth.nextInt(level.random, -3, 3));
               if (level.getBlockState(offset.below()).getBlock() == Blocks.SOUL_SAND && level.getBlockState(offset).isAir()) {
                  level.setBlockAndUpdate(offset, Blocks.NETHER_WART.defaultBlockState());
               }
            }

            level.levelEvent(2005, pos, 0);
            context.getItemInHand().shrink(1);
         }

         return InteractionResult.SUCCESS;
      } else {
         return InteractionResult.FAIL;
      }
   }
}
