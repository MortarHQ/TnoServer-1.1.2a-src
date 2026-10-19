package cn.leolezury.eternalstarlight.common.client.book.component;

import cn.leolezury.eternalstarlight.common.client.book.BookContext;
import cn.leolezury.eternalstarlight.common.client.book.text.BookContent;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.HashSet;
import java.util.List;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.ClickEvent.Action;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.util.FastColor.ARGB32;

public class TextBookComponent extends BookComponent<TextBookComponent.Config> {
   public TextBookComponent() {
      super(TextBookComponent.Config.CODEC);
   }

   public int getTotalHeight(TextBookComponent.Config config, BookContext context) {
      return context.getFont().split(config.text().toComponent(), config.width()).size() * config.lineHeight() + config.offset() + config.extraHeight();
   }

   public void render(TextBookComponent.Config config, BookContext context, GuiGraphics graphics, int x, int y) {
      List<FormattedCharSequence> list = context.getFont().split(config.text().toComponent(), config.width());

      for (int i = 0; i < list.size(); i++) {
         int renderY = y + config.offset() + i * config.lineHeight();
         float alpha = Math.abs(renderY - (context.getContentY() - context.getBookDefinition().frameWidth() + 0.5F * context.getBookDefinition().height()))
            / (0.5F * context.getBookDefinition().height() - context.getBookDefinition().frameWidth());
         graphics.drawString(context.getFont(), list.get(i), x, renderY, ARGB32.colorFromFloat(Mth.clamp(1.2F - alpha, 0.1F, 1.0F), 0.0F, 0.0F, 0.0F), true);
      }
   }

   public void onClick(TextBookComponent.Config config, BookContext context, int x, int y) {
      List<FormattedCharSequence> list = context.getFont().split(config.text().toComponent(), config.width());
      int line = (context.getMouseY() - (y + config.offset())) / config.lineHeight();
      if (context.getMouseY() >= y + config.offset() && line >= 0 && line < list.size()) {
         FormattedCharSequence charSequence = list.get(line);
         int width = context.getFont().width(charSequence);
         if (context.getMouseX() >= x && context.getMouseX() <= x + width) {
            Style style = context.getFont().getSplitter().componentStyleAtWidth(charSequence, context.getMouseX() - x);
            if (style != null) {
               ClickEvent event = style.getClickEvent();
               if (event != null && event.getAction() == Action.CHANGE_PAGE) {
                  context.jumpToComponent(ResourceLocation.parse(event.getValue()));
               }
            }
         }
      }
   }

   public record Config(
      ResourceLocation id, HashSet<HashSet<ResourceLocation>> unlockConditions, BookContent text, int offset, int extraHeight, int width, int lineHeight
   ) implements BookComponentConfig {
      public static final Codec<TextBookComponent.Config> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               ResourceLocation.CODEC.fieldOf("id").forGetter(TextBookComponent.Config::id),
               ResourceLocation.CODEC
                  .listOf()
                  .xmap(Sets::newHashSet, Lists::newArrayList)
                  .listOf()
                  .xmap(Sets::newHashSet, Lists::newArrayList)
                  .fieldOf("unlock_conditions")
                  .forGetter(TextBookComponent.Config::unlockConditions),
               BookContent.CODEC.fieldOf("text").forGetter(TextBookComponent.Config::text),
               Codec.INT.fieldOf("offset").forGetter(TextBookComponent.Config::offset),
               Codec.INT.fieldOf("extra_height").forGetter(TextBookComponent.Config::extraHeight),
               Codec.INT.fieldOf("width").forGetter(TextBookComponent.Config::width),
               Codec.INT.fieldOf("line_height").forGetter(TextBookComponent.Config::lineHeight)
            )
            .apply(instance, TextBookComponent.Config::new)
      );
   }
}
