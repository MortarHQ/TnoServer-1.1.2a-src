package dev.ftb.mods.ftbfiltersystem.api.filter;

import dev.ftb.mods.ftbfiltersystem.api.FTBFilterSystemAPI;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public interface SmartFilter extends Predicate<ItemStack> {
   ResourceLocation getId();

   @Nullable
   SmartFilter.Compound getParent();

   Component getDisplayName();

   default String asString(Provider registryAccess) {
      return FTBFilterSystemAPI.modDefaultedString(this.getId()) + "(" + this.getStringArg(registryAccess) + ")";
   }

   String getStringArg(Provider var1);

   default Component getDisplayArg(Provider registryAccess) {
      return Component.literal(this.getStringArg(registryAccess));
   }

   default boolean isConfigurable() {
      return true;
   }

   interface Compound extends SmartFilter {
      List<SmartFilter> getChildren();

      default int maxChildren() {
         return Integer.MAX_VALUE;
      }
   }

   @FunctionalInterface
   interface DefaultFactory<T extends SmartFilter> {
      T create(SmartFilter.Compound var1);
   }

   @FunctionalInterface
   interface Factory<T extends SmartFilter> {
      T create(SmartFilter.Compound var1, String var2, Provider var3);
   }
}
