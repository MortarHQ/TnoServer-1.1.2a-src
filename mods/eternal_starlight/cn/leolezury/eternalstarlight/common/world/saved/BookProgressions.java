package cn.leolezury.eternalstarlight.common.world.saved;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedData.Factory;

public class BookProgressions extends SavedData {
   private static final String TAG_PROGRESSIONS = "progressions";
   private final Map<UUID, Set<ResourceLocation>> progressions = new HashMap<>();

   public Map<UUID, Set<ResourceLocation>> getProgressions() {
      return this.progressions;
   }

   public static Factory<BookProgressions> factory(ServerLevel serverLevel) {
      return new Factory(BookProgressions::new, (compoundTag, lookup) -> load(serverLevel, compoundTag), null);
   }

   public static BookProgressions load(ServerLevel serverLevel, CompoundTag compoundTag) {
      BookProgressions progression = new BookProgressions();
      if (compoundTag.contains("progressions")) {
         BookProgressions.ProgressionInstance.LIST_CODEC
            .parse(NbtOps.INSTANCE, compoundTag.get("progressions"))
            .resultOrPartial(s -> EternalStarlight.LOGGER.warn("Failed to parse Book Progressions: {}", s))
            .ifPresent(list -> list.forEach(instance -> progression.getProgressions().put(instance.id(), Sets.newHashSet(instance.unlocked()))));
      }

      return progression;
   }

   public CompoundTag save(CompoundTag compoundTag, Provider provider) {
      List<BookProgressions.ProgressionInstance> progressions = this.getProgressions()
         .entrySet()
         .stream()
         .map(entry -> new BookProgressions.ProgressionInstance(entry.getKey(), Lists.newArrayList(entry.getValue())))
         .toList();
      compoundTag.put("progressions", (Tag)BookProgressions.ProgressionInstance.LIST_CODEC.encodeStart(NbtOps.INSTANCE, progressions).getOrThrow());
      return compoundTag;
   }

   private record ProgressionInstance(UUID id, List<ResourceLocation> unlocked) {
      public static final Codec<BookProgressions.ProgressionInstance> CODEC = RecordCodecBuilder.create(
         instance -> instance.group(
               UUIDUtil.CODEC.fieldOf("id").forGetter(BookProgressions.ProgressionInstance::id),
               ResourceLocation.CODEC.listOf().fieldOf("unlocked").forGetter(BookProgressions.ProgressionInstance::unlocked)
            )
            .apply(instance, BookProgressions.ProgressionInstance::new)
      );
      public static final Codec<List<BookProgressions.ProgressionInstance>> LIST_CODEC = CODEC.listOf();
   }
}
