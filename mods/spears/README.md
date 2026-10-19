# Backported Spears（`spears`）

> jar：`[矛]spearbackport.jar` ｜ 版本：`1.8.0` ｜ 作者：N0t_UN_Owen/NotUnaNancyOwen
> 规模：**35 个类 / 10 个包 / 3276 行** ｜ 事件处理类 2 ｜ Mixin 17 ｜ 注册/管理类 1

**依赖**：`neoforge`、`minecraft`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.notunanancyowen.spears.mixin` | 17 | `LivingEntityMixin`、`ModelPredicateProviderRegistryMixin`、`HeldItemRendererMixin` |
| `com.notunanancyowen.spears.components` | 5 | `KineticWeapon`、`SwingAnimation`、`PiercingWeapon` |
| `com.notunanancyowen.spears` | 3 | `Spears`、`PacketCodecHelper`、`SpearsClient` |
| `com.notunanancyowen.spears.neoforge` | 2 | `SpearsConfig`、`SpearsNeoForge` |
| `com.notunanancyowen.spears.packets` | 2 | `PlayerStabC2SPacket`、`TriggerStabEffectsC2SPacket` |
| `com.notunanancyowen.spears.enchantmentfx` | 2 | `ApplyImpulse`、`ApplyExhaustion` |
| `com.notunanancyowen.spears.goals` | 1 | `UseSpearGoal` |
| `architectury_inject_spears_common_20411d78f03145cfbdacc8cc75117c89_d4598a56931864f670ebfec4f065c283b07b6781ef2f970bd157ee14c603ca87spearscommon180devjar` | 1 | `PlatformMethods` |
| `com.notunanancyowen.spears.dataholders` | 1 | `SpearUser` |
| `com.notunanancyowen.spears.criteria` | 1 | `SpearedMobs` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 35）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `SpearsConfig` | class | — | EventBusSubscriber | 0 | 41 | [SpearsConfig.java](com/notunanancyowen/spears/neoforge/SpearsConfig.java) |
| `SpearsNeoForge` | class | — | Mod | 6 | 275 | [SpearsNeoForge.java](com/notunanancyowen/spears/neoforge/SpearsNeoForge.java) |
| `LivingEntityMixin` | class | SpearUser | Mixin | 16 | 321 | [LivingEntityMixin.java](com/notunanancyowen/spears/mixin/LivingEntityMixin.java) |
| `ModelPredicateProviderRegistryMixin` | class | — | Mixin | 1 | 23 | [ModelPredicateProviderRegistryMixin.java](com/notunanancyowen/spears/mixin/ModelPredicateProviderRegistryMixin.java) |
| `HeldItemRendererMixin` | class | — | Mixin | 5 | 203 | [HeldItemRendererMixin.java](com/notunanancyowen/spears/mixin/HeldItemRendererMixin.java) |
| `ItemRendererMixin` | class | — | Mixin | 1 | 52 | [ItemRendererMixin.java](com/notunanancyowen/spears/mixin/ItemRendererMixin.java) |
| `HeldItemFeatureRendererMixin` | class | — | Mixin | 1 | 92 | [HeldItemFeatureRendererMixin.java](com/notunanancyowen/spears/mixin/HeldItemFeatureRendererMixin.java) |
| `ClientPlayerEntityMixin` | class | Player | Mixin | 3 | 71 | [ClientPlayerEntityMixin.java](com/notunanancyowen/spears/mixin/ClientPlayerEntityMixin.java) |
| `Spears` | class | — | — | 77 | 751 | [Spears.java](com/notunanancyowen/spears/Spears.java) |
| `BipedEntityModelMixin` | class | AgeableListModel | Mixin | 5 | 189 | [BipedEntityModelMixin.java](com/notunanancyowen/spears/mixin/BipedEntityModelMixin.java) |
| `MinecraftClientMixin` | class | — | Mixin | 3 | 74 | [MinecraftClientMixin.java](com/notunanancyowen/spears/mixin/MinecraftClientMixin.java) |
| `PiglinEntityModelMixin` | class | PlayerModel | Mixin | 3 | 59 | [PiglinEntityModelMixin.java](com/notunanancyowen/spears/mixin/PiglinEntityModelMixin.java) |
| `ZombifiedPiglinEntityMixin` | class | — | Mixin | 1 | 20 | [ZombifiedPiglinEntityMixin.java](com/notunanancyowen/spears/mixin/ZombifiedPiglinEntityMixin.java) |
| `ItemStackMixin` | class | — | Mixin | 1 | 28 | [ItemStackMixin.java](com/notunanancyowen/spears/mixin/ItemStackMixin.java) |
| `ZombieEntityMixin` | class | — | Mixin | 1 | 23 | [ZombieEntityMixin.java](com/notunanancyowen/spears/mixin/ZombieEntityMixin.java) |
| `PiglinEntityMixin` | class | — | Mixin | 1 | 20 | [PiglinEntityMixin.java](com/notunanancyowen/spears/mixin/PiglinEntityMixin.java) |
| `EnchantmentEntityEffectMixin` | interface | — | Mixin | 1 | 23 | [EnchantmentEntityEffectMixin.java](com/notunanancyowen/spears/mixin/EnchantmentEntityEffectMixin.java) |
| `ZombieEntityModelMixin` | class | — | Mixin | 1 | 28 | [ZombieEntityModelMixin.java](com/notunanancyowen/spears/mixin/ZombieEntityModelMixin.java) |
| `MobEntityMixin` | class | — | Mixin | 2 | 42 | [MobEntityMixin.java](com/notunanancyowen/spears/mixin/MobEntityMixin.java) |
| `MeleeAttackGoalMixin` | class | — | Mixin | 2 | 52 | [MeleeAttackGoalMixin.java](com/notunanancyowen/spears/mixin/MeleeAttackGoalMixin.java) |
| `PacketCodecHelper` | class | — | — | 3 | 61 | [PacketCodecHelper.java](com/notunanancyowen/spears/PacketCodecHelper.java) |
| `PlayerStabC2SPacket` | record | CustomPacketPayload | — | 2 | 28 | [PlayerStabC2SPacket.java](com/notunanancyowen/spears/packets/PlayerStabC2SPacket.java) |
| `TriggerStabEffectsC2SPacket` | record | CustomPacketPayload | — | 2 | 73 | [TriggerStabEffectsC2SPacket.java](com/notunanancyowen/spears/packets/TriggerStabEffectsC2SPacket.java) |
| `SpearsClient` | class | — | — | 2 | 56 | [SpearsClient.java](com/notunanancyowen/spears/SpearsClient.java) |
| `KineticWeapon` | record | — | — | 9 | 185 | [KineticWeapon.java](com/notunanancyowen/spears/components/KineticWeapon.java) |
| `UseSpearGoal` | class | Goal | — | 5 | 116 | [UseSpearGoal.java](com/notunanancyowen/spears/goals/UseSpearGoal.java) |
| `PlatformMethods` | class | — | — | 1 | 8 | [PlatformMethods.java](architectury_inject_spears_common_20411d78f03145cfbdacc8cc75117c89_d4598a56931864f670ebfec4f065c283b07b6781ef2f970bd157ee14c603ca87spearscommon180devjar/PlatformMethods.java) |
| `ApplyImpulse` | record | EnchantmentEntityEffect | — | 4 | 59 | [ApplyImpulse.java](com/notunanancyowen/spears/enchantmentfx/ApplyImpulse.java) |
| `ApplyExhaustion` | record | EnchantmentEntityEffect | — | 2 | 28 | [ApplyExhaustion.java](com/notunanancyowen/spears/enchantmentfx/ApplyExhaustion.java) |
| `SpearUser` | interface | — | — | 0 | 17 | [SpearUser.java](com/notunanancyowen/spears/dataholders/SpearUser.java) |
| `SpearedMobs` | class | SimpleCriterionTrigger | — | 4 | 42 | [SpearedMobs.java](com/notunanancyowen/spears/criteria/SpearedMobs.java) |
| `SwingAnimation` | record | — | — | 0 | 21 | [SwingAnimation.java](com/notunanancyowen/spears/components/SwingAnimation.java) |
| `PiercingWeapon` | record | — | — | 4 | 144 | [PiercingWeapon.java](com/notunanancyowen/spears/components/PiercingWeapon.java) |
| `UseEffects` | record | — | — | 0 | 29 | [UseEffects.java](com/notunanancyowen/spears/components/UseEffects.java) |
| `AttackRange` | record | — | — | 0 | 22 | [AttackRange.java](com/notunanancyowen/spears/components/AttackRange.java) |

## 事件处理（`@SubscribeEvent`，2 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `SpearsNeoForge` | 2 | `com.notunanancyowen.spears.neoforge` | [SpearsNeoForge.java](com/notunanancyowen/spears/neoforge/SpearsNeoForge.java) |
| `SpearsConfig` | 1 | `com.notunanancyowen.spears.neoforge` | [SpearsConfig.java](com/notunanancyowen/spears/neoforge/SpearsConfig.java) |

## Mixin（17 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `LivingEntityMixin` | `LivingEntity` | [LivingEntityMixin.java](com/notunanancyowen/spears/mixin/LivingEntityMixin.java) |
| `ModelPredicateProviderRegistryMixin` | `ModelPredicateProviderRegistry` | [ModelPredicateProviderRegistryMixin.java](com/notunanancyowen/spears/mixin/ModelPredicateProviderRegistryMixin.java) |
| `HeldItemRendererMixin` | `HeldItemRenderer` | [HeldItemRendererMixin.java](com/notunanancyowen/spears/mixin/HeldItemRendererMixin.java) |
| `ItemRendererMixin` | `ItemRenderer` | [ItemRendererMixin.java](com/notunanancyowen/spears/mixin/ItemRendererMixin.java) |
| `HeldItemFeatureRendererMixin` | `HeldItemFeatureRenderer` | [HeldItemFeatureRendererMixin.java](com/notunanancyowen/spears/mixin/HeldItemFeatureRendererMixin.java) |
| `ClientPlayerEntityMixin` | `ClientPlayerEntity` | [ClientPlayerEntityMixin.java](com/notunanancyowen/spears/mixin/ClientPlayerEntityMixin.java) |
| `BipedEntityModelMixin` | `BipedEntityModel` | [BipedEntityModelMixin.java](com/notunanancyowen/spears/mixin/BipedEntityModelMixin.java) |
| `MinecraftClientMixin` | `MinecraftClient` | [MinecraftClientMixin.java](com/notunanancyowen/spears/mixin/MinecraftClientMixin.java) |
| `PiglinEntityModelMixin` | `PiglinEntityModel` | [PiglinEntityModelMixin.java](com/notunanancyowen/spears/mixin/PiglinEntityModelMixin.java) |
| `ZombifiedPiglinEntityMixin` | `ZombifiedPiglinEntity` | [ZombifiedPiglinEntityMixin.java](com/notunanancyowen/spears/mixin/ZombifiedPiglinEntityMixin.java) |
| `ItemStackMixin` | `ItemStack` | [ItemStackMixin.java](com/notunanancyowen/spears/mixin/ItemStackMixin.java) |
| `ZombieEntityMixin` | `ZombieEntity` | [ZombieEntityMixin.java](com/notunanancyowen/spears/mixin/ZombieEntityMixin.java) |
| `PiglinEntityMixin` | `PiglinEntity` | [PiglinEntityMixin.java](com/notunanancyowen/spears/mixin/PiglinEntityMixin.java) |
| `EnchantmentEntityEffectMixin` | `EnchantmentEntityEffect` | [EnchantmentEntityEffectMixin.java](com/notunanancyowen/spears/mixin/EnchantmentEntityEffectMixin.java) |
| `ZombieEntityModelMixin` | `ZombieEntityModel` | [ZombieEntityModelMixin.java](com/notunanancyowen/spears/mixin/ZombieEntityModelMixin.java) |
| `MobEntityMixin` | `MobEntity` | [MobEntityMixin.java](com/notunanancyowen/spears/mixin/MobEntityMixin.java) |
| `MeleeAttackGoalMixin` | `MeleeAttackGoal` | [MeleeAttackGoalMixin.java](com/notunanancyowen/spears/mixin/MeleeAttackGoalMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `SpearsConfig` | EventBusSubscriber | [SpearsConfig.java](com/notunanancyowen/spears/neoforge/SpearsConfig.java) |

## 全部类（35）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `SpearsConfig` | class | — | EventBusSubscriber | 0 | 41 | [SpearsConfig.java](com/notunanancyowen/spears/neoforge/SpearsConfig.java) |
| `SpearsNeoForge` | class | — | Mod | 6 | 275 | [SpearsNeoForge.java](com/notunanancyowen/spears/neoforge/SpearsNeoForge.java) |
| `LivingEntityMixin` | class | SpearUser | Mixin | 16 | 321 | [LivingEntityMixin.java](com/notunanancyowen/spears/mixin/LivingEntityMixin.java) |
| `ModelPredicateProviderRegistryMixin` | class | — | Mixin | 1 | 23 | [ModelPredicateProviderRegistryMixin.java](com/notunanancyowen/spears/mixin/ModelPredicateProviderRegistryMixin.java) |
| `HeldItemRendererMixin` | class | — | Mixin | 5 | 203 | [HeldItemRendererMixin.java](com/notunanancyowen/spears/mixin/HeldItemRendererMixin.java) |
| `ItemRendererMixin` | class | — | Mixin | 1 | 52 | [ItemRendererMixin.java](com/notunanancyowen/spears/mixin/ItemRendererMixin.java) |
| `HeldItemFeatureRendererMixin` | class | — | Mixin | 1 | 92 | [HeldItemFeatureRendererMixin.java](com/notunanancyowen/spears/mixin/HeldItemFeatureRendererMixin.java) |
| `ClientPlayerEntityMixin` | class | Player | Mixin | 3 | 71 | [ClientPlayerEntityMixin.java](com/notunanancyowen/spears/mixin/ClientPlayerEntityMixin.java) |
| `Spears` | class | — | — | 77 | 751 | [Spears.java](com/notunanancyowen/spears/Spears.java) |
| `BipedEntityModelMixin` | class | AgeableListModel | Mixin | 5 | 189 | [BipedEntityModelMixin.java](com/notunanancyowen/spears/mixin/BipedEntityModelMixin.java) |
| `MinecraftClientMixin` | class | — | Mixin | 3 | 74 | [MinecraftClientMixin.java](com/notunanancyowen/spears/mixin/MinecraftClientMixin.java) |
| `PiglinEntityModelMixin` | class | PlayerModel | Mixin | 3 | 59 | [PiglinEntityModelMixin.java](com/notunanancyowen/spears/mixin/PiglinEntityModelMixin.java) |
| `ZombifiedPiglinEntityMixin` | class | — | Mixin | 1 | 20 | [ZombifiedPiglinEntityMixin.java](com/notunanancyowen/spears/mixin/ZombifiedPiglinEntityMixin.java) |
| `ItemStackMixin` | class | — | Mixin | 1 | 28 | [ItemStackMixin.java](com/notunanancyowen/spears/mixin/ItemStackMixin.java) |
| `ZombieEntityMixin` | class | — | Mixin | 1 | 23 | [ZombieEntityMixin.java](com/notunanancyowen/spears/mixin/ZombieEntityMixin.java) |
| `PiglinEntityMixin` | class | — | Mixin | 1 | 20 | [PiglinEntityMixin.java](com/notunanancyowen/spears/mixin/PiglinEntityMixin.java) |
| `EnchantmentEntityEffectMixin` | interface | — | Mixin | 1 | 23 | [EnchantmentEntityEffectMixin.java](com/notunanancyowen/spears/mixin/EnchantmentEntityEffectMixin.java) |
| `ZombieEntityModelMixin` | class | — | Mixin | 1 | 28 | [ZombieEntityModelMixin.java](com/notunanancyowen/spears/mixin/ZombieEntityModelMixin.java) |
| `MobEntityMixin` | class | — | Mixin | 2 | 42 | [MobEntityMixin.java](com/notunanancyowen/spears/mixin/MobEntityMixin.java) |
| `MeleeAttackGoalMixin` | class | — | Mixin | 2 | 52 | [MeleeAttackGoalMixin.java](com/notunanancyowen/spears/mixin/MeleeAttackGoalMixin.java) |
| `PacketCodecHelper` | class | — | — | 3 | 61 | [PacketCodecHelper.java](com/notunanancyowen/spears/PacketCodecHelper.java) |
| `PlayerStabC2SPacket` | record | CustomPacketPayload | — | 2 | 28 | [PlayerStabC2SPacket.java](com/notunanancyowen/spears/packets/PlayerStabC2SPacket.java) |
| `TriggerStabEffectsC2SPacket` | record | CustomPacketPayload | — | 2 | 73 | [TriggerStabEffectsC2SPacket.java](com/notunanancyowen/spears/packets/TriggerStabEffectsC2SPacket.java) |
| `SpearsClient` | class | — | — | 2 | 56 | [SpearsClient.java](com/notunanancyowen/spears/SpearsClient.java) |
| `KineticWeapon` | record | — | — | 9 | 185 | [KineticWeapon.java](com/notunanancyowen/spears/components/KineticWeapon.java) |
| `UseSpearGoal` | class | Goal | — | 5 | 116 | [UseSpearGoal.java](com/notunanancyowen/spears/goals/UseSpearGoal.java) |
| `PlatformMethods` | class | — | — | 1 | 8 | [PlatformMethods.java](architectury_inject_spears_common_20411d78f03145cfbdacc8cc75117c89_d4598a56931864f670ebfec4f065c283b07b6781ef2f970bd157ee14c603ca87spearscommon180devjar/PlatformMethods.java) |
| `ApplyImpulse` | record | EnchantmentEntityEffect | — | 4 | 59 | [ApplyImpulse.java](com/notunanancyowen/spears/enchantmentfx/ApplyImpulse.java) |
| `ApplyExhaustion` | record | EnchantmentEntityEffect | — | 2 | 28 | [ApplyExhaustion.java](com/notunanancyowen/spears/enchantmentfx/ApplyExhaustion.java) |
| `SpearUser` | interface | — | — | 0 | 17 | [SpearUser.java](com/notunanancyowen/spears/dataholders/SpearUser.java) |
| `SpearedMobs` | class | SimpleCriterionTrigger | — | 4 | 42 | [SpearedMobs.java](com/notunanancyowen/spears/criteria/SpearedMobs.java) |
| `SwingAnimation` | record | — | — | 0 | 21 | [SwingAnimation.java](com/notunanancyowen/spears/components/SwingAnimation.java) |
| `PiercingWeapon` | record | — | — | 4 | 144 | [PiercingWeapon.java](com/notunanancyowen/spears/components/PiercingWeapon.java) |
| `UseEffects` | record | — | — | 0 | 29 | [UseEffects.java](com/notunanancyowen/spears/components/UseEffects.java) |
| `AttackRange` | record | — | — | 0 | 22 | [AttackRange.java](com/notunanancyowen/spears/components/AttackRange.java) |
