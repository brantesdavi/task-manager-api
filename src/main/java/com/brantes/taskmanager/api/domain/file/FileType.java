package com.brantes.taskmanager.api.domain.file;

public enum FileType {
    TASK(new String[]{"image/png", "image/jpeg", "image/gif", "application/pdf", "application/vnd.ms-excel", "application/vnd.openxmlformats-officedocument.wordprocessingml.document", "application/vnd.openxmlformats-officedocument.presentationml.presentation"}),
    IMAGE_ONLY(new String[]{"image/png", "image/jpeg", "image/gif"});

    private String[] allowedFormats;

    FileType(String[] allowedFormats) {
        this.allowedFormats = allowedFormats;
    }

    public String[] getAllowedFormats() {
        return allowedFormats;
    }

    public boolean isAllowed(String fileFormat) {
        for (String format : allowedFormats) {
            if (format.equals(fileFormat)) {
                return true;
            }
        }
        return false;
    }

}
