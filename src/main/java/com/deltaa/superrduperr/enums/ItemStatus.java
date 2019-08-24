package com.deltaa.superrduperr.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * Enum for item status
 * @author Rajesh
 */
public enum ItemStatus {
	STARTED, COMPLETED;
	
	public static ItemStatus fromName(String name, ItemStatus defaultStatus) {
		if(StringUtils.isBlank(name)) {
    		return defaultStatus;
    	}
    	
        for (ItemStatus status: ItemStatus.values()) {
            if (status.name().equalsIgnoreCase(name)) {
                return status;
            }
        }
        return defaultStatus;
	}
}
