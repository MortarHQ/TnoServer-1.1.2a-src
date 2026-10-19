package com.sighs.oneenoughblock.data;

import com.mafuyu404.oneenoughitem.data.BaseReplacementValidator;
import com.mafuyu404.oneenoughitem.data.ValidationStreams.Accumulator;
import com.sighs.oneenoughblock.init.Utils;
import java.util.Collection;
import net.minecraft.core.HolderLookup.RegistryLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.block.Block;

public class BlockReplacementValidator extends BaseReplacementValidator<Block> {
   protected boolean isResultExists(String resultId, RegistryLookup<Block> registryLookup) {
      Block b = Utils.getBlockById(resultId);
      return b != null;
   }

   protected Accumulator fromDomainObject(String id, ResourceLocation source, RegistryLookup<Block> registryLookup) {
      return Utils.getBlockById(id) != null ? Accumulator.valid(1) : Accumulator.invalid();
   }

   protected Accumulator fromDomainTag(String tagId, ResourceLocation source, RegistryLookup<Block> registryLookup) {
      try {
         ResourceLocation tag = ResourceLocation.parse(tagId);
         if (Utils.isTagExists(tag, registryLookup)) {
            Collection<Block> objs = Utils.getBlocksOfTag(tag, registryLookup);
            return !objs.isEmpty() ? Accumulator.valid(objs.size()) : Accumulator.invalid();
         } else {
            return Accumulator.deferred();
         }
      } catch (Exception e) {
         return Accumulator.failure("Invalid tag format: " + tagId);
      }
   }

   protected RegistryLookup<Block> getRegistryLookup(MinecraftServer server) {
      return server.registryAccess().lookupOrThrow(Registries.BLOCK);
   }
}
