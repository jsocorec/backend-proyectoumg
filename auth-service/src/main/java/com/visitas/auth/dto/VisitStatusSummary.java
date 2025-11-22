package com.visitas.auth.dto;

public class VisitStatusSummary {
    private String status;
    private Long count;

    public VisitStatusSummary(String status, Long count) {
        this.status = status;
        this.count = count;
    }

    public String getStatus() { return status; }
    public Long getCount() { return count; }

    public void setStatus(String status) { this.status = status; }
    public void setCount(Long count) { this.count = count; }
}
