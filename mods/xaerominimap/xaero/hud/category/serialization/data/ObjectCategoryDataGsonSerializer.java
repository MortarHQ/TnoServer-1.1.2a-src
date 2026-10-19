package xaero.hud.category.serialization.data;

import com.google.gson.Gson;
import javax.annotation.Nonnull;

public final class ObjectCategoryDataGsonSerializer<D extends ObjectCategoryData<D>> extends ObjectCategoryDataSerializer<D, String> {
   private final Gson gson;
   private final Class<D> dataClass;

   private ObjectCategoryDataGsonSerializer(@Nonnull Gson gson, Class<D> dataClass) {
      this.gson = gson;
      this.dataClass = dataClass;
   }

   public String serialize(D data) {
      return this.gson.toJson(data);
   }

   public D deserialize(String serializedData) {
      return (D)this.gson.fromJson(serializedData, this.dataClass);
   }

   public static final class Builder<D extends ObjectCategoryData<D>> extends ObjectCategoryDataSerializer.Builder<D, String> {
      private final Gson gson;
      private final Class<D> dataClass;

      public Builder(Gson gson, Class<D> dataClass) {
         this.gson = gson;
         this.dataClass = dataClass;
      }

      public ObjectCategoryDataGsonSerializer.Builder<D> setDefault() {
         super.setDefault();
         return this;
      }

      public ObjectCategoryDataGsonSerializer<D> build() {
         return new ObjectCategoryDataGsonSerializer<>(this.gson, this.dataClass);
      }

      public static <D extends ObjectCategoryData<D>> ObjectCategoryDataGsonSerializer.Builder<D> begin(Gson gson, Class<D> dataClass) {
         return new ObjectCategoryDataGsonSerializer.Builder<>(gson, dataClass).setDefault();
      }
   }
}
