package cmcm.oasis.service;

import cmcm.oasis.module.Paper;
import org.springframework.web.multipart.MultipartFile;

public interface DataService {
    void updateData(MultipartFile file);

    void inputData(Paper paper, String publicationTitle);


}
