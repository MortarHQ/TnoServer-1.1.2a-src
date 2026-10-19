package com.github.L_Ender.cataclysm.entity.projectile;

import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModItems;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

public class Void_Scatter_Arrow_Entity extends AbstractArrow {
   public Void_Scatter_Arrow_Entity(EntityType<? extends Void_Scatter_Arrow_Entity> p_37411_, Level p_37412_) {
      super(p_37411_, p_37412_);
   }

   public Void_Scatter_Arrow_Entity(Level p_37414_, LivingEntity p_309162_, ItemStack p_309167_, @Nullable ItemStack p_346408_) {
      super((EntityType)ModEntities.VOID_SCATTER_ARROW.get(), p_309162_, p_37414_, p_309167_, p_346408_);
   }

   public Void_Scatter_Arrow_Entity(Level p_37419_, double p_309044_, double p_309099_, double p_308873_, ItemStack p_308959_, @Nullable ItemStack p_345907_) {
      super((EntityType)ModEntities.VOID_SCATTER_ARROW.get(), p_309044_, p_309099_, p_308873_, p_37419_, p_308959_, p_345907_);
   }

   protected void onHit(HitResult hit) {
      super.onHit(hit);
      double x = this.getX();
      double y = this.getY();
      double z = this.getZ();
      if (this.level().isClientSide) {
         for (int l2 = 0; l2 < 8; l2++) {
            this.level()
               .addParticle(
                  new ItemParticleOption(ParticleTypes.ITEM, new ItemStack((ItemLike)ModItems.VOID_SCATTER_ARROW.get())),
                  x,
                  y,
                  z,
                  this.random.nextGaussian() * 0.1,
                  this.random.nextDouble() * 0.15,
                  this.random.nextGaussian() * 0.1
               );
         }
      } else {
         for (Vec3 vec : this.getShootVectors(this.random, 0.0F)) {
            Entity target = null;
            Direction dir = Direction.UP;
            if (hit.getType() == Type.ENTITY) {
               target = ((EntityHitResult)hit).getEntity();
            } else if (hit.getType() == Type.BLOCK) {
               dir = ((BlockHitResult)hit).getDirection();
            }

            vec = vec.scale(0.35F);
            vec = this.mulPoseVector(vec, dir);
            Void_Shard_Entity shard = new Void_Shard_Entity(this.level(), (LivingEntity)this.getOwner(), x + vec.x, y + vec.y + 0.25, vec.z + z, vec, target);
            this.level().addFreshEntity(shard);
         }

         this.playSound(SoundEvents.GLASS_BREAK, 1.1F, 0.8F);
      }

      this.discard();
   }

   public List<Vec3> getShootVectors(RandomSource random, float uncertainty) {
      List<Vec3> vectors = new ArrayList<>();
      float turnFraction = (1.0F + Mth.sqrt(5.0F)) / 2.0F;
      int numPoints = 17;
      double fullness = 0.8;

      for (int i = 1; i <= numPoints; i++) {
         float dst = (float)i / numPoints;
         float inclination = (random.nextFloat() - 0.5F) * uncertainty + (float)Math.acos(1.0 - fullness * dst);
         float azimuth = (float)((random.nextFloat() - 0.5F) * uncertainty + (Math.PI * 2) * (random.nextFloat() + turnFraction * i));
         double x = Math.sin(inclination) * Math.cos(azimuth);
         double z = Math.sin(inclination) * Math.sin(azimuth);
         double y = Math.cos(inclination);
         Vec3 vec = new Vec3(x, y, z);
         if (i == 1) {
            vec = vec.add(0.0, 1.0, 0.0);
            vec = vec.scale(0.5);
         }

         vectors.add(vec);
      }

      return vectors;
   }

   private Vec3 mulPoseVector(Vec3 v, Direction dir) {
      switch (dir) {
         case UP:
         default:
            return v;
         case DOWN:
            return v.multiply(0.0, -1.0, 0.0);
         case NORTH:
            return new Vec3(v.z, v.x, -v.y);
         case SOUTH:
            return new Vec3(v.z, v.x, v.y);
         case WEST:
            return new Vec3(-v.y, v.z, v.x);
         case EAST:
            return new Vec3(v.y, v.z, v.x);
      }
   }

   protected ItemStack getDefaultPickupItem() {
      return new ItemStack((ItemLike)ModItems.VOID_SCATTER_ARROW.get());
   }
}
