package dev.latvian.mods.kubejs.player;

import dev.latvian.mods.kubejs.core.DataSenderKJS;
import dev.latvian.mods.kubejs.core.MessageSenderKJS;
import dev.latvian.mods.rhino.util.RemapPrefixForJS;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

@RemapPrefixForJS("kjs$")
public class EntityArrayList extends ArrayList<Entity> implements MessageSenderKJS, DataSenderKJS {
   public static final Predicate<Entity> ALWAYS_TRUE_PREDICATE = entity -> true;

   public EntityArrayList(int size) {
      super(size);
   }

   public EntityArrayList(Iterable<? extends Entity> entities) {
      this(entities instanceof Collection c ? c.size() : 4);
      this.addAllIterable(entities);
   }

   @Deprecated(forRemoval = true, since = "7.2")
   public EntityArrayList(Level level, Iterable<? extends Entity> entities) {
      this(entities);
   }

   public void addAllIterable(Iterable<? extends Entity> entities) {
      if (entities instanceof Collection c) {
         this.addAll(c);
      } else {
         for (Entity entity : entities) {
            this.add(entity);
         }
      }
   }

   @Override
   public Component kjs$getName() {
      return Component.literal("EntityList");
   }

   @Override
   public Component kjs$getDisplayName() {
      return Component.literal(this.toString()).kjs$lightPurple();
   }

   @Override
   public void kjs$tell(Component message) {
      for (Entity entity : this) {
         entity.kjs$tell(message);
      }
   }

   @Override
   public void kjs$setStatusMessage(Component message) {
      for (Entity entity : this) {
         entity.kjs$setStatusMessage(message);
      }
   }

   @Override
   public void kjs$runCommand(String command) {
      for (Entity entity : this) {
         entity.kjs$runCommand(command);
      }
   }

   @Override
   public void kjs$runCommandSilent(String command) {
      for (Entity entity : this) {
         entity.kjs$runCommandSilent(command);
      }
   }

   @Override
   public void kjs$setActivePostShader(@Nullable ResourceLocation id) {
      for (Entity entity : this) {
         entity.kjs$setActivePostShader(id);
      }
   }

   public void kill() {
      for (Entity entity : this) {
         entity.kill();
      }
   }

   public void playSound(SoundEvent id, float volume, float pitch) {
      for (Entity entity : this) {
         entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), id, entity.getSoundSource(), volume, pitch);
      }
   }

   public void playSound(SoundEvent id) {
      this.playSound(id, 1.0F, 1.0F);
   }

   public EntityArrayList oneFilter(Predicate<Entity> filter) {
      if (this.isEmpty()) {
         return this;
      }

      EntityArrayList list = new EntityArrayList(this.size() / 4);

      for (Entity entity : this) {
         if (filter.test(entity)) {
            list.add(entity);
            break;
         }
      }

      return list;
   }

   public EntityArrayList filter(List<Predicate<Entity>> filterList) {
      if (!this.isEmpty() && !filterList.isEmpty()) {
         EntityArrayList list = new EntityArrayList(this.size());

         for (Entity entity : this) {
            for (Predicate<Entity> filter : filterList) {
               if (filter.test(entity)) {
                  list.add(entity);
               }
            }
         }

         return list;
      } else {
         return this;
      }
   }

   public EntityArrayList filterSelector(EntitySelector selector) {
      return this.filter(selector.contextFreePredicates);
   }

   public EntityArrayList filterDistance(double x, double y, double z, double distance) {
      EntityArrayList list = new EntityArrayList(this.size());

      for (Entity entity : this) {
         if (entity.distanceToSqr(x, y, z) <= distance * distance) {
            list.add(entity);
         }
      }

      return list;
   }

   public EntityArrayList filterDistance(BlockPos pos, double distance) {
      return this.filterDistance(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, distance);
   }

   public EntityArrayList filterPlayers() {
      return this.oneFilter(e -> e instanceof Player);
   }

   public EntityArrayList filterItems() {
      return this.oneFilter(e -> e instanceof ItemEntity);
   }

   public EntityArrayList filterType(EntityType<?> type) {
      return this.oneFilter(e -> e.getType() == type);
   }

   @Override
   public void kjs$sendData(String channel, @Nullable CompoundTag data) {
      for (Entity entity : this) {
         if (entity instanceof Player player) {
            player.kjs$sendData(channel, data);
         }
      }
   }

   @Nullable
   public Entity getFirst() {
      return this.isEmpty() ? null : this.get(0);
   }
}
