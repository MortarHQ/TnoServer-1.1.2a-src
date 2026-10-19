package com.almostreliable.lootjs.loot.table;

import com.almostreliable.lootjs.core.LootType;
import com.almostreliable.lootjs.core.entry.SimpleLootEntry;
import com.almostreliable.lootjs.loot.LootFunctionList;
import com.almostreliable.lootjs.loot.extension.LootPoolExtension;
import com.almostreliable.lootjs.loot.extension.LootTableExtension;
import com.almostreliable.lootjs.util.DebugInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTable.Builder;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

public class MutableLootTable implements LootEntriesTransformer {
   private final LootTable origin;
   private final ResourceLocation location;
   @Nullable
   private LootFunctionList functions;

   public MutableLootTable(LootTable lootTable) {
      this(lootTable, lootTable.getLootTableId());
   }

   public MutableLootTable(LootTable lootTable, ResourceLocation location) {
      this.location = location;
      this.origin = lootTable;
   }

   public MutableLootTable(LootContextParamSet paramSet, ResourceLocation location) {
      this(new Builder().setParamSet(paramSet).setRandomSequence(location).build(), location);
   }

   public ResourceLocation getRandomSequence() {
      ResourceLocation rs = LootTableExtension.cast(this.origin).lootjs$getRandomSequence();
      return rs == null ? this.getLocation() : rs;
   }

   public void setRandomSequence(@Nullable ResourceLocation randomSequence) {
      LootTableExtension.cast(this.origin).lootjs$setRandomSequence(randomSequence);
   }

   public LootType getLootType() {
      return LootType.getLootType(this.origin.getParamSet());
   }

   public ResourceLocation getLocation() {
      return this.location;
   }

   private List<LootPool> getVanillaPools() {
      return LootTableExtension.cast(this.origin).lootjs$getPools();
   }

   public List<MutableLootPool> getPools() {
      return this.getVanillaPools().stream().map(MutableLootPool::new).toList();
   }

   public MutableLootTable firstPool(Consumer<MutableLootPool> onModifyPool) {
      onModifyPool.accept(this.firstPool());
      return this;
   }

   public MutableLootPool firstPool() {
      List<LootPool> pools = LootTableExtension.cast(this.origin).lootjs$getPools();
      return pools.isEmpty() ? this.createPool() : new MutableLootPool(pools.getFirst());
   }

   public MutableLootTable modifyPool(int index, Consumer<MutableLootPool> onModifyPool) {
      MutableLootPool pool = this.getPool(index);
      if (pool != null) {
         onModifyPool.accept(pool);
      }

      return this;
   }

   @Nullable
   public MutableLootPool getPool(int index) {
      List<LootPool> pools = LootTableExtension.cast(this.origin).lootjs$getPools();
      return index >= 0 && index < pools.size() ? new MutableLootPool(pools.get(index)) : null;
   }

   public MutableLootTable modifyPoolByName(String name, Consumer<MutableLootPool> onModifyPool) {
      MutableLootPool pool = this.getPoolByName(name);
      if (pool != null) {
         onModifyPool.accept(pool);
      }

      return this;
   }

   @Nullable
   public MutableLootPool getPoolByName(String name) {
      for (LootPool pool : LootTableExtension.cast(this.origin).lootjs$getPools()) {
         if (pool.getName() != null && pool.getName().equals(name)) {
            return new MutableLootPool(pool);
         }
      }

      return null;
   }

   public MutableLootTable createPool(Consumer<MutableLootPool> onCreatePool) {
      onCreatePool.accept(this.createPool());
      return this;
   }

   public MutableLootPool createPool() {
      LootPool pool = new LootPool(
         new ArrayList(), new ArrayList(), new ArrayList(), ConstantValue.exactly(1.0F), ConstantValue.exactly(0.0F), Optional.empty()
      );
      this.getVanillaPools().add(pool);
      return new MutableLootPool(pool);
   }

   public LootFunctionList getFunctions() {
      if (this.functions == null) {
         this.functions = LootTableExtension.cast(this.origin).lootjs$createFunctionList();
      }

      return this.functions;
   }

   public MutableLootTable onDrop(PostLootAction postLootAction) {
      if (this.origin instanceof PostLootActionOwner owner) {
         owner.lootjs$setPostLootAction(postLootAction);
      }

      return this;
   }

   public void writeToVanillaTable() {
   }

   public void print() {
      DebugInfo info = new DebugInfo();
      info.add("Loot table: " + this.location);
      info.push();
      info.add("% Pools [");
      info.push();

      for (LootPool pool : this.getVanillaPools()) {
         info.add("{");
         info.push();
         LootPoolExtension.cast(pool).lootjs$collectDebugInfo(info);
         info.pop();
         info.add("}");
      }

      info.pop();
      info.add("]");
      LootFunctionList f = this.functions == null ? LootTableExtension.cast(this.origin).lootjs$createFunctionList() : this.functions;
      f.collectDebugInfo(info);
      info.pop();
      info.release();
   }

   public MutableLootTable clear() {
      this.getFunctions().clear();
      this.getVanillaPools().clear();
      return this;
   }

   public MutableLootTable modifyEntry(UnaryOperator<SimpleLootEntry> onTransform, boolean deepTransform) {
      for (LootPool pool : this.getVanillaPools()) {
         new MutableLootPool(pool).modifyEntry(onTransform, deepTransform);
      }

      return this;
   }

   public MutableLootTable removeEntry(Predicate<SimpleLootEntry> onRemove, boolean deepRemove) {
      for (LootPool pool : this.getVanillaPools()) {
         new MutableLootPool(pool).removeEntry(onRemove, deepRemove);
      }

      return this;
   }

   public MutableLootTable apply(Consumer<LootFunctionList> onModifiers) {
      onModifiers.accept(this.getFunctions());
      return this;
   }
}
