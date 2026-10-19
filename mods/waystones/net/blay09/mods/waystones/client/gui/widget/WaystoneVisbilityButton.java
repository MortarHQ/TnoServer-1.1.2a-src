package net.blay09.mods.waystones.client.gui.widget;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import net.blay09.mods.waystones.api.WaystoneVisibility;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

public class WaystoneVisbilityButton extends Button implements ITooltipProvider {
   private final WidgetSprites ACTIVATION_SPRITES = new WidgetSprites(
      ResourceLocation.withDefaultNamespace("waystones/visibility_button_activation"),
      ResourceLocation.withDefaultNamespace("waystones/visibility_button_activation_highlighted")
   );
   private final WidgetSprites GLOBAL_SPRITES = new WidgetSprites(
      ResourceLocation.withDefaultNamespace("waystones/visibility_button_global"),
      ResourceLocation.withDefaultNamespace("waystones/visibility_button_global_highlighted")
   );
   private final WidgetSprites SHARD_ONLY_SPRITES = new WidgetSprites(
      ResourceLocation.withDefaultNamespace("waystones/visibility_button_shard_only"),
      ResourceLocation.withDefaultNamespace("waystones/visibility_button_shard_only_highlighted")
   );
   private final WidgetSprites SHARESTONE_SPRITES = new WidgetSprites(
      ResourceLocation.withDefaultNamespace("waystones/visibility_button_sharestone"),
      ResourceLocation.withDefaultNamespace("waystones/visibility_button_sharestone_highlighted")
   );
   private final List<WaystoneVisibility> options;
   private final boolean canEdit;
   private WaystoneVisibility visibility;

   public WaystoneVisbilityButton(int x, int y, WaystoneVisibility visibility, List<WaystoneVisibility> options, boolean canEdit) {
      super(x, y, 18, 18, Component.empty(), button -> {}, Button.DEFAULT_NARRATION);
      this.options = options;
      this.visibility = visibility;
      this.canEdit = canEdit;
   }

   public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partial) {
      ResourceLocation sprite = this.getSprites().get(this.isActive(), this.isHoveredOrFocused());
      guiGraphics.blitSprite(sprite, this.getX(), this.getY(), 20, 20);
   }

   @Override
   public boolean shouldShowTooltip() {
      return this.isHovered;
   }

   @Override
   public List<Component> getTooltipComponents() {
      MutableComponent visibilityValueComponent = Component.translatable("tooltip.waystones.visibility." + this.visibility.name().toLowerCase(Locale.ROOT))
         .withStyle(ChatFormatting.WHITE);
      ArrayList<Component> result = new ArrayList<>();
      result.add(Component.translatable("tooltip.waystones.visibility", new Object[]{visibilityValueComponent}).withStyle(ChatFormatting.YELLOW));
      if (!this.canEdit) {
         result.add(Component.translatable("tooltip.waystones.edit_restricted").withStyle(ChatFormatting.RED));
      }

      return result;
   }

   public WaystoneVisibility getVisibility() {
      return this.visibility;
   }

   private WidgetSprites getSprites() {
      return switch (this.visibility) {
         case ACTIVATION -> this.ACTIVATION_SPRITES;
         case GLOBAL -> this.GLOBAL_SPRITES;
         case SHARD_ONLY -> this.SHARD_ONLY_SPRITES;
         case ORANGE_SHARESTONE, GRAY_SHARESTONE, LIGHT_GRAY_SHARESTONE, BLACK_SHARESTONE, RED_SHARESTONE, GREEN_SHARESTONE, BROWN_SHARESTONE, BLUE_SHARESTONE, PURPLE_SHARESTONE, CYAN_SHARESTONE, PINK_SHARESTONE, LIME_SHARESTONE, YELLOW_SHARESTONE, LIGHT_BLUE_SHARESTONE, MAGENTA_SHARESTONE -> this.SHARESTONE_SPRITES;
      };
   }

   public void onPress() {
      if (this.canEdit) {
         int index = this.options.indexOf(this.visibility);
         this.visibility = this.options.get((index + 1) % this.options.size());
      }
   }
}
