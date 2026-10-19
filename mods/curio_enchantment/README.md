# Curio Enchantment（`curio_enchantment`）

> jar：`[饰品附魔]curioenchantment.jar` ｜ 版本：`2101.1.3-fix-1` ｜ 作者：chen_1336
> 规模：**21 个类 / 10 个包 / 948 行** ｜ 事件处理类 3 ｜ Mixin 3 ｜ 注册/管理类 1

**依赖**：`neoforge`、`minecraft`、`curios`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.chen1335.curioEnchantment.data` | 5 | `Main`、`LootTables`、`LootModifiers` |
| `com.chen1335.curioEnchantment.API.objects` | 3 | `DataComponentTypes`、`CETags`、`AttachmentTypes` |
| `com.chen1335.curioEnchantment.data.enchantments` | 3 | `EnchantmentsProvider`、`CEEnchantments`、`IronsSpellBooksEnchantments` |
| `com.chen1335.curioEnchantment` | 2 | `Config`、`CurioEnchantment` |
| `com.chen1335.curioEnchantment.common` | 2 | `EventHandler`、`AttributeModifierResourceLocations` |
| `com.chen1335.curioEnchantment.mixins.curio_enchantment` | 2 | `ItemMixin`、`TagLoaderMixin` |
| `com.chen1335.curioEnchantment.mixins.irons_spellbooks` | 1 | `AbstractSpellMixin` |
| `com.chen1335.curioEnchantment.mixins` | 1 | `MainMixinPlugin` |
| `com.chen1335.curioEnchantment.common.attachmentTypes` | 1 | `PlayerData` |
| `com.chen1335.curioEnchantment.API.objects.enchantmentEffects` | 1 | `CurioEnchantmentAttributeEffect` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 21）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `Config` | class | — | EventBusSubscriber | 0 | 18 | [Config.java](com/chen1335/curioEnchantment/Config.java) |
| `Main` | class | — | EventBusSubscriber | 2 | 59 | [Main.java](com/chen1335/curioEnchantment/data/Main.java) |
| `EventHandler` | class | — | — | 3 | 156 | [EventHandler.java](com/chen1335/curioEnchantment/common/EventHandler.java) |
| `CurioEnchantment` | class | — | Mod | 1 | 67 | [CurioEnchantment.java](com/chen1335/curioEnchantment/CurioEnchantment.java) |
| `ItemMixin` | class | — | Mixin | 3 | 30 | [ItemMixin.java](com/chen1335/curioEnchantment/mixins/curio_enchantment/ItemMixin.java) |
| `AbstractSpellMixin` | class | — | Mixin | 1 | 23 | [AbstractSpellMixin.java](com/chen1335/curioEnchantment/mixins/irons_spellbooks/AbstractSpellMixin.java) |
| `TagLoaderMixin` | class | — | Mixin | 1 | 63 | [TagLoaderMixin.java](com/chen1335/curioEnchantment/mixins/curio_enchantment/TagLoaderMixin.java) |
| `LootTables` | class | LootTableProvider | — | 2 | 60 | [LootTables.java](com/chen1335/curioEnchantment/data/LootTables.java) |
| `LootModifiers` | class | GlobalLootModifierProvider | — | 2 | 37 | [LootModifiers.java](com/chen1335/curioEnchantment/data/LootModifiers.java) |
| `CEItemTagsProvider` | class | ItemTagsProvider | — | 1 | 24 | [CEItemTagsProvider.java](com/chen1335/curioEnchantment/data/CEItemTagsProvider.java) |
| `DataComponentTypes` | class | — | — | 0 | 23 | [DataComponentTypes.java](com/chen1335/curioEnchantment/API/objects/DataComponentTypes.java) |
| `MainMixinPlugin` | class | IMixinConfigPlugin | — | 7 | 46 | [MainMixinPlugin.java](com/chen1335/curioEnchantment/mixins/MainMixinPlugin.java) |
| `PlayerData` | class | — | — | 0 | 7 | [PlayerData.java](com/chen1335/curioEnchantment/common/attachmentTypes/PlayerData.java) |
| `CETags` | interface | — | — | 0 | 22 | [CETags.java](com/chen1335/curioEnchantment/API/objects/CETags.java) |
| `AttachmentTypes` | class | — | — | 0 | 17 | [AttachmentTypes.java](com/chen1335/curioEnchantment/API/objects/AttachmentTypes.java) |
| `CurioEnchantmentAttributeEffect` | record | — | — | 0 | 20 | [CurioEnchantmentAttributeEffect.java](com/chen1335/curioEnchantment/API/objects/enchantmentEffects/CurioEnchantmentAttributeEffect.java) |
| `AttributeModifierResourceLocations` | class | — | — | 0 | 10 | [AttributeModifierResourceLocations.java](com/chen1335/curioEnchantment/common/AttributeModifierResourceLocations.java) |
| `CEEnchantmentTagsProvider` | class | EnchantmentTagsProvider | — | 2 | 49 | [CEEnchantmentTagsProvider.java](com/chen1335/curioEnchantment/data/CEEnchantmentTagsProvider.java) |
| `EnchantmentsProvider` | class | — | — | 3 | 42 | [EnchantmentsProvider.java](com/chen1335/curioEnchantment/data/enchantments/EnchantmentsProvider.java) |
| `CEEnchantments` | class | EnchantmentsProvider | — | 2 | 90 | [CEEnchantments.java](com/chen1335/curioEnchantment/data/enchantments/CEEnchantments.java) |
| `IronsSpellBooksEnchantments` | class | EnchantmentsProvider | — | 1 | 85 | [IronsSpellBooksEnchantments.java](com/chen1335/curioEnchantment/data/enchantments/IronsSpellBooksEnchantments.java) |

## 事件处理（`@SubscribeEvent`，3 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `EventHandler` | 3 | `com.chen1335.curioEnchantment.common` | [EventHandler.java](com/chen1335/curioEnchantment/common/EventHandler.java) |
| `Config` | 1 | `com.chen1335.curioEnchantment` | [Config.java](com/chen1335/curioEnchantment/Config.java) |
| `Main` | 1 | `com.chen1335.curioEnchantment.data` | [Main.java](com/chen1335/curioEnchantment/data/Main.java) |

## Mixin（3 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `ItemMixin` | `Item` | [ItemMixin.java](com/chen1335/curioEnchantment/mixins/curio_enchantment/ItemMixin.java) |
| `AbstractSpellMixin` | `AbstractSpell` | [AbstractSpellMixin.java](com/chen1335/curioEnchantment/mixins/irons_spellbooks/AbstractSpellMixin.java) |
| `TagLoaderMixin` | `TagLoader` | [TagLoaderMixin.java](com/chen1335/curioEnchantment/mixins/curio_enchantment/TagLoaderMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `Config` | EventBusSubscriber | [Config.java](com/chen1335/curioEnchantment/Config.java) |

## 全部类（21）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `Config` | class | — | EventBusSubscriber | 0 | 18 | [Config.java](com/chen1335/curioEnchantment/Config.java) |
| `Main` | class | — | EventBusSubscriber | 2 | 59 | [Main.java](com/chen1335/curioEnchantment/data/Main.java) |
| `EventHandler` | class | — | — | 3 | 156 | [EventHandler.java](com/chen1335/curioEnchantment/common/EventHandler.java) |
| `CurioEnchantment` | class | — | Mod | 1 | 67 | [CurioEnchantment.java](com/chen1335/curioEnchantment/CurioEnchantment.java) |
| `ItemMixin` | class | — | Mixin | 3 | 30 | [ItemMixin.java](com/chen1335/curioEnchantment/mixins/curio_enchantment/ItemMixin.java) |
| `AbstractSpellMixin` | class | — | Mixin | 1 | 23 | [AbstractSpellMixin.java](com/chen1335/curioEnchantment/mixins/irons_spellbooks/AbstractSpellMixin.java) |
| `TagLoaderMixin` | class | — | Mixin | 1 | 63 | [TagLoaderMixin.java](com/chen1335/curioEnchantment/mixins/curio_enchantment/TagLoaderMixin.java) |
| `LootTables` | class | LootTableProvider | — | 2 | 60 | [LootTables.java](com/chen1335/curioEnchantment/data/LootTables.java) |
| `LootModifiers` | class | GlobalLootModifierProvider | — | 2 | 37 | [LootModifiers.java](com/chen1335/curioEnchantment/data/LootModifiers.java) |
| `CEItemTagsProvider` | class | ItemTagsProvider | — | 1 | 24 | [CEItemTagsProvider.java](com/chen1335/curioEnchantment/data/CEItemTagsProvider.java) |
| `DataComponentTypes` | class | — | — | 0 | 23 | [DataComponentTypes.java](com/chen1335/curioEnchantment/API/objects/DataComponentTypes.java) |
| `MainMixinPlugin` | class | IMixinConfigPlugin | — | 7 | 46 | [MainMixinPlugin.java](com/chen1335/curioEnchantment/mixins/MainMixinPlugin.java) |
| `PlayerData` | class | — | — | 0 | 7 | [PlayerData.java](com/chen1335/curioEnchantment/common/attachmentTypes/PlayerData.java) |
| `CETags` | interface | — | — | 0 | 22 | [CETags.java](com/chen1335/curioEnchantment/API/objects/CETags.java) |
| `AttachmentTypes` | class | — | — | 0 | 17 | [AttachmentTypes.java](com/chen1335/curioEnchantment/API/objects/AttachmentTypes.java) |
| `CurioEnchantmentAttributeEffect` | record | — | — | 0 | 20 | [CurioEnchantmentAttributeEffect.java](com/chen1335/curioEnchantment/API/objects/enchantmentEffects/CurioEnchantmentAttributeEffect.java) |
| `AttributeModifierResourceLocations` | class | — | — | 0 | 10 | [AttributeModifierResourceLocations.java](com/chen1335/curioEnchantment/common/AttributeModifierResourceLocations.java) |
| `CEEnchantmentTagsProvider` | class | EnchantmentTagsProvider | — | 2 | 49 | [CEEnchantmentTagsProvider.java](com/chen1335/curioEnchantment/data/CEEnchantmentTagsProvider.java) |
| `EnchantmentsProvider` | class | — | — | 3 | 42 | [EnchantmentsProvider.java](com/chen1335/curioEnchantment/data/enchantments/EnchantmentsProvider.java) |
| `CEEnchantments` | class | EnchantmentsProvider | — | 2 | 90 | [CEEnchantments.java](com/chen1335/curioEnchantment/data/enchantments/CEEnchantments.java) |
| `IronsSpellBooksEnchantments` | class | EnchantmentsProvider | — | 1 | 85 | [IronsSpellBooksEnchantments.java](com/chen1335/curioEnchantment/data/enchantments/IronsSpellBooksEnchantments.java) |
