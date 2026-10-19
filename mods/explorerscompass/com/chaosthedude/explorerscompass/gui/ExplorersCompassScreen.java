package com.chaosthedude.explorerscompass.gui;

import com.chaosthedude.explorerscompass.ExplorersCompass;
import com.chaosthedude.explorerscompass.items.ExplorersCompassItem;
import com.chaosthedude.explorerscompass.network.SearchForNextPacket;
import com.chaosthedude.explorerscompass.network.SearchPacket;
import com.chaosthedude.explorerscompass.network.TeleportPacket;
import com.chaosthedude.explorerscompass.sorting.ISorting;
import com.chaosthedude.explorerscompass.sorting.NameSorting;
import com.chaosthedude.explorerscompass.util.CompassState;
import com.chaosthedude.explorerscompass.util.StructureUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.PacketDistributor;

@OnlyIn(Dist.CLIENT)
public class ExplorersCompassScreen extends Screen {
   private Level level;
   private Player player;
   private List<ResourceLocation> allowedStructureKeys;
   private List<ResourceLocation> structureKeysMatchingSearch;
   private ItemStack stack;
   private ExplorersCompassItem explorersCompass;
   private ResourceLocation foundStructureKey;
   private Button searchButton;
   private Button searchGroupButton;
   private Button searchForNextButton;
   private Button sortByButton;
   private Button teleportButton;
   private Button cancelButton;
   private TransparentTextField searchTextField;
   private StructureSearchList selectionList;
   private ISorting sortingCategory;

   public ExplorersCompassScreen(
      Level level, Player player, ItemStack stack, ExplorersCompassItem explorersCompass, List<ResourceLocation> allowedStructureKeys
   ) {
      super(Component.translatable("string.explorerscompass.selectStructure"));
      this.level = level;
      this.player = player;
      this.stack = stack;
      this.explorersCompass = explorersCompass;
      this.allowedStructureKeys = new ArrayList<>(allowedStructureKeys);
      this.structureKeysMatchingSearch = new ArrayList<>(this.allowedStructureKeys);
      this.sortingCategory = new NameSorting();
      if (explorersCompass.getState(stack) == CompassState.FOUND) {
         this.foundStructureKey = explorersCompass.getStructureKey(stack);
      }
   }

   public boolean mouseScrolled(double par1, double par2, double par3, double par4) {
      return this.selectionList.mouseScrolled(par1, par2, par3, par4);
   }

   protected void init() {
      this.setupWidgets();
   }

   public void tick() {
      boolean hasSelection = this.selectionList.hasSelection();
      boolean selectionMatchesFound = hasSelection
         && this.foundStructureKey != null
         && ((StructureSearchEntry)this.selectionList.getSelected()).getStructureKey().equals(this.foundStructureKey);
      this.teleportButton.active = selectionMatchesFound;
      this.searchForNextButton.active = selectionMatchesFound;
      this.searchButton.active = this.searchGroupButton.active = hasSelection;
      if (ExplorersCompass.synced) {
         this.removeWidget(this.selectionList);
         this.allowedStructureKeys = new ArrayList<>(ExplorersCompass.allowedStructureKeys);
         this.structureKeysMatchingSearch = new ArrayList<>(this.allowedStructureKeys);
         this.selectionList = new StructureSearchList(
            this, this.minecraft, this.player, this.foundStructureKey, 130, 40, this.width - 140, this.height - 50, 50
         );
         this.addRenderableWidget(this.selectionList);
         this.teleportButton.visible = ExplorersCompass.canTeleport;
         this.searchForNextButton.visible = ExplorersCompass.maxNextSearches > 0;
         if (this.searchForNextButton.visible) {
            this.sortByButton.setPosition(10, 125);
         } else {
            this.sortByButton.setPosition(10, 90);
         }

         ExplorersCompass.synced = false;
      }
   }

   public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
      super.render(guiGraphics, mouseX, mouseY, partialTicks);
      guiGraphics.drawCenteredString(this.font, this.title, 65, 15, 16777215);
   }

   public boolean keyPressed(int par1, int par2, int par3) {
      boolean ret = super.keyPressed(par1, par2, par3);
      if (this.searchTextField.isFocused()) {
         this.processSearchTerm();
         return true;
      } else {
         return ret;
      }
   }

   public boolean charTyped(char typedChar, int keyCode) {
      boolean ret = super.charTyped(typedChar, keyCode);
      if (this.searchTextField.isFocused()) {
         this.processSearchTerm();
         return true;
      } else {
         return ret;
      }
   }

   public void searchForStructure(ResourceLocation key) {
      this.minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
      PacketDistributor.sendToServer(new SearchPacket(key, List.of(key), this.player.blockPosition(), false), new CustomPacketPayload[0]);
      this.minecraft.setScreen(null);
   }

   public void searchForGroup(ResourceLocation key) {
      this.minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
      PacketDistributor.sendToServer(
         new SearchPacket(key, ExplorersCompass.typeKeysToStructureKeys.get(key), this.player.blockPosition(), true), new CustomPacketPayload[0]
      );
      this.minecraft.setScreen(null);
   }

   public void searchForNext() {
      this.minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
      PacketDistributor.sendToServer(new SearchForNextPacket(), new CustomPacketPayload[0]);
      this.minecraft.setScreen(null);
   }

   public void teleport() {
      PacketDistributor.sendToServer(new TeleportPacket(), new CustomPacketPayload[0]);
      this.minecraft.setScreen(null);
   }

   public void processSearchTerm() {
      this.structureKeysMatchingSearch = new ArrayList<>();
      String searchTerm = this.searchTextField.getValue().toLowerCase();

      for (ResourceLocation key : this.allowedStructureKeys) {
         if (searchTerm.startsWith("@")) {
            if (StructureUtils.getPrettyStructureSource(key).toLowerCase().contains(searchTerm.substring(1))) {
               this.structureKeysMatchingSearch.add(key);
            }
         } else if (StructureUtils.getPrettyStructureName(key).toLowerCase().contains(searchTerm)) {
            this.structureKeysMatchingSearch.add(key);
         }
      }

      this.selectionList.refreshList(true);
   }

   public List<ResourceLocation> sortStructures() {
      List<ResourceLocation> structures = this.structureKeysMatchingSearch;
      Collections.sort(structures, new NameSorting());
      Collections.sort(structures, this.sortingCategory);
      return structures;
   }

   private void setupWidgets() {
      this.clearWidgets();
      this.searchButton = (Button)this.addRenderableWidget(
         new TransparentButton(10, 40, 110, 20, Component.translatable("string.explorerscompass.search"), onPress -> {
            if (this.selectionList.hasSelection()) {
               ((StructureSearchEntry)this.selectionList.getSelected()).searchForStructure();
            }
         })
      );
      this.searchButton.active = false;
      this.searchGroupButton = (Button)this.addRenderableWidget(
         new TransparentButton(10, 65, 110, 20, Component.translatable("string.explorerscompass.searchForGroup"), onPress -> {
            if (this.selectionList.hasSelection()) {
               ((StructureSearchEntry)this.selectionList.getSelected()).searchForGroup();
            }
         })
      );
      this.searchGroupButton.active = false;
      this.searchForNextButton = (Button)this.addRenderableWidget(
         new TransparentButton(10, 90, 110, 20, Component.translatable("string.explorerscompass.searchForNext"), onPress -> this.searchForNext())
      );
      this.searchForNextButton.visible = ExplorersCompass.maxNextSearches > 0;
      this.searchForNextButton.active = false;
      this.sortByButton = (Button)this.addRenderableWidget(
         new TransparentButton(
            10,
            125,
            110,
            20,
            Component.translatable("string.explorerscompass.sortBy").append(Component.literal(": " + this.sortingCategory.getLocalizedName())),
            onPress -> {
               this.sortingCategory = this.sortingCategory.next();
               this.sortByButton
                  .setMessage(
                     Component.translatable("string.explorerscompass.sortBy").append(Component.literal(": " + this.sortingCategory.getLocalizedName()))
                  );
               this.selectionList.refreshList(true);
            }
         )
      );
      if (!this.searchForNextButton.visible) {
         this.sortByButton.setPosition(10, 90);
      }

      this.cancelButton = (Button)this.addRenderableWidget(
         new TransparentButton(10, this.height - 30, 110, 20, Component.translatable("gui.cancel"), onPress -> this.minecraft.setScreen(null))
      );
      this.teleportButton = (Button)this.addRenderableWidget(
         new TransparentButton(this.width - 120, 10, 110, 20, Component.translatable("string.explorerscompass.teleport"), onPress -> this.teleport())
      );
      this.teleportButton.visible = ExplorersCompass.canTeleport;
      this.teleportButton.active = false;
      this.searchTextField = new TransparentTextField(this.font, this.width / 2 - 82, 10, 140, 20, Component.translatable("string.explorerscompass.search"));
      this.addRenderableWidget(this.searchTextField);
      this.selectionList = new StructureSearchList(this, this.minecraft, this.player, this.foundStructureKey, 130, 40, this.width - 140, this.height - 50, 50);
      this.addRenderableWidget(this.selectionList);
   }
}
