package team.creative.creativecore.common.be;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.fml.util.thread.EffectiveSide;

public abstract class BlockEntityCreative extends BlockEntity {
   public BlockEntityCreative(BlockEntityType<?> type, BlockPos pos, BlockState state) {
      super(type, pos, state);
   }

   public boolean isClient() {
      return this.level != null ? this.level.isClientSide : EffectiveSide.get().isClient();
   }

   public Packet<ClientGamePacketListener> getUpdatePacket() {
      return ClientboundBlockEntityDataPacket.create(this);
   }

   public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt, Provider lookupProvider) {
      this.handleUpdate(pkt.getTag(), false);
   }

   public void handleUpdateTag(CompoundTag tag, Provider lookupProvider) {
      this.handleUpdate(tag, true);
   }

   public abstract void handleUpdate(CompoundTag var1, boolean var2);

   public CompoundTag getUpdateTag(Provider provider) {
      return this.saveWithFullMetadata(provider);
   }

   public void markDirty() {
      this.level.blockEntityChanged(this.worldPosition);
      this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3);
   }
}
