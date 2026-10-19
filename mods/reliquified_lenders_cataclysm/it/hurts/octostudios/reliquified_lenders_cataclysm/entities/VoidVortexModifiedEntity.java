package it.hurts.octostudios.reliquified_lenders_cataclysm.entities;

import com.github.L_Ender.cataclysm.client.particle.Options.StormParticleOptions;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import com.github.L_Ender.cataclysm.init.ModSounds;
import it.hurts.octostudios.reliquified_lenders_cataclysm.init.EntityRegistry;
import it.hurts.octostudios.reliquified_lenders_cataclysm.network.packets.VoidVortexParticlesPacket;
import it.hurts.octostudios.reliquified_lenders_cataclysm.utils.ItemUtils;
import it.hurts.sskirillss.relics.network.NetworkHandler;
import it.hurts.sskirillss.relics.network.packets.PacketPlayerMotion;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import it.hurts.sskirillss.relics.utils.ParticleUtils;
import java.awt.Color;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

public class VoidVortexModifiedEntity extends Entity {
   protected static final EntityDataAccessor<Float> DAMAGE = SynchedEntityData.defineId(VoidVortexModifiedEntity.class, EntityDataSerializers.FLOAT);
   protected static final EntityDataAccessor<Integer> HEIGHT = SynchedEntityData.defineId(VoidVortexModifiedEntity.class, EntityDataSerializers.INT);
   protected static final EntityDataAccessor<Float> LIFESPAN = SynchedEntityData.defineId(VoidVortexModifiedEntity.class, EntityDataSerializers.FLOAT);
   protected static final EntityDataAccessor<Float> LIFESPAN_STAT = SynchedEntityData.defineId(VoidVortexModifiedEntity.class, EntityDataSerializers.FLOAT);
   private float maxCircleRadius;
   private boolean madeOpenNoise;
   private boolean madeCloseNoise;
   private UUID ownerUUID;
   private LivingEntity ownerEntity;

   public VoidVortexModifiedEntity(EntityType<?> type, Level level) {
      super(type, level);
      this.setMadeOpenNoise(false);
      this.setMadeCloseNoise(false);
   }

   public VoidVortexModifiedEntity(Level level, double x, double y, double z, float yRot, LivingEntity owner, int lifespan, int height, float damage) {
      this((EntityType<?>)EntityRegistry.VOID_VORTEX_MODIFIED.get(), level);
      this.setLifespan(lifespan);
      this.setLifespanStat(lifespan);
      this.setYRot((float)(yRot * (180.0 / Math.PI)));
      this.setOwner(owner);
      this.setPos(x, y, z);
      this.setHeight(height);
      this.setDamage(damage);
   }

   @NotNull
   public EntityDimensions getDimensions(@NotNull Pose pose) {
      this.setMaxCircleRadius(1.0F + 0.5F * this.getHeight());
      return EntityDimensions.scalable(this.getMaxCircleRadius(), this.getHeight());
   }

   public void tick() {
      super.tick();
      this.refreshDimensions();
      if (this.tickCount == 1 && this.getLifespan() == 0.0F) {
         this.setLifespan(100.0F);
      }

      if (!this.isMadeOpenNoise()) {
         this.gameEvent(GameEvent.ENTITY_PLACE);
         this.playSound(SoundEvents.END_PORTAL_SPAWN, 1.0F, 1.0F + this.random.nextFloat() * 0.2F);
         this.setMadeOpenNoise(true);
         if (this.level().isClientSide) {
            this.spawnExplodeParticles();
         }
      }

      if ((int)Math.min(this.tickCount, this.getLifespan()) >= 16 && this.level().isClientSide) {
         float r = 0.4F;
         float g = 0.1F;
         float b = 0.8F;
         float stepD = 0.25F;
         float stepWRandom = 0.11F;
         float stepH = 1.0F;
         float stepHRandom = 0.075F;

         for (int i = 0; i < this.getHeight(); i++) {
            float width = 0.75F + stepD * i + this.getRandom().nextFloat() * (0.25F + stepWRandom * i);
            float height = 0.5F + stepH * i + this.getRandom().nextFloat() * (0.45F + stepHRandom * i);
            LocalPlayer player = Minecraft.getInstance().player;
            Vec3 pos = this.position();
            if (player != null) {
               Vector3f lookVec = Minecraft.getInstance().gameRenderer.getMainCamera().getLookVector();
               Vec3 direction = new Vec3(lookVec.x(), lookVec.y(), lookVec.z());
               pos = player.getEyePosition().add(direction.scale(8.0)).add(0.0, 1.5, 0.0);
            }

            this.level().addParticle(new StormParticleOptions(r, g, b, width, height, this.getId()), pos.x, pos.y, pos.z, 0.0, 0.0, 0.0);
         }
      }

      if (this.getLifespan() > 0.0F && !this.level().isClientSide) {
         for (Entity entity : this.getEntitiesInVortex()) {
            if (!(entity instanceof LivingEntity livingEntity && livingEntity.isDeadOrDying())) {
               double scaleMax = 0.15;
               double movementScale = scaleMax - 0.5 * scaleMax * entity.distanceTo(this) / this.getHeight();
               Vec3 direction = entity.position().subtract(this.position()).normalize().scale(movementScale);
               Vec3 motion = entity.getDeltaMovement().subtract(direction);
               if (entity instanceof ServerPlayer player && !player.equals(this.getOwner())) {
                  NetworkHandler.sendToClient(new PacketPlayerMotion(motion.x, motion.y, motion.z), player);
               } else {
                  double vortexMotionScale = 0.8 - 0.025 * (this.getHeight() - 3);
                  entity.setDeltaMovement(entity instanceof LivingEntity ? motion : motion.scale(vortexMotionScale));
                  if (entity.horizontalCollision) {
                     entity.addDeltaMovement(new Vec3(0.0, 0.25, 0.0));
                  }
               }

               if (entity instanceof VoidVortexModifiedEntity vortexOther && this.getHeight() >= vortexOther.getHeight()) {
                  int entitiesNum = this.getEntitiesInVortex().size();
                  int entitiesNumOther = vortexOther.getEntitiesInVortex().size();
                  if (this.getHeight() > vortexOther.getHeight()
                     || this.getHeight() == vortexOther.getHeight()
                        && (entitiesNum > entitiesNumOther || entitiesNum == entitiesNumOther && this.getLifespan() >= vortexOther.getLifespan())) {
                     vortexOther.setLifespan(this.getLifespanStat());
                     vortexOther.move(MoverType.SELF, vortexOther.getDeltaMovement());
                     AABB vortexOtherBox = vortexOther.getBoundingBox();
                     if (vortexOtherBox.intersects(this.getBoundingBox())
                        && vortexOtherBox.getBottomCenter().distanceTo(this.getBoundingBox().getBottomCenter()) <= 1.5) {
                        this.setLifespan(this.getLifespanStat());
                        this.setHeight(this.getHeight() + vortexOther.getHeight());
                        this.setDamage(this.getDamage() + vortexOther.getDamage());
                        vortexOther.remove(RemovalReason.DISCARDED);
                        this.level().playSound(null, this.blockPosition(), SoundEvents.BEACON_ACTIVATE, SoundSource.NEUTRAL);
                        NetworkHandler.sendToClientsTrackingEntityAndSelf(new VoidVortexParticlesPacket(this.getId(), -1, 1), this);
                     }
                  }
               }

               NetworkHandler.sendToClientsTrackingEntityAndSelf(new VoidVortexParticlesPacket(this.getId(), entity.getId(), 0), entity);
            }
         }
      }

      if (this.getLifespan() <= 16.0F && !this.isMadeCloseNoise()) {
         this.gameEvent(GameEvent.ENTITY_PLACE);
         this.setMadeCloseNoise(true);
      }

      if (this.getLifespan() == 1.0F && this.level().isClientSide) {
         this.spawnExplodeParticles();
      }

      if (this.getLifespan() <= 0.0F) {
         if (!this.level().isClientSide) {
            this.damageMobs(this.getMaxCircleRadius());
         }

         this.remove(RemovalReason.DISCARDED);
      }

      if (this.getLifespan() % 10.0F == 0.0F) {
         ScreenShake_Entity.ScreenShake(this.level(), this.position(), this.getHeight(), Math.clamp(0.005F * this.getHeight(), 0.015F, 0.1F), 1, 40);
      }

      this.setLifespan(this.getLifespan() - 1.0F);
   }

   private List<Entity> getEntitiesInVortex() {
      AABB area = new AABB(
         this.getX() - this.getHeight(),
         this.getY(),
         this.getZ() - this.getHeight(),
         this.getX() + this.getHeight(),
         this.getY() + this.getHeight(),
         this.getZ() + this.getHeight()
      );
      return this.level()
         .getEntitiesOfClass(Entity.class, area)
         .stream()
         .map(
            entity -> (Entity)(entity.equals(this.getOwner())
                  || EntityUtils.isAlliedTo(entity, this.getOwner())
                  || !(entity instanceof LivingEntity) && !(entity instanceof VoidVortexModifiedEntity voidVortexEntity && !this.equals(voidVortexEntity))
               ? null
               : entity)
         )
         .filter(Objects::nonNull)
         .toList();
   }

   private void damageMobs(float radius) {
      AABB damageBox = new AABB(
         this.getX() - radius, this.getY(), this.getZ() - radius, this.getX() + radius, this.getY() + this.getHeight(), this.getZ() + radius
      );

      for (LivingEntity entity : ItemUtils.getEntitiesInArea(this.getOwner(), this.level(), damageBox)) {
         Vec3 deltaMovement = this.position().subtract(entity.position()).normalize();
         Vec3 motion = entity.getDeltaMovement().subtract(deltaMovement);
         if (entity instanceof ServerPlayer player && !player.equals(this.getOwner())) {
            NetworkHandler.sendToClient(new PacketPlayerMotion(motion.x, motion.y, motion.z), player);
         } else {
            entity.setDeltaMovement(motion);
         }

         entity.hurt(this.damageSources().magic(), this.getDamage());
      }

      this.level().playSound(null, this.blockPosition(), (SoundEvent)ModSounds.EXPLOSION.get(), SoundSource.NEUTRAL);
   }

   public void spawnExplodeParticles() {
      for (int i = 0; i < this.getHeight(); i++) {
         for (int j = 0; j < 32; j++) {
            double xMax = 0.25 * (this.randomized(2.0) - 1.0);
            double yMax = 0.05 + this.randomized(0.1);
            double zMax = 0.25 * (this.randomized(2.0) - 1.0);
            this.level()
               .addParticle(
                  this.getParticle(new Color(61, 0, 135), 0.7F),
                  this.getX() + Math.pow(-1.0, i) * this.randomized(2.0),
                  this.getY() + 0.1 * this.randomized(0.5) + i,
                  this.getZ() + Math.pow(-1.0, i) * this.randomized(2.0),
                  xMax,
                  yMax,
                  zMax
               );
         }
      }
   }

   public ParticleOptions getParticle(Color color, float diameterMin) {
      return ParticleUtils.constructSimpleSpark(color, diameterMin + this.randomized(0.2F), 20 + this.randomized(10), 0.8F + this.randomized(0.1F));
   }

   public float randomized(float value) {
      return value * this.getRandom().nextFloat();
   }

   public double randomized(double value) {
      return value * this.getRandom().nextDouble();
   }

   public int randomized(int value) {
      return value * this.getRandom().nextInt();
   }

   protected void defineSynchedData(Builder entityDataBuilder) {
      entityDataBuilder.define(DAMAGE, 20.0F);
      entityDataBuilder.define(HEIGHT, 7);
      entityDataBuilder.define(LIFESPAN, 200.0F);
      entityDataBuilder.define(LIFESPAN_STAT, 200.0F);
   }

   protected void readAdditionalSaveData(CompoundTag tag) {
      this.setDamage(tag.getFloat("Damage"));
      this.setHeight(tag.getInt("Height"));
      this.setLifespan(tag.getFloat("Lifespan"));
      this.setLifespanStat(tag.getFloat("LifespanStat"));
      if (tag.hasUUID("Owner")) {
         this.ownerUUID = tag.getUUID("Owner");
      }
   }

   protected void addAdditionalSaveData(CompoundTag tag) {
      tag.putFloat("Damage", this.getDamage());
      tag.putInt("Height", this.getHeight());
      tag.putFloat("Lifespan", this.getLifespan());
      tag.putFloat("LifespanStat", this.getLifespanStat());
      if (this.ownerUUID != null) {
         tag.putUUID("Owner", this.ownerUUID);
      }
   }

   @NotNull
   public Packet<ClientGamePacketListener> getAddEntityPacket(@NotNull ServerEntity entity) {
      return new ClientboundAddEntityPacket(this, entity);
   }

   public float getDamage() {
      return (Float)this.entityData.get(DAMAGE);
   }

   public void setDamage(float value) {
      this.entityData.set(DAMAGE, value);
   }

   public int getHeight() {
      return (Integer)this.entityData.get(HEIGHT);
   }

   public void setHeight(int value) {
      this.entityData.set(HEIGHT, value);
   }

   public float getLifespan() {
      return (Float)this.entityData.get(LIFESPAN);
   }

   public void setLifespan(float value) {
      this.entityData.set(LIFESPAN, value);
   }

   public float getLifespanStat() {
      return (Float)this.entityData.get(LIFESPAN_STAT);
   }

   public void setLifespanStat(float value) {
      this.entityData.set(LIFESPAN_STAT, value);
   }

   @Nullable
   public LivingEntity getOwner() {
      if (this.ownerEntity != null && !this.ownerEntity.isRemoved()) {
         return this.ownerEntity;
      } else if (this.ownerUUID != null && this.level() instanceof ServerLevel serverlevel) {
         this.ownerEntity = (LivingEntity)serverlevel.getEntity(this.ownerUUID);
         return this.ownerEntity;
      } else {
         return null;
      }
   }

   public void setOwner(@Nullable LivingEntity ownerEntity) {
      if (ownerEntity != null) {
         this.ownerUUID = ownerEntity.getUUID();
         this.ownerEntity = ownerEntity;
      }
   }

   public float getMaxCircleRadius() {
      return this.maxCircleRadius;
   }

   public boolean isMadeOpenNoise() {
      return this.madeOpenNoise;
   }

   public boolean isMadeCloseNoise() {
      return this.madeCloseNoise;
   }

   public UUID getOwnerUUID() {
      return this.ownerUUID;
   }

   public LivingEntity getOwnerEntity() {
      return this.ownerEntity;
   }

   public void setMaxCircleRadius(float maxCircleRadius) {
      this.maxCircleRadius = maxCircleRadius;
   }

   public void setMadeOpenNoise(boolean madeOpenNoise) {
      this.madeOpenNoise = madeOpenNoise;
   }

   public void setMadeCloseNoise(boolean madeCloseNoise) {
      this.madeCloseNoise = madeCloseNoise;
   }

   public void setOwnerUUID(UUID ownerUUID) {
      this.ownerUUID = ownerUUID;
   }

   public void setOwnerEntity(LivingEntity ownerEntity) {
      this.ownerEntity = ownerEntity;
   }
}
