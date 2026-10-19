package de.ellpeck.naturesaura.api.misc;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.WeightedEntry.IntrusiveBase;

public class WeightedOre extends IntrusiveBase {
   public final ResourceLocation tag;

   public WeightedOre(ResourceLocation tag, int weight) {
      super(weight);
      this.tag = tag;
   }
}
