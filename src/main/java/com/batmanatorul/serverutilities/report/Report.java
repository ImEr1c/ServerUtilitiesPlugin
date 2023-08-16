package com.batmanatorul.serverutilities.report;

import com.google.common.collect.ImmutableMap;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.Map;
import java.util.UUID;

public class Report implements ConfigurationSerializable {
    private final UUID reporter;
    private final String name;
    private final UUID player;
    private final ReportType reportType;

    public Report(UUID reporter, UUID player) {
        this.reporter = reporter;
        this.name = null;
        this.player = player;
        this.reportType = ReportType.PLAYER;
    }

    public Report(UUID reporter, String name) {
        this.reporter = reporter;
        this.name = name;
        this.player = null;
        this.reportType = ReportType.BUG;
    }

    public String getName() {
        return name;
    }

    public ReportType getReportType() {
        return reportType;
    }

    public UUID getPlayer() {
        return player;
    }

    public UUID getReporter() {
        return reporter;
    }

    @Override
    public Map<String, Object> serialize() {
        return switch (reportType) {
            case BUG -> ImmutableMap.of("type", reportType, "reporter", reporter, "name", name);
            case PLAYER -> ImmutableMap.of("type", reportType, "reporter", reporter, "player", player);
        };
    }

    public static Report deserialize(Map<String, Object> map) {
        ReportType reportType = (ReportType) map.get("type");

        return switch (reportType) {
            case PLAYER -> new Report((UUID) map.get("reporter"), (UUID) map.get("player"));
            case BUG -> new Report((UUID) map.get("reporter"), (String) map.get("name"));
        };
    }

    public enum ReportType {
        PLAYER,
        BUG
    }
}
