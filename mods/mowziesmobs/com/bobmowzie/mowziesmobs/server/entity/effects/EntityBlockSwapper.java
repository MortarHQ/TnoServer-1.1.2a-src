package com.bobmowzie.mowziesmobs.server.entity.effects;

import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.bobmowzie.mowziesmobs.server.entity.ILinkedEntity;
import com.bobmowzie.mowziesmobs.server.entity.sculptor.EntitySculptor;
import com.bobmowzie.mowziesmobs.server.message.MessageLinkEntities;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

public class EntityBlockSwapper extends Entity {
   private static final EntityDataAccessor<BlockState> ORIG_BLOCK_STATE = SynchedEntityData.defineId(
      EntityBlockSwapper.class, EntityDataSerializers.BLOCK_STATE
   );
   private static final EntityDataAccessor<Integer> RESTORE_TIME = SynchedEntityData.defineId(EntityBlockSwapper.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<BlockPos> POS = SynchedEntityData.defineId(EntityBlockSwapper.class, EntityDataSerializers.BLOCK_POS);
   protected int duration;
   protected boolean breakParticlesEnd;
   private BlockPos pos;

   public EntityBlockSwapper(EntityType<? extends EntityBlockSwapper> type, Level world) {
      super(type, world);
      this.breakParticlesEnd = false;
   }

   public EntityBlockSwapper(
      EntityType<? extends EntityBlockSwapper> type,
      Level world,
      BlockPos pos,
      BlockState newBlock,
      int duration,
      boolean breakParticlesStart,
      boolean breakParticlesEnd
   ) {
      super(type, world);
      this.setStorePos(pos);
      this.setRestoreTime(duration);
      this.breakParticlesEnd = breakParticlesEnd;
      this.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
      if (!world.isClientSide) {
         this.setOrigBlock(world.getBlockState(pos));
         if (breakParticlesStart) {
            world.destroyBlock(pos, false);
         }

         world.setBlock(pos, newBlock, 19);
      }

      for (EntityBlockSwapper swapper : world.getEntitiesOfClass(EntityBlockSwapper.class, this.getBoundingBox())) {
         if (swapper != this) {
            if (swapper instanceof EntityBlockSwapper.EntityBlockSwapperSculptor swapperSculptor) {
               this.setOrigBlock(swapperSculptor.getOrigBlockAtLocation(pos));
            } else {
               this.setOrigBlock(swapper.getOrigBlock());
               swapper.discard();
            }
         }
      }
   }

   public static void swapBlock(Level world, BlockPos pos, BlockState newBlock, int duration, boolean breakParticlesStart, boolean breakParticlesEnd) {
      if (!world.isClientSide) {
         EntityBlockSwapper swapper = new EntityBlockSwapper(
            (EntityType<? extends EntityBlockSwapper>)EntityHandler.BLOCK_SWAPPER.get(), world, pos, newBlock, duration, breakParticlesStart, breakParticlesEnd
         );
         world.addFreshEntity(swapper);
      }
   }

   public boolean isBlockPosInsideSwapper(BlockPos pos) {
      return pos.equals(this.getStorePos());
   }

   public boolean shouldRender(double p_145770_1_, double p_145770_3_, double p_145770_5_) {
      return false;
   }

   protected void defineSynchedData(@NotNull Builder builder) {
      builder.define(ORIG_BLOCK_STATE, Blocks.DIRT.defaultBlockState());
      builder.define(RESTORE_TIME, 20);
      builder.define(POS, new BlockPos(0, 0, 0));
   }

   public int getRestoreTime() {
      return (Integer)this.entityData.get(RESTORE_TIME);
   }

   public void setRestoreTime(int restoreTime) {
      this.entityData.set(RESTORE_TIME, restoreTime);
      this.duration = restoreTime;
   }

   public BlockPos getStorePos() {
      return (BlockPos)this.entityData.get(POS);
   }

   public void setStorePos(BlockPos bpos) {
      this.entityData.set(POS, bpos);
      this.pos = bpos;
   }

   @Nullable
   public BlockState getOrigBlock() {
      return (BlockState)this.getEntityData().get(ORIG_BLOCK_STATE);
   }

   public void setOrigBlock(BlockState block) {
      this.getEntityData().set(ORIG_BLOCK_STATE, block);
   }

   public void restoreBlock() {
      List<EntityBlockSwapper> swappers = this.level().getEntitiesOfClass(EntityBlockSwapper.class, this.getBoundingBox());
      if (!this.level().isClientSide) {
         boolean canReplace = true;

         for (EntityBlockSwapper swapper : swappers) {
            if (swapper != this && swapper.isBlockPosInsideSwapper(this.pos)) {
               canReplace = false;
               break;
            }
         }

         if (canReplace) {
            if (this.breakParticlesEnd) {
               this.level().destroyBlock(this.pos, false);
            }

            this.level().setBlock(this.pos, this.getOrigBlock(), 19);
         }

         this.discard();
      }
   }

   public void tick() {
      super.tick();
      if (this.canRestoreBlock()) {
         this.restoreBlock();
      }
   }

   protected boolean canRestoreBlock() {
      return this.tickCount > this.duration && this.level().getEntitiesOfClass(Player.class, this.getBoundingBox()).isEmpty();
   }

   public void addAdditionalSaveData(CompoundTag compound) {
      BlockState blockState = (BlockState)this.getEntityData().get(ORIG_BLOCK_STATE);
      compound.put("block", NbtUtils.writeBlockState(blockState));
      compound.putInt("restoreTime", this.getRestoreTime());
      compound.putInt("storePosX", this.getStorePos().getX());
      compound.putInt("storePosY", this.getStorePos().getY());
      compound.putInt("storePosZ", this.getStorePos().getZ());
   }

   public void readAdditionalSaveData(CompoundTag compound) {
      Tag blockNBT = compound.get("block");
      if (blockNBT != null) {
         BlockState blockState = NbtUtils.readBlockState(this.level().holderLookup(Registries.BLOCK), (CompoundTag)blockNBT);
         this.setOrigBlock(blockState);
      }

      this.setRestoreTime(compound.getInt("restoreTime"));
      this.setStorePos(new BlockPos(compound.getInt("storePosX"), compound.getInt("storePosY"), compound.getInt("storePosZ")));
   }

   public static class EntityBlockSwapperSculptor extends EntityBlockSwapper {
      private int height;
      private int radius;
      private BlockState[][][] origStates;

      public EntityBlockSwapperSculptor(EntityType<? extends EntityBlockSwapper.EntityBlockSwapperSculptor> type, Level world) {
         super(type, world);
         this.breakParticlesEnd = false;
         this.height = EntitySculptor.TEST_HEIGHT + 3;
         this.radius = EntitySculptor.TEST_RADIUS;
         this.origStates = new BlockState[this.height][this.radius * 2][this.radius * 2];
         this.setBoundingBox(this.makeBoundingBox());
      }

      public EntityBlockSwapperSculptor(
         EntityType<? extends EntityBlockSwapper.EntityBlockSwapperSculptor> type,
         Level world,
         BlockPos pos,
         BlockState newBlock,
         int duration,
         boolean breakParticlesStart,
         boolean breakParticlesEnd
      ) {
         super(type, world);
         this.height = EntitySculptor.TEST_HEIGHT + 3;
         this.radius = EntitySculptor.TEST_RADIUS;
         this.origStates = new BlockState[this.height][this.radius * 2][this.radius * 2];
         this.setStorePos(pos);
         this.setRestoreTime(duration);
         this.breakParticlesEnd = breakParticlesEnd;
         this.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
         this.setBoundingBox(this.makeBoundingBox());
         List<EntityBlockSwapper.EntityBlockSwapperSculptor> swapperSculptors = world.getEntitiesOfClass(
            EntityBlockSwapper.EntityBlockSwapperSculptor.class, this.getBoundingBox()
         );
         if (!world.isClientSide) {
            for (int k = 0; k < this.height; k++) {
               for (int i = -this.radius; i < this.radius; i++) {
                  for (int j = -this.radius; j < this.radius; j++) {
                     BlockPos thisPos = pos.offset(i, k, j);
                     if (this.isBlockPosInsideSwapper(thisPos) && world.getBlockState(thisPos).getBlock() != Blocks.BEDROCK) {
                        this.origStates[k][i + this.radius][j + this.radius] = world.getBlockState(thisPos);
                        if (breakParticlesStart) {
                           world.destroyBlock(thisPos, false);
                        }

                        world.setBlock(thisPos, newBlock, 19);

                        for (EntityBlockSwapper.EntityBlockSwapperSculptor swapper : swapperSculptors) {
                           if (swapper != this && swapper.getOrigBlockAtLocation(thisPos) != null) {
                              this.origStates[k][i + this.radius][j + this.radius] = swapper.getOrigBlockAtLocation(thisPos);
                              break;
                           }
                        }
                     }
                  }
               }
            }
         }

         List<EntityBlockSwapper.EntityBlockSwapperSculptor> swappers = world.getEntitiesOfClass(
            EntityBlockSwapper.EntityBlockSwapperSculptor.class, this.getBoundingBox()
         );
         if (!swappers.isEmpty()) {
            for (EntityBlockSwapper swapper : swappers) {
               if (swapper != this && !(swapper instanceof EntityBlockSwapper.EntityBlockSwapperSculptor)) {
                  this.setOrigBlockAtLocation(swapper.getStorePos(), swapper.getOrigBlock());
               }
            }
         }
      }

      @Override
      public boolean isBlockPosInsideSwapper(BlockPos pos) {
         return new Vec2(pos.getX() - this.getStorePos().getX(), pos.getZ() - this.getStorePos().getZ()).length()
               < EntitySculptor.testRadiusAtHeight(pos.getY() - this.getY())
            && this.getBoundingBox().contains(new Vec3(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5));
      }

      public void setOrigBlockAtLocation(BlockPos pos, BlockState state) {
         if (this.isBlockPosInsideSwapper(pos)) {
            BlockPos indices = this.posToArrayIndices(pos);
            this.origStates[indices.getY()][indices.getX()][indices.getZ()] = state;
         }
      }

      public BlockState getOrigBlockAtLocation(BlockPos pos) {
         if (this.isBlockPosInsideSwapper(pos)) {
            BlockPos indices = this.posToArrayIndices(pos);
            return this.origStates[indices.getY()][indices.getX()][indices.getZ()];
         } else {
            return null;
         }
      }

      protected BlockPos posToArrayIndices(BlockPos pos) {
         return pos.subtract(this.getStorePos()).offset(this.radius, 0, this.radius);
      }

      protected AABB makeBoundingBox() {
         return EntityDimensions.scalable(this.radius * 2, this.height).makeBoundingBox(this.position());
      }

      @Override
      public void restoreBlock() {
         if (!this.level().isClientSide) {
            List<EntityBlockSwapper> swappers = this.level().getEntitiesOfClass(EntityBlockSwapper.class, this.getBoundingBox());

            for (int k = 0; k < this.height; k++) {
               for (int i = -this.radius; i < this.radius; i++) {
                  for (int j = -this.radius; j < this.radius; j++) {
                     if (!this.level().isClientSide) {
                        BlockPos thisPos = this.getStorePos().offset(i, k, j);
                        if (this.isBlockPosInsideSwapper(thisPos)) {
                           boolean canReplace = true;

                           for (EntityBlockSwapper swapper : swappers) {
                              if (swapper != this && swapper.isBlockPosInsideSwapper(thisPos)) {
                                 canReplace = false;
                                 break;
                              }
                           }

                           if (canReplace) {
                              BlockState restoreState = this.origStates[k][i + this.radius][j + this.radius];
                              if (restoreState != null) {
                                 if (this.breakParticlesEnd) {
                                    this.level().destroyBlock(thisPos, false);
                                 }

                                 this.level().setBlock(thisPos, restoreState, 19);
                              }
                           }
                        }
                     }
                  }
               }
            }

            this.discard();
         }
      }

      @Override
      protected boolean canRestoreBlock() {
         return this.tickCount > this.duration
            && this.level().getEntitiesOfClass(EntitySculptor.class, this.getBoundingBox(), EntitySculptor::isTesting).isEmpty();
      }

      @Override
      public void addAdditionalSaveData(CompoundTag compound) {
         compound.putInt("restoreTime", this.getRestoreTime());
         compound.putInt("storePosX", this.getStorePos().getX());
         compound.putInt("storePosY", this.getStorePos().getY());
         compound.putInt("storePosZ", this.getStorePos().getZ());

         for (int i = 0; i < this.radius * 2; i++) {
            for (int j = 0; j < this.radius * 2; j++) {
               for (int k = 0; k < this.height; k++) {
                  BlockState block = this.origStates[k][i][j];
                  if (block != null) {
                     compound.put("block_" + i + "_" + j + "_" + k, NbtUtils.writeBlockState(block));
                  }
               }
            }
         }
      }

      @Override
      public void readAdditionalSaveData(CompoundTag compound) {
         this.setRestoreTime(compound.getInt("restoreTime"));
         this.setStorePos(new BlockPos(compound.getInt("storePosX"), compound.getInt("storePosY"), compound.getInt("storePosZ")));

         for (int i = 0; i < this.radius * 2; i++) {
            for (int j = 0; j < this.radius * 2; j++) {
               for (int k = 0; k < this.height; k++) {
                  Tag blockNBT = compound.get("block_" + i + "_" + j + "_" + k);
                  if (blockNBT != null) {
                     BlockState blockState = NbtUtils.readBlockState(this.level().holderLookup(Registries.BLOCK), (CompoundTag)blockNBT);
                     this.origStates[k][i][j] = blockState;
                  }
               }
            }
         }
      }
   }

   public static class EntityBlockSwapperTunneling extends EntityBlockSwapper implements ILinkedEntity {
      private LivingEntity cachedTunneler;
      private static final EntityDataAccessor<Optional<UUID>> TUNNELER = SynchedEntityData.defineId(
         EntityBlockSwapper.EntityBlockSwapperTunneling.class, EntityDataSerializers.OPTIONAL_UUID
      );

      public EntityBlockSwapperTunneling(EntityType<? extends EntityBlockSwapper.EntityBlockSwapperTunneling> type, Level world) {
         super(type, world);
      }

      public EntityBlockSwapperTunneling(
         EntityType<? extends EntityBlockSwapper.EntityBlockSwapperTunneling> type,
         Level world,
         BlockPos pos,
         BlockState newBlock,
         int duration,
         boolean breakParticlesStart,
         boolean breakParticlesEnd,
         LivingEntity tunneler
      ) {
         super(type, world, pos, newBlock, duration, breakParticlesStart, breakParticlesEnd);
         this.cachedTunneler = tunneler;
         if (!world.isClientSide && tunneler != null) {
            this.setTunnelerID(tunneler.getUUID());
         }
      }

      public boolean isPickable() {
         return false;
      }

      @Override
      protected void defineSynchedData(@NotNull Builder builder) {
         super.defineSynchedData(builder);
         builder.define(TUNNELER, Optional.empty());
      }

      public Optional<UUID> getTunnelerID() {
         return (Optional<UUID>)this.getEntityData().get(TUNNELER);
      }

      public void setTunnelerID(UUID id) {
         this.getEntityData().set(TUNNELER, Optional.of(id));
      }

      public LivingEntity getTunneler() {
         if (this.cachedTunneler != null && !this.cachedTunneler.isRemoved()) {
            return this.cachedTunneler;
         }

         if (this.getTunnelerID().isPresent() && this.level() instanceof ServerLevel) {
            Entity entity = ((ServerLevel)this.level()).getEntity(this.getTunnelerID().get());
            if (entity instanceof LivingEntity) {
               this.cachedTunneler = (LivingEntity)entity;
               PacketDistributor.sendToPlayersTrackingEntityAndSelf(this, MessageLinkEntities.fromEntity(this, this.cachedTunneler), new CustomPacketPayload[0]);
            }

            return this.cachedTunneler;
         } else {
            return null;
         }
      }

      public boolean canCollideWith(Entity p_20303_) {
         return super.canCollideWith(p_20303_);
      }

      public boolean canBeCollidedWith() {
         return true;
      }

      public Entity getRootVehicle() {
         return this.getTunneler();
      }

      @Override
      public void link(Entity entity) {
         if (entity instanceof LivingEntity) {
            this.cachedTunneler = (LivingEntity)entity;
         }
      }

      @Override
      public void readAdditionalSaveData(CompoundTag compound) {
         super.readAdditionalSaveData(compound);
         this.setTunnelerID(compound.getUUID("tunneler"));
      }

      @Override
      public void addAdditionalSaveData(CompoundTag compound) {
         super.addAdditionalSaveData(compound);
         if (this.getTunnelerID().isPresent()) {
            compound.putUUID("tunneler", this.getTunnelerID().get());
         }
      }

      @NotNull
      public Packet<ClientGamePacketListener> getAddEntityPacket(@NotNull ServerEntity entity) {
         return new ClientboundAddEntityPacket(this, entity, this.cachedTunneler == null ? 0 : this.cachedTunneler.getId());
      }

      public void recreateFromPacket(ClientboundAddEntityPacket packet) {
         super.recreateFromPacket(packet);
         Entity entity = this.level().getEntity(packet.getData());
         if (entity instanceof LivingEntity) {
            this.cachedTunneler = (LivingEntity)entity;
         }
      }
   }
}
