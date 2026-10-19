package team.creative.itemphysiclite;

import net.minecraft.world.level.block.Blocks;
import team.creative.creativecore.common.config.api.CreativeConfig;
import team.creative.creativecore.common.config.api.CreativeConfig.DecimalRange;
import team.creative.creativecore.common.util.type.list.SortingBlockList;

public class ItemPhysicLiteConfig {
   @CreativeConfig
   @DecimalRange(min = 0.0, max = 10.0)
   public float rotateSpeed = 1.0F;
   @CreativeConfig
   public SortingBlockList blockRequireOffset = new SortingBlockList().add(Blocks.SNOW).add(Blocks.SOUL_SAND).add(Blocks.MUD);
   @CreativeConfig
   public SortingBlockList blockBelowRequireOffset = new SortingBlockList();
}
