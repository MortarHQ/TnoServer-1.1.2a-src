package com.github.L_Ender.cataclysm.init;

import com.github.L_Ender.cataclysm.util.CMByteBufCodecs;
import java.util.Optional;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries.Keys;

public class ModEntityDataSerializers {
   public static final DeferredRegister<EntityDataSerializer<?>> DEF_REG = DeferredRegister.create(Keys.ENTITY_DATA_SERIALIZERS, "cataclysm");
   public static final DeferredHolder<EntityDataSerializer<?>, EntityDataSerializer<Optional<Vec3>>> OPTIONAL_VEC_3 = DEF_REG.register(
      "optional_vec_3", () -> EntityDataSerializer.forValueType(CMByteBufCodecs.VEC3.apply(ByteBufCodecs::optional))
   );
}
