package dev.ftb.mods.ftblibrary.util.client;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.ftb.mods.ftblibrary.config.NameMap;
import dev.ftb.mods.ftblibrary.icon.Icon;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.ComponentContents.Type;
import net.minecraft.util.StringRepresentable;

public class ImageComponent implements ComponentContents {
   public static final MapCodec<ImageComponent> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(
            Codec.STRING.fieldOf("image_id").forGetter(ImageComponent::imageStr),
            Codec.INT.optionalFieldOf("width", 100).forGetter(ImageComponent::getWidth),
            Codec.INT.optionalFieldOf("height", 100).forGetter(ImageComponent::getHeight),
            ImageComponent.ImageAlign.CODEC.optionalFieldOf("align", ImageComponent.ImageAlign.CENTER).forGetter(ImageComponent::getAlign),
            Codec.BOOL.optionalFieldOf("fit", false).forGetter(ImageComponent::isFit),
            Codec.STRING.optionalFieldOf("click_action", "").forGetter(ImageComponent::getClickAction)
         )
         .apply(instance, ImageComponent::create)
   );
   private static final Type<ImageComponent> TYPE = new Type(CODEC, "image");
   private Icon image = Icon.empty();
   private int width = 100;
   private int height = 100;
   private ImageComponent.ImageAlign align = ImageComponent.ImageAlign.CENTER;
   private boolean fit = false;
   private String clickAction = "";

   public static ImageComponent create(String id, int width, int height, ImageComponent.ImageAlign align, boolean fit, String clickAction) {
      ImageComponent c = new ImageComponent();
      c.image = Icon.getIcon(id);
      c.width = width;
      c.height = height;
      c.align = align;
      c.fit = fit;
      c.clickAction = clickAction;
      return c;
   }

   public String imageStr() {
      return this.image.toString();
   }

   public Icon getImage() {
      return this.image;
   }

   public void setImage(Icon image) {
      this.image = image;
   }

   public int getWidth() {
      return this.width;
   }

   public void setWidth(int width) {
      this.width = width;
   }

   public int getHeight() {
      return this.height;
   }

   public void setHeight(int height) {
      this.height = height;
   }

   public ImageComponent.ImageAlign getAlign() {
      return this.align;
   }

   public void setAlign(ImageComponent.ImageAlign align) {
      this.align = align;
   }

   public boolean isFit() {
      return this.fit;
   }

   public void setFit(boolean fit) {
      this.fit = fit;
   }

   public String getClickAction() {
      return this.clickAction;
   }

   public void setClickAction(String clickAction) {
      this.clickAction = clickAction;
   }

   @Override
   public String toString() {
      StringBuilder sb = new StringBuilder("{image:");
      sb.append(this.image);
      sb.append(" width:").append(this.width);
      sb.append(" height:").append(this.height);
      sb.append(" align:").append(this.align.getName());
      if (this.fit) {
         sb.append(" fit:true");
      }

      if (!this.clickAction.isEmpty()) {
         sb.append(" click_action:").append(this.clickAction.replace(" ", "%20"));
      }

      sb.append('}');
      return sb.toString();
   }

   public Type<?> type() {
      return TYPE;
   }

   public enum ImageAlign implements StringRepresentable {
      LEFT("left"),
      CENTER("center"),
      RIGHT("right");

      public static final Codec<ImageComponent.ImageAlign> CODEC = StringRepresentable.fromEnum(ImageComponent.ImageAlign::values);
      public static final NameMap<ImageComponent.ImageAlign> NAME_MAP = NameMap.of(CENTER, values()).id(v -> v.name).create();
      private final String name;

      ImageAlign(String name) {
         this.name = name;
      }

      public static ImageComponent.ImageAlign byName(String name) {
         return NAME_MAP.get(name);
      }

      public String getName() {
         return this.name;
      }

      public String getSerializedName() {
         return this.name;
      }
   }
}
