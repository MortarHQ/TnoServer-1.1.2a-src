# ImmersiveUI（`immersiveui`）

> jar：`[物品移动显示]ImmersiveUI.jar` ｜ 版本：`0.3.3` ｜ 作者：OctoStudios
> 规模：**32 个类 / 9 个包 / 1607 行** ｜ 事件处理类 1 ｜ Mixin 15 ｜ 注册/管理类 0

**官方描述**：Makes your UI flashy :D

**依赖**：`neoforge`、`minecraft`、`octolib`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `it.hurts.octostudios.immersiveui.mixin` | 13 | `AbstractContainerScreenMixin`、`AbstractContainerScreenAccessor`、`ItemStackMixin` |
| `it.hurts.octostudios.immersiveui.compat` | 4 | `ExtraScreenData`、`DummySophisticatedCompat`、`SophisticatedCompat` |
| `it.hurts.octostudios.immersiveui.util` | 4 | `RenderUtils`、`CommonCode`、`VectorUtils` |
| `it.hurts.octostudios.immersiveui.client` | 3 | `RenderInfo`、`VariableStorage`、`MouseInfo` |
| `it.hurts.octostudios.immersiveui` | 2 | `Config`、`ImmersiveUI` |
| `it.hurts.octostudios.immersiveui.neoforge.mixin` | 2 | `StorageScreenBaseMixin`、`FloatingItemMixin` |
| `it.hurts.octostudios.immersiveui.client.particle` | 2 | `FlameUIParticle`、`RarityUIParticle` |
| `it.hurts.octostudios.immersiveui.neoforge` | 1 | `ImmersiveUINeoForge` |
| `architectury_inject_immersiveui_common_ae0497413f3842bea7173333991395df_2a4fc0d81295e42fcf1182225b65feaf887ca88e605a1c6bd0a7e4939e75b194ImmersiveUICOMMON0331211devjar` | 1 | `PlatformMethods` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 32）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ImmersiveUINeoForge` | class | — | EventBusSubscriber、Mod | 1 | 34 | [ImmersiveUINeoForge.java](it/hurts/octostudios/immersiveui/neoforge/ImmersiveUINeoForge.java) |
| `Config` | class | OctoConfig | — | 40 | 307 | [Config.java](it/hurts/octostudios/immersiveui/Config.java) |
| `StorageScreenBaseMixin` | class | — | Mixin | 10 | 140 | [StorageScreenBaseMixin.java](it/hurts/octostudios/immersiveui/neoforge/mixin/StorageScreenBaseMixin.java) |
| `AbstractContainerScreenMixin` | class | ExtraScreenData | Mixin | 12 | 161 | [AbstractContainerScreenMixin.java](it/hurts/octostudios/immersiveui/mixin/AbstractContainerScreenMixin.java) |
| `FloatingItemMixin` | class | — | Mixin | 2 | 47 | [FloatingItemMixin.java](it/hurts/octostudios/immersiveui/neoforge/mixin/FloatingItemMixin.java) |
| `AbstractContainerScreenAccessor` | interface | — | Mixin | 0 | 13 | [AbstractContainerScreenAccessor.java](it/hurts/octostudios/immersiveui/mixin/AbstractContainerScreenAccessor.java) |
| `ItemStackMixin` | class | — | Mixin | 1 | 13 | [ItemStackMixin.java](it/hurts/octostudios/immersiveui/mixin/ItemStackMixin.java) |
| `AnvilMenuMixin` | class | — | Mixin | 1 | 22 | [AnvilMenuMixin.java](it/hurts/octostudios/immersiveui/mixin/AnvilMenuMixin.java) |
| `AbstractFurnaceMenuAccessor` | interface | — | Mixin | 0 | 13 | [AbstractFurnaceMenuAccessor.java](it/hurts/octostudios/immersiveui/mixin/AbstractFurnaceMenuAccessor.java) |
| `EnchantmentScreenMixin` | class | — | Mixin | 1 | 51 | [EnchantmentScreenMixin.java](it/hurts/octostudios/immersiveui/mixin/EnchantmentScreenMixin.java) |
| `AbstractFurnaceScreenMixin` | class | — | Mixin | 1 | 26 | [AbstractFurnaceScreenMixin.java](it/hurts/octostudios/immersiveui/mixin/AbstractFurnaceScreenMixin.java) |
| `SmithingMenuMixin` | class | — | Mixin | 1 | 22 | [SmithingMenuMixin.java](it/hurts/octostudios/immersiveui/mixin/SmithingMenuMixin.java) |
| `ScreenMixin` | class | — | Mixin | 0 | 9 | [ScreenMixin.java](it/hurts/octostudios/immersiveui/mixin/ScreenMixin.java) |
| `InGameHudMixin` | class | — | Mixin | 5 | 85 | [InGameHudMixin.java](it/hurts/octostudios/immersiveui/mixin/InGameHudMixin.java) |
| `EnchantmentMixin` | class | — | Mixin | 1 | 41 | [EnchantmentMixin.java](it/hurts/octostudios/immersiveui/mixin/EnchantmentMixin.java) |
| `GooeyMixin` | class | — | Mixin | 1 | 30 | [GooeyMixin.java](it/hurts/octostudios/immersiveui/mixin/GooeyMixin.java) |
| `AdvancementToastMixin` | class | — | Mixin | 2 | 58 | [AdvancementToastMixin.java](it/hurts/octostudios/immersiveui/mixin/AdvancementToastMixin.java) |
| `ExtraScreenData` | interface | — | — | 0 | 18 | [ExtraScreenData.java](it/hurts/octostudios/immersiveui/compat/ExtraScreenData.java) |
| `DummySophisticatedCompat` | class | SophisticatedProxy | — | 1 | 11 | [DummySophisticatedCompat.java](it/hurts/octostudios/immersiveui/compat/DummySophisticatedCompat.java) |
| `SophisticatedCompat` | class | SophisticatedProxy | — | 1 | 15 | [SophisticatedCompat.java](it/hurts/octostudios/immersiveui/compat/SophisticatedCompat.java) |
| `RenderUtils` | class | — | — | 7 | 104 | [RenderUtils.java](it/hurts/octostudios/immersiveui/util/RenderUtils.java) |
| `CommonCode` | class | — | — | 5 | 179 | [CommonCode.java](it/hurts/octostudios/immersiveui/util/CommonCode.java) |
| `VectorUtils` | class | — | — | 1 | 15 | [VectorUtils.java](it/hurts/octostudios/immersiveui/util/VectorUtils.java) |
| `Easing` | class | — | — | 7 | 66 | [Easing.java](it/hurts/octostudios/immersiveui/util/Easing.java) |
| `ImmersiveUI` | class | — | — | 1 | 14 | [ImmersiveUI.java](it/hurts/octostudios/immersiveui/ImmersiveUI.java) |
| `SophisticatedProxy` | interface | — | — | 0 | 8 | [SophisticatedProxy.java](it/hurts/octostudios/immersiveui/compat/SophisticatedProxy.java) |
| `RenderInfo` | class | — | — | 0 | 12 | [RenderInfo.java](it/hurts/octostudios/immersiveui/client/RenderInfo.java) |
| `VariableStorage` | class | — | — | 0 | 15 | [VariableStorage.java](it/hurts/octostudios/immersiveui/client/VariableStorage.java) |
| `MouseInfo` | class | — | — | 0 | 9 | [MouseInfo.java](it/hurts/octostudios/immersiveui/client/MouseInfo.java) |
| `FlameUIParticle` | class | ExtendedUIParticle | — | 1 | 33 | [FlameUIParticle.java](it/hurts/octostudios/immersiveui/client/particle/FlameUIParticle.java) |
| `RarityUIParticle` | class | ExtendedUIParticle | — | 1 | 28 | [RarityUIParticle.java](it/hurts/octostudios/immersiveui/client/particle/RarityUIParticle.java) |
| `PlatformMethods` | class | — | — | 1 | 8 | [PlatformMethods.java](architectury_inject_immersiveui_common_ae0497413f3842bea7173333991395df_2a4fc0d81295e42fcf1182225b65feaf887ca88e605a1c6bd0a7e4939e75b194ImmersiveUICOMMON0331211devjar/PlatformMethods.java) |

## 事件处理（`@SubscribeEvent`，1 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `ImmersiveUINeoForge` | 1 | `it.hurts.octostudios.immersiveui.neoforge` | [ImmersiveUINeoForge.java](it/hurts/octostudios/immersiveui/neoforge/ImmersiveUINeoForge.java) |

## Mixin（15 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `StorageScreenBaseMixin` | `StorageScreenBase` | [StorageScreenBaseMixin.java](it/hurts/octostudios/immersiveui/neoforge/mixin/StorageScreenBaseMixin.java) |
| `AbstractContainerScreenMixin` | `AbstractContainerScreen` | [AbstractContainerScreenMixin.java](it/hurts/octostudios/immersiveui/mixin/AbstractContainerScreenMixin.java) |
| `FloatingItemMixin` | `FloatingItem` | [FloatingItemMixin.java](it/hurts/octostudios/immersiveui/neoforge/mixin/FloatingItemMixin.java) |
| `AbstractContainerScreenAccessor` | `AbstractContainerScreenAccessor` | [AbstractContainerScreenAccessor.java](it/hurts/octostudios/immersiveui/mixin/AbstractContainerScreenAccessor.java) |
| `ItemStackMixin` | `ItemStack` | [ItemStackMixin.java](it/hurts/octostudios/immersiveui/mixin/ItemStackMixin.java) |
| `AnvilMenuMixin` | `AnvilMenu` | [AnvilMenuMixin.java](it/hurts/octostudios/immersiveui/mixin/AnvilMenuMixin.java) |
| `AbstractFurnaceMenuAccessor` | `AbstractFurnaceMenuAccessor` | [AbstractFurnaceMenuAccessor.java](it/hurts/octostudios/immersiveui/mixin/AbstractFurnaceMenuAccessor.java) |
| `EnchantmentScreenMixin` | `EnchantmentScreen` | [EnchantmentScreenMixin.java](it/hurts/octostudios/immersiveui/mixin/EnchantmentScreenMixin.java) |
| `AbstractFurnaceScreenMixin` | `AbstractFurnaceScreen` | [AbstractFurnaceScreenMixin.java](it/hurts/octostudios/immersiveui/mixin/AbstractFurnaceScreenMixin.java) |
| `SmithingMenuMixin` | `SmithingMenu` | [SmithingMenuMixin.java](it/hurts/octostudios/immersiveui/mixin/SmithingMenuMixin.java) |
| `ScreenMixin` | `Screen` | [ScreenMixin.java](it/hurts/octostudios/immersiveui/mixin/ScreenMixin.java) |
| `InGameHudMixin` | `InGameHud` | [InGameHudMixin.java](it/hurts/octostudios/immersiveui/mixin/InGameHudMixin.java) |
| `EnchantmentMixin` | `Enchantment` | [EnchantmentMixin.java](it/hurts/octostudios/immersiveui/mixin/EnchantmentMixin.java) |
| `GooeyMixin` | `Gooey` | [GooeyMixin.java](it/hurts/octostudios/immersiveui/mixin/GooeyMixin.java) |
| `AdvancementToastMixin` | `AdvancementToast` | [AdvancementToastMixin.java](it/hurts/octostudios/immersiveui/mixin/AdvancementToastMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `Config` | — | [Config.java](it/hurts/octostudios/immersiveui/Config.java) |

## 全部类（32）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ImmersiveUINeoForge` | class | — | EventBusSubscriber、Mod | 1 | 34 | [ImmersiveUINeoForge.java](it/hurts/octostudios/immersiveui/neoforge/ImmersiveUINeoForge.java) |
| `Config` | class | OctoConfig | — | 40 | 307 | [Config.java](it/hurts/octostudios/immersiveui/Config.java) |
| `StorageScreenBaseMixin` | class | — | Mixin | 10 | 140 | [StorageScreenBaseMixin.java](it/hurts/octostudios/immersiveui/neoforge/mixin/StorageScreenBaseMixin.java) |
| `AbstractContainerScreenMixin` | class | ExtraScreenData | Mixin | 12 | 161 | [AbstractContainerScreenMixin.java](it/hurts/octostudios/immersiveui/mixin/AbstractContainerScreenMixin.java) |
| `FloatingItemMixin` | class | — | Mixin | 2 | 47 | [FloatingItemMixin.java](it/hurts/octostudios/immersiveui/neoforge/mixin/FloatingItemMixin.java) |
| `AbstractContainerScreenAccessor` | interface | — | Mixin | 0 | 13 | [AbstractContainerScreenAccessor.java](it/hurts/octostudios/immersiveui/mixin/AbstractContainerScreenAccessor.java) |
| `ItemStackMixin` | class | — | Mixin | 1 | 13 | [ItemStackMixin.java](it/hurts/octostudios/immersiveui/mixin/ItemStackMixin.java) |
| `AnvilMenuMixin` | class | — | Mixin | 1 | 22 | [AnvilMenuMixin.java](it/hurts/octostudios/immersiveui/mixin/AnvilMenuMixin.java) |
| `AbstractFurnaceMenuAccessor` | interface | — | Mixin | 0 | 13 | [AbstractFurnaceMenuAccessor.java](it/hurts/octostudios/immersiveui/mixin/AbstractFurnaceMenuAccessor.java) |
| `EnchantmentScreenMixin` | class | — | Mixin | 1 | 51 | [EnchantmentScreenMixin.java](it/hurts/octostudios/immersiveui/mixin/EnchantmentScreenMixin.java) |
| `AbstractFurnaceScreenMixin` | class | — | Mixin | 1 | 26 | [AbstractFurnaceScreenMixin.java](it/hurts/octostudios/immersiveui/mixin/AbstractFurnaceScreenMixin.java) |
| `SmithingMenuMixin` | class | — | Mixin | 1 | 22 | [SmithingMenuMixin.java](it/hurts/octostudios/immersiveui/mixin/SmithingMenuMixin.java) |
| `ScreenMixin` | class | — | Mixin | 0 | 9 | [ScreenMixin.java](it/hurts/octostudios/immersiveui/mixin/ScreenMixin.java) |
| `InGameHudMixin` | class | — | Mixin | 5 | 85 | [InGameHudMixin.java](it/hurts/octostudios/immersiveui/mixin/InGameHudMixin.java) |
| `EnchantmentMixin` | class | — | Mixin | 1 | 41 | [EnchantmentMixin.java](it/hurts/octostudios/immersiveui/mixin/EnchantmentMixin.java) |
| `GooeyMixin` | class | — | Mixin | 1 | 30 | [GooeyMixin.java](it/hurts/octostudios/immersiveui/mixin/GooeyMixin.java) |
| `AdvancementToastMixin` | class | — | Mixin | 2 | 58 | [AdvancementToastMixin.java](it/hurts/octostudios/immersiveui/mixin/AdvancementToastMixin.java) |
| `ExtraScreenData` | interface | — | — | 0 | 18 | [ExtraScreenData.java](it/hurts/octostudios/immersiveui/compat/ExtraScreenData.java) |
| `DummySophisticatedCompat` | class | SophisticatedProxy | — | 1 | 11 | [DummySophisticatedCompat.java](it/hurts/octostudios/immersiveui/compat/DummySophisticatedCompat.java) |
| `SophisticatedCompat` | class | SophisticatedProxy | — | 1 | 15 | [SophisticatedCompat.java](it/hurts/octostudios/immersiveui/compat/SophisticatedCompat.java) |
| `RenderUtils` | class | — | — | 7 | 104 | [RenderUtils.java](it/hurts/octostudios/immersiveui/util/RenderUtils.java) |
| `CommonCode` | class | — | — | 5 | 179 | [CommonCode.java](it/hurts/octostudios/immersiveui/util/CommonCode.java) |
| `VectorUtils` | class | — | — | 1 | 15 | [VectorUtils.java](it/hurts/octostudios/immersiveui/util/VectorUtils.java) |
| `Easing` | class | — | — | 7 | 66 | [Easing.java](it/hurts/octostudios/immersiveui/util/Easing.java) |
| `ImmersiveUI` | class | — | — | 1 | 14 | [ImmersiveUI.java](it/hurts/octostudios/immersiveui/ImmersiveUI.java) |
| `SophisticatedProxy` | interface | — | — | 0 | 8 | [SophisticatedProxy.java](it/hurts/octostudios/immersiveui/compat/SophisticatedProxy.java) |
| `RenderInfo` | class | — | — | 0 | 12 | [RenderInfo.java](it/hurts/octostudios/immersiveui/client/RenderInfo.java) |
| `VariableStorage` | class | — | — | 0 | 15 | [VariableStorage.java](it/hurts/octostudios/immersiveui/client/VariableStorage.java) |
| `MouseInfo` | class | — | — | 0 | 9 | [MouseInfo.java](it/hurts/octostudios/immersiveui/client/MouseInfo.java) |
| `FlameUIParticle` | class | ExtendedUIParticle | — | 1 | 33 | [FlameUIParticle.java](it/hurts/octostudios/immersiveui/client/particle/FlameUIParticle.java) |
| `RarityUIParticle` | class | ExtendedUIParticle | — | 1 | 28 | [RarityUIParticle.java](it/hurts/octostudios/immersiveui/client/particle/RarityUIParticle.java) |
| `PlatformMethods` | class | — | — | 1 | 8 | [PlatformMethods.java](architectury_inject_immersiveui_common_ae0497413f3842bea7173333991395df_2a4fc0d81295e42fcf1182225b65feaf887ca88e605a1c6bd0a7e4939e75b194ImmersiveUICOMMON0331211devjar/PlatformMethods.java) |
