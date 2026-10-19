package net.blay09.mods.waystones.api.event;

import java.util.UUID;
import net.blay09.mods.balm.api.event.BalmEvent;
import net.minecraft.resources.ResourceLocation;

public class WaystoneRemoveReceivedEvent extends BalmEvent {
   private final ResourceLocation waystoneType;
   private final UUID waystoneId;
   private final boolean wasDestroyed;

   public WaystoneRemoveReceivedEvent(ResourceLocation waystoneType, UUID waystoneId, boolean wasDestroyed) {
      this.waystoneType = waystoneType;
      this.waystoneId = waystoneId;
      this.wasDestroyed = wasDestroyed;
   }

   public ResourceLocation getWaystoneType() {
      return this.waystoneType;
   }

   public UUID getWaystoneId() {
      return this.waystoneId;
   }

   public boolean wasDestroyed() {
      return this.wasDestroyed;
   }
}
