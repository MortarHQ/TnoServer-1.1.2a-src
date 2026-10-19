package com.tensura_guild.registry;

import com.tensura_guild.TensuraGuild;
import com.tensura_guild.world.structure.JigsawMinHeightStructure;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class GuildStructureTypes {
   public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES = DeferredRegister.create(Registries.STRUCTURE_TYPE, "tensura_guild");
   public static final DeferredHolder<StructureType<?>, StructureType<JigsawMinHeightStructure>> JIGSAW_MIN_HEIGHT = STRUCTURE_TYPES.register(
      "jigsaw_min_height", () -> () -> JigsawMinHeightStructure.CODEC
   );

   public static void init(IEventBus bus) {
      STRUCTURE_TYPES.register(bus);
      TensuraGuild.LOGGER.info("Structure types registered!");
   }
}
