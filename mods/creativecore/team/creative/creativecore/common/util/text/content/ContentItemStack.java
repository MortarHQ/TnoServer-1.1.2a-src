package team.creative.creativecore.common.util.text.content;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Optional;
import net.minecraft.client.StringSplitter.WidthProvider;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.ComponentContents.Type;
import net.minecraft.network.chat.FormattedText.ContentConsumer;
import net.minecraft.network.chat.FormattedText.StyledContentConsumer;
import net.minecraft.world.item.ItemStack;
import team.creative.creativecore.client.render.GuiRenderHelper;

public record ContentItemStack(ItemStack stack) implements AdvancedContent {
   public <T> Optional<T> visit(StyledContentConsumer<T> consumer, Style style) {
      return this.stack.getHoverName().visit(consumer, style);
   }

   public <T> Optional<T> visit(ContentConsumer<T> consumer) {
      return this.stack.getHoverName().visit(consumer);
   }

   @Override
   public int width(WidthProvider widthProvider, Style style) {
      return 14;
   }

   @Override
   public int height() {
      return 8;
   }

   @Override
   public FormattedText asText() {
      return new ContentItemStack.ContentItemStackText(this);
   }

   public Type<?> type() {
      return null;
   }

   private record ContentItemStackText(ContentItemStack content) implements AdvancedFormattedText {
      @Override
      public int width(WidthProvider widthProvider, Style style) {
         return this.content.width(widthProvider, style);
      }

      @Override
      public int height() {
         return this.content.height();
      }

      @Override
      public void render(GuiGraphics graphics, int defaultColor) {
         PoseStack pose = graphics.pose();
         pose.pushPose();
         pose.translate(0.0F, -2.0F, 10.0F);
         pose.scale(0.8F, 0.8F, 0.8F);
         GuiRenderHelper.drawItemStack(graphics, this.content.stack, 1.0F);
         pose.popPose();
      }
   }
}
