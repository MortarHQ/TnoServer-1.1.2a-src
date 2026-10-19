package net.unusual.block_factorys_bosses.data.gen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.unusual.block_factorys_bosses.init.BossesRiseBlocks;

public class BossesRiseBlockModelGen extends BlockStateProvider {
   public BossesRiseBlockModelGen(PackOutput output, ExistingFileHelper existingFileHelper) {
      super(output, "block_factorys_bosses", existingFileHelper);
   }

   protected void registerStatesAndModels() {
      this.bigOldPlanks(BossesRiseBlocks.BIG_OAK_PLANKS);
      this.bigOldPlanks(BossesRiseBlocks.BIG_OAK_PLANKS_BROKEN);
      this.bigOldPlanks(BossesRiseBlocks.BIG_OAK_PLANKS_CRACKED);
      this.bigOldPlanks(BossesRiseBlocks.BIG_OAK_PLANKS_STRAIGHT);
      this.bigOldPlanks(BossesRiseBlocks.BIG_OAK_PLANKS_VARIATION);
      this.bigOldPlanks(BossesRiseBlocks.BIG_OAK_PLANKS_WET);
      this.bigOldPlanks(BossesRiseBlocks.BIG_OAK_PLANKS_WET_BROKEN);
      this.bigOldPlanks(BossesRiseBlocks.BIG_OAK_PLANKS_WET_CRACKED);
      this.bigOldPlanks(BossesRiseBlocks.BIG_OAK_PLANKS_WET_STRAIGHT);
      this.bigOldPlanks(BossesRiseBlocks.BIG_OAK_PLANKS_WET_VARIATION);
   }

   private void bigOldPlanks(DeferredBlock<Block> block) {
      ResourceLocation id = block.getId();
      BlockModelBuilder model = (BlockModelBuilder)this.models().cubeAll(id.toString(), id.withPrefix("block/"));
      this.horizontalBlock((Block)block.get(), model);
      this.simpleBlockItem((Block)block.get(), model);
   }
}
