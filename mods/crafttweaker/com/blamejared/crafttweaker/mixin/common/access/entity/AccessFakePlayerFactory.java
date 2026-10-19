package com.blamejared.crafttweaker.mixin.common.access.entity;

import com.mojang.authlib.GameProfile;
import java.util.Map;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.common.util.FakePlayerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = FakePlayerFactory.class, remap = false)
public interface AccessFakePlayerFactory {
   @Accessor(remap = false, value = "fakePlayers")
   static Map<GameProfile, FakePlayer> crafttweaker$getFakePlayers() {
      throw new UnsupportedOperationException();
   }

   @Accessor(remap = false, value = "MINECRAFT")
   static GameProfile crafttweaker$getMINECRAFT() {
      throw new UnsupportedOperationException();
   }
}
