package lain.mods.cos.impl.client.gui;

import lain.mods.cos.impl.ModConfigs;
import lain.mods.cos.impl.ModObjects;
import lain.mods.cos.impl.inventory.ContainerCosArmor;
import lain.mods.cos.impl.inventory.InventoryCosArmor;
import lain.mods.cos.impl.network.payload.PayloadSetSkinArmor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeBookComponent;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.RecipeBookMenu;
import net.minecraft.world.inventory.Slot;
import net.neoforged.neoforge.network.PacketDistributor;

public class GuiCosArmorInventory extends EffectRenderingInventoryScreen<ContainerCosArmor> implements RecipeUpdateListener {
   public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("cosmeticarmorreworked", "textures/gui/cosarmorinventory.png");
   private final RecipeBookComponent recipeBook = new RecipeBookComponent() {
      public boolean isVisible() {
         return super.isVisible() && !(Boolean)ModConfigs.CosArmorDisableRecipeBook.get();
      }

      public void toggleVisibility() {
         this.setVisible(!super.isVisible());
      }
   };
   private final Component craftingText;
   private final Minecraft mc = Minecraft.getInstance();
   public float oldMouseX;
   public float oldMouseY;
   private boolean useMousePos;
   private boolean widthTooNarrow;
   private boolean buttonClicked;

   public GuiCosArmorInventory(ContainerCosArmor container, Inventory invPlayer, Component displayName) {
      super(container, invPlayer, displayName);
      this.titleLabelX = 97;
      this.craftingText = Component.translatable("container.crafting");
      this.smoothTransition();
   }

   public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
      if (this.recipeBook.isVisible() && this.widthTooNarrow) {
         this.renderBackground(graphics, mouseX, mouseY, partialTicks);
         this.recipeBook.render(graphics, mouseX, mouseY, partialTicks);
      } else {
         super.render(graphics, mouseX, mouseY, partialTicks);
         this.recipeBook.render(graphics, mouseX, mouseY, partialTicks);
         this.recipeBook.renderGhostRecipe(graphics, this.leftPos, this.topPos, false, partialTicks);
      }

      this.renderTooltip(graphics, mouseX, mouseY);
      this.recipeBook.renderTooltip(graphics, this.leftPos, this.topPos, mouseX, mouseY);
      this.oldMouseX = mouseX;
      this.oldMouseY = mouseY;
   }

   protected void renderBg(GuiGraphics graphics, float partialTicks, int mouseX, int mouseY) {
      int i = this.leftPos;
      int j = this.topPos;
      graphics.blit(TEXTURE, i, j, 0, 0, this.imageWidth, this.imageHeight);
      if (this.useMousePos) {
         this.oldMouseX = mouseX;
         this.oldMouseY = mouseY;
         this.useMousePos = false;
      }

      InventoryScreen.renderEntityInInventoryFollowsMouse(graphics, i + 26, j + 8, i + 75, j + 78, 30, 0.0625F, this.oldMouseX, this.oldMouseY, this.mc.player);
   }

   protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
      graphics.drawString(this.font, this.craftingText, this.titleLabelX, this.titleLabelY, 4210752, false);
   }

   protected void containerTick() {
      this.recipeBook.tick();
   }

   public boolean mouseClicked(double p_mouseClicked_1_, double p_mouseClicked_3_, int p_mouseClicked_5_) {
      if (this.recipeBook.mouseClicked(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_)) {
         this.setFocused(this.recipeBook);
         return true;
      } else {
         return (!this.widthTooNarrow || !this.recipeBook.isVisible()) && super.mouseClicked(p_mouseClicked_1_, p_mouseClicked_3_, p_mouseClicked_5_);
      }
   }

   public boolean mouseReleased(double p_mouseReleased_1_, double p_mouseReleased_3_, int p_mouseReleased_5_) {
      if (this.buttonClicked) {
         this.buttonClicked = false;
         return true;
      } else {
         return super.mouseReleased(p_mouseReleased_1_, p_mouseReleased_3_, p_mouseReleased_5_);
      }
   }

   protected void init() {
      super.init();
      this.widthTooNarrow = this.width < 379;
      this.recipeBook.init(this.width, this.height, this.mc, this.widthTooNarrow, (RecipeBookMenu)this.menu);
      this.leftPos = this.recipeBook.updateScreenPosition(this.width, this.imageWidth);
      this.addWidget(this.recipeBook);
      this.setInitialFocus(this.recipeBook);
      if (!(Boolean)ModConfigs.CosArmorDisableRecipeBook.get()) {
         this.addRenderableWidget(new ImageButton(this.leftPos + 76, this.topPos + 27, 20, 18, RecipeBookComponent.RECIPE_BUTTON_SPRITES, button -> {
            this.recipeBook.toggleVisibility();
            this.leftPos = this.recipeBook.updateScreenPosition(this.width, this.imageWidth);
            ((ImageButton)button).setPosition(this.leftPos + 76, this.topPos + 27);
            this.buttonClicked = true;
         }));
      }

      InventoryCosArmor invCosArmor = ModObjects.invMan.getCosArmorInventoryClient(this.mc.player.getUUID());

      for (int i = 0; i < 4; i++) {
         int j = 3 - i;
         this.addRenderableWidget(
            new GuiCosArmorToggleButton(this.leftPos + 97 + 18 * i, this.topPos + 61, 5, 5, Component.empty(), invCosArmor.isSkinArmor(j) ? 1 : 0, button -> {
               InventoryCosArmor inv = ModObjects.invMan.getCosArmorInventoryClient(this.mc.player.getUUID());
               inv.setSkinArmor(j, !inv.isSkinArmor(j));
               ((GuiCosArmorToggleButton)button).state = inv.isSkinArmor(j) ? 1 : 0;
               PacketDistributor.sendToServer(new PayloadSetSkinArmor(j, inv.isSkinArmor(j)), new CustomPacketPayload[0]);
            })
         );
      }
   }

   public RecipeBookComponent getRecipeBookComponent() {
      return this.recipeBook;
   }

   protected void slotClicked(Slot slotIn, int slotId, int mouseButton, ClickType type) {
      super.slotClicked(slotIn, slotId, mouseButton, type);
      this.recipeBook.slotClicked(slotIn);
   }

   protected boolean hasClickedOutside(double p_195361_1_, double p_195361_3_, int p_195361_5_, int p_195361_6_, int p_195361_7_) {
      boolean flag = p_195361_1_ < p_195361_5_
         || p_195361_3_ < p_195361_6_
         || p_195361_1_ >= p_195361_5_ + this.imageWidth
         || p_195361_3_ >= p_195361_6_ + this.imageHeight;
      return this.recipeBook.hasClickedOutside(p_195361_1_, p_195361_3_, this.leftPos, this.topPos, this.imageWidth, this.imageHeight, p_195361_7_) && flag;
   }

   protected boolean isHovering(int p_195359_1_, int p_195359_2_, int p_195359_3_, int p_195359_4_, double p_195359_5_, double p_195359_7_) {
      return (!this.widthTooNarrow || !this.recipeBook.isVisible())
         && super.isHovering(p_195359_1_, p_195359_2_, p_195359_3_, p_195359_4_, p_195359_5_, p_195359_7_);
   }

   public void recipesUpdated() {
      this.recipeBook.recipesUpdated();
   }

   private void smoothTransition() {
      if (this.mc.screen instanceof InventoryScreen) {
         this.oldMouseX = InventoryScreenAccess.getXMouse((InventoryScreen)this.mc.screen);
         this.oldMouseY = InventoryScreenAccess.getYMouse((InventoryScreen)this.mc.screen);
      } else if (this.mc.screen instanceof CreativeModeInventoryScreen) {
         this.useMousePos = true;
      }
   }
}
