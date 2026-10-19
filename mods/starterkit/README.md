# Starter Kit（`starterkit`）

> jar：`[初始套件]starterkit.jar` ｜ 版本：`7.4` ｜ 作者：Rick South
> 规模：**93 个类 / 37 个包 / 10284 行** ｜ 事件处理类 4 ｜ Mixin 0 ｜ 注册/管理类 16

**依赖**：`collective`、`minecraft`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.natamus.starterkit_common_neoforge.functions` | 6 | `StarterGearFunctions`、`StarterClientFunctions`、`StarterCommandFunctions` |
| `com.natamus.starterkit_common_fabric.functions` | 6 | `StarterGearFunctions`、`StarterClientFunctions`、`StarterCommandFunctions` |
| `com.natamus.starterkit_common_forge.functions` | 6 | `StarterGearFunctions`、`StarterClientFunctions`、`StarterCommandFunctions` |
| `com.natamus.starterkit_common_neoforge.networking.packets` | 5 | `ToClientReceiveKitDataPacket`、`ToClientSelectFirstSlotPacket`、`ToClientAskIfModIsInstalledPacket` |
| `com.natamus.starterkit_common_fabric.networking.packets` | 5 | `ToClientReceiveKitDataPacket`、`ToClientSelectFirstSlotPacket`、`ToClientAskIfModIsInstalledPacket` |
| `com.natamus.starterkit_common_forge.networking.packets` | 5 | `ToClientReceiveKitDataPacket`、`ToClientSelectFirstSlotPacket`、`ToClientAskIfModIsInstalledPacket` |
| `com.natamus.starterkit_common_neoforge.inventory` | 4 | `StarterKitAbstractContainerScreen`、`StarterKitInventoryMenu`、`StarterKitInventoryScreen` |
| `com.natamus.starterkit_common_fabric.inventory` | 4 | `StarterKitAbstractContainerScreen`、`StarterKitInventoryMenu`、`StarterKitInventoryScreen` |
| `com.natamus.starterkit_common_forge.inventory` | 4 | `StarterKitAbstractContainerScreen`、`StarterKitInventoryMenu`、`StarterKitInventoryScreen` |
| `com.natamus.starterkit` | 4 | `ModForge`、`ModNeoForge`、`ModFabricClient` |
| `com.natamus.starterkit_common_neoforge.data` | 4 | `VariablesClient`、`ConstantsClient`、`Variables` |
| `com.natamus.starterkit_common_fabric.data` | 4 | `VariablesClient`、`ConstantsClient`、`Variables` |
| `com.natamus.starterkit_common_forge.data` | 4 | `VariablesClient`、`ConstantsClient`、`Variables` |
| `com.natamus.starterkit.forge.events` | 2 | `ForgeStarterClientEvents`、`ForgeStarterServerEvents` |
| `com.natamus.starterkit.neoforge.events` | 2 | `NeoForgeStarterClientEvents`、`NeoForgeStarterServerEvents` |
| `com.natamus.starterkit_common_neoforge.events` | 2 | `StarterClientEvents`、`StarterServerEvents` |
| `com.natamus.starterkit_common_fabric.events` | 2 | `StarterClientEvents`、`StarterServerEvents` |
| `com.natamus.starterkit_common_forge.events` | 2 | `StarterClientEvents`、`StarterServerEvents` |
| `com.natamus.starterkit_common_neoforge.util` | 2 | `Util`、`Reference` |
| `com.natamus.starterkit_common_fabric.util` | 2 | `Util`、`Reference` |
| `com.natamus.starterkit_common_forge.util` | 2 | `Util`、`Reference` |
| `com.natamus.starterkit_common_neoforge.networking` | 1 | `PacketRegistration` |
| `com.natamus.starterkit_common_neoforge.config` | 1 | `ConfigHandler` |
| `com.natamus.starterkit_common_fabric.networking` | 1 | `PacketRegistration` |
| `com.natamus.starterkit_common_fabric.config` | 1 | `ConfigHandler` |
| `com.natamus.starterkit_common_forge.networking` | 1 | `PacketRegistration` |
| `com.natamus.starterkit_common_forge.config` | 1 | `ConfigHandler` |
| `com.natamus.starterkit_common_neoforge.cmds` | 1 | `CommandStarterkit` |
| `com.natamus.starterkit_common_fabric.cmds` | 1 | `CommandStarterkit` |
| `com.natamus.starterkit_common_forge.cmds` | 1 | `CommandStarterkit` |
| `com.natamus.starterkit.forge.config` | 1 | `IntegrateForgeConfig` |
| `com.natamus.starterkit.neoforge.config` | 1 | `IntegrateNeoForgeConfig` |
| `com.natamus.starterkit.fabric.config` | 1 | `IntegrateModMenu` |
| `com.natamus.starterkit_common_neoforge` | 1 | `ModCommon` |
| `com.natamus.starterkit_common_fabric` | 1 | `ModCommon` |
| `com.natamus.starterkit.util` | 1 | `Placeholder` |
| `com.natamus.starterkit_common_forge` | 1 | `ModCommon` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ForgeStarterClientEvents` | class | — | — | 1 | 16 | [ForgeStarterClientEvents.java](com/natamus/starterkit/forge/events/ForgeStarterClientEvents.java) |
| `NeoForgeStarterClientEvents` | class | — | — | 1 | 13 | [NeoForgeStarterClientEvents.java](com/natamus/starterkit/neoforge/events/NeoForgeStarterClientEvents.java) |
| `ForgeStarterServerEvents` | class | — | — | 4 | 33 | [ForgeStarterServerEvents.java](com/natamus/starterkit/forge/events/ForgeStarterServerEvents.java) |
| `NeoForgeStarterServerEvents` | class | — | — | 4 | 33 | [NeoForgeStarterServerEvents.java](com/natamus/starterkit/neoforge/events/NeoForgeStarterServerEvents.java) |
| `StarterKitAbstractContainerScreen` | class | Screen | — | 30 | 641 | [StarterKitAbstractContainerScreen.java](com/natamus/starterkit_common_neoforge/inventory/StarterKitAbstractContainerScreen.java) |
| `StarterKitAbstractContainerScreen` | class | class_437 | — | 30 | 639 | [StarterKitAbstractContainerScreen.java](com/natamus/starterkit_common_fabric/inventory/StarterKitAbstractContainerScreen.java) |
| `StarterKitAbstractContainerScreen` | class | Screen | — | 30 | 641 | [StarterKitAbstractContainerScreen.java](com/natamus/starterkit_common_forge/inventory/StarterKitAbstractContainerScreen.java) |
| `StarterClientEvents` | class | — | — | 1 | 36 | [StarterClientEvents.java](com/natamus/starterkit_common_neoforge/events/StarterClientEvents.java) |
| `StarterClientEvents` | class | — | — | 1 | 36 | [StarterClientEvents.java](com/natamus/starterkit_common_fabric/events/StarterClientEvents.java) |
| `ModForge` | class | — | Mod | 2 | 42 | [ModForge.java](com/natamus/starterkit/ModForge.java) |
| `ModNeoForge` | class | — | Mod | 2 | 40 | [ModNeoForge.java](com/natamus/starterkit/ModNeoForge.java) |
| `StarterClientEvents` | class | — | — | 1 | 36 | [StarterClientEvents.java](com/natamus/starterkit_common_forge/events/StarterClientEvents.java) |
| `PacketRegistration` | class | — | — | 3 | 57 | [PacketRegistration.java](com/natamus/starterkit_common_neoforge/networking/PacketRegistration.java) |
| `ConfigHandler` | class | DuskConfig | — | 1 | 65 | [ConfigHandler.java](com/natamus/starterkit_common_neoforge/config/ConfigHandler.java) |
| `PacketRegistration` | class | — | — | 3 | 57 | [PacketRegistration.java](com/natamus/starterkit_common_fabric/networking/PacketRegistration.java) |
| `ConfigHandler` | class | DuskConfig | — | 1 | 65 | [ConfigHandler.java](com/natamus/starterkit_common_fabric/config/ConfigHandler.java) |
| `PacketRegistration` | class | — | — | 3 | 57 | [PacketRegistration.java](com/natamus/starterkit_common_forge/networking/PacketRegistration.java) |
| `ConfigHandler` | class | DuskConfig | — | 1 | 65 | [ConfigHandler.java](com/natamus/starterkit_common_forge/config/ConfigHandler.java) |
| `StarterServerEvents` | class | — | — | 3 | 49 | [StarterServerEvents.java](com/natamus/starterkit_common_neoforge/events/StarterServerEvents.java) |
| `StarterServerEvents` | class | — | — | 3 | 49 | [StarterServerEvents.java](com/natamus/starterkit_common_fabric/events/StarterServerEvents.java) |
| `StarterServerEvents` | class | — | — | 3 | 49 | [StarterServerEvents.java](com/natamus/starterkit_common_forge/events/StarterServerEvents.java) |
| `StarterKitInventoryMenu` | class | RecipeBookMenu | — | 23 | 166 | [StarterKitInventoryMenu.java](com/natamus/starterkit_common_neoforge/inventory/StarterKitInventoryMenu.java) |
| `ToClientReceiveKitDataPacket` | class | — | — | 3 | 45 | [ToClientReceiveKitDataPacket.java](com/natamus/starterkit_common_neoforge/networking/packets/ToClientReceiveKitDataPacket.java) |
| `CommandStarterkit` | class | — | — | 5 | 536 | [CommandStarterkit.java](com/natamus/starterkit_common_neoforge/cmds/CommandStarterkit.java) |
| `StarterGearFunctions` | class | — | — | 21 | 410 | [StarterGearFunctions.java](com/natamus/starterkit_common_neoforge/functions/StarterGearFunctions.java) |
| `StarterKitInventoryMenu` | class | class_1729 | — | 23 | 166 | [StarterKitInventoryMenu.java](com/natamus/starterkit_common_fabric/inventory/StarterKitInventoryMenu.java) |
| `ToClientReceiveKitDataPacket` | class | — | — | 3 | 45 | [ToClientReceiveKitDataPacket.java](com/natamus/starterkit_common_fabric/networking/packets/ToClientReceiveKitDataPacket.java) |
| `CommandStarterkit` | class | — | — | 5 | 532 | [CommandStarterkit.java](com/natamus/starterkit_common_fabric/cmds/CommandStarterkit.java) |
| `StarterGearFunctions` | class | — | — | 21 | 410 | [StarterGearFunctions.java](com/natamus/starterkit_common_fabric/functions/StarterGearFunctions.java) |
| `StarterKitInventoryMenu` | class | RecipeBookMenu | — | 23 | 166 | [StarterKitInventoryMenu.java](com/natamus/starterkit_common_forge/inventory/StarterKitInventoryMenu.java) |
| `ToClientReceiveKitDataPacket` | class | — | — | 3 | 45 | [ToClientReceiveKitDataPacket.java](com/natamus/starterkit_common_forge/networking/packets/ToClientReceiveKitDataPacket.java) |
| `CommandStarterkit` | class | — | — | 5 | 536 | [CommandStarterkit.java](com/natamus/starterkit_common_forge/cmds/CommandStarterkit.java) |
| `StarterGearFunctions` | class | — | — | 21 | 410 | [StarterGearFunctions.java](com/natamus/starterkit_common_forge/functions/StarterGearFunctions.java) |
| `StarterKitInventoryScreen` | class | StarterKitEffectRenderingInventorySc | — | 13 | 248 | [StarterKitInventoryScreen.java](com/natamus/starterkit_common_neoforge/inventory/StarterKitInventoryScreen.java) |
| `ToClientSelectFirstSlotPacket` | class | — | — | 3 | 25 | [ToClientSelectFirstSlotPacket.java](com/natamus/starterkit_common_neoforge/networking/packets/ToClientSelectFirstSlotPacket.java) |
| `ToClientAskIfModIsInstalledPacket` | class | — | — | 3 | 25 | [ToClientAskIfModIsInstalledPacket.java](com/natamus/starterkit_common_neoforge/networking/packets/ToClientAskIfModIsInstalledPacket.java) |
| `StarterKitInventoryScreen` | class | StarterKitEffectRenderingInventorySc | — | 13 | 254 | [StarterKitInventoryScreen.java](com/natamus/starterkit_common_fabric/inventory/StarterKitInventoryScreen.java) |
| `ToClientSelectFirstSlotPacket` | class | — | — | 3 | 25 | [ToClientSelectFirstSlotPacket.java](com/natamus/starterkit_common_fabric/networking/packets/ToClientSelectFirstSlotPacket.java) |
| `ToClientAskIfModIsInstalledPacket` | class | — | — | 3 | 25 | [ToClientAskIfModIsInstalledPacket.java](com/natamus/starterkit_common_fabric/networking/packets/ToClientAskIfModIsInstalledPacket.java) |
| `StarterKitInventoryScreen` | class | StarterKitEffectRenderingInventorySc | — | 13 | 248 | [StarterKitInventoryScreen.java](com/natamus/starterkit_common_forge/inventory/StarterKitInventoryScreen.java) |
| `ToClientSelectFirstSlotPacket` | class | — | — | 3 | 25 | [ToClientSelectFirstSlotPacket.java](com/natamus/starterkit_common_forge/networking/packets/ToClientSelectFirstSlotPacket.java) |
| `ToClientAskIfModIsInstalledPacket` | class | — | — | 3 | 25 | [ToClientAskIfModIsInstalledPacket.java](com/natamus/starterkit_common_forge/networking/packets/ToClientAskIfModIsInstalledPacket.java) |
| `StarterClientFunctions` | class | — | — | 13 | 182 | [StarterClientFunctions.java](com/natamus/starterkit_common_neoforge/functions/StarterClientFunctions.java) |
| `StarterClientFunctions` | class | — | — | 13 | 182 | [StarterClientFunctions.java](com/natamus/starterkit_common_fabric/functions/StarterClientFunctions.java) |
| `IntegrateForgeConfig` | class | — | — | 1 | 14 | [IntegrateForgeConfig.java](com/natamus/starterkit/forge/config/IntegrateForgeConfig.java) |
| `IntegrateNeoForgeConfig` | class | — | — | 2 | 20 | [IntegrateNeoForgeConfig.java](com/natamus/starterkit/neoforge/config/IntegrateNeoForgeConfig.java) |
| `StarterClientFunctions` | class | — | — | 13 | 182 | [StarterClientFunctions.java](com/natamus/starterkit_common_forge/functions/StarterClientFunctions.java) |
| `StarterKitEffectRenderingInventoryScreen` | class | StarterKitAbstractContainerScreen | — | 7 | 129 | [StarterKitEffectRenderingInventoryScreen.java](com/natamus/starterkit_common_neoforge/inventory/StarterKitEffectRenderingInventoryScreen.java) |
| `ToServerAnnounceModIsInstalledPacket` | class | — | — | 3 | 35 | [ToServerAnnounceModIsInstalledPacket.java](com/natamus/starterkit_common_neoforge/networking/packets/ToServerAnnounceModIsInstalledPacket.java) |
| `ToServerSendKitChoicePacket` | class | — | — | 3 | 59 | [ToServerSendKitChoicePacket.java](com/natamus/starterkit_common_neoforge/networking/packets/ToServerSendKitChoicePacket.java) |
| `StarterCommandFunctions` | class | — | — | 0 | 34 | [StarterCommandFunctions.java](com/natamus/starterkit_common_neoforge/functions/StarterCommandFunctions.java) |
| `StarterDataFunctions` | class | — | — | 10 | 194 | [StarterDataFunctions.java](com/natamus/starterkit_common_neoforge/functions/StarterDataFunctions.java) |
| `StarterKitEffectRenderingInventoryScreen` | class | StarterKitAbstractContainerScreen | — | 7 | 129 | [StarterKitEffectRenderingInventoryScreen.java](com/natamus/starterkit_common_fabric/inventory/StarterKitEffectRenderingInventoryScreen.java) |
| `ToServerAnnounceModIsInstalledPacket` | class | — | — | 3 | 35 | [ToServerAnnounceModIsInstalledPacket.java](com/natamus/starterkit_common_fabric/networking/packets/ToServerAnnounceModIsInstalledPacket.java) |
| `ToServerSendKitChoicePacket` | class | — | — | 3 | 59 | [ToServerSendKitChoicePacket.java](com/natamus/starterkit_common_fabric/networking/packets/ToServerSendKitChoicePacket.java) |
| `StarterCommandFunctions` | class | — | — | 0 | 34 | [StarterCommandFunctions.java](com/natamus/starterkit_common_fabric/functions/StarterCommandFunctions.java) |
| `StarterDataFunctions` | class | — | — | 10 | 194 | [StarterDataFunctions.java](com/natamus/starterkit_common_fabric/functions/StarterDataFunctions.java) |
| `StarterKitEffectRenderingInventoryScreen` | class | StarterKitAbstractContainerScreen | — | 7 | 129 | [StarterKitEffectRenderingInventoryScreen.java](com/natamus/starterkit_common_forge/inventory/StarterKitEffectRenderingInventoryScreen.java) |
| `ToServerAnnounceModIsInstalledPacket` | class | — | — | 3 | 35 | [ToServerAnnounceModIsInstalledPacket.java](com/natamus/starterkit_common_forge/networking/packets/ToServerAnnounceModIsInstalledPacket.java) |
| `ToServerSendKitChoicePacket` | class | — | — | 3 | 59 | [ToServerSendKitChoicePacket.java](com/natamus/starterkit_common_forge/networking/packets/ToServerSendKitChoicePacket.java) |

## 事件处理（`@SubscribeEvent`，4 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `ForgeStarterServerEvents` | 4 | `com.natamus.starterkit.forge.events` | [ForgeStarterServerEvents.java](com/natamus/starterkit/forge/events/ForgeStarterServerEvents.java) |
| `NeoForgeStarterServerEvents` | 4 | `com.natamus.starterkit.neoforge.events` | [NeoForgeStarterServerEvents.java](com/natamus/starterkit/neoforge/events/NeoForgeStarterServerEvents.java) |
| `ForgeStarterClientEvents` | 1 | `com.natamus.starterkit.forge.events` | [ForgeStarterClientEvents.java](com/natamus/starterkit/forge/events/ForgeStarterClientEvents.java) |
| `NeoForgeStarterClientEvents` | 1 | `com.natamus.starterkit.neoforge.events` | [NeoForgeStarterClientEvents.java](com/natamus/starterkit/neoforge/events/NeoForgeStarterClientEvents.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `ConfigHandler` | — | [ConfigHandler.java](com/natamus/starterkit_common_neoforge/config/ConfigHandler.java) |
| `ConfigHandler` | — | [ConfigHandler.java](com/natamus/starterkit_common_fabric/config/ConfigHandler.java) |
| `ConfigHandler` | — | [ConfigHandler.java](com/natamus/starterkit_common_forge/config/ConfigHandler.java) |
| `IntegrateForgeConfig` | — | [IntegrateForgeConfig.java](com/natamus/starterkit/forge/config/IntegrateForgeConfig.java) |
| `IntegrateNeoForgeConfig` | — | [IntegrateNeoForgeConfig.java](com/natamus/starterkit/neoforge/config/IntegrateNeoForgeConfig.java) |

## 全部类（93）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ForgeStarterClientEvents` | class | — | — | 1 | 16 | [ForgeStarterClientEvents.java](com/natamus/starterkit/forge/events/ForgeStarterClientEvents.java) |
| `NeoForgeStarterClientEvents` | class | — | — | 1 | 13 | [NeoForgeStarterClientEvents.java](com/natamus/starterkit/neoforge/events/NeoForgeStarterClientEvents.java) |
| `ForgeStarterServerEvents` | class | — | — | 4 | 33 | [ForgeStarterServerEvents.java](com/natamus/starterkit/forge/events/ForgeStarterServerEvents.java) |
| `NeoForgeStarterServerEvents` | class | — | — | 4 | 33 | [NeoForgeStarterServerEvents.java](com/natamus/starterkit/neoforge/events/NeoForgeStarterServerEvents.java) |
| `StarterKitAbstractContainerScreen` | class | Screen | — | 30 | 641 | [StarterKitAbstractContainerScreen.java](com/natamus/starterkit_common_neoforge/inventory/StarterKitAbstractContainerScreen.java) |
| `StarterKitAbstractContainerScreen` | class | class_437 | — | 30 | 639 | [StarterKitAbstractContainerScreen.java](com/natamus/starterkit_common_fabric/inventory/StarterKitAbstractContainerScreen.java) |
| `StarterKitAbstractContainerScreen` | class | Screen | — | 30 | 641 | [StarterKitAbstractContainerScreen.java](com/natamus/starterkit_common_forge/inventory/StarterKitAbstractContainerScreen.java) |
| `StarterClientEvents` | class | — | — | 1 | 36 | [StarterClientEvents.java](com/natamus/starterkit_common_neoforge/events/StarterClientEvents.java) |
| `StarterClientEvents` | class | — | — | 1 | 36 | [StarterClientEvents.java](com/natamus/starterkit_common_fabric/events/StarterClientEvents.java) |
| `ModForge` | class | — | Mod | 2 | 42 | [ModForge.java](com/natamus/starterkit/ModForge.java) |
| `ModNeoForge` | class | — | Mod | 2 | 40 | [ModNeoForge.java](com/natamus/starterkit/ModNeoForge.java) |
| `StarterClientEvents` | class | — | — | 1 | 36 | [StarterClientEvents.java](com/natamus/starterkit_common_forge/events/StarterClientEvents.java) |
| `PacketRegistration` | class | — | — | 3 | 57 | [PacketRegistration.java](com/natamus/starterkit_common_neoforge/networking/PacketRegistration.java) |
| `ConfigHandler` | class | DuskConfig | — | 1 | 65 | [ConfigHandler.java](com/natamus/starterkit_common_neoforge/config/ConfigHandler.java) |
| `PacketRegistration` | class | — | — | 3 | 57 | [PacketRegistration.java](com/natamus/starterkit_common_fabric/networking/PacketRegistration.java) |
| `ConfigHandler` | class | DuskConfig | — | 1 | 65 | [ConfigHandler.java](com/natamus/starterkit_common_fabric/config/ConfigHandler.java) |
| `PacketRegistration` | class | — | — | 3 | 57 | [PacketRegistration.java](com/natamus/starterkit_common_forge/networking/PacketRegistration.java) |
| `ConfigHandler` | class | DuskConfig | — | 1 | 65 | [ConfigHandler.java](com/natamus/starterkit_common_forge/config/ConfigHandler.java) |
| `StarterServerEvents` | class | — | — | 3 | 49 | [StarterServerEvents.java](com/natamus/starterkit_common_neoforge/events/StarterServerEvents.java) |
| `StarterServerEvents` | class | — | — | 3 | 49 | [StarterServerEvents.java](com/natamus/starterkit_common_fabric/events/StarterServerEvents.java) |
| `StarterServerEvents` | class | — | — | 3 | 49 | [StarterServerEvents.java](com/natamus/starterkit_common_forge/events/StarterServerEvents.java) |
| `StarterKitInventoryMenu` | class | RecipeBookMenu | — | 23 | 166 | [StarterKitInventoryMenu.java](com/natamus/starterkit_common_neoforge/inventory/StarterKitInventoryMenu.java) |
| `ToClientReceiveKitDataPacket` | class | — | — | 3 | 45 | [ToClientReceiveKitDataPacket.java](com/natamus/starterkit_common_neoforge/networking/packets/ToClientReceiveKitDataPacket.java) |
| `CommandStarterkit` | class | — | — | 5 | 536 | [CommandStarterkit.java](com/natamus/starterkit_common_neoforge/cmds/CommandStarterkit.java) |
| `StarterGearFunctions` | class | — | — | 21 | 410 | [StarterGearFunctions.java](com/natamus/starterkit_common_neoforge/functions/StarterGearFunctions.java) |
| `StarterKitInventoryMenu` | class | class_1729 | — | 23 | 166 | [StarterKitInventoryMenu.java](com/natamus/starterkit_common_fabric/inventory/StarterKitInventoryMenu.java) |
| `ToClientReceiveKitDataPacket` | class | — | — | 3 | 45 | [ToClientReceiveKitDataPacket.java](com/natamus/starterkit_common_fabric/networking/packets/ToClientReceiveKitDataPacket.java) |
| `CommandStarterkit` | class | — | — | 5 | 532 | [CommandStarterkit.java](com/natamus/starterkit_common_fabric/cmds/CommandStarterkit.java) |
| `StarterGearFunctions` | class | — | — | 21 | 410 | [StarterGearFunctions.java](com/natamus/starterkit_common_fabric/functions/StarterGearFunctions.java) |
| `StarterKitInventoryMenu` | class | RecipeBookMenu | — | 23 | 166 | [StarterKitInventoryMenu.java](com/natamus/starterkit_common_forge/inventory/StarterKitInventoryMenu.java) |
| `ToClientReceiveKitDataPacket` | class | — | — | 3 | 45 | [ToClientReceiveKitDataPacket.java](com/natamus/starterkit_common_forge/networking/packets/ToClientReceiveKitDataPacket.java) |
| `CommandStarterkit` | class | — | — | 5 | 536 | [CommandStarterkit.java](com/natamus/starterkit_common_forge/cmds/CommandStarterkit.java) |
| `StarterGearFunctions` | class | — | — | 21 | 410 | [StarterGearFunctions.java](com/natamus/starterkit_common_forge/functions/StarterGearFunctions.java) |
| `StarterKitInventoryScreen` | class | StarterKitEffectRenderingInventorySc | — | 13 | 248 | [StarterKitInventoryScreen.java](com/natamus/starterkit_common_neoforge/inventory/StarterKitInventoryScreen.java) |
| `ToClientSelectFirstSlotPacket` | class | — | — | 3 | 25 | [ToClientSelectFirstSlotPacket.java](com/natamus/starterkit_common_neoforge/networking/packets/ToClientSelectFirstSlotPacket.java) |
| `ToClientAskIfModIsInstalledPacket` | class | — | — | 3 | 25 | [ToClientAskIfModIsInstalledPacket.java](com/natamus/starterkit_common_neoforge/networking/packets/ToClientAskIfModIsInstalledPacket.java) |
| `StarterKitInventoryScreen` | class | StarterKitEffectRenderingInventorySc | — | 13 | 254 | [StarterKitInventoryScreen.java](com/natamus/starterkit_common_fabric/inventory/StarterKitInventoryScreen.java) |
| `ToClientSelectFirstSlotPacket` | class | — | — | 3 | 25 | [ToClientSelectFirstSlotPacket.java](com/natamus/starterkit_common_fabric/networking/packets/ToClientSelectFirstSlotPacket.java) |
| `ToClientAskIfModIsInstalledPacket` | class | — | — | 3 | 25 | [ToClientAskIfModIsInstalledPacket.java](com/natamus/starterkit_common_fabric/networking/packets/ToClientAskIfModIsInstalledPacket.java) |
| `StarterKitInventoryScreen` | class | StarterKitEffectRenderingInventorySc | — | 13 | 248 | [StarterKitInventoryScreen.java](com/natamus/starterkit_common_forge/inventory/StarterKitInventoryScreen.java) |
| `ToClientSelectFirstSlotPacket` | class | — | — | 3 | 25 | [ToClientSelectFirstSlotPacket.java](com/natamus/starterkit_common_forge/networking/packets/ToClientSelectFirstSlotPacket.java) |
| `ToClientAskIfModIsInstalledPacket` | class | — | — | 3 | 25 | [ToClientAskIfModIsInstalledPacket.java](com/natamus/starterkit_common_forge/networking/packets/ToClientAskIfModIsInstalledPacket.java) |
| `StarterClientFunctions` | class | — | — | 13 | 182 | [StarterClientFunctions.java](com/natamus/starterkit_common_neoforge/functions/StarterClientFunctions.java) |
| `StarterClientFunctions` | class | — | — | 13 | 182 | [StarterClientFunctions.java](com/natamus/starterkit_common_fabric/functions/StarterClientFunctions.java) |
| `IntegrateForgeConfig` | class | — | — | 1 | 14 | [IntegrateForgeConfig.java](com/natamus/starterkit/forge/config/IntegrateForgeConfig.java) |
| `IntegrateNeoForgeConfig` | class | — | — | 2 | 20 | [IntegrateNeoForgeConfig.java](com/natamus/starterkit/neoforge/config/IntegrateNeoForgeConfig.java) |
| `StarterClientFunctions` | class | — | — | 13 | 182 | [StarterClientFunctions.java](com/natamus/starterkit_common_forge/functions/StarterClientFunctions.java) |
| `StarterKitEffectRenderingInventoryScreen` | class | StarterKitAbstractContainerScreen | — | 7 | 129 | [StarterKitEffectRenderingInventoryScreen.java](com/natamus/starterkit_common_neoforge/inventory/StarterKitEffectRenderingInventoryScreen.java) |
| `ToServerAnnounceModIsInstalledPacket` | class | — | — | 3 | 35 | [ToServerAnnounceModIsInstalledPacket.java](com/natamus/starterkit_common_neoforge/networking/packets/ToServerAnnounceModIsInstalledPacket.java) |
| `ToServerSendKitChoicePacket` | class | — | — | 3 | 59 | [ToServerSendKitChoicePacket.java](com/natamus/starterkit_common_neoforge/networking/packets/ToServerSendKitChoicePacket.java) |
| `StarterCommandFunctions` | class | — | — | 0 | 34 | [StarterCommandFunctions.java](com/natamus/starterkit_common_neoforge/functions/StarterCommandFunctions.java) |
| `StarterDataFunctions` | class | — | — | 10 | 194 | [StarterDataFunctions.java](com/natamus/starterkit_common_neoforge/functions/StarterDataFunctions.java) |
| `StarterKitEffectRenderingInventoryScreen` | class | StarterKitAbstractContainerScreen | — | 7 | 129 | [StarterKitEffectRenderingInventoryScreen.java](com/natamus/starterkit_common_fabric/inventory/StarterKitEffectRenderingInventoryScreen.java) |
| `ToServerAnnounceModIsInstalledPacket` | class | — | — | 3 | 35 | [ToServerAnnounceModIsInstalledPacket.java](com/natamus/starterkit_common_fabric/networking/packets/ToServerAnnounceModIsInstalledPacket.java) |
| `ToServerSendKitChoicePacket` | class | — | — | 3 | 59 | [ToServerSendKitChoicePacket.java](com/natamus/starterkit_common_fabric/networking/packets/ToServerSendKitChoicePacket.java) |
| `StarterCommandFunctions` | class | — | — | 0 | 34 | [StarterCommandFunctions.java](com/natamus/starterkit_common_fabric/functions/StarterCommandFunctions.java) |
| `StarterDataFunctions` | class | — | — | 10 | 194 | [StarterDataFunctions.java](com/natamus/starterkit_common_fabric/functions/StarterDataFunctions.java) |
| `StarterKitEffectRenderingInventoryScreen` | class | StarterKitAbstractContainerScreen | — | 7 | 129 | [StarterKitEffectRenderingInventoryScreen.java](com/natamus/starterkit_common_forge/inventory/StarterKitEffectRenderingInventoryScreen.java) |
| `ToServerAnnounceModIsInstalledPacket` | class | — | — | 3 | 35 | [ToServerAnnounceModIsInstalledPacket.java](com/natamus/starterkit_common_forge/networking/packets/ToServerAnnounceModIsInstalledPacket.java) |
| `ToServerSendKitChoicePacket` | class | — | — | 3 | 59 | [ToServerSendKitChoicePacket.java](com/natamus/starterkit_common_forge/networking/packets/ToServerSendKitChoicePacket.java) |
| `StarterCommandFunctions` | class | — | — | 0 | 34 | [StarterCommandFunctions.java](com/natamus/starterkit_common_forge/functions/StarterCommandFunctions.java) |
| `StarterDataFunctions` | class | — | — | 10 | 194 | [StarterDataFunctions.java](com/natamus/starterkit_common_forge/functions/StarterDataFunctions.java) |
| `VariablesClient` | class | — | — | 0 | 22 | [VariablesClient.java](com/natamus/starterkit_common_neoforge/data/VariablesClient.java) |
| `ConstantsClient` | class | — | — | 0 | 8 | [ConstantsClient.java](com/natamus/starterkit_common_neoforge/data/ConstantsClient.java) |
| `Util` | class | — | — | 7 | 78 | [Util.java](com/natamus/starterkit_common_neoforge/util/Util.java) |
| `VariablesClient` | class | — | — | 0 | 22 | [VariablesClient.java](com/natamus/starterkit_common_fabric/data/VariablesClient.java) |
| `ConstantsClient` | class | — | — | 0 | 8 | [ConstantsClient.java](com/natamus/starterkit_common_fabric/data/ConstantsClient.java) |
| `Util` | class | — | — | 7 | 78 | [Util.java](com/natamus/starterkit_common_fabric/util/Util.java) |
| `ModFabricClient` | class | ClientModInitializer | — | 2 | 22 | [ModFabricClient.java](com/natamus/starterkit/ModFabricClient.java) |
| `IntegrateModMenu` | class | ModMenuApi | — | 1 | 12 | [IntegrateModMenu.java](com/natamus/starterkit/fabric/config/IntegrateModMenu.java) |
| `VariablesClient` | class | — | — | 0 | 22 | [VariablesClient.java](com/natamus/starterkit_common_forge/data/VariablesClient.java) |
| `ConstantsClient` | class | — | — | 0 | 8 | [ConstantsClient.java](com/natamus/starterkit_common_forge/data/ConstantsClient.java) |
| `Util` | class | — | — | 7 | 78 | [Util.java](com/natamus/starterkit_common_forge/util/Util.java) |
| `StarterDefaultKitFunctions` | class | — | — | 6 | 162 | [StarterDefaultKitFunctions.java](com/natamus/starterkit_common_neoforge/functions/StarterDefaultKitFunctions.java) |
| `StarterDefaultKitFunctions` | class | — | — | 6 | 162 | [StarterDefaultKitFunctions.java](com/natamus/starterkit_common_fabric/functions/StarterDefaultKitFunctions.java) |
| `StarterDefaultKitFunctions` | class | — | — | 6 | 162 | [StarterDefaultKitFunctions.java](com/natamus/starterkit_common_forge/functions/StarterDefaultKitFunctions.java) |
| `ModCommon` | class | — | — | 3 | 24 | [ModCommon.java](com/natamus/starterkit_common_neoforge/ModCommon.java) |
| `Variables` | class | — | — | 0 | 14 | [Variables.java](com/natamus/starterkit_common_neoforge/data/Variables.java) |
| `Constants` | class | — | — | 0 | 26 | [Constants.java](com/natamus/starterkit_common_neoforge/data/Constants.java) |
| `StarterCheckFunctions` | class | — | — | 4 | 53 | [StarterCheckFunctions.java](com/natamus/starterkit_common_neoforge/functions/StarterCheckFunctions.java) |
| `Reference` | class | — | — | 0 | 9 | [Reference.java](com/natamus/starterkit_common_neoforge/util/Reference.java) |
| `ModCommon` | class | — | — | 3 | 24 | [ModCommon.java](com/natamus/starterkit_common_fabric/ModCommon.java) |
| `Variables` | class | — | — | 0 | 14 | [Variables.java](com/natamus/starterkit_common_fabric/data/Variables.java) |
| `Constants` | class | — | — | 0 | 26 | [Constants.java](com/natamus/starterkit_common_fabric/data/Constants.java) |
| `StarterCheckFunctions` | class | — | — | 4 | 53 | [StarterCheckFunctions.java](com/natamus/starterkit_common_fabric/functions/StarterCheckFunctions.java) |
| `Reference` | class | — | — | 0 | 9 | [Reference.java](com/natamus/starterkit_common_fabric/util/Reference.java) |
| `ModFabric` | class | ModInitializer | — | 3 | 38 | [ModFabric.java](com/natamus/starterkit/ModFabric.java) |
| `Placeholder` | class | — | — | 0 | 5 | [Placeholder.java](com/natamus/starterkit/util/Placeholder.java) |
| `ModCommon` | class | — | — | 3 | 24 | [ModCommon.java](com/natamus/starterkit_common_forge/ModCommon.java) |
| `Variables` | class | — | — | 0 | 14 | [Variables.java](com/natamus/starterkit_common_forge/data/Variables.java) |
| `Constants` | class | — | — | 0 | 26 | [Constants.java](com/natamus/starterkit_common_forge/data/Constants.java) |
| `StarterCheckFunctions` | class | — | — | 4 | 53 | [StarterCheckFunctions.java](com/natamus/starterkit_common_forge/functions/StarterCheckFunctions.java) |
| `Reference` | class | — | — | 0 | 9 | [Reference.java](com/natamus/starterkit_common_forge/util/Reference.java) |
