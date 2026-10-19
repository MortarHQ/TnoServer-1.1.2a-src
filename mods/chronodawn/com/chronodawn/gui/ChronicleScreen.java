package com.chronodawn.gui;

import com.chronodawn.gui.data.ChronicleData;
import com.chronodawn.gui.data.Entry;
import com.chronodawn.gui.widgets.CategoryListWidget;
import com.chronodawn.gui.widgets.EntryPageWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ChronicleScreen extends Screen {
   private static final ResourceLocation CORNER_TOP_LEFT = ResourceLocation.fromNamespaceAndPath("chronodawn", "textures/gui/chronicle/corner_top_left.png");
   private static final ResourceLocation CORNER_TOP_RIGHT = ResourceLocation.fromNamespaceAndPath("chronodawn", "textures/gui/chronicle/corner_top_right.png");
   private static final ResourceLocation CORNER_BOTTOM_LEFT = ResourceLocation.fromNamespaceAndPath(
      "chronodawn", "textures/gui/chronicle/corner_bottom_left.png"
   );
   private static final ResourceLocation CORNER_BOTTOM_RIGHT = ResourceLocation.fromNamespaceAndPath(
      "chronodawn", "textures/gui/chronicle/corner_bottom_right.png"
   );
   private static final ResourceLocation EDGE_TOP = ResourceLocation.fromNamespaceAndPath("chronodawn", "textures/gui/chronicle/edge_top.png");
   private static final ResourceLocation EDGE_BOTTOM = ResourceLocation.fromNamespaceAndPath("chronodawn", "textures/gui/chronicle/edge_bottom.png");
   private static final ResourceLocation EDGE_LEFT = ResourceLocation.fromNamespaceAndPath("chronodawn", "textures/gui/chronicle/edge_left.png");
   private static final ResourceLocation EDGE_RIGHT = ResourceLocation.fromNamespaceAndPath("chronodawn", "textures/gui/chronicle/edge_right.png");
   private static final ResourceLocation PAGE_BACKGROUND = ResourceLocation.fromNamespaceAndPath("chronodawn", "textures/gui/chronicle/page.png");
   private static final ResourceLocation BINDING_TOP = ResourceLocation.fromNamespaceAndPath("chronodawn", "textures/gui/chronicle/binding_top.png");
   private static final ResourceLocation BINDING_MIDDLE = ResourceLocation.fromNamespaceAndPath("chronodawn", "textures/gui/chronicle/binding_middle.png");
   private static final ResourceLocation BINDING_BOTTOM = ResourceLocation.fromNamespaceAndPath("chronodawn", "textures/gui/chronicle/binding_bottom.png");
   private static final int BOOK_WIDTH = 320;
   private static final int BOOK_HEIGHT = 240;
   private final ChronicleData data = ChronicleData.getInstance();
   private int leftPos;
   private int topPos;
   private int categoryListWidth;
   private CategoryListWidget categoryList;
   private EntryPageWidget entryPage;
   private Entry selectedEntry;

   public ChronicleScreen() {
      super(Component.translatable("gui.chronodawn.chronicle.title"));
   }

   protected void init() {
      super.init();
      this.leftPos = (this.width - 320) / 2;
      this.topPos = (this.height - 240) / 2;
      this.categoryListWidth = 145;
      int categoryListHeight = 220;
      this.categoryList = new CategoryListWidget(
         this.leftPos + 10, this.topPos + 10, this.categoryListWidth, categoryListHeight, this.data.getCategories(), entry -> {
            this.selectedEntry = entry;
            if (this.entryPage != null) {
               this.entryPage.setEntry(this.selectedEntry);
            }
         }
      );
      this.addRenderableWidget(this.categoryList);
      int entryPageX = this.leftPos + this.categoryListWidth + 20;
      int entryPageWidth = 320 - this.categoryListWidth - 30;
      int entryPageHeight = 220;
      this.entryPage = new EntryPageWidget(entryPageX, this.topPos + 10, entryPageWidth, entryPageHeight);
      this.entryPage.initializeButtons(btn -> this.entryPage.previousPage(), btn -> this.entryPage.nextPage());
      this.addRenderableWidget(this.entryPage);
      if (this.entryPage.getPreviousButton() != null) {
         this.addRenderableWidget(this.entryPage.getPreviousButton());
      }

      if (this.entryPage.getNextButton() != null) {
         this.addRenderableWidget(this.entryPage.getNextButton());
      }
   }

   public void renderBackground(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
      graphics.fill(0, 0, this.width, this.topPos, -2146430960);
      graphics.fill(0, this.topPos + 240, this.width, this.height, -2146430960);
      graphics.fill(0, this.topPos, this.leftPos, this.topPos + 240, -2146430960);
      graphics.fill(this.leftPos + 320, this.topPos, this.width, this.topPos + 240, -2146430960);
   }

   public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
      this.renderBackground(graphics, mouseX, mouseY, partialTick);
      int CORNER_SIZE = 16;
      int EDGE_SIZE = 16;
      int BINDING_WIDTH = 16;
      int bindingX = this.leftPos + this.categoryListWidth + 10;
      int innerWidth = 288;
      int innerHeight = 208;
      int leftPageWidth = bindingX - this.leftPos - 16;
      int rightPageWidth = 320 - (bindingX - this.leftPos) - 16 - 16;

      for (int y = 0; y < innerHeight; y += 16) {
         for (int x = 0; x < leftPageWidth; x += 16) {
            int width = Math.min(16, leftPageWidth - x);
            int height = Math.min(16, innerHeight - y);
            graphics.blit(PAGE_BACKGROUND, this.leftPos + 16 + x, this.topPos + 16 + y, 0.0F, 0.0F, width, height, 16, 16);
         }
      }

      for (int y = 0; y < innerHeight; y += 16) {
         for (int x = 0; x < rightPageWidth; x += 16) {
            int width = Math.min(16, rightPageWidth - x);
            int height = Math.min(16, innerHeight - y);
            graphics.blit(PAGE_BACKGROUND, bindingX + 16 + x, this.topPos + 16 + y, 0.0F, 0.0F, width, height, 16, 16);
         }
      }

      graphics.blit(CORNER_TOP_LEFT, this.leftPos, this.topPos, 0.0F, 0.0F, 16, 16, 16, 16);
      graphics.blit(CORNER_TOP_RIGHT, this.leftPos + 320 - 16, this.topPos, 0.0F, 0.0F, 16, 16, 16, 16);
      graphics.blit(CORNER_BOTTOM_LEFT, this.leftPos, this.topPos + 240 - 16, 0.0F, 0.0F, 16, 16, 16, 16);
      graphics.blit(CORNER_BOTTOM_RIGHT, this.leftPos + 320 - 16, this.topPos + 240 - 16, 0.0F, 0.0F, 16, 16, 16, 16);
      graphics.blit(BINDING_TOP, bindingX, this.topPos, 0.0F, 0.0F, 16, 16, 16, 16);
      int bindingMiddleHeight = 208;

      for (int y = 0; y < bindingMiddleHeight; y += 16) {
         int height = Math.min(16, bindingMiddleHeight - y);
         graphics.blit(BINDING_MIDDLE, bindingX, this.topPos + 16 + y, 0.0F, 0.0F, 16, height, 16, 16);
      }

      graphics.blit(BINDING_BOTTOM, bindingX, this.topPos + 240 - 16, 0.0F, 0.0F, 16, 16, 16, 16);

      for (int x = 16; x < bindingX - this.leftPos; x += 16) {
         int width = Math.min(16, bindingX - this.leftPos - x);
         graphics.blit(EDGE_TOP, this.leftPos + x, this.topPos, 0.0F, 0.0F, width, 16, 16, 16);
      }

      for (int x = bindingX - this.leftPos + 16; x < 304; x += 16) {
         int width = Math.min(16, 304 - x);
         graphics.blit(EDGE_TOP, this.leftPos + x, this.topPos, 0.0F, 0.0F, width, 16, 16, 16);
      }

      for (int x = 16; x < bindingX - this.leftPos; x += 16) {
         int width = Math.min(16, bindingX - this.leftPos - x);
         graphics.blit(EDGE_BOTTOM, this.leftPos + x, this.topPos + 240 - 16, 0.0F, 0.0F, width, 16, 16, 16);
      }

      for (int x = bindingX - this.leftPos + 16; x < 304; x += 16) {
         int width = Math.min(16, 304 - x);
         graphics.blit(EDGE_BOTTOM, this.leftPos + x, this.topPos + 240 - 16, 0.0F, 0.0F, width, 16, 16, 16);
      }

      for (int y = 16; y < 224; y += 16) {
         int height = Math.min(16, 224 - y);
         graphics.blit(EDGE_LEFT, this.leftPos, this.topPos + y, 0.0F, 0.0F, 16, height, 16, 16);
      }

      for (int y = 16; y < 224; y += 16) {
         int height = Math.min(16, 224 - y);
         graphics.blit(EDGE_RIGHT, this.leftPos + 320 - 16, this.topPos + y, 0.0F, 0.0F, 16, height, 16, 16);
      }

      super.render(graphics, mouseX, mouseY, partialTick);
      if (this.categoryList != null) {
         String tooltip = this.categoryList.getHoveredTooltip();
         if (tooltip != null) {
            graphics.renderTooltip(this.font, Component.literal(tooltip), mouseX, mouseY);
         }
      }
   }

   public boolean isPauseScreen() {
      return false;
   }

   public static String getLanguageCode() {
      return Minecraft.getInstance().options.languageCode;
   }
}
