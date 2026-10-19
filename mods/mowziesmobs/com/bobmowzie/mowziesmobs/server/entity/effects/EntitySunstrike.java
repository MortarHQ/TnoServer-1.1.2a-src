package com.bobmowzie.mowziesmobs.server.entity.effects;

import com.bobmowzie.mowziesmobs.MMCommon;
import com.bobmowzie.mowziesmobs.client.particle.ParticleOrb;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.entity.LeaderSunstrikeImmune;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthi;
import java.util.List;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundTeleportEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.neoforged.neoforge.entity.IEntityWithComplexSpawn;
import org.jetbrains.annotations.NotNull;

public class EntitySunstrike extends Entity implements IEntityWithComplexSpawn {
   public static final int STRIKE_EXPLOSION = 35;
   private static final int STRIKE_LENGTH = 43;
   private static final int STRIKE_LINGER = 1243;
   private int prevStrikeTime;
   private int strikeTime;
   private LivingEntity caster;
   private static final EntityDataAccessor<Integer> VARIANT_LEAST = SynchedEntityData.defineId(EntitySunstrike.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> VARIANT_MOST = SynchedEntityData.defineId(EntitySunstrike.class, EntityDataSerializers.INT);

   public EntitySunstrike(EntityType<? extends EntitySunstrike> type, Level world) {
      super(type, world);
      this.noCulling = true;
   }

   public EntitySunstrike(EntityType<? extends EntitySunstrike> type, Level world, LivingEntity caster, int x, int y, int z) {
      this(type, world);
      this.caster = caster;
      this.setPos(x + 0.5F, y + 1.0625F, z + 0.5F);
   }

   protected void defineSynchedData(@NotNull Builder builder) {
      builder.define(VARIANT_LEAST, 0);
      builder.define(VARIANT_MOST, 0);
   }

   public float getStrikeTime(float delta) {
      return this.getActualStrikeTime(delta) / 43.0F;
   }

   public float getStrikeDrawTime(float delta) {
      return this.getActualStrikeTime(delta) / 35.0F;
   }

   public float getStrikeDamageTime(float delta) {
      return (this.getActualStrikeTime(delta) - 35.0F) / 8.0F;
   }

   public boolean isStrikeDrawing(float delta) {
      return this.getActualStrikeTime(delta) < 35.0F;
   }

   public boolean isLingering(float delta) {
      return this.getActualStrikeTime(delta) > 40.0F;
   }

   public boolean isStriking(float delta) {
      return this.getActualStrikeTime(delta) < 43.0F;
   }

   private float getActualStrikeTime(float delta) {
      return this.prevStrikeTime + (this.strikeTime - this.prevStrikeTime) * delta;
   }

   private void setStrikeTime(int strikeTime) {
      this.prevStrikeTime = this.strikeTime = strikeTime;
   }

   public boolean isStriking() {
      return this.isStriking(1.0F);
   }

   public long getVariant() {
      return (long)((Integer)this.getEntityData().get(VARIANT_MOST)).intValue() << 32
         | ((Integer)this.getEntityData().get(VARIANT_LEAST)).intValue() & 4294967295L;
   }

   private void setVariant(long variant) {
      this.getEntityData().set(VARIANT_MOST, (int)(variant >> 32));
      this.getEntityData().set(VARIANT_LEAST, (int)variant);
   }

   public boolean isPickable() {
      return false;
   }

   public boolean isPushable() {
      return false;
   }

   public boolean shouldRenderAtSqrDistance(double distance) {
      return distance < 1024.0;
   }

   public void tick() {
      super.tick();
      this.prevStrikeTime = this.strikeTime;
      if (this.level().isClientSide) {
         if (this.strikeTime == 0) {
            MMCommon.PROXY.playSunstrikeSound(this);
         } else if (this.strikeTime < 25) {
            float time = this.getStrikeTime(1.0F);
            int timeBonus = (int)(time * 5.0F);
            int orbCount = this.random.nextInt(4 + timeBonus) + timeBonus + 1;

            while (orbCount-- > 0) {
               float theta = this.random.nextFloat() * (float) (Math.PI * 2);
               float min = 0.2F;
               float max = 1.9F;
               float r = this.random.nextFloat() * 1.6999999F + 0.2F;
               float ox = r * Mth.cos(theta);
               float oz = r * Mth.sin(theta);
               float minY = 0.1F;
               float oy = this.random.nextFloat() * (time * 6.0F - 0.1F) + 0.1F;
               this.level()
                  .addParticle(
                     ParticleOrb.Data.create((float)this.getX(), (float)this.getZ()), this.getX() + ox, this.getY() + oy, this.getZ() + oz, 0.0, 0.0, 0.0
                  );
            }
         } else if (this.strikeTime > 35) {
            this.smolder();
         } else if (this.strikeTime == 35) {
            this.spawnExplosionParticles(10);
         }
      } else {
         this.moveDownToGround();
         if (this.strikeTime >= 1243 || !this.level().canSeeSkyFromBelowWater(this.blockPosition())) {
            this.discard();
         } else if (this.strikeTime == 35) {
            this.damageEntityLivingBaseNearby(3.0);
         }
      }

      this.strikeTime++;
   }

   public void moveDownToGround() {
      HitResult rayTrace = this.rayTrace(this);
      if (rayTrace.getType() == Type.BLOCK) {
         BlockHitResult hitResult = (BlockHitResult)rayTrace;
         if (hitResult.getDirection() == Direction.UP) {
            BlockState hitBlock = this.level().getBlockState(hitResult.getBlockPos());
            if (this.strikeTime > 43 && hitBlock != this.level().getBlockState(this.blockPosition().below())) {
               this.discard();
            }

            if (hitBlock.getBlock() instanceof SlabBlock && hitBlock.getValue(BlockStateProperties.SLAB_TYPE) == SlabType.BOTTOM) {
               this.setPos(this.getX(), hitResult.getBlockPos().getY() + 1.0625F - 0.5F, this.getZ());
            } else {
               this.setPos(this.getX(), hitResult.getBlockPos().getY() + 1.0625F, this.getZ());
            }

            if (this.level() instanceof ServerLevel) {
               ((ServerLevel)this.level()).getChunkSource().broadcast(this, new ClientboundTeleportEntityPacket(this));
            }
         }
      }
   }

   public void damageEntityLivingBaseNearby(double radius) {
      AABB region = new AABB(
         this.getX() - radius, this.getY() - 0.5, this.getZ() - radius, this.getX() + radius, this.level().getMaxBuildHeight() + 20, this.getZ() + radius
      );
      List<Entity> entities = this.level().getEntities(this, region);
      double radiusSq = radius * radius;

      for (Entity entity : entities) {
         if (!(entity instanceof ItemEntity)
            && this.getDistanceSqXZToEntity(entity) < radiusSq
            && (!(this.caster instanceof EntityUmvuthi) || !(entity instanceof LeaderSunstrikeImmune))
            && (!(this.caster instanceof Player) || entity != this.caster)) {
            float damageFire = 2.0F;
            float damageMob = 2.0F;
            if (this.caster instanceof EntityUmvuthi) {
               damageFire = (float)(damageFire * (Double)ConfigHandler.COMMON.MOBS.UMVUTHI.combatConfig.attackMultiplier.get());
               damageMob = (float)(damageMob * (Double)ConfigHandler.COMMON.MOBS.UMVUTHI.combatConfig.attackMultiplier.get());
            }

            if (this.caster instanceof Player) {
               damageFire = (float)(damageFire * (Double)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.SUNS_BLESSING.sunsBlessingAttackMultiplier.get());
               damageMob = (float)(damageMob * (Double)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.SUNS_BLESSING.sunsBlessingAttackMultiplier.get());
            }

            if (entity.hurt(this.damageSources().mobProjectile(this, this.caster), damageMob)) {
               entity.invulnerableTime = 0;
            }

            if (entity.hurt(this.damageSources().onFire(), damageFire)) {
               entity.igniteForSeconds(3.0F);
            }
         }
      }
   }

   public double getDistanceSqXZToEntity(Entity entityIn) {
      double d0 = this.getX() - entityIn.getX();
      double d2 = this.getZ() - entityIn.getZ();
      return d0 * d0 + d2 * d2;
   }

   private void smolder() {
      if (this.random.nextFloat() < 0.1F) {
         int amount = this.random.nextInt(2) + 1;

         while (amount-- > 0) {
            float theta = this.random.nextFloat() * (float) (Math.PI * 2);
            float r = this.random.nextFloat() * 0.7F;
            float x = r * Mth.cos(theta);
            float z = r * Mth.sin(theta);
            this.level().addParticle(ParticleTypes.LARGE_SMOKE, this.getX() + x, this.getY() + 0.1, this.getZ() + z, 0.0, 0.0, 0.0);
         }
      }
   }

   private void spawnExplosionParticles(int amount) {
      for (int i = 0; i < amount; i++) {
         float velocity = 0.1F;
         float yaw = i * ((float) (Math.PI * 2) / amount);
         float vy = this.random.nextFloat() * 0.08F;
         float vx = 0.1F * Mth.cos(yaw);
         float vz = 0.1F * Mth.sin(yaw);
         this.level().addParticle(ParticleTypes.FLAME, this.getX(), this.getY() + 0.1, this.getZ(), vx, vy, vz);
      }

      for (int i = 0; i < amount / 2; i++) {
         this.level().addParticle(ParticleTypes.LAVA, this.getX(), this.getY() + 0.1, this.getZ(), 0.0, 0.0, 0.0);
      }
   }

   public void onSummon() {
      this.setVariant(this.random.nextLong());
   }

   private HitResult rayTrace(EntitySunstrike entity) {
      Vec3 startPos = new Vec3(entity.getX(), entity.getY(), entity.getZ());
      Vec3 endPos = new Vec3(entity.getX(), this.level().getMinBuildHeight(), entity.getZ());
      return entity.level().clip(new ClipContext(startPos, endPos, Block.COLLIDER, Fluid.NONE, this));
   }

   public void addAdditionalSaveData(CompoundTag compound) {
      compound.putInt("strikeTime", this.strikeTime);
      compound.putLong("variant", this.getVariant());
   }

   public void readAdditionalSaveData(CompoundTag compound) {
      this.setStrikeTime(compound.getInt("strikeTime"));
      this.setVariant(compound.getLong("variant"));
   }

   public void writeSpawnData(@NotNull RegistryFriendlyByteBuf buffer) {
      buffer.writeInt(this.strikeTime);
   }

   public void readSpawnData(@NotNull RegistryFriendlyByteBuf buffer) {
      this.setStrikeTime(buffer.readInt());
   }
}
