package dev.ftb.mods.ftbquests.client.gui;

import dev.architectury.networking.NetworkManager;
import dev.ftb.mods.ftblibrary.config.StringConfig;
import dev.ftb.mods.ftblibrary.config.ui.EditStringConfigOverlay;
import dev.ftb.mods.ftblibrary.icon.Color4I;
import dev.ftb.mods.ftblibrary.icon.Icons;
import dev.ftb.mods.ftblibrary.icon.ItemIcon;
import dev.ftb.mods.ftblibrary.ui.ContextMenuItem;
import dev.ftb.mods.ftblibrary.ui.Panel;
import dev.ftb.mods.ftblibrary.ui.SimpleTextButton;
import dev.ftb.mods.ftblibrary.ui.Theme;
import dev.ftb.mods.ftblibrary.ui.input.Key;
import dev.ftb.mods.ftblibrary.ui.input.MouseButton;
import dev.ftb.mods.ftblibrary.ui.misc.AbstractButtonListScreen;
import dev.ftb.mods.ftblibrary.ui.misc.AbstractThreePanelScreen;
import dev.ftb.mods.ftblibrary.ui.misc.AbstractButtonListScreen.ButtonPanel;
import dev.ftb.mods.ftblibrary.util.TooltipList;
import dev.ftb.mods.ftbquests.FTBQuests;
import dev.ftb.mods.ftbquests.client.ClientQuestFile;
import dev.ftb.mods.ftbquests.client.gui.quests.QuestScreen;
import dev.ftb.mods.ftbquests.net.CreateObjectMessage;
import dev.ftb.mods.ftbquests.net.DeleteObjectMessage;
import dev.ftb.mods.ftbquests.net.EditObjectMessage;
import dev.ftb.mods.ftbquests.quest.QuestObjectBase;
import dev.ftb.mods.ftbquests.quest.loot.LootCrate;
import dev.ftb.mods.ftbquests.quest.loot.RewardTable;
import dev.ftb.mods.ftbquests.quest.reward.RandomReward;
import dev.ftb.mods.ftbquests.quest.translation.TranslationKey;
import dev.ftb.mods.ftbquests.registry.ModItems;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.apache.commons.lang3.mutable.MutableInt;
import org.jetbrains.annotations.NotNull;

public class RewardTablesScreen extends AbstractButtonListScreen {
   private final QuestScreen questScreen;
   private final SimpleTextButton addButton;
   private final List<RewardTable> rewardTablesCopy;
   private final IntSet editedIndexes = new IntOpenHashSet();
   private final IntSet pendingDeleteIndexes = new IntOpenHashSet();
   private boolean changed = false;

   public RewardTablesScreen(QuestScreen questScreen) {
      this.questScreen = questScreen;
      this.rewardTablesCopy = ClientQuestFile.INSTANCE
         .getRewardTables()
         .stream()
         .map(table -> QuestObjectBase.copy((RewardTable)table, () -> new RewardTable(table.id, ClientQuestFile.INSTANCE)))
         .collect(Collectors.toCollection(ArrayList::new));
      this.setTitle(Component.translatable("ftbquests.reward_tables"));
      this.setHasSearchBox(true);
      this.setBorder(1, 1, 1);
      this.addButton = new SimpleTextButton(this.topPanel, Component.translatable("gui.add"), Icons.ADD) {
         public void onClicked(MouseButton button) {
            this.playClickSound();
            StringConfig cfg = new StringConfig();
            EditStringConfigOverlay<String> panel = new EditStringConfigOverlay(this.getGui(), cfg, accepted -> {
               if (accepted) {
                  RewardTable table = new RewardTable(0L, ClientQuestFile.INSTANCE);
                  table.setRawTitle((String)cfg.getValue());
                  RewardTablesScreen.this.rewardTablesCopy.add(table);
                  RewardTablesScreen.this.refreshWidgets();
               }
            }).atPosition(this.posX, this.posY + this.height);
            panel.setExtraZlevel(300);
            this.getGui().pushModalPanel(panel);
         }
      };
   }

   public void addButtons(Panel panel) {
      List<RewardTablesScreen.RewardTableButton> buttons = new ArrayList<>();

      for (int i = 0; i < this.rewardTablesCopy.size(); i++) {
         RewardTable table = this.rewardTablesCopy.get(i);
         buttons.add(new RewardTablesScreen.RewardTableButton(panel, table, i));
      }

      panel.addAll(buttons.stream().sorted(Comparator.comparing(btn -> btn.table)).toList());
   }

   protected int getTopPanelHeight() {
      return 25;
   }

   protected Panel createTopPanel() {
      return new RewardTablesScreen.CustomTopPanel();
   }

   public boolean onInit() {
      int maxW = Math.max(
         this.getTheme().getStringWidth(this.getTitle()) + 100,
         this.rewardTablesCopy.stream().map(t -> this.getTheme().getStringWidth(t.getTitle())).max(Comparator.naturalOrder()).orElse(0)
      );
      this.setWidth(maxW);
      this.setHeight(this.getGui().getScreen().getGuiScaledHeight() * 4 / 5);
      return true;
   }

   public boolean onClosedByKey(Key key) {
      if (super.onClosedByKey(key)) {
         this.doCancel();
         return true;
      } else {
         return false;
      }
   }

   protected void doCancel() {
      if (this.changed) {
         this.openYesNo(Component.translatable("ftblibrary.unsaved_changes"), Component.empty(), this.questScreen);
      } else {
         this.questScreen.run();
      }
   }

   protected void doAccept() {
      IntSet toCreate = new IntOpenHashSet();

      for (int idx = 0; idx < this.rewardTablesCopy.size(); idx++) {
         if (this.rewardTablesCopy.get(idx).getId() == 0L && !this.pendingDeleteIndexes.contains(idx)) {
            toCreate.add(idx);
         }
      }

      this.editedIndexes.removeAll(this.pendingDeleteIndexes);
      int nAdded = this.sendToServer(toCreate, RewardTablesScreen::makeCreationPacket, true);
      int nEdited = this.sendToServer(this.editedIndexes, EditObjectMessage::forQuestObject, false);
      int nDeleted = this.sendToServer(this.pendingDeleteIndexes, DeleteObjectMessage::forQuestObject, false);
      FTBQuests.LOGGER.debug("Sent {} new, {} edited, {} deleted reward tables to server", nAdded, nEdited, nDeleted);
      this.questScreen.run();
   }

   private static CreateObjectMessage makeCreationPacket(RewardTable table) {
      ClientQuestFile file = ClientQuestFile.INSTANCE;
      CompoundTag extra = (CompoundTag)Util.make(
         new CompoundTag(), tag -> file.getTranslationManager().addInitialTranslation(tag, file.getLocale(), TranslationKey.TITLE, table.getRawTitle())
      );
      return CreateObjectMessage.create(table, extra);
   }

   private <T extends CustomPacketPayload> int sendToServer(IntSet indexes, Function<RewardTable, T> func, boolean addNew) {
      int sent = 0;
      IntIterator var5 = indexes.iterator();

      while (var5.hasNext()) {
         int idx = (Integer)var5.next();
         if (idx >= 0 && idx < this.rewardTablesCopy.size()) {
            RewardTable table = this.rewardTablesCopy.get(idx);
            if (addNew && table.id == 0L || !addNew && table.id != 0L) {
               NetworkManager.sendToServer(func.apply(table));
               sent++;
            }
         }
      }

      return sent;
   }

   private class CustomTopPanel extends AbstractThreePanelScreen<ButtonPanel>.TopPanel {
      private CustomTopPanel() {
         super(RewardTablesScreen.this);
      }

      public void addWidgets() {
         this.add(RewardTablesScreen.this.addButton);
      }

      public void alignWidgets() {
         RewardTablesScreen.this.addButton
            .setPosAndSize(this.width - RewardTablesScreen.this.addButton.width - 2, 1, RewardTablesScreen.this.addButton.width, 20);
      }

      public void draw(GuiGraphics graphics, Theme theme, int x, int y, int w, int h) {
         super.draw(graphics, theme, x, y, w, h);
         theme.drawString(graphics, this.getGui().getTitle(), x + 6, y + 6, 2);
      }
   }

   private class RewardTableButton extends SimpleTextButton {
      private final RewardTable table;
      private final int idx;

      public RewardTableButton(Panel panel, RewardTable table, int idx) {
         super(panel, table.getTitle(), table.getIcon());
         this.table = table;
         this.idx = idx;
         this.setHeight(16);
         if (this.table.getLootCrate() != null) {
            this.title = this.title.copy().withStyle(ChatFormatting.YELLOW);
         }
      }

      public void onClicked(MouseButton button) {
         this.playClickSound();
         if (button.isLeft()) {
            if (this.getMouseX() > this.getX() + this.width - 13) {
               this.deleteRewardTable();
            } else if (this.getMouseX() > this.getX() + this.width - 26) {
               this.toggleLootCrate();
            } else {
               this.editRewardTable();
            }
         } else {
            List<ContextMenuItem> menu = List.of(
               new ContextMenuItem(Component.translatable("ftbquests.gui.edit"), ItemIcon.getItemIcon(Items.FEATHER), b -> this.editRewardTable()),
               new ContextMenuItem(
                  Component.translatable(RewardTablesScreen.this.pendingDeleteIndexes.contains(this.idx) ? "ftbquests.gui.restore" : "gui.remove"),
                  Icons.BIN,
                  b -> this.deleteRewardTable()
               ),
               new ContextMenuItem(this.getLootCrateText(), ItemIcon.getItemIcon((Item)ModItems.LOOTCRATE.get()), b -> this.toggleLootCrate()),
               new ContextMenuItem(
                  Component.translatable("ftbquests.gui.copy_id"), Icons.SETTINGS, b -> QuestScreen.setClipboardString(this.table.getCodeString())
               )
            );
            this.getGui().openContextMenu(menu);
         }
      }

      public void drawBackground(GuiGraphics graphics, Theme theme, int x, int y, int w, int h) {
         if (this.isMouseOver) {
            Color4I.WHITE.withAlpha(30).draw(graphics, x, y, w, h);
            ItemIcon.getItemIcon((Item)ModItems.LOOTCRATE.get()).draw(graphics, x + w - 26, y + 2, 12, 12);
            Icons.BIN.draw(graphics, x + w - 13, y + 2, 12, 12);
         }

         if (RewardTablesScreen.this.pendingDeleteIndexes.contains(this.idx)) {
            Color4I.RED.withAlpha(64).draw(graphics, x, y, w, h);
         } else if (RewardTablesScreen.this.rewardTablesCopy.get(this.idx).getId() == 0L) {
            Color4I.GREEN.withAlpha(64).draw(graphics, x, y, w, h);
         }

         Color4I.GRAY.withAlpha(40).draw(graphics, x, y + h, w, 1);
      }

      public void draw(GuiGraphics graphics, Theme theme, int x, int y, int w, int h) {
         super.draw(graphics, theme, x, y, w, h);
         if (RewardTablesScreen.this.pendingDeleteIndexes.contains(this.idx)) {
            Color4I.GRAY.draw(graphics, x + 20, y + h / 2, theme.getStringWidth(this.title), 1);
         } else if (RewardTablesScreen.this.rewardTablesCopy.get(this.idx).getId() == 0L) {
            Icons.ADD.draw(graphics, x + 24 + theme.getStringWidth(this.title), y + 2, 12, 12);
         }
      }

      private void editRewardTable() {
         new EditRewardTableScreen(RewardTablesScreen.this, this.table, editedReward -> {
            RewardTablesScreen.this.rewardTablesCopy.set(this.idx, editedReward);
            RewardTablesScreen.this.changed = true;
            RewardTablesScreen.this.editedIndexes.add(this.idx);
            editedReward.clearCachedData();
            RewardTablesScreen.this.refreshWidgets();
         }).openGui();
      }

      private void deleteRewardTable() {
         if (RewardTablesScreen.this.pendingDeleteIndexes.contains(this.idx)) {
            RewardTablesScreen.this.pendingDeleteIndexes.remove(this.idx);
         } else {
            RewardTablesScreen.this.pendingDeleteIndexes.add(this.idx);
         }

         RewardTablesScreen.this.changed = true;
         RewardTablesScreen.this.refreshWidgets();
      }

      private void toggleLootCrate() {
         LootCrate crate = this.table.toggleLootCrate();
         if (crate != null) {
            this.title = this.table.getMutableTitle().withStyle(ChatFormatting.YELLOW);
         } else {
            this.title = this.table.getTitle();
         }

         RewardTablesScreen.this.changed = true;
         RewardTablesScreen.this.editedIndexes.add(this.idx);
         RewardTablesScreen.this.refreshWidgets();
      }

      public void addMouseOverText(TooltipList list) {
         super.addMouseOverText(list);
         if (isKeyDown(290) || isShiftKeyDown() && isCtrlKeyDown()) {
            list.add(Component.literal(this.table.getCodeString()).withStyle(ChatFormatting.DARK_GRAY));
         }

         if (this.getMouseX() > this.getX() + this.width - 13) {
            list.add(Component.translatable(RewardTablesScreen.this.pendingDeleteIndexes.contains(this.idx) ? "ftbquests.gui.restore" : "gui.remove"));
         } else if (this.getMouseX() > this.getX() + this.width - 26) {
            list.add(this.getLootCrateText());
         } else {
            MutableInt usedIn = new MutableInt(0);
            ClientQuestFile.INSTANCE
               .forAllQuests(
                  quest -> quest.getRewards()
                     .stream()
                     .filter(reward -> reward instanceof RandomReward rr && rr.getTable() != null && rr.getTable().id == this.table.id)
                     .forEach(reward -> usedIn.increment())
               );
            list.add(Component.translatable("ftbquests.reward_table.used_in", new Object[]{usedIn}));
            this.table.addMouseOverText(list, true, true);
         }
      }

      @NotNull
      private Component getLootCrateText() {
         return Component.translatable("ftbquests.reward_table." + (this.table.getLootCrate() != null ? "disable_loot_crate" : "enable_loot_crate"));
      }
   }
}
