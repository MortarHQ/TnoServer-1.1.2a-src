package com.tom.storagemod;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class StorageTags {
   public static final TagKey<Block> REMOTE_ACTIVATE = BlockTags.create(ResourceLocation.tryBuild("toms_storage", "remote_activate"));
   public static final TagKey<Block> TRIMS = BlockTags.create(ResourceLocation.tryBuild("toms_storage", "trims"));
   public static final TagKey<Block> INV_CONNECTOR_SKIP = BlockTags.create(ResourceLocation.tryBuild("toms_storage", "inventory_connector_skip"));
   public static final TagKey<Block> INV_CONFIG_SKIP = BlockTags.create(ResourceLocation.tryBuild("toms_storage", "inventory_configurator_skip"));
}
