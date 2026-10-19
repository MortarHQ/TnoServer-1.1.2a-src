package dev.foxboss.entity;

import dev.foxboss.FoxBossConfig;
import dev.foxboss.registry.FoxEntities;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public final class DragonStarEntity extends Entity {
   private static final EntityDataAccessor<CompoundTag> PATH = SynchedEntityData.defineId(DragonStarEntity.class, EntityDataSerializers.COMPOUND_TAG);
   private static final EntityDataAccessor<Integer> TARGET = SynchedEntityData.defineId(DragonStarEntity.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Boolean> IMPACT = SynchedEntityData.defineId(DragonStarEntity.class, EntityDataSerializers.BOOLEAN);
   private Vec3 origin = Vec3.ZERO;
   private Vec3 control = Vec3.ZERO;
   private Vec3 destination = Vec3.ZERO;
   private long born;
   private int duration = 20;
   private LivingEntity owner;
   private boolean decorative;
   private boolean hit;
   private float damage;

   public DragonStarEntity(EntityType<? extends DragonStarEntity> type, Level level) {
      super(type, level);
      this.noPhysics = true;
      this.setNoGravity(true);
   }

   public static void launch(ServerLevel level, LivingEntity owner, LivingEntity target, Vec3 from, Vec3 to, int seed, boolean decorative) {
      DragonStarEntity star = (DragonStarEntity)((EntityType)FoxEntities.DRAGON_STAR.get()).create(level);
      if (star != null) {
         star.owner = owner;
         star.decorative = decorative;
         star.damage = ((Double)FoxBossConfig.STAR_DAMAGE.get()).floatValue();
         if (owner instanceof MilimEntity milim) {
            star.damage = MilimSatanaelBonuses.dragonStarDamage(milim, star.damage);
         }

         star.setPos(from);
         Vec3 direction = to.subtract(from);
         Vec3 side = new Vec3(-direction.z, 0.0, direction.x).normalize();
         double spread = Math.sin(seed * 2.39996323) * Math.min(10.0, direction.length() * 0.38);
         Vec3 bend = from.lerp(to, 0.45).add(side.scale(spread)).add(0.0, 2.8 + seed % 5 * 1.0, 0.0);
         CompoundTag path = new CompoundTag();
         putVec(path, "from", from);
         putVec(path, "control", bend);
         putVec(path, "to", to);
         path.putLong("born", level.getGameTime());
         path.putInt("duration", Mth.clamp((int)(direction.length() / 1.7) + 9, 12, 38));
         star.entityData.set(PATH, path);
         star.entityData.set(TARGET, target == null ? -1 : target.getId());
         level.addFreshEntity(star);
      }
   }

   protected void defineSynchedData(Builder builder) {
      builder.define(PATH, new CompoundTag());
      builder.define(TARGET, -1);
      builder.define(IMPACT, false);
   }

   public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
      super.onSyncedDataUpdated(key);
      if (PATH.equals(key)) {
         CompoundTag path = (CompoundTag)this.entityData.get(PATH);
         this.origin = getVec(path, "from");
         this.control = getVec(path, "control");
         this.destination = getVec(path, "to");
         this.born = path.getLong("born");
         this.duration = Math.max(1, path.getInt("duration"));
      }
   }

   public float age(float partial) {
      return (float)(this.level().getGameTime() - this.born) + partial;
   }

   public int travelDuration() {
      return this.duration;
   }

   public Vec3 origin() {
      return this.origin;
   }

   public Vec3 endpoint(float partial) {
      return !this.entityData.get(IMPACT) && this.level().getEntity((Integer)this.entityData.get(TARGET)) instanceof LivingEntity living && living.isAlive()
         ? living.getPosition(partial).add(0.0, living.getBbHeight() * 0.5, 0.0)
         : this.destination;
   }

   public Vec3 point(float progress, float partial) {
      double t = Mth.clamp(progress, 0.0F, 1.0F);
      double inv = 1.0 - t;
      return this.origin.scale(inv * inv).add(this.control.scale(2.0 * inv * t)).add(this.endpoint(partial).scale(t * t));
   }

   public void tick() {
      super.tick();
      if (!this.level().isClientSide) {
         int age = (int)this.age(0.0F);
         if (!this.hit && age >= this.duration) {
            this.hit = true;
            Entity target = this.level().getEntity((Integer)this.entityData.get(TARGET));
            Vec3 impact = this.endpoint(1.0F);
            CompoundTag path = ((CompoundTag)this.entityData.get(PATH)).copy();
            putVec(path, "to", impact);
            this.entityData.set(PATH, path);
            this.entityData.set(IMPACT, true);
            if (!this.decorative
               && target instanceof LivingEntity living
               && living.isAlive()
               && (this.owner == null || !this.owner.isAlliedTo(living))
               && !living.isSpectator()
               && !(living instanceof Player p && p.getAbilities().invulnerable)) {
               boolean damaged = living.hurt(this.damageSources().indirectMagic(this, this.owner), this.damage);
               if (damaged && this.owner instanceof MilimEntity milim) {
                  milim.recordSatanaelContact(living);
               }

               ServerLevel server = (ServerLevel)this.level();
               server.sendParticles(ParticleTypes.EXPLOSION_EMITTER, impact.x, impact.y, impact.z, 1, 0.0, 0.0, 0.0, 0.0);
               server.playSound(null, impact.x, impact.y, impact.z, (SoundEvent)SoundEvents.GENERIC_EXPLODE.value(), SoundSource.HOSTILE, 1.0F, 1.0F);
            }
         }

         if (age >= this.duration + 10 || age < 0) {
            this.discard();
         }
      }
   }

   private static void putVec(CompoundTag tag, String prefix, Vec3 value) {
      tag.putDouble(prefix + "X", value.x);
      tag.putDouble(prefix + "Y", value.y);
      tag.putDouble(prefix + "Z", value.z);
   }

   private static Vec3 getVec(CompoundTag tag, String prefix) {
      return new Vec3(tag.getDouble(prefix + "X"), tag.getDouble(prefix + "Y"), tag.getDouble(prefix + "Z"));
   }

   protected void readAdditionalSaveData(CompoundTag tag) {
   }

   protected void addAdditionalSaveData(CompoundTag tag) {
   }
}
