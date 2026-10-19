package com.nododiiiii.ponderer.ui;

import com.nododiiiii.ponderer.ponder.PackStateStore;
import com.nododiiiii.ponderer.ponder.PonderPackInfo;
import com.nododiiiii.ponderer.ponder.SceneStore;
import com.nododiiiii.ponderer.ui.catnip.AbstractReadonlyDeclarativeListScreen;
import com.nododiiiii.ponderer.ui.catnip.FullButtonListEntry;
import com.nododiiiii.ponderer.ui.catnip.SectionHeaderListEntry;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Nullable;
import net.createmod.catnip.config.ui.ConfigScreenList.Entry;
import net.createmod.ponder.foundation.PonderIndex;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class ImportPackScreen extends AbstractReadonlyDeclarativeListScreen {
   private final List<ImportPackScreen.PackEntryRow> packEntries = new ArrayList<>();

   public ImportPackScreen() {
      super(new FunctionScreen(), "ponderer.ui.scope.editor", "ponderer.ui.function_page.import.title", 300);
   }

   @Override
   protected void init() {
      this.scanPacks();
      super.init();
   }

   @Override
   protected void collectEntries(List<Entry> entries) {
      if (this.packEntries.isEmpty()) {
         entries.add(new SectionHeaderListEntry(UIText.of("ponderer.ui.import.none")));
      } else {
         entries.add(new SectionHeaderListEntry(UIText.of("ponderer.ui.import.available")));

         for (ImportPackScreen.PackEntryRow row : this.packEntries) {
            entries.add(new FullButtonListEntry(this.buildLabel(row), this.buildSubtitle(row), () -> this.onPackSelected(row)));
         }
      }
   }

   @Override
   protected int getEntryHeight() {
      return 24;
   }

   private void scanPacks() {
      this.packEntries.clear();
      PackStateStore.load();
      Map<String, ImportPackScreen.PackEntryRow> merged = new LinkedHashMap<>();

      for (PonderPackInfo info : SceneStore.scanAvailableSourcePacks()) {
         PackStateStore.ImportedPackState imported = PackStateStore.getImportedPack(info.name);
         ImportPackScreen.PackEntryState state = PackStateStore.isImported(info.name)
            ? (this.isSourceNewer(imported, info.version) ? ImportPackScreen.PackEntryState.SOURCE_NEWER : ImportPackScreen.PackEntryState.IMPORTED_LOCAL)
            : ImportPackScreen.PackEntryState.READONLY_AVAILABLE;
         merged.put(
            info.name,
            new ImportPackScreen.PackEntryRow(
               info.name, info.name, info.author, info.version, imported != null ? imported.importedVersion : null, info.sourcePath, state
            )
         );
      }

      for (PackStateStore.ImportedPackState imported : PackStateStore.getImportedPacks()) {
         if (imported.packId != null && !imported.packId.isBlank() && PackStateStore.isImported(imported.packId) && !merged.containsKey(imported.packId)) {
            merged.put(
               imported.packId,
               new ImportPackScreen.PackEntryRow(
                  imported.packId,
                  imported.displayName != null && !imported.displayName.isBlank() ? imported.displayName : imported.packId,
                  null,
                  null,
                  imported.importedVersion,
                  null,
                  ImportPackScreen.PackEntryState.LOCAL_ONLY
               )
            );
         }
      }

      this.packEntries.addAll(merged.values().stream().sorted(Comparator.comparing(row -> row.displayName().toLowerCase(Locale.ROOT))).toList());
   }

   private boolean isSourceNewer(@Nullable PackStateStore.ImportedPackState imported, @Nullable String sourceVersion) {
      return imported != null
         && imported.importedVersion != null
         && !imported.importedVersion.isBlank()
         && sourceVersion != null
         && !sourceVersion.isBlank()
         && !sourceVersion.equals(imported.importedVersion);
   }

   private String buildLabel(ImportPackScreen.PackEntryRow row) {
      StringBuilder label = new StringBuilder(row.displayName());
      if (row.sourceVersion() != null && !row.sourceVersion().isBlank()) {
         label.append(" v").append(row.sourceVersion());
      } else if (row.importedVersion() != null && !row.importedVersion().isBlank()) {
         label.append(" v").append(row.importedVersion());
      }

      if (row.author() != null && !row.author().isBlank()) {
         label.append(" | ").append(row.author());
      }

      return label.toString();
   }

   private String buildSubtitle(ImportPackScreen.PackEntryRow row) {
      return switch (row.state()) {
         case READONLY_AVAILABLE -> UIText.of("ponderer.ui.import.status.readonly");
         case IMPORTED_LOCAL -> UIText.of("ponderer.ui.import.status.imported", this.versionLabel(row.importedVersion()));
         case SOURCE_NEWER -> UIText.of(
            "ponderer.ui.import.status.newer_source", this.versionLabel(row.importedVersion()), this.versionLabel(row.sourceVersion())
         );
         case LOCAL_ONLY -> UIText.of("ponderer.ui.import.status.local_only", this.versionLabel(row.importedVersion()));
      };
   }

   private String versionLabel(@Nullable String version) {
      return version != null && !version.isBlank() ? "v" + version : "?";
   }

   private void onPackSelected(ImportPackScreen.PackEntryRow row) {
      switch (row.state()) {
         case READONLY_AVAILABLE:
            this.importPack(row);
            break;
         case IMPORTED_LOCAL:
            this.notifyUser(UIText.of("ponderer.ui.import.already_imported", row.displayName()));
            break;
         case SOURCE_NEWER:
            this.notifyUser(
               UIText.of("ponderer.ui.import.newer_source", row.displayName(), this.versionLabel(row.sourceVersion()), this.versionLabel(row.importedVersion()))
            );
            break;
         case LOCAL_ONLY:
            this.notifyUser(UIText.of("ponderer.ui.import.local_only", row.displayName()));
      }
   }

   private void importPack(ImportPackScreen.PackEntryRow row) {
      if (row.sourcePath() == null) {
         this.notifyUser(UIText.of("ponderer.ui.import.failed", "Missing source pack"));
      } else {
         SceneStore.PackImportResult result = SceneStore.importPackFromResourcePack(row.sourcePath());
         if (result.isSuccess()) {
            SceneStore.autoLoadPonderPacks();
            SceneStore.reloadFromDisk();
            Minecraft.getInstance().execute(PonderIndex::reload);
            this.notifyUser(UIText.of(result.uiMessageKey(), result.uiMessageArgs()));
            Minecraft.getInstance().setScreen(new ImportPackScreen());
         } else {
            String key = result.uiMessageKey();
            this.notifyUser(key != null && !key.isBlank() ? UIText.of(key, result.uiMessageArgs()) : result.englishMessage());
         }
      }
   }

   private void notifyUser(String message) {
      if (Minecraft.getInstance().player != null) {
         Minecraft.getInstance().player.displayClientMessage(Component.literal(message), false);
      }
   }

   private record PackEntryRow(
      String packId,
      String displayName,
      @Nullable String author,
      @Nullable String sourceVersion,
      @Nullable String importedVersion,
      @Nullable Path sourcePath,
      ImportPackScreen.PackEntryState state
   ) {
   }

   private enum PackEntryState {
      READONLY_AVAILABLE,
      IMPORTED_LOCAL,
      SOURCE_NEWER,
      LOCAL_ONLY;
   }
}
