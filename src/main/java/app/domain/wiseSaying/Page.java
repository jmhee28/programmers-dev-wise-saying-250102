package app.domain.wiseSaying;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class Page {

    public List<WiseSaying> wiseSayings;
    public int totalPages;
    public int totalItems;

    public List<WiseSaying> getWiseSayings() {
        return wiseSayings;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public int getTotalPages() {
        return totalPages;
    }

}
