package com.sensor.agri.smartagriculturebackend.utils.enums;

public enum EStatus {
    ACTIVE,          // Entity is active and in use
    INACTIVE,        // Entity is inactive but not deleted
    DELETED,         // Entity is marked for deletion (soft delete)
    PENDING,         // Entity is awaiting approval or action
    ARCHIVED,        // Entity is archived and not actively used
    SUSPENDED,       // Entity is temporarily disabled
    DRAFT            // Entity is in draft mode and not finalized
}
