package com.cerbon.bosses_of_mass_destruction.item.custom;

import com.cerbon.bosses_of_mass_destruction.entity.BMDEntities;
import com.cerbon.bosses_of_mass_destruction.item.BMDItems;
import com.cerbon.bosses_of_mass_destruction.packet.custom.ChargedEnderPearlS2CPacket;
import com.cerbon.bosses_of_mass_destruction.particle.BMDParticles;
import com.cerbon.bosses_of_mass_destruction.sound.BMDSounds;
import com.cerbon.cerbons_api.api.general.particle.ClientParticleBuilder;
import com.cerbon.cerbons_api.api.network.Dispatcher;
import com.cerbon.cerbons_api.api.static_utilities.MathUtils;
import com.cerbon.cerbons_api.api.static_utilities.RandomUtils;
import com.cerbon.cerbons_api.api.static_utilities.SoundUtils;
import com.cerbon.cerbons_api.api.static_utilities.Vec3Colors;
import com.cerbon.cerbons_api.api.static_utilities.VecUtils;
import java.util.Random;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ChargedEnderPearlEntity extends ThrowableItemProjectile {
   private static final double radius = 3.0;
   private static final double impactHeight = 3.0;
   private static final ClientParticleBuilder verticalRodParticle = new ClientParticleBuilder((ParticleOptions)BMDParticles.ROD.get())
      .brightness(15728880)
      .color(age -> MathUtils.lerpVec(age, Vec3Colors.LIGHT_ENDER_PEARL, Vec3Colors.DARK_ENDER_PEARL))
      .colorVariation(0.25);
   private static final ClientParticleBuilder enderParticle = new ClientParticleBuilder((ParticleOptions)BMDParticles.FLUFF.get())
      .brightness(15728880)
      .color(Vec3Colors.ENDER_PURPLE)
      .colorVariation(0.25);

   public ChargedEnderPearlEntity(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
      super(entityType, level);
   }

   protected ChargedEnderPearlEntity(Level level, LivingEntity owner) {
      super((EntityType)BMDEntities.CHARGED_ENDER_PEARL.get(), owner, level);
   }

   @NotNull
   protected Item getDefaultItem() {
      return (Item)BMDItems.CHARGED_ENDER_PEARL.get();
   }

   protected void onHitEntity(@NotNull EntityHitResult result) {
      super.onHitEntity(result);
      result.getEntity().hurt(result.getEntity().level().damageSources().thrown(this, this.getOwner()), 0.0F);
   }

   protected void onHit(@NotNull HitResult result) {
      super.onHit(result);
      if (!this.level().isClientSide() && !this.isRemoved()) {
         this.serverCollision();
      }
   }

   private void serverCollision() {
      this.teleportEntity(this.getOwner());
      this.applyMobEffects(this.getOwner());
      Dispatcher.sendToClientsLoadingPos(new ChargedEnderPearlS2CPacket(this.position()), (ServerLevel)this.level(), this.position());
      this.playSound((SoundEvent)BMDSounds.CHARGED_ENDER_PEARL.get(), 1.0F, SoundUtils.randomPitch(this.random) * 0.8F);
      this.discard();
   }

   private void teleportEntity(Entity entity) {
      if (entity instanceof ServerPlayer player) {
         if (player.connection.isAcceptingMessages() && player.level() == this.level() && !player.isSleeping()) {
            if (player.isPassenger()) {
               player.dismountTo(this.getX(), this.getY(), this.getZ());
            } else {
               player.teleportTo(this.getX(), this.getY(), this.getZ());
            }

            player.resetFallDistance();
         }
      } else if (entity != null) {
         entity.teleportTo(this.getX(), this.getY(), this.getZ());
         entity.resetFallDistance();
      }
   }

   private void applyMobEffects(Entity entity) {
      if (entity instanceof LivingEntity livingEntity) {
         livingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 120, 1));
         livingEntity.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 20, 0));
      }

      for (LivingEntity entity1 : this.level()
         .getEntitiesOfClass(
            LivingEntity.class,
            new AABB(this.getX(), this.getY(), this.getZ(), this.getX(), this.getY(), this.getZ()).inflate(6.0, 6.0, 6.0),
            this::isInXzAndYDistance
         )) {
         Vec3 direction = this.position().subtract(entity1.position());
         entity1.knockback(0.4F, direction.x(), direction.z());
      }
   }

   private boolean isInXzAndYDistance(LivingEntity entity) {
      return this.isInXzDistance(entity) && this.isInYDistance(entity);
   }

   private boolean isInXzDistance(LivingEntity entity) {
      Vec3 xzLineToEntity = VecUtils.planeProject(entity.position(), VecUtils.yAxis).subtract(VecUtils.planeProject(this.position(), VecUtils.yAxis));
      double sqXzDistanceTowardEntity = xzLineToEntity.lengthSqr();
      boolean entityCenterInRadius = sqXzDistanceTowardEntity < 9.0;
      if (entityCenterInRadius) {
         return true;
      }

      Vec3 xzDirectionTowardEntity = xzLineToEntity.normalize();
      Vec3 xzPointTowardEntity = xzDirectionTowardEntity.scale(3.0).add(this.position());
      return entity.getBoundingBox().inflate(0.0, 10.0, 0.0).contains(xzPointTowardEntity);
   }

   private boolean isInYDistance(LivingEntity entity) {
      double yBottom = this.position().y() - 1.0;
      double yTop = this.position().y() + 3.0;
      return entity.getBoundingBox().maxY > yBottom || entity.getBoundingBox().minY < yTop;
   }

   public void tick() {
      if (this.getOwner() instanceof Player player && !player.isAlive()) {
         this.discard();
      } else {
         super.tick();
      }
   }

   @Nullable
   public Entity changeDimension(DimensionTransition transition) {
      Entity entity = this.getOwner();
      if (entity != null && entity.level().dimension() != transition.newLevel().dimension()) {
         this.setOwner(null);
      }

      return super.changeDimension(transition);
   }

   @NotNull
   public Packet<ClientGamePacketListener> getAddEntityPacket(ServerEntity entity) {
      return super.getAddEntityPacket(entity);
   }

   public static void handlePearlImpact(Vec3 pos) {
      spawnTeleportParticles(pos);
      spawnVerticalRods(pos);
   }

   private static void spawnTeleportParticles(Vec3 pos) {
      for (int i = 0; i <= 10; i++) {
         int startingRotation = new Random().nextInt(360);
         double randomRadius = RandomUtils.range(2.0, 3.0);
         double rotationSpeed = RandomUtils.range(3.0, 4.0);
         int finalI = i;
         enderParticle.continuousPosition(particle -> rotateAroundPos(pos, finalI, particle.getAge(), startingRotation, randomRadius, rotationSpeed))
            .build(rotateAroundPos(pos, i, 0, startingRotation, randomRadius, rotationSpeed), Vec3.ZERO);
      }
   }

   private static Vec3 rotateAroundPos(Vec3 pos, int i, int age, int startingRotation, double radius, double rotationSpeed) {
      Vec3 yOffset = VecUtils.yAxis.scale(i / 15.0);
      Vec3 xzOffset = VecUtils.xAxis.yRot((float)Math.toRadians(age * rotationSpeed + startingRotation));
      return pos.add(yOffset).add(xzOffset.scale(radius));
   }

   private static void spawnVerticalRods(Vec3 pos) {
      for (int y = 0; y <= 3; y++) {
         spawnVerticalRodRing(y, pos);
      }
   }

   private static void spawnVerticalRodRing(int y, Vec3 pos) {
      MathUtils.circleCallback(
         3.0,
         30 - y * 3,
         VecUtils.yAxis,
         vec3 -> verticalRodParticle.build(pos.add(vec3).add(VecUtils.yAxis.scale(y + RandomUtils.range(0.0, 1.0))), VecUtils.yAxis.scale(0.1))
      );
   }
}
