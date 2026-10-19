package dev.ftb.mods.ftbchunks.client.gui;

import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.JsonOps;
import dev.architectury.platform.Platform;
import dev.ftb.mods.ftblibrary.icon.Color4I;
import dev.ftb.mods.ftblibrary.icon.EntityIconLoader;
import dev.ftb.mods.ftblibrary.icon.EntityImageIcon;
import dev.ftb.mods.ftblibrary.icon.Icons;
import dev.ftb.mods.ftblibrary.icon.ImageIcon;
import dev.ftb.mods.ftblibrary.icon.EntityIconLoader.EntityIconSettings;
import dev.ftb.mods.ftblibrary.icon.EntityIconLoader.WidthHeight;
import dev.ftb.mods.ftblibrary.icon.EntityImageIcon.ChildIconData;
import dev.ftb.mods.ftblibrary.icon.EntityImageIcon.Offset;
import dev.ftb.mods.ftblibrary.icon.EntityImageIcon.Slice;
import dev.ftb.mods.ftblibrary.ui.BaseScreen;
import dev.ftb.mods.ftblibrary.ui.Button;
import dev.ftb.mods.ftblibrary.ui.IntTextBox;
import dev.ftb.mods.ftblibrary.ui.Panel;
import dev.ftb.mods.ftblibrary.ui.SimpleButton;
import dev.ftb.mods.ftblibrary.ui.Theme;
import dev.ftb.mods.ftblibrary.ui.ToggleableButton;
import dev.ftb.mods.ftblibrary.ui.misc.SimpleToast;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.texture.SimpleTexture.TextureImage;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import org.slf4j.Logger;

public class SliceCreationGUI extends BaseScreen {
   private static final Logger LOGGER = LogUtils.getLogger();
   private final EntityType<?> entityType;
   private final int textureWidth;
   private final int textureHeight;
   private final ResourceLocation texture;
   private final SliceCreationGUI.SliceControlBox sliceControlBox;
   private final SimpleButton exportButton;
   private final IntTextBox imageSizeX;
   private final IntTextBox imageSizeY;
   private final List<SliceCreationGUI.SliceControlBox> sliceControlBoxes;
   private int currentSlice = 0;
   private SliceCreationGUI.SliceControlBox activeSlice;
   private final SimpleButton addSlice;
   private final SimpleButton removeSlice;
   private final SimpleButton nextSlice;
   private final SimpleButton previousSlice;

   public SliceCreationGUI(EntityType<?> entityType) {
      this.entityType = entityType;
      this.sliceControlBoxes = new ArrayList<>();
      this.imageSizeX = new IntTextBox(this);
      this.imageSizeY = new IntTextBox(this);
      this.imageSizeX.setAmount(16);
      this.imageSizeY.setAmount(16);
      Entity entity = entityType.create(Minecraft.getInstance().level);
      this.texture = Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(entity).getTextureLocation(entity);
      this.exportButton = this.createExportButton();
      Pair<Integer, Integer> textureSize = this.getTextureSize();
      this.textureWidth = (Integer)textureSize.getFirst();
      this.textureHeight = (Integer)textureSize.getSecond();
      this.sliceControlBox = new SliceCreationGUI.SliceControlBox(this, this.texture, this.textureWidth, this.textureHeight, false);
      this.addSlice = new SimpleButton(
         this,
         Component.literal("Add Slice"),
         Icons.ADD,
         (button1, mouseButton1) -> {
            SliceCreationGUI.SliceControlBox sliceControlBox = new SliceCreationGUI.SliceControlBox(
               this, this.texture, this.textureWidth, this.textureHeight, true
            );
            this.sliceControlBoxes.add(sliceControlBox);
            this.currentSlice = this.sliceControlBoxes.size() - 1;
            this.activeSlice = sliceControlBox;
            button1.getGui().refreshWidgets();
         }
      );
      this.removeSlice = new SimpleButton(this, Component.literal("Remove Slice"), Icons.REMOVE, (button1, mouseButton1) -> {
         if (!this.sliceControlBoxes.isEmpty()) {
            this.sliceControlBoxes.remove(this.currentSlice);
            this.currentSlice = Math.max(0, Math.min(this.sliceControlBoxes.size() - 1, this.currentSlice));
            this.activeSlice = this.sliceControlBoxes.isEmpty() ? null : this.sliceControlBoxes.get(this.currentSlice);
            button1.getGui().refreshWidgets();
         }
      });
      this.nextSlice = new SimpleButton(this, Component.literal("Next Slice"), Icons.DOWN, (button1, mouseButton1) -> {
         if (!this.sliceControlBoxes.isEmpty()) {
            this.currentSlice = Math.max(0, Math.min(this.sliceControlBoxes.size() - 1, this.currentSlice + 1));
            this.activeSlice = this.sliceControlBoxes.get(this.currentSlice);
            button1.getGui().refreshWidgets();
         }
      });
      this.previousSlice = new SimpleButton(this, Component.literal("Previous Slice"), Icons.UP, (button1, mouseButton1) -> {
         if (!this.sliceControlBoxes.isEmpty()) {
            this.currentSlice = Math.max(0, Math.min(this.sliceControlBoxes.size() - 1, this.currentSlice - 1));
            this.activeSlice = this.sliceControlBoxes.get(this.currentSlice);
            button1.getGui().refreshWidgets();
         }
      });
      this.loadExistingSettings();
   }

   public void addWidgets() {
      this.activeSlice = null;
      this.add(this.sliceControlBox);
      this.add(this.exportButton);
      this.add(this.imageSizeX);
      this.add(this.imageSizeY);
      this.add(this.addSlice);
      this.add(this.removeSlice);
      this.add(this.nextSlice);
      this.add(this.previousSlice);
      if (!this.sliceControlBoxes.isEmpty()) {
         int slice = Math.max(0, Math.min(this.sliceControlBoxes.size() - 1, this.currentSlice));
         SliceCreationGUI.SliceControlBox widget = this.sliceControlBoxes.get(slice);
         this.add(widget);
         this.activeSlice = widget;
      }
   }

   public void alignWidgets() {
      this.setSizeProportional(1.0F, 1.0F);
      int oneFourth = this.width / 4;
      this.sliceControlBox.setPosAndSize(2, 60, oneFourth, 60);
      this.exportButton.setPosAndSize(28, 0, 20, 20);
      this.imageSizeX.setPosAndSize(2, 25, 20, 20);
      this.imageSizeY.setPosAndSize(27, 25, 20, 20);
      if (this.activeSlice != null) {
         this.activeSlice.setPosAndSize(2, 140, oneFourth, 60);
      }

      this.addSlice.setPosAndSize(0, 120, 8, 8);
      this.removeSlice.setPosAndSize(10, 120, 8, 8);
      this.nextSlice.setPosAndSize(20, 120, 8, 8);
      this.previousSlice.setPosAndSize(30, 120, 8, 8);
   }

   public void drawBackground(GuiGraphics graphics, Theme theme, int x, int y, int w, int h) {
      super.drawBackground(graphics, theme, x, y, w, h);
      int oneFourth = w / 4;
      Color4I.BLACK.draw(graphics, oneFourth, y, 4, h);
      theme.drawHorizontalTab(graphics, x + 20, y, w, h, false);
      graphics.pose().pushPose();
      graphics.pose().scale(2.0F, 2.0F, 0.0F);
      this.sliceControlBox.drawMainTexture(graphics, oneFourth + 5, y);
      this.sliceControlBox.drawOverlay(graphics, oneFourth + 5, y);

      for (SliceCreationGUI.SliceControlBox controlBox : this.sliceControlBoxes) {
         controlBox.drawOverlay(graphics, oneFourth + 5, y);
      }

      graphics.pose().popPose();
      List<ChildIconData> slices = new ArrayList<>();

      for (SliceCreationGUI.SliceControlBox controlBox : this.sliceControlBoxes) {
         slices.add(
            new ChildIconData(
               Optional.empty(), controlBox.createSlice(), Optional.of(new Offset(controlBox.offsetXText.getIntValue(), controlBox.offsetYText.getIntValue()))
            )
         );
      }

      new EntityImageIcon(this.sliceControlBox.texture, this.sliceControlBox.createSlice(), slices, null)
         .draw(graphics, x + 2, y + 2, this.imageSizeX.getIntValue(), this.imageSizeY.getIntValue());
   }

   public void drawForeground(GuiGraphics graphics, Theme theme, int x, int y, int w, int h) {
      super.drawForeground(graphics, theme, x, y, w, h);
   }

   private SimpleButton createExportButton() {
      return new SimpleButton(
         this,
         Component.literal("Export"),
         Icons.STAR,
         (button, mouseButton) -> {
            SliceCreationGUI bu = (SliceCreationGUI)button.getGui();
            EntityIconSettings entityIconSettings = new EntityIconSettings(
               true,
               Optional.empty(),
               Optional.of(bu.sliceControlBox.createSlice()),
               bu.sliceControlBoxes
                  .stream()
                  .map(
                     box -> new ChildIconData(
                        Optional.empty(), box.createSlice(), Optional.of(new Offset(box.offsetXText.getIntValue(), box.offsetYText.getIntValue()))
                     )
                  )
                  .toList(),
               WidthHeight.DEFAULT,
               Optional.empty(),
               1.0,
               true
            );
            EntityIconSettings.CODEC.encodeStart(JsonOps.INSTANCE, entityIconSettings).result().ifPresent(jsonElement -> {
               Path path = Platform.getGameFolder().resolve("export");

               try {
                  ResourceLocation key = BuiltInRegistries.ENTITY_TYPE.getKey(this.entityType);
                  String path1 = key.getNamespace() + "/" + key.getPath();
                  Path resolve = path.resolve(path1 + ".json");
                  if (!Files.exists(resolve.getParent())) {
                     Files.createDirectories(resolve.getParent());
                  }

                  Files.write(resolve, jsonElement.toString().getBytes());
                  SimpleToast.info(Component.literal("Saved File"), Component.empty());
               } catch (IOException e) {
                  throw new RuntimeException(e);
               }
            });
         }
      );
   }

   private void loadExistingSettings() {
      EntityIconLoader.getSettings(this.entityType)
         .ifPresent(
            settings -> {
               settings.mainSlice().ifPresent(slice -> {
                  this.sliceControlBox.xText.setAmount(slice.x());
                  this.sliceControlBox.yText.setAmount(slice.y());
                  this.sliceControlBox.wText.setAmount(slice.width());
                  this.sliceControlBox.hText.setAmount(slice.height());
               });
               settings.children()
                  .forEach(
                     child -> {
                        SliceCreationGUI.SliceControlBox sliceControlBox = new SliceCreationGUI.SliceControlBox(
                           this, this.texture, this.textureWidth, this.textureHeight, true
                        );
                        Slice slice1 = child.slice();
                        sliceControlBox.xText.setAmount(slice1.x());
                        sliceControlBox.yText.setAmount(slice1.y());
                        sliceControlBox.wText.setAmount(slice1.width());
                        sliceControlBox.hText.setAmount(slice1.height());
                        child.offset().ifPresent(offset -> {
                           sliceControlBox.offsetXText.setAmount(offset.x());
                           sliceControlBox.offsetYText.setAmount(offset.y());
                        });
                        this.sliceControlBoxes.add(sliceControlBox);
                        this.activeSlice = sliceControlBox;
                     }
                  );
            }
         );
   }

   private Pair<Integer, Integer> getTextureSize() {
      TextureImage textureImage = TextureImage.load(Minecraft.getInstance().getResourceManager(), this.texture);

      try {
         int imageWidth = textureImage.getImage().getWidth();
         int imageHeight = textureImage.getImage().getHeight();
         return Pair.of(imageWidth, imageHeight);
      } catch (Exception e) {
         LOGGER.error("Failed to get texture size for {}", this.texture, e);
         throw new RuntimeException(e);
      }
   }

   private static class ColorButton extends SimpleButton {
      private Color4I color4I;

      public ColorButton(Panel panel, Color4I activeValue) {
         super(panel, Component.literal(""), Icons.COLOR_BLANK, null);
         this.color4I = activeValue;
         this.setConsumer((button, mouseButton) -> {
            int random = (int)Mth.randomBetween(Minecraft.getInstance().level.random, 0.0F, 255.0F);
            this.color4I = Color4I.get256(random);
         });
      }

      public void drawIcon(GuiGraphics graphics, Theme theme, int x, int y, int w, int h) {
      }

      public void draw(GuiGraphics graphics, Theme theme, int x, int y, int w, int h) {
         super.draw(graphics, theme, x, y, w, h);
         theme.drawWidget(graphics, x, y, w, h, this.getWidgetType());
         Color4I.BLACK.withAlpha(125).draw(graphics, x + 2, y + 2, 12, 12);
         this.color4I.draw(graphics, x + 3, y + 3, 10, 10);
      }
   }

   public static class SliceControlBox extends Panel {
      private final ImageIcon mainIcon;
      private final ResourceLocation texture;
      private final IntTextBox xText;
      private final IntTextBox yText;
      private final IntTextBox wText;
      private final IntTextBox hText;
      private final int imageWidth;
      private final int imageHeight;
      private final SliceCreationGUI.ColorButton colorButton;
      private final boolean offset;
      private final IntTextBox offsetXText;
      private final IntTextBox offsetYText;
      private final Button toggleOverlay;
      private boolean overlay = true;

      public SliceControlBox(Panel p, ResourceLocation texture, int imageWidth, int imageHeight, boolean offset) {
         super(p);
         this.colorButton = new SliceCreationGUI.ColorButton(this, Color4I.WHITE);
         this.mainIcon = new ImageIcon(texture);
         this.texture = texture;
         this.add(this.xText = new IntTextBox(this));
         this.xText.setAmount(0);
         this.add(this.yText = new IntTextBox(this));
         this.yText.setAmount(0);
         this.add(this.wText = new IntTextBox(this));
         this.add(this.hText = new IntTextBox(this));
         this.wText.setAmount(imageWidth);
         this.hText.setAmount(imageHeight);
         this.wText.setMinMax(1, imageWidth);
         this.hText.setMinMax(1, imageHeight);
         this.xText.setMinMax(0, imageWidth);
         this.yText.setMinMax(0, imageHeight);
         this.imageWidth = imageWidth;
         this.imageHeight = imageHeight;
         this.offset = offset;
         this.offsetXText = new IntTextBox(this);
         this.offsetYText = new IntTextBox(this);
         this.offsetXText.setAmount(0);
         this.offsetYText.setAmount(0);
         this.toggleOverlay = new ToggleableButton(this, this.overlay, Icons.ACCEPT, Icons.REMOVE, (button, newState) -> this.overlay = newState);
      }

      public void addWidgets() {
         this.add(this.xText);
         this.add(this.yText);
         this.add(this.wText);
         this.add(this.hText);
         this.add(this.colorButton);
         this.add(this.toggleOverlay);
         if (this.offset) {
            this.add(this.offsetXText);
            this.add(this.offsetYText);
         }
      }

      public void alignWidgets() {
         this.colorButton.setPosAndSize(50, 35, 20, 20);
         this.xText.setPosAndSize(0, 0, 20, 20);
         this.yText.setPosAndSize(25, 0, 20, 20);
         this.wText.setPosAndSize(50, 0, 20, 20);
         this.hText.setPosAndSize(75, 0, 20, 20);
         this.toggleOverlay.setPosAndSize(75, 35, 20, 20);
         if (this.offset) {
            this.offsetXText.setPosAndSize(0, 35, 20, 20);
            this.offsetYText.setPosAndSize(25, 35, 20, 20);
         }
      }

      public void drawBackground(GuiGraphics graphics, Theme theme, int x, int y, int w, int h) {
         super.drawBackground(graphics, theme, x, y, w, h);
         theme.drawString(graphics, "X", this.xText.getX() + 1, this.xText.getY() - theme.getFontHeight(), -16777216);
         theme.drawString(graphics, "Y", this.yText.getX() + 1, this.yText.getY() - theme.getFontHeight(), -16777216);
         theme.drawString(graphics, "W", this.wText.getX() + 1, this.wText.getY() - theme.getFontHeight(), -16777216);
         theme.drawString(graphics, "H", this.hText.getX() + 1, this.hText.getY() - theme.getFontHeight(), -16777216);
         if (this.offset) {
            theme.drawString(graphics, "OX", this.offsetXText.getX() + 1, this.offsetXText.getY() - theme.getFontHeight(), -16777216);
            theme.drawString(graphics, "OY", this.offsetYText.getX() + 1, this.offsetYText.getY() - theme.getFontHeight(), -16777216);
         }
      }

      public void drawMainTexture(GuiGraphics graphics, int x, int y) {
         this.mainIcon.draw(graphics, x, y, this.imageWidth, this.imageHeight);
      }

      public void drawOverlay(GuiGraphics graphics, int x, int y) {
         if (this.overlay) {
            this.colorButton
               .color4I
               .withAlpha(100)
               .draw(graphics, x + this.xText.getIntValue(), y + this.yText.getIntValue(), this.wText.getIntValue(), this.hText.getIntValue());
         }
      }

      public Slice createSlice() {
         return new Slice(this.xText.getIntValue(), this.yText.getIntValue(), this.wText.getIntValue(), this.hText.getIntValue());
      }

      public int getW() {
         return this.wText.getIntValue();
      }

      public int getH() {
         return this.hText.getIntValue();
      }
   }
}
