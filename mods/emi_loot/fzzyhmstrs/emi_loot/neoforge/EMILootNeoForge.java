package fzzyhmstrs.emi_loot.neoforge;

import fzzyhmstrs.emi_loot.EMILoot;
import fzzyhmstrs.emi_loot.neoforge.util.BlockRendererImpl;
import fzzyhmstrs.emi_loot.server.condition.BlownUpByCreeperLootCondition;
import fzzyhmstrs.emi_loot.server.condition.KilledByWitherLootCondition;
import fzzyhmstrs.emi_loot.server.condition.MobSpawnedWithLootCondition;
import fzzyhmstrs.emi_loot.server.function.OminousBannerLootFunction;
import fzzyhmstrs.emi_loot.server.function.SetAnyDamageLootFunction;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod("emi_loot")
public class EMILootNeoForge {
   public static final DeferredRegister<LootItemConditionType> LOOT_CONDITION_TYPES = DeferredRegister.create(Registries.LOOT_CONDITION_TYPE, "lootify");
   public static final DeferredRegister<LootItemFunctionType<?>> LOOT_FUNCTION_TYPES = DeferredRegister.create(Registries.LOOT_FUNCTION_TYPE, "lootify");

   public EMILootNeoForge(IEventBus modBus) {
      LOOT_CONDITION_TYPES.register(modBus);
      LOOT_FUNCTION_TYPES.register(modBus);
      if (FMLLoader.getDist().isClient()) {
         NeoForge.EVENT_BUS.addListener(BlockRendererImpl::onClientTick);
      }

      EMILoot.onInitialize();
   }

   static {
      EMILoot.WITHER_KILL = LOOT_CONDITION_TYPES.register("wither_kill", () -> new LootItemConditionType(KilledByWitherLootCondition.CODEC));
      EMILoot.SPAWNS_WITH = LOOT_CONDITION_TYPES.register("spawns_with", () -> new LootItemConditionType(MobSpawnedWithLootCondition.CODEC));
      EMILoot.CREEPER = LOOT_CONDITION_TYPES.register("creeper", () -> new LootItemConditionType(BlownUpByCreeperLootCondition.CODEC));
      EMILoot.SET_ANY_DAMAGE = LOOT_FUNCTION_TYPES.register("set_any_damage", () -> new LootItemFunctionType(SetAnyDamageLootFunction.CODEC));
      EMILoot.OMINOUS_BANNER = LOOT_FUNCTION_TYPES.register("ominous_banner", () -> new LootItemFunctionType(OminousBannerLootFunction.CODEC));
   }
}
