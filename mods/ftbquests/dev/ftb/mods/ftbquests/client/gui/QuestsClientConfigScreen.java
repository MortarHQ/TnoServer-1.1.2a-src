package dev.ftb.mods.ftbquests.client.gui;

import dev.architectury.networking.NetworkManager;
import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftblibrary.config.ui.EditConfigScreen;
import dev.ftb.mods.ftbquests.client.ClientQuestFile;
import dev.ftb.mods.ftbquests.net.RequestTranslationTableMessage;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public class QuestsClientConfigScreen extends EditConfigScreen {
   private final String prevLocale;
   private final String prevFallback;
   private final boolean pause;

   public QuestsClientConfigScreen(ConfigGroup group, boolean pause) {
      super(group);
      this.pause = pause;
      this.prevLocale = ClientQuestFile.INSTANCE.getLocale();
      this.prevFallback = ClientQuestFile.INSTANCE.getFallbackLocale();
      this.setAutoclose(true);
   }

   public Component getTitle() {
      return Component.translatable("ftbquests.gui.preferences").withStyle(ChatFormatting.UNDERLINE);
   }

   public boolean doesGuiPauseGame() {
      return this.pause;
   }

   protected void doAccept() {
      super.doAccept();
      ClientQuestFile file = ClientQuestFile.INSTANCE;
      if (file != null) {
         List<CustomPacketPayload> toSend = new ArrayList<>();
         if (!this.prevLocale.equals(file.getLocale())) {
            toSend.add(new RequestTranslationTableMessage(file.getLocale()));
         }

         if (!this.prevFallback.equals(file.getFallbackLocale())) {
            toSend.add(new RequestTranslationTableMessage(file.getFallbackLocale()));
         }

         if (!toSend.isEmpty()) {
            toSend.forEach(NetworkManager::sendToServer);
            file.clearCachedData();
         }
      }
   }
}
