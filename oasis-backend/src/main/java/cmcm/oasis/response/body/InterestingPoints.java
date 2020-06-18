package cmcm.oasis.response.body;

import cmcm.oasis.module.Paper;
import cmcm.oasis.response.body.paper.PaperShortcut;

import java.util.ArrayList;
import java.util.List;

public class InterestingPoints {
    private List<PaperShortcut> top3ReferenceCount;

    public InterestingPoints(List<Paper> top3ReferencePaper) {
        top3ReferenceCount = new ArrayList<>();
        for(Paper paper:top3ReferencePaper){
            top3ReferenceCount.add(new PaperShortcut(paper));
        }
    }

    public List<PaperShortcut> getTop3ReferenceCount() {
        return top3ReferenceCount;
    }
}
