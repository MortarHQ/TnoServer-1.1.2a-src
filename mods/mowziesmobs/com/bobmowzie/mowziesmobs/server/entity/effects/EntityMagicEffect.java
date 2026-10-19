package com.bobmowzie.mowziesmobs.server.entity.effects;

import com.bobmowzie.mowziesmobs.server.entity.ILinkedEntity;
import com.bobmowzie.mowziesmobs.server.message.MessageLinkEntities;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
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
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

public abstract class EntityMagicEffect extends Entity implements ILinkedEntity {
   private LivingEntity cachedCaster;
   protected boolean hasSyncedCaster = false;
   private static final EntityDataAccessor<Optional<UUID>> CASTER = SynchedEntityData.defineId(EntityMagicEffect.class, EntityDataSerializers.OPTIONAL_UUID);

   public EntityMagicEffect(EntityType<? extends EntityMagicEffect> type, Level worldIn) {
      super(type, worldIn);
   }

   public EntityMagicEffect(EntityType<? extends EntityMagicEffect> type, Level world, LivingEntity caster) {
      super(type, world);
      if (!world.isClientSide && caster != null) {
         this.setCasterID(caster.getUUID());
      }
   }

   public PushReaction getPistonPushReaction() {
      return PushReaction.IGNORE;
   }

   protected void defineSynchedData(@NotNull Builder builder) {
      builder.define(CASTER, Optional.empty());
   }

   public Optional<UUID> getCasterID() {
      return (Optional<UUID>)this.getEntityData().get(CASTER);
   }

   public void setCasterID(UUID id) {
      this.getEntityData().set(CASTER, Optional.of(id));
   }

   public LivingEntity getCaster() {
      if (this.cachedCaster != null && !this.cachedCaster.isRemoved()) {
         return this.cachedCaster;
      }

      if (this.getCasterID().isPresent() && this.level() instanceof ServerLevel) {
         Entity entity = ((ServerLevel)this.level()).getEntity(this.getCasterID().get());
         if (entity instanceof LivingEntity) {
            this.cachedCaster = (LivingEntity)entity;
            PacketDistributor.sendToPlayersTrackingEntityAndSelf(this, MessageLinkEntities.fromEntity(this, this.cachedCaster), new CustomPacketPayload[0]);
         }

         return this.cachedCaster;
      } else {
         return null;
      }
   }

   public boolean isPickable() {
      return false;
   }

   public void push(Entity entityIn) {
   }

   public boolean isPushedByFluid(FluidType type) {
      return false;
   }

   public void tick() {
      super.tick();
   }

   public void onAddedToLevel() {
      super.onAddedToLevel();
   }

   @Override
   public void link(Entity entity) {
      if (entity instanceof LivingEntity) {
         this.cachedCaster = (LivingEntity)entity;
      }
   }

   protected void readAdditionalSaveData(CompoundTag compound) {
      this.setCasterID(compound.getUUID("caster"));
   }

   protected void addAdditionalSaveData(CompoundTag compound) {
      if (this.getCasterID().isPresent()) {
         compound.putUUID("caster", this.getCasterID().get());
      }
   }

   public List<Entity> getEntitiesNearby(double radius) {
      return this.getEntitiesNearby(Entity.class, radius);
   }

   public <T extends Entity> List<T> getEntitiesNearby(Class<T> entityClass, double r) {
      return this.level()
         .getEntitiesOfClass(entityClass, this.getBoundingBox().inflate(r, r, r), e -> e != this && this.distanceTo(e) <= r + e.getBbWidth() / 2.0F);
   }

   public <T extends Entity> List<T> getEntitiesNearbyCube(Class<T> entityClass, double r) {
      return this.level().getEntitiesOfClass(entityClass, this.getBoundingBox().inflate(r, r, r), e -> e != this);
   }

   public boolean raytraceCheckEntity(Entity entity) {
      Vec3 from = this.position();
      int numChecks = 3;

      for (int i = 0; i < numChecks; i++) {
         float increment = entity.getBbHeight() / (numChecks + 1);
         Vec3 to = entity.position().add(0.0, increment * (i + 1), 0.0);
         BlockHitResult result = this.level().clip(new ClipContext(from, to, Block.COLLIDER, Fluid.NONE, this));
         if (result.getType() != Type.BLOCK) {
            return true;
         }
      }

      return false;
   }

   @NotNull
   public Packet<ClientGamePacketListener> getAddEntityPacket(@NotNull ServerEntity entity) {
      return new ClientboundAddEntityPacket(this, entity, this.cachedCaster == null ? 0 : this.cachedCaster.getId());
   }

   public void recreateFromPacket(ClientboundAddEntityPacket packet) {
      super.recreateFromPacket(packet);
      Entity entity = this.level().getEntity(packet.getData());
      if (entity instanceof LivingEntity) {
         this.cachedCaster = (LivingEntity)entity;
      }
   }
}
