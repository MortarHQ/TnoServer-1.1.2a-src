package com.iafenvoy.iceandfire.screen.gui;

import com.iafenvoy.iceandfire.entity.DragonBaseEntity;
import com.iafenvoy.iceandfire.screen.handler.DragonScreenHandler;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.Font.DisplayMode;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class DragonScreen extends AbstractContainerScreen<DragonScreenHandler> {
   private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("iceandfire", "textures/gui/dragon.png");

   public DragonScreen(DragonScreenHandler dragonInv, Inventory playerInv, Component name) {
      super(dragonInv, playerInv, name);
      this.imageHeight = 214;
   }

   protected void renderLabels(GuiGraphics matrixStack, int mouseX, int mouseY) {
   }

   public void render(GuiGraphics matrixStack, int mouseX, int mouseY, float partialTicks) {
      this.renderBackground(matrixStack, mouseX, mouseY, partialTicks);
      super.render(matrixStack, mouseX, mouseY, partialTicks);
      this.renderTooltip(matrixStack, mouseX, mouseY);
   }

   protected void renderBg(GuiGraphics matrixStack, float partialTicks, int mouseX, int mouseY) {
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      int k = (this.width - this.imageWidth) / 2;
      int l = (this.height - this.imageHeight) / 2;
      matrixStack.blit(TEXTURE, k, l, 0, 0, this.imageWidth, this.imageHeight);
      assert Minecraft.getInstance().level != null;
      DragonBaseEntity dragon = ((DragonScreenHandler)this.menu).getDragon();
      float dragonScale = 1.0F / Math.max(1.0E-4F, dragon.getAgeScale());
      Quaternionf quaternionf = new Quaternionf()
         .rotateY((float)Mth.lerp((float)mouseX / this.width, 0.0, Math.PI))
         .rotateZ((float)Mth.lerp((float)mouseY / this.width, Math.PI, 3.3415926535897933));
      InventoryScreen.renderEntityInInventory(
         matrixStack, k + 88, l + (int)(0.5F * dragon.flyProgress) + 55, (int)(dragonScale * 23.0F), new Vector3f(0.0F), quaternionf, null, dragon
      );
      assert this.minecraft != null;
      Font textRenderer = this.minecraft.font;
      String s3 = dragon.getCustomName() == null
         ? I18n.get("dragon.unnamed", new Object[0])
         : I18n.get("dragon.name", new Object[0]) + " " + dragon.getCustomName().getString();
      textRenderer.drawInBatch(
         s3,
         k + this.imageWidth / 2.0F - textRenderer.width(s3) / 2.0F,
         l + 75,
         16777215,
         false,
         matrixStack.pose().last().pose(),
         matrixStack.bufferSource(),
         DisplayMode.NORMAL,
         0,
         15728880
      );
      String s2 = I18n.get("dragon.health", new Object[0])
         + " "
         + Math.floor(Math.min(dragon.getHealth(), dragon.getMaxHealth()))
         + " / "
         + dragon.getMaxHealth();
      textRenderer.drawInBatch(
         s2,
         k + this.imageWidth / 2.0F - textRenderer.width(s2) / 2.0F,
         l + 84,
         16777215,
         false,
         matrixStack.pose().last().pose(),
         matrixStack.bufferSource(),
         DisplayMode.NORMAL,
         0,
         15728880
      );
      String s = dragon.isMale() ? "dragon.gender.male" : "dragon.gender.female";
      String s5 = I18n.get("dragon.gender", new Object[0]) + I18n.get(s, new Object[0]);
      textRenderer.drawInBatch(
         s5,
         k + this.imageWidth / 2.0F - textRenderer.width(s5) / 2.0F,
         l + 93,
         16777215,
         false,
         matrixStack.pose().last().pose(),
         matrixStack.bufferSource(),
         DisplayMode.NORMAL,
         0,
         15728880
      );
      String s6 = I18n.get("dragon.hunger", new Object[0]) + dragon.getHunger() + "/100";
      textRenderer.drawInBatch(
         s6,
         k + this.imageWidth / 2.0F - textRenderer.width(s6) / 2.0F,
         l + 102,
         16777215,
         false,
         matrixStack.pose().last().pose(),
         matrixStack.bufferSource(),
         DisplayMode.NORMAL,
         0,
         15728880
      );
      String s4 = I18n.get("dragon.stage", new Object[0])
         + " "
         + dragon.getDragonStage()
         + " "
         + I18n.get("dragon.days.front", new Object[0])
         + dragon.getAgeInDays()
         + " "
         + I18n.get("dragon.days.back", new Object[0]);
      textRenderer.drawInBatch(
         s4,
         k + this.imageWidth / 2.0F - textRenderer.width(s4) / 2.0F,
         l + 111,
         16777215,
         false,
         matrixStack.pose().last().pose(),
         matrixStack.bufferSource(),
         DisplayMode.NORMAL,
         0,
         15728880
      );
      String s7 = dragon.getOwner() != null
         ? I18n.get("dragon.owner", new Object[0]) + dragon.getOwner().getName().getString()
         : I18n.get("dragon.untamed", new Object[0]);
      textRenderer.drawInBatch(
         s7,
         k + this.imageWidth / 2.0F - textRenderer.width(s7) / 2.0F,
         l + 120,
         16777215,
         false,
         matrixStack.pose().last().pose(),
         matrixStack.bufferSource(),
         DisplayMode.NORMAL,
         0,
         15728880
      );
   }
}
