package xaero.hud.category.serialization.data;

import java.util.function.Supplier;
import net.minecraft.nbt.ByteTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;

public class ObjectCategoryDataNbtSerializer<D extends ObjectCategoryData<D>, DB extends ObjectCategoryData.Builder<D, DB>>
   extends ObjectCategoryDataSerializer<D, CompoundTag> {
   private final Supplier<DB> builderSupplier;

   protected ObjectCategoryDataNbtSerializer(Supplier<DB> builderSupplier) {
      this.builderSupplier = builderSupplier;
   }

   public CompoundTag serialize(D data) {
      CompoundTag resultTag = new CompoundTag();
      resultTag.putString("n", data.getName());
      resultTag.putBoolean("p", data.getProtection());
      CompoundTag settingOverrides = new CompoundTag();
      data.getSettingOverrideIterator().forEachRemaining(entry -> {
         String key = entry.getKey();
         Object value = entry.getValue();
         if (value != null) {
            if (value instanceof Boolean) {
               settingOverrides.putBoolean(key, (Boolean)value);
            } else if (value instanceof Double) {
               settingOverrides.putDouble(key, (Double)value);
            } else if (value instanceof Integer) {
               settingOverrides.putInt(key, (Integer)value);
            } else {
               throw new IllegalArgumentException("Unsupported category setting type: " + value.getClass());
            }
         }
      });
      resultTag.put("v", settingOverrides);
      ListTag subCategoriesTag = new ListTag();
      data.getSubCategoryIterator().forEachRemaining(sub -> {
         CompoundTag subCategoryTag = this.serialize((D)sub);
         subCategoriesTag.add(subCategoryTag);
      });
      resultTag.put("s", subCategoriesTag);
      return resultTag;
   }

   public final D deserialize(CompoundTag serializedData) {
      return this.getConfiguredBuilder(serializedData).build();
   }

   protected DB getConfiguredBuilder(CompoundTag serializedData) {
      DB builder = this.builderSupplier.get();
      builder.setName(serializedData.getString("n"));
      builder.setProtection(serializedData.getBoolean("p"));
      CompoundTag settingOverrides = serializedData.getCompound("v");

      for (String key : settingOverrides.getAllKeys()) {
         Tag valueTag = settingOverrides.get(key);
         if (valueTag instanceof ByteTag) {
            builder.setSettingOverride(key, ((ByteTag)valueTag).getAsByte() == 1);
         } else if (valueTag instanceof DoubleTag) {
            builder.setSettingOverride(key, ((DoubleTag)valueTag).getAsDouble());
         } else {
            if (!(valueTag instanceof IntTag)) {
               throw new IllegalArgumentException("Unsupported category setting NBT tag type: " + valueTag.getClass());
            }

            builder.setSettingOverride(key, ((IntTag)valueTag).getAsInt());
         }
      }

      for (Tag subCategoryTag : serializedData.getList("s", 10)) {
         builder.addSubCategoryBuilder(this.getConfiguredBuilder((CompoundTag)subCategoryTag));
      }

      return builder;
   }

   public abstract static class Builder<D extends ObjectCategoryData<D>, DB extends ObjectCategoryData.Builder<D, DB>, B extends ObjectCategoryDataNbtSerializer.Builder<D, DB, B>> {
      protected final B self = (B)this;
      protected Supplier<DB> builderSupplier;

      protected Builder() {
      }

      public B setDefault() {
         this.setBuilderSupplier(null);
         return this.self;
      }

      public B setBuilderSupplier(Supplier<DB> builderSupplier) {
         this.builderSupplier = builderSupplier;
         return this.self;
      }

      public ObjectCategoryDataNbtSerializer<D, DB> build() {
         if (this.builderSupplier == null) {
            throw new IllegalStateException();
         } else {
            return this.buildInternally();
         }
      }

      protected abstract ObjectCategoryDataNbtSerializer<D, DB> buildInternally();
   }

   public static final class FinalBuilder<D extends ObjectCategoryData<D>, DB extends ObjectCategoryData.Builder<D, DB>>
      extends ObjectCategoryDataNbtSerializer.Builder<D, DB, ObjectCategoryDataNbtSerializer.FinalBuilder<D, DB>> {
      private FinalBuilder() {
      }

      @Override
      protected ObjectCategoryDataNbtSerializer<D, DB> buildInternally() {
         return new ObjectCategoryDataNbtSerializer<>(this.builderSupplier);
      }

      public static <D extends ObjectCategoryData<D>, DB extends ObjectCategoryData.Builder<D, DB>> ObjectCategoryDataNbtSerializer.FinalBuilder<D, DB> begin() {
         return new ObjectCategoryDataNbtSerializer.FinalBuilder().setDefault();
      }
   }
}
