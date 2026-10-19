# Curios API（`curios`）

> jar：`[饰品]curios.jar` ｜ 版本：`9.5.1+1.21.1` ｜ 作者：C4
> 规模：**123 个类 / 35 个包 / 11753 行** ｜ 事件处理类 4 ｜ Mixin 13 ｜ 注册/管理类 19

**依赖**：`neoforge`、`minecraft`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `top.theillusivec4.curios.mixin.core` | 13 | `AccessorEntity`、`MixinPowderSnowBlock`、`MixinLivingEntity` |
| `top.theillusivec4.curios.api` | 13 | `CuriosApi`、`CuriosCapability`、`CuriosDataProvider` |
| `top.theillusivec4.curios.common.network.client` | 8 | `CuriosClientPayloadHandler`、`CuriosClientPackets`、`CPacketOpenCurios` |
| `top.theillusivec4.curios.api.event` | 7 | `CurioAttributeModifierEvent`、`CurioDropsEvent`、`DropRulesEvent` |
| `top.theillusivec4.curios.client.gui` | 6 | `GuiEventHandler`、`CuriosScreen`、`PageButton` |
| `top.theillusivec4.curios.common.network.server` | 6 | `CuriosServerPayloadHandler`、`SPacketGrabbedItem`、`SPacketQuickMove` |
| `top.theillusivec4.curios.common.network.server.sync` | 6 | `SPacketSyncData`、`SPacketSyncCurios`、`SPacketSyncRender` |
| `top.theillusivec4.curios.common.inventory` | 4 | `CurioStacksHandler`、`DynamicStackHandler`、`CurioSlot` |
| `top.theillusivec4.curios.client` | 4 | `ClientEventHandler`、`KeyRegistry`、`CuriosClientConfig` |
| `top.theillusivec4.curios.common.capability` | 4 | `CurioInventoryCapability`、`ItemizedCurioCapability`、`CurioItemHandler` |
| `top.theillusivec4.curios.common.data` | 4 | `CuriosEntityManager`、`CuriosSlotManager`、`SlotData` |
| `top.theillusivec4.curios.api.client` | 3 | `CuriosRendererRegistry`、`ICurioRenderer`、`ICuriosScreen` |
| `top.theillusivec4.curios.api.type.capability` | 3 | `ICuriosItemHandler`、`ICurioItem`、`ICurio` |
| `top.theillusivec4.curios.server.command` | 3 | `CuriosCommand`、`CuriosSelectorOptions`、`CurioArgumentType` |
| `top.theillusivec4.curios.common` | 3 | `CuriosRegistry`、`CuriosHelper`、`CuriosConfig` |
| `top.theillusivec4.curios.api.extensions` | 3 | `RegisterCuriosExtensionsEvent`、`CuriosExtensions`、`ICurioSlotExtension` |
| `top.theillusivec4.curios.api.type.util` | 3 | `ISlotHelper`、`IIconHelper`、`ICuriosHelper` |
| `top.theillusivec4.curios` | 2 | `Curios`、`CuriosConstants` |
| `top.theillusivec4.curios.common.inventory.container` | 2 | `CuriosContainer`、`CuriosContainerProvider` |
| `top.theillusivec4.curios.common.slottype` | 2 | `SlotType`、`LegacySlotManager` |
| `top.theillusivec4.curios.mixin` | 2 | `CuriosImplMixinHooks`、`CuriosUtilMixinHooks` |
| `top.theillusivec4.curios.common.integration.jei` | 2 | `CuriosContainerHandler`、`CuriosJeiPlugin` |
| `top.theillusivec4.curios.api.type.inventory` | 2 | `IDynamicStackHandler`、`ICurioStacksHandler` |
| `top.theillusivec4.curios.common.integration` | 2 | `CuriosIntegrations`、`CuriosExclusionAreas` |
| `top.theillusivec4.curios.common.integration.emi` | 2 | `CuriosEmiIntegration`、`CuriosEmiPlugin` |
| `top.theillusivec4.curios.api.type` | 2 | `ICuriosMenu`、`ISlotType` |
| `top.theillusivec4.curios.common.util` | 2 | `SetCurioAttributesFunction`、`EquipCurioTrigger` |
| `top.theillusivec4.curios.api.type.data` | 2 | `ISlotData`、`IEntitiesData` |
| `top.theillusivec4.curios.platform` | 2 | `Services`、`NeoForgeCurios` |
| `top.theillusivec4.curios.common.event` | 1 | `CuriosEventHandler` |
| `top.theillusivec4.curios.common.network` | 1 | `NetworkHandler` |
| `top.theillusivec4.curios.server` | 1 | `SlotHelper` |
| `top.theillusivec4.curios.common.integration.rei` | 1 | `CuriosReiPlugin` |
| `top.theillusivec4.curios.platform.services` | 1 | `ICuriosPlatform` |
| `top.theillusivec4.curios.client.render` | 1 | `CuriosLayer` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `Curios` | class | — | Mod | 16 | 212 | [Curios.java](top/theillusivec4/curios/Curios.java) |
| `CuriosEventHandler` | class | — | — | 17 | 684 | [CuriosEventHandler.java](top/theillusivec4/curios/common/event/CuriosEventHandler.java) |
| `CurioStacksHandler` | class | ICurioStacksHandler | — | 37 | 812 | [CurioStacksHandler.java](top/theillusivec4/curios/common/inventory/CurioStacksHandler.java) |
| `ClientEventHandler` | class | — | — | 4 | 109 | [ClientEventHandler.java](top/theillusivec4/curios/client/ClientEventHandler.java) |
| `CurioInventoryCapability` | class | ICuriosItemHandler | — | 41 | 613 | [CurioInventoryCapability.java](top/theillusivec4/curios/common/capability/CurioInventoryCapability.java) |
| `GuiEventHandler` | class | — | — | 3 | 64 | [GuiEventHandler.java](top/theillusivec4/curios/client/gui/GuiEventHandler.java) |
| `CuriosClientPayloadHandler` | class | — | — | 13 | 75 | [CuriosClientPayloadHandler.java](top/theillusivec4/curios/common/network/client/CuriosClientPayloadHandler.java) |
| `ItemizedCurioCapability` | class | ICurio | — | 24 | 154 | [ItemizedCurioCapability.java](top/theillusivec4/curios/common/capability/ItemizedCurioCapability.java) |
| `CuriosEntityManager` | class | SimpleJsonResourceReloadListener | — | 6 | 210 | [CuriosEntityManager.java](top/theillusivec4/curios/common/data/CuriosEntityManager.java) |
| `AccessorEntity` | interface | — | Mixin | 0 | 12 | [AccessorEntity.java](top/theillusivec4/curios/mixin/core/AccessorEntity.java) |
| `MixinPowderSnowBlock` | class | — | Mixin | 0 | 21 | [MixinPowderSnowBlock.java](top/theillusivec4/curios/mixin/core/MixinPowderSnowBlock.java) |
| `MixinLivingEntity` | class | — | Mixin | 0 | 19 | [MixinLivingEntity.java](top/theillusivec4/curios/mixin/core/MixinLivingEntity.java) |
| `CuriosContainer` | class | RecipeBookMenu | — | 30 | 537 | [CuriosContainer.java](top/theillusivec4/curios/common/inventory/container/CuriosContainer.java) |
| `NetworkHandler` | class | — | — | 1 | 45 | [NetworkHandler.java](top/theillusivec4/curios/common/network/NetworkHandler.java) |
| `CuriosClientPackets` | class | — | — | 11 | 249 | [CuriosClientPackets.java](top/theillusivec4/curios/common/network/client/CuriosClientPackets.java) |
| `CurioItemHandler` | class | IItemHandler | — | 6 | 61 | [CurioItemHandler.java](top/theillusivec4/curios/common/capability/CurioItemHandler.java) |
| `CuriosSlotManager` | class | SimpleJsonResourceReloadListener | — | 12 | 330 | [CuriosSlotManager.java](top/theillusivec4/curios/common/data/CuriosSlotManager.java) |
| `CuriosRendererRegistry` | class | — | — | 3 | 29 | [CuriosRendererRegistry.java](top/theillusivec4/curios/api/client/CuriosRendererRegistry.java) |
| `MixinCuriosDataProvider` | class | — | Mixin | 0 | 25 | [MixinCuriosDataProvider.java](top/theillusivec4/curios/mixin/core/MixinCuriosDataProvider.java) |
| `SlotType` | class | ISlotType | — | 32 | 330 | [SlotType.java](top/theillusivec4/curios/common/slottype/SlotType.java) |
| `CuriosApi` | class | — | — | 40 | 256 | [CuriosApi.java](top/theillusivec4/curios/api/CuriosApi.java) |
| `ICuriosItemHandler` | interface | — | — | 0 | 191 | [ICuriosItemHandler.java](top/theillusivec4/curios/api/type/capability/ICuriosItemHandler.java) |
| `CuriosScreen` | class | EffectRenderingInventoryScreen | — | 18 | 446 | [CuriosScreen.java](top/theillusivec4/curios/client/gui/CuriosScreen.java) |
| `MixinInventory` | class | Container | Mixin | 1 | 57 | [MixinInventory.java](top/theillusivec4/curios/mixin/core/MixinInventory.java) |
| `MixinV1460` | class | — | Mixin | 1 | 40 | [MixinV1460.java](top/theillusivec4/curios/mixin/core/MixinV1460.java) |
| `MixinEnchantedCountIncreaseFunction` | class | — | Mixin | 0 | 35 | [MixinEnchantedCountIncreaseFunction.java](top/theillusivec4/curios/mixin/core/MixinEnchantedCountIncreaseFunction.java) |
| `MixinApplyBonusCount` | class | — | Mixin | 0 | 35 | [MixinApplyBonusCount.java](top/theillusivec4/curios/mixin/core/MixinApplyBonusCount.java) |
| `MixinCuriosTriggersEquip` | class | — | Mixin | 0 | 40 | [MixinCuriosTriggersEquip.java](top/theillusivec4/curios/mixin/core/MixinCuriosTriggersEquip.java) |
| `MixinCuriosApi` | class | — | Mixin | 0 | 149 | [MixinCuriosApi.java](top/theillusivec4/curios/mixin/core/MixinCuriosApi.java) |
| `MixinPiglinAi` | class | — | Mixin | 0 | 20 | [MixinPiglinAi.java](top/theillusivec4/curios/mixin/core/MixinPiglinAi.java) |
| `MixinNbtPredicate` | class | — | Mixin | 0 | 18 | [MixinNbtPredicate.java](top/theillusivec4/curios/mixin/core/MixinNbtPredicate.java) |
| `MixinCuriosTriggers` | class | — | Mixin | 0 | 43 | [MixinCuriosTriggers.java](top/theillusivec4/curios/mixin/core/MixinCuriosTriggers.java) |
| `CuriosCommand` | class | — | — | 11 | 329 | [CuriosCommand.java](top/theillusivec4/curios/server/command/CuriosCommand.java) |
| `CuriosRegistry` | class | — | — | 1 | 62 | [CuriosRegistry.java](top/theillusivec4/curios/common/CuriosRegistry.java) |
| `DynamicStackHandler` | class | ItemStackHandler | — | 7 | 96 | [DynamicStackHandler.java](top/theillusivec4/curios/common/inventory/DynamicStackHandler.java) |
| `CuriosServerPayloadHandler` | class | — | — | 7 | 190 | [CuriosServerPayloadHandler.java](top/theillusivec4/curios/common/network/server/CuriosServerPayloadHandler.java) |
| `KeyRegistry` | class | — | — | 0 | 8 | [KeyRegistry.java](top/theillusivec4/curios/client/KeyRegistry.java) |
| `CuriosClientConfig` | class | — | — | 4 | 92 | [CuriosClientConfig.java](top/theillusivec4/curios/client/CuriosClientConfig.java) |
| `CuriosImplMixinHooks` | class | — | — | 20 | 261 | [CuriosImplMixinHooks.java](top/theillusivec4/curios/mixin/CuriosImplMixinHooks.java) |
| `SPacketGrabbedItem` | record | CustomPacketPayload | — | 1 | 22 | [SPacketGrabbedItem.java](top/theillusivec4/curios/common/network/server/SPacketGrabbedItem.java) |
| `CuriosContainerHandler` | class | IGuiContainerHandler<CuriosScreen> | — | 1 | 16 | [CuriosContainerHandler.java](top/theillusivec4/curios/common/integration/jei/CuriosContainerHandler.java) |
| `LegacySlotManager` | class | — | — | 4 | 87 | [LegacySlotManager.java](top/theillusivec4/curios/common/slottype/LegacySlotManager.java) |
| `IDynamicStackHandler` | interface | — | — | 0 | 29 | [IDynamicStackHandler.java](top/theillusivec4/curios/api/type/inventory/IDynamicStackHandler.java) |
| `ICurioStacksHandler` | interface | — | — | 0 | 82 | [ICurioStacksHandler.java](top/theillusivec4/curios/api/type/inventory/ICurioStacksHandler.java) |
| `CurioAttributeModifierEvent` | class | Event | — | 10 | 77 | [CurioAttributeModifierEvent.java](top/theillusivec4/curios/api/event/CurioAttributeModifierEvent.java) |
| `SlotHelper` | class | ISlotHelper | — | 16 | 143 | [SlotHelper.java](top/theillusivec4/curios/server/SlotHelper.java) |
| `CuriosHelper` | class | ICuriosHelper | — | 18 | 141 | [CuriosHelper.java](top/theillusivec4/curios/common/CuriosHelper.java) |
| `CuriosConfig` | class | — | — | 0 | 69 | [CuriosConfig.java](top/theillusivec4/curios/common/CuriosConfig.java) |
| `SPacketSyncData` | class | CustomPacketPayload | — | 3 | 52 | [SPacketSyncData.java](top/theillusivec4/curios/common/network/server/sync/SPacketSyncData.java) |
| `CurioDropsEvent` | class | LivingEvent | — | 5 | 49 | [CurioDropsEvent.java](top/theillusivec4/curios/api/event/CurioDropsEvent.java) |
| `DropRulesEvent` | class | LivingEvent | — | 6 | 54 | [DropRulesEvent.java](top/theillusivec4/curios/api/event/DropRulesEvent.java) |
| `CuriosUtilMixinHooks` | class | — | — | 10 | 172 | [CuriosUtilMixinHooks.java](top/theillusivec4/curios/mixin/CuriosUtilMixinHooks.java) |
| `SPacketQuickMove` | record | CustomPacketPayload | — | 1 | 22 | [SPacketQuickMove.java](top/theillusivec4/curios/common/network/server/SPacketQuickMove.java) |
| `SPacketSetIcons` | class | CustomPacketPayload | — | 3 | 56 | [SPacketSetIcons.java](top/theillusivec4/curios/common/network/server/SPacketSetIcons.java) |
| `SPacketPage` | record | CustomPacketPayload | — | 1 | 22 | [SPacketPage.java](top/theillusivec4/curios/common/network/server/SPacketPage.java) |
| `SPacketBreak` | record | CustomPacketPayload | — | 1 | 22 | [SPacketBreak.java](top/theillusivec4/curios/common/network/server/SPacketBreak.java) |
| `SPacketSyncCurios` | class | CustomPacketPayload | — | 3 | 71 | [SPacketSyncCurios.java](top/theillusivec4/curios/common/network/server/sync/SPacketSyncCurios.java) |
| `SPacketSyncRender` | record | CustomPacketPayload | — | 1 | 30 | [SPacketSyncRender.java](top/theillusivec4/curios/common/network/server/sync/SPacketSyncRender.java) |
| `SPacketSyncStack` | record | CustomPacketPayload | — | 2 | 50 | [SPacketSyncStack.java](top/theillusivec4/curios/common/network/server/sync/SPacketSyncStack.java) |
| `SPacketSyncActiveState` | record | CustomPacketPayload | — | 1 | 30 | [SPacketSyncActiveState.java](top/theillusivec4/curios/common/network/server/sync/SPacketSyncActiveState.java) |

## 事件处理（`@SubscribeEvent`，4 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `CuriosEventHandler` | 13 | `top.theillusivec4.curios.common.event` | [CuriosEventHandler.java](top/theillusivec4/curios/common/event/CuriosEventHandler.java) |
| `Curios` | 4 | `top.theillusivec4.curios` | [Curios.java](top/theillusivec4/curios/Curios.java) |
| `ClientEventHandler` | 3 | `top.theillusivec4.curios.client` | [ClientEventHandler.java](top/theillusivec4/curios/client/ClientEventHandler.java) |
| `GuiEventHandler` | 3 | `top.theillusivec4.curios.client.gui` | [GuiEventHandler.java](top/theillusivec4/curios/client/gui/GuiEventHandler.java) |

## Mixin（13 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `AccessorEntity` | `AccessorEntity` | [AccessorEntity.java](top/theillusivec4/curios/mixin/core/AccessorEntity.java) |
| `MixinPowderSnowBlock` | `PowderSnowBlock` | [MixinPowderSnowBlock.java](top/theillusivec4/curios/mixin/core/MixinPowderSnowBlock.java) |
| `MixinLivingEntity` | `LivingEntity` | [MixinLivingEntity.java](top/theillusivec4/curios/mixin/core/MixinLivingEntity.java) |
| `MixinCuriosDataProvider` | `CuriosDataProvider` | [MixinCuriosDataProvider.java](top/theillusivec4/curios/mixin/core/MixinCuriosDataProvider.java) |
| `MixinInventory` | `Inventory` | [MixinInventory.java](top/theillusivec4/curios/mixin/core/MixinInventory.java) |
| `MixinV1460` | `V1460` | [MixinV1460.java](top/theillusivec4/curios/mixin/core/MixinV1460.java) |
| `MixinEnchantedCountIncreaseFunction` | `EnchantedCountIncreaseFunction` | [MixinEnchantedCountIncreaseFunction.java](top/theillusivec4/curios/mixin/core/MixinEnchantedCountIncreaseFunction.java) |
| `MixinApplyBonusCount` | `ApplyBonusCount` | [MixinApplyBonusCount.java](top/theillusivec4/curios/mixin/core/MixinApplyBonusCount.java) |
| `MixinCuriosTriggersEquip` | `CuriosTriggersEquip` | [MixinCuriosTriggersEquip.java](top/theillusivec4/curios/mixin/core/MixinCuriosTriggersEquip.java) |
| `MixinCuriosApi` | `CuriosApi` | [MixinCuriosApi.java](top/theillusivec4/curios/mixin/core/MixinCuriosApi.java) |
| `MixinPiglinAi` | `PiglinAi` | [MixinPiglinAi.java](top/theillusivec4/curios/mixin/core/MixinPiglinAi.java) |
| `MixinNbtPredicate` | `NbtPredicate` | [MixinNbtPredicate.java](top/theillusivec4/curios/mixin/core/MixinNbtPredicate.java) |
| `MixinCuriosTriggers` | `CuriosTriggers` | [MixinCuriosTriggers.java](top/theillusivec4/curios/mixin/core/MixinCuriosTriggers.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `CuriosClientConfig` | — | [CuriosClientConfig.java](top/theillusivec4/curios/client/CuriosClientConfig.java) |
| `CuriosConfig` | — | [CuriosConfig.java](top/theillusivec4/curios/common/CuriosConfig.java) |

## 全部类（123）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `Curios` | class | — | Mod | 16 | 212 | [Curios.java](top/theillusivec4/curios/Curios.java) |
| `CuriosEventHandler` | class | — | — | 17 | 684 | [CuriosEventHandler.java](top/theillusivec4/curios/common/event/CuriosEventHandler.java) |
| `CurioStacksHandler` | class | ICurioStacksHandler | — | 37 | 812 | [CurioStacksHandler.java](top/theillusivec4/curios/common/inventory/CurioStacksHandler.java) |
| `ClientEventHandler` | class | — | — | 4 | 109 | [ClientEventHandler.java](top/theillusivec4/curios/client/ClientEventHandler.java) |
| `CurioInventoryCapability` | class | ICuriosItemHandler | — | 41 | 613 | [CurioInventoryCapability.java](top/theillusivec4/curios/common/capability/CurioInventoryCapability.java) |
| `GuiEventHandler` | class | — | — | 3 | 64 | [GuiEventHandler.java](top/theillusivec4/curios/client/gui/GuiEventHandler.java) |
| `CuriosClientPayloadHandler` | class | — | — | 13 | 75 | [CuriosClientPayloadHandler.java](top/theillusivec4/curios/common/network/client/CuriosClientPayloadHandler.java) |
| `ItemizedCurioCapability` | class | ICurio | — | 24 | 154 | [ItemizedCurioCapability.java](top/theillusivec4/curios/common/capability/ItemizedCurioCapability.java) |
| `CuriosEntityManager` | class | SimpleJsonResourceReloadListener | — | 6 | 210 | [CuriosEntityManager.java](top/theillusivec4/curios/common/data/CuriosEntityManager.java) |
| `AccessorEntity` | interface | — | Mixin | 0 | 12 | [AccessorEntity.java](top/theillusivec4/curios/mixin/core/AccessorEntity.java) |
| `MixinPowderSnowBlock` | class | — | Mixin | 0 | 21 | [MixinPowderSnowBlock.java](top/theillusivec4/curios/mixin/core/MixinPowderSnowBlock.java) |
| `MixinLivingEntity` | class | — | Mixin | 0 | 19 | [MixinLivingEntity.java](top/theillusivec4/curios/mixin/core/MixinLivingEntity.java) |
| `CuriosContainer` | class | RecipeBookMenu | — | 30 | 537 | [CuriosContainer.java](top/theillusivec4/curios/common/inventory/container/CuriosContainer.java) |
| `NetworkHandler` | class | — | — | 1 | 45 | [NetworkHandler.java](top/theillusivec4/curios/common/network/NetworkHandler.java) |
| `CuriosClientPackets` | class | — | — | 11 | 249 | [CuriosClientPackets.java](top/theillusivec4/curios/common/network/client/CuriosClientPackets.java) |
| `CurioItemHandler` | class | IItemHandler | — | 6 | 61 | [CurioItemHandler.java](top/theillusivec4/curios/common/capability/CurioItemHandler.java) |
| `CuriosSlotManager` | class | SimpleJsonResourceReloadListener | — | 12 | 330 | [CuriosSlotManager.java](top/theillusivec4/curios/common/data/CuriosSlotManager.java) |
| `CuriosRendererRegistry` | class | — | — | 3 | 29 | [CuriosRendererRegistry.java](top/theillusivec4/curios/api/client/CuriosRendererRegistry.java) |
| `MixinCuriosDataProvider` | class | — | Mixin | 0 | 25 | [MixinCuriosDataProvider.java](top/theillusivec4/curios/mixin/core/MixinCuriosDataProvider.java) |
| `SlotType` | class | ISlotType | — | 32 | 330 | [SlotType.java](top/theillusivec4/curios/common/slottype/SlotType.java) |
| `CuriosApi` | class | — | — | 40 | 256 | [CuriosApi.java](top/theillusivec4/curios/api/CuriosApi.java) |
| `ICuriosItemHandler` | interface | — | — | 0 | 191 | [ICuriosItemHandler.java](top/theillusivec4/curios/api/type/capability/ICuriosItemHandler.java) |
| `CuriosScreen` | class | EffectRenderingInventoryScreen | — | 18 | 446 | [CuriosScreen.java](top/theillusivec4/curios/client/gui/CuriosScreen.java) |
| `MixinInventory` | class | Container | Mixin | 1 | 57 | [MixinInventory.java](top/theillusivec4/curios/mixin/core/MixinInventory.java) |
| `MixinV1460` | class | — | Mixin | 1 | 40 | [MixinV1460.java](top/theillusivec4/curios/mixin/core/MixinV1460.java) |
| `MixinEnchantedCountIncreaseFunction` | class | — | Mixin | 0 | 35 | [MixinEnchantedCountIncreaseFunction.java](top/theillusivec4/curios/mixin/core/MixinEnchantedCountIncreaseFunction.java) |
| `MixinApplyBonusCount` | class | — | Mixin | 0 | 35 | [MixinApplyBonusCount.java](top/theillusivec4/curios/mixin/core/MixinApplyBonusCount.java) |
| `MixinCuriosTriggersEquip` | class | — | Mixin | 0 | 40 | [MixinCuriosTriggersEquip.java](top/theillusivec4/curios/mixin/core/MixinCuriosTriggersEquip.java) |
| `MixinCuriosApi` | class | — | Mixin | 0 | 149 | [MixinCuriosApi.java](top/theillusivec4/curios/mixin/core/MixinCuriosApi.java) |
| `MixinPiglinAi` | class | — | Mixin | 0 | 20 | [MixinPiglinAi.java](top/theillusivec4/curios/mixin/core/MixinPiglinAi.java) |
| `MixinNbtPredicate` | class | — | Mixin | 0 | 18 | [MixinNbtPredicate.java](top/theillusivec4/curios/mixin/core/MixinNbtPredicate.java) |
| `MixinCuriosTriggers` | class | — | Mixin | 0 | 43 | [MixinCuriosTriggers.java](top/theillusivec4/curios/mixin/core/MixinCuriosTriggers.java) |
| `CuriosCommand` | class | — | — | 11 | 329 | [CuriosCommand.java](top/theillusivec4/curios/server/command/CuriosCommand.java) |
| `CuriosRegistry` | class | — | — | 1 | 62 | [CuriosRegistry.java](top/theillusivec4/curios/common/CuriosRegistry.java) |
| `DynamicStackHandler` | class | ItemStackHandler | — | 7 | 96 | [DynamicStackHandler.java](top/theillusivec4/curios/common/inventory/DynamicStackHandler.java) |
| `CuriosServerPayloadHandler` | class | — | — | 7 | 190 | [CuriosServerPayloadHandler.java](top/theillusivec4/curios/common/network/server/CuriosServerPayloadHandler.java) |
| `KeyRegistry` | class | — | — | 0 | 8 | [KeyRegistry.java](top/theillusivec4/curios/client/KeyRegistry.java) |
| `CuriosClientConfig` | class | — | — | 4 | 92 | [CuriosClientConfig.java](top/theillusivec4/curios/client/CuriosClientConfig.java) |
| `CuriosImplMixinHooks` | class | — | — | 20 | 261 | [CuriosImplMixinHooks.java](top/theillusivec4/curios/mixin/CuriosImplMixinHooks.java) |
| `SPacketGrabbedItem` | record | CustomPacketPayload | — | 1 | 22 | [SPacketGrabbedItem.java](top/theillusivec4/curios/common/network/server/SPacketGrabbedItem.java) |
| `CuriosContainerHandler` | class | IGuiContainerHandler<CuriosScreen> | — | 1 | 16 | [CuriosContainerHandler.java](top/theillusivec4/curios/common/integration/jei/CuriosContainerHandler.java) |
| `LegacySlotManager` | class | — | — | 4 | 87 | [LegacySlotManager.java](top/theillusivec4/curios/common/slottype/LegacySlotManager.java) |
| `IDynamicStackHandler` | interface | — | — | 0 | 29 | [IDynamicStackHandler.java](top/theillusivec4/curios/api/type/inventory/IDynamicStackHandler.java) |
| `ICurioStacksHandler` | interface | — | — | 0 | 82 | [ICurioStacksHandler.java](top/theillusivec4/curios/api/type/inventory/ICurioStacksHandler.java) |
| `CurioAttributeModifierEvent` | class | Event | — | 10 | 77 | [CurioAttributeModifierEvent.java](top/theillusivec4/curios/api/event/CurioAttributeModifierEvent.java) |
| `SlotHelper` | class | ISlotHelper | — | 16 | 143 | [SlotHelper.java](top/theillusivec4/curios/server/SlotHelper.java) |
| `CuriosHelper` | class | ICuriosHelper | — | 18 | 141 | [CuriosHelper.java](top/theillusivec4/curios/common/CuriosHelper.java) |
| `CuriosConfig` | class | — | — | 0 | 69 | [CuriosConfig.java](top/theillusivec4/curios/common/CuriosConfig.java) |
| `SPacketSyncData` | class | CustomPacketPayload | — | 3 | 52 | [SPacketSyncData.java](top/theillusivec4/curios/common/network/server/sync/SPacketSyncData.java) |
| `CurioDropsEvent` | class | LivingEvent | — | 5 | 49 | [CurioDropsEvent.java](top/theillusivec4/curios/api/event/CurioDropsEvent.java) |
| `DropRulesEvent` | class | LivingEvent | — | 6 | 54 | [DropRulesEvent.java](top/theillusivec4/curios/api/event/DropRulesEvent.java) |
| `CuriosUtilMixinHooks` | class | — | — | 10 | 172 | [CuriosUtilMixinHooks.java](top/theillusivec4/curios/mixin/CuriosUtilMixinHooks.java) |
| `SPacketQuickMove` | record | CustomPacketPayload | — | 1 | 22 | [SPacketQuickMove.java](top/theillusivec4/curios/common/network/server/SPacketQuickMove.java) |
| `SPacketSetIcons` | class | CustomPacketPayload | — | 3 | 56 | [SPacketSetIcons.java](top/theillusivec4/curios/common/network/server/SPacketSetIcons.java) |
| `SPacketPage` | record | CustomPacketPayload | — | 1 | 22 | [SPacketPage.java](top/theillusivec4/curios/common/network/server/SPacketPage.java) |
| `SPacketBreak` | record | CustomPacketPayload | — | 1 | 22 | [SPacketBreak.java](top/theillusivec4/curios/common/network/server/SPacketBreak.java) |
| `SPacketSyncCurios` | class | CustomPacketPayload | — | 3 | 71 | [SPacketSyncCurios.java](top/theillusivec4/curios/common/network/server/sync/SPacketSyncCurios.java) |
| `SPacketSyncRender` | record | CustomPacketPayload | — | 1 | 30 | [SPacketSyncRender.java](top/theillusivec4/curios/common/network/server/sync/SPacketSyncRender.java) |
| `SPacketSyncStack` | record | CustomPacketPayload | — | 2 | 50 | [SPacketSyncStack.java](top/theillusivec4/curios/common/network/server/sync/SPacketSyncStack.java) |
| `SPacketSyncActiveState` | record | CustomPacketPayload | — | 1 | 30 | [SPacketSyncActiveState.java](top/theillusivec4/curios/common/network/server/sync/SPacketSyncActiveState.java) |
| `SPacketSyncModifiers` | class | CustomPacketPayload | — | 3 | 72 | [SPacketSyncModifiers.java](top/theillusivec4/curios/common/network/server/sync/SPacketSyncModifiers.java) |
| `CPacketOpenCurios` | record | CustomPacketPayload | — | 1 | 22 | [CPacketOpenCurios.java](top/theillusivec4/curios/common/network/client/CPacketOpenCurios.java) |
| `CPacketPage` | record | CustomPacketPayload | — | 1 | 22 | [CPacketPage.java](top/theillusivec4/curios/common/network/client/CPacketPage.java) |
| `CPacketOpenVanilla` | record | CustomPacketPayload | — | 1 | 22 | [CPacketOpenVanilla.java](top/theillusivec4/curios/common/network/client/CPacketOpenVanilla.java) |
| `CPacketToggleCosmetics` | record | CustomPacketPayload | — | 1 | 22 | [CPacketToggleCosmetics.java](top/theillusivec4/curios/common/network/client/CPacketToggleCosmetics.java) |
| `CPacketToggleRender` | record | CustomPacketPayload | — | 1 | 22 | [CPacketToggleRender.java](top/theillusivec4/curios/common/network/client/CPacketToggleRender.java) |
| `CPacketDestroy` | record | CustomPacketPayload | — | 3 | 27 | [CPacketDestroy.java](top/theillusivec4/curios/common/network/client/CPacketDestroy.java) |
| `SlotData` | class | ISlotData | — | 13 | 154 | [SlotData.java](top/theillusivec4/curios/common/data/SlotData.java) |
| `CuriosCapability` | class | — | — | 0 | 18 | [CuriosCapability.java](top/theillusivec4/curios/api/CuriosCapability.java) |
| `CurioChangeEvent` | class | LivingEvent | — | 4 | 40 | [CurioChangeEvent.java](top/theillusivec4/curios/api/event/CurioChangeEvent.java) |
| `CurioCanEquipEvent` | class | LivingEvent | — | 4 | 41 | [CurioCanEquipEvent.java](top/theillusivec4/curios/api/event/CurioCanEquipEvent.java) |
| `SlotModifiersUpdatedEvent` | class | LivingEvent | — | 1 | 20 | [SlotModifiersUpdatedEvent.java](top/theillusivec4/curios/api/event/SlotModifiersUpdatedEvent.java) |
| `CurioCanUnequipEvent` | class | LivingEvent | — | 4 | 35 | [CurioCanUnequipEvent.java](top/theillusivec4/curios/api/event/CurioCanUnequipEvent.java) |
| `RegisterCuriosExtensionsEvent` | class | Event | — | 2 | 16 | [RegisterCuriosExtensionsEvent.java](top/theillusivec4/curios/api/extensions/RegisterCuriosExtensionsEvent.java) |
| `CurioSlot` | class | SlotItemHandler | — | 11 | 147 | [CurioSlot.java](top/theillusivec4/curios/common/inventory/CurioSlot.java) |
| `CurioInventory` | class | INBTSerializable<CompoundTag> | — | 5 | 201 | [CurioInventory.java](top/theillusivec4/curios/common/capability/CurioInventory.java) |
| `CuriosIntegrations` | class | — | — | 1 | 14 | [CuriosIntegrations.java](top/theillusivec4/curios/common/integration/CuriosIntegrations.java) |
| `CuriosEmiIntegration` | class | — | — | 2 | 36 | [CuriosEmiIntegration.java](top/theillusivec4/curios/common/integration/emi/CuriosEmiIntegration.java) |
| `EntitiesData` | class | IEntitiesData | — | 6 | 77 | [EntitiesData.java](top/theillusivec4/curios/common/data/EntitiesData.java) |
| `CuriosDataProvider` | class | DataProvider | — | 9 | 90 | [CuriosDataProvider.java](top/theillusivec4/curios/api/CuriosDataProvider.java) |
| `CuriosTooltip` | class | — | — | 10 | 84 | [CuriosTooltip.java](top/theillusivec4/curios/api/CuriosTooltip.java) |
| `SlotTypeMessage` | class | — | ScheduledForRemoval | 14 | 134 | [SlotTypeMessage.java](top/theillusivec4/curios/api/SlotTypeMessage.java) |
| `ICuriosMenu` | interface | — | — | 0 | 6 | [ICuriosMenu.java](top/theillusivec4/curios/api/type/ICuriosMenu.java) |
| `ICurioItem` | interface | — | — | 0 | 140 | [ICurioItem.java](top/theillusivec4/curios/api/type/capability/ICurioItem.java) |
| `ICurioRenderer` | interface | — | — | 0 | 78 | [ICurioRenderer.java](top/theillusivec4/curios/api/client/ICurioRenderer.java) |
| `ICuriosScreen` | interface | — | — | 0 | 5 | [ICuriosScreen.java](top/theillusivec4/curios/api/client/ICuriosScreen.java) |
| `CuriosSelectorOptions` | class | — | — | 8 | 175 | [CuriosSelectorOptions.java](top/theillusivec4/curios/server/command/CuriosSelectorOptions.java) |
| `CurioArgumentType` | class | ArgumentType<String> | — | 5 | 52 | [CurioArgumentType.java](top/theillusivec4/curios/server/command/CurioArgumentType.java) |
| `SetCurioAttributesFunction` | class | LootItemConditionalFunction | — | 5 | 139 | [SetCurioAttributesFunction.java](top/theillusivec4/curios/common/util/SetCurioAttributesFunction.java) |
| `CuriosTriggers` | class | — | — | 7 | 56 | [CuriosTriggers.java](top/theillusivec4/curios/api/CuriosTriggers.java) |
| `CurioAttributeModifiers` | record | — | — | 7 | 108 | [CurioAttributeModifiers.java](top/theillusivec4/curios/api/CurioAttributeModifiers.java) |
| `SlotPredicate` | record | — | — | 5 | 51 | [SlotPredicate.java](top/theillusivec4/curios/api/SlotPredicate.java) |
| `ICurio` | interface | — | — | 3 | 206 | [ICurio.java](top/theillusivec4/curios/api/type/capability/ICurio.java) |
| `ISlotData` | interface | — | — | 0 | 40 | [ISlotData.java](top/theillusivec4/curios/api/type/data/ISlotData.java) |
| `IEntitiesData` | interface | — | — | 0 | 21 | [IEntitiesData.java](top/theillusivec4/curios/api/type/data/IEntitiesData.java) |
| `ISlotHelper` | interface | — | ScheduledForRemoval | 0 | 79 | [ISlotHelper.java](top/theillusivec4/curios/api/type/util/ISlotHelper.java) |
| `IIconHelper` | interface | — | ScheduledForRemoval | 0 | 21 | [IIconHelper.java](top/theillusivec4/curios/api/type/util/IIconHelper.java) |
| `ICuriosHelper` | interface | — | ScheduledForRemoval | 0 | 98 | [ICuriosHelper.java](top/theillusivec4/curios/api/type/util/ICuriosHelper.java) |
| `IconHelper` | class | IIconHelper | — | 3 | 26 | [IconHelper.java](top/theillusivec4/curios/client/IconHelper.java) |
| `CuriosConstants` | class | — | — | 0 | 11 | [CuriosConstants.java](top/theillusivec4/curios/CuriosConstants.java) |
| `CosmeticCurioSlot` | class | CurioSlot | — | 2 | 29 | [CosmeticCurioSlot.java](top/theillusivec4/curios/common/inventory/CosmeticCurioSlot.java) |
| `CuriosContainerProvider` | class | MenuProvider | — | 2 | 22 | [CuriosContainerProvider.java](top/theillusivec4/curios/common/inventory/container/CuriosContainerProvider.java) |
| `CuriosExclusionAreas` | class | — | — | 1 | 34 | [CuriosExclusionAreas.java](top/theillusivec4/curios/common/integration/CuriosExclusionAreas.java) |
| `CuriosReiPlugin` | class | REIClientPlugin | REIPluginClient | 1 | 27 | [CuriosReiPlugin.java](top/theillusivec4/curios/common/integration/rei/CuriosReiPlugin.java) |
| `CuriosEmiPlugin` | class | EmiPlugin | EmiEntrypoint | 1 | 21 | [CuriosEmiPlugin.java](top/theillusivec4/curios/common/integration/emi/CuriosEmiPlugin.java) |
| `CuriosJeiPlugin` | class | IModPlugin | JeiPlugin | 2 | 21 | [CuriosJeiPlugin.java](top/theillusivec4/curios/common/integration/jei/CuriosJeiPlugin.java) |
| `EquipCurioTrigger` | class | SimpleCriterionTrigger | — | 4 | 79 | [EquipCurioTrigger.java](top/theillusivec4/curios/common/util/EquipCurioTrigger.java) |
| `Services` | class | — | — | 1 | 13 | [Services.java](top/theillusivec4/curios/platform/Services.java) |
| `NeoForgeCurios` | class | ICuriosPlatform | — | 4 | 34 | [NeoForgeCurios.java](top/theillusivec4/curios/platform/NeoForgeCurios.java) |
| `ICuriosPlatform` | interface | — | — | 0 | 20 | [ICuriosPlatform.java](top/theillusivec4/curios/platform/services/ICuriosPlatform.java) |
| `CuriosTags` | class | — | — | 1 | 24 | [CuriosTags.java](top/theillusivec4/curios/api/CuriosTags.java) |
| `SlotTypePreset` | enum | — | ScheduledForRemoval | 3 | 53 | [SlotTypePreset.java](top/theillusivec4/curios/api/SlotTypePreset.java) |
| `SlotResult` | record | — | — | 0 | 7 | [SlotResult.java](top/theillusivec4/curios/api/SlotResult.java) |
| `SlotAttribute` | class | Attribute | — | 3 | 47 | [SlotAttribute.java](top/theillusivec4/curios/api/SlotAttribute.java) |
| `SlotContext` | record | — | — | 0 | 7 | [SlotContext.java](top/theillusivec4/curios/api/SlotContext.java) |
| `ISlotType` | interface | — | — | 0 | 52 | [ISlotType.java](top/theillusivec4/curios/api/type/ISlotType.java) |
| `CuriosExtensions` | class | — | Internal | 0 | 31 | [CuriosExtensions.java](top/theillusivec4/curios/api/extensions/CuriosExtensions.java) |
| `ICurioSlotExtension` | interface | — | — | 0 | 28 | [ICurioSlotExtension.java](top/theillusivec4/curios/api/extensions/ICurioSlotExtension.java) |
| `PageButton` | class | Button | — | 1 | 73 | [PageButton.java](top/theillusivec4/curios/client/gui/PageButton.java) |
| `CosmeticButton` | class | ImageButton | — | 1 | 47 | [CosmeticButton.java](top/theillusivec4/curios/client/gui/CosmeticButton.java) |
| `CuriosButton` | class | ImageButton | — | 1 | 72 | [CuriosButton.java](top/theillusivec4/curios/client/gui/CuriosButton.java) |
| `RenderButton` | class | ImageButton | — | 2 | 45 | [RenderButton.java](top/theillusivec4/curios/client/gui/RenderButton.java) |
| `CuriosLayer` | class | — | — | 1 | 84 | [CuriosLayer.java](top/theillusivec4/curios/client/render/CuriosLayer.java) |
