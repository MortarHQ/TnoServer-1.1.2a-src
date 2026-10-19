package com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Leviathan;

import com.github.L_Ender.cataclysm.entity.effect.Cm_Falling_Block_Entity;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModParticle;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.cataclysm.init.ModTag;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;

public class Dimensional_Rift_Entity extends Entity {
   protected static final EntityDataAccessor<Integer> LIFESPAN = SynchedEntityData.defineId(Dimensional_Rift_Entity.class, EntityDataSerializers.INT);
   protected static final EntityDataAccessor<Integer> STAGE = SynchedEntityData.defineId(Dimensional_Rift_Entity.class, EntityDataSerializers.INT);
   private boolean madeOpenNoise = false;
   private boolean madeCloseNoise = false;
   private boolean madeParticle = false;
   @Nullable
   private LivingEntity owner;
   @Nullable
   private UUID ownerUUID;
   public int ambientSoundTime;
   private final ObjectArrayList<BlockPos> toBlow = new ObjectArrayList();

   public Dimensional_Rift_Entity(EntityType<?> entityTypeIn, Level worldIn) {
      super(entityTypeIn, worldIn);
   }

   public Dimensional_Rift_Entity(Level worldIn, double x, double y, double z, LivingEntity casterIn) {
      this((EntityType<?>)ModEntities.DIMENSIONAL_RIFT.get(), worldIn);
      this.setOwner(casterIn);
      this.setLifespan(300);
      this.setPos(x, y, z);
   }

   public void tick() {
      super.tick();
      if (!this.madeOpenNoise) {
         this.gameEvent(GameEvent.ENTITY_PLACE);
         this.playSound((SoundEvent)ModSounds.BLACK_HOLE_OPENING.get(), 0.7F, 1.0F + this.random.nextFloat() * 0.2F);
         this.madeOpenNoise = true;
      }

      for (Entity entity : this.level().getEntities(this, this.getBoundingBox().inflate(30.0))) {
         if (entity != this.owner
            && (!(entity instanceof Player) || !((Player)entity).getAbilities().invulnerable)
            && !this.isAlliedTo(entity)
            && !(entity instanceof The_Leviathan_Entity)
            && !(entity instanceof The_Leviathan_Tongue_Entity)) {
            Vec3 diff = entity.position().subtract(this.position().add(0.0, 0.0, 0.0));
            if (entity instanceof LivingEntity) {
               diff = diff.normalize().scale(this.getStage() * 0.015);
               entity.setDeltaMovement(entity.getDeltaMovement().subtract(diff));
            } else if (!entity.getType().is(ModTag.DIMENSIONAL_LIFT_IMMUNE)) {
               diff = diff.normalize().scale(this.getStage() * 0.045);
               entity.setDeltaMovement(entity.getDeltaMovement().subtract(diff));
            }
         }
      }

      this.berserkBlockBreaking(15, 15, 15);

      for (LivingEntity livingentity : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(0.2, 0.0, 0.2))) {
         this.damage(livingentity);
      }

      for (Entity entity : this.level().getEntities(this, this.getBoundingBox().inflate(0.5))) {
         if (entity instanceof Cm_Falling_Block_Entity) {
            entity.remove(RemovalReason.DISCARDED);
         }
      }

      if (this.random.nextInt(3000) < this.ambientSoundTime++) {
         this.resetAmbientSoundTime();
         this.playSound((SoundEvent)ModSounds.BLACK_HOLE_LOOP.get(), 0.7F, 1.0F + this.random.nextFloat() * 0.2F);
      }

      this.setLifespan(this.getLifespan() - 1);
      if (this.getLifespan() <= 100) {
         if (!this.madeCloseNoise) {
            this.gameEvent(GameEvent.ENTITY_PLACE);
            this.playSound((SoundEvent)ModSounds.BLACK_HOLE_CLOSING.get(), 0.7F, 1.0F + this.random.nextFloat() * 0.2F);
            this.madeCloseNoise = true;
         }

         if (this.tickCount % 40 == 0) {
            this.setStage(this.getStage() - 1);
         }

         if (this.getStage() <= 0) {
            if (!this.madeParticle) {
               if (this.level().isClientSide) {
                  this.level().addParticle((ParticleOptions)ModParticle.SHOCK_WAVE.get(), this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
               } else {
                  this.level().explode(this.owner, this.getX(), this.getY(), this.getZ(), 4.0F, false, ExplosionInteraction.NONE);
               }

               this.madeParticle = true;
            } else {
               this.discard();
            }
         }
      }
   }

   private void damage(LivingEntity Hitentity) {
      LivingEntity livingentity = this.getOwner();
      if (Hitentity.isAlive()
         && !Hitentity.isInvulnerable()
         && Hitentity != livingentity
         && !(Hitentity instanceof The_Leviathan_Entity)
         && this.tickCount % 5 == 0) {
         if (livingentity == null) {
            Hitentity.hurt(this.damageSources().magic(), 10.0F);
         } else {
            if (livingentity.isAlliedTo(Hitentity)) {
               return;
            }

            Hitentity.hurt(this.damageSources().indirectMagic(this, livingentity), 10.0F);
         }
      }
   }

   private void berserkBlockBreaking(int x, int y, int z) {
      int MthX = Mth.floor(this.getX());
      int MthY = Mth.floor(this.getY());
      int MthZ = Mth.floor(this.getZ());
      if (!this.level().isClientSide && EventHooks.canEntityGrief(this.level(), this)) {
         for (int k2 = -x; k2 <= x; k2++) {
            for (int l2 = -z; l2 <= z; l2++) {
               for (int j = -y; j <= y; j++) {
                  int i3 = MthX + k2;
                  int k = MthY + j;
                  int l = MthZ + l2;
                  BlockPos blockpos = new BlockPos(i3, k, l);
                  BlockPos blockonpos = new BlockPos(i3, k + 1, l);
                  BlockState block = this.level().getBlockState(blockpos);
                  BlockState blockon = this.level().getBlockState(blockonpos);
                  BlockEntity tileEntity = this.level().getBlockEntity(blockpos);
                  if ((blockon == Blocks.AIR.defaultBlockState() || blockon == Blocks.WATER.defaultBlockState())
                     && block != Blocks.AIR.defaultBlockState()
                     && !block.is(ModTag.LEVIATHAN_IMMUNE)
                     && tileEntity == null
                     && this.random.nextInt(2000) == 0) {
                     this.level().removeBlock(blockpos, true);
                     Cm_Falling_Block_Entity fallingBlockEntity = new Cm_Falling_Block_Entity(this.level(), i3 + 0.5, k + 0.5, l + 0.5, block, 5);
                     this.level().setBlock(blockpos, block.getFluidState().createLegacyBlock(), 3);
                     this.level().addFreshEntity(fallingBlockEntity);
                  }
               }
            }
         }
      }
   }

   public int getAmbientSoundInterval() {
      return 80;
   }

   private void resetAmbientSoundTime() {
      this.ambientSoundTime = -this.getAmbientSoundInterval();
   }

   public int getLifespan() {
      return (Integer)this.entityData.get(LIFESPAN);
   }

   public void setLifespan(int i) {
      this.entityData.set(LIFESPAN, i);
   }

   public int getStage() {
      return (Integer)this.entityData.get(STAGE);
   }

   public void setStage(int i) {
      this.entityData.set(STAGE, i);
   }

   public void setOwner(@Nullable LivingEntity p_19719_) {
      this.owner = p_19719_;
      this.ownerUUID = p_19719_ == null ? null : p_19719_.getUUID();
   }

   @Nullable
   public LivingEntity getOwner() {
      if (this.owner == null && this.ownerUUID != null && this.level() instanceof ServerLevel) {
         Entity entity = ((ServerLevel)this.level()).getEntity(this.ownerUUID);
         if (entity instanceof LivingEntity) {
            this.owner = (LivingEntity)entity;
         }
      }

      return this.owner;
   }

   protected void defineSynchedData(Builder p_326229_) {
      p_326229_.define(LIFESPAN, 300);
      p_326229_.define(STAGE, 0);
   }

   protected void readAdditionalSaveData(CompoundTag compound) {
      this.setLifespan(compound.getInt("Lifespan"));
      this.setStage(compound.getInt("Stage"));
      if (compound.hasUUID("Owner")) {
         this.ownerUUID = compound.getUUID("Owner");
      }
   }

   public boolean shouldRenderAtSqrDistance(double p_36837_) {
      double d0 = this.getBoundingBox().getSize() * 4.0;
      if (Double.isNaN(d0)) {
         d0 = 4.0;
      }

      d0 *= 64.0;
      return p_36837_ < d0 * d0;
   }

   protected void addAdditionalSaveData(CompoundTag compound) {
      compound.putInt("Lifespan", this.getLifespan());
      compound.putInt("Stage", this.getStage());
      if (this.ownerUUID != null) {
         compound.putUUID("Owner", this.ownerUUID);
      }
   }
}
