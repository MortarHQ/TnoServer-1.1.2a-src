package com.blamejared.controlling.client;

import com.blamejared.controlling.ControllingConstants;
import com.blamejared.controlling.api.entries.ICategoryEntry;
import com.blamejared.controlling.api.entries.IKeyEntry;
import com.blamejared.controlling.api.events.IKeyEntryListenersEvent;
import com.blamejared.controlling.api.events.IKeyEntryMouseClickedEvent;
import com.blamejared.controlling.api.events.IKeyEntryMouseReleasedEvent;
import com.blamejared.controlling.platform.Services;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.UnaryOperator;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ComponentPath;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.narration.NarratableEntry.NarrationPriority;
import net.minecraft.client.gui.navigation.FocusNavigationEvent;
import net.minecraft.client.gui.screens.options.controls.KeyBindsScreen;
import net.minecraft.client.gui.screens.options.controls.KeyBindsList.Entry;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.apache.commons.lang3.ArrayUtils;
import org.jetbrains.annotations.Nullable;

public class NewKeyBindsList extends CustomList {
   private final KeyBindsScreen controlsScreen;
   private final Minecraft mc;
   private int maxListLabelWidth;

   public NewKeyBindsList(KeyBindsScreen controls, Minecraft mcIn) {
      super(controls, mcIn);
      this.height -= 52;
      this.setY(48);
      this.controlsScreen = controls;
      this.mc = mcIn;
      this.children().clear();
      this.allEntries = new ArrayList<>();
      KeyMapping[] bindings = (KeyMapping[])ArrayUtils.clone(mcIn.options.keyMappings);
      Arrays.sort(bindings);
      String lastCategory = null;

      for (KeyMapping keybinding : bindings) {
         String category = keybinding.getCategory();
         if (!category.equals(lastCategory)) {
            lastCategory = category;
            if (!category.endsWith(".hidden")) {
               this.addEntry(new NewKeyBindsList.CategoryEntry(Component.translatable(category)));
            }
         }

         Component component = Services.PLATFORM.getKeyName(keybinding);
         int width = mcIn.font.width(component);
         if (width > this.maxListLabelWidth) {
            this.maxListLabelWidth = width;
         }

         if (!category.endsWith(".hidden")) {
            this.addEntry(new NewKeyBindsList.KeyEntry(keybinding, component));
         }
      }
   }

   public int getBottom() {
      return this.controlsScreen.height - 56;
   }

   public class CategoryEntry extends Entry implements ICategoryEntry {
      private final Component name;
      private final int labelWidth;

      public CategoryEntry(Component name) {
         this.name = name;
         this.labelWidth = NewKeyBindsList.this.mc.font.width(this.name);
      }

      public void render(
         GuiGraphics guiGraphics, int slotIndex, int y, int x, int rowLeft, int rowWidth, int mouseX, int mouseY, boolean hovered, float partialTicks
      ) {
         guiGraphics.drawString(
            NewKeyBindsList.this.mc.font,
            this.name,
            Objects.requireNonNull(NewKeyBindsList.this.minecraft.screen).width / 2 - this.labelWidth / 2,
            y + rowWidth - 9 - 1,
            16777215
         );
      }

      public List<? extends NarratableEntry> narratables() {
         return ImmutableList.of(new NarratableEntry() {
            public NarrationPriority narrationPriority() {
               return NarrationPriority.HOVERED;
            }

            public void updateNarration(NarrationElementOutput neo) {
               neo.add(NarratedElementType.TITLE, CategoryEntry.this.name);
            }
         });
      }

      public List<? extends GuiEventListener> children() {
         return Collections.emptyList();
      }

      @Nullable
      public ComponentPath nextFocusPath(FocusNavigationEvent $$0) {
         return null;
      }

      protected void refreshEntry() {
      }

      @Override
      public Component name() {
         return this.name;
      }
   }

   public class KeyEntry extends Entry implements IKeyEntry {
      private final KeyMapping key;
      private final Component keyDesc;
      private final Button btnChangeKeyBinding;
      private final Button btnResetKeyBinding;
      private boolean hasCollision;
      private final Component categoryName;

      public KeyEntry(final KeyMapping key, final Component keyDesc) {
         this.key = key;
         this.keyDesc = keyDesc;
         this.btnChangeKeyBinding = Button.builder(this.keyDesc, btn -> {
               NewKeyBindsList.this.controlsScreen.selectedKey = key;
               NewKeyBindsList.this.resetMappingAndUpdateButtons();
            })
            .bounds(0, 0, 75, 20)
            .createNarration(
               supp -> key.isUnbound()
                  ? Component.translatable("narrator.controls.unbound", new Object[]{keyDesc})
                  : Component.translatable("narrator.controls.bound", new Object[]{keyDesc, supp.get()})
            )
            .build();
         this.btnResetKeyBinding = Button.builder(ControllingConstants.COMPONENT_CONTROLS_RESET, btn -> {
            Services.PLATFORM.setToDefault(NewKeyBindsList.this.minecraft.options, key);
            NewKeyBindsList.this.resetMappingAndUpdateButtons();
         }).bounds(0, 0, 50, 20).createNarration(supp -> Component.translatable("narrator.controls.reset", new Object[]{keyDesc})).build();
         this.categoryName = Component.translatable(this.key.getCategory());
         this.refreshEntry();
      }

      @Override
      public void render(
         GuiGraphics guiGraphics, int slotIndex, int y, int x, int rowLeft, int rowWidth, int mouseX, int mouseY, boolean hovered, float partialTicks
      ) {
         Services.EVENT.fireKeyEntryRenderEvent(this, guiGraphics, slotIndex, y, x, rowLeft, rowWidth, mouseX, mouseY, hovered, partialTicks);
         int resetKeyX = NewKeyBindsList.this.getScrollbarPosition() - this.btnResetKeyBinding.getWidth() - 10;
         this.btnResetKeyBinding.setX(resetKeyX);
         int top = y - 2;
         this.btnResetKeyBinding.setY(top);
         this.btnResetKeyBinding.render(guiGraphics, mouseX, mouseY, partialTicks);
         this.btnChangeKeyBinding.setX(resetKeyX - 5 - this.btnChangeKeyBinding.getWidth());
         this.btnChangeKeyBinding.setY(top);
         guiGraphics.drawString(NewKeyBindsList.this.mc.font, this.keyDesc, x, y + rowWidth / 2 - 4, -1);
         if (this.hasCollision) {
            int markerWidth = 3;
            int minX = this.btnChangeKeyBinding.getX() - 6;
            guiGraphics.fill(minX, y + 2, minX + markerWidth, y + rowWidth + 2, -65536);
         }

         this.btnChangeKeyBinding.render(guiGraphics, mouseX, mouseY, partialTicks);
      }

      @Override
      public List<GuiEventListener> children() {
         return (List<GuiEventListener>)Services.EVENT.fireKeyEntryListenersEvent(this).map(IKeyEntryListenersEvent::getListeners, UnaryOperator.identity());
      }

      public List<? extends NarratableEntry> narratables() {
         return ImmutableList.of(this.btnChangeKeyBinding, this.btnResetKeyBinding);
      }

      @Override
      public boolean mouseClicked(double mouseX, double mouseY, int buttonId) {
         return Services.EVENT
               .fireKeyEntryMouseClickedEvent(this, mouseX, mouseY, buttonId)
               .map(IKeyEntryMouseClickedEvent::isHandled, UnaryOperator.identity())
            ? true
            : super.mouseClicked(mouseX, mouseY, buttonId);
      }

      @Override
      public boolean mouseReleased(double mouseX, double mouseY, int buttonId) {
         return Services.EVENT
               .fireKeyEntryMouseReleasedEvent(this, mouseX, mouseY, buttonId)
               .map(IKeyEntryMouseReleasedEvent::isHandled, UnaryOperator.identity())
            ? true
            : super.mouseReleased(mouseX, mouseY, buttonId);
      }

      @Override
      public KeyMapping getKey() {
         return this.key;
      }

      @Override
      public Component getKeyDesc() {
         return this.keyDesc;
      }

      @Override
      public Component categoryName() {
         return this.categoryName;
      }

      @Override
      public Button getBtnResetKeyBinding() {
         return this.btnResetKeyBinding;
      }

      @Override
      public Button getBtnChangeKeyBinding() {
         return this.btnChangeKeyBinding;
      }

      protected void refreshEntry() {
         this.btnChangeKeyBinding.setMessage(this.key.getTranslatedKeyMessage());
         this.btnResetKeyBinding.active = !this.key.isDefault();
         this.hasCollision = false;
         MutableComponent duplicates = Component.empty();
         if (!this.key.isUnbound()) {
            KeyMapping[] mappings = NewKeyBindsList.this.minecraft.options.keyMappings;

            for (KeyMapping mapping : mappings) {
               if (mapping != this.key && this.key.same(mapping) || Services.PLATFORM.hasConflictingModifier(this.key, mapping)) {
                  if (this.hasCollision) {
                     duplicates.append(", ");
                  }

                  this.hasCollision = true;
                  duplicates.append(Services.PLATFORM.getKeyName(mapping));
               }
            }
         }

         MutableComponent tooltip = Component.translatable(this.key.getCategory());
         if (this.hasCollision) {
            this.btnChangeKeyBinding
               .setMessage(
                  Component.literal("[ ")
                     .append(this.btnChangeKeyBinding.getMessage().copy().withStyle(ChatFormatting.WHITE))
                     .append(" ]")
                     .withStyle(ChatFormatting.RED)
               );
            tooltip.append(CommonComponents.NEW_LINE);
            tooltip.append(Component.translatable("controls.keybinds.duplicateKeybinds", new Object[]{duplicates}));
         }

         this.btnChangeKeyBinding.setTooltip(Tooltip.create(tooltip));
         if (NewKeyBindsList.this.controlsScreen.selectedKey == this.key) {
            this.btnChangeKeyBinding
               .setMessage(
                  Component.literal("> ")
                     .append(this.btnChangeKeyBinding.getMessage().copy().withStyle(new ChatFormatting[]{ChatFormatting.WHITE, ChatFormatting.UNDERLINE}))
                     .append(" <")
                     .withStyle(ChatFormatting.YELLOW)
               );
         }
      }
   }
}
