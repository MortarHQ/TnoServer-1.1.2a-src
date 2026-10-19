package twilightforest.enums;

import java.util.HashMap;
import java.util.Locale;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;

public enum BlockLoggingEnum implements StringRepresentable {
   AIR(Blocks.AIR, Fluids.EMPTY),
   WATER(Blocks.WATER, Fluids.WATER),
   LAVA(Blocks.LAVA, Fluids.LAVA),
   OBSIDIAN(Blocks.OBSIDIAN, Fluids.EMPTY),
   STONE(Blocks.STONE, Fluids.EMPTY),
   BASALT(Blocks.BASALT, Fluids.EMPTY);

   public static final EnumProperty<BlockLoggingEnum> MULTILOGGED = EnumProperty.create("multilogged", BlockLoggingEnum.class);
   private final Block block;
   private final Fluid fluid;
   private final String name;

   BlockLoggingEnum(Block block, Fluid fluid) {
      this.block = block;
      this.fluid = fluid;
      this.name = this.name().toLowerCase(Locale.ROOT);
      if (fluid != Fluids.EMPTY && block.defaultBlockState().liquid()) {
         BlockLoggingEnum.Ref.FLUIDS.put(fluid, this);
      }

      if (fluid == Fluids.EMPTY && block != Blocks.AIR) {
         BlockLoggingEnum.Ref.BLOCKS.put(block, this);
      }
   }

   public static BlockLoggingEnum getFromFluid(Fluid fluid) {
      return BlockLoggingEnum.Ref.FLUIDS.getOrDefault(fluid, AIR);
   }

   public static BlockLoggingEnum getFromBlock(Block block) {
      return BlockLoggingEnum.Ref.BLOCKS.getOrDefault(block, AIR);
   }

   public String getSerializedName() {
      return this.name;
   }

   public Fluid getFluid() {
      return this.fluid;
   }

   public Block getBlock() {
      return this.block;
   }

   public interface IMultiLoggable extends BucketPickup, LiquidBlockContainer {
      default ItemStack pickupBlock(@Nullable Player player, LevelAccessor world, BlockPos pos, BlockState state) {
         Fluid stateFluid = ((BlockLoggingEnum)state.getValue(BlockLoggingEnum.MULTILOGGED)).fluid;
         if (stateFluid != Fluids.EMPTY) {
            world.setBlock(pos, (BlockState)state.setValue(BlockLoggingEnum.MULTILOGGED, BlockLoggingEnum.AIR), 3);
         }

         return new ItemStack(stateFluid.getBucket());
      }

      default boolean canPlaceLiquid(@Nullable Player player, BlockGetter world, BlockPos pos, BlockState state, Fluid fluid) {
         return state.hasProperty(BlockLoggingEnum.MULTILOGGED)
            && BlockLoggingEnum.Ref.FLUIDS.containsKey(fluid)
            && !fluid.equals(((BlockLoggingEnum)state.getValue(BlockLoggingEnum.MULTILOGGED)).fluid)
            && state.getValue(BlockLoggingEnum.MULTILOGGED) == BlockLoggingEnum.AIR;
      }

      default boolean placeLiquid(LevelAccessor world, BlockPos pos, BlockState state, FluidState fluidState) {
         Fluid stateFluid = ((BlockLoggingEnum)state.getValue(BlockLoggingEnum.MULTILOGGED)).fluid;
         if (stateFluid != fluidState.getType() && BlockLoggingEnum.Ref.FLUIDS.containsKey(fluidState.getType())) {
            if (!world.isClientSide()) {
               world.setBlock(pos, (BlockState)state.setValue(BlockLoggingEnum.MULTILOGGED, BlockLoggingEnum.Ref.FLUIDS.get(fluidState.getType())), 3);
               world.scheduleTick(pos, fluidState.getType(), fluidState.getType().getTickDelay(world));
            }

            return true;
         } else {
            return false;
         }
      }

      default Optional<SoundEvent> getPickupSound() {
         return Optional.empty();
      }

      default Optional<SoundEvent> getPickupSound(BlockState state) {
         return ((BlockLoggingEnum)state.getValue(BlockLoggingEnum.MULTILOGGED)).fluid.getPickupSound();
      }
   }

   private static class Ref {
      private static final HashMap<Fluid, BlockLoggingEnum> FLUIDS = new HashMap<>();
      private static final HashMap<Block, BlockLoggingEnum> BLOCKS = new HashMap<>();
   }
}
