package de.cristelknight.cristellib.builtinpacks;

import java.util.function.Supplier;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackResources;

public record BuiltInPack(PackResources packResource, Component displayName, Supplier<Boolean> supplier) {
}
