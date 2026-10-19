package org.crypticdev.stextras.utils;

import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;

public final class EntityTagUtil {
   private EntityTagUtil() {
   }

   public static Optional<EntityTagUtil.TagValue> getTagValue(LivingEntity entity, String key, String... aliases) {
      Objects.requireNonNull(entity, "entity");
      Objects.requireNonNull(key, "key");
      CompoundTag root = new CompoundTag();
      entity.saveWithoutId(root);
      if (root.contains(key)) {
         return Optional.of(read(root, key));
      }

      for (String alt : aliases) {
         if (alt != null && !alt.isEmpty() && root.contains(alt)) {
            return Optional.of(read(root, alt));
         }
      }

      return Optional.empty();
   }

   private static EntityTagUtil.TagValue read(CompoundTag root, String key) {
      if (root.contains(key, 3)) {
         return EntityTagUtil.TagValue.ofInt(key, root.getInt(key));
      } else if (root.contains(key, 4)) {
         return EntityTagUtil.TagValue.ofLong(key, root.getLong(key));
      } else if (root.contains(key, 2)) {
         return EntityTagUtil.TagValue.ofInt(key, root.getShort(key));
      } else if (root.contains(key, 1)) {
         return EntityTagUtil.TagValue.ofInt(key, root.getByte(key));
      } else if (root.contains(key, 5)) {
         return EntityTagUtil.TagValue.ofDouble(key, root.getFloat(key));
      } else if (root.contains(key, 6)) {
         return EntityTagUtil.TagValue.ofDouble(key, root.getDouble(key));
      } else {
         return root.contains(key, 8)
            ? EntityTagUtil.TagValue.ofString(key, root.getString(key))
            : EntityTagUtil.TagValue.ofString(key, String.valueOf(root.get(key)));
      }
   }

   public static final class TagValue {
      private final String key;
      private final EntityTagUtil.TagValue.Kind kind;
      private final int i;
      private final long l;
      private final double d;
      private final String s;

      private TagValue(String key, EntityTagUtil.TagValue.Kind kind, int i, long l, double d, String s) {
         this.key = key;
         this.kind = kind;
         this.i = i;
         this.l = l;
         this.d = d;
         this.s = s;
      }

      public static EntityTagUtil.TagValue ofInt(String key, int v) {
         return new EntityTagUtil.TagValue(key, EntityTagUtil.TagValue.Kind.INT, v, 0L, 0.0, null);
      }

      public static EntityTagUtil.TagValue ofLong(String key, long v) {
         return new EntityTagUtil.TagValue(key, EntityTagUtil.TagValue.Kind.LONG, 0, v, 0.0, null);
      }

      public static EntityTagUtil.TagValue ofDouble(String key, double v) {
         return new EntityTagUtil.TagValue(key, EntityTagUtil.TagValue.Kind.DOUBLE, 0, 0L, v, null);
      }

      public static EntityTagUtil.TagValue ofString(String key, String v) {
         return new EntityTagUtil.TagValue(key, EntityTagUtil.TagValue.Kind.STRING, 0, 0L, 0.0, v);
      }

      public String key() {
         return this.key;
      }

      public EntityTagUtil.TagValue.Kind kind() {
         return this.kind;
      }

      public OptionalInt asInt() {
         return switch (this.kind) {
            case INT -> OptionalInt.of(this.i);
            case LONG -> this.l >= -2147483648L && this.l <= 2147483647L ? OptionalInt.of((int)this.l) : OptionalInt.empty();
            case DOUBLE -> this.d >= -2.1474836E9F && this.d <= 2.147483647E9 ? OptionalInt.of((int)Math.round(this.d)) : OptionalInt.empty();
            case STRING -> parseInt(this.s);
         };
      }

      public OptionalLong asLong() {
         return switch (this.kind) {
            case INT -> OptionalLong.of(this.i);
            case LONG -> OptionalLong.of(this.l);
            case DOUBLE -> OptionalLong.of(Math.round(this.d));
            case STRING -> parseLong(this.s);
         };
      }

      public OptionalDouble asDouble() {
         return switch (this.kind) {
            case INT -> OptionalDouble.of(this.i);
            case LONG -> OptionalDouble.of(this.l);
            case DOUBLE -> OptionalDouble.of(this.d);
            case STRING -> parseDouble(this.s);
         };
      }

      public Optional<String> asString() {
         return switch (this.kind) {
            case INT -> Optional.of(Integer.toString(this.i));
            case LONG -> Optional.of(Long.toString(this.l));
            case DOUBLE -> Optional.of(Double.toString(this.d));
            case STRING -> Optional.ofNullable(this.s);
         };
      }

      public Optional<Boolean> asBoolean() {
         return switch (this.kind) {
            case INT -> Optional.of(this.i != 0);
            case LONG -> Optional.of(this.l != 0L);
            case DOUBLE -> Optional.of(this.d != 0.0);
            case STRING -> parseBool(this.s);
         };
      }

      private static OptionalInt parseInt(String s) {
         try {
            return OptionalInt.of(Integer.parseInt(s.trim()));
         } catch (Exception e) {
            return OptionalInt.empty();
         }
      }

      private static OptionalLong parseLong(String s) {
         try {
            return OptionalLong.of(Long.parseLong(s.trim()));
         } catch (Exception e) {
            return OptionalLong.empty();
         }
      }

      private static OptionalDouble parseDouble(String s) {
         try {
            return OptionalDouble.of(Double.parseDouble(s.trim()));
         } catch (Exception e) {
            return OptionalDouble.empty();
         }
      }

      private static Optional<Boolean> parseBool(String s) {
         String v = s == null ? "" : s.trim().toLowerCase(Locale.ROOT);

         return switch (v) {
            case "true", "1", "yes", "y", "on" -> Optional.of(true);
            case "false", "0", "no", "n", "off" -> Optional.of(false);
            default -> Optional.empty();
         };
      }

      public enum Kind {
         INT,
         LONG,
         DOUBLE,
         STRING;
      }
   }
}
