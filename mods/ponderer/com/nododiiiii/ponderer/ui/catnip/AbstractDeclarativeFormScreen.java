package com.nododiiiii.ponderer.ui.catnip;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.createmod.catnip.config.ui.ConfigScreenList.Entry;
import net.minecraft.client.gui.screens.Screen;

public abstract class AbstractDeclarativeFormScreen extends AbstractDeclarativeListScreen {
   protected static final float DEFAULT_CHOICE_CONTROL_SCALE = EntryTextSupport.halfWidthControlScale();
   private final List<DeclarativeFormEntry> formEntries = new ArrayList<>();
   @Nullable
   private List<Entry> currentEntries;

   protected AbstractDeclarativeFormScreen(@Nullable Screen parent, String scopeKey, String titleKey) {
      this(parent, scopeKey, titleKey, 300);
   }

   protected AbstractDeclarativeFormScreen(@Nullable Screen parent, String scopeKey, String titleKey, int preferredListWidth) {
      super(parent, scopeKey, titleKey, preferredListWidth);
   }

   @Override
   protected int getEntryHeight() {
      return 40;
   }

   @Override
   protected final void collectEntries(List<Entry> entries) {
      this.currentEntries = entries;
      this.formEntries.clear();
      this.collectFormEntries(this.formEntries);

      for (DeclarativeFormEntry entry : this.formEntries) {
         entry.build(this);
      }

      this.currentEntries = null;
   }

   protected abstract void collectFormEntries(List<DeclarativeFormEntry> var1);

   protected final List<DeclarativeFormEntry> builtFormEntries() {
      return this.formEntries;
   }

   protected final PlainTextListEntry addTextFormEntry(
      String labelKey, @Nullable String tooltipKey, @Nullable String hintKey, String initialValue, Consumer<String> responder
   ) {
      return this.addTextFormEntry(labelKey, tooltipKey, hintKey, initialValue, responder);
   }

   protected final PlainTextListEntry addTextFormEntry(
      String labelKey,
      @Nullable String tooltipKey,
      @Nullable String hintKey,
      String initialValue,
      Consumer<String> responder,
      FormTextButtonSpec... buttonSpecs
   ) {
      PlainTextListEntry entry = this.textEntry(labelKey, tooltipKey, hintKey, initialValue, responder);
      this.applyTextButtonSpecs(entry, buttonSpecs);
      this.appendEntry(entry);
      return entry;
   }

   protected final LocalizedTextListEntry addLocalizedTextFormEntry(
      String labelKey,
      @Nullable String tooltipKey,
      @Nullable String hintKey,
      String initialValue,
      Supplier<String> langGetter,
      Runnable onToggle,
      Consumer<String> responder
   ) {
      LocalizedTextListEntry entry = this.localizedTextEntry(labelKey, tooltipKey, hintKey, initialValue, langGetter, onToggle, responder);
      this.appendEntry(entry);
      return entry;
   }

   protected final ToggleListEntry addToggleFormEntry(String labelKey, @Nullable String tooltipKey, BooleanSupplier stateGetter, Runnable onToggle) {
      ToggleListEntry entry = new ToggleListEntry(labelKey, tooltipKey, stateGetter, onToggle);
      this.appendEntry(entry);
      return entry;
   }

   protected final ButtonListEntry addChoiceFormEntry(
      String labelKey,
      @Nullable String tooltipKey,
      int buttonWidth,
      Runnable onClick,
      Supplier<String> labelGetter,
      IntSupplier colorGetter,
      @Nullable String buttonTooltipText
   ) {
      return this.addChoiceFormEntry(labelKey, tooltipKey, buttonWidth, onClick, labelGetter, colorGetter, buttonTooltipText, DEFAULT_CHOICE_CONTROL_SCALE);
   }

   protected final ButtonListEntry addChoiceFormEntry(
      String labelKey,
      @Nullable String tooltipKey,
      int buttonWidth,
      Runnable onClick,
      Supplier<String> labelGetter,
      IntSupplier colorGetter,
      @Nullable String buttonTooltipText,
      float controlWidthScale
   ) {
      return this.addChoiceFormEntry(
         labelKey, tooltipKey, buttonWidth, onClick, labelGetter, colorGetter, buttonTooltipText == null ? null : () -> buttonTooltipText, controlWidthScale
      );
   }

   protected final ButtonListEntry addChoiceFormEntry(
      String labelKey,
      @Nullable String tooltipKey,
      int buttonWidth,
      Runnable onClick,
      Supplier<String> labelGetter,
      IntSupplier colorGetter,
      @Nullable Supplier<String> buttonTooltipGetter,
      float controlWidthScale
   ) {
      ButtonListEntry entry = new ButtonListEntry(labelKey, tooltipKey, buttonWidth, onClick, labelGetter, colorGetter, buttonTooltipGetter);
      if (Math.abs(controlWidthScale - EntryTextSupport.halfWidthControlScale()) < 1.0E-4F) {
         entry.setHalfWidthControl(buttonWidth);
      } else {
         entry.setControlWidthScale(controlWidthScale).setMinimumControlWidth(buttonWidth);
      }

      this.appendEntry(entry);
      return entry;
   }

   protected final FullButtonListEntry addFullButtonFormEntry(String label, @Nullable String tooltipText, Runnable onClick) {
      FullButtonListEntry entry = new FullButtonListEntry(label, tooltipText, onClick);
      this.appendEntry(entry);
      return entry;
   }

   protected final FullButtonListEntry addFullButtonFormEntry(
      Supplier<String> labelGetter, @Nullable Supplier<String> tooltipGetter, Runnable onClick, IntSupplier colorGetter, BooleanSupplier activeGetter
   ) {
      FullButtonListEntry entry = new FullButtonListEntry(labelGetter, tooltipGetter, onClick, colorGetter, activeGetter);
      this.appendEntry(entry);
      return entry;
   }

   protected final SectionHeaderListEntry addSectionHeaderEntry(String title) {
      SectionHeaderListEntry entry = new SectionHeaderListEntry(title);
      this.appendEntry(entry);
      return entry;
   }

   protected final SectionHeaderListEntry addSectionHeaderEntry(Supplier<String> titleGetter) {
      SectionHeaderListEntry entry = new SectionHeaderListEntry(titleGetter);
      this.appendEntry(entry);
      return entry;
   }

   protected final void applyTextButtonSpecs(PlainTextListEntry entry, FormTextButtonSpec... buttonSpecs) {
      if (buttonSpecs != null && buttonSpecs.length != 0) {
         if (buttonSpecs.length > 2) {
            throw new IllegalArgumentException("Text form entries support at most 2 trailing buttons");
         }

         for (FormTextButtonSpec buttonSpec : buttonSpecs) {
            if (buttonSpec != null) {
               buttonSpec.attach(this, entry);
            }
         }
      }
   }

   protected final void appendEntry(Entry entry) {
      if (this.currentEntries == null) {
         throw new IllegalStateException("Form entries can only be appended during collectEntries()");
      }

      this.currentEntries.add(entry);
   }

   public final PlainTextListEntry createTextEntry(
      String labelKey,
      @Nullable String tooltipKey,
      @Nullable String hintKey,
      String initialValue,
      Consumer<String> responder,
      FormTextButtonSpec... buttonSpecs
   ) {
      return this.addTextFormEntry(labelKey, tooltipKey, hintKey, initialValue, responder, buttonSpecs);
   }

   public final LocalizedTextListEntry createLocalizedTextEntry(
      String labelKey,
      @Nullable String tooltipKey,
      @Nullable String hintKey,
      String initialValue,
      Supplier<String> langGetter,
      Runnable onToggle,
      Consumer<String> responder
   ) {
      return this.addLocalizedTextFormEntry(labelKey, tooltipKey, hintKey, initialValue, langGetter, onToggle, responder);
   }

   public final ToggleListEntry createToggleEntry(String labelKey, @Nullable String tooltipKey, BooleanSupplier stateGetter, Runnable onToggle) {
      return this.addToggleFormEntry(labelKey, tooltipKey, stateGetter, onToggle);
   }

   public final ButtonListEntry createChoiceEntry(
      String labelKey,
      @Nullable String tooltipKey,
      int buttonWidth,
      Runnable onClick,
      Supplier<String> labelGetter,
      IntSupplier colorGetter,
      @Nullable String buttonTooltipText,
      float controlWidthScale
   ) {
      return this.addChoiceFormEntry(labelKey, tooltipKey, buttonWidth, onClick, labelGetter, colorGetter, buttonTooltipText, controlWidthScale);
   }

   public final ButtonListEntry createChoiceEntry(
      String labelKey,
      @Nullable String tooltipKey,
      int buttonWidth,
      Runnable onClick,
      Supplier<String> labelGetter,
      IntSupplier colorGetter,
      @Nullable Supplier<String> buttonTooltipGetter,
      float controlWidthScale
   ) {
      return this.addChoiceFormEntry(labelKey, tooltipKey, buttonWidth, onClick, labelGetter, colorGetter, buttonTooltipGetter, controlWidthScale);
   }

   public final FullButtonListEntry createFullButtonEntry(String label, @Nullable String tooltipText, Runnable onClick) {
      return this.addFullButtonFormEntry(label, tooltipText, onClick);
   }

   public final FullButtonListEntry createFullButtonEntry(
      Supplier<String> labelGetter, @Nullable Supplier<String> tooltipGetter, Runnable onClick, IntSupplier colorGetter, BooleanSupplier activeGetter
   ) {
      return this.addFullButtonFormEntry(labelGetter, tooltipGetter, onClick, colorGetter, activeGetter);
   }

   public final SectionHeaderListEntry createSectionHeaderEntry(String title) {
      return this.addSectionHeaderEntry(title);
   }

   public final SectionHeaderListEntry createSectionHeaderEntry(Supplier<String> titleGetter) {
      return this.addSectionHeaderEntry(titleGetter);
   }

   public final void appendBuiltEntry(Entry entry) {
      this.appendEntry(entry);
   }
}
