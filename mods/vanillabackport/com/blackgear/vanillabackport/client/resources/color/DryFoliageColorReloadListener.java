package com.blackgear.vanillabackport.client.resources.color;

import com.blackgear.vanillabackport.client.api.color.DryFoliageColor;
import java.io.IOException;
import net.minecraft.client.resources.LegacyStuffWrapper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DryFoliageColorReloadListener extends SimplePreparableReloadListener<int[]> {
   private static final ResourceLocation LOCATION = ResourceLocation.withDefaultNamespace("textures/colormap/dry_foliage.png");
   public static final DryFoliageColorReloadListener INSTANCE = new DryFoliageColorReloadListener();

   protected int[] prepare(ResourceManager resourceManager, ProfilerFiller profiler) {
      try {
         return LegacyStuffWrapper.getPixels(resourceManager, LOCATION);
      } catch (IOException exception) {
         throw new IllegalStateException("Failed to load dry foliage color texture", exception);
      }
   }

   protected void apply(int[] object, ResourceManager resourceManager, ProfilerFiller profiler) {
      DryFoliageColor.init(object);
   }
}
