package it.hurts.sskirillss.relics.badges.base;

import net.minecraft.resources.ResourceLocation;

public abstract sealed class AbstractBadge permits AbilityBadge, RelicBadge {
   private final String id;

   public abstract ResourceLocation getIconTexture();

   public abstract ResourceLocation getOutlineTexture();

   public AbstractBadge(String id) {
      this.id = id;
   }

   public String getId() {
      return this.id;
   }
}
