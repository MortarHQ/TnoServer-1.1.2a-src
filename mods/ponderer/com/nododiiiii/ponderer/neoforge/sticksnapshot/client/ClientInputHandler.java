package com.nododiiiii.ponderer.neoforge.sticksnapshot.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.nododiiiii.ponderer.compat.jei.JeiCompat;
import com.nododiiiii.ponderer.mixin.PonderProgressBarAccessorMixin;
import com.nododiiiii.ponderer.neoforge.sticksnapshot.StickSnapshotFeature;
import com.nododiiiii.ponderer.neoforge.sticksnapshot.network.MirrorClosePacket;
import com.nododiiiii.ponderer.neoforge.sticksnapshot.network.ReplaySnapshotPacket;
import com.nododiiiii.ponderer.neoforge.sticksnapshot.network.SaveSnapshotPacket;
import com.nododiiiii.ponderer.neoforge.sticksnapshot.snapshot.BlockSnapshot;
import com.nododiiiii.ponderer.platform.PondererServices;
import com.nododiiiii.ponderer.ponder.DslScene;
import com.nododiiiii.ponderer.ui.InterfaceSlotEditState;
import com.nododiiiii.ponderer.ui.InterfaceSlotOverlayRenderer;
import com.nododiiiii.ponderer.ui.PonderUiInteractionHelper;
import com.nododiiiii.ponderer.ui.UiAnchorCoords;
import com.nododiiiii.ponderer.ui.UiAnchorViewport;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.createmod.catnip.gui.element.ScreenElement;
import net.createmod.ponder.foundation.ui.PonderProgressBar;
import net.createmod.ponder.foundation.ui.PonderUI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;
import net.neoforged.neoforge.client.event.InputEvent.InteractionKeyMappingTriggered;
import net.neoforged.neoforge.client.event.InputEvent.MouseButton.Pre;
import net.neoforged.neoforge.client.event.ScreenEvent.Closing;
import net.neoforged.neoforge.client.event.ScreenEvent.Opening;
import net.neoforged.neoforge.common.NeoForge;

public class ClientInputHandler {
   private static final int SHOW_INTERFACE_AUTO_REPLAY_DELAY_TICKS = 1;
   private static BlockSnapshot localSnapshot;
   private static long lastReplayMillis = 0L;
   private static boolean awaitingMirrorOpen = false;
   private static boolean mirrorScreenActive = false;
   private static int lastObservedContainerId = -999;
   private static int mirrorAutoCloseTicks = -1;
   private static int autoReplayTicks = -1;
   private static boolean autoReplayArmed = false;
   private static boolean suppressNextAutoReplay = false;
   private static boolean pendingAutoClick = false;
   private static double pendingAutoClickNormX = 0.0;
   private static double pendingAutoClickNormY = 0.0;
   private static int pendingAutoClickButton = 0;
   private static int blockedProtectedSlotReleaseButton = Integer.MIN_VALUE;
   @Nullable
   private static DslScene.InterfaceSlotBinding draggedSlotBinding;
   @Nullable
   private static Integer draggedSlotOriginIndex;
   private static final Map<Integer, ItemStack> protectedMirrorSlotStacks = new HashMap<>();
   @Nullable
   private static AbstractContainerMenu previousPlayerMenu;
   @Nullable
   private static Screen embeddedMirrorScreen;
   private static int embeddedMirrorRenderDepth = 0;

   private ClientInputHandler() {
   }

   public static void register() {
      NeoForge.EVENT_BUS.register(ClientInputHandler.class);
   }

   public static void prepareMirrorReplay(int autoCloseTicks) {
      prepareMirrorReplay(autoCloseTicks, false);
   }

   public static void prepareMirrorReplay(int autoCloseTicks, boolean scheduleAutoReplay) {
      Minecraft mc = Minecraft.getInstance();
      awaitingMirrorOpen = true;
      mirrorScreenActive = false;
      embeddedMirrorScreen = null;
      mirrorAutoCloseTicks = autoCloseTicks > 0 ? autoCloseTicks : -1;
      boolean shouldAutoReplay = scheduleAutoReplay && !suppressNextAutoReplay;
      suppressNextAutoReplay = false;
      autoReplayArmed = shouldAutoReplay;
      autoReplayTicks = shouldAutoReplay ? 1 : -1;
      pendingAutoClick = false;
      blockedProtectedSlotReleaseButton = Integer.MIN_VALUE;
      draggedSlotBinding = null;
      draggedSlotOriginIndex = null;
      protectedMirrorSlotStacks.clear();
      InterfaceSlotEditState.clearJeiViewport();
      if (mc.player != null && mc.player.containerMenu != null) {
         lastObservedContainerId = mc.player.containerMenu.containerId;
      }
   }

   public static void clickEmbeddedMirrorAt(double normalizedX, double normalizedY, int button) {
      pendingAutoClick = true;
      pendingAutoClickNormX = normalizedX;
      pendingAutoClickNormY = normalizedY;
      pendingAutoClickButton = button;
      Minecraft mc = Minecraft.getInstance();
      if (embeddedMirrorScreen != null && mc.screen instanceof PonderUI && (!autoReplayArmed || autoReplayTicks <= 0)) {
         runPendingAutoClick(mc);
      }
   }

   public static void attachMirrorToPonder(Screen mirrorScreen) {
      Minecraft mc = Minecraft.getInstance();
      embeddedMirrorScreen = mirrorScreen;
      awaitingMirrorOpen = false;
      mirrorScreenActive = true;
      mirrorScreen.init(mc, mc.getWindow().getGuiScaledWidth(), mc.getWindow().getGuiScaledHeight());
      stripJeiWidgets(mirrorScreen);
      captureProtectedMirrorSlotStacks(mirrorScreen);
      if (InterfaceSlotEditState.isActive()) {
         InterfaceSlotEditState.captureJeiViewport(mirrorScreen);
      }
   }

   @Nullable
   public static Screen getEmbeddedMirrorScreen() {
      return embeddedMirrorScreen;
   }

   public static boolean hasEmbeddedMirrorScreen() {
      return embeddedMirrorScreen != null;
   }

   public static void beginEmbeddedMirrorRender() {
      embeddedMirrorRenderDepth++;
   }

   public static void endEmbeddedMirrorRender() {
      if (embeddedMirrorRenderDepth > 0) {
         embeddedMirrorRenderDepth--;
      }
   }

   public static boolean isRenderingEmbeddedMirror() {
      return embeddedMirrorRenderDepth > 0;
   }

   public static boolean shouldRenderEmbeddedMirror() {
      return embeddedMirrorScreen == null ? false : !autoReplayArmed || autoReplayTicks <= 0;
   }

   public static boolean isAutoReplayWaitActive() {
      return autoReplayArmed && autoReplayTicks > 0;
   }

   public static void renderDraggedSlotBinding(GuiGraphics graphics, int mouseX, int mouseY) {
      if (draggedSlotBinding != null) {
         ScreenElement element = JeiCompat.resolveIngredientById(draggedSlotBinding.ingredientId, draggedSlotBinding.ingredientKind);
         if (element != null) {
            graphics.flush();
            graphics.pose().pushPose();
            graphics.pose().translate(0.0F, 0.0F, 440.0F);
            RenderSystem.disableDepthTest();
            element.render(graphics, mouseX - 8, mouseY - 8);
            graphics.pose().popPose();
            graphics.flush();
         }
      }
   }

   public static void bindMirrorMenu(AbstractContainerMenu menu) {
      Minecraft mc = Minecraft.getInstance();
      if (mc.player != null) {
         if (previousPlayerMenu == null) {
            previousPlayerMenu = mc.player.containerMenu;
         }

         mc.player.containerMenu = menu;
         lastObservedContainerId = menu.containerId;
      }
   }

   public static void closeEmbeddedMirrorFromPonder(String reason) {
      if (embeddedMirrorScreen != null) {
         closeMirrorSession(reason);
      }
   }

   public static void onMousePre(Pre event) {
      if (event.getButton() == 2 && event.getAction() == 1) {
         Minecraft mc = Minecraft.getInstance();
         if (mc.player != null && mc.level != null) {
            if (mc.player.getMainHandItem().is(Items.STICK)) {
               if (mc.hitResult instanceof BlockHitResult hit) {
                  BlockPos var7 = hit.getBlockPos();
                  BlockState state = mc.level.getBlockState(var7);
                  ResourceLocation blockId = BuiltInRegistries.BLOCK.getKey(state.getBlock());
                  BlockEntity be = mc.level.getBlockEntity(var7);
                  localSnapshot = new BlockSnapshot(
                     Block.getId(state),
                     blockId,
                     be != null ? be.saveWithFullMetadata(mc.level.registryAccess()) : null,
                     mc.level.dimension().location(),
                     var7.immutable(),
                     hit.getDirection(),
                     hit.getLocation(),
                     hit.isInside()
                  );
                  PondererServices.NETWORK.sendToServer(new SaveSnapshotPacket(localSnapshot));
                  event.setCanceled(true);
               }
            }
         }
      }
   }

   public static void onUseKey(InteractionKeyMappingTriggered event) {
      if (event.isUseItem()) {
         Minecraft mc = Minecraft.getInstance();
         if (mc.player != null) {
            if (mc.player.getMainHandItem().is(Items.STICK)) {
               long now = System.currentTimeMillis();
               if (now - lastReplayMillis >= 250L) {
                  lastReplayMillis = now;
                  event.setCanceled(true);
                  prepareMirrorReplay(-1);
                  PondererServices.NETWORK.sendToServer(new ReplaySnapshotPacket());
               }
            }
         }
      }
   }

   @SubscribeEvent
   public static void onClientTick(Post event) {
      if (awaitingMirrorOpen || mirrorScreenActive) {
         Minecraft mc = Minecraft.getInstance();
         if (mc.player != null && mc.player.containerMenu != null) {
            int currentId = mc.player.containerMenu.containerId;
            if (currentId != lastObservedContainerId) {
               lastObservedContainerId = currentId;
            }

            if (mirrorScreenActive && mirrorAutoCloseTicks > 0) {
               mirrorAutoCloseTicks--;
               if (mirrorAutoCloseTicks <= 0) {
                  if (embeddedMirrorScreen != null) {
                     closeMirrorSession("auto-close-embedded");
                  } else if (mc.screen != null) {
                     mc.setScreen(null);
                  }
               }
            }

            if (autoReplayArmed && autoReplayTicks >= 0 && embeddedMirrorScreen != null && mc.screen instanceof PonderUI ponder) {
               if (autoReplayTicks > 0) {
                  autoReplayTicks--;
               }

               if (autoReplayTicks == 0) {
                  runPendingAutoClick(mc);
                  autoReplayArmed = false;
                  autoReplayTicks = -1;
                  suppressNextAutoReplay = true;
                  resetProgressBarBeforeReplay(ponder);
                  callPonderReplay(ponder);
               }
            }

            if (pendingAutoClick && embeddedMirrorScreen != null && mc.screen instanceof PonderUI && (!autoReplayArmed || autoReplayTicks <= 0)) {
               runPendingAutoClick(mc);
            }
         }
      }
   }

   @SubscribeEvent
   public static void onScreenOpening(Opening event) {
      if (awaitingMirrorOpen && event.getNewScreen() != null && embeddedMirrorScreen == null) {
         awaitingMirrorOpen = false;
         mirrorScreenActive = true;
      }
   }

   @SubscribeEvent
   public static void onScreenClosing(Closing event) {
      if (mirrorScreenActive) {
         closeMirrorSession("screen-event-closing");
      }
   }

   @SubscribeEvent
   public static void onPonderMousePressed(net.neoforged.neoforge.client.event.ScreenEvent.MouseButtonPressed.Pre event) {
      Screen mirror = embeddedMirrorScreen;
      if (mirror != null && shouldRenderEmbeddedMirror() && event.getScreen() instanceof PonderUI) {
         if (!PonderUiInteractionHelper.hasPriorityPonderButtonAt(event.getScreen(), event.getMouseX(), event.getMouseY())) {
            if (handleMirrorMousePressed(mirror, event.getMouseX(), event.getMouseY(), event.getButton(), false)) {
               event.setCanceled(true);
            }
         }
      }
   }

   @SubscribeEvent
   public static void onPonderMouseReleased(net.neoforged.neoforge.client.event.ScreenEvent.MouseButtonReleased.Pre event) {
      Screen mirror = embeddedMirrorScreen;
      if (mirror != null && shouldRenderEmbeddedMirror() && event.getScreen() instanceof PonderUI) {
         if (!PonderUiInteractionHelper.hasPriorityPonderButtonAt(event.getScreen(), event.getMouseX(), event.getMouseY())) {
            if (handleMirrorMouseReleased(mirror, event.getMouseX(), event.getMouseY(), event.getButton(), false)) {
               event.setCanceled(true);
            }
         }
      }
   }

   @SubscribeEvent
   public static void onPonderMouseScrolled(net.neoforged.neoforge.client.event.ScreenEvent.MouseScrolled.Pre event) {
      Screen mirror = embeddedMirrorScreen;
      if (mirror != null && shouldRenderEmbeddedMirror() && event.getScreen() instanceof PonderUI) {
         mirror.mouseScrolled(event.getMouseX(), event.getMouseY(), event.getScrollDeltaX(), event.getScrollDeltaY());
         event.setCanceled(true);
      }
   }

   private static void closeMirrorSession(String reason) {
      if (mirrorScreenActive) {
         mirrorScreenActive = false;
         awaitingMirrorOpen = false;
         mirrorAutoCloseTicks = -1;
         autoReplayArmed = false;
         autoReplayTicks = -1;
         pendingAutoClick = false;
         blockedProtectedSlotReleaseButton = Integer.MIN_VALUE;
         draggedSlotBinding = null;
         draggedSlotOriginIndex = null;
         protectedMirrorSlotStacks.clear();
         InterfaceSlotEditState.clearJeiViewport();
         InterfaceSlotOverlayRenderer.clearRuntimeBindings();
         embeddedMirrorScreen = null;
         restorePlayerMenu();
         MirrorNeoForgeOpenClient.restoreInjectedBlock();
         PondererServices.NETWORK.sendToServer(new MirrorClosePacket());
      }
   }

   private static void restorePlayerMenu() {
      Minecraft mc = Minecraft.getInstance();
      if (mc.player == null) {
         previousPlayerMenu = null;
      } else {
         mc.player.containerMenu = (AbstractContainerMenu)(previousPlayerMenu != null ? previousPlayerMenu : mc.player.inventoryMenu);
         lastObservedContainerId = mc.player.containerMenu.containerId;
         previousPlayerMenu = null;
      }
   }

   private static void runPendingAutoClick(Minecraft mc) {
      if (pendingAutoClick && embeddedMirrorScreen != null) {
         UiAnchorViewport.Rect viewport = UiAnchorViewport.resolve(mc);
         double localX = UiAnchorCoords.decodeToPixelX(pendingAutoClickNormX, (int)Math.max(1.0, viewport.width()));
         double localY = UiAnchorCoords.decodeToPixelYTopLeft(pendingAutoClickNormY, (int)Math.max(1.0, viewport.height()));
         double mouseX = viewport.left() + localX;
         double mouseY = viewport.top() + localY;
         handleMirrorMousePressed(embeddedMirrorScreen, mouseX, mouseY, pendingAutoClickButton, true);
         handleMirrorMouseReleased(embeddedMirrorScreen, mouseX, mouseY, pendingAutoClickButton, true);
         pendingAutoClick = false;
      }
   }

   private static boolean handleMirrorMousePressed(Screen mirror, double mouseX, double mouseY, int button, boolean allowProtectedSlotInteraction) {
      Slot slot = InterfaceSlotOverlayRenderer.findSlotAt(mirror, mouseX, mouseY);
      if (InterfaceSlotEditState.isActive() && slot != null) {
         if (button == 1 && InterfaceSlotEditState.removeBinding(slot.index)) {
            return true;
         }

         if (button == 0) {
            DslScene.InterfaceSlotBinding binding = InterfaceSlotEditState.getBinding(slot.index);
            if (binding != null) {
               draggedSlotBinding = binding;
               draggedSlotOriginIndex = binding.slotIndex;
               InterfaceSlotEditState.removeBinding(slot.index);
               return true;
            }
         }

         return true;
      } else if (shouldBlockProtectedSlotInteraction(slot, button, allowProtectedSlotInteraction)) {
         blockedProtectedSlotReleaseButton = button;
         return true;
      } else {
         mirror.mouseClicked(mouseX, mouseY, button);
         return false;
      }
   }

   private static boolean handleMirrorMouseReleased(Screen mirror, double mouseX, double mouseY, int button, boolean allowProtectedSlotInteraction) {
      if (blockedProtectedSlotReleaseButton == button) {
         blockedProtectedSlotReleaseButton = Integer.MIN_VALUE;
         return true;
      }

      Slot slot = InterfaceSlotOverlayRenderer.findSlotAt(mirror, mouseX, mouseY);
      if (draggedSlotBinding != null && button == 0) {
         Slot target = slot;
         if (target != null) {
            InterfaceSlotEditState.putBinding(target.index, target.x, target.y, draggedSlotBinding.ingredientId, draggedSlotBinding.ingredientKind);
         } else if (draggedSlotOriginIndex != null) {
            InterfaceSlotEditState.putBinding(
               draggedSlotOriginIndex, draggedSlotBinding.slotX, draggedSlotBinding.slotY, draggedSlotBinding.ingredientId, draggedSlotBinding.ingredientKind
            );
         }

         draggedSlotBinding = null;
         draggedSlotOriginIndex = null;
         return true;
      } else {
         if (InterfaceSlotEditState.isActive() && slot != null) {
            return button != 0;
         }

         if (shouldBlockProtectedSlotInteraction(slot, button, allowProtectedSlotInteraction)) {
            return true;
         }

         mirror.mouseReleased(mouseX, mouseY, button);
         return false;
      }
   }

   private static boolean shouldBlockProtectedSlotInteraction(@Nullable Slot slot, int button, boolean allowProtectedSlotInteraction) {
      if (allowProtectedSlotInteraction || slot == null) {
         return false;
      } else if (button != 0 && button != 1) {
         return false;
      } else {
         ItemStack original = protectedMirrorSlotStacks.get(slot.index);
         if (original != null && !original.isEmpty()) {
            ItemStack current = slot.getItem();
            return !current.isEmpty() && current.getCount() == original.getCount() && ItemStack.isSameItemSameComponents(current, original);
         } else {
            return false;
         }
      }
   }

   private static void captureProtectedMirrorSlotStacks(Screen mirrorScreen) {
      protectedMirrorSlotStacks.clear();
      Minecraft mc = Minecraft.getInstance();
      if (mirrorScreen instanceof AbstractContainerScreen<?> container && mc.player != null) {
         Inventory playerInventory = mc.player.getInventory();

         for (Slot slot : container.getMenu().slots) {
            if (slot.container != playerInventory) {
               ItemStack stack = slot.getItem();
               if (!stack.isEmpty()) {
                  protectedMirrorSlotStacks.put(slot.index, stack.copy());
               }
            }
         }
      }
   }

   private static void stripJeiWidgets(Screen screen) {
      removeJeiEntries(screen.children());
      removeJeiEntries(readListField(screen, "renderables"));
      removeJeiEntries(readListField(screen, "narratables"));
   }

   private static int removeJeiEntries(@Nullable List<?> list) {
      if (list != null && !list.isEmpty()) {
         int before = list.size();
         list.removeIf(ClientInputHandler::isJeiOwned);
         return before - list.size();
      } else {
         return 0;
      }
   }

   @Nullable
   private static List<?> readListField(Screen screen, String fieldName) {
      try {
         Field field = Screen.class.getDeclaredField(fieldName);
         field.setAccessible(true);
         if (field.get(screen) instanceof List<?> list) {
            return list;
         }
      } catch (Throwable var5) {
      }

      return null;
   }

   private static boolean isJeiOwned(Object obj) {
      return obj != null && obj.getClass().getName().startsWith("mezz.jei.");
   }

   private static void callPonderReplay(PonderUI ponder) {
      try {
         Method replay = PonderUI.class.getDeclaredMethod("replay");
         replay.setAccessible(true);
         replay.invoke(ponder);
      } catch (Throwable t) {
         StickSnapshotFeature.LOGGER.warn("show_interface delayed replay invoke failed", t);
      }
   }

   private static void resetProgressBarBeforeReplay(PonderUI ponder) {
      Screen screen = (Screen)ponder;

      for (GuiEventListener child : screen.children()) {
         if (child instanceof PonderProgressBar bar) {
            LerpedFloatReset((PonderProgressBarAccessorMixin)bar);
         }
      }
   }

   private static void LerpedFloatReset(PonderProgressBarAccessorMixin accessor) {
      accessor.ponderer$getProgress().startWithValue(0.0);
   }
}
