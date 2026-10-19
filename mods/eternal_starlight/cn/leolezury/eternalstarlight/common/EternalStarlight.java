package cn.leolezury.eternalstarlight.common;

import cn.leolezury.eternalstarlight.common.block.flammable.ESFlammabilityRegistry;
import cn.leolezury.eternalstarlight.common.client.helper.ClientHelper;
import cn.leolezury.eternalstarlight.common.client.helper.ClientSideHelper;
import cn.leolezury.eternalstarlight.common.client.helper.EmptyClientHelper;
import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import cn.leolezury.eternalstarlight.common.registry.ESArmorMaterials;
import cn.leolezury.eternalstarlight.common.registry.ESAttributes;
import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESBoarwarfProfessions;
import cn.leolezury.eternalstarlight.common.registry.ESCreativeModeTabs;
import cn.leolezury.eternalstarlight.common.registry.ESCriteriaTriggers;
import cn.leolezury.eternalstarlight.common.registry.ESDataAttachments;
import cn.leolezury.eternalstarlight.common.registry.ESDataComponents;
import cn.leolezury.eternalstarlight.common.registry.ESEnchantmentEffectComponents;
import cn.leolezury.eternalstarlight.common.registry.ESEnchantmentEntityEffects;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESFeatures;
import cn.leolezury.eternalstarlight.common.registry.ESFluids;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.registry.ESMaterialConditions;
import cn.leolezury.eternalstarlight.common.registry.ESMenuTypes;
import cn.leolezury.eternalstarlight.common.registry.ESMobEffects;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import cn.leolezury.eternalstarlight.common.registry.ESPlacementModifierTypes;
import cn.leolezury.eternalstarlight.common.registry.ESPoiTypes;
import cn.leolezury.eternalstarlight.common.registry.ESPotions;
import cn.leolezury.eternalstarlight.common.registry.ESRecipeSerializers;
import cn.leolezury.eternalstarlight.common.registry.ESRecipes;
import cn.leolezury.eternalstarlight.common.registry.ESSensorTypes;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import cn.leolezury.eternalstarlight.common.registry.ESSpells;
import cn.leolezury.eternalstarlight.common.registry.ESStructurePieceTypes;
import cn.leolezury.eternalstarlight.common.registry.ESStructurePlacementTypes;
import cn.leolezury.eternalstarlight.common.registry.ESStructurePoolElementTypes;
import cn.leolezury.eternalstarlight.common.registry.ESStructureTypes;
import cn.leolezury.eternalstarlight.common.registry.ESTreeDecorators;
import cn.leolezury.eternalstarlight.common.registry.ESTreePlacers;
import cn.leolezury.eternalstarlight.common.registry.ESWeathers;
import cn.leolezury.eternalstarlight.common.registry.ESWorldCarvers;
import cn.leolezury.eternalstarlight.common.util.ESMiscUtil;
import com.mojang.logging.LogUtils;
import java.util.concurrent.atomic.AtomicReference;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

public class EternalStarlight {
   public static final String ID = "eternal_starlight";
   public static final Logger LOGGER = LogUtils.getLogger();

   public static void init() {
      ESConfig.load();
      ESFluids.loadClass();
      ESBlocks.loadClass();
      ESPoiTypes.loadClass();
      ESDataComponents.loadClass();
      ESEnchantmentEffectComponents.loadClass();
      ESArmorMaterials.loadClass();
      ESSpells.loadClass();
      ESEntities.loadClass();
      ESPotions.loadClass();
      ESItems.loadClass();
      ESCreativeModeTabs.loadClass();
      ESCriteriaTriggers.loadClass();
      ESAttributes.loadClass();
      ESSensorTypes.loadClass();
      ESMobEffects.loadClass();
      ESBlockEntities.loadClass();
      ESMenuTypes.loadClass();
      ESEnchantmentEntityEffects.loadClass();
      ESMaterialConditions.loadClass();
      ESWorldCarvers.loadClass();
      ESFeatures.loadClass();
      ESPlacementModifierTypes.loadClass();
      ESTreePlacers.loadClass();
      ESTreeDecorators.loadClass();
      ESStructureTypes.loadClass();
      ESStructurePieceTypes.loadClass();
      ESStructurePlacementTypes.loadClass();
      ESStructurePoolElementTypes.loadClass();
      ESParticles.loadClass();
      ESSoundEvents.loadClass();
      ESRecipeSerializers.loadClass();
      ESRecipes.loadClass();
      ESWeathers.loadClass();
      ESBoarwarfProfessions.loadClass();
      ESDataAttachments.loadClass();
      ESRegistries.loadClass();
      ESFlammabilityRegistry.registerDefaults();
   }

   public static ResourceLocation id(String string) {
      return ResourceLocation.fromNamespaceAndPath("eternal_starlight", string);
   }

   public static ClientHelper getClientHelper() {
      AtomicReference<ClientHelper> helper = new AtomicReference<>(new EmptyClientHelper());
      ESMiscUtil.runWhenOnClient(() -> () -> helper.set(new ClientSideHelper()));
      return helper.get();
   }
}
