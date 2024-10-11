package com.system_stats.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;

@Service
public class SystemStatsImpl implements SystemStats {

    @Override
    public ResponseEntity<Map<String, Object>> getSystemStats() {
        Map<String, Object> stats = new HashMap<>();

        SystemInfo systemInfo = new SystemInfo();
        HardwareAbstractionLayer hal = systemInfo.getHardware();
        CentralProcessor processor = hal.getProcessor();
        GlobalMemory memory = hal.getMemory();

        stats.put("logicalProcessorCount", processor.getLogicalProcessorCount());
        stats.put("physicalProcessorCount", processor.getPhysicalProcessorCount());

        // Get CPU usage percentage
        double cpuUsage = getCpuUsagePercentage(processor);
        stats.put("cpuUsagePercentage", String.format("%.2f%%", cpuUsage));

        // Get CPU usage percentage for each core
        double[] coreCpuUsage = getCoreCpuUsagePercentage(processor);
        stats.put("coreCpuUsagePercentage", Arrays.stream(coreCpuUsage)
                                                  .mapToObj(usage -> String.format("%.2f%%", usage))
                                                  .toArray(String[]::new));

        // RAM Usage
        long totalMemory = memory.getTotal();
        long usedMemory = totalMemory - memory.getAvailable();
        double ramUsagePercentage = ((double) usedMemory / totalMemory) * 100;
        stats.put("ramUsagePercentage", String.format("%.2f%%", ramUsagePercentage));
        stats.put("ramUsedGB", String.format("%.2f GB", usedMemory / 1073741824.0));
        stats.put("ramTotalGB", String.format("%.2f GB", totalMemory / 1073741824.0));

        // Swap Memory Usage
        long totalSwap = memory.getVirtualMemory().getSwapTotal();
        long usedSwap = memory.getVirtualMemory().getSwapUsed();
        double swapUsagePercentage = ((double) usedSwap / totalSwap) * 100;
        stats.put("swapUsagePercentage", String.format("%.2f%%", swapUsagePercentage));
        stats.put("swapUsedGB", String.format("%.2f GB", usedSwap / 1073741824.0));
        stats.put("swapTotalGB", String.format("%.2f GB", totalSwap / 1073741824.0));

        return ResponseEntity.ok(stats);
    }

    private double getCpuUsagePercentage(CentralProcessor processor) {
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return processor.getSystemCpuLoadBetweenTicks(prevTicks) * 100;
    }

    private double[] getCoreCpuUsagePercentage(CentralProcessor processor) {
        long[][] prevTicks = processor.getProcessorCpuLoadTicks();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        double[] load = processor.getProcessorCpuLoadBetweenTicks(prevTicks);
        return Arrays.stream(load).map(d -> d * 100).toArray();
    }
}