package dev.ftb.mods.ftbquests.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.architectury.networking.NetworkManager;
import dev.ftb.mods.ftblibrary.icon.Color4I;
import dev.ftb.mods.ftblibrary.icon.Icons;
import dev.ftb.mods.ftblibrary.ui.BaseScreen;
import dev.ftb.mods.ftblibrary.ui.GuiHelper;
import dev.ftb.mods.ftblibrary.ui.Panel;
import dev.ftb.mods.ftblibrary.ui.SimpleTextButton;
import dev.ftb.mods.ftblibrary.ui.Theme;
import dev.ftb.mods.ftblibrary.ui.Widget;
import dev.ftb.mods.ftblibrary.ui.WidgetType;
import dev.ftb.mods.ftblibrary.ui.WidgetLayout.Horizontal;
import dev.ftb.mods.ftblibrary.ui.input.MouseButton;
import dev.ftb.mods.ftblibrary.util.TimeUtils;
import dev.ftb.mods.ftblibrary.util.TooltipList;
import dev.ftb.mods.ftblibrary.util.client.PositionedIngredient;
import dev.ftb.mods.ftbquests.client.ClientQuestFile;
import dev.ftb.mods.ftbquests.net.GetEmergencyItemsMessage;
import dev.ftb.mods.ftbquests.quest.QuestShape;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;

public class EmergencyItemsScreen extends BaseScreen {
   private static long endTime = 0L;
   private final SimpleTextButton getItemsButton;
   private final SimpleTextButton cancelButton;
   private final Panel itemPanel = new EmergencyItemsScreen.ItemPanel();

   public EmergencyItemsScreen() {
      this.cancelButton = SimpleTextButton.cancel(this, mb -> this.closeGui(), new Component[0]);
      this.getItemsButton = new SimpleTextButton(this, Component.translatable("ftbquests.file.emergency_items.get_items"), Icons.ACCEPT) {
         public void onClicked(MouseButton button) {
            if (Util.getEpochMillis() >= EmergencyItemsScreen.endTime) {
               this.playClickSound();
               NetworkManager.sendToServer(GetEmergencyItemsMessage.INSTANCE);
               EmergencyItemsScreen.resetCooldown();
            }
         }

         public void tick() {
            MutableComponent c = Component.translatable("ftbquests.file.emergency_items.get_items");
            this.setTitle(Util.getEpochMillis() >= EmergencyItemsScreen.endTime ? c : c.withStyle(ChatFormatting.DARK_GRAY));
         }

         public WidgetType getWidgetType() {
            return Util.getEpochMillis() < EmergencyItemsScreen.endTime ? WidgetType.DISABLED : super.getWidgetType();
         }
      };
   }

   public static void initCooldown() {
      if (endTime == 0L) {
         endTime = Util.getEpochMillis() + ClientQuestFile.INSTANCE.getEmergencyItemsCooldown() * 1000L;
      }
   }

   public static void resetCooldown() {
      endTime = Util.getEpochMillis() + ClientQuestFile.INSTANCE.getEmergencyItemsCooldown() * 1000L;
   }

   public static Component getCooldownSeconds() {
      long seconds = Math.max(0L, (endTime - Util.getEpochMillis()) / 1000L * 1000L + 1000L);
      return Component.literal(seconds == 0L ? "00:00" : TimeUtils.getTimeString(seconds));
   }

   public void addWidgets() {
      this.add(this.itemPanel);
      this.add(this.cancelButton);
      this.add(this.getItemsButton);
      this.cancelButton.setPos(this.width / 2 - this.cancelButton.width - 5, this.height * 2 / 3 + 16);
      this.getItemsButton.setPos(this.cancelButton.getX() + this.cancelButton.getWidth() + 10, this.height * 2 / 3 + 16);
   }

   public boolean onInit() {
      return this.setFullscreen();
   }

   public void drawBackground(GuiGraphics graphics, Theme theme, int x, int y, int w, int h) {
      PoseStack poseStack = graphics.pose();
      poseStack.pushPose();
      poseStack.translate((int)(w / 2.0), (int)(h / 5.0), 0.0F);
      poseStack.scale(2.0F, 2.0F, 1.0F);
      Component titleMsg = Component.translatable("ftbquests.file.emergency_items");
      theme.drawString(graphics, titleMsg, -theme.getStringWidth(titleMsg) / 2, 0, Color4I.WHITE, 0);
      poseStack.popPose();
      poseStack.pushPose();
      poseStack.translate((int)(w / 2.0), (int)(h / 2.5), 0.0F);
      poseStack.scale(4.0F, 4.0F, 1.0F);
      Component timeStr = getCooldownSeconds();
      int x1 = -theme.getStringWidth(timeStr) / 2;
      theme.drawString(graphics, timeStr, x1 - 1, 0, Color4I.BLACK, 0);
      theme.drawString(graphics, timeStr, x1 + 1, 0, Color4I.BLACK, 0);
      theme.drawString(graphics, timeStr, x1, 1, Color4I.BLACK, 0);
      theme.drawString(graphics, timeStr, x1, -1, Color4I.BLACK, 0);
      theme.drawString(graphics, timeStr, x1, 0, Color4I.WHITE, 0);
      poseStack.popPose();
   }

   public Theme getTheme() {
      return FTBQuestsTheme.INSTANCE;
   }

   private static class EmergencyItemWidget extends Widget {
      private final ItemStack stack;

      public EmergencyItemWidget(Panel panel, ItemStack stack) {
         super(panel);
         this.stack = stack;
         this.setY(3);
         this.setSize(16, 16);
      }

      public void addMouseOverText(TooltipList list) {
         List<Component> l = new ArrayList<>();
         GuiHelper.addStackTooltip(this.stack, l);
         l.forEach(list::add);
      }

      public void draw(GuiGraphics graphics, Theme theme, int x, int y, int w, int h) {
         GuiHelper.setupDrawing();
         QuestShape.get("rsquare").getOutline().draw(graphics, x - 3, y - 3, w + 6, h + 6);
         graphics.pose().pushPose();
         graphics.pose().translate(x + w / 2.0, y + h / 2.0, 100.0);
         GuiHelper.drawItem(graphics, this.stack, 0, true, null);
         graphics.pose().popPose();
      }

      public Optional<PositionedIngredient> getIngredientUnderMouse() {
         return PositionedIngredient.of(this.stack, this);
      }
   }

   private class ItemPanel extends Panel {
      public ItemPanel() {
         super(EmergencyItemsScreen.this);
      }

      public void addWidgets() {
         ClientQuestFile.INSTANCE.getEmergencyItems().forEach(stack -> this.add(new EmergencyItemsScreen.EmergencyItemWidget(this, stack)));
      }

      public void alignWidgets() {
         this.setWidth(this.align(new Horizontal(3, 7, 3)));
         this.setHeight(22);
         this.setPos((EmergencyItemsScreen.this.width - EmergencyItemsScreen.this.itemPanel.width) / 2, EmergencyItemsScreen.this.height * 2 / 3 - 10);
      }
   }
}
