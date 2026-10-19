package com.finderfeed.fdlib.systems.screen.screen_effect;

@FunctionalInterface
public interface ScreenEffectFactory<D extends ScreenEffectData, T extends ScreenEffect<D>> {
   T create(D var1, int var2, int var3, int var4);
}
