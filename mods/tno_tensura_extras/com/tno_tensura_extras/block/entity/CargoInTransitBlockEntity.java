package com.tno_tensura_extras.block.entity;

import com.tno_tensura_extras.registry.ExtrasBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class CargoInTransitBlockEntity extends BlockEntity {
   private BlockState cargoState = Blocks.AIR.defaultBlockState();
   @Nullable
   private CompoundTag cargoData;
   private int delay;
   private int duration = 60;
   private int progress;
   private boolean delivered;
   private boolean sinking;

   public CargoInTransitBlockEntity(BlockPos pos, BlockState state) {
      super(ExtrasBlocks.CARGO_IN_TRANSIT_ENTITY.get(), pos, state);
   }

   @Nullable
   public static CargoInTransitBlockEntity place(ServerLevel level, BlockPos pos, BlockState cargoState, CompoundTag cargoData, int delay, int riseDuration) {
      if (!level.getBlockState(pos).isAir()) {
         level.destroyBlock(pos, true);
      }

      CargoInTransitBlockEntity cargo = put(level, pos, cargoState, delay, riseDuration);
      if (cargo == null) {
         return null;
      }

      cargo.cargoData = cargoData;
      cargo.setChanged();
      return cargo;
   }

   public static void sink(ServerLevel level, BlockPos pos, BlockState cargoState, int delay, int duration) {
      CargoInTransitBlockEntity cargo = put(level, pos, cargoState, delay, duration);
      if (cargo != null) {
         cargo.sinking = true;
         cargo.setChanged();
         level.sendBlockUpdated(pos, cargo.getBlockState(), cargo.getBlockState(), 3);
      }
   }

   @Nullable
   private static CargoInTransitBlockEntity put(ServerLevel level, BlockPos pos, BlockState cargoState, int delay, int duration) {
      if (!level.setBlock(pos, ExtrasBlocks.CARGO_IN_TRANSIT.get().defaultBlockState(), 3)) {
         return null;
      } else if (level.getBlockEntity(pos) instanceof CargoInTransitBlockEntity cargo) {
         cargo.cargoState = cargoState;
         cargo.delay = Math.max(0, delay);
         cargo.duration = Math.max(1, duration);
         cargo.setChanged();
         level.sendBlockUpdated(pos, cargo.getBlockState(), cargo.getBlockState(), 3);
         return cargo;
      } else {
         level.removeBlock(pos, false);
         return null;
      }
   }

   public void cancelBeforeDeparture() {
      this.delivered = true;
   }

   public static void tick(Level level, BlockPos pos, BlockState state, CargoInTransitBlockEntity cargo) {
      cargo.progress++;
      if (level instanceof ServerLevel serverLevel && cargo.progress >= cargo.delay + cargo.duration) {
         if (cargo.sinking) {
            cargo.delivered = true;
            serverLevel.removeBlock(pos, false);
         } else {
            cargo.deliver(serverLevel, pos);
         }
      }
   }

   private void deliver(ServerLevel level, BlockPos pos) {
      if (!this.delivered) {
         if (this.cargoState.isAir()) {
            this.delivered = true;
            level.removeBlock(pos, false);
         } else {
            this.delivered = true;
            if (!level.setBlock(pos, this.cargoState, 3)) {
               this.delivered = false;
            } else {
               BlockEntity placed = level.getBlockEntity(pos);
               if (this.cargoData != null && placed != null) {
                  placed.loadWithComponents(this.cargoData.copy(), level.registryAccess());
                  placed.setChanged();
               }

               level.playSound(null, pos, this.cargoState.getSoundType().getPlaceSound(), SoundSource.BLOCKS, 1.0F, 1.0F);
            }
         }
      }
   }

   public void dropUndelivered() {
      Level level = this.level;
      if (!this.delivered && !this.sinking && level != null && !level.isClientSide() && !this.cargoState.isAir()) {
         this.delivered = true;
         BlockPos pos = this.getBlockPos();
         double x = pos.getX() + 0.5;
         double y = pos.getY() + 0.5;
         double z = pos.getZ() + 0.5;
         Containers.dropItemStack(level, x, y, z, new ItemStack(this.cargoState.getBlock()));
         if (this.cargoData != null) {
            ListTag items = this.cargoData.getList("Items", 10);

            for (int i = 0; i < items.size(); i++) {
               ItemStack.parse(level.registryAccess(), items.getCompound(i)).ifPresent(stack -> Containers.dropItemStack(level, x, y, z, stack));
            }
         }
      }
   }

   public BlockState getCargoState() {
      return this.cargoState;
   }

   public boolean isVisible() {
      return this.sinking || this.progress >= this.delay;
   }

   public float getOffset(float partialTick) {
      float travelled = Mth.clamp((this.progress - this.delay + partialTick) / this.duration, 0.0F, 1.0F);
      return this.sinking ? -travelled : travelled - 1.0F;
   }

   protected void saveAdditional(CompoundTag tag, Provider registries) {
      super.saveAdditional(tag, registries);
      tag.put("CargoState", NbtUtils.writeBlockState(this.cargoState));
      if (this.cargoData != null) {
         tag.put("CargoData", this.cargoData);
      }

      tag.putInt("Delay", this.delay);
      tag.putInt("Duration", this.duration);
      tag.putInt("Progress", this.progress);
      tag.putBoolean("Delivered", this.delivered);
      tag.putBoolean("Sinking", this.sinking);
   }

   protected void loadAdditional(CompoundTag tag, Provider registries) {
      super.loadAdditional(tag, registries);
      if (tag.contains("CargoState", 10)) {
         this.cargoState = NbtUtils.readBlockState(BuiltInRegistries.BLOCK.asLookup(), tag.getCompound("CargoState"));
      }

      this.cargoData = tag.contains("CargoData", 10) ? tag.getCompound("CargoData") : null;
      this.delay = tag.getInt("Delay");
      this.duration = Math.max(1, tag.getInt("Duration"));
      this.progress = tag.getInt("Progress");
      this.delivered = tag.getBoolean("Delivered");
      this.sinking = tag.getBoolean("Sinking");
   }

   public CompoundTag getUpdateTag(Provider registries) {
      CompoundTag tag = new CompoundTag();
      tag.put("CargoState", NbtUtils.writeBlockState(this.cargoState));
      tag.putInt("Delay", this.delay);
      tag.putInt("Duration", this.duration);
      tag.putInt("Progress", this.progress);
      tag.putBoolean("Sinking", this.sinking);
      return tag;
   }

   @Nullable
   public Packet<ClientGamePacketListener> getUpdatePacket() {
      return ClientboundBlockEntityDataPacket.create(this);
   }
}
