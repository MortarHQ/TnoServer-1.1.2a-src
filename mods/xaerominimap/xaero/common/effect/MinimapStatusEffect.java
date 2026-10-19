package xaero.common.effect;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class MinimapStatusEffect extends MobEffect {
   private ResourceLocation id;

   protected MinimapStatusEffect(MobEffectCategory type, int color, String idPrefix) {
      super(type, color);
      String suffix = type == MobEffectCategory.HARMFUL ? "_harmful" : (type == MobEffectCategory.BENEFICIAL ? "_beneficial" : "");
      this.setRegistryName(ResourceLocation.fromNamespaceAndPath("xaerominimap", idPrefix + suffix));
   }

   protected void setRegistryName(ResourceLocation id) {
      this.id = id;
   }

   public ResourceLocation getRegistryName() {
      return this.id;
   }
}
