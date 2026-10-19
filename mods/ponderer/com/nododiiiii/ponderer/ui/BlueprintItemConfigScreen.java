package com.nododiiiii.ponderer.ui;

import com.nododiiiii.ponderer.Config;
import com.nododiiiii.ponderer.compat.jei.JeiCompat;
import com.nododiiiii.ponderer.network.BlueprintConfigRequestPayload;
import com.nododiiiii.ponderer.network.BlueprintConfigResponsePayload;
import com.nododiiiii.ponderer.network.BlueprintConfigUpdatePayload;
import com.nododiiiii.ponderer.platform.PondererServices;
import com.nododiiiii.ponderer.ui.catnip.DeclarativeFormEntry;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;

public class BlueprintItemConfigScreen extends AbstractJeiAwareFormScreen {
   private static final String ENABLE_BUILTIN_KEY = "enable_builtin";
   private static final String CARRIER_ITEM_KEY = "carrier_item";
   private boolean enableBuiltinItem = readEnableBuiltinItem();
   private boolean serverEnableBuiltinItem = this.enableBuiltinItem;
   private boolean serverStateRequested;
   private boolean serverStateLoaded;
   private boolean waitingForServerUpdate;
   private boolean viewerCanManage;
   private String carrierItem = readCarrierItem();

   public BlueprintItemConfigScreen(Screen parent) {
      super(parent, "ponderer.ui.scope.blueprint", "ponderer.ui.function_page.blueprint_item.title", 300, JeiCompat::setActiveScreen);
   }

   @Override
   protected void init() {
      super.init();
      this.requestServerStateIfNeeded();
   }

   @Override
   protected void collectFormEntries(List<DeclarativeFormEntry> entries) {
      entries.add(
         FieldSpecs.toggle(
            "ponderer.ui.function_page.blueprint_item.use_builtin",
            "ponderer.ui.function_page.blueprint_item.use_builtin.tooltip",
            () -> this.enableBuiltinItem,
            this::toggleEnableBuiltinItem
         )
      );
      entries.add(
         FieldSpecs.text(
            FieldBindings.transientString(() -> this.carrierItem, value -> this.carrierItem = value == null ? "" : value),
            "ponderer.ui.function_page.blueprint_item.carrier",
            "ponderer.ui.function_page.blueprint_item.carrier.tooltip",
            "ponderer.ui.function_page.blueprint_item.carrier.hint",
            -1,
            FieldDecorators.jei(IdFieldMode.ITEM)
         )
      );
   }

   @Override
   protected boolean saveEdits() {
      this.clearStatusMessages();
      String normalizedCarrier = this.carrierItem == null ? "" : this.carrierItem.trim();
      if (normalizedCarrier.isEmpty()) {
         this.setErrorMessage(UIText.of("ponderer.ui.error.required_field", UIText.of("ponderer.ui.function_page.blueprint_item.carrier")));
         return false;
      }

      if (ResourceLocation.tryParse(normalizedCarrier) == null) {
         this.setErrorMessage(UIText.of("ponderer.ui.create_item_entity.error.invalid_id"));
         return false;
      }

      this.deactivateJei();
      Config.BLUEPRINT_CARRIER_ITEM.set(normalizedCarrier);
      this.carrierItem = normalizedCarrier;
      this.updateBaseline(null, normalizedCarrier);
      if (this.isEnableBuiltinDirty()) {
         if (!this.serverStateLoaded) {
            this.requestServerStateIfNeeded();
            this.setErrorMessage(UIText.of("ponderer.ui.function_page.blueprint_item.wait_server"));
            return false;
         } else if (!this.viewerCanManage) {
            this.enableBuiltinItem = this.serverEnableBuiltinItem;
            this.setErrorMessage(UIText.of("ponderer.ui.function_page.blueprint_item.admin_required"));
            this.rebuildListPreservingScroll();
            return false;
         } else {
            this.waitingForServerUpdate = true;
            this.setInfoMessage(UIText.of("ponderer.ui.function_page.blueprint_item.saving"));
            PondererServices.NETWORK.sendToServer(new BlueprintConfigUpdatePayload(this.enableBuiltinItem));
            this.rebuildListPreservingScroll();
            return false;
         }
      } else {
         this.setInfoMessage(UIText.of("ponderer.ui.function_page.blueprint_item.set", normalizedCarrier));
         return true;
      }
   }

   @Override
   protected boolean isSaveButtonActive() {
      return !this.waitingForServerUpdate && this.hasUnsavedChanges();
   }

   public void receiveServerState(BlueprintConfigResponsePayload payload) {
      this.waitingForServerUpdate = false;
      boolean firstLoad = !this.serverStateLoaded;
      this.serverStateLoaded = true;
      this.viewerCanManage = payload.canManage();
      this.serverEnableBuiltinItem = payload.enableBuiltinItem();
      this.enableBuiltinItem = payload.enableBuiltinItem();
      writeLocalEnableBuiltinItem(payload.enableBuiltinItem());
      this.updateBaseline(payload.enableBuiltinItem(), null);
      String messageKey = payload.messageKey();
      if (messageKey != null && !messageKey.isBlank()) {
         if (payload.error()) {
            this.setErrorMessage(UIText.of(messageKey));
         } else {
            this.setInfoMessage(UIText.of(messageKey));
         }
      } else if (firstLoad) {
         this.setInfoMessage(UIText.of("ponderer.ui.function_page.blueprint_item.loaded"));
      }

      this.rebuildListPreservingScroll();
   }

   @Override
   protected Map<String, String> snapshotState() {
      Map<String, String> snapshot = new LinkedHashMap<>();
      snapshot.put("enable_builtin", String.valueOf(this.enableBuiltinItem));
      snapshot.put("carrier_item", this.carrierItem == null ? "" : this.carrierItem);
      return snapshot;
   }

   @Override
   protected void restoreSnapshot(Map<String, String> snapshot) {
      this.enableBuiltinItem = Boolean.parseBoolean(snapshot.getOrDefault("enable_builtin", String.valueOf(readEnableBuiltinItem())));
      this.carrierItem = snapshot.getOrDefault("carrier_item", readCarrierItem());
   }

   private void toggleEnableBuiltinItem() {
      if (this.waitingForServerUpdate) {
         this.setInfoMessage(UIText.of("ponderer.ui.function_page.blueprint_item.saving"));
      } else if (!this.serverStateLoaded) {
         this.requestServerStateIfNeeded();
         this.setErrorMessage(UIText.of("ponderer.ui.function_page.blueprint_item.wait_server"));
      } else if (!this.viewerCanManage) {
         this.enableBuiltinItem = this.serverEnableBuiltinItem;
         this.setErrorMessage(UIText.of("ponderer.ui.function_page.blueprint_item.admin_required"));
         this.rebuildListPreservingScroll();
      } else {
         this.enableBuiltinItem = !this.enableBuiltinItem;
         this.clearStatusMessages();
      }
   }

   private void requestServerStateIfNeeded() {
      if (!this.serverStateRequested && !this.serverStateLoaded) {
         this.serverStateRequested = true;
         this.setInfoMessage(UIText.of("ponderer.ui.function_page.blueprint_item.loading"));
         PondererServices.NETWORK.sendToServer(new BlueprintConfigRequestPayload());
      }
   }

   private boolean isEnableBuiltinDirty() {
      String baselineValue = this.isBaselineCaptured() ? this.baselineStateSnapshot().get("enable_builtin") : null;
      boolean baseline = baselineValue == null ? this.serverEnableBuiltinItem : Boolean.parseBoolean(baselineValue);
      return this.enableBuiltinItem != baseline;
   }

   private void updateBaseline(@Nullable Boolean enableBuiltin, @Nullable String carrier) {
      Map<String, String> baseline = new LinkedHashMap<>();
      if (this.isBaselineCaptured()) {
         baseline.putAll(this.baselineStateSnapshot());
      } else {
         baseline.putAll(this.snapshotState());
      }

      if (enableBuiltin != null) {
         baseline.put("enable_builtin", String.valueOf(enableBuiltin));
      }

      if (carrier != null) {
         baseline.put("carrier_item", carrier);
      }

      this.restoreBaselineState(baseline);
   }

   private static void writeLocalEnableBuiltinItem(boolean value) {
      try {
         Config.ENABLE_BLUEPRINT_ITEM.set(value);
      } catch (Exception var2) {
      }
   }

   private static boolean readEnableBuiltinItem() {
      try {
         return (Boolean)Config.ENABLE_BLUEPRINT_ITEM.get();
      } catch (Exception e) {
         return false;
      }
   }

   private static String readCarrierItem() {
      try {
         String value = (String)Config.BLUEPRINT_CARRIER_ITEM.get();
         return value != null && !value.isBlank() ? value : "minecraft:paper";
      } catch (Exception e) {
         return "minecraft:paper";
      }
   }
}
