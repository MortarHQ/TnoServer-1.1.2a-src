package dev.ftbqlang.client;

import dev.ftb.mods.ftblibrary.ui.Panel;
import dev.ftb.mods.ftblibrary.ui.ScreenWrapper;
import dev.ftb.mods.ftblibrary.ui.Widget;
import dev.ftb.mods.ftbquests.client.ClientQuestFile;
import dev.ftb.mods.ftbquests.client.gui.quests.QuestScreen;
import dev.ftbqlang.network.LanguageNetwork;
import dev.ftbqlang.network.LanguageStatePayload;
import dev.ftbqlang.network.QueryPayload;
import dev.ftbqlang.network.SelectLanguagePayload;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.PacketDistributor;

public final class QuestLanguageClient {
   private static List<String> languages = List.of();
   private static String selected = "";
   private static boolean initialQuerySent;
   private static boolean bookWasOpen;
   private static ClientQuestFile syncedFile;
   private static final SelectorSession selector = new SelectorSession();
   private static LanguageSelectionPanel activePanel;
   private static boolean manualRequest;
   private static boolean receivedState;
   private static ClientQuestFile pendingTranslationRefresh;

   private QuestLanguageClient() {
   }

   public static void reset() {
      languages = List.of();
      selected = "";
      initialQuerySent = false;
      bookWasOpen = false;
      syncedFile = null;
      selector.reset();
      activePanel = null;
      manualRequest = false;
      receivedState = false;
      pendingTranslationRefresh = null;
      LanguageNetwork.setClientHandler(QuestLanguageClient::receive);
   }

   public static String localeOverride() {
      return selected;
   }

   public static List<String> languages() {
      return languages;
   }

   public static boolean hasMultipleLanguages() {
      return languages.size() > 1;
   }

   public static void tick() {
      Minecraft minecraft = Minecraft.getInstance();
      if (minecraft.player != null && ClientQuestFile.exists()) {
         if (syncedFile != ClientQuestFile.INSTANCE) {
            syncedFile = ClientQuestFile.INSTANCE;
            initialQuerySent = false;
         }

         if (!initialQuerySent) {
            initialQuerySent = true;
            PacketDistributor.sendToServer(new QueryPayload(false), new CustomPacketPayload[0]);
         }

         QuestScreen screen = currentQuestScreen();
         if (screen == null) {
            bookWasOpen = false;
            selector.leftBook();
            activePanel = null;
         } else if (activePanel != null && activePanel.getGui() == screen && activePanel.isExitComplete()) {
            LanguageSelectionPanel closingPanel = activePanel;
            screen.closeModalPanel(closingPanel);
            applyPendingTranslationRefresh(screen);
         } else {
            if (!bookWasOpen) {
               bookWasOpen = true;
               PacketDistributor.sendToServer(new QueryPayload(true), new CustomPacketPayload[0]);
            }

            if (receivedState && hasMultipleLanguages() && selector.shouldAttach(screen)) {
               selector.attached(screen);
               activePanel = new LanguageSelectionPanel(screen, languages, selected);
               screen.pushModalPanel(activePanel);
            }

            applyPendingTranslationRefresh(screen);
         }
      }
   }

   public static void selectorClosed(QuestScreen host) {
      selector.dismissed(host);
      if (activePanel != null && activePanel.getGui() == host) {
         activePanel = null;
      }
   }

   public static boolean blocksBookClose(QuestScreen host) {
      return selector.isAttachedTo(host);
   }

   public static int modalBackgroundColor(Object host, int color) {
      if (activePanel != null && activePanel.getGui() == host) {
         int alpha = (int)Math.round((color >>> 24) * activePanel.backdropOpacity());
         return color & 16777215 | alpha << 24;
      } else {
         return color;
      }
   }

   public static void refreshTranslations(ClientQuestFile file) {
      file.clearCachedData();
      pendingTranslationRefresh = file;
   }

   private static void applyPendingTranslationRefresh(QuestScreen screen) {
      if (pendingTranslationRefresh != null && !blocksBookClose(screen)) {
         ClientQuestFile file = pendingTranslationRefresh;
         pendingTranslationRefresh = null;
         if (file == ClientQuestFile.INSTANCE && file.getQuestScreen().orElse(null) == screen) {
            screen.refreshChapterPanel();
            refreshPanelPreservingScroll(screen.questPanel);
            screen.otherButtonsBottomPanel.refreshWidgets();
            screen.otherButtonsTopPanel.refreshWidgets();
            if (screen.isViewingQuest()) {
               refreshPanelPreservingScroll(screen.viewQuestPanel);
            }
         }
      }
   }

   private static void refreshPanelPreservingScroll(Panel panel) {
      double x = panel.getScrollX();
      double y = panel.getScrollY();
      Panel content = firstChildPanel(panel);
      double contentX = content == null ? 0.0 : content.getScrollX();
      double contentY = content == null ? 0.0 : content.getScrollY();
      panel.refreshWidgets();
      panel.setScrollX(x);
      panel.setScrollY(y);
      Panel refreshedContent = firstChildPanel(panel);
      if (content != null && refreshedContent != null) {
         refreshedContent.setScrollX(contentX);
         refreshedContent.setScrollY(Math.clamp(contentY, 0.0, Math.max(0, refreshedContent.getContentHeight() - refreshedContent.getHeight())));
      }
   }

   private static Panel firstChildPanel(Panel panel) {
      for (Widget widget : panel.getWidgets()) {
         if (widget instanceof Panel child) {
            return child;
         }
      }

      return null;
   }

   public static void openSelector() {
      if (Minecraft.getInstance().player != null && !manualRequest) {
         manualRequest = true;
         PacketDistributor.sendToServer(new QueryPayload(true), new CustomPacketPayload[0]);
      }
   }

   public static void selectLanguage(String locale) {
      if (languages.contains(locale)) {
         PacketDistributor.sendToServer(new SelectLanguagePayload(locale), new CustomPacketPayload[0]);
      }
   }

   private static void receive(LanguageStatePayload payload) {
      if (Minecraft.getInstance().player != null) {
         languages = List.copyOf(payload.languages());
         selected = payload.selected();
         receivedState = true;
         if (!hasMultipleLanguages()) {
            selector.reset();
         } else if (payload.prompt() || manualRequest) {
            selector.requestOpen();
         }

         manualRequest = false;
         if (ClientQuestFile.exists()) {
            refreshTranslations(ClientQuestFile.INSTANCE);
         }
      }
   }

   private static QuestScreen currentQuestScreen() {
      return Minecraft.getInstance().screen instanceof ScreenWrapper wrapper && wrapper.getGui() instanceof QuestScreen quests ? quests : null;
   }
}
