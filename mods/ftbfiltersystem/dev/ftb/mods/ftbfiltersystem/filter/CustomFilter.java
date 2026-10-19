package dev.ftb.mods.ftbfiltersystem.filter;

import dev.ftb.mods.ftbfiltersystem.api.FTBFilterSystemAPI;
import dev.ftb.mods.ftbfiltersystem.api.event.CustomFilterEvent;
import dev.ftb.mods.ftbfiltersystem.api.filter.AbstractSmartFilter;
import dev.ftb.mods.ftbfiltersystem.api.filter.SmartFilter;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class CustomFilter extends AbstractSmartFilter {
   public static final ResourceLocation ID = FTBFilterSystemAPI.rl("custom");
   private final String eventId;
   private final String extraData;

   public CustomFilter(SmartFilter.Compound parent) {
      this(parent, "", "");
   }

   public CustomFilter(SmartFilter.Compound parent, String eventId, String extraData) {
      super(parent);
      this.eventId = eventId;
      this.extraData = extraData;
   }

   public String getEventId() {
      return this.eventId;
   }

   public String getExtraData() {
      return this.extraData;
   }

   @Override
   public ResourceLocation getId() {
      return ID;
   }

   public boolean test(ItemStack stack) {
      return ((CustomFilterEvent)CustomFilterEvent.MATCH_ITEM.invoker()).matchItem(stack, this.eventId, this.extraData).isTrue();
   }

   @Override
   public String getStringArg(Provider registryAccess) {
      return this.extraData.isEmpty() ? this.eventId : this.eventId + "/" + this.extraData;
   }

   public static CustomFilter fromString(SmartFilter.Compound parent, String str, Provider ignored2) {
      String[] parts = str.split("/");
      return new CustomFilter(parent, parts[0], parts.length > 1 ? parts[1] : "");
   }
}
