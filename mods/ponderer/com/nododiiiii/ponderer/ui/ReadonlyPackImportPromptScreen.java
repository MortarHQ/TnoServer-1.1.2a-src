package com.nododiiiii.ponderer.ui;

import com.nododiiiii.ponderer.ponder.DslScene;
import com.nododiiiii.ponderer.ponder.PonderPackInfo;
import com.nododiiiii.ponderer.ponder.SceneRuntime;
import com.nododiiiii.ponderer.ponder.SceneStore;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.createmod.ponder.foundation.PonderIndex;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ReadonlyPackImportPromptScreen {
   private final Screen source;
   private final String packId;
   private final String sceneKey;
   private final int sceneIndex;

   public ReadonlyPackImportPromptScreen(@Nonnull Screen source, String packId, String sceneKey, int sceneIndex) {
      this.source = source;
      this.packId = packId;
      this.sceneKey = sceneKey;
      this.sceneIndex = sceneIndex;
   }

   public void open() {
      new PondererDialogScreen(
            this.source,
            List.of(Component.translatable("ponderer.ui.readonly_import_prompt.title")),
            List.of(Component.translatable("ponderer.ui.readonly_import_prompt.message")),
            List.of(
               PondererDialogScreen.button(
                  Component.translatable("ponderer.ui.readonly_import_prompt.import", new Object[]{"[" + this.packId + "]"}), this::importAndEdit
               ),
               PondererDialogScreen.closeButton(Component.translatable("ponderer.ui.cancel"))
            )
         )
         .open();
   }

   private void importAndEdit(PondererDialogScreen dialog) {
      PonderPackInfo info = findSourcePack(this.packId);
      if (info != null && info.sourcePath != null) {
         SceneStore.PackImportResult result = SceneStore.importPackFromResourcePack(info.sourcePath);
         if (result.isSuccess()) {
            SceneStore.autoLoadPonderPacks();
            SceneStore.reloadFromDisk();
            Minecraft.getInstance().execute(PonderIndex::reload);
            DslScene importedScene = SceneRuntime.findByKey(this.sceneKey);
            this.notifyUser(Component.literal(UIText.of(result.uiMessageKey(), result.uiMessageArgs())));
            if (importedScene == null) {
               dialog.closeToSource();
            } else {
               SceneEditorScreen.markUiToEditorTransition(importedScene);
               Minecraft.getInstance().setScreen(new SceneEditorScreen(importedScene, this.sceneIndex));
            }
         } else {
            dialog.closeToSource();
            String key = result.uiMessageKey();
            this.notifyUser(
               key != null && !key.isBlank() ? Component.literal(UIText.of(key, result.uiMessageArgs())) : Component.literal(result.englishMessage())
            );
         }
      } else {
         dialog.closeToSource();
         this.notifyUser(Component.literal(UIText.of("ponderer.ui.import.failed", UIText.of("ponderer.ui.readonly_import_prompt.missing_source", this.packId))));
      }
   }

   @Nullable
   private static PonderPackInfo findSourcePack(String packId) {
      for (PonderPackInfo info : SceneStore.scanAvailableSourcePacks()) {
         if (packId.equals(info.name)) {
            return info;
         }
      }

      return null;
   }

   private void notifyUser(Component message) {
      if (Minecraft.getInstance().player != null) {
         Minecraft.getInstance().player.displayClientMessage(message, false);
      }
   }
}
