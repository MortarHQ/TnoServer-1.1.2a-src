package noobanidus.mods.lootr.common.api;

import java.util.function.Function;
import net.minecraft.world.level.block.entity.BlockEntityType;
import noobanidus.mods.lootr.common.api.data.blockentity.ILootrBlockEntity;

public interface ILootrBlockEntityConverter<T> extends Function<T, ILootrBlockEntity> {
   ILootrBlockEntity apply(T var1);

   BlockEntityType<?> getBlockEntityType();
}
