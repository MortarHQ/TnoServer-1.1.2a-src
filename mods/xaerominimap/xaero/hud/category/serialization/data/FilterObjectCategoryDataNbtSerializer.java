package xaero.hud.category.serialization.data;

import java.util.function.Supplier;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import xaero.hud.category.rule.ExcludeListMode;

public class FilterObjectCategoryDataNbtSerializer<D extends FilterObjectCategoryData<D>, DB extends FilterObjectCategoryData.Builder<D, DB>>
   extends ObjectCategoryDataNbtSerializer<D, DB> {
   protected FilterObjectCategoryDataNbtSerializer(Supplier<DB> builderSupplier) {
      super(builderSupplier);
   }

   public CompoundTag serialize(D data) {
      CompoundTag resultTag = super.serialize(data);
      resultTag.putString("h", data.getHardInclude());
      resultTag.putBoolean("i", data.getIncludeListInSuperCategory());
      resultTag.putString("m", data.getExcludeMode().name());
      ListTag includeListTag = new ListTag();
      data.getIncludeListIterator().forEachRemaining(includeEntry -> includeListTag.add(StringTag.valueOf(includeEntry)));
      resultTag.put("l", includeListTag);
      ListTag excludeListTag = new ListTag();
      data.getExcludeListIterator().forEachRemaining(excludeEntry -> excludeListTag.add(StringTag.valueOf(excludeEntry)));
      resultTag.put("e", excludeListTag);
      return resultTag;
   }

   protected DB getConfiguredBuilder(CompoundTag serializedData) {
      DB builder = super.getConfiguredBuilder(serializedData);
      builder.setHardInclude(serializedData.getString("h"));
      builder.setIncludeListInSuperCategory(serializedData.getBoolean("i"));
      builder.setExcludeMode(ExcludeListMode.valueOf(serializedData.getString("m")));

      for (Tag includeEntryTag : serializedData.getList("l", 8)) {
         builder.addToIncludeList(includeEntryTag.getAsString());
      }

      for (Tag excludeEntryTag : serializedData.getList("e", 8)) {
         builder.addToExcludeList(excludeEntryTag.getAsString());
      }

      return builder;
   }

   public abstract static class Builder<D extends FilterObjectCategoryData<D>, DB extends FilterObjectCategoryData.Builder<D, DB>, B extends FilterObjectCategoryDataNbtSerializer.Builder<D, DB, B>>
      extends ObjectCategoryDataNbtSerializer.Builder<D, DB, B> {
      protected Builder() {
      }

      public FilterObjectCategoryDataNbtSerializer<D, DB> build() {
         return (FilterObjectCategoryDataNbtSerializer<D, DB>)super.build();
      }
   }

   public static final class FinalBuilder<D extends FilterObjectCategoryData<D>, DB extends FilterObjectCategoryData.Builder<D, DB>>
      extends FilterObjectCategoryDataNbtSerializer.Builder<D, DB, FilterObjectCategoryDataNbtSerializer.FinalBuilder<D, DB>> {
      private FinalBuilder() {
      }

      protected FilterObjectCategoryDataNbtSerializer<D, DB> buildInternally() {
         return new FilterObjectCategoryDataNbtSerializer<>(this.builderSupplier);
      }

      public static <D extends FilterObjectCategoryData<D>, DB extends FilterObjectCategoryData.Builder<D, DB>> FilterObjectCategoryDataNbtSerializer.FinalBuilder<D, DB> begin() {
         return new FilterObjectCategoryDataNbtSerializer.FinalBuilder().setDefault();
      }
   }
}
