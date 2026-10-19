package fzzyhmstrs.emi_loot.client;

import fzzyhmstrs.emi_loot.EMILoot;
import fzzyhmstrs.emi_loot.util.ConditionalStack;
import fzzyhmstrs.emi_loot.util.TextKey;
import io.netty.handler.codec.DecoderException;
import it.unimi.dsi.fastutil.floats.Float2ObjectArrayMap;
import it.unimi.dsi.fastutil.floats.Float2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public abstract class AbstractTextKeyParsingClientLootTable<T extends LootReceiver> implements LootReceiver {
   private final Map<List<TextKey>, ClientRawPool> rawItems;
   public List<ClientBuiltPool> builtItems;

   public AbstractTextKeyParsingClientLootTable() {
      this.rawItems = new HashMap<>();
   }

   public AbstractTextKeyParsingClientLootTable(Map<List<TextKey>, ClientRawPool> map) {
      this.rawItems = map;
   }

   static ResourceLocation getIdFromBuf(FriendlyByteBuf buf) {
      String idToParse = buf.readUtf();
      if (idToParse.contains(":")) {
         return ResourceLocation.parse(idToParse);
      } else if (idToParse.startsWith("b/")) {
         return ResourceLocation.parse("blocks/" + idToParse.substring(2));
      } else if (idToParse.startsWith("e/")) {
         return ResourceLocation.parse("entities/" + idToParse.substring(2));
      } else if (idToParse.startsWith("c/")) {
         return ResourceLocation.parse("chests/" + idToParse.substring(2));
      } else if (idToParse.startsWith("g/")) {
         return ResourceLocation.parse("gameplay/" + idToParse.substring(2));
      } else {
         return idToParse.startsWith("a/") ? ResourceLocation.parse("archaeology/" + idToParse.substring(2)) : ResourceLocation.parse(idToParse);
      }
   }

   abstract void getSpecialTextKeyList(Level var1, Block var2, List<Tuple<Integer, Component>> var3);

   public void build(Level world, Block block) {
      boolean bl = BuiltInRegistries.BLOCK.wrapAsHolder(block).is(BlockTags.SLABS);
      Map<List<Tuple<Integer, Component>>, Object2FloatMap<ItemStack>> builderItems = new LinkedHashMap<>();
      this.rawItems.forEach((list, pool) -> {
         List<Tuple<Integer, Component>> applyToAllList = new ArrayList<>();
         this.getSpecialTextKeyList(world, block, applyToAllList);

         for (TextKey textKey : list) {
            Component text = textKey.processText();
            applyToAllList.add(new Tuple(textKey.index(), text));
         }

         pool.map().forEach((poolList, poolItemMap) -> {
            List<Tuple<Integer, Component>> newPoolList = new ArrayList<>();
            Object2FloatMap<ItemStack> itemsToAdd = new Object2FloatOpenHashMap();
            List<ItemStack> itemsToRemove = new ArrayList<>();

            for (TextKey textKeyxx : poolList) {
               poolItemMap.forEach((poolStack, weight) -> {
                  List<ItemStack> stacks = textKeyxx.processStack(poolStack, world);
                  float toAddWeight = 1.0F;
                  if (!stacks.contains(poolStack)) {
                     itemsToRemove.add(poolStack);
                     toAddWeight = weight;
                  }

                  for (ItemStack stack : stacks) {
                     if (poolItemMap.containsKey(stack)) {
                        toAddWeight = poolItemMap.getFloat(stack);
                     }
                  }

                  for (ItemStack stack : stacks) {
                     if (!poolItemMap.containsKey(stack)) {
                        itemsToAdd.put(stack, toAddWeight);
                     }
                  }
               });
               Component textx = textKeyxx.processText();
               newPoolList.add(new Tuple(textKeyxx.index(), textx));
            }

            for (TextKey textKeyx : list) {
               poolItemMap.forEach((poolStack, weight) -> {
                  List<ItemStack> stacks = textKeyx.processStack(poolStack, world);
                  float toAddWeight = 1.0F;
                  if (!stacks.contains(poolStack)) {
                     itemsToRemove.add(poolStack);
                     toAddWeight = weight;
                  }

                  for (ItemStack stack : stacks) {
                     if (poolItemMap.containsKey(stack)) {
                        toAddWeight = poolItemMap.getFloat(stack);
                     }
                  }

                  for (ItemStack stack : stacks) {
                     if (!poolItemMap.containsKey(stack)) {
                        itemsToAdd.put(stack, toAddWeight);
                     }
                  }
               });
            }

            List<Tuple<Integer, Component>> summedList;
            if (applyToAllList.isEmpty()) {
               if (!newPoolList.isEmpty() || EMILoot.config.skippedKeys.contains("emi_loot.no_conditions") && EMILoot.config.isTooltipStyle()) {
                  summedList = newPoolList;
               } else {
                  summedList = TextKey.noConditionsList.get();
               }
            } else {
               summedList = new ArrayList<>(applyToAllList);
               summedList.addAll(newPoolList);
            }

            Object2FloatMap<ItemStack> builderPoolMap = builderItems.getOrDefault(summedList, (Object2FloatMap<ItemStack>)poolItemMap);
            builderPoolMap.putAll(itemsToAdd);
            itemsToRemove.forEach(builderPoolMap::removeFloat);
            builderItems.put(summedList, builderPoolMap);
         });
      });
      List<ClientBuiltPool> finalList = new ArrayList<>();
      builderItems.forEach(
         (builtList, builtMap) -> {
            Float2ObjectMap<List<ItemStack>> consolidatedMap = new Float2ObjectArrayMap();
            builtMap.forEach((stack, weight) -> {
               List<ItemStack> consolidatedList = (List<ItemStack>)consolidatedMap.getOrDefault(weight, new ArrayList());
               if (!consolidatedList.contains(stack)) {
                  consolidatedList.add(stack);
               }

               consolidatedMap.put(weight, consolidatedList);
            });
            Float2ObjectMap<List<ItemStack>> emiConsolidatedMap = new Float2ObjectArrayMap();
            consolidatedMap.forEach((consolidatedWeight, consolidatedList) -> emiConsolidatedMap.put(consolidatedWeight, consolidatedList));
            finalList.add(
               new ClientBuiltPool(
                  (List<Tuple<Integer, Component>>)builtList,
                  emiConsolidatedMap.float2ObjectEntrySet()
                     .stream()
                     .map(
                        entry -> {
                           List<ItemStack> sortedList = ((List)entry.getValue())
                              .stream()
                              .sorted(Comparator.comparingInt(s -> BuiltInRegistries.ITEM.getId(s.getItem())))
                              .toList();
                           return new ConditionalStack(builtList, entry.getFloatKey(), sortedList);
                        }
                     )
                     .toList()
               )
            );
         }
      );
      this.builtItems = finalList;
   }

   abstract Tuple<ResourceLocation, ResourceLocation> getBufId(FriendlyByteBuf var1);

   abstract T simpleTableToReturn(Tuple<ResourceLocation, ResourceLocation> var1, FriendlyByteBuf var2);

   abstract T emptyTableToReturn();

   abstract T filledTableToReturn(Tuple<ResourceLocation, ResourceLocation> var1, Map<List<TextKey>, ClientRawPool> var2);

   @Override
   public LootReceiver fromBuf(FriendlyByteBuf buf) {
      boolean isEmpty = true;

      Tuple<ResourceLocation, ResourceLocation> ids;
      ResourceLocation id;
      try {
         ids = this.getBufId(buf);
         id = (ResourceLocation)ids.getA();
      } catch (Throwable e) {
         throw new DecoderException("Parsing of Loot Receiver failed before id could be parsed", e);
      }

      try {
         if (EMILoot.DEBUG) {
            EMILoot.LOGGER.info("parsing table {}", id);
         }

         int builderCount = buf.readShort();
         if (builderCount == -1) {
            return this.simpleTableToReturn(ids, buf);
         }

         Map<List<TextKey>, ClientRawPool> itemMap = new LinkedHashMap<>();

         for (int b = 0; b < builderCount; b++) {
            int conditionSize = buf.readShort();
            List<TextKey> qualifierList = new ArrayList<>(conditionSize + 1);

            for (int i = 0; i < conditionSize; i++) {
               try {
                  TextKey key = TextKey.fromBuf(buf);
                  qualifierList.add(key);
               } catch (DecoderException e) {
                  EMILoot.LOGGER.error("Client table {} had a TextKey decoding error while reading a loot condition!", id);
               }
            }

            int functionSize = buf.readShort();

            for (int i = 0; i < functionSize; i++) {
               try {
                  TextKey key = TextKey.fromBuf(buf);
                  qualifierList.add(key);
               } catch (DecoderException e) {
                  EMILoot.LOGGER.error("Client table {} had a TextKey decoding error while reading a loot function!", id);
               }
            }

            ClientRawPool pool = itemMap.getOrDefault(qualifierList, new ClientRawPool(new LinkedHashMap<>()));
            int pileSize = buf.readShort();

            for (int i = 0; i < pileSize; i++) {
               int pileQualifierSize = buf.readShort();
               List<TextKey> pileQualifierList = new ArrayList<>(pileQualifierSize);

               for (int j = 0; j < pileQualifierSize; j++) {
                  try {
                     TextKey key = TextKey.fromBuf(buf);
                     pileQualifierList.add(key);
                  } catch (DecoderException e) {
                     EMILoot.LOGGER.error("Client table {} had a TextKey decoding error while reading an item pile qualifier!", id);
                  }
               }

               Object2FloatMap<ItemStack> pileItemMap = pool.map().getOrDefault(pileQualifierList, new Object2FloatOpenHashMap());
               int pileItemSize = buf.readShort();

               for (int j = 0; j < pileItemSize; j++) {
                  ItemStack stack = this.readItemStack(buf);
                  float weight = buf.readFloat();
                  pileItemMap.put(stack, weight);
                  isEmpty = false;
               }

               pool.map().put(pileQualifierList, pileItemMap);
            }

            itemMap.put(qualifierList, pool);
         }

         return isEmpty ? this.emptyTableToReturn() : this.filledTableToReturn(ids, itemMap);
      } catch (Throwable e) {
         throw new DecoderException("Parsing of Loot Receiver failed for id: " + id.toString(), e);
      }
   }
}
