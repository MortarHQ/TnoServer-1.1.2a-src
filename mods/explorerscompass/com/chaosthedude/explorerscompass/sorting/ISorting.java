package com.chaosthedude.explorerscompass.sorting;

import java.util.Comparator;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface ISorting extends Comparator<ResourceLocation> {
   int compare(ResourceLocation var1, ResourceLocation var2);

   Object getValue(ResourceLocation var1);

   ISorting next();

   String getLocalizedName();
}
