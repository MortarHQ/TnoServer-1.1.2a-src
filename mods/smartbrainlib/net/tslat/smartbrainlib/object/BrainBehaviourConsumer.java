package net.tslat.smartbrainlib.object;

import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.schedule.Activity;
import org.jetbrains.annotations.Nullable;

@FunctionalInterface
public interface BrainBehaviourConsumer {
   void consume(int var1, Activity var2, BehaviorControl<?> var3, @Nullable BehaviorControl<?> var4);
}
