# Melody（`melody`）

> jar：`[前置]melody.jar` ｜ 版本：`1.0.10` ｜ 作者：Keksuccino
> 规模：**20 个类 / 7 个包 / 1090 行** ｜ 事件处理类 0 ｜ Mixin 3 ｜ 注册/管理类 2

**依赖**：`minecraft`

## 包结构

| 包 | 类数 | 代表类 |
|---|---|---|
| `de.keksuccino.melody.resources.audio.openal` | 5 | `ALAudioClip`、`ALErrorHandler`、`ALUtils` |
| `de.keksuccino.melody.resources.audio` | 4 | `SimpleAudioFactory`、`MinecraftSoundSettingsObserver`、`MelodyAudioException` |
| `de.keksuccino.melody.mixin.mixins.common.client` | 3 | `IMixinSoundManager`、`IMixinSoundEngine`、`MixinSoundEngine` |
| `de.keksuccino.melody.platform` | 3 | `NeoForgeCompatibilityLayer`、`NeoForgePlatformHelper`、`Services` |
| `de.keksuccino.melody` | 2 | `MelodyNeoForge`、`Melody` |
| `de.keksuccino.melody.platform.services` | 2 | `IPlatformCompatibilityLayer`、`IPlatformHelper` |
| `de.keksuccino.melody.mixin` | 1 | `MelodyMixinPlugin` |

## 关键类（按重要度：注解/事件/命名/规模综合，Top 20）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `IMixinSoundManager` | interface | — | Mixin | 0 | 13 | [IMixinSoundManager.java](de/keksuccino/melody/mixin/mixins/common/client/IMixinSoundManager.java) |
| `MelodyNeoForge` | class | — | Mod | 0 | 11 | [MelodyNeoForge.java](de/keksuccino/melody/MelodyNeoForge.java) |
| `IMixinSoundEngine` | interface | — | Mixin | 0 | 12 | [IMixinSoundEngine.java](de/keksuccino/melody/mixin/mixins/common/client/IMixinSoundEngine.java) |
| `MixinSoundEngine` | class | — | Mixin | 2 | 40 | [MixinSoundEngine.java](de/keksuccino/melody/mixin/mixins/common/client/MixinSoundEngine.java) |
| `ALAudioClip` | class | AudioClip | — | 22 | 243 | [ALAudioClip.java](de/keksuccino/melody/resources/audio/openal/ALAudioClip.java) |
| `ALErrorHandler` | class | — | — | 3 | 34 | [ALErrorHandler.java](de/keksuccino/melody/resources/audio/openal/ALErrorHandler.java) |
| `SimpleAudioFactory` | class | — | — | 5 | 312 | [SimpleAudioFactory.java](de/keksuccino/melody/resources/audio/SimpleAudioFactory.java) |
| `NeoForgeCompatibilityLayer` | class | IPlatformCompatibilityLayer | — | 0 | 7 | [NeoForgeCompatibilityLayer.java](de/keksuccino/melody/platform/NeoForgeCompatibilityLayer.java) |
| `NeoForgePlatformHelper` | class | IPlatformHelper | — | 9 | 78 | [NeoForgePlatformHelper.java](de/keksuccino/melody/platform/NeoForgePlatformHelper.java) |
| `IPlatformCompatibilityLayer` | interface | — | — | 0 | 5 | [IPlatformCompatibilityLayer.java](de/keksuccino/melody/platform/services/IPlatformCompatibilityLayer.java) |
| `MinecraftSoundSettingsObserver` | class | — | — | 6 | 48 | [MinecraftSoundSettingsObserver.java](de/keksuccino/melody/resources/audio/MinecraftSoundSettingsObserver.java) |
| `ALUtils` | class | — | — | 3 | 61 | [ALUtils.java](de/keksuccino/melody/resources/audio/openal/ALUtils.java) |
| `ALAudioBuffer` | class | — | — | 5 | 68 | [ALAudioBuffer.java](de/keksuccino/melody/resources/audio/openal/ALAudioBuffer.java) |
| `MelodyMixinPlugin` | class | IMixinConfigPlugin | — | 7 | 38 | [MelodyMixinPlugin.java](de/keksuccino/melody/mixin/MelodyMixinPlugin.java) |
| `IPlatformHelper` | interface | — | — | 0 | 30 | [IPlatformHelper.java](de/keksuccino/melody/platform/services/IPlatformHelper.java) |
| `Melody` | class | — | — | 1 | 21 | [Melody.java](de/keksuccino/melody/Melody.java) |
| `MelodyAudioException` | class | Exception | — | 0 | 11 | [MelodyAudioException.java](de/keksuccino/melody/resources/audio/MelodyAudioException.java) |
| `AudioClip` | interface | — | — | 0 | 32 | [AudioClip.java](de/keksuccino/melody/resources/audio/AudioClip.java) |
| `ALException` | class | Exception | — | 0 | 11 | [ALException.java](de/keksuccino/melody/resources/audio/openal/ALException.java) |
| `Services` | class | — | — | 1 | 15 | [Services.java](de/keksuccino/melody/platform/Services.java) |

## Mixin（3 个类）

| Mixin 类 | 目标（按命名推断） | 源码 |
|---|---|---|
| `IMixinSoundManager` | `ISoundManager` | [IMixinSoundManager.java](de/keksuccino/melody/mixin/mixins/common/client/IMixinSoundManager.java) |
| `IMixinSoundEngine` | `ISoundEngine` | [IMixinSoundEngine.java](de/keksuccino/melody/mixin/mixins/common/client/IMixinSoundEngine.java) |
| `MixinSoundEngine` | `SoundEngine` | [MixinSoundEngine.java](de/keksuccino/melody/mixin/mixins/common/client/MixinSoundEngine.java) |

## 全部类（20）

| 类 | 类型 | 继承 / 接口 | 注解 | 方法 | 行数 | 源码 |
|---|---|---|---|---|---|---|
| `IMixinSoundManager` | interface | — | Mixin | 0 | 13 | [IMixinSoundManager.java](de/keksuccino/melody/mixin/mixins/common/client/IMixinSoundManager.java) |
| `MelodyNeoForge` | class | — | Mod | 0 | 11 | [MelodyNeoForge.java](de/keksuccino/melody/MelodyNeoForge.java) |
| `IMixinSoundEngine` | interface | — | Mixin | 0 | 12 | [IMixinSoundEngine.java](de/keksuccino/melody/mixin/mixins/common/client/IMixinSoundEngine.java) |
| `MixinSoundEngine` | class | — | Mixin | 2 | 40 | [MixinSoundEngine.java](de/keksuccino/melody/mixin/mixins/common/client/MixinSoundEngine.java) |
| `ALAudioClip` | class | AudioClip | — | 22 | 243 | [ALAudioClip.java](de/keksuccino/melody/resources/audio/openal/ALAudioClip.java) |
| `ALErrorHandler` | class | — | — | 3 | 34 | [ALErrorHandler.java](de/keksuccino/melody/resources/audio/openal/ALErrorHandler.java) |
| `SimpleAudioFactory` | class | — | — | 5 | 312 | [SimpleAudioFactory.java](de/keksuccino/melody/resources/audio/SimpleAudioFactory.java) |
| `NeoForgeCompatibilityLayer` | class | IPlatformCompatibilityLayer | — | 0 | 7 | [NeoForgeCompatibilityLayer.java](de/keksuccino/melody/platform/NeoForgeCompatibilityLayer.java) |
| `NeoForgePlatformHelper` | class | IPlatformHelper | — | 9 | 78 | [NeoForgePlatformHelper.java](de/keksuccino/melody/platform/NeoForgePlatformHelper.java) |
| `IPlatformCompatibilityLayer` | interface | — | — | 0 | 5 | [IPlatformCompatibilityLayer.java](de/keksuccino/melody/platform/services/IPlatformCompatibilityLayer.java) |
| `MinecraftSoundSettingsObserver` | class | — | — | 6 | 48 | [MinecraftSoundSettingsObserver.java](de/keksuccino/melody/resources/audio/MinecraftSoundSettingsObserver.java) |
| `ALUtils` | class | — | — | 3 | 61 | [ALUtils.java](de/keksuccino/melody/resources/audio/openal/ALUtils.java) |
| `ALAudioBuffer` | class | — | — | 5 | 68 | [ALAudioBuffer.java](de/keksuccino/melody/resources/audio/openal/ALAudioBuffer.java) |
| `MelodyMixinPlugin` | class | IMixinConfigPlugin | — | 7 | 38 | [MelodyMixinPlugin.java](de/keksuccino/melody/mixin/MelodyMixinPlugin.java) |
| `IPlatformHelper` | interface | — | — | 0 | 30 | [IPlatformHelper.java](de/keksuccino/melody/platform/services/IPlatformHelper.java) |
| `Melody` | class | — | — | 1 | 21 | [Melody.java](de/keksuccino/melody/Melody.java) |
| `MelodyAudioException` | class | Exception | — | 0 | 11 | [MelodyAudioException.java](de/keksuccino/melody/resources/audio/MelodyAudioException.java) |
| `AudioClip` | interface | — | — | 0 | 32 | [AudioClip.java](de/keksuccino/melody/resources/audio/AudioClip.java) |
| `ALException` | class | Exception | — | 0 | 11 | [ALException.java](de/keksuccino/melody/resources/audio/openal/ALException.java) |
| `Services` | class | — | — | 1 | 15 | [Services.java](de/keksuccino/melody/platform/Services.java) |
