package net.blay09.mods.trashslot.client;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.platform.InputConstants.Type;
import com.mojang.blaze3d.vertex.PoseStack;
import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.event.client.screen.ContainerScreenDrawEvent.Background;
import net.blay09.mods.balm.api.event.client.screen.ScreenInitEvent.Post;
import net.blay09.mods.balm.api.event.client.screen.ScreenMouseEvent.Release.Pre;
import net.blay09.mods.balm.mixin.AbstractContainerScreenAccessor;
import net.blay09.mods.balm.mixin.SlotAccessor;
import net.blay09.mods.trashslot.TrashHelper;
import net.blay09.mods.trashslot.TrashSlot;
import net.blay09.mods.trashslot.TrashSlotSaveState;
import net.blay09.mods.trashslot.api.IGuiContainerLayout;
import net.blay09.mods.trashslot.client.deletion.DeletionProvider;
import net.blay09.mods.trashslot.client.gui.TrashSlotComponent;
import net.blay09.mods.trashslot.config.TrashSlotConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class TrashSlotGuiHandler {
   private static final TrashSlotSlot trashSlot = new TrashSlotSlot();
   private static TrashSlotComponent trashSlotComponent;
   private static ContainerSettings currentContainerSettings = ContainerSettings.NONE;
   private static boolean ignoreMouseUp;
   private static boolean sentMissingMessage;
   private static boolean isLeftMouseDown;
   private static Hint currentHint;

   public static void initialize() {
      Balm.getEvents().onEvent(Post.class, TrashSlotGuiHandler::onScreenInit);
      Balm.getEvents().onEvent(Pre.class, TrashSlotGuiHandler::onMouseRelease);
      Balm.getEvents().onEvent(net.blay09.mods.balm.api.event.client.screen.ScreenMouseEvent.Click.Pre.class, TrashSlotGuiHandler::onMouseClick);
      Balm.getEvents().onEvent(net.blay09.mods.balm.api.event.client.screen.ScreenKeyEvent.Press.Pre.class, TrashSlotGuiHandler::onKeyPress);
      Balm.getEvents().onEvent(Background.class, TrashSlotGuiHandler::onBackgroundDrawn);
      Balm.getEvents().onEvent(Background.class, TrashSlotGuiHandler::onScreenDrawn);
   }

   private static void onScreenInit(Post event) {
      if (!event.getScreen().getClass().getName().startsWith("com.replaymod")) {
         Player player = Minecraft.getInstance().player;
         if (player == null || !player.isSpectator()) {
            if (event.getScreen() instanceof CreativeModeInventoryScreen) {
               currentContainerSettings = ContainerSettings.NONE;
               trashSlotComponent = null;
            } else {
               if (event.getScreen() instanceof AbstractContainerScreen<?> screen) {
                  if (!TrashSlot.isServerSideInstalled && !sentMissingMessage) {
                     TrashSlot.logger.info("TrashSlot is not installed on the server and thus will be unavailable.");
                     MutableComponent noHabloEspanol = Component.translatable("trashslot.serverNotInstalled");
                     noHabloEspanol.withStyle(ChatFormatting.RED);
                     showHint("serverNotInstalled", noHabloEspanol, 5000, true);
                     sentMissingMessage = true;
                     return;
                  }

                  if (screen instanceof InventoryScreen && player != null && player.getAbilities().instabuild) {
                     return;
                  }

                  IGuiContainerLayout layout = LayoutManager.getLayout(screen);
                  currentContainerSettings = TrashSlotSaveState.getSettings(screen, layout);
                  if (currentContainerSettings != ContainerSettings.NONE) {
                     trashSlotComponent = new TrashSlotComponent(screen, layout, currentContainerSettings, trashSlot);
                     if (!currentContainerSettings.isEnabled()
                        && !layout.isEnabledByDefault()
                        && !ModKeyMappings.keyBindToggleSlot.getBinding().key().equals(InputConstants.UNKNOWN)) {
                        MutableComponent hintMessage = Component.translatable(
                           "trashslot.hint.toggleOn", new Object[]{ModKeyMappings.keyBindToggleSlot.getBinding().key().getDisplayName()}
                        );
                        showHint("toggleOn", hintMessage, 5000);
                     }
                  } else {
                     trashSlotComponent = null;
                  }
               } else {
                  currentContainerSettings = ContainerSettings.NONE;
                  trashSlotComponent = null;
               }
            }
         }
      }
   }

   private static void onMouseRelease(Pre event) {
      if (event.getButton() == 0) {
         isLeftMouseDown = false;
      }

      if (ignoreMouseUp) {
         event.setCanceled(true);
         ignoreMouseUp = false;
      }
   }

   private static void onMouseClick(net.blay09.mods.balm.api.event.client.screen.ScreenMouseEvent.Click.Pre event) {
      if (event.getButton() == 0) {
         isLeftMouseDown = true;
      }

      DeletionProvider deletionProvider = TrashSlotConfig.getDeletionProvider();
      if (deletionProvider != null && currentContainerSettings.isEnabled()) {
         int mouseButton = event.getButton();
         if (runKeyBindings(event.getScreen(), Type.MOUSE, mouseButton, 0, 0)) {
            event.setCanceled(true);
         } else {
            if (event.getScreen() instanceof AbstractContainerScreen<?> screen) {
               double mouseX = event.getMouseX();
               double mouseY = event.getMouseY();
               if (((AbstractContainerScreenAccessor)screen).callIsHovering(trashSlot, mouseX, mouseY)) {
                  Player player = Minecraft.getInstance().player;
                  if (player != null) {
                     ItemStack mouseItem = screen.getMenu().getCarried();
                     boolean isRightClick = mouseButton == 1;
                     if (mouseItem.isEmpty()) {
                        deletionProvider.undeleteLast(player, trashSlot, isRightClick);
                     } else if (TrashHelper.canDelete(mouseItem)) {
                        deletionProvider.deleteMouseItem(player, mouseItem, trashSlot, isRightClick);
                     } else {
                        MutableComponent hintMessage = Component.translatable("trashslot.hint.deletionDenied");
                        hintMessage.withStyle(ChatFormatting.RED);
                        showHint("deletionDenied", hintMessage, 1000, true);
                     }

                     event.setCanceled(true);
                     ignoreMouseUp = true;
                  }
               } else if (trashSlotComponent.isInside((int)mouseX, (int)mouseY)) {
                  event.setCanceled(true);
                  ignoreMouseUp = true;
               }
            }
         }
      }
   }

   private static void onKeyPress(net.blay09.mods.balm.api.event.client.screen.ScreenKeyEvent.Press.Pre event) {
      DeletionProvider deletionProvider = TrashSlotConfig.getDeletionProvider();
      if (deletionProvider != null) {
         int keyCode = event.getKey();
         int scanCode = event.getScanCode();
         if (runKeyBindings(event.getScreen(), Type.KEYSYM, keyCode, scanCode, event.getModifiers())) {
            event.setCanceled(true);
         }
      }
   }

   private static boolean runKeyBindings(Screen screen, Type type, int keyCodeOrButton, int scanCode, int modifiers) {
      DeletionProvider deletionProvider = TrashSlotConfig.getDeletionProvider();
      if (deletionProvider == null) {
         return false;
      }

      boolean isDelete = type == Type.KEYSYM
         ? ModKeyMappings.keyBindDelete.isActiveAndMatchesKey(keyCodeOrButton, scanCode, modifiers)
         : ModKeyMappings.keyBindDelete.isActiveAndMatchesMouse(keyCodeOrButton);
      boolean isDeleteAll = type == Type.KEYSYM
         ? ModKeyMappings.keyBindDeleteAll.isActiveAndMatchesKey(keyCodeOrButton, scanCode, modifiers)
         : ModKeyMappings.keyBindDeleteAll.isActiveAndMatchesMouse(keyCodeOrButton);
      LocalPlayer player = Minecraft.getInstance().player;
      if ((isDelete || isDeleteAll)
         && TrashSlotConfig.getActive().enableDeleteKeysInCreative
         && screen instanceof CreativeModeInventoryScreen containerScreen
         && player != null) {
         Slot mouseSlot = ((AbstractContainerScreenAccessor)containerScreen).getHoveredSlot();
         DeletionProvider creativeDeletionProvider = TrashSlotConfig.getCreativeDeletionProvider();
         if (mouseSlot != null && mouseSlot.getClass() == Slot.class) {
            creativeDeletionProvider.deleteContainerItem(player, containerScreen.getMenu(), mouseSlot.index - 9, isDeleteAll, trashSlot);
         } else if (mouseSlot != null && mouseSlot.getClass().getSimpleName().equals("SlotWrapper")) {
            creativeDeletionProvider.deleteContainerItem(player, containerScreen.getMenu(), mouseSlot.getContainerSlot(), isDeleteAll, trashSlot);
         }
      }

      if ((currentContainerSettings.isEnabled() || TrashSlotConfig.getActive().allowDeletionWhileTrashSlotIsInvisible)
         && (isDelete || isDeleteAll)
         && player != null
         && screen instanceof AbstractContainerScreen<?> containerScreen) {
         Slot mouseSlot = ((AbstractContainerScreenAccessor)containerScreen).getHoveredSlot();
         if (mouseSlot == null || !mouseSlot.hasItem()) {
            Window mainWindow = Minecraft.getInstance().getWindow();
            double rawMouseX = Minecraft.getInstance().mouseHandler.xpos();
            double rawMouseY = Minecraft.getInstance().mouseHandler.ypos();
            double mouseX = rawMouseX * mainWindow.getGuiScaledWidth() / mainWindow.getWidth();
            double mouseY = rawMouseY * mainWindow.getGuiScaledHeight() / mainWindow.getHeight();
            if (((AbstractContainerScreenAccessor)containerScreen).callIsHovering(trashSlot, mouseX, mouseY)) {
               deletionProvider.emptyTrashSlot(player, trashSlot);
            }
         } else if (TrashHelper.canDelete(mouseSlot.getItem())) {
            deletionProvider.deleteContainerItem(player, containerScreen.getMenu(), mouseSlot.index, isDeleteAll, trashSlot);
            if (!currentContainerSettings.isEnabled()) {
               MutableComponent hintMessage = Component.translatable("trashslot.hint.deletedWhileHidden");
               hintMessage.withStyle(ChatFormatting.GOLD);
               showHint("deletedWhileHidden", hintMessage, 800, true);
            }
         } else {
            MutableComponent hintMessage = Component.translatable("trashslot.hint.deletionDenied");
            hintMessage.withStyle(ChatFormatting.RED);
            showHint("deletionDenied", hintMessage, 1000, true);
         }

         return true;
      } else {
         if (!(screen instanceof AbstractContainerScreen) || currentContainerSettings == ContainerSettings.NONE) {
            return false;
         }

         if (type == Type.KEYSYM
            ? !ModKeyMappings.keyBindToggleSlot.isActiveAndMatchesKey(keyCodeOrButton, scanCode, modifiers)
            : !ModKeyMappings.keyBindToggleSlot.isActiveAndMatchesMouse(keyCodeOrButton)) {
            if (type == Type.KEYSYM
               ? !ModKeyMappings.keyBindToggleSlotLock.isActiveAndMatchesKey(keyCodeOrButton, scanCode, modifiers)
               : !ModKeyMappings.keyBindToggleSlotLock.isActiveAndMatchesMouse(keyCodeOrButton)) {
               return false;
            }

            currentContainerSettings.setLocked(!currentContainerSettings.isLocked());
            if (currentContainerSettings.isLocked()) {
               MutableComponent hintMessage = Component.translatable(
                  "trashslot.hint.locked", new Object[]{ModKeyMappings.keyBindToggleSlotLock.getBinding().key().getDisplayName()}
               );
               hintMessage.withStyle(ChatFormatting.GOLD);
               showHint("locked", hintMessage, 5000, true);
            } else {
               MutableComponent hintMessage = Component.translatable(
                  "trashslot.hint.unlocked", new Object[]{ModKeyMappings.keyBindToggleSlotLock.getBinding().key().getDisplayName()}
               );
               hintMessage.withStyle(ChatFormatting.GOLD);
               showHint("unlocked", hintMessage, 5000, true);
            }

            TrashSlotSaveState.save();
            return true;
         } else {
            currentContainerSettings.setEnabled(!currentContainerSettings.isEnabled());
            if (!currentContainerSettings.isEnabled() && !ModKeyMappings.keyBindToggleSlot.getBinding().key().equals(InputConstants.UNKNOWN)) {
               MutableComponent hintMessage = Component.translatable(
                  "trashslot.hint.toggledOff", new Object[]{ModKeyMappings.keyBindToggleSlot.getBinding().key().getDisplayName()}
               );
               showHint("toggledOff", hintMessage, 5000);
            }

            TrashSlotSaveState.save();
            return true;
         }
      }
   }

   private static void showHint(String id, MutableComponent message, int timeToDisplay) {
      showHint(id, message, timeToDisplay, false);
   }

   private static void showHint(String id, MutableComponent message, int timeToDisplay, boolean force) {
      TrashSlotSaveState saveState = TrashSlotSaveState.getInstance();
      if (force || !saveState.hasSeenHint(id) && TrashSlotConfig.getActive().enableHints) {
         currentHint = new Hint(id, message, timeToDisplay);
      }
   }

   public static void onBackgroundDrawn(Background event) {
      DeletionProvider deletionProvider = TrashSlotConfig.getDeletionProvider();
      if (deletionProvider != null && currentContainerSettings.isEnabled()) {
         if (event.getScreen() instanceof AbstractContainerScreen<?> screen && trashSlotComponent != null) {
            trashSlotComponent.update(event.getMouseX(), event.getMouseY());
            trashSlotComponent.drawBackground(event.getGuiGraphics());
            if (((AbstractContainerScreenAccessor)screen).callIsHovering(trashSlot, event.getMouseX(), event.getMouseY())) {
               PoseStack poseStack = event.getGuiGraphics().pose();
               poseStack.pushPose();
               poseStack.translate(((AbstractContainerScreenAccessor)screen).getLeftPos(), ((AbstractContainerScreenAccessor)screen).getTopPos(), 0.0F);
               AbstractContainerScreen.renderSlotHighlight(event.getGuiGraphics(), trashSlot.x, trashSlot.y, 0);
               poseStack.popPose();
            }

            boolean isMouseSlot = ((AbstractContainerScreenAccessor)screen).callIsHovering(trashSlot, event.getMouseX(), event.getMouseY());
            if (isMouseSlot) {
               if (screen.getMenu().getCarried().isEmpty() && trashSlot.hasItem()) {
                  event.getGuiGraphics().renderTooltip(Minecraft.getInstance().font, trashSlot.getItem(), event.getMouseX(), event.getMouseY());
               } else if (!trashSlotComponent.isDragging()) {
                  if (TrashSlotConfig.getActive().instantDeletion) {
                     event.getGuiGraphics()
                        .renderTooltip(
                           Minecraft.getInstance().font, Component.translatable("tooltip.trashslot.destroy_item"), event.getMouseX(), event.getMouseY()
                        );
                  } else {
                     event.getGuiGraphics()
                        .renderTooltip(
                           Minecraft.getInstance().font, Component.translatable("tooltip.trashslot.trash_item"), event.getMouseX(), event.getMouseY()
                        );
                  }
               }
            }
         }
      }
   }

   public static void onScreenDrawn(Background event) {
      if (currentHint != null) {
         currentHint.render(event.getScreen(), event.getGuiGraphics());
         if (currentHint.isComplete()) {
            TrashSlotSaveState.getInstance().markHintAsSeen(currentHint.getId());
            TrashSlotSaveState.save();
            currentHint = null;
         }
      }

      DeletionProvider deletionProvider = TrashSlotConfig.getDeletionProvider();
      if (deletionProvider != null && currentContainerSettings.isEnabled()) {
         if (event.getScreen() instanceof AbstractContainerScreen<?> screen && trashSlotComponent != null) {
            TrashSlotSlot trashSlot = TrashSlotGuiHandler.trashSlot;
            SlotAccessor slotAccessor = (SlotAccessor)trashSlot;
            slotAccessor.setX(trashSlot.x + ((AbstractContainerScreenAccessor)screen).getLeftPos());
            slotAccessor.setY(trashSlot.y + ((AbstractContainerScreenAccessor)screen).getTopPos());
            ((AbstractContainerScreenAccessor)screen).callRenderSlot(event.getGuiGraphics(), trashSlot);
            slotAccessor.setX(trashSlot.x - ((AbstractContainerScreenAccessor)screen).getLeftPos());
            slotAccessor.setY(trashSlot.y - ((AbstractContainerScreenAccessor)screen).getTopPos());
            boolean isMouseSlot = ((AbstractContainerScreenAccessor)screen).callIsHovering(trashSlot, event.getMouseX(), event.getMouseY());
            if (isMouseSlot && screen.getMenu().getCarried().isEmpty() && trashSlot.hasItem()) {
               event.getGuiGraphics().renderTooltip(Minecraft.getInstance().font, trashSlot.getItem(), event.getMouseX(), event.getMouseY());
            }
         }
      }
   }

   public static TrashSlotComponent getTrashSlotComponent() {
      return trashSlotComponent;
   }

   public static TrashSlotSlot getTrashSlot() {
      return trashSlot;
   }

   public static boolean isLeftMouseDown() {
      return isLeftMouseDown;
   }
}
