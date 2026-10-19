package com.finderfeed.fdlib.init;

import com.finderfeed.fdlib.systems.FDRegistries;
import com.finderfeed.fdlib.systems.screen.screen_effect.ScreenEffectType;
import com.finderfeed.fdlib.systems.screen.screen_effect.instances.ScreenColorEffect;
import com.finderfeed.fdlib.systems.screen.screen_effect.instances.chromatic_abberation.ChromaticAbberationData;
import com.finderfeed.fdlib.systems.screen.screen_effect.instances.chromatic_abberation.ChromaticAbberationScreenEffect;
import com.finderfeed.fdlib.systems.screen.screen_effect.instances.datas.ScreenColorData;
import java.util.function.Supplier;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FDScreenEffects {
   public static final DeferredRegister<ScreenEffectType<?, ?>> SCREEN_EFFECT_TYPES = DeferredRegister.create(FDRegistries.SCREEN_EFFECTS, "fdlib");
   public static final Supplier<ScreenEffectType<ScreenColorData, ScreenColorEffect>> SCREEN_COLOR = SCREEN_EFFECT_TYPES.register(
      "screen_color", () -> ScreenEffectType.create(ScreenColorEffect::new, ScreenColorData.STREAM_CODEC)
   );
   public static final Supplier<ScreenEffectType<ChromaticAbberationData, ChromaticAbberationScreenEffect>> CHROMATIC_ABBERATION = SCREEN_EFFECT_TYPES.register(
      "chromatic_abberation", () -> ScreenEffectType.create(ChromaticAbberationScreenEffect::new, ChromaticAbberationData.STREAM_CODEC)
   );
}
