package cn.leolezury.eternalstarlight.common.entity.projectile;

import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.entity.interfaces.TrailOwner;
import cn.leolezury.eternalstarlight.common.entity.living.monster.Creteor;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.particle.ESExplosionParticleOptions;
import cn.leolezury.eternalstarlight.common.particle.ExplosionShockParticleOptions;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESDataAttachments;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import cn.leolezury.eternalstarlight.common.util.ModelSnapshot;
import cn.leolezury.eternalstarlight.common.util.TrailEffect;
import cn.leolezury.eternalstarlight.common.vfx.ScreenShakeVfx;
import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector4f;

public class AethersentMeteor extends AbstractHurtingProjectile implements TrailOwner {
   private static final String TAG_SIZE = "size";
   private static final String TAG_TARGET = "target";
   private static final String TAG_TARGET_X = "target_x";
   private static final String TAG_TARGET_Y = "target_y";
   private static final String TAG_TARGET_Z = "target_z";
   private static final String TAG_NATURAL = "natural";
   protected static final EntityDataAccessor<Integer> SIZE = SynchedEntityData.defineId(AethersentMeteor.class, EntityDataSerializers.INT);
   public final List<Pair<Vec3, ModelSnapshot>> trailSnapshots = new ArrayList<>();
   public float lastTrailTick = 0.0F;
   public float oXSpin;
   public float xSpin;
   public float oYSpin;
   public float ySpin;
   @Nullable
   private LivingEntity target;
   @Nullable
   private UUID targetId;
   private Vec3 targetPos = null;
   private boolean natural = true;

   public int getSize() {
      return (Integer)this.getEntityData().get(SIZE);
   }

   public void setSize(int size) {
      this.getEntityData().set(SIZE, size);
   }

   public LivingEntity getTarget() {
      return this.target;
   }

   public void setTarget(LivingEntity target) {
      this.target = target;
      if (target != null) {
         this.targetId = target.getUUID();
      }
   }

   public void setTargetPos(Vec3 targetPos) {
      this.targetPos = targetPos;
   }

   public boolean isNatural() {
      return this.natural;
   }

   public AethersentMeteor(EntityType<? extends AethersentMeteor> type, Level level) {
      super(type, level);
      this.noCulling = true;
   }

   public AethersentMeteor(Level level, LivingEntity entity, double x, double y, double z) {
      this(ESEntities.AETHERSENT_METEOR.get(), level);
      this.xo = x;
      this.yo = y;
      this.zo = z;
      this.setPos(x, y, z);
      this.setOwner(entity);
   }

   public static void createMeteorShower(
      Level level, LivingEntity entity, LivingEntity target, double targetX, double targetY, double targetZ, double height, int cooldown
   ) {
      if (!level.isClientSide) {
         if (ESDataAttachments.METEOR_COOLDOWN.getData(entity) > 0) {
            return;
         }

         ESDataAttachments.METEOR_COOLDOWN.setData(entity, cooldown);
         level.playSound(
            null,
            entity.getX(),
            entity.getY(),
            entity.getZ(),
            ESSoundEvents.AETHERSENT_METEOR_WHOOSH.get(),
            entity.getSoundSource(),
            1.0F,
            (entity.getRandom().nextFloat() - entity.getRandom().nextFloat()) * 0.2F + 1.0F
         );

         for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
               RandomSource random = entity.getRandom();
               AethersentMeteor meteor = new AethersentMeteor(
                  level,
                  entity,
                  targetX + x + (random.nextFloat() - 0.5) * 3.0,
                  targetY + height + (random.nextFloat() - 0.5) * 5.0,
                  targetZ + z + (random.nextFloat() - 0.5) * 3.0
               );
               meteor.setSize(random.nextInt(2, 5));
               meteor.setTarget(target);
               meteor.setTargetPos(new Vec3(targetX, targetY, targetZ));
               meteor.natural = false;
               level.addFreshEntity(meteor);
               if (level instanceof ServerLevel serverLevel) {
                  serverLevel.sendParticles(ESExplosionParticleOptions.AETHERSENT, meteor.getX(), meteor.getY(), meteor.getZ(), 2, 0.2, 0.2, 0.2, 0.0);
               }
            }
         }
      }
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(SIZE, 0);
   }

   public void readAdditionalSaveData(CompoundTag compoundTag) {
      this.setSize(compoundTag.getInt("size"));
      if (compoundTag.hasUUID("target")) {
         this.targetId = compoundTag.getUUID("target");
      }

      this.targetPos = new Vec3(compoundTag.getDouble("target_x"), compoundTag.getDouble("target_y"), compoundTag.getDouble("target_z"));
      if (compoundTag.contains("natural", 1)) {
         this.natural = compoundTag.getBoolean("natural");
      }
   }

   public void addAdditionalSaveData(CompoundTag compoundTag) {
      compoundTag.putInt("size", this.getSize());
      if (this.target != null) {
         compoundTag.putUUID("target", this.target.getUUID());
      }

      if (this.targetPos != null) {
         compoundTag.putDouble("target_x", this.targetPos.x);
         compoundTag.putDouble("target_y", this.targetPos.y);
         compoundTag.putDouble("target_z", this.targetPos.z);
      }

      compoundTag.putBoolean("natural", this.natural);
   }

   public void dropAndDiscard(boolean clean) {
      if (!this.isRemoved() && !this.level().isClientSide) {
         if (this.natural && this.getSize() >= 10) {
            ItemEntity entity = this.spawnAtLocation(new ItemStack((ItemLike)ESItems.RAW_AETHERSENT.get(), this.random.nextInt(20, 30)));
            if (entity != null) {
               entity.setGlowingTag(true);
            }

            if (clean) {
               if (this.level() instanceof ServerLevel serverLevel) {
                  for (int i = 0; i < 25; i++) {
                     Vec3 speed = new Vec3(
                           (this.random.nextFloat() - this.random.nextFloat()) * 0.1F,
                           this.random.nextFloat() * 0.05F,
                           (this.random.nextFloat() - this.random.nextFloat()) * 0.1F
                        )
                        .normalize();
                     ESPlatform.INSTANCE
                        .sendToAllClients(
                           serverLevel,
                           new ParticlePacket(
                              ExplosionShockParticleOptions.AETHERSENT,
                              this.position().x + speed.x * 1.2,
                              this.position().y + speed.y * 1.2,
                              this.position().z + speed.z * 1.2,
                              speed.x,
                              speed.y,
                              speed.z
                           )
                        );
                  }
               }
            } else {
               if (ESConfig.INSTANCE.aethersentMeteorReplaceBlocks) {
                  for (int x = -3; x <= 3; x++) {
                     for (int y = -3; y <= 3; y++) {
                        for (int z = -3; z <= 3; z++) {
                           BlockPos pos = this.blockPosition().offset(x, y, z);
                           boolean canDestroy = ESPlatform.INSTANCE.postEntityDestroyBlockEvent(this.level(), pos, this);
                           if (canDestroy
                              && pos.distToCenterSqr(this.blockPosition().getCenter()) <= 3.5
                              && this.level().getBlockState(pos).is(ESTags.Blocks.AETHERSENT_METEOR_REPLACEABLES)) {
                              this.level()
                                 .setBlockAndUpdate(
                                    pos,
                                    this.random.nextBoolean()
                                       ? ESBlocks.RAW_AETHERSENT_BLOCK.get().defaultBlockState()
                                       : ESBlocks.NEBULAITE.get().defaultBlockState()
                                 );
                           }
                        }
                     }
                  }
               }

               if (ESConfig.INSTANCE.mobsConfig.creteor.canSpawn()
                  && this.random.nextFloat() < ESConfig.INSTANCE.mobsConfig.creteor.spawnChance()
                  && this.level().getEntitiesOfClass(Creteor.class, this.getBoundingBox().inflate(32.0)).isEmpty()) {
                  Creteor creteor = new Creteor(ESEntities.CRETEOR.get(), this.level());
                  creteor.setPos(this.position());
                  creteor.setPersistenceRequired();
                  this.level().addFreshEntity(creteor);
               }

               for (int i = 0; i < ((ServerLevel)this.level()).players().size(); i++) {
                  ServerPlayer serverPlayer = (ServerPlayer)((ServerLevel)this.level()).players().get(i);
                  ((ServerLevel)this.level())
                     .sendParticles(
                        serverPlayer,
                        (SimpleParticleType)ESParticles.AETHERSENT_EXPLOSION.get(),
                        true,
                        this.getX(),
                        this.getY(),
                        this.getZ(),
                        1,
                        0.0,
                        0.0,
                        0.0,
                        0.0
                     );
               }
            }
         }

         this.discard();
      }
   }

   protected void onHit(HitResult hitResult) {
      super.onHit(hitResult);
      if (hitResult.getType() != Type.MISS) {
         if (this.natural && this.level() instanceof ServerLevel serverLevel) {
            ScreenShakeVfx.createInstance(this.level().dimension(), this.position(), 45.0F, 30, 0.15F, 0.24F, 4.0F, 5.0F).send(serverLevel);
         }

         if (this.natural
            || this.getTarget() == null && this.targetPos == null
            || this.getTarget() != null && this.getY() <= this.getTarget().getY() + this.getTarget().getBbHeight()
            || this.targetPos != null && this.getY() <= this.targetPos.y + 1.0) {
            if (this.natural) {
               this.playSound((SoundEvent)SoundEvents.GENERIC_EXPLODE.value(), this.getSoundVolume(), this.getVoicePitch());
            }

            if (!this.level().isClientSide && this.level() instanceof ServerLevel serverLevel) {
               for (LivingEntity livingEntity : this.level()
                  .getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(this.getSize(), 0.0, this.getSize()))) {
                  if (ESEntityUtil.shouldHarm(this.getOwner(), livingEntity)) {
                     livingEntity.invulnerableTime = 0;
                     livingEntity.hurt(
                        ESDamageTypes.getEntityDamageSource(this.level(), ESDamageTypes.METEOR, this.getOwner()),
                        this.getSize() * 5
                           * (this.getOwner() instanceof LivingEntity ? 0.08F : 1.0F)
                           * (this.getOwner() instanceof Player ? (float)ESConfig.INSTANCE.itemsConfig.playerAethersentMeteorDamageScale : 1.0F)
                     );
                  }
               }

               serverLevel.sendParticles(
                  (ParticleOptions)(this.getSize() >= 10 ? ParticleTypes.EXPLOSION_EMITTER : ESExplosionParticleOptions.AETHERSENT),
                  this.getX(),
                  this.getY() + 0.05 * this.getSize(),
                  this.getZ(),
                  1,
                  0.0,
                  0.0,
                  0.0,
                  0.0
               );
               this.dropAndDiscard(false);
            }
         }
      }
   }

   public void tick() {
      super.tick();
      this.setDeltaMovement(0.0, this.natural ? -2.0 : -4.0, 0.0);
      if (this.tickCount % 3 == 0) {
         this.refreshDimensions();
      }

      if (!this.level().isClientSide) {
         if (this.target == null && this.targetId != null && this.level() instanceof ServerLevel serverLevel) {
            if (serverLevel.getEntity(this.targetId) instanceof LivingEntity livingEntity) {
               this.target = livingEntity;
            }

            if (this.target == null) {
               this.targetId = null;
            }
         }
      } else {
         this.oXSpin = this.xSpin;
         this.xSpin = this.xSpin + (float) Math.PI * (0.03F + this.getRandom().nextFloat() * 0.02F);
         this.oYSpin = this.ySpin;
         this.ySpin = this.ySpin + (float) Math.PI * (0.03F + this.getRandom().nextFloat() * 0.02F);
      }
   }

   public EntityDimensions getDimensions(Pose pose) {
      return super.getDimensions(pose).scale(this.getSize() / 10.0F);
   }

   @Nullable
   protected ParticleOptions getTrailParticle() {
      return null;
   }

   protected float getSoundVolume() {
      return 1.0F;
   }

   public float getVoicePitch() {
      return (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F;
   }

   public boolean isPickable() {
      return false;
   }

   public boolean isOnFire() {
      return false;
   }

   public boolean hurt(DamageSource damageSource, float amount) {
      return false;
   }

   protected boolean shouldBurn() {
      return false;
   }

   @Override
   public TrailEffect createNewTrail() {
      return new TrailEffect(Math.max(this.getSize() / 10.0F, 0.4F), 15.0F);
   }

   @Override
   public void updateTrail(TrailEffect effect) {
      Vec3 oldPos = new Vec3(this.xOld, this.yOld, this.zOld);
      effect.update(oldPos.add(0.0, this.getBbHeight() / 2.0F, 0.0));
      if (this.isRemoved()) {
         effect.setLength(Math.max(effect.getLength() - 1.2F, 0.0F));
      }
   }

   @Override
   public Vector4f getTrailColor() {
      return new Vector4f(0.5647059F, 0.36862746F, 0.65882355F, 1.0F);
   }

   @Override
   public boolean isTrailFullBright() {
      return true;
   }
}
