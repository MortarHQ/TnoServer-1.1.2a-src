package com.natamus.starterkit_common_fabric.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import com.natamus.collective_common_fabric.implementations.networking.api.Dispatcher;
import com.natamus.starterkit_common_fabric.config.ConfigHandler;
import com.natamus.starterkit_common_fabric.data.ConstantsClient;
import com.natamus.starterkit_common_fabric.data.VariablesClient;
import com.natamus.starterkit_common_fabric.functions.StarterClientFunctions;
import com.natamus.starterkit_common_fabric.networking.packets.ToServerSendKitChoicePacket;
import com.natamus.starterkit_common_fabric.util.Util;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.class_124;
import net.minecraft.class_1304;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_1661;
import net.minecraft.class_1713;
import net.minecraft.class_1735;
import net.minecraft.class_1799;
import net.minecraft.class_2371;
import net.minecraft.class_2561;
import net.minecraft.class_308;
import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_341;
import net.minecraft.class_4185;
import net.minecraft.class_4587;
import net.minecraft.class_5481;
import net.minecraft.class_898;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class StarterKitInventoryScreen extends StarterKitEffectRenderingInventoryScreen<StarterKitInventoryMenu> {
   private float xMouse;
   private float yMouse;
   private boolean widthTooNarrow;
   private boolean buttonClicked;

   public StarterKitInventoryScreen(class_1657 player) {
      super(
         new StarterKitInventoryMenu(player),
         VariablesClient.cachedStarterKitInventory,
         class_2561.method_43470(Util.formatKitName(VariablesClient.cachedStarterKitName)).method_27692(class_124.field_1067)
      );
      this.field_22787 = class_310.method_1551();
      if (player != null) {
         class_1661 kitInventory = VariablesClient.cachedStarterKitInventory;
         if (kitInventory != null) {
            class_2371<class_1799> itemList = kitInventory.field_7547;
            class_2371<class_1799> armourList = kitInventory.field_7548;
            class_2371<class_1799> offhandList = kitInventory.field_7544;
            player.method_31548().field_7545 = 0;
            player.method_5673(class_1304.field_6169, (class_1799)armourList.get(3));
            player.method_5673(class_1304.field_6174, (class_1799)armourList.get(2));
            player.method_5673(class_1304.field_6172, (class_1799)armourList.get(1));
            player.method_5673(class_1304.field_6166, (class_1799)armourList.get(0));
            player.method_5673(class_1304.field_6173, (class_1799)itemList.get(0));
            player.method_5673(class_1304.field_6171, (class_1799)offhandList.get(0));
            ConstantsClient.mc.field_1690.field_1842 = true;
         }
      }
   }

   @Override
   public void containerTick() {
   }

   @Override
   protected void method_25426() {
      super.method_25426();
      this.widthTooNarrow = this.field_22789 < 379;
      this.setupButtons();
   }

   private void setupButtons() {
      class_4185 previousKitButton = class_4185.method_46430(
            class_2561.method_43470(" < "), button -> StarterClientFunctions.cycleChooseKitScreen(this.field_22787.field_1724, false)
         )
         .method_46434(this.field_22789 / 2 + 13, this.field_22790 / 2 - 21, 30, 16)
         .method_46431();
      class_4185 nextKitButton = class_4185.method_46430(
            class_2561.method_43470(" > "), button -> StarterClientFunctions.cycleChooseKitScreen(this.field_22787.field_1724, true)
         )
         .method_46434(this.field_22789 / 2 + 47, this.field_22790 / 2 - 21, 30, 16)
         .method_46431();
      class_4185 chooseKitButton = class_4185.method_46430(class_2561.method_43470("Choose Starter Kit"), button -> {
         StarterClientFunctions.clearPriorEquipmentCache();
         Dispatcher.sendToServer(new ToServerSendKitChoicePacket(VariablesClient.cachedStarterKitName));
         ConstantsClient.mc.method_1507(null);
      }).method_46434(this.field_22789 / 2 - 60, this.field_22790 / 2 + 88, 120, 20).method_46431();
      this.method_37063(previousKitButton);
      this.method_37063(nextKitButton);
      this.method_37063(chooseKitButton);
   }

   @Override
   protected void renderLabels(class_332 guiGraphics, int mouseX, int mouseY) {
      String playerName = "";
      if (this.field_22787.field_1724 != null) {
         playerName = this.field_22787.field_1724.method_5477().getString();
      }

      String headerString = ConfigHandler.chooseKitText.replace("%s", playerName);
      if (playerName.equals("")) {
         headerString = headerString.replace(", p", "P");
      }

      class_2561 headerComponent = class_2561.method_43470(headerString).method_27692(class_124.field_1065).method_27692(class_124.field_1067);
      int headerWidth = this.field_22793.method_27525(headerComponent);
      guiGraphics.method_51439(this.field_22793, headerComponent, -((headerWidth - this.imageWidth) / 2), -16, 0, true);
      String fullKitName = Util.formatKitName(VariablesClient.cachedStarterKitName);
      String kitName = fullKitName;

      while (this.field_22793.method_1727(kitName) > 88) {
         kitName = Util.removeLastChar(kitName);
      }

      if (!fullKitName.equalsIgnoreCase(kitName)) {
         kitName = kitName + "...";
      }

      guiGraphics.method_51433(this.field_22793, kitName, 79, 7, 4210752, false);
      if (VariablesClient.cachedStarterKitDescriptions.containsKey(VariablesClient.cachedStarterKitName.toLowerCase())) {
         class_4587 poseStack = guiGraphics.method_51448();
         poseStack.method_22903();
         String kitDescription = VariablesClient.cachedStarterKitDescriptions.get(VariablesClient.cachedStarterKitName.toLowerCase());
         class_2561 descriptionComponent = class_2561.method_43470(kitDescription);
         poseStack.method_22905(0.66F, 0.66F, 0.66F);
         int y = 28;
         List<class_5481> descriptionLines = class_341.method_1850(descriptionComponent, 136, this.field_22787.field_1772);

         for (class_5481 line : descriptionLines) {
            int x = 120;
            if (y > 28) {
               x -= 2;
            }

            if (y > 70 && descriptionLines.size() > 6) {
               guiGraphics.method_51433(this.field_22793, "...", x + 132, y, 4210752, false);
            }

            guiGraphics.method_51430(this.field_22793, line, x, y, 4210752, false);
            y += 10;
            if (y > 80) {
               break;
            }
         }

         poseStack.method_22909();
      }
   }

   @Override
   public void method_25394(@NotNull class_332 guiGraphics, int mouseX, int mouseY, float partialTick) {
      super.method_25394(guiGraphics, mouseX, mouseY, partialTick);
      this.renderTooltip(guiGraphics, mouseX, mouseY);
      this.xMouse = mouseX;
      this.yMouse = mouseY;
   }

   @Override
   protected void renderBg(class_332 guiGraphics, float partialTick, int mouseX, int mouseY) {
      int i = this.leftPos;
      int j = this.topPos;
      guiGraphics.method_25302(INVENTORY_LOCATION, i, j, 0, 0, this.imageWidth, this.imageHeight);
      renderEntityInInventoryFollowsMouse(guiGraphics, i + 26, j + 8, i + 75, j + 78, 30, 0.0625F, this.xMouse, this.yMouse, this.field_22787.field_1724);
   }

   public static void renderEntityInInventoryFollowsMouse(
      class_332 guiGraphics, int x1, int y1, int x2, int y2, int scale, float yOffset, float mouseX, float mouseY, class_1309 entity
   ) {
      float f = (x1 + x2) / 2.0F;
      float g = (y1 + y2) / 2.0F;
      guiGraphics.method_44379(x1, y1, x2, y2);
      float h = (float)Math.atan((f - mouseX) / 40.0F);
      float i = (float)Math.atan((g - mouseY) / 40.0F);
      Quaternionf quaternionf = new Quaternionf().rotateZ((float) Math.PI);
      Quaternionf quaternionf2 = new Quaternionf().rotateX(i * 20.0F * (float) (Math.PI / 180.0));
      quaternionf.mul(quaternionf2);
      float j = entity.field_6283;
      float k = entity.method_36454();
      float l = entity.method_36455();
      float m = entity.field_6259;
      float n = entity.field_6241;
      entity.field_6283 = 180.0F + h * 20.0F;
      entity.method_36456(180.0F + h * 40.0F);
      entity.method_36457(-i * 20.0F);
      entity.field_6241 = entity.method_36454();
      entity.field_6259 = entity.method_36454();
      Vector3f vector3f = new Vector3f(0.0F, entity.method_17682() / 2.0F + yOffset, 0.0F);
      renderEntityInInventory(guiGraphics, f, g, scale, vector3f, quaternionf, quaternionf2, entity);
      entity.field_6283 = j;
      entity.method_36456(k);
      entity.method_36457(l);
      entity.field_6259 = m;
      entity.field_6241 = n;
      guiGraphics.method_44380();
   }

   public static void renderEntityInInventory(
      class_332 guiGraphics, float x, float y, int scale, Vector3f translate, Quaternionf pose, @Nullable Quaternionf cameraOrientation, class_1309 entity
   ) {
      guiGraphics.method_51448().method_22903();
      guiGraphics.method_51448().method_22904(x, y, 50.0);
      guiGraphics.method_51448().method_22905(scale, scale, -scale);
      guiGraphics.method_51448().method_46416(translate.x, translate.y, translate.z);
      guiGraphics.method_51448().method_22907(pose);
      class_308.method_34742();
      class_898 entityRenderDispatcher = class_310.method_1551().method_1561();
      if (cameraOrientation != null) {
         cameraOrientation.conjugate();
         entityRenderDispatcher.method_24196(cameraOrientation);
      }

      entityRenderDispatcher.method_3948(false);
      RenderSystem.runAsFancy(
         () -> entityRenderDispatcher.method_3954(entity, 0.0, 0.0, 0.0, 0.0F, 1.0F, guiGraphics.method_51448(), guiGraphics.method_51450(), 15728880)
      );
      guiGraphics.method_51452();
      entityRenderDispatcher.method_3948(true);
      guiGraphics.method_51448().method_22909();
      class_308.method_24211();
   }

   @Override
   protected boolean isHovering(int x, int y, int width, int height, double mouseX, double mouseY) {
      return !this.widthTooNarrow && super.isHovering(x, y, width, height, mouseX, mouseY);
   }

   @Override
   public boolean method_25402(double mouseX, double mouseY, int button) {
      return !this.widthTooNarrow && super.method_25402(mouseX, mouseY, button);
   }

   @Override
   public boolean method_25406(double mouseX, double mouseY, int button) {
      if (this.buttonClicked) {
         this.buttonClicked = false;
         return true;
      } else {
         return super.method_25406(mouseX, mouseY, button);
      }
   }

   @Override
   protected void slotClicked(@NotNull class_1735 slot, int slotId, int mouseButton, @NotNull class_1713 type) {
   }

   public void recipesUpdated() {
   }
}
