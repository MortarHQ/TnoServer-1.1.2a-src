package com.tom.storagemod.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public record WorldPos(ResourceKey<Level> dim, BlockPos pos) {
   public static final Codec<WorldPos> CODEC = RecordCodecBuilder.mapCodec(
         b -> b.group(
               ResourceKey.codec(Registries.DIMENSION).fieldOf("bound_dim").forGetter(WorldPos::dim),
               BlockPos.CODEC.fieldOf("bounds_pos").forGetter(WorldPos::pos)
            )
            .apply(b, WorldPos::new)
      )
      .codec();

   public BlockEntity getBlockEntity(ServerLevel world) {
      Level dim = world.getServer().getLevel(this.dim);
      return !dim.isLoaded(this.pos) ? null : dim.getBlockEntity(this.pos);
   }
}
