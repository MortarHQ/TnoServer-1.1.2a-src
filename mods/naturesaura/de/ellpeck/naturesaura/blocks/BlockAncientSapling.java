package de.ellpeck.naturesaura.blocks;

import com.mojang.serialization.MapCodec;
import de.ellpeck.naturesaura.data.BlockStateGenerator;
import de.ellpeck.naturesaura.data.ItemModelGenerator;
import de.ellpeck.naturesaura.gen.ModFeatures;
import de.ellpeck.naturesaura.reg.ICustomBlockState;
import de.ellpeck.naturesaura.reg.ICustomItemModel;
import de.ellpeck.naturesaura.reg.IModItem;
import de.ellpeck.naturesaura.reg.ModRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.event.level.BlockGrowFeatureEvent;

public class BlockAncientSapling extends BushBlock implements BonemealableBlock, IModItem, ICustomBlockState, ICustomItemModel {
   protected static final VoxelShape SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 12.0, 14.0);

   public BlockAncientSapling() {
      super(Properties.of().strength(0.0F).sound(SoundType.GRASS));
      ModRegistry.ALL_ITEMS.add(this);
   }

   public VoxelShape getShape(BlockState state, BlockGetter levelIn, BlockPos pos, CollisionContext context) {
      return SHAPE;
   }

   public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
      if (!level.isClientSide) {
         super.randomTick(state, level, pos, random);
         if (level.getLightEmission(pos.above()) >= 9 && random.nextInt(7) == 0) {
            this.performBonemeal(level, random, pos, state);
         }
      }
   }

   @Override
   public String getBaseName() {
      return "ancient_sapling";
   }

   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      builder.add(new Property[]{SaplingBlock.STAGE});
   }

   public boolean isValidBonemealTarget(LevelReader pLevel, BlockPos pPos, BlockState pState) {
      return true;
   }

   public boolean isBonemealSuccess(Level level, RandomSource rand, BlockPos pos, BlockState state) {
      return level.random.nextFloat() < 0.45F;
   }

   public void performBonemeal(ServerLevel level, RandomSource rand, BlockPos pos, BlockState state) {
      if ((Integer)state.getValue(SaplingBlock.STAGE) == 0) {
         level.setBlock(pos, (BlockState)state.cycle(SaplingBlock.STAGE), 4);
      } else {
         BlockGrowFeatureEvent event = EventHooks.fireBlockGrowFeature(level, rand, pos, null);
         if (!event.isCanceled()) {
            Registry<ConfiguredFeature<?, ?>> registry = level.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE);
            ((ConfiguredFeature)registry.getHolderOrThrow(ModFeatures.Configured.ANCIENT_TREE).value())
               .place(level, level.getChunkSource().getGenerator(), rand, pos);
         }
      }
   }

   @Override
   public void generateCustomBlockState(BlockStateGenerator generator) {
      generator.simpleBlock(
         this, ((BlockModelBuilder)generator.models().cross(this.getBaseName(), generator.modLoc("block/" + this.getBaseName()))).renderType("cutout_mipped")
      );
   }

   @Override
   public void generateCustomItemModel(ItemModelGenerator generator) {
      ((ItemModelBuilder)generator.withExistingParent(this.getBaseName(), "item/generated")).texture("layer0", "block/" + this.getBaseName());
   }

   protected MapCodec<? extends BushBlock> codec() {
      return null;
   }
}
