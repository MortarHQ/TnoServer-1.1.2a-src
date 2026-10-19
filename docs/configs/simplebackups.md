---
页面: configs/simplebackups
数据来源: /storage/mc/TnoServer-1.1.2a/config 下全部配置文件 + 反编译源码（grep 键名）
证据等级: ★ 键/值与注释取自配置原文；读取者为源码中出现的同名标识符（可能含同名误报）
最后更新: 2026-10-20
---

# `simplebackups` 配置项

> **3 个文件 / 28 个键** ｜ 返回 [配置目录](README.md) ｜ 源码：[sources/simplebackups](../../mods/simplebackups/)

| 文件 | 键 | 实际值 | 类型 | 注释 | 读取者（同名标识符出现的类） |
|---|---|---|---|---|---|
| `simplebackups-server.toml` | `commandsCheatsDisabled` | `False` | bool | Should commands work without cheats enabled? Mainly recommen | `simplebackups:EventListener`、`simplebackups:ServerConfig` |
| `simplebackups-server.toml` | `messagesForEveryone` | `True` | bool | Should all users receive the backup message? Disable to only | `simplebackups:BackupThread`、`simplebackups:ServerConfig` |
| `simplebackups-common.toml` | `enabled` | `True` | bool | If set false, no backups are being made. | `artifacts:ArtifactHooks`、`artifacts:ArtifactsConfigScreen`、`artifacts:CureEffects` |
| `simplebackups-common.toml` | `backupType` | `MODIFIED_SINCE_LAST` | str | Allowed Values: FULL_BACKUPS, MODIFIED_SINCE_LAST, MODIFIED_ | `simplebackups:BackupChain`、`simplebackups:BackupChainManager`、`simplebackups:BackupThread` |
| `simplebackups-common.toml` | `saveAll` | `True` | bool | Should a save-all be forced before backup? | `cloth_config:AbstractConfigScreen`、`cloth_config:ClothConfigScreen`、`cloth_config:ConfigScreen` |
| `simplebackups-common.toml` | `captureLatestLog` | `True` | bool | Should log lines written to logs/latest.log during backup be | `simplebackups:BackupThread`、`simplebackups:CommonConfig` |
| `simplebackups-common.toml` | `fullBackupTimer` | `4320` | int | Range: 1 ~ 5259600 | `simplebackups:CommonConfig` |
| `simplebackups-common.toml` | `backupsToKeep` | `10` | int | Range: 1 ~ 32767 | `simplebackups:CommonConfig` |
| `simplebackups-common.toml` | `timer` | `120` | int | Range: 1 ~ 32767 | `block_factorys_bosses:AbstractBossEntity`、`block_factorys_bosses:ActiveDataState`、`block_factorys_bosses:ActiveState` |
| `simplebackups-common.toml` | `compressionLevel` | `-1` | int | Range: -1 ~ 9 | `simplebackups:CommonConfig` |
| `simplebackups-common.toml` | `sendMessages` | `True` | bool | Should message be sent when backup is in the making? | `fdlib:TriggerClientsideConfigReloadPacket`、`simplebackups:BackupThread`、`simplebackups:CommonConfig` |
| `simplebackups-common.toml` | `maxDiskSize` | `250 GB` | str | Valid storage types: B, KB, MB, GB, TB | `simplebackups:CommonConfig` |
| `simplebackups-common.toml` | `outputPath` | `simplebackups` | str | Used to define the output path. | `foxablazeaqzl_wiki:FoxWikiCommands`、`friendsandfoes:StructureNbtUpdater`、`moogs_structures:StructureNbtUpdater` |
| `simplebackups-common.toml` | `noPlayerBackups` | `False` | bool | Create backups, even if nobody is online | `simplebackups:CommonConfig` |
| `simplebackups-common.toml` | `noPlayerBackupCount` | `1` | int | Range: > 0 | `simplebackups:CommonConfig`、`simplebackups:EventListener` |
| `simplebackups-common.toml` | `noPlayerBackupTimer` | `0` | int | Range: > 0 | `simplebackups:CommonConfig` |
| `simplebackups-common.toml` | `createSubDirs` | `True` | bool | Keep in mind that all configs above, including backupsToKeep | `simplebackups:CommonConfig` |
| `simplebackups-common.toml` | `useTickCounter` | `False` | bool | Keep in mind that lagging servers will result in larger gaps | `simplebackups:BackupThread`、`simplebackups:CommonConfig`、`simplebackups:EventListener` |
| `simplebackups-common.toml` | `error_handling.collectErrors` | `True` | bool | I/O Errors will be collected and print into the chat. The mo | `simplebackups:BackupThread`、`simplebackups:CommonConfig` |
| `simplebackups-common.toml` | `error_handling.deleteUnfinishedBackup` | `True` | bool | If collectErrors is 'false', backups with errors will be del | `simplebackups:BackupThread`、`simplebackups:CommonConfig` |
| `simplebackups-common.toml` | `to_ignore.ignored_paths` | `[]` | list | All files within these directories will also be excluded | `simplebackups:CommonConfig` |
| `simplebackups-common.toml` | `to_ignore.ignored_files` | `[]` | list | Use this for individual files rather than entire directories | `simplebackups:CommonConfig` |
| `simplebackups-common.toml` | `to_ignore.ignored_files_regex` | `` | str | Leave empty to disable regex-based file exclusion | `simplebackups:CommonConfig` |
| `simplebackups-common.toml` | `mod_compat.mc2discord` | `True` | bool | Should backup notifications be sent to Discord by using mc2d | `simplebackups:BackupThread`、`simplebackups:CommonConfig`、`simplebackups:Mc2DiscordCompat` |
| `simplebackups-common.toml` | `mod_compat.onlyFavorites` | `False` | bool | Should only worlds be backed up that are marked as favorite  | `simplebackups:CherishedWorldsCompat`、`simplebackups:CommonConfig` |
| `simplebackups-common-experimental.toml` | `enabled` | `False` | bool | Only enable if you want to test the experimental backup syst | `artifacts:ArtifactHooks`、`artifacts:ArtifactsConfigScreen`、`artifacts:CureEffects` |
| `simplebackups-common-experimental.toml` | `backupType` | `INCREMENTAL` | str | Allowed Values: FULL_BACKUPS, INCREMENTAL, DIFFERENTIAL | `simplebackups:BackupChain`、`simplebackups:BackupChainManager`、`simplebackups:BackupThread` |
| `simplebackups-common-experimental.toml` | `backupChainsToKeep` | `10` | int | Range: 1 ~ 32767 | `simplebackups:CommonConfig`、`simplebackups:ExperimentalConfig` |
