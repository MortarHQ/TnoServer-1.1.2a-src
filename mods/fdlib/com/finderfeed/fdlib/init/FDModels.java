package com.finderfeed.fdlib.init;

import com.finderfeed.fdlib.FDLib;
import com.finderfeed.fdlib.systems.FDRegistries;
import com.finderfeed.fdlib.systems.bedrock.models.FDModelInfo;
import java.util.function.Supplier;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FDModels {
   public static final DeferredRegister<FDModelInfo> MODELS = DeferredRegister.create(FDRegistries.MODELS, "fdlib");
   public static final Supplier<FDModelInfo> TEST = MODELS.register("test", () -> new FDModelInfo(FDLib.location("test"), 1.0F));
   public static final Supplier<FDModelInfo> TEST2 = MODELS.register("test2", () -> new FDModelInfo(FDLib.location("test"), 1.5F));
}
