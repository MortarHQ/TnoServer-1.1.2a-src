# Tiny Skeletons（`tinyskeletons`）

> jar：`[小骷髅变种]TinySkeletons.jar` ｜ 版本：`21.1.0` ｜ 作者：Fuzs
> 规模：**30 个类 / 20 个包 / 1686 行** ｜ 事件处理类 0 ｜ Mixin 1 ｜ 注册/管理类 3

**官方描述**：Teeny, tiny skeletons, send shivers down your spine...

**依赖**：`neoforge`、`minecraft`、`puzzleslib`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `fuzs.tinyskeletons.world.entity.monster` | 5 | `BabySkeleton`、`BabyWitherSkeleton`、`BabyStray` |
| `fuzs.tinyskeletons.client.renderer.entity` | 4 | `BabySkeletonRenderer`、`BabyStrayRenderer`、`BabyBoggedRenderer` |
| `fuzs.tinyskeletons.client.renderer.entity.layers` | 3 | `BackItemLayer`、`MainHandItemLayer`、`HeldSkullItemLayer` |
| `fuzs.tinyskeletons.data` | 2 | `ModEntityTypeLootProvider`、`ModEntityTypeTagProvider` |
| `fuzs.tinyskeletons.neoforge.client` | 1 | `TinySkeletonsNeoForgeClient` |
| `fuzs.tinyskeletons.neoforge` | 1 | `TinySkeletonsNeoForge` |
| `fuzs.tinyskeletons.mixin.accessor` | 1 | `LivingEntityAccessor` |
| `fuzs.tinyskeletons.client.init` | 1 | `ModClientRegistry` |
| `fuzs.tinyskeletons.handler` | 1 | `BabyConversionHandler` |
| `fuzs.tinyskeletons.init` | 1 | `ModRegistry` |
| `fuzs.tinyskeletons.neoforge.client.core` | 1 | `NeoForgeClientAbstractions` |
| `fuzs.tinyskeletons.client` | 1 | `TinySkeletonsClient` |
| `fuzs.tinyskeletons.client.core` | 1 | `ClientAbstractions` |
| `fuzs.tinyskeletons` | 1 | `TinySkeletons` |
| `fuzs.tinyskeletons.world.entity.monster.projectile` | 1 | `Mushroom` |
| `architectury_inject_TinySkeletons1211_Common_e7d3546990b141f2a072ba760e495783_a1b95f897459aacbca755ff3df6345a3ed9cbb0c0e9b8f1d4048c07a79e890a6TinySkeletonsv21101211Commondevjar` | 1 | `PlatformMethods` |
| `fuzs.tinyskeletons.data.client` | 1 | `ModLanguageProvider` |
| `fuzs.tinyskeletons.world.entity.ai.goal` | 1 | `RangedBowEasyAttackGoal` |
| `fuzs.tinyskeletons.client.packs` | 1 | `BabySkeletonPackResources` |
| `fuzs.tinyskeletons.client.model` | 1 | `SkullCarryingSkeletonModel` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 30）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `TinySkeletonsNeoForgeClient` | class | — | Mod | 0 | 18 | [TinySkeletonsNeoForgeClient.java](fuzs/tinyskeletons/neoforge/client/TinySkeletonsNeoForgeClient.java) |
| `TinySkeletonsNeoForge` | class | — | Mod | 0 | 18 | [TinySkeletonsNeoForge.java](fuzs/tinyskeletons/neoforge/TinySkeletonsNeoForge.java) |
| `LivingEntityAccessor` | interface | — | Mixin | 0 | 12 | [LivingEntityAccessor.java](fuzs/tinyskeletons/mixin/accessor/LivingEntityAccessor.java) |
| `ModClientRegistry` | class | — | — | 0 | 23 | [ModClientRegistry.java](fuzs/tinyskeletons/client/init/ModClientRegistry.java) |
| `BabyConversionHandler` | class | — | — | 5 | 102 | [BabyConversionHandler.java](fuzs/tinyskeletons/handler/BabyConversionHandler.java) |
| `ModRegistry` | class | — | — | 1 | 49 | [ModRegistry.java](fuzs/tinyskeletons/init/ModRegistry.java) |
| `ModEntityTypeLootProvider` | class | EntityTypes | — | 1 | 131 | [ModEntityTypeLootProvider.java](fuzs/tinyskeletons/data/ModEntityTypeLootProvider.java) |
| `NeoForgeClientAbstractions` | class | ClientAbstractions | — | 1 | 20 | [NeoForgeClientAbstractions.java](fuzs/tinyskeletons/neoforge/client/core/NeoForgeClientAbstractions.java) |
| `ModEntityTypeTagProvider` | class | AbstractTagProvider | — | 1 | 30 | [ModEntityTypeTagProvider.java](fuzs/tinyskeletons/data/ModEntityTypeTagProvider.java) |
| `BabySkeleton` | class | Skeleton | — | 11 | 133 | [BabySkeleton.java](fuzs/tinyskeletons/world/entity/monster/BabySkeleton.java) |
| `BabyWitherSkeleton` | class | WitherSkeleton | — | 11 | 118 | [BabyWitherSkeleton.java](fuzs/tinyskeletons/world/entity/monster/BabyWitherSkeleton.java) |
| `BabyStray` | class | Stray | — | 10 | 85 | [BabyStray.java](fuzs/tinyskeletons/world/entity/monster/BabyStray.java) |
| `TinySkeletonsClient` | class | ClientModConstructor | — | 3 | 66 | [TinySkeletonsClient.java](fuzs/tinyskeletons/client/TinySkeletonsClient.java) |
| `ClientAbstractions` | interface | — | — | 0 | 14 | [ClientAbstractions.java](fuzs/tinyskeletons/client/core/ClientAbstractions.java) |
| `BabySkeletonRenderer` | class | MobRenderer | — | 2 | 45 | [BabySkeletonRenderer.java](fuzs/tinyskeletons/client/renderer/entity/BabySkeletonRenderer.java) |
| `BabyStrayRenderer` | class | SkeletonRenderer | — | 1 | 24 | [BabyStrayRenderer.java](fuzs/tinyskeletons/client/renderer/entity/BabyStrayRenderer.java) |
| `BabyBoggedRenderer` | class | SkeletonRenderer | — | 1 | 24 | [BabyBoggedRenderer.java](fuzs/tinyskeletons/client/renderer/entity/BabyBoggedRenderer.java) |
| `BabyWitherSkeletonRenderer` | class | MobRenderer | — | 2 | 54 | [BabyWitherSkeletonRenderer.java](fuzs/tinyskeletons/client/renderer/entity/BabyWitherSkeletonRenderer.java) |
| `BackItemLayer` | class | — | — | 1 | 67 | [BackItemLayer.java](fuzs/tinyskeletons/client/renderer/entity/layers/BackItemLayer.java) |
| `MainHandItemLayer` | class | — | — | 2 | 79 | [MainHandItemLayer.java](fuzs/tinyskeletons/client/renderer/entity/layers/MainHandItemLayer.java) |
| `HeldSkullItemLayer` | class | — | — | 2 | 57 | [HeldSkullItemLayer.java](fuzs/tinyskeletons/client/renderer/entity/layers/HeldSkullItemLayer.java) |
| `TinySkeletons` | class | ModConstructor | — | 6 | 94 | [TinySkeletons.java](fuzs/tinyskeletons/TinySkeletons.java) |
| `BabyBogged` | class | Bogged | — | 8 | 67 | [BabyBogged.java](fuzs/tinyskeletons/world/entity/monster/BabyBogged.java) |
| `Mushroom` | class | ThrowableItemProjectile | — | 5 | 62 | [Mushroom.java](fuzs/tinyskeletons/world/entity/monster/projectile/Mushroom.java) |
| `PlatformMethods` | class | — | — | 1 | 8 | [PlatformMethods.java](architectury_inject_TinySkeletons1211_Common_e7d3546990b141f2a072ba760e495783_a1b95f897459aacbca755ff3df6345a3ed9cbb0c0e9b8f1d4048c07a79e890a6TinySkeletonsv21101211Commondevjar/PlatformMethods.java) |
| `ModLanguageProvider` | class | AbstractLanguageProvider | — | 1 | 21 | [ModLanguageProvider.java](fuzs/tinyskeletons/data/client/ModLanguageProvider.java) |
| `RangedBowEasyAttackGoal` | class | RangedBowAttackGoal | — | 3 | 89 | [RangedBowEasyAttackGoal.java](fuzs/tinyskeletons/world/entity/ai/goal/RangedBowEasyAttackGoal.java) |
| `SkullCarryingMob` | interface | — | — | 0 | 27 | [SkullCarryingMob.java](fuzs/tinyskeletons/world/entity/monster/SkullCarryingMob.java) |
| `BabySkeletonPackResources` | class | AbstractModPackResources | — | 2 | 107 | [BabySkeletonPackResources.java](fuzs/tinyskeletons/client/packs/BabySkeletonPackResources.java) |
| `SkullCarryingSkeletonModel` | class | SkeletonModel | — | 1 | 42 | [SkullCarryingSkeletonModel.java](fuzs/tinyskeletons/client/model/SkullCarryingSkeletonModel.java) |

## Mixin（1 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `LivingEntityAccessor` | `LivingEntityAccessor` | [LivingEntityAccessor.java](fuzs/tinyskeletons/mixin/accessor/LivingEntityAccessor.java) |

## 全部类（30）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `TinySkeletonsNeoForgeClient` | class | — | Mod | 0 | 18 | [TinySkeletonsNeoForgeClient.java](fuzs/tinyskeletons/neoforge/client/TinySkeletonsNeoForgeClient.java) |
| `TinySkeletonsNeoForge` | class | — | Mod | 0 | 18 | [TinySkeletonsNeoForge.java](fuzs/tinyskeletons/neoforge/TinySkeletonsNeoForge.java) |
| `LivingEntityAccessor` | interface | — | Mixin | 0 | 12 | [LivingEntityAccessor.java](fuzs/tinyskeletons/mixin/accessor/LivingEntityAccessor.java) |
| `ModClientRegistry` | class | — | — | 0 | 23 | [ModClientRegistry.java](fuzs/tinyskeletons/client/init/ModClientRegistry.java) |
| `BabyConversionHandler` | class | — | — | 5 | 102 | [BabyConversionHandler.java](fuzs/tinyskeletons/handler/BabyConversionHandler.java) |
| `ModRegistry` | class | — | — | 1 | 49 | [ModRegistry.java](fuzs/tinyskeletons/init/ModRegistry.java) |
| `ModEntityTypeLootProvider` | class | EntityTypes | — | 1 | 131 | [ModEntityTypeLootProvider.java](fuzs/tinyskeletons/data/ModEntityTypeLootProvider.java) |
| `NeoForgeClientAbstractions` | class | ClientAbstractions | — | 1 | 20 | [NeoForgeClientAbstractions.java](fuzs/tinyskeletons/neoforge/client/core/NeoForgeClientAbstractions.java) |
| `ModEntityTypeTagProvider` | class | AbstractTagProvider | — | 1 | 30 | [ModEntityTypeTagProvider.java](fuzs/tinyskeletons/data/ModEntityTypeTagProvider.java) |
| `BabySkeleton` | class | Skeleton | — | 11 | 133 | [BabySkeleton.java](fuzs/tinyskeletons/world/entity/monster/BabySkeleton.java) |
| `BabyWitherSkeleton` | class | WitherSkeleton | — | 11 | 118 | [BabyWitherSkeleton.java](fuzs/tinyskeletons/world/entity/monster/BabyWitherSkeleton.java) |
| `BabyStray` | class | Stray | — | 10 | 85 | [BabyStray.java](fuzs/tinyskeletons/world/entity/monster/BabyStray.java) |
| `TinySkeletonsClient` | class | ClientModConstructor | — | 3 | 66 | [TinySkeletonsClient.java](fuzs/tinyskeletons/client/TinySkeletonsClient.java) |
| `ClientAbstractions` | interface | — | — | 0 | 14 | [ClientAbstractions.java](fuzs/tinyskeletons/client/core/ClientAbstractions.java) |
| `BabySkeletonRenderer` | class | MobRenderer | — | 2 | 45 | [BabySkeletonRenderer.java](fuzs/tinyskeletons/client/renderer/entity/BabySkeletonRenderer.java) |
| `BabyStrayRenderer` | class | SkeletonRenderer | — | 1 | 24 | [BabyStrayRenderer.java](fuzs/tinyskeletons/client/renderer/entity/BabyStrayRenderer.java) |
| `BabyBoggedRenderer` | class | SkeletonRenderer | — | 1 | 24 | [BabyBoggedRenderer.java](fuzs/tinyskeletons/client/renderer/entity/BabyBoggedRenderer.java) |
| `BabyWitherSkeletonRenderer` | class | MobRenderer | — | 2 | 54 | [BabyWitherSkeletonRenderer.java](fuzs/tinyskeletons/client/renderer/entity/BabyWitherSkeletonRenderer.java) |
| `BackItemLayer` | class | — | — | 1 | 67 | [BackItemLayer.java](fuzs/tinyskeletons/client/renderer/entity/layers/BackItemLayer.java) |
| `MainHandItemLayer` | class | — | — | 2 | 79 | [MainHandItemLayer.java](fuzs/tinyskeletons/client/renderer/entity/layers/MainHandItemLayer.java) |
| `HeldSkullItemLayer` | class | — | — | 2 | 57 | [HeldSkullItemLayer.java](fuzs/tinyskeletons/client/renderer/entity/layers/HeldSkullItemLayer.java) |
| `TinySkeletons` | class | ModConstructor | — | 6 | 94 | [TinySkeletons.java](fuzs/tinyskeletons/TinySkeletons.java) |
| `BabyBogged` | class | Bogged | — | 8 | 67 | [BabyBogged.java](fuzs/tinyskeletons/world/entity/monster/BabyBogged.java) |
| `Mushroom` | class | ThrowableItemProjectile | — | 5 | 62 | [Mushroom.java](fuzs/tinyskeletons/world/entity/monster/projectile/Mushroom.java) |
| `PlatformMethods` | class | — | — | 1 | 8 | [PlatformMethods.java](architectury_inject_TinySkeletons1211_Common_e7d3546990b141f2a072ba760e495783_a1b95f897459aacbca755ff3df6345a3ed9cbb0c0e9b8f1d4048c07a79e890a6TinySkeletonsv21101211Commondevjar/PlatformMethods.java) |
| `ModLanguageProvider` | class | AbstractLanguageProvider | — | 1 | 21 | [ModLanguageProvider.java](fuzs/tinyskeletons/data/client/ModLanguageProvider.java) |
| `RangedBowEasyAttackGoal` | class | RangedBowAttackGoal | — | 3 | 89 | [RangedBowEasyAttackGoal.java](fuzs/tinyskeletons/world/entity/ai/goal/RangedBowEasyAttackGoal.java) |
| `SkullCarryingMob` | interface | — | — | 0 | 27 | [SkullCarryingMob.java](fuzs/tinyskeletons/world/entity/monster/SkullCarryingMob.java) |
| `BabySkeletonPackResources` | class | AbstractModPackResources | — | 2 | 107 | [BabySkeletonPackResources.java](fuzs/tinyskeletons/client/packs/BabySkeletonPackResources.java) |
| `SkullCarryingSkeletonModel` | class | SkeletonModel | — | 1 | 42 | [SkullCarryingSkeletonModel.java](fuzs/tinyskeletons/client/model/SkullCarryingSkeletonModel.java) |
