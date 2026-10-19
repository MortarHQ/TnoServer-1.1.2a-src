package dev.ftb.mods.ftbquests.quest.translation;

import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Either;
import dev.ftb.mods.ftbquests.quest.ServerQuestFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public class TranslationTable {
   public static final StreamCodec<FriendlyByteBuf, TranslationTable> STREAM_CODEC = StreamCodec.composite(
      ByteBufCodecs.map(
         Maps::newHashMapWithExpectedSize,
         ByteBufCodecs.STRING_UTF8,
         ByteBufCodecs.either(ByteBufCodecs.STRING_UTF8, ByteBufCodecs.STRING_UTF8.apply(ByteBufCodecs.list()))
      ),
      t -> t.map,
      TranslationTable::new
   );
   private final Map<String, Either<String, List<String>>> map;
   private boolean saveNeeded;

   public TranslationTable() {
      this.map = new HashMap<>();
   }

   public static TranslationTable fromNBT(CompoundTag tag) {
      Map<String, Either<String, List<String>>> map = new HashMap<>();
      tag.getAllKeys().forEach(k -> {
         switch (tag.get(k)) {
            case StringTag str:
               map.put(k, Either.left(str.getAsString()));
               break;
            case ListTag list:
               map.put(k, Either.right(list.stream().map(Tag::getAsString).toList()));
               break;
            case null:
            default:
         }
      });
      return new TranslationTable(map);
   }

   private TranslationTable(Map<String, Either<String, List<String>>> map) {
      this.map = map;
   }

   boolean isSaveNeeded() {
      return this.saveNeeded;
   }

   void setSaveNeeded(boolean saveNeeded) {
      this.saveNeeded = saveNeeded;
   }

   public Optional<String> getStringTranslation(String key) {
      return Optional.ofNullable(this.map.get(key)).flatMap(e -> e.left());
   }

   public Optional<List<String>> getStringListTranslation(String key) {
      return Optional.ofNullable(this.map.get(key)).flatMap(e -> e.right());
   }

   public void put(String key, String message) {
      this.map.put(key, Either.left(message));
      this.setSaveNeeded(true);
   }

   public void put(String key, List<String> message) {
      this.map.put(key, Either.right(message));
      this.setSaveNeeded(true);
   }

   public void remove(String key) {
      if (this.map.remove(key) != null) {
         this.setSaveNeeded(true);
      }
   }

   public CompoundTag saveToNBT() {
      return (CompoundTag)Util.make(new CompoundTag(), tag -> this.map.forEach((key, val) -> val.ifLeft(str -> {
         if (!str.isEmpty()) {
            tag.putString(key, str);
         }
      }).ifRight(list -> {
         if (!list.isEmpty()) {
            tag.put(key, listOfStr(list));
         }
      })));
   }

   static ListTag listOfStr(List<String> l) {
      return (ListTag)Util.make(new ListTag(), t -> l.forEach(s -> t.add(StringTag.valueOf(s))));
   }

   public int size() {
      return this.map.size();
   }

   public boolean contains(String key) {
      return this.map.containsKey(key);
   }

   public List<String> findStaleEntries(ServerQuestFile file) {
      List<String> res = new ArrayList<>();
      this.map.keySet().forEach(key -> {
         long id = TranslationManager.getQuestIdFromKey(key);
         if (id != 0L && file.getBase(id) == null) {
            res.add(key);
         }
      });
      return res;
   }
}
