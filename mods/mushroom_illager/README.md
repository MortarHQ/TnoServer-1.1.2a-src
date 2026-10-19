# Mushroom Illager（`mushroom_illager`）

> jar：`[蘑菇灾厄]mushroomillager.jar` ｜ 版本：`2.0.0` ｜ 作者：MadeBySanny
> 规模：**9 个类 / 5 个包 / 510 行** ｜ 事件处理类 5 ｜ Mixin 0 ｜ 注册/管理类 0

**官方描述**：Adds an Mushroom Illager House and Mob to the Darkforest

**依赖**：`neoforge`、`minecraft`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `net.mcreator.mushroomillager.init` | 5 | `MushroomIllagerModEntityRenderers`、`MushroomIllagerModEntities`、`MushroomIllagerModModels` |
| `net.mcreator.mushroomillager` | 1 | `MushroomIllagerMod` |
| `net.mcreator.mushroomillager.entity` | 1 | `MushroomIllagerEntity` |
| `net.mcreator.mushroomillager.client.renderer` | 1 | `MushroomIllagerRenderer` |
| `net.mcreator.mushroomillager.client.model` | 1 | `Modelunknown` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 9）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `MushroomIllagerModEntityRenderers` | class | — | EventBusSubscriber | 1 | 18 | [MushroomIllagerModEntityRenderers.java](net/mcreator/mushroomillager/init/MushroomIllagerModEntityRenderers.java) |
| `MushroomIllagerMod` | class | — | Mod | 4 | 83 | [MushroomIllagerMod.java](net/mcreator/mushroomillager/MushroomIllagerMod.java) |
| `MushroomIllagerModEntities` | class | — | EventBusSubscriber | 3 | 43 | [MushroomIllagerModEntities.java](net/mcreator/mushroomillager/init/MushroomIllagerModEntities.java) |
| `MushroomIllagerModModels` | class | — | EventBusSubscriber | 1 | 17 | [MushroomIllagerModModels.java](net/mcreator/mushroomillager/init/MushroomIllagerModModels.java) |
| `MushroomIllagerModTabs` | class | — | EventBusSubscriber | 1 | 24 | [MushroomIllagerModTabs.java](net/mcreator/mushroomillager/init/MushroomIllagerModTabs.java) |
| `MushroomIllagerEntity` | class | Monster | — | 7 | 75 | [MushroomIllagerEntity.java](net/mcreator/mushroomillager/entity/MushroomIllagerEntity.java) |
| `MushroomIllagerRenderer` | class | MobRenderer | — | 1 | 18 | [MushroomIllagerRenderer.java](net/mcreator/mushroomillager/client/renderer/MushroomIllagerRenderer.java) |
| `MushroomIllagerModItems` | class | — | — | 0 | 16 | [MushroomIllagerModItems.java](net/mcreator/mushroomillager/init/MushroomIllagerModItems.java) |
| `Modelunknown` | class | EntityModel | — | 3 | 216 | [Modelunknown.java](net/mcreator/mushroomillager/client/model/Modelunknown.java) |

## 事件处理（`@SubscribeEvent`，5 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `MushroomIllagerModEntities` | 2 | `net.mcreator.mushroomillager.init` | [MushroomIllagerModEntities.java](net/mcreator/mushroomillager/init/MushroomIllagerModEntities.java) |
| `MushroomIllagerModEntityRenderers` | 1 | `net.mcreator.mushroomillager.init` | [MushroomIllagerModEntityRenderers.java](net/mcreator/mushroomillager/init/MushroomIllagerModEntityRenderers.java) |
| `MushroomIllagerMod` | 1 | `net.mcreator.mushroomillager` | [MushroomIllagerMod.java](net/mcreator/mushroomillager/MushroomIllagerMod.java) |
| `MushroomIllagerModModels` | 1 | `net.mcreator.mushroomillager.init` | [MushroomIllagerModModels.java](net/mcreator/mushroomillager/init/MushroomIllagerModModels.java) |
| `MushroomIllagerModTabs` | 1 | `net.mcreator.mushroomillager.init` | [MushroomIllagerModTabs.java](net/mcreator/mushroomillager/init/MushroomIllagerModTabs.java) |

## 全部类（9）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `MushroomIllagerModEntityRenderers` | class | — | EventBusSubscriber | 1 | 18 | [MushroomIllagerModEntityRenderers.java](net/mcreator/mushroomillager/init/MushroomIllagerModEntityRenderers.java) |
| `MushroomIllagerMod` | class | — | Mod | 4 | 83 | [MushroomIllagerMod.java](net/mcreator/mushroomillager/MushroomIllagerMod.java) |
| `MushroomIllagerModEntities` | class | — | EventBusSubscriber | 3 | 43 | [MushroomIllagerModEntities.java](net/mcreator/mushroomillager/init/MushroomIllagerModEntities.java) |
| `MushroomIllagerModModels` | class | — | EventBusSubscriber | 1 | 17 | [MushroomIllagerModModels.java](net/mcreator/mushroomillager/init/MushroomIllagerModModels.java) |
| `MushroomIllagerModTabs` | class | — | EventBusSubscriber | 1 | 24 | [MushroomIllagerModTabs.java](net/mcreator/mushroomillager/init/MushroomIllagerModTabs.java) |
| `MushroomIllagerEntity` | class | Monster | — | 7 | 75 | [MushroomIllagerEntity.java](net/mcreator/mushroomillager/entity/MushroomIllagerEntity.java) |
| `MushroomIllagerRenderer` | class | MobRenderer | — | 1 | 18 | [MushroomIllagerRenderer.java](net/mcreator/mushroomillager/client/renderer/MushroomIllagerRenderer.java) |
| `MushroomIllagerModItems` | class | — | — | 0 | 16 | [MushroomIllagerModItems.java](net/mcreator/mushroomillager/init/MushroomIllagerModItems.java) |
| `Modelunknown` | class | EntityModel | — | 3 | 216 | [Modelunknown.java](net/mcreator/mushroomillager/client/model/Modelunknown.java) |
