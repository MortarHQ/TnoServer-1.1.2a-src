package fzzyhmstrs.emi_loot.client;

import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ClientArchaeologyLootTable implements LootReceiver {
   public static ClientArchaeologyLootTable INSTANCE = new ClientArchaeologyLootTable();
   public final ResourceLocation id;
   public final Object2FloatMap<ItemStack> items;

   public ClientArchaeologyLootTable() {
      this.id = ResourceLocation.parse("empty");
      this.items = new Object2FloatOpenHashMap();
   }

   public ClientArchaeologyLootTable(ResourceLocation id, Object2FloatMap<ItemStack> map) {
      this.id = id;
      this.items = map;
   }

   @Override
   public boolean isEmpty() {
      return this.items.isEmpty();
   }

   @Override
   public ResourceLocation getId() {
      return this.id;
   }

   @Override
   public LootReceiver fromBuf(FriendlyByteBuf buf) {
      ResourceLocation id = AbstractTextKeyParsingClientLootTable.getIdFromBuf(buf);
      int mapCount = buf.readShort();
      Object2FloatMap<ItemStack> itemMap = new Object2FloatOpenHashMap();

      for (int i = 0; i < mapCount; i++) {
         ItemStack item = this.readItemStack(buf);
         float itemWeight = buf.readFloat();
         if (!item.is(Items.AIR)) {
            itemMap.put(item, itemWeight);
         }
      }

      return new ClientArchaeologyLootTable(id, itemMap);
   }
}
