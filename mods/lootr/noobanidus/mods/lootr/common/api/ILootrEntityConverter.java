package noobanidus.mods.lootr.common.api;

import java.util.function.Function;
import net.minecraft.world.entity.EntityType;
import noobanidus.mods.lootr.common.api.data.entity.ILootrEntity;

public interface ILootrEntityConverter<T> extends Function<T, ILootrEntity> {
   ILootrEntity apply(T var1);

   EntityType<?> getEntityType();
}
