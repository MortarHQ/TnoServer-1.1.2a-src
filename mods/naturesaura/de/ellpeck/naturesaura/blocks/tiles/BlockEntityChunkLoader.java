package de.ellpeck.naturesaura.blocks.tiles;

import de.ellpeck.naturesaura.ModConfig;
import de.ellpeck.naturesaura.api.aura.chunk.IAuraChunk;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.state.BlockState;

public class BlockEntityChunkLoader extends BlockEntityImpl implements ITickableBlockEntity {
   private final List<ChunkPos> forcedChunks = new ArrayList<>();
   private boolean firstTick = true;
   private boolean canUseRightNow = true;

   public BlockEntityChunkLoader(BlockPos pos, BlockState state) {
      super(ModBlockEntities.CHUNK_LOADER, pos, state);
   }

   public void setRemoved() {
      super.setRemoved();
      this.loadChunks(true);
   }

   @Override
   public void onRedstonePowerChange(int newPower) {
      super.onRedstonePowerChange(newPower);
      if (!this.level.isClientSide) {
         this.loadChunks(false);
         this.sendToClients();
      }
   }

   public int range() {
      return this.redstonePower * 2;
   }

   public int getAuraUsed() {
      return Mth.ceil(this.range() / 2.0F);
   }

   private void loadChunks(boolean unload) {
      if (!this.level.isClientSide && (Boolean)ModConfig.instance.chunkLoader.get()) {
         ServerLevel level = (ServerLevel)this.level;
         List<ChunkPos> shouldBeForced = new ArrayList<>();
         if (!unload) {
            int range = this.range();
            if (range > 0 && this.canUseRightNow) {
               for (int x = this.worldPosition.getX() - range >> 4; x <= this.worldPosition.getX() + range >> 4; x++) {
                  for (int z = this.worldPosition.getZ() - range >> 4; z <= this.worldPosition.getZ() + range >> 4; z++) {
                     ChunkPos pos = new ChunkPos(x, z);
                     if (this.forcedChunks.contains(pos) || !level.getForcedChunks().contains(pos.toLong())) {
                        shouldBeForced.add(pos);
                     }
                  }
               }
            }
         }

         for (ChunkPos pos : this.forcedChunks) {
            if (!shouldBeForced.contains(pos)) {
               level.setChunkForced(pos.x, pos.z, false);
            }
         }

         this.forcedChunks.clear();

         for (ChunkPos pos : shouldBeForced) {
            level.setChunkForced(pos.x, pos.z, true);
            this.forcedChunks.add(pos);
         }
      }
   }

   @Override
   public void tick() {
      if (!this.level.isClientSide && (Boolean)ModConfig.instance.chunkLoader.get()) {
         if (this.firstTick) {
            this.loadChunks(false);
            this.firstTick = false;
         }

         int toUse = this.getAuraUsed();
         boolean canUse = this.canUseRightNow(toUse);
         if (this.canUseRightNow != canUse) {
            this.canUseRightNow = canUse;
            this.loadChunks(false);
         }

         if (this.level.getGameTime() % 20L != 0L) {
            return;
         }

         if (toUse > 0 && this.canUseRightNow) {
            BlockPos spot = IAuraChunk.getHighestSpot(this.level, this.worldPosition, 35, this.worldPosition);
            IAuraChunk.getAuraChunk(this.level, spot).drainAura(spot, toUse);
         }
      }
   }

   @Override
   public void writeNBT(CompoundTag compound, BlockEntityImpl.SaveType type, Provider registries) {
      super.writeNBT(compound, type, registries);
      if (type == BlockEntityImpl.SaveType.TILE) {
         compound.putLongArray("forced_chunks", this.forcedChunks.stream().map(ChunkPos::toLong).collect(Collectors.toList()));
      }
   }

   @Override
   public void readNBT(CompoundTag compound, BlockEntityImpl.SaveType type, Provider registries) {
      super.readNBT(compound, type, registries);
      if (type == BlockEntityImpl.SaveType.TILE) {
         this.forcedChunks.clear();
         Arrays.stream(compound.getLongArray("forced_chunks")).mapToObj(ChunkPos::new).forEach(this.forcedChunks::add);
      }
   }

   @Override
   public boolean allowsLowerLimiter() {
      return true;
   }
}
