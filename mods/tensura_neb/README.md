# TenSura: Not Enough Bosses（`tensura_neb`）

> jar：`[转生附属]tensura_neb.jar` ｜ 版本：`2.0.0.3` ｜ 作者：MinhEragon
> 规模：**35 个类 / 12 个包 / 4551 行** ｜ 事件处理类 0 ｜ Mixin 2 ｜ 注册/管理类 3

**依赖**：`neoforge`、`minecraft`、`architectury`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `io.github.manasmods.tensura_neb.neoforge.data` | 10 | `NebRegistryProvider`、`NebEntityLootProvider`、`NebLootProvider` |
| `io.github.manasmods.tensura_neb.entity` | 5 | `LuminousValentineEntity`、`CarrionEntity`、`RimuruOgreFightEntity` |
| `io.github.manasmods.tensura_neb.client.entity` | 5 | `CarrionRenderer`、`LuminousRenderer`、`VeldoraRenderer` |
| `io.github.manasmods.tensura_neb.registry` | 3 | `NebRegistry`、`NebItems`、`NebEntityTypes` |
| `io.github.manasmods.tensura_neb.mixin` | 2 | `MixinSolarBeamProjectile`、`MixinOtherworlder` |
| `io.github.manasmods.tensura_neb` | 2 | `NebConfig`、`TensuraNeb` |
| `io.github.manasmods.tensura_neb.data` | 2 | `NebEntityExistenceData`、`NebGearExistenceData` |
| `io.github.manasmods.tensura_neb.item` | 2 | `NightRoseItem`、`WhiteTigerAzureDragonHalberdItem` |
| `io.github.manasmods.tensura_neb.neoforge` | 1 | `TensuraNebNeoForge` |
| `io.github.manasmods.tensura_neb.handler` | 1 | `NebHandler` |
| `io.github.manasmods.tensura_neb.client` | 1 | `TensuraNebClient` |
| `architectury_inject_tensura_neb_common_78d4a14938824e7f8f11651cbe70e958_0a31dabc4e8557791c0278c5f2dd6c74407265c307abc896805b84f0dea50a85common2003devjar` | 1 | `PlatformMethods` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 35）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `LuminousValentineEntity` | class | OtherworlderEntity | — | 60 | 1058 | [LuminousValentineEntity.java](io/github/manasmods/tensura_neb/entity/LuminousValentineEntity.java) |
| `CarrionEntity` | class | OtherworlderEntity | — | 64 | 962 | [CarrionEntity.java](io/github/manasmods/tensura_neb/entity/CarrionEntity.java) |
| `RimuruOgreFightEntity` | class | OtherworlderEntity | — | 42 | 826 | [RimuruOgreFightEntity.java](io/github/manasmods/tensura_neb/entity/RimuruOgreFightEntity.java) |
| `TensuraNebNeoForge` | class | — | Mod | 1 | 46 | [TensuraNebNeoForge.java](io/github/manasmods/tensura_neb/neoforge/TensuraNebNeoForge.java) |
| `NebRegistryProvider` | class | DatapackBuiltinEntriesProvider | — | 0 | 22 | [NebRegistryProvider.java](io/github/manasmods/tensura_neb/neoforge/data/NebRegistryProvider.java) |
| `MixinSolarBeamProjectile` | class | — | Mixin | 1 | 23 | [MixinSolarBeamProjectile.java](io/github/manasmods/tensura_neb/mixin/MixinSolarBeamProjectile.java) |
| `MixinOtherworlder` | class | OtherworlderEntity | Mixin | 2 | 81 | [MixinOtherworlder.java](io/github/manasmods/tensura_neb/mixin/MixinOtherworlder.java) |
| `NebRegistry` | class | — | — | 1 | 9 | [NebRegistry.java](io/github/manasmods/tensura_neb/registry/NebRegistry.java) |
| `NebHandler` | class | — | — | 1 | 20 | [NebHandler.java](io/github/manasmods/tensura_neb/handler/NebHandler.java) |
| `NebEntityLootProvider` | class | EntityLootSubProvider | — | 2 | 74 | [NebEntityLootProvider.java](io/github/manasmods/tensura_neb/neoforge/data/NebEntityLootProvider.java) |
| `NebConfig` | class | ManasConfig | — | 1 | 18 | [NebConfig.java](io/github/manasmods/tensura_neb/NebConfig.java) |
| `VeldoraEntity` | class | OtherworlderEntity | — | 9 | 94 | [VeldoraEntity.java](io/github/manasmods/tensura_neb/entity/VeldoraEntity.java) |
| `NebLootProvider` | class | LootTableProvider | — | 0 | 17 | [NebLootProvider.java](io/github/manasmods/tensura_neb/neoforge/data/NebLootProvider.java) |
| `NebEntityExistenceData` | class | — | — | 2 | 116 | [NebEntityExistenceData.java](io/github/manasmods/tensura_neb/data/NebEntityExistenceData.java) |
| `CarrionRenderer` | class | GeoEntityRenderer | — | 5 | 103 | [CarrionRenderer.java](io/github/manasmods/tensura_neb/client/entity/CarrionRenderer.java) |
| `LuminousRenderer` | class | PlayerLikeRenderer | — | 7 | 148 | [LuminousRenderer.java](io/github/manasmods/tensura_neb/client/entity/LuminousRenderer.java) |
| `RimuruCloneEntity` | class | RimuruOgreFightEntity | — | 3 | 134 | [RimuruCloneEntity.java](io/github/manasmods/tensura_neb/entity/RimuruCloneEntity.java) |
| `NebEntityTypeTagProvider` | class | EntityTypeTagsProvider | — | 1 | 133 | [NebEntityTypeTagProvider.java](io/github/manasmods/tensura_neb/neoforge/data/NebEntityTypeTagProvider.java) |
| `NebBlockTagProvider` | class | BlockTagsProvider | — | 1 | 18 | [NebBlockTagProvider.java](io/github/manasmods/tensura_neb/neoforge/data/NebBlockTagProvider.java) |
| `NebItemModelProvider` | class | ItemModelProvider | — | 1 | 20 | [NebItemModelProvider.java](io/github/manasmods/tensura_neb/neoforge/data/NebItemModelProvider.java) |
| `NebItemTagProvider` | class | ItemTagsProvider | — | 1 | 23 | [NebItemTagProvider.java](io/github/manasmods/tensura_neb/neoforge/data/NebItemTagProvider.java) |
| `NightRoseItem` | class | TensuraSwordItem | — | 1 | 28 | [NightRoseItem.java](io/github/manasmods/tensura_neb/item/NightRoseItem.java) |
| `WhiteTigerAzureDragonHalberdItem` | class | TwoHandedSwordItem | — | 1 | 34 | [WhiteTigerAzureDragonHalberdItem.java](io/github/manasmods/tensura_neb/item/WhiteTigerAzureDragonHalberdItem.java) |
| `NebItems` | class | — | — | 1 | 53 | [NebItems.java](io/github/manasmods/tensura_neb/registry/NebItems.java) |
| `NebEntityTypes` | class | — | — | 1 | 55 | [NebEntityTypes.java](io/github/manasmods/tensura_neb/registry/NebEntityTypes.java) |
| `TensuraNebClient` | class | — | — | 4 | 36 | [TensuraNebClient.java](io/github/manasmods/tensura_neb/client/TensuraNebClient.java) |
| `VeldoraRenderer` | class | GeoEntityRenderer | — | 2 | 41 | [VeldoraRenderer.java](io/github/manasmods/tensura_neb/client/entity/VeldoraRenderer.java) |
| `NebAdvancementGenerator` | class | AdvancementGenerator | — | 5 | 173 | [NebAdvancementGenerator.java](io/github/manasmods/tensura_neb/neoforge/data/NebAdvancementGenerator.java) |
| `NebGearExistenceData` | class | — | — | 2 | 28 | [NebGearExistenceData.java](io/github/manasmods/tensura_neb/data/NebGearExistenceData.java) |
| `PlatformMethods` | class | — | — | 1 | 8 | [PlatformMethods.java](architectury_inject_tensura_neb_common_78d4a14938824e7f8f11651cbe70e958_0a31dabc4e8557791c0278c5f2dd6c74407265c307abc896805b84f0dea50a85common2003devjar/PlatformMethods.java) |
| `TensuraNeb` | class | — | — | 2 | 39 | [TensuraNeb.java](io/github/manasmods/tensura_neb/TensuraNeb.java) |
| `NebAdvancementProvider` | class | AdvancementProvider | — | 0 | 15 | [NebAdvancementProvider.java](io/github/manasmods/tensura_neb/neoforge/data/NebAdvancementProvider.java) |
| `NebLanguageProvider` | class | LanguageProvider | — | 3 | 40 | [NebLanguageProvider.java](io/github/manasmods/tensura_neb/neoforge/data/NebLanguageProvider.java) |
| `VeldoraModel` | class | TensuraEntityGeoModel | — | 2 | 21 | [VeldoraModel.java](io/github/manasmods/tensura_neb/client/entity/VeldoraModel.java) |
| `CarrionModel` | class | TensuraEntityGeoModel | — | 3 | 35 | [CarrionModel.java](io/github/manasmods/tensura_neb/client/entity/CarrionModel.java) |

## Mixin（2 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `MixinSolarBeamProjectile` | `SolarBeamProjectile` | [MixinSolarBeamProjectile.java](io/github/manasmods/tensura_neb/mixin/MixinSolarBeamProjectile.java) |
| `MixinOtherworlder` | `Otherworlder` | [MixinOtherworlder.java](io/github/manasmods/tensura_neb/mixin/MixinOtherworlder.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `NebConfig` | — | [NebConfig.java](io/github/manasmods/tensura_neb/NebConfig.java) |

## 全部类（35）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `LuminousValentineEntity` | class | OtherworlderEntity | — | 60 | 1058 | [LuminousValentineEntity.java](io/github/manasmods/tensura_neb/entity/LuminousValentineEntity.java) |
| `CarrionEntity` | class | OtherworlderEntity | — | 64 | 962 | [CarrionEntity.java](io/github/manasmods/tensura_neb/entity/CarrionEntity.java) |
| `RimuruOgreFightEntity` | class | OtherworlderEntity | — | 42 | 826 | [RimuruOgreFightEntity.java](io/github/manasmods/tensura_neb/entity/RimuruOgreFightEntity.java) |
| `TensuraNebNeoForge` | class | — | Mod | 1 | 46 | [TensuraNebNeoForge.java](io/github/manasmods/tensura_neb/neoforge/TensuraNebNeoForge.java) |
| `NebRegistryProvider` | class | DatapackBuiltinEntriesProvider | — | 0 | 22 | [NebRegistryProvider.java](io/github/manasmods/tensura_neb/neoforge/data/NebRegistryProvider.java) |
| `MixinSolarBeamProjectile` | class | — | Mixin | 1 | 23 | [MixinSolarBeamProjectile.java](io/github/manasmods/tensura_neb/mixin/MixinSolarBeamProjectile.java) |
| `MixinOtherworlder` | class | OtherworlderEntity | Mixin | 2 | 81 | [MixinOtherworlder.java](io/github/manasmods/tensura_neb/mixin/MixinOtherworlder.java) |
| `NebRegistry` | class | — | — | 1 | 9 | [NebRegistry.java](io/github/manasmods/tensura_neb/registry/NebRegistry.java) |
| `NebHandler` | class | — | — | 1 | 20 | [NebHandler.java](io/github/manasmods/tensura_neb/handler/NebHandler.java) |
| `NebEntityLootProvider` | class | EntityLootSubProvider | — | 2 | 74 | [NebEntityLootProvider.java](io/github/manasmods/tensura_neb/neoforge/data/NebEntityLootProvider.java) |
| `NebConfig` | class | ManasConfig | — | 1 | 18 | [NebConfig.java](io/github/manasmods/tensura_neb/NebConfig.java) |
| `VeldoraEntity` | class | OtherworlderEntity | — | 9 | 94 | [VeldoraEntity.java](io/github/manasmods/tensura_neb/entity/VeldoraEntity.java) |
| `NebLootProvider` | class | LootTableProvider | — | 0 | 17 | [NebLootProvider.java](io/github/manasmods/tensura_neb/neoforge/data/NebLootProvider.java) |
| `NebEntityExistenceData` | class | — | — | 2 | 116 | [NebEntityExistenceData.java](io/github/manasmods/tensura_neb/data/NebEntityExistenceData.java) |
| `CarrionRenderer` | class | GeoEntityRenderer | — | 5 | 103 | [CarrionRenderer.java](io/github/manasmods/tensura_neb/client/entity/CarrionRenderer.java) |
| `LuminousRenderer` | class | PlayerLikeRenderer | — | 7 | 148 | [LuminousRenderer.java](io/github/manasmods/tensura_neb/client/entity/LuminousRenderer.java) |
| `RimuruCloneEntity` | class | RimuruOgreFightEntity | — | 3 | 134 | [RimuruCloneEntity.java](io/github/manasmods/tensura_neb/entity/RimuruCloneEntity.java) |
| `NebEntityTypeTagProvider` | class | EntityTypeTagsProvider | — | 1 | 133 | [NebEntityTypeTagProvider.java](io/github/manasmods/tensura_neb/neoforge/data/NebEntityTypeTagProvider.java) |
| `NebBlockTagProvider` | class | BlockTagsProvider | — | 1 | 18 | [NebBlockTagProvider.java](io/github/manasmods/tensura_neb/neoforge/data/NebBlockTagProvider.java) |
| `NebItemModelProvider` | class | ItemModelProvider | — | 1 | 20 | [NebItemModelProvider.java](io/github/manasmods/tensura_neb/neoforge/data/NebItemModelProvider.java) |
| `NebItemTagProvider` | class | ItemTagsProvider | — | 1 | 23 | [NebItemTagProvider.java](io/github/manasmods/tensura_neb/neoforge/data/NebItemTagProvider.java) |
| `NightRoseItem` | class | TensuraSwordItem | — | 1 | 28 | [NightRoseItem.java](io/github/manasmods/tensura_neb/item/NightRoseItem.java) |
| `WhiteTigerAzureDragonHalberdItem` | class | TwoHandedSwordItem | — | 1 | 34 | [WhiteTigerAzureDragonHalberdItem.java](io/github/manasmods/tensura_neb/item/WhiteTigerAzureDragonHalberdItem.java) |
| `NebItems` | class | — | — | 1 | 53 | [NebItems.java](io/github/manasmods/tensura_neb/registry/NebItems.java) |
| `NebEntityTypes` | class | — | — | 1 | 55 | [NebEntityTypes.java](io/github/manasmods/tensura_neb/registry/NebEntityTypes.java) |
| `TensuraNebClient` | class | — | — | 4 | 36 | [TensuraNebClient.java](io/github/manasmods/tensura_neb/client/TensuraNebClient.java) |
| `VeldoraRenderer` | class | GeoEntityRenderer | — | 2 | 41 | [VeldoraRenderer.java](io/github/manasmods/tensura_neb/client/entity/VeldoraRenderer.java) |
| `NebAdvancementGenerator` | class | AdvancementGenerator | — | 5 | 173 | [NebAdvancementGenerator.java](io/github/manasmods/tensura_neb/neoforge/data/NebAdvancementGenerator.java) |
| `NebGearExistenceData` | class | — | — | 2 | 28 | [NebGearExistenceData.java](io/github/manasmods/tensura_neb/data/NebGearExistenceData.java) |
| `PlatformMethods` | class | — | — | 1 | 8 | [PlatformMethods.java](architectury_inject_tensura_neb_common_78d4a14938824e7f8f11651cbe70e958_0a31dabc4e8557791c0278c5f2dd6c74407265c307abc896805b84f0dea50a85common2003devjar/PlatformMethods.java) |
| `TensuraNeb` | class | — | — | 2 | 39 | [TensuraNeb.java](io/github/manasmods/tensura_neb/TensuraNeb.java) |
| `NebAdvancementProvider` | class | AdvancementProvider | — | 0 | 15 | [NebAdvancementProvider.java](io/github/manasmods/tensura_neb/neoforge/data/NebAdvancementProvider.java) |
| `NebLanguageProvider` | class | LanguageProvider | — | 3 | 40 | [NebLanguageProvider.java](io/github/manasmods/tensura_neb/neoforge/data/NebLanguageProvider.java) |
| `VeldoraModel` | class | TensuraEntityGeoModel | — | 2 | 21 | [VeldoraModel.java](io/github/manasmods/tensura_neb/client/entity/VeldoraModel.java) |
| `CarrionModel` | class | TensuraEntityGeoModel | — | 3 | 35 | [CarrionModel.java](io/github/manasmods/tensura_neb/client/entity/CarrionModel.java) |
