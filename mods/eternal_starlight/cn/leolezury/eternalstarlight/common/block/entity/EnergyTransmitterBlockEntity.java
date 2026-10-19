package cn.leolezury.eternalstarlight.common.block.entity;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.EnergyTransmitterBlock;
import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import com.mojang.math.OctahedralGroup;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

public class EnergyTransmitterBlockEntity extends BlockEntity {
   private static final String TAG_INPUT_OFFSET = "input_offset";
   private static final String TAG_OUTPUT_OFFSET = "output_offset";
   private Vec3i inputOffset = Vec3i.ZERO;
   private Vec3i outputOffset = Vec3i.ZERO;

   public void setInputOffset(Vec3i inputOffset) {
      OctahedralGroup group = (OctahedralGroup)this.getBlockState().getValue(EnergyTransmitterBlock.OFFSET_TRANSFORMATION);
      this.inputOffset = this.transformOffset(inputOffset, group.inverse());
      this.setChanged();
   }

   public Vec3i getInputOffset() {
      OctahedralGroup group = (OctahedralGroup)this.getBlockState().getValue(EnergyTransmitterBlock.OFFSET_TRANSFORMATION);
      return this.transformOffset(this.inputOffset, group);
   }

   public BlockState getInputState() {
      return this.getLevel() != null ? this.getLevel().getBlockState(this.getBlockPos().offset(this.getInputOffset())) : Blocks.AIR.defaultBlockState();
   }

   public void setOutputOffset(Vec3i outputOffset) {
      OctahedralGroup group = (OctahedralGroup)this.getBlockState().getValue(EnergyTransmitterBlock.OFFSET_TRANSFORMATION);
      this.outputOffset = this.transformOffset(outputOffset, group.inverse());
      this.setChanged();
   }

   public Vec3i getOutputOffset() {
      OctahedralGroup group = (OctahedralGroup)this.getBlockState().getValue(EnergyTransmitterBlock.OFFSET_TRANSFORMATION);
      return this.transformOffset(this.outputOffset, group);
   }

   private Vec3i transformOffset(Vec3i original, OctahedralGroup group) {
      Vector3f offset = group.rotate(Direction.EAST)
         .step()
         .mul(original.getX())
         .add(group.rotate(Direction.UP).step().mul(original.getY()))
         .add(group.rotate(Direction.SOUTH).step().mul(original.getZ()));
      return new Vec3i(Math.round(offset.x), Math.round(offset.y), Math.round(offset.z));
   }

   public EnergyTransmitterBlockEntity(BlockPos blockPos, BlockState blockState) {
      super(ESBlockEntities.ENERGY_TRANSMITTER.get(), blockPos, blockState);
   }

   public void setChanged() {
      super.setChanged();
      if (this.getLevel() != null) {
         this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
      }
   }

   public static void tick(Level level, BlockPos pos, BlockState state, EnergyTransmitterBlockEntity entity) {
      if (!level.isClientSide) {
         if (entity.getInputOffset().distManhattan(Vec3i.ZERO) > 64) {
            entity.setInputOffset(Vec3i.ZERO);
            entity.setChanged();
         }

         if (entity.getOutputOffset().distManhattan(Vec3i.ZERO) > 64) {
            entity.setOutputOffset(Vec3i.ZERO);
         }

         if (!entity.getInputOffset().equals(Vec3i.ZERO) && !entity.getOutputOffset().equals(Vec3i.ZERO)) {
            entity.setOutputOffset(Vec3i.ZERO);
         }

         int directOutputPower = 0;
         boolean receiver = !entity.getInputOffset().equals(Vec3i.ZERO);
         if (receiver) {
            BlockPos inputPos = pos.offset(entity.getInputOffset());
            if (level.getBlockEntity(inputPos) instanceof EnergyTransmitterBlockEntity inputEntity
               && inputEntity.getOutputOffset().equals(entity.getInputOffset().multiply(-1))) {
               BlockState inputState = level.getBlockState(inputPos);
               if (inputState.hasProperty(EnergyTransmitterBlock.DIRECT_POWER)) {
                  directOutputPower = Math.max((Integer)inputState.getValue(EnergyTransmitterBlock.DIRECT_POWER) - 1, 0);
               }
            }
         }

         boolean powered = receiver
            ? directOutputPower > 0
            : state.hasProperty(EnergyTransmitterBlock.DIRECT_POWER) && (Integer)state.getValue(EnergyTransmitterBlock.DIRECT_POWER) > 0;
         if (state.hasProperty(EnergyTransmitterBlock.POWER)
            && state.hasProperty(EnergyTransmitterBlock.POWERED)
            && (
               (Integer)state.getValue(EnergyTransmitterBlock.POWER) != directOutputPower || (Boolean)state.getValue(EnergyTransmitterBlock.POWERED) != powered
            )) {
            level.setBlockAndUpdate(
               pos, (BlockState)((BlockState)state.setValue(EnergyTransmitterBlock.POWER, directOutputPower)).setValue(EnergyTransmitterBlock.POWERED, powered)
            );
         }
      }
   }

   public void loadAdditional(CompoundTag compoundTag, Provider provider) {
      super.loadAdditional(compoundTag, provider);
      if (compoundTag.contains("input_offset")) {
         Vec3i.CODEC
            .parse(NbtOps.INSTANCE, compoundTag.get("input_offset"))
            .resultOrPartial(s -> EternalStarlight.LOGGER.warn("Failed to parse Energy Transmitter input offset: {}", s))
            .ifPresent(pos -> this.inputOffset = pos);
      }

      if (compoundTag.contains("output_offset")) {
         Vec3i.CODEC
            .parse(NbtOps.INSTANCE, compoundTag.get("output_offset"))
            .resultOrPartial(s -> EternalStarlight.LOGGER.warn("Failed to parse Energy Transmitter output offset: {}", s))
            .ifPresent(pos -> this.outputOffset = pos);
      }
   }

   protected void saveAdditional(CompoundTag compoundTag, Provider provider) {
      super.saveAdditional(compoundTag, provider);
      compoundTag.put("input_offset", (Tag)Vec3i.CODEC.encodeStart(NbtOps.INSTANCE, this.inputOffset).getOrThrow());
      compoundTag.put("output_offset", (Tag)Vec3i.CODEC.encodeStart(NbtOps.INSTANCE, this.outputOffset).getOrThrow());
   }

   @Nullable
   public Packet<ClientGamePacketListener> getUpdatePacket() {
      return ClientboundBlockEntityDataPacket.create(this);
   }

   public CompoundTag getUpdateTag(Provider provider) {
      return this.saveWithFullMetadata(provider);
   }
}
