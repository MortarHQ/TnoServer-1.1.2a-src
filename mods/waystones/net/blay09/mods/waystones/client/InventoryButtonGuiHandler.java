package net.blay09.mods.waystones.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import net.blay09.mods.balm.api.Balm;
import net.blay09.mods.balm.api.client.BalmClient;
import net.blay09.mods.balm.api.event.client.screen.ScreenInitEvent.Post;
import net.blay09.mods.waystones.api.TeleportFlags;
import net.blay09.mods.waystones.api.Waystone;
import net.blay09.mods.waystones.api.WaystoneCooldowns;
import net.blay09.mods.waystones.api.WaystoneTeleportContext;
import net.blay09.mods.waystones.api.WaystonesAPI;
import net.blay09.mods.waystones.api.requirement.WarpRequirement;
import net.blay09.mods.waystones.client.gui.screen.InventoryButtonReturnConfirmScreen;
import net.blay09.mods.waystones.client.gui.widget.WaystoneInventoryButton;
import net.blay09.mods.waystones.config.InventoryButtonMode;
import net.blay09.mods.waystones.config.WaystonesConfig;
import net.blay09.mods.waystones.core.InvalidWaystone;
import net.blay09.mods.waystones.core.PlayerWaystoneManager;
import net.blay09.mods.waystones.network.message.InventoryButtonMessage;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;

public class InventoryButtonGuiHandler {
   private static WaystoneInventoryButton warpButton;

   public static void initialize() {
      Balm.getEvents()
         .onEvent(
            Post.class,
            event -> {
               Screen screen = event.getScreen();
               if (screen instanceof InventoryScreen || screen instanceof CreativeModeInventoryScreen) {
                  Minecraft mc = Minecraft.getInstance();
                  if (screen == mc.screen) {
                     InventoryButtonMode inventoryButtonMode = WaystonesConfig.getActive().getInventoryButtonMode();
                     if (inventoryButtonMode.isEnabled()) {
                        Supplier<Integer> xPosition = screen instanceof CreativeModeInventoryScreen
                           ? () -> WaystonesConfig.getActive().inventoryButton.creativeInventoryButtonX
                           : () -> WaystonesConfig.getActive().inventoryButton.inventoryButtonX;
                        Supplier<Integer> yPosition = screen instanceof CreativeModeInventoryScreen
                           ? () -> WaystonesConfig.getActive().inventoryButton.creativeInventoryButtonY
                           : () -> WaystonesConfig.getActive().inventoryButton.inventoryButtonY;
                        warpButton = new WaystoneInventoryButton(
                           (AbstractContainerScreen<?>)screen,
                           button -> {
                              Player player = mc.player;
                              if (player.getAbilities().instabuild) {
                                 PlayerWaystoneManager.resetCooldowns(player);
                              }

                              WarpRequirement requirements = WaystonesAPI.resolveRequirements(
                                 WaystonesAPI.createUnboundTeleportContext(player).addFlag(TeleportFlags.INVENTORY_BUTTON)
                              );
                              if (requirements.canAfford(player)) {
                                 if (inventoryButtonMode.hasNamedTarget()) {
                                    mc.setScreen(new InventoryButtonReturnConfirmScreen(inventoryButtonMode.getNamedTarget()));
                                 } else if (inventoryButtonMode.isReturnToNearest()) {
                                    if (PlayerWaystoneManager.getNearestWaystone(player).isPresent()) {
                                       mc.setScreen(new InventoryButtonReturnConfirmScreen());
                                    }
                                 } else if (inventoryButtonMode.isReturnToAny()) {
                                    Balm.getNetworking().sendToServer(new InventoryButtonMessage());
                                 }
                              } else {
                                 mc.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 0.5F));
                              }
                           },
                           () -> screen instanceof CreativeModeInventoryScreen creativeModeInventoryScreen
                              ? creativeModeInventoryScreen.isInventoryOpen()
                              : true,
                           xPosition,
                           yPosition
                        );
                        BalmClient.getScreens().addRenderableWidget(screen, warpButton);
                     }
                  }
               }
            }
         );
      Balm.getEvents()
         .onEvent(
            net.blay09.mods.balm.api.event.client.screen.ScreenDrawEvent.Post.class,
            event -> {
               Screen screen = event.getScreen();
               GuiGraphics guiGraphics = event.getGuiGraphics();
               int mouseX = event.getMouseX();
               int mouseY = event.getMouseY();
               if ((screen instanceof InventoryScreen || screen instanceof CreativeModeInventoryScreen)
                  && warpButton != null
                  && warpButton.isHoveredOrFocused()) {
                  InventoryButtonMode inventoryButtonMode = WaystonesConfig.getActive().getInventoryButtonMode();
                  List<Component> tooltip = new ArrayList<>();
                  Player player = Minecraft.getInstance().player;
                  if (player == null) {
                     return;
                  }

                  long millisLeft = PlayerWaystoneManager.getCooldownMillisLeft(player, WaystoneCooldowns.INVENTORY_BUTTON);
                  Waystone waystone = PlayerWaystoneManager.getInventoryButtonTarget(player).orElse(InvalidWaystone.INSTANCE);
                  WaystoneTeleportContext context = WaystonesAPI.createUnboundTeleportContext(player, waystone).addFlag(TeleportFlags.INVENTORY_BUTTON);
                  WarpRequirement requirements = WaystonesAPI.resolveRequirements(context);
                  if (inventoryButtonMode.hasNamedTarget()) {
                     tooltip.add(Component.translatable("gui.waystones.inventory.return_to_waystone").withStyle(ChatFormatting.YELLOW));
                     MutableComponent targetComponent = Component.literal(inventoryButtonMode.getNamedTarget()).withStyle(ChatFormatting.DARK_AQUA);
                     tooltip.add(Component.translatable("tooltip.waystones.bound_to", new Object[]{targetComponent}).withStyle(ChatFormatting.GRAY));
                  } else if (inventoryButtonMode.isReturnToNearest()) {
                     tooltip.add(Component.translatable("gui.waystones.inventory.return_to_nearest_waystone").withStyle(ChatFormatting.YELLOW));
                     Optional<Waystone> nearestWaystone = PlayerWaystoneManager.getNearestWaystone(player);
                     tooltip.add(
                        (Component)nearestWaystone.<MutableComponent>map(it -> it.getName().copy().withStyle(ChatFormatting.DARK_AQUA))
                           .map(it -> Component.translatable("tooltip.waystones.bound_to", new Object[]{it}).withStyle(ChatFormatting.GRAY))
                           .orElseGet(() -> Component.translatable("gui.waystones.inventory.no_waystones_activated").withStyle(ChatFormatting.RED))
                     );
                  } else if (inventoryButtonMode.isReturnToAny()) {
                     tooltip.add(Component.translatable("gui.waystones.inventory.return_to_waystone").withStyle(ChatFormatting.YELLOW));
                     if (PlayerWaystoneManager.getActivatedWaystones(player).isEmpty()) {
                        tooltip.add(Component.translatable("gui.waystones.inventory.no_waystones_activated").withStyle(ChatFormatting.RED));
                     }
                  }

                  if (!requirements.canAfford(player)) {
                     requirements.appendHoverText(player, tooltip);
                  }

                  guiGraphics.renderTooltip(Minecraft.getInstance().font, tooltip, Optional.empty(), mouseX, mouseY);
               }
            }
         );
   }
}
