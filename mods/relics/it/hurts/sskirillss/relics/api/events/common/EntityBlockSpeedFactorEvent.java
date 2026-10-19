package it.hurts.sskirillss.relics.api.events.common;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.event.entity.EntityEvent;

public class EntityBlockSpeedFactorEvent extends EntityEvent {
   private final BlockState state;
   private float speedFactor;

   public EntityBlockSpeedFactorEvent(Entity entity, BlockState state, float speedFactor) {
      super(entity);
      this.state = state;
      this.speedFactor = speedFactor;
   }

   public BlockState getState() {
      return this.state;
   }

   public float getSpeedFactor() {
      return this.speedFactor;
   }

   public void setSpeedFactor(float speedFactor) {
      this.speedFactor = speedFactor;
   }
}
