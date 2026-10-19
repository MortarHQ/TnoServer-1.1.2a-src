package com.tom.storagemod.screen.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.lwjgl.glfw.GLFW;

public abstract class ListWidget<T> extends AbstractWidget {
   protected static final WidgetSprites LIST_BUTTON_SPRITES = new WidgetSprites(
      ResourceLocation.tryBuild("toms_storage", "widget/small_button"),
      ResourceLocation.tryBuild("toms_storage", "widget/small_button_disabled"),
      ResourceLocation.tryBuild("toms_storage", "widget/small_button_hovered")
   );
   protected static final WidgetSprites LIST_BUTTON_SPRITES_S = new WidgetSprites(
      ResourceLocation.tryBuild("toms_storage", "widget/small_button_selected"),
      ResourceLocation.tryBuild("toms_storage", "widget/small_button_disabled"),
      ResourceLocation.tryBuild("toms_storage", "widget/small_button_selected_hovered")
   );
   protected static final WidgetSprites SCROLL_SPRITES = new WidgetSprites(
      ResourceLocation.tryBuild("toms_storage", "widget/small_scroll"),
      ResourceLocation.tryBuild("toms_storage", "widget/small_scroll_disabled"),
      ResourceLocation.tryBuild("toms_storage", "widget/small_scroll_hovered")
   );
   private final int elemH;
   protected float currentScroll;
   protected boolean isScrolling;
   protected boolean wasClicking;
   protected Supplier<List<T>> list;
   protected T selected;
   private List<ListWidget<T>.ListEntry> listEntries = new ArrayList<>();

   public ListWidget(int x, int y, int w, int h, int elemH, Component narrator) {
      super(x, y, w, h, narrator);
      this.elemH = elemH;

      for (int i = 0; i < this.getLines(); i++) {
         this.listEntries.add(new ListWidget.ListEntry(x, y + i * elemH, i));
      }
   }

   public void preRender(int mouseX, int mouseY) {
      boolean flag = GLFW.glfwGetMouseButton(Minecraft.getInstance().getWindow().getWindow(), 0) != 0;
      int k = this.getX() + 56;
      int l = this.getY() - 1;
      int i1 = k + 14;
      int j1 = l + 58;
      if (!this.wasClicking && flag && mouseX >= k && mouseY >= l && mouseX < i1 && mouseY < j1) {
         this.isScrolling = this.needsScrollBars();
      }

      if (!flag) {
         this.isScrolling = false;
      }

      this.wasClicking = flag;
      if (this.isScrolling) {
         this.currentScroll = (mouseY - l - 4.5F) / (j1 - l - 9.0F);
         this.currentScroll = Mth.clamp(this.currentScroll, 0.0F, 1.0F);
      }
   }

   protected void renderWidget(GuiGraphics st, int mouseX, int mouseY, float p_268085_) {
      int x = this.getX() + this.getWidth() - 6;
      int y = this.getY() - 1 + (int)((this.getHeight() - 9) * this.currentScroll);
      boolean isHovered = mouseX >= x && mouseY >= y && mouseX < x + 5 && mouseY < y + 9;
      st.blitSprite(SCROLL_SPRITES.get(this.needsScrollBars(), isHovered), x, y, 5, 9);
   }

   public void tooltip(GuiGraphics matrixStack, int mouseX, int mouseY) {
      this.listEntries.stream().filter(s -> s.isHovered()).findFirst().ifPresent(le -> {
         T id = (T)le.getId();
         if (id != null) {
            this.renderTooltip(matrixStack, id, mouseX, mouseY);
         }
      });
   }

   protected void updateWidgetNarration(NarrationElementOutput p_259858_) {
   }

   public boolean mouseScrolled(double p_94734_, double p_94735_, double p_94736_, double dir) {
      return this.scroll(dir);
   }

   private boolean scroll(double dir) {
      if (!this.needsScrollBars()) {
         return false;
      }

      int i = this.list.get().size() - this.getLines();
      this.currentScroll = (float)(this.currentScroll - dir / i);
      this.currentScroll = Mth.clamp(this.currentScroll, 0.0F, 1.0F);
      return true;
   }

   public boolean needsScrollBars() {
      return this.list.get().size() > this.getLines();
   }

   public int getLines() {
      return this.height / this.elemH;
   }

   public void setList(Supplier<List<T>> list) {
      this.list = list;
   }

   public void setSelected(T selected) {
      this.selected = selected;
   }

   public T getSelected() {
      return this.selected;
   }

   public void setCurrentScroll(float currentScroll) {
      this.currentScroll = currentScroll;
   }

   protected abstract Font getFont();

   protected abstract void addButton(AbstractWidget var1);

   protected abstract Component toComponent(T var1);

   protected abstract void renderTooltip(GuiGraphics var1, T var2, int var3, int var4);

   protected void selectionChanged(T to) {
   }

   protected void renderEntry(GuiGraphics st, int x2, int y2, int width, int height, T id, int mouseX, int mouseY, float pt) {
   }

   public class ListEntry extends Button {
      private int id;

      public ListEntry(int x, int y, int id) {
         super(x, y, ListWidget.this.width - 8, ListWidget.this.elemH, Component.empty(), null, DEFAULT_NARRATION);
         this.id = id;
         ListWidget.this.addButton(this);
      }

      public void renderWidget(GuiGraphics st, int mouseX, int mouseY, float pt) {
         if (this.visible) {
            T id = (T)this.getId();
            if (id != null) {
               int x = this.getX();
               int y = this.getY();
               st.setColor(1.0F, 1.0F, 1.0F, this.alpha);
               RenderSystem.enableBlend();
               RenderSystem.enableDepthTest();
               this.isHovered = mouseX >= x && mouseY >= y && mouseX < x + this.width && mouseY < y + this.height;
               ResourceLocation spr = (id.equals(ListWidget.this.selected) ? ListWidget.LIST_BUTTON_SPRITES_S : ListWidget.LIST_BUTTON_SPRITES)
                  .get(this.active, this.isHoveredOrFocused());
               st.blitSprite(spr, this.getX(), this.getY(), this.getWidth(), this.getHeight());
               ListWidget.this.renderEntry(st, this.getX(), this.getY(), this.getWidth(), this.getHeight(), id, mouseX, mouseY, pt);
               int c = this.active ? 16777215 : 10526880;
               int v = c | Mth.ceil(this.alpha * 255.0F) << 24;
               int k = this.getX() + 2;
               int l = this.getX() + this.getWidth() - 2;
               AbstractWidget.renderScrollingString(
                  st, ListWidget.this.getFont(), ListWidget.this.toComponent(id), k, this.getY(), l, this.getY() + this.getHeight(), v
               );
            }
         }
      }

      public boolean mouseScrolled(double p_94734_, double p_94735_, double p_94736_, double dir) {
         return ListWidget.this.scroll(dir);
      }

      public void onPress() {
         T id = (T)this.getId();
         if (id != null) {
            ListWidget.this.selected = id;
            ListWidget.this.selectionChanged(id);
         }
      }

      private T getId() {
         List<T> l = ListWidget.this.list.get();
         int i = l.size() - ListWidget.this.getLines();
         int j = (int)(ListWidget.this.currentScroll * i + 0.5);
         if (j < 0) {
            j = 0;
         }

         return this.id + j < l.size() ? l.get(this.id + j) : null;
      }
   }
}
