# Apple Cows（`apple_cows`）

> jar：`[苹果牛]apple_cows.jar` ｜ 版本：`1.0.0+1.21.1-neoforge` ｜ 作者：CraisinLord
> 规模：**10 个类 / 8 个包 / 750 行** ｜ 事件处理类 1 ｜ Mixin 0 ｜ 注册/管理类 0

**官方描述**：Adds cute little apple cows!! Standalone from the Antarchy mod.

**依赖**：`minecraft`、`geckolib`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.craisinlord.apple_cows.content.entity` | 2 | `AppleCowEntity`、`AppleCowEntityVariants` |
| `com.craisinlord.apple_cows.neoforge.registry` | 2 | `AppleCowsNeoforgeItems`、`AppleCowsNeoforgeEntities` |
| `com.craisinlord.apple_cows.neoforge.client` | 1 | `AppleCowsNeoForgeClient` |
| `com.craisinlord.apple_cows.neoforge` | 1 | `AppleCowsNeoforge` |
| `com.craisinlord.apple_cows.content.client.renderer` | 1 | `AppleCowRenderer` |
| `architectury_inject_AppleCows_common_dac55d1c3d7c43d0b24fcf81e4608720_5735218e23cbe9531c578f085ae81bacee6468ecde68b2198194d0bbf02c767eapple_cows1001211commondevjar` | 1 | `PlatformMethods` |
| `com.craisinlord.apple_cows` | 1 | `AppleCows` |
| `com.craisinlord.apple_cows.content.client.model` | 1 | `AppleCowModel` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 10）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `AppleCowsNeoForgeClient` | class | — | EventBusSubscriber | 1 | 21 | [AppleCowsNeoForgeClient.java](com/craisinlord/apple_cows/neoforge/client/AppleCowsNeoForgeClient.java) |
| `AppleCowsNeoforge` | class | — | Mod | 3 | 69 | [AppleCowsNeoforge.java](com/craisinlord/apple_cows/neoforge/AppleCowsNeoforge.java) |
| `AppleCowEntity` | class | Cow | — | 38 | 399 | [AppleCowEntity.java](com/craisinlord/apple_cows/content/entity/AppleCowEntity.java) |
| `AppleCowsNeoforgeItems` | class | — | — | 1 | 30 | [AppleCowsNeoforgeItems.java](com/craisinlord/apple_cows/neoforge/registry/AppleCowsNeoforgeItems.java) |
| `AppleCowEntityVariants` | class | AppleCowEntity | — | 0 | 29 | [AppleCowEntityVariants.java](com/craisinlord/apple_cows/content/entity/AppleCowEntityVariants.java) |
| `AppleCowRenderer` | class | GeoEntityRenderer | — | 3 | 80 | [AppleCowRenderer.java](com/craisinlord/apple_cows/content/client/renderer/AppleCowRenderer.java) |
| `PlatformMethods` | class | — | — | 1 | 8 | [PlatformMethods.java](architectury_inject_AppleCows_common_dac55d1c3d7c43d0b24fcf81e4608720_5735218e23cbe9531c578f085ae81bacee6468ecde68b2198194d0bbf02c767eapple_cows1001211commondevjar/PlatformMethods.java) |
| `AppleCows` | class | — | — | 1 | 13 | [AppleCows.java](com/craisinlord/apple_cows/AppleCows.java) |
| `AppleCowsNeoforgeEntities` | class | — | — | 2 | 37 | [AppleCowsNeoforgeEntities.java](com/craisinlord/apple_cows/neoforge/registry/AppleCowsNeoforgeEntities.java) |
| `AppleCowModel` | class | GeoModel | — | 4 | 64 | [AppleCowModel.java](com/craisinlord/apple_cows/content/client/model/AppleCowModel.java) |

## 事件处理（`@SubscribeEvent`，1 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `AppleCowsNeoForgeClient` | 1 | `com.craisinlord.apple_cows.neoforge.client` | [AppleCowsNeoForgeClient.java](com/craisinlord/apple_cows/neoforge/client/AppleCowsNeoForgeClient.java) |

## 全部类（10）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `AppleCowsNeoForgeClient` | class | — | EventBusSubscriber | 1 | 21 | [AppleCowsNeoForgeClient.java](com/craisinlord/apple_cows/neoforge/client/AppleCowsNeoForgeClient.java) |
| `AppleCowsNeoforge` | class | — | Mod | 3 | 69 | [AppleCowsNeoforge.java](com/craisinlord/apple_cows/neoforge/AppleCowsNeoforge.java) |
| `AppleCowEntity` | class | Cow | — | 38 | 399 | [AppleCowEntity.java](com/craisinlord/apple_cows/content/entity/AppleCowEntity.java) |
| `AppleCowsNeoforgeItems` | class | — | — | 1 | 30 | [AppleCowsNeoforgeItems.java](com/craisinlord/apple_cows/neoforge/registry/AppleCowsNeoforgeItems.java) |
| `AppleCowEntityVariants` | class | AppleCowEntity | — | 0 | 29 | [AppleCowEntityVariants.java](com/craisinlord/apple_cows/content/entity/AppleCowEntityVariants.java) |
| `AppleCowRenderer` | class | GeoEntityRenderer | — | 3 | 80 | [AppleCowRenderer.java](com/craisinlord/apple_cows/content/client/renderer/AppleCowRenderer.java) |
| `PlatformMethods` | class | — | — | 1 | 8 | [PlatformMethods.java](architectury_inject_AppleCows_common_dac55d1c3d7c43d0b24fcf81e4608720_5735218e23cbe9531c578f085ae81bacee6468ecde68b2198194d0bbf02c767eapple_cows1001211commondevjar/PlatformMethods.java) |
| `AppleCows` | class | — | — | 1 | 13 | [AppleCows.java](com/craisinlord/apple_cows/AppleCows.java) |
| `AppleCowsNeoforgeEntities` | class | — | — | 2 | 37 | [AppleCowsNeoforgeEntities.java](com/craisinlord/apple_cows/neoforge/registry/AppleCowsNeoforgeEntities.java) |
| `AppleCowModel` | class | GeoModel | — | 4 | 64 | [AppleCowModel.java](com/craisinlord/apple_cows/content/client/model/AppleCowModel.java) |
