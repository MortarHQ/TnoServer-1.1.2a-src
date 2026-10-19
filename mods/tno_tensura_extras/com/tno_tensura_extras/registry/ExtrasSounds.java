package com.tno_tensura_extras.registry;

import com.tno_tensura_extras.TensuraExtras;
import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ExtrasSounds {
   public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, "tno_tensura_extras");
   public static final ResourceLocation TIME_SPACE_ID = TensuraExtras.id("time_space");
   public static final Supplier<SoundEvent> TIME_SPACE = SOUND_EVENTS.register("time_space", () -> SoundEvent.createVariableRangeEvent(TIME_SPACE_ID));

   private ExtrasSounds() {
   }
}
