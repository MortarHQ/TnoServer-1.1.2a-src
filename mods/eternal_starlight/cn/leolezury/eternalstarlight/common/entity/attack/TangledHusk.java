package cn.leolezury.eternalstarlight.common.entity.attack;

import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.entity.projectile.LunarSpore;
import cn.leolezury.eternalstarlight.common.network.ParticlePacket;
import cn.leolezury.eternalstarlight.common.particle.ESExplosionParticleOptions;
import cn.leolezury.eternalstarlight.common.particle.ESSmokeParticleOptions;
import cn.leolezury.eternalstarlight.common.particle.RingExplosionParticleOptions;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import cn.leolezury.eternalstarlight.common.util.ESMathUtil;
import cn.leolezury.eternalstarlight.common.vfx.ScreenShakeVfx;
import java.util.List;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class TangledHusk extends LivingEntity implements TraceableEntity {
   private static final String TAG_OWNER = "owner";
   private static final String TAG_SPAWNED_TICKS = "spawned_ticks";
   public Player cachedPlayerDisplay;
   @Nullable
   private LivingEntity owner;
   @Nullable
   private UUID ownerId;
   protected static final EntityDataAccessor<Integer> OWNER_ID = SynchedEntityData.defineId(TangledHusk.class, EntityDataSerializers.INT);
   protected static final EntityDataAccessor<Integer> SPAWNED_TICKS = SynchedEntityData.defineId(TangledHusk.class, EntityDataSerializers.INT);

   public TangledHusk(EntityType<? extends TangledHusk> type, Level level) {
      super(type, level);
   }

   public LivingEntity getOwner() {
      return this.owner;
   }

   public void setOwner(LivingEntity owner) {
      this.ownerId = owner.getUUID();
      this.owner = owner;
   }

   public int getOwnerId() {
      return (Integer)this.getEntityData().get(OWNER_ID);
   }

   public void setOwnerId(int ownerId) {
      this.getEntityData().set(OWNER_ID, ownerId);
   }

   public int getSpawnedTicks() {
      return (Integer)this.getEntityData().get(SPAWNED_TICKS);
   }

   public void setSpawnedTicks(int spawnedTicks) {
      this.getEntityData().set(SPAWNED_TICKS, spawnedTicks);
   }

   public static Builder createAttributes() {
      return createLivingAttributes().add(Attributes.STEP_HEIGHT, 0.0);
   }

   public void readAdditionalSaveData(CompoundTag compoundTag) {
      super.readAdditionalSaveData(compoundTag);
      if (compoundTag.hasUUID("owner")) {
         this.ownerId = compoundTag.getUUID("owner");
      }

      this.setSpawnedTicks(compoundTag.getInt("spawned_ticks"));
   }

   public void addAdditionalSaveData(CompoundTag compoundTag) {
      super.addAdditionalSaveData(compoundTag);
      if (this.owner != null) {
         compoundTag.putUUID("owner", this.owner.getUUID());
      }

      compoundTag.putInt("spawned_ticks", this.getSpawnedTicks());
   }

   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
      builder.define(OWNER_ID, -1).define(SPAWNED_TICKS, 0);
   }

   public boolean hurt(DamageSource source, float amount) {
      return source.is(DamageTypeTags.BYPASSES_INVULNERABILITY) ? super.hurt(source, amount) : false;
   }

   public boolean isOnFire() {
      return false;
   }

   public void tick() {
      super.tick();
      if (!this.level().isClientSide && this.level() instanceof ServerLevel serverLevel) {
         if (this.owner == null && this.ownerId != null) {
            if (serverLevel.getEntity(this.ownerId) instanceof LivingEntity livingEntity) {
               this.owner = livingEntity;
            }

            if (this.owner == null) {
               this.ownerId = null;
            }
         }

         if (this.owner == null) {
            this.discard();
         } else {
            this.setOwnerId(this.owner.getId());
         }

         for (Mob mob : this.level().getEntitiesOfClass(Mob.class, this.getBoundingBox().inflate(10.0))) {
            if (mob.getTarget() == this.getOwner()) {
               mob.setTarget(this);
               mob.setLastHurtByMob(this);
               mob.getBrain().setMemory(MemoryModuleType.ATTACK_TARGET, this);
            }
         }

         this.setSpawnedTicks(this.getSpawnedTicks() + 1);
         if (this.getSpawnedTicks() > 100) {
            serverLevel.sendParticles(
               ESExplosionParticleOptions.LUNAR,
               this.getX(),
               this.getY() + this.getBbHeight() / 2.0F,
               this.getZ(),
               20,
               this.getBbWidth() / 2.0F,
               this.getBbHeight() / 2.0F,
               this.getBbWidth() / 2.0F,
               0.0
            );
            ESPlatform.INSTANCE
               .sendToAllClients(serverLevel, new ParticlePacket(RingExplosionParticleOptions.LUNAR, this.getX(), this.getY(), this.getZ(), 0.0, 0.2, 0.0));
            ScreenShakeVfx.createInstance(this.level().dimension(), this.position(), 40.0F, 50, 0.3F, 0.3F, 3.0F, 5.5F).send(serverLevel);
            this.playSound((SoundEvent)SoundEvents.GENERIC_EXPLODE.value());

            for (LivingEntity living : this.level()
               .getNearbyEntities(LivingEntity.class, TargetingConditions.DEFAULT, this, this.getBoundingBox().inflate(2.0))) {
               if (ESEntityUtil.shouldHarm(this.getOwner(), living)
                  && living.hurt(ESDamageTypes.getEntityDamageSource(this.level(), ESDamageTypes.POISON, this.getOwner()), 10.0F)) {
                  living.hurtMarked = true;
                  living.addDeltaMovement(living.position().subtract(this.position()).normalize().scale(1.25));
               }
            }

            for (int i = 0; i < 15; i++) {
               Vec3 shootPos = this.position().add(0.0, this.getBbHeight() / 2.0F, 0.0);
               LunarSpore spore = new LunarSpore(this.level(), this.getOwner(), shootPos.x, shootPos.y, shootPos.z);
               spore.setDeltaMovement(
                  ESMathUtil.rotationToPosition(0.9F, Mth.wrapDegrees(this.random.nextFloat() * 360.0F), Mth.wrapDegrees(this.random.nextFloat() * 360.0F))
               );
               this.level().addFreshEntity(spore);
            }

            this.discard();
         }
      } else if (this.random.nextInt(5) == 0) {
         this.level().addParticle(ESSmokeParticleOptions.LUNAR_SHORT, this.getRandomX(1.5), this.getRandomY(), this.getRandomZ(1.5), 0.0, 0.0, 0.0);
      }
   }

   public Iterable<ItemStack> getArmorSlots() {
      return List.of();
   }

   public ItemStack getItemBySlot(EquipmentSlot equipmentSlot) {
      return ItemStack.EMPTY;
   }

   public void setItemSlot(EquipmentSlot equipmentSlot, ItemStack itemStack) {
   }

   public HumanoidArm getMainArm() {
      return HumanoidArm.RIGHT;
   }
}
