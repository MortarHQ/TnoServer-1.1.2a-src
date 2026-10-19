package com.cerbon.bosses_of_mass_destruction.projectile;

import com.cerbon.bosses_of_mass_destruction.entity.BMDEntities;
import com.cerbon.bosses_of_mass_destruction.entity.custom.obsidilith.RiftBurst;
import com.cerbon.bosses_of_mass_destruction.particle.BMDParticles;
import com.cerbon.bosses_of_mass_destruction.sound.BMDSounds;
import com.cerbon.bosses_of_mass_destruction.util.BMDUtils;
import com.cerbon.cerbons_api.api.general.event.EventScheduler;
import com.cerbon.cerbons_api.api.general.event.TimedEvent;
import com.cerbon.cerbons_api.api.general.particle.ClientParticleBuilder;
import com.cerbon.cerbons_api.api.static_utilities.CapabilityUtils;
import com.cerbon.cerbons_api.api.static_utilities.MathUtils;
import com.cerbon.cerbons_api.api.static_utilities.SoundUtils;
import com.cerbon.cerbons_api.api.static_utilities.Vec3Colors;
import com.cerbon.cerbons_api.api.static_utilities.VecUtils;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.DirectionalPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

public class SporeBallProjectile extends BaseThrownItemProjectile implements GeoEntity {
   private final AnimatableInstanceCache animationFactory = GeckoLibUtil.createInstanceCache(this);
   private final List<Vec3> circlePoints = MathUtils.buildBlockCircle(7.0);
   private final ClientParticleBuilder projectileParticles = new ClientParticleBuilder((ParticleOptions)BMDParticles.DISAPPEARING_SWIRL.get())
      .color(Vec3Colors.GREEN)
      .colorVariation(0.4)
      .scale(0.2F)
      .brightness(15728880);
   private final byte particle = 5;
   private float impactedPitch = 0.0F;
   public float impactedTicks = 0.0F;
   public boolean impacted = false;
   public static final int explosionDelay = 30;

   public SporeBallProjectile(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
      super(entityType, level);
      this.collisionPredicate = hitResult -> true;
   }

   public SporeBallProjectile(LivingEntity livingEntity, Level level, Predicate<EntityHitResult> entityPredicate) {
      super((EntityType<? extends ThrowableItemProjectile>)BMDEntities.SPORE_BALL.get(), livingEntity, level, entityPredicate);
      this.collisionPredicate = hitResult -> true;
   }

   protected void onHitBlock(@NotNull BlockHitResult result) {
      this.onImpact();
   }

   @Override
   public void clientTick() {
      super.clientTick();
      if (this.impacted) {
         this.impactedTicks++;
      }
   }

   @NotNull
   public Vec3 getDeltaMovement() {
      return !this.impacted ? super.getDeltaMovement() : Vec3.ZERO;
   }

   private void onImpact() {
      if (!this.impacted) {
         this.impactedPitch = this.getXRot();
         this.impacted = true;
         if (this.getOwner() instanceof LivingEntity livingEntity) {
            this.doExplosion(livingEntity);
         } else if (!this.level().isClientSide()) {
            this.discard();
         }
      }
   }

   public float getXRot() {
      return this.impacted ? this.impactedPitch : this.tickCount * 5.0F;
   }

   public float getYRot() {
      return 0.0F;
   }

   private void doExplosion(LivingEntity owner) {
      this.level().broadcastEntityEvent(this, (byte)5);
      this.playSound((SoundEvent)BMDSounds.SPORE_BALL_LAND.get(), 1.0F, SoundUtils.randomPitch(this.random) - 0.2F);
      EventScheduler eventScheduler = CapabilityUtils.getLevelEventScheduler(this.level());
      Consumer<LivingEntity> onImpact = entity -> {
         float damage = (float)owner.getAttributeValue(Attributes.ATTACK_DAMAGE);
         if (this.getOwner() != null) {
            entity.hurt(BMDUtils.shieldPiercing(this.level(), this.getOwner()), damage);
            entity.addEffect(new MobEffectInstance(MobEffects.POISON, 140), this.getOwner());
         }
      };
      if (!this.level().isClientSide) {
         RiftBurst riftBurst = new RiftBurst(
            owner,
            (ServerLevel)this.level(),
            (ParticleOptions)BMDParticles.SPORE_INDICATOR.get(),
            (ParticleOptions)BMDParticles.SPORE.get(),
            30,
            eventScheduler,
            onImpact,
            this::isOpenBlock,
            this::posFinder
         );
         eventScheduler.addEvent(new TimedEvent(() -> {
            this.playSound((SoundEvent)BMDSounds.SPORE_IMPACT.get(), 1.5F, SoundUtils.randomPitch(this.random));
            this.discard();
         }, 30));
         Vec3 center = VecUtils.asVec3(this.blockPosition()).add(VecUtils.unit.scale(0.5));

         for (Vec3 point : this.circlePoints) {
            riftBurst.tryPlaceRift(center.add(point));
         }
      }
   }

   private BlockPos posFinder(Vec3 pos) {
      BlockPos above = BlockPos.containing(pos.add(VecUtils.yAxis.scale(2.0)));
      BlockPos groundPos = BMDUtils.findGroundBelow(this.level(), above, pos1 -> true);
      BlockPos up = groundPos.above();
      return up.getY() + 8 >= above.getY() && this.isOpenBlock(up) ? up : null;
   }

   private boolean isOpenBlock(BlockPos up) {
      BlockState blockState = this.level().getBlockState(up);
      return blockState.canBeReplaced(new DirectionalPlaceContext(this.level(), up, Direction.DOWN, ItemStack.EMPTY, Direction.UP))
         || blockState.getBlock() == Blocks.MOSS_CARPET;
   }

   public void handleEntityEvent(byte id) {
      if (id == 5) {
         for (Vec3 point : MathUtils.circlePoints(0.8, 16, VecUtils.yAxis)) {
            this.projectileParticles.build(point.add(this.position()), point.scale(0.1));
         }
      }

      super.handleEntityEvent(id);
   }

   @Override
   public void entityHit(EntityHitResult entityHitResult) {
      if (!this.level().isClientSide()) {
         Entity owner = this.getOwner();
         Entity entity = entityHitResult.getEntity();
         if (owner instanceof LivingEntity livingEntity && entity != livingEntity) {
            float damage = (float)livingEntity.getAttributeValue(Attributes.ATTACK_DAMAGE);
            entity.hurt(this.level().damageSources().thrown(this, livingEntity), damage);
         }
      }
   }

   public void registerControllers(ControllerRegistrar controllerRegistrar) {
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.animationFactory;
   }
}
