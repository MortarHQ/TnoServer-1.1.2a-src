package com.finderfeed.fdlib.init;

import com.finderfeed.fdlib.FDClientConfig;
import com.finderfeed.fdlib.systems.FDRegistries;
import com.finderfeed.fdlib.systems.config.JsonConfig;
import com.finderfeed.fdlib.systems.config.test.TestConfig;
import java.util.function.Supplier;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FDConfigs {
   public static final DeferredRegister<JsonConfig> CONFIGS = DeferredRegister.create(FDRegistries.CONFIGS, "fdlib");
   public static final Supplier<TestConfig> TEST = CONFIGS.register("test", TestConfig::new);
   public static final Supplier<FDClientConfig> CLIENTSIDE_CONFIG = CONFIGS.register("client_config", FDClientConfig::new);
}
