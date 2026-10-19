package com.blamejared.controlling.client;

import com.blamejared.controlling.ControllingConstants;
import com.blamejared.controlling.api.entries.IInputEntry;
import com.blamejared.controlling.mixin.AccessInputConstantsKey;
import com.blamejared.controlling.mixin.AccessKeyMapping;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.InputConstants.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.options.controls.KeyBindsScreen;
import net.minecraft.client.gui.screens.options.controls.KeyBindsList.Entry;

public class FreeKeysList extends CustomList {
   private final KeyBindsScreen controlsScreen;
   private final Minecraft mc;
   private int maxListLabelWidth;
   private final List<KeyMapping> keyBindings;

   public FreeKeysList(KeyBindsScreen controls, Minecraft mcIn) {
      super(controls, mcIn);
      this.height -= 52;
      this.setY(48);
      this.controlsScreen = controls;
      this.mc = mcIn;
      this.children().clear();
      this.allEntries = new ArrayList<>();
      this.keyBindings = Arrays.stream(this.mc.options.keyMappings).collect(Collectors.toList());
      this.recalculate();
   }

   public int getBottom() {
      return this.controlsScreen.height - 56;
   }

   public int getRight() {
      return this.controlsScreen.width + 45;
   }

   public void recalculate() {
      this.children().clear();
      this.allEntries.clear();
      this.addEntry(new FreeKeysList.HeaderEntry("Available Keys"));
      AccessInputConstantsKey.controlling$getNAME_MAP()
         .values()
         .stream()
         .filter(input -> !input.getName().startsWith("key.keyboard.world"))
         .filter(Predicate.not(InputConstants.UNKNOWN::equals))
         .sorted(Comparator.comparing(o -> o.getDisplayName().getString()))
         .forEach(input -> {
            if (this.keyBindings.stream().noneMatch(keyBinding -> ((AccessKeyMapping)keyBinding).controlling$getKey().equals(input))) {
               int i = this.mc.font.width(input.getDisplayName().getString());
               if (i > this.maxListLabelWidth) {
                  this.maxListLabelWidth = i;
               }

               this.addEntry(new FreeKeysList.InputEntry(input));
            }
         });
   }

   protected int getScrollbarPosition() {
      return super.getScrollbarPosition() + 15 + 20;
   }

   public class HeaderEntry extends Entry {
      private final String text;

      public HeaderEntry(String text) {
         this.text = text;
      }

      public List<? extends NarratableEntry> narratables() {
         return ImmutableList.of();
      }

      public List<? extends GuiEventListener> children() {
         return ImmutableList.of();
      }

      public void render(
         GuiGraphics guiGraphics, int slotIndex, int y, int x, int width, int height, int mouseX, int mouseY, boolean hovered, float partialTicks
      ) {
         guiGraphics.drawCenteredString(
            FreeKeysList.this.mc.font,
            ControllingConstants.COMPONENT_OPTIONS_AVAILABLE_KEYS,
            Objects.requireNonNull(FreeKeysList.this.mc.screen).width / 2 - this.text.length() / 2,
            y + height - 9 - 1,
            16777215
         );
      }

      protected void refreshEntry() {
      }
   }

   public class InputEntry extends Entry implements IInputEntry {
      private final Key input;

      public InputEntry(Key input) {
         this.input = input;
      }

      @Override
      public Key getInput() {
         return this.input;
      }

      public void render(
         GuiGraphics guiGraphics, int slotIndex, int y, int x, int width, int height, int mouseX, int mouseY, boolean hovered, float partialTicks
      ) {
         String str = this.input.toString() + " - " + this.input.getValue();
         int length = FreeKeysList.this.mc.font.width(this.input.getDisplayName().getString());
         guiGraphics.drawString(FreeKeysList.this.mc.font, str, x, y + height / 2 - 4, 16777215);
         guiGraphics.renderTooltip(FreeKeysList.this.mc.font, this.input.getDisplayName(), x + width - length, y + height);
      }

      public List<? extends NarratableEntry> narratables() {
         return ImmutableList.of();
      }

      public List<? extends GuiEventListener> children() {
         return ImmutableList.of();
      }

      protected void refreshEntry() {
      }
   }
}
