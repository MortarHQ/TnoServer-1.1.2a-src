package com.almostreliable.lootjs.loot;

import com.almostreliable.lootjs.core.entry.LootEntry;
import com.almostreliable.lootjs.core.filters.ItemFilter;
import com.almostreliable.lootjs.loot.modifier.GroupedLootAction;
import com.almostreliable.lootjs.loot.modifier.LootAction;
import com.almostreliable.lootjs.loot.modifier.handler.AddLootAction;
import com.almostreliable.lootjs.loot.modifier.handler.CustomPlayerAction;
import com.almostreliable.lootjs.loot.modifier.handler.DropExperienceAction;
import com.almostreliable.lootjs.loot.modifier.handler.ExplodeAction;
import com.almostreliable.lootjs.loot.modifier.handler.LightningStrikeAction;
import com.almostreliable.lootjs.loot.modifier.handler.LootPoolAction;
import com.almostreliable.lootjs.loot.modifier.handler.ModifyLootAction;
import com.almostreliable.lootjs.loot.modifier.handler.RemoveLootAction;
import com.almostreliable.lootjs.loot.modifier.handler.ReplaceLootAction;
import com.almostreliable.lootjs.loot.table.MutableLootPool;
import java.util.function.Consumer;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

public interface LootActionContainer<A extends LootActionContainer<?>> {
   default A addLoot(LootEntry... entries) {
      return this.addAction(new AddLootAction(entries));
   }

   default A addAlternativesLoot(LootEntry... entries) {
      return this.addAction(new AddLootAction(LootEntry.alternative(entries)));
   }

   default A addSequenceLoot(LootEntry... entries) {
      return this.addAction(new AddLootAction(LootEntry.sequence(entries)));
   }

   default A removeLoot(ItemFilter filter) {
      return this.addAction(new RemoveLootAction(filter));
   }

   default A replaceLoot(ItemFilter filter, LootEntry lootEntry) {
      return this.replaceLoot(filter, lootEntry, false);
   }

   default A replaceLoot(ItemFilter filter, LootEntry lootEntry, boolean preserveCount) {
      return this.replaceLoot(filter, lootEntry, preserveCount, new DataComponentType[0]);
   }

   default A replaceLoot(ItemFilter filter, LootEntry lootEntry, boolean preserveCount, DataComponentType<?>[] preserveComponentTypes) {
      return this.addAction(new ReplaceLootAction(filter, lootEntry, preserveCount, preserveComponentTypes));
   }

   default A modifyLoot(ItemFilter filter, ModifyLootAction.Callback callback) {
      return this.addAction(new ModifyLootAction(filter, callback));
   }

   default A triggerExplosion(float radius, boolean destroy, boolean fire) {
      BlockInteraction mode = destroy ? BlockInteraction.DESTROY : BlockInteraction.KEEP;
      return this.addAction(new ExplodeAction(radius, mode, fire));
   }

   default A triggerExplosion(float radius, BlockInteraction mode, boolean fire) {
      return this.addAction(new ExplodeAction(radius, mode, fire));
   }

   default A triggerLightningStrike(boolean shouldDamage) {
      return this.addAction(new LightningStrikeAction(shouldDamage));
   }

   default A dropExperience(NumberProvider amount) {
      return this.addAction(new DropExperienceAction(amount));
   }

   default A customAction(LootAction action) {
      return this.addAction(action);
   }

   default A playerAction(Consumer<ServerPlayer> action) {
      return this.addAction(new CustomPlayerAction(action));
   }

   default A group(ItemFilter filter, Consumer<GroupedLootAction.Builder> onCreateGroup) {
      GroupedLootAction.Builder builder = new GroupedLootAction.Builder(filter);
      onCreateGroup.accept(builder);
      return this.addAction(builder.build());
   }

   default A group(Consumer<GroupedLootAction.Builder> onCreateGroup) {
      GroupedLootAction.Builder builder = new GroupedLootAction.Builder();
      onCreateGroup.accept(builder);
      return this.addAction(builder.build());
   }

   default A pool(Consumer<MutableLootPool> onCreatePool) {
      MutableLootPool pool = new MutableLootPool(LootPool.lootPool().build());
      onCreatePool.accept(pool);
      return this.addAction(new LootPoolAction(pool.getVanillaPool()));
   }

   A addAction(LootAction var1);
}
