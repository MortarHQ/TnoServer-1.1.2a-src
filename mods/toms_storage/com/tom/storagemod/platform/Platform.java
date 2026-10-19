package com.tom.storagemod.platform;

import com.tom.storagemod.Content;
import com.tom.storagemod.block.entity.BlockFilterAttachment;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.neoforged.neoforge.network.connection.ConnectionType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries.Keys;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

public class Platform {
   public static final GameObject.GameRegistry<Item> ITEMS = new GameObject.GameRegistry<>(Registries.ITEM);
   public static final GameObject.GameRegistry<Block> BLOCKS = new GameObject.GameRegistry<>(Registries.BLOCK);
   public static final GameObject.GameRegistryBE BLOCK_ENTITY = new GameObject.GameRegistryBE();
   public static final GameObject.GameRegistry<MenuType<?>> MENU_TYPE = new GameObject.GameRegistry<>(Registries.MENU);
   public static final DeferredRegister<CreativeModeTab> TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, "toms_storage");
   public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(Keys.ATTACHMENT_TYPES, "toms_storage");
   public static final GameObject.GameRegistry<DataComponentType<?>> DATA_COMPONENT_TYPES = new GameObject.GameRegistry<>(Registries.DATA_COMPONENT_TYPE);
   private static List<Item> tabItems = new ArrayList<>();
   public static final DeferredHolder<CreativeModeTab, CreativeModeTab> STORAGE_MOD_TAB = TAB.register(
      "tab",
      () -> CreativeModeTab.builder()
         .title(Component.translatable("itemGroup.toms_storage.tab"))
         .icon(() -> new ItemStack((ItemLike)Content.terminal.get()))
         .displayItems((p, out) -> tabItems.forEach(out::accept))
         .build()
   );
   public static final Supplier<AttachmentType<BlockFilterAttachment>> BLOCK_FILTER = ATTACHMENT_TYPES.register(
      "block_filter", () -> AttachmentType.serializable(BlockFilterAttachment::new).build()
   );
   public static boolean vivecraft = ModList.get().isLoaded("vivecraft");
   private static boolean curios = ModList.get().isLoaded("curios");

   public static <I extends Item> I addItemToTab(I item) {
      tabItems.add(item);
      return item;
   }

   public static void register(IEventBus bus) {
      ITEMS.register(bus);
      BLOCKS.register(bus);
      BLOCK_ENTITY.register(bus);
      MENU_TYPE.register(bus);
      TAB.register(bus);
      ATTACHMENT_TYPES.register(bus);
      DATA_COMPONENT_TYPES.register(bus);
   }

   public static InteractionResult checkUse(Level worldIn, BlockHitResult lookingAt, Player playerIn, InteractionHand handIn) {
      RightClickBlock event = new RightClickBlock(playerIn, handIn, lookingAt.getBlockPos(), lookingAt);
      NeoForge.EVENT_BUS.post(event);
      return event.isCanceled() ? event.getCancellationResult() : null;
   }

   public static <T> T checkExtraSlots(Player player, Predicate<ItemStack> is, T def, Function<ItemStack, T> map) {
      if (curios) {
         ICuriosItemHandler handler = (ICuriosItemHandler)player.getCapability(CuriosCapability.INVENTORY);
         if (handler == null) {
            return def;
         }

         List<SlotResult> s = handler.findCurios(is);
         if (!s.isEmpty()) {
            return map.apply(s.get(0).stack());
         }
      }

      return def;
   }

   public static boolean notifyBlocks(Level level, BlockPos worldPosition, Direction direction) {
      return !EventHooks.onNeighborNotify(level, worldPosition, level.getBlockState(worldPosition), EnumSet.of(direction), false).isCanceled();
   }

   public static RegistryFriendlyByteBuf makeRegByteBuf(ByteBuf buffer, RegistryAccess reg) {
      return new RegistryFriendlyByteBuf(buffer, reg, ConnectionType.NEOFORGE);
   }

   public static ItemStack getCloneItemStack(Level level, HitResult hitResult, BlockPos pos, Player player) {
      return level.getBlockState(pos).getCloneItemStack(hitResult, level, pos, player);
   }

   public static boolean canScrapeWithItem(ItemStack item, Player player, InteractionHand hand) {
      return item.canPerformAction(ItemAbilities.AXE_SCRAPE);
   }
}
