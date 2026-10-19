package io.github.manasmods.tensura_iaf.neoforge.data;

import com.iafenvoy.iceandfire.registry.IafBlocks;
import io.github.manasmods.tensura.data.TensuraBlockTags;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class IafBlockTagProvider extends BlockTagsProvider {
   public IafBlockTagProvider(PackOutput output, CompletableFuture<Provider> completableFuture, @Nullable ExistingFileHelper existingFileHelper) {
      super(output, completableFuture, "tensura_iaf", existingFileHelper);
   }

   protected void addTags(Provider arg) {
      this.tag(TensuraBlockTags.HEAT_SOURCE_BLOCKS)
         .add(new Block[]{(Block)IafBlocks.DRAGONFORGE_FIRE_CORE.get(), (Block)IafBlocks.DRAGONFORGE_LIGHTNING_CORE.get()});
      this.tag(TensuraBlockTags.TRAP_BLOCKS).add((Block)IafBlocks.DRAGON_ICE_SPIKES.get());
      this.tag(TensuraBlockTags.TREASURE_BLOCKS).add((Block)IafBlocks.GHOST_CHEST.get());
      this.tag(TensuraBlockTags.SKILL_UNBREAKABLE)
         .add(
            new Block[]{
               (Block)IafBlocks.DREAD_SPAWNER.get(),
               (Block)IafBlocks.DREAD_PORTAL.get(),
               (Block)IafBlocks.DREAD_STONE.get(),
               (Block)IafBlocks.DREAD_STONE_BRICKS.get(),
               (Block)IafBlocks.DREAD_STONE_BRICKS_STAIRS.get(),
               (Block)IafBlocks.DREAD_STONE_BRICKS_SLAB.get(),
               (Block)IafBlocks.DREAD_STONE_BRICKS_CHISELED.get(),
               (Block)IafBlocks.DREAD_STONE_BRICKS_CRACKED.get(),
               (Block)IafBlocks.DREAD_STONE_BRICKS_MOSSY.get(),
               (Block)IafBlocks.DREAD_STONE_TILE.get(),
               (Block)IafBlocks.DREAD_STONE_FACE.get(),
               (Block)IafBlocks.DREAD_TORCH_WALL.get(),
               (Block)IafBlocks.DREAD_TORCH.get(),
               (Block)IafBlocks.DREADWOOD_LOG.get(),
               (Block)IafBlocks.DREADWOOD_PLANKS.get(),
               (Block)IafBlocks.DREADWOOD_PLANKS_LOCK.get()
            }
         );
      this.tag(TensuraBlockTags.SKILL_UNOBTAINABLE)
         .add(
            new Block[]{
               (Block)IafBlocks.DREAD_SPAWNER.get(),
               (Block)IafBlocks.DREAD_PORTAL.get(),
               (Block)IafBlocks.DREAD_STONE.get(),
               (Block)IafBlocks.DREAD_STONE_BRICKS.get(),
               (Block)IafBlocks.DREAD_STONE_BRICKS_STAIRS.get(),
               (Block)IafBlocks.DREAD_STONE_BRICKS_SLAB.get(),
               (Block)IafBlocks.DREAD_STONE_BRICKS_CHISELED.get(),
               (Block)IafBlocks.DREAD_STONE_BRICKS_CRACKED.get(),
               (Block)IafBlocks.DREAD_STONE_BRICKS_MOSSY.get(),
               (Block)IafBlocks.DREAD_STONE_TILE.get(),
               (Block)IafBlocks.DREAD_STONE_FACE.get(),
               (Block)IafBlocks.DREAD_TORCH_WALL.get(),
               (Block)IafBlocks.DREAD_TORCH.get(),
               (Block)IafBlocks.DREADWOOD_LOG.get(),
               (Block)IafBlocks.DREADWOOD_PLANKS.get(),
               (Block)IafBlocks.DREADWOOD_PLANKS_LOCK.get()
            }
         );
   }
}
