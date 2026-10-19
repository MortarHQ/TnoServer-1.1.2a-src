package com.nododiiiii.ponderer.ui;

import com.nododiiiii.ponderer.compat.jei.JeiCompat;
import com.nododiiiii.ponderer.ui.catnip.AbstractDeclarativeFormScreen;
import com.nododiiiii.ponderer.ui.catnip.LocalizedTextListEntry;
import com.nododiiiii.ponderer.ui.catnip.PlainTextListEntry;
import com.nododiiiii.ponderer.ui.catnip.XyzListEntry;
import java.util.function.Consumer;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.world.item.ItemStack;

public final class FieldDecorators {
   private FieldDecorators() {
   }

   public static FieldDecorator textAction(
      final int width, final Runnable onClick, final Supplier<String> labelGetter, final IntSupplier colorGetter, @Nullable final String tooltipText
   ) {
      return new FieldDecorator() {
         @Override
         public void applyText(AbstractDeclarativeFormScreen screen, PlainTextListEntry entry) {
            entry.addTrailingButton(width, onClick, labelGetter, colorGetter, tooltipText);
         }
      };
   }

   public static FieldDecorator textAction(String label, int color, @Nullable String tooltipText, Runnable onClick) {
      return textAction(20, onClick, () -> label, () -> color, tooltipText);
   }

   public static FieldDecorator xyzAction(
      final int width, final Runnable onClick, final Supplier<String> labelGetter, final IntSupplier colorGetter, @Nullable final String tooltipText
   ) {
      return new FieldDecorator() {
         @Override
         public void applyXyz(AbstractDeclarativeFormScreen screen, XyzListEntry entry) {
            entry.addTrailingButton(width, onClick, labelGetter, colorGetter, tooltipText);
         }
      };
   }

   public static FieldDecorator xyzAction(String label, int color, @Nullable String tooltipText, Runnable onClick) {
      return xyzAction(20, onClick, () -> label, () -> color, tooltipText);
   }

   public static FieldDecorator jei(final IdFieldMode mode) {
      return new FieldDecorator() {
         @Override
         public void applyText(AbstractDeclarativeFormScreen screen, PlainTextListEntry entry) {
            if (JeiCompat.isAvailable()) {
               if (screen instanceof JeiTextButtonHost host) {
                  entry.addTrailingButton(
                     20,
                     () -> host.toggleJeiForField(entry.field(), mode),
                     () -> "J",
                     () -> host.isJeiActiveForField(entry.field()) ? 5635925 : 11184895,
                     UIText.of("ponderer.ui.jei_browse.tooltip")
                  );
               } else {
                  throw new IllegalStateException("JEI decorator requires screen to implement JeiTextButtonHost");
               }
            }
         }
      };
   }

   public static FieldDecorator pointPick(final PickState.TargetField target, final boolean halfOffset) {
      return new FieldDecorator() {
         @Override
         public void applyXyz(AbstractDeclarativeFormScreen screen, XyzListEntry entry) {
            if (screen instanceof PointPickButtonHost host) {
               entry.addTrailingButton(
                  20, () -> host.startPointPickFromButton(target, halfOffset), () -> "+", () -> 8454143, UIText.of("ponderer.ui.pick.tooltip")
               );
            } else {
               throw new IllegalStateException("Point pick decorator requires screen to implement PointPickButtonHost");
            }
         }
      };
   }

   public static FieldDecorator pointPick(PickState.TargetField target) {
      return pointPick(target, false);
   }

   public static FieldDecorator blockPick(final String nbtSnapshotKey) {
      return new FieldDecorator() {
         @Override
         public void applyText(AbstractDeclarativeFormScreen screen, PlainTextListEntry entry) {
            if (screen instanceof NbtPickButtonHost host) {
               entry.addTrailingButton(
                  20, () -> host.startNbtPickFromButton(nbtSnapshotKey, true), () -> "+", () -> 6750054, UIText.of("ponderer.ui.block_pick.tooltip")
               );
            } else {
               throw new IllegalStateException("Block pick decorator requires screen to implement NbtPickButtonHost");
            }
         }
      };
   }

   public static FieldDecorator nbtPick(final String nbtSnapshotKey) {
      return new FieldDecorator() {
         @Override
         public void applyText(AbstractDeclarativeFormScreen screen, PlainTextListEntry entry) {
            if (screen instanceof NbtPickButtonHost host) {
               entry.addTrailingButton(
                  20, () -> host.startNbtPickFromButton(nbtSnapshotKey, false), () -> "+", () -> 6750054, UIText.of("ponderer.ui.nbt_pick.tooltip")
               );
            } else {
               throw new IllegalStateException("NBT pick decorator requires screen to implement NbtPickButtonHost");
            }
         }
      };
   }

   public static FieldDecorator heldItem(final Consumer<ItemStack> onItemPicked) {
      return new FieldDecorator() {
         @Override
         public void applyText(AbstractDeclarativeFormScreen screen, PlainTextListEntry entry) {
            if (screen instanceof HeldItemButtonHost host) {
               entry.addTrailingButton(20, () -> host.useHeldItemFromButton(onItemPicked), () -> "+", () -> 6750054, UIText.of("ponderer.ui.held_item.tooltip"));
            } else {
               throw new IllegalStateException("Held item decorator requires screen to implement HeldItemButtonHost");
            }
         }
      };
   }

   public static FieldDecorator sceneSelector(Runnable onClick) {
      return textAction("S", 8454143, UIText.of("ponderer.ui.scene_desc.structure_list_title"), onClick);
   }

   public static FieldDecorator langToggle(final Supplier<String> langGetter, final Runnable onToggle) {
      return new FieldDecorator() {
         @Override
         public FieldDecorators.LangToggleSpec langToggle() {
            return new FieldDecorators.LangToggleSpec(langGetter, onToggle);
         }

         @Override
         public void applyLocalizedText(AbstractDeclarativeFormScreen screen, LocalizedTextListEntry entry) {
         }
      };
   }

   public record LangToggleSpec(Supplier<String> langGetter, Runnable onToggle) {
   }
}
