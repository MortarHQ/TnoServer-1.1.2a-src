package net.blay09.mods.waystones.api.event;

import java.util.List;
import net.blay09.mods.balm.api.event.BalmEvent;
import net.blay09.mods.waystones.api.WaystoneTeleportContext;
import net.blay09.mods.waystones.api.requirement.WarpRequirement;
import net.minecraft.world.entity.Entity;

public abstract class WaystoneTeleportEvent extends BalmEvent {
   public static class Post extends WaystoneTeleportEvent {
      private final WaystoneTeleportContext context;
      private final List<Entity> teleportedEntities;

      public Post(WaystoneTeleportContext context, List<Entity> teleportedEntities) {
         this.context = context;
         this.teleportedEntities = teleportedEntities;
      }

      public WaystoneTeleportContext getContext() {
         return this.context;
      }

      public List<Entity> getTeleportedEntities() {
         return this.teleportedEntities;
      }
   }

   public static class Pre extends WaystoneTeleportEvent {
      private final WaystoneTeleportContext context;

      public Pre(WaystoneTeleportContext context) {
         this.context = context;
      }

      public WaystoneTeleportContext getContext() {
         return this.context;
      }

      public WarpRequirement getRequirements() {
         return this.context.getRequirements();
      }

      public void setRequirements(WarpRequirement warpRequirement) {
         this.context.setRequirements(warpRequirement);
      }

      public void addAdditionalEntity(Entity additionalEntity) {
         this.context.addAdditionalEntity(additionalEntity);
      }
   }
}
