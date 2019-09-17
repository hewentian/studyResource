package com.hewentian.os;

public class MonitorInfoBean {
	/** 可使用内存. */
	private long totalMemory;

	/** 剩余内存. */
	private long freeMemory;

	/** 最大可使用内存. */
	private long maxMemory;

	/** 操作系统. */
	private String osName;

	/** 总的物理内存. */
	private long totalMemorySize;

	/** 剩余的物理内存. */
	private long freePhysicalMemorySize;

	/** 已使用的物理内存. */
	private long usedMemory;

	/** 线程总数. */
	private int totalThread;

	/** cpu使用率. */
	private double cpuRatio;

	/** 总的磁盘空间, 单位：MB */
	private long diskTotalSpace;

	/** 可用磁盘空间, 单位：MB */
	private long diskFreeSpace;

	/** 已用磁盘空间, 单位：MB */
	private long diskUsedSpace;

	public long getFreeMemory() {
		return freeMemory;
	}

	public void setFreeMemory(long freeMemory) {
		this.freeMemory = freeMemory;
	}

	public long getFreePhysicalMemorySize() {
		return freePhysicalMemorySize;
	}

	public void setFreePhysicalMemorySize(long freePhysicalMemorySize) {
		this.freePhysicalMemorySize = freePhysicalMemorySize;
	}

	public long getMaxMemory() {
		return maxMemory;
	}

	public void setMaxMemory(long maxMemory) {
		this.maxMemory = maxMemory;
	}

	public String getOsName() {
		return osName;
	}

	public void setOsName(String osName) {
		this.osName = osName;
	}

	public long getTotalMemory() {
		return totalMemory;
	}

	public void setTotalMemory(long totalMemory) {
		this.totalMemory = totalMemory;
	}

	public long getTotalMemorySize() {
		return totalMemorySize;
	}

	public void setTotalMemorySize(long totalMemorySize) {
		this.totalMemorySize = totalMemorySize;
	}

	public int getTotalThread() {
		return totalThread;
	}

	public void setTotalThread(int totalThread) {
		this.totalThread = totalThread;
	}

	public long getUsedMemory() {
		return usedMemory;
	}

	public void setUsedMemory(long usedMemory) {
		this.usedMemory = usedMemory;
	}

	public double getCpuRatio() {
		return cpuRatio;
	}

	public void setCpuRatio(double cpuRatio) {
		this.cpuRatio = cpuRatio;
	}

	public long getDiskTotalSpace() {
		return diskTotalSpace;
	}

	public void setDiskTotalSpace(long diskTotalSpace) {
		this.diskTotalSpace = diskTotalSpace;
	}

	public long getDiskFreeSpace() {
		return diskFreeSpace;
	}

	public void setDiskFreeSpace(long diskFreeSpace) {
		this.diskFreeSpace = diskFreeSpace;
	}

	public long getDiskUsedSpace() {
		return diskUsedSpace;
	}

	public void setDiskUsedSpace(long diskUsedSpace) {
		this.diskUsedSpace = diskUsedSpace;
	}
}