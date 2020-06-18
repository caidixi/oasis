package cmcm.oasis.controller;

import cmcm.oasis.response.Response;
import cmcm.oasis.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class MainController {

    private final PaperService paperService;

    @Autowired
    public MainController(PaperService paperService) {
        this.paperService = paperService;
    }

    @PostMapping(value = "/administrator/crawlData")
    public Response crawlData(@RequestParam("conference")String conference, @RequestParam("start")int start, @RequestParam("end")int end){
        return paperService.crawlData(conference,start,end);
    }

    @PostMapping(value = "/administrator/uploadData")
    public Response uploadData(@RequestParam("file") MultipartFile file){
        return paperService.LoadData(file);
    }
}
