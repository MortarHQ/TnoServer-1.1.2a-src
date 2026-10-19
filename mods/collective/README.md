# Collective（`collective`）

> jar：`[初始装备前置]collective.jar` ｜ 版本：`8.3` ｜ 作者：Rick South
> 规模：**414 个类 / 85 个包 / 29052 行** ｜ 事件处理类 5 ｜ Mixin 63 ｜ 注册/管理类 48

**依赖**：`minecraft`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.natamus.collective.fabric.mixin` | 40 | `ItemEntityMixin`、`BlockEntityMixin`、`AbstractFurnaceBlockEntityMixin` |
| `com.natamus.collective_common_fabric.functions` | 38 | `BlockPosFunctions`、`EntityFunctions`、`GearFunctions` |
| `com.natamus.collective_common_neoforge.functions` | 38 | `BlockPosFunctions`、`EntityFunctions`、`GearFunctions` |
| `com.natamus.collective_common_forge.functions` | 38 | `BlockPosFunctions`、`EntityFunctions`、`GearFunctions` |
| `com.natamus.collective.fabric.callbacks` | 20 | `CollectiveCommandEvents`、`CollectiveItemEvents`、`CollectiveEntityEvents` |
| `com.natamus.collective.fabric.mixin.crop` | 10 | `CocoaBlockMixin`、`GrowingPlantHeadBlockMixin`、`CactusBlockMixin` |
| `com.natamus.collective.neoforge.mixin` | 7 | `BlockEntityMixin`、`TitleScreenMixin`、`BoneMealItemMixin` |
| `com.natamus.collective_common_fabric.services.helpers` | 7 | `EventTriggerHelper`、`RegisterItemHelper`、`RegisterBlockHelper` |
| `com.natamus.collective_common_neoforge.services.helpers` | 7 | `EventTriggerHelper`、`RegisterItemHelper`、`RegisterBlockHelper` |
| `com.natamus.collective.forge.services` | 7 | `ForgeEventTriggerHelper`、`ForgeRegisterBlockHelper`、`ForgeBlockTagsHelper` |
| `com.natamus.collective.neoforge.services` | 7 | `NeoForgeRegisterBlockHelper`、`NeoForgeEventTriggerHelper`、`NeoForgeToolFunctionsHelper` |
| `com.natamus.collective.fabric.services` | 7 | `FabricEventTriggerHelper`、`FabricRegisterBlockHelper`、`FabricBlockTagsHelper` |
| `com.natamus.collective_common_forge.services.helpers` | 7 | `EventTriggerHelper`、`RegisterItemHelper`、`RegisterBlockHelper` |
| `com.natamus.collective.forge.mixin` | 6 | `BlockEntityMixin`、`TitleScreenMixin`、`BoneMealItemMixin` |
| `com.natamus.collective_common_fabric.implementations.event` | 6 | `EventFactoryImpl`、`EventPhaseData`、`ArrayBackedEvent` |
| `com.natamus.collective_common_neoforge.implementations.event` | 6 | `EventFactoryImpl`、`EventPhaseData`、`ArrayBackedEvent` |
| `com.natamus.collective_common_forge.implementations.event` | 6 | `EventFactoryImpl`、`EventPhaseData`、`ArrayBackedEvent` |
| `com.natamus.collective_common_fabric.data` | 5 | `BlockEntityData`、`GlobalVariables`、`Constants` |
| `com.natamus.collective_common_neoforge.data` | 5 | `BlockEntityData`、`GlobalVariables`、`Constants` |
| `com.natamus.collective_common_forge.data` | 5 | `BlockEntityData`、`GlobalVariables`、`Constants` |
| `com.natamus.collective_common_fabric.globalcallbacks` | 5 | `CachedBlockEntityCallback`、`MainMenuLoadedCallback`、`JSONCallback` |
| `com.natamus.collective_common_fabric.schematic` | 5 | `Schematic`、`SchematicBlockObject`、`SchematicFunctions` |
| `com.natamus.collective_common_neoforge.globalcallbacks` | 5 | `CachedBlockEntityCallback`、`MainMenuLoadedCallback`、`JSONCallback` |
| `com.natamus.collective_common_neoforge.schematic` | 5 | `Schematic`、`SchematicBlockObject`、`SchematicFunctions` |
| `com.natamus.collective_common_forge.globalcallbacks` | 5 | `CachedBlockEntityCallback`、`MainMenuLoadedCallback`、`JSONCallback` |
| `com.natamus.collective_common_forge.schematic` | 5 | `Schematic`、`SchematicBlockObject`、`SchematicFunctions` |
| `com.natamus.collective_common_fabric.implementations.networking` | 4 | `DelayedPacketRegistrationHandler`、`PacketRegistrationHandler`、`NetworkSetup` |
| `com.natamus.collective_common_neoforge.implementations.networking` | 4 | `DelayedPacketRegistrationHandler`、`PacketRegistrationHandler`、`NetworkSetup` |
| `com.natamus.collective_common_forge.implementations.networking` | 4 | `DelayedPacketRegistrationHandler`、`PacketRegistrationHandler`、`NetworkSetup` |
| `com.natamus.collective_common_fabric.config` | 4 | `DuskConfig`、`CollectiveConfigHandler`、`GenerateJSONFiles` |
| `com.natamus.collective_common_neoforge.config` | 4 | `DuskConfig`、`CollectiveConfigHandler`、`GenerateJSONFiles` |
| `com.natamus.collective_common_forge.config` | 4 | `DuskConfig`、`CollectiveConfigHandler`、`GenerateJSONFiles` |
| `com.natamus.collective` | 4 | `CollectiveNeoForge`、`CollectiveForge`、`CollectiveFabricClient` |
| `com.natamus.collective_common_fabric.implementations.networking.data` | 4 | `CommonPacketWrapper`、`PacketContext`、`PacketContainer` |
| `com.natamus.collective_common_neoforge.implementations.networking.data` | 4 | `CommonPacketWrapper`、`PacketContext`、`PacketContainer` |
| `com.natamus.collective_common_forge.implementations.networking.data` | 4 | `CommonPacketWrapper`、`PacketContext`、`PacketContainer` |
| `com.natamus.collective_common_fabric.implementations.networking.api` | 3 | `NetworkHandler`、`Network`、`Dispatcher` |
| `com.natamus.collective_common_neoforge.implementations.networking.api` | 3 | `NetworkHandler`、`Network`、`Dispatcher` |
| `com.natamus.collective_common_forge.implementations.networking.api` | 3 | `NetworkHandler`、`Network`、`Dispatcher` |
| `com.natamus.collective.forge.events` | 2 | `RegisterCollectiveForgeEvents`、`RegisterCollectiveForgeClientEvents` |
| … | | 其余 45 个包 |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `RegisterCollectiveForgeEvents` | class | — | EventBusSubscriber | 8 | 84 | [RegisterCollectiveForgeEvents.java](com/natamus/collective/forge/events/RegisterCollectiveForgeEvents.java) |
| `RegisterCollectiveNeoForgeEvents` | class | — | EventBusSubscriber | 7 | 68 | [RegisterCollectiveNeoForgeEvents.java](com/natamus/collective/neoforge/events/RegisterCollectiveNeoForgeEvents.java) |
| `DelayedPacketRegistrationHandler` | class | PacketRegistrar | — | 3 | 38 | [DelayedPacketRegistrationHandler.java](com/natamus/collective_common_fabric/implementations/networking/DelayedPacketRegistrationHandler.java) |
| `PacketRegistrationHandler` | class | NetworkHandler | — | 3 | 40 | [PacketRegistrationHandler.java](com/natamus/collective_common_fabric/implementations/networking/PacketRegistrationHandler.java) |
| `DelayedPacketRegistrationHandler` | class | PacketRegistrar | — | 3 | 42 | [DelayedPacketRegistrationHandler.java](com/natamus/collective_common_neoforge/implementations/networking/DelayedPacketRegistrationHandler.java) |
| `PacketRegistrationHandler` | class | NetworkHandler | — | 3 | 44 | [PacketRegistrationHandler.java](com/natamus/collective_common_neoforge/implementations/networking/PacketRegistrationHandler.java) |
| `RegisterCollectiveForgeClientEvents` | class | — | — | 1 | 16 | [RegisterCollectiveForgeClientEvents.java](com/natamus/collective/forge/events/RegisterCollectiveForgeClientEvents.java) |
| `RegisterCollectiveNeoForgeClientEvents` | class | — | — | 1 | 13 | [RegisterCollectiveNeoForgeClientEvents.java](com/natamus/collective/neoforge/events/RegisterCollectiveNeoForgeClientEvents.java) |
| `DelayedPacketRegistrationHandler` | class | PacketRegistrar | — | 3 | 42 | [DelayedPacketRegistrationHandler.java](com/natamus/collective_common_forge/implementations/networking/DelayedPacketRegistrationHandler.java) |
| `PacketRegistrationHandler` | class | NetworkHandler | — | 3 | 44 | [PacketRegistrationHandler.java](com/natamus/collective_common_forge/implementations/networking/PacketRegistrationHandler.java) |
| `NeoForgeNetworkHandler` | class | PacketRegistrationHandler | — | 6 | 82 | [NeoForgeNetworkHandler.java](com/natamus/collective/neoforge/networking/NeoForgeNetworkHandler.java) |
| `DuskConfig` | class | class_4266 | — | 24 | 819 | [DuskConfig.java](com/natamus/collective_common_fabric/config/DuskConfig.java) |
| `DuskConfig` | class | net.minecraft.client.gui.components. | — | 24 | 810 | [DuskConfig.java](com/natamus/collective_common_neoforge/config/DuskConfig.java) |
| `DuskConfig` | class | net.minecraft.client.gui.components. | — | 24 | 810 | [DuskConfig.java](com/natamus/collective_common_forge/config/DuskConfig.java) |
| `CollectiveCommandEvents` | class | — | — | 0 | 25 | [CollectiveCommandEvents.java](com/natamus/collective/fabric/callbacks/CollectiveCommandEvents.java) |
| `CollectiveEvents` | class | — | — | 5 | 250 | [CollectiveEvents.java](com/natamus/collective_common_fabric/events/CollectiveEvents.java) |
| `CollectiveClientEvents` | class | — | — | 1 | 23 | [CollectiveClientEvents.java](com/natamus/collective_common_fabric/events/CollectiveClientEvents.java) |
| `CollectiveEvents` | class | — | — | 5 | 250 | [CollectiveEvents.java](com/natamus/collective_common_neoforge/events/CollectiveEvents.java) |
| `CollectiveClientEvents` | class | — | — | 1 | 23 | [CollectiveClientEvents.java](com/natamus/collective_common_neoforge/events/CollectiveClientEvents.java) |
| `CollectiveNeoForge` | class | — | Mod | 3 | 55 | [CollectiveNeoForge.java](com/natamus/collective/CollectiveNeoForge.java) |
| `CollectiveForge` | class | — | Mod | 3 | 53 | [CollectiveForge.java](com/natamus/collective/CollectiveForge.java) |
| `BlockEntityMixin` | class | — | Mixin | 2 | 48 | [BlockEntityMixin.java](com/natamus/collective/forge/mixin/BlockEntityMixin.java) |
| `BlockEntityMixin` | class | — | Mixin | 2 | 48 | [BlockEntityMixin.java](com/natamus/collective/neoforge/mixin/BlockEntityMixin.java) |
| `ItemEntityMixin` | class | class_1297 | Mixin | 2 | 42 | [ItemEntityMixin.java](com/natamus/collective/fabric/mixin/ItemEntityMixin.java) |
| `BlockEntityMixin` | class | — | Mixin | 2 | 48 | [BlockEntityMixin.java](com/natamus/collective/fabric/mixin/BlockEntityMixin.java) |
| `AbstractFurnaceBlockEntityMixin` | class | — | Mixin | 1 | 27 | [AbstractFurnaceBlockEntityMixin.java](com/natamus/collective/fabric/mixin/AbstractFurnaceBlockEntityMixin.java) |
| `BlockItemMixin` | class | — | Mixin | 1 | 26 | [BlockItemMixin.java](com/natamus/collective/fabric/mixin/BlockItemMixin.java) |
| `ItemInHandRendererMixin` | class | — | Mixin | 1 | 37 | [ItemInHandRendererMixin.java](com/natamus/collective/fabric/mixin/ItemInHandRendererMixin.java) |
| `CollectiveItemEvents` | class | — | — | 0 | 82 | [CollectiveItemEvents.java](com/natamus/collective/fabric/callbacks/CollectiveItemEvents.java) |
| `CollectiveEntityEvents` | class | — | — | 0 | 164 | [CollectiveEntityEvents.java](com/natamus/collective/fabric/callbacks/CollectiveEntityEvents.java) |
| `CollectiveClientEvents` | class | — | — | 0 | 24 | [CollectiveClientEvents.java](com/natamus/collective/fabric/callbacks/CollectiveClientEvents.java) |
| `CollectiveBlockEvents` | class | — | — | 0 | 115 | [CollectiveBlockEvents.java](com/natamus/collective/fabric/callbacks/CollectiveBlockEvents.java) |
| `CollectiveEvents` | class | — | — | 5 | 250 | [CollectiveEvents.java](com/natamus/collective_common_forge/events/CollectiveEvents.java) |
| `CollectiveClientEvents` | class | — | — | 1 | 23 | [CollectiveClientEvents.java](com/natamus/collective_common_forge/events/CollectiveClientEvents.java) |
| `CollectiveConfigHandler` | class | DuskConfig | — | 1 | 39 | [CollectiveConfigHandler.java](com/natamus/collective_common_fabric/config/CollectiveConfigHandler.java) |
| `BlockPosFunctions` | class | — | — | 27 | 432 | [BlockPosFunctions.java](com/natamus/collective_common_fabric/functions/BlockPosFunctions.java) |
| `CollectiveConfigHandler` | class | DuskConfig | — | 1 | 39 | [CollectiveConfigHandler.java](com/natamus/collective_common_neoforge/config/CollectiveConfigHandler.java) |
| `BlockPosFunctions` | class | — | — | 27 | 426 | [BlockPosFunctions.java](com/natamus/collective_common_neoforge/functions/BlockPosFunctions.java) |
| `ForgeNetworkHandler` | class | PacketRegistrationHandler | — | 5 | 93 | [ForgeNetworkHandler.java](com/natamus/collective/forge/networking/ForgeNetworkHandler.java) |
| `CommandsMixin` | class | — | Mixin | 0 | 9 | [CommandsMixin.java](com/natamus/collective/fabric/mixin/CommandsMixin.java) |
| `LivingEntityMixin` | class | — | Mixin | 8 | 95 | [LivingEntityMixin.java](com/natamus/collective/fabric/mixin/LivingEntityMixin.java) |
| `ServerGamePacketListenerImplMixin` | class | — | Mixin | 1 | 41 | [ServerGamePacketListenerImplMixin.java](com/natamus/collective/fabric/mixin/ServerGamePacketListenerImplMixin.java) |
| `TeleportCommandMixin` | class | — | Mixin | 1 | 42 | [TeleportCommandMixin.java](com/natamus/collective/fabric/mixin/TeleportCommandMixin.java) |
| `CollectiveConfigHandler` | class | DuskConfig | — | 1 | 39 | [CollectiveConfigHandler.java](com/natamus/collective_common_forge/config/CollectiveConfigHandler.java) |
| `BlockPosFunctions` | class | — | — | 27 | 426 | [BlockPosFunctions.java](com/natamus/collective_common_forge/functions/BlockPosFunctions.java) |
| `EntityFunctions` | class | — | — | 26 | 205 | [EntityFunctions.java](com/natamus/collective_common_fabric/functions/EntityFunctions.java) |
| `NetworkHandler` | interface | — | — | 0 | 54 | [NetworkHandler.java](com/natamus/collective_common_fabric/implementations/networking/api/NetworkHandler.java) |
| `EntityFunctions` | class | — | — | 26 | 205 | [EntityFunctions.java](com/natamus/collective_common_neoforge/functions/EntityFunctions.java) |
| `NetworkHandler` | interface | — | — | 0 | 54 | [NetworkHandler.java](com/natamus/collective_common_neoforge/implementations/networking/api/NetworkHandler.java) |
| `TitleScreenMixin` | class | — | Mixin | 1 | 24 | [TitleScreenMixin.java](com/natamus/collective/forge/mixin/TitleScreenMixin.java) |
| `BoneMealItemMixin` | class | — | Mixin | 2 | 33 | [BoneMealItemMixin.java](com/natamus/collective/forge/mixin/BoneMealItemMixin.java) |
| `TitleScreenMixin` | class | — | Mixin | 1 | 24 | [TitleScreenMixin.java](com/natamus/collective/neoforge/mixin/TitleScreenMixin.java) |
| `BoneMealItemMixin` | class | — | Mixin | 2 | 33 | [BoneMealItemMixin.java](com/natamus/collective/neoforge/mixin/BoneMealItemMixin.java) |
| `FabricNetworkHandler` | class | PacketRegistrationHandler | — | 4 | 74 | [FabricNetworkHandler.java](com/natamus/collective/fabric/networking/FabricNetworkHandler.java) |
| `TitleScreenMixin` | class | — | Mixin | 1 | 24 | [TitleScreenMixin.java](com/natamus/collective/fabric/mixin/TitleScreenMixin.java) |
| `EntityTypeMixin` | class | — | Mixin | 1 | 42 | [EntityTypeMixin.java](com/natamus/collective/fabric/mixin/EntityTypeMixin.java) |
| `PistonBaseBlockMixin` | class | — | Mixin | 2 | 49 | [PistonBaseBlockMixin.java](com/natamus/collective/fabric/mixin/PistonBaseBlockMixin.java) |
| `BoneMealItemMixin` | class | — | Mixin | 2 | 33 | [BoneMealItemMixin.java](com/natamus/collective/fabric/mixin/BoneMealItemMixin.java) |
| `ItemStackMixin` | class | — | Mixin | 2 | 49 | [ItemStackMixin.java](com/natamus/collective/fabric/mixin/ItemStackMixin.java) |
| `AnvilMenuMixin` | class | class_4861 | Mixin | 1 | 60 | [AnvilMenuMixin.java](com/natamus/collective/fabric/mixin/AnvilMenuMixin.java) |

## 事件处理（`@SubscribeEvent`，5 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `RegisterCollectiveForgeEvents` | 8 | `com.natamus.collective.forge.events` | [RegisterCollectiveForgeEvents.java](com/natamus/collective/forge/events/RegisterCollectiveForgeEvents.java) |
| `RegisterCollectiveNeoForgeEvents` | 7 | `com.natamus.collective.neoforge.events` | [RegisterCollectiveNeoForgeEvents.java](com/natamus/collective/neoforge/events/RegisterCollectiveNeoForgeEvents.java) |
| `RegisterCollectiveForgeClientEvents` | 1 | `com.natamus.collective.forge.events` | [RegisterCollectiveForgeClientEvents.java](com/natamus/collective/forge/events/RegisterCollectiveForgeClientEvents.java) |
| `RegisterCollectiveNeoForgeClientEvents` | 1 | `com.natamus.collective.neoforge.events` | [RegisterCollectiveNeoForgeClientEvents.java](com/natamus/collective/neoforge/events/RegisterCollectiveNeoForgeClientEvents.java) |
| `NeoForgeNetworkHandler` | 1 | `com.natamus.collective.neoforge.networking` | [NeoForgeNetworkHandler.java](com/natamus/collective/neoforge/networking/NeoForgeNetworkHandler.java) |

## Mixin（63 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `BlockEntityMixin` | `BlockEntity` | [BlockEntityMixin.java](com/natamus/collective/forge/mixin/BlockEntityMixin.java) |
| `BlockEntityMixin` | `BlockEntity` | [BlockEntityMixin.java](com/natamus/collective/neoforge/mixin/BlockEntityMixin.java) |
| `ItemEntityMixin` | `ItemEntity` | [ItemEntityMixin.java](com/natamus/collective/fabric/mixin/ItemEntityMixin.java) |
| `BlockEntityMixin` | `BlockEntity` | [BlockEntityMixin.java](com/natamus/collective/fabric/mixin/BlockEntityMixin.java) |
| `AbstractFurnaceBlockEntityMixin` | `AbstractFurnaceBlockEntity` | [AbstractFurnaceBlockEntityMixin.java](com/natamus/collective/fabric/mixin/AbstractFurnaceBlockEntityMixin.java) |
| `BlockItemMixin` | `BlockItem` | [BlockItemMixin.java](com/natamus/collective/fabric/mixin/BlockItemMixin.java) |
| `ItemInHandRendererMixin` | `ItemInHandRenderer` | [ItemInHandRendererMixin.java](com/natamus/collective/fabric/mixin/ItemInHandRendererMixin.java) |
| `CommandsMixin` | `Commands` | [CommandsMixin.java](com/natamus/collective/fabric/mixin/CommandsMixin.java) |
| `LivingEntityMixin` | `LivingEntity` | [LivingEntityMixin.java](com/natamus/collective/fabric/mixin/LivingEntityMixin.java) |
| `ServerGamePacketListenerImplMixin` | `ServerGamePacketListenerImpl` | [ServerGamePacketListenerImplMixin.java](com/natamus/collective/fabric/mixin/ServerGamePacketListenerImplMixin.java) |
| `TeleportCommandMixin` | `TeleportCommand` | [TeleportCommandMixin.java](com/natamus/collective/fabric/mixin/TeleportCommandMixin.java) |
| `TitleScreenMixin` | `TitleScreen` | [TitleScreenMixin.java](com/natamus/collective/forge/mixin/TitleScreenMixin.java) |
| `BoneMealItemMixin` | `BoneMealItem` | [BoneMealItemMixin.java](com/natamus/collective/forge/mixin/BoneMealItemMixin.java) |
| `TitleScreenMixin` | `TitleScreen` | [TitleScreenMixin.java](com/natamus/collective/neoforge/mixin/TitleScreenMixin.java) |
| `BoneMealItemMixin` | `BoneMealItem` | [BoneMealItemMixin.java](com/natamus/collective/neoforge/mixin/BoneMealItemMixin.java) |
| `TitleScreenMixin` | `TitleScreen` | [TitleScreenMixin.java](com/natamus/collective/fabric/mixin/TitleScreenMixin.java) |
| `EntityTypeMixin` | `EntityType` | [EntityTypeMixin.java](com/natamus/collective/fabric/mixin/EntityTypeMixin.java) |
| `PistonBaseBlockMixin` | `PistonBaseBlock` | [PistonBaseBlockMixin.java](com/natamus/collective/fabric/mixin/PistonBaseBlockMixin.java) |
| `BoneMealItemMixin` | `BoneMealItem` | [BoneMealItemMixin.java](com/natamus/collective/fabric/mixin/BoneMealItemMixin.java) |
| `ItemStackMixin` | `ItemStack` | [ItemStackMixin.java](com/natamus/collective/fabric/mixin/ItemStackMixin.java) |
| `AnvilMenuMixin` | `AnvilMenu` | [AnvilMenuMixin.java](com/natamus/collective/fabric/mixin/AnvilMenuMixin.java) |
| `BowItemMixin` | `BowItem` | [BowItemMixin.java](com/natamus/collective/fabric/mixin/BowItemMixin.java) |
| `BlockMixin` | `Block` | [BlockMixin.java](com/natamus/collective/fabric/mixin/BlockMixin.java) |
| `ClientLevelMixin` | `ClientLevel` | [ClientLevelMixin.java](com/natamus/collective/fabric/mixin/ClientLevelMixin.java) |
| `CreateWorldScreenMixin` | `CreateWorldScreen` | [CreateWorldScreenMixin.java](com/natamus/collective/fabric/mixin/CreateWorldScreenMixin.java) |
| `CocoaBlockMixin` | `CocoaBlock` | [CocoaBlockMixin.java](com/natamus/collective/fabric/mixin/crop/CocoaBlockMixin.java) |
| `GrowingPlantHeadBlockMixin` | `GrowingPlantHeadBlock` | [GrowingPlantHeadBlockMixin.java](com/natamus/collective/fabric/mixin/crop/GrowingPlantHeadBlockMixin.java) |
| `CactusBlockMixin` | `CactusBlock` | [CactusBlockMixin.java](com/natamus/collective/fabric/mixin/crop/CactusBlockMixin.java) |
| `SweetBerryBushBlockMixin` | `SweetBerryBushBlock` | [SweetBerryBushBlockMixin.java](com/natamus/collective/fabric/mixin/crop/SweetBerryBushBlockMixin.java) |
| `ChorusFlowerBlockMixin` | `ChorusFlowerBlock` | [ChorusFlowerBlockMixin.java](com/natamus/collective/fabric/mixin/crop/ChorusFlowerBlockMixin.java) |
| `SugarCaneBlockMixin` | `SugarCaneBlock` | [SugarCaneBlockMixin.java](com/natamus/collective/fabric/mixin/crop/SugarCaneBlockMixin.java) |
| `NetherWartBlockMixin` | `NetherWartBlock` | [NetherWartBlockMixin.java](com/natamus/collective/fabric/mixin/crop/NetherWartBlockMixin.java) |
| `StemBlockMixin` | `StemBlock` | [StemBlockMixin.java](com/natamus/collective/fabric/mixin/crop/StemBlockMixin.java) |
| `CropBlockMixin` | `CropBlock` | [CropBlockMixin.java](com/natamus/collective/fabric/mixin/crop/CropBlockMixin.java) |
| `BambooBlockMixin` | `BambooBlock` | [BambooBlockMixin.java](com/natamus/collective/fabric/mixin/crop/BambooBlockMixin.java) |
| `PrimaryLevelDataMixin` | `PrimaryLevelData` | [PrimaryLevelDataMixin.java](com/natamus/collective/forge/mixin/PrimaryLevelDataMixin.java) |
| `PrimaryLevelDataMixin` | `PrimaryLevelData` | [PrimaryLevelDataMixin.java](com/natamus/collective/neoforge/mixin/PrimaryLevelDataMixin.java) |
| `PlayerMixin` | `Player` | [PlayerMixin.java](com/natamus/collective/fabric/mixin/PlayerMixin.java) |
| `GuiMixin` | `Gui` | [GuiMixin.java](com/natamus/collective/forge/mixin/GuiMixin.java) |
| `WorldOpenFlowsMixin` | `WorldOpenFlows` | [WorldOpenFlowsMixin.java](com/natamus/collective/forge/mixin/WorldOpenFlowsMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `DuskConfig` | — | [DuskConfig.java](com/natamus/collective_common_fabric/config/DuskConfig.java) |
| `DuskConfig` | — | [DuskConfig.java](com/natamus/collective_common_neoforge/config/DuskConfig.java) |
| `DuskConfig` | — | [DuskConfig.java](com/natamus/collective_common_forge/config/DuskConfig.java) |
| `CollectiveConfigHandler` | — | [CollectiveConfigHandler.java](com/natamus/collective_common_fabric/config/CollectiveConfigHandler.java) |
| `CollectiveConfigHandler` | — | [CollectiveConfigHandler.java](com/natamus/collective_common_neoforge/config/CollectiveConfigHandler.java) |
| `CollectiveConfigHandler` | — | [CollectiveConfigHandler.java](com/natamus/collective_common_forge/config/CollectiveConfigHandler.java) |
| `ForgeMixinConfigPlugin` | — | [ForgeMixinConfigPlugin.java](com/natamus/collective/forge/mixin/plugin/ForgeMixinConfigPlugin.java) |
| `ForgeCollectiveConfigScreen` | — | [ForgeCollectiveConfigScreen.java](com/natamus/collective/forge/config/ForgeCollectiveConfigScreen.java) |
| `NeoForgeMixinConfigPlugin` | — | [NeoForgeMixinConfigPlugin.java](com/natamus/collective/neoforge/mixin/plugin/NeoForgeMixinConfigPlugin.java) |
| `NeoForgeCollectiveConfigScreen` | — | [NeoForgeCollectiveConfigScreen.java](com/natamus/collective/neoforge/config/NeoForgeCollectiveConfigScreen.java) |
| `FabricMixinConfigPlugin` | — | [FabricMixinConfigPlugin.java](com/natamus/collective/fabric/mixin/plugin/FabricMixinConfigPlugin.java) |
| `FabricCollectiveConfigScreen` | — | [FabricCollectiveConfigScreen.java](com/natamus/collective/fabric/config/FabricCollectiveConfigScreen.java) |
| `ConfigFunctions` | — | [ConfigFunctions.java](com/natamus/collective_common_fabric/functions/ConfigFunctions.java) |
| `ConfigFunctions` | — | [ConfigFunctions.java](com/natamus/collective_common_neoforge/functions/ConfigFunctions.java) |
| `ForgeBundleConfigCheck` | — | [ForgeBundleConfigCheck.java](com/natamus/collective/forge/bundle/ForgeBundleConfigCheck.java) |
| `NeoForgeBundleConfigCheck` | — | [NeoForgeBundleConfigCheck.java](com/natamus/collective/neoforge/bundle/NeoForgeBundleConfigCheck.java) |
| `FabricBundleConfigCheck` | — | [FabricBundleConfigCheck.java](com/natamus/collective/fabric/bundle/FabricBundleConfigCheck.java) |
| `ConfigFunctions` | — | [ConfigFunctions.java](com/natamus/collective_common_forge/functions/ConfigFunctions.java) |

## 按包浏览

- `com.natamus.collective`（4 类）
- `com.natamus.collective.fabric.bundle`（2 类）
- `com.natamus.collective.fabric.callbacks`（20 类）
- `com.natamus.collective.fabric.config`（1 类）
- `com.natamus.collective.fabric.data`（1 类）
- `com.natamus.collective.fabric.mixin`（40 类）
- `com.natamus.collective.fabric.mixin.crop`（10 类）
- `com.natamus.collective.fabric.mixin.plugin`（1 类）
- `com.natamus.collective.fabric.networking`（1 类）
- `com.natamus.collective.fabric.services`（7 类）
- `com.natamus.collective.forge.bundle`（2 类）
- `com.natamus.collective.forge.config`（1 类）
- `com.natamus.collective.forge.events`（2 类）
- `com.natamus.collective.forge.mixin`（6 类）
- `com.natamus.collective.forge.mixin.plugin`（1 类）
- `com.natamus.collective.forge.networking`（1 类）
- `com.natamus.collective.forge.services`（7 类）
- `com.natamus.collective.neoforge.bundle`（2 类）
- `com.natamus.collective.neoforge.config`（1 类）
- `com.natamus.collective.neoforge.events`（2 类）
- `com.natamus.collective.neoforge.mixin`（7 类）
- `com.natamus.collective.neoforge.mixin.plugin`（1 类）
- `com.natamus.collective.neoforge.networking`（1 类）
- `com.natamus.collective.neoforge.services`（7 类）
- `com.natamus.collective.util`（1 类）
- `com.natamus.collective_common_fabric`（1 类）
- `com.natamus.collective_common_fabric.check`（2 类）
- `com.natamus.collective_common_fabric.cmds`（1 类）
- `com.natamus.collective_common_fabric.config`（4 类）
- `com.natamus.collective_common_fabric.data`（5 类）
- `com.natamus.collective_common_fabric.events`（2 类）
- `com.natamus.collective_common_fabric.fakeplayer`（2 类）
- `com.natamus.collective_common_fabric.features`（1 类）
- `com.natamus.collective_common_fabric.functions`（38 类）
- `com.natamus.collective_common_fabric.globalcallbacks`（5 类）
- `com.natamus.collective_common_fabric.implementations.event`（6 类）
- `com.natamus.collective_common_fabric.implementations.networking`（4 类）
- `com.natamus.collective_common_fabric.implementations.networking.api`（3 类）
- `com.natamus.collective_common_fabric.implementations.networking.data`（4 类）
- `com.natamus.collective_common_fabric.implementations.networking.exceptions`（1 类）
- `com.natamus.collective_common_fabric.objects`（2 类）
- `com.natamus.collective_common_fabric.schematic`（5 类）
- `com.natamus.collective_common_fabric.services`（1 类）
- `com.natamus.collective_common_fabric.services.helpers`（7 类）
- `com.natamus.collective_common_fabric.util`（1 类）
- `com.natamus.collective_common_forge`（1 类）
- `com.natamus.collective_common_forge.check`（2 类）
- `com.natamus.collective_common_forge.cmds`（1 类）
- `com.natamus.collective_common_forge.config`（4 类）
- `com.natamus.collective_common_forge.data`（5 类）
- `com.natamus.collective_common_forge.events`（2 类）
- `com.natamus.collective_common_forge.fakeplayer`（2 类）
- `com.natamus.collective_common_forge.features`（1 类）
- `com.natamus.collective_common_forge.functions`（38 类）
- `com.natamus.collective_common_forge.globalcallbacks`（5 类）
- `com.natamus.collective_common_forge.implementations.event`（6 类）
- `com.natamus.collective_common_forge.implementations.networking`（4 类）
- `com.natamus.collective_common_forge.implementations.networking.api`（3 类）
- `com.natamus.collective_common_forge.implementations.networking.data`（4 类）
- `com.natamus.collective_common_forge.implementations.networking.exceptions`（1 类）
- `com.natamus.collective_common_forge.objects`（2 类）
- `com.natamus.collective_common_forge.schematic`（5 类）
- `com.natamus.collective_common_forge.services`（1 类）
- `com.natamus.collective_common_forge.services.helpers`（7 类）
- `com.natamus.collective_common_forge.util`（1 类）
- `com.natamus.collective_common_neoforge`（1 类）
- `com.natamus.collective_common_neoforge.check`（2 类）
- `com.natamus.collective_common_neoforge.cmds`（1 类）
- `com.natamus.collective_common_neoforge.config`（4 类）
- `com.natamus.collective_common_neoforge.data`（5 类）
- `com.natamus.collective_common_neoforge.events`（2 类）
- `com.natamus.collective_common_neoforge.fakeplayer`（2 类）
- `com.natamus.collective_common_neoforge.features`（1 类）
- `com.natamus.collective_common_neoforge.functions`（38 类）
- `com.natamus.collective_common_neoforge.globalcallbacks`（5 类）
- `com.natamus.collective_common_neoforge.implementations.event`（6 类）
- `com.natamus.collective_common_neoforge.implementations.networking`（4 类）
- `com.natamus.collective_common_neoforge.implementations.networking.api`（3 类）
- `com.natamus.collective_common_neoforge.implementations.networking.data`（4 类）
- `com.natamus.collective_common_neoforge.implementations.networking.exceptions`（1 类）
- `com.natamus.collective_common_neoforge.objects`（2 类）
- `com.natamus.collective_common_neoforge.schematic`（5 类）
- `com.natamus.collective_common_neoforge.services`（1 类）
- `com.natamus.collective_common_neoforge.services.helpers`（7 类）
- `com.natamus.collective_common_neoforge.util`（1 类）
