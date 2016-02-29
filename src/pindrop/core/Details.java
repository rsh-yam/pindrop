package pindrop.core;

import java.lang.management.ManagementFactory;

import java.io.File;

import com.sun.management.OperatingSystemMXBean;

@SuppressWarnings("restriction")
public class Details {

	public static OperatingSystemMXBean bean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

	public static String getMem() {
		StringBuffer sb = new StringBuffer();

		long totalMem = bean.getTotalPhysicalMemorySize();
		long freeMem = bean.getFreePhysicalMemorySize();
		long totalSwap = bean.getTotalSwapSpaceSize();
		long freeSwap = bean.getFreeSwapSpaceSize();

		if (totalMem != -1) {
			sb.append("<totalMem>");
			sb.append(totalMem);
			sb.append("</totalMem>");
		}
		if (freeMem != -1) {
			sb.append("<freeMem>");
			sb.append(freeMem);
			sb.append("</freeMem>");
		}
		if (totalSwap != -1) {
			sb.append("<totalSwap>");
			sb.append(totalSwap);
			sb.append("</totalSwap>");
		}
		if (freeSwap != -1) {
			sb.append("<freeSwap>");
			sb.append(freeSwap);
			sb.append("</freeSwap>");
		}

		Memory.insert(totalMem, freeMem, totalSwap, freeSwap);
		return sb.toString();
	}

	public static String getCPU() {
		StringBuffer sb = new StringBuffer();
		double cpu = bean.getSystemLoadAverage() / bean.getAvailableProcessors();
		sb.append("<cpu>");
		sb.append(cpu);
		sb.append("</cpu>");
		CPU.insert(cpu);
		return sb.toString();

	}

	public static String getDisk() {
		StringBuffer sb = new StringBuffer();
		File[] drives = File.listRoots();
		for (File f : drives) {
			sb.append("<file>");
			sb.append("<usable>");
			long usable = f.getUsableSpace();
			sb.append(usable + "</usable>");
			sb.append("<free>");
			long free = f.getFreeSpace();
			sb.append(free + "</free>");
			sb.append("<total>");
			long total = f.getTotalSpace();
			sb.append(total + "</total>");
			sb.append("</file>");
			Disk.insert(usable, free, total);
		}

		return sb.toString();
	}

}