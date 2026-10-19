package com.nododiiiii.ponderer.ui;

import com.nododiiiii.ponderer.ponder.DslScene;
import com.nododiiiii.ponderer.ui.catnip.DeclarativeFormEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.createmod.catnip.gui.ScreenOpener;
import net.createmod.catnip.gui.ConfirmationScreen.Response;
import net.createmod.catnip.gui.widget.BoxWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.ItemStack;

public abstract class AbstractSceneEditorFormScreen extends AbstractJeiAwareFormScreen implements HeldItemButtonHost, NbtPickButtonHost {
   protected final DslScene scene;
   protected final int sceneIndex;
   protected final SceneEditorScreen parent;
   @Nullable
   protected Screen returnScreen;
   @Nullable
   protected BoxWidget confirmButton;
   @Nullable
   protected BoxWidget cancelButton;
   private final List<SnapshotParticipant> formStateParticipants = new ArrayList<>();
   private boolean formStateParticipantsConfigured = false;
   private boolean initialStatePrepared = false;
   @Nullable
   private Map<String, String> pendingFormRestore = null;
   @Nullable
   private Map<String, String> pendingBaselineRestore = null;

   protected AbstractSceneEditorFormScreen(
      DslScene scene,
      int sceneIndex,
      SceneEditorScreen parent,
      String scopeKey,
      String titleKey,
      int preferredListWidth,
      BiConsumer<JeiAwareScreen, IdFieldMode> activationAction
   ) {
      super(parent, scopeKey, titleKey, preferredListWidth, activationAction);
      this.scene = scene;
      this.sceneIndex = sceneIndex;
      this.parent = parent;
   }

   public AbstractSceneEditorFormScreen setReturnScreen(@Nullable Screen returnScreen) {
      this.returnScreen = returnScreen;
      return this;
   }

   public AbstractSceneEditorFormScreen setPendingFormRestore(@Nullable Map<String, String> snapshot) {
      this.pendingBaselineRestore = null;
      if (snapshot == null) {
         this.pendingFormRestore = null;
         return this;
      }

      this.pendingFormRestore = new HashMap<>(snapshot);
      this.pendingBaselineRestore = this.extractBaselineSnapshotMetadata(this.pendingFormRestore);
      if (this.pendingFormRestore != null) {
         this.prepareSnapshotForBuild(this.pendingFormRestore);
      }

      return this;
   }

   @Override
   protected boolean shouldAutoCaptureBaselineOnInit() {
      return false;
   }

   @Override
   protected void init() {
      this.ensureFormStateParticipants();
      if (!this.initialStatePrepared) {
         this.prepareInitialState();
         this.initialStatePrepared = true;
      }

      if (this.pendingFormRestore != null) {
         this.prepareSnapshotForBuild(this.pendingFormRestore);
      }

      super.init();
      this.confirmButton = this.saveChanges;
      this.cancelButton = this.goBack;
      this.configureActionButtons();
      if (this.pendingFormRestore != null) {
         this.restoreFromSnapshot(this.pendingFormRestore);
         if (this.pendingBaselineRestore != null) {
            this.restoreBaselineState(this.pendingBaselineRestore);
         }

         this.pendingFormRestore = null;
         this.pendingBaselineRestore = null;
      } else if (!this.isBaselineCaptured()) {
         this.markStateSaved();
      }
   }

   @Override
   protected void renderWindow(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
      super.renderWindow(graphics, mouseX, mouseY, partialTicks);
      this.renderFormForeground(graphics, mouseX, mouseY, partialTicks);
   }

   @Override
   protected void attemptBackToParent() {
      if (!this.hasUnsavedChanges()) {
         this.returnToParent();
      } else {
         this.showLeavingPrompt(response -> {
            if (response != Response.Cancel) {
               if (response == Response.Confirm) {
                  if (!this.saveEdits()) {
                     return;
                  }
               } else {
                  this.discardEdits();
               }

               this.returnToParent();
            }
         });
      }
   }

   @Override
   public void onClose() {
      this.attemptBackToParent();
   }

   protected void prepareInitialState() {
   }

   protected void configureActionButtons() {
   }

   protected void renderFormForeground(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
   }

   protected String getHeaderTitle() {
      return UIText.of(this.titleKey);
   }

   @Override
   protected String getBreadcrumbTitleText() {
      return this.getHeaderTitle();
   }

   protected void returnToParent() {
      ScreenOpener.open((Screen)(this.returnScreen != null ? this.returnScreen : this.parent));
   }

   protected void addBaseFormStateParticipants(List<SnapshotParticipant> participants) {
   }

   protected void configureFormState(List<SnapshotParticipant> participants) {
   }

   @Override
   protected final Map<String, String> snapshotState() {
      Map<String, String> snapshot = new HashMap<>();

      for (DeclarativeFormEntry entry : this.builtFormEntries()) {
         entry.snapshot(snapshot);
      }

      FormState.snapshotOf(this.formStateParticipants).forEach(snapshot::put);
      this.appendCustomSnapshot(snapshot);
      return snapshot;
   }

   @Override
   protected final void restoreSnapshot(Map<String, String> snapshot) {
      FormState.restoreInto(snapshot, this.formStateParticipants);

      for (DeclarativeFormEntry entry : this.builtFormEntries()) {
         entry.restore(snapshot);
      }

      this.restoreCustomSnapshot(snapshot);
   }

   protected void appendCustomSnapshot(Map<String, String> snapshot) {
   }

   protected void restoreCustomSnapshot(Map<String, String> snapshot) {
   }

   protected final Map<String, String> snapshotForm() {
      Map<String, String> snapshot = this.snapshotState();
      this.appendBaselineSnapshotMetadata(snapshot);
      return snapshot;
   }

   protected final void restoreFromSnapshot(Map<String, String> snapshot) {
      this.restoreSnapshot(snapshot);
   }

   @Override
   protected void prepareSnapshotForBuild(Map<String, String> snapshot) {
      FormState.restoreInto(snapshot, this.formStateParticipants);
      this.restoreCustomSnapshot(snapshot);
   }

   @Override
   public final void startNbtPickFromButton(String nbtSnapshotKey, boolean captureBlockId) {
      Minecraft mc = Minecraft.getInstance();
      if (mc.player != null) {
         this.clearStatusMessages();
         NbtPickState.startPick(this.snapshotForm(), nbtSnapshotKey, captureBlockId, this.createReturnContext());
         mc.setScreen(null);
      }
   }

   @Override
   public final void useHeldItemFromButton(Consumer<ItemStack> onItemPicked) {
      Minecraft mc = Minecraft.getInstance();
      if (mc.player != null) {
         ItemStack held = mc.player.getMainHandItem();
         if (held.isEmpty()) {
            held = mc.player.getOffhandItem();
         }

         if (held.isEmpty()) {
            this.setErrorMessage(UIText.of("ponderer.ui.held_item.error.empty"));
         } else {
            this.clearStatusMessages();
            onItemPicked.accept(held);
            this.setInfoMessage(UIText.of("ponderer.ui.nbt_pick.filled", held.getHoverName().getString()));
         }
      }
   }

   protected void restoreNbtPickNotice(Map<String, String> snapshot) {
      if (snapshot.containsKey("_nbt_pick_notice")) {
         String pickedName = snapshot.get("_nbt_pick_notice");
         String translated = UIText.of("ponderer.ui.nbt_pick.filled", pickedName);
         this.setInfoMessage("ponderer.ui.nbt_pick.filled".equals(translated) ? "NBT <- " + pickedName : translated);
      }
   }

   @Nullable
   protected Double parseDouble(String value, String fieldName) {
      if (value != null && !value.trim().isEmpty()) {
         try {
            return Double.parseDouble(value.trim());
         } catch (NumberFormatException e) {
            this.setErrorMessage(UIText.of("ponderer.ui.error.invalid_number", fieldName));
            return null;
         }
      } else {
         this.setErrorMessage(UIText.of("ponderer.ui.error.required_field", fieldName));
         return null;
      }
   }

   protected double parseDoubleOr(String value, double fallback) {
      if (value != null && !value.trim().isEmpty()) {
         try {
            return Double.parseDouble(value.trim());
         } catch (NumberFormatException e) {
            return fallback;
         }
      } else {
         return fallback;
      }
   }

   @Nullable
   protected Float parseFloat(String value, String fieldName) {
      Double d = this.parseDouble(value, fieldName);
      return d == null ? null : d.floatValue();
   }

   @Nullable
   protected Integer parseInt(String value, String fieldName) {
      if (value != null && !value.trim().isEmpty()) {
         try {
            return Integer.parseInt(value.trim());
         } catch (NumberFormatException e) {
            this.setErrorMessage(UIText.of("ponderer.ui.error.invalid_integer", fieldName));
            return null;
         }
      } else {
         this.setErrorMessage(UIText.of("ponderer.ui.error.required_field", fieldName));
         return null;
      }
   }

   protected int parseIntOr(String value, int fallback) {
      if (value != null && !value.trim().isEmpty()) {
         try {
            return Integer.parseInt(value.trim());
         } catch (NumberFormatException e) {
            return fallback;
         }
      } else {
         return fallback;
      }
   }

   protected abstract SnapshotReturnContext createReturnContext();

   private void ensureFormStateParticipants() {
      if (!this.formStateParticipantsConfigured) {
         this.formStateParticipantsConfigured = true;
         this.addBaseFormStateParticipants(this.formStateParticipants);
         this.configureFormState(this.formStateParticipants);
      }
   }
}
