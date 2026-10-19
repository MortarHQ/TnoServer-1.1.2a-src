package de.ellpeck.naturesaura.blocks.tiles;

import de.ellpeck.naturesaura.api.aura.chunk.IAuraChunk;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Plane;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.common.FarmlandWaterManager;
import net.neoforged.neoforge.common.ticket.AABBTicket;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.IFluidTank;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.IFluidHandler.FluidAction;

public class BlockEntitySpring extends BlockEntityImpl implements ITickableBlockEntity {
   public final IFluidHandler tank = new BlockEntitySpring.InfiniteTank();
   private AABBTicket waterTicket;

   public BlockEntitySpring(BlockPos pos, BlockState state) {
      super(ModBlockEntities.SPRING, pos, state);
   }

   public void onLoad() {
      super.onLoad();
      if (!this.level.isClientSide) {
         AABB area = new AABB(this.worldPosition).inflate(5.0, 1.0, 5.0);
         this.waterTicket = FarmlandWaterManager.addAABBTicket(this.level, area);
      }
   }

   public void setRemoved() {
      super.setRemoved();
      if (!this.level.isClientSide && this.waterTicket != null && this.waterTicket.isValid()) {
         this.waterTicket.invalidate();
         this.waterTicket = null;
      }
   }

   @Override
   public void tick() {
      if (!this.level.isClientSide && this.level.getGameTime() % 35L == 0L) {
         BlockPos up = this.worldPosition.above();
         BlockState upState = this.level.getBlockState(up);
         if (upState.hasProperty(BlockStateProperties.LEVEL_CAULDRON)) {
            int level = (Integer)upState.getValue(BlockStateProperties.LEVEL_CAULDRON);
            if (level < 3 && this.tryConsumeAura(2500)) {
               this.level.setBlockAndUpdate(up, (BlockState)upState.setValue(BlockStateProperties.LEVEL_CAULDRON, level + 1));
               this.level.playSound(null, up, SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
               return;
            }
         }

         int spongeRadius = 2;

         for (int x = -spongeRadius; x <= spongeRadius; x++) {
            for (int y = -spongeRadius; y <= spongeRadius; y++) {
               for (int z = -spongeRadius; z <= spongeRadius; z++) {
                  BlockPos pos = this.worldPosition.offset(x, y, z);
                  BlockState state = this.level.getBlockState(pos);
                  if (state.getBlock() == Blocks.SPONGE && this.tryConsumeAura(2500)) {
                     this.level.setBlock(pos, Blocks.WET_SPONGE.defaultBlockState(), 2);
                     this.level.levelEvent(2001, pos, Block.getId(Blocks.WATER.defaultBlockState()));
                     return;
                  }
               }
            }
         }

         for (Direction dir : Plane.HORIZONTAL) {
            BlockPos side = this.worldPosition.relative(dir);
            if (this.isLava(side, true) && this.tryConsumeAura(1500)) {
               this.level.setBlockAndUpdate(side, EventHooks.fireFluidPlaceBlockEvent(this.level, side, side, Blocks.OBSIDIAN.defaultBlockState()));
               this.level.levelEvent(1501, side, 0);
               return;
            }
         }

         BlockPos twoUp = this.worldPosition.above(2);
         if (this.isLava(twoUp, false) && (this.level.getBlockState(up).isAir() || this.isLava(up, false)) && this.tryConsumeAura(150)) {
            this.level.setBlockAndUpdate(up, EventHooks.fireFluidPlaceBlockEvent(this.level, up, twoUp, Blocks.STONE.defaultBlockState()));
            this.level.levelEvent(1501, up, 0);
         } else {
            for (Direction dir : Plane.HORIZONTAL) {
               BlockPos twoSide = this.worldPosition.relative(dir, 2);
               BlockPos side = this.worldPosition.relative(dir);
               if (this.isLava(twoSide, false) && (this.level.getBlockState(side).isAir() || this.isLava(side, false)) && this.tryConsumeAura(100)) {
                  this.level.setBlockAndUpdate(side, EventHooks.fireFluidPlaceBlockEvent(this.level, side, twoSide, Blocks.COBBLESTONE.defaultBlockState()));
                  this.level.levelEvent(1501, side, 0);
                  return;
               }
            }
         }
      }
   }

   @Override
   public boolean allowsLowerLimiter() {
      return true;
   }

   public boolean tryConsumeAura(int amount) {
      if (!this.canUseRightNow(amount)) {
         return false;
      }

      BlockPos pos = IAuraChunk.getHighestSpot(this.level, this.worldPosition, 35, this.worldPosition);
      IAuraChunk.getAuraChunk(this.level, pos).drainAura(pos, amount);
      return true;
   }

   private boolean isLava(BlockPos offset, boolean source) {
      FluidState state = this.level.getFluidState(offset);
      return (!source || state.isSource()) && state.is(FluidTags.LAVA);
   }

   private class InfiniteTank implements IFluidTank, IFluidHandler {
      public FluidStack getFluid() {
         return new FluidStack(Fluids.WATER, 1000);
      }

      public int getFluidAmount() {
         return 1000;
      }

      public int getCapacity() {
         return 1000;
      }

      public boolean isFluidValid(FluidStack stack) {
         return stack.getFluid().defaultFluidState().is(FluidTags.WATER);
      }

      public int fill(FluidStack resource, FluidAction action) {
         return 0;
      }

      public FluidStack drain(int maxDrain, FluidAction action) {
         int drain = Math.min(maxDrain, 1000);
         int auraUsed = Mth.ceil(drain / 2.0F);
         if (!BlockEntitySpring.this.canUseRightNow(auraUsed)) {
            return FluidStack.EMPTY;
         }

         if (action.execute()) {
            BlockEntitySpring.this.tryConsumeAura(auraUsed);
         }

         return new FluidStack(Fluids.WATER, drain);
      }

      public FluidStack drain(FluidStack resource, FluidAction action) {
         return this.isFluidValid(resource) ? this.drain(resource.getAmount(), action) : FluidStack.EMPTY;
      }

      public int getTanks() {
         return 1;
      }

      public FluidStack getFluidInTank(int tank) {
         return this.getFluid();
      }

      public int getTankCapacity(int tank) {
         return this.getCapacity();
      }

      public boolean isFluidValid(int tank, FluidStack stack) {
         return this.isFluidValid(stack);
      }
   }
}
