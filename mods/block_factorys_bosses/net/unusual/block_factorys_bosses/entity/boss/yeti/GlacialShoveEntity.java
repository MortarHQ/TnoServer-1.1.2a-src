package net.unusual.block_factorys_bosses.entity.boss.yeti;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.unusual.block_factorys_bosses.attachment.entity.RollAttachment;
import net.unusual.block_factorys_bosses.entity.OwnableByAllEntity;
import net.unusual.block_factorys_bosses.geckolib.DangerZonesProvider;
import net.unusual.block_factorys_bosses.init.BossesRiseSounds;
import net.unusual.block_factorys_bosses.util.SpatialUtil;
import org.joml.Vector3f;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class GlacialShoveEntity extends Entity implements GeoEntity, OwnableByAllEntity, DangerZonesProvider {
   protected static final EntityDataAccessor<Optional<UUID>> DATA_OWNER_UUID = SynchedEntityData.defineId(
      GlacialShoveEntity.class, EntityDataSerializers.OPTIONAL_UUID
   );
   public static final EntityDataAccessor<Integer> DATA_TARGET = SynchedEntityData.defineId(GlacialShoveEntity.class, EntityDataSerializers.INT);
   public static final EntityDataAccessor<Integer> DATA_DELAY = SynchedEntityData.defineId(GlacialShoveEntity.class, EntityDataSerializers.INT);
   public static final int HIT_TICK = 31;
   private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);
   private final DangerZonesProvider.DangerZone zone;
   public boolean shouldSetAttack = true;

   public GlacialShoveEntity(EntityType<?> entityType, Level level) {
      super(entityType, level);
      this.zone = new DangerZonesProvider.DangerZone();
      this.zone.setSize(3.0F, 3.0F);
      this.zone.setOffset(new Vector3f(0.0F, 0.0F, 0.0F));
      this.zone.setColor(16711680);
   }

   protected void defineSynchedData(Builder builder) {
      builder.define(DATA_OWNER_UUID, Optional.empty());
      builder.define(DATA_TARGET, -1);
      builder.define(DATA_DELAY, 31);
   }

   public void setTarget(LivingEntity target) {
      this.entityData.set(DATA_TARGET, target.getId());
   }

   public void setDelay(int delay) {
      this.entityData.set(DATA_DELAY, delay);
   }

   public int getDelay() {
      return (Integer)this.entityData.get(DATA_DELAY);
   }

   @Nullable
   public LivingEntity getTarget() {
      int target = (Integer)this.entityData.get(DATA_TARGET);
      return target != -1 && this.level().getEntity(target) instanceof LivingEntity living ? living : null;
   }

   public Vec3 position() {
      if (this.tickCount < this.getDelay()) {
         LivingEntity target = this.getTarget();
         if (target != null) {
            Vec3 pos = target.position();
            BlockHitResult clip = this.level().clip(new ClipContext(pos, pos.subtract(0.0, 10.0, 0.0), Block.COLLIDER, Fluid.NONE, CollisionContext.empty()));
            return clip.getLocation();
         }
      }

      return super.position();
   }

   public void tick() {
      super.tick();
      LivingEntity target = this.getTarget();
      if (this.tickCount < this.getDelay() && target != null) {
         Vec3 pos = target.position();
         BlockHitResult clip = this.level().clip(new ClipContext(pos, pos.subtract(0.0, 10.0, 0.0), Block.COLLIDER, Fluid.NONE, CollisionContext.empty()));
         this.setPos(clip.getLocation());
      } else if (this.tickCount == this.getDelay()) {
         this.playSound((SoundEvent)BossesRiseSounds.YETI_RANGED_CONTROL_ICE_BLOCK.value(), 1.0F, 0.4F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
         LivingEntity owner = this.getOwner();
         if (owner instanceof YetiEntity yeti) {
            boolean worked = false;

            for (Entity entity : this.level().getEntities(this, this.getBoundingBox())) {
               if (entity instanceof ServerPlayer player) {
                  if (!RollAttachment.fromPlayer(player).isInvulnerable()) {
                     Vec3 diff = owner.position().subtract(player.position()).scale(0.05F).add(0.0, 0.5, 0.0).scale(3.0);
                     SpatialUtil.pushEntity(player, diff);
                     player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 2));
                     worked = true;
                  }
               } else if (entity instanceof LivingEntity living) {
                  Vec3 diff = owner.position().subtract(living.position()).scale(0.05F).add(0.0, 0.5, 0.0).scale(3.0);
                  living.push(diff);
                  living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 2));
                  worked = true;
               }
            }

            if (!this.level().isClientSide && this.shouldSetAttack) {
               if (worked) {
                  yeti.attackBuffer = YetiEntity.YetiState.PUNCH.attackOrDefault();
               } else {
                  yeti.doAttack(YetiEntity.YetiState.LEAP_SMASH.attackOrDefault());
               }
            }
         }
      } else if (this.tickCount > 180) {
         this.discard();
      }
   }

   protected void readAdditionalSaveData(CompoundTag tag) {
      if (tag.hasUUID("owner")) {
         this.setOwnerUUID(tag.getUUID("owner"));
      }

      this.tickCount = tag.getInt("tick_count");
   }

   protected void addAdditionalSaveData(CompoundTag tag) {
      if (this.getOwnerUUID() != null) {
         tag.putUUID("Owner", this.getOwnerUUID());
      }

      tag.putInt("tick_count", this.tickCount);
   }

   public void registerControllers(ControllerRegistrar controllers) {
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.geoCache;
   }

   @Nullable
   public UUID getOwnerUUID() {
      return (UUID)((Optional)this.entityData.get(DATA_OWNER_UUID)).orElse(null);
   }

   public void setOwnerUUID(@Nullable UUID uuid) {
      this.entityData.set(DATA_OWNER_UUID, Optional.ofNullable(uuid));
   }

   public boolean ignoreExplosion(Explosion explosion) {
      return true;
   }

   public boolean fireImmune() {
      return true;
   }

   public boolean canCollideWith(Entity entity) {
      return this.tickCount > this.getDelay();
   }

   public boolean canBeCollidedWith() {
      return this.tickCount > this.getDelay();
   }

   @Override
   public Collection<DangerZonesProvider.DangerZone> getDangerZones() {
      return this.tickCount < this.getDelay() ? List.of(this.zone) : List.of();
   }
}
