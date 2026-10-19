package com.tensura_guild.init;

import com.tensura_guild.TensuraGuild;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.Structure;

public class GuildStructures {
   public static final ResourceKey<Structure> ADVENTURER_GUILD = registerKey("adventurer_guild");

   private static ResourceKey<Structure> registerKey(String name) {
      return ResourceKey.create(Registries.STRUCTURE, ResourceLocation.fromNamespaceAndPath("tensura_guild", name));
   }

   public static void register() {
      TensuraGuild.LOGGER.info("Registering Structures for {}", "tensura_guild");
   }
}
