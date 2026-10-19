package nadiendev.constructionwand.items.containeritems;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.capabilities.Capabilities.ItemHandler;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;

public class ItemVoidSack extends Item {
   public static final String TAG_ROOT = "VoidSack";
   public static final String TAG_ITEMS = "Items";
   public static final String TAG_LINKED_POS = "LinkedPos";
   public static final String TAG_LINKED_DIM = "LinkedDim";
   public static final String TAG_SEND_TO_CONT = "SendToContainer";
   public static final String TAG_ACTIVE = "Active";
   public static final int ROWS = 4;
   public static final int COLS = 4;
   public static final int SIZE = 16;
   private final int tier;

   public ItemVoidSack(Properties properties, int tier) {
      super(properties);
      this.tier = tier;
   }

   public static CompoundTag getData(ItemStack sack) {
      CustomData cd = (CustomData)sack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
      CompoundTag root = cd.copyTag();
      if (!root.contains("VoidSack", 10)) {
         root.put("VoidSack", new CompoundTag());
      }

      return root.getCompound("VoidSack");
   }

   public static void setData(ItemStack sack, CompoundTag tag) {
      CustomData cd = (CustomData)sack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
      CompoundTag root = cd.copyTag();
      root.put("VoidSack", tag);
      sack.set(DataComponents.CUSTOM_DATA, CustomData.of(root));
   }

   public static SimpleContainer loadInventory(ItemStack sack) {
      SimpleContainer inv = new SimpleContainer(16);
      CompoundTag tag = getData(sack);
      if (!tag.contains("Items", 9)) {
         return inv;
      }

      ListTag list = tag.getList("Items", 10);

      for (int i = 0; i < list.size(); i++) {
         CompoundTag slot = list.getCompound(i);
         int slotIdx = slot.getByte("Slot") & 255;
         if (slotIdx < 16) {
            ItemStack s = ItemStack.CODEC.parse(NbtOps.INSTANCE, slot).result().orElse(ItemStack.EMPTY);
            if (!s.isEmpty()) {
               inv.setItem(slotIdx, s);
            }
         }
      }

      return inv;
   }

   public static void saveInventory(ItemStack sack, SimpleContainer inv) {
      CompoundTag tag = getData(sack).copy();
      ListTag list = new ListTag();

      for (int i = 0; i < inv.getContainerSize(); i++) {
         ItemStack s = inv.getItem(i);
         if (!s.isEmpty()) {
            Tag encoded = (Tag)ItemStack.CODEC.encodeStart(NbtOps.INSTANCE, s).result().orElse(null);
            if (encoded instanceof CompoundTag slot) {
               slot.putByte("Slot", (byte)i);
               list.add(slot);
            }
         }
      }

      tag.put("Items", list);
      setData(sack, tag);
   }

   @Nullable
   public static BlockPos getLinkedPos(ItemStack sack) {
      CompoundTag tag = getData(sack);
      return !tag.contains("LinkedPos", 4) ? null : BlockPos.of(tag.getLong("LinkedPos"));
   }

   @Nullable
   public static ResourceLocation getLinkedDim(ItemStack sack) {
      CompoundTag tag = getData(sack);
      return !tag.contains("LinkedDim", 8) ? null : ResourceLocation.tryParse(tag.getString("LinkedDim"));
   }

   public static void setLinkedPos(ItemStack sack, @Nullable BlockPos pos, @Nullable ResourceLocation dim) {
      CompoundTag tag = getData(sack).copy();
      if (pos == null) {
         tag.remove("LinkedPos");
         tag.remove("LinkedDim");
      } else {
         tag.putLong("LinkedPos", pos.asLong());
         tag.putString("LinkedDim", dim != null ? dim.toString() : "minecraft:overworld");
      }

      setData(sack, tag);
   }

   public static boolean isSendToContainer(ItemStack sack) {
      return getData(sack).getBoolean("SendToContainer");
   }

   public static void setSendToContainer(ItemStack sack, boolean value) {
      CompoundTag tag = getData(sack).copy();
      tag.putBoolean("SendToContainer", value);
      setData(sack, tag);
   }

   public static boolean isActive(ItemStack sack) {
      return getData(sack).getBoolean("Active");
   }

   public static void setActive(ItemStack sack, boolean value) {
      CompoundTag tag = getData(sack).copy();
      tag.putBoolean("Active", value);
      setData(sack, tag);
   }

   public static int receive(ServerLevel level, ItemStack sack, ItemStack toInsert) {
      if (!sack.isEmpty() && !toInsert.isEmpty()) {
         int remaining = toInsert.getCount();
         if (isSendToContainer(sack) && getLinkedPos(sack) != null) {
            remaining = insertToLinkedContainer(level, sack, toInsert.copy());
            if (remaining == 0) {
               return 0;
            }
         }

         ItemStack leftover = toInsert.copy();
         leftover.setCount(remaining);
         return insertToInternalInventory(sack, leftover);
      } else {
         return toInsert.getCount();
      }
   }

   public static int interceptPickup(ServerLevel level, ItemStack sack, ItemStack pickedUp) {
      return !isActive(sack) ? pickedUp.getCount() : receive(level, sack, pickedUp);
   }

   private static int insertToInternalInventory(ItemStack sack, ItemStack toInsert) {
      SimpleContainer inv = loadInventory(sack);
      ItemStack remainder = inv.addItem(toInsert.copy());
      saveInventory(sack, inv);
      return remainder.getCount();
   }

   private static int insertToLinkedContainer(ServerLevel level, ItemStack sack, ItemStack toInsert) {
      BlockPos pos = getLinkedPos(sack);
      if (pos == null) {
         return toInsert.getCount();
      }

      ResourceLocation dim = getLinkedDim(sack);
      if (dim != null && !dim.equals(level.dimension().location())) {
         return toInsert.getCount();
      }

      BlockEntity be = level.getBlockEntity(pos);
      if (be == null) {
         return toInsert.getCount();
      }

      IItemHandler handler = (IItemHandler)level.getCapability(ItemHandler.BLOCK, pos, be.getBlockState(), be, null);
      if (handler == null) {
         return toInsert.getCount();
      }

      ItemStack remaining = toInsert.copy();

      for (int i = 0; i < handler.getSlots() && !remaining.isEmpty(); i++) {
         remaining = handler.insertItem(i, remaining, false);
      }

      return remaining.getCount();
   }

   public InteractionResult useOn(UseOnContext ctx) {
      Level level = ctx.getLevel();
      BlockPos pos = ctx.getClickedPos();
      Player player = ctx.getPlayer();
      ItemStack sack = ctx.getItemInHand();
      if (player == null) {
         return InteractionResult.PASS;
      }

      if (!level.isClientSide) {
         BlockEntity be = level.getBlockEntity(pos);
         if (be != null) {
            IItemHandler handler = (IItemHandler)level.getCapability(ItemHandler.BLOCK, pos, be.getBlockState(), be, null);
            if (handler != null) {
               ResourceLocation dim = level.dimension().location();
               setLinkedPos(sack, pos, dim);
               if (player instanceof ServerPlayer sp) {
                  sp.sendSystemMessage(
                     Component.translatable("item.constructionwand.void_sack.linked_msg", new Object[]{pos.getX(), pos.getY(), pos.getZ()})
                        .withStyle(ChatFormatting.GREEN)
                  );
               }

               return InteractionResult.SUCCESS;
            }
         }
      }

      return InteractionResult.PASS;
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      ItemStack sack = player.getItemInHand(hand);
      if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
         openGui(serverPlayer, hand);
      }

      return InteractionResultHolder.sidedSuccess(sack, level.isClientSide);
   }

   public static void openGui(ServerPlayer player, final InteractionHand hand) {
      player.openMenu(new MenuProvider() {
         public Component getDisplayName() {
            return Component.translatable("item.constructionwand.void_sack");
         }

         public AbstractContainerMenu createMenu(int id, Inventory inv, Player p) {
            return new MenuVoidSack(id, inv, hand);
         }
      }, buf -> buf.writeBoolean(hand == InteractionHand.MAIN_HAND));
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
      super.appendHoverText(stack, context, tooltip, flag);
      boolean active = isActive(stack);
      tooltip.add(
         Component.translatable(active ? "item.constructionwand.void_sack.active" : "item.constructionwand.void_sack.inactive")
            .withStyle(active ? ChatFormatting.GREEN : ChatFormatting.DARK_GRAY)
      );
      BlockPos linked = getLinkedPos(stack);
      if (linked != null) {
         tooltip.add(
            Component.translatable("item.constructionwand.void_sack.linked", new Object[]{linked.getX(), linked.getY(), linked.getZ()})
               .withStyle(ChatFormatting.GRAY)
         );
         boolean send = isSendToContainer(stack);
         tooltip.add(
            Component.translatable(send ? "item.constructionwand.void_sack.sending" : "item.constructionwand.void_sack.storing")
               .withStyle(send ? ChatFormatting.GREEN : ChatFormatting.YELLOW)
         );
      } else {
         tooltip.add(Component.translatable("item.constructionwand.void_sack.no_link").withStyle(ChatFormatting.DARK_GRAY));
      }

      SimpleContainer inv = loadInventory(stack);
      int used = 0;

      for (int i = 0; i < inv.getContainerSize(); i++) {
         if (!inv.getItem(i).isEmpty()) {
            used++;
         }
      }

      tooltip.add(Component.translatable("item.constructionwand.void_sack.slots_used", new Object[]{used, 16}).withStyle(ChatFormatting.AQUA));
   }
}
