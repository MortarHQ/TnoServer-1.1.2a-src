package it.hurts.sskirillss.relics.entities;

import it.hurts.sskirillss.relics.init.EffectRegistry;
import it.hurts.sskirillss.relics.init.EntityRegistry;
import it.hurts.sskirillss.relics.network.NetworkHandler;
import it.hurts.sskirillss.relics.network.packets.PacketPlayerMotion;
import it.hurts.sskirillss.relics.utils.MathUtils;
import it.hurts.sskirillss.relics.utils.ParticleUtils;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class DissectionEntity extends Entity {
   private static final EntityDataAccessor<Integer> LIFE_TIME = SynchedEntityData.defineId(DissectionEntity.class, EntityDataSerializers.INT);
   private boolean isMaster = false;
   private int maxLifeTime;
   public List<UUID> entities = new ArrayList<>();
   public List<UUID> blacklist = new ArrayList<>();
   public boolean locked = true;
   public UUID pair;

   public int getLifeTime() {
      return (Integer)this.getEntityData().get(LIFE_TIME);
   }

   public void setLifeTime(int amount) {
      this.getEntityData().set(LIFE_TIME, amount);
   }

   public DissectionEntity(EntityType<?> pEntityType, Level level) {
      super(pEntityType, level);
      this.noPhysics = true;
   }

   public DissectionEntity(Level level) {
      super((EntityType)EntityRegistry.DISSECTION.get(), level);
      this.noPhysics = true;
   }

   @Nullable
   public DissectionEntity getPair() {
      if (this.pair != null && !this.level().isClientSide()) {
         return ((ServerLevel)this.level()).getEntity(this.pair) instanceof DissectionEntity dissection ? dissection : null;
      } else {
         return null;
      }
   }

   public void setPair(Entity entity) {
      if (entity instanceof DissectionEntity) {
         this.pair = entity.getUUID();
      }
   }

   public void tick() {
      super.tick();
      RandomSource random = this.level().getRandom();
      this.clearFire();
      if (this.tickCount > 5) {
         for (int i = 0; i < 5; i++) {
            float step = Math.max(Math.min(this.getLifeTime() > 20 ? (this.tickCount - 5) * 0.075F : this.getLifeTime() * 0.075F, 1.0F), 0.0F);
            float mul = random.nextFloat() * 0.3F;
            Vec3 pos = this.position()
               .add(this.getLookAngle())
               .add(0.0, this.getBbHeight() / 2.0F, 0.0)
               .add(MathUtils.randomFloat(random) * step, MathUtils.randomFloat(random) * step, MathUtils.randomFloat(random) * step);
            Vec3 angle = this.getLookAngle().normalize().multiply(mul, mul, mul);
            this.level()
               .addParticle(
                  ParticleUtils.constructSimpleSpark(
                     new Color(150 + random.nextInt(100), 100, 0), 0.2F + random.nextFloat() * 0.1F, 10 + random.nextInt(20), 0.9F
                  ),
                  pos.x(),
                  pos.y(),
                  pos.z(),
                  angle.x(),
                  angle.y(),
                  angle.z()
               );
         }
      }

      if (!this.level().isClientSide()) {
         if (this.tickCount > 1200) {
            this.discard();
         } else {
            this.locked = false;
            ServerLevel serverLevel = (ServerLevel)this.level();
            DissectionEntity pair = this.getPair();
            if (pair == null) {
               if (!this.isMaster() || this.getMaxLifeTime() == 0 || this.getLifeTime() != this.getMaxLifeTime()) {
                  this.discard();
               }
            } else {
               if (this.isMaster()) {
                  int time = this.getLifeTime();
                  if (time > 0) {
                     this.setLifeTime(--time);
                     pair.setLifeTime(time);
                  } else {
                     this.discard();
                  }
               }

               this.lookAt(Anchor.FEET, pair.position());
               Vec3 currentVec = this.position().add(this.getLookAngle()).add(0.0, 1.25, 0.0);
               Vec3 nextVec = pair.position().add(pair.getLookAngle()).add(0.0, 1.25, 0.0);
               int distance = (int)Math.round(currentVec.distanceTo(nextVec));
               Vec3 finalVec = currentVec.add(nextVec.subtract(currentVec).normalize().multiply(distance, distance, distance));
               distance = (int)Math.round(currentVec.distanceTo(finalVec));

               for (int j = 0; j < distance; j++) {
                  float x = (float)((finalVec.x - currentVec.x) * j / distance + currentVec.x);
                  float y = (float)((finalVec.y - currentVec.y) * j / distance + currentVec.y);
                  float z = (float)((finalVec.z - currentVec.z) * j / distance + currentVec.z);
                  BlockPos.betweenClosedStream(new AABB(new BlockPos((int)x, (int)y, (int)z)))
                     .forEach(
                        pos -> {
                           if (serverLevel.getBlockState(pos).blocksMotion()) {
                              this.locked = true;
                              serverLevel.sendParticles(
                                 ParticleUtils.constructSimpleSpark(new Color(255, random.nextInt(50), 0), 0.1F, 10, 0.9F),
                                 pos.getX() + 0.5F,
                                 pos.getY() + 0.5F,
                                 pos.getZ() + 0.5F,
                                 1,
                                 0.3F,
                                 0.3F,
                                 0.3F,
                                 0.025F
                              );
                           }
                        }
                     );
               }

               if (this.locked) {
                  this.entities.clear();
               } else {
                  List<UUID> toRemoveFromEntities = new ArrayList<>();

                  for (UUID uuid : this.entities) {
                     Entity target = serverLevel.getEntity(uuid);
                     if (!(target instanceof LivingEntity)) {
                        toRemoveFromEntities.add(uuid);
                     } else if (!this.blacklist.contains(uuid)) {
                        if (target.position().distanceTo(this.position()) > this.position().distanceTo(pair.position())) {
                           toRemoveFromEntities.add(uuid);
                        } else if (!serverLevel.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox()).contains(target)) {
                           double mul = this.position().distanceTo(pair.position()) * 0.05F;
                           Vec3 motion = this.position()
                              .add(0.0, this.getBbHeight() / 2.0F, 0.0)
                              .subtract(target.position())
                              .normalize()
                              .multiply(mul, mul, mul);
                           if (target instanceof ServerPlayer player) {
                              NetworkHandler.sendToClient(new PacketPlayerMotion(motion.x(), motion.y(), motion.z()), player);
                           } else {
                              target.setDeltaMovement(motion);
                           }

                           target.fallDistance = 0.0F;
                           ((LivingEntity)target).addEffect(new MobEffectInstance(EffectRegistry.VANISHING, 5, 0, false, false));
                           serverLevel.sendParticles(
                              ParticleUtils.constructSimpleSpark(new Color(150 + random.nextInt(100), 100, 0), 0.2F, 20, 0.9F),
                              target.getX(),
                              target.getY() + 1.25,
                              target.getZ(),
                              Math.round(target.getBbHeight() * 3.0F),
                              0.1F,
                              0.1F,
                              0.1F,
                              0.05F
                           );
                        } else {
                           this.blacklist.add(target.getUUID());
                           toRemoveFromEntities.add(target.getUUID());
                        }
                     }
                  }

                  if (!toRemoveFromEntities.isEmpty()) {
                     this.entities.removeAll(toRemoveFromEntities);
                  }

                  List<UUID> toRemoveFromBlacklist = new ArrayList<>();

                  for (UUID uuid : this.blacklist) {
                     if (!serverLevel.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox()).stream().map(Entity::getUUID).toList().contains(uuid)) {
                        toRemoveFromBlacklist.add(uuid);
                     }
                  }

                  if (!toRemoveFromBlacklist.isEmpty()) {
                     this.blacklist.removeAll(toRemoveFromBlacklist);
                  }

                  for (LivingEntity target : serverLevel.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox())) {
                     UUID uuid = target.getUUID();
                     if (!this.blacklist.contains(uuid) && !this.getPair().entities.contains(uuid)) {
                        this.getPair().entities.add(uuid);
                     }
                  }
               }
            }
         }
      }
   }

   public void onRemovedFromLevel() {
      super.onRemovedFromLevel();
      if (!this.isMaster()) {
         DissectionEntity pair = this.getPair();
         if (pair != null) {
            pair.setLifeTime(Math.min(pair.getLifeTime(), 20));
         }
      }
   }

   protected void defineSynchedData(Builder builder) {
      builder.define(LIFE_TIME, 100);
   }

   protected void readAdditionalSaveData(CompoundTag compound) {
      this.setMaxLifeTime(compound.getInt("maxLifeTime"));
      this.setMaster(compound.getBoolean("isMaster"));
      this.setLifeTime(compound.getInt("lifeTime"));
   }

   protected void addAdditionalSaveData(CompoundTag compound) {
      compound.putInt("maxLifeTime", this.getMaxLifeTime());
      compound.putBoolean("isMaster", this.isMaster());
      compound.putInt("lifeTime", this.getLifeTime());
   }

   public boolean isPushedByFluid() {
      return false;
   }

   public boolean isMaster() {
      return this.isMaster;
   }

   public void setMaster(boolean isMaster) {
      this.isMaster = isMaster;
   }

   public int getMaxLifeTime() {
      return this.maxLifeTime;
   }

   public void setMaxLifeTime(int maxLifeTime) {
      this.maxLifeTime = maxLifeTime;
   }
}
