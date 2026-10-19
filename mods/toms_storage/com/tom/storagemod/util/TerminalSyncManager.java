package com.tom.storagemod.util;

import com.tom.storagemod.inventory.StoredItemStack;
import com.tom.storagemod.inventory.TerminalItemStack;
import com.tom.storagemod.menu.TerminalCraftingFiller;
import com.tom.storagemod.network.NetworkHandler;
import com.tom.storagemod.platform.Platform;
import io.netty.buffer.Unpooled;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemLore;

public class TerminalSyncManager {
   private static final int MAX_PACKET_SIZE = 64000;
   private Object2IntMap<StoredItemStack> idMap = new Object2IntOpenHashMap();
   private Int2ObjectMap<StoredItemStack> idMap2 = new Int2ObjectArrayMap();
   private Map<StoredItemStack, TerminalItemStack> items = new HashMap<>();
   private Map<TerminalItemStack, TerminalItemStack> itemList = new HashMap<>();
   private int lastId = 1;
   private int lastChangeID = -1;
   private RegistryFriendlyByteBuf workBuf;

   public TerminalSyncManager(RegistryAccess reg) {
      this.workBuf = Platform.makeRegByteBuf(Unpooled.buffer(64000, 128000), reg);
   }

   private void write(RegistryFriendlyByteBuf buf, TerminalItemStack stack) {
      ItemStack st = stack.getStack();
      byte flags = (byte)(stack.getQuantity() == 0L ? 1 : 0);
      boolean wr = true;
      int id = this.idMap.getInt(stack);
      if (id != 0) {
         flags = (byte)(flags | 4);
         wr = false;
      }

      buf.writeByte(flags);
      buf.writeVarInt(this.idMap.computeIfAbsent(stack, s -> {
         int i = this.lastId++;
         this.idMap2.put(i, (StoredItemStack)s);
         return i;
      }));
      if (wr) {
         ItemStack.STREAM_CODEC.encode(buf, st);
      }

      if (stack.getQuantity() != 0L) {
         buf.writeVarLong(stack.getQuantity());
         buf.writeVarInt(stack.getUsedSlotCount());
      }
   }

   private void writeMiniStack(RegistryFriendlyByteBuf buf, TerminalItemStack stack) {
      int id = this.idMap.getInt(stack);
      byte flags = (byte)((stack.getQuantity() == 0L ? 1 : 0) | 2);
      buf.writeByte(flags);
      buf.writeVarInt(id);
      ItemStack is = new ItemStack(stack.getStack().getItem());
      is.set(DataComponents.LORE, new ItemLore(List.of(Component.translatable("tooltip.toms_storage.nbt_overflow").withStyle(ChatFormatting.RED))));
      ItemStack.STREAM_CODEC.encode(buf, is);
      if (stack.getQuantity() != 0L) {
         buf.writeVarLong(stack.getQuantity());
         buf.writeVarInt(stack.getUsedSlotCount());
      }
   }

   private TerminalItemStack read(RegistryFriendlyByteBuf buf) {
      byte flags = buf.readByte();
      int id = buf.readVarInt();
      boolean rd = (flags & 4) == 0;
      TerminalItemStack stack;
      if (rd) {
         stack = new TerminalItemStack((ItemStack)ItemStack.STREAM_CODEC.decode(buf));
      } else {
         StoredItemStack st = (StoredItemStack)this.idMap2.get(id);
         if (st == null) {
            ItemStack is = new ItemStack(Items.BARRIER);
            is.set(DataComponents.LORE, new ItemLore(List.of(Component.translatable("tooltip.toms_storage.item_sync_error").withStyle(ChatFormatting.RED))));
            stack = new TerminalItemStack(is);
         } else {
            stack = new TerminalItemStack(st.getStack());
         }
      }

      if ((flags & 1) == 0) {
         stack.setCount(buf.readVarLong());
         stack.setUsedSlotCount(buf.readVarInt());
      } else {
         stack.setCount(0L);
         stack.setUsedSlotCount(0);
      }

      this.idMap.put(stack, id);
      this.idMap2.put(id, stack);
      return stack;
   }

   public void update(int changeID, Map<StoredItemStack, TerminalItemStack> items, ServerPlayer player, Consumer<CompoundTag> extraSync) {
      if (changeID != this.lastChangeID) {
         this.lastChangeID = changeID;
         List<TerminalItemStack> toWrite = new ArrayList<>();
         items.forEach((sx, c) -> {
            TerminalItemStack pc = this.items.remove(sx);
            if (pc == null || !c.equalDetails(pc)) {
               toWrite.add(c);
            }
         });
         this.items.forEach((sx, c) -> toWrite.add(new TerminalItemStack(sx.getStack(), 0L)));
         this.items.clear();
         this.items.putAll(items);
         if (!toWrite.isEmpty()) {
            this.workBuf.writerIndex(0);
            int j = 0;

            for (int i = 0; i < toWrite.size(); j++) {
               TerminalItemStack stack = toWrite.get(i);
               int li = this.workBuf.writerIndex();

               try {
                  this.write(this.workBuf, stack);
               } catch (IndexOutOfBoundsException e) {
                  this.workBuf.writerIndex(li);
                  this.writeMiniStack(this.workBuf, stack);
               }

               int s = this.workBuf.writerIndex();
               if ((s > 64000 || j > 32000) && j > 1) {
                  CompoundTag t = this.writeBuf("d", this.workBuf, li);
                  t.putShort("l", (short)j);
                  NetworkHandler.sendTo(player, t);
                  j = 0;
                  this.workBuf.writerIndex(0);
                  if (s - li > 64000) {
                     this.writeMiniStack(this.workBuf, stack);
                  } else {
                     this.workBuf.writeBytes(this.workBuf, li, s - li);
                  }
               }

               i++;
            }

            if (j > 0 || extraSync != null) {
               CompoundTag t;
               if (j > 0) {
                  t = this.writeBuf("d", this.workBuf, this.workBuf.writerIndex());
                  t.putShort("l", (short)j);
               } else {
                  t = new CompoundTag();
               }

               if (extraSync != null) {
                  extraSync.accept(t);
               }

               NetworkHandler.sendTo(player, t);
               return;
            }
         }
      }

      if (extraSync != null) {
         CompoundTag t = new CompoundTag();
         extraSync.accept(t);
         NetworkHandler.sendTo(player, t);
      }
   }

   public boolean receiveUpdate(RegistryAccess registryAccess, CompoundTag tag) {
      if (!tag.contains("d")) {
         return false;
      }

      RegistryFriendlyByteBuf buf = Platform.makeRegByteBuf(Unpooled.wrappedBuffer(tag.getByteArray("d")), registryAccess);
      List<TerminalItemStack> in = new ArrayList<>();
      short len = tag.getShort("l");

      for (int i = 0; i < len; i++) {
         in.add(this.read(buf));
      }

      in.forEach(s -> {
         if (s.getQuantity() == 0L) {
            this.itemList.remove(s);
         } else {
            this.itemList.put(s, s);
         }
      });
      return true;
   }

   public void sendInteract(StoredItemStack intStack, TerminalSyncManager.SlotAction action, boolean mod) {
      FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
      int flags = mod ? 1 : 0;
      if (intStack == null) {
         buf.writeByte(flags | 2);
      } else {
         buf.writeByte(flags);
         buf.writeVarInt(this.idMap.getInt(intStack));
         buf.writeVarLong(intStack.getQuantity());
      }

      buf.writeEnum(action);
      NetworkHandler.sendDataToServer(this.writeBuf("a", buf, buf.writerIndex()));
   }

   private CompoundTag writeBuf(String id, FriendlyByteBuf buf, int len) {
      byte[] data = new byte[len];
      buf.getBytes(0, data);
      CompoundTag tag = new CompoundTag();
      tag.putByteArray(id, data);
      return tag;
   }

   public void receiveInteract(CompoundTag tag, TerminalSyncManager.InteractHandler handler) {
      if (tag.contains("a")) {
         FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.wrappedBuffer(tag.getByteArray("a")));
         byte flags = buf.readByte();
         StoredItemStack stack;
         if ((flags & 2) != 0) {
            stack = null;
         } else {
            stack = (StoredItemStack)this.idMap2.get(buf.readVarInt());
            long count = buf.readVarLong();
            if (stack != null) {
               stack = new StoredItemStack(stack.getStack());
               stack.setCount(count);
            }
         }

         handler.onInteract(stack, (TerminalSyncManager.SlotAction)buf.readEnum(TerminalSyncManager.SlotAction.class), (flags & 1) != 0);
      }
   }

   public List<TerminalItemStack> getAsList() {
      return new ArrayList<>(this.itemList.values());
   }

   public void fillStackedContents(StackedContents stc) {
      this.items.forEach((s, c) -> stc.accountSimpleStack(c.getActualStack()));
   }

   public long getAmount(StoredItemStack stack) {
      StoredItemStack s = this.itemList.get(stack);
      return s != null ? s.getQuantity() : 0L;
   }

   public void fillCraftingFiller(TerminalCraftingFiller terminalCraftingFiller) {
      this.items.forEach((s, c) -> terminalCraftingFiller.accountStack(c.getActualStack()));
   }

   public interface InteractHandler {
      void onInteract(StoredItemStack var1, TerminalSyncManager.SlotAction var2, boolean var3);
   }

   public enum SlotAction {
      PULL_OR_PUSH_STACK,
      PULL_ONE,
      SPACE_CLICK,
      SHIFT_PULL,
      GET_HALF,
      GET_QUARTER,
      CRAFT;

      public static final TerminalSyncManager.SlotAction[] VALUES = values();
   }
}
