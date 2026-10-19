package noobanidus.mods.lootr.common.api.replacement;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.core.Holder.Reference;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.level.block.state.properties.Property;
import noobanidus.mods.lootr.common.api.LootrTags;
import org.jetbrains.annotations.Nullable;

public class BlockReplacementMap {
   private final Set<Block> ignoredBlocks = new HashSet<>();
   private final Map<Block, Block> replacements = new Object2ObjectOpenHashMap();
   private final List<ILootrBlockReplacementProvider> allProviders = new ArrayList<>();

   public void register(ILootrBlockReplacementProvider provider) {
      this.allProviders.add(provider);
   }

   public void sort() {
      this.allProviders.sort(Comparator.comparingInt(ILootrBlockReplacementProvider::getPriority).reversed());
   }

   public void clear() {
      this.ignoredBlocks.clear();
      this.replacements.clear();
   }

   @Nullable
   public BlockState getReplacement(BlockState state) {
      Block block = state.getBlock();
      if (this.ignoredBlocks.contains(block)) {
         return null;
      }

      Reference<Block> holder = block.builtInRegistryHolder();
      if (holder.is(LootrTags.Blocks.CONVERT_BLACKLIST)) {
         this.ignoredBlocks.add(block);
         return null;
      }

      Block result = this.replacements.get(block);
      if (result == null) {
         for (ILootrBlockReplacementProvider provider : this.allProviders) {
            Block replacement = provider.apply(block);
            if (replacement != null) {
               result = replacement;
               break;
            }
         }
      }

      if (result == null) {
         this.ignoredBlocks.add(block);
         return null;
      }

      BlockState replacement = result.defaultBlockState();

      for (Property<?> prop : replacement.getProperties()) {
         if (state.hasProperty(prop)) {
            replacement = safeReplace(replacement, state, prop);
         }
      }

      return replacement;
   }

   public static <V extends Comparable<V>> BlockState safeReplace(BlockState state, BlockState original, Property<V> property) {
      if (property == ChestBlock.TYPE && state.hasProperty(property)) {
         return (BlockState)state.setValue(ChestBlock.TYPE, ChestType.SINGLE);
      } else {
         return original.hasProperty(property) && state.hasProperty(property) ? (BlockState)state.setValue(property, original.getValue(property)) : state;
      }
   }
}
