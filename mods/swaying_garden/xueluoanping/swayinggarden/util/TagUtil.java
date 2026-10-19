package xueluoanping.swayinggarden.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class TagUtil {
   public static TagKey<Block> create(String pName) {
      return TagKey.create(Registries.BLOCK, ResourceLocation.parse(pName));
   }
}
