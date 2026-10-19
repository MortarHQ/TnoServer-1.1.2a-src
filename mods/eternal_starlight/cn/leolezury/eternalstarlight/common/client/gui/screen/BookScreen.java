package cn.leolezury.eternalstarlight.common.client.gui.screen;

import cn.leolezury.eternalstarlight.common.client.book.BookContext;
import cn.leolezury.eternalstarlight.common.client.book.BookDefinition;
import cn.leolezury.eternalstarlight.common.client.book.component.ConfiguredBookComponent;
import cn.leolezury.eternalstarlight.common.client.gui.screen.widget.BookHistoryButton;
import cn.leolezury.eternalstarlight.common.client.gui.screen.widget.BookProgressButton;
import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class BookScreen extends Screen {
   public static final int OVERLAY_Z_OFFSET = 770;
   public static final int BUTTON_Z_OFFSET = 780;
   public static final int TOOLTIP_Z_OFFSET = 790;
   private final BookDefinition book;
   private final BookContext context;
   private final List<List<ConfiguredBookComponent<?, ?>>> unlockedComponents;
   private final List<ConfiguredBookComponent<?, ?>> currentComponents = new ArrayList<>();
   private BookProgressButton upButton;
   private BookProgressButton downButton;
   private BookHistoryButton leftButton;
   private BookHistoryButton rightButton;
   private int tickCount = 0;
   private int totalHeight = 0;
   private int scrollProgress = 0;
   private boolean scrolling = false;
   private int mouseOffset;
   private int mouseX;
   private int mouseY;
   private final List<BookScreen.Stamp> stamps = new ArrayList<>();
   private int currentIndex = 0;

   public BookScreen(BookDefinition book, Set<ResourceLocation> unlocked) {
      super(Component.empty());
      this.book = book;
      this.unlockedComponents = new ArrayList<>();

      for (List<ConfiguredBookComponent<?, ?>> innerList : book.components()) {
         this.unlockedComponents.add(new ArrayList<>(innerList));
      }

      this.unlockedComponents.forEach(list -> list.removeIf(component -> !component.isEnabled(unlocked)));
      this.unlockedComponents.removeIf(List::isEmpty);
      if (!this.unlockedComponents.isEmpty()) {
         this.currentComponents.addAll(this.unlockedComponents.getFirst());
         if (!this.currentComponents.isEmpty()) {
            this.stamps.add(new BookScreen.Stamp(this.currentComponents.getFirst().config().id(), 0));
         }
      }

      this.context = new BookContext() {
         @Override
         public int getMouseX() {
            return BookScreen.this.mouseX;
         }

         @Override
         public int getMouseY() {
            return BookScreen.this.mouseY;
         }

         @Override
         public Font getFont() {
            return BookScreen.this.font;
         }

         @Override
         public BookDefinition getBookDefinition() {
            return book;
         }

         @Override
         public int getContentX() {
            return BookScreen.this.getContentX();
         }

         @Override
         public int getContentY() {
            return BookScreen.this.getContentY();
         }

         @Override
         public int getTickCount() {
            return BookScreen.this.tickCount;
         }

         @Override
         public boolean isComponentEnabled(ResourceLocation id) {
            return BookScreen.this.unlockedComponents.stream().flatMap(Collection::stream).anyMatch(component -> component.config().id().equals(id));
         }

         @Override
         public void jumpToComponent(ResourceLocation id) {
            BookScreen.this.jumpToComponent(id, true);
         }
      };
   }

   protected void init() {
      this.totalHeight = 0;

      for (ConfiguredBookComponent<?, ?> component : this.currentComponents) {
         this.totalHeight = this.totalHeight + component.getTotalHeight(this.context);
      }

      this.upButton = (BookProgressButton)this.addRenderableWidget(
         new BookProgressButton(
            this.getBaseX() + this.book.width() - this.book.buttons().upDownButtonWidth() - this.book.buttons().upDownButtonDistanceFromRight(),
            this.getBaseY() + this.book.buttons().upButtonOffset(),
            this.book,
            false,
            button -> this.setScrollProgress(this.scrollProgress - 9)
         )
      );
      this.downButton = (BookProgressButton)this.addRenderableWidget(
         new BookProgressButton(
            this.getBaseX() + this.book.width() - this.book.buttons().upDownButtonWidth() - this.book.buttons().upDownButtonDistanceFromRight(),
            this.getBaseY() + this.book.buttons().downButtonOffset(),
            this.book,
            true,
            button -> this.setScrollProgress(this.scrollProgress + 9)
         )
      );
      this.leftButton = (BookHistoryButton)this.addRenderableWidget(
         new BookHistoryButton(
            this.getBaseX() + this.book.buttons().leftButtonOffset(),
            this.getBaseY() + this.book.buttons().leftRightButtonDistanceFromTop(),
            this.book,
            true,
            button -> {
               if (this.currentIndex - 1 >= 0 && this.currentIndex - 1 < this.stamps.size()) {
                  BookScreen.Stamp stamp = this.stamps.get(this.currentIndex - 1);
                  this.jumpToComponent(stamp.id(), false);
                  this.setScrollProgress(stamp.progress());
                  this.currentIndex--;
               }
            }
         )
      );
      this.rightButton = (BookHistoryButton)this.addRenderableWidget(
         new BookHistoryButton(
            this.getBaseX() + this.book.buttons().rightButtonOffset(),
            this.getBaseY() + this.book.buttons().leftRightButtonDistanceFromTop(),
            this.book,
            false,
            button -> {
               if (this.currentIndex + 1 >= 0 && this.currentIndex + 1 < this.stamps.size()) {
                  BookScreen.Stamp stamp = this.stamps.get(this.currentIndex + 1);
                  this.jumpToComponent(stamp.id(), false);
                  this.setScrollProgress(stamp.progress());
                  this.currentIndex++;
               }
            }
         )
      );
      this.updateButtonVisibility();
   }

   public void jumpToComponent(ResourceLocation id, boolean leaveStamp) {
      if (this.currentIndex >= 0 && this.currentIndex < this.stamps.size()) {
         this.stamps.set(this.currentIndex, new BookScreen.Stamp(this.stamps.get(this.currentIndex).id(), this.scrollProgress));
      }

      for (List<ConfiguredBookComponent<?, ?>> list : this.unlockedComponents) {
         int currentHeight = 0;

         for (ConfiguredBookComponent<?, ?> component : list) {
            if (component.config().id().equals(id)) {
               this.currentComponents.clear();
               this.currentComponents.addAll(list);
               this.setScrollProgress(currentHeight);
               if (leaveStamp) {
                  List<BookScreen.Stamp> toRemove = new ArrayList<>();

                  for (int i = this.currentIndex + 1; i < this.stamps.size(); i++) {
                     toRemove.add(this.stamps.get(i));
                  }

                  this.stamps.removeIf(toRemove::contains);
                  this.stamps.add(new BookScreen.Stamp(id, this.scrollProgress));
                  this.currentIndex = this.stamps.size() - 1;
               }

               this.rebuildWidgets();
               break;
            }

            currentHeight += component.getTotalHeight(this.context);
         }
      }
   }

   public void mouseMoved(double x, double y) {
      super.mouseMoved(x, y);
      this.mouseX = (int)x;
      this.mouseY = (int)y;
   }

   public boolean mouseClicked(double x, double y, int button) {
      boolean result = super.mouseClicked(x, y, button);
      if (!result) {
         if (x >= this.getContentX()
            && x <= this.getContentX() + this.book.width() - 2 * this.book.frameWidth()
            && y >= this.getContentY()
            && y <= this.getContentY() + this.book.height() - 2 * this.book.frameWidth()) {
            int startHeight = 0;

            for (ConfiguredBookComponent<?, ?> component : new ArrayList<>(this.currentComponents)) {
               int currentY = this.getContentY() - this.scrollProgress + startHeight;
               if (y >= currentY && y <= currentY + component.getTotalHeight(this.context)) {
                  component.onClick(this.context, this.getContentX(), this.getContentY() - this.scrollProgress + startHeight);
                  break;
               }

               startHeight += component.getTotalHeight(this.context);
            }
         }

         boolean scrollArea = x >= this.getBaseX() + this.book.scrollbar().scrollbarXOffset()
            && x <= this.getBaseX() + this.book.scrollbar().scrollbarXOffset() + this.book.scrollbar().scrollbarWidth()
            && y >= this.getBaseY() + this.book.scrollbar().scrollbarYOffset()
            && y <= this.getBaseY() + this.book.scrollbar().scrollbarYOffset() + this.book.scrollbar().scrollbarHeight();
         if (scrollArea && button == 0) {
            this.scrolling = true;
            this.mouseOffset = Mth.clamp(
               (int)(
                  y
                     - (
                        this.getBaseY()
                           + this.book.scrollbar().scrollbarYOffset()
                           + (int)(this.getMaxScroll() * ((double)this.scrollProgress / (this.totalHeight - this.book.height() + 2 * this.book.frameWidth())))
                     )
               ),
               0,
               this.getScrollButtonHeight()
            );
            return true;
         }
      }

      return result;
   }

   public boolean mouseReleased(double x, double y, int button) {
      if (button == 0) {
         this.scrolling = false;
         this.mouseOffset = 0;
      }

      return super.mouseReleased(x, y, button);
   }

   public boolean mouseDragged(double x, double y, int button, double dragX, double dragY) {
      if (this.scrolling) {
         this.setScrollProgress(
            (int)(
               (y - this.mouseOffset - this.getBaseY() - this.book.scrollbar().scrollbarYOffset())
                  * (this.totalHeight - this.book.height() + 2 * this.book.frameWidth())
                  / this.getMaxScroll()
            )
         );
         return true;
      } else {
         return false;
      }
   }

   public boolean mouseScrolled(double x, double y, double scrollX, double scrollY) {
      this.setScrollProgress(this.scrollProgress - (int)(scrollY * 9.0));
      return true;
   }

   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      boolean up = keyCode == 265;
      boolean down = keyCode == 264;
      if (up || down) {
         this.setScrollProgress(this.scrollProgress + (up ? -1 : 1) * 9);
      }

      return super.keyPressed(keyCode, scanCode, modifiers);
   }

   public void tick() {
      this.tickCount++;
      int startHeight = 0;

      for (ConfiguredBookComponent<?, ?> component : new ArrayList<>(this.currentComponents)) {
         component.tick(this.context, this.getContentX(), this.getContentY() - this.scrollProgress + startHeight);
         startHeight += component.getTotalHeight(this.context);
      }

      if (this.stamps.isEmpty()) {
         this.currentIndex = 0;
      } else {
         this.currentIndex = Mth.clamp(this.currentIndex, 0, this.stamps.size() - 1);
      }

      this.updateButtonVisibility();
   }

   public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
      super.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
      guiGraphics.blit(
         this.book.textures().background(),
         this.getBaseX(),
         this.getBaseY(),
         0.0F,
         0.0F,
         this.book.width(),
         this.book.height(),
         this.book.width(),
         this.book.height()
      );
      guiGraphics.enableScissor(
         this.getContentX(),
         this.getContentY(),
         this.getContentX() + this.book.width() - 2 * this.book.frameWidth(),
         this.getContentY() + this.book.height() - 2 * this.book.frameWidth()
      );
      int startHeight = 0;

      for (ConfiguredBookComponent<?, ?> component : new ArrayList<>(this.currentComponents)) {
         if (this.getContentY() - this.scrollProgress + startHeight < this.getContentY() + this.book.height() - 2 * this.book.frameWidth()
            && this.getContentY() - this.scrollProgress + startHeight + component.getTotalHeight(this.context) > this.getContentY()) {
            component.render(this.context, guiGraphics, this.getContentX(), this.getContentY() - this.scrollProgress + startHeight);
         }

         startHeight += component.getTotalHeight(this.context);
      }

      guiGraphics.pose().pushPose();
      guiGraphics.pose().translate(0.0, 0.0, 770.0);
      guiGraphics.disableScissor();
      RenderSystem.enableBlend();
      RenderSystem.enableDepthTest();
      RenderSystem.blendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ONE_MINUS_SRC_ALPHA);
      guiGraphics.blit(
         this.book.textures().overlay(),
         this.getBaseX(),
         this.getBaseY(),
         0.0F,
         0.0F,
         this.book.width(),
         this.book.height(),
         this.book.width(),
         this.book.height()
      );
      RenderSystem.disableBlend();
      RenderSystem.disableDepthTest();
      RenderSystem.defaultBlendFunc();
      guiGraphics.pose().popPose();
      guiGraphics.blit(
         this.book.textures().topOverlay(),
         this.getBaseX(),
         this.getBaseY(),
         0.0F,
         0.0F,
         this.book.width(),
         this.book.height(),
         this.book.width(),
         this.book.height()
      );
      if (this.getScrollButtonHeight() < this.book.scrollbar().scrollbarHeight()) {
         int scrollButtonX = this.getBaseX()
            + this.book.scrollbar().scrollbarXOffset()
            + (this.book.scrollbar().scrollbarWidth() - this.book.scrollbar().scrollButtonWidth()) / 2;
         int scrollButtonY = this.getBaseY()
            + this.book.scrollbar().scrollbarYOffset()
            + (int)(this.getMaxScroll() * ((double)this.scrollProgress / (this.totalHeight - this.book.height() + 2 * this.book.frameWidth())));
         guiGraphics.pose().pushPose();
         guiGraphics.pose().translate(0.0, 0.0, 780.0);
         guiGraphics.fill(
            scrollButtonX,
            scrollButtonY,
            scrollButtonX + this.book.scrollbar().scrollButtonWidth(),
            scrollButtonY + this.getScrollButtonHeight(),
            this.book.scrollbar().scrollButtonColor()
         );
         guiGraphics.pose().popPose();
      }
   }

   public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
      super.render(guiGraphics, mouseX, mouseY, partialTick);
      int startHeight = 0;

      for (ConfiguredBookComponent<?, ?> component : new ArrayList<>(this.currentComponents)) {
         if (this.getContentY() - this.scrollProgress + startHeight < this.getContentY() + this.book.height() - 2 * this.book.frameWidth()
            && this.getContentY() - this.scrollProgress + startHeight + component.getTotalHeight(this.context) > this.getContentY()) {
            component.renderDelayed(this.context, guiGraphics, this.getContentX(), this.getContentY() - this.scrollProgress + startHeight);
         }

         startHeight += component.getTotalHeight(this.context);
      }
   }

   public boolean isPauseScreen() {
      return false;
   }

   private void updateButtonVisibility() {
      this.upButton.visible = this.scrollProgress > 0;
      this.downButton.visible = this.scrollProgress < this.totalHeight - this.book.height() + 2 * this.book.frameWidth();
      if (this.totalHeight <= this.book.height() - 2 * this.book.frameWidth()) {
         this.upButton.visible = false;
         this.downButton.visible = false;
      }

      this.leftButton.visible = this.currentIndex - 1 >= 0 && this.currentIndex - 1 < this.stamps.size();
      this.rightButton.visible = this.currentIndex + 1 >= 0 && this.currentIndex + 1 < this.stamps.size();
   }

   private int getBaseX() {
      return (this.width - this.book.width()) / 2;
   }

   private int getContentX() {
      return this.getBaseX() + this.book.frameWidth();
   }

   private int getBaseY() {
      return (this.height - this.book.height()) / 2;
   }

   private int getContentY() {
      return this.getBaseY() + this.book.frameWidth();
   }

   private void setScrollProgress(int progress) {
      this.scrollProgress = Mth.clamp(progress, 0, Math.max(this.totalHeight - this.book.height() + 2 * this.book.frameWidth(), 0));
   }

   private int getMaxScroll() {
      return this.book.scrollbar().scrollbarHeight() - this.getScrollButtonHeight();
   }

   private int getScrollButtonHeight() {
      return Math.clamp(
         (int)((double)this.book.scrollbar().scrollbarHeight() * (this.book.height() - 2 * this.book.frameWidth()) / this.totalHeight),
         this.book.scrollbar().scrollButtonWidth(),
         this.book.scrollbar().scrollbarHeight()
      );
   }

   private record Stamp(ResourceLocation id, int progress) {
   }
}
