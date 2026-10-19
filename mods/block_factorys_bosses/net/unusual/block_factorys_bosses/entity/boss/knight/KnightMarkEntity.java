package net.unusual.block_factorys_bosses.entity.boss.knight;

import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.unusual.block_factorys_bosses.client.particle.util.SubParticleEmitter;
import net.unusual.block_factorys_bosses.entity.IParticleAttachment;
import net.unusual.block_factorys_bosses.entity.OwnableByAllEntity;
import net.unusual.block_factorys_bosses.init.BossesRiseParticleTypes;
import net.unusual.block_factorys_bosses.init.BossesRiseSounds;
import org.joml.Vector3f;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class KnightMarkEntity extends Entity implements OwnableByAllEntity, IParticleAttachment {
   protected static final EntityDataAccessor<Optional<UUID>> DATA_OWNER_UUID = SynchedEntityData.defineId(
      KnightMarkEntity.class, EntityDataSerializers.OPTIONAL_UUID
   );
   protected static final EntityDataAccessor<Vector3f> DATA_OFFSET = SynchedEntityData.defineId(KnightMarkEntity.class, EntityDataSerializers.VECTOR3);
   protected static final EntityDataAccessor<Float> DATA_SCALE = SynchedEntityData.defineId(KnightMarkEntity.class, EntityDataSerializers.FLOAT);
   protected boolean hasParticle = false;

   public KnightMarkEntity(EntityType<?> entityType, Level level) {
      super(entityType, level);
   }

   protected void defineSynchedData(Builder builder) {
      builder.define(DATA_OWNER_UUID, Optional.empty());
      builder.define(DATA_OFFSET, new Vector3f());
      builder.define(DATA_SCALE, 1.0F);
   }

   public Vector3f getOffset() {
      return (Vector3f)this.getEntityData().get(DATA_OFFSET);
   }

   public void setOffset(Vector3f offset) {
      this.entityData.set(DATA_OFFSET, offset);
   }

   public float getScale() {
      return (Float)this.getEntityData().get(DATA_SCALE);
   }

   public void setScale(float scale) {
      this.entityData.set(DATA_SCALE, scale);
   }

   @Nullable
   public UUID getOwnerUUID() {
      return (UUID)((Optional)this.entityData.get(DATA_OWNER_UUID)).orElse(null);
   }

   public void setOwnerUUID(@Nullable UUID uuid) {
      this.entityData.set(DATA_OWNER_UUID, Optional.ofNullable(uuid));
   }

   @OnlyIn(Dist.CLIENT)
   public void tick() {
      super.tick();
      if (!this.hasParticle && this.level().isClientSide) {
         SubParticleEmitter.attachNewParticle(this, (ParticleOptions)BossesRiseParticleTypes.MARK_GLINT.get(), null, this.getScale(), 0.0F, 0.0F);
         SubParticleEmitter.attachNewParticle(this, (ParticleOptions)BossesRiseParticleTypes.MARK_GLINT_2.get(), null, this.getScale(), 0.0F, 0.0F);
         SubParticleEmitter.attachNewParticle(this, (ParticleOptions)BossesRiseParticleTypes.MARK_GLINT_3.get(), null, this.getScale(), 0.0F, 0.0F);
         SubParticleEmitter.attachNewParticle(this, (ParticleOptions)BossesRiseParticleTypes.MARK_GLINT_EXP.get(), null);
         SubParticleEmitter.attachNewParticle(this, (ParticleOptions)BossesRiseParticleTypes.MARK_GLINT_EXP_2.get(), null);
         this.hasParticle = true;
      }
   }

   public void baseTick() {
      super.baseTick();
      if (this.level() instanceof ServerLevel) {
         UUID id = this.getOwnerUUID();
         if (id == null || this.tickCount > (int)(100.0F * this.getScale())) {
            this.discard();
         }
      }
   }

   public boolean hurt(DamageSource source, float amount) {
      if (this.level() instanceof ServerLevel level) {
         UUID id = this.getOwnerUUID();
         if (id == null) {
            this.discard();
            return true;
         }

         if (level.getEntity(id) instanceof LivingEntity living) {
            amount *= 2.0F;
            if (living instanceof UnderworldKnightEntity knight) {
               if (amount > 4.0F) {
                  int stacks = knight.getImmuneStacks();
                  if (stacks > 0) {
                     knight.removeOneImmuneStack();
                  }

                  knight.setState("knocked_down");
               }

               knight.processHurt(source, amount, true);
            } else {
               living.hurt(source, amount);
            }

            double x = this.getX();
            double y = this.getY() + this.getBbHeight() * 0.5;
            double z = this.getZ();
            this.level().playSound(null, x, y, z, (SoundEvent)BossesRiseSounds.KNIGHT_STACK_REMOVE.value(), SoundSource.HOSTILE, 6.0F, 2.0F);
            this.level().playSound(null, x, y, z, (SoundEvent)BossesRiseSounds.KNIGHT_HURT.value(), SoundSource.HOSTILE, 6.0F, 2.0F);
            level.sendParticles((SimpleParticleType)BossesRiseParticleTypes.MARK_GLINT_EXP.get(), x, y, z, 1, 0.0, 0.0, 0.0, 1.0);
            level.sendParticles((SimpleParticleType)BossesRiseParticleTypes.MARK_GLINT_EXP_2.get(), x, y, z, 1, 0.0, 0.0, 0.0, 1.0);
            this.discard();
            return true;
         } else {
            return super.hurt(source, amount);
         }
      } else {
         return true;
      }
   }

   public boolean isPickable() {
      return true;
   }

   protected void readAdditionalSaveData(CompoundTag tag) {
      if (tag.hasUUID("owner")) {
         this.setOwnerUUID(tag.getUUID("owner"));
      }

      if (tag.contains("offset_x")) {
         this.setOffset(new Vector3f(tag.getFloat("offset_x"), tag.getFloat("offset_y"), tag.getFloat("offset_z")));
      }

      this.tickCount = tag.getInt("tick_count");
      this.setScale(tag.getFloat("scale"));
   }

   protected void addAdditionalSaveData(CompoundTag tag) {
      if (this.getOwnerUUID() != null) {
         tag.putUUID("Owner", this.getOwnerUUID());
      }

      tag.putFloat("offset_x", this.getOffset().x);
      tag.putFloat("offset_y", this.getOffset().y);
      tag.putFloat("offset_z", this.getOffset().z);
      tag.putFloat("scale", this.getScale());
      tag.putInt("tick_count", this.tickCount);
   }
}
