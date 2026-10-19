package noobanidus.mods.lootr.common.api.processor;

import net.minecraft.world.entity.Entity;

public interface ILootrEntityProcessor {
   non-sealed interface Post extends ILootrProcessor.Post<Entity> {
   }

   non-sealed interface Pre extends ILootrProcessor.Pre<Entity> {
   }
}
