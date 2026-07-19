package com.alex.researchhub.ai.dto;

import java.util.List;

public class NoveltyRequest {

    private String userAbstract;

    private List<PaperDTO> papers;

    public NoveltyRequest() {
    }

    public NoveltyRequest(String userAbstract, List<PaperDTO> papers) {
        this.userAbstract = userAbstract;
        this.papers = papers;
    }

    public String getUserAbstract() {
        return userAbstract;
    }

    public void setUserAbstract(String userAbstract) {
        this.userAbstract = userAbstract;
    }

    public List<PaperDTO> getPapers() {
        return papers;
    }

    public void setPapers(List<PaperDTO> papers) {
        this.papers = papers;
    }
}