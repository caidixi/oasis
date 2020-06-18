package cmcm.oasis.controller;

import cmcm.oasis.response.Response;
import cmcm.oasis.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    private final PaperService paperService;

    @Autowired
    public UserController(PaperService paperService) {
        this.paperService = paperService;
    }

    @GetMapping(value = "/queryPaper")
    public Response queryPaper(@RequestParam("authors") String authors,@RequestParam("institute") String institute,
                               @RequestParam("conference") String conference,@RequestParam("keyword") String keyword,
                               @RequestParam("startYear")int startYear,@RequestParam("endYear")int endYear){
        return paperService.queryPaper(authors,institute,conference,keyword,startYear,endYear);
    }

    @GetMapping(value = "/interval")
    public Response queryInterval(){
        return paperService.queryInterval();
    }

    @GetMapping(value = "/queryPaperDetail")
    public Response queryPaperDetail(@RequestParam("DOI")String DOI){
        return paperService.queryPaperDetail(DOI);
    }

    @GetMapping(value = "/queryAuthorDetail")
    public Response queryAuthorDetail(@RequestParam("authorId")Long authorId){
        return paperService.queryAuthorDetail(authorId);
    }

    @GetMapping(value = "/queryInstituteDetail")
    public Response queryInstituteDetail(@RequestParam("institutionId")Long institutionId){
        return paperService.queryInstituteDetail(institutionId);
    }

    @GetMapping(value = "/queryConferenceDetail")
    public Response queryConferenceDetail(@RequestParam("conferenceId")Long conferenceId){
        return paperService.queryConferenceDetail(conferenceId);
    }

    @GetMapping(value = "/queryResearchDirectionDetail")
    public Response queryResearchDirectionDetail(@RequestParam("researchDirectionId")Long researchDirectionId){
        return paperService.queryResearchDirectionDetail(researchDirectionId);
    }

    @GetMapping(value = "/trend/paperTrend")
    public Response getPaperTrend(@RequestParam("sort")String sort){
        return paperService.getPaperTrend(sort);
    }

    @GetMapping(value = "/trend/authorTrend")
    public Response getAuthorTrend(@RequestParam("sort")String sort){
        return paperService.getAuthorTrend(sort);
    }

    @GetMapping(value = "/trend/instituteTrend")
    public Response getInstituteTrend(@RequestParam("sort")String sort){
        return paperService.getInstituteTrend(sort);
    }

    @GetMapping(value = "/trend/researchHotSpots")
    public Response getResearchHotSpots(@RequestParam("sort")String sort){
        return paperService.getResearchHotSpots(sort);
    }


    @GetMapping(value = "/statistics")
    public Response getStatistics(){
        return paperService.getInterestingPoints();
    }

    @GetMapping(value = "/segmentation")
    public Response getSegmentation(@RequestParam("type")String type,@RequestParam("percentage")double percentage){
        return paperService.getSegmentation(type,percentage);
    }

    @GetMapping(value = "/graph/author")
    public Response getAuthorGraph(){
        return paperService.getMainAuthorGraph();
    }

    
}
