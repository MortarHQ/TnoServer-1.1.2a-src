package dev.ftb.mods.ftbfiltersystem.client.gui;

import dev.architectury.platform.Platform;
import dev.ftb.mods.ftbfiltersystem.api.client.gui.AbstractFilterConfigScreen;
import dev.ftb.mods.ftbfiltersystem.api.client.gui.AbstractFilterScreen;
import dev.ftb.mods.ftbfiltersystem.client.gui.widget.CustomSelectionList;
import dev.ftb.mods.ftbfiltersystem.filter.ModFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ModConfigScreen extends AbstractFilterConfigScreen<ModFilter> implements GhostDropReceiver {
   private static String lastSearch;
   private final List<ModConfigScreen.ModData> matchingModData = new ArrayList<>();
   private EditBox searchField;
   private ModConfigScreen.ModList modList;

   public ModConfigScreen(ModFilter filter, AbstractFilterScreen parentScreen) {
      super(filter, parentScreen, 200, 176);
   }

   @Override
   protected void init() {
      super.init();
      this.searchField = this.makeSearchEditBox(this.leftPos + 8, this.topPos + 18, () -> lastSearch, s -> lastSearch = s);
      this.updateSearchEntries();
      this.modList = new ModConfigScreen.ModList(this.minecraft, this.topPos + 32, this.getListWidth(), this.getListHeight());
      this.modList.setX(this.leftPos + 8);
      this.addWidget(this.modList);
      this.modList
         .children()
         .stream()
         .filter(child -> child.modData.modId().equals(this.filter.getStringArg(this.minecraft.level.registryAccess())))
         .findFirst()
         .ifPresent(entry -> this.modList.selectAndCenter(entry));
   }

   @Override
   public void render(GuiGraphics guiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
      super.render(guiGraphics, pMouseX, pMouseY, pPartialTick);
      this.modList.render(guiGraphics, pMouseX, pMouseY, pPartialTick);
   }

   @Nullable
   protected ModFilter makeNewFilter() {
      return this.modList.getSelected() != null
         ? new ModFilter(this.filter.getParent(), ((ModConfigScreen.ModList.ModEntry)this.modList.getSelected()).modData.modId)
         : null;
   }

   @Override
   protected void doScheduledUpdate() {
      this.updateSearchEntries();
      this.modList.addChildren();
   }

   private int getListHeight() {
      return 148;
   }

   private int getListWidth() {
      return 176;
   }

   private void updateSearchEntries() {
      String srch = this.searchField.getValue().toLowerCase(Locale.ROOT);
      this.matchingModData.clear();
      this.matchingModData
         .addAll(
            Platform.getMods()
               .stream()
               .filter(mod -> srch.isEmpty() || mod.getModId().toLowerCase(Locale.ROOT).contains(srch))
               .map(mod -> new ModConfigScreen.ModData(mod.getModId(), mod.getName()))
               .sorted()
               .toList()
         );
   }

   @Override
   public Rect2i getGhostDropRegion() {
      return new Rect2i(this.leftPos + 9, this.topPos + 33, this.getListWidth() + 6, this.getListHeight() + 2);
   }

   @Override
   public void receiveGhostDrop(ItemStack stack) {
      String modId = stack.getItem().arch$registryName().getNamespace();
      this.modList
         .children()
         .stream()
         .filter(entry -> entry.modData.modId.equals(modId))
         .findFirst()
         .ifPresent(modEntry -> this.modList.selectAndCenter(modEntry));
   }

   private record ModData(String modId, String modName) implements Comparable<ModConfigScreen.ModData> {
      public int compareTo(@NotNull ModConfigScreen.ModData modData) {
         return this.modId.compareTo(modData.modId);
      }
   }

   private class ModList extends CustomSelectionList<ModConfigScreen.ModList.ModEntry> {
      private static final int ELEMENT_HEIGHT = 12;

      public ModList(Minecraft minecraft, int y, int width, int height) {
         super(minecraft, width, height, y, 12);
      }

      protected void renderListBackground(GuiGraphics guiGraphics) {
      }

      @Override
      protected List<ModConfigScreen.ModList.ModEntry> buildChildrenList() {
         return ModConfigScreen.this.matchingModData.stream().map(x$0 -> new ModConfigScreen.ModList.ModEntry(x$0)).toList();
      }

      private class ModEntry extends CustomSelectionList.Entry<ModConfigScreen.ModList.ModEntry> {
         private final ModConfigScreen.ModData modData;

         private ModEntry(ModConfigScreen.ModData modData) {
            this.modData = modData;
         }

         public void render(
            GuiGraphics guiGraphics, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean isMouseOver, float partialTick
         ) {
            Component txt = Component.literal(this.modData.modId())
               .withStyle(Style.EMPTY.withColor(2105440))
               .append(Component.literal(" ["))
               .append(Component.literal(this.modData.modName()).withStyle(Style.EMPTY.withColor(8405024)))
               .append(Component.literal("]"));
            guiGraphics.drawString(ModConfigScreen.this.font, txt, left + 1, top + 1, 4210752, false);
         }

         @Override
         protected boolean onMouseClick(double x, double y, int button, boolean isDoubleClick) {
            ModList.this.setSelected(this);
            if (isDoubleClick) {
               ModConfigScreen.this.applyChanges();
            }

            return true;
         }

         public Component getNarration() {
            return Component.translatable("narrator.select", new Object[]{this.modData.modId()});
         }
      }
   }
}
