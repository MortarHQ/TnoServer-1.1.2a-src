package cc.sighs.oelib.config.api;

import cc.sighs.oelib.config.ConfigUnit;

public record ConfigSyncEvent<T>(ConfigUnit<T> unit, T value, boolean fromServer) {
}
