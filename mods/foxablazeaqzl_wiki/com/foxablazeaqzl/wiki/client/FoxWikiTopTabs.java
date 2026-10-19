package com.foxablazeaqzl.wiki.client;

import io.github.manasmods.tensura.util.client.RenderHelper;
import io.github.manasmods.tensura.util.client.ScreenHelper;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;

public final class FoxWikiTopTabs {
   private static final ResourceLocation SKILL_ICON = ResourceLocation.fromNamespaceAndPath("tensura", "textures/gui/skill/skill_tab_icon.png");
   private static final ResourceLocation ITEM_ICON = ResourceLocation.fromNamespaceAndPath("tensura", "textures/item/high_quality_magic_crystal.png");
   private static final int SKILL_CLICK_X = 4;
   private static final int SKILL_CLICK_Y = 0;
   private static final int SKILL_CLICK_WIDTH = 24;
   private static final int SKILL_CLICK_HEIGHT = 23;
   private static final int ITEM_CLICK_X = 30;
   private static final int ITEM_CLICK_Y = 2;
   private static final int ITEM_CLICK_WIDTH = 24;
   private static final int ITEM_CLICK_HEIGHT = 21;

   private FoxWikiTopTabs() {
   }

   public static void render(GuiGraphics graphics, Screen screen, int guiLeft, int guiTop, int mouseX, int mouseY) {
      graphics.blit(SKILL_ICON, guiLeft + 9, guiTop + 7, 16, 16, 0.0F, 0.0F, 16, 16, 16, 16);
      graphics.blit(ITEM_ICON, guiLeft + 36, guiTop + 8, 14, 14, 0.0F, 0.0F, 16, 16, 16, 16);
      if (overSkill(mouseX, mouseY, guiLeft, guiTop)) {
         screen.setTooltipForNextRenderPass(FoxWikiScreen.TITLE);
      } else if (overItem(mouseX, mouseY, guiLeft, guiTop)) {
         screen.setTooltipForNextRenderPass(FoxItemWikiScreen.TITLE);
      }
   }

   public static boolean clicked(double mouseX, double mouseY, int button, int guiLeft, int guiTop, FoxWikiTopTabs.Tab activeTab) {
      if (button != 0) {
         return false;
      }

      if (overSkill(mouseX, mouseY, guiLeft, guiTop)) {
         if (activeTab == FoxWikiTopTabs.Tab.SKILL) {
            ScreenHelper.clicked();
         } else {
            ScreenHelper.clicked(() -> ScreenHelper.openScreen(new FoxWikiScreen()));
         }

         return true;
      } else if (overItem(mouseX, mouseY, guiLeft, guiTop)) {
         if (activeTab == FoxWikiTopTabs.Tab.ITEM) {
            ScreenHelper.clicked();
         } else {
            ScreenHelper.clicked(() -> ScreenHelper.openScreen(new FoxItemWikiScreen()));
         }

         return true;
      } else {
         return false;
      }
   }

   private static boolean overSkill(double mouseX, double mouseY, int guiLeft, int guiTop) {
      return RenderHelper.mouseOver(mouseX, mouseY, guiLeft + 4, guiLeft + 4 + 24, guiTop + 0, guiTop + 0 + 23);
   }

   private static boolean overItem(double mouseX, double mouseY, int guiLeft, int guiTop) {
      return RenderHelper.mouseOver(mouseX, mouseY, guiLeft + 30, guiLeft + 30 + 24, guiTop + 2, guiTop + 2 + 21);
   }

   public enum Tab {
      SKILL,
      ITEM;
   }
}
