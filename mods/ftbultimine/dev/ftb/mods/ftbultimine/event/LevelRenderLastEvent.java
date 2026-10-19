package dev.ftb.mods.ftbultimine.event;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.architectury.event.Event;
import dev.architectury.event.EventFactory;

public interface LevelRenderLastEvent {
   Event<LevelRenderLastEvent> EVENT = EventFactory.createLoop(new LevelRenderLastEvent[0]);

   void onRenderLast(PoseStack var1);
}
