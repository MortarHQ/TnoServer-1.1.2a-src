package de.ellpeck.naturesaura.blocks;

import de.ellpeck.naturesaura.api.NaturesAuraAPI;
import de.ellpeck.naturesaura.blocks.tiles.BlockEntityFurnaceHeater;
import de.ellpeck.naturesaura.data.BlockStateGenerator;
import de.ellpeck.naturesaura.reg.ICustomBlockState;
import de.ellpeck.naturesaura.reg.IPickaxeBreakable;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class BlockFurnaceHeater extends BlockContainerImpl implements ICustomBlockState, IPickaxeBreakable {
   public static final DirectionProperty FACING = BlockStateProperties.FACING;
   private static final VoxelShape[] SHAPES = new VoxelShape[]{
      Block.box(2.0, 12.0, 2.0, 14.0, 16.0, 14.0),
      Block.box(2.0, 0.0, 2.0, 14.0, 4.0, 14.0),
      Block.box(2.0, 2.0, 12.0, 14.0, 14.0, 16.0),
      Block.box(2.0, 2.0, 0.0, 14.0, 14.0, 4.0),
      Block.box(12.0, 2.0, 2.0, 16.0, 14.0, 14.0),
      Block.box(0.0, 2.0, 2.0, 4.0, 14.0, 14.0)
   };

   public BlockFurnaceHeater() {
      super("furnace_heater", BlockEntityFurnaceHeater.class, Properties.of().strength(3.0F));
   }

   @Override
   protected boolean hasWaterlogging() {
      return true;
   }

   @OnlyIn(Dist.CLIENT)
   public void animateTick(BlockState stateIn, Level levelIn, BlockPos pos, RandomSource rand) {
      if (levelIn.getBlockEntity(pos) instanceof BlockEntityFurnaceHeater heater && heater.isActive) {
         Direction facing = (Direction)stateIn.getValue(FACING);
         float x;
         float y;
         float z;
         if (facing == Direction.UP) {
            x = 0.35F + rand.nextFloat() * 0.3F;
            y = 0.0F;
            z = 0.35F + rand.nextFloat() * 0.3F;
         } else if (facing == Direction.DOWN) {
            x = 0.35F + rand.nextFloat() * 0.3F;
            y = 1.0F;
            z = 0.35F + rand.nextFloat() * 0.3F;
         } else {
            x = facing.getStepZ() != 0 ? 0.35F + rand.nextFloat() * 0.3F : (facing.getStepX() < 0 ? 1.0F : 0.0F);
            y = 0.35F + rand.nextFloat() * 0.3F;
            z = facing.getStepX() != 0 ? 0.35F + rand.nextFloat() * 0.3F : (facing.getStepZ() < 0 ? 1.0F : 0.0F);
         }

         NaturesAuraAPI.instance()
            .spawnMagicParticle(
               pos.getX() + x,
               pos.getY() + y,
               pos.getZ() + z,
               (rand.nextFloat() * 0.016F + 0.01F) * facing.getStepX(),
               (rand.nextFloat() * 0.016F + 0.01F) * facing.getStepY(),
               (rand.nextFloat() * 0.016F + 0.01F) * facing.getStepZ(),
               16019010,
               rand.nextFloat() + 0.5F,
               55,
               0.0F,
               true,
               true
            );
      }
   }

   public VoxelShape getShape(BlockState state, BlockGetter levelIn, BlockPos pos, CollisionContext context) {
      return SHAPES[((Direction)state.getValue(FACING)).get3DDataValue()];
   }

   @Override
   protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
      super.createBlockStateDefinition(builder);
      builder.add(new Property[]{FACING});
   }

   @Nullable
   @Override
   public BlockState getStateForPlacement(BlockPlaceContext context) {
      return (BlockState)super.getStateForPlacement(context).setValue(FACING, context.getClickedFace());
   }

   @Override
   public void generateCustomBlockState(BlockStateGenerator generator) {
      generator.directionalBlock(this, generator.models().getExistingFile(generator.modLoc(this.getBaseName())));
   }
}
