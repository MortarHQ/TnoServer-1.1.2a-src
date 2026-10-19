package team.creative.creativecore.common.util.type.list;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import team.creative.creativecore.common.config.api.CreativeConfig;
import team.creative.creativecore.common.config.premade.registry.RegistryObjectListConfig;
import team.creative.creativecore.common.config.premade.registry.RegistryTagListConfig;

public class SortingBlockList {
   @CreativeConfig
   public RegistryObjectListConfig<Block> blocks = new RegistryObjectListConfig<>(BuiltInRegistries.BLOCK);
   @CreativeConfig
   public RegistryTagListConfig<Block> tags = new RegistryTagListConfig<>(BuiltInRegistries.BLOCK);

   public SortingBlockList add(TagKey<Block> tag) {
      this.tags.add(tag);
      return this;
   }

   public SortingBlockList add(Block block) {
      this.blocks.add(((ResourceKey)block.builtInRegistryHolder().unwrapKey().get()).location());
      return this;
   }

   public boolean is(BlockState state) {
      return this.is(state.getBlock());
   }

   public boolean is(Block block) {
      for (TagKey<Block> tag : this.tags) {
         if (block.builtInRegistryHolder().is(tag)) {
            return true;
         }
      }

      return this.blocks.contains(((ResourceKey)block.builtInRegistryHolder().unwrapKey().get()).location());
   }
}
