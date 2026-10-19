package com.finderfeed.fdlib.init;

import com.finderfeed.fdlib.FDLib;
import com.finderfeed.fdlib.systems.FDRegistries;
import com.finderfeed.fdlib.systems.bedrock.animations.Animation;
import java.util.function.Supplier;
import net.neoforged.neoforge.registries.DeferredRegister;

public class FDAnims {
   public static final DeferredRegister<Animation> ANIMATIONS = DeferredRegister.create(FDRegistries.ANIMATIONS, "fdlib");
   public static final Supplier<Animation> TEST = ANIMATIONS.register("testanim", () -> new Animation(FDLib.location("test")));
}
