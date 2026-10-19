package cn.leolezury.eternalstarlight.common.entity.projectile;

import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.data.ESDamageTypes;
import cn.leolezury.eternalstarlight.common.entity.attack.EnergizedFlame;
import cn.leolezury.eternalstarlight.common.entity.interfaces.TrailOwner;
import cn.leolezury.eternalstarlight.common.entity.living.boss.golem.Permafrost;
import cn.leolezury.eternalstarlight.common.entity.living.boss.golem.StarlightGolem;
import cn.leolezury.eternalstarlight.common.entity.living.monster.Freeze;
import cn.leolezury.eternalstarlight.common.registry.ESCriteriaTriggers;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import cn.leolezury.eternalstarlight.common.util.ESEntityUtil;
import cn.leolezury.eternalstarlight.common.util.TrailEffect;
import it.unimi.dsi.fastutil.ints.Int2IntArrayMap;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import org.joml.Vector4f;

public class FrozenTube extends ThrowableProjectile implements TrailOwner {
   public FrozenTube(EntityType<? extends FrozenTube> entityType, Level level) {
      super(entityType, level);
   }

   public FrozenTube(Level level, LivingEntity livingEntity) {
      super(ESEntities.FROZEN_TUBE.get(), livingEntity, level);
   }

   public FrozenTube(Level level, double x, double y, double z) {
      super(ESEntities.FROZEN_TUBE.get(), x, y, z, level);
   }

   protected void defineSynchedData(Builder builder) {
   }

   protected void onHit(HitResult hitResult) {
      super.onHit(hitResult);
      if (hitResult.getType() != Type.MISS) {
         this.playSound(ESSoundEvents.FROZEN_TUBE_BREAK.get(), 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
         if (this.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(
               new ItemParticleOption(ParticleTypes.ITEM, ESItems.FROZEN_TUBE.get().getDefaultInstance()),
               this.getX() + (this.random.nextFloat() - 0.5) * this.getBbWidth(),
               this.getY() + this.random.nextFloat() * this.getBbHeight(),
               this.getZ() + (this.random.nextFloat() - 0.5) * this.getBbWidth(),
               5,
               0.2,
               0.2,
               0.2,
               0.0
            );
         }

         for (LivingEntity entity : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(2.0))) {
            if (!this.level().isClientSide && ESEntityUtil.shouldHarm(this.getOwner(), entity)) {
               if (entity.canFreeze()) {
                  entity.setTicksFrozen(Math.min(entity.getTicksFrozen() + 100, 300));
               }

               if (this.getOwner() instanceof ServerPlayer serverPlayer && entity instanceof StarlightGolem golem && golem.getAttackEnergy() > 0) {
                  golem.setAttackEnergy(Math.max(golem.getAttackEnergy() - 5, 0));
                  Int2IntArrayMap cooldowns = golem.getBehaviorManager().getCooldowns();
                  cooldowns.put(4, Math.max(cooldowns.getOrDefault(4, 0) - 60, 0));
                  ((PlayerTrigger)ESCriteriaTriggers.FREEZE_STARLIGHT_GOLEM.get()).trigger(serverPlayer);
               }
            }
         }

         for (int x = -4; x <= 4; x++) {
            for (int y = -4; y <= 4; y++) {
               for (int z = -4; z <= 4; z++) {
                  BlockPos pos = this.blockPosition().offset(x, y, z);
                  if (this.level().getBlockState(pos).is(Blocks.FIRE) && this.blockPosition().distSqr(pos) <= 16.0) {
                     this.level().setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                  }

                  if (this.level().getBlockState(pos).is(Blocks.WATER)
                     && this.level().getFluidState(pos).isSource()
                     && this.blockPosition().distSqr(pos) <= 16.0) {
                     this.level().setBlockAndUpdate(pos, Blocks.ICE.defaultBlockState());
                  }

                  if (this.level().getBlockState(pos).is(Blocks.LAVA) && this.level().getFluidState(pos).isSource() && this.blockPosition().distSqr(pos) <= 4.0
                     )
                   {
                     this.level().setBlockAndUpdate(pos, Blocks.OBSIDIAN.defaultBlockState());
                  }
               }
            }
         }

         this.level().getEntitiesOfClass(EnergizedFlame.class, this.getBoundingBox().inflate(5.0)).forEach(Entity::discard);
         this.discard();
      }
   }

   protected void onHitEntity(EntityHitResult hitResult) {
      if (hitResult.getType() != Type.MISS && this.getOwner() instanceof LivingEntity owner && ESEntityUtil.shouldHarm(owner, hitResult.getEntity())) {
         hitResult.getEntity()
            .hurt(
               ESDamageTypes.getIndirectEntityDamageSource(this.level(), ESDamageTypes.FREEZE, this, owner),
               (float)(
                  switch (this.getOwner()) {
                     case Player ignored -> 6.0;
                     case Freeze ignored -> ESConfig.INSTANCE.mobsConfig.freeze.attackDamage();
                     case Permafrost permafrost -> (
                           permafrost.getAttribute(Attributes.ATTACK_DAMAGE) != null ? permafrost.getAttributeValue(Attributes.ATTACK_DAMAGE) : 12.0
                        )
                        * 0.4;
                     default -> 3.0;
                  }
               )
            );
      }
   }

   @Override
   public TrailEffect createNewTrail() {
      return new TrailEffect(0.3F, 8.0F);
   }

   @Override
   public void updateTrail(TrailEffect effect) {
      Vec3 oldPos = new Vec3(this.xOld, this.yOld, this.zOld);
      effect.update(oldPos.add(0.0, this.getBbHeight() / 2.0F, 0.0));
      if (this.isRemoved()) {
         effect.setLength(Math.max(effect.getLength() - 0.9F, 0.0F));
      }
   }

   @Override
   public Vector4f getTrailColor() {
      return new Vector4f(0.40784314F, 0.8F, 1.0F, 1.0F);
   }
}
