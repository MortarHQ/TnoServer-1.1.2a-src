package com.chronodawn.core.portal;

import com.chronodawn.ChronoDawn;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public class PortalStateMachine {
   private final UUID portalId;
   private final ResourceKey<Level> sourceDimension;
   private final BlockPos position;
   private PortalState currentState;

   public PortalStateMachine(UUID portalId, ResourceKey<Level> sourceDimension, BlockPos position) {
      this.portalId = portalId;
      this.sourceDimension = sourceDimension;
      this.position = position;
      this.currentState = PortalState.INACTIVE;
   }

   public boolean activate() {
      if (this.currentState != PortalState.INACTIVE) {
         ChronoDawn.LOGGER.warn("Cannot activate portal {} - current state is {}", this.portalId, this.currentState);
         return false;
      } else {
         this.currentState = PortalState.ACTIVATED;
         PortalRegistry.getInstance().markDirtyForPortal(this.portalId);
         ChronoDawn.LOGGER.debug("Portal {} activated at {} in dimension {}", new Object[]{this.portalId, this.position, this.sourceDimension.location()});
         return true;
      }
   }

   public boolean deactivate() {
      if (this.currentState != PortalState.ACTIVATED) {
         ChronoDawn.LOGGER.warn("Cannot deactivate portal {} - current state is {}", this.portalId, this.currentState);
         return false;
      } else {
         this.currentState = PortalState.DEACTIVATED;
         PortalRegistry.getInstance().markDirtyForPortal(this.portalId);
         ChronoDawn.LOGGER.debug("Portal {} deactivated at {} in dimension {}", new Object[]{this.portalId, this.position, this.sourceDimension.location()});
         return true;
      }
   }

   public boolean stabilize() {
      if (!this.currentState.canBeStabilized()) {
         ChronoDawn.LOGGER.warn("Cannot stabilize portal {} - current state is {}", this.portalId, this.currentState);
         return false;
      } else {
         this.currentState = PortalState.STABILIZED;
         PortalRegistry.getInstance().markDirtyForPortal(this.portalId);
         ChronoDawn.LOGGER.debug("Portal {} stabilized at {} in dimension {}", new Object[]{this.portalId, this.position, this.sourceDimension.location()});
         return true;
      }
   }

   public PortalState getCurrentState() {
      return this.currentState;
   }

   public void setState(PortalState state) {
      this.currentState = state;
   }

   public UUID getPortalId() {
      return this.portalId;
   }

   public ResourceKey<Level> getSourceDimension() {
      return this.sourceDimension;
   }

   public BlockPos getPosition() {
      return this.position;
   }

   public boolean allowsTravel() {
      return this.currentState.allowsTravel();
   }

   public boolean isBidirectional() {
      return this.currentState.isBidirectional();
   }

   public boolean hasPortalBlocks() {
      return this.currentState.hasPortalBlocks();
   }
}
