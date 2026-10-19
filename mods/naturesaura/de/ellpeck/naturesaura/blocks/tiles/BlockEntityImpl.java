package de.ellpeck.naturesaura.blocks.tiles;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.ellpeck.naturesaura.api.aura.chunk.IAuraChunk;
import de.ellpeck.naturesaura.blocks.ModBlocks;
import de.ellpeck.naturesaura.packet.PacketHandler;
import de.ellpeck.naturesaura.packet.PacketParticles;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities.ItemHandler;
import net.neoforged.neoforge.items.IItemHandler;

public class BlockEntityImpl extends BlockEntity {
   public int redstonePower;

   public BlockEntityImpl(BlockEntityType<?> type, BlockPos pos, BlockState state) {
      super(type, pos, state);
   }

   public void saveAdditional(CompoundTag compound, Provider registries) {
      this.writeNBT(compound, BlockEntityImpl.SaveType.TILE, registries);
   }

   protected void loadAdditional(CompoundTag tag, Provider registries) {
      this.readNBT(tag, BlockEntityImpl.SaveType.TILE, registries);
   }

   public void writeNBT(CompoundTag compound, BlockEntityImpl.SaveType type, Provider registries) {
      if (type != BlockEntityImpl.SaveType.BLOCK) {
         super.saveAdditional(compound, registries);
         compound.putInt("redstone", this.redstonePower);
      }
   }

   public void readNBT(CompoundTag compound, BlockEntityImpl.SaveType type, Provider registries) {
      if (type != BlockEntityImpl.SaveType.BLOCK) {
         super.loadAdditional(compound, registries);
         this.redstonePower = compound.getInt("redstone");
      }
   }

   public void onRedstonePowerChange(int newPower) {
      this.redstonePower = newPower;
   }

   public final ClientboundBlockEntityDataPacket getUpdatePacket() {
      return ClientboundBlockEntityDataPacket.create(this, (e, r) -> {
         CompoundTag compound = new CompoundTag();
         this.writeNBT(compound, BlockEntityImpl.SaveType.SYNC, r);
         return compound;
      });
   }

   public final CompoundTag getUpdateTag(Provider registries) {
      CompoundTag compound = new CompoundTag();
      this.writeNBT(compound, BlockEntityImpl.SaveType.SYNC, registries);
      return compound;
   }

   public void handleUpdateTag(CompoundTag tag, Provider registries) {
      this.readNBT(tag, BlockEntityImpl.SaveType.SYNC, registries);
   }

   public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt, Provider registries) {
      this.readNBT(pkt.getTag(), BlockEntityImpl.SaveType.SYNC, registries);
   }

   public void sendToClients() {
      ServerLevel world = (ServerLevel)this.getLevel();
      List<ServerPlayer> entities = world.getChunkSource().chunkMap.getPlayers(new ChunkPos(this.getBlockPos()), false);
      ClientboundBlockEntityDataPacket packet = this.getUpdatePacket();

      for (ServerPlayer e : entities) {
         e.connection.send(packet);
      }
   }

   public void dropInventory() {
      IItemHandler handler = (IItemHandler)this.level.getCapability(ItemHandler.BLOCK, this.worldPosition, this.getBlockState(), this, null);
      if (handler != null) {
         for (int i = 0; i < handler.getSlots(); i++) {
            ItemStack stack = handler.getStackInSlot(i);
            if (!stack.isEmpty()) {
               ItemEntity item = new ItemEntity(
                  this.level, this.worldPosition.getX() + 0.5, this.worldPosition.getY() + 0.5, this.worldPosition.getZ() + 0.5, stack
               );
               this.level.addFreshEntity(item);
            }
         }
      }
   }

   public void modifyDrop(ItemStack regularItem) {
      CompoundTag compound = new CompoundTag();
      this.writeNBT(compound, BlockEntityImpl.SaveType.BLOCK, this.getLevel().registryAccess());
      if (!compound.isEmpty()) {
         regularItem.set(BlockEntityImpl.DroppedItemData.TYPE, new BlockEntityImpl.DroppedItemData(compound));
      }
   }

   public void loadDataOnPlace(ItemStack stack) {
      if (stack.has(BlockEntityImpl.DroppedItemData.TYPE)) {
         CompoundTag compound = ((BlockEntityImpl.DroppedItemData)stack.get(BlockEntityImpl.DroppedItemData.TYPE)).data;
         if (compound != null) {
            this.readNBT(compound, BlockEntityImpl.SaveType.BLOCK, this.level.registryAccess());
         }
      }
   }

   public boolean canUseRightNow(int toUse) {
      if (this.allowsLowerLimiter()) {
         for (Direction dir : Direction.values()) {
            BlockPos offset = this.worldPosition.relative(dir);
            if (this.level.getBlockState(offset).getBlock() == ModBlocks.LOWER_LIMITER) {
               int aura = IAuraChunk.getAuraInArea(this.level, this.worldPosition, 35);
               return aura - toUse > 0;
            }
         }
      }

      return true;
   }

   public boolean canGenerateRightNow(int toAdd) {
      if (this.wantsLimitRemover()) {
         BlockState below = this.level.getBlockState(this.worldPosition.below());
         if (below.getBlock() == ModBlocks.GENERATOR_LIMIT_REMOVER) {
            return true;
         }
      }

      int aura = IAuraChunk.getAuraInArea(this.level, this.worldPosition, 35);
      return aura + toAdd <= 2000000;
   }

   public void spawnCannotGenerateParticles() {
      if (!this.level.isClientSide) {
         PacketHandler.sendToAllAround(
            this.level,
            this.worldPosition,
            32,
            new PacketParticles(this.worldPosition.getX(), this.worldPosition.getY(), this.worldPosition.getZ(), PacketParticles.Type.CANNOT_GENERATE)
         );
      }
   }

   public boolean wantsLimitRemover() {
      return false;
   }

   public boolean allowsLowerLimiter() {
      return false;
   }

   public void generateAura(int amount) {
      while (amount > 0) {
         BlockPos spot = IAuraChunk.getLowestSpot(this.level, this.worldPosition, 35, this.worldPosition);
         amount -= IAuraChunk.getAuraChunk(this.level, spot).storeAura(spot, amount);
      }
   }

   public record DroppedItemData(CompoundTag data) {
      public static final Codec<BlockEntityImpl.DroppedItemData> CODEC = RecordCodecBuilder.create(
         i -> i.group(CompoundTag.CODEC.fieldOf("data").forGetter(d -> d.data)).apply(i, BlockEntityImpl.DroppedItemData::new)
      );
      public static final DataComponentType<BlockEntityImpl.DroppedItemData> TYPE = DataComponentType.builder().persistent(CODEC).cacheEncoding().build();
   }

   public enum SaveType {
      TILE,
      SYNC,
      BLOCK;
   }
}
