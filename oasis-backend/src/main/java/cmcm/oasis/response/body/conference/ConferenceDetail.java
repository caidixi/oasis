package cmcm.oasis.response.body.conference;

import cmcm.oasis.response.body.NameAndId;
import cmcm.oasis.response.body.paper.PaperMessage;

import java.util.LinkedList;
import java.util.List;

public class ConferenceDetail {
    private String name;
    private Long conferenceId;
    private List<NameAndId> fieldOfResearches;
    private List<PaperMessage> papers;

    public ConferenceDetail(String name, Long conferenceId) {
        this.name = name;
        this.conferenceId = conferenceId;
        fieldOfResearches = new LinkedList<>();
        papers = new LinkedList<>();
    }

    public ConferenceDetail() {
    }

    public void addFieldOfResearch(LinkedList<NameAndId> nameAndId){
        fieldOfResearches.addAll(nameAndId);
    }

    public void addPaper(PaperMessage paper){
        papers.add(paper);
    }

    public String getName() {
        return name;
    }

    public Long getConferenceId() {
        return conferenceId;
    }

    public List<NameAndId> getFieldOfResearches() {
        return fieldOfResearches;
    }

    public List<PaperMessage> getPapers() {
        return papers;
    }
}
