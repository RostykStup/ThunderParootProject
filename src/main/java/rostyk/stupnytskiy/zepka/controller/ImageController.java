package rostyk.stupnytskiy.zepka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rostyk.stupnytskiy.zepka.dto.request.AddImageRequest;
import rostyk.stupnytskiy.zepka.dto.request.CartRequest;
import rostyk.stupnytskiy.zepka.dto.response.CartResponse;
import rostyk.stupnytskiy.zepka.service.CartService;
import rostyk.stupnytskiy.zepka.service.PublicationService;

import java.io.IOException;

@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    PublicationService publicationService;

    @PutMapping("main")
    public void updateImage(Long id, String imageName){
        publicationService.updateImage(id,imageName);
    }

    @PutMapping()
    public void addImage(Long id,  @RequestBody AddImageRequest request) throws IOException {
        publicationService.addImage(id,request);
    }

    @DeleteMapping()
    public void deleteImage(Long id, String imageName){
        publicationService.deleteImage(id,imageName);
    }

}
