package dev.ftb.mods.ftbfiltersystem.filter.compound;

import dev.ftb.mods.ftbfiltersystem.api.FTBFilterSystemAPI;
import dev.ftb.mods.ftbfiltersystem.api.filter.AbstractCompoundFilter;
import dev.ftb.mods.ftbfiltersystem.api.filter.SmartFilter;
import java.util.List;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class NotFilter extends AbstractCompoundFilter {
   public static final ResourceLocation ID = FTBFilterSystemAPI.rl("not");

   public NotFilter(SmartFilter.Compound parent) {
      super(parent);
   }

   public NotFilter(SmartFilter.Compound parent, List<SmartFilter> children) {
      super(parent, children);
   }

   @Override
   public int maxChildren() {
      return 1;
   }

   public boolean test(ItemStack stack) {
      return !this.getChildren().isEmpty() && !this.getChildren().getFirst().test(stack);
   }

   public static NotFilter fromString(SmartFilter.Compound parent, String str, Provider registryAccess) {
      return createCompoundFilter(NotFilter::new, parent, str, registryAccess);
   }

   @Override
   public ResourceLocation getId() {
      return ID;
   }
}
