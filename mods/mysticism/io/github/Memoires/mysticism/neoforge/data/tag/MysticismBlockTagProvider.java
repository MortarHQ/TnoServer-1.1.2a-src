package io.github.Memoires.mysticism.neoforge.data.tag;

import io.github.Memoires.mysticism.registry.block.MysticismBlocks;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags.Blocks;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class MysticismBlockTagProvider extends BlockTagsProvider {
   public MysticismBlockTagProvider(PackOutput output, CompletableFuture<Provider> lookup, ExistingFileHelper helper) {
      super(output, lookup, "mysticism", helper);
   }

   protected void addTags(Provider provider) {
      this.mineableWithPickaxe(MysticismBlocks.ICE_ORE);
      this.needsDiamondTool(MysticismBlocks.ICE_ORE);
      this.tag(Blocks.ORES).add(new Block[]{(Block)MysticismBlocks.ICE_ORE.get()});
   }

   @SafeVarargs
   protected final void mineableWithPickaxe(Supplier<? extends Block>... blocks) {
      this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(Arrays.stream(blocks).map(Supplier::get).toArray(Block[]::new));
   }

   @SafeVarargs
   protected final void needsDiamondTool(Supplier<? extends Block>... blocks) {
      this.tag(BlockTags.NEEDS_DIAMOND_TOOL).add(Arrays.stream(blocks).map(Supplier::get).toArray(Block[]::new));
   }
}
