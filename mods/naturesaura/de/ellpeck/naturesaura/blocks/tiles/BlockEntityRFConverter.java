package de.ellpeck.naturesaura.blocks.tiles;

import de.ellpeck.naturesaura.ModConfig;
import de.ellpeck.naturesaura.api.aura.chunk.IAuraChunk;
import de.ellpeck.naturesaura.blocks.multi.Multiblocks;
import de.ellpeck.naturesaura.packet.PacketHandler;
import de.ellpeck.naturesaura.packet.PacketParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities.EnergyStorage;
import net.neoforged.neoforge.energy.IEnergyStorage;

public class BlockEntityRFConverter extends BlockEntityImpl implements ITickableBlockEntity {
   public final BlockEntityRFConverter.RFStorage storage = new BlockEntityRFConverter.RFStorage();
   private int lastEnergy;

   public BlockEntityRFConverter(BlockPos pos, BlockState state) {
      super(ModBlockEntities.RF_CONVERTER, pos, state);
   }

   @Override
   public void writeNBT(CompoundTag compound, BlockEntityImpl.SaveType type, Provider registries) {
      super.writeNBT(compound, type, registries);
      compound.putInt("energy", this.storage.getEnergyStored());
   }

   @Override
   public void readNBT(CompoundTag compound, BlockEntityImpl.SaveType type, Provider registries) {
      super.readNBT(compound, type, registries);
      this.storage.setEnergy(compound.getInt("energy"));
   }

   @Override
   public void tick() {
      if (!this.level.isClientSide && (Boolean)ModConfig.instance.rfConverter.get()) {
         if (this.lastEnergy != this.storage.getEnergyStored() && this.level.getGameTime() % 10L == 0L) {
            this.sendToClients();
            this.lastEnergy = this.storage.getEnergyStored();
         }

         for (Direction facing : Direction.values()) {
            BlockEntity tile = this.level.getBlockEntity(this.worldPosition.relative(facing));
            if (tile != null) {
               IEnergyStorage storage = (IEnergyStorage)this.level
                  .getCapability(EnergyStorage.BLOCK, tile.getBlockPos(), tile.getBlockState(), tile, facing.getOpposite());
               if (storage != null) {
                  int canStore = storage.receiveEnergy(Integer.MAX_VALUE, true);
                  if (canStore > 0) {
                     int extracted = this.storage.extractEnergy(canStore, false);
                     if (extracted > 0) {
                        storage.receiveEnergy(extracted, false);
                        break;
                     }
                  }
               }
            }
         }

         int emptyPart = this.storage.getMaxEnergyStored() - this.storage.getEnergyStored();
         if (emptyPart <= 0) {
            return;
         }

         if (this.level.getGameTime() % 20L != 0L) {
            return;
         }

         if (!Multiblocks.RF_CONVERTER.isComplete(this.level, this.worldPosition)) {
            return;
         }

         int aura = IAuraChunk.getAuraInArea(this.level, this.worldPosition, 45);
         if (aura <= 1000000) {
            return;
         }

         int amountToGen = Math.min(Math.min(10000, aura / 1000), emptyPart);
         int amountToUse = Mth.ceil(amountToGen / (Double)ModConfig.instance.auraToRFRatio.get());
         this.storage.setEnergy(this.storage.getEnergyStored() + amountToGen);
         BlockPos pos = IAuraChunk.getHighestSpot(this.level, this.worldPosition, 45, this.worldPosition);
         IAuraChunk.getAuraChunk(this.level, pos).drainAura(pos, amountToUse);
         PacketHandler.sendToAllAround(
            this.level,
            this.worldPosition,
            32,
            new PacketParticles(this.worldPosition.getX(), this.worldPosition.getY(), this.worldPosition.getZ(), PacketParticles.Type.RF_CONVERTER)
         );
      }
   }

   public void setRemoved() {
      super.setRemoved();
   }

   public static class RFStorage extends net.neoforged.neoforge.energy.EnergyStorage {
      public RFStorage() {
         super(50000, 0, 2000);
      }

      public void setEnergy(int energy) {
         this.energy = energy;
      }
   }
}
