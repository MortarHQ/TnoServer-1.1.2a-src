package com.finderfeed.raids_enhanced.init;

import com.finderfeed.fdlib.systems.FDRegistries;
import com.finderfeed.fdlib.systems.config.JsonConfig;
import com.finderfeed.raids_enhanced.REConfig;
import java.util.function.Supplier;
import net.neoforged.neoforge.registries.DeferredRegister;

public class REConfigs {
   public static final DeferredRegister<JsonConfig> CONFIGS = DeferredRegister.create(FDRegistries.CONFIGS, "raidsenhanced");
   public static final Supplier<REConfig> CONFIG = CONFIGS.register("raidsenhanced", REConfig::new);
}
