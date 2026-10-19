package dev.ftb.mods.ftbteams.api.property;

import dev.ftb.mods.ftblibrary.icon.Color4I;
import dev.ftb.mods.ftbteams.api.FTBTeamsAPI;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class TeamPropertyType<T> {
   private static final Map<ResourceLocation, TeamPropertyType<?>> MAP = new ConcurrentHashMap<>();
   public static final TeamPropertyType<Boolean> BOOLEAN = register("boolean", BooleanProperty::fromNetwork);
   public static final TeamPropertyType<String> STRING = register("string", StringProperty::fromNetwork);
   public static final TeamPropertyType<List<String>> STRING_LIST = register("string_list", StringListProperty::fromNetwork);
   public static final TeamPropertyType<Set<String>> STRING_SET = register("string_set", StringSetProperty::fromNetwork);
   public static final TeamPropertyType<Integer> INT = register("int", IntProperty::fromNetwork);
   public static final TeamPropertyType<Double> DOUBLE = register("double", DoubleProperty::fromNetwork);
   public static final TeamPropertyType<Color4I> COLOR = register("color", ColorProperty::fromNetwork);
   public static final TeamPropertyType<String> ENUM = register("enum", EnumProperty::fromNetwork);
   public static final TeamPropertyType<PrivacyMode> PRIVACY_MODE = register("privacy_mode", PrivacyProperty::fromNetwork);
   public static final TeamPropertyType<BigInteger> BIG_INTEGER = register("big_integer", BigIntegerProperty::fromNetwork);
   public static final TeamPropertyType<Map<String, Integer>> INT_MAP = register("int_map", StringMapProperty.ToInteger::fromNetwork);
   public static final TeamPropertyType<Map<String, Boolean>> BOOL_MAP = register("bool_map", StringMapProperty.ToBoolean::fromNetwork);
   public static final TeamPropertyType<Map<String, String>> STRING_MAP = register("string_map", StringMapProperty.ToString::fromNetwork);
   private final ResourceLocation id;
   private final TeamPropertyType.FromNet<T> deserializer;

   private TeamPropertyType(ResourceLocation id, TeamPropertyType.FromNet<T> deserializer) {
      this.id = id;
      this.deserializer = deserializer;
   }

   public static TeamProperty<?> read(RegistryFriendlyByteBuf buf) {
      ResourceLocation typeId = buf.readResourceLocation();
      ResourceLocation propId = buf.readResourceLocation();
      boolean playerEditable = buf.readBoolean();
      boolean hidden = buf.readBoolean();
      TeamProperty<?> prop = MAP.get(typeId).deserializer.apply(propId, buf);
      if (!playerEditable) {
         prop = prop.notPlayerEditable();
      }

      if (hidden) {
         prop = prop.hidden();
      }

      return prop;
   }

   public static void write(RegistryFriendlyByteBuf buf, TeamProperty<?> prop) {
      buf.writeResourceLocation(prop.getType().id);
      buf.writeResourceLocation(prop.id);
      buf.writeBoolean(prop.isPlayerEditable());
      buf.writeBoolean(prop.isHidden());
      prop.write(buf);
   }

   private static <Y> TeamPropertyType<Y> register(String id, TeamPropertyType.FromNet<Y> deserializer) {
      return register(FTBTeamsAPI.rl(id), deserializer);
   }

   public static <Y> TeamPropertyType<Y> register(ResourceLocation id, TeamPropertyType.FromNet<Y> deserializer) {
      TeamPropertyType<Y> t = new TeamPropertyType<>(id, deserializer);
      if (MAP.containsKey(id)) {
         throw new IllegalStateException("team property type '" + id + "' is already registered!");
      }

      MAP.put(id, t);
      return t;
   }

   public interface FromNet<Y> {
      TeamProperty<Y> apply(ResourceLocation var1, RegistryFriendlyByteBuf var2);
   }
}
