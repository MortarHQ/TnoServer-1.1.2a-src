package com.finderfeed.fdlib;

import com.finderfeed.fdlib.init.FDAnims;
import com.finderfeed.fdlib.init.FDBlockEntities;
import com.finderfeed.fdlib.init.FDBlocks;
import com.finderfeed.fdlib.init.FDCommandArgumentTypes;
import com.finderfeed.fdlib.init.FDConfigs;
import com.finderfeed.fdlib.init.FDEDataSerializers;
import com.finderfeed.fdlib.init.FDEntities;
import com.finderfeed.fdlib.init.FDItems;
import com.finderfeed.fdlib.init.FDModelAttachmentTypes;
import com.finderfeed.fdlib.init.FDModels;
import com.finderfeed.fdlib.init.FDParticles;
import com.finderfeed.fdlib.init.FDRenderTypes;
import com.finderfeed.fdlib.init.FDScreenEffects;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod("fdlib")
public class FDLib {
   public static final String MOD_ID = "fdlib";
   public static final Logger LOGGER = LogUtils.getLogger();

   public static ResourceLocation location(String loc) {
      return ResourceLocation.fromNamespaceAndPath("fdlib", loc);
   }

   public FDLib(IEventBus bus, ModContainer modContainer) {
      FDItems.ITEMS.register(bus);
      FDBlocks.BLOCKS.register(bus);
      FDModels.MODELS.register(bus);
      FDConfigs.CONFIGS.register(bus);
      FDAnims.ANIMATIONS.register(bus);
      FDParticles.PARTICLES.register(bus);
      FDEntities.ENTITY_TYPES.register(bus);
      FDRenderTypes.RENDER_TYPES.register(bus);
      FDBlockEntities.BLOCK_ENTITIES.register(bus);
      FDEDataSerializers.SERIALIZERS.register(bus);
      FDScreenEffects.SCREEN_EFFECT_TYPES.register(bus);
      FDCommandArgumentTypes.ARGUMENT_TYPES.register(bus);
      FDModelAttachmentTypes.MODEL_ATTACHMENT_TYPES.register(bus);
   }
}
