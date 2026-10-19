package de.ellpeck.naturesaura.blocks.tiles;

import de.ellpeck.naturesaura.Helper;
import de.ellpeck.naturesaura.api.aura.chunk.IAuraChunk;
import de.ellpeck.naturesaura.items.ModItems;
import de.ellpeck.naturesaura.packet.PacketHandler;
import de.ellpeck.naturesaura.packet.PacketParticles;
import de.ellpeck.naturesaura.reg.ModRegistry;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.capabilities.Capabilities.ItemHandler;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.common.util.FakePlayerFactory;
import net.neoforged.neoforge.items.IItemHandler;

public class BlockEntityPlacer extends BlockEntityImpl implements ITickableBlockEntity {
   public BlockEntityPlacer(BlockPos pos, BlockState state) {
      super(ModBlockEntities.PLACER, pos, state);
   }

   @Override
   public void tick() {
      if (!this.level.isClientSide && this.level.getGameTime() % 15L == 0L) {
         if (this.redstonePower > 0) {
            return;
         }

         BlockEntity tileUp = this.level.getBlockEntity(this.worldPosition.above());
         if (tileUp == null) {
            return;
         }

         IItemHandler handler = (IItemHandler)this.level.getCapability(ItemHandler.BLOCK, tileUp.getBlockPos(), tileUp.getBlockState(), tileUp, Direction.DOWN);
         if (handler == null) {
            return;
         }

         List<ItemFrame> frames = Helper.getAttachedItemFrames(this.level, this.worldPosition);
         if (frames.isEmpty()) {
            return;
         }

         int toDrain = 200;
         if (!this.canUseRightNow(toDrain)) {
            return;
         }

         List<BlockPos> validPositions = new ArrayList<>();
         int range = 5;

         for (int x = -range; x <= range; x++) {
            for (int y = -range; y <= range; y++) {
               for (int z = -range; z <= range; z++) {
                  BlockPos pos = this.worldPosition.offset(x, y, z);
                  if (this.framesContain(frames, this.level.getBlockState(pos))) {
                     BlockPos up = pos.above();
                     BlockState state = this.level.getBlockState(up);
                     if (state.canBeReplaced()) {
                        validPositions.add(up);
                     }
                  }
               }
            }
         }

         if (validPositions.isEmpty()) {
            return;
         }

         for (int i = 0; i < handler.getSlots(); i++) {
            ItemStack stack = handler.extractItem(i, 1, true);
            if (!stack.isEmpty()) {
               BlockPos pos = validPositions.get(this.level.random.nextInt(validPositions.size()));
               ItemStack left = this.tryPlace(stack.copy(), pos);
               if (!ItemStack.matches(stack, left)) {
                  handler.extractItem(i, 1, false);
                  BlockPos spot = IAuraChunk.getHighestSpot(this.level, this.worldPosition, 10, this.worldPosition);
                  IAuraChunk.getAuraChunk(this.level, spot).drainAura(spot, toDrain);
                  PacketHandler.sendToAllAround(
                     this.level, this.worldPosition, 32, new PacketParticles(pos.getX(), pos.getY(), pos.getZ(), PacketParticles.Type.PLACER_PLACING)
                  );
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

   private boolean framesContain(List<ItemFrame> frames, BlockState state) {
      ItemStack stack = new ItemStack(state.getBlock());
      if (stack.isEmpty()) {
         return false;
      }

      for (ItemFrame frame : frames) {
         ItemStack frameStack = frame.getItem();
         if (!frameStack.isEmpty()) {
            if (Helper.areItemsEqual(stack, frameStack, false)) {
               return true;
            }

            if (state.getBlock() == Blocks.FARMLAND && frameStack.getItem() == ModItems.FARMING_STENCIL) {
               return true;
            }
         }
      }

      return false;
   }

   private ItemStack tryPlace(ItemStack stack, BlockPos pos) {
      if (!(this.level instanceof ServerLevel)) {
         return stack;
      }

      FakePlayer fake = FakePlayerFactory.get((ServerLevel)this.level, ModRegistry.FAKE_PLAYER);
      fake.getInventory().items.set(fake.getInventory().selected, stack);
      BlockHitResult ray = new BlockHitResult(Vec3.atCenterOf(pos), Direction.UP, pos, false);
      CommonHooks.onPlaceItemIntoWorld(new UseOnContext(fake, InteractionHand.MAIN_HAND, ray));
      return fake.getMainHandItem().copy();
   }
}
