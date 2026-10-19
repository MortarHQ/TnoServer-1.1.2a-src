package com.iafenvoy.iceandfire.entity;

import com.iafenvoy.iceandfire.data.component.ChainData;
import com.iafenvoy.iceandfire.registry.IafEntities;
import com.iafenvoy.iceandfire.registry.IafItems;
import java.util.concurrent.atomic.AtomicBoolean;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.phys.AABB;

public class ChainTieEntity extends HangingEntity {
   public ChainTieEntity(EntityType<? extends HangingEntity> type, Level worldIn) {
      super(type, worldIn);
   }

   public ChainTieEntity(EntityType<? extends HangingEntity> type, Level worldIn, BlockPos hangingPositionIn) {
      super(type, worldIn, hangingPositionIn);
      this.setPos(hangingPositionIn.getX() + 0.5, hangingPositionIn.getY(), hangingPositionIn.getZ() + 0.5);
   }

   public static ChainTieEntity createTie(Level worldIn, BlockPos fence) {
      ChainTieEntity chainTieEntity = new ChainTieEntity((EntityType<? extends HangingEntity>)IafEntities.CHAIN_TIE.get(), worldIn, fence);
      worldIn.addFreshEntity(chainTieEntity);
      chainTieEntity.playPlacementSound();
      return chainTieEntity;
   }

   public static ChainTieEntity getKnotForPosition(Level worldIn, BlockPos pos) {
      int i = pos.getX();
      int j = pos.getY();
      int k = pos.getZ();

      for (ChainTieEntity entityleashknot : worldIn.getEntitiesOfClass(ChainTieEntity.class, new AABB(i - 1.0, j - 1.0, k - 1.0, i + 1.0, j + 1.0, k + 1.0))) {
         if (entityleashknot != null && entityleashknot.pos != null && entityleashknot.pos.equals(pos)) {
            return entityleashknot;
         }
      }

      return null;
   }

   public void setPos(double x, double y, double z) {
      super.setPos(Mth.floor(x) + 0.5, Mth.floor(y) + 0.5, Mth.floor(z) + 0.5);
   }

   protected AABB calculateBoundingBox(BlockPos pos, Direction side) {
      this.setPosRaw(this.pos.getX() + 0.5, this.pos.getY() + 0.5, this.pos.getZ() + 0.5);
      double xSize = 0.3;
      double ySize = 0.875;
      AABB box = new AABB(this.getX() - xSize, this.getY() - 0.5, this.getZ() - xSize, this.getX() + xSize, this.getY() + ySize - 0.5, this.getZ() + xSize);
      this.setBoundingBox(box);
      return box;
   }

   public boolean hurt(DamageSource source, float amount) {
      return source.getEntity() != null && source.getEntity() instanceof Player ? super.hurt(source, amount) : false;
   }

   public void addAdditionalSaveData(CompoundTag compound) {
      BlockPos blockpos = this.getPos();
      compound.putInt("TileX", blockpos.getX());
      compound.putInt("TileY", blockpos.getY());
      compound.putInt("TileZ", blockpos.getZ());
   }

   public void readAdditionalSaveData(CompoundTag compound) {
      this.pos = new BlockPos(compound.getInt("TileX"), compound.getInt("TileY"), compound.getInt("TileZ"));
   }

   public boolean shouldRenderAtSqrDistance(double distance) {
      return distance < 1024.0;
   }

   public void dropItem(Entity brokenEntity) {
      this.playSound((SoundEvent)SoundEvents.ARMOR_EQUIP_CHAIN.value(), 1.0F, 1.0F);
   }

   protected void defineSynchedData(Builder builder) {
   }

   public void remove(RemovalReason removalReason) {
      super.remove(removalReason);
      double d0 = 30.0;

      for (LivingEntity livingEntity : this.level()
         .getEntitiesOfClass(
            LivingEntity.class, new AABB(this.getX() - d0, this.getY() - d0, this.getZ() - d0, this.getX() + d0, this.getY() + d0, this.getZ() + d0)
         )) {
         ChainData chainData = ChainData.get(livingEntity);
         if (chainData.isChainedTo(this.getUUID())) {
            chainData.removeChain(this.getUUID());
            ItemEntity entityitem = new ItemEntity(this.level(), this.getX(), this.getY() + 1.0, this.getZ(), new ItemStack((ItemLike)IafItems.CHAIN.get()));
            entityitem.setNoPickUpDelay();
            this.level().addFreshEntity(entityitem);
         }
      }
   }

   public InteractionResult interact(Player player, InteractionHand hand) {
      if (this.level().isClientSide) {
         return InteractionResult.SUCCESS;
      }

      AtomicBoolean flag = new AtomicBoolean(false);
      double radius = 30.0;

      for (LivingEntity livingEntity : this.level()
         .getEntitiesOfClass(
            LivingEntity.class,
            new AABB(this.getX() - radius, this.getY() - radius, this.getZ() - radius, this.getX() + radius, this.getY() + radius, this.getZ() + radius)
         )) {
         ChainData chainData = ChainData.get(livingEntity);
         if (chainData.isChainedTo(player.getUUID())) {
            chainData.removeChain(player.getUUID());
            chainData.attachChain(this.getUUID());
            flag.set(true);
         }
      }

      if (!flag.get()) {
         this.remove(RemovalReason.DISCARDED);
         return InteractionResult.SUCCESS;
      } else {
         return InteractionResult.CONSUME;
      }
   }

   public boolean survives() {
      return this.level().getBlockState(this.pos).getBlock() instanceof WallBlock;
   }

   public void playPlacementSound() {
      this.playSound((SoundEvent)SoundEvents.ARMOR_EQUIP_CHAIN.value(), 1.0F, 1.0F);
   }
}
