package it.hurts.sskirillss.relics.entities;

import it.hurts.octostudios.octolib.module.particle.trail.EntityTrailProvider;
import it.hurts.sskirillss.relics.entities.misc.ITargetableEntity;
import it.hurts.sskirillss.relics.init.EffectRegistry;
import it.hurts.sskirillss.relics.items.relics.base.IRelicItem;
import it.hurts.sskirillss.relics.network.NetworkHandler;
import it.hurts.sskirillss.relics.network.packets.sync.S2CEntityTargetPacket;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import it.hurts.sskirillss.relics.utils.MathUtils;
import it.hurts.sskirillss.relics.utils.ParticleUtils;
import java.awt.Color;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

public class SporeEntity extends ThrowableProjectile implements ITargetableEntity {
   private static final EntityDataAccessor<ItemStack> RELIC_STACK = SynchedEntityData.defineId(SporeEntity.class, EntityDataSerializers.ITEM_STACK);
   private static final EntityDataAccessor<Float> DAMAGE = SynchedEntityData.defineId(SporeEntity.class, EntityDataSerializers.FLOAT);
   private LivingEntity target;

   public void setRelicStack(ItemStack stack) {
      this.getEntityData().set(RELIC_STACK, stack);
   }

   public ItemStack getRelicStack() {
      return (ItemStack)this.getEntityData().get(RELIC_STACK);
   }

   public void setDamage(float damage) {
      this.getEntityData().set(DAMAGE, damage);
   }

   public float getDamage() {
      return (Float)this.getEntityData().get(DAMAGE);
   }

   public SporeEntity(EntityType<? extends ThrowableProjectile> entityType, Level level) {
      super(entityType, level);
   }

   public void tick() {
      super.tick();
      Level level = this.getCommandSenderWorld();
      Vec3 particleCenter = this.position().add(this.getDeltaMovement().scale(-1.0));
      if (this.tickCount > 3) {
         for (int i = 0; i < 3; i++) {
            level.addParticle(
               ParticleUtils.constructSimpleSpark(
                  new Color(50 + this.random.nextInt(100), 150 + this.random.nextInt(100), 0),
                  0.01F + this.random.nextFloat() * Math.min(this.tickCount * 0.01F, 0.1F),
                  5 + this.random.nextInt(3),
                  0.9F
               ),
               particleCenter.x() + MathUtils.randomFloat(this.random) * 0.05F,
               particleCenter.y() + MathUtils.randomFloat(this.random) * 0.05F,
               particleCenter.z() + MathUtils.randomFloat(this.random) * 0.05F,
               0.0,
               0.0,
               0.0
            );
         }
      }

      if (this.tickCount >= 250) {
         if (!level.isClientSide()) {
            this.discard();
         }
      } else if (this.target != null && !this.target.isDeadOrDying()) {
         Vec3 direction = this.target.position().subtract(this.position()).normalize();
         Vec3 motion = this.getDeltaMovement();
         float factor = Math.clamp(this.tickCount * 0.05F, 0.0F, 1.0F);
         this.setDeltaMovement(motion.x + (direction.x * factor - motion.x) * factor, motion.y, motion.z + (direction.z * factor - motion.z) * factor);
      } else if (!level.isClientSide()) {
         List<LivingEntity> targets = this.locateNearestTargets();
         if (!targets.isEmpty()) {
            this.setTarget(targets.get(this.random.nextInt(targets.size())));
            NetworkHandler.sendToClientsTrackingEntity(new S2CEntityTargetPacket(this.getId(), this.target.getId()), this);
         }
      }
   }

   public List<LivingEntity> locateNearestTargets() {
      return EntityUtils.gatherPotentialTargets(this, LivingEntity.class, 32.0)
         .filter(entity -> !(this.getOwner() instanceof Player player && EntityUtils.isAlliedTo(player, entity)))
         .toList();
   }

   public void onRemovedFromLevel() {
      super.onRemovedFromLevel();
      Level level = this.getCommandSenderWorld();
      Vec3 vec = this.position();
      level.playSound(null, this.blockPosition(), SoundEvents.PUFFER_FISH_BLOW_UP, SoundSource.MASTER, 0.5F, 1.5F + this.random.nextFloat() * 0.5F);

      for (int i = 0; i < 3; i++) {
         ParticleUtils.createBall(
            ParticleUtils.constructSimpleSpark(
               new Color(50 + this.random.nextInt(100), 150 + this.random.nextInt(100), 0),
               0.1F + this.random.nextFloat() * 0.2F,
               20 + this.random.nextInt(10),
               0.95F
            ),
            vec,
            level,
            1,
            0.025F + this.random.nextFloat() * 0.15F
         );
      }
   }

   protected void onHit(HitResult result) {
      super.onHit(result);
      if (this.getOwner() instanceof Player player
         && result instanceof EntityHitResult entityResult
         && entityResult.getEntity() instanceof LivingEntity entity
         && !entity.getStringUUID().equals(player.getStringUUID())) {
         entity.invulnerableTime = 0;
         ItemStack stack = this.getRelicStack();
         if (entity.hurt(this.getCommandSenderWorld().damageSources().thrown(this, player), this.getDamage())) {
            if (stack.getItem() instanceof IRelicItem relic) {
               relic.spreadRelicExperience(player, stack, 1);
            }

            if (this.isOnFire()) {
               entity.igniteForTicks(this.getRemainingFireTicks());
            }

            entity.addEffect(new MobEffectInstance(EffectRegistry.ANTI_HEAL, 0, 100, false, false), player);
         }
      }

      this.discard();
   }

   protected void defineSynchedData(Builder builder) {
      builder.define(RELIC_STACK, ItemStack.EMPTY);
      builder.define(DAMAGE, 1.0F);
   }

   public void addAdditionalSaveData(CompoundTag tag) {
      super.addAdditionalSaveData(tag);
      tag.put("relic_stack", this.getRelicStack().save(this.registryAccess()));
      tag.putFloat("damage", this.getDamage());
   }

   public void readAdditionalSaveData(CompoundTag tag) {
      super.readAdditionalSaveData(tag);
      this.setRelicStack(ItemStack.parseOptional(this.registryAccess(), tag.getCompound("relic_stack")));
      this.setDamage(tag.getFloat("damage"));
   }

   public boolean isPushedByFluid() {
      return false;
   }

   @Nullable
   @Override
   public LivingEntity getTarget() {
      return this.target;
   }

   @Override
   public void setTarget(LivingEntity target) {
      this.target = target;
   }

   @OnlyIn(Dist.CLIENT)
   public static class TrailProvider extends EntityTrailProvider<SporeEntity> {
      public TrailProvider(SporeEntity entity) {
         super(entity);
      }

      public Vec3 getTrailPosition(float partialTicks) {
         return ((SporeEntity)this.entity).getPosition(partialTicks).add(((SporeEntity)this.entity).getDeltaMovement().scale(-1.0));
      }

      public int getTrailUpdateFrequency() {
         return 1;
      }

      public boolean isTrailAlive() {
         return ((SporeEntity)this.entity).isAlive();
      }

      public boolean isTrailGrowing() {
         return ((SporeEntity)this.entity).tickCount > 2;
      }

      public int getTrailMaxLength() {
         return 3;
      }

      public int getTrailFadeInColor() {
         return -11141376;
      }

      public int getTrailFadeOutColor() {
         return -2139029760;
      }

      public double getTrailScale() {
         return 0.075F;
      }
   }
}
