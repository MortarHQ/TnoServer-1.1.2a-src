package de.ellpeck.naturesaura.blocks;

import de.ellpeck.naturesaura.Helper;
import de.ellpeck.naturesaura.api.render.IVisualizable;
import de.ellpeck.naturesaura.blocks.tiles.BlockEntitySlimeSplitGenerator;
import de.ellpeck.naturesaura.data.BlockStateGenerator;
import de.ellpeck.naturesaura.reg.ICustomBlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.AABB;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

public class BlockSlimeSplitGenerator extends BlockContainerImpl implements IVisualizable, ICustomBlockState {
   public BlockSlimeSplitGenerator() {
      super("slime_split_generator", BlockEntitySlimeSplitGenerator.class, Properties.ofFullCopy(Blocks.SLIME_BLOCK).strength(2.0F));
      NeoForge.EVENT_BUS.register(new BlockSlimeSplitGenerator.Events());
   }

   @Override
   public void generateCustomBlockState(BlockStateGenerator generator) {
      generator.simpleBlock(
         this,
         generator.models()
            .cubeBottomTop(
               this.getBaseName(),
               generator.modLoc("block/" + this.getBaseName()),
               generator.modLoc("block/" + this.getBaseName() + "_bottom"),
               generator.modLoc("block/" + this.getBaseName() + "_top")
            )
      );
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public AABB getVisualizationBounds(Level level, BlockPos pos) {
      return new AABB(pos).inflate(8.0);
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public int getVisualizationColor(Level level, BlockPos pos) {
      return 5089359;
   }

   private static class Events {
      @SubscribeEvent
      public void onLivingDeath(LivingDeathEvent event) {
         LivingEntity entity = event.getEntity();
         if (entity instanceof Slime slime && !entity.level().isClientSide) {
            int size = slime.getSize();
            if (size > 1) {
               Helper.getBlockEntitiesInArea(entity.level(), entity.blockPosition(), 8, tile -> {
                  if (tile instanceof BlockEntitySlimeSplitGenerator gen) {
                     if (gen.isBusy()) {
                        return false;
                     }

                     gen.startGenerating(slime);
                     return true;
                  } else {
                     return false;
                  }
               });
            }
         }
      }
   }
}
