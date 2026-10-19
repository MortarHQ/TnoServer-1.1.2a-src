package net.blay09.mods.waystones.api.event;

import java.util.List;
import net.blay09.mods.balm.api.event.BalmEvent;
import net.blay09.mods.waystones.api.Waystone;
import net.minecraft.resources.ResourceLocation;

public class WaystonesListReceivedEvent extends BalmEvent {
   private final ResourceLocation waystoneType;
   private final List<Waystone> waystones;

   public WaystonesListReceivedEvent(ResourceLocation waystoneType, List<Waystone> waystones) {
      this.waystoneType = waystoneType;
      this.waystones = waystones;
   }

   public ResourceLocation getWaystoneType() {
      return this.waystoneType;
   }

   public List<Waystone> getWaystones() {
      return this.waystones;
   }
}
