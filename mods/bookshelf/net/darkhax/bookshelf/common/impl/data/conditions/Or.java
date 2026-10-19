package net.darkhax.bookshelf.common.impl.data.conditions;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.darkhax.bookshelf.common.api.data.conditions.ConditionType;
import net.darkhax.bookshelf.common.api.data.conditions.ILoadCondition;
import net.darkhax.bookshelf.common.api.data.conditions.LoadConditions;
import net.darkhax.bookshelf.common.api.function.CachedSupplier;
import net.darkhax.bookshelf.common.impl.Constants;
import net.minecraft.resources.ResourceLocation;

public class Or implements ILoadCondition {
   public static final ResourceLocation TYPE_ID = Constants.id("or");
   public static final CachedSupplier<ConditionType> TYPE = CachedSupplier.cache(() -> LoadConditions.getType(TYPE_ID));
   public static final MapCodec<Or> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(LoadConditions.CODEC_HELPER.getList("conditions", Or::getConditions)).apply(instance, Or::new)
   );
   private final List<ILoadCondition> conditions;

   private Or(List<ILoadCondition> conditions) {
      this.conditions = conditions;
   }

   public List<ILoadCondition> getConditions() {
      return this.conditions;
   }

   @Override
   public boolean allowLoading() {
      for (ILoadCondition condition : this.conditions) {
         if (condition.allowLoading()) {
            return true;
         }
      }

      return false;
   }

   @Override
   public ConditionType getType() {
      return TYPE.get();
   }
}
