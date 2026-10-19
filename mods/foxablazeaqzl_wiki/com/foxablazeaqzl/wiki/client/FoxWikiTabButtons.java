package com.foxablazeaqzl.wiki.client;

import io.github.manasmods.tensura.client.screen.widgets.SimpleButton;
import io.github.manasmods.tensura.util.client.RenderHelper;
import io.github.manasmods.tensura.util.client.ScreenHelper;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public final class FoxWikiTabButtons {
   public static final Component ADVENTURE_TITLE = Component.translatable("foxablazeaqzl_wiki.menu.adventure_archive");
   private static final ResourceLocation WIKI_TAB = ResourceLocation.fromNamespaceAndPath("foxablazeaqzl_wiki", "textures/gui/wiki_tab.png");
   private static final ResourceLocation WIKI_TAB_ICON = ResourceLocation.fromNamespaceAndPath("foxablazeaqzl_wiki", "textures/gui/wiki_tab_icon.png");
   private static final ResourceLocation TR_BEYOND_TAB = ResourceLocation.fromNamespaceAndPath(
      "beyond_gacha_c", "textures/gui/tr_integration/button_filled.png"
   );
   private static final String TR_BEYOND_SCREEN = "com.trbeyond.gui.tensura.MainScreen";
   private static final int RIGHT_TAB_X = 233;
   private static final int RIGHT_TAB_WIDTH = 21;
   private static final int RIGHT_TAB_HEIGHT = 24;
   private static final int MAIN_TAB_Y = 30;
   private static final int TR_BEYOND_TAB_Y = 56;
   private static final int WIKI_TAB_Y = 82;

   private FoxWikiTabButtons() {
   }

   public static SimpleButton createWikiButton(int guiLeft, int guiTop) {
      return new FoxWikiButton(
         guiLeft + 233, guiTop + 82, 21, 24, WIKI_TAB, null, ADVENTURE_TITLE, self -> ScreenHelper.clicked(() -> ScreenHelper.openScreen(new FoxWikiScreen()))
      );
   }

   public static SimpleButton createAdventureButton(int guiLeft, int guiTop) {
      return new FoxWikiButton(
         guiLeft + 233, guiTop + 82, 21, 24, WIKI_TAB, null, ADVENTURE_TITLE, self -> ScreenHelper.clicked(() -> ScreenHelper.openScreen(new FoxWikiScreen()))
      );
   }

   public static SimpleButton createMainButton(int guiLeft, int guiTop) {
      return new SimpleButton(
         guiLeft + 233,
         guiTop + 30,
         21,
         24,
         ScreenHelper.getTabTexture(4),
         null,
         ScreenHelper.getMenuName(4),
         self -> ScreenHelper.clicked(() -> ScreenHelper.openScreen(4))
      );
   }

   public static SimpleButton createTrBeyondButton(int guiLeft, int guiTop) {
      return new SimpleButton(
         guiLeft + 233,
         guiTop + 56,
         21,
         24,
         TR_BEYOND_TAB,
         null,
         Component.literal("TR Beyond"),
         self -> ScreenHelper.clicked(FoxWikiTabButtons::openTrBeyondScreen)
      );
   }

   public static void disableOriginalThirdRightTab(Screen screen, int guiLeft, int guiTop) {
      disableRightTabAt(screen, guiLeft, guiTop, 82);
   }

   public static void disableTrBeyondTab(Screen screen, int guiLeft, int guiTop) {
      disableRightTabAt(screen, guiLeft, guiTop, 56);
   }

   public static boolean isTrBeyondLoaded() {
      try {
         Class.forName("com.trbeyond.gui.tensura.MainScreen", false, FoxWikiTabButtons.class.getClassLoader());
         return true;
      } catch (ClassNotFoundException | LinkageError ignored) {
         return false;
      }
   }

   private static void disableRightTabAt(Screen screen, int guiLeft, int guiTop, int tabY) {
      for (GuiEventListener child : screen.children()) {
         if (child instanceof SimpleButton button && button.getX() == guiLeft + 233 && Math.abs(button.getY() - (guiTop + tabY)) <= 1) {
            button.setRenderCheck(() -> false);
            button.setClickedCheck(() -> false);
            button.setTooltipCheck(() -> false);
         }
      }
   }

   private static void openTrBeyondScreen() {
      try {
         Class<?> screenClass = Class.forName("com.trbeyond.gui.tensura.MainScreen");
         if (Screen.class.isAssignableFrom(screenClass)) {
            Screen screen = (Screen)screenClass.getDeclaredConstructor().newInstance();
            ScreenHelper.openScreen(screen);
         }
      } catch (ReflectiveOperationException | LinkageError var2) {
      }
   }

   public static void renderTrBeyondTabBase(GuiGraphics graphics, int guiLeft, int guiTop, int mouseX, int mouseY) {
      float v = RenderHelper.mouseOver(mouseX, mouseY, guiLeft + 233, guiLeft + 233 + 21, guiTop + 56, guiTop + 56 + 24) ? 24.0F : 0.0F;
      graphics.blit(ScreenHelper.RIGHT_TAB, guiLeft + 233, guiTop + 56, 0.0F, v, 21, 24, 21, 48);
   }

   public static void renderCurrentWikiTab(GuiGraphics graphics, Screen screen, int guiLeft, int guiTop, int mouseX, int mouseY) {
      graphics.blit(WIKI_TAB_ICON, guiLeft + 233 + 1, guiTop + 82 + 4, 0.0F, 0.0F, 16, 16, 16, 16);
      if (FoxWikiUnreadState.hasUnreadWiki()) {
         FoxWikiMarker.render(graphics, guiLeft + 233 + 21 - 8 - 1, guiTop + 82 + 1, 8);
      }

      if (RenderHelper.mouseOver(mouseX, mouseY, guiLeft + 233, guiLeft + 233 + 21, guiTop + 82, guiTop + 82 + 24)) {
         screen.setTooltipForNextRenderPass(ADVENTURE_TITLE);
      }
   }
}
