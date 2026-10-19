package net.darkhax.bookshelf.common.api.data.conditions;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.MapCodec;
import java.util.HashMap;
import java.util.Map;
import net.darkhax.bookshelf.common.api.data.codecs.map.MapCodecHelper;
import net.darkhax.bookshelf.common.api.data.codecs.map.MapCodecs;
import net.darkhax.bookshelf.common.impl.Constants;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class LoadConditions {
   private static final Map<ResourceLocation, ConditionType> CONDITION_TYPES = new HashMap<>();
   private static final Codec<ConditionType> CONDITION_TYPE_CODEC = ResourceLocation.CODEC.xmap(CONDITION_TYPES::get, ConditionType::id);
   public static final String LOAD_CONDITION_TAG = Constants.id("load_conditions").toString();
   public static final Codec<ILoadCondition> CONDITION_CODEC = CONDITION_TYPE_CODEC.dispatch(ILoadCondition::getType, ConditionType::codec);
   public static final MapCodecHelper<ILoadCondition> CODEC_HELPER = new MapCodecHelper<>(CONDITION_CODEC);

   @Nullable
   public static ConditionType getType(ResourceLocation id) {
      return CONDITION_TYPES.get(id);
   }

   public static <T extends ILoadCondition> ConditionType register(ResourceLocation id, MapCodec<T> codec) {
      if (CONDITION_TYPES.containsKey(id)) {
         Constants.LOG
            .warn("JSON Load Serializer ID {} has already been assigned to {}. Replacing with {}.", new Object[]{id, CONDITION_TYPES.get(id).codec(), codec});
      }

      ConditionType type = new ConditionType(id, codec);
      CONDITION_TYPES.put(id, type);
      return type;
   }

   public static ILoadCondition[] getConditions(JsonElement conditionData) {
      return (ILoadCondition[])((Pair)MapCodecs.LOAD_CONDITION.getArray().decode(JsonOps.INSTANCE, conditionData).getOrThrow()).getFirst();
   }

   public static boolean canLoad(JsonObject rawJson) {
      if (rawJson.has(LOAD_CONDITION_TAG)) {
         JsonElement conditionData = rawJson.get(LOAD_CONDITION_TAG);

         for (ILoadCondition condition : getConditions(conditionData)) {
            if (!condition.allowLoading()) {
               return false;
            }
         }
      }

      return true;
   }
}
