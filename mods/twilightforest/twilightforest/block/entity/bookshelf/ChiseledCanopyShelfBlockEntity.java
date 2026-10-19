package twilightforest.block.entity.bookshelf;

import com.mojang.datafixers.util.Either;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SpawnData;
import net.minecraft.world.level.Spawner;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChiseledBookShelfBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import twilightforest.block.ChiseledCanopyShelfBlock;
import twilightforest.init.TFBlockEntities;
import twilightforest.init.TFBlocks;

public class ChiseledCanopyShelfBlockEntity extends ChiseledBookShelfBlockEntity implements Spawner {
   private final BookshelfSpawner spawner = new BookshelfSpawner() {
      @Override
      public void broadcastEvent(Level level, BlockPos pos, int id) {
         level.blockEvent(pos, (Block)TFBlocks.CHISELED_CANOPY_BOOKSHELF.get(), id, 0);
      }

      @Override
      public void setNextSpawnData(@Nullable Level level, BlockPos pos, SpawnData data) {
         super.setNextSpawnData(level, pos, data);
         if (level != null) {
            BlockState blockstate = level.getBlockState(pos);
            level.sendBlockUpdated(pos, blockstate, blockstate, 4);
         }
      }

      public Either<BlockEntity, Entity> getOwner() {
         return Either.left(ChiseledCanopyShelfBlockEntity.this);
      }
   };

   public ChiseledCanopyShelfBlockEntity(BlockPos pos, BlockState state) {
      super(pos, state);
   }

   public boolean isValidBlockState(BlockState state) {
      return ((BlockEntityType)TFBlockEntities.CHISELED_CANOPY_BOOKSHELF.get()).isValid(state);
   }

   public static void tick(Level level, BlockPos pos, BlockState state, ChiseledCanopyShelfBlockEntity te) {
      if (!level.isClientSide() && (Boolean)state.getValue(ChiseledCanopyShelfBlock.SPAWNER)) {
         te.spawner.serverTick((ServerLevel)level, pos, state);
      }
   }

   public BlockEntityType<?> getType() {
      return (BlockEntityType<?>)TFBlockEntities.CHISELED_CANOPY_BOOKSHELF.get();
   }

   protected void loadAdditional(CompoundTag tag, Provider provider) {
      super.loadAdditional(tag, provider);
      this.spawner.load(this.level, this.worldPosition, tag);
   }

   protected void saveAdditional(CompoundTag tag, Provider provider) {
      super.saveAdditional(tag, provider);
      this.spawner.save(tag);
   }

   public ClientboundBlockEntityDataPacket getUpdatePacket() {
      return ClientboundBlockEntityDataPacket.create(this);
   }

   public CompoundTag getUpdateTag(Provider provider) {
      CompoundTag compoundtag = this.saveCustomOnly(provider);
      compoundtag.remove("SpawnPotentials");
      return compoundtag;
   }

   public boolean triggerEvent(int id, int type) {
      return this.spawner.onEventTriggered(this.level, id) || super.triggerEvent(id, type);
   }

   public boolean onlyOpCanSetNbt() {
      return true;
   }

   public void setEntityId(EntityType<?> type, RandomSource random) {
      this.spawner.setEntityId(type, this.level, random, this.worldPosition);
      if (this.level != null) {
         this.level.setBlockAndUpdate(this.worldPosition, (BlockState)this.getBlockState().setValue(ChiseledCanopyShelfBlock.SPAWNER, true));
      }

      this.setChanged();
   }

   public BookshelfSpawner getSpawner() {
      return this.spawner;
   }

   public ItemStack removeItem(int slot, int amount) {
      return this.getBlockState().getValue(ChiseledCanopyShelfBlock.SPAWNER) ? ItemStack.EMPTY : super.removeItem(slot, amount);
   }

   public boolean canTakeItem(Container target, int slot, ItemStack stack) {
      return !(Boolean)this.getBlockState().getValue(ChiseledCanopyShelfBlock.SPAWNER) && super.canTakeItem(target, slot, stack);
   }

   public boolean canPlaceItem(int slot, ItemStack stack) {
      return !(Boolean)this.getBlockState().getValue(ChiseledCanopyShelfBlock.SPAWNER) && super.canPlaceItem(slot, stack);
   }
}
