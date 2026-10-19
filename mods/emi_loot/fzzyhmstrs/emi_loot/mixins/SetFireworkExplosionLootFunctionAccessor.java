package fzzyhmstrs.emi_loot.mixins;

import it.unimi.dsi.fastutil.ints.IntList;
import java.util.Optional;
import net.minecraft.world.item.component.FireworkExplosion;
import net.minecraft.world.item.component.FireworkExplosion.Shape;
import net.minecraft.world.level.storage.loot.functions.SetFireworkExplosionFunction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(SetFireworkExplosionFunction.class)
public interface SetFireworkExplosionLootFunctionAccessor {
   @Accessor
   Optional<Shape> getShape();

   @Accessor
   Optional<IntList> getColors();

   @Accessor
   Optional<IntList> getFadeColors();

   @Accessor
   Optional<Boolean> getTrail();

   @Accessor
   Optional<Boolean> getTwinkle();

   @Invoker
   FireworkExplosion callApply(FireworkExplosion var1);
}
