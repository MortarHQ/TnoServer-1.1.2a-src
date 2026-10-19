# Ponderer（`ponderer`）

> jar：`[思索者]ponderer.jar` ｜ 版本：`1.8.2` ｜ 作者：Nobodiiiii
> 规模：**244 个类 / 20 个包 / 37869 行** ｜ 事件处理类 3 ｜ Mixin 24 ｜ 注册/管理类 19

**依赖**：`minecraft`、`ponder`、`flywheel`、`jei`、`resourcify`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `com.nododiiiii.ponderer.ui` | 90 | `PonderItemGridScreen`、`SceneEditorScreen`、`CommandParamScreen` |
| `com.nododiiiii.ponderer.ui.catnip` | 36 | `AbstractDeclarativeListScreen`、`AbstractDeclarativeConfigListScreen`、`AbstractDeclarativeFormScreen` |
| `com.nododiiiii.ponderer.mixin` | 24 | `JeiGuiEventHandlerMixin`、`PonderSceneRegistryNbtMixinFabric`、`PonderSceneRegistryMixinFabric` |
| `com.nododiiiii.ponderer.ponder` | 21 | `PondererClientCommands`、`SceneStore`、`DynamicPonderPlugin` |
| `com.nododiiiii.ponderer.network` | 14 | `CaptureBlockEntityNbtRequestPayload`、`BlueprintConfigResponsePayload`、`BlueprintConfigRequestPayload` |
| `com.nododiiiii.ponderer.neoforge.sticksnapshot.snapshot` | 9 | `ReplaySessionManager`、`ReplayEventInterceptors`、`SnapshotReplayer` |
| `com.nododiiiii.ponderer.ai` | 9 | `RegistryMapper`、`AiSceneGenerator`、`HttpClientFactory` |
| `com.nododiiiii.ponderer.compat.jei` | 9 | `JeiAwareGhostHandler`、`PondererJeiPlugin`、`StepEditorGhostHandler` |
| `com.nododiiiii.ponderer.blueprint` | 6 | `BlueprintHandler`、`BlueprintExport`、`BlueprintPromptScreen` |
| `com.nododiiiii.ponderer.neoforge` | 5 | `PondererNeoForge`、`NeoForgeRegistrationHelper`、`PondererNeoForgeClient` |
| `com.nododiiiii.ponderer.neoforge.sticksnapshot.client` | 4 | `ClientInputHandler`、`MirrorNeoForgeOpenClient`、`NeoForgeShowInterfaceClient` |
| `com.nododiiiii.ponderer.neoforge.sticksnapshot.network` | 4 | `MirrorClosePacket`、`MirrorNeoForgeOpenPacket`、`ReplaySnapshotPacket` |
| `com.nododiiiii.ponderer.platform.services` | 3 | `RegistrationHelper`、`NetworkHelper`、`PlatformHelper` |
| `com.nododiiiii.ponderer` | 3 | `Config`、`ModKeyBindings`、`Ponderer` |
| `com.nododiiiii.ponderer.compat.resourcify` | 2 | `ResourcifyCompat`、`ResourcifyBrowseHelper` |
| `com.nododiiiii.ponderer.util` | 1 | `SafePaths` |
| `com.nododiiiii.ponderer.platform.annotations` | 1 | `ClientOnly` |
| `com.nododiiiii.ponderer.registry` | 1 | `ModItems` |
| `com.nododiiiii.ponderer.neoforge.sticksnapshot` | 1 | `StickSnapshotFeature` |
| `com.nododiiiii.ponderer.platform` | 1 | `PondererServices` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 60）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `ClientInputHandler` | class | — | — | 37 | 532 | [ClientInputHandler.java](com/nododiiiii/ponderer/neoforge/sticksnapshot/client/ClientInputHandler.java) |
| `PonderItemGridScreen` | class | AbstractDeclarativeListScreen | — | 85 | 1298 | [PonderItemGridScreen.java](com/nododiiiii/ponderer/ui/PonderItemGridScreen.java) |
| `JeiGuiEventHandlerMixin` | class | — | Mixin、Pseudo | 0 | 46 | [JeiGuiEventHandlerMixin.java](com/nododiiiii/ponderer/mixin/JeiGuiEventHandlerMixin.java) |
| `PondererClientCommands` | class | — | — | 36 | 669 | [PondererClientCommands.java](com/nododiiiii/ponderer/ponder/PondererClientCommands.java) |
| `SceneEditorScreen` | class | AbstractDeclarativeListScreen | — | 85 | 1069 | [SceneEditorScreen.java](com/nododiiiii/ponderer/ui/SceneEditorScreen.java) |
| `CommandParamScreen` | class | AbstractJeiAwareFormScreen | — | 37 | 461 | [CommandParamScreen.java](com/nododiiiii/ponderer/ui/CommandParamScreen.java) |
| `PonderSceneRegistryNbtMixinFabric` | class | — | Mixin | 0 | 30 | [PonderSceneRegistryNbtMixinFabric.java](com/nododiiiii/ponderer/mixin/PonderSceneRegistryNbtMixinFabric.java) |
| `PonderSceneRegistryMixinFabric` | class | — | Mixin | 0 | 87 | [PonderSceneRegistryMixinFabric.java](com/nododiiiii/ponderer/mixin/PonderSceneRegistryMixinFabric.java) |
| `PonderSceneRegistryMixin` | class | — | Mixin | 0 | 102 | [PonderSceneRegistryMixin.java](com/nododiiiii/ponderer/mixin/PonderSceneRegistryMixin.java) |
| `PonderSceneRegistryNbtMixin` | class | — | Mixin | 0 | 34 | [PonderSceneRegistryNbtMixin.java](com/nododiiiii/ponderer/mixin/PonderSceneRegistryNbtMixin.java) |
| `BlueprintItemConfigScreen` | class | AbstractJeiAwareFormScreen | — | 14 | 216 | [BlueprintItemConfigScreen.java](com/nododiiiii/ponderer/ui/BlueprintItemConfigScreen.java) |
| `SceneStore` | class | — | — | 140 | 2306 | [SceneStore.java](com/nododiiiii/ponderer/ponder/SceneStore.java) |
| `DynamicPonderPlugin` | class | PonderPlugin | — | 77 | 1923 | [DynamicPonderPlugin.java](com/nododiiiii/ponderer/ponder/DynamicPonderPlugin.java) |
| `PermissionManagementScreen` | class | AbstractStatefulDeclarativeFormScree | — | 41 | 542 | [PermissionManagementScreen.java](com/nododiiiii/ponderer/ui/PermissionManagementScreen.java) |
| `PondererNeoForge` | class | — | Mod | 2 | 54 | [PondererNeoForge.java](com/nododiiiii/ponderer/neoforge/PondererNeoForge.java) |
| `ReplaySessionManager` | class | — | — | 8 | 118 | [ReplaySessionManager.java](com/nododiiiii/ponderer/neoforge/sticksnapshot/snapshot/ReplaySessionManager.java) |
| `TriggerManager` | class | — | — | 16 | 422 | [TriggerManager.java](com/nododiiiii/ponderer/ponder/TriggerManager.java) |
| `PondererKeyBindingsScreen` | class | AbstractDeclarativeListScreen | — | 30 | 313 | [PondererKeyBindingsScreen.java](com/nododiiiii/ponderer/ui/PondererKeyBindingsScreen.java) |
| `AbstractSceneEditorFormScreen` | class | AbstractJeiAwareFormScreen | — | 32 | 321 | [AbstractSceneEditorFormScreen.java](com/nododiiiii/ponderer/ui/AbstractSceneEditorFormScreen.java) |
| `AbstractDeclarativeListScreen` | class | AbstractDeclarativeScreen | — | 45 | 388 | [AbstractDeclarativeListScreen.java](com/nododiiiii/ponderer/ui/catnip/AbstractDeclarativeListScreen.java) |
| `PonderUIMixin` | class | Screen | Mixin | 2 | 601 | [PonderUIMixin.java](com/nododiiiii/ponderer/mixin/PonderUIMixin.java) |
| `BlueprintHandler` | class | — | — | 11 | 238 | [BlueprintHandler.java](com/nododiiiii/ponderer/blueprint/BlueprintHandler.java) |
| `FieldSpecs` | class | — | — | 53 | 455 | [FieldSpecs.java](com/nododiiiii/ponderer/ui/FieldSpecs.java) |
| `AiGenerateScreen` | class | AbstractJeiAwareFormScreen | — | 24 | 439 | [AiGenerateScreen.java](com/nododiiiii/ponderer/ui/AiGenerateScreen.java) |
| `AbstractDeclarativeConfigListScreen` | class | AbstractDeclarativeFormScreen | — | 10 | 93 | [AbstractDeclarativeConfigListScreen.java](com/nododiiiii/ponderer/ui/catnip/AbstractDeclarativeConfigListScreen.java) |
| `AbstractDeclarativeFormScreen` | class | AbstractDeclarativeListScreen | — | 27 | 262 | [AbstractDeclarativeFormScreen.java](com/nododiiiii/ponderer/ui/catnip/AbstractDeclarativeFormScreen.java) |
| `ReplayEventInterceptors` | class | — | — | 6 | 64 | [ReplayEventInterceptors.java](com/nododiiiii/ponderer/neoforge/sticksnapshot/snapshot/ReplayEventInterceptors.java) |
| `SnapshotReplayer` | class | Connection | — | 27 | 608 | [SnapshotReplayer.java](com/nododiiiii/ponderer/neoforge/sticksnapshot/snapshot/SnapshotReplayer.java) |
| `AbstractContainerScreenAccessor` | interface | — | Mixin | 0 | 21 | [AbstractContainerScreenAccessor.java](com/nododiiiii/ponderer/mixin/AbstractContainerScreenAccessor.java) |
| `EmbeddedMirrorContainerScreenMixin` | class | — | Mixin | 0 | 197 | [EmbeddedMirrorContainerScreenMixin.java](com/nododiiiii/ponderer/mixin/EmbeddedMirrorContainerScreenMixin.java) |
| `RegistryMapper` | class | — | — | 6 | 247 | [RegistryMapper.java](com/nododiiiii/ponderer/ai/RegistryMapper.java) |
| `UploadPermissions` | class | — | — | 37 | 516 | [UploadPermissions.java](com/nododiiiii/ponderer/ponder/UploadPermissions.java) |
| `PonderJsParsers` | class | — | — | 29 | 762 | [PonderJsParsers.java](com/nododiiiii/ponderer/ponder/PonderJsParsers.java) |
| `ReferenceUrlManager` | class | SnapshotParticipant | — | 14 | 110 | [ReferenceUrlManager.java](com/nododiiiii/ponderer/ui/ReferenceUrlManager.java) |
| `ModifyBlockEntityNbtScreen` | class | AbstractStepEditorScreen | — | 7 | 153 | [ModifyBlockEntityNbtScreen.java](com/nododiiiii/ponderer/ui/ModifyBlockEntityNbtScreen.java) |
| `CreateItemEntityScreen` | class | AbstractStepEditorScreen | — | 6 | 167 | [CreateItemEntityScreen.java](com/nododiiiii/ponderer/ui/CreateItemEntityScreen.java) |
| `AbstractStepEditorScreen` | class | AbstractSceneEditorFormScreen | — | 23 | 214 | [AbstractStepEditorScreen.java](com/nododiiiii/ponderer/ui/AbstractStepEditorScreen.java) |
| `TriggerEditorScreen` | class | AbstractSceneEditorFormScreen | — | 18 | 473 | [TriggerEditorScreen.java](com/nododiiiii/ponderer/ui/TriggerEditorScreen.java) |
| `NeoForgeRegistrationHelper` | class | RegistrationHelper | — | 2 | 24 | [NeoForgeRegistrationHelper.java](com/nododiiiii/ponderer/neoforge/NeoForgeRegistrationHelper.java) |
| `RegistrationHelper` | interface | — | — | 0 | 11 | [RegistrationHelper.java](com/nododiiiii/ponderer/platform/services/RegistrationHelper.java) |
| `PonderPackRegistry` | class | — | — | 8 | 129 | [PonderPackRegistry.java](com/nododiiiii/ponderer/ponder/PonderPackRegistry.java) |
| `PondererConfigScreen` | class | AbstractDeclarativeConfigListScreen | — | 3 | 69 | [PondererConfigScreen.java](com/nododiiiii/ponderer/ui/PondererConfigScreen.java) |
| `UndoManager` | class | — | — | 6 | 65 | [UndoManager.java](com/nododiiiii/ponderer/ui/UndoManager.java) |
| `SetBlockScreen` | class | AbstractStepEditorScreen | — | 7 | 318 | [SetBlockScreen.java](com/nododiiiii/ponderer/ui/SetBlockScreen.java) |
| `AiConfigScreen` | class | AbstractDeclarativeConfigListScreen | — | 1 | 63 | [AiConfigScreen.java](com/nododiiiii/ponderer/ui/AiConfigScreen.java) |
| `SceneDescEditorScreen` | class | AbstractStatefulDeclarativeFormScree | — | 17 | 352 | [SceneDescEditorScreen.java](com/nododiiiii/ponderer/ui/SceneDescEditorScreen.java) |
| `WorldSectionElementImplMixin` | class | — | Mixin | 0 | 32 | [WorldSectionElementImplMixin.java](com/nododiiiii/ponderer/mixin/WorldSectionElementImplMixin.java) |
| `PonderUINbtMixin` | class | — | Mixin | 0 | 44 | [PonderUINbtMixin.java](com/nododiiiii/ponderer/mixin/PonderUINbtMixin.java) |
| `PonderUIAccessor` | interface | — | Mixin | 0 | 36 | [PonderUIAccessor.java](com/nododiiiii/ponderer/mixin/PonderUIAccessor.java) |
| `PonderUINbtMixinFabric` | class | — | Mixin | 0 | 44 | [PonderUINbtMixinFabric.java](com/nododiiiii/ponderer/mixin/PonderUINbtMixinFabric.java) |
| `PonderSceneViewOffsetMixin` | class | PonderSceneViewOffsetAccess | Mixin | 0 | 84 | [PonderSceneViewOffsetMixin.java](com/nododiiiii/ponderer/mixin/PonderSceneViewOffsetMixin.java) |
| `PonderTooltipNbtMixinFabric` | class | — | Mixin | 0 | 33 | [PonderTooltipNbtMixinFabric.java](com/nododiiiii/ponderer/mixin/PonderTooltipNbtMixinFabric.java) |
| `PonderSceneAccessor` | interface | — | Mixin | 0 | 30 | [PonderSceneAccessor.java](com/nododiiiii/ponderer/mixin/PonderSceneAccessor.java) |
| `PonderUIMirrorRenderMixin` | class | — | Mixin | 0 | 171 | [PonderUIMirrorRenderMixin.java](com/nododiiiii/ponderer/mixin/PonderUIMirrorRenderMixin.java) |
| `MinecraftCoordPickScrollMixin` | class | — | Mixin | 0 | 28 | [MinecraftCoordPickScrollMixin.java](com/nododiiiii/ponderer/mixin/MinecraftCoordPickScrollMixin.java) |
| `PonderProgressBarAccessorMixin` | interface | — | Mixin | 0 | 13 | [PonderProgressBarAccessorMixin.java](com/nododiiiii/ponderer/mixin/PonderProgressBarAccessorMixin.java) |
| `SceneTransformOffsetMixin` | class | — | Mixin | 0 | 56 | [SceneTransformOffsetMixin.java](com/nododiiiii/ponderer/mixin/SceneTransformOffsetMixin.java) |
| `SceneTransformOffsetMixinFabric` | class | — | Mixin | 0 | 79 | [SceneTransformOffsetMixinFabric.java](com/nododiiiii/ponderer/mixin/SceneTransformOffsetMixinFabric.java) |
| `PonderTooltipNbtMixin` | class | — | Mixin | 0 | 37 | [PonderTooltipNbtMixin.java](com/nododiiiii/ponderer/mixin/PonderTooltipNbtMixin.java) |
| `PonderKeybindsAccessor` | interface | — | Mixin | 0 | 13 | [PonderKeybindsAccessor.java](com/nododiiiii/ponderer/mixin/PonderKeybindsAccessor.java) |

## 事件处理（`@SubscribeEvent`，3 个类）

| 类 | 事件方法数 | 包 | 源码 |
|---|---|---|---|
| `ClientInputHandler` | 6 | `com.nododiiiii.ponderer.neoforge.sticksnapshot.client` | [ClientInputHandler.java](com/nododiiiii/ponderer/neoforge/sticksnapshot/client/ClientInputHandler.java) |
| `ReplayEventInterceptors` | 6 | `com.nododiiiii.ponderer.neoforge.sticksnapshot.snapshot` | [ReplayEventInterceptors.java](com/nododiiiii/ponderer/neoforge/sticksnapshot/snapshot/ReplayEventInterceptors.java) |
| `ReplaySessionManager` | 3 | `com.nododiiiii.ponderer.neoforge.sticksnapshot.snapshot` | [ReplaySessionManager.java](com/nododiiiii/ponderer/neoforge/sticksnapshot/snapshot/ReplaySessionManager.java) |

## Mixin（24 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `JeiGuiEventHandlerMixin` | `JeiGuiEventHandler` | [JeiGuiEventHandlerMixin.java](com/nododiiiii/ponderer/mixin/JeiGuiEventHandlerMixin.java) |
| `PonderSceneRegistryNbtMixinFabric` | `PonderSceneRegistryNbtFabric` | [PonderSceneRegistryNbtMixinFabric.java](com/nododiiiii/ponderer/mixin/PonderSceneRegistryNbtMixinFabric.java) |
| `PonderSceneRegistryMixinFabric` | `PonderSceneRegistryFabric` | [PonderSceneRegistryMixinFabric.java](com/nododiiiii/ponderer/mixin/PonderSceneRegistryMixinFabric.java) |
| `PonderSceneRegistryMixin` | `PonderSceneRegistry` | [PonderSceneRegistryMixin.java](com/nododiiiii/ponderer/mixin/PonderSceneRegistryMixin.java) |
| `PonderSceneRegistryNbtMixin` | `PonderSceneRegistryNbt` | [PonderSceneRegistryNbtMixin.java](com/nododiiiii/ponderer/mixin/PonderSceneRegistryNbtMixin.java) |
| `PonderUIMixin` | `PonderUI` | [PonderUIMixin.java](com/nododiiiii/ponderer/mixin/PonderUIMixin.java) |
| `AbstractContainerScreenAccessor` | `AbstractContainerScreenAccessor` | [AbstractContainerScreenAccessor.java](com/nododiiiii/ponderer/mixin/AbstractContainerScreenAccessor.java) |
| `EmbeddedMirrorContainerScreenMixin` | `EmbeddedMirrorContainerScreen` | [EmbeddedMirrorContainerScreenMixin.java](com/nododiiiii/ponderer/mixin/EmbeddedMirrorContainerScreenMixin.java) |
| `WorldSectionElementImplMixin` | `WorldSectionElementImpl` | [WorldSectionElementImplMixin.java](com/nododiiiii/ponderer/mixin/WorldSectionElementImplMixin.java) |
| `PonderUINbtMixin` | `PonderUINbt` | [PonderUINbtMixin.java](com/nododiiiii/ponderer/mixin/PonderUINbtMixin.java) |
| `PonderUIAccessor` | `PonderUIAccessor` | [PonderUIAccessor.java](com/nododiiiii/ponderer/mixin/PonderUIAccessor.java) |
| `PonderUINbtMixinFabric` | `PonderUINbtFabric` | [PonderUINbtMixinFabric.java](com/nododiiiii/ponderer/mixin/PonderUINbtMixinFabric.java) |
| `PonderSceneViewOffsetMixin` | `PonderSceneViewOffset` | [PonderSceneViewOffsetMixin.java](com/nododiiiii/ponderer/mixin/PonderSceneViewOffsetMixin.java) |
| `PonderTooltipNbtMixinFabric` | `PonderTooltipNbtFabric` | [PonderTooltipNbtMixinFabric.java](com/nododiiiii/ponderer/mixin/PonderTooltipNbtMixinFabric.java) |
| `PonderSceneAccessor` | `PonderSceneAccessor` | [PonderSceneAccessor.java](com/nododiiiii/ponderer/mixin/PonderSceneAccessor.java) |
| `PonderUIMirrorRenderMixin` | `PonderUIMirrorRender` | [PonderUIMirrorRenderMixin.java](com/nododiiiii/ponderer/mixin/PonderUIMirrorRenderMixin.java) |
| `MinecraftCoordPickScrollMixin` | `MinecraftCoordPickScroll` | [MinecraftCoordPickScrollMixin.java](com/nododiiiii/ponderer/mixin/MinecraftCoordPickScrollMixin.java) |
| `PonderProgressBarAccessorMixin` | `PonderProgressBarAccessor` | [PonderProgressBarAccessorMixin.java](com/nododiiiii/ponderer/mixin/PonderProgressBarAccessorMixin.java) |
| `SceneTransformOffsetMixin` | `SceneTransformOffset` | [SceneTransformOffsetMixin.java](com/nododiiiii/ponderer/mixin/SceneTransformOffsetMixin.java) |
| `SceneTransformOffsetMixinFabric` | `SceneTransformOffsetFabric` | [SceneTransformOffsetMixinFabric.java](com/nododiiiii/ponderer/mixin/SceneTransformOffsetMixinFabric.java) |
| `PonderTooltipNbtMixin` | `PonderTooltipNbt` | [PonderTooltipNbtMixin.java](com/nododiiiii/ponderer/mixin/PonderTooltipNbtMixin.java) |
| `PonderKeybindsAccessor` | `PonderKeybindsAccessor` | [PonderKeybindsAccessor.java](com/nododiiiii/ponderer/mixin/PonderKeybindsAccessor.java) |
| `MinecraftNbtPickMixin` | `MinecraftNbtPick` | [MinecraftNbtPickMixin.java](com/nododiiiii/ponderer/mixin/MinecraftNbtPickMixin.java) |
| `PonderLocalizationMixin` | `PonderLocalization` | [PonderLocalizationMixin.java](com/nododiiiii/ponderer/mixin/PonderLocalizationMixin.java) |

## 配置相关类

| 类 | 注解 | 源码 |
|---|---|---|
| `BlueprintItemConfigScreen` | — | [BlueprintItemConfigScreen.java](com/nododiiiii/ponderer/ui/BlueprintItemConfigScreen.java) |
| `AbstractDeclarativeConfigListScreen` | — | [AbstractDeclarativeConfigListScreen.java](com/nododiiiii/ponderer/ui/catnip/AbstractDeclarativeConfigListScreen.java) |
| `PondererConfigScreen` | — | [PondererConfigScreen.java](com/nododiiiii/ponderer/ui/PondererConfigScreen.java) |
| `AiConfigScreen` | — | [AiConfigScreen.java](com/nododiiiii/ponderer/ui/AiConfigScreen.java) |
| `BlueprintConfigResponsePayload` | — | [BlueprintConfigResponsePayload.java](com/nododiiiii/ponderer/network/BlueprintConfigResponsePayload.java) |
| `ConfigEntries` | — | [ConfigEntries.java](com/nododiiiii/ponderer/ui/catnip/ConfigEntries.java) |
| `Config` | — | [Config.java](com/nododiiiii/ponderer/Config.java) |
| `BlueprintConfigRequestPayload` | — | [BlueprintConfigRequestPayload.java](com/nododiiiii/ponderer/network/BlueprintConfigRequestPayload.java) |
| `BlueprintConfigUpdatePayload` | — | [BlueprintConfigUpdatePayload.java](com/nododiiiii/ponderer/network/BlueprintConfigUpdatePayload.java) |
| `LocalizedStringConfigEntry` | — | [LocalizedStringConfigEntry.java](com/nododiiiii/ponderer/ui/catnip/LocalizedStringConfigEntry.java) |
| `ClippedConfigTextField` | — | [ClippedConfigTextField.java](com/nododiiiii/ponderer/ui/catnip/ClippedConfigTextField.java) |
| `LocalizedChoiceConfigEntry` | — | [LocalizedChoiceConfigEntry.java](com/nododiiiii/ponderer/ui/catnip/LocalizedChoiceConfigEntry.java) |
| `ConfigEntrySupport` | — | [ConfigEntrySupport.java](com/nododiiiii/ponderer/ui/catnip/ConfigEntrySupport.java) |
| `LocalizedIntegerConfigEntry` | — | [LocalizedIntegerConfigEntry.java](com/nododiiiii/ponderer/ui/catnip/LocalizedIntegerConfigEntry.java) |
| `LocalizedBooleanConfigEntry` | — | [LocalizedBooleanConfigEntry.java](com/nododiiiii/ponderer/ui/catnip/LocalizedBooleanConfigEntry.java) |

## 按包浏览

- `com.nododiiiii.ponderer`（3 类）
- `com.nododiiiii.ponderer.ai`（9 类）
- `com.nododiiiii.ponderer.blueprint`（6 类）
- `com.nododiiiii.ponderer.compat.jei`（9 类）
- `com.nododiiiii.ponderer.compat.resourcify`（2 类）
- `com.nododiiiii.ponderer.mixin`（24 类）
- `com.nododiiiii.ponderer.neoforge`（5 类）
- `com.nododiiiii.ponderer.neoforge.sticksnapshot`（1 类）
- `com.nododiiiii.ponderer.neoforge.sticksnapshot.client`（4 类）
- `com.nododiiiii.ponderer.neoforge.sticksnapshot.network`（4 类）
- `com.nododiiiii.ponderer.neoforge.sticksnapshot.snapshot`（9 类）
- `com.nododiiiii.ponderer.network`（14 类）
- `com.nododiiiii.ponderer.platform`（1 类）
- `com.nododiiiii.ponderer.platform.annotations`（1 类）
- `com.nododiiiii.ponderer.platform.services`（3 类）
- `com.nododiiiii.ponderer.ponder`（21 类）
- `com.nododiiiii.ponderer.registry`（1 类）
- `com.nododiiiii.ponderer.ui`（90 类）
- `com.nododiiiii.ponderer.ui.catnip`（36 类）
- `com.nododiiiii.ponderer.util`（1 类）
