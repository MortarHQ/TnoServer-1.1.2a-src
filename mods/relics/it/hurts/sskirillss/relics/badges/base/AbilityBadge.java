package it.hurts.sskirillss.relics.badges.base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public abstract non-sealed class AbilityBadge extends AbstractBadge {
   public AbilityBadge(String id) {
      super(id);
   }

   public MutableComponent getTitle(ItemStack stack, String ability) {
      return Component.translatable("tooltip.relics.researching.badge.ability." + this.getId() + ".title");
   }

   public List<MutableComponent> getDescription(ItemStack stack, String ability) {
      return Arrays.asList(Component.translatable("tooltip.relics.researching.badge.ability." + this.getId() + ".description"));
   }

   public List<MutableComponent> getHint(ItemStack stack, String ability) {
      return new ArrayList<>();
   }

   public boolean isVisible(ItemStack stack, String ability) {
      return false;
   }

   @Override
   public final ResourceLocation getIconTexture() {
      return ResourceLocation.fromNamespaceAndPath("relics", "textures/gui/description/general/badges/ability/" + this.getId() + ".png");
   }

   @Override
   public final ResourceLocation getOutlineTexture() {
      return ResourceLocation.fromNamespaceAndPath("relics", "textures/gui/description/general/badges/ability/" + this.getId() + "_outline.png");
   }
}
