package dev.ftb.mods.ftbquests.client.gui.quests;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.architectury.networking.NetworkManager;
import dev.ftb.mods.ftblibrary.icon.Color4I;
import dev.ftb.mods.ftblibrary.icon.Icons;
import dev.ftb.mods.ftblibrary.icon.ItemIcon;
import dev.ftb.mods.ftblibrary.ui.Button;
import dev.ftb.mods.ftblibrary.ui.ContextMenuItem;
import dev.ftb.mods.ftblibrary.ui.GuiHelper;
import dev.ftb.mods.ftblibrary.ui.Panel;
import dev.ftb.mods.ftblibrary.ui.ScreenWrapper;
import dev.ftb.mods.ftblibrary.ui.SimpleTextButton;
import dev.ftb.mods.ftblibrary.ui.Theme;
import dev.ftb.mods.ftblibrary.ui.WidgetType;
import dev.ftb.mods.ftblibrary.ui.input.MouseButton;
import dev.ftb.mods.ftblibrary.ui.misc.AbstractButtonListScreen;
import dev.ftb.mods.ftblibrary.util.TooltipList;
import dev.ftb.mods.ftblibrary.util.client.PositionedIngredient;
import dev.ftb.mods.ftbquests.api.ItemFilterAdapter;
import dev.ftb.mods.ftbquests.client.gui.ContextMenuBuilder;
import dev.ftb.mods.ftbquests.integration.item_filtering.ItemMatchingSystem;
import dev.ftb.mods.ftbquests.net.EditObjectMessage;
import dev.ftb.mods.ftbquests.net.GiveItemToPlayerMessage;
import dev.ftb.mods.ftbquests.net.ReorderItemMessage;
import dev.ftb.mods.ftbquests.quest.TeamData;
import dev.ftb.mods.ftbquests.quest.task.ItemTask;
import dev.ftb.mods.ftbquests.quest.task.Task;
import dev.ftb.mods.ftbquests.quest.theme.property.ThemeProperties;
import dev.ftb.mods.ftbquests.util.TextUtils;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class TaskButton extends Button {
   private final QuestScreen questScreen;
   final Task task;

   public TaskButton(Panel panel, Task task) {
      super(panel, task.getTitle(), Icons.ACCEPT);
      this.questScreen = (QuestScreen)panel.getGui();
      this.task = task;
   }

   public boolean mousePressed(MouseButton button) {
      if (!this.isMouseOver()) {
         return false;
      }

      if (button.isRight() || this.getWidgetType() != WidgetType.DISABLED) {
         this.onClicked(button);
      }

      return true;
   }

   public void onClicked(MouseButton button) {
      if (button.isLeft()) {
         if (this.task.getQuestFile().canEdit() && ScreenWrapper.hasAltDown()) {
            this.task.onEditButtonClicked(this.questScreen);
         } else {
            boolean canClick = this.task.isValid()
               && this.questScreen.file.selfTeamData.canStartTasks(this.task.getQuest())
               && !this.questScreen.file.selfTeamData.isCompleted(this.task);
            this.task.onButtonClicked(this, canClick);
         }
      } else if (button.isRight() && this.questScreen.file.canEdit()) {
         this.playClickSound();
         ContextMenuBuilder builder = ContextMenuBuilder.create(this.task, this.questScreen);
         builder.insertAtTop(
            List.of(
               new ContextMenuItem(
                  Component.translatable("ftbquests.gui.move_left"),
                  Icons.LEFT,
                  b -> NetworkManager.sendToServer(new ReorderItemMessage(this.task.getId(), false))
               )
            )
         );
         builder.insertAtTop(
            List.of(
               new ContextMenuItem(
                  Component.translatable("ftbquests.gui.move_right"),
                  Icons.RIGHT,
                  b -> NetworkManager.sendToServer(new ReorderItemMessage(this.task.getId(), true))
               )
            )
         );
         if (this.task instanceof ItemTask itemTask && !itemTask.getItemStack().isEmpty()) {
            List<TagKey<Item>> tags = itemTask.getItemStack().getItem().builtInRegistryHolder().tags().toList();
            if (!tags.isEmpty() && !ItemMatchingSystem.INSTANCE.isItemFilter(itemTask.getItemStack())) {
               for (ItemFilterAdapter adapter : ItemMatchingSystem.INSTANCE.adapters()) {
                  if (adapter.hasItemTagFilter()) {
                     builder.insertAtTop(
                        List.of(
                           new ContextMenuItem(
                              Component.translatable("ftbquests.task.ftbquests.item.convert_tag", new Object[]{adapter.getName()}),
                              ThemeProperties.RELOAD_ICON.get(),
                              b -> {
                                 if (tags.size() == 1) {
                                    this.setTagFilterAndSave(itemTask, adapter, tags.get(0));
                                 } else {
                                    new TaskButton.TagSelectionScreen(tags, itemTask, adapter).openGui();
                                 }
                              }
                           )
                        )
                     );
                  }
               }
            }

            builder.insertAtTop(
               List.of(
                  new ContextMenuItem(
                     Component.translatable("ftbquests.task.grab_item"),
                     Icons.ADD_GRAY,
                     b -> NetworkManager.sendToServer(new GiveItemToPlayerMessage(itemTask.getItemStack()))
                  )
               )
            );
         }

         if (this.task.getIcon() instanceof ItemIcon itemIcon) {
            builder.insertAtTop(List.of(new ContextMenuItem(Component.translatable("ftbquests.gui.use_as_quest_icon"), ThemeProperties.EDIT_ICON.get(), b -> {
               this.task.getQuest().setRawIcon(itemIcon.getStack().copy());
               this.task.getQuest().clearCachedData();
               EditObjectMessage.sendToServer(this.task.getQuest());
            })));
         }

         builder.openContextMenu(this.getGui());
      }
   }

   private static String stackToString(ItemStack itemStack) {
      StringBuilder builder = new StringBuilder(BuiltInRegistries.ITEM.getKey(itemStack.getItem()).toString());
      String compData = itemStack.getComponents()
         .stream()
         .filter(c -> !c.value().equals(itemStack.getItem().components().get(c.type())))
         .map(c -> c.type() + "=\"" + c.value() + "\"")
         .collect(Collectors.joining(","));
      if (!compData.isEmpty()) {
         builder.append("[").append(compData).append("]");
      }

      if (itemStack.getCount() > 1) {
         builder.append(" ").append(itemStack.getCount());
      }

      return builder.toString();
   }

   private void setTagFilterAndSave(ItemTask itemTask, ItemFilterAdapter adapter, TagKey<Item> tag) {
      itemTask.setStackAndCount(adapter.makeTagFilterStack(tag), itemTask.getItemStack().getCount());
      if (itemTask.getRawTitle().isEmpty()) {
         itemTask.setRawTitle("Any #" + tag.location());
      }

      EditObjectMessage.sendToServer(itemTask);
   }

   public Optional<PositionedIngredient> getIngredientUnderMouse() {
      return this.task.getIngredient(this);
   }

   public void addMouseOverText(TooltipList list) {
      this.questScreen.addInfoTooltip(list, this.task);
      TeamData teamData = this.questScreen.file.selfTeamData;
      this.task.addMouseOverHeader(list, teamData, Minecraft.getInstance().options.advancedItemTooltips);
      if (teamData.canStartTasks(this.task.getQuest())) {
         long maxp = this.task.getMaxProgress();
         long progress = teamData.getProgress(this.task);
         if (maxp > 1L) {
            if (this.task.hideProgressNumbers()) {
               list.add(Component.literal("[" + this.task.getRelativeProgressFromChildren(teamData) + "%]").withStyle(ChatFormatting.DARK_GREEN));
            } else {
               String max = isShiftKeyDown() ? Long.toUnsignedString(maxp) : this.task.formatMaxProgress();
               String prog = isShiftKeyDown() ? Long.toUnsignedString(progress) : this.task.formatProgress(teamData, progress);
               String s = (progress > maxp ? max : prog) + " / " + max;
               if (maxp < 100L) {
                  list.add(Component.literal(s).withStyle(ChatFormatting.DARK_GREEN));
               } else {
                  list.add(
                     Component.literal(s)
                        .withStyle(ChatFormatting.DARK_GREEN)
                        .append(Component.literal(" [" + this.task.getRelativeProgressFromChildren(teamData) + "%]").withStyle(ChatFormatting.DARK_GRAY))
                  );
               }
            }
         }
      }

      if (this.task.isOptionalForProgression(teamData)) {
         list.add(Component.translatable("ftbquests.quest.misc.optional_task").withStyle(ChatFormatting.GRAY));
      }

      this.task.addMouseOverText(list, teamData);
   }

   public void drawBackground(GuiGraphics graphics, Theme theme, int x, int y, int w, int h) {
      if (this.isMouseOver()) {
         super.drawBackground(graphics, theme, x, y, w, h);
      }
   }

   public void drawIcon(GuiGraphics graphics, Theme theme, int x, int y, int w, int h) {
      this.task.drawGUI(this.questScreen.file.selfTeamData, graphics, x, y, w, h);
   }

   public void draw(GuiGraphics graphics, Theme theme, int x, int y, int w, int h) {
      int bs = h >= 32 ? 32 : 16;
      GuiHelper.setupDrawing();
      this.drawBackground(graphics, theme, x, y, w, h);
      this.drawIcon(graphics, theme, x + (w - bs) / 2, y + (h - bs) / 2, bs, bs);
      if (this.questScreen.file.selfTeamData != null) {
         if (this.questScreen.getContextMenu().isPresent()) {
         }

         PoseStack poseStack = graphics.pose();
         if (this.questScreen.file.selfTeamData.isCompleted(this.task)) {
            poseStack.pushPose();
            poseStack.translate(0.0F, 0.0F, 200.0F);
            RenderSystem.enableBlend();
            ThemeProperties.CHECK_ICON.get().draw(graphics, x + w - 9, y + 1, 8, 8);
            poseStack.popPose();
         } else {
            MutableComponent buttonText = this.task.getButtonText();
            if (!TextUtils.isComponentEmpty(buttonText)) {
               poseStack.pushPose();
               poseStack.translate(x + 19.0F - theme.getStringWidth(buttonText) / 2.0F, y + 15.0F, 200.0F);
               poseStack.scale(0.5F, 0.5F, 1.0F);
               RenderSystem.enableBlend();
               theme.drawString(graphics, buttonText, 0, 0, Color4I.WHITE, 2);
               poseStack.popPose();
            }
         }
      }
   }

   private class TagSelectionScreen extends AbstractButtonListScreen {
      private final List<TagKey<Item>> tags;
      private final ItemTask itemTask;
      private final ItemFilterAdapter adapter;

      public TagSelectionScreen(List<TagKey<Item>> tags, ItemTask itemTask, ItemFilterAdapter adapter) {
         this.itemTask = itemTask;
         this.tags = tags;
         this.adapter = adapter;
         this.setTitle(Component.translatable("ftbquests.task.ftbquests.item.select_tag"));
         this.showBottomPanel(false);
         this.showCloseButton(true);
      }

      public void addButtons(Panel panel) {
         this.tags
            .stream()
            .sorted(Comparator.comparing(itemTagKey -> itemTagKey.location().toString()))
            .forEach(tag -> panel.add(new TaskButton.TagSelectionScreen.TagSelectionButton(panel, (TagKey<Item>)tag)));
      }

      public boolean onInit() {
         int titleW = this.getTheme().getStringWidth(this.getTitle());
         int w = this.tags.stream().map(t -> this.getTheme().getStringWidth(t.location().toString())).max(Comparator.naturalOrder()).orElse(100);
         this.setSize(Math.max(titleW, w) + 20, this.getScreen().getGuiScaledHeight() * 3 / 4);
         return true;
      }

      protected void doCancel() {
         TaskButton.this.questScreen.openGui();
      }

      protected void doAccept() {
         TaskButton.this.questScreen.openGui();
      }

      private class TagSelectionButton extends SimpleTextButton {
         private final TagKey<Item> tag;

         public TagSelectionButton(Panel panel, TagKey<Item> tag) {
            super(panel, Component.literal(tag.location().toString()), Color4I.empty());
            this.tag = tag;
         }

         public void onClicked(MouseButton button) {
            TaskButton.this.questScreen.openGui();
            TaskButton.this.setTagFilterAndSave(TagSelectionScreen.this.itemTask, TagSelectionScreen.this.adapter, this.tag);
         }

         public void drawBackground(GuiGraphics graphics, Theme theme, int x, int y, int w, int h) {
            if (this.isMouseOver) {
               Color4I.WHITE.withAlpha(30).draw(graphics, x, y, w, h);
            }

            Color4I.GRAY.withAlpha(40).draw(graphics, x, y + h, w, 1);
         }
      }
   }
}
