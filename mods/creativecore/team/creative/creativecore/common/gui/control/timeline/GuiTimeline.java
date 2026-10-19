package team.creative.creativecore.common.gui.control.timeline;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import team.creative.creativecore.client.render.GuiRenderHelper;
import team.creative.creativecore.common.gui.Align;
import team.creative.creativecore.common.gui.GuiControl;
import team.creative.creativecore.common.gui.GuiParent;
import team.creative.creativecore.common.gui.VAlign;
import team.creative.creativecore.common.gui.control.parent.GuiColumn;
import team.creative.creativecore.common.gui.control.parent.GuiRow;
import team.creative.creativecore.common.gui.control.parent.GuiScrollY;
import team.creative.creativecore.common.gui.control.simple.GuiLabel;
import team.creative.creativecore.common.gui.event.GuiControlChangedEvent;
import team.creative.creativecore.common.gui.event.GuiControlEvent;
import team.creative.creativecore.common.gui.flow.GuiFlow;
import team.creative.creativecore.common.gui.flow.GuiSizeRule;
import team.creative.creativecore.common.gui.style.ControlFormatting;
import team.creative.creativecore.common.gui.style.GuiStyle;
import team.creative.creativecore.common.gui.style.display.DisplayColor;
import team.creative.creativecore.common.gui.style.display.StyleDisplay;
import team.creative.creativecore.common.util.math.geo.Rect;
import team.creative.creativecore.common.util.math.vec.SmoothValue;

public class GuiTimeline extends GuiParent {
   protected static final double MAXIMUM_ZOOM = 10.0;
   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   public StyleDisplay cursorHighlight;
   public final GuiAnimationHandler handler;
   private final GuiRow header;
   private final List<GuiTimelineChannel> channels = new ArrayList<>();
   private final GuiParent sidebar;
   private final GuiParent channelbar;
   private final GuiScrollY channelParent;
   protected int duration = 100;
   protected double basePixelWidth;
   protected int headerHeight = 15;
   protected int channelHeight = 9;
   protected int sidebarWidth = 50;
   protected int timelineOffset = 8;
   protected SmoothValue zoom = new SmoothValue(100L);
   protected SmoothValue scrollX = new SmoothValue(100L);
   private GuiTimelineKey selected;
   private int cachedTimelineWidth;
   private double lastZoom = 0.0;
   protected double maxScrollX;

   public GuiTimeline(GuiAnimationHandler handler) {
      this.handler = handler;
      this.align = Align.STRETCH;
      this.valign = VAlign.STRETCH;
      this.flow = GuiFlow.STACK_Y;
      this.spacing = -1;
      this.header = new GuiRow(
         (GuiColumn.GuiColumnHeader)new GuiColumn.GuiColumnHeader().setDim(new GuiSizeRule.GuiFixedDimension(this.sidebarWidth)),
         new GuiTimeline.GuiTimelineHeader()
      );
      this.header.setDim(new GuiSizeRule.GuiSizeRules().prefHeight(this.headerHeight).minHeight(this.headerHeight));
      this.header.spacing = -1;
      this.add(this.header);
      this.channelParent = (new GuiScrollY() {
         @Override
         public ControlFormatting getControlFormatting() {
            return ControlFormatting.TRANSPARENT;
         }

         @Override
         public void scroll(double scrolled) {
            if (Screen.hasControlDown()) {
               super.scroll(scrolled);
            }
         }
      }).setHovered();
      this.channelParent.spacing = -1;
      this.channelParent.align = Align.STRETCH;
      this.channelParent.flow = GuiFlow.STACK_X;
      this.add(this.channelParent);
      this.sidebar = new GuiParent(GuiFlow.STACK_Y).setAlign(Align.STRETCH);
      this.sidebar.spacing = -1;
      this.channelParent.add(this.sidebar);
      this.channelbar = new GuiParent(GuiFlow.STACK_Y).setAlign(Align.STRETCH);
      this.channelbar.spacing = -1;
      this.channelParent.add(this.channelbar.setExpandableX());
   }

   public int getDuration() {
      return this.duration;
   }

   public GuiTimeline setDuration(int duration) {
      this.duration = duration;
      if (this.cachedTimelineWidth != 0) {
         this.updateTickWidth();
      }

      this.adjustKeysPositionX();
      this.scrollX.setStart(0.0);
      this.zoom.setStart(0.0);
      this.raiseEvent(new GuiControlChangedEvent<>(this));
      return this;
   }

   private void updateTickWidth() {
      this.basePixelWidth = (double)(this.cachedTimelineWidth - this.timelineOffset * 2) / this.duration;
   }

   public void selectKey(GuiTimelineKey key) {
      if (this.selected != null) {
         this.selected.setSelected(false);
      }

      this.selected = key;
      this.selected.setSelected(true);
      this.raiseEvent(new GuiTimeline.KeySelectedEvent(key));
   }

   public void deselect() {
      if (this.selected != null) {
         this.selected.setSelected(false);
         this.selected = null;
         this.raiseEvent(new GuiTimeline.NoKeySelectedEvent(this));
      }
   }

   @Override
   public boolean mouseClicked(double x, double y, int button) {
      boolean result = super.mouseClicked(x, y, button);
      if (!result && button == 0) {
         this.deselect();
         return false;
      } else {
         return result;
      }
   }

   public double scrolledX() {
      return this.scrollX.current();
   }

   public void adjustKeyPositionX(GuiTimelineKey key) {
      key.rect.setX(this.timelineOffset + (int)(key.tick * this.getTickWidth()) - key.rect.getWidth() / 2);
   }

   public void adjustKeysPositionX() {
      double tickWidth = this.getTickWidth();

      for (GuiTimelineChannel<?> channel : this.channels) {
         for (GuiControl key : channel) {
            key.rect.setX(this.timelineOffset + (int)(((GuiTimelineKey)key).tick * tickWidth) - key.rect.getWidth() / 2);
         }
      }
   }

   public void setSidebarWidth(int sidebarWidth) {
      this.sidebarWidth = sidebarWidth;
      this.sidebar.setDim(sidebarWidth, -1);
   }

   public GuiTimelineChannel addGuiTimelineChannel(MutableComponent title, GuiTimelineChannel channel) {
      GuiColumn left = new GuiColumn.GuiColumnHeader();
      left.add(new GuiLabel("title").setDropShadow(false).setDefaultColor(-16777216).setTitle(title.withStyle(ChatFormatting.BOLD)));
      this.sidebar.add(left.setVAlign(VAlign.CENTER).setDim(this.sidebarWidth, this.channelHeight));
      channel.sidebarTitle = left;
      this.channels.add(channel);
      this.channelbar.add(channel.setDim(-1, this.channelHeight));
      return channel;
   }

   public void removeChannel(GuiTimelineChannel channel) {
      this.sidebar.remove(channel.sidebarTitle);
      this.channelbar.remove(channel);
      this.channels.remove(channel);
      this.raiseEvent(new GuiControlChangedEvent<>(this));
   }

   @Override
   public boolean isExpandableX() {
      return this.expandableX;
   }

   protected double getTickWidth() {
      return this.basePixelWidth + this.zoom.current();
   }

   protected double getTickWidthAimed() {
      return this.basePixelWidth + this.zoom.aimed();
   }

   public int getTimeAt(double x) {
      double tickWidth = this.getTickWidth();
      return Mth.clamp((int)((x - this.timelineOffset + tickWidth / 2.0 + this.scrollX.current()) / tickWidth), 0, this.duration);
   }

   public int getTimeAtAimed(double x) {
      double tickWidth = this.getTickWidthAimed();
      return Mth.clamp((int)((x - this.timelineOffset + tickWidth / 2.0 + this.scrollX.aimed()) / tickWidth), 0, this.duration);
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   @Override
   public void render(GuiGraphics graphics, Rect controlRect, Rect realRect, double scale, int mouseX, int mouseY) {
      this.zoom.tick();
      this.scrollX.tick();
      super.render(graphics, controlRect, realRect, scale, mouseX, mouseY);
   }

   @Override
   public boolean mouseScrolled(double x, double y, double delta) {
      if (Screen.hasShiftDown()) {
         this.scrollX.set(Mth.clamp(this.scrollX.aimed() - delta * 10.0, 0.0, this.maxScrollX));
         return true;
      } else if (Screen.hasControlDown()) {
         this.channelParent.scroll(delta);
         return true;
      } else {
         return super.mouseScrolled(x, y, delta);
      }
   }

   public void scrolled(int width, double x, double delta) {
      x += this.timelineOffset;
      int focusedTick = Math.max(0, this.getTimeAtAimed(x));
      this.zoom
         .set(Mth.clamp(this.zoom.aimed() + delta * this.basePixelWidth * 2.0 * Math.max(this.basePixelWidth * 2.0, this.zoom.aimed()) / 10.0, 0.0, 10.0));
      int currentTick = Math.max(0, this.getTimeAtAimed(x));
      double aimedTickWidth = this.getTickWidthAimed();
      double sizeX = this.timelineOffset * 2 + aimedTickWidth * this.duration;
      this.maxScrollX = Math.max(0.0, sizeX - width);
      this.scrollX.set(Mth.clamp(this.scrollX.aimed() + (focusedTick - currentTick) * aimedTickWidth, 0.0, this.maxScrollX));
   }

   public class GuiTimelineHeader extends GuiColumn.GuiColumnHeader {
      public boolean dragged = false;

      public GuiTimelineHeader() {
         this.setExpandableX();
      }

      @Override
      public boolean mouseClicked(double x, double y, int button) {
         this.dragged = true;
         GuiTimeline.this.handler.set(GuiTimeline.this.getTimeAt(x));
         playSound(SoundEvents.GLOW_ITEM_FRAME_ROTATE_ITEM);
         return true;
      }

      @Override
      public void mouseMoved(double x, double y) {
         if (this.dragged) {
            int tick = GuiTimeline.this.getTimeAt(x);
            if (tick != GuiTimeline.this.handler.get()) {
               GuiTimeline.this.handler.set(tick);
               playSound(SoundEvents.GLOW_ITEM_FRAME_ROTATE_ITEM, 0.02F, 1.0F);
            }
         }
      }

      @Override
      public void mouseReleased(double x, double y, int button) {
         this.dragged = false;
      }

      @Override
      public boolean mouseScrolled(double x, double y, double delta) {
         GuiTimeline.this.scrolled(this.rect.getWidth(), x, delta);
         return true;
      }

      @OnlyIn(Dist.CLIENT)
      @Environment(EnvType.CLIENT)
      @Override
      protected void renderContent(GuiGraphics graphics, int mouseX, int mouseY) {
         if (GuiTimeline.this.lastZoom != GuiTimeline.this.zoom.current()) {
            GuiTimeline.this.lastZoom = GuiTimeline.this.zoom.current();
            GuiTimeline.this.adjustKeysPositionX();
         }

         PoseStack pose = graphics.pose();
         double tickWidth = GuiTimeline.this.getTickWidth();
         if (GuiTimeline.this.cursorHighlight == null) {
            GuiTimeline.this.cursorHighlight = new DisplayColor(0.78F, 0.78F, 0.0F, 0.59F);
         }

         int width = this.rect.getContentWidth();
         int height = this.rect.getContentHeight();
         int contentOffset = this.getContentOffset() - 1;
         pose.translate(GuiTimeline.this.timelineOffset - 1, -contentOffset, 0.0F);
         int ticks = (int)(width / tickWidth);
         int area = 5;
         int halfArea = 5;
         int smallestStep = 0;

         while (Math.pow(area, smallestStep) * tickWidth < 3.0) {
            smallestStep++;
         }

         smallestStep = (int)Math.pow(area, smallestStep);
         double stepWidth = tickWidth * smallestStep;
         int stepOffset = (int)(GuiTimeline.this.scrollX.current() / stepWidth);
         int stamps = ticks / smallestStep;
         int begin = Math.max(0, stepOffset);
         int end = stepOffset + stamps + 1;
         int pointerWidth = Math.max((int)tickWidth, 1);
         GuiTimeline.this.cursorHighlight
            .render(graphics, tickWidth * GuiTimeline.this.handler.get() - pointerWidth / 2.0 - GuiTimeline.this.scrollX.current(), 0.0, pointerWidth, height);
         GuiStyle style = this.getStyle();
         StyleDisplay border = style.get(ControlFormatting.ControlStyleBorder.SMALL);
         Font font = GuiRenderHelper.getFont();
         pose.pushPose();
         pose.translate(-GuiTimeline.this.scrollX.current() + begin * stepWidth, 0.0, 0.0);

         for (int i = begin; i < end; i++) {
            if (i % halfArea == 0) {
               border.render(graphics, 1.0, 4.0);
               String text = i * smallestStep + "";
               graphics.drawString(Minecraft.getInstance().font, text, 0 - font.width(text) / 2, 5, -16777216, false);
            } else {
               border.render(graphics, 1.0, 2.0);
            }

            pose.translate(stepWidth, 0.0, 0.0);
         }

         pose.popPose();
      }

      @Override
      public void flowX(int width, int preferred) {
         super.flowX(width, preferred);
         GuiTimeline.this.cachedTimelineWidth = width;
         GuiTimeline.this.updateTickWidth();
      }
   }

   public static class KeySelectedEvent extends GuiControlEvent<GuiTimelineKey> {
      public KeySelectedEvent(GuiTimelineKey source) {
         super(source);
      }

      @Override
      public boolean cancelable() {
         return false;
      }
   }

   public static class NoKeySelectedEvent extends GuiControlEvent<GuiTimeline> {
      public NoKeySelectedEvent(GuiTimeline timeline) {
         super(timeline);
      }

      @Override
      public boolean cancelable() {
         return false;
      }
   }
}
