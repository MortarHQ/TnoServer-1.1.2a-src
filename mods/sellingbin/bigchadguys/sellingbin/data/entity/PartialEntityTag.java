package bigchadguys.sellingbin.data.entity;

import bigchadguys.sellingbin.data.nbt.PartialCompoundNbt;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Optional;
import net.minecraft.ResourceLocationException;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.LevelWriter;
import net.minecraft.world.phys.Vec3;

public class PartialEntityTag implements EntityPlacement<PartialEntityTag> {
   private ResourceLocation id;
   private PartialCompoundNbt nbt;

   public PartialEntityTag(ResourceLocation id, PartialCompoundNbt nbt) {
      this.id = id;
      this.nbt = nbt;
   }

   public static PartialEntityTag of(ResourceLocation id, PartialCompoundNbt entity) {
      return new PartialEntityTag(id, entity);
   }

   public boolean isSubsetOf(PartialEntityTag other) {
      return (this.id == null || this.id.equals(other.id)) && this.nbt.isSubsetOf(other.nbt);
   }

   @Override
   public boolean isSubsetOf(Entity entity) {
      return false;
   }

   public void fillInto(PartialEntityTag other) {
      if (this.id != null) {
         other.id = this.id;
      }

      this.nbt.fillInto(other.nbt);
   }

   @Override
   public void place(LevelWriter world) {
   }

   @Override
   public boolean test(Vec3 pos, BlockPos blockPos, PartialCompoundNbt nbt) {
      if (!this.nbt.isSubsetOf(nbt)) {
         return false;
      } else {
         ResourceLocation id = this.nbt.asWhole().filter(tag -> tag.contains("id", 8)).map(tag -> ResourceLocation.tryParse(tag.getString("id"))).orElse(null);
         if (id != null && BuiltInRegistries.ENTITY_TYPE.containsKey(id)) {
            EntityType<?> type = (EntityType<?>)BuiltInRegistries.ENTITY_TYPE.get(id);
            return BuiltInRegistries.ENTITY_TYPE.wrapAsHolder(type).tags().anyMatch(tag -> tag.location().equals(this.id));
         } else {
            return false;
         }
      }
   }

   public PartialEntityTag copy() {
      return new PartialEntityTag(this.id, this.nbt.copy());
   }

   @Override
   public String toString() {
      return (this.id != null ? "#" + this.id : "") + this.nbt.toString();
   }

   public static Optional<PartialEntityTag> parse(String string, boolean logErrors) {
      try {
         return Optional.of(parse(new StringReader(string)));
      } catch (CommandSyntaxException | IllegalArgumentException e) {
         if (logErrors) {
            e.printStackTrace();
         }

         return Optional.empty();
      }
   }

   public static PartialEntityTag parse(String string) throws CommandSyntaxException {
      return parse(new StringReader(string));
   }

   public static PartialEntityTag parse(StringReader reader) throws CommandSyntaxException {
      if (reader.peek() != '#') {
         throw new IllegalArgumentException("Invalid entity tag '" + reader.getString() + "' does not start with #");
      }

      reader.skip();
      int cursor = reader.getCursor();

      while (reader.canRead() && isCharValid(reader.peek())) {
         reader.skip();
      }

      String string = reader.getString().substring(cursor, reader.getCursor());

      try {
         return of(ResourceLocation.parse(string), PartialCompoundNbt.parse(reader));
      } catch (ResourceLocationException e) {
         reader.setCursor(cursor);
         throw new IllegalArgumentException("Invalid tag identifier '" + string + "' in block tag '" + reader.getString() + "'");
      }
   }

   protected static boolean isCharValid(char c) {
      return c >= '0' && c <= '9' || c >= 'a' && c <= 'z' || c == '_' || c == ':' || c == '/' || c == '.' || c == '-';
   }
}
