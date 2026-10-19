package fuzs.illagerinvasion.world.entity.monster;

import fuzs.illagerinvasion.init.ModEntityTypes;
import fuzs.illagerinvasion.init.ModSoundEvents;
import java.util.UUID;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class InvokerFangs extends Entity {
   private int warmup;
   private boolean startedAttack;
   private int ticksLeft = 22;
   private boolean playingAnimation;
   @Nullable
   private LivingEntity owner;
   @Nullable
   private UUID ownerUuid;

   public InvokerFangs(EntityType<? extends InvokerFangs> entityType, Level world) {
      super(entityType, world);
   }

   public InvokerFangs(Level world, double x, double y, double z, float yaw, int warmup, LivingEntity owner) {
      this((EntityType<? extends InvokerFangs>)ModEntityTypes.INVOKER_FANGS_ENTITY_TYPE.value(), world);
      this.warmup = warmup;
      this.setOwner(owner);
      this.setYRot(yaw * (180.0F / (float)Math.PI));
      this.setPos(x, y, z);
   }

   protected void defineSynchedData(Builder builder) {
   }

   @Nullable
   public LivingEntity getOwner() {
      Entity entity;
      if (this.owner == null
         && this.ownerUuid != null
         && this.level() instanceof ServerLevel
         && (entity = ((ServerLevel)this.level()).getEntity(this.ownerUuid)) instanceof LivingEntity) {
         this.owner = (LivingEntity)entity;
      }

      return this.owner;
   }

   public void setOwner(@Nullable LivingEntity owner) {
      this.owner = owner;
      this.ownerUuid = owner == null ? null : owner.getUUID();
   }

   protected void readAdditionalSaveData(CompoundTag nbt) {
      this.warmup = nbt.getInt("Warmup");
      if (nbt.hasUUID("Owner")) {
         this.ownerUuid = nbt.getUUID("Owner");
      }
   }

   protected void addAdditionalSaveData(CompoundTag nbt) {
      nbt.putInt("Warmup", this.warmup);
      if (this.ownerUuid != null) {
         nbt.putUUID("Owner", this.ownerUuid);
      }
   }

   public void tick() {
      super.tick();
      if (this.level().isClientSide) {
         if (this.playingAnimation) {
            this.ticksLeft--;
            if (this.ticksLeft == 14) {
               for (int i = 0; i < 12; i++) {
                  double d = this.getX() + (this.random.nextDouble() * 2.0 - 1.0) * this.getBbWidth() * 0.5;
                  double e = this.getY() + 0.05 + this.random.nextDouble();
                  double f = this.getZ() + (this.random.nextDouble() * 2.0 - 1.0) * this.getBbWidth() * 0.5;
                  double g = (this.random.nextDouble() * 2.0 - 1.0) * 0.3;
                  double h = 0.3 + this.random.nextDouble() * 0.3;
                  double j = (this.random.nextDouble() * 2.0 - 1.0) * 0.3;
                  this.level().addParticle(ParticleTypes.CRIT, d, e + 1.0, f, g, h, j);
               }
            }
         }
      } else if (--this.warmup < 0) {
         if (this.warmup == -8) {
            for (LivingEntity livingEntity : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(0.2, 0.0, 0.2))) {
               this.damage(livingEntity);
            }
         }

         if (!this.startedAttack) {
            this.level().broadcastEntityEvent(this, (byte)4);
            this.startedAttack = true;
         }

         if (--this.ticksLeft < 0) {
            this.discard();
         }
      }
   }

   private void damage(LivingEntity target) {
      LivingEntity livingEntity = this.getOwner();
      if (target.isAlive() && !target.isInvulnerable() && target != livingEntity) {
         if (livingEntity == null) {
            target.hurt(this.damageSources().magic(), 10.0F);
            target.push(0.0, 1.7F, 0.0);
         } else {
            if (livingEntity.isAlliedTo(target)) {
               return;
            }

            target.hurt(this.damageSources().indirectMagic(this, livingEntity), 10.0F);
            this.knockBack(target);
         }
      }
   }

   public void handleEntityEvent(byte status) {
      super.handleEntityEvent(status);
      if (status == 4) {
         this.playingAnimation = true;
         if (!this.isSilent()) {
            this.level()
               .playLocalSound(
                  this.getX(),
                  this.getY(),
                  this.getZ(),
                  (SoundEvent)ModSoundEvents.INVOKER_FANGS_SOUND_EVENT.value(),
                  this.getSoundSource(),
                  1.0F,
                  this.random.nextFloat() * 0.2F + 0.85F,
                  false
               );
         }
      }
   }

   private void knockBack(LivingEntity entity) {
      entity.push(0.0, 0.6, 0.0);
   }

   public float getAnimationProgress(float tickDelta) {
      if (!this.playingAnimation) {
         return 0.0F;
      }

      int i = this.ticksLeft - 2;
      return i <= 0 ? 1.0F : 1.0F - (i - tickDelta) / 20.0F;
   }
}
