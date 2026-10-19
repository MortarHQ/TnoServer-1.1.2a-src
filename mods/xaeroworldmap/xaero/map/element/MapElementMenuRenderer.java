package xaero.map.element;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import xaero.lib.client.gui.util.GuiUtils;
import xaero.map.element.render.ElementReader;
import xaero.map.element.render.ElementRenderer;
import xaero.map.gui.GuiMap;

public abstract class MapElementMenuRenderer<E, C> {
   private static final int MENU_RIGHT_PADDING = 27;
   public static final int MAX_MENU_SIZE = 10;
   public static final int MIN_MENU_SIZE = 2;
   protected final MapElementMenuScroll scrollUp;
   protected final MapElementMenuScroll scrollDown;
   protected final MapElementMenuHitbox extraHitbox;
   protected final MenuScrollReader scrollReader;
   protected final MenuHitboxReader hitboxReader;
   protected final C context;
   protected final MapElementRenderProvider<E, C> provider;
   protected ArrayList<E> filteredElements;
   private EditBox filterField;
   public int menuOffset = 0;
   protected Pattern searchPattern = null;
   protected Pattern searchStartPattern = null;
   protected final Minecraft mc;

   protected MapElementMenuRenderer(C context, MapElementRenderProvider<E, C> provider) {
      this.scrollUp = new MapElementMenuScroll("gui.xaero_wm_up", "△", 1);
      this.scrollDown = new MapElementMenuScroll("gui.xaero_wm_down", "▽", -1);
      this.scrollReader = new MenuScrollReader();
      this.extraHitbox = new MapElementMenuHitbox(-150, 0, 177, 0);
      this.hitboxReader = new MenuHitboxReader();
      this.mc = Minecraft.getInstance();
      this.context = context;
      this.provider = provider;
   }

   public void onMapInit(GuiMap screen, Minecraft mc, int width, int height) {
      String searchText = this.filterField == null ? "" : this.filterField.getValue();
      this.filterField = new EditBox(
         mc.font, screen.width - 172, this.menuStartPos(height) + 3 + this.menuSearchPadding(), 150, 20, Component.translatable(this.getFilterPlaceholder())
      );
      this.filterField.setValue(searchText);
      this.filterField.setResponder(s -> this.updateSearch());
      screen.addWidget(this.filterField);
   }

   public HoveredMapElementHolder<?, ?> renderMenu(
      GuiGraphics guiGraphics,
      GuiMap gui,
      double scale,
      int width,
      int height,
      int mouseX,
      int mouseY,
      boolean leftMousePressed,
      boolean leftMouseClicked,
      HoveredMapElementHolder<?, ?> oldHovered,
      Minecraft mc
   ) {
      if (this.filteredElements == null) {
         this.updateFilteredList();
      }

      ArrayList<? extends E> elements = this.filteredElements;
      int menuElementCount = getMenuElementCount(this.menuStartPos(height));
      if (this.menuOffset + menuElementCount > elements.size()) {
         this.menuOffset = elements.size() - menuElementCount;
      }

      if (this.menuOffset < 0) {
         this.menuOffset = 0;
      }

      int offset = this.menuOffset;
      Object viewed = null;
      int menuStartPos = this.menuStartPos(height);
      int yPos = menuStartPos;
      int elementCount = getMenuElementCount(yPos);
      this.beforeMenuRender();
      yPos -= 8;
      viewed = this.renderMenuElement(guiGraphics, this.scrollDown, width, yPos, mouseX, mouseY, viewed, leftMousePressed, gui, offset > 0, mc);
      yPos -= 8;

      for (int i = offset; i < elements.size(); i++) {
         yPos -= 8;
         viewed = this.renderMenuElement(guiGraphics, elements.get(i), width, yPos, mouseX, mouseY, viewed, leftMousePressed, gui, true, mc);
         yPos -= 8;
         if (i - offset == elementCount - 1) {
            break;
         }
      }

      yPos -= 8;
      viewed = this.renderMenuElement(
         guiGraphics, this.scrollUp, width, yPos, mouseX, mouseY, viewed, leftMousePressed, gui, offset < elements.size() - elementCount, mc
      );
      yPos -= 8;
      if (viewed != null && leftMouseClicked) {
         Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
      }

      if (leftMousePressed && viewed instanceof MapElementMenuScroll) {
         int direction = ((MapElementMenuScroll)viewed).scroll();
         this.menuOffset += direction;
      }

      if (viewed == null) {
         this.extraHitbox.setH(menuStartPos - yPos);
         this.extraHitbox.setY(yPos - menuStartPos);
         viewed = this.renderMenuElement(guiGraphics, this.extraHitbox, width, menuStartPos, mouseX, mouseY, viewed, leftMousePressed, gui, true, mc);
      }

      this.afterMenuRender();
      return oldHovered != null && oldHovered.equals(viewed)
         ? oldHovered
         : (viewed == null ? null : MapElementRenderHandler.createResult(viewed, this.getAnyRenderer(viewed)));
   }

   protected abstract void beforeMenuRender();

   protected abstract void afterMenuRender();

   public void postMapRender(GuiGraphics guiGraphics, GuiMap gui, int scaledMouseX, int scaledMouseY, int width, int height, float partialTicks) {
      String searchText = this.filterField.getValue();
      boolean searchFieldPlaceHolder = searchText.isEmpty() && !this.filterField.isFocused();
      boolean invalidRegex = false;
      if (searchFieldPlaceHolder) {
         GuiUtils.setFieldText(this.filterField, I18n.get(this.getFilterPlaceholder(), new Object[0]), -11184811);
      } else if (!searchText.isEmpty() && this.searchPattern == null) {
         invalidRegex = true;
      }

      this.filterField.render(guiGraphics, scaledMouseX, scaledMouseY, partialTicks);
      if (searchFieldPlaceHolder) {
         GuiUtils.setFieldText(this.filterField, "");
      } else if (invalidRegex) {
         String errorMessage = I18n.get("gui.xaero_wm_search_invalid_regex", new Object[0]);
         guiGraphics.drawString(this.mc.font, errorMessage, width - 176 - this.mc.font.width(errorMessage), this.filterField.getY() + 6, -43691);
      }
   }

   public static int getMenuElementCount(int menuStartPos) {
      return Math.min(10, Math.max(2, (menuStartPos - 34) / 16 - 2));
   }

   private <O> Object renderMenuElement(
      GuiGraphics guiGraphics,
      O element,
      int width,
      int yPos,
      int mouseX,
      int mouseY,
      Object viewed,
      boolean leftMousePressed,
      Screen gui,
      boolean enabled,
      Minecraft mc
   ) {
      ElementReader<? super O, ?, ?> reader = element != this.scrollDown && element != this.scrollUp
         ? (element == this.extraHitbox ? this.hitboxReader : this.getAnyRenderer(element).getReader())
         : this.scrollReader;
      int xPos = width - 27;
      boolean hovered = viewed == null && reader.isMouseOverMenuElement(element, xPos, yPos, mouseX, mouseY, mc);
      if (hovered) {
         viewed = element;
      }

      if (element != this.extraHitbox) {
         this.renderMenuElement(reader, element, guiGraphics, gui, xPos, yPos, mouseX, mouseY, 1.0, enabled, hovered, mc, leftMousePressed);
      }

      return viewed;
   }

   public <O> void renderMenuElement(
      ElementReader<O, ?, ?> reader,
      O element,
      GuiGraphics guiGraphics,
      Screen gui,
      int x,
      int y,
      int mouseX,
      int mouseY,
      double scale,
      boolean enabled,
      boolean hovered,
      Minecraft mc,
      boolean pressed
   ) {
      PoseStack matrixStack = guiGraphics.pose();
      matrixStack.pushPose();
      if (hovered) {
         matrixStack.translate(pressed ? 1.0F : 2.0F, 0.0F, 0.0F);
      }

      matrixStack.translate(x, y, 0.0F);
      matrixStack.scale((float)scale, (float)scale, 1.0F);
      matrixStack.translate(-4.0F, -4.0F, 0.0F);
      String name = reader.getMenuName(element);
      int len = mc.font.width(name);
      int textX = -3 - len;
      guiGraphics.fill(textX - 2 - reader.getMenuTextFillLeftPadding(element), -2, textX + len + 2, 11, 1996488704);
      guiGraphics.drawString(mc.font, name, textX, 0, enabled ? -1 : -11184811);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.enableBlend();
      matrixStack.translate(4.0F, 4.0F, 0.0F);
      if (element != this.scrollUp && element != this.scrollDown) {
         E elementCast = (E)element;
         this.renderInMenu(elementCast, guiGraphics, gui, mouseX, mouseY, scale, enabled, hovered, mc, pressed, textX);
      } else {
         this.renderScroll((MapElementMenuScroll)element, guiGraphics, gui, mouseX, mouseY, scale, enabled, hovered, mc, pressed);
      }

      matrixStack.popPose();
   }

   public void onMapMouseRelease(double par1, double par2, int par3) {
      this.releaseScroll();
   }

   private void releaseScroll() {
      this.scrollUp.onMouseRelease();
      this.scrollDown.onMouseRelease();
   }

   private void renderScroll(
      MapElementMenuScroll scroll,
      GuiGraphics guiGraphics,
      Screen gui,
      int mouseX,
      int mouseY,
      double scale,
      boolean enabled,
      boolean hovered,
      Minecraft mc,
      boolean pressed
   ) {
      PoseStack matrixStack = guiGraphics.pose();
      if (enabled && hovered) {
         matrixStack.translate(pressed ? 1.0F : 2.0F, 0.0F, 0.0F);
      }

      matrixStack.translate(-4.0F, -4.0F, 0.0F);
      RenderSystem.enableBlend();
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      int color = enabled ? -1 : -11184811;
      guiGraphics.drawString(mc.font, scroll.getIcon(), 5 - mc.font.width(scroll.getIcon()) / 2, 1, color);
      RenderSystem.enableBlend();
   }

   private void updateSearch() {
      String search = this.filterField.getValue();

      try {
         this.searchPattern = Pattern.compile(search.toLowerCase());
         if (search.length() > 0) {
            if (search.charAt(0) == '^') {
               this.searchStartPattern = this.searchPattern;
            } else {
               this.searchStartPattern = Pattern.compile("^" + search.toString().toLowerCase());
            }
         } else {
            this.searchPattern = this.searchStartPattern = null;
         }
      } catch (PatternSyntaxException e) {
         this.searchPattern = this.searchStartPattern = null;
      }

      this.updateFilteredList();
   }

   public boolean charTyped() {
      return this.filterField.isFocused();
   }

   public boolean keyPressed(GuiMap screen, int keyCode) {
      if (screen.getFocused() == this.filterField) {
         if (keyCode == 257) {
            this.filterField.setValue("");
         }

         return true;
      } else {
         return false;
      }
   }

   public void mouseScrolled(int direction) {
      this.scroll(direction);
   }

   public void tick() {
   }

   public void unfocusAll() {
      if (this.filterField != null) {
         this.filterField.setFocused(false);
      }
   }

   public void onMenuClosed() {
      this.menuOffset = 0;
      this.searchPattern = null;
      this.searchStartPattern = null;
      this.updateFilteredList();
      this.filterField = null;
   }

   private void scroll(int direction) {
      this.menuOffset += direction;
   }

   public Pattern getSearchPattern() {
      return this.searchPattern;
   }

   public Pattern getSearchStartPattern() {
      return this.searchStartPattern;
   }

   public void updateFilteredList() {
      MapElementRenderProvider<E, C> provider = this.provider;
      if (provider == null) {
         this.filteredElements = null;
      } else {
         if (this.filteredElements == null) {
            this.filteredElements = new ArrayList<>();
         } else {
            this.filteredElements.clear();
         }

         Pattern regex = this.searchPattern;
         Pattern regexStartsWith = this.searchStartPattern;
         this.beforeFiltering();
         provider.begin(4, this.context);

         try {
            while (provider.hasNext(4, this.context)) {
               E e = provider.getNext(4, this.context);
               if (regex == null) {
                  this.filteredElements.add(e);
               } else {
                  String filterName = this.getRenderer(e).getReader().getFilterName(e).toLowerCase();
                  if (regexStartsWith.matcher(filterName).find()) {
                     this.filteredElements.add(0, e);
                  } else if (regex.matcher(filterName).find()) {
                     this.filteredElements.add(e);
                  }
               }
            }
         } finally {
            provider.end(4, this.context);
         }
      }
   }

   protected <O> ElementRenderer<? super O, ?, ?> getAnyRenderer(O element) {
      return element != this.scrollDown && element != this.scrollUp && element != this.extraHitbox ? this.getRenderer((E)element) : null;
   }

   protected abstract ElementRenderer<? super E, ?, ?> getRenderer(E var1);

   public abstract int menuStartPos(int var1);

   public abstract int menuSearchPadding();

   public abstract void renderInMenu(
      E var1, GuiGraphics var2, Screen var3, int var4, int var5, double var6, boolean var8, boolean var9, Minecraft var10, boolean var11, int var12
   );

   protected abstract String getFilterPlaceholder();

   protected abstract void beforeFiltering();
}
