package org.anti_ad.mc.ipnext.item.rule.parameter;

import net.minecraft.resources.ResourceLocation;
import org.anti_ad.mc.ipnext.item.ItemType;
import org.jetbrains.annotations.NotNull;

public abstract sealed class ItemTypeMatcher permits ItemTypeMatcher$IsItem, ItemTypeMatcher$IsTag {
   @NotNull
   public static final ItemTypeMatcher$Companion Companion = new ItemTypeMatcher$Companion(null);

   private ItemTypeMatcher() {
   }

   public abstract boolean match(@NotNull ItemType var1);

   @NotNull
   public abstract ResourceLocation getIdentifier();
}
