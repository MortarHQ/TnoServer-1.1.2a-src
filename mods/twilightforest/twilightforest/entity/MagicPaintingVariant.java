package twilightforest.entity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.item.ItemStack;
import twilightforest.TFRegistries;
import twilightforest.init.custom.MagicPaintingVariants;

public record MagicPaintingVariant(int width, int height, List<MagicPaintingVariant.Layer> layers, Component author, ResourceLocation backTexture) {
   public static final Codec<MagicPaintingVariant> CODEC = RecordCodecBuilder.create(
      recordCodecBuilder -> recordCodecBuilder.group(
            ExtraCodecs.POSITIVE_INT.fieldOf("width").forGetter(MagicPaintingVariant::width),
            ExtraCodecs.POSITIVE_INT.fieldOf("height").forGetter(MagicPaintingVariant::height),
            ExtraCodecs.nonEmptyList(MagicPaintingVariant.Layer.CODEC.listOf()).fieldOf("layers").forGetter(MagicPaintingVariant::layers),
            ComponentSerialization.CODEC.fieldOf("author").forGetter(MagicPaintingVariant::author),
            ResourceLocation.CODEC.fieldOf("back_texture").forGetter(MagicPaintingVariant::backTexture)
         )
         .apply(recordCodecBuilder, MagicPaintingVariant::new)
   );

   public static Optional<MagicPaintingVariant> getVariant(@Nullable Provider regAccess, String id) {
      return getVariant(regAccess, ResourceLocation.withDefaultNamespace(id));
   }

   public static Optional<MagicPaintingVariant> getVariant(@Nullable Provider regAccess, ResourceLocation id) {
      return getVariant(regAccess, ResourceKey.create(TFRegistries.Keys.MAGIC_PAINTINGS, id));
   }

   public static Optional<MagicPaintingVariant> getVariant(@Nullable Provider regAccess, ResourceKey<MagicPaintingVariant> id) {
      return regAccess == null
         ? Optional.empty()
         : regAccess.asGetterLookup().lookup(TFRegistries.Keys.MAGIC_PAINTINGS).flatMap(reg -> reg.get(id)).map(Reference::value);
   }

   public static String getVariantId(RegistryAccess regAccess, MagicPaintingVariant variant) {
      return getVariantResourceLocation(regAccess, variant).toString();
   }

   public static ResourceLocation getVariantResourceLocation(RegistryAccess regAccess, MagicPaintingVariant variant) {
      return regAccess.registry(TFRegistries.Keys.MAGIC_PAINTINGS).map(reg -> reg.getKey(variant)).orElse(MagicPaintingVariants.DEFAULT.location());
   }

   public record Layer(
      String path,
      @Nullable MagicPaintingVariant.Layer.Parallax parallax,
      @Nullable MagicPaintingVariant.Layer.OpacityModifier opacityModifier,
      boolean fullbright,
      boolean localLighting
   ) {
      public static final Codec<MagicPaintingVariant.Layer> CODEC = RecordCodecBuilder.create(
         recordCodecBuilder -> recordCodecBuilder.group(
               ExtraCodecs.NON_EMPTY_STRING.fieldOf("path").forGetter(MagicPaintingVariant.Layer::path),
               MagicPaintingVariant.Layer.Parallax.CODEC.optionalFieldOf("parallax").forGetter(layer -> Optional.ofNullable(layer.parallax())),
               MagicPaintingVariant.Layer.OpacityModifier.CODEC
                  .optionalFieldOf("opacity_modifier")
                  .forGetter(layer -> Optional.ofNullable(layer.opacityModifier())),
               Codec.BOOL.fieldOf("fullbright").orElse(false).forGetter(MagicPaintingVariant.Layer::fullbright),
               Codec.BOOL.fieldOf("local_lighting").orElse(false).forGetter(MagicPaintingVariant.Layer::localLighting)
            )
            .apply(recordCodecBuilder, MagicPaintingVariant.Layer::create)
      );

      private static MagicPaintingVariant.Layer create(
         String path,
         Optional<MagicPaintingVariant.Layer.Parallax> parallax,
         Optional<MagicPaintingVariant.Layer.OpacityModifier> opacityModifier,
         boolean fullbright,
         boolean localLighting
      ) {
         return new MagicPaintingVariant.Layer(path, parallax.orElse(null), opacityModifier.orElse(null), fullbright, localLighting);
      }

      public record OpacityModifier(
         MagicPaintingVariant.Layer.OpacityModifier.Type type,
         float multiplier,
         boolean invert,
         float min,
         float max,
         float from,
         float to,
         ItemStack item,
         Optional<MobEffectCategory> effectCategory
      ) {
         public static final Codec<MobEffectCategory> MOB_EFFECT_CATEGORY_CODEC = Codec.stringResolver(Enum::toString, MobEffectCategory::valueOf);
         public static final Codec<MagicPaintingVariant.Layer.OpacityModifier> CODEC = RecordCodecBuilder.create(
            recordCodecBuilder -> recordCodecBuilder.group(
                  MagicPaintingVariant.Layer.OpacityModifier.Type.CODEC.fieldOf("type").forGetter(MagicPaintingVariant.Layer.OpacityModifier::type),
                  ExtraCodecs.POSITIVE_FLOAT.fieldOf("multiplier").forGetter(MagicPaintingVariant.Layer.OpacityModifier::multiplier),
                  Codec.BOOL.fieldOf("invert").forGetter(MagicPaintingVariant.Layer.OpacityModifier::invert),
                  Codec.FLOAT.fieldOf("min").forGetter(MagicPaintingVariant.Layer.OpacityModifier::min),
                  ExtraCodecs.POSITIVE_FLOAT.fieldOf("max").forGetter(MagicPaintingVariant.Layer.OpacityModifier::max),
                  Codec.FLOAT.optionalFieldOf("from").forGetter(modifier -> Float.isNaN(modifier.from()) ? Optional.empty() : Optional.of(modifier.from())),
                  Codec.FLOAT.optionalFieldOf("to").forGetter(modifier -> Float.isNaN(modifier.to()) ? Optional.empty() : Optional.of(modifier.to())),
                  ItemStack.CODEC
                     .optionalFieldOf("item_stack")
                     .forGetter(modifier -> modifier.item().isEmpty() ? Optional.empty() : Optional.of(modifier.item())),
                  MOB_EFFECT_CATEGORY_CODEC.optionalFieldOf("effect_category")
                     .forGetter(modifier -> modifier.effectCategory().isEmpty() ? Optional.empty() : modifier.effectCategory())
               )
               .apply(recordCodecBuilder, MagicPaintingVariant.Layer.OpacityModifier::create)
         );

         public OpacityModifier(MagicPaintingVariant.Layer.OpacityModifier.Type type, float multiplier, boolean invert, float min, float max) {
            this(type, multiplier, invert, min, max, Float.NaN, Float.NaN, ItemStack.EMPTY, Optional.empty());
         }

         public OpacityModifier(
            MagicPaintingVariant.Layer.OpacityModifier.Type type, float multiplier, boolean invert, float min, float max, float from, float to
         ) {
            this(type, multiplier, invert, min, max, from, to, ItemStack.EMPTY, Optional.empty());
         }

         public OpacityModifier(MagicPaintingVariant.Layer.OpacityModifier.Type type, float multiplier, boolean invert, float min, float max, ItemStack item) {
            this(type, multiplier, invert, min, max, Float.NaN, Float.NaN, item, Optional.empty());
         }

         public OpacityModifier(
            MagicPaintingVariant.Layer.OpacityModifier.Type type, float multiplier, boolean invert, float min, float max, MobEffectCategory effectCategory
         ) {
            this(type, multiplier, invert, min, max, Float.NaN, Float.NaN, ItemStack.EMPTY, Optional.of(effectCategory));
         }

         private static MagicPaintingVariant.Layer.OpacityModifier create(
            MagicPaintingVariant.Layer.OpacityModifier.Type type,
            float multiplier,
            boolean invert,
            float min,
            float max,
            Optional<Float> from,
            Optional<Float> to,
            Optional<ItemStack> item,
            Optional<MobEffectCategory> effectCategory
         ) {
            if (!type.usesRange() || !from.isEmpty() && !to.isEmpty()) {
               return new MagicPaintingVariant.Layer.OpacityModifier(
                  type, multiplier, invert, min, max, from.orElse(Float.NaN), to.orElse(Float.NaN), item.orElse(ItemStack.EMPTY), effectCategory
               );
            } else {
               throw new NoSuchElementException("Range for opacity modifier is not defined!");
            }
         }

         public enum Type implements StringRepresentable {
            DISTANCE("distance", true, true),
            WEATHER("weather", false, true),
            STORM("storm", false, true),
            LIGHTNING("lightning", false, false),
            DAY_TIME("day_time", true, true),
            SINE_TIME("sine_time", false, false),
            HEALTH("health", true, true),
            HUNGER("hunger", true, true),
            HOLDING_ITEM("holding_item", false, true),
            MOB_EFFECT_CATEGORY("mob_effect_category", false, true);

            static final Codec<MagicPaintingVariant.Layer.OpacityModifier.Type> CODEC = StringRepresentable.fromEnum(
               MagicPaintingVariant.Layer.OpacityModifier.Type::values
            );
            private final String name;
            private final boolean usesRange;
            private final boolean toThePowerOfItsMultiplier;

            Type(String pName, boolean usesRange, boolean toThePowerOfItsMultiplier) {
               this.name = pName;
               this.usesRange = usesRange;
               this.toThePowerOfItsMultiplier = toThePowerOfItsMultiplier;
            }

            public String getSerializedName() {
               return this.name;
            }

            public boolean usesRange() {
               return this.usesRange;
            }

            public boolean powerOfMultiplier() {
               return this.toThePowerOfItsMultiplier;
            }
         }
      }

      public record Parallax(MagicPaintingVariant.Layer.Parallax.Type type, float multiplier, int width, int height) {
         public static final Codec<MagicPaintingVariant.Layer.Parallax> CODEC = RecordCodecBuilder.create(
            recordCodecBuilder -> recordCodecBuilder.group(
                  MagicPaintingVariant.Layer.Parallax.Type.CODEC.fieldOf("type").forGetter(MagicPaintingVariant.Layer.Parallax::type),
                  Codec.FLOAT.fieldOf("multiplier").forGetter(MagicPaintingVariant.Layer.Parallax::multiplier),
                  ExtraCodecs.POSITIVE_INT.fieldOf("width").forGetter(MagicPaintingVariant.Layer.Parallax::width),
                  ExtraCodecs.POSITIVE_INT.fieldOf("height").forGetter(MagicPaintingVariant.Layer.Parallax::height)
               )
               .apply(recordCodecBuilder, MagicPaintingVariant.Layer.Parallax::new)
         );

         public enum Type implements StringRepresentable {
            VIEW_ANGLE("view_angle"),
            LINEAR_TIME("linear_time"),
            SINE_TIME("sine_time");

            static final Codec<MagicPaintingVariant.Layer.Parallax.Type> CODEC = StringRepresentable.fromEnum(MagicPaintingVariant.Layer.Parallax.Type::values);
            private final String name;

            Type(String pName) {
               this.name = pName;
            }

            public String getSerializedName() {
               return this.name;
            }
         }
      }
   }
}
