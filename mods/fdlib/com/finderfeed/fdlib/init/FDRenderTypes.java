package com.finderfeed.fdlib.init;

import com.finderfeed.fdlib.systems.FDRegistries;
import com.finderfeed.fdlib.systems.render_types.FDRenderType;
import com.finderfeed.fdlib.systems.render_types.instances.FDRTEntityCutout;
import com.finderfeed.fdlib.systems.render_types.instances.FDRTEntityCutoutNoCull;
import com.finderfeed.fdlib.systems.render_types.instances.FDRTEntityTranslucent;
import com.finderfeed.fdlib.systems.render_types.instances.FDRTEntityTranslucentCull;
import com.finderfeed.fdlib.systems.render_types.instances.FDRTEntityTranslucentEmissive;
import com.finderfeed.fdlib.systems.render_types.instances.FDRTEyes;
import com.finderfeed.fdlib.systems.render_types.instances.FDRTLightning;
import java.util.function.Supplier;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FDRenderTypes {
   public static final DeferredRegister<FDRenderType> RENDER_TYPES = DeferredRegister.create(FDRegistries.RENDER_TYPE, "fdlib");
   public static final Supplier<FDRTEntityCutout> ENTITY_CUTOUT = RENDER_TYPES.register("entity_cutout", FDRTEntityCutout::new);
   public static final Supplier<FDRTEntityCutoutNoCull> ENTITY_CUTOUT_NO_CULL = RENDER_TYPES.register("entity_cutout_no_cull", FDRTEntityCutoutNoCull::new);
   public static final Supplier<FDRTEntityTranslucent> ENTITY_TRANSLUCENT = RENDER_TYPES.register("entity_translucent", FDRTEntityTranslucent::new);
   public static final Supplier<FDRTEntityTranslucentCull> ENTITY_TRANSLUCENT_CULL = RENDER_TYPES.register(
      "entity_translucent_cull", FDRTEntityTranslucentCull::new
   );
   public static final Supplier<FDRTEntityTranslucentEmissive> ENTITY_TRANSLUCENT_EMISSIVE = RENDER_TYPES.register(
      "entity_translucent_emissive", FDRTEntityTranslucentEmissive::new
   );
   public static final Supplier<FDRTEyes> EYES = RENDER_TYPES.register("eyes", FDRTEyes::new);
   public static final Supplier<FDRTLightning> LIGHTNING = RENDER_TYPES.register("lightning", FDRTLightning::new);
}
