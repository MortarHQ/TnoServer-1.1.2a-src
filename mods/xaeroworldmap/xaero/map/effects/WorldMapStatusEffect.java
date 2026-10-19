package xaero.map.effects;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class WorldMapStatusEffect extends MobEffect {
   private ResourceLocation id;

   protected WorldMapStatusEffect(MobEffectCategory type, int color, String idPrefix) {
      super(type, color);
      String suffix = type == MobEffectCategory.HARMFUL ? "_harmful" : (type == MobEffectCategory.BENEFICIAL ? "_beneficial" : "");
      this.setRegistryName(ResourceLocation.fromNamespaceAndPath("xaeroworldmap", idPrefix + suffix));
   }

   protected void setRegistryName(ResourceLocation id) {
      this.id = id;
   }

   public ResourceLocation getRegistryName() {
      return this.id;
   }
}
