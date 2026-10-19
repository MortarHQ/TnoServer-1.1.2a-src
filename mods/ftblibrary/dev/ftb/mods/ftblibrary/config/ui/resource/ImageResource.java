package dev.ftb.mods.ftblibrary.config.ui.resource;

import dev.ftb.mods.ftblibrary.icon.Icon;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ImageResource implements SelectableResource<ResourceLocation> {
   private final ResourceLocation location;
   private final Component name;
   private final Icon icon;

   public ImageResource(ResourceLocation location) {
      this.location = location;
      this.name = location == null
         ? Component.translatable("gui.none").withStyle(ChatFormatting.GRAY)
         : Component.literal(location.getNamespace())
            .withStyle(ChatFormatting.GOLD)
            .append(":")
            .append(Component.literal(location.getPath()).withStyle(ChatFormatting.YELLOW));
      this.icon = Icon.getIcon(location);
   }

   public ResourceLocation resource() {
      return this.location;
   }

   @Override
   public long getCount() {
      return 1L;
   }

   @Override
   public void setCount(int count) {
   }

   @Override
   public Component getName() {
      return this.name;
   }

   @Override
   public Icon getIcon() {
      return this.icon;
   }

   @Override
   public SelectableResource<ResourceLocation> copyWithCount(long count) {
      return this;
   }
}
