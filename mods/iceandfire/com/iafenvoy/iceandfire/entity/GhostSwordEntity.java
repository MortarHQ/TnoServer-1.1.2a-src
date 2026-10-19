package com.iafenvoy.iceandfire.entity;

import com.google.common.collect.Lists;
import com.iafenvoy.iceandfire.registry.IafItems;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import java.util.List;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

public class GhostSwordEntity extends AbstractArrow {
   private IntOpenHashSet piercedEntities;
   private List<Entity> hitEntities;
   private int knockbackStrength;

   public GhostSwordEntity(EntityType<? extends GhostSwordEntity> type, Level worldIn) {
      super(type, worldIn);
      this.setBaseDamage(9.0);
      this.pickup = Pickup.DISALLOWED;
   }

   public GhostSwordEntity(EntityType<? extends GhostSwordEntity> type, Level worldIn, double x, double y, double z, float r, float g, float b) {
      this(type, worldIn);
      this.setPos(x, y, z);
      this.setBaseDamage(9.0);
   }

   public GhostSwordEntity(EntityType<? extends GhostSwordEntity> type, Level worldIn, LivingEntity shooter, double dmg, ItemStack from) {
      super(type, shooter, worldIn, new ItemStack((ItemLike)IafItems.GHOST_SWORD.get()), from);
      this.setBaseDamage(dmg);
      this.pickup = Pickup.DISALLOWED;
   }

   public boolean isInWater() {
      return false;
   }

   public void tick() {
      super.tick();
      this.noPhysics = true;
      float sqrt = Mth.sqrt((float)(this.getDeltaMovement().x * this.getDeltaMovement().x + this.getDeltaMovement().z * this.getDeltaMovement().z));
      if (sqrt < 0.1F && this.tickCount > 200) {
         this.remove(RemovalReason.DISCARDED);
      }

      double d0 = 0.0;
      double d1 = 0.0;
      double d2 = 0.01;
      double x = this.getX() + this.random.nextFloat() * this.getBbWidth() * 2.0F - this.getBbWidth();
      double y = this.getY() + this.random.nextFloat() * this.getBbHeight() - this.getBbHeight();
      double z = this.getZ() + this.random.nextFloat() * this.getBbWidth() * 2.0F - this.getBbWidth();
      float f = (this.getBbWidth() + this.getBbHeight() + this.getBbWidth()) * 0.333F + 0.5F;
      if (this.particleDistSq(x, y, z) < f * f) {
         this.level().addParticle(ParticleTypes.SNEEZE, x, y + 0.5, z, d0, d1, d2);
      }

      Vec3 vector3d = this.getDeltaMovement();
      double f3 = vector3d.horizontalDistance();
      this.setYRot((float)(Mth.atan2(vector3d.x, vector3d.z) * 180.0F / (float)Math.PI));
      this.setXRot((float)(Mth.atan2(vector3d.y, f3) * 180.0F / (float)Math.PI));
      this.yRotO = this.getYRot();
      this.xRotO = this.getXRot();
      Vec3 vector3d2 = this.position();
      Vec3 vector3d3 = vector3d2.add(vector3d);
      HitResult raytraceresult = this.level().clip(new ClipContext(vector3d2, vector3d3, Block.COLLIDER, Fluid.NONE, this));
      if (raytraceresult.getType() != Type.MISS) {
         vector3d3 = raytraceresult.getLocation();
      }

      while (!this.isRemoved()) {
         EntityHitResult entityraytraceresult = this.findHitEntity(vector3d2, vector3d3);
         if (entityraytraceresult != null) {
            raytraceresult = entityraytraceresult;
         }

         if (raytraceresult != null && raytraceresult.getType() == Type.ENTITY) {
            assert raytraceresult instanceof EntityHitResult;
            Entity entity = ((EntityHitResult)raytraceresult).getEntity();
            Entity entity1 = this.getOwner();
            if (entity instanceof Player && entity1 instanceof Player && !((Player)entity1).canHarmPlayer((Player)entity)) {
               raytraceresult = null;
               entityraytraceresult = null;
            }
         }

         if (raytraceresult != null && raytraceresult.getType() != Type.MISS) {
            if (raytraceresult.getType() != Type.BLOCK) {
               this.onHit(raytraceresult);
            }

            this.hasImpulse = true;
         }

         if (entityraytraceresult == null || this.getPierceLevel() <= 0) {
            break;
         }

         raytraceresult = null;
      }
   }

   public double particleDistSq(double toX, double toY, double toZ) {
      double d0 = this.getX() - toX;
      double d1 = this.getY() - toY;
      double d2 = this.getZ() - toZ;
      return d0 * d0 + d1 * d1 + d2 * d2;
   }

   public void playSound(SoundEvent soundIn, float volume, float pitch) {
      if (!this.isSilent() && soundIn != SoundEvents.ARROW_HIT && soundIn != SoundEvents.ARROW_HIT_PLAYER) {
         this.level().playSound(null, this.getX(), this.getY(), this.getZ(), soundIn, this.getSoundSource(), volume, pitch);
      }
   }

   public boolean isNoGravity() {
      return true;
   }

   public void setPunch(int knockbackStrengthIn) {
      this.knockbackStrength = knockbackStrengthIn;
   }

   protected void onHitEntity(EntityHitResult result) {
      Entity entity = result.getEntity();
      float f = (float)this.getDeltaMovement().length();
      int i = Mth.ceil(Math.max(f * this.getBaseDamage(), 0.0));
      if (this.getPierceLevel() > 0) {
         if (this.piercedEntities == null) {
            this.piercedEntities = new IntOpenHashSet(5);
         }

         if (this.hitEntities == null) {
            this.hitEntities = Lists.newArrayListWithCapacity(5);
         }

         if (this.piercedEntities.size() >= this.getPierceLevel() + 1) {
            this.remove(RemovalReason.DISCARDED);
            return;
         }

         this.piercedEntities.add(entity.getId());
      }

      if (this.isCritArrow()) {
         i += this.random.nextInt(i / 2 + 2);
      }

      Entity entity1 = this.getOwner();
      DamageSource damagesource = this.level().damageSources().magic();
      if (entity1 != null && entity1 instanceof LivingEntity living) {
         damagesource = this.level().damageSources().indirectMagic(this, entity1);
         living.setLastHurtMob(entity);
      }

      boolean flag = entity.getType() == EntityType.ENDERMAN;
      int j = entity.getRemainingFireTicks();
      if (this.isOnFire() && !flag) {
         entity.igniteForSeconds(5.0F);
      }

      if (entity.hurt(damagesource, i)) {
         if (flag) {
            return;
         }

         if (entity instanceof LivingEntity livingentity) {
            if (this.knockbackStrength > 0) {
               Vec3 vec3d = this.getDeltaMovement().multiply(1.0, 0.0, 1.0).normalize().scale(this.knockbackStrength * 0.6);
               if (vec3d.lengthSqr() > 0.0) {
                  livingentity.push(vec3d.x, 0.1, vec3d.z);
               }
            }

            this.doPostHurtEffects(livingentity);
            if (livingentity != entity1 && livingentity instanceof Player && entity1 instanceof ServerPlayer player) {
               player.connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.ARROW_HIT_PLAYER, 0.0F), null);
            }

            if (!entity.isAlive() && this.hitEntities != null) {
               this.hitEntities.add(livingentity);
            }
         }

         this.playSound(this.getHitGroundSoundEvent(), 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
         if (this.getPierceLevel() <= 0) {
            this.remove(RemovalReason.DISCARDED);
         }
      } else {
         this.setDeltaMovement(this.getDeltaMovement().scale(-0.1));
         if (!this.level().isClientSide && this.getDeltaMovement().lengthSqr() < 1.0E-7) {
            this.remove(RemovalReason.DISCARDED);
         }
      }
   }

   protected ItemStack getDefaultPickupItem() {
      return new ItemStack((ItemLike)IafItems.GHOST_SWORD.get());
   }
}
