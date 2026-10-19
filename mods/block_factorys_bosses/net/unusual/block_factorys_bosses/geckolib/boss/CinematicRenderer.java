package net.unusual.block_factorys_bosses.geckolib.boss;

import com.mojang.datafixers.util.Pair;
import net.minecraft.client.player.LocalPlayer;
import software.bernie.geckolib.cache.object.GeoBone;

public interface CinematicRenderer<T> {
   Pair<Float, Float> getCinematicYawAndPitch(T var1, GeoBone var2, LocalPlayer var3);
}
