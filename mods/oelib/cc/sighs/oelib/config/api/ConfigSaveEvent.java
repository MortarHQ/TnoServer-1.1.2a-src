package cc.sighs.oelib.config.api;

import cc.sighs.oelib.config.ConfigUnit;

public record ConfigSaveEvent<T>(ConfigUnit<T> unit, T value) {
}
