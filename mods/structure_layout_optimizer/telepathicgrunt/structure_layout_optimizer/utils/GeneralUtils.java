package telepathicgrunt.structure_layout_optimizer.utils;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntComparators;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap.Entry;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import net.minecraft.Util;
import net.minecraft.core.FrontAndTop;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NumericTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.JigsawBlock;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;

public final class GeneralUtils {
   private GeneralUtils() {
   }

   public static <T> T loadService(Class<T> service) {
      return ServiceLoader.load(service, service.getClassLoader())
         .findFirst()
         .orElseThrow(() -> new IllegalStateException("No platform implementation found for " + service.getName()));
   }

   public static boolean canJigsawsAttach(StructureBlockInfo jigsaw1, StructureBlockInfo jigsaw2) {
      FrontAndTop prop1 = (FrontAndTop)jigsaw1.state().getValue(JigsawBlock.ORIENTATION);
      FrontAndTop prop2 = (FrontAndTop)jigsaw2.state().getValue(JigsawBlock.ORIENTATION);
      return prop1.front() == prop2.front().getOpposite()
         && (prop1.top() == prop2.top() || isRollableJoint(jigsaw1, prop1))
         && getStringMicroOptimised(jigsaw1.nbt(), "target").equals(getStringMicroOptimised(jigsaw2.nbt(), "name"));
   }

   private static boolean isRollableJoint(StructureBlockInfo jigsaw1, FrontAndTop prop1) {
      String joint = getStringMicroOptimised(jigsaw1.nbt(), "joint");
      return !joint.equals("rollable") && !joint.equals("aligned") ? !prop1.front().getAxis().isHorizontal() : joint.equals("rollable");
   }

   public static void shuffleAndPrioritize(List<StructureBlockInfo> list, RandomSource random) {
      Int2ObjectArrayMap<List<StructureBlockInfo>> buckets = new Int2ObjectArrayMap();

      for (StructureBlockInfo structureBlockInfo : list) {
         int key = 0;
         if (structureBlockInfo.nbt() != null) {
            key = getIntMicroOptimised(structureBlockInfo.nbt(), "selection_priority");
         }

         ((List)buckets.computeIfAbsent(key, k -> new ArrayList())).add(structureBlockInfo);
      }

      ObjectIterator var6 = buckets.values().iterator();

      while (var6.hasNext()) {
         List<StructureBlockInfo> bucketList = (List<StructureBlockInfo>)var6.next();
         Util.shuffle(bucketList, random);
      }

      if (buckets.size() == 1) {
         list.clear();
         copyAll((List<StructureBlockInfo>)((Entry)buckets.int2ObjectEntrySet().fastIterator().next()).getValue(), list);
      } else if (buckets.size() > 1) {
         list.clear();
         IntArrayList keys = new IntArrayList(buckets.keySet());
         keys.sort(IntComparators.OPPOSITE_COMPARATOR);

         for (int i = 0; i < keys.size(); i++) {
            copyAll((List<StructureBlockInfo>)buckets.get(keys.getInt(i)), list);
         }
      }
   }

   public static int getIntMicroOptimised(CompoundTag tag, String key) {
      return tag.get(key) instanceof NumericTag numericTag ? numericTag.getAsInt() : 0;
   }

   public static String getStringMicroOptimised(CompoundTag tag, String key) {
      return tag.get(key) instanceof StringTag stringTag ? stringTag.getAsString() : "";
   }

   public static <T> void copyAll(List<T> src, List<T> dest) {
      for (int i = 0; i < src.size(); i++) {
         dest.add(src.get(i));
      }
   }
}
