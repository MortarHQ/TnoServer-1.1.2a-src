package noobanidus.mods.lootr.neoforge.gen;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags.Blocks;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import noobanidus.mods.lootr.common.api.LootrTags;
import noobanidus.mods.lootr.common.api.registry.LootrRegistry;
import org.jetbrains.annotations.Nullable;

public class LootrBlockTagProvider extends BlockTagsProvider {
   public LootrBlockTagProvider(PackOutput output, CompletableFuture<Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
      super(output, lookupProvider, "lootr", existingFileHelper);
   }

   protected void addTags(Provider provider) {
      this.tag(BlockTags.SAND).add(LootrRegistry.getSuspiciousSandBlock());
      this.tag(BlockTags.SHULKER_BOXES).add(LootrRegistry.getShulkerBlock());
      this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(new Block[]{LootrRegistry.getTrophyBlock(), LootrRegistry.getDecoratedPotBlock()});
      this.tag(BlockTags.MINEABLE_WITH_AXE)
         .add(
            new Block[]{LootrRegistry.getChestBlock(), LootrRegistry.getTrappedChestBlock(), LootrRegistry.getBarrelBlock(), LootrRegistry.getInventoryBlock()}
         );
      this.tag(BlockTags.MINEABLE_WITH_SHOVEL).add(new Block[]{LootrRegistry.getSuspiciousGravelBlock(), LootrRegistry.getSuspiciousSandBlock()});
      this.tag(BlockTags.GUARDED_BY_PIGLINS)
         .add(
            new Block[]{LootrRegistry.getChestBlock(), LootrRegistry.getTrappedChestBlock(), LootrRegistry.getBarrelBlock(), LootrRegistry.getInventoryBlock()}
         );
      this.tag(Blocks.CHESTS_WOODEN).add(new Block[]{LootrRegistry.getChestBlock(), LootrRegistry.getInventoryBlock()});
      this.tag(Blocks.CHESTS_TRAPPED).add(LootrRegistry.getTrappedChestBlock());
      this.tag(Blocks.BARRELS).add(LootrRegistry.getBarrelBlock());
      this.tag(LootrTags.Blocks.NON_BLOCKING);
      this.tag(LootrTags.Blocks.BARRELS).add(LootrRegistry.getBarrelBlock());
      this.tag(LootrTags.Blocks.CHESTS).add(new Block[]{LootrRegistry.getChestBlock(), LootrRegistry.getInventoryBlock()});
      this.tag(LootrTags.Blocks.TRAPPED_CHESTS).add(LootrRegistry.getTrappedChestBlock());
      this.tag(LootrTags.Blocks.SHULKERS).add(LootrRegistry.getShulkerBlock());
      this.tag(LootrTags.Blocks.SANDS).add(LootrRegistry.getSuspiciousSandBlock());
      this.tag(LootrTags.Blocks.GRAVELS).add(LootrRegistry.getSuspiciousGravelBlock());
      this.tag(LootrTags.Blocks.POTS).add(LootrRegistry.getDecoratedPotBlock());
      this.tag(LootrTags.Blocks.CONTAINERS)
         .addTags(
            new TagKey[]{
               LootrTags.Blocks.BARRELS,
               LootrTags.Blocks.CHESTS,
               LootrTags.Blocks.TRAPPED_CHESTS,
               LootrTags.Blocks.SHULKERS,
               LootrTags.Blocks.SANDS,
               LootrTags.Blocks.GRAVELS,
               LootrTags.Blocks.POTS
            }
         );
      this.tag(LootrTags.Blocks.CONVERT_BARRELS).add(net.minecraft.world.level.block.Blocks.BARREL).addTag(Blocks.BARRELS);
      this.tag(LootrTags.Blocks.CONVERT_CHESTS).add(net.minecraft.world.level.block.Blocks.CHEST).addTag(Blocks.CHESTS_WOODEN);
      this.tag(LootrTags.Blocks.CONVERT_TRAPPED_CHESTS).add(net.minecraft.world.level.block.Blocks.TRAPPED_CHEST).addTag(Blocks.CHESTS_TRAPPED);
      this.tag(LootrTags.Blocks.CONVERT_SHULKERS).add(net.minecraft.world.level.block.Blocks.SHULKER_BOX);
      this.tag(LootrTags.Blocks.CONVERT_SANDS).add(net.minecraft.world.level.block.Blocks.SUSPICIOUS_SAND);
      this.tag(LootrTags.Blocks.CONVERT_GRAVELS).add(net.minecraft.world.level.block.Blocks.SUSPICIOUS_GRAVEL);
      this.tag(LootrTags.Blocks.CONVERT_POTS).add(net.minecraft.world.level.block.Blocks.DECORATED_POT);
      this.tag(LootrTags.Blocks.CONVERT_BLOCK)
         .addTags(
            new TagKey[]{
               LootrTags.Blocks.CONVERT_BARRELS,
               LootrTags.Blocks.CONVERT_CHESTS,
               LootrTags.Blocks.CONVERT_TRAPPED_CHESTS,
               LootrTags.Blocks.CONVERT_SHULKERS,
               LootrTags.Blocks.CONVERT_SANDS,
               LootrTags.Blocks.CONVERT_GRAVELS,
               LootrTags.Blocks.CONVERT_POTS
            }
         );
      this.tag(LootrTags.Blocks.CONVERT_BLACKLIST);
      this.tag(LootrTags.Blocks.CUSTOM_ELIGIBLE).add(new Block[]{net.minecraft.world.level.block.Blocks.BARREL, net.minecraft.world.level.block.Blocks.CHEST});
      this.tag(LootrTags.Blocks.INTERACT_WHITELIST_BLOCKS)
         .addTags(
            new TagKey[]{
               LootrTags.Blocks.BARRELS,
               LootrTags.Blocks.CHESTS,
               LootrTags.Blocks.TRAPPED_CHESTS,
               LootrTags.Blocks.SHULKERS,
               LootrTags.Blocks.SANDS,
               LootrTags.Blocks.GRAVELS,
               LootrTags.Blocks.POTS
            }
         );
      this.tag(LootrTags.Blocks.INTERACT_WHITELIST).addTag(LootrTags.Blocks.INTERACT_WHITELIST_BLOCKS);
   }

   public String getName() {
      return "Lootr Block Tags";
   }
}
