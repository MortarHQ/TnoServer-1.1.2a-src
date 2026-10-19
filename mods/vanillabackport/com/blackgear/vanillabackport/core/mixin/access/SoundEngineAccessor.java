package com.blackgear.vanillabackport.core.mixin.access;

import java.util.Map;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.client.sounds.ChannelAccess.ChannelHandle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(SoundEngine.class)
public interface SoundEngineAccessor {
   @Accessor
   Map<SoundInstance, ChannelHandle> getInstanceToChannel();

   @Accessor
   boolean isLoaded();

   @Invoker
   float callCalculateVolume(SoundInstance var1);
}
