package fzzyhmstrs.emi_loot.client;

import fzzyhmstrs.emi_loot.util.LText;
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
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class ClientBlockLootTable extends AbstractTextKeyParsingClientLootTable<ClientBlockLootTable> {
   public static ClientBlockLootTable INSTANCE = new ClientBlockLootTable();
   private static final ResourceLocation EMPTY = ResourceLocation.parse("blocks/empty");
   public final ResourceLocation id;
   public final ResourceLocation blockId;
   public final boolean isSimple;

   public ClientBlockLootTable() {
      this.id = EMPTY;
      this.blockId = ResourceLocation.parse("air");
      this.isSimple = false;
   }

   public ClientBlockLootTable(ResourceLocation id, Map<List<TextKey>, ClientRawPool> map) {
      super(map);
      this.id = id;
      String ns = id.getNamespace();
      String pth = id.getPath();
      int lastSlashIndex = pth.lastIndexOf(47);
      if (lastSlashIndex == -1) {
         this.blockId = ResourceLocation.fromNamespaceAndPath(ns, pth);
      } else {
         this.blockId = ResourceLocation.fromNamespaceAndPath(ns, pth.substring(Math.min(lastSlashIndex + 1, pth.length())));
      }

      this.isSimple = false;
   }

   public ClientBlockLootTable(ResourceLocation id, Map<List<TextKey>, ClientRawPool> map, boolean isSimple) {
      super(map);
      this.id = id;
      String ns = id.getNamespace();
      String pth = id.getPath();
      int lastSlashIndex = pth.lastIndexOf(47);
      if (lastSlashIndex == -1) {
         this.blockId = ResourceLocation.fromNamespaceAndPath(ns, pth);
      } else {
         this.blockId = ResourceLocation.fromNamespaceAndPath(ns, pth.substring(Math.min(lastSlashIndex + 1, pth.length())));
      }

      this.isSimple = isSimple;
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
      String tool = "";
      if (block.builtInRegistryHolder().is(BlockTags.MINEABLE_WITH_PICKAXE)) {
         tool = "pickaxe";
      } else if (block.builtInRegistryHolder().is(BlockTags.MINEABLE_WITH_AXE)) {
         tool = "axe";
      } else if (block.builtInRegistryHolder().is(BlockTags.MINEABLE_WITH_SHOVEL)) {
         tool = "shovel";
      } else if (block.builtInRegistryHolder().is(BlockTags.MINEABLE_WITH_HOE)) {
         tool = "hoe";
      }

      if (!Objects.equals(tool, "")) {
         String type;
         if (block.builtInRegistryHolder().is(BlockTags.NEEDS_STONE_TOOL)) {
            type = "stone";
         } else if (block.builtInRegistryHolder().is(BlockTags.NEEDS_IRON_TOOL)) {
            type = "iron";
         } else if (block.builtInRegistryHolder().is(BlockTags.NEEDS_DIAMOND_TOOL)) {
            type = "diamond";
         } else {
            type = "wood";
         }

         String keyString = "emi_loot." + tool + "." + type;
         int keyIndex = TextKey.getIndex(keyString);
         if (keyIndex != -1) {
            inputList.add(new Tuple(keyIndex, LText.translatable(keyString)));
         }
      }
   }

   @Override
   Tuple<ResourceLocation, ResourceLocation> getBufId(FriendlyByteBuf buf) {
      return new Tuple(getIdFromBuf(buf), EMPTY);
   }

   ClientBlockLootTable simpleTableToReturn(Tuple<ResourceLocation, ResourceLocation> ids, FriendlyByteBuf buf) {
      ClientRawPool simplePool = new ClientRawPool(new HashMap<>());
      Object2FloatMap<ItemStack> simpleMap = new Object2FloatOpenHashMap();
      ItemStack simpleStack = new ItemStack(
         BuiltInRegistries.ITEM.getHolder(buf.readResourceKey(Registries.ITEM)).<ItemLike>map(Reference::value).orElse(Items.AIR)
      );
      simpleMap.put(simpleStack, 100.0F);
      simplePool.map().put(new ArrayList<>(), simpleMap);
      Map<List<TextKey>, ClientRawPool> itemMap = new HashMap<>();
      itemMap.put(new ArrayList<>(), simplePool);
      return new ClientBlockLootTable((ResourceLocation)ids.getA(), itemMap, true);
   }

   ClientBlockLootTable emptyTableToReturn() {
      return new ClientBlockLootTable();
   }

   ClientBlockLootTable filledTableToReturn(Tuple<ResourceLocation, ResourceLocation> ids, Map<List<TextKey>, ClientRawPool> itemMap) {
      return new ClientBlockLootTable((ResourceLocation)ids.getA(), itemMap);
   }
}
