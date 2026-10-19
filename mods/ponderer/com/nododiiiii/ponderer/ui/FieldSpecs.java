package com.nododiiiii.ponderer.ui;

import com.nododiiiii.ponderer.ui.catnip.AbstractDeclarativeFormScreen;
import com.nododiiiii.ponderer.ui.catnip.BlockPropertyListEntry;
import com.nododiiiii.ponderer.ui.catnip.ButtonListEntry;
import com.nododiiiii.ponderer.ui.catnip.DualTextListEntry;
import com.nododiiiii.ponderer.ui.catnip.EntryTextSupport;
import com.nododiiiii.ponderer.ui.catnip.LocalizedTextListEntry;
import com.nododiiiii.ponderer.ui.catnip.PlainTextListEntry;
import com.nododiiiii.ponderer.ui.catnip.XyzListEntry;
import java.util.Map;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.createmod.catnip.config.ui.HintableTextFieldWidget;

public final class FieldSpecs {
   private static final float DEFAULT_CHOICE_CONTROL_SCALE = EntryTextSupport.halfWidthControlScale();
   private static final int HALF_WIDTH_CONTROL_MIN = 100;

   private FieldSpecs() {
   }

   public static FieldSpec text(
      final FieldBinding<String> binding,
      final String labelKey,
      @Nullable final String tooltipKey,
      @Nullable final String hintKey,
      final int fieldWidth,
      final Consumer<PlainTextListEntry> afterBuild,
      final FieldDecorator... decorators
   ) {
      return new FieldSpec() {
         @Override
         public void build(AbstractDeclarativeFormScreen screen) {
            FieldDecorators.LangToggleSpec langToggle = FieldSpecs.findLangToggle(decorators);
            if (langToggle != null) {
               LocalizedTextListEntry entry = screen.createLocalizedTextEntry(
                  labelKey, tooltipKey, hintKey, FieldSpecs.safeValue(binding.get()), langToggle.langGetter(), langToggle.onToggle(), binding::set
               );
               FieldSpecs.attachWidget(binding, entry.field());
               if (fieldWidth > 0) {
                  entry.setPreferredFieldWidth(fieldWidth);
               }

               FieldSpecs.applyLocalizedDecorators(screen, entry, decorators);
               afterBuild.accept(entry);
            } else {
               PlainTextListEntry entry = screen.createTextEntry(labelKey, tooltipKey, hintKey, FieldSpecs.safeValue(binding.get()), binding::set);
               FieldSpecs.attachWidget(binding, entry.field());
               if (fieldWidth > 0) {
                  entry.setPreferredFieldWidth(fieldWidth);
               }

               FieldSpecs.applyTextDecorators(screen, entry, decorators);
               afterBuild.accept(entry);
            }
         }

         @Override
         public void snapshot(Map<String, String> snapshot) {
            binding.snapshot(snapshot);
         }

         @Override
         public void restore(Map<String, String> snapshot) {
            binding.restore(snapshot);
         }
      };
   }

   public static FieldSpec text(
      FieldBinding<String> binding, String labelKey, @Nullable String tooltipKey, @Nullable String hintKey, int fieldWidth, FieldDecorator... decorators
   ) {
      return text(binding, labelKey, tooltipKey, hintKey, fieldWidth, entry -> {}, decorators);
   }

   public static FieldSpec localizedText(
      final FieldBinding<String> binding,
      final String labelKey,
      @Nullable final String tooltipKey,
      @Nullable final String hintKey,
      final int fieldWidth,
      final Supplier<String> langGetter,
      final Runnable onToggle,
      final Consumer<LocalizedTextListEntry> afterBuild
   ) {
      return new FieldSpec() {
         @Override
         public void build(AbstractDeclarativeFormScreen screen) {
            LocalizedTextListEntry entry = screen.createLocalizedTextEntry(
               labelKey, tooltipKey, hintKey, FieldSpecs.safeValue(binding.get()), langGetter, onToggle, binding::set
            );
            FieldSpecs.attachWidget(binding, entry.field());
            if (fieldWidth > 0) {
               entry.setPreferredFieldWidth(fieldWidth);
            }

            afterBuild.accept(entry);
         }

         @Override
         public void snapshot(Map<String, String> snapshot) {
            binding.snapshot(snapshot);
         }

         @Override
         public void restore(Map<String, String> snapshot) {
            binding.restore(snapshot);
         }
      };
   }

   public static FieldSpec localizedText(
      FieldBinding<String> binding,
      String labelKey,
      @Nullable String tooltipKey,
      @Nullable String hintKey,
      int fieldWidth,
      Supplier<String> langGetter,
      Runnable onToggle
   ) {
      return localizedText(binding, labelKey, tooltipKey, hintKey, fieldWidth, langGetter, onToggle, entry -> {});
   }

   public static FieldSpec number(
      FieldBinding<String> binding, String labelKey, @Nullable String tooltipKey, String hintKey, int fieldWidth, @Nullable String unitKey
   ) {
      return text(binding, labelKey, tooltipKey, hintKey, fieldWidth, entry -> {
         entry.setHalfWidthControl(Math.max(100, fieldWidth));
         if (unitKey != null) {
            entry.setTrailingText(() -> UIText.of(unitKey));
         }
      });
   }

   public static FieldSpec ticksNumber(FieldBinding<String> binding, String labelKey, @Nullable String tooltipKey, String hintKey, int fieldWidth) {
      return number(binding, labelKey, tooltipKey, hintKey, fieldWidth, "ponderer.ui.ticks");
   }

   public static FieldSpec toggle(final FieldBinding<Boolean> binding, final String labelKey, @Nullable final String tooltipKey) {
      return new FieldSpec() {
         @Override
         public void build(AbstractDeclarativeFormScreen screen) {
            screen.createToggleEntry(labelKey, tooltipKey, () -> Boolean.TRUE.equals(binding.get()), () -> binding.set(!Boolean.TRUE.equals(binding.get())));
         }

         @Override
         public void snapshot(Map<String, String> snapshot) {
            binding.snapshot(snapshot);
         }

         @Override
         public void restore(Map<String, String> snapshot) {
            binding.restore(snapshot);
         }
      };
   }

   public static FieldSpec toggle(final String labelKey, @Nullable final String tooltipKey, final BooleanSupplier stateGetter, final Runnable onToggle) {
      return new FieldSpec() {
         @Override
         public void build(AbstractDeclarativeFormScreen screen) {
            screen.createToggleEntry(labelKey, tooltipKey, stateGetter, onToggle);
         }
      };
   }

   public static FieldSpec choice(
      String labelKey,
      @Nullable String tooltipKey,
      int buttonWidth,
      Runnable onClick,
      Supplier<String> labelGetter,
      IntSupplier colorGetter,
      @Nullable String buttonTooltipText,
      float controlWidthScale
   ) {
      return screen -> screen.createChoiceEntry(labelKey, tooltipKey, buttonWidth, onClick, labelGetter, colorGetter, buttonTooltipText, controlWidthScale);
   }

   public static FieldSpec choice(
      String labelKey,
      @Nullable String tooltipKey,
      int buttonWidth,
      Runnable onClick,
      Supplier<String> labelGetter,
      IntSupplier colorGetter,
      @Nullable Supplier<String> buttonTooltipGetter,
      float controlWidthScale
   ) {
      return screen -> screen.createChoiceEntry(labelKey, tooltipKey, buttonWidth, onClick, labelGetter, colorGetter, buttonTooltipGetter, controlWidthScale);
   }

   public static FieldSpec choice(
      String labelKey,
      @Nullable String tooltipKey,
      int buttonWidth,
      Runnable onClick,
      Supplier<String> labelGetter,
      IntSupplier colorGetter,
      @Nullable String buttonTooltipText
   ) {
      return choice(labelKey, tooltipKey, buttonWidth, onClick, labelGetter, colorGetter, buttonTooltipText, DEFAULT_CHOICE_CONTROL_SCALE);
   }

   public static FieldSpec choice(
      String labelKey, @Nullable String tooltipKey, int buttonWidth, Runnable onClick, Supplier<String> labelGetter, IntSupplier colorGetter
   ) {
      return choice(labelKey, tooltipKey, buttonWidth, onClick, labelGetter, colorGetter, (String)null, DEFAULT_CHOICE_CONTROL_SCALE);
   }

   public static FieldSpec choice(String labelKey, @Nullable String tooltipKey, int buttonWidth, Runnable onClick, Supplier<String> labelGetter) {
      return choice(labelKey, tooltipKey, buttonWidth, onClick, labelGetter, () -> 16777215, (String)null, DEFAULT_CHOICE_CONTROL_SCALE);
   }

   public static FieldSpec cycle(
      final FieldBinding<Integer> binding,
      final String labelKey,
      @Nullable final String tooltipKey,
      final int buttonWidth,
      final int optionCount,
      final Runnable afterCycle,
      final Supplier<String> labelGetter,
      final IntSupplier colorGetter
   ) {
      return new FieldSpec() {
         @Override
         public void build(AbstractDeclarativeFormScreen screen) {
            int alignedHalfWidth = Math.min(buttonWidth, 100);
            ButtonListEntry entry = screen.createChoiceEntry(labelKey, tooltipKey, alignedHalfWidth, () -> {
               int current = binding.get() == null ? 0 : binding.get();
               binding.set((current + 1 + optionCount) % optionCount);
               afterCycle.run();
            }, labelGetter, colorGetter, (String)null, 1.0F);
            entry.setHalfWidthControl(alignedHalfWidth);
         }

         @Override
         public void snapshot(Map<String, String> snapshot) {
            binding.snapshot(snapshot);
         }

         @Override
         public void restore(Map<String, String> snapshot) {
            binding.restore(snapshot);
         }
      };
   }

   public static FieldSpec fullButton(String label, @Nullable String tooltipText, Runnable onClick) {
      return screen -> screen.createFullButtonEntry(label, tooltipText, onClick);
   }

   public static FieldSpec fullButton(
      Supplier<String> labelGetter, @Nullable Supplier<String> tooltipGetter, Runnable onClick, IntSupplier colorGetter, BooleanSupplier activeGetter
   ) {
      return screen -> screen.createFullButtonEntry(labelGetter, tooltipGetter, onClick, colorGetter, activeGetter);
   }

   public static FieldSpec labeledButton(
      String labelKey, @Nullable String tooltipKey, Runnable onClick, Supplier<String> buttonLabelGetter, @Nullable Supplier<String> buttonTooltipGetter
   ) {
      return choice(labelKey, tooltipKey, 0, onClick, buttonLabelGetter, () -> 16777215, buttonTooltipGetter, 1.0F);
   }

   public static FieldSpec sectionHeader(String title) {
      return screen -> screen.createSectionHeaderEntry(title);
   }

   public static FieldSpec sectionHeader(Supplier<String> titleGetter) {
      return screen -> screen.createSectionHeaderEntry(titleGetter);
   }

   public static FieldSpec xyz(
      final StepXyzFieldHandle handle,
      final String labelKey,
      @Nullable final String tooltipKey,
      @Nullable final String xHint,
      @Nullable final String yHint,
      @Nullable final String zHint,
      final FieldDecorator... decorators
   ) {
      return new FieldSpec() {
         @Override
         public void build(AbstractDeclarativeFormScreen screen) {
            XyzListEntry entry = new XyzListEntry(labelKey, tooltipKey, xHint, yHint, zHint);
            FieldSpecs.attachWidget(handle.xHandle(), entry.xField());
            FieldSpecs.attachWidget(handle.yHandle(), entry.yField());
            FieldSpecs.attachWidget(handle.zHandle(), entry.zField());
            FieldSpecs.applyXyzDecorators(screen, entry, decorators);
            screen.appendBuiltEntry(entry);
         }

         @Override
         public void snapshot(Map<String, String> snapshot) {
            handle.snapshot(snapshot);
         }

         @Override
         public void restore(Map<String, String> snapshot) {
            handle.restore(snapshot);
         }
      };
   }

   public static FieldSpec xyz(
      StepXyzFieldHandle handle, String labelKey, @Nullable String tooltipKey, @Nullable PickState.TargetField target, boolean halfOffset
   ) {
      FieldDecorator[] decorators = target == null ? new FieldDecorator[0] : new FieldDecorator[]{FieldDecorators.pointPick(target, halfOffset)};
      return xyz(handle, labelKey, tooltipKey, "X", "Y", "Z", decorators);
   }

   public static FieldSpec xyz(StepXyzFieldHandle handle, String labelKey, @Nullable String tooltipKey, PickState.TargetField target) {
      return xyz(handle, labelKey, tooltipKey, target, false);
   }

   public static FieldSpec xyz(StepXyzFieldHandle handle, String labelKey, @Nullable String tooltipKey) {
      return xyz(handle, labelKey, tooltipKey, "X", "Y", "Z");
   }

   public static FieldSpec dualText(
      final FieldBinding<String> firstBinding,
      final FieldBinding<String> secondBinding,
      final String labelKey,
      @Nullable final String tooltipKey,
      final String firstHint,
      final int firstWidth,
      final String secondHint,
      final int secondWidth
   ) {
      return new FieldSpec() {
         @Override
         public void build(AbstractDeclarativeFormScreen screen) {
            if (!(screen instanceof AbstractStepEditorScreen)) {
               throw new IllegalStateException("Dual text fields require an AbstractStepEditorScreen");
            }

            DualTextListEntry entry = new DualTextListEntry(labelKey, tooltipKey, firstHint, secondHint);
            entry.setPreferredWidths(firstWidth, secondWidth);
            FieldSpecs.attachWidget(firstBinding, entry.firstField());
            FieldSpecs.attachWidget(secondBinding, entry.secondField());
            entry.firstField().setResponder(firstBinding::set);
            entry.secondField().setResponder(secondBinding::set);
            entry.firstField().setValue(FieldSpecs.safeValue(firstBinding.get()));
            entry.secondField().setValue(FieldSpecs.safeValue(secondBinding.get()));
            screen.appendBuiltEntry(entry);
         }

         @Override
         public void snapshot(Map<String, String> snapshot) {
            firstBinding.snapshot(snapshot);
            secondBinding.snapshot(snapshot);
         }

         @Override
         public void restore(Map<String, String> snapshot) {
            firstBinding.restore(snapshot);
            secondBinding.restore(snapshot);
         }
      };
   }

   public static FieldSpec blockProperties(final KeyValueListState state, final String labelKey, @Nullable final String tooltipKey) {
      return new FieldSpec() {
         @Override
         public void build(AbstractDeclarativeFormScreen screen) {
            for (int i = 0; i < state.size(); i++) {
               int index = i;
               KeyValueListState.KeyValue pair = state.get(i);
               BlockPropertyListEntry entry = new BlockPropertyListEntry(i == 0 ? labelKey : "", i == 0 ? tooltipKey : null, pair.key(), pair.value(), () -> {
                  state.remove(index);
                  if (screen instanceof AbstractStepEditorScreen stepScreen) {
                     stepScreen.rebuildFormPreservingState();
                  }
               });
               entry.keyField().setResponder(value -> state.updateKey(index, value));
               entry.valueField().setResponder(value -> state.updateValue(index, value));
               screen.appendBuiltEntry(entry);
            }

            ButtonListEntry addEntry = new ButtonListEntry("", null, 40, () -> {
               state.addEmpty();
               if (screen instanceof AbstractStepEditorScreen stepScreen) {
                  stepScreen.rebuildFormPreservingState();
               }
            }, () -> "+", () -> 8454016, UIText.of("ponderer.ui.block_properties"));
            screen.appendBuiltEntry(addEntry);
         }

         @Override
         public void snapshot(Map<String, String> snapshot) {
            state.snapshot(snapshot);
         }

         @Override
         public void restore(Map<String, String> snapshot) {
            state.restore(snapshot);
         }
      };
   }

   private static void applyTextDecorators(AbstractDeclarativeFormScreen screen, PlainTextListEntry entry, FieldDecorator... decorators) {
      for (FieldDecorator decorator : decorators) {
         if (decorator != null) {
            decorator.applyText(screen, entry);
         }
      }
   }

   private static void applyLocalizedDecorators(AbstractDeclarativeFormScreen screen, LocalizedTextListEntry entry, FieldDecorator... decorators) {
      for (FieldDecorator decorator : decorators) {
         if (decorator != null) {
            decorator.applyLocalizedText(screen, entry);
         }
      }
   }

   private static void applyXyzDecorators(AbstractDeclarativeFormScreen screen, XyzListEntry entry, FieldDecorator... decorators) {
      for (FieldDecorator decorator : decorators) {
         if (decorator != null) {
            decorator.applyXyz(screen, entry);
         }
      }
   }

   @Nullable
   private static FieldDecorators.LangToggleSpec findLangToggle(FieldDecorator... decorators) {
      for (FieldDecorator decorator : decorators) {
         if (decorator != null && decorator.langToggle() != null) {
            return decorator.langToggle();
         }
      }

      return null;
   }

   private static void attachWidget(FieldBinding<String> binding, HintableTextFieldWidget widget) {
      if (binding instanceof HintableTextWidgetBinding widgetBinding) {
         widgetBinding.attach(widget);
      } else {
         String value = safeValue(binding.get());
         if (!value.equals(widget.getValue())) {
            widget.setValue(value);
         }
      }
   }

   private static String safeValue(@Nullable String value) {
      return value == null ? "" : value;
   }
}
