package net.jadenxgamer.netherexp.core.entity;

import java.awt.Color;
import net.jadenxgamer.netherexp.core.keys.JNEDamageSources;
import net.jadenxgamer.netherexp.registry.JNEItems;
import net.jadenxgamer.netherexp.registry.JNEParticleTypes;
import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import team.lodestar.lodestone.systems.particle.SimpleParticleOptions.ParticleSpritePicker;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;
import team.lodestar.lodestone.systems.particle.data.spin.SpinParticleData;
import team.lodestar.lodestone.systems.particle.render_types.LodestoneWorldParticleRenderType;
import team.lodestar.lodestone.systems.rendering.trail.TrailPointBuilder;

public abstract class AbstractPellet extends AbstractArrow {
   public final TrailPointBuilder trailPointBuilder = TrailPointBuilder.create(4);
   private boolean projectileBoost = false;

   protected AbstractPellet(EntityType<? extends AbstractArrow> entityType, Level level) {
      super(entityType, level);
      this.pickup = Pickup.DISALLOWED;
      this.setPickupItemStack(new ItemStack((ItemLike)JNEItems.BLANK_SHOTGUN_SHELL.get()));
   }

   public void tick() {
      super.tick();
      if (!this.level().isClientSide && (this.tickCount > this.getMaxLife() || this.isInWater())) {
         this.level().broadcastEntityEvent(this, (byte)40);
         this.hitGroundSound();
         this.discard();
      }

      if (this.level().isClientSide) {
         this.trailPointBuilder.addTrailPoint(this.position().add(0.0, 0.15, 0.0));
      }

      this.trailPointBuilder.tickTrailPoints();
   }

   protected void onHitEntity(EntityHitResult result) {
      Entity target = result.getEntity();
      if (this.getOwner() != null) {
         target.hurt(this.damageSources().source(this.getDamageSource(), this.getOwner()), this.getDamage());
      } else {
         target.hurt(this.damageSources().source(this.getDamageSource()), this.getDamage());
      }

      if (!this.level().isClientSide) {
         this.level().broadcastEntityEvent(this, (byte)40);
         this.hitGroundSound();
         this.discard();
      }
   }

   protected void onHitBlock(BlockHitResult result) {
      if (!this.level().isClientSide) {
         this.level().broadcastEntityEvent(this, (byte)40);
         this.hitGroundSound();
         this.discard();
      }
   }

   public float getPickRadius() {
      return 0.0F;
   }

   private void triggerExplosion() {
      this.level().explode(this.getOwner(), this.getX(), this.getY(), this.getZ(), 2.0F, ExplosionInteraction.NONE);
   }

   protected void hitGroundSound() {
      this.playSound(this.getHitSound(), 0.15F, 1.0F);
   }

   @NotNull
   protected SoundEvent getDefaultHitGroundSoundEvent() {
      return this.getHitSound();
   }

   public boolean isNoGravity() {
      return true;
   }

   public void handleEntityEvent(byte id) {
      if (id == 40) {
         AbstractPellet.Client.hitParticle(this, this.level(), this.position());
      }

      super.handleEntityEvent(id);
   }

   protected SoundEvent getHitSound() {
      return JNESoundEvents.SHOTGUN_IMPACT.get();
   }

   protected float getDamage() {
      return 1.0F;
   }

   protected int getMaxLife() {
      return 40;
   }

   protected ResourceKey<DamageType> getDamageSource() {
      return JNEDamageSources.SHOTGUN_PELLET;
   }

   public Color getTrailColor() {
      return new Color(16777215);
   }

   public Color getHitColor() {
      return new Color(16777215);
   }

   @OnlyIn(Dist.CLIENT)
   public static class Client {
      private static void hitParticle(AbstractPellet pellet, Level level, Vec3 pos) {
         hitParticle(pellet, level, pos.x, pos.y, pos.z);
      }

      private static void hitParticle(AbstractPellet pellet, Level level, double x, double y, double z) {
         WorldParticleBuilder.create(JNEParticleTypes.PELLET_HIT.get())
            .setFullBrightLighting()
            .setSpinData(SpinParticleData.create(0.0F).build())
            .setScaleData(GenericParticleData.create(0.195F).build())
            .setColorData(ColorParticleData.create(pellet.getHitColor()).build())
            .setTransparencyData(GenericParticleData.create(1.0F).build())
            .setRenderType(LodestoneWorldParticleRenderType.TRANSPARENT)
            .setSpritePicker(ParticleSpritePicker.WITH_AGE)
            .setLifetime(Mth.randomBetweenInclusive(level.random, 3, 7))
            .enableNoClip()
            .spawn(level, x, y, z);
      }
   }
}
