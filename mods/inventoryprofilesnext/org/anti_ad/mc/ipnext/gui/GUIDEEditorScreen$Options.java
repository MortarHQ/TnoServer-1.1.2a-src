package org.anti_ad.mc.ipnext.gui;

import kotlin.Unit;
import org.anti_ad.mc.ipn.api.IPNButton;
import org.anti_ad.mc.ipnext.inventory.ContainerTypes;
import org.jetbrains.annotations.NotNull;

public final class GUIDEEditorScreen$Options {
   @NotNull
   private final GUIDEEditorScreen$EditorConfigBoolean screenIgnoreOption;
   @NotNull
   private final GUIDEEditorScreen$EditorConfigBoolean screenPlayerSideOption;
   @NotNull
   private final GUIDEEditorScreen$EditorConfigBoolean screenDisableSwipe;
   @NotNull
   private final GUIDEEditorScreen$EditorConfigBoolean screenForceOption;
   @NotNull
   private final GUIDEEditorScreen$EditorConfigBoolean screenShowProfileSelectorOption;
   @NotNull
   private final GUIDEEditorScreen$EditorConfigBoolean containerIgnoreOption;
   @NotNull
   private final GUIDEEditorScreen$EditorConfigBoolean containerPlayerSideOption;
   @NotNull
   private final GUIDEEditorScreen$EditorConfigBoolean containerForceOption;

   public GUIDEEditorScreen$Options(GUIDEEditorScreen var1) {
      this.this$0 = var1;
      this.screenIgnoreOption = new GUIDEEditorScreen$EditorConfigBoolean(
         this.this$0,
         "screen.ignore",
         GUIDEEditorScreen.access$isVanillaScreen$p(this.this$0),
         false,
         GUIDEEditorScreen.access$getScreenHints$p(this.this$0).getIgnore(),
         GUIDEEditorScreen$Options::screenIgnoreOption$lambda$0
      );
      this.screenPlayerSideOption = new GUIDEEditorScreen$EditorConfigBoolean(
         this.this$0,
         "screen.player_side",
         GUIDEEditorScreen.access$isVanillaScreen$p(this.this$0),
         false,
         GUIDEEditorScreen.access$getScreenHints$p(this.this$0).getPlayerSideOnly(),
         GUIDEEditorScreen$Options::screenPlayerSideOption$lambda$1
      );
      this.screenDisableSwipe = new GUIDEEditorScreen$EditorConfigBoolean(
         this.this$0,
         "screen.disable_fast_swipe",
         GUIDEEditorScreen.access$isVanillaScreen$p(this.this$0),
         false,
         GUIDEEditorScreen.access$getScreenHints$p(this.this$0).getDisableFastSwipe(),
         GUIDEEditorScreen$Options::screenDisableSwipe$lambda$2
      );
      this.screenForceOption = new GUIDEEditorScreen$EditorConfigBoolean(
         this.this$0,
         "screen.force",
         GUIDEEditorScreen.access$isVanillaScreen$p(this.this$0),
         false,
         GUIDEEditorScreen.access$getScreenHints$p(this.this$0).getForce(),
         GUIDEEditorScreen$Options::screenForceOption$lambda$3
      );
      this.screenShowProfileSelectorOption = new GUIDEEditorScreen$EditorConfigBoolean(
         this.this$0,
         "screen.profile",
         GUIDEEditorScreen.access$isVanillaScreen$p(this.this$0),
         true,
         !GUIDEEditorScreen.access$getScreenHints$p(this.this$0).hintFor(IPNButton.PROFILE_SELECTOR).getHide(),
         GUIDEEditorScreen$Options::screenShowProfileSelectorOption$lambda$4
      );
      this.containerIgnoreOption = new GUIDEEditorScreen$EditorConfigBoolean(
         this.this$0,
         "screen.ignore",
         GUIDEEditorScreen.access$isVanillaContainer$p(this.this$0),
         false,
         GUIDEEditorScreen.access$getContainerHints$p(this.this$0).getIgnore(),
         GUIDEEditorScreen$Options::containerIgnoreOption$lambda$5
      );
      this.containerPlayerSideOption = new GUIDEEditorScreen$EditorConfigBoolean(
         this.this$0,
         "screen.player_side",
         GUIDEEditorScreen.access$isVanillaContainer$p(this.this$0),
         false,
         GUIDEEditorScreen.access$getContainerHints$p(this.this$0).getPlayerSideOnly(),
         GUIDEEditorScreen$Options::containerPlayerSideOption$lambda$6
      );
      this.containerForceOption = new GUIDEEditorScreen$EditorConfigBoolean(
         this.this$0,
         "screen.force",
         GUIDEEditorScreen.access$isVanillaContainer$p(this.this$0),
         false,
         GUIDEEditorScreen.access$getContainerHints$p(this.this$0).getForce(),
         GUIDEEditorScreen$Options::containerForceOption$lambda$7
      );
   }

   @NotNull
   public final GUIDEEditorScreen$EditorConfigBoolean getScreenIgnoreOption() {
      return this.screenIgnoreOption;
   }

   @NotNull
   public final GUIDEEditorScreen$EditorConfigBoolean getScreenPlayerSideOption() {
      return this.screenPlayerSideOption;
   }

   @NotNull
   public final GUIDEEditorScreen$EditorConfigBoolean getScreenDisableSwipe() {
      return this.screenDisableSwipe;
   }

   @NotNull
   public final GUIDEEditorScreen$EditorConfigBoolean getScreenForceOption() {
      return this.screenForceOption;
   }

   @NotNull
   public final GUIDEEditorScreen$EditorConfigBoolean getScreenShowProfileSelectorOption() {
      return this.screenShowProfileSelectorOption;
   }

   @NotNull
   public final GUIDEEditorScreen$EditorConfigBoolean getContainerIgnoreOption() {
      return this.containerIgnoreOption;
   }

   @NotNull
   public final GUIDEEditorScreen$EditorConfigBoolean getContainerPlayerSideOption() {
      return this.containerPlayerSideOption;
   }

   @NotNull
   public final GUIDEEditorScreen$EditorConfigBoolean getContainerForceOption() {
      return this.containerForceOption;
   }

   private static final Unit screenIgnoreOption$lambda$0(GUIDEEditorScreen var0, boolean var1) {
      if (GUIDEEditorScreen.access$getScreenHints$p(var0).getIgnore() != var1) {
         GUIDEEditorScreen.access$getScreenHints$p(var0).markAsDirty();
      }

      GUIDEEditorScreen.access$getScreenHints$p(var0).setIgnore(var1);
      return Unit.INSTANCE;
   }

   private static final Unit screenPlayerSideOption$lambda$1(GUIDEEditorScreen var0, boolean var1) {
      if (GUIDEEditorScreen.access$getScreenHints$p(var0).getPlayerSideOnly() != var1) {
         GUIDEEditorScreen.access$getScreenHints$p(var0).markAsDirty();
      }

      GUIDEEditorScreen.access$getScreenHints$p(var0).setPlayerSideOnly(var1);
      return Unit.INSTANCE;
   }

   private static final Unit screenDisableSwipe$lambda$2(GUIDEEditorScreen var0, boolean var1) {
      if (GUIDEEditorScreen.access$getScreenHints$p(var0).getDisableFastSwipe() != var1) {
         GUIDEEditorScreen.access$getScreenHints$p(var0).markAsDirty();
      }

      GUIDEEditorScreen.access$getScreenHints$p(var0).setDisableFastSwipe(var1);
      return Unit.INSTANCE;
   }

   private static final Unit screenForceOption$lambda$3(GUIDEEditorScreen var0, boolean var1) {
      if (GUIDEEditorScreen.access$getScreenHints$p(var0).getForce() != var1) {
         GUIDEEditorScreen.access$getScreenHints$p(var0).markAsDirty();
      }

      GUIDEEditorScreen.access$getScreenHints$p(var0).setForce(var1);
      return Unit.INSTANCE;
   }

   private static final Unit screenShowProfileSelectorOption$lambda$4(GUIDEEditorScreen var0, boolean var1) {
      if (GUIDEEditorScreen.access$getScreenHints$p(var0).hintFor(IPNButton.PROFILE_SELECTOR).getHide() == var1) {
         GUIDEEditorScreen.access$getScreenHints$p(var0).markAsDirty();
      }

      GUIDEEditorScreen.access$getScreenHints$p(var0).hintFor(IPNButton.PROFILE_SELECTOR).setHide(!var1);
      return Unit.INSTANCE;
   }

   private static final Unit containerIgnoreOption$lambda$5(GUIDEEditorScreen var0, boolean var1) {
      if (GUIDEEditorScreen.access$getContainerHints$p(var0).getIgnore() != var1) {
         GUIDEEditorScreen.access$getContainerHints$p(var0).markAsDirty();
      }

      GUIDEEditorScreen.access$getContainerHints$p(var0).setIgnore(var1);
      ContainerTypes.INSTANCE.deregister(GUIDEEditorScreen.access$getContainer$p(var0).getClass());
      return Unit.INSTANCE;
   }

   private static final Unit containerPlayerSideOption$lambda$6(GUIDEEditorScreen var0, boolean var1) {
      if (GUIDEEditorScreen.access$getContainerHints$p(var0).getPlayerSideOnly() != var1) {
         GUIDEEditorScreen.access$getContainerHints$p(var0).markAsDirty();
      }

      GUIDEEditorScreen.access$getContainerHints$p(var0).setPlayerSideOnly(var1);
      ContainerTypes.INSTANCE.deregister(GUIDEEditorScreen.access$getContainer$p(var0).getClass());
      return Unit.INSTANCE;
   }

   private static final Unit containerForceOption$lambda$7(GUIDEEditorScreen var0, boolean var1) {
      if (GUIDEEditorScreen.access$getContainerHints$p(var0).getForce() != var1) {
         GUIDEEditorScreen.access$getContainerHints$p(var0).markAsDirty();
      }

      GUIDEEditorScreen.access$getContainerHints$p(var0).setForce(var1);
      ContainerTypes.INSTANCE.deregister(GUIDEEditorScreen.access$getContainer$p(var0).getClass());
      return Unit.INSTANCE;
   }
}
