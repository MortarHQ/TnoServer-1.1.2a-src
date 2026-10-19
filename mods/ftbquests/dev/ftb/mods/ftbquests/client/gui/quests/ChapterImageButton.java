package dev.ftb.mods.ftbquests.client.gui.quests;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftblibrary.config.ui.EditConfigScreen;
import dev.ftb.mods.ftblibrary.icon.Color4I;
import dev.ftb.mods.ftblibrary.icon.Icon;
import dev.ftb.mods.ftblibrary.icon.Icons;
import dev.ftb.mods.ftblibrary.ui.Button;
import dev.ftb.mods.ftblibrary.ui.ContextMenuItem;
import dev.ftb.mods.ftblibrary.ui.Panel;
import dev.ftb.mods.ftblibrary.ui.Theme;
import dev.ftb.mods.ftblibrary.ui.Widget;
import dev.ftb.mods.ftblibrary.ui.WidgetType;
import dev.ftb.mods.ftblibrary.ui.Widget.DrawLayer;
import dev.ftb.mods.ftblibrary.ui.input.MouseButton;
import dev.ftb.mods.ftblibrary.util.TooltipList;
import dev.ftb.mods.ftbquests.net.EditObjectMessage;
import dev.ftb.mods.ftbquests.quest.ChapterImage;
import dev.ftb.mods.ftbquests.quest.Movable;
import dev.ftb.mods.ftbquests.quest.theme.property.ThemeProperties;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;

public class ChapterImageButton extends Button implements QuestPositionableButton {
   private final QuestScreen questScreen;
   private final ChapterImage chapterImage;
   private static final BiFunction<ChapterImageButton.XYPair, Double, ChapterImageButton.XYPair> MEMOIZED_ROTATE = Util.memoize((xy, rotateDeg) -> {
      double radius = xy.radius();
      double angle = xy.angle();
      double rotateRad = Math.toRadians(rotateDeg);
      return new ChapterImageButton.XYPair(radius * Math.cos(angle - rotateRad), radius * Math.sin(angle - rotateRad));
   });

   public ChapterImageButton(Panel panel, ChapterImage i) {
      super(panel, Component.empty(), i.getImage());
      this.questScreen = (QuestScreen)panel.getGui();
      this.setSize(20, 20);
      this.chapterImage = i;
      this.setDrawLayer(DrawLayer.BACKGROUND);
   }

   public static Optional<ChapterImage> getClipboardImage() {
      ChapterImage img = ChapterImage.clipboard.get();
      if (img != null) {
         if (img.getChapter().isValid()) {
            return Optional.of(img);
         }

         ChapterImage.clipboard = new WeakReference<>(null);
      }

      return Optional.empty();
   }

   public boolean checkMouseOver(int mouseX, int mouseY) {
      if (this.questScreen.questPanel.mouseOverQuest == null
         && !this.questScreen.movingObjects
         && !this.questScreen.viewQuestPanel.isMouseOver()
         && !this.questScreen.chapterPanel.isMouseOver()
         && (!this.chapterImage.getClick().isEmpty() || this.questScreen.file.canEdit())) {
         if (this.chapterImage.getRotation() != 0.0) {
            double cx = this.chapterImage.isAlignToCorner() ? this.getX() : this.getX() + this.getWidth() / 2.0;
            double cy = this.chapterImage.isAlignToCorner() ? this.getY() : this.getY() + this.getHeight() / 2.0;
            ChapterImageButton.XYPair rotated = MEMOIZED_ROTATE.apply(new ChapterImageButton.XYPair(mouseX - cx, mouseY - cy), this.chapterImage.getRotation());
            mouseX = (int)(cx + rotated.x);
            mouseY = (int)(cy + rotated.y);
         }

         return super.checkMouseOver(mouseX, mouseY);
      } else {
         return false;
      }
   }

   public boolean mousePressed(MouseButton button) {
      if (this.isMouseOver() && this.getWidgetType() != WidgetType.DISABLED) {
         this.onClicked(button);
         return !button.isLeft() || button.isLeft() && Screen.hasAltDown() || !this.chapterImage.getClick().isEmpty();
      } else {
         return false;
      }
   }

   public void onClicked(MouseButton button) {
      if (this.questScreen.file.canEdit() && button.isRight()) {
         List<ContextMenuItem> contextMenu = new ArrayList<>();
         contextMenu.add(ContextMenuItem.title(Component.literal("\"").append(this.chapterImage.getTitle()).append(Component.literal("\""))));
         contextMenu.add(ContextMenuItem.SEPARATOR);
         contextMenu.add(new ContextMenuItem(Component.translatable("selectServer.edit"), ThemeProperties.EDIT_ICON.get(), b -> this.openEditScreen()));
         contextMenu.add(
            new ContextMenuItem(
               Component.translatable("gui.move"),
               ThemeProperties.MOVE_UP_ICON.get(this.chapterImage.getChapter()),
               b -> this.questScreen.initiateMoving(this.chapterImage)
            ) {
               public void addMouseOverText(TooltipList list) {
                  list.add(Component.translatable("ftbquests.gui.move_tooltip").withStyle(ChatFormatting.DARK_GRAY));
               }
            }
         );
         contextMenu.add(new ContextMenuItem(Component.translatable("gui.copy"), Icons.INFO, b -> this.chapterImage.copyToClipboard()) {
            public void addMouseOverText(TooltipList list) {
               list.add(Component.literal(ChapterImageButton.this.chapterImage.getImage().toString()).withStyle(ChatFormatting.DARK_GRAY));
            }
         });
         if (this.chapterImage.isAspectRatioOff()) {
            contextMenu.add(
               new ContextMenuItem(Component.translatable("ftbquests.gui.fix_aspect_ratio_w"), Icons.ART, b -> this.chapterImage.fixupAspectRatio(true))
            );
            contextMenu.add(
               new ContextMenuItem(Component.translatable("ftbquests.gui.fix_aspect_ratio_h"), Icons.ART, b -> this.chapterImage.fixupAspectRatio(false))
            );
         }

         contextMenu.add(new ContextMenuItem(Component.translatable("selectServer.delete"), ThemeProperties.DELETE_ICON.get(), b -> {
            this.chapterImage.getChapter().removeImage(this.chapterImage);
            EditObjectMessage.sendToServer(this.chapterImage.getChapter());
         }).setYesNoText(Component.translatable("delete_item", new Object[]{this.chapterImage.getImage().toString()})));
         this.getGui().openContextMenu(contextMenu);
      } else if (button.isLeft()) {
         if (Screen.hasControlDown() && this.questScreen.file.canEdit()) {
            this.questScreen.toggleSelected(this.chapterImage);
         } else if (Screen.hasAltDown() && this.questScreen.file.canEdit()) {
            this.openEditScreen();
         } else if (!this.chapterImage.getClick().isEmpty()) {
            this.playClickSound();
            this.handleClick(this.chapterImage.getClick());
         }
      } else if (this.questScreen.file.canEdit() && button.isMiddle()) {
         if (!this.questScreen.selectedObjects.contains(this.chapterImage)) {
            this.questScreen.toggleSelected(this.chapterImage);
         }

         this.questScreen.movingObjects = true;
      }
   }

   private void openEditScreen() {
      final String name = this.chapterImage.getImage() instanceof Color4I ? this.chapterImage.getColor().toString() : this.chapterImage.getImage().toString();
      final ConfigGroup group = new ConfigGroup("ftbquests", accepted -> {
         if (accepted) {
            EditObjectMessage.sendToServer(this.chapterImage.getChapter());
         }

         this.run();
      }) {
         public Component getName() {
            MutableComponent type = Component.literal(" [")
               .append(Component.translatable("ftbquests.chapter.image"))
               .append("]")
               .withStyle(ChatFormatting.AQUA);
            return Component.empty().append(Component.literal(name).withStyle(ChatFormatting.UNDERLINE)).append(type);
         }
      };
      this.chapterImage.fillConfigGroup(group.getOrCreateSubgroup("chapter").getOrCreateSubgroup("image"));
      (new EditConfigScreen(group) {
         public Component getTitle() {
            return group.getName();
         }
      }).openGui();
   }

   public boolean collidesWith(int x, int y, int w, int h) {
      return this.chapterImage.getRotation() != 0.0 || super.collidesWith(x, y, w, h);
   }

   public void addMouseOverText(TooltipList list) {
      this.chapterImage.addHoverText(list);
   }

   public void draw(GuiGraphics graphics, Theme theme, int x, int y, int w, int h) {
      Icon image = this.chapterImage.getImage();
      boolean transparent = !this.chapterImage.shouldShowImage(this.questScreen.file.selfTeamData);
      if (transparent) {
         image = image.withColor(Color4I.WHITE.withAlpha(100));
      } else if (!this.chapterImage.getColor().equals(Color4I.WHITE) || this.chapterImage.getAlpha() < 255) {
         image = image.withColor(this.chapterImage.getColor().withAlpha(this.chapterImage.getAlpha()));
      }

      PoseStack poseStack = graphics.pose();
      poseStack.pushPose();
      if (this.chapterImage.isAlignToCorner()) {
         poseStack.translate(x, y, 0.0F);
         poseStack.mulPose(Axis.ZP.rotationDegrees((float)this.chapterImage.getRotation()));
         poseStack.scale(w, h, 1.0F);
         image.draw(graphics, 0, 0, 1, 1);
         if (this.questScreen.selectedObjects.contains(this.moveAndDeleteFocus())) {
            Color4I col = Color4I.WHITE.withAlpha((int)(128.0 + Math.sin(System.currentTimeMillis() * 0.003) * 50.0));
            col.draw(graphics, 0, 0, 1, 1);
         }
      } else {
         poseStack.translate((int)(x + w / 2.0), (int)(y + h / 2.0), 0.0F);
         poseStack.mulPose(Axis.ZP.rotationDegrees((float)this.chapterImage.getRotation()));
         poseStack.scale(w / 2.0F, h / 2.0F, 1.0F);
         image.draw(graphics, -1, -1, 2, 2);
         if (this.questScreen.selectedObjects.contains(this.moveAndDeleteFocus())) {
            Color4I col = Color4I.WHITE.withAlpha((int)(128.0 + Math.sin(System.currentTimeMillis() * 0.003) * 50.0));
            col.draw(graphics, -1, -1, 2, 2);
         }
      }

      poseStack.popPose();
   }

   @Override
   public QuestPositionableButton.Position getPosition() {
      return new QuestPositionableButton.Position(
         this.chapterImage.getX(), this.chapterImage.getY(), this.chapterImage.getWidth(), this.chapterImage.getHeight()
      );
   }

   public int compareTo(@NotNull Widget o) {
      return o instanceof ChapterImageButton cb2 ? Integer.compare(this.chapterImage.getOrder(), cb2.chapterImage.getOrder()) : 0;
   }

   @Override
   public Movable moveAndDeleteFocus() {
      return this.chapterImage;
   }

   private record XYPair(double x, double y) {
      double radius() {
         return Math.sqrt(this.x * this.x + this.y * this.y);
      }

      double angle() {
         return Math.atan2(this.y, this.x);
      }
   }
}
