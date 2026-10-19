package com.benji.netherman;

import com.benji.netherman.common.network.ModMessages;
import com.benji.netherman.config.AzazelConfig;
import com.benji.netherman.init.ModArmorMaterials;
import com.benji.netherman.init.ModBlockEntities;
import com.benji.netherman.init.ModBlocks;
import com.benji.netherman.init.ModCreativeTabs;
import com.benji.netherman.init.ModEffects;
import com.benji.netherman.init.ModEntities;
import com.benji.netherman.init.ModItems;
import com.benji.netherman.init.ModRecipeSerializers;
import com.benji.netherman.init.ModSounds;
import com.benji.netherman.init.ModStructureTypes;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig.Type;
import org.jetbrains.annotations.Contract;
import org.slf4j.Logger;

@Mod("netherman")
public class NetherExp {
   public static final String MODID = "netherman";
   private static final Logger LOGGER = LogUtils.getLogger();

   @Contract("_ -> new")
   public static ResourceLocation location(String path) {
      return path.contains(":") ? ResourceLocation.tryParse(path) : ResourceLocation.fromNamespaceAndPath("netherman", path);
   }

   public NetherExp(IEventBus modEventBus, ModContainer modContainer) {
      modContainer.registerConfig(Type.COMMON, AzazelConfig.SPEC);
      ModBlocks.init(modEventBus);
      ModItems.init(modEventBus);
      ModBlockEntities.init(modEventBus);
      ModEntities.init(modEventBus);
      ModEffects.init(modEventBus);
      ModCreativeTabs.init(modEventBus);
      ModRecipeSerializers.init(modEventBus);
      ModArmorMaterials.init(modEventBus);
      ModStructureTypes.init(modEventBus);
      ModSounds.SOUNDS.register(modEventBus);
      modEventBus.addListener(ModMessages::register);
   }
}
