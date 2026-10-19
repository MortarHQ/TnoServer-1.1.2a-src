package net.p3pp3rf1y.sophisticatedcore.upgrades.jukebox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.JukeboxSong;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.Tags.Items;
import net.p3pp3rf1y.sophisticatedcore.api.IDiscHandler;

public class VanillaDiscHandler implements IDiscHandler<Holder<JukeboxSong>> {
   private static List<Item> musicDiscs = null;
   private static Supplier<List<? extends String>> discBlockListGetter = Collections::emptyList;

   @Override
   public Optional<Holder<JukeboxSong>> getSongInfo(ItemStack itemStack, Level level) {
      return JukeboxSong.fromStack(level.registryAccess(), itemStack);
   }

   @Override
   public void playDisc(ServerLevel serverLevel, BlockPos position, UUID storageUuid, ItemStack discItemStack, Runnable onFinished) {
      this.getSongInfo(discItemStack, serverLevel)
         .ifPresent(jukeboxSong -> ServerStorageSoundHandler.startPlayingDisc(serverLevel, position, storageUuid, (Holder<JukeboxSong>)jukeboxSong, onFinished));
   }

   @Override
   public void playDisc(ServerLevel serverLevel, Vec3 position, UUID storageUuid, ItemStack discItemStack, int entityId, Runnable onFinished) {
      this.getSongInfo(discItemStack, serverLevel)
         .ifPresent(
            jukeboxSong -> ServerStorageSoundHandler.startPlayingDisc(
               serverLevel, position, storageUuid, entityId, (Holder<JukeboxSong>)jukeboxSong, onFinished
            )
         );
   }

   @Override
   public Optional<Integer> getMusicLengthInTicks(ItemStack itemStack, Level level) {
      return this.getSongInfo(itemStack, level).map(jukeboxSong -> ((JukeboxSong)jukeboxSong.value()).lengthInTicks());
   }

   @Override
   public boolean supports(ItemStack itemStack) {
      return itemStack.has(DataComponents.JUKEBOX_PLAYABLE);
   }

   @Override
   public Optional<ItemStack> getRandomDisc(RandomSource randomSource) {
      List<Item> discs = this.getMusicDiscs();
      if (!discs.isEmpty()) {
         Item disc = discs.get(randomSource.nextInt(discs.size()));
         return Optional.of(new ItemStack(disc, 1));
      } else {
         return Optional.empty();
      }
   }

   @Override
   public int getMusicDiscSize() {
      return this.getMusicDiscs().size();
   }

   private List<Item> getMusicDiscs() {
      if (musicDiscs == null) {
         BuiltInRegistries.ITEM.getTag(Items.MUSIC_DISCS).ifPresentOrElse(records -> {
            Set<String> blockedDiscs = new HashSet<>(discBlockListGetter.get());
            musicDiscs = new ArrayList<>();
            records.forEach(musicDisc -> {
               if (!blockedDiscs.contains(musicDisc.getKey().location().toString())) {
                  musicDiscs.add((Item)musicDisc.value());
               }
            });
         }, () -> musicDiscs = Collections.emptyList());
      }

      return musicDiscs;
   }

   public static void setDiscBlockListGetter(Supplier<List<? extends String>> getter) {
      discBlockListGetter = getter;
   }
}
