package net.p3pp3rf1y.sophisticatedbackpacks.client;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.InputConstants.Key;
import com.mojang.blaze3d.platform.InputConstants.Type;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;
import net.neoforged.neoforge.client.event.ScreenEvent.KeyPressed.Pre;
import net.neoforged.neoforge.client.settings.IKeyConflictContext;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.client.settings.KeyModifier;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.PacketDistributor;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackItem;
import net.p3pp3rf1y.sophisticatedbackpacks.client.gui.BackpackScreen;
import net.p3pp3rf1y.sophisticatedbackpacks.client.gui.SBPTranslationHelper;
import net.p3pp3rf1y.sophisticatedbackpacks.network.BackpackOpenPayload;
import net.p3pp3rf1y.sophisticatedbackpacks.network.BlockToolSwapPayload;
import net.p3pp3rf1y.sophisticatedbackpacks.network.EntityToolSwapPayload;
import net.p3pp3rf1y.sophisticatedbackpacks.network.InventoryInteractionPayload;
import net.p3pp3rf1y.sophisticatedbackpacks.network.UpgradeTogglePayload;
import net.p3pp3rf1y.sophisticatedcore.util.CapabilityHelper;

public class KeybindHandler {
   private static final int KEY_B = 66;
   private static final int KEY_C = 67;
   private static final int KEY_Z = 90;
   private static final int KEY_X = 88;
   private static final int KEY_UNKNOWN = -1;
   private static final int CHEST_SLOT_INDEX = 38;
   private static final int OFFHAND_SLOT_INDEX = 40;
   private static final String KEYBIND_SOPHISTICATEDBACKPACKS_CATEGORY = "key.category.sophisticatedbackpacks.main";
   public static final KeyMapping BACKPACK_TOGGLE_UPGRADE_5 = new KeyMapping(
      SBPTranslationHelper.INSTANCE.translKeybind("toggle_upgrade_5"),
      KeyConflictContext.UNIVERSAL,
      Type.KEYSYM.getOrCreate(-1),
      "key.category.sophisticatedbackpacks.main"
   );
   public static final KeyMapping BACKPACK_TOGGLE_UPGRADE_4 = new KeyMapping(
      SBPTranslationHelper.INSTANCE.translKeybind("toggle_upgrade_4"),
      KeyConflictContext.UNIVERSAL,
      Type.KEYSYM.getOrCreate(-1),
      "key.category.sophisticatedbackpacks.main"
   );
   public static final KeyMapping BACKPACK_TOGGLE_UPGRADE_3 = new KeyMapping(
      SBPTranslationHelper.INSTANCE.translKeybind("toggle_upgrade_3"),
      KeyConflictContext.UNIVERSAL,
      Type.KEYSYM.getOrCreate(-1),
      "key.category.sophisticatedbackpacks.main"
   );
   public static final KeyMapping BACKPACK_TOGGLE_UPGRADE_2 = new KeyMapping(
      SBPTranslationHelper.INSTANCE.translKeybind("toggle_upgrade_2"),
      KeyConflictContext.UNIVERSAL,
      KeyModifier.ALT,
      Type.KEYSYM.getOrCreate(88),
      "key.category.sophisticatedbackpacks.main"
   );
   public static final KeyMapping BACKPACK_TOGGLE_UPGRADE_1 = new KeyMapping(
      SBPTranslationHelper.INSTANCE.translKeybind("toggle_upgrade_1"),
      KeyConflictContext.UNIVERSAL,
      KeyModifier.ALT,
      Type.KEYSYM.getOrCreate(90),
      "key.category.sophisticatedbackpacks.main"
   );
   public static final Map<Integer, KeyMapping> UPGRADE_SLOT_TOGGLE_KEYBINDS = Map.of(
      0, BACKPACK_TOGGLE_UPGRADE_1, 1, BACKPACK_TOGGLE_UPGRADE_2, 2, BACKPACK_TOGGLE_UPGRADE_3, 3, BACKPACK_TOGGLE_UPGRADE_4, 4, BACKPACK_TOGGLE_UPGRADE_5
   );
   public static final KeyMapping TOOL_SWAP_KEYBIND = new KeyMapping(
      SBPTranslationHelper.INSTANCE.translKeybind("tool_swap"),
      KeyConflictContext.IN_GAME,
      Type.KEYSYM.getOrCreate(-1),
      "key.category.sophisticatedbackpacks.main"
   );
   public static final KeyMapping INVENTORY_INTERACTION_KEYBIND = new KeyMapping(
      SBPTranslationHelper.INSTANCE.translKeybind("inventory_interaction"),
      KeyConflictContext.IN_GAME,
      Type.KEYSYM.getOrCreate(67),
      "key.category.sophisticatedbackpacks.main"
   );
   public static final KeyMapping BACKPACK_OPEN_KEYBIND = new KeyMapping(
      SBPTranslationHelper.INSTANCE.translKeybind("open_backpack"),
      KeybindHandler.BackpackKeyConflictContext.INSTANCE,
      Type.KEYSYM.getOrCreate(66),
      "key.category.sophisticatedbackpacks.main"
   );

   private KeybindHandler() {
   }

   public static void register() {
      IEventBus eventBus = NeoForge.EVENT_BUS;
      eventBus.addListener(EventPriority.HIGH, KeybindHandler::handleKeyInputEvent);
      eventBus.addListener(EventPriority.HIGH, KeybindHandler::handleGuiMouseKeyPress);
      eventBus.addListener(EventPriority.HIGH, KeybindHandler::handleGuiKeyPress);
   }

   public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
      event.register(BACKPACK_OPEN_KEYBIND);
      event.register(INVENTORY_INTERACTION_KEYBIND);
      event.register(TOOL_SWAP_KEYBIND);
      UPGRADE_SLOT_TOGGLE_KEYBINDS.forEach((slot, keybind) -> event.register(keybind));
   }

   public static void handleGuiKeyPress(Pre event) {
      Key key = InputConstants.getKey(event.getKeyCode(), event.getScanCode());
      if (BACKPACK_OPEN_KEYBIND.isActiveAndMatches(key) && sendBackpackOpenOrCloseMessage()) {
         event.setCanceled(true);
      }
   }

   public static void handleGuiMouseKeyPress(net.neoforged.neoforge.client.event.ScreenEvent.MouseButtonPressed.Pre event) {
      Key input = Type.MOUSE.getOrCreate(event.getButton());
      if (BACKPACK_OPEN_KEYBIND.isActiveAndMatches(input) && sendBackpackOpenOrCloseMessage()) {
         event.setCanceled(true);
      }
   }

   public static void handleKeyInputEvent(Post event) {
      if (BACKPACK_OPEN_KEYBIND.consumeClick()) {
         sendBackpackOpenOrCloseMessage();
      } else if (INVENTORY_INTERACTION_KEYBIND.consumeClick()) {
         sendInteractWithInventoryMessage();
      } else if (TOOL_SWAP_KEYBIND.consumeClick()) {
         sendToolSwapMessage();
      } else {
         for (Entry<Integer, KeyMapping> slotKeybind : UPGRADE_SLOT_TOGGLE_KEYBINDS.entrySet()) {
            if (slotKeybind.getValue().consumeClick()) {
               PacketDistributor.sendToServer(new UpgradeTogglePayload(slotKeybind.getKey()), new CustomPacketPayload[0]);
            }
         }
      }
   }

   private static void sendToolSwapMessage() {
      Minecraft mc = Minecraft.getInstance();
      LocalPlayer player = mc.player;
      if (player != null && mc.hitResult != null) {
         if (player.getMainHandItem().getItem() instanceof BackpackItem) {
            player.displayClientMessage(Component.translatable("gui.sophisticatedbackpacks.status.unable_to_swap_tool_for_backpack"), true);
         } else {
            HitResult rayTrace = mc.hitResult;
            if (rayTrace.getType() == net.minecraft.world.phys.HitResult.Type.BLOCK) {
               BlockHitResult blockRayTraceResult = (BlockHitResult)rayTrace;
               BlockPos pos = blockRayTraceResult.getBlockPos();
               PacketDistributor.sendToServer(new BlockToolSwapPayload(pos), new CustomPacketPayload[0]);
            } else if (rayTrace.getType() == net.minecraft.world.phys.HitResult.Type.ENTITY) {
               EntityHitResult entityRayTraceResult = (EntityHitResult)rayTrace;
               PacketDistributor.sendToServer(new EntityToolSwapPayload(entityRayTraceResult.getEntity().getId()), new CustomPacketPayload[0]);
            }
         }
      }
   }

   private static void sendInteractWithInventoryMessage() {
      Minecraft mc = Minecraft.getInstance();
      HitResult rayTrace = mc.hitResult;
      if (rayTrace != null && rayTrace.getType() == net.minecraft.world.phys.HitResult.Type.BLOCK) {
         BlockHitResult blockraytraceresult = (BlockHitResult)rayTrace;
         BlockPos pos = blockraytraceresult.getBlockPos();
         if (!Boolean.FALSE.equals(CapabilityHelper.getFromItemHandler(mc.level, pos, itemHandler -> true, false))) {
            PacketDistributor.sendToServer(new InventoryInteractionPayload(pos, blockraytraceresult.getDirection()), new CustomPacketPayload[0]);
         }
      }
   }

   private static boolean sendBackpackOpenOrCloseMessage() {
      if (!KeyConflictContext.GUI.isActive()) {
         PacketDistributor.sendToServer(new BackpackOpenPayload(), new CustomPacketPayload[0]);
         return false;
      }

      Screen screen = Minecraft.getInstance().screen;
      if (screen instanceof AbstractContainerScreen<?> containerScreen) {
         Slot slot = containerScreen.getSlotUnderMouse();
         if (slot != null && slot.container instanceof Inventory) {
            Optional<String> handlerName = getPlayerInventoryHandlerName(slot.getSlotIndex());
            if (handlerName.isPresent() && slot.getItem().getItem() instanceof BackpackItem) {
               PacketDistributor.sendToServer(new BackpackOpenPayload(slot.getSlotIndex(), "", handlerName.get()), new CustomPacketPayload[0]);
               return true;
            }
         }

         if (screen instanceof BackpackScreen && slot != null && slot.getItem().getItem() instanceof BackpackItem && slot.getItem().getCount() == 1) {
            PacketDistributor.sendToServer(new BackpackOpenPayload(slot.index), new CustomPacketPayload[0]);
            return true;
         }
      }

      return false;
   }

   private static Optional<String> getPlayerInventoryHandlerName(int slotIndex) {
      if (slotIndex == 38) {
         return Optional.of("armor");
      } else if (slotIndex == 40) {
         return Optional.of("offhand");
      } else {
         return slotIndex >= 0 && slotIndex < 36 ? Optional.of("main") : Optional.empty();
      }
   }

   private static class BackpackKeyConflictContext implements IKeyConflictContext {
      public static final KeybindHandler.BackpackKeyConflictContext INSTANCE = new KeybindHandler.BackpackKeyConflictContext();

      public boolean isActive() {
         return !KeyConflictContext.GUI.isActive() || Minecraft.getInstance().screen instanceof AbstractContainerScreen;
      }

      public boolean conflicts(IKeyConflictContext other) {
         return this == other;
      }
   }
}
