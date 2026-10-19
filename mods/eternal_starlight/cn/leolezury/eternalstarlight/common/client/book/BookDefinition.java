package cn.leolezury.eternalstarlight.common.client.book;

import cn.leolezury.eternalstarlight.common.client.book.component.ConfiguredBookComponent;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import net.minecraft.resources.ResourceLocation;

public record BookDefinition(
   List<List<ConfiguredBookComponent<?, ?>>> components,
   int width,
   int height,
   int frameWidth,
   BookDefinition.Buttons buttons,
   BookDefinition.Scrollbar scrollbar,
   BookDefinition.Textures textures
) {
   public static final Codec<BookDefinition> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
            ConfiguredBookComponent.CODEC.listOf().listOf().fieldOf("components").forGetter(BookDefinition::components),
            Codec.INT.fieldOf("width").forGetter(BookDefinition::width),
            Codec.INT.fieldOf("height").forGetter(BookDefinition::height),
            Codec.INT.fieldOf("frame_width").forGetter(BookDefinition::frameWidth),
            BookDefinition.Buttons.CODEC.fieldOf("buttons").forGetter(BookDefinition::buttons),
            BookDefinition.Scrollbar.CODEC.fieldOf("scrollbar").forGetter(BookDefinition::scrollbar),
            BookDefinition.Textures.CODEC.fieldOf("textures").forGetter(BookDefinition::textures)
         )
         .apply(instance, BookDefinition::new)
   );

   public Optional<ConfiguredBookComponent<?, ?>> getComponent(ResourceLocation id) {
      for (ConfiguredBookComponent<?, ?> component : this.components().stream().flatMap(Collection::stream).toList()) {
         if (component.config().id().equals(id)) {
            return Optional.of(component);
         }
      }

      return Optional.empty();
   }

   public record Buttons(
      int upDownButtonWidth,
      int upDownButtonHeight,
      int upButtonOffset,
      int downButtonOffset,
      int upDownButtonDistanceFromRight,
      int leftRightButtonWidth,
      int leftRightButtonHeight,
      int leftButtonOffset,
      int rightButtonOffset,
      int leftRightButtonDistanceFromTop
   ) {
      public static final Codec<BookDefinition.Buttons> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               Codec.INT.fieldOf("up_down_button_width").forGetter(BookDefinition.Buttons::upDownButtonWidth),
               Codec.INT.fieldOf("up_down_button_height").forGetter(BookDefinition.Buttons::upDownButtonHeight),
               Codec.INT.fieldOf("up_button_offset").forGetter(BookDefinition.Buttons::upButtonOffset),
               Codec.INT.fieldOf("down_button_offset").forGetter(BookDefinition.Buttons::downButtonOffset),
               Codec.INT.fieldOf("up_down_button_distance_from_right").forGetter(BookDefinition.Buttons::upDownButtonDistanceFromRight),
               Codec.INT.fieldOf("left_right_button_width").forGetter(BookDefinition.Buttons::leftRightButtonWidth),
               Codec.INT.fieldOf("left_right_button_height").forGetter(BookDefinition.Buttons::leftRightButtonHeight),
               Codec.INT.fieldOf("left_button_offset").forGetter(BookDefinition.Buttons::leftButtonOffset),
               Codec.INT.fieldOf("right_button_offset").forGetter(BookDefinition.Buttons::rightButtonOffset),
               Codec.INT.fieldOf("left_right_button_distance_from_top").forGetter(BookDefinition.Buttons::leftRightButtonDistanceFromTop)
            )
            .apply(instance, BookDefinition.Buttons::new)
      );
   }

   public record Scrollbar(int scrollbarWidth, int scrollbarHeight, int scrollbarXOffset, int scrollbarYOffset, int scrollButtonWidth, int scrollButtonColor) {
      public static final Codec<BookDefinition.Scrollbar> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               Codec.INT.fieldOf("scrollbar_width").forGetter(BookDefinition.Scrollbar::scrollbarWidth),
               Codec.INT.fieldOf("scrollbar_height").forGetter(BookDefinition.Scrollbar::scrollbarHeight),
               Codec.INT.fieldOf("scrollbar_x_offset").forGetter(BookDefinition.Scrollbar::scrollbarXOffset),
               Codec.INT.fieldOf("scrollbar_y_offset").forGetter(BookDefinition.Scrollbar::scrollbarYOffset),
               Codec.INT.fieldOf("scroll_button_width").forGetter(BookDefinition.Scrollbar::scrollButtonWidth),
               Codec.INT.fieldOf("scroll_button_color").forGetter(BookDefinition.Scrollbar::scrollButtonColor)
            )
            .apply(instance, BookDefinition.Scrollbar::new)
      );
   }

   public record Textures(
      ResourceLocation background,
      ResourceLocation overlay,
      ResourceLocation topOverlay,
      ResourceLocation upButton,
      ResourceLocation downButton,
      ResourceLocation leftButton,
      ResourceLocation rightButton
   ) {
      public static final Codec<BookDefinition.Textures> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               ResourceLocation.CODEC.fieldOf("background").forGetter(BookDefinition.Textures::background),
               ResourceLocation.CODEC.fieldOf("overlay").forGetter(BookDefinition.Textures::overlay),
               ResourceLocation.CODEC.fieldOf("top_overlay").forGetter(BookDefinition.Textures::topOverlay),
               ResourceLocation.CODEC.fieldOf("up_button").forGetter(BookDefinition.Textures::upButton),
               ResourceLocation.CODEC.fieldOf("down_button").forGetter(BookDefinition.Textures::downButton),
               ResourceLocation.CODEC.fieldOf("left_button").forGetter(BookDefinition.Textures::leftButton),
               ResourceLocation.CODEC.fieldOf("right_button").forGetter(BookDefinition.Textures::rightButton)
            )
            .apply(instance, BookDefinition.Textures::new)
      );
   }
}
