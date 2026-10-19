package team.creative.creativecore.client.render.text;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.ComponentCollector;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.Font.DisplayMode;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.FormattedText.StyledContentConsumer;
import net.minecraft.util.Mth;
import net.minecraft.util.StringDecomposer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import team.creative.creativecore.CreativeCore;
import team.creative.creativecore.common.gui.Align;
import team.creative.creativecore.common.gui.VAlign;
import team.creative.creativecore.common.util.text.AdvancedComponentHelper;
import team.creative.creativecore.common.util.text.content.AdvancedContent;
import team.creative.creativecore.common.util.text.content.AdvancedContentConsumer;
import team.creative.creativecore.common.util.text.content.AdvancedFormattedText;
import team.creative.creativecore.common.util.type.list.SingletonList;

public class CompiledText {
   public static final CompiledText EMPTY = new CompiledText(0, 0) {
      {
         this.original = Collections.EMPTY_LIST;
         this.lines = Collections.EMPTY_LIST;
      }

      @Override
      public void setText(Component component) {
      }

      @Override
      public void setText(List<Component> components) {
      }

      @Override
      protected void compile() {
      }

      @Override
      public int getTotalHeight() {
         return 0;
      }

      @OnlyIn(Dist.CLIENT)
      @Environment(EnvType.CLIENT)
      @Override
      public void render(GuiGraphics graphics) {
      }
   };
   private int maxWidth;
   private int maxWidthScaled;
   private int maxHeight;
   private int maxHeightScaled;
   private int usedWidth;
   private int usedHeight;
   private int lineSpacing = 2;
   private boolean shadow = true;
   private int defaultColor = -1;
   private Align align = Align.LEFT;
   private VAlign valign = VAlign.TOP;
   protected List<CompiledText.CompiledLine> lines;
   protected List<Component> original;
   private double scale = 1.0;

   public static CompiledText createAnySize() {
      return new CompiledText(Integer.MAX_VALUE, Integer.MAX_VALUE);
   }

   private static int width(FormattedText text) {
      return text instanceof AdvancedFormattedText adv
         ? adv.width(AdvancedComponentHelper.SPLITTER.width, Style.EMPTY)
         : Mth.ceil(AdvancedComponentHelper.SPLITTER.stringWidth(text));
   }

   private static int lineHeight(FormattedText text) {
      if (text instanceof AdvancedContent adv) {
         return adv.height();
      } else {
         return text instanceof Component comp && comp.getContents() instanceof AdvancedContent adv
            ? adv.height()
            : AdvancedComponentHelper.SPLITTER.lineHeight;
      }
   }

   public CompiledText(int width, int height) {
      this.maxWidth = this.maxWidthScaled = width;
      this.maxHeight = this.maxHeightScaled = height;
      this.setText(Collections.EMPTY_LIST);
   }

   public void setMaxHeight(int height) {
      this.maxHeight = height;
      this.maxHeightScaled = (int)(this.maxHeight * this.scale);
   }

   public void setDimension(int width, int height) {
      this.maxWidth = width;
      this.maxWidthScaled = (int)(this.maxWidth / this.scale);
      this.maxHeight = height;
      this.maxHeightScaled = (int)(this.maxHeight / this.scale);
      this.compile();
   }

   public int getMaxWidth() {
      return this.maxWidth;
   }

   public int getMaxHeight() {
      return this.maxHeight;
   }

   public int getDefaultColor() {
      return this.defaultColor;
   }

   public void setDefaultColor(int color) {
      this.defaultColor = color;
   }

   public void setShadow(boolean shadow) {
      this.shadow = shadow;
   }

   public void setAlign(Align align) {
      this.align = align;
   }

   public void setVAlign(VAlign valign) {
      this.valign = valign;
   }

   public void setScale(double scale) {
      this.scale = scale;
      this.maxWidthScaled = (int)(this.maxWidth / scale);
      this.maxHeightScaled = (int)(this.maxHeight / scale);
      this.compile();
   }

   public double getScale() {
      return this.scale;
   }

   public void setText(Component component) {
      this.setText(new SingletonList<>(component));
   }

   public void setText(List<Component> components) {
      this.original = components;
      this.compile();
   }

   protected void compile() {
      if (!CreativeCore.loader().getOverallSide().isServer()) {
         List<Component> copy = new ArrayList<>();

         for (Component component : this.original) {
            copy.add(AdvancedComponentHelper.copy(component));
         }

         this.lines = new ArrayList<>();
         this.compileNext(null, true, copy);
      }
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   private CompiledText.CompiledLine compileNext(CompiledText.CompiledLine currentLine, boolean newLine, List<? extends FormattedText> components) {
      for (FormattedText component : components) {
         if (newLine) {
            this.lines.add(currentLine = new CompiledText.CompiledLine());
         }

         currentLine = this.compileNext(currentLine, component);
      }

      return currentLine;
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   private CompiledText.CompiledLine compileNext(CompiledText.CompiledLine currentLine, boolean newLine, FormattedText component) {
      if (newLine) {
         this.lines.add(currentLine = new CompiledText.CompiledLine());
      }

      return this.compileNext(currentLine, component);
   }

   private CompiledText.CompiledLine compileNext(CompiledText.CompiledLine currentLine, FormattedText component) {
      List<Component> siblings = null;
      if (component instanceof Component && !((Component)component).getSiblings().isEmpty()) {
         siblings = new ArrayList<>(((Component)component).getSiblings());
         ((Component)component).getSiblings().clear();
      }

      FormattedText next = currentLine.add(component);
      if (next != null) {
         this.lines.add(currentLine = new CompiledText.CompiledLine());
         currentLine = this.compileNext(currentLine, false, next);
      }

      if (siblings != null) {
         currentLine = this.compileNext(currentLine, false, siblings);
      }

      return currentLine;
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   public int getTotalHeight() {
      int height = -this.lineSpacing;

      for (CompiledText.CompiledLine line : this.lines) {
         height += line.height + this.lineSpacing;
      }

      return Mth.ceil(height * this.scale);
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   public void render(GuiGraphics graphics) {
      if (this.lines != null) {
         this.usedWidth = 0;
         this.usedHeight = -this.lineSpacing;
         int totalHeight = this.getTotalHeight();
         PoseStack stack = graphics.pose();
         if (this.scale != 1.0) {
            stack.pushPose();
            stack.scale((float)this.scale, (float)this.scale, 1.0F);
         }

         stack.pushPose();

         float y = Math.max(0, switch (this.valign) {
            case CENTER -> this.maxHeightScaled / 2 - totalHeight / 2;
            case BOTTOM -> this.maxHeightScaled - totalHeight;
            default -> 0;
         });
         stack.translate(0.0F, y, 0.0F);
         this.usedHeight += (int)y;

         for (CompiledText.CompiledLine line : this.lines) {
            switch (this.align) {
               case CENTER: {
                  int x = this.maxWidthScaled / 2 - line.width / 2;
                  stack.translate(x, 0.0F, 0.0F);
                  line.render(graphics);
                  stack.translate(-x, 0.0F, 0.0F);
                  this.usedWidth = Math.max(this.usedWidth, this.maxWidthScaled);
                  break;
               }
               case RIGHT: {
                  int x = this.maxWidthScaled - line.width;
                  stack.translate(x, 0.0F, 0.0F);
                  line.render(graphics);
                  stack.translate(-x, 0.0F, 0.0F);
                  this.usedWidth = Math.max(this.usedWidth, this.maxWidthScaled);
                  break;
               }
               default:
                  line.render(graphics);
                  this.usedWidth = Math.max(this.usedWidth, line.width);
            }

            int height = line.height + this.lineSpacing;
            stack.translate(0.0F, height, 0.0F);
            this.usedHeight += height;
            if (this.usedHeight > this.maxHeightScaled) {
               break;
            }
         }

         stack.popPose();
         if (this.scale != 1.0) {
            stack.popPose();
         }

         RenderSystem.enableBlend();
         this.usedWidth = (int)(this.usedWidth * this.scale);
         this.usedHeight = (int)(this.usedHeight * this.scale);
      }
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   public CompiledText.FormattedTextSplit splitByWidth(FormattedText text, int width, Style style, final boolean force) {
      final WidthLimitedCharSink charSink = new WidthLimitedCharSink(width, Minecraft.getInstance().font.getSplitter());
      final ComponentCollector head = new ComponentCollector();
      final ComponentCollector tail = new ComponentCollector();
      if (text instanceof Component comp) {
         AdvancedComponentHelper.visit(comp, new AdvancedContentConsumer() {
            @Override
            public Optional accept(Style style, AdvancedContent content) {
               if (!charSink.accept(style, content) && !force) {
                  tail.append(content.asText());
               } else {
                  head.append(content.asText());
               }

               return Optional.empty();
            }

            @Override
            public Optional accept(Style style, String textx) {
               charSink.resetPosition();
               if (!StringDecomposer.iterateFormatted(textx, style, charSink)) {
                  Linebreaker breaker = charSink.lastBreaker();
                  if (!force && breaker == null) {
                     tail.append(FormattedText.of(textx, style));
                  } else {
                     String sHead;
                     String sTail;
                     if (breaker == null) {
                        sHead = textx.substring(0, charSink.getPosition());
                        sTail = textx.substring(charSink.getPosition());
                     } else {
                        int pos = charSink.lastBreakerPos();
                        sHead = textx.substring(0, pos + (breaker.includeChar && breaker.head ? 1 : 0));
                        sTail = textx.substring(pos + (breaker.includeChar && !breaker.head ? 0 : 1));
                     }

                     if (!sHead.isEmpty()) {
                        head.append(FormattedText.of(sHead, style));
                     }

                     if (!sTail.isEmpty()) {
                        tail.append(FormattedText.of(sTail, style));
                     }
                  }
               } else if (!textx.isEmpty()) {
                  head.append(FormattedText.of(textx, style));
               }

               return Optional.empty();
            }
         }, style);
      } else {
         text.visit(new StyledContentConsumer<FormattedText>() {
            public Optional<FormattedText> accept(Style style, String text) {
               charSink.resetPosition();
               if (!StringDecomposer.iterateFormatted(text, style, charSink)) {
                  Linebreaker breaker = charSink.lastBreaker();
                  if (!force && breaker == null) {
                     tail.append(FormattedText.of(text, style));
                  } else {
                     String sHead;
                     String sTail;
                     if (breaker == null) {
                        sHead = text.substring(0, charSink.getPosition());
                        sTail = text.substring(charSink.getPosition());
                     } else {
                        int pos = charSink.lastBreakerPos();
                        sHead = text.substring(0, pos + (breaker.includeChar && breaker.head ? 1 : 0));
                        sTail = text.substring(pos + (breaker.includeChar && !breaker.head ? 0 : 1));
                     }

                     if (!sHead.isEmpty()) {
                        head.append(FormattedText.of(sHead, style));
                     }

                     if (!sTail.isEmpty()) {
                        tail.append(FormattedText.of(sTail, style));
                     }
                  }
               } else if (!text.isEmpty()) {
                  head.append(FormattedText.of(text, style));
               }

               return Optional.empty();
            }
         }, style);
      }

      FormattedText headText = head.getResult();
      FormattedText tailText = tail.getResult();
      return headText == null && tailText == null ? null : new CompiledText.FormattedTextSplit(headText, tailText);
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   public int getTotalWidth() {
      return Mth.ceil(this.calculateWidth(0, true, this.original) * this.scale);
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   private int calculateWidth(int width, boolean newLine, List<? extends FormattedText> components) {
      for (FormattedText component : components) {
         int result = width(component);
         if (newLine) {
            width = Math.max(width, result);
         } else {
            width += result;
         }
      }

      return width;
   }

   public CompiledText sameDimensions() {
      CompiledText copy = new CompiledText(this.maxWidth, this.maxHeight);
      copy.align = this.align;
      copy.valign = this.valign;
      copy.lineSpacing = this.lineSpacing;
      copy.scale = this.scale;
      copy.maxWidthScaled = this.maxWidthScaled;
      copy.maxHeightScaled = this.maxHeightScaled;
      return copy;
   }

   public CompiledText copy() {
      CompiledText copy = new CompiledText(this.maxWidth, this.maxHeight);
      copy.align = this.align;
      copy.valign = this.valign;
      copy.defaultColor = this.defaultColor;
      copy.lineSpacing = this.lineSpacing;
      copy.shadow = this.shadow;
      copy.scale = this.scale;
      copy.maxWidthScaled = this.maxWidthScaled;
      copy.maxHeightScaled = this.maxHeightScaled;
      List<Component> components = new ArrayList<>();

      for (Component component : this.original) {
         components.add(component.copy());
      }

      copy.setText(components);
      return copy;
   }

   public boolean contains(String search) {
      for (CompiledText.CompiledLine line : this.lines) {
         if (line.contains(search)) {
            return true;
         }
      }

      return false;
   }

   public int getUsedWidth() {
      return this.usedWidth;
   }

   public int getUsedHeight() {
      return this.usedHeight;
   }

   public Iterable<Component> untrimmedContent() {
      return this.original;
   }

   public class CompiledLine {
      private final List<FormattedText> components = new ArrayList<>();
      private int height = 0;
      private int width = 0;

      public boolean contains(String search) {
         for (FormattedText text : this.components) {
            if (text.getString().contains(search)) {
               return true;
            }
         }

         return false;
      }

      @OnlyIn(Dist.CLIENT)
      @Environment(EnvType.CLIENT)
      public void render(GuiGraphics graphics) {
         Font font = Minecraft.getInstance().font;
         int xOffset = 0;
         PoseStack pose = graphics.pose();
         BufferSource bufferSource = graphics.bufferSource();

         for (FormattedText text : this.components) {
            int height = CompiledText.lineHeight(text);
            int width = CompiledText.width(text);
            int yOffset = 0;
            if (height < this.height) {
               yOffset = (this.height - height) / 2;
            }

            pose.pushPose();
            pose.translate(xOffset, yOffset, 0.0F);
            if (text instanceof AdvancedFormattedText adv) {
               adv.render(graphics, CompiledText.this.defaultColor);
            } else {
               font.drawInBatch(
                  Language.getInstance().getVisualOrder(text),
                  0.0F,
                  0.0F,
                  CompiledText.this.defaultColor,
                  CompiledText.this.shadow,
                  pose.last().pose(),
                  bufferSource,
                  DisplayMode.NORMAL,
                  0,
                  15728880
               );
               bufferSource.endBatch();
            }

            pose.popPose();
            xOffset += width;
         }
      }

      @OnlyIn(Dist.CLIENT)
      @Environment(EnvType.CLIENT)
      public void updateDimension(int width, int height) {
         this.width = Math.max(width, this.width);
         this.height = Math.max(height, this.height);
      }

      public FormattedText add(FormattedText component) {
         int remainingWidth = CompiledText.this.maxWidthScaled - this.width;
         int textWidth = CompiledText.width(component);
         if (remainingWidth >= textWidth) {
            if (component instanceof Component comp && comp.getContents() instanceof AdvancedContent adv) {
               this.components.add(adv.asText());
            } else {
               this.components.add(component);
            }

            this.updateDimension(this.width + textWidth, CompiledText.lineHeight(component));
            return null;
         } else {
            CompiledText.FormattedTextSplit split = CompiledText.this.splitByWidth(component, remainingWidth, Style.EMPTY, this.width == 0);
            if (split == null || split.head == null && this.width != 0) {
               return this.width == 0 ? null : component;
            } else if (split.head != null) {
               this.updateDimension(this.width + CompiledText.width(split.head), CompiledText.lineHeight(split.head));
               this.components.add(split.head);
               return split.tail;
            } else {
               this.updateDimension(this.width + CompiledText.width(split.tail), CompiledText.lineHeight(split.tail));
               this.components.add(split.tail);
               return null;
            }
         }
      }
   }

   public record FormattedTextSplit(FormattedText head, FormattedText tail) {
   }
}
