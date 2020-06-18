package cmcm.oasis;

import cmcm.oasis.module.Paper;
import cmcm.oasis.response.Response;
import cmcm.oasis.response.body.InterestingPoints;
import cmcm.oasis.response.body.paper.PaperShortcut;
import cmcm.oasis.service.PaperService;
import org.apache.poi.util.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PaperServiceTests {
    @Autowired
    PaperService paperService;

    @Test//file=正确格式CSV学术信息
    void loadData_1() throws IOException {
        File file = new File("src/test/resources/ase13_15_16_17_19.csv");
        FileInputStream fileInputStream = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("test",file.getName(),"text/plain", IOUtils.toByteArray(fileInputStream));
        paperService.LoadData(multipartFile);
    }

    @Test//file=重复CSV学术信息
    void loadData_2() throws IOException {
        File file = new File("src/test/resources/icse15_16_17_18_19.csv");
        FileInputStream fileInputStream = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("test",file.getName(),"text/plain", IOUtils.toByteArray(fileInputStream));
        paperService.LoadData(multipartFile);
        paperService.LoadData(multipartFile);
    }

    @Test//file=错误格式CSV学术信息
    void loadData_3(){

    }

    @Test//file=覆盖CSV学术信息
    void loadData_4(){

    }

    @Test//authorInfos
    void queryPaper_1(){
        Response response = paperService.queryPaper("S. Yu","","","",0,2020);
        List<Paper> papers = (List<Paper>)response.getBody();
//        for(PaperInfo paper:papers)
//            System.out.println(paper.getAuthorKeywords());
        String[] eqs = {"Crowdsourced Testing;Mobile App Testing;Bug Report Generation",
                "Cross-Platform Testing;Image Recognition;Record and Replay"};
        for(int i=0;i<2;i++){
            assertEquals(papers.get(i).getAuthorKeywords(),eqs[i]);
        }
    }

    @Test//institute
    void queryPaper_2(){
        Response response = paperService.queryPaper("","Nanjing University","","",0,2020);
        List<Paper> papers = (List<Paper>)response.getBody();
//        for(PaperInfo paper:papers)
//            System.out.println(paper.getAuthorKeywords());
        String[] eqs = {"Python, call graph, empirical study, quantitative, qualitative",
                "Crowdsourced Testing;Mobile App Testing;Bug Report Generation",
                "Cross-Platform Testing;Image Recognition;Record and Replay",
                "Visualization;Neural Network Visualization;Program Comprehension"};
        for(int i=0;i<4;i++){
            assertEquals(papers.get(i).getAuthorKeywords(),eqs[i]);
        }
    }

    @Test//conference
    void queryPaper_3(){
        Response response = paperService.queryPaper("","","IEEE Conferences","",0,2020);
        List<Paper> papers = (List<Paper>)response.getBody();
//        for(PaperInfo paper:papers)
//            System.out.println("!"+paper.getAuthorKeywords());
        String[] eqs = {"Program Repair;JVM Bytecode;Mutation Testing",
                "pointer analysis;uninitialized pointer;sensitivity;multi-entry",
                "comment generation;program comprehension;deep learning",
                "software testing;automated test input generation;graphical user interface;deep learning;mobile application;Android"};
        for(int i=0;i<4;i++){
            assertEquals(papers.get(i+2).getAuthorKeywords(),eqs[i]);
        }
    }

    @Test//Keyword
    void queryPaper_4(){
        //Python
        Response response = paperService.queryPaper("","","","Python",0,2020);
        List<Paper> papers = (List<Paper>)response.getBody();
//        for(PaperInfo paper:papers)
//            System.out.println("!"+paper.getAuthorKeywords());
        String[] eqs = {"Python, call graph, empirical study, quantitative, qualitative",
                "Dynamic Analysis;Static Analysis;Python;Type Inference;Test Generation",
                "Docker;Configuration Management;Environment Inference;Dependencies;Python",
                "Debugging;Probabilistic Inference;Python"};
        for(int i=0;i<4;i++){
            assertEquals(papers.get(i).getAuthorKeywords(),eqs[i]);
        }
    }

    @Test//author Keyword
    void queryPaper_5(){
        Response response = paperService.queryPaper("S. Yu","","","Record and Replay",0,2020);
        List<Paper> papers = (List<Paper>)response.getBody();
//        for(PaperInfo paper:papers)
//            System.out.println(paper.getAuthorKeywords());
        String[] eqs = {"Cross-Platform Testing;Image Recognition;Record and Replay"};
        for(int i=0;i<1;i++){
            assertEquals(papers.get(i).getAuthorKeywords(),eqs[i]);
        }
    }

    @Test//institute conference
    void queryPaper_6(){
        Response response = paperService.queryPaper("","Nanjing University","IEEE Conferences","",0,2020);
        List<Paper> papers = (List<Paper>)response.getBody();
//        for(PaperInfo paper:papers)
//            System.out.println(paper.getAuthorKeywords());
        String[] eqs = {"Python, call graph, empirical study, quantitative, qualitative",
                "Crowdsourced Testing;Mobile App Testing;Bug Report Generation",
                "Cross-Platform Testing;Image Recognition;Record and Replay",
                "Visualization;Neural Network Visualization;Program Comprehension"};
        for(int i=0;i<4;i++){
            assertEquals(papers.get(i).getAuthorKeywords(),eqs[i]);
        }
    }

    @Test//authorInfos conference institute Keyword
    void queryPaper_7(){
        Response response = paperService.queryPaper("S. Yu","Nanjing University","IEEE Conferences","Record and Replay",0,2020);
        List<Paper> papers = (List<Paper>)response.getBody();
//        for(PaperInfo paper:papers)
//            System.out.println(paper.getAuthorAffiliations());
        String[] eqs = {"Cross-Platform Testing;Image Recognition;Record and Replay"};
        for(int i=0;i<1;i++){
            assertEquals(papers.get(i).getAuthorKeywords(),eqs[i]);
        }
    }

    @Test//null*4
    void queryPaper_8(){
        Response response = paperService.queryPaper("","","","",0,2020);
        List<Paper> papers = (List<Paper>)response.getBody();
        String[] eqs = {"J. Penix","A. Ghanbari",
                "Y. Wang; G. Chen; M. Zhou; M. Gu; J. Sun","B. Wei"};
        for(int i=0;i<4;i++){
            assertEquals(papers.get(i+1).getAuthors(),eqs[i]);
        }
    }

    @Test//异常输入-authorInfos
    void queryPaper_9(){

    }

    @Test//异常输入-authorInfos、conference
    void queryPaper_10(){

    }

    @Test//异常输入-institute、Keyword
    void queryPaper_11(){

    }

    @Test//空结果
    void queryPaper_12(){
        Response response = paperService.queryPaper("!L!","","","",0,2020);
        List<Paper> papers = (List<Paper>)response.getBody();
        assertEquals(0,papers.size());
    }

    @Test//信息分析_首页
    void getInterestingPoints_1(){
        Response response = paperService.getInterestingPoints();
        InterestingPoints interestingPoints = (InterestingPoints)response.getBody();
        String[] eqs = {"M. White; M. Tufano; C. Vendome; D. Poshyvanyk","Y. Wang",
                "Z. Lin; D. Marinov; H. Zhong; Y. Chen; J. Zhao"};
        int i=0;
        for(PaperShortcut paper:interestingPoints.getTop3ReferenceCount()){
            assertEquals(paper.getAuthors(),eqs[i++]);
        }
    }

    @Test//信息分析_空结果
    void getInterestingPoints_2(){
    }

    @Test//信息分析_检索结果
    void paperService_getInterestingPoints_First(){
    }

    @Test
    void displayPaper(Paper paper){

    }
}
