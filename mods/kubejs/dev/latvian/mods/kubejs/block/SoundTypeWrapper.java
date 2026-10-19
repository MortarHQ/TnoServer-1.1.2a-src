package dev.latvian.mods.kubejs.block;

import com.google.gson.JsonElement;
import dev.latvian.mods.rhino.Context;
import dev.latvian.mods.rhino.Undefined;
import dev.latvian.mods.rhino.type.TypeInfo;
import dev.latvian.mods.rhino.util.wrap.TypeWrapperFactory;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import net.minecraft.world.level.block.SoundType;

public class SoundTypeWrapper implements TypeWrapperFactory<SoundType> {
   public static final SoundTypeWrapper INSTANCE = new SoundTypeWrapper();
   private Map<String, SoundType> map;

   public Map<String, SoundType> getMap() {
      if (this.map == null) {
         this.map = new LinkedHashMap<>();
         this.map.put("empty", SoundType.EMPTY);

         try {
            for (Field field : SoundType.class.getFields()) {
               if (field.getType() == SoundType.class && Modifier.isPublic(field.getModifiers()) && Modifier.isStatic(field.getModifiers())) {
                  try {
                     this.map.put(field.getName().toLowerCase(Locale.ROOT), (SoundType)field.get(null));
                  } catch (Exception ex) {
                     ex.printStackTrace();
                  }
               }
            }
         } catch (Exception ex) {
            ex.printStackTrace();
         }
      }

      return this.map;
   }

   public SoundType wrap(Context cx, Object o, TypeInfo target) {
      if (o instanceof SoundType t) {
         return t;
      } else {
         return o != null && !Undefined.isUndefined(o)
            ? this.getMap().getOrDefault((o instanceof JsonElement j ? j.getAsString() : o.toString()).toLowerCase(Locale.ROOT), SoundType.EMPTY)
            : SoundType.EMPTY;
      }
   }
}
