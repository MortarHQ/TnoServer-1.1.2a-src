package net.unusual.block_factorys_bosses.data.gen.tag;

import java.util.concurrent.CompletableFuture;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider.IntrinsicTagAppender;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.unusual.block_factorys_bosses.init.BossesRiseBlocks;
import net.unusual.block_factorys_bosses.init.BossesRiseTags;
import org.jetbrains.annotations.Nullable;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BossesRiseBlockTagGen extends BlockTagsProvider {
   public BossesRiseBlockTagGen(PackOutput output, CompletableFuture<Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
      super(output, lookupProvider, "block_factorys_bosses", existingFileHelper);
   }

   protected void addTags(Provider provider) {
      this.tag(BossesRiseTags.Blocks.UNDERWORLD_ARENA_VALID_SPAWN)
         .add(new Block[]{Blocks.COBBLED_DEEPSLATE, Blocks.DEEPSLATE, Blocks.CYAN_WOOL, Blocks.CYAN_CONCRETE_POWDER, Blocks.GRAY_CONCRETE_POWDER});
      this.tag(BossesRiseTags.Blocks.SHIP_PLANKS)
         .add(
            new Block[]{
               (Block)BossesRiseBlocks.BIG_OAK_PLANKS.get(),
               (Block)BossesRiseBlocks.BIG_OAK_PLANKS_BROKEN.get(),
               (Block)BossesRiseBlocks.BIG_OAK_PLANKS_CRACKED.get(),
               (Block)BossesRiseBlocks.BIG_OAK_PLANKS_STRAIGHT.get(),
               (Block)BossesRiseBlocks.BIG_OAK_PLANKS_VARIATION.get(),
               (Block)BossesRiseBlocks.BIG_OAK_PLANKS_WET.get(),
               (Block)BossesRiseBlocks.BIG_OAK_PLANKS_WET_BROKEN.get(),
               (Block)BossesRiseBlocks.BIG_OAK_PLANKS_WET_CRACKED.get(),
               (Block)BossesRiseBlocks.BIG_OAK_PLANKS_WET_STRAIGHT.get(),
               (Block)BossesRiseBlocks.BIG_OAK_PLANKS_WET_VARIATION.get()
            }
         );
      this.tag(BlockTags.PLANKS).addTag(BossesRiseTags.Blocks.SHIP_PLANKS);
      this.tag(BlockTags.INSIDE_STEP_SOUND_BLOCKS).add(new Block[]{(Block)BossesRiseBlocks.PLANK.get(), (Block)BossesRiseBlocks.COIN_PILE.get()});
      this.tag(BossesRiseTags.Blocks.BONE_REMAINS)
         .add(
            new Block[]{
               (Block)BossesRiseBlocks.BONE_REMAINS_LEGS.get(), (Block)BossesRiseBlocks.BONE_REMAINS_RIB_CAGE.get(), (Block)BossesRiseBlocks.BONE_REMAINS.get()
            }
         );
      addOptional(
         this.tag(BossesRiseTags.Blocks.PROTECTION_EXEMPT),
         "gravestone:gravestone",
         "mogrulgravestones:boulder_gravestone",
         "mogrulgravestones:celtic_gravestone",
         "mogrulgravestones:headstone_gravestone",
         "soulgravestone:gravestone"
      );
      this.tag(BlockTags.REPLACEABLE).add((Block)BossesRiseBlocks.BOSS_SPAWNER.get());
      this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
         .add(
            new Block[]{
               (Block)BossesRiseBlocks.BIG_CHAIN.get(),
               (Block)BossesRiseBlocks.TALL_CANDLES_CROSS.get(),
               (Block)BossesRiseBlocks.UNDER_WORLD_TALL_CANDLE_CROSS.get(),
               (Block)BossesRiseBlocks.TALL_CANDLES.get(),
               (Block)BossesRiseBlocks.UNDERWORLD_TALL_CANDLES.get(),
               (Block)BossesRiseBlocks.CANDLES.get(),
               (Block)BossesRiseBlocks.UNDERWOLD_CANDLES.get(),
               (Block)BossesRiseBlocks.CORPSE.get(),
               (Block)BossesRiseBlocks.TALL_VASE.get(),
               (Block)BossesRiseBlocks.VASE.get(),
               (Block)BossesRiseBlocks.UNDERWORLD_ARENA_DOOR.get()
            }
         )
         .addTags(new TagKey[]{BossesRiseTags.Blocks.BONE_REMAINS});
      this.tag(BlockTags.INCORRECT_FOR_GOLD_TOOL).add((Block)BossesRiseBlocks.UNDERWORLD_ARENA_DOOR.get());
      this.tag(BlockTags.INCORRECT_FOR_IRON_TOOL).add((Block)BossesRiseBlocks.UNDERWORLD_ARENA_DOOR.get());
      this.tag(BlockTags.INCORRECT_FOR_STONE_TOOL).add((Block)BossesRiseBlocks.UNDERWORLD_ARENA_DOOR.get());
      this.tag(BlockTags.INCORRECT_FOR_WOODEN_TOOL).add((Block)BossesRiseBlocks.UNDERWORLD_ARENA_DOOR.get());
   }

   private static void addOptional(IntrinsicTagAppender<Block> tagAppender, String... locations) {
      for (String location : locations) {
         tagAppender.addOptional(ResourceLocation.parse(location));
      }
   }
}
