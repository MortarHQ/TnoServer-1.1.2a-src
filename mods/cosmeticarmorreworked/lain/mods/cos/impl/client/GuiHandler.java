package lain.mods.cos.impl.client;

import com.google.common.collect.ImmutableSet;
import java.util.Set;
import lain.mods.cos.impl.ModConfigs;
import lain.mods.cos.impl.ModObjects;
import lain.mods.cos.impl.client.gui.GuiCosArmorButton;
import lain.mods.cos.impl.client.gui.GuiCosArmorInventory;
import lain.mods.cos.impl.client.gui.GuiCosArmorToggleButton;
import lain.mods.cos.impl.client.gui.ICreativeInvWidget;
import lain.mods.cos.impl.client.gui.IShiftingWidget;
import lain.mods.cos.impl.client.gui.InventoryScreenAccess;
import lain.mods.cos.impl.network.payload.PayloadOpenCosArmorInventory;
import lain.mods.cos.impl.network.payload.PayloadOpenNormalInventory;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.ScreenEvent.Init.Post;
import net.neoforged.neoforge.client.event.ScreenEvent.Render.Pre;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.PacketDistributor;

public enum GuiHandler {
   INSTANCE;

   public static final Set<Integer> ButtonIds = ImmutableSet.of(76, 77);
   private int lastLeft;
   private boolean lastInventoryOpen;

   private void handleGuiDrawPre(Pre event) {
      if (event.getScreen() instanceof AbstractContainerScreen) {
         AbstractContainerScreen<?> screen = (AbstractContainerScreen<?>)event.getScreen();
         if (this.lastLeft != screen.getGuiLeft()) {
            int diffLeft = screen.getGuiLeft() - this.lastLeft;
            this.lastLeft = screen.getGuiLeft();
            screen.children().stream().filter(IShiftingWidget.class::isInstance).map(IShiftingWidget.class::cast).forEach(b -> b.shiftLeft(diffLeft));
         }

         if (event.getScreen() instanceof CreativeModeInventoryScreen) {
            boolean isInventoryOpen = ((CreativeModeInventoryScreen)event.getScreen()).isInventoryOpen();
            if (this.lastInventoryOpen != isInventoryOpen) {
               this.lastInventoryOpen = isInventoryOpen;
               screen.children()
                  .stream()
                  .filter(ICreativeInvWidget.class::isInstance)
                  .map(ICreativeInvWidget.class::cast)
                  .forEach(b -> b.onSelectedTabChanged(isInventoryOpen));
            }
         }
      }
   }

   private void handleGuiInitPost(Post event) {
      if (event.getScreen() instanceof AbstractContainerScreen) {
         AbstractContainerScreen<?> screen = (AbstractContainerScreen<?>)event.getScreen();
         this.lastLeft = screen instanceof CreativeModeInventoryScreen ? 0 : screen.getGuiLeft();
         this.lastInventoryOpen = true;
      }

      if (event.getScreen() instanceof InventoryScreen || event.getScreen() instanceof GuiCosArmorInventory) {
         AbstractContainerScreen<?> screen = (AbstractContainerScreen<?>)event.getScreen();
         if (!(Boolean)ModConfigs.CosArmorGuiButton_Hidden.get()) {
            event.addListener(
               new GuiCosArmorButton(
                  screen.getGuiLeft() + (Integer)ModConfigs.CosArmorGuiButton_Left.get(),
                  screen.getGuiTop() + (Integer)ModConfigs.CosArmorGuiButton_Top.get(),
                  10,
                  10,
                  event.getScreen() instanceof GuiCosArmorInventory
                     ? Component.translatable("cos.gui.buttonnormal")
                     : Component.translatable("cos.gui.buttoncos"),
                  button -> {
                     if (screen instanceof GuiCosArmorInventory) {
                        InventoryScreen newGui = new InventoryScreen(screen.getMinecraft().player);
                        InventoryScreenAccess.setXMouse(newGui, ((GuiCosArmorInventory)screen).oldMouseX);
                        InventoryScreenAccess.setYMouse(newGui, ((GuiCosArmorInventory)screen).oldMouseY);
                        screen.getMinecraft().setScreen(newGui);
                        PacketDistributor.sendToServer(new PayloadOpenNormalInventory(), new CustomPacketPayload[0]);
                     } else {
                        PacketDistributor.sendToServer(new PayloadOpenCosArmorInventory(), new CustomPacketPayload[0]);
                     }
                  },
                  null
               )
            );
         }

         if (!(Boolean)ModConfigs.CosArmorToggleButton_Hidden.get()) {
            event.addListener(
               new GuiCosArmorToggleButton(
                  screen.getGuiLeft() + (Integer)ModConfigs.CosArmorToggleButton_Left.get(),
                  screen.getGuiTop() + (Integer)ModConfigs.CosArmorToggleButton_Top.get(),
                  5,
                  5,
                  Component.empty(),
                  PlayerRenderHandler.Disabled ? 1 : 0,
                  button -> {
                     PlayerRenderHandler.Disabled = !PlayerRenderHandler.Disabled;
                     ((GuiCosArmorToggleButton)button).state = PlayerRenderHandler.Disabled ? 1 : 0;
                  }
               )
            );
         }
      } else if (event.getScreen() instanceof CreativeModeInventoryScreen) {
         AbstractContainerScreen<?> screen = (AbstractContainerScreen<?>)event.getScreen();
         if (!(Boolean)ModConfigs.CosArmorCreativeGuiButton_Hidden.get()) {
            event.addListener(
               new GuiCosArmorButton(
                  (Integer)ModConfigs.CosArmorCreativeGuiButton_Left.get(),
                  screen.getGuiTop() + (Integer)ModConfigs.CosArmorCreativeGuiButton_Top.get(),
                  10,
                  10,
                  Component.translatable("cos.gui.buttoncos"),
                  button -> PacketDistributor.sendToServer(new PayloadOpenCosArmorInventory(), new CustomPacketPayload[0]),
                  (button, isInventoryOpen) -> button.visible = isInventoryOpen
               )
            );
         }
      }
   }

   public void registerEvents() {
      NeoForge.EVENT_BUS.addListener(this::handleGuiDrawPre);
      NeoForge.EVENT_BUS.addListener(this::handleGuiInitPost);
   }

   public void registerMenuScreens(RegisterMenuScreensEvent event) {
      event.register(ModObjects.getTypeContainerCosArmor(), GuiCosArmorInventory::new);
   }
}
