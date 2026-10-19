package team.lodestar.lodestone.systems.rendering.rendeertype;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.ShaderInstance;

public class ShaderUniformHandler {
   public final ConcurrentHashMap<String, Float[]> uniformChanges = new ConcurrentHashMap<>();
   public final ConcurrentHashMap<String, Integer> samplerChanges = new ConcurrentHashMap<>();
   public static final ShaderUniformHandler DEPTH_FADE = new ShaderUniformHandler().withDepthFade().lock();
   public static final ShaderUniformHandler LUMITRANSPARENT = new ShaderUniformHandler().withLumiTransparency().lock();
   public static final ShaderUniformHandler LUMITRANSPARENT_DEPTH_FADE = new ShaderUniformHandler().withLumiTransparency().withDepthFade().lock();
   private boolean locked;

   public ShaderUniformHandler() {
   }

   public ShaderUniformHandler(@Nullable ShaderUniformHandler original) {
      if (original != null) {
         this.locked = original.locked;

         for (Entry<String, Float[]> entry : original.uniformChanges.entrySet()) {
            this.uniformChanges.put(entry.getKey(), Arrays.copyOf(entry.getValue(), entry.getValue().length));
         }

         this.samplerChanges.putAll(original.samplerChanges);
      }
   }

   public ShaderUniformHandler accept(Consumer<ShaderUniformHandler> modifier) {
      if (this.locked) {
         return this;
      }

      modifier.accept(this);
      return this;
   }

   public ShaderUniformHandler withLumiTransparency() {
      return this.modifyUniform("LumiTransparency", 1.0F);
   }

   public ShaderUniformHandler withDepthFade() {
      return this.modifyUniform("DepthFade", 1.5F);
   }

   public ShaderUniformHandler withDepthFade(float value) {
      return this.modifyUniform("DepthFade", value);
   }

   public ShaderUniformHandler modifyUniform(String uniformName, float... values) {
      if (!this.locked && values != null && values.length != 0) {
         Float[] newValues = new Float[values.length];

         for (int i = 0; i < values.length; i++) {
            newValues[i] = values[i];
         }

         this.uniformChanges.put(uniformName, newValues);
         return this;
      } else {
         return this;
      }
   }

   public ShaderUniformHandler setSamplerTexture(String samplerName, int textureId) {
      if (this.locked) {
         return this;
      }

      this.samplerChanges.put(samplerName, textureId);
      return this;
   }

   public void updateShaderData(ShaderInstance instance) {
      for (Entry<String, Float[]> uniformChange : this.uniformChanges.entrySet()) {
         instance.safeGetUniform(uniformChange.getKey()).set(this.toPrimitive(uniformChange.getValue()));
      }

      for (Entry<String, Integer> samplerChange : this.samplerChanges.entrySet()) {
         instance.setSampler(samplerChange.getKey(), samplerChange.getValue());
      }
   }

   private float[] toPrimitive(Float[] boxed) {
      float[] result = new float[boxed.length];

      for (int i = 0; i < boxed.length; i++) {
         result[i] = boxed[i];
      }

      return result;
   }

   public ShaderUniformHandler lock() {
      this.locked = true;
      return this;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      }

      if (o instanceof ShaderUniformHandler that) {
         if (this.uniformChanges.size() != that.uniformChanges.size()) {
            return false;
         }

         for (Entry<String, Float[]> entry : this.uniformChanges.entrySet()) {
            Float[] otherValues = that.uniformChanges.get(entry.getKey());
            if (!Arrays.equals(entry.getValue(), otherValues)) {
               return false;
            }
         }

         if (this.samplerChanges.size() != that.samplerChanges.size()) {
            return false;
         }

         for (Entry<String, Integer> entry : this.samplerChanges.entrySet()) {
            if (!Objects.equals(entry.getValue(), that.samplerChanges.get(entry.getKey()))) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      int result = 1;
      List<String> uniformKeys = new ArrayList<>(this.uniformChanges.keySet());
      Collections.sort(uniformKeys);

      for (String key : uniformKeys) {
         result = 31 * result + key.hashCode();
         result = 31 * result + Arrays.hashCode(this.uniformChanges.get(key));
      }

      List<String> samplerKeys = new ArrayList<>(this.samplerChanges.keySet());
      Collections.sort(samplerKeys);

      for (String key : samplerKeys) {
         result = 31 * result + key.hashCode();
         result = 31 * result + Objects.hashCode(this.samplerChanges.get(key));
      }

      return result;
   }
}
