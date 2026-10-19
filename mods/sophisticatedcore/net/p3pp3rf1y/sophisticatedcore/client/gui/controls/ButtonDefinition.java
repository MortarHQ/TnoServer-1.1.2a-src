package net.p3pp3rf1y.sophisticatedcore.client.gui.controls;

import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Dimension;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.TextureBlitData;

public class ButtonDefinition {
   private final Dimension dimension;
   @Nullable
   private final TextureBlitData backgroundTexture;
   @Nullable
   private final TextureBlitData hoveredBackgroundTexture;
   @Nullable
   private final TextureBlitData foregroundTexture;
   private final List<Component> tooltip;

   public ButtonDefinition(Dimension dimension, @Nullable TextureBlitData backgroundTexture, @Nullable TextureBlitData hoveredBackgroundTexture) {
      this(dimension, backgroundTexture, hoveredBackgroundTexture, null, Component.literal(""));
   }

   public ButtonDefinition(
      Dimension dimension,
      @Nullable TextureBlitData backgroundTexture,
      @Nullable TextureBlitData hoveredBackgroundTexture,
      @Nullable TextureBlitData foregroundTexture,
      Component... tooltip
   ) {
      this.dimension = dimension;
      this.backgroundTexture = backgroundTexture;
      this.hoveredBackgroundTexture = hoveredBackgroundTexture;
      this.foregroundTexture = foregroundTexture;
      this.tooltip = List.of(tooltip);
   }

   public Dimension getDimension() {
      return this.dimension;
   }

   @Nullable
   public TextureBlitData getBackgroundTexture() {
      return this.backgroundTexture;
   }

   @Nullable
   public TextureBlitData getHoveredBackgroundTexture() {
      return this.hoveredBackgroundTexture;
   }

   @Nullable
   public TextureBlitData getForegroundTexture() {
      return this.foregroundTexture;
   }

   public List<Component> getTooltip() {
      return this.tooltip;
   }

   public static class Toggle<T extends Comparable<T>> extends ButtonDefinition {
      private final Map<T, ToggleButton.StateData> stateData;

      public Toggle(
         Dimension dimension, TextureBlitData backgroundTexture, Map<T, ToggleButton.StateData> stateData, @Nullable TextureBlitData hoveredBackgroundTexture
      ) {
         super(dimension, backgroundTexture, hoveredBackgroundTexture);
         this.stateData = stateData;
      }

      public Map<T, ToggleButton.StateData> getStateData() {
         return this.stateData;
      }
   }
}
