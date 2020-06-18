package cmcm.oasis.util;

import java.util.List;

public class PaperInfo {
    public String title;
    public List<AuthorInfo> authorInfos;
    public String abstract_;
    public List<String> keywords;
    public String publication;
    public String doi;
    public int ref;

    public String link_num;

    public String toString(){
        return "title:"+title+" authorInfos:"+ authorInfos +" abstract:"+
                abstract_+" keywords:"+keywords+" publication:"+
                publication+" doi:"+doi+" ref:"+ref;
    }
}
