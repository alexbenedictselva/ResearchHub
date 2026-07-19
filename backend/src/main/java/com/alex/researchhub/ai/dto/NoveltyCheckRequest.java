package com.alex.researchhub.ai.dto;

import java.util.List;

public class NoveltyCheckRequest {

    private String userAbstract;
    private List<PaperIdItem> papers;

    public String getUserAbstract() { return userAbstract; }
    public void setUserAbstract(String userAbstract) { this.userAbstract = userAbstract; }

    public List<PaperIdItem> getPapers() { return papers; }
    public void setPapers(List<PaperIdItem> papers) { this.papers = papers; }

    public static class PaperIdItem {
        private String paperId;
        public String getPaperId() { return paperId; }
        public void setPaperId(String paperId) { this.paperId = paperId; }
    }
}
