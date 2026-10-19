package bigchadguys.sellingbin.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class BaseBlockEntity extends BlockEntity {
   public BaseBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
      super(type, pos, state);
   }

   public abstract void writeNbt(CompoundTag var1, Provider var2, BaseBlockEntity.UpdateType var3);

   public abstract void readNbt(CompoundTag var1, Provider var2, BaseBlockEntity.UpdateType var3);

   public final void saveAdditional(CompoundTag nbt, Provider registries) {
      super.saveAdditional(nbt, registries);
      this.writeNbt(nbt, registries, BaseBlockEntity.UpdateType.SERVER);
   }

   public final void loadAdditional(CompoundTag nbt, Provider registries) {
      super.loadAdditional(nbt, registries);
      this.readNbt(nbt, registries, BaseBlockEntity.UpdateType.SERVER);
   }

   public CompoundTag getUpdateTag(Provider registries) {
      CompoundTag nbt = super.getUpdateTag(registries);
      this.writeNbt(nbt, registries, BaseBlockEntity.UpdateType.INITIAL_PACKET);
      return nbt;
   }

   public final ClientboundBlockEntityDataPacket toUpdatePacket() {
      return ClientboundBlockEntityDataPacket.create(this, (entity, registries) -> {
         CompoundTag nbt = new CompoundTag();
         if (entity instanceof BaseBlockEntity baseEntity) {
            baseEntity.writeNbt(nbt, registries, BaseBlockEntity.UpdateType.UPDATE_PACKET);
            return nbt;
         } else {
            throw new IllegalStateException("BlockEntity is not an instance of BaseBlockEntity");
         }
      });
   }

   public void sendUpdatesToClient() {
      this.setChanged();
      if (this.getLevel() != null) {
         this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
         this.getLevel().blockUpdated(this.getBlockPos(), this.getBlockState().getBlock());
      }
   }

   public enum UpdateType {
      SERVER,
      INITIAL_PACKET,
      UPDATE_PACKET;
   }
}
