# Relics（`relics`）

> jar：`[遗物]relics.jar` ｜ 版本：`0.10.7.8` ｜ 作者：SSKirillSS
> 规模：**289 个类 / 92 个包 / 31814 行** ｜ 事件处理类 42 ｜ Mixin 19 ｜ 注册/管理类 27

**依赖**：`minecraft`、`neoforge`、`curios`、`octolib`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `it.hurts.sskirillss.relics.init` | 19 | `CommandRegistry`、`RemoteRegistry`、`DataComponentRegistry` |
| `it.hurts.sskirillss.relics.mixin` | 17 | `MouseHandlerMixin`、`KeyboardHandlerMixin`、`LivingEntityMixin` |
| `it.hurts.sskirillss.relics.entities` | 12 | `ShadowGlaiveEntity`、`SporeEntity`、`LifeEssenceEntity` |
| `it.hurts.sskirillss.relics.utils` | 10 | `EntityUtils`、`NBTUtils`、`Easing` |
| `it.hurts.sskirillss.relics.items.relics.feet` | 8 | `MagmaWalkerItem`、`AquaWalkerItem`、`AmphibianBootItem` |
| `it.hurts.sskirillss.relics.client.renderer.entities` | 8 | `BlockSimulationRenderer`、`SporeRenderer`、`StalactiteRenderer` |
| `it.hurts.sskirillss.relics.components` | 8 | `AbilityComponent`、`DataComponent`、`StatComponent` |
| `it.hurts.sskirillss.relics.client.screen.description.general.widgets` | 8 | `LogoWidget`、`TabWidget`、`RelicBadgeWidget` |
| `it.hurts.sskirillss.relics.badges.ability` | 8 | `CyclicalBadge`、`InstantaneousBadge`、`ToggleableBadge` |
| `it.hurts.sskirillss.relics.config.data` | 7 | `LootEntryConfigData`、`LootConfigData`、`StatConfigData` |
| `it.hurts.sskirillss.relics.effects` | 7 | `ParalysisEffect`、`StunEffect`、`BleedingEffect` |
| `it.hurts.sskirillss.relics.client.screen.description.ability.widgets` | 7 | `AbilityCardWidget`、`BigAbilityCardWidget`、`RerollAbilityActionWidget` |
| `it.hurts.sskirillss.relics.items.relics.base.data.leveling` | 6 | `LevelingSourceData`、`AbilityData`、`StatData` |
| `it.hurts.sskirillss.relics.utils.data` | 6 | `GUIRenderer`、`AnimationData`、`SpriteMirror` |
| `it.hurts.sskirillss.relics.items.relics` | 5 | `InfiniteHamItem`、`MagicMirrorItem`、`ShadowGlaiveItem` |
| `it.hurts.sskirillss.relics.api.events.common` | 5 | `EntityBlockSpeedFactorEvent`、`TooltipDisplayEvent`、`ContainerSlotClickEvent` |
| `it.hurts.sskirillss.relics.client.screen.base` | 5 | `ITabbedDescriptionScreen`、`IRelicScreenProvider`、`IAutoScaledScreen` |
| `it.hurts.sskirillss.relics.client.models.entities` | 5 | `StalactiteModel`、`SolidSnowballModel`、`DissectionModel` |
| `it.hurts.sskirillss.relics.items.relics.base.data` | 4 | `RelicData`、`RelicAttributeModifier`、`RelicSlotModifier` |
| `it.hurts.sskirillss.relics.items` | 4 | `BlockItemBase`、`RelicExperienceBottleItem`、`ItemBase` |
| `it.hurts.sskirillss.relics.client.screen.description.experience.widgets` | 4 | `ExperienceGemWidget`、`ResetExperienceActionWidget`、`BigExperienceCardWidget` |
| `it.hurts.sskirillss.relics.client.models.items` | 4 | `CurioModel`、`SidedCurioModel`、`SidedFPRCurioModel` |
| `it.hurts.sskirillss.relics.client.gui.layers` | 4 | `ActiveAbilitiesLayer`、`InfoTileLayer`、`PhantomBootBridgeLayer` |
| `it.hurts.sskirillss.relics.client.handlers` | 3 | `ArmorRenderHandler`、`HandRenderHandler`、`DescriptionHandler` |
| `it.hurts.sskirillss.relics.items.relics.belt` | 3 | `LeatherBeltItem`、`DrownedBeltItem`、`HunterBeltItem` |
| `it.hurts.sskirillss.relics.items.relics.hands` | 3 | `RageGloveItem`、`WoolMittenItem`、`EnderHandItem` |
| `it.hurts.sskirillss.relics.items.relics.necklace` | 3 | `HolyLocketItem`、`ReflectionNecklaceItem`、`JellyfishNecklaceItem` |
| `it.hurts.sskirillss.relics.network.packets` | 3 | `PacketSyncEntityEffects`、`PacketItemActivation`、`PacketPlayerMotion` |
| `it.hurts.sskirillss.relics.items.relics.base` | 3 | `IRelicItem`、`RelicItem`、`IRenderableCurio` |
| `it.hurts.sskirillss.relics.items.relics.base.data.style` | 3 | `TooltipData`、`StyleData`、`BeamsData` |
| `it.hurts.sskirillss.relics.items.relics.ring` | 3 | `BastionRingItem`、`ChorusInhibitorItem`、`LeafyRingItem` |
| `it.hurts.sskirillss.relics.client.screen.description.misc` | 3 | `DescriptionCache`、`DescriptionUtils`、`DescriptionTextures` |
| `it.hurts.sskirillss.relics.badges.base` | 3 | `RelicBadge`、`AbilityBadge`、`AbstractBadge` |
| `it.hurts.sskirillss.relics.client.screen.description.research.widgets` | 3 | `HintWidget`、`StarWidget`、`TipWidget` |
| `it.hurts.sskirillss.relics.client.screen.description.general.widgets.base` | 3 | `AbstractPlateWidget`、`AbstractBadgeWidget`、`AbstractDescriptionWidget` |
| `it.hurts.sskirillss.relics.client.screen.description.relic.particles` | 3 | `SparkParticleData`、`ChainParticleData`、`ExperienceParticleData` |
| `it.hurts.sskirillss.relics.items.relics.base.data.cast.misc` | 3 | `PredicateType`、`CastType`、`CastStage` |
| `it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc` | 3 | `UpgradeOperation`、`GemColor`、`GemShape` |
| `it.hurts.sskirillss.relics.system.casts.handlers` | 2 | `CacheHandler`、`HUDRenderHandler` |
| `it.hurts.sskirillss.relics.items.relics.back` | 2 | `ElytraBoosterItem`、`MidnightRobeItem` |
| … | | 其余 52 个包 |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `NetworkHandler` | class | — | EventBusSubscriber | 5 | 60 | [NetworkHandler.java](it/hurts/sskirillss/relics/network/NetworkHandler.java) |
| `CommandRegistry` | class | — | EventBusSubscriber | 2 | 38 | [CommandRegistry.java](it/hurts/sskirillss/relics/init/CommandRegistry.java) |
| `RemoteRegistry` | class | — | EventBusSubscriber | 6 | 205 | [RemoteRegistry.java](it/hurts/sskirillss/relics/init/RemoteRegistry.java) |
| `DataComponentRegistry` | class | — | EventBusSubscriber | 2 | 77 | [DataComponentRegistry.java](it/hurts/sskirillss/relics/init/DataComponentRegistry.java) |
| `ArmorRenderHandler` | class | — | EventBusSubscriber | 6 | 92 | [ArmorRenderHandler.java](it/hurts/sskirillss/relics/client/handlers/ArmorRenderHandler.java) |
| `ParticleRegistry` | class | — | EventBusSubscriber | 2 | 31 | [ParticleRegistry.java](it/hurts/sskirillss/relics/init/ParticleRegistry.java) |
| `HotkeyRegistry` | class | — | EventBusSubscriber | 1 | 22 | [HotkeyRegistry.java](it/hurts/sskirillss/relics/init/HotkeyRegistry.java) |
| `CreativeTabRegistry` | class | — | EventBusSubscriber | 2 | 50 | [CreativeTabRegistry.java](it/hurts/sskirillss/relics/init/CreativeTabRegistry.java) |
| `RegistryRegistry` | class | — | EventBusSubscriber | 1 | 31 | [RegistryRegistry.java](it/hurts/sskirillss/relics/init/RegistryRegistry.java) |
| `CacheHandler` | class | — | EventBusSubscriber | 1 | 58 | [CacheHandler.java](it/hurts/sskirillss/relics/system/casts/handlers/CacheHandler.java) |
| `HandRenderHandler` | class | — | EventBusSubscriber | 1 | 74 | [HandRenderHandler.java](it/hurts/sskirillss/relics/client/handlers/HandRenderHandler.java) |
| `DescriptionHandler` | class | — | EventBusSubscriber | 4 | 123 | [DescriptionHandler.java](it/hurts/sskirillss/relics/client/handlers/DescriptionHandler.java) |
| `TooltipBorderHandler` | class | — | EventBusSubscriber | 2 | 142 | [TooltipBorderHandler.java](it/hurts/sskirillss/relics/client/tooltip/TooltipBorderHandler.java) |
| `MagmaWalkerItem` | class | RelicItem | EventBusSubscriber | 8 | 217 | [MagmaWalkerItem.java](it/hurts/sskirillss/relics/items/relics/feet/MagmaWalkerItem.java) |
| `AquaWalkerItem` | class | RelicItem | EventBusSubscriber | 7 | 208 | [AquaWalkerItem.java](it/hurts/sskirillss/relics/items/relics/feet/AquaWalkerItem.java) |
| `ElytraBoosterItem` | class | RelicItem | EventBusSubscriber | 9 | 283 | [ElytraBoosterItem.java](it/hurts/sskirillss/relics/items/relics/back/ElytraBoosterItem.java) |
| `LeatherBeltItem` | class | RelicItem | EventBusSubscriber | 5 | 106 | [LeatherBeltItem.java](it/hurts/sskirillss/relics/items/relics/belt/LeatherBeltItem.java) |
| `RelicLootModifier` | class | LootModifier | — | 34 | 405 | [RelicLootModifier.java](it/hurts/sskirillss/relics/level/RelicLootModifier.java) |
| `RelicsCoreShaders` | class | — | EventBusSubscriber | 1 | 27 | [RelicsCoreShaders.java](it/hurts/sskirillss/relics/init/RelicsCoreShaders.java) |
| `HUDRenderHandler` | class | — | OnlyIn | 11 | 568 | [HUDRenderHandler.java](it/hurts/sskirillss/relics/system/casts/handlers/HUDRenderHandler.java) |
| `InfiniteHamItem` | class | RelicItem | — | 20 | 439 | [InfiniteHamItem.java](it/hurts/sskirillss/relics/items/relics/InfiniteHamItem.java) |
| `MouseHandlerMixin` | class | — | Mixin | 1 | 22 | [MouseHandlerMixin.java](it/hurts/sskirillss/relics/mixin/MouseHandlerMixin.java) |
| `KeyboardHandlerMixin` | class | — | Mixin | 1 | 22 | [KeyboardHandlerMixin.java](it/hurts/sskirillss/relics/mixin/KeyboardHandlerMixin.java) |
| `Relics` | class | — | Mod | 1 | 53 | [Relics.java](it/hurts/sskirillss/relics/Relics.java) |
| `LootEntryConfigData` | class | — | — | 12 | 116 | [LootEntryConfigData.java](it/hurts/sskirillss/relics/config/data/LootEntryConfigData.java) |
| `AbilityResearchScreen` | class | Screen | OnlyIn | 26 | 665 | [AbilityResearchScreen.java](it/hurts/sskirillss/relics/client/screen/description/research/AbilityResearchScreen.java) |
| `ConfigRegistry` | class | — | — | 1 | 33 | [ConfigRegistry.java](it/hurts/sskirillss/relics/init/ConfigRegistry.java) |
| `MagicMirrorItem` | class | RelicItem | — | 11 | 240 | [MagicMirrorItem.java](it/hurts/sskirillss/relics/items/relics/MagicMirrorItem.java) |
| `LevelingSourceData` | class | — | — | 46 | 495 | [LevelingSourceData.java](it/hurts/sskirillss/relics/items/relics/base/data/leveling/LevelingSourceData.java) |
| `RageGloveItem` | class | RelicItem | — | 9 | 416 | [RageGloveItem.java](it/hurts/sskirillss/relics/items/relics/hands/RageGloveItem.java) |
| `HolyLocketItem` | class | RelicItem | — | 11 | 393 | [HolyLocketItem.java](it/hurts/sskirillss/relics/items/relics/necklace/HolyLocketItem.java) |
| `LivingEntityMixin` | class | — | Mixin | 6 | 83 | [LivingEntityMixin.java](it/hurts/sskirillss/relics/mixin/LivingEntityMixin.java) |
| `LootConfigData` | class | OctoConfig | — | 6 | 44 | [LootConfigData.java](it/hurts/sskirillss/relics/config/LootConfigData.java) |
| `LootConfigData` | class | — | — | 7 | 70 | [LootConfigData.java](it/hurts/sskirillss/relics/config/data/LootConfigData.java) |
| `LootCodecRegistry` | class | — | — | 1 | 23 | [LootCodecRegistry.java](it/hurts/sskirillss/relics/init/LootCodecRegistry.java) |
| `AbilityData` | class | — | — | 34 | 355 | [AbilityData.java](it/hurts/sskirillss/relics/items/relics/base/data/leveling/AbilityData.java) |
| `AmphibianBootItem` | class | RelicItem | — | 8 | 283 | [AmphibianBootItem.java](it/hurts/sskirillss/relics/items/relics/feet/AmphibianBootItem.java) |
| `ReflectionNecklaceItem` | class | RelicItem | — | 6 | 217 | [ReflectionNecklaceItem.java](it/hurts/sskirillss/relics/items/relics/necklace/ReflectionNecklaceItem.java) |
| `DrownedBeltItem` | class | RelicItem | — | 10 | 198 | [DrownedBeltItem.java](it/hurts/sskirillss/relics/items/relics/belt/DrownedBeltItem.java) |
| `MidnightRobeItem` | class | RelicItem | — | 9 | 304 | [MidnightRobeItem.java](it/hurts/sskirillss/relics/items/relics/back/MidnightRobeItem.java) |
| `AbstractContainerMenuMixin` | class | — | Mixin | 2 | 41 | [AbstractContainerMenuMixin.java](it/hurts/sskirillss/relics/mixin/AbstractContainerMenuMixin.java) |
| `EntityMixin` | class | — | Mixin | 3 | 100 | [EntityMixin.java](it/hurts/sskirillss/relics/mixin/EntityMixin.java) |
| `ItemMixin` | class | — | Mixin | 4 | 92 | [ItemMixin.java](it/hurts/sskirillss/relics/mixin/ItemMixin.java) |
| `PowderSnowBlockMixin` | class | — | Mixin | 1 | 25 | [PowderSnowBlockMixin.java](it/hurts/sskirillss/relics/mixin/PowderSnowBlockMixin.java) |
| `BlockMixin` | class | — | Mixin | 2 | 88 | [BlockMixin.java](it/hurts/sskirillss/relics/mixin/BlockMixin.java) |
| `ScreenMixin` | class | — | Mixin | 2 | 59 | [ScreenMixin.java](it/hurts/sskirillss/relics/mixin/ScreenMixin.java) |
| `StatConfigData` | class | — | — | 17 | 176 | [StatConfigData.java](it/hurts/sskirillss/relics/config/data/StatConfigData.java) |
| `ParticleData` | class | — | — | 31 | 269 | [ParticleData.java](it/hurts/sskirillss/relics/client/screen/description/general/particles/base/ParticleData.java) |
| `BasicColoredParticle` | class | TextureSheetParticle | — | 43 | 497 | [BasicColoredParticle.java](it/hurts/sskirillss/relics/client/particles/BasicColoredParticle.java) |
| `ShadowGlaiveEntity` | class | ThrowableProjectile | — | 27 | 258 | [ShadowGlaiveEntity.java](it/hurts/sskirillss/relics/entities/ShadowGlaiveEntity.java) |
| `EntityUtils` | class | — | — | 20 | 288 | [EntityUtils.java](it/hurts/sskirillss/relics/utils/EntityUtils.java) |
| `GUIRenderer` | class | — | OnlyIn | 27 | 312 | [GUIRenderer.java](it/hurts/sskirillss/relics/utils/data/GUIRenderer.java) |
| `ItemRegistry` | class | — | — | 1 | 77 | [ItemRegistry.java](it/hurts/sskirillss/relics/init/ItemRegistry.java) |
| `EntityRegistry` | class | — | — | 1 | 67 | [EntityRegistry.java](it/hurts/sskirillss/relics/init/EntityRegistry.java) |
| `BlockRegistry` | class | — | — | 1 | 33 | [BlockRegistry.java](it/hurts/sskirillss/relics/init/BlockRegistry.java) |
| `PacketSyncEntityEffects` | class | CustomPacketPayload | — | 10 | 138 | [PacketSyncEntityEffects.java](it/hurts/sskirillss/relics/network/packets/PacketSyncEntityEffects.java) |
| `S2CEntityMotionPacket` | class | CustomPacketPayload | — | 10 | 112 | [S2CEntityMotionPacket.java](it/hurts/sskirillss/relics/network/packets/sync/S2CEntityMotionPacket.java) |
| `CapabilitySyncPacket` | class | CustomPacketPayload | — | 8 | 82 | [CapabilitySyncPacket.java](it/hurts/sskirillss/relics/network/packets/capability/CapabilitySyncPacket.java) |
| `IRelicItem` | interface | — | — | 0 | 1061 | [IRelicItem.java](it/hurts/sskirillss/relics/items/relics/base/IRelicItem.java) |
| `LootEntry` | class | — | — | 22 | 189 | [LootEntry.java](it/hurts/sskirillss/relics/items/relics/base/data/loot/LootEntry.java) |

## 事件处理（`@SubscribeEvent`，42 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `RemoteRegistry` | 6 | `it.hurts.sskirillss.relics.init` | [RemoteRegistry.java](it/hurts/sskirillss/relics/init/RemoteRegistry.java) |
| `HUDRenderHandler` | 4 | `it.hurts.sskirillss.relics.system.casts.handlers` | [HUDRenderHandler.java](it/hurts/sskirillss/relics/system/casts/handlers/HUDRenderHandler.java) |
| `ArmorRenderHandler` | 3 | `it.hurts.sskirillss.relics.client.handlers` | [ArmorRenderHandler.java](it/hurts/sskirillss/relics/client/handlers/ArmorRenderHandler.java) |
| `HolyLocketItem` | 3 | `it.hurts.sskirillss.relics.items.relics.necklace` | [HolyLocketItem.java](it/hurts/sskirillss/relics/items/relics/necklace/HolyLocketItem.java) |
| `DrownedBeltItem` | 3 | `it.hurts.sskirillss.relics.items.relics.belt` | [DrownedBeltItem.java](it/hurts/sskirillss/relics/items/relics/belt/DrownedBeltItem.java) |
| `StunEffect` | 3 | `it.hurts.sskirillss.relics.effects` | [StunEffect.java](it/hurts/sskirillss/relics/effects/StunEffect.java) |
| `DescriptionHandler` | 2 | `it.hurts.sskirillss.relics.client.handlers` | [DescriptionHandler.java](it/hurts/sskirillss/relics/client/handlers/DescriptionHandler.java) |
| `TooltipBorderHandler` | 2 | `it.hurts.sskirillss.relics.client.tooltip` | [TooltipBorderHandler.java](it/hurts/sskirillss/relics/client/tooltip/TooltipBorderHandler.java) |
| `MagmaWalkerItem` | 2 | `it.hurts.sskirillss.relics.items.relics.feet` | [MagmaWalkerItem.java](it/hurts/sskirillss/relics/items/relics/feet/MagmaWalkerItem.java) |
| `InfiniteHamItem` | 2 | `it.hurts.sskirillss.relics.items.relics` | [InfiniteHamItem.java](it/hurts/sskirillss/relics/items/relics/InfiniteHamItem.java) |
| `MagicMirrorItem` | 2 | `it.hurts.sskirillss.relics.items.relics` | [MagicMirrorItem.java](it/hurts/sskirillss/relics/items/relics/MagicMirrorItem.java) |
| `RollerSkatesItem` | 2 | `it.hurts.sskirillss.relics.items.relics.feet` | [RollerSkatesItem.java](it/hurts/sskirillss/relics/items/relics/feet/RollerSkatesItem.java) |
| `VanishingEffect` | 2 | `it.hurts.sskirillss.relics.effects` | [VanishingEffect.java](it/hurts/sskirillss/relics/effects/VanishingEffect.java) |
| `NetworkHandler` | 1 | `it.hurts.sskirillss.relics.network` | [NetworkHandler.java](it/hurts/sskirillss/relics/network/NetworkHandler.java) |
| `CommandRegistry` | 1 | `it.hurts.sskirillss.relics.init` | [CommandRegistry.java](it/hurts/sskirillss/relics/init/CommandRegistry.java) |
| `DataComponentRegistry` | 1 | `it.hurts.sskirillss.relics.init` | [DataComponentRegistry.java](it/hurts/sskirillss/relics/init/DataComponentRegistry.java) |
| `ParticleRegistry` | 1 | `it.hurts.sskirillss.relics.init` | [ParticleRegistry.java](it/hurts/sskirillss/relics/init/ParticleRegistry.java) |
| `HotkeyRegistry` | 1 | `it.hurts.sskirillss.relics.init` | [HotkeyRegistry.java](it/hurts/sskirillss/relics/init/HotkeyRegistry.java) |
| `CreativeTabRegistry` | 1 | `it.hurts.sskirillss.relics.init` | [CreativeTabRegistry.java](it/hurts/sskirillss/relics/init/CreativeTabRegistry.java) |
| `RegistryRegistry` | 1 | `it.hurts.sskirillss.relics.init` | [RegistryRegistry.java](it/hurts/sskirillss/relics/init/RegistryRegistry.java) |
| `CacheHandler` | 1 | `it.hurts.sskirillss.relics.system.casts.handlers` | [CacheHandler.java](it/hurts/sskirillss/relics/system/casts/handlers/CacheHandler.java) |
| `HandRenderHandler` | 1 | `it.hurts.sskirillss.relics.client.handlers` | [HandRenderHandler.java](it/hurts/sskirillss/relics/client/handlers/HandRenderHandler.java) |
| `AquaWalkerItem` | 1 | `it.hurts.sskirillss.relics.items.relics.feet` | [AquaWalkerItem.java](it/hurts/sskirillss/relics/items/relics/feet/AquaWalkerItem.java) |
| `ElytraBoosterItem` | 1 | `it.hurts.sskirillss.relics.items.relics.back` | [ElytraBoosterItem.java](it/hurts/sskirillss/relics/items/relics/back/ElytraBoosterItem.java) |
| `LeatherBeltItem` | 1 | `it.hurts.sskirillss.relics.items.relics.belt` | [LeatherBeltItem.java](it/hurts/sskirillss/relics/items/relics/belt/LeatherBeltItem.java) |
| `RelicLootModifier` | 1 | `it.hurts.sskirillss.relics.level` | [RelicLootModifier.java](it/hurts/sskirillss/relics/level/RelicLootModifier.java) |
| `RelicsCoreShaders` | 1 | `it.hurts.sskirillss.relics.init` | [RelicsCoreShaders.java](it/hurts/sskirillss/relics/init/RelicsCoreShaders.java) |
| `RageGloveItem` | 1 | `it.hurts.sskirillss.relics.items.relics.hands` | [RageGloveItem.java](it/hurts/sskirillss/relics/items/relics/hands/RageGloveItem.java) |
| `AmphibianBootItem` | 1 | `it.hurts.sskirillss.relics.items.relics.feet` | [AmphibianBootItem.java](it/hurts/sskirillss/relics/items/relics/feet/AmphibianBootItem.java) |
| `ReflectionNecklaceItem` | 1 | `it.hurts.sskirillss.relics.items.relics.necklace` | [ReflectionNecklaceItem.java](it/hurts/sskirillss/relics/items/relics/necklace/ReflectionNecklaceItem.java) |
| `MidnightRobeItem` | 1 | `it.hurts.sskirillss.relics.items.relics.back` | [MidnightRobeItem.java](it/hurts/sskirillss/relics/items/relics/back/MidnightRobeItem.java) |
| `IceBreakerItem` | 1 | `it.hurts.sskirillss.relics.items.relics.feet` | [IceBreakerItem.java](it/hurts/sskirillss/relics/items/relics/feet/IceBreakerItem.java) |
| `HunterBeltItem` | 1 | `it.hurts.sskirillss.relics.items.relics.belt` | [HunterBeltItem.java](it/hurts/sskirillss/relics/items/relics/belt/HunterBeltItem.java) |
| `ShadowGlaiveItem` | 1 | `it.hurts.sskirillss.relics.items.relics` | [ShadowGlaiveItem.java](it/hurts/sskirillss/relics/items/relics/ShadowGlaiveItem.java) |
| `WoolMittenItem` | 1 | `it.hurts.sskirillss.relics.items.relics.hands` | [WoolMittenItem.java](it/hurts/sskirillss/relics/items/relics/hands/WoolMittenItem.java) |
| `BastionRingItem` | 1 | `it.hurts.sskirillss.relics.items.relics.ring` | [BastionRingItem.java](it/hurts/sskirillss/relics/items/relics/ring/BastionRingItem.java) |
| `ChorusInhibitorItem` | 1 | `it.hurts.sskirillss.relics.items.relics.ring` | [ChorusInhibitorItem.java](it/hurts/sskirillss/relics/items/relics/ring/ChorusInhibitorItem.java) |
| `ParalysisEffect` | 1 | `it.hurts.sskirillss.relics.effects` | [ParalysisEffect.java](it/hurts/sskirillss/relics/effects/ParalysisEffect.java) |
| `BleedingEffect` | 1 | `it.hurts.sskirillss.relics.effects` | [BleedingEffect.java](it/hurts/sskirillss/relics/effects/BleedingEffect.java) |
| `AntiHealEffect` | 1 | `it.hurts.sskirillss.relics.effects` | [AntiHealEffect.java](it/hurts/sskirillss/relics/effects/AntiHealEffect.java) |

## Mixin（19 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `MouseHandlerMixin` | `MouseHandler` | [MouseHandlerMixin.java](it/hurts/sskirillss/relics/mixin/MouseHandlerMixin.java) |
| `KeyboardHandlerMixin` | `KeyboardHandler` | [KeyboardHandlerMixin.java](it/hurts/sskirillss/relics/mixin/KeyboardHandlerMixin.java) |
| `LivingEntityMixin` | `LivingEntity` | [LivingEntityMixin.java](it/hurts/sskirillss/relics/mixin/LivingEntityMixin.java) |
| `AbstractContainerMenuMixin` | `AbstractContainerMenu` | [AbstractContainerMenuMixin.java](it/hurts/sskirillss/relics/mixin/AbstractContainerMenuMixin.java) |
| `EntityMixin` | `Entity` | [EntityMixin.java](it/hurts/sskirillss/relics/mixin/EntityMixin.java) |
| `ItemMixin` | `Item` | [ItemMixin.java](it/hurts/sskirillss/relics/mixin/ItemMixin.java) |
| `PowderSnowBlockMixin` | `PowderSnowBlock` | [PowderSnowBlockMixin.java](it/hurts/sskirillss/relics/mixin/PowderSnowBlockMixin.java) |
| `BlockMixin` | `Block` | [BlockMixin.java](it/hurts/sskirillss/relics/mixin/BlockMixin.java) |
| `ScreenMixin` | `Screen` | [ScreenMixin.java](it/hurts/sskirillss/relics/mixin/ScreenMixin.java) |
| `GameDataMixin` | `GameData` | [GameDataMixin.java](it/hurts/sskirillss/relics/mixin/GameDataMixin.java) |
| `WindowMixin` | `Window` | [WindowMixin.java](it/hurts/sskirillss/relics/mixin/WindowMixin.java) |
| `CameraMixin` | `Camera` | [CameraMixin.java](it/hurts/sskirillss/relics/mixin/CameraMixin.java) |
| `GuiGraphicsMixin` | `GuiGraphics` | [GuiGraphicsMixin.java](it/hurts/sskirillss/relics/mixin/GuiGraphicsMixin.java) |
| `PiglinAiMixin` | `PiglinAi` | [PiglinAiMixin.java](it/hurts/sskirillss/relics/mixin/PiglinAiMixin.java) |
| `EnderManMixin` | `EnderMan` | [EnderManMixin.java](it/hurts/sskirillss/relics/mixin/EnderManMixin.java) |
| `AbstractChestedHorseMixin` | `AbstractChestedHorse` | [AbstractChestedHorseMixin.java](it/hurts/sskirillss/relics/mixin/AbstractChestedHorseMixin.java) |
| `ParticleEngineMixin` | `ParticleEngine` | [ParticleEngineMixin.java](it/hurts/sskirillss/relics/mixin/ParticleEngineMixin.java) |
| `FeedingUpgradeWrapperMixin` | `FeedingUpgradeWrapper` | [FeedingUpgradeWrapperMixin.java](it/hurts/sskirillss/relics/mixin/compat/sophisticatedbackpacks/FeedingUpgradeWrapperMixin.java) |
| `UpgradeWrapperBaseAccessor` | `UpgradeWrapperBaseAccessor` | [UpgradeWrapperBaseAccessor.java](it/hurts/sskirillss/relics/mixin/compat/sophisticatedbackpacks/UpgradeWrapperBaseAccessor.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `LootEntryConfigData` | — | [LootEntryConfigData.java](it/hurts/sskirillss/relics/config/data/LootEntryConfigData.java) |
| `ConfigRegistry` | — | [ConfigRegistry.java](it/hurts/sskirillss/relics/init/ConfigRegistry.java) |
| `LootConfigData` | — | [LootConfigData.java](it/hurts/sskirillss/relics/config/LootConfigData.java) |
| `LootConfigData` | — | [LootConfigData.java](it/hurts/sskirillss/relics/config/data/LootConfigData.java) |
| `StatConfigData` | — | [StatConfigData.java](it/hurts/sskirillss/relics/config/data/StatConfigData.java) |
| `AbilityConfigData` | — | [AbilityConfigData.java](it/hurts/sskirillss/relics/config/data/AbilityConfigData.java) |
| `RelicConfigData` | — | [RelicConfigData.java](it/hurts/sskirillss/relics/config/data/RelicConfigData.java) |
| `LevelingConfigData` | — | [LevelingConfigData.java](it/hurts/sskirillss/relics/config/data/LevelingConfigData.java) |
| `RelicsConfigData` | — | [RelicsConfigData.java](it/hurts/sskirillss/relics/config/RelicsConfigData.java) |
| `AbilitiesConfigData` | — | [AbilitiesConfigData.java](it/hurts/sskirillss/relics/config/data/AbilitiesConfigData.java) |

## 按包浏览

- `it.hurts.sskirillss.relics`（1 类）
- `it.hurts.sskirillss.relics.api.events.base`（1 类）
- `it.hurts.sskirillss.relics.api.events.common`（5 类）
- `it.hurts.sskirillss.relics.api.events.leveling`（1 类）
- `it.hurts.sskirillss.relics.badges.ability`（8 类）
- `it.hurts.sskirillss.relics.badges.ability.base`（1 类）
- `it.hurts.sskirillss.relics.badges.base`（3 类）
- `it.hurts.sskirillss.relics.badges.relic`（1 类）
- `it.hurts.sskirillss.relics.blocks`（2 类）
- `it.hurts.sskirillss.relics.client.gui.layers`（4 类）
- `it.hurts.sskirillss.relics.client.handlers`（3 类）
- `it.hurts.sskirillss.relics.client.models.effects`（1 类）
- `it.hurts.sskirillss.relics.client.models.entities`（5 类）
- `it.hurts.sskirillss.relics.client.models.items`（4 类）
- `it.hurts.sskirillss.relics.client.models.items.utils`（1 类）
- `it.hurts.sskirillss.relics.client.models.layers`（1 类）
- `it.hurts.sskirillss.relics.client.models.parts`（2 类）
- `it.hurts.sskirillss.relics.client.particles`（1 类）
- `it.hurts.sskirillss.relics.client.renderer.entities`（8 类）
- `it.hurts.sskirillss.relics.client.renderer.items.items`（1 类）
- `it.hurts.sskirillss.relics.client.renderer.tiles`（1 类）
- `it.hurts.sskirillss.relics.client.screen.base`（5 类）
- `it.hurts.sskirillss.relics.client.screen.description.ability`（1 类）
- `it.hurts.sskirillss.relics.client.screen.description.ability.widgets`（7 类）
- `it.hurts.sskirillss.relics.client.screen.description.ability.widgets.base`（1 类）
- `it.hurts.sskirillss.relics.client.screen.description.experience`（1 类）
- `it.hurts.sskirillss.relics.client.screen.description.experience.widgets`（4 类）
- `it.hurts.sskirillss.relics.client.screen.description.experience.widgets.base`（1 类）
- `it.hurts.sskirillss.relics.client.screen.description.general.misc`（1 类）
- `it.hurts.sskirillss.relics.client.screen.description.general.particles.base`（1 类）
- `it.hurts.sskirillss.relics.client.screen.description.general.widgets`（8 类）
- `it.hurts.sskirillss.relics.client.screen.description.general.widgets.base`（3 类）
- `it.hurts.sskirillss.relics.client.screen.description.misc`（3 类）
- `it.hurts.sskirillss.relics.client.screen.description.relic`（1 类）
- `it.hurts.sskirillss.relics.client.screen.description.relic.particles`（3 类）
- `it.hurts.sskirillss.relics.client.screen.description.relic.widgets`（2 类）
- `it.hurts.sskirillss.relics.client.screen.description.research`（1 类）
- `it.hurts.sskirillss.relics.client.screen.description.research.misc`（1 类）
- `it.hurts.sskirillss.relics.client.screen.description.research.particles`（2 类）
- `it.hurts.sskirillss.relics.client.screen.description.research.widgets`（3 类）
- `it.hurts.sskirillss.relics.client.screen.utils`（2 类）
- `it.hurts.sskirillss.relics.client.tooltip`（1 类）
- `it.hurts.sskirillss.relics.commands`（1 类）
- `it.hurts.sskirillss.relics.commands.arguments`（2 类）
- `it.hurts.sskirillss.relics.components`（8 类）
- `it.hurts.sskirillss.relics.config`（2 类）
- `it.hurts.sskirillss.relics.config.data`（7 类）
- `it.hurts.sskirillss.relics.effects`（7 类）
- `it.hurts.sskirillss.relics.entities`（12 类）
- `it.hurts.sskirillss.relics.entities.misc`（1 类）
- `it.hurts.sskirillss.relics.init`（19 类）
- `it.hurts.sskirillss.relics.items`（4 类）
- `it.hurts.sskirillss.relics.items.misc`（2 类）
- `it.hurts.sskirillss.relics.items.relics`（5 类）
- `it.hurts.sskirillss.relics.items.relics.back`（2 类）
- `it.hurts.sskirillss.relics.items.relics.base`（3 类）
- `it.hurts.sskirillss.relics.items.relics.base.data`（4 类）
- `it.hurts.sskirillss.relics.items.relics.base.data.cast`（1 类）
- `it.hurts.sskirillss.relics.items.relics.base.data.cast.containers`（2 类）
- `it.hurts.sskirillss.relics.items.relics.base.data.cast.containers.base`（1 类）
- `it.hurts.sskirillss.relics.items.relics.base.data.cast.misc`（3 类）
- `it.hurts.sskirillss.relics.items.relics.base.data.leveling`（6 类）
- `it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc`（3 类）
- `it.hurts.sskirillss.relics.items.relics.base.data.loot`（2 类）
- `it.hurts.sskirillss.relics.items.relics.base.data.loot.misc`（1 类）
- `it.hurts.sskirillss.relics.items.relics.base.data.research`（2 类）
- `it.hurts.sskirillss.relics.items.relics.base.data.style`（3 类）
- `it.hurts.sskirillss.relics.items.relics.belt`（3 类）
- `it.hurts.sskirillss.relics.items.relics.charm`（1 类）
- `it.hurts.sskirillss.relics.items.relics.feet`（8 类）
- `it.hurts.sskirillss.relics.items.relics.hands`（3 类）
- `it.hurts.sskirillss.relics.items.relics.necklace`（3 类）
- `it.hurts.sskirillss.relics.items.relics.ring`（3 类）
- `it.hurts.sskirillss.relics.level`（1 类）
- `it.hurts.sskirillss.relics.mixin`（17 类）
- `it.hurts.sskirillss.relics.mixin.compat.sophisticatedbackpacks`（2 类）
- `it.hurts.sskirillss.relics.network`（1 类）
- `it.hurts.sskirillss.relics.network.packets`（3 类）
- `it.hurts.sskirillss.relics.network.packets.abilities`（1 类）
- `it.hurts.sskirillss.relics.network.packets.capability`（1 类）
- `it.hurts.sskirillss.relics.network.packets.leveling`（2 类）
- `it.hurts.sskirillss.relics.network.packets.lock`（1 类）
- `it.hurts.sskirillss.relics.network.packets.research`（2 类）
- `it.hurts.sskirillss.relics.network.packets.sync`（2 类）
- `it.hurts.sskirillss.relics.system.casts.abilities`（2 类）
- `it.hurts.sskirillss.relics.system.casts.handlers`（2 类）
- `it.hurts.sskirillss.relics.system.casts.slots`（2 类）
- `it.hurts.sskirillss.relics.system.casts.slots.base`（1 类）
- `it.hurts.sskirillss.relics.tiles`（1 类）
- `it.hurts.sskirillss.relics.tiles.base`（1 类）
- `it.hurts.sskirillss.relics.utils`（10 类）
- `it.hurts.sskirillss.relics.utils.data`（6 类）
