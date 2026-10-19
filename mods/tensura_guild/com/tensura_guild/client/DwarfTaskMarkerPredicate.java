package com.tensura_guild.client;

import io.github.manasmods.tensura.entity.human.DwarfEntity;

@FunctionalInterface
public interface DwarfTaskMarkerPredicate {
   boolean shouldShowTaskMarker(DwarfEntity var1);
}
