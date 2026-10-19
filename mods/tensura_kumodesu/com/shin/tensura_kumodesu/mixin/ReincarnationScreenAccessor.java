package com.shin.tensura_kumodesu.mixin;

import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.tensura.client.screen.ReincarnationScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ReincarnationScreen.class)
public interface ReincarnationScreenAccessor {
   @Invoker("getSelectedRace")
   ManasRace kumodesu$getSelectedRace();
}
