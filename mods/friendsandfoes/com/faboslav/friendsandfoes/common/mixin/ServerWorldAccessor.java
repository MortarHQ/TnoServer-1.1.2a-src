package com.faboslav.friendsandfoes.common.mixin;

import java.util.List;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.CustomSpawner;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ServerLevel.class)
public interface ServerWorldAccessor {
   @Accessor("customSpawners")
   List<CustomSpawner> getSpawners();

   @Accessor("customSpawners")
   @Final
   @Mutable
   void setSpawners(List<CustomSpawner> var1);
}
