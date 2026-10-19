package com.blackgear.vanillabackport.common.registries;

import com.blackgear.platform.core.helper.BlockEntityRegistry;
import com.blackgear.platform.core.helper.BlockEntityTypeBuilder;
import com.blackgear.vanillabackport.common.level.blockentities.CreakingHeartBlockEntity;
import com.blackgear.vanillabackport.common.level.blockentities.PotentSulfurBlockEntity;
import java.util.function.Supplier;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlockEntities {
   public static final BlockEntityRegistry BLOCK_ENTITIES = BlockEntityRegistry.create("minecraft");
   public static final Supplier<BlockEntityType<CreakingHeartBlockEntity>> CREAKING_HEART = BLOCK_ENTITIES.register(
      "creaking_heart", BlockEntityTypeBuilder.create(CreakingHeartBlockEntity::new, new Supplier[]{ModBlocks.CREAKING_HEART})
   );
   public static final Supplier<BlockEntityType<PotentSulfurBlockEntity>> POTENT_SULFUR = BLOCK_ENTITIES.register(
      "potent_sulfur", BlockEntityTypeBuilder.create(PotentSulfurBlockEntity::new, new Supplier[]{ModBlocks.POTENT_SULFUR})
   );
}
