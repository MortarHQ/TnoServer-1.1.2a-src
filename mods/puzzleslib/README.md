# Puzzles Lib（`puzzleslib`）

> jar：`[前置]PuzzlesLib.jar` ｜ 版本：`21.1.38` ｜ 作者：Fuzs
> 规模：**474 个类 / 120 个包 / 26407 行** ｜ 事件处理类 0 ｜ Mixin 16 ｜ 注册/管理类 78

**官方描述**：Why it's called Puzzles, you ask? That's the puzzle!

**依赖**：`neoforge`、`minecraft`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `fuzs.puzzleslib.api.event.v1.entity.living` | 21 | `UseItemEvents`、`MobEffectEvents`、`LivingBreathEvents` |
| `fuzs.puzzleslib.impl.event.data` | 20 | `EventDefaultedBoolean`、`EventMutableValue`、`EventDefaultedDouble` |
| `fuzs.puzzleslib.neoforge.impl.client.core.context` | 19 | `BlockEntityRenderersContextNeoForgeImpl`、`EntityRenderersContextNeoForgeImpl`、`MenuScreensContextNeoForgeImpl` |
| `fuzs.puzzleslib.api.client.core.v1.context` | 18 | `BlockEntityRenderersContext`、`EntityRenderersContext`、`BuiltinModelItemRendererContext` |
| `fuzs.puzzleslib.api.client.event.v1.gui` | 16 | `ScreenEvents`、`ScreenMouseEvents`、`ContainerScreenEvents` |
| `fuzs.puzzleslib.api.event.v1.entity.player` | 14 | `ItemEntityEvents`、`PlayerNetworkEvents`、`PlayerCopyEvents` |
| `fuzs.puzzleslib.neoforge.impl.core.context` | 12 | `BlockInteractionsContextNeoForgeImpl`、`GameplayContentContextNeoForgeImpl`、`CompostableBlocksContextNeoForgeImpl` |
| `fuzs.puzzleslib.api.core.v1.context` | 12 | `CompostableBlocksContext`、`FlammableBlocksContext`、`BlockInteractionsContext` |
| `fuzs.puzzleslib.api.client.event.v1.renderer` | 11 | `RenderLivingEvents`、`RenderHandEvents`、`FogEvents` |
| `fuzs.puzzleslib.neoforge.impl.core` | 10 | `NeoForgeBiomeLoadingHandler`、`NeoForgeModContainer`、`NeoForgeAbstractions` |
| `fuzs.puzzleslib.api.event.v1.data` | 10 | `MutableDouble`、`MutableInt`、`MutableFloat` |
| `fuzs.puzzleslib.api.core.v1` | 9 | `ContentRegistrationFlags`、`ModLoader`、`ServiceProviderHelper` |
| `fuzs.puzzleslib.api.event.v1.server` | 8 | `LootTableLoadEvents`、`ServerTickEvents`、`ServerLifecycleEvents` |
| `fuzs.puzzleslib.api.capability.v3.data` | 8 | `BlockEntityCapabilityKey`、`EntityCapabilityKey`、`CapabilityComponent` |
| `fuzs.puzzleslib.neoforge.mixin` | 7 | `LootItemRandomChanceWithEnchantedBonusConditionNeoForgeMixin`、`DatagenModLoaderNeoForgeMixin`、`EnchantmentHelperNeoForgeMixin` |
| `fuzs.puzzleslib.api.event.v1.level` | 7 | `BlockEvents`、`ServerLevelTickEvents`、`ExplosionEvents` |
| `fuzs.puzzleslib.impl.core` | 7 | `EventHandlerProvider`、`ModContext`、`ClientProxyImpl` |
| `fuzs.puzzleslib.api.network.v3` | 7 | `NetworkHandler`、`ClientboundMessage`、`ClientMessageListener` |
| `fuzs.puzzleslib.api.biome.v1` | 7 | `BiomeLoadingContext`、`GenerationSettingsContext`、`MobSpawnSettingsContext` |
| `fuzs.puzzleslib.api.init.v3.registry` | 6 | `RegistryManager`、`RegistryHelper`、`RegistryFactory` |
| `fuzs.puzzleslib.api.data.v2` | 6 | `AbstractLootProvider`、`AbstractRecipeProvider`、`AbstractAdvancementProvider` |
| `fuzs.puzzleslib.impl.item` | 6 | `CreativeModeTabConfiguratorImpl`、`CopyComponentsShapelessRecipe`、`CopyComponentsShapedRecipe` |
| `fuzs.puzzleslib.api.client.init.v1` | 6 | `ReloadingBuiltInItemRenderer`、`BuiltinItemRenderer`、`DynamicItemDecorator` |
| `fuzs.puzzleslib.impl.network` | 5 | `NetworkHandlerRegistryImpl`、`NetworkHandlerRegistry`、`CustomPacketPayloadAdapterImpl` |
| `fuzs.puzzleslib.api.client.event.v1.entity.player` | 5 | `ClientPlayerNetworkEvents`、`InteractionInputEvents`、`ClientPlayerCopyCallback` |
| `fuzs.puzzleslib.api.event.v1.entity` | 5 | `ServerEntityLevelEvents`、`EntityRidingEvents`、`EntityTickEvents` |
| `fuzs.puzzleslib.api.event.v1.core` | 5 | `EventInvokerRegistry`、`EventResultHolder`、`EventInvoker` |
| `fuzs.puzzleslib.api.core.v1.utility` | 5 | `ReflectionHelper`、`ResourceLocationHelper`、`EnvironmentAwareBuilder` |
| `fuzs.puzzleslib.neoforge.impl.capability.data` | 5 | `NeoForgeBlockEntityCapabilityKey`、`NeoForgeEntityCapabilityKey`、`NeoForgeLevelChunkCapabilityKey` |
| `fuzs.puzzleslib.api.event.v1` | 5 | `RegistryEntryAddedCallback`、`AddBlockEntityTypeBlocksCallback`、`ComputeItemAttributeModifiersCallback` |
| `fuzs.puzzleslib.impl.config.annotation` | 5 | `ConfigBuilder`、`ConfigEntry`、`LimitedEntry` |
| `fuzs.puzzleslib.api.config.v3` | 5 | `ConfigDataHolder`、`Config`、`ConfigCore` |
| `fuzs.puzzleslib.api.client.gui.v2.components` | 5 | `GuiGraphicsHelper`、`ScreenElementPositioner`、`TooltipRenderHelper` |
| `fuzs.puzzleslib.neoforge.impl.biome` | 5 | `SpecialEffectsContextNeoForge`、`MobSpawnSettingsContextNeoForge`、`ClimateSettingsContextNeoForge` |
| `fuzs.puzzleslib.neoforge.impl.init` | 4 | `NeoForgeRegistryManager`、`NeoForgeRegistryFactory`、`NeoForgePotionBrewingBuilder` |
| `fuzs.puzzleslib.impl.init` | 4 | `RegistryManagerImpl`、`MinecartTypeRegistryImpl`、`LazyHolder` |
| `fuzs.puzzleslib.impl.config` | 4 | `ConfigTranslationsManager`、`ConfigHolderRegistry`、`ConfigDataHolderImpl` |
| `fuzs.puzzleslib.api.client.event.v1` | 4 | `ClientTickEvents`、`InputEvents`、`ModelEvents` |
| `fuzs.puzzleslib.api.config.v3.json` | 4 | `JsonConfigFileUtil`、`JsonSerializationUtil`、`GsonEnumHelper` |
| `fuzs.puzzleslib.impl.attachment` | 4 | `DataAttachmentRegistryImpl`、`ClientboundEntityDataAttachmentMessage`、`DataAttachmentTypeImpl` |
| … | | 其余 80 个包 |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `NetworkHandlerRegistryImpl` | class | NetworkHandler.Builder | — | 16 | 169 | [NetworkHandlerRegistryImpl.java](fuzs/puzzleslib/impl/network/NetworkHandlerRegistryImpl.java) |
| `NeoForgeEventInvokerRegistryImpl` | class | NeoForgeEventInvokerRegistry | — | 10 | 1008 | [NeoForgeEventInvokerRegistryImpl.java](fuzs/puzzleslib/neoforge/impl/event/NeoForgeEventInvokerRegistryImpl.java) |
| `NewRegistryEventNeoForgeAccessor` | interface | — | Mixin | 0 | 17 | [NewRegistryEventNeoForgeAccessor.java](fuzs/puzzleslib/neoforge/mixin/accessor/NewRegistryEventNeoForgeAccessor.java) |
| `NetworkHandlerRegistry` | interface | — | — | 0 | 28 | [NetworkHandlerRegistry.java](fuzs/puzzleslib/impl/network/NetworkHandlerRegistry.java) |
| `ClientPlayerNetworkEvents` | class | — | — | 0 | 25 | [ClientPlayerNetworkEvents.java](fuzs/puzzleslib/api/client/event/v1/entity/player/ClientPlayerNetworkEvents.java) |
| `PuzzlesLibNeoForgeClient` | class | — | Mod | 2 | 31 | [PuzzlesLibNeoForgeClient.java](fuzs/puzzleslib/neoforge/impl/client/PuzzlesLibNeoForgeClient.java) |
| `NeoForgeRegistryManager` | class | RegistryManagerImpl | — | 8 | 114 | [NeoForgeRegistryManager.java](fuzs/puzzleslib/neoforge/impl/init/NeoForgeRegistryManager.java) |
| `ConfigDataSetImpl` | class | ConfigDataSetImpl.EntryHolder | — | 36 | 369 | [ConfigDataSetImpl.java](fuzs/puzzleslib/impl/config/serialization/ConfigDataSetImpl.java) |
| `RegistryManagerImpl` | class | RegistryManager | — | 8 | 102 | [RegistryManagerImpl.java](fuzs/puzzleslib/impl/init/RegistryManagerImpl.java) |
| `ItemEntityEvents` | class | — | — | 0 | 32 | [ItemEntityEvents.java](fuzs/puzzleslib/api/event/v1/entity/player/ItemEntityEvents.java) |
| `ClientEntityLevelEvents` | class | — | — | 0 | 25 | [ClientEntityLevelEvents.java](fuzs/puzzleslib/api/client/event/v1/entity/ClientEntityLevelEvents.java) |
| `RegistryManager` | interface | — | — | 3 | 333 | [RegistryManager.java](fuzs/puzzleslib/api/init/v3/registry/RegistryManager.java) |
| `LootItemRandomChanceWithEnchantedBonusConditionNeoForgeMixin` | class | — | Mixin | 1 | 25 | [LootItemRandomChanceWithEnchantedBonusConditionNeoForgeMixin.java](fuzs/puzzleslib/neoforge/mixin/LootItemRandomChanceWithEnchantedBonusConditionNeoForgeMixin.java) |
| `ConfigTranslationsManager` | class | — | — | 10 | 134 | [ConfigTranslationsManager.java](fuzs/puzzleslib/impl/config/ConfigTranslationsManager.java) |
| `PlayerNetworkEvents` | class | — | — | 0 | 23 | [PlayerNetworkEvents.java](fuzs/puzzleslib/api/event/v1/entity/player/PlayerNetworkEvents.java) |
| `LootTableLoadEvents` | class | — | — | 0 | 28 | [LootTableLoadEvents.java](fuzs/puzzleslib/api/event/v1/server/LootTableLoadEvents.java) |
| `AbstractLootProvider` | class | BlockLootSubProvider | — | 40 | 405 | [AbstractLootProvider.java](fuzs/puzzleslib/api/data/v2/AbstractLootProvider.java) |
| `ScreenEvents` | class | — | — | 5 | 67 | [ScreenEvents.java](fuzs/puzzleslib/api/client/event/v1/gui/ScreenEvents.java) |
| `ScreenMouseEvents` | class | — | — | 8 | 92 | [ScreenMouseEvents.java](fuzs/puzzleslib/api/client/event/v1/gui/ScreenMouseEvents.java) |
| `PuzzlesLibNeoForge` | class | — | Mod | 0 | 13 | [PuzzlesLibNeoForge.java](fuzs/puzzleslib/neoforge/impl/PuzzlesLibNeoForge.java) |
| `ConfigHolderRegistry` | interface | — | — | 0 | 18 | [ConfigHolderRegistry.java](fuzs/puzzleslib/impl/config/ConfigHolderRegistry.java) |
| `EditBoxMixin` | class | AbstractWidget | Mixin | 18 | 227 | [EditBoxMixin.java](fuzs/puzzleslib/mixin/client/EditBoxMixin.java) |
| `ServerEntityLevelEvents` | class | — | — | 0 | 33 | [ServerEntityLevelEvents.java](fuzs/puzzleslib/api/event/v1/entity/ServerEntityLevelEvents.java) |
| `EntityRidingEvents` | class | — | — | 0 | 25 | [EntityRidingEvents.java](fuzs/puzzleslib/api/event/v1/entity/EntityRidingEvents.java) |
| `EntityTickEvents` | class | — | — | 0 | 24 | [EntityTickEvents.java](fuzs/puzzleslib/api/event/v1/entity/EntityTickEvents.java) |
| `UseItemEvents` | class | — | — | 0 | 39 | [UseItemEvents.java](fuzs/puzzleslib/api/event/v1/entity/living/UseItemEvents.java) |
| `BlockEvents` | class | — | — | 0 | 37 | [BlockEvents.java](fuzs/puzzleslib/api/event/v1/level/BlockEvents.java) |
| `ClientTickEvents` | class | — | — | 0 | 23 | [ClientTickEvents.java](fuzs/puzzleslib/api/client/event/v1/ClientTickEvents.java) |
| `ContainerScreenEvents` | class | — | — | 0 | 24 | [ContainerScreenEvents.java](fuzs/puzzleslib/api/client/event/v1/gui/ContainerScreenEvents.java) |
| `ScreenKeyboardEvents` | class | — | — | 4 | 52 | [ScreenKeyboardEvents.java](fuzs/puzzleslib/api/client/event/v1/gui/ScreenKeyboardEvents.java) |
| `ClientLevelEvents` | class | — | — | 0 | 24 | [ClientLevelEvents.java](fuzs/puzzleslib/api/client/event/v1/level/ClientLevelEvents.java) |
| `ClientLevelTickEvents` | class | — | — | 0 | 24 | [ClientLevelTickEvents.java](fuzs/puzzleslib/api/client/event/v1/level/ClientLevelTickEvents.java) |
| `ClientChunkEvents` | class | — | — | 0 | 24 | [ClientChunkEvents.java](fuzs/puzzleslib/api/client/event/v1/level/ClientChunkEvents.java) |
| `RegistryHelper` | class | — | — | 15 | 118 | [RegistryHelper.java](fuzs/puzzleslib/api/init/v3/registry/RegistryHelper.java) |
| `NeoForgeNetworkHandler` | class | NetworkHandlerRegistryImpl | — | 5 | 136 | [NeoForgeNetworkHandler.java](fuzs/puzzleslib/neoforge/impl/network/NeoForgeNetworkHandler.java) |
| `NeoForgeClientEventInvokers` | class | — | — | 4 | 973 | [NeoForgeClientEventInvokers.java](fuzs/puzzleslib/neoforge/impl/client/event/NeoForgeClientEventInvokers.java) |
| `NeoForgeEventInvokerRegistry` | interface | — | — | 0 | 31 | [NeoForgeEventInvokerRegistry.java](fuzs/puzzleslib/neoforge/api/event/v1/core/NeoForgeEventInvokerRegistry.java) |
| `StreamCodecRegistryImpl` | class | StreamCodecRegistry<StreamCodecRegis | — | 11 | 248 | [StreamCodecRegistryImpl.java](fuzs/puzzleslib/impl/network/codec/StreamCodecRegistryImpl.java) |
| `EventInvokerRegistry` | interface | — | — | 0 | 25 | [EventInvokerRegistry.java](fuzs/puzzleslib/api/event/v1/core/EventInvokerRegistry.java) |
| `NeoForgeConfigCommand` | class | StringRepresentable | — | 6 | 116 | [NeoForgeConfigCommand.java](fuzs/puzzleslib/neoforge/impl/client/commands/NeoForgeConfigCommand.java) |
| `ItemColorsNeoForgeAccessor` | interface | — | Mixin | 0 | 15 | [ItemColorsNeoForgeAccessor.java](fuzs/puzzleslib/neoforge/mixin/client/accessor/ItemColorsNeoForgeAccessor.java) |
| `BlockColorsNeoForgeAccessor` | interface | — | Mixin | 0 | 15 | [BlockColorsNeoForgeAccessor.java](fuzs/puzzleslib/neoforge/mixin/client/accessor/BlockColorsNeoForgeAccessor.java) |
| `EventHandlerProvider` | interface | — | — | 0 | 12 | [EventHandlerProvider.java](fuzs/puzzleslib/impl/core/EventHandlerProvider.java) |
| `ConfigDataHolderImpl` | class | ConfigDataHolder<T> | — | 16 | 171 | [ConfigDataHolderImpl.java](fuzs/puzzleslib/impl/config/ConfigDataHolderImpl.java) |
| `NetworkHandler` | interface | — | — | 0 | 174 | [NetworkHandler.java](fuzs/puzzleslib/api/network/v3/NetworkHandler.java) |
| `ReflectionHelper` | class | — | — | 32 | 275 | [ReflectionHelper.java](fuzs/puzzleslib/api/core/v1/utility/ReflectionHelper.java) |
| `PlayerCopyEvents` | class | — | — | 0 | 23 | [PlayerCopyEvents.java](fuzs/puzzleslib/api/event/v1/entity/player/PlayerCopyEvents.java) |
| `GrindstoneEvents` | class | — | — | 0 | 28 | [GrindstoneEvents.java](fuzs/puzzleslib/api/event/v1/entity/player/GrindstoneEvents.java) |
| `PlayerTickEvents` | class | — | — | 0 | 23 | [PlayerTickEvents.java](fuzs/puzzleslib/api/event/v1/entity/player/PlayerTickEvents.java) |
| `PlayerTrackingEvents` | class | — | — | 0 | 24 | [PlayerTrackingEvents.java](fuzs/puzzleslib/api/event/v1/entity/player/PlayerTrackingEvents.java) |
| `ContainerEvents` | class | — | — | 0 | 24 | [ContainerEvents.java](fuzs/puzzleslib/api/event/v1/entity/player/ContainerEvents.java) |
| `AnvilEvents` | class | — | — | 0 | 31 | [AnvilEvents.java](fuzs/puzzleslib/api/event/v1/entity/player/AnvilEvents.java) |
| `PlayerInteractEvents` | class | — | — | 0 | 57 | [PlayerInteractEvents.java](fuzs/puzzleslib/api/event/v1/entity/player/PlayerInteractEvents.java) |
| `MobEffectEvents` | class | — | — | 0 | 39 | [MobEffectEvents.java](fuzs/puzzleslib/api/event/v1/entity/living/MobEffectEvents.java) |
| `LivingBreathEvents` | class | — | — | 0 | 25 | [LivingBreathEvents.java](fuzs/puzzleslib/api/event/v1/entity/living/LivingBreathEvents.java) |
| `ServerTickEvents` | class | — | — | 0 | 23 | [ServerTickEvents.java](fuzs/puzzleslib/api/event/v1/server/ServerTickEvents.java) |
| `ServerLifecycleEvents` | class | — | — | 0 | 35 | [ServerLifecycleEvents.java](fuzs/puzzleslib/api/event/v1/server/ServerLifecycleEvents.java) |
| `ServerLevelTickEvents` | class | — | — | 0 | 24 | [ServerLevelTickEvents.java](fuzs/puzzleslib/api/event/v1/level/ServerLevelTickEvents.java) |
| `ExplosionEvents` | class | — | — | 0 | 28 | [ExplosionEvents.java](fuzs/puzzleslib/api/event/v1/level/ExplosionEvents.java) |
| `ServerChunkEvents` | class | — | — | 0 | 38 | [ServerChunkEvents.java](fuzs/puzzleslib/api/event/v1/level/ServerChunkEvents.java) |

## Mixin（16 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `NewRegistryEventNeoForgeAccessor` | `NewRegistryEventNeoForgeAccessor` | [NewRegistryEventNeoForgeAccessor.java](fuzs/puzzleslib/neoforge/mixin/accessor/NewRegistryEventNeoForgeAccessor.java) |
| `LootItemRandomChanceWithEnchantedBonusConditionNeoForgeMixin` | `LootItemRandomChanceWithEnchantedBonusConditionNeoForge` | [LootItemRandomChanceWithEnchantedBonusConditionNeoForgeMixin.java](fuzs/puzzleslib/neoforge/mixin/LootItemRandomChanceWithEnchantedBonusConditionNeoForgeMixin.java) |
| `EditBoxMixin` | `EditBox` | [EditBoxMixin.java](fuzs/puzzleslib/mixin/client/EditBoxMixin.java) |
| `ItemColorsNeoForgeAccessor` | `ItemColorsNeoForgeAccessor` | [ItemColorsNeoForgeAccessor.java](fuzs/puzzleslib/neoforge/mixin/client/accessor/ItemColorsNeoForgeAccessor.java) |
| `BlockColorsNeoForgeAccessor` | `BlockColorsNeoForgeAccessor` | [BlockColorsNeoForgeAccessor.java](fuzs/puzzleslib/neoforge/mixin/client/accessor/BlockColorsNeoForgeAccessor.java) |
| `DatagenModLoaderNeoForgeMixin` | `DatagenModLoaderNeoForge` | [DatagenModLoaderNeoForgeMixin.java](fuzs/puzzleslib/neoforge/mixin/DatagenModLoaderNeoForgeMixin.java) |
| `EnchantmentHelperNeoForgeMixin` | `EnchantmentHelperNeoForge` | [EnchantmentHelperNeoForgeMixin.java](fuzs/puzzleslib/neoforge/mixin/EnchantmentHelperNeoForgeMixin.java) |
| `TagsProviderNeoForgeMixin` | `TagsProviderNeoForge` | [TagsProviderNeoForgeMixin.java](fuzs/puzzleslib/neoforge/mixin/TagsProviderNeoForgeMixin.java) |
| `EnchantedCountIncreaseFunctionNeoForgeMixin` | `EnchantedCountIncreaseFunctionNeoForge` | [EnchantedCountIncreaseFunctionNeoForgeMixin.java](fuzs/puzzleslib/neoforge/mixin/EnchantedCountIncreaseFunctionNeoForgeMixin.java) |
| `AbstractPackResourcesNeoForgeMixin` | `AbstractPackResourcesNeoForge` | [AbstractPackResourcesNeoForgeMixin.java](fuzs/puzzleslib/neoforge/mixin/AbstractPackResourcesNeoForgeMixin.java) |
| `MobSpawnSettingsBuilderNeoForgeAccessor` | `MobSpawnSettingsBuilderNeoForgeAccessor` | [MobSpawnSettingsBuilderNeoForgeAccessor.java](fuzs/puzzleslib/neoforge/mixin/accessor/MobSpawnSettingsBuilderNeoForgeAccessor.java) |
| `BiomeSpecialEffectsBuilderNeoForgeAccessor` | `BiomeSpecialEffectsBuilderNeoForgeAccessor` | [BiomeSpecialEffectsBuilderNeoForgeAccessor.java](fuzs/puzzleslib/neoforge/mixin/accessor/BiomeSpecialEffectsBuilderNeoForgeAccessor.java) |
| `AbstractMinecartMixin` | `AbstractMinecart` | [AbstractMinecartMixin.java](fuzs/puzzleslib/mixin/AbstractMinecartMixin.java) |
| `DedicatedServerSettingsMixin` | `DedicatedServerSettings` | [DedicatedServerSettingsMixin.java](fuzs/puzzleslib/mixin/server/DedicatedServerSettingsMixin.java) |
| `EulaMixin` | `Eula` | [EulaMixin.java](fuzs/puzzleslib/mixin/server/EulaMixin.java) |
| `ModelBakeryMixin` | `ModelBakery` | [ModelBakeryMixin.java](fuzs/puzzleslib/mixin/client/ModelBakeryMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `ConfigDataSetImpl` | — | [ConfigDataSetImpl.java](fuzs/puzzleslib/impl/config/serialization/ConfigDataSetImpl.java) |
| `ConfigTranslationsManager` | — | [ConfigTranslationsManager.java](fuzs/puzzleslib/impl/config/ConfigTranslationsManager.java) |
| `ConfigHolderRegistry` | — | [ConfigHolderRegistry.java](fuzs/puzzleslib/impl/config/ConfigHolderRegistry.java) |
| `NeoForgeConfigCommand` | — | [NeoForgeConfigCommand.java](fuzs/puzzleslib/neoforge/impl/client/commands/NeoForgeConfigCommand.java) |
| `ConfigDataHolderImpl` | — | [ConfigDataHolderImpl.java](fuzs/puzzleslib/impl/config/ConfigDataHolderImpl.java) |
| `JsonConfigFileUtil` | — | [JsonConfigFileUtil.java](fuzs/puzzleslib/api/config/v3/json/JsonConfigFileUtil.java) |
| `ConfigHolderImpl` | — | [ConfigHolderImpl.java](fuzs/puzzleslib/impl/config/ConfigHolderImpl.java) |
| `CreativeModeTabConfiguratorImpl` | — | [CreativeModeTabConfiguratorImpl.java](fuzs/puzzleslib/impl/item/CreativeModeTabConfiguratorImpl.java) |
| `MixinConfigPluginImpl` | — | [MixinConfigPluginImpl.java](fuzs/puzzleslib/mixin/MixinConfigPluginImpl.java) |
| `NeoForgeConfigHolderImpl` | — | [NeoForgeConfigHolderImpl.java](fuzs/puzzleslib/neoforge/impl/config/NeoForgeConfigHolderImpl.java) |
| `MixinConfigPluginNeoForgeImpl` | — | [MixinConfigPluginNeoForgeImpl.java](fuzs/puzzleslib/neoforge/mixin/MixinConfigPluginNeoForgeImpl.java) |
| `ConfigBuilder` | — | [ConfigBuilder.java](fuzs/puzzleslib/impl/config/annotation/ConfigBuilder.java) |
| `ConfigEntry` | — | [ConfigEntry.java](fuzs/puzzleslib/impl/config/annotation/ConfigEntry.java) |
| `ConfigDataHolder` | — | [ConfigDataHolder.java](fuzs/puzzleslib/api/config/v3/ConfigDataHolder.java) |
| `ConfigDataSet` | — | [ConfigDataSet.java](fuzs/puzzleslib/api/config/v3/serialization/ConfigDataSet.java) |
| `Config` | Retention;Target | [Config.java](fuzs/puzzleslib/api/config/v3/Config.java) |
| `ConfigCore` | — | [ConfigCore.java](fuzs/puzzleslib/api/config/v3/ConfigCore.java) |
| `ConfigHolder` | — | [ConfigHolder.java](fuzs/puzzleslib/api/config/v3/ConfigHolder.java) |
| `CreativeModeTabConfigurator` | — | [CreativeModeTabConfigurator.java](fuzs/puzzleslib/api/item/v2/CreativeModeTabConfigurator.java) |

## 按包浏览

- `architectury_inject_PuzzlesLib1211_Common_807d71e998744279ad4626f4a73ee09d_10d49b7a4d6b6a2c74aa0f80893be820ea732149e2d327705a5ee8c0a1418e2aPuzzlesLibv211381211Commondevjar`（1 类）
- `fuzs.puzzleslib.api.attachment.v4`（2 类）
- `fuzs.puzzleslib.api.biome.v1`（7 类）
- `fuzs.puzzleslib.api.block.v1`（3 类）
- `fuzs.puzzleslib.api.block.v1.entity`（2 类）
- `fuzs.puzzleslib.api.capability.v3`（1 类）
- `fuzs.puzzleslib.api.capability.v3.data`（8 类）
- `fuzs.puzzleslib.api.chat.v1`（1 类）
- `fuzs.puzzleslib.api.client.core.v1`（2 类）
- `fuzs.puzzleslib.api.client.core.v1.context`（18 类）
- `fuzs.puzzleslib.api.client.data.v2`（3 类）
- `fuzs.puzzleslib.api.client.data.v2.models`（2 类）
- `fuzs.puzzleslib.api.client.event.v1`（4 类）
- `fuzs.puzzleslib.api.client.event.v1.entity`（1 类）
- `fuzs.puzzleslib.api.client.event.v1.entity.player`（5 类）
- `fuzs.puzzleslib.api.client.event.v1.gui`（16 类）
- `fuzs.puzzleslib.api.client.event.v1.level`（3 类）
- `fuzs.puzzleslib.api.client.event.v1.renderer`（11 类）
- `fuzs.puzzleslib.api.client.gui.v2.components`（5 类）
- `fuzs.puzzleslib.api.client.gui.v2.components.tooltip`（2 类）
- `fuzs.puzzleslib.api.client.gui.v2.screen`（2 类）
- `fuzs.puzzleslib.api.client.init.v1`（6 类）
- `fuzs.puzzleslib.api.client.key.v1`（3 类）
- `fuzs.puzzleslib.api.client.packs.v1`（1 类）
- `fuzs.puzzleslib.api.client.particle.v1`（1 类）
- `fuzs.puzzleslib.api.client.searchtree.v1`（2 类）
- `fuzs.puzzleslib.api.codec.v1`（1 类）
- `fuzs.puzzleslib.api.config.v3`（5 类）
- `fuzs.puzzleslib.api.config.v3.json`（4 类）
- `fuzs.puzzleslib.api.config.v3.serialization`（2 类）
- `fuzs.puzzleslib.api.container.v1`（3 类）
- `fuzs.puzzleslib.api.core.v1`（9 类）
- `fuzs.puzzleslib.api.core.v1.context`（12 类）
- `fuzs.puzzleslib.api.core.v1.resources`（2 类）
- `fuzs.puzzleslib.api.core.v1.utility`（5 类）
- `fuzs.puzzleslib.api.data.v2`（6 类）
- `fuzs.puzzleslib.api.data.v2.core`（2 类）
- `fuzs.puzzleslib.api.data.v2.recipes`（2 类）
- `fuzs.puzzleslib.api.data.v2.tags`（2 类）
- `fuzs.puzzleslib.api.entity.v1`（2 类）
- `fuzs.puzzleslib.api.event.v1`（5 类）
- `fuzs.puzzleslib.api.event.v1.core`（5 类）
- `fuzs.puzzleslib.api.event.v1.data`（10 类）
- `fuzs.puzzleslib.api.event.v1.entity`（5 类）
- `fuzs.puzzleslib.api.event.v1.entity.living`（21 类）
- `fuzs.puzzleslib.api.event.v1.entity.player`（14 类）
- `fuzs.puzzleslib.api.event.v1.level`（7 类）
- `fuzs.puzzleslib.api.event.v1.server`（8 类）
- `fuzs.puzzleslib.api.init.v3`（2 类）
- `fuzs.puzzleslib.api.init.v3.override`（2 类）
- `fuzs.puzzleslib.api.init.v3.registry`（6 类）
- `fuzs.puzzleslib.api.init.v3.tags`（3 类）
- `fuzs.puzzleslib.api.item.v2`（4 类）
- `fuzs.puzzleslib.api.item.v2.crafting`（1 类）
- `fuzs.puzzleslib.api.network.v2`（2 类）
- `fuzs.puzzleslib.api.network.v3`（7 类）
- `fuzs.puzzleslib.api.network.v3.codec`（2 类）
- `fuzs.puzzleslib.api.resources.v1`（3 类）
- `fuzs.puzzleslib.api.shape.v1`（1 类）
- `fuzs.puzzleslib.impl`（2 类）
- `fuzs.puzzleslib.impl.attachment`（4 类）
- `fuzs.puzzleslib.impl.attachment.builder`（2 类）
- `fuzs.puzzleslib.impl.capability`（3 类）
- `fuzs.puzzleslib.impl.chat`（1 类）
- `fuzs.puzzleslib.impl.client`（1 类）
- `fuzs.puzzleslib.impl.client.core`（1 类）
- `fuzs.puzzleslib.impl.client.core.context`（2 类）
- `fuzs.puzzleslib.impl.client.event`（2 类）
- `fuzs.puzzleslib.impl.client.gui`（3 类）
- `fuzs.puzzleslib.impl.client.init`（1 类）
- `fuzs.puzzleslib.impl.client.key`（1 类）
- `fuzs.puzzleslib.impl.config`（4 类）
- `fuzs.puzzleslib.impl.config.annotation`（5 类）
- `fuzs.puzzleslib.impl.config.serialization`（3 类）
- `fuzs.puzzleslib.impl.core`（7 类）
- `fuzs.puzzleslib.impl.core.resources`（2 类）
- `fuzs.puzzleslib.impl.event`（3 类）
- `fuzs.puzzleslib.impl.event.core`（2 类）
- `fuzs.puzzleslib.impl.event.data`（20 类）
- `fuzs.puzzleslib.impl.init`（4 类）
- `fuzs.puzzleslib.impl.item`（6 类）
- `fuzs.puzzleslib.impl.network`（5 类）
- `fuzs.puzzleslib.impl.network.codec`（3 类）
- `fuzs.puzzleslib.impl.resources`（1 类）
- `fuzs.puzzleslib.mixin`（2 类）
- `fuzs.puzzleslib.mixin.client`（2 类）
- `fuzs.puzzleslib.mixin.server`（2 类）
- `fuzs.puzzleslib.neoforge.api.core.v1`（1 类）
- `fuzs.puzzleslib.neoforge.api.data.v2`（1 类）
- `fuzs.puzzleslib.neoforge.api.data.v2.client`（4 类）
- `fuzs.puzzleslib.neoforge.api.data.v2.client.model`（1 类）
- `fuzs.puzzleslib.neoforge.api.data.v2.core`（2 类）
- `fuzs.puzzleslib.neoforge.api.event.v1.core`（1 类）
- `fuzs.puzzleslib.neoforge.api.event.v1.entity.living`（1 类）
- `fuzs.puzzleslib.neoforge.api.init.v3.capability`（1 类）
- `fuzs.puzzleslib.neoforge.impl`（1 类）
- `fuzs.puzzleslib.neoforge.impl.attachment`（2 类）
- `fuzs.puzzleslib.neoforge.impl.attachment.builder`（3 类）
- `fuzs.puzzleslib.neoforge.impl.biome`（5 类）
- `fuzs.puzzleslib.neoforge.impl.capability`（1 类）
- `fuzs.puzzleslib.neoforge.impl.capability.data`（5 类）
- `fuzs.puzzleslib.neoforge.impl.client`（1 类）
- `fuzs.puzzleslib.neoforge.impl.client.commands`（1 类）
- `fuzs.puzzleslib.neoforge.impl.client.core`（4 类）
- `fuzs.puzzleslib.neoforge.impl.client.core.context`（19 类）
- `fuzs.puzzleslib.neoforge.impl.client.event`（2 类）
- `fuzs.puzzleslib.neoforge.impl.client.init`（1 类）
- `fuzs.puzzleslib.neoforge.impl.client.key`（1 类）
- `fuzs.puzzleslib.neoforge.impl.config`（1 类）
- `fuzs.puzzleslib.neoforge.impl.core`（10 类）
- `fuzs.puzzleslib.neoforge.impl.core.context`（12 类）
- `fuzs.puzzleslib.neoforge.impl.data`（2 类）
- `fuzs.puzzleslib.neoforge.impl.event`（1 类）
- `fuzs.puzzleslib.neoforge.impl.init`（4 类）
- `fuzs.puzzleslib.neoforge.impl.item`（1 类）
- `fuzs.puzzleslib.neoforge.impl.item.crafting`（1 类）
- `fuzs.puzzleslib.neoforge.impl.network`（1 类）
- `fuzs.puzzleslib.neoforge.mixin`（7 类）
- `fuzs.puzzleslib.neoforge.mixin.accessor`（3 类）
- `fuzs.puzzleslib.neoforge.mixin.client.accessor`（2 类）
