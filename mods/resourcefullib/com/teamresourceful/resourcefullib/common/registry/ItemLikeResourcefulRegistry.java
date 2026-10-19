package com.teamresourceful.resourcefullib.common.registry;

import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

public class ItemLikeResourcefulRegistry<T extends ItemLike> implements ResourcefulRegistry<T> {
   private final ResourcefulRegistry<T> parent;
   private final List<ItemLikeResourcefulRegistry.ItemLikeEntry<T>> entries = new ArrayList<>();

   public ItemLikeResourcefulRegistry(Registry<T> registry, String id) {
      this.parent = ResourcefulRegistries.create(registry, id);
   }

   public <I extends T> ItemLikeResourcefulRegistry.Entry<I> register(String id, Supplier<I> supplier) {
      ItemLikeResourcefulRegistry.Entry<I> entry = new ItemLikeResourcefulRegistry.Entry<>(this.parent.register(id, supplier));
      this.entries.add(entry);
      return entry;
   }

   @Override
   public HolderRegistryEntry<T> registerHolder(String id, Supplier<T> supplier) {
      ItemLikeResourcefulRegistry.HolderEntry<T> entry = new ItemLikeResourcefulRegistry.HolderEntry<>(this.parent.registerHolder(id, supplier));
      this.entries.add(entry);
      return entry;
   }

   @Override
   public Collection<RegistryEntry<T>> getEntries() {
      return ImmutableList.copyOf(this.entries);
   }

   public Collection<ItemLikeResourcefulRegistry.ItemLikeEntry<T>> getItemLikeEntries() {
      return ImmutableList.copyOf(this.entries);
   }

   @Override
   public void init() {
      this.parent.init();
   }

   public record Entry<T extends ItemLike>(RegistryEntry<T> entry) implements ItemLikeResourcefulRegistry.ItemLikeEntry<T> {
      public T get() {
         return this.entry.get();
      }

      @Override
      public ResourceLocation getId() {
         return this.entry.getId();
      }

      @NotNull
      public Item asItem() {
         return this.entry.get().asItem();
      }
   }

   public record HolderEntry<T extends ItemLike>(HolderRegistryEntry<T> entry) implements HolderRegistryEntry<T>, ItemLikeResourcefulRegistry.ItemLikeEntry<T> {
      @Override
      public Holder<T> holder() {
         return this.entry.holder();
      }

      @Override
      public ResourceLocation getId() {
         return this.entry.getId();
      }

      @NotNull
      public Item asItem() {
         return this.entry.get().asItem();
      }
   }

   public interface ItemLikeEntry<T extends ItemLike> extends RegistryEntry<T>, ItemLike {
   }
}
