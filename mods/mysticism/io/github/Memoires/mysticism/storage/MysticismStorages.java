package io.github.Memoires.mysticism.storage;

import io.github.Memoires.mysticism.storage.effect.IMysticismEffect;
import io.github.Memoires.mysticism.storage.effect.MysticismEffectStorage;
import io.github.Memoires.mysticism.storage.element.ElementStorage;
import io.github.Memoires.mysticism.storage.element.IElement;
import io.github.Memoires.mysticism.storage.entity.IMysticismEntity;
import io.github.Memoires.mysticism.storage.entity.MysticismEntityStorage;
import io.github.Memoires.mysticism.storage.player.IMysticismPlayer;
import io.github.Memoires.mysticism.storage.player.MysticismPlayerStorage;
import io.github.Memoires.mysticism.storage.soulEnergy.ISoulEnergy;
import io.github.Memoires.mysticism.storage.soulEnergy.SoulEnergyStorage;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class MysticismStorages {
   public static void init() {
      ElementStorage.init();
      MysticismEffectStorage.init();
      MysticismEntityStorage.init();
      MysticismPlayerStorage.init();
      SoulEnergyStorage.init();
   }

   public static IElement getElementFrom(@NotNull LivingEntity entity) {
      if (entity == null) {
         throw new NullPointerException("entity is marked non-null but is null");
      } else {
         return (IElement)entity.manasCore$getStorage(ElementStorage.getKey());
      }
   }

   public static IMysticismPlayer getPlayerDataFrom(@NotNull LivingEntity entity) {
      if (entity == null) {
         throw new NullPointerException("entity is marked non-null but is null");
      } else {
         return (IMysticismPlayer)entity.manasCore$getStorage(MysticismPlayerStorage.getKey());
      }
   }

   public static IMysticismEffect getEffectDataFrom(@NotNull LivingEntity entity) {
      if (entity == null) {
         throw new NullPointerException("entity is marked non-null but is null");
      } else {
         return (IMysticismEffect)entity.manasCore$getStorage(MysticismEffectStorage.getKey());
      }
   }

   public static IMysticismEntity getEntityDataFrom(@NotNull LivingEntity entity) {
      if (entity == null) {
         throw new NullPointerException("entity is marked non-null but is null");
      } else {
         return (IMysticismEntity)entity.manasCore$getStorage(MysticismEntityStorage.getKey());
      }
   }

   public static void resetElement(LivingEntity entity) {
      IElement element = getElementFrom(entity);
      element.setElement(null);
   }

   public static void resetPlayerData(LivingEntity entity) {
      IMysticismPlayer playerData = getPlayerDataFrom(entity);
   }

   public static ISoulEnergy getSoulEnergyFrom(LivingEntity entity) {
      if (entity == null) {
         throw new NullPointerException("entity is marked non-null but is null");
      } else {
         return (ISoulEnergy)entity.manasCore$getStorage(SoulEnergyStorage.getKey());
      }
   }
}
