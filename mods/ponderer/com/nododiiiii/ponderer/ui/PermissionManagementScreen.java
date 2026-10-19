package com.nododiiiii.ponderer.ui;

import com.nododiiiii.ponderer.network.PermissionListRequestPayload;
import com.nododiiiii.ponderer.network.PermissionListResponsePayload;
import com.nododiiiii.ponderer.network.PermissionUpdateRequestPayload;
import com.nododiiiii.ponderer.platform.PondererServices;
import com.nododiiiii.ponderer.ponder.UploadPermissions;
import com.nododiiiii.ponderer.ui.catnip.ActionStripListEntry;
import com.nododiiiii.ponderer.ui.catnip.CollapsibleSectionHeaderListEntry;
import com.nododiiiii.ponderer.ui.catnip.DeclarativeFormEntry;
import com.nododiiiii.ponderer.ui.catnip.FormTextButtonSpec;
import com.nododiiiii.ponderer.ui.catnip.LabeledActionStripListEntry;
import com.nododiiiii.ponderer.ui.catnip.PlainTextListEntry;
import com.nododiiiii.ponderer.ui.catnip.PlayerPermissionListEntry;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.createmod.ponder.enums.PonderGuiTextures;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;

public class PermissionManagementScreen extends AbstractStatefulDeclarativeFormScreen {
   private static final int SCREEN_WIDTH = 300;
   private final List<PermissionListResponsePayload.Entry> entries = new ArrayList<>();
   private final Map<String, UploadPermissions.Role> pendingRoleEdits = new LinkedHashMap<>();
   private final Map<String, String> pendingRemovals = new LinkedHashMap<>();
   private final Set<UploadPermissions.Role> collapsedRoleSections = EnumSet.noneOf(UploadPermissions.Role.class);
   private String pendingSubject = "";
   private UploadPermissions.Role selectedRole = UploadPermissions.Role.UPLOAD;
   private String viewerRole = "";
   private boolean serverOperator;
   private boolean canManage;
   private boolean requestSent;
   private boolean waitingForServer = true;

   public PermissionManagementScreen(@Nullable Screen parent) {
      super(parent, "ponderer.ui.scope.server", "ponderer.ui.function_page.permissions.title", 300);
   }

   @Override
   protected void init() {
      super.init();
      if (!this.requestSent) {
         this.requestRefresh();
      }
   }

   @Override
   protected boolean shouldAutoCaptureBaselineOnInit() {
      return false;
   }

   public void receiveSnapshot(PermissionListResponsePayload payload) {
      this.entries.clear();
      this.entries.addAll(payload.entries());
      this.entries
         .sort(
            Comparator.<PermissionListResponsePayload.Entry>comparingInt(entry -> -roleLevel(entry.role()))
               .thenComparing(entry -> !entry.locked())
               .thenComparing(entry -> entry.subject().toLowerCase(Locale.ROOT))
         );
      this.viewerRole = payload.viewerRole() == null ? "" : payload.viewerRole();
      this.serverOperator = payload.serverOperator();
      this.canManage = payload.canManage();
      this.waitingForServer = false;
      PermissionListResponsePayload.Entry currentEntry = this.findEntry(this.pendingSubject);
      if (currentEntry != null) {
         this.selectedRole = this.displayRole(currentEntry);
      }

      this.reconcilePendingChanges();
      this.restoreBaselineState(new LinkedHashMap<>());
      if (payload.messageKey() != null && !payload.messageKey().isBlank()) {
         String message = payload.messageSubject() != null && !payload.messageSubject().isBlank()
            ? UIText.of(payload.messageKey(), payload.messageSubject())
            : UIText.of(payload.messageKey());
         if (payload.error()) {
            this.setErrorMessage(message);
         } else {
            this.setInfoMessage(message);
         }
      } else {
         this.setInfoMessage(UIText.of("ponderer.ui.function_page.permissions.loaded", this.entries.size()));
      }

      this.rebuildListPreservingScroll();
   }

   @Override
   protected void collectFormEntries(List<DeclarativeFormEntry> formEntries) {
      formEntries.add(
         screen -> {
            PlainTextListEntry subjectEntry = screen.createTextEntry(
               "ponderer.ui.function_page.permissions.player",
               "ponderer.ui.function_page.permissions.player.tooltip",
               "ponderer.ui.function_page.permissions.player.hint",
               this.pendingSubject,
               this::handleSubjectChanged,
               FormTextButtonSpec.action(
                  34,
                  this::fillSelf,
                  () -> UIText.of("ponderer.ui.function_page.permissions.use_self.short"),
                  () -> 8454143,
                  UIText.of("ponderer.ui.function_page.permissions.use_self.tooltip")
               )
            );
            subjectEntry.field().setMaxLength(64);
         }
      );
      formEntries.add(
         screen -> screen.appendBuiltEntry(
            new LabeledActionStripListEntry(
               "ponderer.ui.function_page.permissions.role",
               "ponderer.ui.function_page.permissions.role.tooltip",
               List.of(
                  ActionStripListEntry.button(
                     () -> UIText.of(roleLabelKey(this.selectedRole)),
                     tooltip("ponderer.ui.function_page.permissions.role.tooltip"),
                     this::cycleSelectedRole,
                     () -> roleColor(this.selectedRole),
                     this::canCyclePendingRole
                  ),
                  ActionStripListEntry.iconButton(
                     PonderGuiTextures.ICON_CONFIG_SAVE,
                     () -> this.sendSet(this.pendingSubject, this.selectedRole),
                     tooltip("ponderer.ui.function_page.permissions.set.tooltip"),
                     this::canEditPendingSubject
                  ),
                  ActionStripListEntry.iconButton(
                     PonderGuiTextures.ICON_CONFIG_RESET, this::requestRefresh, tooltip("ponderer.ui.function_page.permissions.refresh.tooltip"), () -> true
                  )
               )
            )
         )
      );
      formEntries.add(screen -> screen.createSectionHeaderEntry(this::viewerText));
      if (this.waitingForServer && this.entries.isEmpty()) {
         formEntries.add(screen -> screen.createSectionHeaderEntry(UIText.of("ponderer.ui.function_page.permissions.loading")));
      } else if (this.entries.isEmpty()) {
         formEntries.add(screen -> screen.createSectionHeaderEntry(UIText.of("ponderer.ui.function_page.permissions.empty")));
      } else {
         this.addRoleSection(formEntries, UploadPermissions.Role.ADMIN);
         this.addRoleSection(formEntries, UploadPermissions.Role.UPLOAD);
         this.addRoleSection(formEntries, UploadPermissions.Role.PULL);
      }
   }

   private void addRoleSection(List<DeclarativeFormEntry> formEntries, UploadPermissions.Role role) {
      List<PermissionListResponsePayload.Entry> matching = this.entries
         .stream()
         .filter(entryx -> !this.isPendingRemoval(entryx.subject()))
         .filter(entryx -> role.id().equals(roleFromId(entryx.role()).id()))
         .toList();
      if (!matching.isEmpty()) {
         formEntries.add(
            screen -> screen.appendBuiltEntry(
               new CollapsibleSectionHeaderListEntry(
                  () -> UIText.of(roleSectionKey(role), matching.size()),
                  () -> this.isRoleSectionCollapsed(role),
                  () -> this.toggleRoleSection(role),
                  () -> UIText.of("ponderer.ui.function_page.permissions.section.expand"),
                  () -> UIText.of("ponderer.ui.function_page.permissions.section.collapse")
               )
            )
         );
         if (!this.isRoleSectionCollapsed(role)) {
            for (PermissionListResponsePayload.Entry entry : matching) {
               formEntries.add(screen -> screen.appendBuiltEntry(this.createRoleEntry(entry)));
            }
         }
      }
   }

   private PlayerPermissionListEntry createRoleEntry(PermissionListResponsePayload.Entry entry) {
      UploadPermissions.Role entryRole = this.displayRole(entry);
      UploadPermissions.Role nextRole = nextRole(entryRole);
      boolean editable = this.canManage && !entry.locked();
      boolean dirty = this.isEntryRoleDirty(entry);
      String roleTooltipKey = entry.locked()
         ? "ponderer.ui.function_page.permissions.operator_locked.tooltip"
         : "ponderer.ui.function_page.permissions.row.role.tooltip";
      return new PlayerPermissionListEntry(
         entry.subject(),
         UIText.of(roleTooltipKey),
         List.of(
            ActionStripListEntry.button(
               () -> entry.locked() ? UIText.of("ponderer.ui.function_page.permissions.role.operator") : UIText.of(roleLabelKey(entryRole)),
               tooltip(roleTooltipKey),
               () -> this.stageRoleEdit(entry, nextRole),
               () -> roleColor(entryRole),
               () -> editable
            ),
            ActionStripListEntry.iconButton(
               PonderGuiTextures.ICON_CONFIG_SAVE,
               () -> this.sendEntryRoleEdit(entry),
               tooltip("ponderer.ui.function_page.permissions.set.tooltip"),
               () -> editable && dirty
            ),
            ActionStripListEntry.failIconButton(
               PonderGuiTextures.ICON_DISABLE,
               () -> this.stageRemoval(entry),
               tooltip("ponderer.ui.function_page.permissions.row.remove.tooltip"),
               () -> editable
            )
         )
      );
   }

   private void requestRefresh() {
      this.requestSent = true;
      this.waitingForServer = true;
      this.setInfoMessage(UIText.of("ponderer.ui.function_page.permissions.loading"));
      PondererServices.NETWORK.sendToServer(new PermissionListRequestPayload());
      if (this.list != null) {
         this.rebuildListPreservingScroll();
      }
   }

   private void sendSet(String rawSubject, UploadPermissions.Role role) {
      String subject = rawSubject == null ? "" : rawSubject.trim();
      if (subject.isEmpty()) {
         this.setErrorMessage(UIText.of("ponderer.ui.error.required_field", UIText.of("ponderer.ui.function_page.permissions.player")));
      } else if (!this.canManage) {
         this.setErrorMessage(UIText.of("ponderer.ui.function_page.permissions.denied", subject));
      } else if (this.isLockedSubject(subject)) {
         this.setErrorMessage(UIText.of("ponderer.ui.function_page.permissions.operator_locked", subject));
      } else {
         this.waitingForServer = true;
         this.setInfoMessage(UIText.of("ponderer.ui.function_page.permissions.saving"));
         PondererServices.NETWORK.sendToServer(new PermissionUpdateRequestPayload("set", subject, role.id()));
         this.rebuildListPreservingScroll();
      }
   }

   private void stageRoleEdit(PermissionListResponsePayload.Entry entry, UploadPermissions.Role role) {
      if (this.canEditEntry(entry)) {
         String key = subjectKey(entry.subject());
         this.pendingRemovals.remove(key);
         if (role == roleFromId(entry.role())) {
            this.pendingRoleEdits.remove(key);
         } else {
            this.pendingRoleEdits.put(key, role);
         }

         this.clearStatusMessages();
         this.rebuildListPreservingScroll();
      }
   }

   private void stageRemoval(PermissionListResponsePayload.Entry entry) {
      if (this.canEditEntry(entry)) {
         String key = subjectKey(entry.subject());
         this.pendingRoleEdits.remove(key);
         this.pendingRemovals.put(key, entry.subject());
         this.clearStatusMessages();
         this.rebuildListPreservingScroll();
      }
   }

   private void sendEntryRoleEdit(PermissionListResponsePayload.Entry entry) {
      if (this.canEditEntry(entry) && this.isEntryRoleDirty(entry)) {
         this.sendSet(entry.subject(), this.displayRole(entry));
      }
   }

   private boolean canEditEntry(PermissionListResponsePayload.Entry entry) {
      if (!this.canManage) {
         this.setErrorMessage(UIText.of("ponderer.ui.function_page.permissions.denied", entry.subject()));
         return false;
      }

      if (!entry.locked() && !this.isLockedSubject(entry.subject())) {
         return true;
      }

      this.setErrorMessage(UIText.of("ponderer.ui.function_page.permissions.operator_locked", entry.subject()));
      return false;
   }

   private boolean isRoleSectionCollapsed(UploadPermissions.Role role) {
      return this.collapsedRoleSections.contains(role);
   }

   private void toggleRoleSection(UploadPermissions.Role role) {
      if (!this.collapsedRoleSections.remove(role)) {
         this.collapsedRoleSections.add(role);
      }

      this.rebuildListPreservingScroll();
   }

   private void cycleSelectedRole() {
      if (this.canCyclePendingRole()) {
         this.selectedRole = nextRole(this.selectedRole);
         this.clearStatusMessages();
      }
   }

   private void fillSelf() {
      LocalPlayer player = Minecraft.getInstance().player;
      if (player != null) {
         this.handleSubjectChanged(player.getGameProfile().getName());
         this.clearStatusMessages();
         this.rebuildListPreservingScroll();
      }
   }

   private void handleSubjectChanged(String value) {
      this.pendingSubject = value == null ? "" : value;
      PermissionListResponsePayload.Entry entry = this.findEntry(this.pendingSubject);
      if (entry != null) {
         this.selectedRole = this.displayRole(entry);
      }
   }

   private String viewerText() {
      return UIText.of(
         this.canManage ? "ponderer.ui.function_page.permissions.viewer.manage" : "ponderer.ui.function_page.permissions.viewer.readonly",
         this.viewerRoleLabel()
      );
   }

   private boolean canCyclePendingRole() {
      return this.canManage && !this.isLockedSubject(this.pendingSubject);
   }

   private boolean canEditPendingSubject() {
      String subject = this.pendingSubject == null ? "" : this.pendingSubject.trim();
      return this.canManage && !subject.isEmpty() && !this.isLockedSubject(subject);
   }

   @Nullable
   private PermissionListResponsePayload.Entry findEntry(String subject) {
      if (subject != null && !subject.isBlank()) {
         String key = subject.trim().toLowerCase(Locale.ROOT);

         for (PermissionListResponsePayload.Entry entry : this.entries) {
            if (entry.subject().toLowerCase(Locale.ROOT).equals(key)) {
               return entry;
            }
         }

         return null;
      } else {
         return null;
      }
   }

   private boolean isLockedSubject(String subject) {
      PermissionListResponsePayload.Entry entry = this.findEntry(subject);
      return entry != null && entry.locked();
   }

   private String viewerRoleLabel() {
      if (this.serverOperator) {
         return UIText.of("ponderer.ui.function_page.permissions.role.operator");
      }

      UploadPermissions.Role role = UploadPermissions.Role.fromId(this.viewerRole);
      return role == null ? UIText.of("ponderer.ui.function_page.permissions.role.none") : UIText.of(roleLabelKey(role));
   }

   @Override
   protected Map<String, String> snapshotState() {
      Map<String, String> snapshot = new LinkedHashMap<>();

      for (Entry<String, UploadPermissions.Role> entry : this.pendingRoleEdits.entrySet()) {
         snapshot.put("role:" + entry.getKey(), entry.getValue().id());
      }

      for (Entry<String, String> entry : this.pendingRemovals.entrySet()) {
         snapshot.put("remove:" + entry.getKey(), entry.getValue());
      }

      return snapshot;
   }

   @Override
   protected void restoreSnapshot(Map<String, String> snapshot) {
      this.pendingRoleEdits.clear();
      this.pendingRemovals.clear();

      for (Entry<String, String> entry : snapshot.entrySet()) {
         if (entry.getKey().startsWith("role:")) {
            UploadPermissions.Role role = UploadPermissions.Role.fromId(entry.getValue());
            if (role != null) {
               this.pendingRoleEdits.put(entry.getKey().substring("role:".length()), role);
            }
         } else if (entry.getKey().startsWith("remove:")) {
            this.pendingRemovals.put(entry.getKey().substring("remove:".length()), entry.getValue());
         }
      }
   }

   @Override
   protected void afterSnapshotRestored(Map<String, String> snapshot) {
      this.rebuildListPreservingScroll();
   }

   @Override
   protected boolean saveEdits() {
      if (!this.hasPendingListChanges()) {
         return true;
      }

      if (!this.canManage) {
         this.setErrorMessage(UIText.of("ponderer.ui.function_page.permissions.denied", this.pendingSubject));
         return false;
      }

      for (String subject : this.pendingRemovals.values()) {
         if (this.isLockedSubject(subject)) {
            this.setErrorMessage(UIText.of("ponderer.ui.function_page.permissions.operator_locked", subject));
            return false;
         }
      }

      for (String key : this.pendingRoleEdits.keySet()) {
         PermissionListResponsePayload.Entry entry = this.findEntryByKey(key);
         if (entry != null && entry.locked()) {
            this.setErrorMessage(UIText.of("ponderer.ui.function_page.permissions.operator_locked", entry.subject()));
            return false;
         }
      }

      this.waitingForServer = true;
      this.setInfoMessage(UIText.of("ponderer.ui.function_page.permissions.saving"));

      for (String subject : this.pendingRemovals.values()) {
         PondererServices.NETWORK.sendToServer(new PermissionUpdateRequestPayload("remove", subject, ""));
      }

      for (Entry<String, UploadPermissions.Role> entry : this.pendingRoleEdits.entrySet()) {
         PermissionListResponsePayload.Entry source = this.findEntryByKey(entry.getKey());
         if (source != null) {
            PondererServices.NETWORK.sendToServer(new PermissionUpdateRequestPayload("set", source.subject(), entry.getValue().id()));
         }
      }

      this.rebuildListPreservingScroll();
      return true;
   }

   private static Supplier<List<Component>> tooltip(String key) {
      return () -> List.of(Component.literal(UIText.of(key)));
   }

   private static UploadPermissions.Role nextRole(UploadPermissions.Role role) {
      return switch (role) {
         case PULL -> UploadPermissions.Role.UPLOAD;
         case UPLOAD -> UploadPermissions.Role.ADMIN;
         case ADMIN -> UploadPermissions.Role.PULL;
      };
   }

   private static UploadPermissions.Role roleFromId(String roleId) {
      UploadPermissions.Role role = UploadPermissions.Role.fromId(roleId);
      return role == null ? UploadPermissions.Role.PULL : role;
   }

   private static int roleLevel(String roleId) {
      return switch (roleFromId(roleId)) {
         case PULL -> 1;
         case UPLOAD -> 2;
         case ADMIN -> 3;
      };
   }

   private static String roleLabelKey(UploadPermissions.Role role) {
      return switch (role) {
         case PULL -> "ponderer.ui.function_page.permissions.role.pull";
         case UPLOAD -> "ponderer.ui.function_page.permissions.role.upload";
         case ADMIN -> "ponderer.ui.function_page.permissions.role.admin";
      };
   }

   private static String roleSectionKey(UploadPermissions.Role role) {
      return switch (role) {
         case PULL -> "ponderer.ui.function_page.permissions.section.pull";
         case UPLOAD -> "ponderer.ui.function_page.permissions.section.upload";
         case ADMIN -> "ponderer.ui.function_page.permissions.section.admin";
      };
   }

   private static int roleColor(UploadPermissions.Role role) {
      return switch (role) {
         case PULL -> 8964351;
         case UPLOAD -> 9496720;
         case ADMIN -> 16769162;
      };
   }

   private UploadPermissions.Role displayRole(PermissionListResponsePayload.Entry entry) {
      return this.pendingRoleEdits.getOrDefault(subjectKey(entry.subject()), roleFromId(entry.role()));
   }

   private boolean isEntryRoleDirty(PermissionListResponsePayload.Entry entry) {
      return this.pendingRoleEdits.containsKey(subjectKey(entry.subject()));
   }

   private boolean isPendingRemoval(String subject) {
      return this.pendingRemovals.containsKey(subjectKey(subject));
   }

   private boolean hasPendingListChanges() {
      return !this.pendingRoleEdits.isEmpty() || !this.pendingRemovals.isEmpty();
   }

   private void reconcilePendingChanges() {
      this.pendingRoleEdits.entrySet().removeIf(entry -> {
         PermissionListResponsePayload.Entry current = this.findEntryByKey(entry.getKey());
         return current == null || roleFromId(current.role()) == entry.getValue();
      });
      this.pendingRemovals.entrySet().removeIf(entry -> this.findEntryByKey(entry.getKey()) == null);
   }

   @Nullable
   private PermissionListResponsePayload.Entry findEntryByKey(String key) {
      for (PermissionListResponsePayload.Entry entry : this.entries) {
         if (subjectKey(entry.subject()).equals(key)) {
            return entry;
         }
      }

      return null;
   }

   private static String subjectKey(String subject) {
      return subject == null ? "" : subject.trim().toLowerCase(Locale.ROOT);
   }
}
