# Skill Study Books（`tensura_skill_books`）

> jar：`[转生附属]tensura_skill_books.jar` ｜ 版本：`2.1.2` ｜ 作者：SJyueluo
> 规模：**24 个类 / 10 个包 / 1818 行** ｜ 事件处理类 1 ｜ Mixin 0 ｜ 注册/管理类 1

**依赖**：`neoforge`、`minecraft`、`tensura`、`manascore`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.sjyueluo.tensura_skill_books.util` | 6 | `SkillGrantCompat`、`SkillCompat`、`SkillItemNbt` |
| `com.sjyueluo.tensura_skill_books.client` | 5 | `SkillStudyBookClientEvents`、`SkillStudyBookBakedModel`、`SkillStudyBookItemRenderer` |
| `com.sjyueluo.tensura_skill_books.catalog` | 3 | `SkillEntry`、`SkillCatalog`、`SkillCategory` |
| `com.sjyueluo.tensura_skill_books` | 2 | `TensuraSkillBooksMod`、`TensuraSkillBooksConfig` |
| `com.sjyueluo.tensura_skill_books.registry` | 2 | `ModItems`、`ModCreativeTabs` |
| `com.sjyueluo.tensura_skill_books.client.icon` | 2 | `IconData`、`TensuraSkillIconResolver` |
| `com.sjyueluo.tensura_skill_books.item` | 1 | `SkillStudyBookItem` |
| `com.sjyueluo.tensura_skill_books.command` | 1 | `SkillBooksCommands` |
| `com.sjyueluo.tensura_skill_books.integration.emi` | 1 | `TensuraSkillBooksEmiPlugin` |
| `com.sjyueluo.tensura_skill_books.integration.jei` | 1 | `TensuraSkillBooksJeiPlugin` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 24）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `SkillStudyBookClientEvents` | class | — | EventBusSubscriber | 2 | 37 | [SkillStudyBookClientEvents.java](com/sjyueluo/tensura_skill_books/client/SkillStudyBookClientEvents.java) |
| `TensuraSkillBooksMod` | class | — | Mod | 1 | 43 | [TensuraSkillBooksMod.java](com/sjyueluo/tensura_skill_books/TensuraSkillBooksMod.java) |
| `SkillGrantCompat` | class | — | — | 10 | 201 | [SkillGrantCompat.java](com/sjyueluo/tensura_skill_books/util/SkillGrantCompat.java) |
| `SkillStudyBookBakedModel` | class | BakedModel | — | 25 | 146 | [SkillStudyBookBakedModel.java](com/sjyueluo/tensura_skill_books/client/SkillStudyBookBakedModel.java) |
| `TensuraSkillBooksConfig` | class | — | — | 2 | 45 | [TensuraSkillBooksConfig.java](com/sjyueluo/tensura_skill_books/TensuraSkillBooksConfig.java) |
| `SkillCompat` | class | — | — | 10 | 198 | [SkillCompat.java](com/sjyueluo/tensura_skill_books/util/SkillCompat.java) |
| `SkillStudyBookItemRenderer` | class | BlockEntityWithoutLevelRenderer | — | 3 | 178 | [SkillStudyBookItemRenderer.java](com/sjyueluo/tensura_skill_books/client/SkillStudyBookItemRenderer.java) |
| `SkillStudyBookItem` | class | Item | — | 6 | 140 | [SkillStudyBookItem.java](com/sjyueluo/tensura_skill_books/item/SkillStudyBookItem.java) |
| `SkillBooksCommands` | class | — | — | 1 | 47 | [SkillBooksCommands.java](com/sjyueluo/tensura_skill_books/command/SkillBooksCommands.java) |
| `SkillItemNbt` | class | — | — | 5 | 83 | [SkillItemNbt.java](com/sjyueluo/tensura_skill_books/util/SkillItemNbt.java) |
| `ModItems` | class | — | — | 1 | 24 | [ModItems.java](com/sjyueluo/tensura_skill_books/registry/ModItems.java) |
| `SkillStudyBookItemExtensions` | class | IClientItemExtensions | — | 1 | 16 | [SkillStudyBookItemExtensions.java](com/sjyueluo/tensura_skill_books/client/SkillStudyBookItemExtensions.java) |
| `SkillStudyBookItemDecorator` | class | IItemDecorator | — | 2 | 40 | [SkillStudyBookItemDecorator.java](com/sjyueluo/tensura_skill_books/client/SkillStudyBookItemDecorator.java) |
| `ModCreativeTabs` | class | — | — | 6 | 130 | [ModCreativeTabs.java](com/sjyueluo/tensura_skill_books/registry/ModCreativeTabs.java) |
| `StudyBookLoreUtil` | class | — | — | 3 | 62 | [StudyBookLoreUtil.java](com/sjyueluo/tensura_skill_books/util/StudyBookLoreUtil.java) |
| `StudyBookStackFactory` | class | — | — | 5 | 60 | [StudyBookStackFactory.java](com/sjyueluo/tensura_skill_books/util/StudyBookStackFactory.java) |
| `SkillNameUtil` | class | — | — | 3 | 38 | [SkillNameUtil.java](com/sjyueluo/tensura_skill_books/util/SkillNameUtil.java) |
| `IconData` | record | — | — | 2 | 14 | [IconData.java](com/sjyueluo/tensura_skill_books/client/icon/IconData.java) |
| `TensuraSkillIconResolver` | class | — | — | 6 | 136 | [TensuraSkillIconResolver.java](com/sjyueluo/tensura_skill_books/client/icon/TensuraSkillIconResolver.java) |
| `SkillEntry` | record | — | — | 0 | 7 | [SkillEntry.java](com/sjyueluo/tensura_skill_books/catalog/SkillEntry.java) |
| `SkillCatalog` | class | — | — | 4 | 101 | [SkillCatalog.java](com/sjyueluo/tensura_skill_books/catalog/SkillCatalog.java) |
| `SkillCategory` | enum | — | — | 0 | 18 | [SkillCategory.java](com/sjyueluo/tensura_skill_books/catalog/SkillCategory.java) |
| `TensuraSkillBooksEmiPlugin` | class | EmiPlugin | EmiEntrypoint | 1 | 21 | [TensuraSkillBooksEmiPlugin.java](com/sjyueluo/tensura_skill_books/integration/emi/TensuraSkillBooksEmiPlugin.java) |
| `TensuraSkillBooksJeiPlugin` | class | IModPlugin | JeiPlugin | 3 | 33 | [TensuraSkillBooksJeiPlugin.java](com/sjyueluo/tensura_skill_books/integration/jei/TensuraSkillBooksJeiPlugin.java) |

## 事件处理（`@SubscribeEvent`，1 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `SkillStudyBookClientEvents` | 2 | `com.sjyueluo.tensura_skill_books.client` | [SkillStudyBookClientEvents.java](com/sjyueluo/tensura_skill_books/client/SkillStudyBookClientEvents.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `TensuraSkillBooksConfig` | — | [TensuraSkillBooksConfig.java](com/sjyueluo/tensura_skill_books/TensuraSkillBooksConfig.java) |

## 全部类（24）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `SkillStudyBookClientEvents` | class | — | EventBusSubscriber | 2 | 37 | [SkillStudyBookClientEvents.java](com/sjyueluo/tensura_skill_books/client/SkillStudyBookClientEvents.java) |
| `TensuraSkillBooksMod` | class | — | Mod | 1 | 43 | [TensuraSkillBooksMod.java](com/sjyueluo/tensura_skill_books/TensuraSkillBooksMod.java) |
| `SkillGrantCompat` | class | — | — | 10 | 201 | [SkillGrantCompat.java](com/sjyueluo/tensura_skill_books/util/SkillGrantCompat.java) |
| `SkillStudyBookBakedModel` | class | BakedModel | — | 25 | 146 | [SkillStudyBookBakedModel.java](com/sjyueluo/tensura_skill_books/client/SkillStudyBookBakedModel.java) |
| `TensuraSkillBooksConfig` | class | — | — | 2 | 45 | [TensuraSkillBooksConfig.java](com/sjyueluo/tensura_skill_books/TensuraSkillBooksConfig.java) |
| `SkillCompat` | class | — | — | 10 | 198 | [SkillCompat.java](com/sjyueluo/tensura_skill_books/util/SkillCompat.java) |
| `SkillStudyBookItemRenderer` | class | BlockEntityWithoutLevelRenderer | — | 3 | 178 | [SkillStudyBookItemRenderer.java](com/sjyueluo/tensura_skill_books/client/SkillStudyBookItemRenderer.java) |
| `SkillStudyBookItem` | class | Item | — | 6 | 140 | [SkillStudyBookItem.java](com/sjyueluo/tensura_skill_books/item/SkillStudyBookItem.java) |
| `SkillBooksCommands` | class | — | — | 1 | 47 | [SkillBooksCommands.java](com/sjyueluo/tensura_skill_books/command/SkillBooksCommands.java) |
| `SkillItemNbt` | class | — | — | 5 | 83 | [SkillItemNbt.java](com/sjyueluo/tensura_skill_books/util/SkillItemNbt.java) |
| `ModItems` | class | — | — | 1 | 24 | [ModItems.java](com/sjyueluo/tensura_skill_books/registry/ModItems.java) |
| `SkillStudyBookItemExtensions` | class | IClientItemExtensions | — | 1 | 16 | [SkillStudyBookItemExtensions.java](com/sjyueluo/tensura_skill_books/client/SkillStudyBookItemExtensions.java) |
| `SkillStudyBookItemDecorator` | class | IItemDecorator | — | 2 | 40 | [SkillStudyBookItemDecorator.java](com/sjyueluo/tensura_skill_books/client/SkillStudyBookItemDecorator.java) |
| `ModCreativeTabs` | class | — | — | 6 | 130 | [ModCreativeTabs.java](com/sjyueluo/tensura_skill_books/registry/ModCreativeTabs.java) |
| `StudyBookLoreUtil` | class | — | — | 3 | 62 | [StudyBookLoreUtil.java](com/sjyueluo/tensura_skill_books/util/StudyBookLoreUtil.java) |
| `StudyBookStackFactory` | class | — | — | 5 | 60 | [StudyBookStackFactory.java](com/sjyueluo/tensura_skill_books/util/StudyBookStackFactory.java) |
| `SkillNameUtil` | class | — | — | 3 | 38 | [SkillNameUtil.java](com/sjyueluo/tensura_skill_books/util/SkillNameUtil.java) |
| `IconData` | record | — | — | 2 | 14 | [IconData.java](com/sjyueluo/tensura_skill_books/client/icon/IconData.java) |
| `TensuraSkillIconResolver` | class | — | — | 6 | 136 | [TensuraSkillIconResolver.java](com/sjyueluo/tensura_skill_books/client/icon/TensuraSkillIconResolver.java) |
| `SkillEntry` | record | — | — | 0 | 7 | [SkillEntry.java](com/sjyueluo/tensura_skill_books/catalog/SkillEntry.java) |
| `SkillCatalog` | class | — | — | 4 | 101 | [SkillCatalog.java](com/sjyueluo/tensura_skill_books/catalog/SkillCatalog.java) |
| `SkillCategory` | enum | — | — | 0 | 18 | [SkillCategory.java](com/sjyueluo/tensura_skill_books/catalog/SkillCategory.java) |
| `TensuraSkillBooksEmiPlugin` | class | EmiPlugin | EmiEntrypoint | 1 | 21 | [TensuraSkillBooksEmiPlugin.java](com/sjyueluo/tensura_skill_books/integration/emi/TensuraSkillBooksEmiPlugin.java) |
| `TensuraSkillBooksJeiPlugin` | class | IModPlugin | JeiPlugin | 3 | 33 | [TensuraSkillBooksJeiPlugin.java](com/sjyueluo/tensura_skill_books/integration/jei/TensuraSkillBooksJeiPlugin.java) |
