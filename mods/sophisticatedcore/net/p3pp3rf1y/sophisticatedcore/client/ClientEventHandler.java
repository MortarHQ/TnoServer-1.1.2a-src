package net.p3pp3rf1y.sophisticatedcore.client;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.InputConstants.Key;
import com.mojang.blaze3d.platform.InputConstants.Type;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Collections;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent.Stage;
import net.neoforged.neoforge.client.event.ScreenEvent.KeyPressed.Pre;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.settings.IKeyConflictContext;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.fluids.FluidType;
import net.p3pp3rf1y.sophisticatedcore.api.IStashStorageItem;
import net.p3pp3rf1y.sophisticatedcore.client.gui.StorageScreenBase;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.GuiSoundHelper;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.TranslationHelper;
import net.p3pp3rf1y.sophisticatedcore.client.init.ModParticles;
import net.p3pp3rf1y.sophisticatedcore.client.render.BlockHighlightRenderer;
import net.p3pp3rf1y.sophisticatedcore.common.gui.StorageContainerMenuBase;
import net.p3pp3rf1y.sophisticatedcore.init.ModFluids;
import net.p3pp3rf1y.sophisticatedcore.inventory.ItemStackKey;
import net.p3pp3rf1y.sophisticatedcore.upgrades.jukebox.StorageSoundHandler;

public class ClientEventHandler {
   private static final int MIDDLE_BUTTON = 2;
   private static final String KEYBIND_SOPHISTICATEDCORE_CATEGORY = "key.category.sophisticatedcore.main";
   public static final KeyMapping SORT_KEYBIND = new KeyMapping(
      TranslationHelper.INSTANCE.translKeybind("sort"),
      ClientEventHandler.SophisticatedScreenKeyConflictContext.INSTANCE,
      Type.MOUSE.getOrCreate(2),
      "key.category.sophisticatedcore.main"
   );
   public static final KeyMapping TRANSFER_TO_STORAGE_KEYBIND = new KeyMapping(
      TranslationHelper.INSTANCE.translKeybind("transfer_to_storage"),
      ClientEventHandler.ContainerScreenKeyConflictContext.INSTANCE,
      Type.KEYSYM.getOrCreate(91),
      "key.category.sophisticatedcore.main"
   );
   public static final KeyMapping TRANSFER_TO_INVENTORY_KEYBIND = new KeyMapping(
      TranslationHelper.INSTANCE.translKeybind("transfer_to_inventory"),
      ClientEventHandler.ContainerScreenKeyConflictContext.INSTANCE,
      Type.KEYSYM.getOrCreate(93),
      "key.category.sophisticatedcore.main"
   );

   private ClientEventHandler() {
   }

   public static void registerHandlers(IEventBus modBus) {
      modBus.addListener(ModParticles::registerFactories);
      modBus.addListener(ClientEventHandler::registerFluidClientExtension);
      modBus.addListener(ClientEventHandler::registerKeyMappings);
      IEventBus eventBus = NeoForge.EVENT_BUS;
      eventBus.addListener(StorageSoundHandler::tick);
      eventBus.addListener(StorageSoundHandler::onWorldUnload);
      eventBus.addListener(ClientEventHandler::onDrawScreen);
      eventBus.addListener(ClientEventHandler::handleGuiKeyPress);
      eventBus.addListener(ClientEventHandler::handleGuiMouseKeyPress);
      eventBus.addListener(ClientEventHandler::renderLevelStage);
      eventBus.addListener(ClientEventHandler::onTickEnd);
   }

   private static void onTickEnd(Post event) {
      ItemStackKey.clearCache();
   }

   private static void renderLevelStage(RenderLevelStageEvent event) {
      if (event.getStage() == Stage.AFTER_BLOCK_ENTITIES) {
         float partialTick = event.getPartialTick().getGameTimeDeltaPartialTick(false);
         BlockHighlightRenderer.render(event.getPoseStack(), partialTick, event.getCamera().getPosition());
      }
   }

   private static void registerKeyMappings(RegisterKeyMappingsEvent event) {
      event.register(SORT_KEYBIND);
      event.register(TRANSFER_TO_STORAGE_KEYBIND);
      event.register(TRANSFER_TO_INVENTORY_KEYBIND);
   }

   public static void handleGuiKeyPress(Pre event) {
      Key key = InputConstants.getKey(event.getKeyCode(), event.getScanCode());
      if (SORT_KEYBIND.isActiveAndMatches(key) && tryCallSort(event.getScreen())) {
         GuiSoundHelper.playButtonClickSound();
         event.setCanceled(true);
      } else if (TRANSFER_TO_STORAGE_KEYBIND.isActiveAndMatches(key) && tryCallTransferToStorage(event.getScreen())) {
         GuiSoundHelper.playButtonClickSound();
         event.setCanceled(true);
      } else if (TRANSFER_TO_INVENTORY_KEYBIND.isActiveAndMatches(key) && tryCallTransferToInventory(event.getScreen())) {
         GuiSoundHelper.playButtonClickSound();
         event.setCanceled(true);
      }
   }

   public static void handleGuiMouseKeyPress(net.neoforged.neoforge.client.event.ScreenEvent.MouseButtonPressed.Pre event) {
      Key input = Type.MOUSE.getOrCreate(event.getButton());
      if (SORT_KEYBIND.isActiveAndMatches(input) && tryCallSort(event.getScreen())) {
         GuiSoundHelper.playButtonClickSound();
         event.setCanceled(true);
      } else if (TRANSFER_TO_STORAGE_KEYBIND.isActiveAndMatches(input) && tryCallTransferToStorage(event.getScreen())) {
         GuiSoundHelper.playButtonClickSound();
         event.setCanceled(true);
      } else if (TRANSFER_TO_INVENTORY_KEYBIND.isActiveAndMatches(input) && tryCallTransferToInventory(event.getScreen())) {
         GuiSoundHelper.playButtonClickSound();
         event.setCanceled(true);
      }
   }

   private static boolean tryCallTransferToStorage(Screen gui) {
      if (gui instanceof StorageScreenBase<?> screen) {
         ((StorageContainerMenuBase)screen.getMenu()).transferItemsToStorage(!Screen.hasShiftDown());
         return true;
      } else {
         return false;
      }
   }

   private static boolean tryCallTransferToInventory(Screen gui) {
      if (gui instanceof StorageScreenBase<?> screen) {
         ((StorageContainerMenuBase)screen.getMenu()).transferItemsToPlayerInventory(!Screen.hasShiftDown());
         return true;
      } else {
         return false;
      }
   }

   private static boolean tryCallSort(Screen gui) {
      Minecraft mc = Minecraft.getInstance();
      if (mc.player != null && mc.player.containerMenu instanceof StorageContainerMenuBase<?> container && gui instanceof StorageScreenBase<?> screen) {
         MouseHandler mh = mc.mouseHandler;
         double mouseX = mh.xpos() * mc.getWindow().getGuiScaledWidth() / mc.getWindow().getScreenWidth();
         double mouseY = mh.ypos() * mc.getWindow().getGuiScaledHeight() / mc.getWindow().getScreenHeight();
         Slot selectedSlot = screen.findSlot(mouseX, mouseY);
         if (selectedSlot == null || container.isNotPlayersInventorySlot(selectedSlot.index)) {
            container.sort();
            return true;
         }
      }

      return false;
   }

   private static void onDrawScreen(net.neoforged.neoforge.client.event.ScreenEvent.Render.Post event) {
      Minecraft mc = Minecraft.getInstance();
      Screen gui = mc.screen;
      if (gui instanceof AbstractContainerScreen<?> containerGui && !(gui instanceof CreativeModeInventoryScreen) && mc.player != null) {
         AbstractContainerMenu menu = containerGui.getMenu();
         ItemStack held = menu.getCarried();
         if (!held.isEmpty()) {
            Slot under = containerGui.getSlotUnderMouse();

            for (Slot s : menu instanceof StorageContainerMenuBase<?> storageMenu ? storageMenu.realInventorySlots : menu.slots) {
               ItemStack stack = s.getItem();
               if (s.mayPickup(mc.player) && !stack.isEmpty()) {
                  Optional<ClientEventHandler.StashResultAndTooltip> stashResultAndTooltip = getStashResultAndTooltip(stack, held);
                  if (!stashResultAndTooltip.isEmpty()) {
                     if (s == under) {
                        renderSpecialTooltip(event, mc, event.getGuiGraphics(), stashResultAndTooltip.get());
                     } else {
                        renderStashSign(mc, containerGui, event.getGuiGraphics(), s, stack, stashResultAndTooltip.get().stashResult());
                     }
                  }
               }
            }
         }
      }
   }

   private static void renderStashSign(
      Minecraft mc, AbstractContainerScreen<?> containerGui, GuiGraphics guiGraphics, Slot s, ItemStack stack, IStashStorageItem.StashResult stashResult
   ) {
      int x = containerGui.getGuiLeft() + s.x;
      int y = containerGui.getGuiTop() + s.y;
      PoseStack poseStack = guiGraphics.pose();
      poseStack.pushPose();
      poseStack.translate(0.0F, 0.0F, 300.0F);
      int color = stashResult == IStashStorageItem.StashResult.MATCH_AND_SPACE ? ChatFormatting.GREEN.getColor() : 16776960;
      if (stack.getItem() instanceof IStashStorageItem) {
         guiGraphics.drawString(mc.font, "+", x + 10, y + 8, color);
      } else {
         guiGraphics.drawString(mc.font, "-", x + 1, y, color);
      }

      poseStack.popPose();
   }

   private static void renderSpecialTooltip(
      net.neoforged.neoforge.client.event.ScreenEvent.Render.Post event,
      Minecraft mc,
      GuiGraphics guiGraphics,
      ClientEventHandler.StashResultAndTooltip stashResultAndTooltip
   ) {
      int x = event.getMouseX();
      int y = event.getMouseY();
      PoseStack poseStack = guiGraphics.pose();
      poseStack.pushPose();
      poseStack.translate(0.0F, 0.0F, 100.0F);
      guiGraphics.renderTooltip(
         mc.font,
         Collections.singletonList(Component.translatable(TranslationHelper.INSTANCE.translItemTooltip("storage") + ".right_click_to_add_to_storage")),
         stashResultAndTooltip.tooltip(),
         x,
         y
      );
      poseStack.popPose();
   }

   private static Optional<ClientEventHandler.StashResultAndTooltip> getStashResultAndTooltip(ItemStack inInventory, ItemStack held) {
      if (inInventory.getCount() == 1 && inInventory.getItem() instanceof IStashStorageItem stashStorageItem) {
         return getStashResultAndTooltip(inInventory, held, stashStorageItem);
      } else {
         return held.getItem() instanceof IStashStorageItem stashStorageItem ? getStashResultAndTooltip(held, inInventory, stashStorageItem) : Optional.empty();
      }
   }

   private static Optional<ClientEventHandler.StashResultAndTooltip> getStashResultAndTooltip(
      ItemStack potentialStashStorage, ItemStack potentiallyStashable, IStashStorageItem stashStorageItem
   ) {
      IStashStorageItem.StashResult stashResult = stashStorageItem.getItemStashable(
         Minecraft.getInstance().level.registryAccess(), potentialStashStorage, potentiallyStashable
      );
      return stashResult == IStashStorageItem.StashResult.NO_SPACE
         ? Optional.empty()
         : Optional.of(new ClientEventHandler.StashResultAndTooltip(stashResult, stashStorageItem.getInventoryTooltip(potentialStashStorage)));
   }

   private static void registerFluidClientExtension(RegisterClientExtensionsEvent event) {
      event.registerFluidType(new IClientFluidTypeExtensions() {
         private static final ResourceLocation XP_STILL_TEXTURE = ResourceLocation.fromNamespaceAndPath("sophisticatedcore", "block/xp_still");
         private static final ResourceLocation XP_FLOWING_TEXTURE = ResourceLocation.fromNamespaceAndPath("sophisticatedcore", "block/xp_flowing");

         public ResourceLocation getStillTexture() {
            return XP_STILL_TEXTURE;
         }

         public ResourceLocation getFlowingTexture() {
            return XP_FLOWING_TEXTURE;
         }
      }, new FluidType[]{ModFluids.XP_FLUID_TYPE.get()});
   }

   private static class ContainerScreenKeyConflictContext implements IKeyConflictContext {
      public static final ClientEventHandler.ContainerScreenKeyConflictContext INSTANCE = new ClientEventHandler.ContainerScreenKeyConflictContext();

      public boolean isActive() {
         return KeyConflictContext.GUI.isActive() && Minecraft.getInstance().screen instanceof AbstractContainerScreen;
      }

      public boolean conflicts(IKeyConflictContext other) {
         return this == other;
      }
   }

   private static class SophisticatedScreenKeyConflictContext implements IKeyConflictContext {
      public static final ClientEventHandler.SophisticatedScreenKeyConflictContext INSTANCE = new ClientEventHandler.SophisticatedScreenKeyConflictContext();

      public boolean isActive() {
         return KeyConflictContext.GUI.isActive() && Minecraft.getInstance().screen instanceof StorageScreenBase;
      }

      public boolean conflicts(IKeyConflictContext other) {
         return this == other;
      }
   }

   private record StashResultAndTooltip(IStashStorageItem.StashResult stashResult, Optional<TooltipComponent> tooltip) {
   }
}
