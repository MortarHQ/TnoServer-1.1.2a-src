package com.blamejared.crafttweaker.mixin.common.access.tag;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagNetworkSerialization;
import net.minecraft.tags.TagNetworkSerialization.NetworkPayload;
import net.minecraft.tags.TagNetworkSerialization.TagOutput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(TagNetworkSerialization.class)
public interface AccessTagNetworkSerialization {
   @Invoker("deserializeTagsFromNetwork")
   static <T> void crafttweaker$$callDeserializeTagsFromNetwork(ResourceKey<? extends Registry<T>> $$0, Registry<T> $$1, NetworkPayload $$2, TagOutput<T> $$3) {
   }
}
