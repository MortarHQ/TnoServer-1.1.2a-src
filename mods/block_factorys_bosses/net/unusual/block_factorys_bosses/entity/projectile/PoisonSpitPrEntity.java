package net.unusual.block_factorys_bosses.entity.projectile;

import javax.annotation.Nullable;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.entity.PartEntity;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;
import net.unusual.block_factorys_bosses.init.BossesRiseEntities;
import net.unusual.block_factorys_bosses.init.BossesRiseItems;
import net.unusual.block_factorys_bosses.init.BossesRiseParticleTypes;

@EventBusSubscriber
public class PoisonSpitPrEntity extends AbstractArrow {
   private int knockback = 0;

   public PoisonSpitPrEntity(EntityType<? extends PoisonSpitPrEntity> type, Level world) {
      super(type, world);
   }

   public PoisonSpitPrEntity(EntityType<? extends PoisonSpitPrEntity> type, double x, double y, double z, Level world, @Nullable ItemStack firedFromWeapon) {
      super(type, x, y, z, world, new ItemStack((ItemLike)BossesRiseItems.PLACEHOLDER.get()), firedFromWeapon);
   }

   public PoisonSpitPrEntity(EntityType<? extends PoisonSpitPrEntity> type, LivingEntity entity, Level world, @Nullable ItemStack firedFromWeapon) {
      super(type, entity, world, new ItemStack((ItemLike)BossesRiseItems.PLACEHOLDER.get()), firedFromWeapon);
   }

   protected ItemStack getDefaultPickupItem() {
      return new ItemStack((ItemLike)BossesRiseItems.PLACEHOLDER.get());
   }

   protected void doPostHurtEffects(LivingEntity entity) {
      super.doPostHurtEffects(entity);
      entity.setArrowCount(entity.getArrowCount() - 1);
   }

   public void setKnockback(int knockback) {
      this.knockback = knockback;
   }

   protected void doKnockback(LivingEntity livingEntity, DamageSource damageSource) {
      if (this.knockback > 0.0) {
         double d1 = Math.max(0.0, 1.0 - livingEntity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
         Vec3 vec3 = this.getDeltaMovement().multiply(1.0, 0.0, 1.0).normalize().scale(this.knockback * 0.6 * d1);
         if (vec3.lengthSqr() > 0.0) {
            livingEntity.push(vec3.x, 0.1, vec3.z);
         }
      }
   }

   private Vec3 findGround(Vec3 atPosition) {
      BlockHitResult hitResult = this.level()
         .clip(new ClipContext(atPosition, atPosition.add(0.0, -20.0, 0.0), Block.COLLIDER, Fluid.ANY, CollisionContext.empty()));
      return hitResult.getType() == Type.MISS ? atPosition : hitResult.getLocation();
   }

   public void onHitEntity(EntityHitResult entityHitResult) {
      super.onHitEntity(entityHitResult);
      this.spawnPoisonAreaEntity(entityHitResult.getLocation());
   }

   public void onHitBlock(BlockHitResult blockHitResult) {
      super.onHitBlock(blockHitResult);
      this.spawnPoisonAreaEntity(blockHitResult.getLocation());
   }

   private void spawnPoisonAreaEntity(Vec3 position) {
      if (this.level() instanceof ServerLevel level) {
         position = this.findGround(position).add(0.0, 0.001F + this.getRandom().nextFloat() * 0.005F, 0.0);
         Entity entityToSpawn = ((EntityType)BossesRiseEntities.POISON_AREA.get()).create(level);
         if (entityToSpawn != null) {
            entityToSpawn.moveTo(position, this.getRandom().nextFloat() * 360.0F, 0.0F);
            level.addFreshEntity(entityToSpawn);
         }

         this.discard();
      }
   }

   public void tick() {
      super.tick();
      if (this.level().isClientSide()) {
         RandomSource random = this.getRandom();

         for (int count = 0; count < 2; count++) {
            this.level()
               .addParticle(
                  (ParticleOptions)BossesRiseParticleTypes.POISON_SPIT.get(),
                  this.getX() + Mth.nextDouble(random, -0.1, 0.1),
                  this.getY() + Mth.nextDouble(random, -0.1, 0.1),
                  this.getZ() + Mth.nextDouble(random, -0.1, 0.1),
                  random.nextGaussian() * 0.1,
                  random.nextGaussian() * 0.1,
                  random.nextGaussian() * 0.1
               );
         }

         this.level()
            .addParticle(
               ParticleTypes.ITEM_SLIME,
               this.getX() + Mth.nextDouble(random, -0.1, 0.1),
               this.getY() + Mth.nextDouble(random, -0.1, 0.1),
               this.getZ() + Mth.nextDouble(random, -0.1, 0.1),
               random.nextGaussian() * 0.1,
               random.nextGaussian() * 0.1,
               random.nextGaussian() * 0.1
            );
      }

      if (this.inGround) {
         this.discard();
      }
   }

   @SubscribeEvent
   public static void onProjectileImpactEvent(ProjectileImpactEvent event) {
      if (event.getProjectile() instanceof PoisonSpitPrEntity poisonSpitEntity && event.getRayTraceResult() instanceof EntityHitResult entityHitResult) {
         Entity hitEntity = entityHitResult.getEntity();
         if (hitEntity instanceof PartEntity<?> partEntity) {
            hitEntity = partEntity.getParent();
         }

         if (hitEntity == poisonSpitEntity.getOwner()) {
            event.setCanceled(true);
         }
      }
   }

   public static PoisonSpitPrEntity shoot(Level world, LivingEntity entity, RandomSource source) {
      return shoot(world, entity, source, 1.0F, 5.0, 5);
   }

   public static PoisonSpitPrEntity shoot(Level world, LivingEntity entity, RandomSource source, float pullingPower) {
      return shoot(world, entity, source, pullingPower * 1.0F, 5.0, 5);
   }

   public static PoisonSpitPrEntity shoot(Level world, LivingEntity entity, RandomSource random, float power, double damage, int knockback) {
      PoisonSpitPrEntity entityarrow = new PoisonSpitPrEntity(
         (EntityType<? extends PoisonSpitPrEntity>)BossesRiseEntities.POISON_SPIT_PR.get(), entity, world, null
      );
      entityarrow.shoot(entity.getViewVector(1.0F).x, entity.getViewVector(1.0F).y, entity.getViewVector(1.0F).z, power * 2.0F, 0.0F);
      entityarrow.setSilent(true);
      entityarrow.setCritArrow(false);
      entityarrow.setBaseDamage(damage);
      entityarrow.setKnockback(knockback);
      world.addFreshEntity(entityarrow);
      world.playSound(
         null,
         entity.getX(),
         entity.getY(),
         entity.getZ(),
         SoundEvents.BLAZE_SHOOT,
         SoundSource.PLAYERS,
         1.0F,
         1.0F / (random.nextFloat() * 0.5F + 1.0F) + power / 2.0F
      );
      return entityarrow;
   }

   public static PoisonSpitPrEntity shoot(LivingEntity entity, LivingEntity target) {
      PoisonSpitPrEntity entityarrow = new PoisonSpitPrEntity(
         (EntityType<? extends PoisonSpitPrEntity>)BossesRiseEntities.POISON_SPIT_PR.get(), entity, entity.level(), null
      );
      double dx = target.getX() - entity.getX();
      double dy = target.getY() + target.getEyeHeight() - 1.1;
      double dz = target.getZ() - entity.getZ();
      entityarrow.shoot(dx, dy - entityarrow.getY() + Math.hypot(dx, dz) * 0.2F, dz, 2.0F, 12.0F);
      entityarrow.setSilent(true);
      entityarrow.setBaseDamage(5.0);
      entityarrow.setKnockback(5);
      entityarrow.setCritArrow(false);
      entity.level().addFreshEntity(entityarrow);
      entity.level()
         .playSound(
            null,
            entity.getX(),
            entity.getY(),
            entity.getZ(),
            SoundEvents.BLAZE_SHOOT,
            SoundSource.PLAYERS,
            1.0F,
            1.0F / (RandomSource.create().nextFloat() * 0.5F + 1.0F)
         );
      return entityarrow;
   }
}
