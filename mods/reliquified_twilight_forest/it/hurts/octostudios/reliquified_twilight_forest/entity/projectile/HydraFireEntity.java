package it.hurts.octostudios.reliquified_twilight_forest.entity.projectile;

import it.hurts.octostudios.octolib.module.particle.trail.EntityTrailProvider;
import it.hurts.octostudios.reliquified_twilight_forest.entity.HydraFirePuddleEntity;
import it.hurts.octostudios.reliquified_twilight_forest.init.EntityRegistry;
import it.hurts.octostudios.reliquified_twilight_forest.item.relic.HydraHeartItem;
import it.hurts.sskirillss.relics.utils.MathUtils;
import it.hurts.sskirillss.relics.utils.ParticleUtils;
import java.awt.Color;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class HydraFireEntity extends ThrowableProjectile {
   private static final EntityDataAccessor<ItemStack> RELIC_STACK = SynchedEntityData.defineId(HydraFireEntity.class, EntityDataSerializers.ITEM_STACK);
   private int age = 200;

   public void setRelicStack(ItemStack stack) {
      this.getEntityData().set(RELIC_STACK, stack);
   }

   public ItemStack getRelicStack() {
      return (ItemStack)this.getEntityData().get(RELIC_STACK);
   }

   public HydraFireEntity(EntityType<? extends ThrowableProjectile> entityType, Level level) {
      super(entityType, level);
   }

   public void tick() {
      super.tick();
      Level level = this.getCommandSenderWorld();
      Vec3 particleCenter = this.position().add(this.getDeltaMovement().scale(-1.0));
      if (!level.isClientSide) {
         if (this.getAge() <= 0) {
            this.discard();
         }

         if (this.isAlive()) {
            int i = this.getAge();
            if (i < 0) {
               this.setAge(++i);
            } else if (i > 0) {
               this.setAge(--i);
            }
         }
      } else {
         for (int i = 0; i < 3; i++) {
            level.addParticle(
               ParticleUtils.constructSimpleSpark(
                  new Color(155 + this.random.nextInt(100), 50 + this.random.nextInt(100), 0),
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
   }

   protected double getDefaultGravity() {
      return 0.05;
   }

   public void onRemovedFromLevel() {
      super.onRemovedFromLevel();
      Level level = this.getCommandSenderWorld();
      Vec3 vec = this.position();
      level.playSound(null, this.blockPosition(), SoundEvents.PUFFER_FISH_BLOW_UP, SoundSource.MASTER, 0.5F, 1.5F + this.random.nextFloat() * 0.5F);
   }

   protected void onHit(HitResult result) {
      super.onHit(result);
      if (!this.level().isClientSide) {
         if (result instanceof BlockHitResult block) {
            this.discard();
            ItemStack stack = this.getRelicStack();
            int lifetime = 100;
            float damage = 1.0F;
            if (stack.getItem() instanceof HydraHeartItem relic && this.getOwner() instanceof Player player) {
               lifetime = (int)Math.round(relic.getStatValue(stack, "hydra_fire", "lifetime"));
               damage = (float)relic.getStatValue(stack, "hydra_fire", "damage");
               relic.spreadRelicExperience(player, stack, 1);
            }

            HydraFirePuddleEntity puddle = new HydraFirePuddleEntity((EntityType<? extends Entity>)EntityRegistry.HYDRA_FIRE_PUDDLE.get(), this.level());
            puddle.setPos(block.getLocation());
            puddle.setLifetime(lifetime);
            puddle.setDamage(damage);
            puddle.setOwner(this.getOwner());
            this.level().addFreshEntity(puddle);
         }
      }
   }

   protected void defineSynchedData(Builder builder) {
      builder.define(RELIC_STACK, ItemStack.EMPTY);
   }

   public void addAdditionalSaveData(CompoundTag tag) {
      super.addAdditionalSaveData(tag);
      tag.put("relic_stack", this.getRelicStack().save(this.registryAccess()));
      tag.putInt("Age", this.getAge());
   }

   public void readAdditionalSaveData(CompoundTag tag) {
      super.readAdditionalSaveData(tag);
      this.setRelicStack(ItemStack.parseOptional(this.registryAccess(), tag.getCompound("relic_stack")));
      this.setAge(tag.getInt("Age"));
   }

   public boolean isPushedByFluid() {
      return false;
   }

   public int getAge() {
      return this.age;
   }

   public void setAge(int age) {
      this.age = age;
   }

   @OnlyIn(Dist.CLIENT)
   public static class TrailProvider extends EntityTrailProvider<HydraFireEntity> {
      public TrailProvider(HydraFireEntity entity) {
         super(entity);
      }

      public Vec3 getTrailPosition(float partialTicks) {
         return ((HydraFireEntity)this.entity).getPosition(partialTicks);
      }

      public int getTrailUpdateFrequency() {
         return 1;
      }

      public boolean isTrailAlive() {
         return ((HydraFireEntity)this.entity).isAlive();
      }

      public boolean isTrailGrowing() {
         return ((HydraFireEntity)this.entity).tickCount > 1;
      }

      public int getTrailMaxLength() {
         return 3;
      }

      public int getTrailFadeInColor() {
         return -35072;
      }

      public int getTrailFadeOutColor() {
         return 16711680;
      }

      public double getTrailScale() {
         return 0.075F;
      }
   }
}
