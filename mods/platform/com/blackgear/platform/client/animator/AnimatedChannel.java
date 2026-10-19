package com.blackgear.platform.client.animator;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public record AnimatedChannel(AnimatedPoint... targets) {
}
