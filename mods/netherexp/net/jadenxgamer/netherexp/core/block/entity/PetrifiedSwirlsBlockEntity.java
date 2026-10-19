package net.jadenxgamer.netherexp.core.block.entity;

import java.util.UUID;
import net.jadenxgamer.netherexp.core.block.PetrifiedSwirlsBlock;
import net.jadenxgamer.netherexp.registry.JNEBlockEntityType;
import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class PetrifiedSwirlsBlockEntity extends BlockEntity {
   private UUID petrifier;
   private BlockPos petrifierPos;
   private ResourceLocation unpetrifiedBlock;
   private long nextCheckTime = 20L;
   private boolean pendingUnpetrify = false;

   public PetrifiedSwirlsBlockEntity(BlockPos pos, BlockState blockState) {
      super(JNEBlockEntityType.PETRIFIED_SWIRLS.get(), pos, blockState);
   }

   public CompoundTag getUpdateTag(Provider registries) {
      CompoundTag tag = super.getUpdateTag(registries);
      this.saveAdditional(tag, registries);
      return tag;
   }

   public void handleUpdateTag(CompoundTag tag, Provider registries) {
      super.handleUpdateTag(tag, registries);
      this.loadAdditional(tag, registries);
   }

   public BlockEntityType<?> getType() {
      return JNEBlockEntityType.PETRIFIED_SWIRLS.get();
   }

   public boolean triggerEvent(int id, int type) {
      if (this.level != null && id == 1) {
         if (this.level.isClientSide()) {
            switch (type) {
               case 0:
                  PetrifiedSwirlsBlock.Client.petrificationParticle(this.level, this.level.random, this.getBlockPos());
                  break;
               case 1:
                  PetrifiedSwirlsBlock.Client.unpetrifyParticle(this.level, this.level.random, this.getBlockPos());
                  break;
               case 2:
                  PetrifiedSwirlsBlock.Client.attractToPetrifierParticle(this.level, this.level.random, this.getBlockPos());
            }
         }

         return true;
      } else {
         return super.triggerEvent(id, type);
      }
   }

   public void tick() {
      if (this.level != null && !this.level.isClientSide()) {
         if (this.pendingUnpetrify) {
            this.performUnpetrify();
         } else {
            long gameTime = this.level.getGameTime();
            if (gameTime >= this.nextCheckTime || this.nextCheckTime == 0L) {
               if (this.petrifier != null && this.level instanceof ServerLevel serverLevel) {
                  if (this.level.random.nextInt(5) == 0) {
                     serverLevel.blockEvent(this.getBlockPos(), this.getBlockState().getBlock(), 1, 0);
                  }

                  if (this.petrifierPos != null && serverLevel.isLoaded(this.petrifierPos)) {
                     Entity entity = serverLevel.getEntity(this.petrifier);
                     if (entity == null || !entity.isAlive()) {
                        this.unpetrify();
                     }
                  }
               }

               this.nextCheckTime = gameTime + 20L + this.level.random.nextInt(61);
            }
         }
      }
   }

   private void unpetrify() {
      if (this.level != null && this.unpetrifiedBlock != null) {
         this.level.blockEvent(this.getBlockPos(), this.getBlockState().getBlock(), 1, 1);
         this.pendingUnpetrify = true;
         this.level.scheduleTick(this.getBlockPos(), this.getBlockState().getBlock(), 2);
      }
   }

   private void performUnpetrify() {
      if (this.pendingUnpetrify && this.level != null && this.unpetrifiedBlock != null) {
         this.pendingUnpetrify = false;
         Block targetBlock = (Block)BuiltInRegistries.BLOCK.get(this.unpetrifiedBlock);
         if (targetBlock != Blocks.AIR) {
            BlockState currentState = this.getBlockState();
            BlockState newState = targetBlock.defaultBlockState();
            if (newState.hasProperty(BlockStateProperties.FACING) && currentState.hasProperty(BlockStateProperties.FACING)) {
               newState = (BlockState)newState.setValue(BlockStateProperties.FACING, (Direction)currentState.getValue(BlockStateProperties.FACING));
            }

            if (newState.hasProperty(BlockStateProperties.WATERLOGGED) && currentState.hasProperty(BlockStateProperties.WATERLOGGED)) {
               newState = (BlockState)newState.setValue(BlockStateProperties.WATERLOGGED, (Boolean)currentState.getValue(BlockStateProperties.WATERLOGGED));
            }

            this.level.setBlock(this.getBlockPos(), newState, 3);
            this.level.playSound(null, this.getBlockPos(), JNESoundEvents.SOUL_SWIRLS_UNPETRIFY.get(), SoundSource.BLOCKS, 0.5F, 1.0F);
         }
      }
   }

   protected void saveAdditional(CompoundTag nbt, Provider registries) {
      super.saveAdditional(nbt, registries);
      if (this.petrifier != null) {
         nbt.putUUID("Petrifier", this.petrifier);
      }

      if (this.petrifierPos != null) {
         nbt.put("PetrifierPos", NbtUtils.writeBlockPos(this.petrifierPos));
      }

      if (this.unpetrifiedBlock != null) {
         nbt.putString("UnpetrifiedBlock", this.unpetrifiedBlock.toString());
      }
   }

   protected void loadAdditional(CompoundTag nbt, Provider registries) {
      super.loadAdditional(nbt, registries);
      if (nbt.hasUUID("Petrifier")) {
         this.petrifier = nbt.getUUID("Petrifier");
      }

      if (nbt.contains("PetrifierPos")) {
         this.petrifierPos = (BlockPos)NbtUtils.readBlockPos(nbt, "PetrifierPos").orElse(null);
      }

      if (nbt.contains("UnpetrifiedBlock")) {
         this.unpetrifiedBlock = ResourceLocation.tryParse(nbt.getString("UnpetrifiedBlock"));
      }

      this.pendingUnpetrify = false;
   }

   public void setPetrifier(Entity petrifier) {
      this.petrifier = petrifier.getUUID();
      this.petrifierPos = petrifier.blockPosition();
   }

   public void setUnpetrifiedBlock(ResourceLocation unpetrifiedBlock) {
      this.unpetrifiedBlock = unpetrifiedBlock;
   }

   public UUID getPetrifier() {
      return this.petrifier;
   }

   public BlockPos getPetrifierPos() {
      return this.petrifierPos;
   }

   public ResourceLocation getUnpetrifiedBlock() {
      return this.unpetrifiedBlock;
   }

   public boolean hasPendingUnpetrify() {
      return this.pendingUnpetrify;
   }

   public Packet<ClientGamePacketListener> getUpdatePacket() {
      return ClientboundBlockEntityDataPacket.create(this);
   }
}
