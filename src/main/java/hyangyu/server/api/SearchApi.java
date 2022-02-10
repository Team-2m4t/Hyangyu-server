package hyangyu.server.api;

import hyangyu.server.dto.event.SearchDto;
import hyangyu.server.dto.event.SearchResponseDto;
import hyangyu.server.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SearchApi {

    private final SearchService searchService;

    @GetMapping("/search/{keyword}")
    public ResponseEntity getSearchDate(@PathVariable String keyword) throws Exception {
        SearchResponseDto searchData = searchService.getSearchData(keyword);
        SearchDto searchDto = new SearchDto(200, searchData);
        return new ResponseEntity(searchDto, HttpStatus.OK);
    }
}
