package com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom;

import com.cerbon.bosses_of_mass_destruction.entity.util.IEntityTick;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.AABB;

public class VoidBlossomSpikeTick implements IEntityTick<ServerLevel> {
   private final VoidBlossomEntity entity;

   public VoidBlossomSpikeTick(VoidBlossomEntity entity) {
      this.entity = entity;
   }

   public void tick(ServerLevel level) {
      AABB spikeHitbox = new AABB(this.entity.position(), this.entity.position()).inflate(3.0, 3.0, 3.0).move(0.0, 1.5, 0.0);

      for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, spikeHitbox, livingEntity -> livingEntity != this.entity)) {
         float damage = (float)this.entity.getAttributeValue(Attributes.ATTACK_DAMAGE);
         if (target.position().distanceToSqr(this.entity.position()) < Math.pow(3.0, 2.0)) {
            target.hurt(this.entity.level().damageSources().thorns(this.entity), damage);
         }
      }
   }
}
