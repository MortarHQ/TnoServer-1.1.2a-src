package dev.ftb.mods.ftbfiltersystem.api.filter;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractSmartFilter implements SmartFilter {
   private final SmartFilter.Compound parent;
   private Component displayName = null;

   protected AbstractSmartFilter(@Nullable SmartFilter.Compound parent) {
      this.parent = parent;
   }

   @Nullable
   @Override
   public SmartFilter.Compound getParent() {
      return this.parent;
   }

   @Override
   public Component getDisplayName() {
      if (this.displayName == null) {
         this.displayName = getDisplayName(this.getId());
      }

      return this.displayName;
   }

   @NotNull
   public static MutableComponent getDisplayName(ResourceLocation id) {
      return Component.translatable("filter." + id.toString().replace(':', '.') + ".name");
   }

   @NotNull
   public static MutableComponent getTooltip(ResourceLocation id) {
      return Component.translatable("filter." + id.toString().replace(':', '.') + ".tooltip");
   }
}
