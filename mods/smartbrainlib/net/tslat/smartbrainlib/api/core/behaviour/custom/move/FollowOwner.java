package net.tslat.smartbrainlib.api.core.behaviour.custom.move;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;

public class FollowOwner<E extends TamableAnimal> extends FollowEntity<E, LivingEntity> {
   protected LivingEntity owner = null;

   public FollowOwner() {
      this.following(this::getOwner);
      this.teleportToTargetAfter(12.0);
   }

   protected LivingEntity getOwner(E entity) {
      if (this.owner != null && (this.owner.isRemoved() || !this.owner.getUUID().equals(entity.getOwnerUUID()))) {
         this.owner = null;
      }

      if (this.owner == null) {
         this.owner = entity.getOwner();
      }

      return this.owner;
   }
}
