package com.blackgear.vanillabackport.core.mixin.access;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.NaturalSpawner.SpawnState;
import net.minecraft.world.level.chunk.ChunkAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(SpawnState.class)
public interface SpawnStateAccessor {
   @Invoker("canSpawnForCategory")
   boolean callCanSpawnForCategory(MobCategory var1, ChunkPos var2);

   @Invoker("canSpawn")
   boolean callCanSpawn(EntityType<?> var1, BlockPos var2, ChunkAccess var3);

   @Invoker("afterSpawn")
   void callAfterSpawn(Mob var1, ChunkAccess var2);
}
