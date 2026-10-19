package net.blay09.mods.balm.common.config;

import java.util.List;
import java.util.Set;
import net.blay09.mods.balm.api.config.schema.BalmConfigSchema;
import net.blay09.mods.balm.api.config.schema.ConfigSchemaBuilder;
import net.blay09.mods.balm.api.config.schema.ConfiguredBoolean;
import net.blay09.mods.balm.api.config.schema.ConfiguredEnum;
import net.blay09.mods.balm.api.config.schema.ConfiguredFloat;
import net.blay09.mods.balm.api.config.schema.ConfiguredInt;
import net.blay09.mods.balm.api.config.schema.ConfiguredList;
import net.blay09.mods.balm.api.config.schema.ConfiguredSet;
import net.blay09.mods.balm.api.config.schema.ConfiguredString;
import net.blay09.mods.balm.api.config.schema.builder.BalmConfigCategoryInitializer;
import net.blay09.mods.balm.api.config.schema.builder.ConfigCategoryBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;

public class ExampleDeclarativeConfig {
   public static final ConfigSchemaBuilder schema = BalmConfigSchema.create(ResourceLocation.fromNamespaceAndPath("balm", "client"));
   public static final ConfiguredBoolean exampleBoolean = schema.property("exampleBoolean").comment("This is an example boolean property").boolOf(true);
   public static final ConfiguredInt exampleInt = schema.property("exampleInt").comment("This is an example int property").intOf(42);
   public static final ConfiguredString exampleString = schema.property("exampleString").comment("This is an example string property").stringOf("Hello World");
   public static final ConfiguredEnum<ExampleDeclarativeConfig.ExampleEnum> exampleEnum = schema.property("exampleEnum")
      .comment("This is an example enum property")
      .enumOf(ExampleDeclarativeConfig.ExampleEnum.Hello);
   public static final ConfiguredList<String> exampleStringList = schema.property("exampleStringList")
      .comment("This is an example string list property")
      .synced()
      .listOf(String.class, List.of("Hello", "World"));
   public static final ConfiguredSet<ResourceLocation> exampleResourceLocationSet = schema.property("exampleResourceLocationSet")
      .comment("This is an example resource location set property")
      .synced()
      .setOf(ResourceLocation.class, Set.of(ResourceLocation.withDefaultNamespace("dirt"), ResourceLocation.withDefaultNamespace("diamond")));
   public static final ConfiguredList<Integer> exampleIntList = schema.property("exampleIntList")
      .comment("This is an example int list property")
      .listOf(Integer.class, List.of(12, 24));
   public static final ConfiguredList<ExampleDeclarativeConfig.ExampleEnum> exampleEnumList = schema.property("exampleEnumList")
      .comment("This is an example enum list property")
      .listOf(ExampleDeclarativeConfig.ExampleEnum.class, List.of(ExampleDeclarativeConfig.ExampleEnum.Hello, ExampleDeclarativeConfig.ExampleEnum.World));
   public static final ExampleDeclarativeConfig.ExampleCategory exampleCategory = schema.category("exampleCategory")
      .comment("This is an example category")
      .via(ExampleDeclarativeConfig.ExampleCategory::new);

   public static class ExampleCategory extends BalmConfigCategoryInitializer {
      public final ConfiguredString innerField = this.category
         .property("innerField")
         .comment("This is an example string inside a category")
         .stringOf("I am inside");
      public final ConfiguredFloat exampleFloat = this.category
         .property("exampleFloat")
         .comment("This is an example float inside a category")
         .synced()
         .floatOf(42.84F);

      public ExampleCategory(ConfigCategoryBuilder category) {
         super(category);
      }
   }

   public enum ExampleEnum implements StringRepresentable {
      Hello,
      World;

      public String getSerializedName() {
         return this.name();
      }
   }
}
