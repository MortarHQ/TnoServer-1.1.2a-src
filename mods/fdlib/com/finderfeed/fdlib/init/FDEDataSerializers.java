package com.finderfeed.fdlib.init;

import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class FDEDataSerializers {
   public static final DeferredRegister<EntityDataSerializer<?>> SERIALIZERS = DeferredRegister.create(NeoForgeRegistries.ENTITY_DATA_SERIALIZERS, "fdlib");
   public static final Supplier<EntityDataSerializer<Vec3>> VEC3 = SERIALIZERS.register(
      "vec3",
      () -> new EntityDataSerializer<Vec3>() {
         public static final StreamCodec<FriendlyByteBuf, Vec3> CODEC = StreamCodec.composite(
            ByteBufCodecs.DOUBLE, v -> v.x, ByteBufCodecs.DOUBLE, v -> v.y, ByteBufCodecs.DOUBLE, v -> v.z, Vec3::new
         );

         public StreamCodec<? super RegistryFriendlyByteBuf, Vec3> codec() {
            return CODEC;
         }

         public Vec3 copy(Vec3 vec) {
            return new Vec3(vec.x, vec.y, vec.z);
         }
      }
   );
}
