package fzzyhmstrs.emi_loot.client;

import fzzyhmstrs.emi_loot.util.TextKey;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class ClientMobLootTable extends AbstractTextKeyParsingClientLootTable<ClientMobLootTable> {
   public static ClientMobLootTable INSTANCE = new ClientMobLootTable();
   private static final ResourceLocation EMPTY = ResourceLocation.parse("entity/empty");
   public final ResourceLocation id;
   public final ResourceLocation mobId;
   public String color = "";

   public ClientMobLootTable() {
      this.id = EMPTY;
      this.mobId = ResourceLocation.parse("empty");
   }

   public ClientMobLootTable(ResourceLocation id, ResourceLocation mobId, Map<List<TextKey>, ClientRawPool> map) {
      super(map);
      this.id = id;
      String ns = id.getNamespace();
      String pth = id.getPath();
      if (!BuiltInRegistries.ENTITY_TYPE.containsKey(mobId)) {
         this.mobId = ResourceLocation.parse("empty");
      } else {
         if (Objects.equals(mobId, BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.SHEEP))) {
            int lastSlashIndex = pth.lastIndexOf(47);
            if (lastSlashIndex != -1) {
               this.color = pth.substring(Math.min(lastSlashIndex + 1, pth.length()));
            }
         }

         this.mobId = mobId;
      }
   }

   @Override
   public ResourceLocation getId() {
      return this.id;
   }

   @Override
   public boolean isEmpty() {
      return Objects.equals(this.id, EMPTY);
   }

   @Override
   void getSpecialTextKeyList(Level world, Block block, List<Tuple<Integer, Component>> inputList) {
   }

   @Override
   Tuple<ResourceLocation, ResourceLocation> getBufId(FriendlyByteBuf buf) {
      ResourceLocation id = getIdFromBuf(buf);
      ResourceLocation mobId = getIdFromBuf(buf);
      return new Tuple(id, mobId);
   }

   ClientMobLootTable simpleTableToReturn(Tuple<ResourceLocation, ResourceLocation> ids, FriendlyByteBuf buf) {
      ClientRawPool simplePool = new ClientRawPool(new HashMap<>());
      Object2FloatMap<ItemStack> simpleMap = new Object2FloatOpenHashMap();
      ItemStack simpleStack = new ItemStack(
         BuiltInRegistries.ITEM.getHolder(buf.readResourceKey(Registries.ITEM)).<ItemLike>map(Reference::value).orElse(Items.AIR)
      );
      simpleMap.put(simpleStack, 100.0F);
      simplePool.map().put(new ArrayList<>(), simpleMap);
      Map<List<TextKey>, ClientRawPool> itemMap = new HashMap<>();
      itemMap.put(new ArrayList<>(), simplePool);
      return new ClientMobLootTable((ResourceLocation)ids.getA(), (ResourceLocation)ids.getB(), itemMap);
   }

   ClientMobLootTable emptyTableToReturn() {
      return new ClientMobLootTable();
   }

   ClientMobLootTable filledTableToReturn(Tuple<ResourceLocation, ResourceLocation> ids, Map<List<TextKey>, ClientRawPool> itemMap) {
      return new ClientMobLootTable((ResourceLocation)ids.getA(), (ResourceLocation)ids.getB(), itemMap);
   }
}
