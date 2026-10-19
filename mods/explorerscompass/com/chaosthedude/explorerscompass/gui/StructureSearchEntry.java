package com.chaosthedude.explorerscompass.gui;

import com.chaosthedude.explorerscompass.ExplorersCompass;
import com.chaosthedude.explorerscompass.util.StructureUtils;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList.Entry;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class StructureSearchEntry extends Entry<StructureSearchEntry> {
   private static final ResourceLocation[] ENABLED_LEVEL_SPRITES = new ResourceLocation[]{
      ResourceLocation.withDefaultNamespace("container/enchanting_table/level_1"),
      ResourceLocation.withDefaultNamespace("container/enchanting_table/level_2"),
      ResourceLocation.withDefaultNamespace("container/enchanting_table/level_3")
   };
   private static final ResourceLocation[] DISABLED_LEVEL_SPRITES = new ResourceLocation[]{
      ResourceLocation.withDefaultNamespace("container/enchanting_table/level_1_disabled"),
      ResourceLocation.withDefaultNamespace("container/enchanting_table/level_2_disabled"),
      ResourceLocation.withDefaultNamespace("container/enchanting_table/level_3_disabled")
   };
   private final Minecraft mc;
   private final ExplorersCompassScreen parentScreen;
   private final ResourceLocation structureKey;
   private final StructureSearchList structuresList;
   private final Player player;
   private int xpLevels;
   private long lastClickTime;

   public StructureSearchEntry(StructureSearchList structuresList, ResourceLocation structureKey, Player player) {
      this.structuresList = structuresList;
      this.structureKey = structureKey;
      this.player = player;
      this.parentScreen = structuresList.getParentScreen();
      this.mc = Minecraft.getInstance();
      this.xpLevels = 0;
      if (ExplorersCompass.xpLevelsForAllowedStructureKeys.containsKey(structureKey)) {
         int levels = ExplorersCompass.xpLevelsForAllowedStructureKeys.get(structureKey);
         if (levels > 3) {
            levels = 3;
         }

         this.xpLevels = levels;
      }
   }

   public void render(
      GuiGraphics guiGraphics, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean isHovering, float partialTick
   ) {
      if (this.xpLevels > 0) {
         int spriteSize = (int)(height * 0.4F);
         int spriteBorder = (height - spriteSize) / 2;
         int spriteIndex = this.xpLevels - 1;
         ResourceLocation spriteId = this.isEnabled() ? ENABLED_LEVEL_SPRITES[spriteIndex] : DISABLED_LEVEL_SPRITES[spriteIndex];
         guiGraphics.blitSprite(spriteId, left + width - spriteSize - spriteBorder, top + spriteBorder, spriteSize, spriteSize);
      }

      int nameColor = this.isEnabled() ? 16777215 : 8421504;
      int infoColor = this.isEnabled() ? 8421504 : 5592405;
      guiGraphics.drawString(
         this.mc.font, Component.literal(StructureUtils.getPrettyStructureName(this.structureKey)), left + 5, top + height / 2 - (9 + 2) * 2, nameColor, true
      );
      guiGraphics.drawString(
         this.mc.font,
         Component.translatable("string.explorerscompass.source").append(": " + StructureUtils.getPrettyStructureSource(this.structureKey)),
         left + 5,
         top + height / 2 - (9 + 2) * 1,
         infoColor,
         true
      );
      guiGraphics.drawString(
         this.mc.font,
         Component.translatable("string.explorerscompass.group")
            .append(": " + StructureUtils.getPrettyStructureName(ExplorersCompass.structureKeysToTypeKeys.get(this.structureKey))),
         left + 5,
         top + height / 2 - (9 + 2) * 0,
         infoColor,
         true
      );
      guiGraphics.drawString(
         this.mc.font,
         Component.translatable("string.explorerscompass.dimension")
            .append(": " + StructureUtils.dimensionKeysToString(ExplorersCompass.dimensionKeysForAllowedStructureKeys.get(this.structureKey))),
         left + 5,
         top + height / 2 + (9 + 2) * 1,
         infoColor,
         true
      );
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (button == 0 && this.isEnabled()) {
         this.structuresList.setSelected(this);
         if (Util.getMillis() - this.lastClickTime < 250L) {
            this.searchForStructure();
            return true;
         } else {
            this.lastClickTime = Util.getMillis();
            return false;
         }
      } else {
         return false;
      }
   }

   public Component getNarration() {
      return Component.literal(StructureUtils.getPrettyStructureName(this.structureKey));
   }

   public void searchForStructure() {
      this.mc.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
      this.parentScreen.searchForStructure(this.structureKey);
   }

   public void searchForGroup() {
      this.mc.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
      this.parentScreen.searchForGroup(ExplorersCompass.structureKeysToTypeKeys.get(this.structureKey));
   }

   public boolean isEnabled() {
      return ExplorersCompass.infiniteXp || this.player.experienceLevel >= this.xpLevels;
   }

   public ResourceLocation getStructureKey() {
      return this.structureKey;
   }

   public ResourceLocation getTypeKey() {
      return ExplorersCompass.structureKeysToTypeKeys.get(this.structureKey);
   }
}
