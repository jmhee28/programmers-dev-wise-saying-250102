package app.domain.wiseSaying.repository;

import app.domain.wiseSaying.WiseSaying;
import app.domain.wiseSaying.WiseSayingService;
import app.global.AppConfig;
import app.standard.Util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class WiseSayingFileRepository implements WiseSayingRepository {

    private static final String DB_PATH = AppConfig.getDbPath() + "/wiseSaying";
    private static final String ID_FILE_PATH = DB_PATH + "/lastId.txt";
    private static final String BUILD_PATH = DB_PATH + "/build/data.json";

    public WiseSayingFileRepository() {
        System.out.println("파일 DB 사용");
        init();
    }

    public void init() {
        if (!Util.File.exists(ID_FILE_PATH)) {
            Util.File.createFile(ID_FILE_PATH);
        }

        if (!Util.File.exists(DB_PATH)) {
            Util.File.createDir(DB_PATH);
        }
    }

    public WiseSaying save(WiseSaying wiseSaying) {

        boolean isNew = wiseSaying.isNew();

        if (isNew) {
            wiseSaying.setId(getLastId() + 1);
        }

        Util.Json.writeAsMap(getFilePath(wiseSaying.getId()), wiseSaying.toMap());

        if (isNew) {
            setLastId(wiseSaying.getId());
        }

        return wiseSaying;
    }

    public List<WiseSaying> findAll() {


//        명령형
//        List<Path> paths = Util.File.getPaths(DB_PATH);
//        List<WiseSaying> wiseSayingList = new ArrayList<>();
//        
//        for(Path path : paths) {
//            String filePath = path.toString();
//            Map<String, Object> map = Util.Json.readAsMap(filePath);
//            WiseSaying wiseSaying = WiseSaying.fromMap(map);
//            wiseSayingList.add(wiseSaying);
//        }
//        
//        return wiseSayingList;


        // 선언형
        return Util.File.getPaths(DB_PATH).stream()
                .map(Path::toString)
                .filter(path -> path.endsWith(".json"))
                .map(Util.Json::readAsMap)
                .map(WiseSaying::fromMap)
                .toList();

    }

    public boolean deleteById(int id) {
        return Util.File.delete(getFilePath(id));
    }

    public Optional<WiseSaying> findById(int id) {
        String path = getFilePath(id);
        Map<String, Object> map = Util.Json.readAsMap(path);

        if (map.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(WiseSaying.fromMap(map));

    }

    public static String getFilePath(int id) {
        return DB_PATH + "/" + id + ".json";
    }

    public int getLastId() {
        String idStr = Util.File.readAsString(ID_FILE_PATH);

        if (idStr.isEmpty()) {
            return 0;
        }

        try {
            return Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public void setLastId(int id) {
        Util.File.write(ID_FILE_PATH, id);
    }

    public void build() {

        List<Map<String, Object>> mapList = findAll().stream()
                .map(WiseSaying::toMap)
                .toList();

        String jsonStr = Util.Json.listToJson(mapList);
        Util.File.write(BUILD_PATH, jsonStr);
    }

    public static String getBuildPath() {
        return BUILD_PATH;
    }
}
