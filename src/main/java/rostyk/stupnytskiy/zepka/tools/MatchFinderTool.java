package rostyk.stupnytskiy.zepka.tools;

import org.springframework.stereotype.Component;
import rostyk.stupnytskiy.zepka.dto.response.PublicationResponse;
import rostyk.stupnytskiy.zepka.entity.Publication;

@Component
public class MatchFinderTool {

    public Integer findMatchPointsFromPublication(String request, Publication p) {
        if (request != null) {
            return findMatchPointsFromName(request, p.getName())
                    + findMatchPointsFromDescription(request, p.getDescription())
                    + findMatchPointsFromName(request,p.getSubcategory().getName())
                    + findMatchPointsFromName(request,p.getSubcategory().getCategory().getName());
        } else return 100;
    }

    public static Integer findMatchPointsFromName(String request, String name) {
        Integer points = 0;
        String[] requestArr = request.toLowerCase().split(" ");
        String[] nameArr = name.toLowerCase().split(" ");

        for (String req : requestArr){
            for (String nam : nameArr) {
                if (req.equals(nam)) points+=1000;
                else {
                    if(nam.contains(req) || req.contains(nam)) points+=100;
                }
            }
        }
        return points;
    }

    public static Integer findMatchPointsFromDescription(String request, String description) {
        Integer points = 0;
        String[] requestArr = request.toLowerCase().split(" ");
        String[] nameArr = description.toLowerCase().split(" ");
        for (String req : requestArr){
            for (String nam : nameArr) {
                if (req.equals(nam)) points+=100;
                else {
                    if(nam.contains(req) || req.contains(nam)) points+=10;
                }
            }
        }

        return points;
    }
}
