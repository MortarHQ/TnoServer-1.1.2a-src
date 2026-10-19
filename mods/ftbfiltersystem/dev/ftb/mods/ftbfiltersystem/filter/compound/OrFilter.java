package dev.ftb.mods.ftbfiltersystem.filter.compound;

import dev.ftb.mods.ftbfiltersystem.api.FTBFilterSystemAPI;
import dev.ftb.mods.ftbfiltersystem.api.filter.AbstractCompoundFilter;
import dev.ftb.mods.ftbfiltersystem.api.filter.SmartFilter;
import java.util.List;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class OrFilter extends AbstractCompoundFilter {
   public static final ResourceLocation ID = FTBFilterSystemAPI.rl("or");

   public OrFilter(SmartFilter.Compound parent) {
      super(parent);
   }

   public OrFilter(SmartFilter.Compound parent, List<SmartFilter> children) {
      super(parent, children);
   }

   public boolean test(ItemStack stack) {
      return this.getChildren().stream().anyMatch(filter -> filter.test(stack));
   }

   public static OrFilter fromString(SmartFilter.Compound parent, String str, Provider registryAccess) {
      return createCompoundFilter(OrFilter::new, parent, str, registryAccess);
   }

   @Override
   public ResourceLocation getId() {
      return ID;
   }
}
