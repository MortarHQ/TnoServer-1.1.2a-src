package com.mafuyu404.oneenoughitem.data;

import com.mafuyu404.oneenoughitem.util.Utils;
import net.minecraft.core.HolderLookup.RegistryLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.Item;

public class ItemReplacementValidator extends BaseReplacementValidator<Item> {
   @Override
   protected boolean isResultExists(String resultId, RegistryLookup<Item> registryLookup) {
      return Utils.getItemById(resultId) != null;
   }

   @Override
   protected ValidationStreams.Accumulator fromDomainObject(String id, ResourceLocation source, RegistryLookup<Item> registryLookup) {
      return Utils.getItemById(id) != null ? ValidationStreams.Accumulator.valid(1) : ValidationStreams.Accumulator.invalid();
   }

   @Override
   protected ValidationStreams.Accumulator fromDomainTag(String tagId, ResourceLocation source, RegistryLookup<Item> registryLookup) {
      return Validators.fromTag(tagId, source, registryLookup);
   }

   @Override
   protected RegistryLookup<Item> getRegistryLookup(MinecraftServer server) {
      return Validators.getItemRegistryLookup(server);
   }
}
