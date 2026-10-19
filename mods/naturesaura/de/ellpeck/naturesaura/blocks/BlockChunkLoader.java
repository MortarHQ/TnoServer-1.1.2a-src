package de.ellpeck.naturesaura.blocks;

import de.ellpeck.naturesaura.ModConfig;
import de.ellpeck.naturesaura.api.NaturesAuraAPI;
import de.ellpeck.naturesaura.api.render.IVisualizable;
import de.ellpeck.naturesaura.blocks.tiles.BlockEntityChunkLoader;
import de.ellpeck.naturesaura.data.BlockStateGenerator;
import de.ellpeck.naturesaura.reg.ICustomBlockState;
import de.ellpeck.naturesaura.reg.IPickaxeBreakable;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class BlockChunkLoader extends BlockContainerImpl implements IVisualizable, ICustomBlockState, IPickaxeBreakable {
   private static final VoxelShape SHAPE = Block.box(4.0, 4.0, 4.0, 12.0, 12.0, 12.0);

   public BlockChunkLoader() {
      super("chunk_loader", BlockEntityChunkLoader.class, Properties.of().strength(3.0F).sound(SoundType.STONE));
   }

   @Override
   protected boolean hasWaterlogging() {
      return true;
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public AABB getVisualizationBounds(Level level, BlockPos pos) {
      BlockEntity tile = level.getBlockEntity(pos);
      if (tile instanceof BlockEntityChunkLoader) {
         int range = ((BlockEntityChunkLoader)tile).range();
         if (range > 0) {
            return new AABB(
               pos.getX() - range >> 4 << 4,
               level.getMinBuildHeight(),
               pos.getZ() - range >> 4 << 4,
               (pos.getX() + range >> 4 << 4) + 16,
               level.getMaxBuildHeight(),
               (pos.getZ() + range >> 4 << 4) + 16
            );
         }
      }

      return null;
   }

   @OnlyIn(Dist.CLIENT)
   public void animateTick(BlockState stateIn, Level levelIn, BlockPos pos, RandomSource rand) {
      if ((Boolean)ModConfig.instance.chunkLoader.get()) {
         if (levelIn.getBlockEntity(pos) instanceof BlockEntityChunkLoader loader && loader.canUseRightNow(loader.getAuraUsed())) {
            for (int i = Mth.ceil(loader.range() / 8.0F); i > 0; i--) {
               NaturesAuraAPI.instance()
                  .spawnMagicParticle(
                     pos.getX() + levelIn.random.nextFloat(),
                     pos.getY() + levelIn.random.nextFloat(),
                     pos.getZ() + levelIn.random.nextFloat(),
                     0.0,
                     0.0,
                     0.0,
                     10563071,
                     1.0F + levelIn.random.nextFloat(),
                     100,
                     0.0F,
                     false,
                     true
                  );
            }
         }
      }
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public int getVisualizationColor(Level level, BlockPos pos) {
      return 12671481;
   }

   public VoxelShape getShape(BlockState state, BlockGetter levelIn, BlockPos pos, CollisionContext context) {
      return SHAPE;
   }

   public String getDescriptionId() {
      return ModConfig.instance.chunkLoader.get() ? super.getDescriptionId() : "block.naturesaura." + this.getBaseName() + ".disabled";
   }

   @Override
   public void generateCustomBlockState(BlockStateGenerator generator) {
      generator.simpleBlock(this, generator.models().getExistingFile(generator.modLoc(this.getBaseName())));
   }
}
