package hyangyu.server.api;

import hyangyu.server.dto.event.SearchDto;
import hyangyu.server.dto.event.SearchResponseDto;
import hyangyu.server.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SearchApi {

    private final SearchService searchService;

    @PostMapping("/search")
    public ResponseEntity getSearchDate(@RequestBody Map<String, String> request) throws Exception {
        String keyword = request.get("keyword");
        SearchResponseDto searchData = searchService.getSearchData(keyword);
        SearchDto searchDto = new SearchDto(200, searchData);
        return new ResponseEntity(searchDto, HttpStatus.OK);
    }
}
