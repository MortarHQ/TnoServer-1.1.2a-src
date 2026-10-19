package it.hurts.sskirillss.relics.badges.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public abstract non-sealed class RelicBadge extends AbstractBadge {
   public RelicBadge(String id) {
      super(id);
   }

   public MutableComponent getTitle(ItemStack stack) {
      return Component.translatable("tooltip.relics.researching.badge.relic." + this.getId() + ".title");
   }

   public List<MutableComponent> getDescription(ItemStack stack) {
      return Arrays.asList(Component.translatable("tooltip.relics.researching.badge.relic." + this.getId() + ".description"));
   }

   public List<MutableComponent> getHint(ItemStack stack) {
      return new ArrayList<>();
   }

   public boolean isVisible(ItemStack stack) {
      return false;
   }

   @Override
   public final ResourceLocation getIconTexture() {
      return ResourceLocation.fromNamespaceAndPath("relics", "textures/gui/description/general/badges/relic/" + this.getId() + ".png");
   }

   @Override
   public final ResourceLocation getOutlineTexture() {
      return ResourceLocation.fromNamespaceAndPath("relics", "textures/gui/description/general/badges/relic/" + this.getId() + "_outline.png");
   }
}
