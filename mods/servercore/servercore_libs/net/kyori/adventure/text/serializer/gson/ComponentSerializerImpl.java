package servercore_libs.net.kyori.adventure.text.serializer.gson;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import org.jetbrains.annotations.Nullable;
import servercore_libs.net.kyori.adventure.key.Key;
import servercore_libs.net.kyori.adventure.text.BlockNBTComponent;
import servercore_libs.net.kyori.adventure.text.BuildableComponent;
import servercore_libs.net.kyori.adventure.text.Component;
import servercore_libs.net.kyori.adventure.text.ComponentBuilder;
import servercore_libs.net.kyori.adventure.text.EntityNBTComponent;
import servercore_libs.net.kyori.adventure.text.KeybindComponent;
import servercore_libs.net.kyori.adventure.text.NBTComponent;
import servercore_libs.net.kyori.adventure.text.NBTComponentBuilder;
import servercore_libs.net.kyori.adventure.text.ScoreComponent;
import servercore_libs.net.kyori.adventure.text.SelectorComponent;
import servercore_libs.net.kyori.adventure.text.StorageNBTComponent;
import servercore_libs.net.kyori.adventure.text.TextComponent;
import servercore_libs.net.kyori.adventure.text.TranslatableComponent;
import servercore_libs.net.kyori.adventure.text.TranslationArgument;
import servercore_libs.net.kyori.adventure.text.format.Style;
import servercore_libs.net.kyori.adventure.text.serializer.json.JSONOptions;
import servercore_libs.net.kyori.option.OptionState;

final class ComponentSerializerImpl extends TypeAdapter<Component> {
   static final Type COMPONENT_LIST_TYPE = (new TypeToken<List<Component>>() {}).getType();
   static final Type TRANSLATABLE_ARGUMENT_LIST_TYPE = (new TypeToken<List<TranslationArgument>>() {}).getType();
   private final boolean emitCompactTextComponent;
   private final Gson gson;

   static TypeAdapter<Component> create(final OptionState features, final Gson gson) {
      return new ComponentSerializerImpl(features.value(JSONOptions.EMIT_COMPACT_TEXT_COMPONENT), gson).nullSafe();
   }

   private ComponentSerializerImpl(final boolean emitCompactTextComponent, final Gson gson) {
      this.emitCompactTextComponent = emitCompactTextComponent;
      this.gson = gson;
   }

   public BuildableComponent<?, ?> read(final JsonReader in) throws IOException {
      JsonToken token = in.peek();
      if (token != JsonToken.STRING && token != JsonToken.NUMBER && token != JsonToken.BOOLEAN) {
         if (token == JsonToken.BEGIN_ARRAY) {
            ComponentBuilder<?, ?> parent = null;
            in.beginArray();

            while (in.hasNext()) {
               BuildableComponent<?, ?> child = this.read(in);
               if (parent == null) {
                  parent = child.toBuilder();
               } else {
                  parent.append(child);
               }
            }

            if (parent == null) {
               throw notSureHowToDeserialize(in.getPath());
            }

            in.endArray();
            return parent.build();
         } else {
            if (token != JsonToken.BEGIN_OBJECT) {
               throw notSureHowToDeserialize(in.getPath());
            }

            JsonObject style = new JsonObject();
            List<Component> extra = Collections.emptyList();
            String text = null;
            String translate = null;
            String translateFallback = null;
            List<TranslationArgument> translateWith = null;
            String scoreName = null;
            String scoreObjective = null;
            String scoreValue = null;
            String selector = null;
            String keybind = null;
            String nbt = null;
            boolean nbtInterpret = false;
            BlockNBTComponent.Pos nbtBlock = null;
            String nbtEntity = null;
            Key nbtStorage = null;
            Component separator = null;
            in.beginObject();

            while (in.hasNext()) {
               String fieldName = in.nextName();
               if (fieldName.equals("text")) {
                  text = GsonHacks.readString(in);
               } else if (fieldName.equals("translate")) {
                  translate = in.nextString();
               } else if (fieldName.equals("fallback")) {
                  translateFallback = in.nextString();
               } else if (fieldName.equals("with")) {
                  translateWith = (List<TranslationArgument>)this.gson.fromJson(in, TRANSLATABLE_ARGUMENT_LIST_TYPE);
               } else if (fieldName.equals("score")) {
                  in.beginObject();

                  while (in.hasNext()) {
                     String scoreFieldName = in.nextName();
                     if (scoreFieldName.equals("name")) {
                        scoreName = in.nextString();
                     } else if (scoreFieldName.equals("objective")) {
                        scoreObjective = in.nextString();
                     } else if (scoreFieldName.equals("value")) {
                        scoreValue = in.nextString();
                     } else {
                        in.skipValue();
                     }
                  }

                  if (scoreName == null || scoreObjective == null) {
                     throw new JsonParseException("A score component requires a name and objective");
                  }

                  in.endObject();
               } else if (fieldName.equals("selector")) {
                  selector = in.nextString();
               } else if (fieldName.equals("keybind")) {
                  keybind = in.nextString();
               } else if (fieldName.equals("nbt")) {
                  nbt = in.nextString();
               } else if (fieldName.equals("interpret")) {
                  nbtInterpret = in.nextBoolean();
               } else if (fieldName.equals("block")) {
                  nbtBlock = (BlockNBTComponent.Pos)this.gson.fromJson(in, SerializerFactory.BLOCK_NBT_POS_TYPE);
               } else if (fieldName.equals("entity")) {
                  nbtEntity = in.nextString();
               } else if (fieldName.equals("storage")) {
                  nbtStorage = (Key)this.gson.fromJson(in, SerializerFactory.KEY_TYPE);
               } else if (fieldName.equals("extra")) {
                  extra = (List<Component>)this.gson.fromJson(in, COMPONENT_LIST_TYPE);
               } else if (fieldName.equals("separator")) {
                  separator = this.read(in);
               } else {
                  style.add(fieldName, (JsonElement)this.gson.fromJson(in, JsonElement.class));
               }
            }

            ComponentBuilder<?, ?> builder;
            if (text != null) {
               builder = Component.text().content(text);
            } else if (translate != null) {
               if (translateWith != null) {
                  builder = Component.translatable().key(translate).fallback(translateFallback).arguments(translateWith);
               } else {
                  builder = Component.translatable().key(translate).fallback(translateFallback);
               }
            } else if (scoreName != null && scoreObjective != null) {
               if (scoreValue == null) {
                  builder = Component.score().name(scoreName).objective(scoreObjective);
               } else {
                  builder = Component.score().name(scoreName).objective(scoreObjective).value(scoreValue);
               }
            } else if (selector != null) {
               builder = Component.selector().pattern(selector).separator(separator);
            } else if (keybind != null) {
               builder = Component.keybind().keybind(keybind);
            } else {
               if (nbt == null) {
                  throw notSureHowToDeserialize(in.getPath());
               }

               if (nbtBlock != null) {
                  builder = ((BlockNBTComponent.Builder)nbt(Component.blockNBT(), nbt, nbtInterpret, separator)).pos(nbtBlock);
               } else if (nbtEntity != null) {
                  builder = ((EntityNBTComponent.Builder)nbt(Component.entityNBT(), nbt, nbtInterpret, separator)).selector(nbtEntity);
               } else {
                  if (nbtStorage == null) {
                     throw notSureHowToDeserialize(in.getPath());
                  }

                  builder = ((StorageNBTComponent.Builder)nbt(Component.storageNBT(), nbt, nbtInterpret, separator)).storage(nbtStorage);
               }
            }

            builder.style((Style)this.gson.fromJson(style, SerializerFactory.STYLE_TYPE)).append(extra);
            in.endObject();
            return builder.build();
         }
      } else {
         return Component.text(GsonHacks.readString(in));
      }
   }

   private static <C extends NBTComponent<C, B>, B extends NBTComponentBuilder<C, B>> B nbt(
      final B builder, final String nbt, final boolean interpret, @Nullable final Component separator
   ) {
      return builder.nbtPath(nbt).interpret(interpret).separator(separator);
   }

   public void write(final JsonWriter out, final Component value) throws IOException {
      if (value instanceof TextComponent && value.children().isEmpty() && !value.hasStyling() && this.emitCompactTextComponent) {
         out.value(((TextComponent)value).content());
      } else {
         out.beginObject();
         if (value.hasStyling()) {
            JsonElement style = this.gson.toJsonTree(value.style(), SerializerFactory.STYLE_TYPE);
            if (style.isJsonObject()) {
               for (Entry<String, JsonElement> entry : style.getAsJsonObject().entrySet()) {
                  out.name(entry.getKey());
                  this.gson.toJson(entry.getValue(), out);
               }
            }
         }

         if (!value.children().isEmpty()) {
            out.name("extra");
            this.gson.toJson(value.children(), COMPONENT_LIST_TYPE, out);
         }

         if (value instanceof TextComponent) {
            out.name("text");
            out.value(((TextComponent)value).content());
         } else if (value instanceof TranslatableComponent) {
            TranslatableComponent translatable = (TranslatableComponent)value;
            out.name("translate");
            out.value(translatable.key());
            String fallback = translatable.fallback();
            if (fallback != null) {
               out.name("fallback");
               out.value(fallback);
            }

            if (!translatable.arguments().isEmpty()) {
               out.name("with");
               this.gson.toJson(translatable.arguments(), TRANSLATABLE_ARGUMENT_LIST_TYPE, out);
            }
         } else if (value instanceof ScoreComponent) {
            ScoreComponent score = (ScoreComponent)value;
            out.name("score");
            out.beginObject();
            out.name("name");
            out.value(score.name());
            out.name("objective");
            out.value(score.objective());
            if (score.value() != null) {
               out.name("value");
               out.value(score.value());
            }

            out.endObject();
         } else if (value instanceof SelectorComponent) {
            SelectorComponent selector = (SelectorComponent)value;
            out.name("selector");
            out.value(selector.pattern());
            this.serializeSeparator(out, selector.separator());
         } else if (value instanceof KeybindComponent) {
            out.name("keybind");
            out.value(((KeybindComponent)value).keybind());
         } else {
            if (!(value instanceof NBTComponent)) {
               throw notSureHowToSerialize(value);
            }

            NBTComponent<?, ?> nbt = (NBTComponent<?, ?>)value;
            out.name("nbt");
            out.value(nbt.nbtPath());
            out.name("interpret");
            out.value(nbt.interpret());
            this.serializeSeparator(out, nbt.separator());
            if (value instanceof BlockNBTComponent) {
               out.name("block");
               this.gson.toJson(((BlockNBTComponent)value).pos(), SerializerFactory.BLOCK_NBT_POS_TYPE, out);
            } else if (value instanceof EntityNBTComponent) {
               out.name("entity");
               out.value(((EntityNBTComponent)value).selector());
            } else {
               if (!(value instanceof StorageNBTComponent)) {
                  throw notSureHowToSerialize(value);
               }

               out.name("storage");
               this.gson.toJson(((StorageNBTComponent)value).storage(), SerializerFactory.KEY_TYPE, out);
            }
         }

         out.endObject();
      }
   }

   private void serializeSeparator(final JsonWriter out, @Nullable final Component separator) throws IOException {
      if (separator != null) {
         out.name("separator");
         this.write(out, separator);
      }
   }

   static JsonParseException notSureHowToDeserialize(final Object element) {
      return new JsonParseException("Don't know how to turn " + element + " into a Component");
   }

   private static IllegalArgumentException notSureHowToSerialize(final Component component) {
      return new IllegalArgumentException("Don't know how to serialize " + component + " as a Component");
   }
}
