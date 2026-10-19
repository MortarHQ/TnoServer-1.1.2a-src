package com.chronodawn.gui.widgets;

import com.chronodawn.compat.CompatResourceLocation;
import com.chronodawn.gui.ChronicleScreen;
import com.chronodawn.gui.data.Entry;
import com.chronodawn.gui.data.Page;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.vertex.PoseStack;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.components.Button.OnPress;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

public class EntryPageWidget extends AbstractWidget {
   private static final ResourceLocation BOOK_TEXTURE = CompatResourceLocation.create("minecraft", "textures/gui/book.png");
   private static final WidgetSprites PAGE_FORWARD_SPRITES = new WidgetSprites(
      CompatResourceLocation.create("minecraft", "widget/page_forward"), CompatResourceLocation.create("minecraft", "widget/page_forward_highlighted")
   );
   private static final WidgetSprites PAGE_BACKWARD_SPRITES = new WidgetSprites(
      CompatResourceLocation.create("minecraft", "widget/page_backward"), CompatResourceLocation.create("minecraft", "widget/page_backward_highlighted")
   );
   private Entry currentEntry;
   private int currentPageIndex = 0;
   private List<EntryPageWidget.VirtualPage> virtualPages = new ArrayList<>();
   private Button previousButton;
   private Button nextButton;
   private Component pageNumberText;
   private static final int TEXT_MARGIN = 5;
   private static final int LINE_HEIGHT = 10;

   public EntryPageWidget(int x, int y, int width, int height) {
      super(x, y, width, height, Component.empty());
   }

   public void setEntry(Entry entry) {
      this.currentEntry = entry;
      this.currentPageIndex = 0;
      this.virtualPages.clear();
      if (entry != null) {
         int maxTextHeight = this.height - 40;
         int maxLinesPerPage = maxTextHeight / 10;
         Font font = Minecraft.getInstance().font;
         int maxLineWidth = this.width - 10;

         for (Page page : entry.getPages()) {
            if (page.getType() == Page.PageType.TEXT) {
               String languageCode = ChronicleScreen.getLanguageCode();
               String text = page.getText().get(languageCode);
               List<String> allLines = this.wrapText(text, maxLineWidth, font);

               for (int i = 0; i < allLines.size(); i += maxLinesPerPage) {
                  int endIndex = Math.min(i + maxLinesPerPage, allLines.size());
                  List<String> pageLines = allLines.subList(i, endIndex);
                  this.virtualPages.add(new EntryPageWidget.VirtualPage(new ArrayList<>(pageLines)));
               }
            } else if (page.getType() == Page.PageType.RECIPE) {
               this.virtualPages.add(new EntryPageWidget.VirtualPage(page.getRecipe()));
            } else if (page.getType() == Page.PageType.IMAGE) {
               this.virtualPages.add(new EntryPageWidget.VirtualPage(Page.PageType.IMAGE, page.getImage()));
            }
         }
      }

      this.updatePageButtons();
   }

   public void initializeButtons(OnPress previousAction, OnPress nextAction) {
      int buttonY = this.getY() + this.height - 15;
      this.previousButton = new EntryPageWidget.SilentImageButton(this.getX() + 5, buttonY, 23, 13, PAGE_BACKWARD_SPRITES, previousAction);
      this.nextButton = new EntryPageWidget.SilentImageButton(this.getX() + this.width - 28, buttonY, 23, 13, PAGE_FORWARD_SPRITES, nextAction);
      this.updatePageButtons();
   }

   private void updatePageButtons() {
      if (this.previousButton != null && this.nextButton != null) {
         if (this.virtualPages.isEmpty()) {
            this.previousButton.visible = false;
            this.previousButton.active = false;
            this.nextButton.visible = false;
            this.nextButton.active = false;
            this.pageNumberText = Component.empty();
         } else {
            int totalPages = this.virtualPages.size();
            boolean hasPrevious = this.currentPageIndex > 0;
            boolean hasNext = this.currentPageIndex < totalPages - 1;
            this.previousButton.visible = hasPrevious;
            this.previousButton.active = hasPrevious;
            this.nextButton.visible = hasNext;
            this.nextButton.active = hasNext;
            this.pageNumberText = Component.translatable("gui.chronodawn.chronicle.page", new Object[]{this.currentPageIndex + 1, totalPages});
         }
      }
   }

   public void previousPage() {
      if (!this.virtualPages.isEmpty() && this.currentPageIndex > 0) {
         this.currentPageIndex--;
         this.updatePageButtons();
         Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.BOOK_PAGE_TURN, 1.0F));
      }
   }

   public void nextPage() {
      if (!this.virtualPages.isEmpty() && this.currentPageIndex < this.virtualPages.size() - 1) {
         this.currentPageIndex++;
         this.updatePageButtons();
         Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.BOOK_PAGE_TURN, 1.0F));
      }
   }

   public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
      if (this.virtualPages.isEmpty()) {
         Component placeholderText = Component.translatable("gui.chronodawn.chronicle.select_category");
         Font font = Minecraft.getInstance().font;
         int textX = this.getX() + (this.width - font.width(placeholderText)) / 2;
         int textY = this.getY() + this.height / 2;
         graphics.drawString(font, placeholderText, textX, textY, 8421504, false);
      } else {
         EntryPageWidget.VirtualPage virtualPage = this.virtualPages.get(this.currentPageIndex);
         if (virtualPage.type == Page.PageType.TEXT) {
            this.renderTextVirtualPage(graphics, virtualPage);
         } else if (virtualPage.type == Page.PageType.RECIPE) {
            this.renderRecipeVirtualPage(graphics, virtualPage);
         } else if (virtualPage.type == Page.PageType.IMAGE) {
            this.renderImageVirtualPage(graphics, virtualPage);
         }

         if (this.pageNumberText != null) {
            Font font = Minecraft.getInstance().font;
            int textX = this.getX() + (this.width - font.width(this.pageNumberText)) / 2;
            int textY = this.getY() + this.height - 15 + 2;
            graphics.drawString(font, this.pageNumberText, textX, textY, 4144959, false);
         }
      }
   }

   private void renderTextVirtualPage(GuiGraphics graphics, EntryPageWidget.VirtualPage virtualPage) {
      Font font = Minecraft.getInstance().font;
      int textX = this.getX() + 5;
      int textY = this.getY() + 5;

      for (String line : virtualPage.lines) {
         graphics.drawString(font, line, textX, textY, 0, false);
         textY += 10;
      }
   }

   private void renderRecipeVirtualPage(GuiGraphics graphics, EntryPageWidget.VirtualPage virtualPage) {
      ResourceLocation recipeId = virtualPage.recipe;
      RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();
      Optional<RecipeHolder<?>> recipeHolder = recipeManager.byKey(recipeId);
      if (!recipeHolder.isEmpty()) {
         Recipe<?> recipe = recipeHolder.get().value();
         ItemStack result = recipe.getResultItem(Minecraft.getInstance().level.registryAccess());
         int iconX = this.getX() + this.width / 2 - 8;
         int iconY = this.getY() + 20;
         graphics.renderItem(result, iconX, iconY);
         Font font = Minecraft.getInstance().font;
         String itemName = result.getHoverName().getString();
         int textX = this.getX() + (this.width - font.width(itemName)) / 2;
         int textY = iconY + 20;
         graphics.drawString(font, itemName, textX, textY, 0, false);
      } else {
         Font font = Minecraft.getInstance().font;
         String errorText = "Recipe not found: " + recipeId;
         int maxLineWidth = this.width - 10;
         List<String> lines = this.wrapText(errorText, maxLineWidth, font);
         int textX = this.getX() + 5;
         int textY = this.getY() + 5;

         for (String line : lines) {
            graphics.drawString(font, line, textX, textY, 16711680, false);
            textY += 10;
         }
      }
   }

   private void renderImageVirtualPage(GuiGraphics graphics, EntryPageWidget.VirtualPage virtualPage) {
      ResourceLocation imageLocation = virtualPage.image;

      try {
         int availableWidth = this.width - 10;
         int availableHeight = this.height - 50;
         int[] dimensions = this.getImageDimensions(imageLocation);
         if (dimensions == null) {
            throw new Exception("Could not load image dimensions");
         }

         int imageWidth = dimensions[0];
         int imageHeight = dimensions[1];
         float scaleX = (float)availableWidth / imageWidth;
         float scaleY = (float)availableHeight / imageHeight;
         float scale = Math.min(scaleX, scaleY);
         int renderWidth = (int)(imageWidth * scale);
         int renderHeight = (int)(imageHeight * scale);
         int renderX = this.getX() + (this.width - renderWidth) / 2;
         int renderY = this.getY() + (this.height - 40 - renderHeight) / 2;
         graphics.setColor(0.94F, 0.88F, 0.82F, 1.0F);
         PoseStack poseStack = graphics.pose();
         poseStack.pushPose();
         poseStack.translate(renderX, renderY, 0.0F);
         poseStack.scale(scale, scale, 1.0F);
         graphics.blit(imageLocation, 0, 0, 0.0F, 0.0F, imageWidth, imageHeight, imageWidth, imageHeight);
         poseStack.popPose();
         graphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
         this.renderVignetteEffect(graphics, renderX, renderY, renderWidth, renderHeight);
      } catch (Exception e) {
         Font font = Minecraft.getInstance().font;
         String errorText = "Failed to load image: " + imageLocation;
         int maxLineWidth = this.width - 10;
         List<String> lines = this.wrapText(errorText, maxLineWidth, font);
         int textX = this.getX() + 5;
         int textY = this.getY() + 5;

         for (String line : lines) {
            graphics.drawString(font, line, textX, textY, 16711680, false);
            textY += 10;
         }
      }
   }

   private void renderVignetteEffect(GuiGraphics graphics, int x, int y, int width, int height) {
      int bookBgColor = 15786192;
      int r = bookBgColor >> 16 & 0xFF;
      int g = bookBgColor >> 8 & 0xFF;
      int b = bookBgColor & 0xFF;
      int fadeDistance = 15;

      for (int i = 0; i <= fadeDistance; i++) {
         int alpha = (int)(255.0F * (1.0F - (float)i / fadeDistance));
         int color = alpha << 24 | r << 16 | g << 8 | b;
         graphics.fill(x, y + i, x + width, y + i + 1, color);
      }

      for (int i = 0; i <= fadeDistance; i++) {
         int alpha = (int)(255.0F * (1.0F - (float)i / fadeDistance));
         int color = alpha << 24 | r << 16 | g << 8 | b;
         graphics.fill(x, y + height - i, x + width, y + height - i + 1, color);
      }

      for (int i = 0; i <= fadeDistance; i++) {
         int alpha = (int)(255.0F * (1.0F - (float)i / fadeDistance));
         int color = alpha << 24 | r << 16 | g << 8 | b;
         graphics.fill(x + i, y, x + i + 1, y + height, color);
      }

      for (int i = 0; i <= fadeDistance; i++) {
         int alpha = (int)(255.0F * (1.0F - (float)i / fadeDistance));
         int color = alpha << 24 | r << 16 | g << 8 | b;
         graphics.fill(x + width - i, y, x + width - i + 1, y + height, color);
      }

      for (int i = 0; i <= fadeDistance; i++) {
         for (int j = 0; j <= fadeDistance; j++) {
            float distance = (float)Math.sqrt(i * i + j * j);
            if (distance <= fadeDistance) {
               int alpha = (int)(255.0F * (1.0F - distance / fadeDistance));
               int color = alpha << 24 | r << 16 | g << 8 | b;
               graphics.fill(x + i, y + j, x + i + 1, y + j + 1, color);
            }
         }
      }

      for (int i = 0; i <= fadeDistance; i++) {
         for (int j = 0; j <= fadeDistance; j++) {
            float distance = (float)Math.sqrt(i * i + j * j);
            if (distance <= fadeDistance) {
               int alpha = (int)(255.0F * (1.0F - distance / fadeDistance));
               int color = alpha << 24 | r << 16 | g << 8 | b;
               graphics.fill(x + width - 1 - i, y + j, x + width - i, y + j + 1, color);
            }
         }
      }

      for (int i = 0; i <= fadeDistance; i++) {
         for (int j = 0; j <= fadeDistance; j++) {
            float distance = (float)Math.sqrt(i * i + j * j);
            if (distance <= fadeDistance) {
               int alpha = (int)(255.0F * (1.0F - distance / fadeDistance));
               int color = alpha << 24 | r << 16 | g << 8 | b;
               graphics.fill(x + i, y + height - 1 - j, x + i + 1, y + height - j, color);
            }
         }
      }

      for (int i = 0; i <= fadeDistance; i++) {
         for (int j = 0; j <= fadeDistance; j++) {
            float distance = (float)Math.sqrt(i * i + j * j);
            if (distance <= fadeDistance) {
               int alpha = (int)(255.0F * (1.0F - distance / fadeDistance));
               int color = alpha << 24 | r << 16 | g << 8 | b;
               graphics.fill(x + width - 1 - i, y + height - 1 - j, x + width - i, y + height - j, color);
            }
         }
      }
   }

   private int[] getImageDimensions(ResourceLocation imageLocation) {
      try {
         ResourceManager resourceManager = Minecraft.getInstance().getResourceManager();
         ResourceLocation resourcePath = CompatResourceLocation.create(imageLocation.getNamespace(), imageLocation.getPath());

         try (InputStream stream = resourceManager.open(resourcePath)) {
            NativeImage image = NativeImage.read(stream);
            int width = image.getWidth();
            int height = image.getHeight();
            image.close();
            return new int[]{width, height};
         }
      } catch (IOException e) {
         return null;
      }
   }

   private List<String> wrapText(String text, int maxWidth, Font font) {
      List<String> lines = new ArrayList<>();
      String[] paragraphs = text.split("\n");

      for (String paragraph : paragraphs) {
         if (paragraph.isEmpty()) {
            lines.add("");
         } else {
            StringBuilder currentLine = new StringBuilder();

            for (int i = 0; i < paragraph.length(); i++) {
               char c = paragraph.charAt(i);
               String testLine = currentLine.toString() + c;
               if (font.width(testLine) <= maxWidth) {
                  currentLine.append(c);
               } else if (currentLine.length() > 0) {
                  lines.add(currentLine.toString());
                  currentLine = new StringBuilder();
                  currentLine.append(c);
               } else {
                  lines.add(String.valueOf(c));
               }
            }

            if (currentLine.length() > 0) {
               lines.add(currentLine.toString());
            }
         }
      }

      return lines;
   }

   public Button getPreviousButton() {
      return this.previousButton;
   }

   public Button getNextButton() {
      return this.nextButton;
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (this.previousButton != null && this.previousButton.mouseClicked(mouseX, mouseY, button)) {
         return true;
      } else {
         return this.nextButton != null && this.nextButton.mouseClicked(mouseX, mouseY, button) ? true : super.mouseClicked(mouseX, mouseY, button);
      }
   }

   public void playDownSound(SoundManager soundManager) {
   }

   protected void updateWidgetNarration(NarrationElementOutput output) {
      if (this.currentEntry != null) {
         String languageCode = ChronicleScreen.getLanguageCode();
         output.add(NarratedElementType.TITLE, Component.literal(this.currentEntry.getTitle().get(languageCode)));
      }
   }

   private static class SilentImageButton extends ImageButton {
      public SilentImageButton(int x, int y, int width, int height, WidgetSprites sprites, OnPress onPress) {
         super(x, y, width, height, sprites, onPress);
      }

      public void playDownSound(SoundManager soundManager) {
      }
   }

   private static class VirtualPage {
      final Page.PageType type;
      final List<String> lines;
      final ResourceLocation recipe;
      final ResourceLocation image;

      VirtualPage(List<String> lines) {
         this.type = Page.PageType.TEXT;
         this.lines = lines;
         this.recipe = null;
         this.image = null;
      }

      VirtualPage(ResourceLocation recipe) {
         this.type = Page.PageType.RECIPE;
         this.lines = null;
         this.recipe = recipe;
         this.image = null;
      }

      VirtualPage(Page.PageType type, ResourceLocation image) {
         this.type = type;
         this.lines = null;
         this.recipe = null;
         this.image = image;
      }
   }
}
