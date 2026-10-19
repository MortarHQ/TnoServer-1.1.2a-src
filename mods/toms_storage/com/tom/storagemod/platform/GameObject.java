package com.tom.storagemod.platform;

import java.util.Arrays;
import java.util.function.Supplier;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.BlockEntitySupplier;
import net.minecraft.world.level.block.entity.BlockEntityType.Builder;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class GameObject<T> {
   private final DeferredHolder<? super T, T> value;

   protected GameObject(DeferredHolder<? super T, T> value) {
      this.value = value;
   }

   public T get() {
      return (T)this.value.get();
   }

   public ResourceLocation getId() {
      return this.value.getId();
   }

   public static class GameObjectBlockEntity<T extends BlockEntity> extends GameObject<BlockEntityType<T>> {
      protected GameObjectBlockEntity(DeferredHolder<BlockEntityType<?>, BlockEntityType<T>> value) {
         super(value);
      }
   }

   public static class GameRegistry<T> {
      protected final DeferredRegister<T> handle;

      public GameRegistry(ResourceKey<? extends Registry<T>> reg) {
         this.handle = DeferredRegister.create(reg, "toms_storage");
      }

      public <I extends T> GameObject<I> register(String name, Supplier<? extends I> sup) {
         return new GameObject<>(this.handle.register(name, sup));
      }

      public void register(IEventBus bus) {
         this.handle.register(bus);
      }
   }

   public static class GameRegistryBE extends GameObject.GameRegistry<BlockEntityType<?>> {
      public GameRegistryBE() {
         super(Registries.BLOCK_ENTITY_TYPE);
      }

      public <BE extends BlockEntity, I extends BlockEntityType<BE>> GameObject.GameObjectBlockEntity<BE> registerBE(
         String name, BlockEntitySupplier<BE> sup, GameObject<? extends Block>... blocks
      ) {
         return new GameObject.GameObjectBlockEntity<>(
            this.handle.register(name, () -> Builder.of(sup, Arrays.stream(blocks).map(GameObject::get).toArray(Block[]::new)).build(null))
         );
      }
   }
}
