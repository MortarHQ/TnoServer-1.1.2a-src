package com.mongoose.royalvariations.common.entities.projectile;

import com.mongoose.royalvariations.client.particles.RVParticleTypes;
import com.mongoose.royalvariations.common.entities.RVEntityType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class NetEntity extends TangleEntity {
   public NetEntity(EntityType<?> p_19870_, Level p_19871_) {
      super(p_19870_, p_19871_);
   }

   public NetEntity(Level p_19871_, @Nullable LivingEntity owner, Entity target) {
      super((EntityType<?>)RVEntityType.NET.get(), p_19871_, owner, target);
   }

   public NetEntity(Level p_19871_, @Nullable LivingEntity owner, BlockPos blockPos) {
      super((EntityType<?>)RVEntityType.NET.get(), p_19871_, owner, blockPos);
   }

   protected void defineSynchedData(Builder builder) {
   }

   public boolean isPickable() {
      return true;
   }

   public boolean hurt(DamageSource source, float damage) {
      if (source.getDirectEntity() instanceof LivingEntity livingEntity
         && (source.getMsgId().equals("mob") || source.getMsgId().equals("player") || source.getMsgId().equals("goety.summon"))
         && livingEntity.getMainHandItem().is(ItemTags.SWORDS)) {
         this.burrow();
      }

      return super.hurt(source, damage);
   }

   @Override
   public void burrow() {
      if (this.level() instanceof ServerLevel serverLevel) {
         serverLevel.sendParticles(
            (SimpleParticleType)RVParticleTypes.SCRAPS_NET.get(),
            this.getX(),
            this.getY() + 0.5,
            this.getZ(),
            serverLevel.getRandom().nextIntBetweenInclusive(4, 6),
            0.35,
            0.2,
            0.35,
            0.2
         );
      }

      this.playSound(SoundEvents.LEASH_KNOT_BREAK);
      this.discard();
   }
}
