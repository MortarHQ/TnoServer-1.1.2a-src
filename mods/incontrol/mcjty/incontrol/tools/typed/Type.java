package mcjty.incontrol.tools.typed;

import java.util.List;
import javax.annotation.Nonnull;
import mcjty.incontrol.mob.CNPCMob;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public record Type<V>(@Nonnull Class<V> type) {
   public static final Type<Object> OBJECT = new Type<>(Object.class);
   public static final Type<Integer> INTEGER = create(Integer.class);
   public static final Type<Double> DOUBLE = create(Double.class);
   public static final Type<Float> FLOAT = create(Float.class);
   public static final Type<Long> LONG = create(Long.class);
   public static final Type<String> STRING = create(String.class);
   public static final Type<Boolean> BOOLEAN = create(Boolean.class);
   public static final Type<String> JSON = create(String.class);
   public static final Type<ResourceKey<Level>> DIMENSION_TYPE = create(ResourceKey.class);
   public static final Type<CNPCMob> NPC = create(CNPCMob.class);
   public static final Type<AttributeMap> MAP = create(AttributeMap.class);

   @Nonnull
   public static <V> Type<V> create(@Nonnull Class<? super V> type) {
      return new Type<>((Class<V>)type);
   }

   @Nonnull
   public List<V> convert(@Nonnull List list) {
      return list;
   }

   public V convert(Object o) {
      return (V)o;
   }

   @Override
   public String toString() {
      return "Type(" + this.type().getSimpleName() + ")";
   }
}
