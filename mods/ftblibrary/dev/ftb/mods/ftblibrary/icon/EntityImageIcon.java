package dev.ftb.mods.ftblibrary.icon;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.texture.SimpleTexture.TextureImage;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class EntityImageIcon extends Icon {
   @Nullable
   private final EntityImageIcon.Slice mainSlice;
   private final List<EntityImageIcon.ChildIconData> children;
   private final Icon mainIcon;
   private final List<Icon> childIcons;
   @Nullable
   private final EntityIconLoader.WidthHeight defaultImageSize;

   public EntityImageIcon(
      ResourceLocation mainTexture,
      @Nullable EntityImageIcon.Slice mainSlice,
      List<EntityImageIcon.ChildIconData> children,
      @Nullable EntityIconLoader.WidthHeight defaultImageSize
   ) {
      this.mainSlice = mainSlice;
      this.children = children;
      this.defaultImageSize = defaultImageSize;
      this.mainIcon = this.createIcon(mainTexture, mainSlice);
      this.childIcons = children.stream().map(childIconData -> this.createIcon(childIconData.texture.orElse(mainTexture), childIconData.slice)).toList();
   }

   @Override
   public void draw(GuiGraphics graphics, int x, int y, int width, int height) {
      PoseStack pose = graphics.pose();
      pose.pushPose();
      pose.translate(x, y, 0.0F);
      float drawWidth = this.mainSlice == null ? width : this.mainSlice.width;
      float drawHeight = this.mainSlice == null ? height : this.mainSlice.height;
      float scaleX = width / drawWidth;
      float scaleY = height / drawHeight;
      pose.scale(scaleX, scaleY, 1.0F);
      this.mainIcon.draw(graphics, 0, 0, (int)drawWidth, (int)drawHeight);

      for (int i = 0; i < this.children.size(); i++) {
         EntityImageIcon.ChildIconData child = this.children.get(i);
         Icon icon = this.childIcons.get(i);
         pose.pushPose();
         child.offset.ifPresent(offset -> pose.translate(offset.x, offset.y, 0.0F));
         icon.draw(graphics, 0, 0, child.slice.width, child.slice.height);
         pose.popPose();
      }

      pose.popPose();
   }

   private Icon createIcon(ResourceLocation texture, @Nullable EntityImageIcon.Slice slice) {
      TextureImage load = TextureImage.load(Minecraft.getInstance().getResourceManager(), texture);

      try {
         ImageIcon imageIcon = new ImageIcon(texture);
         if (slice != null) {
            int textureWidth = load.getImage().getWidth();
            int textureHeight = load.getImage().getHeight();
            if (this.defaultImageSize != null) {
               int defaultTextureWidth = this.defaultImageSize.width();
               int defaultTextureHeight = this.defaultImageSize.height();
               float scaleX = (float)textureWidth / defaultTextureWidth;
               float scaleY = (float)textureHeight / defaultTextureHeight;
               return imageIcon.withUV(slice.x * scaleX, slice.y * scaleY, slice.width * scaleX, slice.height * scaleY, textureWidth, textureHeight);
            } else {
               return imageIcon.withUV(slice.x, slice.y, slice.width, slice.height, textureWidth, textureHeight);
            }
         } else {
            return imageIcon;
         }
      } catch (Exception e) {
         throw new RuntimeException("Failed to load image: " + texture, e);
      }
   }

   public record ChildIconData(Optional<ResourceLocation> texture, EntityImageIcon.Slice slice, Optional<EntityImageIcon.Offset> offset) {
      public static final Codec<EntityImageIcon.ChildIconData> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               ResourceLocation.CODEC.optionalFieldOf("texture").forGetter(entityImageIcon -> entityImageIcon.texture),
               EntityImageIcon.Slice.CODEC.fieldOf("slice").forGetter(EntityImageIcon.ChildIconData::slice),
               EntityImageIcon.Offset.CODEC.optionalFieldOf("offset").forGetter(EntityImageIcon.ChildIconData::offset)
            )
            .apply(instance, EntityImageIcon.ChildIconData::new)
      );
   }

   public record Offset(int x, int y) {
      public static final Codec<EntityImageIcon.Offset> CODEC = RecordCodecBuilder.create(
         builder -> builder.group(Codec.INT.fieldOf("x").forGetter(o -> o.x), Codec.INT.fieldOf("y").forGetter(o -> o.y))
            .apply(builder, EntityImageIcon.Offset::new)
      );
   }

   public record Slice(int x, int y, int width, int height) {
      public static final Codec<EntityImageIcon.Slice> CODEC = RecordCodecBuilder.create(
         builder -> builder.group(
               Codec.INT.fieldOf("x").forGetter(o -> o.x),
               Codec.INT.fieldOf("y").forGetter(o -> o.y),
               Codec.INT.fieldOf("width").forGetter(s -> s.width),
               Codec.INT.fieldOf("height").forGetter(s -> s.height)
            )
            .apply(builder, EntityImageIcon.Slice::new)
      );
   }
}
