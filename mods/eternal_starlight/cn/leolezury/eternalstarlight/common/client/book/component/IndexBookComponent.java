package cn.leolezury.eternalstarlight.common.client.book.component;

import cn.leolezury.eternalstarlight.common.client.book.BookContext;
import cn.leolezury.eternalstarlight.common.client.book.text.BookContent;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.ItemStack;

public class IndexBookComponent extends BookComponent<IndexBookComponent.Config> {
   public IndexBookComponent() {
      super(IndexBookComponent.Config.CODEC);
   }

   private List<IndexBookComponent.Entry> filterEnabledEntries(IndexBookComponent.Config config, BookContext context) {
      List<IndexBookComponent.Entry> filtered = new ArrayList<>(config.entries());
      filtered.removeIf(entry -> !context.isComponentEnabled(entry.jumpTo));
      return filtered;
   }

   public int getTotalHeight(IndexBookComponent.Config config, BookContext context) {
      int totalHeight = config.offset() + config.extraHeight();

      for (IndexBookComponent.Entry entry : this.filterEnabledEntries(config, context)) {
         totalHeight += entry.getHeight(config, context);
      }

      return totalHeight;
   }

   public void render(IndexBookComponent.Config config, BookContext context, GuiGraphics graphics, int x, int y) {
      int startHeight = config.offset();

      for (IndexBookComponent.Entry entry : this.filterEnabledEntries(config, context)) {
         entry.render(config, context, graphics, x, y + startHeight);
         startHeight += entry.getHeight(config, context);
      }
   }

   public void onClick(IndexBookComponent.Config config, BookContext context, int x, int y) {
      int startHeight = y + config.offset();

      for (IndexBookComponent.Entry entry : this.filterEnabledEntries(config, context)) {
         int currentHeight = entry.getHeight(config, context);
         if (context.getMouseY() > startHeight && context.getMouseY() < startHeight + currentHeight) {
            context.jumpToComponent(entry.jumpTo);
            break;
         }

         startHeight += currentHeight;
      }
   }

   public record Config(
      ResourceLocation id,
      HashSet<HashSet<ResourceLocation>> unlockConditions,
      List<IndexBookComponent.Entry> entries,
      int offset,
      int extraHeight,
      int width,
      int lineHeight
   ) implements BookComponentConfig {
      public static final Codec<IndexBookComponent.Config> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               ResourceLocation.CODEC.fieldOf("id").forGetter(IndexBookComponent.Config::id),
               ResourceLocation.CODEC
                  .listOf()
                  .xmap(Sets::newHashSet, Lists::newArrayList)
                  .listOf()
                  .xmap(Sets::newHashSet, Lists::newArrayList)
                  .fieldOf("unlock_conditions")
                  .forGetter(IndexBookComponent.Config::unlockConditions),
               IndexBookComponent.Entry.CODEC.listOf().fieldOf("entries").forGetter(IndexBookComponent.Config::entries),
               Codec.INT.fieldOf("offset").forGetter(IndexBookComponent.Config::offset),
               Codec.INT.fieldOf("extra_height").forGetter(IndexBookComponent.Config::extraHeight),
               Codec.INT.fieldOf("width").forGetter(IndexBookComponent.Config::width),
               Codec.INT.fieldOf("line_height").forGetter(IndexBookComponent.Config::lineHeight)
            )
            .apply(instance, IndexBookComponent.Config::new)
      );
   }

   public static class Entry {
      public static final Codec<IndexBookComponent.Entry> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               BookContent.CODEC.fieldOf("text").forGetter(o -> o.text),
               ResourceLocation.CODEC.fieldOf("jump_to").forGetter(o -> o.jumpTo),
               ResourceLocation.CODEC.listOf().xmap(Sets::newHashSet, Lists::newArrayList).fieldOf("listening").forGetter(o -> o.listening),
               Codec.INT.fieldOf("icon_frame_width").forGetter(o -> o.iconFrameWidth),
               Codec.INT.fieldOf("icon_frame_height").forGetter(o -> o.iconFrameHeight),
               ResourceLocation.CODEC.fieldOf("icon_frame").forGetter(o -> o.iconFrame),
               CompoundTag.CODEC.fieldOf("icon").forGetter(o -> o.icon)
            )
            .apply(instance, IndexBookComponent.Entry::new)
      );
      private final BookContent text;
      private final ResourceLocation jumpTo;
      private final HashSet<ResourceLocation> listening;
      private final int iconFrameWidth;
      private final int iconFrameHeight;
      private final ResourceLocation iconFrame;
      private final CompoundTag icon;
      private ItemStack cachedIcon;

      public Entry(
         BookContent text,
         ResourceLocation jumpTo,
         HashSet<ResourceLocation> listening,
         int iconFrameWidth,
         int iconFrameHeight,
         ResourceLocation iconFrame,
         CompoundTag icon
      ) {
         this.text = text;
         this.jumpTo = jumpTo;
         this.listening = listening;
         this.iconFrameWidth = iconFrameWidth;
         this.iconFrameHeight = iconFrameHeight;
         this.iconFrame = iconFrame;
         this.icon = icon;
      }

      public ResourceLocation getJumpToId() {
         return this.jumpTo;
      }

      public HashSet<ResourceLocation> getListeningIds() {
         return this.listening;
      }

      public Component getText() {
         return this.text.toComponent();
      }

      public ItemStack getIcon() {
         if (this.cachedIcon == null && Minecraft.getInstance().level != null) {
            this.cachedIcon = ItemStack.parseOptional(Minecraft.getInstance().level.registryAccess(), this.icon);
         }

         return this.cachedIcon == null ? ItemStack.EMPTY : this.cachedIcon;
      }

      private List<FormattedCharSequence> splitText(IndexBookComponent.Config config, BookContext context, Component component) {
         return context.getFont().split(component, config.width() - this.iconFrameWidth - 5);
      }

      public int getHeight(IndexBookComponent.Config config, BookContext context) {
         return Math.max(
            (this.splitText(config, context, this.getText()).size() - 1) * config.lineHeight() + 9 / 2 + this.iconFrameHeight / 2, this.iconFrameHeight
         );
      }

      public void render(IndexBookComponent.Config config, BookContext context, GuiGraphics graphics, int x, int y) {
         graphics.blit(this.iconFrame, x, y, 0.0F, 0.0F, this.iconFrameWidth, this.iconFrameHeight, this.iconFrameWidth, this.iconFrameHeight);
         graphics.renderItem(this.getIcon(), x + this.iconFrameWidth / 2 - 8, y + this.iconFrameHeight / 2 - 8);
         boolean selected = context.getMouseX() >= context.getContentX()
            && context.getMouseX() <= context.getContentX() + context.getBookDefinition().width() - 2 * context.getBookDefinition().frameWidth()
            && context.getMouseY() > y
            && context.getMouseY() < y + this.getHeight(config, context);
         List<FormattedCharSequence> list = this.splitText(
            config, context, (Component)(selected ? this.getText().copy().withStyle(ChatFormatting.UNDERLINE) : this.getText())
         );

         for (int i = 0; i < list.size(); i++) {
            graphics.drawString(
               context.getFont(), list.get(i), x + this.iconFrameWidth + 5, y + this.iconFrameHeight / 2 - 9 / 2 + i * config.lineHeight(), -1, true
            );
         }
      }
   }
}
